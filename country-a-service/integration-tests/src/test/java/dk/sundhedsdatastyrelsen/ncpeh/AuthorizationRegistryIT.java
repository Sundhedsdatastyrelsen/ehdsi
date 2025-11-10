package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Sosi;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AuthorizationRegistryIT {
    private final String serviceUrl = "https://test2.ekstern-test.nspop.dk:8443/stamdata-authorization-lookup-ws/service/AuthorizationCodeService-20240105";

    @Test
    void authorizationLookup() throws JAXBException {
        var client = new AuthorizationRegistryClient(serviceUrl, Sosi.nspClientDgws);
        // 6QF17 is the authorisation code of Charles Babbage
        var response = client.requestByAuthorizationCode("6QF17", TestIdentities.systemIdentity);

        assertThat(response.getAutorisation().size(), is(1));
        // For codes: https://www.nspop.dk/display/public/web/Autorisation
        assertThat(response.getAutorisation().getFirst().getUddannelsesKode(), is("7170"));
        assertThat(response.getAutorisation().getFirst().getUddannelsesNavn(), is("Læge"));
    }
}
