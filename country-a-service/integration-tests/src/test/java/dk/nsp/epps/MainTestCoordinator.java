package dk.nsp.epps;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.mock.model.DispenseRequest;
import dk.nsp.epps.mock.model.PackageSize;
import dk.nsp.epps.mock.util.cda.util.CDAUtil;
import dk.nsp.epps.test.FmkResponseStorage;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.MapperException;
import eu.europa.ec.sante.ehdsi.openncp.configmanager.ConfigurationManagerFactory;
import eu.europa.ec.sante.ehdsi.openncp.configmanager.domain.Property;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import static dk.nsp.epps.test.FmkResponseStorage.storedPrescriptions;

public class MainTestCoordinator
{
    private static final File storageDir = new File("integration-tests/src/test/resources/test-file-storage");
    private static final List<String> testCprs = List.of("1111111118", "0201909309");
    private static String getFmkFileName(String cpr) {return "get-prescription-" + cpr + ".xml";}
    private static String getCdaFileName(String cpr) {return "cda-" + cpr + ".xml";}
    private static String getDispensationFileName(String cpr) {return "dispensation-" + cpr + ".xml";}
    static final JAXBContext fmkJaxContext;

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
    static void ensureDirectoriesExist() {
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new RuntimeException("Could not create dir: " + storageDir.getAbsolutePath());
            }
        }
    }

    // This is from FMK Response Storage
    //TODO Make it not generate the fmk-responses folder for no reason when loading the FmkResponseStorage Class
    @Test
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
}
