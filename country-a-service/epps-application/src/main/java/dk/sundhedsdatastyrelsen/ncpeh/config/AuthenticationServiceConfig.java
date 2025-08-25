package dk.sundhedsdatastyrelsen.ncpeh.config;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.Name;

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
        @Name("endpoint.url")
        String uri,
        @Name("keystore.path")
        String keystorePath,
        @Name("keystore.alias")
        String keystoreAlias,
        @Name("keystore.password")
        String keystorePassword,
        @Name("issuer")
        String issuer
    ) throws IOException {
        this(URI.create(uri), new BufferedInputStream(Files.newInputStream(Path.of(keystorePath))), keystoreAlias, keystorePassword, issuer);
    }
}
