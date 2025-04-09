package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;

public class LocalLmsLoader {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalLmsLoader.class);

    /**
     * Download data from LMS (medicinpriser/taksten) and load it into a local SQLite database.
     */
    public static void fetchData(String jdbcUrl, FtpConnection.ServerInfo serverInfo) throws IOException, SQLException {
        try (var ftpConn = new FtpConnection(serverInfo)) {
            parseAndLoadRawData(ftpConn.rawDataProvider(), jdbcUrl);
        }
    }

    static void parseAndLoadRawData(RawDataProvider rdp, String jdbcUrl) throws IOException, SQLException {
        var tables = Specs.get();
        try (var conn = DriverManager.getConnection(jdbcUrl)) {
            conn.setAutoCommit(false);

            for (var table : tables) {
                // update DDL
                try (var stmt = conn.createStatement()) {
                    for (var ddl: table.ddl()) {
                        stmt.executeUpdate(ddl);
                    }
                }

                try (var rdr = reader(rdp.get(table));
                     var pstmt = conn.prepareStatement(table.insertSql())) {
                    String row;
                    while ((row = rdr.readLine()) != null) {
                        var parsed = table.parseRow(row);
                        for (var i = 0; i < parsed.size(); i++) {
                            pstmt.setString(i + 1, parsed.get(i));
                        }
                        pstmt.executeUpdate();
                    }
                }
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
        }
    }

    private static BufferedReader reader(InputStream is) {
        // LMS files are encoded with charset cp850.
        return new BufferedReader(new InputStreamReader(is, Charset.forName("cp850")));
    }

    @FunctionalInterface
    public interface RawDataProvider {
        InputStream get(Specs.Table table) throws IOException;
    }
}
