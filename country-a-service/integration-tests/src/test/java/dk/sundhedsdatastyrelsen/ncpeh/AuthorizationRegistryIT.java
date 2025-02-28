package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class AuthorizationRegistryIT {
    private final String serviceUrl = "https://test2.ekstern-test.nspop.dk:8443/stamdata-authorization-lookup-ws/service/AuthorizationCodeService";

    @Test
    void authorizationLookup() throws JAXBException {
        var client = new AuthorizationRegistryClient(serviceUrl);
        // 6QF17 is the authorisation code of Charles Babbage
        var response = client.requestByAuthorizationCode("6QF17", OrganizationIdentities.sundhedsdatastyrelsen());

        assertThat(response.getAuthorization().size(), is(1));
        assertThat(response.getAuthorization().getFirst().getEducationCode(), is("7170"));
        assertThat(response.getAuthorization().getFirst().getEducationName(), is("Læge"));
    }
}
