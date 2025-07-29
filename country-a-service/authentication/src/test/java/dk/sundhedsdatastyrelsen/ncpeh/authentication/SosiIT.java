package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenExchangeRequest;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenParams;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.OpenNcpAssertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SosiIT {
    static CertificateAndKey testEnvCert() throws AuthenticationException {
        var password = System.getenv("KEYSTORE_PASSWORD");
        assertThat(password, notNullValue());
        return CertificateUtils.loadCertificateFromKeystore(Path.of("../config/epps-sosi-sts-client.p12"), "epps-sosi-sts-client", password);
    }

    private static String soapHeader() {
        return TestUtils.resource("openncp_soap_header.xml");
    }

    static BootstrapTokenExchangeRequest makeRequest(Clock clock) throws AuthenticationException {
        var cert = testEnvCert();

        var bstParams = BootstrapTokenParams.fromOpenNcpAssertions(
            OpenNcpAssertions.fromSoapHeader(soapHeader()),
            cert.certificate(),
            "https://fmk"
        );
//        var clock = Clock.fixed(Instant.parse("2000-01-01T00:00:00Z"), ZoneId.of("UTC"));
//        var clock = Clock.systemUTC();
        var bst = BootstrapToken.of(bstParams, "https://ehdsi-idp.testkald.nspop.dk", cert, clock);
        return BootstrapTokenExchangeRequest.of(bstParams.audience(), bst, cert);
    }

    static SosiStsClient sosiStsClient() {
        return new SosiStsClient(URI.create("https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws"));
    }

    @Test
    void exchangeToken() throws AuthenticationException {
        var idwsToken = sosiStsClient().exchangeBootstrapToken(makeRequest(Clock.systemUTC()));

        assertThat(idwsToken.audience(), is("https://fmk"));
        assertThat(idwsToken.assertion(), notNullValue());
    }

    @Test
    void exchangeTokenWithError() {
        var clock = Clock.fixed(Instant.parse("2000-01-01T00:00:00Z"), ZoneId.of("UTC"));
        assertThrows(AuthenticationException.SosiStsException.class, () -> sosiStsClient().exchangeBootstrapToken(makeRequest(clock)));
    }
}
