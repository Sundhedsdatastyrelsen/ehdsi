package dk.sundhedsdatastyrelsen.ncpeh.jobqueue;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FunMatcher.where;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JobQueueTest {
    static SingleConnectionDataSource ds() {
        return new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
    }

    static SingleConnectionDataSource ds(Path db) {
        return new SingleConnectionDataSource("jdbc:sqlite:" + db.toAbsolutePath(), true);
    }

    static JobQueue<TestPayload> jobQueue() throws SQLException {
        return jobQueue(ds());
    }

    static JobQueue<TestPayload> jobQueue(DataSource ds) throws SQLException {
        return JobQueue.open(ds, "test-queue", TestPayload.class, Duration.ofSeconds(30));
    }

    record TestPayload(String foo, long bar) {}

    record TestPayloadIncompatible(int foo, long bar, boolean baz) {}

    record TestPayloadConservativeExtension(String foo, long bar, boolean baz) {}

    record TestPayloadInstant(Instant instant, OffsetDateTime odt, LocalDate ld) {}

    @Test
    void testEnqueueAndReserve() throws Exception {
        var payload = new TestPayload("test-data", 42);
        var q = jobQueue();
        var jobId = q.enqueue(payload);
        assertThat(jobId, is(notNullValue()));

        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, contains(where(JobQueue.ReservedJob::payload, is(payload))));
    }

    @Test
    void testMultipleJobs() throws Exception {
        var payloads = List.of(
                new TestPayload("first", 1),
                new TestPayload("second", 2),
                new TestPayload("third", 3)
        );

        var q = jobQueue();
        payloads.stream().map(q::enqueue).toList();
        assertThat(q.size(), is(3L));

        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, hasSize(3));
        assertThat(
                reservedJobs.stream().map(JobQueue.ReservedJob::payload).toList(),
                contains(payloads.toArray()));
    }

    @Test
    void testPersistence() throws SQLException, IOException {
        var db = Files.createTempFile("job-queue", ".sqlite");
        try {
            var payload = new TestPayload("foo", 1313L);
            var jq1 = jobQueue(ds(db));
            jq1.enqueue(payload);

            var jq2 = jobQueue(ds(db));
            var res = jq2.reserve(1);
            assertThat(res, contains(where(JobQueue.ReservedJob::payload, equalTo(payload))));
        } finally {
            Files.delete(db);
        }
    }

    @Test
    void testWrongDeserializationClass() throws SQLException, IOException {
        var db = Files.createTempFile("job-queue", ".sqlite");
        try {
            var payload = new TestPayload("foo", 1313L);
            var jq1 = JobQueue.open(ds(db), "test-queue", TestPayload.class, Duration.ofSeconds(30));
            jq1.enqueue(payload);

            // reserving jobs fails with incompatible deserialization class
            var jq2 = JobQueue.open(ds(db), "test-queue", TestPayloadIncompatible.class, Duration.ofSeconds(30));
            var ex = assertThrows(JobQueueException.class, () -> jq2.reserve(1));
            assertThat(ex.getCause(), instanceOf(MismatchedInputException.class));
            assertThat(ex.getMessage(), containsStringIgnoringCase("could not deserialize"));

            // the job should be available with a different but compatible deserialization class
            var jq3 = JobQueue.open(ds(db), "test-queue", TestPayloadConservativeExtension.class, Duration.ofSeconds(30));
            var res = jq3.reserve(1);
            assertThat(res, contains(where(JobQueue.ReservedJob::payload, equalTo(new TestPayloadConservativeExtension("foo", 1313L, false)))));
        } finally {
            Files.delete(db);
        }
    }

    @Test
    void testReserveWithLimit() throws Exception {
        var payloads = List.of(
                new TestPayload("a", 1),
                new TestPayload("b", 2),
                new TestPayload("c", 3)
        );

        var q = jobQueue();
        payloads.forEach(q::enqueue);

        var reservedJobs = q.reserve(2);
        assertThat(reservedJobs, hasSize(2));
        assertThat(reservedJobs.get(0).payload(), is(equalTo(payloads.get(0))));
        assertThat(reservedJobs.get(1).payload(), is(equalTo(payloads.get(1))));
    }

    @Test
    void testAckJobs() throws Exception {
        var payloads = List.of(
                new TestPayload("ack1", 1),
                new TestPayload("ack2", 2)
        );

        var q = jobQueue();
        var jobIds = payloads.stream().map(q::enqueue).toList();
        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, hasSize(2));
        assertThat(q.size(), is(2L));

        q.ack(jobIds);

        var remainingJobs = q.reserve(10);
        assertThat(remainingJobs, is(empty()));
        assertThat(q.size(), is(0L));
    }

    @Test
    void testNackJobs() throws Exception {
        var payloads = List.of(
                new TestPayload("nack1", 1),
                new TestPayload("nack2", 2)
        );

        var q = jobQueue();
        var jobIds = payloads.stream().map(q::enqueue).toList();
        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, hasSize(2));
        assertThat(reservedJobs, everyItem(where(JobQueue.ReservedJob::attempt, is(0))));

        q.nack(jobIds);

        var reReservedJobs = q.reserve(10);
        assertThat(reReservedJobs, hasSize(2));
        assertThat(reReservedJobs, everyItem(where(JobQueue.ReservedJob::attempt, is(1))));
    }

    @Test
    void testPartialAck() throws Exception {
        var payloads = List.of(
                new TestPayload("keep1", 1),
                new TestPayload("ack", 2),
                new TestPayload("keep2", 3)
        );

        var q = jobQueue();
        var jobIds = payloads.stream().map(q::enqueue).toList();
        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, hasSize(3));

        q.ack(List.of(jobIds.get(1))); // ack only the middle one
        q.nack(jobIds);

        var remainingJobs = q.reserve(10);
        assertThat(remainingJobs, hasSize(2));
        assertThat(
                remainingJobs, contains(
                        where(JobQueue.ReservedJob::payload, is(payloads.get(0))),
                        where(JobQueue.ReservedJob::payload, is(payloads.get(2)))
                ));
    }

    @Test
    void testEmptyQueue() throws Exception {
        var q = jobQueue();
        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, is(empty()));
    }

    @Test
    void testReserveZeroJobs() throws Exception {
        var q = jobQueue();
        q.enqueue(new TestPayload("data", 1));
        assertThrows(IllegalArgumentException.class, () -> q.reserve(0));
    }

    @Test
    void testMultipleQueues() throws Exception {
        var ds = ds();
        var q1 = JobQueue.open(ds, "queue1", TestPayload.class, Duration.ofSeconds(30));
        var q2 = JobQueue.open(ds, "queue2", TestPayload.class, Duration.ofSeconds(30));

        var payload1 = new TestPayload("queue1-data", 1);
        var payload2 = new TestPayload("queue2-data", 2);

        q1.enqueue(payload1);
        q2.enqueue(payload2);

        var queue1Jobs = q1.reserve(10);
        var queue2Jobs = q2.reserve(10);

        assertThat(queue1Jobs, hasSize(1));
        assertThat(queue2Jobs, hasSize(1));
        assertThat(queue1Jobs, contains(where(JobQueue.ReservedJob::payload, is(payload1))));
        assertThat(queue2Jobs, contains(where(JobQueue.ReservedJob::payload, is(payload2))));
    }

    @Test
    void testVisibilityTimeout() throws Exception {
        var q = JobQueue.open(ds(), "timeout-queue", TestPayload.class, Duration.ofMillis(100));
        var payload = new TestPayload("timeout-test", 1);
        q.enqueue(payload);

        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, hasSize(1));

        //Check that job is not available while reserved
        var moreReservedJobs = q.reserve(10);
        assertThat(moreReservedJobs, hasSize(0));

        Thread.sleep(150); // Wait for timeout

        var reReservedJobs = q.reserve(10);
        assertThat(reReservedJobs, hasSize(1));
        assertThat(reReservedJobs, contains(where(JobQueue.ReservedJob::attempt, is(1))));
    }

    @Test
    void testAckEmptyList() throws Exception {
        var q = jobQueue();
        assertDoesNotThrow(() -> q.ack(List.of()));
    }

    @Test
    void testNackEmptyList() throws Exception {
        var q = jobQueue();
        assertDoesNotThrow(() -> q.nack(List.of()));
    }

    @Test
    void testJobIdValues() throws Exception {
        var q = jobQueue();
        var jobId1 = q.enqueue(new TestPayload("first", 1));
        var jobId2 = q.enqueue(new TestPayload("second", 2));

        assertThat(jobId1.value(), is(greaterThan(0L)));
        assertThat(jobId2.value(), is(greaterThan(jobId1.value())));
    }

    @Test
    void testAttemptCounting() throws Exception {
        var q = JobQueue.open(ds(), "test-queue", TestPayload.class, Duration.ofMillis(100));
        var payload = new TestPayload("retry-test", 1);
        var jobId = q.enqueue(payload);

        // First reservation
        var reserved1 = q.reserve(10);
        assertThat(reserved1, contains(where(JobQueue.ReservedJob::attempt, is(0))));

        // Nack and reserve again
        q.nack(List.of(jobId));
        var reserved2 = q.reserve(10);
        assertThat(reserved2, contains(where(JobQueue.ReservedJob::attempt, is(1))));

        // Wait for timeout and reserve again
        Thread.sleep(150);
        var reserved3 = q.reserve(10);
        assertThat(reserved3, contains(where(JobQueue.ReservedJob::attempt, is(2))));
    }

    @Test
    void testFifoOrdering() throws Exception {
        var payloads = List.of(
                new TestPayload("first", 1),
                new TestPayload("second", 2),
                new TestPayload("third", 3)
        );

        var q = jobQueue();
        payloads.forEach(q::enqueue);

        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, hasSize(3));
        assertThat(
                reservedJobs, contains(
                        where(JobQueue.ReservedJob::payload, is(payloads.get(0))),
                        where(JobQueue.ReservedJob::payload, is(payloads.get(1))),
                        where(JobQueue.ReservedJob::payload, is(payloads.get(2)))
                ));
    }

    @Test
    void testInstantPayload() throws SQLException {
        var payload = new TestPayloadInstant(
                Instant.now(),
                OffsetDateTime.now(ZoneOffset.ofHours(-8)),
                LocalDate.of(1989, Month.NOVEMBER, 9));
        var q = JobQueue.open(ds(), "test-queue", TestPayloadInstant.class, Duration.ofSeconds(30));
        var jobId = q.enqueue(payload);
        assertThat(jobId, is(notNullValue()));
        var reservedJobs = q.reserve(10);
        assertThat(reservedJobs, contains(where(JobQueue.ReservedJob::payload, is(payload))));
    }
}
