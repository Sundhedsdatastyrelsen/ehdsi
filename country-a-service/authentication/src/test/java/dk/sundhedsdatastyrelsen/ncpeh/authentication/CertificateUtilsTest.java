package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CertificateUtilsTest {
    @Test
    void loadTestCertAndPrivateKey() throws Exception {
        try (var keystore = getClass().getClassLoader().getResourceAsStream("test-signer.p12")) {
            assertThat(keystore, notNullValue());
            var result = CertificateUtils.loadCertificateFromKeystore(keystore, "test-signer", "test123");
            assertThat(
                result.certificate().getSubjectX500Principal().getName(),
                equalTo("CN=Test Certificate,OU=UnitTest,O=TestOrg,L=Copenhagen,ST=DK,C=DK"));
        }
    }

    @Test
    void loadTestCertAndPrivateKeyFromFile() throws Exception {
        var tempFile = Files.createTempFile("keystore", "p12");
        try (var keystoreIs = getClass().getClassLoader().getResourceAsStream("test-signer.p12")) {
            assertThat(keystoreIs, notNullValue());
            Files.copy(keystoreIs, tempFile, StandardCopyOption.REPLACE_EXISTING);

            var result = CertificateUtils.loadCertificateFromKeystore(tempFile, "test-signer", "test123");
            assertThat(
                result.certificate().getSubjectX500Principal().getName(),
                equalTo("CN=Test Certificate,OU=UnitTest,O=TestOrg,L=Copenhagen,ST=DK,C=DK"));
        } finally {
            Files.delete(tempFile);
        }
    }
}
