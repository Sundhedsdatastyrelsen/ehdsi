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
            assertThat(CertificateUtils.extractCountryCode(result.certificate()), is("DK"));
        }
    }

    @Test
    void loadTestCertAndPrivateKeyFromFile() throws Exception {
        var tempFile = Files.createTempFile("keystore", "p12");
        try (var keystore = getClass().getClassLoader().getResourceAsStream("test-signer.p12")) {
            assertThat(keystore, notNullValue());
            Files.copy(keystore, tempFile, StandardCopyOption.REPLACE_EXISTING);

            var result = CertificateUtils.loadCertificateFromKeystore(tempFile, "test-signer", "test123");
            assertThat(
                result.certificate().getSubjectX500Principal().getName(),
                equalTo("CN=Test Certificate,OU=UnitTest,O=TestOrg,L=Copenhagen,ST=DK,C=DK"));
            assertThat(CertificateUtils.extractCountryCode(result.certificate()), is("DK"));
        } finally {
            Files.delete(tempFile);
        }
    }

    @Test
    void fromBase64() throws AuthenticationException {
        var b64Cert = """
            MIIEKTCCAxGgAwIBAgICAMUwDQYJKoZIhvcNAQENBQAwUTELMAkGA1UEBhMCQkUxHDAaBgNVBAoME0V1cm9w
            ZWFuIENvbW1pc3Npb24xETAPBgNVBAMMCEVIRFNJIENBMREwDwYDVQQLDAhERyBTYW50ZTAeFw0yNDEwMDcx
            MzM2NTNaFw0zNDEwMDcxMzM2NTNaMF4xCzAJBgNVBAYTAkRLMSkwJwYDVQQKDCBUaGUgRGFuaXNoIEhlYWx0
            aCBEYXRhIEF1dGhvcml0eTEkMCIGA1UEAwwbR1JQOkVIRUFMVEhfTkNQX0JPT1RDQU1QX0RLMIICIjANBgkq
            hkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtpXCcVejHR1AGvKA06LaxGWwyXRQw4U3W118l5RU+BNdOgD7nvC0
            ujvi05ESdnL7SKrGeVosh1qyyIBGbNCdZTYqGsYLkIkiRb0cP/YRCCfW/NuEcW22sdezVgPGbv1vgIEaINkD
            kXHM4s5yZEi55JyBuNGG19ghNfwxnhgqge54GdLhOPct5K+m06AgGuRTEU4mR/uBRdM4sue0WfOVkw4bF9JT
            eNoU5YI0qp1q8nSsuJAwsOGaNPd7Q0garSeT8WXl/q8Z5RijdnUcPnznvMbri+JhryVH9n33S6ejFQ06mQHs
            wn1006ZJkoGXVtv7VCmVtzUZtO34p1w8HiVnRpQ/K907uJEMkY5kOxrzpuMv0hf7veKGMSDwyW1hgd9UHV/f
            k4rtKY5LfNueFWjpXNcvW1YpRZHSCa7T0a3HvrnIC2HaZszt8ALX5RtTmqH7nyAhHHUX2eY6bGseuM1+x+55
            n215DuK7rV6kMVd4taOQcbmeTmtwQp6Kc4oUXpYDUTpUu+xlzV9thDTqnl6cnwXuGHb1b9s6TvXv2ouiQ4RV
            91u+XY1+YLdEKHtAKVfekaWQU//vdeRqORfEpj4PbzoIQsEgW01/xLMzgw2BsMtiHE9+yk+v/ljzFxGDdgmr
            yCu6ODX4Hol5jSXwtGHa5KDl9zZTwtSSZp2axxURinECAwEAATANBgkqhkiG9w0BAQ0FAAOCAQEAEPj3ChZY
            4indo6n6dUxqcZWmXizDYWHW1KCbac4SXMfpv4lOfUK/lbuuDxGZEIdYDf1A4kelBfky9sS5k1zsPnn8O6wH
            boLKbFiXZzOElfa0uSjY+IO3Fe73xvnW762xickZ8g5EdMhm+wUy5PWbUFMYruyiOAVScTCP90Kp9DCDYBuH
            Ud6KzNF5V458QrKLb7uNruK0hoXrExHMjbAeM5hIFfDz477Y84Suh6XSNA1istQ8Stfw1H8hSIiFrRbPf+57
            IfLWQ/SYM/ghY4ZGIiEn1dXBpSeyjlAeU3sr4rxCm1zCDd8DniYcITTXrwND/3V/5FmtSUX4V4Oh7MXinA==
            """;
        var cert = CertificateUtils.fromBase64(b64Cert);
        assertThat(cert, notNullValue());
        assertThat(CertificateUtils.extractCountryCode(cert), is("DK"));
    }
}
