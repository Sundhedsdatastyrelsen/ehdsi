package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsp.test.idp.model.Identity;
import dk.sosi.seal.model.Reply;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.RegistrationRequestType;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class MinLogClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MinLogClient.class);

    private static final dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.ObjectFactory factory = new dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.ObjectFactory();

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    public MinLogClient(@Value("${app.minlog.endpoint.url}") String minlogEndpointUrl) {
        try {
            this.serviceUri = new URI(minlogEndpointUrl);
            this.jaxbContext = JAXBContext.newInstance(
                "dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration"
                    + ":oasis.names.tc.ebxml_regrep.xsd.rs._3"
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Bad URI", e);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public RegistryResponseType register(
        RegistrationRequestType request,
        Identity caller
    ) throws JAXBException {

        var jaxbElement = factory.createRegistrationRequest(request);

        return makeMinLogRequest(
            jaxbElement,
            "AddRegistrations",
            RegistryResponseType.class,
            caller
        );
    }

    /**********************
     * Private
     ***********************/

    /**
     * @param <A> Request type
     * @param <B> Response type
     */
    private <A, B> B makeMinLogRequest(
        JAXBElement<A> request,
        String soapAction,
        Class<B> clazz,
        Identity caller
    ) throws JAXBException {
        final Reply reply;
        try {
            log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
            reply = NspClient.request(
                serviceUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                caller
            );
        } catch (Exception e) {
            throw new NspClientException("MinLog request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply.getBody(), clazz).getValue();
    }
}
