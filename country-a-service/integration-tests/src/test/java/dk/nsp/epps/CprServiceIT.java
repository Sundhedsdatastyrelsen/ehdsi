package dk.nsp.epps;

import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.testing.shared.Cpr;
import dk.nsp.test.idp.OrganizationIdentities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CprServiceIT {
    @Test
    void getPersonInformationTest() throws Exception {
        var response = Cpr.apiClient()
            .getPersonInformation("0611809735", OrganizationIdentities.sundhedsdatastyrelsen());
        Assertions.assertEquals(
            "Charles Test Babbage",
            response.getPersonInformationStructure()
                .getRegularCPRPerson()
                .getPersonNameForAddressingName());
    }

    @Test
    void getPersonInformationAlternativeCaller() throws Exception {
        var response = Cpr.apiClient().getPersonInformation("0611809735", TestIdentities.apotekerJeppeMoeller);
        Assertions.assertEquals(
            "Charles Test Babbage",
            response.getPersonInformationStructure()
                .getRegularCPRPerson()
                .getPersonNameForAddressingName());
    }
}
