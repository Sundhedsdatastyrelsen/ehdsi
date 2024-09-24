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
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.client.CprClient;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.test.idp.EmployeeIdentities;
import dk.nsp.epps.testing.shared.Cpr;
import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.test.idp.OrganizationIdentities;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Integration tests for FMK and CPR.
 * To be run manually (for now) because they require external dependencies (including NSP STS).
 */
@Disabled("Requires external dependencies")
public class IntegrationTests {
    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory medCardFac =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();

    @Test
    public void fmkGetMedicineCard() throws Exception {
        var getMedicineCardRequest = GetMedicineCardRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludePrescriptions(true)
            .build();

        var response = Fmk.apiClient().getMedicineCard(
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

        var prescriptions = Fmk.apiClient().getPrescription(getPrescriptionRequest, TestIdentities.apotekerChrisChristoffersen);
        Assertions.assertEquals("Helle", prescriptions.getPatient().getPerson().getName().getGivenName());
    }

    @Test
    public void fmkGetPrescriptionAlternativeCaller() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludeOpenPrescriptions().end()
            .build();

        var prescriptions = Fmk.apiClient().getPrescription(getPrescriptionRequest, TestIdentities.apotekerJeppeMoeller);
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
        final var caller = TestIdentities.apotekerChrisChristoffersen;
        var prescriptions = Fmk.apiClient().getPrescription(
            GetPrescriptionRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
                .withIncludeOpenPrescriptions().end()
                .build(),
            caller);

        var prescription = prescriptions.getPrescription().stream()
            .map(PrescriptionType::getIdentifier)
            .toList();

        var startEffectuation = Fmk.apiClient().startEffectuation(startEffectuationRequest(prescription), caller);
        Assertions.assertTrue(startEffectuation.getStartEffectuationFailed().isEmpty());
        Assertions.assertEquals("Cipramil", startEffectuation.getPrescription().get(0).getDrug().getName());
    }

    @Test
    public void fmkCreateEffectuation() throws Exception {
        final var caller = TestIdentities.apotekerChrisChristoffersen;
        var prescriptions = Fmk.apiClient().getPrescription(
            GetPrescriptionRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
                .withIncludeOpenPrescriptions().end()
                .build(),
            caller);

        var prescription = prescriptions.getPrescription().stream()
            .map(PrescriptionType::getIdentifier)
            .toList();

        var startEffectuation = Fmk.apiClient().startEffectuation(startEffectuationRequest(prescription), caller);

        var request = CreatePharmacyEffectuationRequestType.builder()
            .withPersonIdentifier().end() // TODO
            .withCreatedBy().end() // TODO
            .withPrescription(CreatePharmacyEffectuationOnPrescriptionType.builder()
                .withPrescriptionIdentifier(startEffectuation.getPrescription().get(0).getIdentifier())
                .build())
            .build();

        var effectuation = Fmk.apiClient().createPharmacyEffectuation(request, TestIdentities.apotekerChrisChristoffersen);

        Assertions.assertEquals(
            effectuation.getEffectuation().get(0).getEffectuationIdentifier(),
            1341404078102001010L);
    }

    @Test
    void cprGetPersonInformation() throws Exception {
        var response = Cpr.apiClient().getPersonInformation("0611809735", OrganizationIdentities.sundhedsdatastyrelsen());
        Assertions.assertEquals("Charles Test Babbage", response.getPersonInformationStructure()
            .getRegularCPRPerson().getPersonNameForAddressingName());
    }

    @Test
    void cprGetPersonInformationAlternativeCaller() throws Exception {
        var response = Cpr.apiClient().getPersonInformation("0611809735", TestIdentities.apotekerJeppeMoeller);
        Assertions.assertEquals("Charles Test Babbage", response.getPersonInformationStructure()
            .getRegularCPRPerson().getPersonNameForAddressingName());
    }


