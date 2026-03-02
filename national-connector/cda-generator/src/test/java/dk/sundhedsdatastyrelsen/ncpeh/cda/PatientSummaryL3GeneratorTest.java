package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PatientSummaryL3GeneratorTest {
    @Test
    void generateTest() throws TemplateException, IOException {
        var oid = Oid.DK_PATIENT_SUMMARY;
        var extension = "1230879456";
        var title = "Generate PS L3 Document Title";
        var creationTimestamp = OffsetDateTime.now();
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
        var preferredHp = PreferredHealthProfessional.builder()
            .name(Name.fromFullName("Tycho Brahe"))
            .telecoms(List.of(Telecom.builder().use(Telecom.Use.WORK_PLACE).value("tel:+4511111111").build()))
            .address(new Address(List.of("Rundetårn", "Købmagergade 52A", "Kælderen"), "København K", "1150", "DK"))
            .build();

        var model = PatientSummaryL3.builder()
            .documentId(new CdaId(oid, extension))
            .effectiveTime(creationTimestamp)
            .title(title)
            .patient(patient)
            .preferredHp(preferredHp)
            .build();
        var cda = PatientSummaryL3Generator.generate(model);
        Assertions.assertNotNull(cda);

        //Initalize xPath Engine to read the data to validate it
        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        String oidPath = "/hl7:ClinicalDocument/hl7:id/@root";
        String idExtensionPath = "/hl7:ClinicalDocument/hl7:id/@extension";
        String titlePath = "/hl7:ClinicalDocument/hl7:title";
        String creationTimestampPath = "/hl7:ClinicalDocument/hl7:effectiveTime/@value";
        String patientBirthTimePath = "/hl7:ClinicalDocument/hl7:recordTarget//hl7:birthTime/@value";
        String softwareNamePath = "/hl7:ClinicalDocument/hl7:author//hl7:softwareName";

        var generatedCda = Input.fromString(cda).build();

        assertThat("oid matches", xpathEngine.evaluate(oidPath, generatedCda), is(oid.value));
        assertThat("extension matches", xpathEngine.evaluate(idExtensionPath, generatedCda), is(extension));
        assertThat("title matches", xpathEngine.evaluate(titlePath, generatedCda), is(title));
        assertThat("creation time matches", OffsetDateTime.parse(xpathEngine.evaluate(creationTimestampPath, generatedCda)), is(creationTimestamp));
        assertThat("patient birth time matches", xpathEngine.evaluate(patientBirthTimePath, generatedCda), is("19821103"));
        assertThat(
            "patient family name is correct",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:family", generatedCda),
            is("Andersen"));
        assertThat(
            "patient first name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:given)[1]", generatedCda),
            is("Hans"));
        assertThat(
            "patient middle name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:given)[2]", generatedCda),
            is("Christian"));
        assertThat("software name is there", xpathEngine.evaluate(softwareNamePath, generatedCda), is("NCPeH Denmark"));
        assertThat(
            "preferred health professional phone is correct",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:participant//hl7:telecom/@value", generatedCda),
            is("tel:+4511111111"));
    }

    @Test
    void noPreferredHpTest() {
        var oid = Oid.DK_PATIENT_SUMMARY;
        var extension = "1230879456";
        var title = "Generate PS L3 Document Title";
        var creationTimestamp = OffsetDateTime.now();
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

        var model = PatientSummaryL3.builder()
            .documentId(new CdaId(oid, extension))
            .effectiveTime(creationTimestamp)
            .title(title)
            .patient(patient)
            .preferredHp(null)
            .build();
        var cda = PatientSummaryL3Generator.generate(model);
        Assertions.assertNotNull(cda);
    }
}
