package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.service.client.FmkClient;
import dk.nsp.epps.service.client.Identities;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

/**
 * These methods are concerned with retrieving and serializing responses (ePrescriptions) from FMK
 * so that they can be used as reliable test data for e.g. CDA generation.
 */
@Disabled("To be executed on demand")
public class FmkResponseStorage {
    static final JAXBContext jaxbContext;

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

    private static final File storageDir = new File("epps-service/src/test/resources/fmk-responses");
    private static final String resourceDir = "fmk-responses";

    static {
        assert storageDir.exists() || storageDir.mkdirs() : "error when creating dir: " + storageDir.getAbsolutePath();
    }

    private static FmkClient fmkClient;

    public static List<String> testCprs = List.of("1111111118", "0101010000", "0201909309");


    @BeforeAll
    public static void setup() throws Exception {
        fmkClient = new FmkClient(IntegrationTests.fmkEndpointUri);
    }

    private <T> void serializeToFile(JAXBElement<T> obj, File f) throws JAXBException {
        var marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, f);
    }

    private JAXBElement<GetPrescriptionResponseType> openPrescriptionsForCpr(String cpr) throws JAXBException {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();

        GetPrescriptionResponseType prescriptions = fmkClient.getPrescription(
            getPrescriptionRequest,
            Identities.apotekerChrisChristoffersen
        );
        var fac = new dk.dkma.medicinecard.xml_schema._2015._06._01.e6.ObjectFactory();
        return fac.createGetPrescriptionResponse(prescriptions);
    }

    public static GetPrescriptionResponseType storedPrescriptions(String cpr) throws JAXBException {
        var name = "get-prescription-" + cpr + ".xml";
        return readStoredPrescriptions(name);
    }

    public static GetPrescriptionResponseType readStoredPrescriptions(String resourceName) throws JAXBException {
        var url = FmkResponseStorage.class.getClassLoader()
            .getResource(String.format("%s/%s", resourceDir, resourceName));
        var unmarshaller = jaxbContext.createUnmarshaller();
        var result = (JAXBElement<?>) unmarshaller.unmarshal(url);
        var value = result.getValue();
        if (value instanceof GetPrescriptionResponseType) {
            return (GetPrescriptionResponseType) value;
        }
        throw new RuntimeException("File does not contain GetPrescriptionResponseType data");
    }

    @Test
    void storePrescriptions() throws JAXBException {
        for (var cpr : testCprs) {
            var f = new File(storageDir, "get-prescription-" + cpr + ".xml");
            serializeToFile(openPrescriptionsForCpr(cpr), f);
            System.out.println("Wrote prescriptions to " + f.getAbsolutePath());
        }
    }

    @Test
    void validateStoredPrescriptions() throws JAXBException {
        for (var cpr : testCprs) {
            var ps = storedPrescriptions(cpr);
            Assert.notNull(ps, "stored prescription is null");
        }
    }
}
