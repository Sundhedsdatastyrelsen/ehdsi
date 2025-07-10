package dk.sundhedsdatastyrelsen.ncpeh.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class validates the configuration of the application.
 * The purpose is to fail early if the configuration is invalid.
 */
// @Component ensures that the bean is created on startup.
@Component
public class ConfigurationValidator {

    private final Environment env;

    public ConfigurationValidator(Environment env) {
        this.env = env;
        validate();
    }

    public void validate() {
        final var nonBlankVals = List.of(
            "app.authorization-registry.endpoint.url",
            "app.cpr.endpoint.url",
            "app.fmk.endpoint.url",
            "app.fsk.endpoint.url",
            "app.minlog.endpoint.url",
            "server.ssl.key-alias",
            "server.ssl.key-password",
            "server.ssl.key-store",
            "server.ssl.key-store-password"
        );
        for (var key : nonBlankVals) {
            var value = env.getProperty(key);
            if (value == null || value.isBlank()) {
                throw new IllegalStateException("Configuration value for '" + key + "' cannot be blank");
            }
        }
    }
}
