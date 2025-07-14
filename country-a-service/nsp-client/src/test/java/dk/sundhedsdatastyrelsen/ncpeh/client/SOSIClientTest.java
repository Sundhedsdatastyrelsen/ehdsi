package dk.sundhedsdatastyrelsen.ncpeh.client;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;


/*
Ugly mock that is not verified working as the MinLog dependencies does not work.
 */
class SOSIClientTest {

    @Test
    void testSend_returnsMockedSoapResponse() throws Exception {
        String dummyResponse = "<samlp:Response>Success</samlp:Response>";
        SOSIClient client = new SOSIClient("https://mocked-endpoint") {
            @Override
            protected HttpURLConnection openConnection(URL url) {
                return new HttpURLConnection(url) {
                    @Override public void disconnect() {}
                    @Override public boolean usingProxy() { return false; }
                    @Override public void connect() {}

                    @Override public int getResponseCode() {
                        return 200;
                    }

                    @Override public InputStream getInputStream() {
                        return new ByteArrayInputStream(dummyResponse.getBytes());
                    }

                    @Override public OutputStream getOutputStream() {
                        return new ByteArrayOutputStream(); // Accept writes
                    }
                };
            }
        };

        String result = client.send("<Envelope>test</Envelope>");
        assertTrue(result.contains("Success"));
    }
}
