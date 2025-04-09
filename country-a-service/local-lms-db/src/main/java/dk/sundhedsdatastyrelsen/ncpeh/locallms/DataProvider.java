package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import javax.sql.DataSource;
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
            return Optional.ofNullable(queryOneString("SELECT last_import FROM import_metadata"))
                .map(Instant::parse);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }

    public String manufacturerOrganizationName(long drugId) {
        return queryOneString("""
            SELECT LMS09.companyName
            FROM LMS09
            JOIN LMS01
            ON LMS01.marketingAuthorizationHolder = LMS09.companyId
            WHERE LMS01.drugId = ?
            """,
            Long.toString(drugId));
    }

    /**
     * Perform a SQL query where the result set is expected to be 1 row with 1 string.
     * @return the (first) string result, or null if there are none.
     */
    private String queryOneString(String sql, String... params) {
        try (var conn = dataSource.getConnection();
             var pstmt = conn.prepareStatement(sql)) {
            for (var i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            var resultSet = pstmt.executeQuery();
            return resultSet.next()
                ? resultSet.getString(1)
                : null;
        } catch (SQLException e) {
            throw new IllegalStateException("SQLite query failed.", e);
        }
    }
}
