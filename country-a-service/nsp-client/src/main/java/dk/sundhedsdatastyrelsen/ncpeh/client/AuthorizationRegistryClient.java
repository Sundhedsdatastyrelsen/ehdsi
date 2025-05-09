package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationResponseType;
import dk.nsp.test.idp.model.Identity;
import dk.sosi.seal.model.Reply;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class AuthorizationRegistryClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthorizationRegistryClient.class);
    private final URI serviceUri;

    public AuthorizationRegistryClient(@Value("${app.authorization-registry.endpoint.url}") String serviceUri) {
        try {
            this.serviceUri = new URI(serviceUri);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Element authorizationCodeRequestType(String authorizationCode) throws Exception {
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

    public AuthorizationResponseType requestByAuthorizationCode(String authorizationCode, Identity caller) throws JAXBException {
        final Reply response;
        try {
            log.info("Calling AuthorizationCodeService at {}", serviceUri);
            response = NspClient.request(
                serviceUri,
                authorizationCodeRequestType(authorizationCode),
                "http://nsi.dk/sdm/Gateway",
                caller
            );
        } catch (Exception e) {
            throw new NspClientException("AuthorizationCodeService request failed", e);
        }

        final var unmarshaller = JAXBContext.newInstance(AuthorizationResponseType.class).createUnmarshaller();
        final var jaxbResponse = unmarshaller.unmarshal(response.getBody(), AuthorizationResponseType.class);
        return jaxbResponse.getValue();
    }
}
