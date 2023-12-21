package dk.nsp.epps.service.client;

import dk.sosi.seal.model.Reply;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import oio.medcom.cprservice._1_0.GetPersonInformationIn;
import oio.medcom.cprservice._1_0.GetPersonInformationOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class CprClient {

    private final URI serviceUri;
    private final JAXBContext jaxbContext;
    private final SosiClient sosiClient;

    public CprClient(@Value("${app.cpr.endpoint.url}") String serviceUri, SosiClient sosiClient) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(serviceUri);
        this.sosiClient = sosiClient;
        jaxbContext = JAXBContext.newInstance(GetPersonInformationIn.class, GetPersonInformationOut.class);
    }

    private <T> Element toElement(JAXBElement<T> jaxbElement) throws JAXBException {
        DOMResult res = new DOMResult();
        jaxbContext.createMarshaller().marshal(jaxbElement, res);
        return ((Document) res.getNode()).getDocumentElement();
    }

    public GetPersonInformationOut getPersonInformation(String cpr) throws IOException,
        InterruptedException, JAXBException {
        final var requestBody = getPersonInformationIn(cpr);
        final Reply response;
        try {
            log.info("Calling getPersonInformation at {}", serviceUri);
            response = sosiClient.sendNspRequest(
                serviceUri,
                toElement(requestBody),
                "http://rep.oio.dk/medcom.sundcom.dk/xml/wsdl/2007/06/28/getPersonInformation"
            );
        } catch (ServiceResponseException e) {
            if (e.getBody().contains("Ingen data fundet")) {
                return null;
            }
            throw new RuntimeException(e);
        }

        final var unmarshaller = jaxbContext.createUnmarshaller();
        final var jaxbResponse = unmarshaller.unmarshal(response.getBody(), GetPersonInformationOut.class);
        return jaxbResponse.getValue();
    }

    private JAXBElement<GetPersonInformationIn> getPersonInformationIn(String cpr) {
        final var value = new GetPersonInformationIn();
        value.setPersonCivilRegistrationIdentifier(cpr);
        return new JAXBElement<>(
            new QName("urn:oio:medcom:cprservice:1.0.4", "getPersonInformationIn"),
            GetPersonInformationIn.class,
            value
        );
    }
}
