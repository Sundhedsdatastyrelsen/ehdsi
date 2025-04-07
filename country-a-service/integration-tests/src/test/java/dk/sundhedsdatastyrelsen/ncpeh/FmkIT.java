package dk.sundhedsdatastyrelsen.ncpeh;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.mocks.AuthorizationRegistryClientMock;
import dk.sundhedsdatastyrelsen.ncpeh.mocks.EPrescriptionMapperServiceMock;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.LmsDataLookupService;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import org.apache.commons.lang3.tuple.Pair;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.io.FileMatchers.aReadableFile;

public class FmkIT {
    private static final PrescriptionService PRESCRIPTION_SERVICE = new PrescriptionService(Fmk.apiClient(), undoDispensationRepository(), ePrescriptionMappingService(), authorizationRegistryClient());

    /**
     * This test simply checks that we can connect and get an answer on the data.
     *
     * @throws Exception
     */
    @Test
    void getPrescriptionAndMedicationTest() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier()
            .withSource("CPR")
            .withValue(Fmk.cprHelleReadOnly)
            .end()
            .withIncludeOpenPrescriptions()
            .end()
            .build();

        var prescriptions = Fmk.apiClient()
            .getPrescription(getPrescriptionRequest, TestIdentities.apotekerJeppeMoeller);

        var validPrescriptions = PrescriptionService.PrescriptionFilter.none()
            .validPrescriptionIndexes(prescriptions.getPrescription())
            .map(Pair::getRight);

        var drugMedicationIds = validPrescriptions
            .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
            .toList();

        var drugMedications = PRESCRIPTION_SERVICE.getDrugMedicationResponse(Fmk.cprHelleReadOnly, drugMedicationIds, TestIdentities.apotekerJeppeMoeller);
        assertThat(prescriptions.getPatient().getPerson().getName().getGivenName(), is("Helle"));
        assertThat(drugMedications.getPersonIdentifier().getValue(), is(Fmk.cprHelleReadOnly));
    }

    @Test
    void getValidDrugMedications() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier()
            .withSource("CPR")
            .withValue(Fmk.cprKarl)
            .end()
            .withIncludeOpenPrescriptions()
            .end()
            .build();

        var prescriptions = Fmk.apiClient()
            .getPrescription(getPrescriptionRequest, TestIdentities.apotekerJeppeMoeller);

        var validPrescriptions = PrescriptionService.PrescriptionFilter.none()
            .validPrescriptionIndexes(prescriptions.getPrescription())
            .map(Pair::getRight)
            .toList();

        var drugMedicationIds = validPrescriptions.stream()
            .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
            .toList();

        var drugMedications = PRESCRIPTION_SERVICE.getDrugMedicationResponse(Fmk.cprKarl, drugMedicationIds, TestIdentities.apotekerJeppeMoeller);
        assertThat(validPrescriptions.size(), is(drugMedications.getDrugMedication().size()));
    }

    private static String patientId(String cpr) {
        return String.format("%s^^^&%s&ISO", cpr, Oid.DK_CPR.value);
    }

    private static UndoDispensationRepository undoDispensationRepository() {
        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        // perform db migrations
        Flyway.configure().dataSource(dataSource).load().migrate();
        return new UndoDispensationRepository(dataSource);
    }

    private static LmsDataLookupService ePrescriptionMappingService() {
        return new EPrescriptionMapperServiceMock();
    }

    private static AuthorizationRegistryClient authorizationRegistryClient() {
        return new AuthorizationRegistryClientMock();
    }

    /**
     * The dispensation test case is complex because:
     * - There has to be a valid prescription in the test environment to dispense for.
     * - We need a valid eDispensation CDA input document which references the prescription.
     * So this test can only run if the prerequisite scripts have run, and an eDispensation CDA
     * is available at the path given by the system property eDispensationITPath.
     */
    @Test
    public void submitDispensationTest() throws Exception {
        var cpr = Fmk.cprKarl;
        var eDispensationRawPath = System.getProperty("eDispensationITPath");
        assertThat(
            "Java system property eDispensationITPath must be set",
            eDispensationRawPath,
            is(not(anyOf(nullValue(), blankString()))));
        var eDispensationPath = Path.of(eDispensationRawPath);
        assertThat(
            "Cannot find eDispensation CDA at " + eDispensationPath,
            eDispensationPath.toFile(),
            is(aReadableFile()));
        var eDispensation = Utils.readXmlDocument(Files.newInputStream(eDispensationPath));

        var prescriptionService = new PrescriptionService(Fmk.apiClient(), undoDispensationRepository(), ePrescriptionMappingService(), authorizationRegistryClient());

        // shouldn't throw:
        prescriptionService.submitDispensation(
            patientId(cpr),
            eDispensation,
            TestIdentities.apotekerChrisChristoffersen);

        // shouldn't throw:
        prescriptionService.undoDispensation(patientId(cpr), eDispensation, TestIdentities.apotekerChrisChristoffersen);

        // we perform the dispensation again to clean up after ourselves:
        prescriptionService.submitDispensation(
            patientId(cpr),
            eDispensation,
            TestIdentities.apotekerChrisChristoffersen);
    }
}
