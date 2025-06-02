package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import org.slf4j.Logger;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

public class LocalLmsLoader {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalLmsLoader.class);

    private LocalLmsLoader() {
    }

    /**
     * Download data from LMS (medicinpriser/taksten) and load it into a local SQLite database.
     */
    public static void fetchData(FtpConnection.ServerInfo serverInfo, DataSource dataSource) throws IOException, SQLException {
        try (var ftpConn = new FtpConnection(serverInfo)) {
            parseAndLoadRawData(ftpConn.rawDataProvider(), dataSource);
        }
        log.info("Local LMS database synchronized");
    }

    static void parseAndLoadRawData(RawDataProvider rdp, DataSource dataSource) throws IOException, SQLException {
        var tables = Specs.get();
        try (var conn = dataSource.getConnection()) {
            // We could have decided to reset autoCommit to the original value after we release the connection,
            // but we don't expect anyone to need to perform updates anywhere else, much less with autoCommit=true,
            // so why bother.
            conn.setAutoCommit(false);
            try {
                for (var table : tables) {
                    // update DDL
                    try (var stmt = conn.createStatement()) {
                        for (var ddl : table.ddl()) {
                            stmt.executeUpdate(ddl);
                        }
                    }
                    // parse and insert data from data provider
                    try (var rdr = reader(rdp.get(table));
                         var pstmt = conn.prepareStatement(table.insertSql())) {
                        String row;
                        while ((row = rdr.readLine()) != null) {
                            var parsed = table.parseRow(row);
                            for (var i = 0; i < parsed.size(); i++) {
                                pstmt.setObject(
                                    i + 1,
                                    switch (table.fields().get(i).type()) {
                                        case "TEXT" -> parsed.get(i);
                                        case "INTEGER" -> intOrNull(parsed.get(i));
                                        default -> throw new IllegalArgumentException(
                                            "Field type not supported: " + table.fields().get(i).type());
                                    });
                            }
                            pstmt.executeUpdate();
                        }
                    }
                    validate(conn, table);
                }
                try (var stmt = conn.createStatement()) {
                    stmt.executeUpdate("DROP TABLE IF EXISTS import_metadata");
                    stmt.executeUpdate("CREATE TABLE import_metadata (last_import TEXT)");
                }
                try (var pstmt = conn.prepareStatement("INSERT INTO import_metadata (last_import) VALUES (?)")) {
                    pstmt.setString(1, Instant.now().toString());
                    pstmt.executeUpdate();
                }
                conn.commit();
            } catch (Exception e) {
                log.warn("Something went wrong with LMS import, rolling back to previous version.");
                conn.rollback();
                throw e;
            }
        }
    }

    private static void validate(Connection conn, Specs.Table table) throws SQLException, IOException {
        // We use the following heuristics to validate that an import was successful:
        // - the table should be non-empty.
        try (var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery("SELECT COUNT(1) FROM %s".formatted(table.name()));
            rs.next();
            var count = rs.getInt(1);
            if (count == 0) {
                throw new IOException("Bad data from LMS");
            }
        }
    }

    private static BufferedReader reader(InputStream is) {
        // LMS files are encoded with charset cp850.
        return new BufferedReader(new InputStreamReader(is, Charset.forName("cp850")));
    }

    private static Integer intOrNull(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
