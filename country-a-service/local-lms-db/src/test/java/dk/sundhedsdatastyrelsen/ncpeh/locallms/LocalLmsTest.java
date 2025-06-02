package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalLmsTest {
    @Test
    void loadAndQueryTestData() throws SQLException, IOException {
        var ds = dataSource();
        LocalLmsLoader.parseAndLoadRawData(testDataProvider(), ds);
        var q = new DataProvider(ds);

        // Querying existing values
        assertThat(q.lastImport().isPresent(), is(true));
        assertThat(q.manufacturerOrganizationName(28100636073L), is("Haleon Denmark ApS"));
        assertThat(q.manufacturerOrganizationName(28100713276L), is("Dansk Lægemiddelforsyning DLF"));
        assertThat(q.packageFormCode("005813"), is("BLI"));
        // FMK removes leading zeros, so it should work without them:
        assertThat(q.packageFormCode("5813"), is("BLI"));
        assertThat(
            q.packageInfo("005813"), is(new PackageInfo(
                "28100636073",
                "HX18",
                "BLI",
                1
            )));
        // Test what happens with empty subpackages
        assertThat(
            q.packageInfo("000005"), is(new PackageInfo(
                "28103023098",
                "B",
                "BLI",
                null
            )));

        // Querying unknown values should give null
        assertThat(q.manufacturerOrganizationName(0L), is(nullValue()));
        assertThat(q.packageFormCode("00000"), is(nullValue()));

        // Querying malformed values should throw
        assertThrows(
            IllegalArgumentException.class, () ->
                q.packageInfo("abc"));
    }

    @Test
    void crashingImportDoesNotDestroyOldData() throws SQLException, IOException {
        var ds = dataSource();
        LocalLmsLoader.parseAndLoadRawData(testDataProvider(), ds);
        var q = new DataProvider(ds);
        var lastImport = q.lastImport();

        // this should fail
        assertThrows(IOException.class, () -> LocalLmsLoader.parseAndLoadRawData(crashingDataProvider(), ds));

        // the data should still be intact
        assertThat(q.lastImport(), is(lastImport));
        // query that uses LMS09:
        assertThat(q.manufacturerOrganizationName(28100636073L), is("Haleon Denmark ApS"));
    }

    @Test
    void corruptedDataShouldNotDestroyOldData() throws SQLException, IOException {
        var ds = dataSource();
        LocalLmsLoader.parseAndLoadRawData(testDataProvider(), ds);
        var q = new DataProvider(ds);
        var lastImport = q.lastImport();

        // this should fail
        assertThrows(IOException.class, () -> LocalLmsLoader.parseAndLoadRawData(emptyLms09DataProvider(), ds));

        // the data should still be intact
        assertThat(q.lastImport(), is(lastImport));
        // query that uses LMS09:
        assertThat(q.manufacturerOrganizationName(28100636073L), is("Haleon Denmark ApS"));
    }

    private RawDataProvider testDataProvider() {
        return table -> Thread.currentThread().getContextClassLoader()
            .getResourceAsStream("lms-test-data/%s.txt".formatted(table.name()));
    }

    private RawDataProvider crashingDataProvider() {
        return table -> {
            // we crash when we reach LMS09
            if ("LMS09".equals(table.name())) {
                throw new IOException("I'm crashing!");
            }
            return Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("lms-test-data/%s.txt".formatted(table.name()));
        };
    }

    private RawDataProvider emptyLms09DataProvider() {
        return table -> {
            // we return the empty stream when we reach LMS09
            if ("LMS09".equals(table.name())) {
                return new ByteArrayInputStream(new byte[] {});
            }
            return Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("lms-test-data/%s.txt".formatted(table.name()));
        };
    }

    private DataSource dataSource() {
        return new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
    }
}
