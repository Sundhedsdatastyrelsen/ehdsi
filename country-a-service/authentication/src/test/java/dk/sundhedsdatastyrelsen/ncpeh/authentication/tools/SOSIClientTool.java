package dk.sundhedsdatastyrelsen.ncpeh.authentication.tools;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.BootstrapTokenTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

// TODO Refactor, move to README
public class SOSIClientTool {

    private static final String ENDPOINT = "https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws";
    private static final String ENVELOPE = "temp/request.xml";

    public static void main(String[] args) throws Exception {
        BootstrapTokenTest.main();
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
