package dk.sundhedsdatastyrelsen.ncpeh.service.undo; import org.slf4j.Logger; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Service; import java.time.Clock; import java.time.Duration; import java.time.Instant; @Service public class UndoDispensationCleanupService { private static final Logger log = org.slf4j.LoggerFactory.getLogger(UndoDispensationCleanupService.class); private final UndoDispensationRepository repository; private final Clock clock; public UndoDispensationCleanupService(UndoDispensationRepository repository, Clock clock) { this.repository = repository; this.clock = clock; } @Scheduled(cron = "0 0 2 * * *") public void cleanupOldDispensations() { log.info("Running scheduled undo dispensation info cleanup..."); var cutoffTime = Instant.now(clock).minus(Duration.ofHours(24)); var deletedRows = repository.deleteOlderThan(cutoffTime); log.info("Cleaned up {} row(s)", deletedRows); } } 