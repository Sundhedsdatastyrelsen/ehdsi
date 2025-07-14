package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceTest {

    @Test
    void testCreateSosiRequestBody_withValidInputs_shouldSignSuccessfully() throws Exception {
        AuthenticationConfig config = new AuthenticationConfig(
            "soap_template.xml",
            "test-signer.p12",
            "test123",
            "test-signer"
        );

        AuthenticationService service = new AuthenticationService(config);

        String soapHeader = readResourceFile("SoapHeader.xml");
        String signedXml = service.createSosiRequestBody(soapHeader, "1234567890");

        assertTrue(signedXml.contains("ds:Signature"), "Output should contain SAML signature");
        assertTrue(signedXml.contains("X509Certificate"), "Output should contain certificate");
    }

    private String readResourceFile(String resourceName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) throw new RuntimeException("Resource not found: " + resourceName);
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}

