package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructuresForRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PersonIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PredefinedOrganisationTypeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreateDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreateDrugMedicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.nsp.test.idp.EmployeeIdentities;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class FmkPrescriptionCreator {
    //The source for creating prescriptions is always Medicinpriser
    private static final String prescriptionStaticSource = "Medicinpriser";
    // This date was used during development because it worked.  It is unknown if it will keep working.
    // Setting a date is mandatory.
    private static final String prescriptionStaticDate = "2024-09-12";

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory medicineCardFactory =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();

    public static CreatePrescriptionResponseType createNewPrecriptionForCpr(String cpr) throws Exception {
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
        var drugMedicationResponse = Fmk.apiClient()
            .createDrugMedication(
                createDrugMedicationRequest,
                EmployeeIdentities.lægeCharlesBabbage(),
                PredefinedRequestedRole.LÆGE);

        var medicineCardVersion = drugMedicationResponse.getMedicineCardVersion();
        var drugMedicationIdentifier = drugMedicationResponse.getDrugMedication().getFirst().getIdentifier();

        var createPrescriptionRequest = CreatePrescriptionRequestType.builder()
            .withPersonIdentifier(personIdentifier)
            .withMedicineCardVersion(medicineCardVersion)
            .withCreatedBy(prescriptionCreatedBy())
            .addPrescription()
            .withDrugMedicationIdentifier(drugMedicationIdentifier)
            .withAuthorisationDateTime(dk.sundhedsdatastyrelsen.ncpeh.service.Utils.xmlGregorianCalendar(ZonedDateTime.now()))
            // System name is mandatory, but the value doesn't seem to be validated by FMK.
            .withSystemName("NCP ePrescription test system")
            .withValidFromDate(dk.sundhedsdatastyrelsen.ncpeh.service.Utils.xmlGregorianCalendar(LocalDate.now()))
            .withPackageRestriction()
            .withPackageQuantity(1)
            // 056232 is LMS02 id for "Pinex 500mg 100 stks." package
            .withPackageNumber()
            .withSource(prescriptionStaticSource)
            .withDate(prescriptionStaticDate)
            .withValue("056232")
            .end()
            .end()
            // dosage text is mandatory but seems to be overridden by structured dosage info
            .withDosageText("1 tablet 3 gange dagligt")
            .end()
            .build();

        var createPrescriptionResponse = Fmk.apiClient().createPrescription(
            createPrescriptionRequest,
            EmployeeIdentities.lægeCharlesBabbage(),
            PredefinedRequestedRole.LÆGE);

        return createPrescriptionResponse;
    }

    /*
    The following methods are used to populate the "creation of new prescriptions" models.
     */
    private static OrganisationType prescripingOrganisation() {
        // The SKS number is checked by FMK. The name and telephone number are mandatory, but probably not validated.
        return OrganisationType.builder()
            .withIdentifier().withSource("SKS").withValue("133016N").end()
            .withType(PredefinedOrganisationTypeType.SYGEHUS.value())
            .withName("Amager og Hvidovre Hospital,\nFamilieambulatorium, Rigshospitalet")
            .withTelephoneNumber("+4587654321")
            .build();
    }

    private static ModificatorType prescriptionCreatedBy() {
        var authorisedHCP = AuthorisedHealthcareProfessionalType.builder()
            .withAuthorisationIdentifier(EmployeeIdentities.lægeCharlesBabbage()
                .getEmployee()
                .getAuthorizationCode())
            .withName("Charles Babbage")
            .build();
        return ModificatorType.builder()
            .withContent(
                medicineCardFactory.createAuthorisedHealthcareProfessional(authorisedHCP),
                medicineCardFactory.createOrganisation(prescripingOrganisation())
            )
            .build();
    }

    private static CreateDrugMedicationType drugMedication() {
        // "1 tablet 3 gange dagligt"
        var dosageStructure = DosageStructuresForRequestType.builder()
            .addStructureOrEmptyStructure(DosageStructureForRequestType.builder()
                .withIterationInterval(1)
                .withStartDate(dk.sundhedsdatastyrelsen.ncpeh.service.Utils.xmlGregorianCalendar(LocalDate.now()))
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
            .withTreatmentStartDate(dk.sundhedsdatastyrelsen.ncpeh.service.Utils.xmlGregorianCalendar(LocalDate.now()))
            .withTreatmentEndingUndetermined()
            .end()
            .end()
            .withIndication()
            // 145 is LMS26 code for "mod smerter"
            .withCode()
            .withSource(prescriptionStaticSource)
            .withDate(prescriptionStaticDate)
            .withValue(145)
            .end()
            .end()
            .withRouteOfAdministration()
            // OR is LMS11 code for "Oral Anvendelse"
            .withCode()
            .withSource(prescriptionStaticSource)
            .withDate(prescriptionStaticDate)
            .withValue("OR")
            .end()
            .end()
            .withDrug()
            // 28103888005 is LMS01 code for "Pinex 500mg filmovertrukne tabletter"
            .withIdentifier()
            .withSource(prescriptionStaticSource)
            .withDate(prescriptionStaticDate)
            .withValue(28103888005L)
            .end()
            .withName("Pinex")
            .end()
            .withDosage()
            .withUnitText("tablet")
            .withStructuresFixed(dosageStructure)
            .end()
            .withSubstitutionAllowed(true)
            .build();
    }

    public static void main(String[] args) throws Exception {
        var input = Fmk.cprKarl;
        if (args.length > 0) {
            input = args[0];
        }

        createNewPrecriptionForCpr(input);
    }
}
