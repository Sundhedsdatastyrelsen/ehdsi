package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.test.TestUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Base64;

/// Only for use in tests.
public class Sosi {
    private static AuthenticationService authService;
    private static String soapHeader;
    private static final URI sosiUri = URI.create("https://test2-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws");
    public static final URI sosiOrganisationDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/NewSecurityTokenService");
    public static final URI sosiPersonalDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI");

    @SneakyThrows
    public static EuropeanHcpIdwsToken getToken(String audience) {
        if (authService == null) {
            var base64 = System.getenv("CERT_BASE_64");
            var alias = System.getenv("CERT_ALIAS");
            var password = System.getenv("CERT_PASSWORD");
            if (StringUtils.isEmpty(base64) || StringUtils.isEmpty(alias) || StringUtils.isEmpty(password)) {
                throw new IllegalArgumentException("CERT_BASE_64, CERT_ALIAS, and CERT_PASSWORD must be set to run the test.");
            }
            var signingKey = CertificateUtils.loadCertificateFromKeystore(
                new ByteArrayInputStream(Base64.getDecoder().decode(base64)),
                alias,
                password);

            authService = new AuthenticationService(
                sosiUri,
                signingKey,
                "https://ehdsi-idp.testkald.nspop.dk");
        }
        if (soapHeader == null) {
            soapHeader = TestUtils.slurp(TestUtils.resource("openncp_soap_header.xml"));
        }
        return authService.xcaSoapHeaderToIdwsToken(soapHeader, audience);
    }

}
