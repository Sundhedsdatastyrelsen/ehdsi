package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClient;

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
}
