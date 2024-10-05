package dk.nsp.epps;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.testing.shared.Cpr;
import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.test.idp.OrganizationIdentities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for FMK and CPR.
 * To be run manually (for now) because they require external dependencies (including NSP STS).
 */
public class FmkInfoGetIT {
    @Test
    public void fmkGetPrescriptionAlternativeCaller() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType
            .builder()
            .withPersonIdentifier()
            .withSource("CPR")
            .withValue("1111111118")
            .end()
            .withIncludeOpenPrescriptions()
            .end()
            .build();

        var prescriptions = Fmk
            .apiClient()
            .getPrescription(getPrescriptionRequest, TestIdentities.apotekerJeppeMoeller);
        Assertions.assertEquals("Helle", prescriptions.getPatient().getPerson().getName().getGivenName());
    }


    @Test
    void cprGetPersonInformation() throws Exception {
        var response = Cpr
            .apiClient()
            .getPersonInformation("0611809735", OrganizationIdentities.sundhedsdatastyrelsen());
        Assertions.assertEquals("Charles Test Babbage", response
            .getPersonInformationStructure()
            .getRegularCPRPerson()
            .getPersonNameForAddressingName());
    }

    @Test
    void cprGetPersonInformationAlternativeCaller() throws Exception {
        var response = Cpr.apiClient().getPersonInformation("0611809735", TestIdentities.apotekerJeppeMoeller);
        Assertions.assertEquals("Charles Test Babbage", response
            .getPersonInformationStructure()
            .getRegularCPRPerson()
            .getPersonNameForAddressingName());
    }
}
