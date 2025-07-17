package dk.sundhedsdatastyrelsen.ncpeh.authentication;

public record AuthenticationConfig(
    String templatePath,
    String keyStorePath,
    String keyStorePassword,
    String keyAlias
) {

    // Default constructor - load from system properties or fall back
    public AuthenticationConfig() {
        this(
            System.getProperty("TEMPLATE_PATH", "envelope/soap_template.xml"),
            System.getProperty("KEYSTORE_PATH", "epps-sosi-sts-client.p12"),
            getRequiredKeyStorePassword(),
            System.getProperty("KEYSTORE_ALIAS", "epps-sosi-sts-client")
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
