package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import lombok.SneakyThrows;
import org.apache.axis.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/// Only for use in tests.
public class Sosi {
    record Config(
        URI sosiStsUri,
        InputStream keystore,
        String keyAlias,
        String keystorePassword,
        String issuer
    ) implements AuthenticationService.Config {}

    private static Config authConfig;
    private static AuthenticationService authService;
    private static String soapHeader;

    @SneakyThrows
    public static EuropeanHcpIdwsToken getToken() {
        if (authConfig == null) {
            var base64 = System.getenv("SOSI_CERT_BASE_64");
            var alias = System.getenv("SOSI_CERT_ALIAS");
            var password = System.getenv("SOSI_CERT_PASSWORD");
            if (StringUtils.isEmpty(base64) || StringUtils.isEmpty(alias) || StringUtils.isEmpty(password)) {
                throw new IllegalArgumentException("SOSI_CERT_BASE_64, SOSI_CERT_ALIAS, and SOSI_CERT_PASSWORD must be set to run the test.");
            }
            authConfig = new Config(
                URI.create("https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws"),
                new ByteArrayInputStream(Base64.getDecoder().decode(base64)),
                alias,
                password,
                "https://ehdsi-idp.testkald.nspop.dk"
            );
        }
        if (authService == null) {
            authService = new AuthenticationService(authConfig);
        }
        if (soapHeader == null) {
            try (var is = Sosi.class.getClassLoader().getResourceAsStream("openncp_soap_header.xml")) {
                soapHeader = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        return authService.xcaSoapHeaderToIdwsToken(soapHeader, "https://fmk");
    }
}
