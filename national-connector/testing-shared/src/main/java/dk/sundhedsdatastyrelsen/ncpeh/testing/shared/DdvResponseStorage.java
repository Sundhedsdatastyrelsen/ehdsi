package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.client.DdvClientIdws;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClientIdws;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardRequestType;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardResponseType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * These methods are concerned with retrieving and serializing responses (vaccinations) from DDV
 * so that they can be used as reliable test data for e.g. CDA generation.
 */
@SuppressWarnings("java:S106")
public class DdvResponseStorage {
    private static final JAXBContext jaxbContext;


    static {
        try {
            jaxbContext = JAXBContext.newInstance(
                ":dk.vaccinationsregister.schemas._2013._12._01"
                    + ":dk.sdsd.ddv.dgws._2012._06"
            );
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    private static final String rawResponseDir = "ddv-responses";
    private static final String testVaccinationsDir = "test-vaccinations";

    @NonNull
    private final DdvClientIdws ddvClient;

    private static final List<String> rawResponseCprs = List.of("0201909309", "0410009234", "1004219992");

    public DdvResponseStorage(@NonNull DdvClientIdws ddvClient) {
        this.ddvClient = ddvClient;
    }

    public static <T> void serializeToFile(JAXBElement<T> obj, File f) throws JAXBException {
        var marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, f);
    }

    /***
     * Copied wholesale from PrescriptionService
     * Not referenced to avoid dependency problems. Should probably be fixed, but this was way easier.
     */
    public GetVaccinationCardResponseType getVaccinationResponse(String cpr, EuropeanHcpIdwsToken caller) throws JAXBException {
        final var request = GetVaccinationCardRequestType.builder()
            .withPersonCivilRegistrationIdentifier(cpr)
            .build();
        return ddvClient.getVaccinationCard(request, caller);
    }

    public JAXBElement<GetVaccinationCardResponseType> createXmlFromVaccination(GetVaccinationCardResponseType response) {
        var fac = new dk.vaccinationsregister.schemas._2013._12._01.ObjectFactory();
        return fac.createGetVaccinationCardResponse(response);
    }

    public static GetVaccinationCardResponseType getTestVaccination(String cpr) throws JAXBException {
        return storedVaccinations(cpr, testVaccinationsDir);
    }

    public static GetVaccinationCardResponseType storedVaccinations(String cpr) throws JAXBException {
        return storedVaccinations(cpr, rawResponseDir);
    }

    public static GetVaccinationCardResponseType storedVaccinations(String cpr, String resourceDir) throws JAXBException {
        var name = "vaccination-card-" + cpr + ".xml";
        return readStoredVaccinations(name, resourceDir);
    }

    public static GetVaccinationCardResponseType readStoredVaccinations(String resourceName, String resourceDir) throws JAXBException {
        var url = DdvResponseStorage.class.getClassLoader()
            .getResource(String.format("%s/%s", resourceDir, resourceName));
        if (url == null) {
            throw new IllegalArgumentException("resourceName does not exist");
        }
        var unmarshaller = jaxbContext.createUnmarshaller();
        var result = (JAXBElement<?>) unmarshaller.unmarshal(url);
        var value = result.getValue();
        if (value instanceof GetVaccinationCardResponseType getVaccinationCardResponseType) {
            return getVaccinationCardResponseType;
        }
        throw new IllegalStateException("File does not contain GetVaccinationCardResponseType data");
    }

    public static GetVaccinationCardResponseType readStoredVaccinations(File f) throws JAXBException {
        var unmarshaller = jaxbContext.createUnmarshaller();
        var result = (JAXBElement<?>) unmarshaller.unmarshal(f);
        var value = result.getValue();
        if (value instanceof GetVaccinationCardResponseType getVaccinationCardResponseType) {
            return getVaccinationCardResponseType;
        }
        throw new IllegalStateException("File does not contain GetVaccinationCardResponseType data");
    }

    /**
     * Download and replace the existing stored FMK responses.
     */
    public static void main(String[] args) throws Exception {
        var drs = new DdvResponseStorage(Ddv.apiClient());

        var dir = Files.createDirectories(
            Path.of("testing-shared", "src", "main", "resources", rawResponseDir));
        for (var cpr : rawResponseCprs) {
            var token = Sosi.getToken(Sosi.TokenArgs.builder()
                .audience(Sosi.Audience.DDV)
                .healthProfessionalRole("221")
                .patientCpr(cpr)
                .build());
            var mcf = dir.resolve("vaccination-card-" + cpr + ".xml").toFile();
            var vaccinationCard = drs.getVaccinationResponse(cpr, token);
            serializeToFile(drs.createXmlFromVaccination(vaccinationCard), mcf);
            System.out.printf("Wrote vaccination-card to %s%n", mcf.getAbsolutePath());

        }
    }
}
