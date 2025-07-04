package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.MinLogService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.MinLog;
import org.junit.jupiter.api.Test;

class MinLogIT {
    private final MinLogService service = new MinLogService(MinLog.apiClient(), OrganizationIdentities.sundhedsdatastyrelsen());

    @Test
    void testEvent() {
        service.logEventOnPatient(MinLog.cprJensJensenReadOnly, "integrationstest", TestIdentities.foreignPharmacistChrisChristoffersen);
    }
}