    @Test
    public void fmkSubmitDispensation() throws Exception {

        final var caller = TestIdentities.apotekerChrisChristoffersen;
        var patientId = "0201909309^^^&2.16.17.710.802.1000.990.1.500&ISO";
        var dispensationMapper = new DispensationMapper();
        //       <id extension="0201909309" root="2.16.17.710.802.1000.990.1.500" />
        var effectuationRequest = dispensationMapper.startEffectuationRequest(patientId, testDispensationCda());

        var startEffectuationResponse = Fmk.apiClient().startEffectuation(effectuationRequest, caller);
        Assertions.assertTrue(startEffectuationResponse.getStartEffectuationFailed().isEmpty());
        Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst());
        Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getPackageRestriction());

        var createPharmacyEffectuationResult = Fmk.apiClient().createPharmacyEffectuation(
            dispensationMapper.createPharmacyEffectuationRequest(
                patientId,
                testDispensationCda(),
                startEffectuationResponse),
            TestIdentities.apotekerChrisChristoffersen);



        Assertions.assertTrue(true);


    }

    Document testDispensationCda() {
        try (var is = this.getClass().getClassLoader().getResourceAsStream("dispensation2.xml")) {
            return dk.nsp.epps.Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new prescription for tests in the FMK test environment.
     * The process is:
     *  - Get most recent medicine card version.
     *  - Create "Drug Medication" (lægemiddelordination) on medicine card
     *  - Create prescription on "Drug Medication"
     */
    @Test
    void createNewPrescription() throws Exception {
        var cpr = "0201909309";

        var personIdentifier = PersonIdentifierType.builder()
            .withSource("CPR")
            .withValue(cpr)
            .build();

        var medicineCard = Fmk.apiClient().getMedicineCard(
            GetMedicineCardRequestType.builder()
                .withPersonIdentifier(personIdentifier)
                .withIncludePrescriptions(true)
                .build(),
            EmployeeIdentities.lægeCharlesBabbage(),
            PredefinedRequestedRole.LÆGE
        ).getMedicineCard().getFirst();
        var createDrugMedicationRequest = CreateDrugMedicationRequestType.builder()
            .withPersonIdentifier(personIdentifier)
            .withMedicineCardVersion(medicineCard.getVersion())
            .withCreatedBy(prescriptionCreatedBy())
            .addDrugMedication(drugMedication())
            .build();
        var drugMedicationResponse = Fmk.apiClient().createDrugMedication(createDrugMedicationRequest, EmployeeIdentities.lægeCharlesBabbage(), PredefinedRequestedRole.LÆGE);
        Assertions.assertEquals(1, drugMedicationResponse.getDrugMedication().size());

        var medicineCardVersion = drugMedicationResponse.getMedicineCardVersion();
        var drugMedicationIdentifier = drugMedicationResponse.getDrugMedication().getFirst().getIdentifier();

        var createPrescriptionRequest = CreatePrescriptionRequestType.builder()
            .withPersonIdentifier(personIdentifier)
            .withMedicineCardVersion(medicineCardVersion)
            .withCreatedBy(prescriptionCreatedBy())
            .addPrescription()
            .withDrugMedicationIdentifier(drugMedicationIdentifier)
            .withAuthorisationDateTime(Utils.xmlGregorianCalendar(ZonedDateTime.now()))
            // System name is mandatory, but the value doesn't seem to be validated by FMK.
            .withSystemName("NCP ePrescription test system")
            .withValidFromDate(Utils.xmlGregorianCalendar(LocalDate.now()))
            .withPackageRestriction()
            .withPackageQuantity(1)
            // 056232 is LMS02 id for "Pinex 500mg 100 stks." package
            .withPackageNumber().withSource(medicinpriserSource).withDate(medicinpriserDate).withValue("056232").end()
            .end()
            // dosage text is mandatory but seems to be overridden by structured dosage info
            .withDosageText("1 tablet 3 gange dagligt")
            .end()
            .build();

        var createPrescriptionResponse = Fmk.apiClient().createPrescription(
            createPrescriptionRequest,
            EmployeeIdentities.lægeCharlesBabbage(),
            PredefinedRequestedRole.LÆGE);
        Assertions.assertNotNull(createPrescriptionResponse.getPrescription().getFirst());
    }

    private static final String medicinpriserSource = "Medicinpriser";
    // This date was used during development because it worked.  It is unknown if it will keep working.
    // Setting a date is mandatory.
    private static final String medicinpriserDate = "2024-09-12";

    private OrganisationType prescripingOrganisation() {
        // The SKS number is checked by FMK. The name and telephone number are mandatory, but probably not validated.
        return OrganisationType.builder()
            .withIdentifier().withSource("SKS").withValue("133016N").end()
            .withType(PredefinedOrganisationTypeType.SYGEHUS.value())
            .withName("Amager og Hvidovre Hospital,\nFamilieambulatorium, Rigshospitalet")
            .withTelephoneNumber("+4587654321")
            .build();
    }

    private ModificatorType prescriptionCreatedBy() {
        var authorisedHCP = AuthorisedHealthcareProfessionalType.builder()
            .withAuthorisationIdentifier(EmployeeIdentities.lægeCharlesBabbage().getEmployee().getAuthorizationCode())
            .withName("Charles Babbage")
            .build();
        return ModificatorType.builder()
            .withContent(
                medCardFac.createAuthorisedHealthcareProfessional(authorisedHCP),
                medCardFac.createOrganisation(prescripingOrganisation())
            )
            .build();
    }

    private CreateDrugMedicationType drugMedication() {
        // "1 tablet 3 gange dagligt"
        var dosageStructure = DosageStructuresForRequestType.builder()
            .addStructureOrEmptyStructure(DosageStructureForRequestType.builder()
                .withIterationInterval(1)
                .withStartDate(Utils.xmlGregorianCalendar(LocalDate.now()))
                .withDosageEndingUndetermined().end()
                .addDay()
                    .withNumber(1)
                    .addDose().withQuantity(BigDecimal.valueOf(1)).end()
                    .addDose().withQuantity(BigDecimal.valueOf(1)).end()
                    .addDose().withQuantity(BigDecimal.valueOf(1)).end()
                .end()
                .build())
            .build();

        return CreateDrugMedicationType.builder()
            .withBeginEndDate()
                .withTreatmentStartDate(Utils.xmlGregorianCalendar(LocalDate.now()))
                .withTreatmentEndingUndetermined().end()
            .end()
            .withIndication()
                // 145 is LMS26 code for "mod smerter"
                .withCode().withSource(medicinpriserSource).withDate(medicinpriserDate).withValue(145).end()
            .end()
            .withRouteOfAdministration()
                // OR is LMS11 code for "Oral Anvendelse"
                .withCode().withSource(medicinpriserSource).withDate(medicinpriserDate).withValue("OR").end()
            .end()
            .withDrug()
                // 28103888005 is LMS01 code for "Pinex 500mg filmovertrukne tabletter"
                .withIdentifier().withSource(medicinpriserSource).withDate(medicinpriserDate).withValue(28103888005L).end()
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
