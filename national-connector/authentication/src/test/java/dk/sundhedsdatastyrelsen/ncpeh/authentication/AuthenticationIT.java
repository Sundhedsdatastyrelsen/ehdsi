package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.test.TestUtils;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationIT {
    private static AuthenticationServiceImpl authenticationService() throws AuthenticationException {
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
        return new AuthenticationServiceImpl(
            new AuthenticationServiceImpl.IdwsConfiguration(
                URI.create("https://test2-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws"),
                signingKey,
                "https://ehdsi-idp.testkald.nspop.dk"),
            null);
    }

    @Test
    void exchangeToken() throws AuthenticationException {
        var service = authenticationService();
        var idwsToken = service.xcaSoapHeaderToIdwsToken(TestUtils.slurp(TestUtils.resource("openncp_soap_header.xml")), "https://fmk");

        assertThat(idwsToken.audience(), is("https://fmk"));
        assertThat(idwsToken.assertion(), notNullValue());
        assertThat(idwsToken.subjectId(), is("John House"));
        assertThat(idwsToken.countryOfTreatment(), is("DK"));
        assertThat(idwsToken.pointOfCare(), is("eHDSI EU Testing MedCare Center"));
        assertThat(idwsToken.organizationId(), is("urn:hl7ii:1.2.3.4:ABCD"));
        assertThat(idwsToken.organizationName(), is("eHealth OpenNCP EU Portal"));
    }

    @Test
    void exchangeTokenNamespaceBugRegressions() throws AuthenticationException {
        var service = authenticationService();
        var idwsToken = service.xcaSoapHeaderToIdwsToken(TestUtils.slurp(TestUtils.resource("openncp_soap_header_namespace_bug_regression.xml")), "https://fmk");

        assertThat(idwsToken.audience(), is("https://fmk"));
        assertThat(idwsToken.assertion(), notNullValue());
        assertThat(idwsToken.subjectId(), is("Helvi Inkinen"));
        assertThat(idwsToken.countryOfTreatment(), is("FI"));
        assertThat(idwsToken.pointOfCare(), is("Kela pd3 as.tst"));
        assertThat(idwsToken.organizationId(), is("urn:oid:1.2.246.556.13001.48"));
        assertThat(idwsToken.organizationName(), nullValue());
    }

    @Test
    void exchangeTokenWithError() throws AuthenticationException {
        var service = authenticationService();
        assertThrows(
            AuthenticationException.SosiStsException.class,
            () -> service.xcaSoapHeaderToIdwsToken(TestUtils.slurp(TestUtils.resource("openncp_soap_header_bad.xml")), "https://fmk"));
    }
}
