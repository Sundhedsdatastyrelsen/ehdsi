package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.FskClient;

/**
 * API client for FMK test environment
 */
public class Fsk {
    private static final String fskEndpointUri = "http://test1.ekstern-test.nspop.dk:8080/decoupling";
    /**
     * Jens Jensen is a test person vi can see in the test person overview. Do not edit.
     */
    public static final String cprJensJensenReadOnly = "0408801111";


    private static FskClient fskClient;

    public static FskClient apiClient() {
        if (fskClient == null) {
            try {
                fskClient = new FskClient(fskEndpointUri);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return fskClient;
    }
}
