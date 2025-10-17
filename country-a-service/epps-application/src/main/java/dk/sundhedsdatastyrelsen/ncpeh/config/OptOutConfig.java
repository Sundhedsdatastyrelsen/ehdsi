package dk.sundhedsdatastyrelsen.ncpeh.config;

import dk.sundhedsdatastyrelsen.ncpeh.optout.OptOutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record OptOutConfig(
    @Value("${app.opt-out.host}") String host,
    @Value("${app.opt-out.clientKeystorePath}") String clientKeystorePath,
    @Value("${app.opt-out.clientKeystorePassword}") String clientKeystorePassword,
    @Value("${app.opt-out.clientTruststorePath}") String clientTruststorePath,
    @Value("${app.opt-out.clientTruststorePassword}") String clientTruststorePassword
) {
    public OptOutService.Config config() {
        return new OptOutService.Config(
            host,
            clientKeystorePath,
            clientKeystorePassword,
            clientTruststorePath,
            clientTruststorePassword
        );
    }
}
