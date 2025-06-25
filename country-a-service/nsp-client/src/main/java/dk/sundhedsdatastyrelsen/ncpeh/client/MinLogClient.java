package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsp.test.idp.model.Identity;
import dk.sosi.seal.model.Reply;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2023._04._25.minlog2_registration.RegistrationRequestType;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import ihe.iti.xds_b._2007.ObjectFactory;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.namespace.QName;
import java.net.URI;
import java.net.URISyntaxException;

public class MinLogClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MinLogClient.class);

    private static final ihe.iti.xds_b._2007.ObjectFactory factory = new ObjectFactory();

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    public MinLogClient(@Value("${app.minlog.endpoint.url}") String minlogEndpointUrl) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(minlogEndpointUrl);
        this.jaxbContext = JAXBContext.newInstance(
            "oasis.names.tc.ebxml_regrep.xsd.query._3"
                + ":dk.sdsd.dgws._2012._06"
                + ":ihe.iti.xds_b._2007"
        );
    }

    public RegistryResponseType register(
        RegistrationRequestType request,
        Identity caller
    ) throws JAXBException {
        QName qname = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0", "RegistrationRequest");

        JAXBElement<RegistrationRequestType> jaxbElement = new JAXBElement<>(
            qname,
            RegistrationRequestType.class,
            request
        );

        return makeMinlogRequest(
            jaxbElement,
            "urn:ihe:iti:2007:RegistryStoredQuery",
            RegistryResponseType.class,
            caller
        );
    }

    /**********************
     * Private
     ***********************/
    private <RequestType, ResponseType> ResponseType makeMinlogRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
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
            throw new NspClientException("FSK request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply.getBody(), clazz).getValue();
    }
}
