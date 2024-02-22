package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationOnPrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.service.client.CprClient;
import dk.nsp.epps.service.client.FmkClient;
import dk.nsp.epps.service.client.Identities;
import dk.nsp.test.idp.OrganizationIdentities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collection;

/**
 * Integration tests for FMK and CPR.
 * To be run manually (for now) because they require external dependencies (including NSP STS).
 */
@Disabled("Requires external dependencies")
public class IntegrationTests {
    /// Configuration:
    // Alternatives: "https://test2.fmk.netic.dk/fmk12/ws/MedicineCard", "http://localhost:8080/ws/"
    private static final String fmkEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling";
    private static final String cprEndpointUri =
        "http://test2.ekstern-test.nspop.dk:8080/stamdata-cpr-ws/service/DetGodeCPROpslag-1.0.4";

    private static FmkClient fmkClient;
    private static CprClient cprClient;

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory medCardFac =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();

    @BeforeAll
    public static void setup() throws Exception {
        fmkClient = new FmkClient(fmkEndpointUri);
        cprClient = new CprClient(cprEndpointUri);
    }

    @Test
    public void fmkGetPrescription() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludeAllPrescriptions().end()
            .build();

        var prescriptions = fmkClient.getPrescription(getPrescriptionRequest, Identities.apotekerChrisChristoffersen);
        Assertions.assertEquals("Helle", prescriptions.getPatient().getPerson().getName().getGivenName());
        Assertions.assertEquals("Cipramil", prescriptions.getPrescription().get(0).getDrug().getName());
    }

    @Test
    public void fmkGetPrescriptionAlternativeCaller() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludeOpenPrescriptions().end()
            .build();

        var prescriptions = fmkClient.getPrescription(getPrescriptionRequest, Identities.apotekerChrisChristoffersen);
        Assertions.assertEquals("Helle", prescriptions.getPatient().getPerson().getName().getGivenName());
        Assertions.assertEquals("Cipramil", prescriptions.getPrescription().get(0).getDrug().getName());
    }

    private StartEffectuationRequestType startEffectuationRequest(Collection<Long> prescriptionIds) {
        // based on example at https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:pabegynd_ekspedition
        var pharmacy = OrganisationType.builder()
            .withName("Skanderborg Apotek")
            .withAddressLine("Adelgade 27", "8660 Skanderborg")
            .withType("Apotek")
            .withIdentifier().withSource("EAN-Lokationsnummer").withValue("5790000170609").end()
            .build();
        return StartEffectuationRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withOrderedAtPharmacy(pharmacy)
            .withModifiedBy().withContent(
                medCardFac.createModificatorTypeOther(
                    ModificatorPersonType.builder()
                        .withName().withGivenName("Chris").withSurname("Christoffersen").end()
                        .withPersonIdentifier().withSource("CPR").withValue("3001010033").end().build()),
                medCardFac.createModificatorTypeRole("Apoteker"),
                medCardFac.createModificatorTypeOrganisation(pharmacy)
            ).end()
            .withPrescription(prescriptionIds.stream()
                .map(p -> StartEffectuationRequestType.Prescription.builder().withIdentifier(p).build())
                .toList())

            .build();
    }

    @Test
    public void fmkStartEffectuation() throws Exception {
        final var caller = Identities.apotekerChrisChristoffersen;
        var prescriptions = fmkClient.getPrescription(
            GetPrescriptionRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
                .withIncludeOpenPrescriptions().end()
                .build(),
            caller);

        var prescription = prescriptions.getPrescription().stream()
            .map(PrescriptionType::getIdentifier)
            .toList();

        var startEffectuation = fmkClient.startEffectuation(startEffectuationRequest(prescription), caller);
        Assertions.assertTrue(startEffectuation.getStartEffectuationFailed().isEmpty());
        Assertions.assertEquals("Cipramil", startEffectuation.getPrescription().get(0).getDrug().getName());
    }

    @Test
    public void fmkCreateEffectuation() throws Exception {
        final var caller = Identities.apotekerChrisChristoffersen;
        var prescriptions = fmkClient.getPrescription(
            GetPrescriptionRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
                .withIncludeOpenPrescriptions().end()
                .build(),
            caller);

        var prescription = prescriptions.getPrescription().stream()
            .map(PrescriptionType::getIdentifier)
            .toList();

        var startEffectuation = fmkClient.startEffectuation(startEffectuationRequest(prescription), caller);

        var request = CreatePharmacyEffectuationRequestType.builder()
            .withPersonIdentifier().end() // TODO
            .withCreatedBy().end() // TODO
            .withPrescription(CreatePharmacyEffectuationOnPrescriptionType.builder()
                .withPrescriptionIdentifier(startEffectuation.getPrescription().get(0).getIdentifier())
                .build())
            .build();

        var effectuation = fmkClient.createPharmacyEffectuation(request, Identities.apotekerChrisChristoffersen);

        Assertions.assertEquals(effectuation.getEffectuation().get(0).getEffectuationIdentifier(),
            1341404078102001010L);
    }

    @Test
    void cprGetPersonInformation() throws Exception {
        var response = cprClient.getPersonInformation("0611809735", OrganizationIdentities.sundhedsdatastyrelsen());
        Assertions.assertEquals("Charles Test Babbage", response.getPersonInformationStructure()
            .getRegularCPRPerson().getPersonNameForAddressingName());
    }

    @Test
    void cprGetPersonInformationAlternativeCaller() throws Exception {
        var response = cprClient.getPersonInformation("0611809735", Identities.apotekerJeppeMoeller);
        Assertions.assertEquals("Charles Test Babbage", response.getPersonInformationStructure()
            .getRegularCPRPerson().getPersonNameForAddressingName());
    }
}
