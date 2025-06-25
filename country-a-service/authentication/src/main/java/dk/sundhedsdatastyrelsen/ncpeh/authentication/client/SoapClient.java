package dk.sundhedsdatastyrelsen.ncpeh.authentication.client;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SoapClient {

    private static final String ENDPOINT = "https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws";
    //private static final String ENVELOPE = "/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/envelope/soap_envelope.xml";
    private static final String ENVELOPE = "/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/envelope/self_signed_soap_envelope.xml"; // SELF SIGNED

    public static void main(String[] args) throws Exception {
        String soapXml = loadSoapTemplate(ENVELOPE);
        sendSoapRequest(soapXml);
    }

    private static void sendSoapRequest(String xml) throws Exception {
        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(xml.getBytes("UTF-8"));
        }

        int code = conn.getResponseCode();
        System.out.println("HTTP Response Code: " + code);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
            code >= 400 ? conn.getErrorStream() : conn.getInputStream()))) {
            in.lines().forEach(System.out::println);
        }
    }

    private static String loadSoapTemplate(String path) throws IOException {
        // You can replace this with file loading or external config if preferred
        return new String(Files.readAllBytes(new File(path).toPath()));
    }
}

