package dk.sundhedsdatastyrelsen.ncpeh.optout;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public class OptOutServiceImpl implements OptOutService, AutoCloseable {
    private final ObjectMapper json = new ObjectMapper();
    private final Config config;

    private final HttpClient httpClient;

    public OptOutServiceImpl(Config config) {
        this.config = config;
        try {
            this.httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext(config))
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void close() {
        httpClient.close();
    }

    @Override
    public boolean hasOptedOut(String cpr, Service service) {
        var req = new Request(cpr, List.of(service));
        try {
            var resp = lookup(req);
            var value = resp.get(service);
            if (value == null) {
                throw new OptOutServiceException("Missing opt-out value for service: " + service);
            }
            return value;
        } catch (IOException e) {
            throw new OptOutServiceException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OptOutServiceException(e);
        }
    }

    private record Request(String cpr, List<OptOutService.Service> services) {}
    // Response:  Map<Service, Boolean>

    private Map<Service, Boolean> lookup(Request request) throws IOException, InterruptedException {
        var resp = httpClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(config.host()).resolve("/api/lookup"))
                        .headers(
                                "Accept", "application/json",
                                "Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json.writeValueAsString(request)))
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            throw new OptOutServiceException("Bad response from opt-out lookup service. Response code: %s. Body: %s"
                    .formatted(resp.statusCode(), resp.body()));
        }

        return json.readValue(resp.body(), new TypeReference<>() {});
    }

    private static SSLContext sslContext(Config config) throws IOException, GeneralSecurityException {
        var clientKeyStore = KeyStore.getInstance("PKCS12");
        try (var in = Files.newInputStream(Path.of(config.clientKeystorePath()))) {
            clientKeyStore.load(in, config.clientKeystorePassword().toCharArray());
        }

        var trustStore = KeyStore.getInstance("PKCS12");
        try (var in = Files.newInputStream(Path.of(config.clientTruststorePath()))) {
            trustStore.load(in, config.clientTruststorePassword().toCharArray());
        }

        var kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientKeyStore, config.clientKeystorePassword().toCharArray());

        var tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        var sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());

        return sslContext;
    }

    @SuppressWarnings("java:S106") // don't complain about System.out.println
    public static void main(String... args) {
        try (var oo = new OptOutServiceImpl(new Config(
                "https://localhost:8444",
                "opt-out/src/test/resources/opt-out-keystore.p12",
                "changeit",
                "opt-out/src/test/resources/opt-out-truststore.p12",
                "changeit"))) {
            System.out.println(oo.hasOptedOut("0101019999", Service.PATIENT_SUMMARY));
        }
    }
}
