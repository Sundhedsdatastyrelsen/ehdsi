package dk.nsp.epps;

import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PrescriptionErrorType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.service.PrescriptionService;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import dk.nsp.epps.testing.shared.Fmk;
import dk.sds.ncp.cda.MapperException;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
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

    private static StartEffectuationResponseType startEffectuation(Document eDispensation, String cpr)
        throws MapperException, JAXBException {
        var dispensationMapper = new DispensationMapper();
        var effectuationRequest = dispensationMapper.startEffectuationRequest(patientId(cpr), eDispensation);

        return Fmk.apiClient().startEffectuation(effectuationRequest, TestIdentities.apotekerChrisChristoffersen);

    }

    private static CreatePharmacyEffectuationResponseType createPharmacyEffectuation(
        Document eDispensation,
        String cpr,
        StartEffectuationResponseType startEffectuationResponse
    ) throws MapperException, JAXBException {
        var dispensationMapper = new DispensationMapper();

        return Fmk.apiClient().createPharmacyEffectuation(
            dispensationMapper.createPharmacyEffectuationRequest(
                patientId(cpr),
                eDispensation,
                startEffectuationResponse),
            TestIdentities.apotekerChrisChristoffersen);
    }

    private static UndoEffectuationResponseType undoEffectuation(Document eDispensation, String cpr)
        throws JAXBException, MapperException {
        var patientId = cpr + "^^^&2.16.17.710.802.1000.990.1.500&ISO";
        var service = new PrescriptionService(Fmk.apiClient(), new EPrescriptionMapper(""), new DispensationMapper());
        return service.undoDispensation(patientId, eDispensation);
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

        var startEffectuationResponse = startEffectuation(eDispensation, cpr);

        var prettyFailedMessages = startEffectuationResponse.getStartEffectuationFailed().stream()
            .map(PrescriptionErrorType::getReasonText).toList();
        assertThat("startEffectuation failures", prettyFailedMessages, is(empty()));
        assertThat(startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst(), notNullValue());
        assertThat(startEffectuationResponse.getPrescription().getFirst().getPackageRestriction(), notNullValue());

        var createPharmacyEffectuationResult = createPharmacyEffectuation(
            eDispensation,
            cpr,
            startEffectuationResponse);

        assertThat(createPharmacyEffectuationResult.getEffectuation(), not(empty()));

        var undoResponse = undoEffectuation(eDispensation, cpr);
        assertThat(undoResponse.getPersonIdentifier().getValue(), is(cpr));
    }
}
