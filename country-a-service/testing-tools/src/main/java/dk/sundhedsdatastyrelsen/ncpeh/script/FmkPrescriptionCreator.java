package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ConsentHeaderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreateDrugMedicationResponseType;
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
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import dk.sdsd.dgws._2012._06.ObjectFactory;
import dk.sdsd.dgws._2012._06.WhitelistingHeader;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientDgws;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientException;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.w3c.dom.Element;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

public class FmkPrescriptionCreator {
    private static final JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(
                "dk.dkma.medicinecard.xml_schema._2015._06._01"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e2"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e5"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                    + ":dk.sdsd.dgws._2012._06"
            );
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory facE2 =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory();

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

        // GetMedicineCard should work with IDWS, but it doesn't, so we use DGWS instead.
        // It's not critical for production.
        var medicineCard = getMedicineCard(
            GetMedicineCardRequestType.builder()
                .withPersonIdentifier(personIdentifier)
                .withIncludePrescriptions(true)
                .build(),
            TestIdentities.lægeCharlesBabbage,
            PredefinedRequestedRole.LÆGE
        ).getMedicineCard().getFirst();
        var createDrugMedicationRequest = CreateDrugMedicationRequestType.builder()
            .withPersonIdentifier(personIdentifier)
            .withMedicineCardVersion(medicineCard.getVersion())
            .withCreatedBy(prescriptionCreatedBy())
            .addDrugMedication(drugMedication())
            .build();
        var drugMedicationResponse = createDrugMedication(
            createDrugMedicationRequest,
            TestIdentities.lægeCharlesBabbage,
            PredefinedRequestedRole.LÆGE);

        var medicineCardVersion = drugMedicationResponse.getMedicineCardVersion();
        var drugMedicationIdentifier = drugMedicationResponse.getDrugMedication().getFirst().getIdentifier();

        var createPrescriptionRequest = CreatePrescriptionRequestType.builder()
            .withPersonIdentifier(personIdentifier)
            .withMedicineCardVersion(medicineCardVersion)
            .withCreatedBy(prescriptionCreatedBy())
            .addPrescription()
            .withDrugMedicationIdentifier(drugMedicationIdentifier)
            .withAuthorisationDateTime(xmlGregorianCalendar(ZonedDateTime.now()))
            // System name is mandatory, but the value doesn't seem to be validated by FMK.
            .withSystemName("NCP ePrescription test system")
            .withValidFromDate(xmlGregorianCalendar(LocalDate.now()))
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

        return createPrescription(
            createPrescriptionRequest,
            TestIdentities.lægeCharlesBabbage,
            PredefinedRequestedRole.LÆGE);
    }

    /**
     * Convert a ZonedDateTime to XMLGregorianCalendar.
     */
    static XMLGregorianCalendar xmlGregorianCalendar(ZonedDateTime zdt) {
        return DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(GregorianCalendar.from(zdt));
    }

    /**
     * Convert a LocalDate to XMLGregorianCalendar.
     */
    static XMLGregorianCalendar xmlGregorianCalendar(LocalDate date) {
        return xmlGregorianCalendar(date.atStartOfDay(ZoneId.of("Z")));
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
            .withAuthorisationIdentifier("6QF17")
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
                .withStartDate(xmlGregorianCalendar(LocalDate.now()))
                .withEndDate(xmlGregorianCalendar(LocalDate.now().plusDays(2)))
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
            .withTreatmentStartDate(xmlGregorianCalendar(LocalDate.now()))
            .withTreatmentEndDate(xmlGregorianCalendar(LocalDate.now().plusDays(2)))
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

    public static GetMedicineCardResponseType getMedicineCard(GetMedicineCardRequestType request, NspDgwsIdentity identity, PredefinedRequestedRole requestedRole) throws JAXBException {
        return fmkRequestDgws(
            facE2.createGetMedicineCardRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetMedicineCard",
            GetMedicineCardResponseType.class,
            identity,
            requestedRole,
            false
        );
    }

    public static CreatePrescriptionResponseType createPrescription(
        CreatePrescriptionRequestType request,
        NspDgwsIdentity identity,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        return fmkRequestDgws(
            facE2.createCreatePrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreatePrescription",
            CreatePrescriptionResponseType.class,
            identity,
            requestedRole,
            false
        );
    }

    public static CreateDrugMedicationResponseType createDrugMedication(
        CreateDrugMedicationRequestType request,
        NspDgwsIdentity identity,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        return fmkRequestDgws(
            facE2.createCreateDrugMedicationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreateDrugMedication",
            CreateDrugMedicationResponseType.class,
            identity,
            requestedRole,
            false
        );
    }

    /// We only need DGWS to create prescriptions and drug medications for tests. Foreign health professionals should
    /// not be able to create Danish prescriptions. So we keep it in here.
    private static <RequestType, ResponseType> ResponseType fmkRequestDgws(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        NspDgwsIdentity identity,
        PredefinedRequestedRole requestedRole,
        boolean requiresMedicineCardConsent
    ) throws JAXBException {
        final Element body;
        Element[] extraHeaders;
        if (requiresMedicineCardConsent) {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole)), ClientUtils.toElement(jaxbContext, getMedicineReviewConsent())};
        } else {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole))};
        }
        try {
            body = NspClientDgws.request(
                URI.create(Fmk.FMK_DGWS_ENDPOINT_URI),
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                identity,
                extraHeaders
            );
        } catch (Exception e) {
            throw new NspClientException("FMK request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(body, clazz).getValue();
    }

    private static JAXBElement<WhitelistingHeader> getWhitelistingHeader(PredefinedRequestedRole requestedRole) {
        final var header = WhitelistingHeader.builder()
            .withSystemName("ePPS PoC")
            .withSystemOwnerName("Sundhedsdatastyrelsen")
            .withSystemVersion("0.1.0")
            .withOrgResponsibleName("Sundhedsdatastyrelsen")
            .withOrgUsingName("Sundhedsdatastyrelsen")
            .withOrgUsingID()
            // TODO: Don't use Region Hovedstaden's location number:
            .withNameFormat(NameFormat.MEDCOM_LOCATIONNUMBER).withValue("5790000120512").end()
            .withRequestedRole(requestedRole.value())
            .build();

        return new ObjectFactory().createWhitelistingHeader(header);
    }

    private static JAXBElement<ConsentHeaderType> getMedicineReviewConsent() {
        final var consentHeader = ConsentHeaderType.builder()
            .addConsent()
            .withSource("User")
            .withConsentType("MedicineReviewConsent")
            .withContent("MedicineCard")
            .end()
            .build();

        var objectFactory = new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();

        return objectFactory.createConsentHeader(consentHeader);
    }

    public static void main(String[] args) throws Exception {
        var input = Fmk.cprKarl;
        if (args.length > 0) {
            input = args[0];
        }

        createNewPrecriptionForCpr(input);
    }
}
