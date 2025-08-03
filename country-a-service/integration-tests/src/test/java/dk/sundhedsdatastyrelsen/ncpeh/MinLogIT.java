package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.jobqueue.JobQueue;
import dk.sundhedsdatastyrelsen.ncpeh.service.MinLogService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.MinLog;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MinLogIT {
    public static MinLogService minLogService() throws SQLException {
        return minLogService(ds());
    }

    public static MinLogService minLogService(DataSource ds) throws SQLException {
        return new MinLogService(MinLog.apiClient(), OrganizationIdentities.sundhedsdatastyrelsen(), ds);
    }

    static SingleConnectionDataSource ds() {
        return new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
    }

    @Test
    void testEvent() throws Exception {
        try (var ds = ds();
             var service = minLogService(ds);
             var q = JobQueue.open(ds, "minlog", null, null)) {
            service.logEventOnPatient(MinLog.CPR_JENS_JENSEN_READ_ONLY, "integrationstest", TestIdentities.foreignPharmacistChrisChristoffersen);
            assertThat(q.size(), is(1L));
            assertDoesNotThrow(service::sendBatch);
            assertThat(q.size(), is(0L));
        }
    }
}
