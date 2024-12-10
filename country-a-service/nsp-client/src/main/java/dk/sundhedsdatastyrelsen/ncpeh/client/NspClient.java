package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsp.test.idp.NspSoapClient;
import dk.nsp.test.idp.model.Identity;
import dk.sosi.seal.SOSIFactory;
import dk.sosi.seal.model.Reply;
import dk.sosi.seal.pki.SOSITestFederation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

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
            var reply = sosiFactory.deserializeReply(IOUtils.toString(response.getResponse(), StandardCharsets.UTF_8));
            if (response.isFault()) {
                throw new RuntimeException(String.format("Request failed with message: %s", reply.getFaultString()));
            }
            return reply;
        }
    }
}
