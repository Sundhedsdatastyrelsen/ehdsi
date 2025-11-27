package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.FtpConnection;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.LocalLmsLoader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class LocalLmsIT {
    private static FtpConnection.ServerInfo serverInfo() {
        var user = Objects.requireNonNull(System.getenv("LMSFTP_USERNAME"), "envvar LMSFTP_USERNAME is not set");
        var password = Objects.requireNonNull(System.getenv("LMSFTP_PASSWORD"), "envvar LMSFTP_PASSWORD is not set");
        return new FtpConnection.ServerInfo("ftp.medicinpriser.dk", 21, user, password);
    }

    static DataProvider dataProvider;
    static Path dbFile;

    @BeforeAll
    static void beforeAll() throws IOException, SQLException {
        dbFile = Files.createTempFile("local-lms-it", ".sqlite");
        var jdbcUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath();
        var ds = new SQLiteDataSource();
        ds.setUrl(jdbcUrl);
        dataProvider = new DataProvider(ds);

        LocalLmsLoader.fetchData(serverInfo(), ds);
    }

    @AfterAll
    static void afterAll() throws IOException {
        Files.delete(dbFile);
    }

    @Test
    void loadTablesWithRealData() {
        assertThat(dataProvider.lastImport().isPresent(), is(true));
        // This test is valid as long as Panodil 500 mg is marketed by Haleon Denmark ApS
        assertThat(dataProvider.manufacturerOrganizationName(28100636073L), is("Haleon Denmark ApS"));
        assertThat(dataProvider.packageFormCode("005813"), is("BLI"));
    }

    @Test
    void testNecessaryDivisionWorks() {
        // We need to perform division of the data in the LMS db. In order to ensure that keeps working we check nightly
        // whether there are any problems with it.
        var testData = dataProvider.allSubpackagesAndPackageSizes();
        assertThat(testData.size(), greaterThan(10));
        for (var packageSize : testData) {
            EPrescriptionL3Mapper.calculateInnerPackageAmount(packageSize.numericalPackageSize(), packageSize.numberOfSubPackages());
            // If it doesn't throw, we're good.
        }
        assertThat("We got this far without errors", true);
    }
}
