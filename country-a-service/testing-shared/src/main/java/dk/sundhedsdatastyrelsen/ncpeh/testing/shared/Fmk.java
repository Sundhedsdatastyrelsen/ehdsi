package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClientIdws;
import org.apache.axis.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.util.Base64;

/**
 * API client for FMK test environment
 */
public class Fmk {
    private Fmk() {}

    private static final String FMK_ENDPOINT_URI = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling";

    public static final String FMK_IDWS_ENDPOINT_URI = "https://test1.fmk.netic.dk/idws_xua/fmk_xua_146_E6";

    /**
     * Helle Bonde is a test persona which we do *not* own, so we should only perform read operations on her.
     */
    public static final String cprHelleReadOnly = "1111111118";

    /**
     * This is our EU Test person
     */
    public static final String cprLotteSvendsen = "1503194046";
    /**
     * Karl Læge ePPS is a test persona which we own, so we can perform read and write operations on him.
     */
    public static final String cprKarl = "0201909309";
    private static FmkClient fmkClient;

    public static FmkClient apiClient() {
        if (fmkClient == null) {
            try {
                fmkClient = new FmkClient(FMK_ENDPOINT_URI);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return fmkClient;
    }

    private static FmkClientIdws idwsFmkClient;
    private static CertificateAndKey signingKey;

    public static CertificateAndKey getSigningKey() {
        if (signingKey == null) {
            var base64 = System.getenv("FMK_CERT_BASE_64");
            var alias = System.getenv("FMK_CERT_ALIAS");
            var password = System.getenv("FMK_CERT_PASSWORD");
            if (StringUtils.isEmpty(base64) || StringUtils.isEmpty(alias) || StringUtils.isEmpty(password)) {
                throw new IllegalArgumentException("FMK_CERT_BASE_64, FMK_CERT_ALIAS and FMK_CERT_PASSWORD must all be set for the integration test to work.");
            }
            try {
                signingKey = CertificateUtils.loadCertificateFromKeystore(
                    new ByteArrayInputStream(Base64.getDecoder().decode(base64)),
                    alias,
                    password);
            } catch (AuthenticationException e) {
                throw new IllegalStateException(e);
            }
        }
        return signingKey;
    }

    public static FmkClientIdws idwsApiClient() {
        if (idwsFmkClient == null) {
            try {
                idwsFmkClient = new FmkClientIdws(getSigningKey().privateKey(), FMK_IDWS_ENDPOINT_URI);
            } catch (URISyntaxException e) {
                throw new IllegalStateException(e);
            }
        }
        return idwsFmkClient;
    }
}
