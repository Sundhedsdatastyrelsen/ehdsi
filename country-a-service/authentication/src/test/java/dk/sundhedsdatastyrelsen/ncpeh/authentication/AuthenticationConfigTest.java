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

        assertThat(config.templatePath(), is("envelope/soap_template.xml"));
        assertThat(config.keyStorePath(), is("epps-sosi-sts-client.p12"));
        assertThat(config.keyStorePassword(), is("testpw123")); // TODO to be removed
        assertThat(config.keyAlias(), is("epps-sosi-sts-client"));
    }
}
