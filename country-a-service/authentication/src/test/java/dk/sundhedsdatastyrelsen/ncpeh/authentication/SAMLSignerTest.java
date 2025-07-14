package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SAMLSignerTest {

    @Test
    void signsTemplateSuccessfully() throws Exception {
        // Load test config (adapt path or use constructor that allows overrides if needed)
        AuthenticationConfig config = new AuthenticationConfig("soap_template.xml", "test-signer.p12", "test123", "test-signer");

        // Load template from test resources
        InputStream is = getClass().getClassLoader().getResourceAsStream("envelope/soap_template.xml");
        assertNotNull(is, "soap_template.xml should be on the test classpath");

        String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        assertFalse(template.isEmpty(), "Template must not be empty");

        // Sign the template
        SAMLSigner signer = new SAMLSigner(config);
        String signedXml = signer.sign(template);

        // Basic assertion
        assertNotNull(signedXml);
        assertTrue(signedXml.contains("<ds:Signature"), "Signed XML must contain a Signature element");
        System.out.println("Signed XML (truncated):\n" + signedXml.substring(0, Math.min(500, signedXml.length())));
    }
}

