package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationConfigTest {

    @BeforeEach
    void setup() {
        System.setProperty("KEYSTORE_PASSWORD", "testpw123");
    }

    @Test
    void shouldLoadDefaultConfigurationValues() {
        AuthenticationConfig config = new AuthenticationConfig();

        assertEquals("envelope/soap_template.xml", config.getTemplatePath());
        assertEquals("epps-sosi-sts-client.p12", config.getKeyStorePath());
        assertEquals("testpw123", config.getKeyStorePassword()); // to be removed
        assertEquals("epps-sosi-sts-client", config.getKeyAlias());
    }
}
