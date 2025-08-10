package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.nsp.test.idp.model.OrganizationIdentity;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.DestinationForEntryForRegistrationType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.LogDataEntryForRegistrationType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.PersonIdSourceType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.RegistrationRequestType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.SourceForEntryType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.UserPersonIdSourceType;
import dk.sundhedsdatastyrelsen.ncpeh.client.EuropeanHealthcareProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.client.MinLogClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientException;
import dk.sundhedsdatastyrelsen.ncpeh.jobqueue.JobQueue;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.ObservableLongUpDownCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinLogService implements AutoCloseable {
    private static final String MINLOG_QUEUE_NAME = "minlog";
    private static final String MINLOG_FAILED_QUEUE_NAME = "minlog_errors";

    private final MinLogClient minLogClient;
    private final JobQueue<LogEvent> jobQueue;
    private final JobQueue<LogEvent> failedJobQueue;

    private final ScheduledExecutorService scheduler;

    private final ObservableLongUpDownCounter queueSizeCounter;
    private final ObservableLongUpDownCounter failedQueueSizeCounter;

    /**
     * The system (FOCES/VOCES) which makes the service request, i.e., certificates representing the NCPeH DK.
     * This is not the same as the parent request caller, i.e., the foreign healthcare professional.
     */
    private final OrganizationIdentity systemCaller;

    /**
     * Maximum number of attempts for processing a job before moving it to the failed queue
     */
    private final int maxAttempts;

    public MinLogService(
        MinLogClient minLogClient,
        OrganizationIdentity systemCaller,
        @Qualifier("jobQueueDataSource") DataSource jobQueueDatasource,
        @Value("${app.minlog.max-attempts:3}") int maxAttempts
    ) throws SQLException {
        this.minLogClient = minLogClient;
        this.systemCaller = systemCaller;
        this.maxAttempts = maxAttempts;
        this.jobQueue = JobQueue.open(jobQueueDatasource, MINLOG_QUEUE_NAME, LogEvent.class, Duration.ofMinutes(3));
        this.failedJobQueue = JobQueue.open(jobQueueDatasource, MINLOG_FAILED_QUEUE_NAME, LogEvent.class, Duration.ofMinutes(3));

        log.info("Starting MinLog service with job queue datasource: {} and max attempts: {}", jobQueueDatasource, maxAttempts);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        // https://www.nspop.dk/spaces/web/pages/98456923/MinLog2+-+Min+Log+Registrering+-+Guide+til+anvendere#MinLog2MinLogRegistreringGuidetilanvendere-Operationer
        // "Det anbefalede antal logentries er 500 entries pr. request, og den anbefalede kaldefrekvens er op til 20 requests i timen."
        //
        // We will send up to 500 requests per 3 minutes.  This should keep us within the recommendations.
        scheduler.scheduleAtFixedRate(this::sendBatch, 0, 3, TimeUnit.MINUTES);
        // We use otel metrics (prometheus) to keep track of the queue size.  There should be alerts if this number is too high.
        var meter = GlobalOpenTelemetry.meterBuilder("dk.sundhedsdatastyrelsen.ncpeh.service").build();
        this.queueSizeCounter = meter.upDownCounterBuilder("minlog.job_queue.size")
            .buildWithCallback(m -> m.record(jobQueue.size()));

        // We use otel metrics (prometheus) to keep track of the queue size.  There should be alerts if this number is too high.
        var failedMeter = GlobalOpenTelemetry.meterBuilder("dk.sundhedsdatastyrelsen.ncpeh.service").build();
        this.failedQueueSizeCounter = failedMeter.upDownCounterBuilder("minlog.job_queue_failed.size")
            .buildWithCallback(m -> m.record(failedJobQueue.size()));
    }

    /// Send the next batch of log entries to MinLog.
    ///
    /// Public for testing purposes.
    ///
    /// @hidden
    public void sendBatch() {
        List<JobQueue.ReservedJob<LogEvent>> jobs = null;
        try {
            jobs = jobQueue.reserve(500);
            List<JobQueue.JobId> jobIds = jobs.stream().map(JobQueue.ReservedJob::id).toList();
            if (!jobs.isEmpty()) {
                log.info("Sending {} log entries to MinLog", jobs.size());
                logEventsSync(jobs.stream().map(JobQueue.ReservedJob::payload).toList());
                jobQueue.ack(jobIds);
            } else {
                log.debug("Found no pending MinLog entries.");
            }
        } catch (Exception e) {
            // we should not rethrow, otherwise our scheduler will stop on the first error.
            log.error("Something went wrong while sending log entries to MinLog. Will retry.", e);
            if (jobs != null) {
                // Check if any jobs have exceeded max attempts and move them to failed queue
                List<JobQueue.JobId> jobsToNack = new java.util.ArrayList<>();
                List<JobQueue.JobId> jobsToMoveToFailed = new java.util.ArrayList<>();
                
                for (JobQueue.ReservedJob<LogEvent> job : jobs) {
                    if (job.attempt() >= maxAttempts) {
                        // Move to failed queue
                        failedJobQueue.enqueue(job.payload());
                        jobsToMoveToFailed.add(job.id());
                        log.warn("Job {} exceeded max attempts ({}), moving to failed queue", job.id(), maxAttempts);
                    } else {
                        // Nack for retry
                        jobsToNack.add(job.id());
                    }
                }
                
                // Nack jobs that should be retried
                if (!jobsToNack.isEmpty()) {
                    jobQueue.nack(jobsToNack);
                }
                
                // Remove jobs that were moved to failed queue
                if (!jobsToMoveToFailed.isEmpty()) {
                    jobQueue.ack(jobsToMoveToFailed);
                }
            }
        }
    }

    /// A representation of the MinLog registration.
    ///
    /// **Important:** this value will be serialized and deserialized in a persistent queue.  If the type is updated,
    /// then you must make sure that the change is either
    /// - a conservative extension, and the system will continue working with the old values, i.e.,
    ///   you add new nullable fields, or
    /// - you make sure the queue is empty before applying the update.
    public record LogEvent(
        String citizenCpr,
        String eventText,
        String hcpId,
        Instant timestamp
    ) {}

    private void logEventsSync(List<LogEvent> events) {
        // See https://www.nspop.dk/pages/releaseview.action?pageId=98456923#MinLog2MinLogRegistreringGuidetilanvendere-Skemabeskrivelse
        // for schema description
        var requestBuilder = RegistrationRequestType.builder();
        for (var ev : events) {
            var source = SourceForEntryType.builder()
                .withSystemName("DK-NCPeH")
                .withSource()
                .withSystemName("DK-NCPeH")
                .build();
            var destination = DestinationForEntryForRegistrationType.builder()
                .withSystemName("NCPeH") // TODO Replace with actual country we sent it to?
                .withActivity(ev.eventText())
                .withDateTime(Utils.xmlGregorianCalendar(ev.timestamp()))
                .withPersonIdentifier()
                .withSource(PersonIdSourceType.CPR)
                .withValue(ev.citizenCpr())
                .end()
                .withUserPersonName("TODO") // TODO include name which must be max 50 chars long (bytes? unicode code points?)
                .withUserPersonIdentifier()
                .withSource(UserPersonIdSourceType.EUROPEAN_HEALTHCARE_PROFESSIONAL)
                .withValue(ev.hcpId()) // TODO react to id's exceeding the 200 char limit
                .end()
                .build();
            requestBuilder.addLogDataEntry(LogDataEntryForRegistrationType.builder()
                .withSource(source)
                .withDestination(destination)
                .build());
        }
        var response = minLogClient.register(requestBuilder.build(), systemCaller);
        if (response.getNumberFailed() > 0) {
            for (var failedEntry : response.getFailedLogDataEntries()) {
                log.error("MinLog error: {}: {}", failedEntry.getFaultCode(), failedEntry.getFaultText());
            }
            // TODO[hbg]: We need better error handling.  If some registrations fail, but not all, then we should re-queue the failing ones only.
            throw new NspClientException(
                "MinLog registration failed. Failed entries: %d, Succeeded entries: %d".formatted(
                    response.getNumberFailed(),
                    response.getNumberAdded()));
        }
    }

    /// Register MinLog event.  The event will be added to a queue and handled in a separate thread.
    public void logEventOnPatient(
        String cpr,
        String eventText,
        EuropeanHealthcareProfessional europeanHealthcareProfessional
    ) {
        var logEvent = new LogEvent(
            cpr,
            eventText,
            europeanHealthcareProfessional.europeanHealthcareProfessionalId(),
            Instant.now());

        jobQueue.enqueue(logEvent);
        log.debug("Enqueued MinLog event: {}", eventText);
    }

    /// Stops the scheduler which stop the MinLog registration.
    @Override
    public void close() {
        scheduler.close();
        queueSizeCounter.close();
        failedQueueSizeCounter.close();
    }
}
