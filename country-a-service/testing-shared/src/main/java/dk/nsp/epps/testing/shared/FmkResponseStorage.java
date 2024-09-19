package dk.nsp.epps.testing.shared;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.Identities;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.NonNull;

import java.io.File;
import java.util.List;

/**
 * These methods are concerned with retrieving and serializing responses (ePrescriptions) from FMK
 * so that they can be used as reliable test data for e.g. CDA generation.
 */
public class FmkResponseStorage {
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

    private static final String resourceDir = "fmk-responses";

    @NonNull private final FmkClient fmkClient;

    private static final List<String> testCprs = List.of("1111111118", "0101010000", "0201909309");

    public static List<String> testCprs() {
        return testCprs;
    }

    public FmkResponseStorage(@NonNull FmkClient fmkClient) {
        this.fmkClient = fmkClient;
    }

    private static <T> void serializeToFile(JAXBElement<T> obj, File f) throws JAXBException {
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
        if (url == null) {
            throw new IllegalArgumentException("resourceName does not exist");
        }
        var unmarshaller = jaxbContext.createUnmarshaller();
        var result = (JAXBElement<?>) unmarshaller.unmarshal(url);
        var value = result.getValue();
        if (value instanceof GetPrescriptionResponseType) {
            return (GetPrescriptionResponseType) value;
        }
        throw new RuntimeException("File does not contain GetPrescriptionResponseType data");
    }

    private static File storageDir() {
        final var storageDir = new File("testing-shared/src/main/resources/" + resourceDir);
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new RuntimeException("Could not create dir: " + storageDir.getAbsolutePath());
            }
        }
        return storageDir;
    }

    /**
     * Download and replace the existing stored FMK responses.
     */
    public static void main(String[] args) throws Exception {
        var frs = new FmkResponseStorage(Fmk.apiClient());
        for (var cpr : testCprs) {
            var f = new File(storageDir(), "get-prescription-" + cpr + ".xml");
            serializeToFile(frs.openPrescriptionsForCpr(cpr), f);
            System.out.println("Wrote prescriptions to " + f.getAbsolutePath());
        }
    }
}
