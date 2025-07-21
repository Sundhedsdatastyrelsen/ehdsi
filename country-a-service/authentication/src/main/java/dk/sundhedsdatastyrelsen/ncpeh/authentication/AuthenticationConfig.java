package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.net.URI;
import java.nio.file.Path;

public record AuthenticationConfig(
    URI templatePath,
    URI keyStorePath,
    String keyStorePassword,
    String keyAlias
) {

    /**
     * @deprecated
     */
    public AuthenticationConfig() {
        this(
            Path.of(System.getProperty("TEMPLATE_PATH", "envelope/soap_template.xml")).toUri(),
            Path.of(System.getProperty("KEYSTORE_PATH", "epps-sosi-sts-client.p12")).toUri(),
            getRequiredKeyStorePassword(),
            System.getProperty("KEYSTORE_ALIAS", "epps-sosi-sts-client")
        );
    }

    public static AuthenticationConfig ofFilePaths(
            String templatePath,
            String keyStorePath,
            String keyStorePassword,
            String keyAlias
    ) {
        return new AuthenticationConfig(
                Path.of(templatePath).toUri(),
                Path.of(keyStorePath).toUri(),
                keyStorePassword,
                keyAlias
        );
    }

    private static String getRequiredKeyStorePassword() {
        String password = System.getProperty("KEYSTORE_PASSWORD"); // set jvm properties like: -DKEYSTORE_PASSWORD=xxx -DKEYSTORE_PATH=your.p12
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("Missing system property KEYSTORE_PASSWORD");
        }
        return password;
    }
}
