package dk.openncp.nationalconnector;

import dk.nsp.epps.ApiClient;
import dk.nsp.epps.api.DefaultApi;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * Access to the generated Country A service API.
 */
public class CountryAService {
    static final String countryAHost = System.getenv().getOrDefault("NC_DK_COUNTRY_A_HOST", "http://localhost:8180");
    static final DefaultApi api;

    static {
        try {
            final var uri = new URI(countryAHost);
            api = new DefaultApi(new ApiClient()
                .setScheme(uri.getScheme())
                .setHost(uri.getHost())
                .setPort(uri.getPort())
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Environment variable NC_DK_COUNTRY_A_HOST is not a valid URI");
        }
    }

    public static DefaultApi api() {
        return api;
    }
}
