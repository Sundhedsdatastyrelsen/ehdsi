package dk.sundhedsdatastyrelsen.ncpeh.client;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SOSIClient {

    private final String endpointUrl; // Test1 SOSI Url: "https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws";

    public SOSIClient(String endpoint) {
        this.endpointUrl = endpoint;
    }

    public String send(String signedSoapEnvelopeXml) throws IOException {
        HttpURLConnection conn = openConnection(new URL(endpointUrl));

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.setDoOutput(true);
        conn.setConnectTimeout(10_000); // 10 seconds
        conn.setReadTimeout(15_000);    // 15 seconds

        // Send payload
        try (OutputStream os = conn.getOutputStream()) {
            os.write(signedSoapEnvelopeXml.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        InputStream responseStream = (status >= 400) ? conn.getErrorStream() : conn.getInputStream();

        String responseBody = readStream(responseStream);
        log.info("STS Response: {}", status);
        return responseBody;
    }

    protected HttpURLConnection openConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private String readStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;

            // TODO: Soap services are fragile on linebreaks, verify this works!
            while ((line = reader.readLine()) != null) sb.append(line).append("\n");
            return sb.toString().trim();
        }
    }
}
