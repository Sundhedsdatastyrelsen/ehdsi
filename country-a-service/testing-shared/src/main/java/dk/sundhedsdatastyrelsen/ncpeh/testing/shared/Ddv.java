package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.client.DdvClientIdws;

public class Ddv {
    private static final String ENDPOINT = "https://test1.fmk.netic.dk/idws_xua/ddv_xua_140";
    private static DdvClientIdws ddvClient;

    private Ddv() {}

    public static DdvClientIdws apiClient() {
        if (ddvClient == null) {
            try {
                ddvClient = new DdvClientIdws(Fmk.getSigningKey().privateKey(), ENDPOINT);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ddvClient;
    }

}
