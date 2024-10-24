package dk.nsp.epps;

import dk.nsp.epps.integration.CreateNewPrescriptionInFmk;
import dk.nsp.epps.integration.GenerateCdaDocument;
import dk.nsp.epps.integration.GetPrescriptionFromFmk;
import dk.nsp.epps.integration.SubmitDispensationToFmk;
import dk.nsp.epps.integration.UndoEffectuationInFmk;
import dk.nsp.epps.testing.shared.TestingInput;
import dk.sds.ncp.cda.MapperException;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("NonAsciiCharacters")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FmkPrescriptionIT {

    //Name for internally handled state communication
    private static final String internalFileMark = "integration";

    public static Stream<Arguments> provideTestingInput(boolean preparedInput) {
        return Arrays
            .stream(TestingInput.testingCprs())
            .map(cpr -> Arguments.of(cpr, preparedInput
                ? TestingInput.preparedFilesMark()
                : internalFileMark, internalFileMark));
    }

    private static Stream<Arguments> providePreparedTestingInput() {
        return provideTestingInput(true);
    }

    private static Stream<Arguments> provideIntegrationTestingInput() {
        return provideTestingInput(false);
    }

    /**
     * We have experienced some problems running the test sequentially, maybe because the operation to write to disk is
     * async, so the next method starts before the last one finishes. Thus we just wait a bit for the filesystem to finish
     */
    @BeforeEach
    public void waitForFilesystem() throws InterruptedException {
        Thread.sleep(2000);
    }

    /**
     * Create a new prescription for tests in the FMK test environment.
     * The process is:
     * - Get most recent medicine card version.
     * - Create "Drug Medication" (lægemiddelordination) on medicine card
     * - Create prescription on "Drug Medication"
     */
    @ParameterizedTest
    @MethodSource("provideIntegrationTestingInput") //Method ignores both input and output file, so we can use either
    @Order(1)
    @Disabled("Should not be run automatically")
    void createNewPrescription(String cpr, String inputFileMark, String outputFileMark) throws Exception {
        var response = CreateNewPrescriptionInFmk.createNewPrecriptionForCpr(cpr);
        Assertions.assertNotNull(response.getPrescription().getFirst());
    }


    // Keep
    @ParameterizedTest
    @MethodSource("provideIntegrationTestingInput") //Input file is ignored, so either works
    @Order(2)
    public void getPrescriptionsFromFmk(String cpr, String inputFileMark, String outputFileMark) throws JAXBException, URISyntaxException {
        GetPrescriptionFromFmk.getPrescriptionsFromFmkForCpr(cpr, outputFileMark);
    }

    // Keep
    @ParameterizedTest
    //@MethodSource("provideIntegrationTestingInput") //Use this to run them all in sequence in here
    @MethodSource("providePreparedTestingInput")
    @Order(3)
    void generateCdaDocuments(String cpr, String inputFileMark, String outputFileMark) throws JAXBException, TemplateException, MapperException, IOException {
        GenerateCdaDocument.generateCdaDocumentForCpr(cpr, inputFileMark, outputFileMark);
    }

    /* COUNTRY B
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
    private String generateIdentifierExtension() {
        Random secureRandom = new SecureRandom();
        var bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        var extension = Base64.encodeBase64String(bytes);
        return extension.substring(0, 16);
    }

    */


    //Keep
    @ParameterizedTest
    //@MethodSource("provideIntegrationTestingInput") //Use this to run them all in sequence in here
    @MethodSource("providePreparedTestingInput")
    @Order(5)
    @Disabled("Missing Country-B part")
    public void fmkSubmitDispensation(String cpr, String inputFileMark, String outputFileMark) throws Exception {
        var startEffectuationResponse = SubmitDispensationToFmk.startEffectuation(cpr, inputFileMark);
        Assertions.assertTrue(startEffectuationResponse
            .getStartEffectuationFailed()
            .isEmpty(), () -> "Effectuation call failed with message: " + startEffectuationResponse
            .getStartEffectuationFailed()
            .get(0)
            .getReasonText());
        Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst());
        Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getPackageRestriction());

        var createPharmacyEffectuationResult = SubmitDispensationToFmk.createPharmacyEffectuation(
            cpr,
            inputFileMark,
            startEffectuationResponse);
        Assertions.assertFalse(createPharmacyEffectuationResult.getEffectuation().isEmpty());
    }

    @ParameterizedTest
    //@MethodSource("provideIntegrationTestingInput") //Use this to run them all in sequence in here
    @MethodSource("providePreparedTestingInput")
    @Order(6)
    @Disabled("Missing Country-B part")
    public void fmkUndoDispensation(String cpr, String inputFileMark, String outputFileMark) throws Exception {
        var undoEffectuationResponse = UndoEffectuationInFmk.undoEffectuation(cpr, inputFileMark);
        //No exceptions thrown means a success
    }
}
