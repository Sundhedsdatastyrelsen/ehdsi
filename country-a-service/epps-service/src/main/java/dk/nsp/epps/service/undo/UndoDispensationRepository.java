package dk.nsp.epps.service.undo;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Repository
public class UndoDispensationRepository {
    private final JdbcTemplate jdbcTemplate;

    public UndoDispensationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Inserts a new undo dispensation record into the database.
     * The timestamp field must be null as it is automatically set by the database.
     *
     * @param dispensation The dispensation record to insert
     * @throws IllegalArgumentException if the timestamp field is not null
     */
    public void insert(UndoDispensationRow dispensation) {
        if (dispensation.timestamp() != null) {
            throw new IllegalArgumentException("timestamp value should be null when inserting UndoDispensationRow");
        }
        String sql = "INSERT INTO undo_dispensation (cda_id_hash, order_id, effectuation_id) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, dispensation.cdaIdHash(), dispensation.orderId(), dispensation.effectuationId());
        } catch (UncategorizedSQLException e) {
            if (e.getSQLException() instanceof SQLiteException sqlEx
                && SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY.equals(sqlEx.getResultCode())) {
                throw new DuplicateKeyException("Primary key constraint violation", sqlEx);
            }
            throw e;
        }
    }

    /**
     * Finds an undo dispensation record by its CDA ID.
     *
     * @param cdaId The CDA ID to search for
     * @return The found dispensation record, or null if no record exists
     */
    public UndoDispensationRow findByCdaId(String cdaId) {
        return findByCdaIdHash(UndoDispensationRow.cdaIdHash(cdaId));
    }

    /**
     * Finds an undo dispensation record by its CDA ID hash.
     *
     * @param cdaIdHash The hashed CDA ID to search for
     * @return The found dispensation record, or null if no record exists
     */
    private UndoDispensationRow findByCdaIdHash(String cdaIdHash) {
        String sql = "SELECT * FROM undo_dispensation WHERE cda_id_hash = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, cdaIdHash);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Deletes a row from the undo_dispensation table by the CDA ID.
     *
     * @param cdaId The CDA ID of the dispensation to delete
     * @throws EmptyResultDataAccessException if the deletion fails
     */
    public void deleteByCdaId(String cdaId) {
        deleteByCdaIdHash(UndoDispensationRow.cdaIdHash(cdaId));
    }

    /**
     * Deletes a row from the undo_dispensation table by the CDA ID hash.
     *
     * @param cdaIdHash The CDA ID hash of the dispensation to delete
     * @throws EmptyResultDataAccessException if the deletion fails
     */
    public void deleteByCdaIdHash(String cdaIdHash) {
        String sql = "DELETE FROM undo_dispensation WHERE cda_id_hash = ?";
        var deleted = jdbcTemplate.update(sql, cdaIdHash);
        if (deleted == 0) {
            throw new EmptyResultDataAccessException(
                String.format("No dispensation found with CDA ID hash '%s'", cdaIdHash),
                1);
        }
    }

    /**
     * Delete all rows with timestamp older than the given timestamp.
     *
     * @param timestamp The timestamp to delete rows older than
     * @return The number of rows deleted
     */
    public long deleteOlderThan(Instant timestamp) {
        String sql = "DELETE FROM undo_dispensation WHERE timestamp < ?";
        // sqlite stores timestamps as strings in the format 'yyyy-MM-dd HH:mm:ss'
        // see https://www.sqlite.org/lang_createtable.html, so for the comparison
        // to work, we need to format the timestamp in the same way
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = timestamp.atOffset(ZoneOffset.UTC).toLocalDateTime().format(formatter);
        return jdbcTemplate.update(sql, formattedTimestamp);
    }

    private final RowMapper<UndoDispensationRow> rowMapper = (rs, rowNumber) ->
        new UndoDispensationRow(
            rs.getString("cda_id_hash"),
            rs.getLong("effectuation_id"),
            rs.getLong("order_id"),
            // sqlite doesn't store tz info on timestamps, but we know they are UTC
            rs.getTimestamp("timestamp").toLocalDateTime().atZone(ZoneOffset.UTC).toInstant()
        );
}
