package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationIT {
    static AuthenticationService.Config config() {
        var keystorePath = System.getenv("KEYSTORE_PATH");
        assertThat("KEYSTORE_PATH env var should be set", keystorePath, notNullValue());
        var keyAlias = System.getenv("KEY_ALIAS");
        assertThat("KEY_ALIAS env var should be set", keyAlias, notNullValue());
        var password = System.getenv("KEYSTORE_PASSWORD");
        assertThat("KEYSTORE_PASSWORD env var should be set", password, notNullValue());
        return new AuthenticationService.Config(
            URI.create("https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws"),
            Path.of(keystorePath),
            keyAlias,
            password,
            "https://ehdsi-idp.testkald.nspop.dk"
        );
    }

    @Test
    void exchangeToken() throws AuthenticationException {
        var service = new AuthenticationService(config());
        var idwsToken = service.xcaSoapHeaderToIdwsToken(TestUtils.resource("openncp_soap_header.xml"), "https://fmk");

        assertThat(idwsToken.audience(), is("https://fmk"));
        assertThat(idwsToken.assertion(), notNullValue());
    }

    @Test
    void exchangeTokenWithError() {
        var service = new AuthenticationService(config());
        assertThrows(
            AuthenticationException.SosiStsException.class,
            () -> service.xcaSoapHeaderToIdwsToken(TestUtils.resource("openncp_soap_header_bad.xml"), "https://fmk"));
    }
}
