package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.MinLogService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.MinLog;
import org.junit.jupiter.api.Test;

public class MinLogIT {
    private final MinLogService service = new MinLogService(MinLog.apiClient());

    @Test
    void testEvent() {
        service.logEventOnPatient(MinLog.cprJensJensenReadOnly, "integrationstest", OrganizationIdentities.sundhedsdatastyrelsen());
    }
}
