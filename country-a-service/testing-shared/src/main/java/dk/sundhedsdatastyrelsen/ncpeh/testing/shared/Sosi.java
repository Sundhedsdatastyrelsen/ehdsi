package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientDgws;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/// Only for use in tests.
public class Sosi {
    private static AuthenticationService authService;
    private static String soapHeader;
    private static final URI sosiUri = URI.create("https://test2-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws");
    public static final URI sosiOrganisationDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/NewSecurityTokenService");
    public static final URI sosiPersonalDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI");

    @SneakyThrows
    public static EuropeanHcpIdwsToken getToken() {
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
                "https://ehdsi-idp.testkald.nspop.dk",
                new DgwsIdCardRequest.Configuration("", "", "", ""));
        }
        if (soapHeader == null) {
            try (var is = Sosi.class.getClassLoader().getResourceAsStream("openncp_soap_header.xml")) {
                soapHeader = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        return authService.xcaSoapHeaderToIdwsToken(soapHeader, "https://fmk");
    }

    public static final NspClientDgws nspClientDgws = new NspClientDgws(
        new AuthenticationService(
            URI.create("https://ncp"),
            null,
            "",
            new DgwsIdCardRequest.Configuration(
                "33257872",
                "NCPeH-DK",
                "NCPeH-DK",
                "Sundhedsdatastyrelsen")));
}
