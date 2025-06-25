package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.MinLogService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fsk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.MinLog;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MinLogIT {
    private final MinLogService service = new MinLogService(MinLog.apiClient());

    @Test
    void testEvent() throws Exception {
        service.logEventOnPatient(MinLog.cprJensJensenReadOnly,"integrationstest", OrganizationIdentities.sundhedsdatastyrelsen());
    }
}
