package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AuthenticationServiceTest {

    @Test
    void testCreateSosiRequestBody_withValidInputs_shouldSignSuccessfully() throws Exception {
        AuthenticationConfig config = new AuthenticationConfig(
            this.getClass().getClassLoader().getResource("soap_template.xml").toURI(),
            this.getClass().getClassLoader().getResource("test-signer.p12").toURI(),
            "test123",
            "test-signer"
        );

        AuthenticationService service = new AuthenticationService(config);

        String soapHeader = readResourceFile("SoapHeader.xml");
        String signedXml = service.createSosiRequestBody(soapHeader, "1234567890");

        assertThat(signedXml, allOf(
            containsString("ds:Signature"),
            containsString("X509Certificate")
        ));
    }

    private String readResourceFile(String resourceName) throws IOException {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) throw new RuntimeException("Resource not found: " + resourceName);
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
