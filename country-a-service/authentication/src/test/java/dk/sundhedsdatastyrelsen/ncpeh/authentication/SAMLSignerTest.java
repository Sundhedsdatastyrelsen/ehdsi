package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SAMLSignerTest {

    @Test
    void signsTemplateSuccessfully() throws Exception {
        // Load template from test resources
        var is = getClass().getClassLoader().getResourceAsStream("soap_template.xml");
        assertThat("soap_template.xml should be on the test classpath", is, is(notNullValue()));
        var template = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        assertThat("Template must not be empty", template, is(not(emptyString())));

        var testCert = AuthenticationServiceTest.testCert();
        // Sign the template
        var signer = new SAMLSigner(testCert.certificate(), testCert.privateKey());
        var signedXml = signer.sign(template);

        // Basic assertion
        assertThat(
            "Signed XML must contain a Signature element",
            signedXml,
            containsString("<ds:Signature")
        );
        System.out.println("Signed XML (truncated):\n" + signedXml.substring(0, Math.min(500, signedXml.length())));
    }
}
