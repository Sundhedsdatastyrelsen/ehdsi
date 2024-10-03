package dk.nsp.epps;

import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PrescriptionErrorType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.service.mapping.DispensationMapper;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FmkIT {
    /**
     * Helle Bonde is a test persona which we do *not* own, so we should only perform read operations on her.
     */
    private static final String cprHelle = "1111111118";
    /**
     * Karl Læge ePPS is a test persona which we own, so we can perform read and write operations on him.
     */
    private static final String cprKarl = "0201909309";

    @Test
    public void getPrescriptionTest() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier()
            .withSource("CPR")
            .withValue(cprHelle)
            .end()
            .withIncludeOpenPrescriptions()
            .end()
            .build();

        var prescriptions = Fmk.apiClient()
            .getPrescription(getPrescriptionRequest, TestIdentities.apotekerJeppeMoeller);
        assertEquals("Helle", prescriptions.getPatient().getPerson().getName().getGivenName());
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


    /**
     * The dispensation test case is complex because
     * - There has to be a valid prescription in the test environment to dispense for.
     * - We need a valid eDispensation CDA input document which references the prescription.
     * So this test can only run if the prerequisite scripts have run, and an eDispensation CDA
     * is available at ...TODO...
     */
    @Test
    public void submitDispensationTest() throws Exception {
        var eDispensationRawPath = System.getProperty("testing.edispensation.path");
        assertThat(
            "Java system property testing.edispensation.path must be set",
            eDispensationRawPath,
            is(not(anyOf(nullValue(), blankString()))));
        var eDispensationPath = Path.of(eDispensationRawPath);
        assertThat(
            "Cannot find eDispensation CDA at " + eDispensationPath,
            eDispensationPath.toFile(),
            is(aReadableFile()));
        var eDispensation = Utils.readXmlDocument(Files.newInputStream(eDispensationPath));

        var startEffectuationResponse = startEffectuation(eDispensation, cprKarl);

        var prettyFailedMessages = startEffectuationResponse.getStartEffectuationFailed().stream()
            .map(PrescriptionErrorType::getReasonText).toList();
        assertThat("startEffectuation failures", prettyFailedMessages, is(empty()));
        assertThat(startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst(), notNullValue());
        assertThat(startEffectuationResponse.getPrescription().getFirst().getPackageRestriction(), notNullValue());

        var createPharmacyEffectuationResult = createPharmacyEffectuation(
            eDispensation,
            cprKarl,
            startEffectuationResponse);

        assertThat(createPharmacyEffectuationResult.getEffectuation(), not(empty()));
    }
}
