package dk.sundhedsdatastyrelsen.ncpeh.config;

import dk.sundhedsdatastyrelsen.ncpeh.optout.OptOutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public record OptOutConfig(
    @Value("${app.opt-out.host:#{null}}") String host,
    @Value("${app.opt-out.clientKeystorePath:#{null}}") String clientKeystorePath,
    @Value("${app.opt-out.clientKeystorePassword:#{null}}") String clientKeystorePassword,
    @Value("${app.opt-out.clientTruststorePath:#{null}}") String clientTruststorePath,
    @Value("${app.opt-out.clientTruststorePassword:#{null}}") String clientTruststorePassword,
    @Value("${app.opt-out.enabled}") boolean enabled
) {
    public OptOutConfig {
        if (enabled &&
            Stream.of(host, clientKeystorePath, clientKeystorePassword, clientTruststorePath, clientTruststorePassword)
                .anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Opt-out config is invalid, missing values.");
        }
    }

    public OptOutService.Config config() {
        if (!enabled) return null;
        return new OptOutService.Config(
            host,
            clientKeystorePath,
            clientKeystorePassword,
            clientTruststorePath,
            clientTruststorePassword
        );
    }
}
