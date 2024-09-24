package dk.nsp.epps.testing.shared;

import dk.nsp.epps.client.CprClient;

/**
 * API client for NSP CPR registry service in test environment
 */
public class Cpr {
    static final String cprEndpointUri =
        "http://test2.ekstern-test.nspop.dk:8080/stamdata-cpr-ws/service/DetGodeCPROpslag-1.0.4";

    private static CprClient cprClient;

    public static CprClient apiClient() {
        if (cprClient == null) {
            try {
                cprClient = new CprClient(cprEndpointUri);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return cprClient;
    }
}
