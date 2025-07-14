package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.Data;

@Data
public class AuthenticationConfig {

    private final String templatePath;
    private final String keyStorePath;
    private final String keyStorePassword;
    private final String keyAlias;

    // Default constructor - load from system properties or fall back
    public AuthenticationConfig() {
        this.templatePath = System.getProperty("TEMPLATE_PATH", "envelope/soap_template.xml");
        this.keyStorePath = System.getProperty("KEYSTORE_PATH", "epps-sosi-sts-client.p12");
        this.keyStorePassword = System.getProperty("KEYSTORE_PASSWORD"); // set jvm properties like: -DKEYSTORE_PASSWORD=xxx -DKEYSTORE_PATH=your.p12
        this.keyAlias = System.getProperty("KEYSTORE_ALIAS", "epps-sosi-sts-client");

        if (this.keyStorePassword == null || this.keyStorePassword.isBlank()) {
            throw new IllegalStateException("Missing system property KEYSTORE_PASSWORD");
        }
    }

    // Manual override constructor for test use
    public AuthenticationConfig(String templatePath, String keyStorePath, String keyStorePassword, String keyAlias) {
        if (keyStorePassword == null || keyStorePassword.isBlank()) {
            throw new IllegalArgumentException("Missing required keystore password");
        }
        this.templatePath = templatePath;
        this.keyStorePath = keyStorePath;
        this.keyStorePassword = keyStorePassword;
        this.keyAlias = keyAlias;
    }
}
