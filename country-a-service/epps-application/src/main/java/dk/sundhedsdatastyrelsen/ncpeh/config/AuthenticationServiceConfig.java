package dk.sundhedsdatastyrelsen.ncpeh.config;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

@ConfigurationProperties("app.sosi")
public record AuthenticationServiceConfig(
    URI sosiStsUri,
    InputStream keystore,
    String keyAlias,
    String keystorePassword,
    String issuer
) implements AuthenticationService.Config {

    @ConstructorBinding
    public AuthenticationServiceConfig(
        String uri,
        String keystorePath,
        String keystoreAlias,
        String keystorePassword,
        String issuer
    ) throws IOException {
        this(URI.create(uri), new BufferedInputStream(Files.newInputStream(Path.of(keystorePath))), keystoreAlias, keystorePassword, issuer);
    }
}
