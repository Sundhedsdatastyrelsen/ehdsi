package dk.sundhedsdatastyrelsen.ncpeh.jobqueue;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/// A persistent, SQLite-backed job queue.
///
/// Not thread-safe.
public class JobQueue<T> implements AutoCloseable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JobQueue.class);

    private final Connection conn;
    private final String queueName;
    private final Class<T> type;
    private final ObjectMapper mapper;
    private final Duration visibilityTimeout;

    private JobQueue(Connection conn, String queueName, Class<T> type, ObjectMapper mapper, Duration visibilityTimeout) {
        this.conn = conn;
        this.queueName = queueName;
        this.type = type;
        this.mapper = mapper;
        this.visibilityTimeout = visibilityTimeout;
    }

    public record JobId(long value) {}

    public record ReservedJob<T>(JobId id, T payload, int attempt) {}

    /**
     * Open (or create) a named job queue.
     *
     * @param sqliteDatasource the connection info for the underlying sqlite db
     * @param queueName the name of the queue
     * @param payloadType the reified type of the payload (same as T)
     * @param visibilityTimeout timeout before a job becomes available again after reservation. Resolution in milliseconds.
     * @param <T> the type of the payload (same as payloadType)
     * @return the job queue object.
     * @throws SQLException if something goes wrong when opening the queue.
     */
    @SuppressWarnings("java:S2095")
    public static <T> JobQueue<T> open(
        DataSource sqliteDatasource,
        String queueName,
        Class<T> payloadType,
        Duration visibilityTimeout
    ) throws SQLException {
        var conn = sqliteDatasource.getConnection();
        conn.setAutoCommit(false);
        ensureSchema(conn);
        var objectMapper = new ObjectMapper();
        // enable and fix handling of java.time values
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new JobQueue<>(conn, queueName, payloadType, objectMapper, visibilityTimeout);
    }

    ///  Put payload in job queue.
    public JobId enqueue(T payload) {
        var sql = "INSERT INTO jobs (queue, payload, available_at) VALUES (?, ?, datetime('now', 'subsecond'))";
        try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            var json = mapper.writeValueAsString(payload);
            stmt.setString(1, queueName);
            stmt.setString(2, json);
            stmt.executeUpdate();
            var keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                conn.commit();
                return new JobId(keys.getLong(1));
            }
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e2) {
                // ignore
            }
            throw new JobQueueException("Could not insert job into queue", e);
        }
        throw new JobQueueException("Could not insert job into queue");
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
        List<ReservedJob<T>> result = new ArrayList<>();
        try (var pstmt = conn.prepareStatement(sql);
             var update = conn.prepareStatement("""
                 UPDATE jobs
                 SET available_at=datetime('now', 'subsecond', ?),
                     attempt=attempt+1
                 WHERE id=?""")) {
            pstmt.setString(1, queueName);
            pstmt.setInt(2, maxJobs);

            var rs = pstmt.executeQuery();
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
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
                // ignore
            }
            if (e instanceof MismatchedInputException) {
                throw new JobQueueException("Could not deserialize job payload to " + type, e);
            }
            throw new JobQueueException("Could not fetch jobs from queue", e);
        }
        return result;
    }

    private static String durationModifier(Duration duration) {
        return "+%s seconds".formatted(duration.toMillis() / 1000.0);
    }

    ///  Remove jobs from queue.
    public void ack(List<JobId> ids) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM jobs WHERE id=?")) {
            for (var id : ids) {
                stmt.setLong(1, id.value());
                stmt.addBatch();
            }
            stmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
                // ignore
            }
            throw new JobQueueException("Failed when marking jobs as completed", e);
        }
    }

    /// Return jobs to queue (increments attempts).
    public void nack(List<JobId> ids) {
        try (PreparedStatement stmt = conn.prepareStatement("""
            UPDATE jobs
            SET available_at=datetime('now', 'subsecond')
            WHERE id=?
            """)) {
            for (var id : ids) {
                stmt.setLong(1, id.value());
                stmt.addBatch();
            }
            var res = stmt.executeBatch();
            conn.commit();
            if (log.isWarnEnabled()) {
                for (var i = 0; i < res.length; i++) {
                    if (res[i] == 0) {
                        log.warn("Could not return job to queue, it has most likely already been marked as completed: {}", ids.get(i));
                    }
                }
            }
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
                // ignore
            }
            throw new JobQueueException("Failed when returning jobs to queue", e);
        }
    }

    /// Count how many jobs are in queue.
    ///
    /// Synchronized, so it can be run by multiple threads.
    public synchronized long size() {
        try (var stmt = conn.prepareStatement("SELECT COUNT(1) FROM jobs WHERE queue=?")) {
            stmt.setString(1, queueName);
            var rs = stmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
                // ignore
            }
            throw new JobQueueException("Could not retrieve queue size", e);
        }
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

    private static void ensureSchema(Connection conn) throws SQLException {
        // Be careful here!
        // When updating the schema, it is crucial that the existing sqlite files be updated to reflect the new schema.
        // Otherwise, the queue will likely crash.  The simplest would be to make sure the db is empty, delete it, and
        // restart the service.
        try (var stmt = conn.createStatement()) {
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
}
