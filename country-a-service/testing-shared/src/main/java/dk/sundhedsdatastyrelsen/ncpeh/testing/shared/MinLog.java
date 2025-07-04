package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.MinLogClient;

public class MinLog {
    private MinLog() {
    }

    private static final String MINLOG_ENDPOINT_URI = "http://test1.ekstern-test.nspop.dk:8080/minlog2-registration/20250312/RegisterService";
    /**
     * Jens Jensen is a test person we can see in the test person overview. Do not edit.
     */
    public static final String CPR_JENS_JENSEN_READ_ONLY = "0408801111";

    private static MinLogClient minLogClient;

    public static MinLogClient apiClient() {
        if (minLogClient == null) {
            minLogClient = new MinLogClient(MINLOG_ENDPOINT_URI);
        }
        return minLogClient;
    }
}
