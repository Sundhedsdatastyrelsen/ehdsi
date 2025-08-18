package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import lombok.SneakyThrows;

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

    static final Config authConfig = new Config(
        URI.create("https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws"),
        Base64.getDecoder()
            .wrap(new ByteArrayInputStream(System.getenv("SOSI_CERT_BASE_64").getBytes(StandardCharsets.UTF_8))),
        System.getenv("SOSI_CERT_ALIAS"),
        System.getenv("SOSI_CERT_PASSWORD"),
        "https://ehdsi-idp.testkald.nspop.dk"
    );

    private static AuthenticationService authService;
    private static String soapHeader;

    @SneakyThrows
    public static EuropeanHcpIdwsToken getToken() {
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
