package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LocalLmsIT {
    private static FtpConnection.ServerInfo serverInfo() {
        var user = Objects.requireNonNull(System.getenv("MEDICINPRISER_FTP_USERNAME"), "envvar MEDICINPRISER_FTP_USERNAME is not set");
        var password = Objects.requireNonNull(System.getenv("MEDICINPRISER_FTP_PASSWORD"), "envvar MEDICINPRISER_FTP_PASSWORD is not set");
        return new FtpConnection.ServerInfo("ftp.medicinpriser.dk", 21, user, password);
    }

    @Test
    void loadTablesWithRealData() throws IOException, SQLException {
        var dbFile = Files.createTempFile("local-lms-it", ".sqlite");
        var jdbcUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath();
        var ds = new SQLiteDataSource();
        ds.setUrl(jdbcUrl);
        var q = new DataProvider(ds);

        LocalLmsLoader.fetchData(serverInfo(), ds);

        assertThat(q.lastImport().isPresent(), is(true));
        // This test is valid as long as Panodil 500 mg is marketed by Haleon Denmark ApS
        assertThat(q.manufacturerOrganizationName(28100636073L), is("Haleon Denmark ApS"));
        assertThat(q.packageFormCode("005813"), is("BLI"));
        Files.delete(dbFile);
    }
}
