package dk.nsp.epps;

import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.test.FmkResponseStorage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static dk.nsp.epps.test.FmkResponseStorage.storedPrescriptions;

public class MainTestCoordinator
{
    private static final File storageDir = new File("integration-tests/src/test/resources/test-file-storage");
    private static final List<String> testCprs = List.of("1111111118", "0101010000", "0201909309");
    private static String getFmkFileName(String cpr) {return "get-prescription-" + cpr + ".xml";}
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

    private static <T> void serializeToFile(JAXBElement<T> obj, File f, JAXBContext context) throws JAXBException {
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, f);
    }
}
