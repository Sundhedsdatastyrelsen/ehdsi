package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Cpr;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CprServiceIT {
    @Test
    void getPersonInformationTest() throws Exception {
        var response = Cpr.apiClient()
            .getPersonInformation("0611809735", TestIdentities.systemIdentity);
        Assertions.assertEquals(
            "Charles Test Babbage",
            response.getPersonInformationStructure()
                .getRegularCPRPerson()
                .getPersonNameForAddressingName());
    }

    @Test
    @Disabled("Doesn't work with organization identity")
    void getPersonInformationAlternativeCaller() throws Exception {
        var response = Cpr.apiClient().getPersonInformation("0611809735", TestIdentities.apotekerJeppeMoeller);
        Assertions.assertEquals(
            "Charles Test Babbage",
            response.getPersonInformationStructure()
                .getRegularCPRPerson()
                .getPersonNameForAddressingName());
    }
}
