package dk.nsp.epps.testing.shared;

import dk.nsp.epps.client.FmkClient;

/**
 * API client for FMK test environment
 */
public class Fmk {
    private static final String fmkEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling";
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
