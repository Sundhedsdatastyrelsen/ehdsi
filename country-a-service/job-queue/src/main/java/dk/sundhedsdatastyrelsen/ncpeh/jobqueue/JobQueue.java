package dk.sundhedsdatastyrelsen.ncpeh.jobqueue;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// A persistent, SQLite-backed job queue.
///
/// Each queue has a name and a payload type. The payload type is a class which can be serialized and deserialized
/// to JSON with Jackson's [ObjectMapper].
///
/// Jobs are enqueued one at a time with [#enqueue(Object)].
///
/// To process jobs, the first step is to reserve a batch with [#reserve(int)]. After the jobs are reserved, a
/// visibility timer begins its countdown, after which the jobs will be available again. This ensures that no jobs are
/// lost if the application crashes. When a batch of jobs has been handled successfully it can be acked
/// ([#ack(java.util.List)]) which means it will be deleted from the queue. Jobs can also be nack'ed ([#nack(java.util.List)])
/// to make them immediately available for retrying.
public class JobQueue<T> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JobQueue.class);

    private final DataSource ds;
    private final String queueName;
    private final Class<T> type;
    private final ObjectMapper mapper;
    private final Duration visibilityTimeout;

    private JobQueue(DataSource ds, String queueName, Class<T> type, ObjectMapper mapper, Duration visibilityTimeout) {
        this.ds = ds;
        this.queueName = queueName;
        this.type = type;
        this.mapper = mapper;
        this.visibilityTimeout = visibilityTimeout;
    }

    public record JobId(long value) {}

    public record ReservedJob<T>(JobId id, T payload, int attempt) {}

    /// Open (or create) a named job queue.
    ///
    /// @param sqliteDatasource  the connection info for the underlying sqlite db
    /// @param queueName         the name of the queue
    /// @param payloadType       the reified type of the payload (same as T)
    /// @param visibilityTimeout timeout before a job becomes available again after reservation. Resolution in milliseconds.
    /// @param <T>               the type of the payload (same as payloadType)
    /// @return the job queue object.
    /// @throws SQLException if something goes wrong when opening the queue.
    @SuppressWarnings("java:S2095")
    public static <T> JobQueue<T> open(
        DataSource sqliteDatasource,
        String queueName,
        Class<T> payloadType,
        Duration visibilityTimeout
    ) throws SQLException {
        var objectMapper = new ObjectMapper();
        // enable and fix handling of java.time values
        objectMapper.registerModule(new JavaTimeModule());

        //Preserve original time zone information for deserialization
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        //Write dates in a human readable format instead of epoch
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var jq = new JobQueue<>(sqliteDatasource, queueName, payloadType, objectMapper, visibilityTimeout);
        jq.ensureSchema();
        return jq;
    }

    ///  Put payload in job queue.
    public JobId enqueue(T payload) {
        var sql = "INSERT INTO jobs (queue, payload, available_at) VALUES (?, ?, datetime('now', 'subsecond'))";
        return withTx(
            conn -> {
                try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    var json = mapper.writeValueAsString(payload);
                    stmt.setString(1, queueName);
                    stmt.setString(2, json);
                    stmt.executeUpdate();
                    var keys = stmt.getGeneratedKeys();
                    if (keys.next()) {
                        return new JobId(keys.getLong(1));
                    }
                    throw new JobQueueException("Could not insert job into queue");
                }
            }, ex -> {
                throw new JobQueueException("Could not insert job into queue", ex);
            });
    }

    /// Retrieve the next jobs in the queue, up to `maxJobs`.
    ///
    /// Hide the retrieved jobs until they are either:
    /// - removed with [#ack]
    /// - the visibilityTimeout duration elapses
    /// - they are made visible again with [#nack].
    public List<ReservedJob<T>> reserve(int maxJobs) {
        if (maxJobs < 1) {
            throw new IllegalArgumentException("maxJobs must be at least 1");
        }
        var sql = """
            SELECT id, payload, attempt
            FROM jobs
            WHERE queue = ?
            AND available_at <= datetime('now', 'subsecond')
            ORDER BY created_at
            LIMIT ?""";
        return withTx(conn -> {
            try (var pstmt = conn.prepareStatement(sql);
                 var update = conn.prepareStatement("""
                     UPDATE jobs
                     SET available_at=datetime('now', 'subsecond', ?),
                         attempt=attempt+1
                     WHERE id=?""")) {
                pstmt.setString(1, queueName);
                pstmt.setInt(2, maxJobs);

                var rs = pstmt.executeQuery();
                List<ReservedJob<T>> result = new ArrayList<>();
                while (rs.next()) {
                    var id = rs.getLong("id");
                    var attempt = rs.getInt("attempt");
                    var payload = rs.getString("payload");
                    var obj = mapper.readValue(payload, type);

                    update.setString(1, durationModifier(visibilityTimeout));
                    update.setLong(2, id);
                    update.addBatch();
                    result.add(new ReservedJob<>(new JobId(id), obj, attempt));
                }
                update.executeBatch();
                return result;
            }
        }, e -> {
            if (e instanceof MismatchedInputException) {
                throw new JobQueueException("Could not deserialize job payload to " + type, e);
            }
            throw new JobQueueException("Could not fetch jobs from queue", e);
        });
    }

    private static String durationModifier(Duration duration) {
        return "+%s seconds".formatted(duration.toMillis() / 1000.0);
    }

    ///  Remove jobs from queue.
    public void ack(List<JobId> ids) {
        withTx(conn -> {
            try (var stmt = conn.prepareStatement("DELETE FROM jobs WHERE id=?")) {
                for (var id : ids) {
                    stmt.setLong(1, id.value());
                    stmt.addBatch();
                }
                stmt.executeBatch();
                return null;
            }
        }, e -> {
            throw new JobQueueException("Failed when marking jobs as completed", e);
        });
    }

    /// Return jobs to queue (increments attempts, indirectly, by returning them to available in the queue (attempts
    /// are incremented during reservation)).
    public void nack(List<JobId> ids) {
        withTx(conn -> {
            try (var stmt = conn.prepareStatement("""
            UPDATE jobs
            SET available_at=datetime('now', 'subsecond')
            WHERE id=?
            """)) {
                for (var id : ids) {
                    stmt.setLong(1, id.value());
                    stmt.addBatch();
                }
                var res = stmt.executeBatch();
                if (log.isWarnEnabled()) {
                    for (var i = 0; i < res.length; i++) {
                        if (res[i] == 0) {
                            log.warn("Could not return job to queue, it has most likely already been marked as completed: {}", ids.get(i));
                        }
                    }
                }
                return null;
            }
        }, e -> {
            throw new JobQueueException("Failed when returning jobs to queue", e);
        });
    }

    /// Count how many jobs are in queue.
    public long size() {
        try {
            try (var conn = ds.getConnection();
                 var stmt = conn.prepareStatement("SELECT COUNT(1) FROM jobs WHERE queue=?")) {
                stmt.setString(1, queueName);
                var rs = stmt.executeQuery();
                rs.next();
                return rs.getLong(1);
            }
        } catch (Exception e) {
            throw new JobQueueException("Could not retrieve queue size", e);
        }
    }

    private void ensureSchema() throws SQLException {
        // Be careful here!
        // When updating the schema, it is crucial that the existing sqlite files be updated to reflect the new schema.
        // Otherwise, the queue will likely crash.  The simplest would be to make sure the db is empty, delete it, and
        // restart the service.
        try (var conn = ds.getConnection();
             var stmt = conn.createStatement()) {
            conn.setAutoCommit(false);
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS jobs (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        queue TEXT NOT NULL,
                        payload TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT (datetime('now', 'subsecond')),
                        available_at TIMESTAMP DEFAULT (datetime('now', 'subsecond')),
                        attempt INTEGER DEFAULT 0
                    )
                """);
            stmt.executeUpdate("""
                CREATE INDEX IF NOT EXISTS jobs_queue_idx ON jobs (queue)
                """);
            conn.commit();
        }
    }

    @FunctionalInterface
    @SuppressWarnings("java:S112")
    private interface DbAction<U> {
        U run(Connection conn) throws Exception;
    }

    private <U> U withTx(DbAction<U> action, Consumer<Exception> exceptionHandler) {
        try {
            try (var conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                try {
                    var result = action.run(conn);
                    conn.commit();
                    return result;
                } catch (Exception e) {
                    try {
                        conn.rollback();
                    } catch (SQLException e2) {
                        log.debug("Rollback during error failed - ignoring exception");
                        // ignore
                    }
                    exceptionHandler.accept(e);
                    // we most likely won't get to here:
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new JobQueueException("Could not open SQLite connection", e);
        }
    }
}
