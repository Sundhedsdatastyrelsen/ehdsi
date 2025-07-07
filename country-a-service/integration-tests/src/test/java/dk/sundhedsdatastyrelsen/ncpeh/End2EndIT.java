package dk.sundhedsdatastyrelsen.ncpeh;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.script.FmkPrescriptionCreator;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@Slf4j
class End2EndIT {
    static final String HOST_ENV_NAME = "E2E_TRAINING_HOST";
    static final String HOST = System.getenv(HOST_ENV_NAME);
    static final String TEST_CPR = "2401933053";
    static final ObjectMapper mapper = new ObjectMapper();

    /// See the resources/portal-backend-examples folder for examples of the requests and responses.
    @Test
    @Disabled("HBG 2025-07-07: Fails every night. Fix test before re-enabling.")
    void fetchAndDispenseThroughCountryB() throws Exception {
        // Setup
        assertThat(String.format("Missing environment variable \"%s\"", HOST_ENV_NAME), HOST, is(not(nullValue())));
        // Fetch the existing prescriptions, so we know if we need to create one, and to be able to check later that
        // it's dispensed.
        var existingPrescriptions = fetchFmkPrescriptions(TEST_CPR);
        log.info("Fetched {} prescriptions.", existingPrescriptions.getPrescription().size());
        var prescriptionId = fetchOrCreatePrescriptionId(existingPrescriptions, TEST_CPR);
        log.info("Prescription id: {}.", prescriptionId);

        // Test
        var patient = fetchCountryBPatient(TEST_CPR);
        log.info("Fetched patient {} {}.", patient.get("firstName"), patient.get("familyName"));
        var patientIdentifier = patient.at("/identifier").textValue();
        var prescriptions = fetchCountryBPrescriptions(patientIdentifier);
        log.info("Found {} prescriptions in country B", prescriptions.get("clinicalDocuments").size());
        assertThat(
            prescriptions.at("/clinicalDocuments/0/identifier")
                .textValue(), is(equalTo(prescriptionId + "L1")));

        var l1Prescription = fetchCountryBSinglePrescription(patientIdentifier, prescriptionId + "L1");
        log.info("Fetched L1 prescription.");
        assertThat(l1Prescription.get("content").textValue(), is(not(emptyOrNullString())));

        var l3Prescription = fetchCountryBSinglePrescription(patientIdentifier, prescriptionId + "L3");
        log.info("Fetched L3 prescription.");
        var dispensation = dispenseThroughCountryB(l3Prescription, patient);

        // Validate

        assertThat(
            dispensation.get("status").textValue(),
            is(equalTo("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success")));

        try {
            var updatedPrescriptionList = fetchCountryBPrescriptions(patientIdentifier);
            // If we could fetch, check that there are two fewer clinical docs than before (L1 and L3)
            assertThat(
                updatedPrescriptionList.get("clinicalDocuments").size(),
                is(prescriptions.get("clinicalDocuments").size() - 2));
        } catch (Exception e) {
            // They throw an exception if there are no prescriptions.
        }

        var updatedFmkPrescriptions = fetchFmkPrescriptions(TEST_CPR);
        assertThat(
            updatedFmkPrescriptions.getPrescription().size(),
            is(Math.max(existingPrescriptions.getPrescription().size() - 1, 0)));

        log.info("Dispensed successfully.");
    }

    /// This is used in order to not create new prescriptions if there is already one open, and in order to check that
    /// the dispensation actually closes the prescription on the DK side.
    static GetPrescriptionResponseType fetchFmkPrescriptions(String cpr) throws JAXBException {
        return Fmk.apiClient()
            .getPrescription(
                GetPrescriptionRequestType.builder()
                    .withPersonIdentifier()
                    .withSource("CPR")
                    .withValue(cpr)
                    .end()
                    .withIncludeOpenPrescriptions()
                    .end()
                    .build(),
                TestIdentities.apotekerChrisChristoffersen);
    }

