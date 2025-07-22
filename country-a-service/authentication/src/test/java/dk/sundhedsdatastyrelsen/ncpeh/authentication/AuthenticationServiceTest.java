package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AuthenticationServiceTest {
    CertificateUtils.CertificateWithPrivateKey testCert() throws AuthenticationException {
        var keystore = this.getClass().getClassLoader().getResourceAsStream("test-signer.p12");
        assertThat(keystore, notNullValue());
        return CertificateUtils.loadCertificateFromKeystore(keystore, "test-signer", "test123");
    }

    @Test
    void testCreateSosiRequestBody_withValidInputs_shouldSignSuccessfully() throws Exception {
        var testCert = testCert();
        var service = new AuthenticationService(testCert.certificate(), testCert.privateKey());
        var soapHeader = readResourceFile("SoapHeader.xml");
        var signedXml = service.createSosiRequestBody(soapHeader, "1234567890");

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
