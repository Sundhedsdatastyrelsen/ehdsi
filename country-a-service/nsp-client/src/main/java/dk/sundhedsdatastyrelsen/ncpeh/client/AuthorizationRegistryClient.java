package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URI;
import java.net.URISyntaxException;

public class AuthorizationRegistryClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthorizationRegistryClient.class);
    private final URI serviceUri;

    public AuthorizationRegistryClient(String serviceUri) {
        try {
            this.serviceUri = new URI(serviceUri);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Element authorizationCodeRequestType(String authorizationCode) throws ParserConfigurationException {
        var factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        var builder = factory.newDocumentBuilder();
        var document = builder.newDocument();
        var ns = "http://nsi.dk/2024/01/05/StamdataAuthorization";
        var rootElement = document.createElementNS(ns, "AuthorizationCodeRequestStructure");
        var authCodeElement = document.createElementNS(ns, "authorizationCode");
        authCodeElement.appendChild(document.createTextNode(authorizationCode));
        rootElement.appendChild(authCodeElement);
        return rootElement;
    }

    public AuthorizationResponseType requestByAuthorizationCode(String authorizationCode, NspDgwsIdentity caller) throws JAXBException {
        final Element response;
        try {
            log.info("Calling AuthorizationCodeService at {}", serviceUri);
            response = NspClientDgws.request(
                serviceUri,
                authorizationCodeRequestType(authorizationCode),
                "http://nsi.dk/sdm/Gateway",
                caller
            );
        } catch (NspClientException | ParserConfigurationException e) {
            throw new NspClientException("AuthorizationCodeService request failed", e);
        }

        final var unmarshaller = JAXBContext.newInstance(AuthorizationResponseType.class).createUnmarshaller();
        final var jaxbResponse = unmarshaller.unmarshal(response, AuthorizationResponseType.class);
        return jaxbResponse.getValue();
    }
}
