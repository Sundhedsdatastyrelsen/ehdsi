package dk.nsp.epps;

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
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.mock.model.DispenseRequest;
import dk.nsp.epps.mock.model.PackageSize;
import dk.nsp.epps.mock.util.cda.util.CDAUtil;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.epps.testing.shared.FmkResponseStorage;
import dk.nsp.test.idp.EmployeeIdentities;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.MapperException;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static dk.nsp.epps.testing.shared.FmkResponseStorage.serializeToFile;
import static dk.nsp.epps.testing.shared.StaticFileNames.getCdaFileName;
import static dk.nsp.epps.testing.shared.StaticFileNames.getDispensationFileName;
import static dk.nsp.epps.testing.shared.StaticFileNames.getFmkFileName;
import static dk.nsp.epps.testing.shared.StaticFileNames.storageDir;

@SuppressWarnings("NonAsciiCharacters")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FmkPrescriptionIT {
    static final String fmkEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling";
    //The source for creating prescriptions is always Medicinpriser
    private static final String prescriptionStaticSource = "Medicinpriser";
    // This date was used during development because it worked.  It is unknown if it will keep working.
    // Setting a date is mandatory.
    private static final String prescriptionStaticDate = "2024-09-12";

    public static Stream<Arguments> provideTestingInput(boolean preparedInput) {
        return Stream.of(
            Arguments.of("1111111118", preparedInput ? "prepared" : "integration","integration"),
            Arguments.of("0201909309", preparedInput ? "prepared" : "integration","integration")
        );
    }

    private static Stream<Arguments> providePreparedTestingInput(){
        return provideTestingInput(true);
    }

    private static Stream<Arguments> provideIntegrationTestingInput(){
        return provideTestingInput(false);
    }

    private static final List<String> testCprs = List.of("1111111118", "0201909309");
    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory medicineCardFactory =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();


    static final JAXBContext fmkJaxContext; //Initialized below

    static {
        try {
            //Create the XML context
            fmkJaxContext = JAXBContext.newInstance(
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

    @BeforeAll
    static void ensureDirectoriesExistAndInitializeFmkClient() throws JAXBException, URISyntaxException {
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new RuntimeException("Could not create dir: " + storageDir.getAbsolutePath());
            }
        }
    }

    /**
     * We have experienced some problems running the test sequentially, maybe because the operation to write to disk is
     * async, so the next method starts before the last one finishes. Thus we just wait a bit for the filesystem to finish
     */
    @BeforeEach
    void waitForFilesystem() throws InterruptedException {
        Thread.sleep(2000);
    }

    /**
     * Create a new prescription for tests in the FMK test environment.
     * The process is:
     * - Get most recent medicine card version.
     * - Create "Drug Medication" (lægemiddelordination) on medicine card
     * - Create prescription on "Drug Medication"
     */
    //Slated for "scriptization"
    @Test
    @Order(1)
    void createNewPrescription() throws Exception {
        for (var cpr : testCprs) {
            var response = createNewPrecriptionForCpr(cpr);
            Assertions.assertNotNull(response.getPrescription().getFirst());
        }
    }

    public CreatePrescriptionResponseType createNewPrecriptionForCpr(String cpr) throws Exception {
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
                                                                     .withAuthorisationDateTime(dk.nsp.epps.service.Utils.xmlGregorianCalendar(ZonedDateTime.now()))
                                                                     // System name is mandatory, but the value doesn't seem to be validated by FMK.
                                                                     .withSystemName("NCP ePrescription test system")
                                                                     .withValidFromDate(dk.nsp.epps.service.Utils.xmlGregorianCalendar(LocalDate.now()))
                                                                     .withPackageRestriction()
                                                                     .withPackageQuantity(1)
                                                                     // 056232 is LMS02 id for "Pinex 500mg 100 stks." package
                                                                     .withPackageNumber().withSource(prescriptionStaticSource).withDate(prescriptionStaticDate).withValue("056232").end()
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


    // Keep
    @ParameterizedTest
    @MethodSource("provideIntegrationTestingInput")
    @Order(2)
    public void getPrescriptionsFromFmk(String cpr, String inputFileMark, String outputFileMark) throws JAXBException, URISyntaxException {

    }

    // Keep
    @Test
    @Order(3)
    void generateCdaDocuments() throws JAXBException, TemplateException, MapperException, IOException {
        for (var cpr : testCprs) {
            var fmkResponseFile = new File(storageDir, getFmkFileName(cpr));
            var response = readStoredPrescriptions(fmkResponseFile);
            var xmlString = EPrescriptionL3Generator.generate(response, 0);
            var cdaFile = new File(storageDir, getCdaFileName(cpr));
            serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8), cdaFile);
            System.out.println("Wrote CDAs to " + cdaFile.getAbsolutePath());
        }
    }

    public void generateCdaDocumentForCpr(String cpr, File fmkResponse, File cdaOutputFile) throws JAXBException, TemplateException, MapperException, IOException {
        var response = readStoredPrescriptions(fmkResponse);
        var xmlString = EPrescriptionL3Generator.generate(response, 0);
        serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8), cdaOutputFile);
    }

    //Replace
    @Test
    @Order(4)
    public void testGenerateDispensation() throws ParserConfigurationException, IOException, SAXException, JAXBException {
        for (var cpr : testCprs) {
            var epCda = new File(storageDir, getCdaFileName(cpr));

            byte[] dispensationDocument = generateTestDispensation(epCda);
            var dispensationFile = new File(storageDir, getDispensationFileName(cpr));
            serializeToFile(dispensationDocument, dispensationFile);
            System.out.println("Wrote Dispensations to " + dispensationFile.getAbsolutePath());

        }
    }

    public byte[] generateTestDispensation(File epCda) throws IOException, ParserConfigurationException, SAXException {
        try (var is = new FileInputStream(epCda)) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document epDocument = builder.parse(new InputSource(is));
            DispenseRequest dispenseRequest = new DispenseRequest();
            dispenseRequest.setCountryCode("EU");
            dispenseRequest.setNumberOfPackage(1);
            dispenseRequest.setPrescriptionId("prescriptionId");
            dispenseRequest.setProductName("test product");
            dispenseRequest.setSubstitution(true);
            PackageSize packageSize = new PackageSize();
            packageSize.setPackageSizeL1("1");
            packageSize.setPackageSizeL2("2");
            packageSize.setPackageSizeL3(null);
            packageSize.setQuantity(null);
            dispenseRequest.setPackageSize(packageSize);
            return CDAUtil.generateDispensationDocument(dispenseRequest, epDocument, generateIdentifierExtension());
        }
    }


    //Keep
    @Test
    @Order(5)
    public void fmkSubmitDispensation() throws Exception {
        for (var cpr : testCprs) {
            var dispensationFile = new File(storageDir, getDispensationFileName(cpr));
            try (var is = new FileInputStream(dispensationFile)) {
                var dispensationDocument = dk.nsp.epps.Utils.readXmlDocument(is);
                final var caller = TestIdentities.apotekerChrisChristoffersen;
                var patientId = cpr + "^^^&2.16.17.710.802.1000.990.1.500&ISO";
                var dispensationMapper = new DispensationMapper();
                //       <id extension="0201909309" root="2.16.17.710.802.1000.990.1.500" />
                var effectuationRequest = dispensationMapper.startEffectuationRequest(patientId, dispensationDocument);

                var startEffectuationResponse = Fmk.apiClient().startEffectuation(effectuationRequest, caller);
                Assertions.assertTrue(startEffectuationResponse.getStartEffectuationFailed().isEmpty(), () -> "Effectuation call failed with message: " + startEffectuationResponse.getStartEffectuationFailed().get(0).getReasonText());
                Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst());
                Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getPackageRestriction());

                var createPharmacyEffectuationResult = Fmk.apiClient().createPharmacyEffectuation(
                    dispensationMapper.createPharmacyEffectuationRequest(
                        patientId,
                        dispensationDocument,
                        startEffectuationResponse),
                    TestIdentities.apotekerChrisChristoffersen);

                Assertions.assertFalse(createPharmacyEffectuationResult.getEffectuation().isEmpty());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }




    /**
     * Used to read stored prescription responses from a file
     *
     * @param f the file to read
     * @return A stored prescription response from FMK
     */
    private static GetPrescriptionResponseType readStoredPrescriptions(File f) throws JAXBException {
        var unmarshaller = fmkJaxContext.createUnmarshaller();
        var result = (JAXBElement<?>) unmarshaller.unmarshal(f);
        var value = result.getValue();
        if (value instanceof GetPrescriptionResponseType) {
            return (GetPrescriptionResponseType) value;
        }
        throw new RuntimeException("File does not contain GetPrescriptionResponseType data");
    }

    /**
     * Generate a random identifier string
     */
    private String generateIdentifierExtension() {
        Random secureRandom = new SecureRandom();
        var bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        var extension = Base64.encodeBase64String(bytes);
        return extension.substring(0, 16);
    }

    /*
    The following methods are used to populate the "creation of new prescriptions" models.
     */
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
                medicineCardFactory.createAuthorisedHealthcareProfessional(authorisedHCP),
                medicineCardFactory.createOrganisation(prescripingOrganisation())
            )
            .build();
    }

    private CreateDrugMedicationType drugMedication() {
        // "1 tablet 3 gange dagligt"
        var dosageStructure = DosageStructuresForRequestType.builder()
            .addStructureOrEmptyStructure(DosageStructureForRequestType.builder()
                .withIterationInterval(1)
                .withStartDate(dk.nsp.epps.service.Utils.xmlGregorianCalendar(LocalDate.now()))
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
            .withTreatmentStartDate(dk.nsp.epps.service.Utils.xmlGregorianCalendar(LocalDate.now()))
            .withTreatmentEndingUndetermined().end()
            .end()
            .withIndication()
            // 145 is LMS26 code for "mod smerter"
            .withCode().withSource(prescriptionStaticSource).withDate(prescriptionStaticDate).withValue(145).end()
            .end()
            .withRouteOfAdministration()
            // OR is LMS11 code for "Oral Anvendelse"
            .withCode().withSource(prescriptionStaticSource).withDate(prescriptionStaticDate).withValue("OR").end()
            .end()
            .withDrug()
            // 28103888005 is LMS01 code for "Pinex 500mg filmovertrukne tabletter"
            .withIdentifier().withSource(prescriptionStaticSource).withDate(prescriptionStaticDate).withValue(28103888005L).end()
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
