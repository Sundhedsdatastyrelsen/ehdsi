package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import ihe.iti.xds_b._2007.ObjectFactory;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Client for calling FSK SOAP endpoints.
 * Documentation is here: https://www.nspop.dk/display/public/web/FSK+-+Guide+til+anvendere
 */
@Component
public class FskClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FskClient.class);

    private static final oasis.names.tc.ebxml_regrep.xsd.query._3.ObjectFactory fac =
        new oasis.names.tc.ebxml_regrep.xsd.query._3.ObjectFactory();

    private static final ihe.iti.xds_b._2007.ObjectFactory factory = new ObjectFactory();

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    public FskClient(@Value("${app.fsk.endpoint.url}") String fskEndpointUrl) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(fskEndpointUrl);
        this.jaxbContext = JAXBContext.newInstance(
            "ihe.iti.xds_b._2007"
                + ":oasis.names.tc.ebxml_regrep.xsd.query._3"
        );
    }

    public AdhocQueryResponse list(
        AdhocQueryRequest request,
        NspDgwsIdentity caller
    ) throws JAXBException {
        QName qname = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0", "AdhocQueryRequest");

        JAXBElement<AdhocQueryRequest> jaxbElement = new JAXBElement<>(
            qname,
            AdhocQueryRequest.class,
            request
        );

        return makeFskRequest(
            "/nspservices/sfskreg",
            jaxbElement,
            "urn:ihe:iti:2007:RegistryStoredQuery",
            AdhocQueryResponse.class,
            caller
        );
    }

    public RetrieveDocumentSetResponseType getDocument(
        RetrieveDocumentSetRequestType request,
        NspDgwsIdentity caller
    ) throws JAXBException {
        return makeFskRequest(
            "/nspservices/sfskrep",
            factory.createRetrieveDocumentSetRequest(request),
            "urn:ihe:iti:2007:RetrieveDocumentSet",
            RetrieveDocumentSetResponseType.class,
            caller
        );
    }

    /**********************
     * Private
     ***********************/
    private <RequestType, ResponseType> ResponseType makeFskRequest(
        String specificEndpoint,
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        NspDgwsIdentity caller
    ) throws JAXBException {
        final Element reply;
        try {
            var fullUri = new URI(new StringBuilder().append(serviceUri).append(specificEndpoint).toString());
            log.info("Calling '{}' with a SOAP action '{}'", fullUri, soapAction);
            reply = NspClientDgws.request(
                fullUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                caller
            );
        } catch (Exception e) {
            throw new NspClientException("FSK request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply, clazz).getValue();
    }
}
