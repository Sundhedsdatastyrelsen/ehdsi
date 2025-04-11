package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

public class DataProvider {
    private final DataSource dataSource;

    public DataProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Return the timestamp of the last import, if available.
     * Returns empty if the timestamp is not available or we cannot connect to the database.
     */
    public Optional<Instant> lastImport() {
        try {
            return Optional.ofNullable(queryRow(rs -> rs.getString(1), "SELECT last_import FROM import_metadata"))
                .map(Instant::parse);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }

    public String manufacturerOrganizationName(long drugId) {
        return queryRow(
            rs -> rs.getString(1),
            """
            SELECT LMS09.companyName
            FROM LMS09
            JOIN LMS01
            ON LMS01.marketingAuthorizationHolder = LMS09.companyId
            WHERE LMS01.drugId = ?
            """,
            Long.toString(drugId));
    }

    public String packageFormCode(String packageNumber) {
        return queryRow(
            rs -> rs.getString(1),
            """
            SELECT LMS02.packageFormCode
            FROM LMS02
            WHERE LMS02.packageNumber = ?
            """,
            packageNumber);
    }

    public PackageInfo packageInfo(String packageNumber) {
        var sql = """
            SELECT drugId, dispensationRegulationCode, packageFormCode
            FROM LMS02
            WHERE packageNumber = ?
            """;
        return queryRow(
            rs -> new PackageInfo(rs.getString(1), rs.getString(2), rs.getString(3)),
            sql,
            packageNumber);
    }

    /**
     * Perform a SQL query where the result set is expected to be 1 row.
     * @return the (first) result row, or null if there are none.
     */
    private <T> T queryRow(RowMapper<T> rowMapper, String sql, String... params) {
        try (var conn = dataSource.getConnection();
             var pstmt = conn.prepareStatement(sql)) {
            for (var i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            var resultSet = pstmt.executeQuery();
            return resultSet.next()
                ? rowMapper.get(resultSet)
                : null;
        } catch (SQLException e) {
            throw new IllegalStateException("SQLite query failed.", e);
        }
    }

    /**
     * Funtional interface for a transformation from a result set to a row.
     * The result set is assumed to be in the position of a row.  The implementation should not
     * call next() on the result set.
     * @param <T> the type of the row.
     */
    @FunctionalInterface
    interface RowMapper<T> {
        T get(ResultSet rs) throws SQLException;
    }
}
