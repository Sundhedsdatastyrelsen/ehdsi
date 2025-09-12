package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.shared.test.TestUtils;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationIT {
    private static AuthenticationService authenticationService() throws AuthenticationException {
        var keystorePath = System.getenv("KEYSTORE_PATH");
        assertThat("KEYSTORE_PATH env var should be set", keystorePath, notNullValue());
        var keyAlias = System.getenv("KEY_ALIAS");
        assertThat("KEY_ALIAS env var should be set", keyAlias, notNullValue());
        var password = System.getenv("KEYSTORE_PASSWORD");
        assertThat("KEYSTORE_PASSWORD env var should be set", password, notNullValue());
        var signingKey = CertificateUtils.loadCertificateFromKeystore(
            Path.of(keystorePath),
            keyAlias,
            password);
        return new AuthenticationService(
            URI.create("https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws"),
            signingKey,
            "https://ehdsi-idp.testkald.nspop.dk");
    }

    @Test
    void exchangeToken() throws AuthenticationException {
        var service = authenticationService();
        var idwsToken = service.xcaSoapHeaderToIdwsToken(TestUtils.resource("openncp_soap_header.xml", AuthenticationIT.class.getClassLoader()), "https://fmk");

        assertThat(idwsToken.audience(), is("https://fmk"));
        assertThat(idwsToken.assertion(), notNullValue());
    }

    @Test
    void exchangeTokenWithError() throws AuthenticationException {
        var service = authenticationService();
        assertThrows(
            AuthenticationException.SosiStsException.class,
            () -> service.xcaSoapHeaderToIdwsToken(TestUtils.resource("openncp_soap_header_bad.xml", AuthenticationIT.class.getClassLoader()), "https://fmk"));
    }
}
