package dk.nsp.epps;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalType;
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
import dk.nsp.epps.client.CprClient;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.mock.model.DispenseRequest;
import dk.nsp.epps.mock.model.PackageSize;
import dk.nsp.epps.mock.util.cda.util.CDAUtil;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.test.FmkResponseStorage;
import dk.nsp.test.idp.EmployeeIdentities;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.MapperException;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import eu.europa.ec.sante.ehdsi.openncp.configmanager.domain.Property;
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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoordinatedIntegrationTest
{
    static final String fmkEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling";
    static final String cprEndpointUri =
        "http://test2.ekstern-test.nspop.dk:8080/stamdata-cpr-ws/service/DetGodeCPROpslag-1.0.4";
    private static final File storageDir = new File("src/test/resources/test-file-storage");
    //private static final List<String> testCprs = List.of("1111111118", "0201909309");
    private static final List<String> testCprs = List.of("0201909309");
    private static String getFmkFileName(String cpr) {return "get-prescription-" + cpr + ".xml";}
    private static String getCdaFileName(String cpr) {return "cda-" + cpr + ".xml";}
    private static String getDispensationFileName(String cpr) {return "dispensation-" + cpr + ".xml";}
    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory medCardFac =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();
    static final JAXBContext fmkJaxContext;
    private static FmkClient fmkClient;
    private static CprClient cprClient;

    static {
        try {
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
    static void ensureDirectoriesExist() throws JAXBException, URISyntaxException {
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new RuntimeException("Could not create dir: " + storageDir.getAbsolutePath());
            }
        }
        fmkClient = new FmkClient(fmkEndpointUri);
        cprClient = new CprClient(cprEndpointUri);
    }

    @BeforeEach
    void waitForFilesystem() throws InterruptedException {
        Thread.sleep(1000);
    }

    // This is from FMK Response Storage
    //TODO Make it not generate the fmk-responses folder for no reason when loading the FmkResponseStorage Class
    @Test
    @Order(2)
    void getPrescriptionsFromFmk() throws JAXBException, URISyntaxException {
        var fmkClient = new FmkClient("https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling");
        var frs = new FmkResponseStorage(fmkClient);
        for (var cpr : testCprs) {
            var f = new File(storageDir, getFmkFileName(cpr));
            serializeToFile(frs.openPrescriptionsForCpr(cpr), f, fmkJaxContext);
            System.out.println("Wrote prescriptions to " + f.getAbsolutePath());
        }
    }

    // This is from CDA Generator
    @Test
    @Order(3)
    void generateCdaDocuments() throws JAXBException, TemplateException, MapperException, IOException {
        for (var cpr : testCprs) {
            var fmkResponseFile = new File(storageDir, getFmkFileName(cpr));
            var response = readStoredPrescriptions(fmkResponseFile);
            var xmlString = EPrescriptionL3Generator.generate(response, 0);
            var cdaFile = new File(storageDir, getCdaFileName(cpr));
            serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8),cdaFile);
            System.out.println("Wrote CDAs to " + cdaFile.getAbsolutePath());
        }
    }

    //Country B Mock
    @Test
    @Order(4)
    public void  testGenerateDispensation() throws ParserConfigurationException, IOException, SAXException, JAXBException {
        for (var cpr : testCprs) {
            var epCda = new File(storageDir, getCdaFileName(cpr));
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
                byte[] dispensationDocument = CDAUtil.generateDispensationDocument(dispenseRequest, epDocument, generateIdentifierExtension());
                var dispensationFile = new File(storageDir, getDispensationFileName(cpr));
                serializeToFile(dispensationDocument,dispensationFile);
                System.out.println("Wrote Dispensations to " + dispensationFile.getAbsolutePath());
            }
        }
    }


    //Use dispensation
    @Test
    @Order(5)
    public void fmkSubmitDispensation() throws Exception {
        for (var cpr : testCprs) {
            var dispensationFile = new File(storageDir, getDispensationFileName(cpr));
            try (var is = new FileInputStream(dispensationFile)) {
                var dispensationDocument = dk.nsp.epps.Utils.readXmlDocument(is);
                final var caller = TestIdentities.apotekerChrisChristoffersen;
                var patientId = "0201909309^^^&2.16.17.710.802.1000.990.1.500&ISO";
                var dispensationMapper = new DispensationMapper();
                //       <id extension="0201909309" root="2.16.17.710.802.1000.990.1.500" />
                var effectuationRequest = dispensationMapper.startEffectuationRequest(patientId, dispensationDocument);

                var startEffectuationResponse = fmkClient.startEffectuation(effectuationRequest, caller);
                Assertions.assertTrue(startEffectuationResponse.getStartEffectuationFailed().isEmpty());
                Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst());
                Assertions.assertNotNull(startEffectuationResponse.getPrescription().getFirst().getPackageRestriction());

                var createPharmacyEffectuationResult = fmkClient.createPharmacyEffectuation(
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
     * Create a new prescription for tests in the FMK test environment.
     * The process is:
     *  - Get most recent medicine card version.
     *  - Create "Drug Medication" (lægemiddelordination) on medicine card
     *  - Create prescription on "Drug Medication"
     */
    @Test
    @Order(1)
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
        var createDrugMedicationRequest = CreateDrugMedicationRequestType.builder()
            .withPersonIdentifier(personIdentifier)
            .withMedicineCardVersion(medicineCard.getVersion())
            .withCreatedBy(prescriptionCreatedBy())
            .addDrugMedication(drugMedication())
            .build();
        var drugMedicationResponse = fmkClient.createDrugMedication(createDrugMedicationRequest, EmployeeIdentities.lægeCharlesBabbage(), PredefinedRequestedRole.LÆGE);
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
            .withPackageNumber().withSource(medicinpriserSource).withDate(medicinpriserDate).withValue("056232").end()
            .end()
            // dosage text is mandatory but seems to be overridden by structured dosage info
            .withDosageText("1 tablet 3 gange dagligt")
            .end()
            .build();

        var createPrescriptionResponse = fmkClient.createPrescription(
            createPrescriptionRequest,
            EmployeeIdentities.lægeCharlesBabbage(),
            PredefinedRequestedRole.LÆGE);
        Assertions.assertNotNull(createPrescriptionResponse.getPrescription().getFirst());
    }



    private static GetPrescriptionResponseType readStoredPrescriptions(File f) throws JAXBException {
        var unmarshaller = fmkJaxContext.createUnmarshaller();
        var result = (JAXBElement<?>) unmarshaller.unmarshal(f);
        var value = result.getValue();
        if (value instanceof GetPrescriptionResponseType) {
            return (GetPrescriptionResponseType) value;
        }
        throw new RuntimeException("File does not contain GetPrescriptionResponseType data");
    }

    private static <T> void serializeToFile(JAXBElement<T> obj, File f, JAXBContext context) throws JAXBException {
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, f);
    }

    private static <T> void serializeToFile(byte[] bytes, File file) throws JAXBException, IOException {
        java.nio.file.Files.write(
            java.nio.file.Path.of(file.getAbsolutePath()),
            bytes,
            java.nio.file.StandardOpenOption.CREATE,
            java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private String generateIdentifierExtension() {

        Random secureRandom = new SecureRandom();
        var bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        var extension = Base64.encodeBase64String(bytes);
        return extension.substring(0, 16);
    }

    private static Property createProperty(String key, String value) {
        Property property = new Property();
        property.setKey(key);
        property.setValue(value);
        return property;
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
