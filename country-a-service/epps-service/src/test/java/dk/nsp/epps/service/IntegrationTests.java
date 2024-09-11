package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationOnPrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.Utils;
import dk.nsp.epps.client.CprClient;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.Identities;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.test.idp.OrganizationIdentities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Collection;

/**
 * Integration tests for FMK and CPR.
 * To be run manually (for now) because they require external dependencies (including NSP STS).
 */
@Disabled("Requires external dependencies")
public class IntegrationTests {
    /// Configuration:
    // Alternatives: "https://test2.fmk.netic.dk/fmk12/ws/MedicineCard", "http://localhost:8080/ws/"
    static final String fmkEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling";
    static final String cprEndpointUri =
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
    public void fmkGetMedicineCard() throws Exception {
        var getMedicineCardRequest = GetMedicineCardRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludePrescriptions(true)
            .build();

        var medicineCard = fmkClient.getMedicineCard(getMedicineCardRequest);
        System.out.println(medicineCard.getMedicineCard().size());

    }

    @Test
    public void fmkGetPrescription() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludeOpenPrescriptions().end()
            .build();

        var prescriptions = fmkClient.getPrescription(getPrescriptionRequest, Identities.apotekerChrisChristoffersen);
        Assertions.assertEquals("Helle", prescriptions.getPatient().getPerson().getName().getGivenName());
    }

    @Test
    public void fmkGetPrescriptionAlternativeCaller() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludeOpenPrescriptions().end()
            .build();

        var prescriptions = fmkClient.getPrescription(getPrescriptionRequest, Identities.apotekerJeppeMoeller);
        Assertions.assertEquals("Helle", prescriptions.getPatient().getPerson().getName().getGivenName());
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

        Assertions.assertEquals(
            effectuation.getEffectuation().get(0).getEffectuationIdentifier(),
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


    @Test
    public void fmkSubmitDispensation() throws Exception {
        final var caller = Identities.apotekerChrisChristoffersen;
        var patientId = "0201909309^^^&2.16.17.710.802.1000.990.1.500&ISO";
        var dispensationMapper = new DispensationMapper();
        //       <id extension="0201909309" root="2.16.17.710.802.1000.990.1.500" />
        var effectuationRequest = dispensationMapper.startEffectuationRequest(patientId, testDispensationCda());

        var startEffectuationResponse = fmkClient.startEffectuation(effectuationRequest, caller);
        Assertions.assertTrue(startEffectuationResponse.getStartEffectuationFailed().isEmpty());
        Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst());
        Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getPackageRestriction());

        var createPharmacyEffectuationResult = fmkClient.createPharmacyEffectuation(
            dispensationMapper.createPharmacyEffectuationRequest(
                patientId,
                testDispensationCda(),
                startEffectuationResponse),
            Identities.apotekerChrisChristoffersen);

        Assertions.assertTrue(true);
    }

    Document testDispensationCda() {
        try (var is = this.getClass().getClassLoader().getResourceAsStream("dispensation2.xml")) {
            return Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
