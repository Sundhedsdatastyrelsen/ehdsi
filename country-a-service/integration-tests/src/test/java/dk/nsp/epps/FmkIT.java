package dk.nsp.epps;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.service.PrescriptionService;
import dk.nsp.epps.service.undo.UndoDispensationRepository;
import dk.nsp.epps.testing.shared.Fmk;
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
    @Test
    public void getPrescriptionTest() throws Exception {
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
        assertThat(prescriptions.getPatient().getPerson().getName().getGivenName(), is("Helle"));
    }

    private static String patientId(String cpr) {
        return cpr + "^^^&2.16.17.710.802.1000.990.1.500&ISO";
    }

    private static UndoDispensationRepository undoDispensationRepository() {
        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        // perform db migrations
        Flyway.configure().dataSource(dataSource).load().migrate();
        return new UndoDispensationRepository(dataSource);
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

        var prescriptionService = new PrescriptionService(Fmk.apiClient(), undoDispensationRepository());

        // shouldn't throw:
        prescriptionService.submitDispensation(patientId(cpr),
            eDispensation,
            TestIdentities.apotekerChrisChristoffersen);

        // shouldn't throw:
        prescriptionService.undoDispensation(patientId(cpr), eDispensation, TestIdentities.apotekerChrisChristoffersen);
    }
}
