package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector;

import dk.sundhedsdatastyrelsen.ncpeh.ApiClient;
import dk.sundhedsdatastyrelsen.ncpeh.api.DefaultApi;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * Access to the generated Country A service API.
 */
public class NationalConnectorService {
    static final String host = System.getenv().getOrDefault("NC_DK_HOST", "http://localhost:8180");
    static final DefaultApi api;

    static {
        try {
            final var uri = new URI(host);
            api = new DefaultApi(new ApiClient()
                .setScheme(uri.getScheme())
                .setHost(uri.getHost())
                .setPort(uri.getPort())
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Environment variable NC_DK_HOST is not a valid URI");
        }
    }

    public static DefaultApi api() {
        return api;
    }
}
