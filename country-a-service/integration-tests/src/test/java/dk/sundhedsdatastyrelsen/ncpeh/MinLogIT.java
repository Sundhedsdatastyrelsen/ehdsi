package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.MinLogService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.MinLog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MinLogIT {
    private final MinLogService service = new MinLogService(MinLog.apiClient(), OrganizationIdentities.sundhedsdatastyrelsen());

    @Test
    void testEvent() {
        assertDoesNotThrow(() ->
            service.logEventOnPatient(MinLog.CPR_JENS_JENSEN_READ_ONLY, "integrationstest", TestIdentities.foreignPharmacistChrisChristoffersen)
        );
    }
}
