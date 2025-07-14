package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SoapHeaderParserTest {

    @Test
    void shouldParseAssertionFromSoapHeader() throws Exception {
        // Load the test SOAP header from resources
        InputStream in = getClass().getClassLoader().getResourceAsStream("soap-headers/SoapHeader.xml");
        assertNotNull(in, "SOAP header file should be present in test resources");

        String soapXml = new String(in.readAllBytes(), StandardCharsets.UTF_8);

        // Parse the assertion
        Assertion assertion = SoapHeaderParser.parse(soapXml);

        // Verify some key fields
        assertNotNull(assertion.getId());
        assertNotNull(assertion.getIssueInstant());
        assertEquals("2.0", assertion.getVersion());
        assertNotNull(assertion.getIssuer());
        assertNotNull(assertion.getSignature());
        assertNotNull(assertion.getSubject());
        assertNotNull(assertion.getConditions());
        assertNotNull(assertion.getAttributes());
        assertFalse(assertion.getAttributes().isEmpty());

        // Optional: check presence of specific attributes
        boolean hasSubjectAttr = assertion.getAttributes().stream()
            .anyMatch(attr -> "XSPA Subject".equals(attr.getFriendlyName()));
        assertTrue(hasSubjectAttr, "Should contain XSPA Subject attribute");
    }
}

