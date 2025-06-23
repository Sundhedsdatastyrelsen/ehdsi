package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
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
        var oid = Oid.DK_FMK_PRESCRIPTION; //TODO This should be replaced with a proper OID. Maybe based on 1.2.208.176.7.200
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
        assertThat("software name is there", xpathEngine.evaluate(softwareNamePath, generatedCda), is("NCPeH Denmark"));
    }
}
