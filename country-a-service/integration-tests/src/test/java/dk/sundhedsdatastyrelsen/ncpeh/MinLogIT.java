package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.MinLogService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.MinLog;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MinLogIT {
    public static MinLogService minLogService() throws SQLException {
        return new MinLogService(MinLog.apiClient(), OrganizationIdentities.sundhedsdatastyrelsen(), ds());
    }

    static SQLiteDataSource ds() {
        var ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite::memory:");
        return ds;
    }

    @Test
    void testEvent() throws Exception {
        try (var service = minLogService()) {
            service.logEventOnPatient(MinLog.CPR_JENS_JENSEN_READ_ONLY, "integrationstest", TestIdentities.foreignPharmacistChrisChristoffersen);
            assertDoesNotThrow(service::sendBatch);
        }
    }
}