    static String fetchOrCreatePrescriptionId(GetPrescriptionResponseType existingPrescriptions, String cpr) throws Exception {
        var existingPrescriptionId = existingPrescriptions
            .getPrescription()
            .stream()
            .findFirst()
            .map(PrescriptionType::getIdentifier)
            .map(id -> Long.toString(id));

        if (existingPrescriptionId.isPresent()) {
            return existingPrescriptionId.get();
        }

        return Long.toString(FmkPrescriptionCreator.createNewPrecriptionForCpr(cpr)
            .getPrescription()
            .getFirst()
            .getPrescriptionIdentifier()
            .getFirst());
    }

    static JsonNode fetchCountryBPatient(String cpr) throws IOException, InterruptedException {
        var jsonBody = mapper.readTree("""
            {"countryCode":"DK","patientIdentityTrait":{"livingSubjectIds":[{"extension":"?","root":"1.2.208.176.1.2"}]}}""");
        jsonBody.withObject("/patientIdentityTrait/livingSubjectIds/0").put("extension", cpr);
        return fetch("/openncp-portal-backend/api/patient", mapper.writeValueAsString(jsonBody));
    }

    static JsonNode fetchCountryBPrescriptions(String patientIdentifier) throws IOException, InterruptedException {
        var jsonBody = mapper.readTree("""
            {"nextOfKin":false,"countryCode":"DK","patientIdentifier":"?","purposeOfUse":"TREATMENT"}""");
        jsonBody.withObject("").put("patientIdentifier", patientIdentifier);
        return fetch("/openncp-portal-backend/api/ep", mapper.writeValueAsString(jsonBody));
    }

    static JsonNode fetchCountryBSinglePrescription(String patientIdentifier, String documentIdentifier) throws IOException, InterruptedException {
        var jsonBody = mapper.readTree("""
            {"nextOfKin":false,"countryCode":"DK","patientIdentifier":"?","purposeOfUse":"TREATMENT","repositoryId":"1.2.208","documentIdentifier":"?","homeCommunityId":"urn:oid:1.2.208"}""");
        jsonBody.withObject("")
            .put("patientIdentifier", patientIdentifier)
            .put("documentIdentifier", documentIdentifier);
        return fetch("/openncp-portal-backend/api/ep/retrieve", mapper.writeValueAsString(jsonBody));
    }

    static JsonNode dispenseThroughCountryB(JsonNode prescription, JsonNode patient) throws IOException, InterruptedException {
        var jsonBody = mapper.readTree("""
            {
              "numberOfPackage":"1",
              "productName":"Loette 28",
              "countryCode":"DK",
              "substitution":false,
              "packageSize":{"quantity":null,"packageSizeL1":"84","packageSizeL2":null,"packageSizeL3":null},
              "partPackageSizes":[],
              "nextOfKin":false,
              "purposeOfUse":"TREATMENT",
              "repositoryId":"1.2.208",
              "homeCommunityId":"urn:oid:1.2.208"
            }""");
        jsonBody.withObject("").set("clinicalDocumentContent", prescription);
        jsonBody.withObject("").put("prescriptionId", "");
        jsonBody.withObject("").set("patientDemographics", patient.get("patientDemographics"));
        jsonBody.withObject("").put("patientIdentifier", patient.get("identifier").textValue());
        jsonBody.withObject("").put("documentIdentifier", prescription.get("identifier").textValue());
        // The prescription id is the same as the document id without the last 2 digits
        jsonBody.withObject("")
            .put("prescriptionId", prescription.get("identifier").textValue().replaceFirst("..$", ""));
        return fetch("/openncp-portal-backend/api/ep/dispense", mapper.writeValueAsString(jsonBody));
    }

    static JsonNode fetch(String url, String body) throws IOException, InterruptedException {
        var uri = URI.create(HOST + url);
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest
                .newBuilder()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
            var res = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 400) {
                throw new RuntimeException(String.format("Error when fetching.%nUrl: %s.%nRequest body: %s.%nCode: %d.%nResponse body: %s.", url, body, res.statusCode(), res.body()));
            }
            return mapper.readTree(res.body());
        }
    }
}
