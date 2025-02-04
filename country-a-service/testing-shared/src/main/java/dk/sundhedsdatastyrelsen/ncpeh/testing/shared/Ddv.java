package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.DdvClient;

public class Ddv {
    private static final String ddvEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling"; //Should be the same as the FMK endpoint
    /**
     * Helle Bonde is a test persona which we do *not* own, so we should only perform read operations on her.
     */
    public static final String cprHelleReadOnly = "1111111118";
    /**
     * Karl Læge ePPS is a test persona which we own, so we can perform read and write operations on him.
     * (Should not be necessary to do any write operations in DDV, but this is preserved for completeness)
     */
    public static final String cprKarl = "0201909309";
    private static DdvClient ddvClient;

    public static DdvClient apiClient() {
        if (ddvClient == null) {
            try {
                ddvClient = new DdvClient(ddvEndpointUri);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ddvClient;
    }
}
