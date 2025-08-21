package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClientIdws;
import org.apache.axis.utils.StringUtils;
import org.apache.ws.security.WSSConfig;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * API client for FMK test environment
 */
public class Fmk {
    static {
        WSSConfig.init();
    }

    private static final String fmkEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling";
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
                fmkClient = new FmkClient(fmkEndpointUri);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return fmkClient;
    }

    private static FmkClientIdws idwsFmkClient;

    public static FmkClientIdws idwsApiClient() {
        if (idwsFmkClient == null) {
            try {
                var base64 = System.getenv("FMK_CERT_BASE_64");
                var alias = System.getenv("FMK_CERT_ALIAS");
                var password = System.getenv("FMK_CERT_PASSWORD");
                if (StringUtils.isEmpty(base64) || StringUtils.isEmpty(alias) || StringUtils.isEmpty(password)) {
                    throw new IllegalArgumentException("FMK_CERT_BASE_64, FMK_CERT_ALIAS and FMK_CERT_PASSWORD must all be set for the integration test to work.");
                }
                var config = new FmkClientIdws.Config(
                    new ByteArrayInputStream(Base64.getDecoder().decode(base64)),
                    alias,
                    password,
                    fmkEndpointUri
                );
                idwsFmkClient = new FmkClientIdws(config);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return idwsFmkClient;
    }
}
