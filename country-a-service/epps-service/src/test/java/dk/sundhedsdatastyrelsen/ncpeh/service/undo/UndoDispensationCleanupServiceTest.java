package dk.sundhedsdatastyrelsen.ncpeh.service.undo; import org.flywaydb.core.Flyway; import org.junit.jupiter.api.Test; import org.springframework.jdbc.datasource.SingleConnectionDataSource; import java.time.Clock; import java.time.Duration; import static org.hamcrest.MatcherAssert.assertThat; import static org.hamcrest.Matchers.is; import static org.hamcrest.Matchers.notNullValue; import static org.hamcrest.Matchers.nullValue; class UndoDispensationCleanupServiceTest { UndoDispensationRepository undoDispensationRepository() { var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true); Flyway.configure().dataSource(dataSource).load().migrate(); return new UndoDispensationRepository(dataSource); } @Test void testCleanupOldDispensations() { var repository = undoDispensationRepository(); repository.insert(UndoDispensationRow.fromCdaId("cda-id", 1L, 1L)); var clock = Clock.systemDefaultZone(); var cleanupService = new UndoDispensationCleanupService(repository, clock); cleanupService.cleanupOldDispensations(); var dispensation = repository.findByCdaId("cda-id"); assertThat(dispensation, is(notNullValue())); var clockAfterTwentyThreeHours = Clock.offset(Clock.systemDefaultZone(), Duration.ofHours(23)); cleanupService = new UndoDispensationCleanupService(repository, clockAfterTwentyThreeHours); cleanupService.cleanupOldDispensations(); dispensation = repository.findByCdaId("cda-id"); assertThat(dispensation, is(notNullValue())); var clockAfterOneDay = Clock.offset(Clock.systemDefaultZone(), Duration.ofHours(24).plusSeconds(1)); cleanupService = new UndoDispensationCleanupService(repository, clockAfterOneDay); cleanupService.cleanupOldDispensations(); dispensation = repository.findByCdaId("cda-id"); assertThat(dispensation, is(nullValue())); } } 