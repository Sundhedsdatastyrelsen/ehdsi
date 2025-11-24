package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PatientDemographicsDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.CprService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Cpr;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dk.sundhedsdatastyrelsen.ncpeh.testing.shared.WhereMatcher.where;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CprServiceIT {
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
    void getPersonInformationAlternativeCaller() throws Exception {
        var response = Cpr.apiClient().getPersonInformation("0611809735", TestIdentities.apotekerJeppeMoeller);
        Assertions.assertEquals(
            "Charles Test Babbage",
            response.getPersonInformationStructure()
                .getRegularCPRPerson()
                .getPersonNameForAddressingName());
    }

    @Test
    void getOneGoodViaServiceTest() {
        var service = new CprService(Cpr.apiClient(), TestIdentities.systemIdentity);
        // 0611809735: valid and exists
        var result = service.findPatients(List.of("0611809735"));
        assertThat(result.getPatients(), contains(where(PatientDemographicsDto::getFamilyName, is("Babbage"))));
    }

    @Test
    void getBadViaServiceTest() {
        var service = new CprService(Cpr.apiClient(), TestIdentities.systemIdentity);
        // 0909729999: valid, but doesn't exist (per 2025-11-24)
        // 9999999999: invalid
        var result = service.findPatients(List.of("0909729999", "9999999999"));
        assertThat(result.getPatients(), empty());
    }

    @Test
    void getOneGoodAndOneBadViaServiceTest() {
        var service = new CprService(Cpr.apiClient(), TestIdentities.systemIdentity);
        // 0611809735: valid and exists
        // 0909729999: valid, but doesn't exist (per 2025-11-24)
        var result = service.findPatients(List.of("0611809735", "0909729999"));
        assertThat(result.getPatients(), contains(where(PatientDemographicsDto::getFamilyName, is("Babbage"))));
    }
}
