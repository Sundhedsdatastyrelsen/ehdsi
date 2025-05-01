package dk.sundhedsdatastyrelsen.ncpeh;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.FtpConnection;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.LocalLmsLoader;
import dk.sundhedsdatastyrelsen.ncpeh.mocks.AuthorizationRegistryClientMock;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import org.apache.commons.lang3.tuple.Pair;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.io.FileMatchers.aReadableFile;

class FmkIT {
    private final DataSource lmsDataSource = lmsDataSource();

    private final PrescriptionService prescriptionService = new PrescriptionService(
        Fmk.apiClient(),
        undoDispensationRepository(),
        lmsDataSource,
        authorizationRegistryClient());

    private static DataSource lmsDataSource() {
        return new SingleConnectionDataSource("jdbc:sqlite:./local-lms-db-it.sqlite", true);
    }

    @BeforeAll
    static void initialiseLmsData() throws SQLException, IOException {
        var ds = lmsDataSource();
        if (new DataProvider(ds).lastImport().isEmpty()) {
            LocalLmsLoader.fetchData(lmsServerInfo(), ds);
        }
    }

    private static FtpConnection.ServerInfo lmsServerInfo() {
        var user = Objects.requireNonNull(System.getenv("LMSFTP_USERNAME"), "envvar LMSFTP_USERNAME is not set");
        var password = Objects.requireNonNull(System.getenv("LMSFTP_PASSWORD"), "envvar LMSFTP_PASSWORD is not set");
        return new FtpConnection.ServerInfo("ftp.medicinpriser.dk", 21, user, password);
    }

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

        var drugMedications = prescriptionService.getDrugMedicationResponse(Fmk.cprHelleReadOnly, drugMedicationIds, TestIdentities.apotekerJeppeMoeller);
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

        var drugMedications = prescriptionService.getDrugMedicationResponse(Fmk.cprKarl, drugMedicationIds, TestIdentities.apotekerJeppeMoeller);
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
    void submitDispensationTest() throws Exception {
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
