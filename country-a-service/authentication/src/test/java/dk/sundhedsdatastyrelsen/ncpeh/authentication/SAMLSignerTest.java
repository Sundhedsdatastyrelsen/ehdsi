package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SAMLSignerTest {

    @Test
    void signsTemplateSuccessfully() throws Exception {
        // Load test config (adapt path or use constructor that allows overrides if needed)
        AuthenticationConfig config = new AuthenticationConfig("soap_template.xml", "test-signer.p12", "test123", "test-signer");

        // Load template from test resources
        InputStream is = getClass().getClassLoader().getResourceAsStream("soap_template.xml");
        assertThat("soap_template.xml should be on the test classpath", is, is(notNullValue()));

        String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        assertThat("Template must not be empty", template, is(not(emptyString())));

        // Sign the template
        SAMLSigner signer = new SAMLSigner(config);
        String signedXml = signer.sign(template);

        // Basic assertion
        assertThat(
            "Signed XML must contain a Signature element",
            signedXml,
            containsString("<ds:Signature")
        );
        System.out.println("Signed XML (truncated):\n" + signedXml.substring(0, Math.min(500, signedXml.length())));
    }
}
