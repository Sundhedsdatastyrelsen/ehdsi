package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL1;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.DdvResponseStorage;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PatientSummaryL1GeneratorTest {
    private static final String BASE_ID = "test-document-id";
    private static final String CPR = "0410009234";

    @Test
    void generateTest() throws JAXBException {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);

        Assertions.assertNotNull(cda);
    }

    @Test
    void generatedXmlIsWellFormed() throws Exception {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);
        var documentBuilder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder();

        Assertions.assertDoesNotThrow(() ->
            documentBuilder.parse(new ByteArrayInputStream(cda.getBytes(StandardCharsets.UTF_8)))
        );
    }

    @Test
    void documentIdHasL1Suffix() throws JAXBException {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat(
            "document id root matches repository OID",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:id/@root", generatedCda),
            is(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value));
        assertThat(
            "document id extension has L1 suffix",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:id/@extension", generatedCda),
            endsWith("L1"));
    }

    @Test
    void relatedDocumentIdHasL3Suffix() throws JAXBException {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat(
            "related document id extension has L3 suffix",
            xpathEngine.evaluate(
                "/hl7:ClinicalDocument/hl7:relatedDocument/hl7:parentDocument/hl7:id/@extension",
                generatedCda),
            endsWith("L3"));
    }

    @Test
    void nonXmlBodyContainsPdfContent() throws JAXBException {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat(
            "nonXMLBody has pdf mediaType",
            xpathEngine.evaluate(
                "/hl7:ClinicalDocument/hl7:component/hl7:nonXMLBody/hl7:text/@mediaType",
                generatedCda),
            is("application/pdf"));
        assertThat(
            "nonXMLBody text content is non-empty",
            xpathEngine.evaluate(
                "/hl7:ClinicalDocument/hl7:component/hl7:nonXMLBody/hl7:text",
                generatedCda),
            not(emptyString()));
    }

    @Test
    void patientDataIsPresent() throws JAXBException {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat(
            "patient birth time matches",
            xpathEngine.evaluate(
                "/hl7:ClinicalDocument/hl7:recordTarget//hl7:birthTime/@value",
                generatedCda),
            is("19821103"));
        assertThat(
            "patient family name is correct",
            xpathEngine.evaluate(
                "/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:family",
                generatedCda),
            is("Andersen"));
    }

    @Test
    void generateFromInputTest() throws JAXBException {
        var cpr = "0410009234";
        var rootedDocumentId = Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value + "^"
            + DocumentIdMapper.level1DocumentId(BASE_ID);
        var patient = Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .code("M")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();

        var medicationSummary = FmkResponseStorage.getTestMedicineCards(cpr);
        var immunization = DdvResponseStorage.getTestVaccination(cpr);
        var input = new PatientSummaryL3Input(rootedDocumentId, null, patient, medicationSummary, immunization);
        var cda = PatientSummaryL1Generator.generate(input);

        Assertions.assertNotNull(cda);
    }

    @Test
    void noPreferredHpTest() throws JAXBException {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);

        Assertions.assertTrue(cda.contains("nonXMLBody"));
    }

    @Test
    void withPreferredHpTest() throws JAXBException {
        var model = buildL1Model(preferredHp("DK"));
        var cda = PatientSummaryL1Generator.generate(model);

        Assertions.assertNotNull(cda);
    }

    private static PatientSummaryL1 buildL1Model(
        PreferredHealthProfessional preferredHp
    ) throws JAXBException {
        var l3Model = buildL3Model(preferredHp);

        var pdfModel = PatientSummaryPdfMapper.map(l3Model);
        var pdf = PatientSummaryPdfGenerator.generate(pdfModel);
        var relatedL3DocumentId = new CdaId(
            Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID,
            DocumentIdMapper.level3DocumentId(BASE_ID));

        return PatientSummaryL1.builder()
            .modelData(l3Model)
            .base64EncodedDocument(Base64.getEncoder().encodeToString(pdf))
            .relatedL3DocumentId(relatedL3DocumentId)
            .build();
    }

    private static PatientSummaryL3 buildL3Model(
        PreferredHealthProfessional preferredHp
    ) throws JAXBException {
        var patient = patient("DK");
        var medicationSummary = FmkResponseStorage.getTestMedicineCards(CPR);
        var input = new PatientSummaryL3Input(
            BASE_ID,
            preferredHp,
            patient,
            medicationSummary,
            null);

        return PatientSummaryL3Mapper.model(input);
    }

    private static Patient patient(String country) {
        return Patient.builder()
            .id(new CdaId(Oid.DK_CPR, CPR))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .address(new Address(
                List.of("Overgaden Oven Vandet 10", "1."),
                "København K",
                "1415",
                country))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .codeSystemVersion("913-20091020")
                .code("M")
                .displayName("Male")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();
    }

    private static PreferredHealthProfessional preferredHp(String country) {
        return PreferredHealthProfessional.builder()
            .name(Name.fromFullName("Tycho Brahe"))
            .telecoms(List.of(Telecom.builder()
                .use(Telecom.Use.WORK_PLACE)
                .value("tel:+4511111111")
                .build()))
            .address(new Address(
                List.of("Rundetårn", "Købmagergade 52A", "Kælderen"),
                "København K",
                "1150",
                country))
            .build();
    }
}
