package dk.nsp.epps;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.test.FmkResponseStorage;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.MapperException;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static dk.nsp.epps.test.FmkResponseStorage.storedPrescriptions;

public class MainTestCoordinator
{
    private static final File storageDir = new File("integration-tests/src/test/resources/test-file-storage");
    private static final List<String> testCprs = List.of("1111111118", "0201909309");
    private static String getFmkFileName(String cpr) {return "get-prescription-" + cpr + ".xml";}
    private static String getCdaFileName(String cpr) {return "cda-" + cpr + ".xml";}
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
            java.nio.file.Files.writeString(
                 java.nio.file.Path.of(cdaFile.getAbsolutePath()),
                 xmlString,
                 java.nio.file.StandardOpenOption.CREATE,
                 java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
             );
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

    private static <T> void serializeToFile(String s, File f) throws JAXBException {

    }
}
