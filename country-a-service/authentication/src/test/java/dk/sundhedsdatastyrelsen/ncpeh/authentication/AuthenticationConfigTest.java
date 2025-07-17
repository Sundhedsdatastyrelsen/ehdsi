package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AuthenticationConfigTest {

    @BeforeEach
    void setup() {
        System.setProperty("KEYSTORE_PASSWORD", "testpw123");
    }

    @Test
    void shouldLoadDefaultConfigurationValues() {
        AuthenticationConfig config = new AuthenticationConfig();

        assertThat(config.getTemplatePath(), is("envelope/soap_template.xml"));
        assertThat(config.getKeyStorePath(), is("epps-sosi-sts-client.p12"));
        assertThat(config.getKeyStorePassword(), is("testpw123")); // TODO to be removed
        assertThat(config.getKeyAlias(), is("epps-sosi-sts-client"));
    }
}
