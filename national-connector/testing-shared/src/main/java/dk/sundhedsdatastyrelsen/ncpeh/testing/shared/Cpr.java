package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.CprClient;

/**
 * API client for NSP CPR registry service in test environment
 */
public class Cpr {
    static final String cprEndpointUri =
        "https://test2.ekstern-test.nspop.dk:8443/stamdata-cpr-ws/service/DetGodeCPROpslag-1.0.4";

    private static CprClient cprClient;

    public static CprClient apiClient() {
        if (cprClient == null) {
            try {
                cprClient = new CprClient(cprEndpointUri, Sosi.nspClientDgws);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return cprClient;
    }
}
