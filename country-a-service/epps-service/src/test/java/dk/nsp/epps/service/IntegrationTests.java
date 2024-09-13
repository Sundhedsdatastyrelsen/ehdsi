package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructuresForRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PersonIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PredefinedOrganisationTypeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreateDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreateDrugMedicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationOnPrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.client.CprClient;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.Identities;
import dk.nsp.test.idp.EmployeeIdentities;
import dk.nsp.test.idp.OrganizationIdentities;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
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

        var response = fmkClient.getMedicineCard(
            getMedicineCardRequest,
            EmployeeIdentities.lægeCharlesBabbage(),
            PredefinedRequestedRole.LÆGE
        );
        Assertions.assertEquals(response.getMedicineCard().size(), 1);
        var medicineCard = response.getMedicineCard().getFirst();
        Assertions.assertNotNull(medicineCard.getVersion());
    }

    @Test
    public void fmkGetPrescription() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludeOpenPrescriptions().end()
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

        var prescriptions = fmkClient.getPrescription(getPrescriptionRequest, Identities.apotekerJeppeMoeller);
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
    void createNewPrescription() throws Exception {
        var cpr = "0201909309";

        var personIdentifier = PersonIdentifierType.builder()
            .withSource("CPR")
            .withValue(cpr)
            .build();

        var medicineCard = fmkClient.getMedicineCard(
            GetMedicineCardRequestType.builder()
                .withPersonIdentifier(personIdentifier)
                .withIncludePrescriptions(true)
                .build(),
            EmployeeIdentities.lægeCharlesBabbage(),
            PredefinedRequestedRole.LÆGE
        ).getMedicineCard().getFirst();
        var request = CreateDrugMedicationRequestType.builder()
            .withPersonIdentifier(personIdentifier)
            .withMedicineCardVersion(medicineCard.getVersion())
            .withCreatedBy(prescriptionCreatedBy())
            .addDrugMedication(drugMedication())
            .build();
        var drugMedication = fmkClient.createDrugMedication(request, EmployeeIdentities.lægeCharlesBabbage(), PredefinedRequestedRole.LÆGE);

        Assertions.assertNotNull(drugMedication);
    }

    private ModificatorType prescriptionCreatedBy() {
        var authorisedHCP = AuthorisedHealthcareProfessionalType.builder()
            .withAuthorisationIdentifier(EmployeeIdentities.lægeCharlesBabbage().getEmployee().getAuthorizationCode())
            .withName("Charles Babbage")
            .build();
        var org = OrganisationType.builder()
            .withIdentifier().withSource("SKS").withValue("133016N").end()
            .withType(PredefinedOrganisationTypeType.SYGEHUS.value())
            .withName("Amager og Hvidovre Hospital,\nFamilieambulatorium, Rigshospitalet")
            .build();
        return ModificatorType.builder()
            .withContent(
                medCardFac.createAuthorisedHealthcareProfessional(authorisedHCP),
                medCardFac.createOrganisation(org)
            )
            .build();
    }

    private CreateDrugMedicationType drugMedication() {
        var dosageStructure = DosageStructuresForRequestType.builder()
            .addStructureOrEmptyStructure(DosageStructureForRequestType.builder()
                .withIterationInterval(1)
                .withStartDate(Utils.xmlGregorianCalendar(LocalDate.now()))
                .withDosageEndingUndetermined().end()
                .addDay()
                    .withNumber(1)
                    .addDose().withQuantity(BigDecimal.valueOf(3)).end()
                .end()
                .build())
            .build();

        return CreateDrugMedicationType.builder()
            .withBeginEndDate()
                .withTreatmentStartDate(Utils.xmlGregorianCalendar(LocalDate.now()))
                .withTreatmentEndingUndetermined().end()
            .end()
            .withIndication()
                .withCode().withSource("Medicinpriser").withDate("2024-09-12").withValue(145).end()
            .end()
            .withRouteOfAdministration()
                .withCode().withSource("Medicinpriser").withDate("2024-09-12").withValue("OR").end()
            .end()
            .withDrug()
                .withIdentifier().withSource("Medicinpriser").withDate("2024-09-12").withValue(28103888005L).end()
                .withName("Pinex")
            .end()
            .withDosage()
                .withUnitText("tablet")
                .withStructuresFixed(dosageStructure)
            .end()
            .withSubstitutionAllowed(true)
            .build();
    }
}
