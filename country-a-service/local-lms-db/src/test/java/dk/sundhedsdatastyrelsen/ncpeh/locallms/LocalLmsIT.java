package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LocalLmsIT {
    private static FtpConnection.ServerInfo getServerInfo() {
        var user = Objects.requireNonNull(System.getenv("MEDICINPRISER_FTP_USERNAME"), "envvar MEDICINPRISER_FTP_USERNAME is not set");
        var password = Objects.requireNonNull(System.getenv("MEDICINPRISER_FTP_PASSWORD"), "envvar MEDICINPRISER_FTP_PASSWORD is not set");
        return new FtpConnection.ServerInfo("ftp.medicinpriser.dk", 21, user, password);
    }

    @Test
    void loadTablesWithRealData() throws IOException {
        var dbFile = Files.createTempFile("local-lms-it", ".sqlite");
        var jdbcUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath();
        var q = new DataProvider(jdbcUrl);

        var serverInfo = getServerInfo();
        Loader.loadTables(jdbcUrl, serverInfo);

        assertThat(q.lastImport().isPresent(), is(true));
        // This test is valid as long as Panodil 500 mg is marketed by Haleon Denmark ApS
        assertThat(q.getManufacturerOrganizationNameFromDrugId(28100636073L), is("Haleon Denmark ApS"));
        Files.delete(dbFile);
    }
}
