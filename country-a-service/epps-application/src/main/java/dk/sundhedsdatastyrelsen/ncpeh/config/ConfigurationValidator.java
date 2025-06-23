package dk.sundhedsdatastyrelsen.ncpeh.config;

import jakarta.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigurationValidator {

    private final Environment env;

    public ConfigurationValidator(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void validate() {
        final var nonBlankVals = List.of(
            "app.authorization-registry.endpoint.url",
            "app.cpr.endpoint.url",
            "app.fmk.endpoint.url",
            "app.fsk.endpoint.url",
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
