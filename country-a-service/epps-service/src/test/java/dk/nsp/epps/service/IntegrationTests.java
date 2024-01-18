package dk.nsp.epps.service;

import dk.nsp.epps.Utils;
import dk.nsp.epps.service.client.FmkClient;
import dk.nsp.epps.service.client.SosiClient;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Integration tests for FMK and CPR.
 * To be run manually (for now) because they require external dependencies (including NSP STS).
 */
@Ignore("Requires external dependencies")
public class IntegrationTests {
    /// Configuration:
    private static final String fmkEndpointUrl = "http://localhost:8080/ws/";
    private static final String stsUri = "http://test2.ekstern-test.nspop.dk:8080/sts/services/NewSecurityTokenService";
    private static final Map<String, String> keystore = Map.of(
        "path", "epps-test-cert.p12",
        "password", System.getenv("EPPS_TEST_CERT_PASSWORD"),
        "alias", "epps - nsp testmiljø - 3/11/2026"
    );

    private static FmkClient fmkClient;
    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory medCardFac =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();
    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory medCardE2Fac =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory();

    @BeforeAll
    public static void setup() throws Exception {
        var sosiClient = new SosiClient(stsUri, keystore.get("path"), keystore.get("password"), keystore.get("alias"));
        fmkClient = new FmkClient(fmkEndpointUrl, sosiClient);
    }

    @Test
    public void fmkGetPrescription() throws Exception {
        var getPrescriptionRequest = medCardFac.createGetPrescriptionRequestType();
        getPrescriptionRequest.setPersonIdentifier(Utils.apply(medCardFac.createPersonIdentifierType(), pi -> {
            pi.setSource("CPR");
            pi.setValue("1111111118");
        }));
        var prescriptions = fmkClient.getPrescription(getPrescriptionRequest);
        Assertions.assertEquals("Primcillin", prescriptions.getPrescription().get(0).getDrug().getName());
    }

    @Test
    public void fmkStartEffectuation() throws Exception {
        var effectuationRequest = medCardFac.createStartEffectuationRequestType();
        effectuationRequest.setPersonIdentifier(Utils.apply(medCardFac.createPersonIdentifierType(), pi -> {
            pi.setSource("CPR");
            pi.setValue("1111111118");
        }));
        var startEffectuation = fmkClient.startEffectuation(effectuationRequest);
        Assertions.assertEquals("Primcillin", startEffectuation.getPrescription().get(0).getDrug().getName());
    }

    @Test
    public void fmkCreateEffectuation() throws Exception {
        var effectuationRequest = medCardFac.createStartEffectuationRequestType();
        effectuationRequest.setPersonIdentifier(Utils.apply(medCardFac.createPersonIdentifierType(), pi -> {
            pi.setSource("CPR");
            pi.setValue("1111111118");
        }));
        var startEffectuation = fmkClient.startEffectuation(effectuationRequest);

        var createdBy = medCardFac.createModificatorType(); // TODO
        var personIdentifier = medCardFac.createPersonIdentifierType(); // TODO
        var prescription = Utils.apply(medCardE2Fac.createCreatePharmacyEffectuationOnPrescriptionType(), x ->
            x.setPrescriptionIdentifier(startEffectuation.getPrescription().get(0).getIdentifier())); // TODO
        var request = medCardE2Fac.createCreatePharmacyEffectuationRequestType();
        request.setCreatedBy(createdBy);
        request.setPersonIdentifier(personIdentifier);
        request.getPrescription().add(prescription);

        var effectuation = fmkClient.createPharmacyEffectuation(request);

        Assertions.assertEquals(effectuation.getEffectuation().get(0).getEffectuationIdentifier(), 1341404078102001010L);
    }
}
