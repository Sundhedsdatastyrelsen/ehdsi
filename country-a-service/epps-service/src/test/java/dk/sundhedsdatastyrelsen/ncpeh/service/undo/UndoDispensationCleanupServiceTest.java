package dk.sundhedsdatastyrelsen.ncpeh.service.undo;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import java.time.Clock;
import java.time.Duration;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
class UndoDispensationCleanupServiceTest {
  UndoDispensationRepository undoDispensationRepository() {
 var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
 // perform db migrations
 Flyway.configure().dataSource(dataSource).load().migrate();
 return new UndoDispensationRepository(dataSource);
 }
  @Test
 void testCleanupOldDispensations() {
 var repository = undoDispensationRepository();
  // insert a dispensation record
 repository.insert(UndoDispensationRow.fromCdaId("cda-id", 1L, 1L));
  // verify that the undo information is not cleaned up immediately
 var clock = Clock.systemDefaultZone();
 var cleanupService = new UndoDispensationCleanupService(repository, clock);
 cleanupService.cleanupOldDispensations();
 var dispensation = repository.findByCdaId("cda-id");
 assertThat(dispensation, is(notNullValue()));
  // and also not after 23 hours
 var clockAfterTwentyThreeHours = Clock.offset(Clock.systemDefaultZone(), Duration.ofHours(23));
 cleanupService = new UndoDispensationCleanupService(repository, clockAfterTwentyThreeHours);
 cleanupService.cleanupOldDispensations();
 dispensation = repository.findByCdaId("cda-id");
 assertThat(dispensation, is(notNullValue()));
  // but after 24 hours it should be cleaned up
 var clockAfterOneDay = Clock.offset(Clock.systemDefaultZone(), Duration.ofHours(24).plusSeconds(1));
 cleanupService = new UndoDispensationCleanupService(repository, clockAfterOneDay);
 cleanupService.cleanupOldDispensations();
 dispensation = repository.findByCdaId("cda-id");
 assertThat(dispensation, is(nullValue()));
 }
 }
