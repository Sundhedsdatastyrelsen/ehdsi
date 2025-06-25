package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.MinLogClient;

public class MinLog {
    private static final String minlogEndpointUri = "http://test1.ekstern-test.nspop.dk:8080/decoupling";
    /**
     * Jens Jensen is a test person vi can see in the test person overview. Do not edit.
     */
    public static final String cprJensJensenReadOnly = "0408801111";

    private static MinLogClient minLogClient;

    public static MinLogClient apiClient() {
        if (minLogClient == null) {
            try {
                minLogClient = new MinLogClient(minlogEndpointUri);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return minLogClient;
    }
}
