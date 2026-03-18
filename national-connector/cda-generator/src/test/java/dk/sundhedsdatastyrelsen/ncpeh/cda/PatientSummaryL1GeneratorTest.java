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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PatientSummaryL1GeneratorTest {
    private static final String BASE_ID = "test-document-id";

    private static PatientSummaryL3 buildL3Model(PreferredHealthProfessional preferredHp) {
        var patient = Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .address(new Address(List.of("Overgaden Oven Vandet 10", "1."), "København K", "1415", "DK"))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .codeSystemVersion("913-20091020")
                .code("M")
                .displayName("Male")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();
        return PatientSummaryL3.builder()
            .documentId(new CdaId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID,
                PatientSummaryDocumentIdMapper.level1DocumentId(BASE_ID)))
            .effectiveTime(OffsetDateTime.now())
            .title("Patient Summary L1 Test")
            .patient(patient)
            .preferredHp(preferredHp)
            .build();
    }

    private static PatientSummaryL1 buildL1Model(PreferredHealthProfessional preferredHp) {
        var l3Model = buildL3Model(preferredHp);
        var pdf = PatientSummaryPdfGenerator.generate(l3Model);
        var relatedL3DocumentId = new CdaId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID,
            PatientSummaryDocumentIdMapper.level3DocumentId(BASE_ID));
        return PatientSummaryL1.builder()
            .modelData(l3Model)
            .base64EncodedDocument(Base64.getEncoder().encodeToString(pdf))
            .relatedL3DocumentId(relatedL3DocumentId)
            .build();
    }

    @Test
    void generateTest() {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);
        Assertions.assertNotNull(cda);
    }

    @Test
    void generatedXmlIsWellFormed() throws Exception {
        var cda = PatientSummaryL1Generator.generate(buildL1Model(null));
        var documentBuilder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder();
        Assertions.assertDoesNotThrow(() ->
            documentBuilder.parse(new ByteArrayInputStream(cda.getBytes(StandardCharsets.UTF_8)))
        );
    }

    @Test
    void documentIdHasL1Suffix() {
        var cda = PatientSummaryL1Generator.generate(buildL1Model(null));

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat("document id root matches repository OID",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:id/@root", generatedCda),
            is(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value));
        assertThat("document id extension has L1 suffix",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:id/@extension", generatedCda),
            endsWith("L1"));
    }

    @Test
    void relatedDocumentIdHasL3Suffix() {
        var cda = PatientSummaryL1Generator.generate(buildL1Model(null));

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat("related document id extension has L3 suffix",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:relatedDocument/hl7:parentDocument/hl7:id/@extension", generatedCda),
            endsWith("L3"));
    }

    @Test
    void nonXmlBodyContainsPdfContent() {
        var cda = PatientSummaryL1Generator.generate(buildL1Model(null));

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat("nonXMLBody has pdf mediaType",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:component/hl7:nonXMLBody/hl7:text/@mediaType", generatedCda),
            is("application/pdf"));
        assertThat("nonXMLBody text content is non-empty",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:component/hl7:nonXMLBody/hl7:text", generatedCda),
            not(emptyString()));
    }

    @Test
    void patientDataIsPresent() {
        var cda = PatientSummaryL1Generator.generate(buildL1Model(null));

        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        assertThat("patient birth time matches",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:recordTarget//hl7:birthTime/@value", generatedCda),
            is("19821103"));
        assertThat("patient family name is correct",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:family", generatedCda),
            is("Andersen"));
    }

    @Test
    void generateFromInputTest() {
        var rootedDocumentId = Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value + "^"
            + PatientSummaryDocumentIdMapper.level1DocumentId(BASE_ID);
        var patient = Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .code("M")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();
        var input = new PatientSummaryInput(rootedDocumentId, null, patient);
        var cda = PatientSummaryL1Generator.generate(input);
        Assertions.assertNotNull(cda);
    }

    @Test
    void noPreferredHpTest() {
        var model = buildL1Model(null);
        var cda = PatientSummaryL1Generator.generate(model);
        Assertions.assertNotNull(cda);
    }

    @Test
    void withPreferredHpTest() {
        var preferredHp = PreferredHealthProfessional.builder()
            .name(Name.fromFullName("Tycho Brahe"))
            .telecoms(List.of(Telecom.builder().use(Telecom.Use.WORK_PLACE).value("tel:+4511111111").build()))
            .address(new Address(List.of("Rundetårn", "Købmagergade 52A"), "København K", "1150", "DK"))
            .build();
        var model = buildL1Model(preferredHp);
        var cda = PatientSummaryL1Generator.generate(model);
        Assertions.assertNotNull(cda);
    }
}
