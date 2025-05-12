package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.builder.Input;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientSummaryL3GeneratorTest {
    @Test
    void generateTest() throws TemplateException, IOException {
        var oid = Oid.DK_FMK_PRESCRIPTION; //TODO This should be replaced with a proper OID. Maybe based on 1.2.208.176.7.200
        var extension = "1230879456";
        var title = "Generate PS L3 Document Title";
        var creationTimestamp = OffsetDateTime.now();

        var model = PatientSummaryL3.builder().documentId(new CdaId(oid,extension)).effectiveTime(creationTimestamp).title(title).build();
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

        var generatedCda = Input.fromString(cda).build();

        assertEquals(oid.value,xpathEngine.evaluate(oidPath, generatedCda));
        assertEquals(extension,xpathEngine.evaluate(idExtensionPath, generatedCda));
        assertEquals(title,xpathEngine.evaluate(titlePath, generatedCda));
        assertEquals(creationTimestamp, OffsetDateTime.parse(xpathEngine.evaluate(creationTimestampPath, generatedCda)));
    }
}
