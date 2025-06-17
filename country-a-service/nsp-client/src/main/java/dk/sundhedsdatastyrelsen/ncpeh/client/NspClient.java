package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsp.test.idp.NspSoapClient;
import dk.nsp.test.idp.model.Identity;
import dk.sosi.seal.SOSIFactory;
import dk.sosi.seal.model.Reply;
import dk.sosi.seal.pki.SOSITestFederation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NspClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NspClient.class);

    private static final SOSIFactory sosiFactory =
        new SOSIFactory(new SOSITestFederation(new Properties()), new Properties());

    private NspClient() {
    }

    /**
     * Send a SOAP request to an NSP service.
     */
    public static Reply request(URI uri, Element soapBody, String soapAction, Identity caller, Element... extraHeaders) throws Exception {
        try (var client = NspSoapClient.builder().withLogger(log::debug).build();
             var response = client.request(uri, soapAction)
                 .as(caller)
                 .execute(soapBody, extraHeaders)) {
            response.getResponse().reset();
            var fullText = convertStreamToString(response.getResponse());
            
            // Check if response is MIME multipart
            if (fullText.contains("--uuid:")) {
                fullText = extractSoapFromMime(fullText);
            }
            
            var reply = sosiFactory.deserializeReply(fullText);
            if (response.isFault()) {
                throw new NspClientException(String.format("Request failed with message: %s", reply.getFaultString()));
            }
            return reply;
        }
    }

    /**
     * Extract SOAP message from MIME multipart response.
     * The SOAP message is typically in the part with Content-Type: application/xop+xml
     */
    private static String extractSoapFromMime(String mimeResponse) {
        // Pattern to match the SOAP part in MIME response
        Pattern pattern = Pattern.compile(
            "--uuid:[^\\r\\n]+\\r?\\n" +
            "Content-Id: <root\\.message@cxf\\.apache\\.org>\\r?\\n" +
            "Content-Type: application/xop\\+xml[^\\r\\n]*\\r?\\n" +
            "Content-Transfer-Encoding: binary\\r?\\n\\r?\\n" +
            "([\\s\\S]*?)" +
            "--uuid:[^\\r\\n]+--",
            Pattern.MULTILINE
        );

        Matcher matcher = pattern.matcher(mimeResponse);
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        throw new NspClientException("Could not extract SOAP message from MIME response");
    }

    public static String convertStreamToString(InputStream bis) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(bis, StandardCharsets.UTF_8)) {
            int character;
            while ((character = reader.read()) != -1) {
                stringBuilder.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
