package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.DdvClient;

public class Ddv {
    private static final String DdvEndpointUri = "https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling"; //Should be the same as the FMK endpoint
    private static DdvClient ddvClient;

    public static DdvClient apiClient() {
        if (ddvClient == null) {
            try {
                ddvClient = new DdvClient(DdvEndpointUri);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ddvClient;
    }
}
