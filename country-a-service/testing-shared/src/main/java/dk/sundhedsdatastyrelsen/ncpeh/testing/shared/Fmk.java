package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClientIdws;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * API client for FMK test environment
 */
public class Fmk {
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
                var config = new FmkClientIdws.Config(
                    Base64.getDecoder()
                        .wrap(new ByteArrayInputStream(System.getenv("FMK_CERT_BASE_64")
                            .getBytes(StandardCharsets.UTF_8))),
                    System.getenv("FMK_CERT_ALIAS"),
                    System.getenv("FMK_CERT_PASSWORD"),
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
