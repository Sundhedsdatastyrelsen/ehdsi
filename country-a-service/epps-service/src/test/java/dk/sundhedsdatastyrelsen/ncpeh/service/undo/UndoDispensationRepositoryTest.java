package dk.sundhedsdatastyrelsen.ncpeh.service.undo;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UndoDispensationRepositoryTest {
    private UndoDispensationRepository repository;

    @BeforeEach
    void setUp() {
        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        // perform db migrations
        Flyway.configure().dataSource(dataSource).load().migrate();
        repository = new UndoDispensationRepository(dataSource);
    }

    @Test
    void testInsertAndFindByCdaIdHash() {
        // Given
        var dispensation = UndoDispensationRow.fromCdaId(
            "test-cda-id",
            123L,
            456L
        );

        // When
        repository.insert(dispensation);
        var found = repository.findByCdaId("test-cda-id");

        // Then
        assertThat(found, notNullValue());
        assertThat(found.cdaIdHash(), equalTo(dispensation.cdaIdHash()));
        assertThat(found.orderId(), equalTo(dispensation.orderId()));
        assertThat(found.effectuationId(), equalTo(dispensation.effectuationId()));
        assertThat(
            found.timestamp(), allOf(
                greaterThan(Instant.now().minusSeconds(3)),
                lessThan(Instant.now().plusSeconds(3))
            ));
    }

    @Test
    void testFindDoesNotExist() {
        var found = repository.findByCdaId("test-cda-id");
        assertThat(found, is(nullValue()));
    }

    @Test
    void testDeleteDoesNotExist() {
        assertThrows(
            EmptyResultDataAccessException.class,
            () -> repository.deleteByCdaId("cda-id-which-does-not-exist")
        );
    }

    @Test
    void testDeleteByCdaIdHash() {
        var dispensation = UndoDispensationRow.fromCdaId(
            "test-cda-id",
            123L,
            456L
        );
        repository.insert(dispensation);

        var before = repository.findByCdaId("test-cda-id");
        repository.deleteByCdaIdHash(dispensation.cdaIdHash());
        var after = repository.findByCdaId("test-cda-id");


        assertThat(before.cdaIdHash(), is(dispensation.cdaIdHash()));
        assertThat(after, is(nullValue()));
    }

    @Test
    void testInsertWithNonNullTimestamp() {
        // Given
        var dispensation = new UndoDispensationRow(
            "hash-value",
            123L,
            456L,
            Instant.now()  // Non-null timestamp
        );

        // Then
        assertThrows(
            IllegalArgumentException.class,
            () -> repository.insert(dispensation)
        );
    }

    @Test
    void testInsertWithExtremelyLongCdaId() {
        // Given
        String longCdaId = "a".repeat(1000);
        var dispensation = UndoDispensationRow.fromCdaId(
            longCdaId,
            123L,
            456L
        );

        // When
        repository.insert(dispensation);
        var found = repository.findByCdaId(longCdaId);

        // Then
        assertThat(found, notNullValue());
        assertThat(found.cdaIdHash(), equalTo(dispensation.cdaIdHash()));
    }

    @Test
    void testInsertWithSpecialCharactersInCdaId() {
        // Given
        String specialCdaId = "test-cda-id!@#$%^&*()_+";
        var dispensation = UndoDispensationRow.fromCdaId(
            specialCdaId,
            123L,
            456L
        );

        // When
        repository.insert(dispensation);
        var found = repository.findByCdaId(specialCdaId);

        // Then
        assertThat(found, notNullValue());
        assertThat(found.cdaIdHash(), equalTo(dispensation.cdaIdHash()));
    }

    @Test
    void testInsertDuplicateCdaId() {
        // Given
        var dispensation1 = UndoDispensationRow.fromCdaId(
            "test-cda-id",
            123L,
            456L
        );
        var dispensation2 = UndoDispensationRow.fromCdaId(
            "test-cda-id",
            789L,
            101112L
        );

        repository.insert(dispensation1);
        assertThrows(
            DuplicateKeyException.class,
            () -> repository.insert(dispensation2)
        );
    }

    @Test
    void testInsertWithMaxLongValues() {
        // Given
        var dispensation = UndoDispensationRow.fromCdaId(
            "test-cda-id",
            Long.MAX_VALUE,
            Long.MAX_VALUE
        );

        // When
        repository.insert(dispensation);
        var found = repository.findByCdaId("test-cda-id");

        // Then
        assertThat(found, notNullValue());
        assertThat(found.orderId(), equalTo(Long.MAX_VALUE));
        assertThat(found.effectuationId(), equalTo(Long.MAX_VALUE));
    }

    @Test
    void testDeleteOlderThan() {
        // Given
        var dispensation1 = UndoDispensationRow.fromCdaId("test-cda-id-1", 123L, 456L);
        var dispensation2 = UndoDispensationRow.fromCdaId("test-cda-id-2", 789L, 101112L);
        repository.insert(dispensation1);
        repository.insert(dispensation2);

        // Let's ensure the records are actually inserted
        var found1 = repository.findByCdaId("test-cda-id-1");
        var found2 = repository.findByCdaId("test-cda-id-2");
        assertThat(found1, notNullValue());
        assertThat(found2, notNullValue());

        // When - delete records older than 1 second from now
        var futureTimestamp = Instant.now().plusSeconds(1);
        var deletedCount = repository.deleteOlderThan(futureTimestamp);

        // Then
        assertThat(deletedCount, is(2L));
        assertThat(repository.findByCdaId("test-cda-id-1"), nullValue());
        assertThat(repository.findByCdaId("test-cda-id-2"), nullValue());
    }

    @Test
    void testDeleteOlderThanNoMatches() {
        // Given
        var dispensation = UndoDispensationRow.fromCdaId("test-cda-id", 123L, 456L);
        repository.insert(dispensation);

        // When - delete records older than 10 seconds ago (should find none)
        var pastTimestamp = Instant.now().minusSeconds(10);
        var deletedCount = repository.deleteOlderThan(pastTimestamp);

        // Then
        assertThat(deletedCount, is(0L));
        assertThat(repository.findByCdaId("test-cda-id"), notNullValue());
    }

    @Test
    void testDeleteOlderThanEmptyTable() {
        // When
        var deletedCount = repository.deleteOlderThan(Instant.now());

        // Then
        assertThat(deletedCount, is(0L));
    }
}
