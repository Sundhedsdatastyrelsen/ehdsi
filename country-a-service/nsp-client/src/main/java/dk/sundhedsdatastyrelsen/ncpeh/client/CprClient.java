package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import oio.medcom.cprservice._1_0.GetPersonInformationIn;
import oio.medcom.cprservice._1_0.GetPersonInformationOut;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMResult;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class CprClient {

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    public CprClient(String serviceUri) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(serviceUri);
        jaxbContext = JAXBContext.newInstance(GetPersonInformationIn.class, GetPersonInformationOut.class);
    }

    private <T> Element toElement(JAXBElement<T> jaxbElement) throws JAXBException {
        DOMResult res = new DOMResult();
        jaxbContext.createMarshaller().marshal(jaxbElement, res);
        return ((Document) res.getNode()).getDocumentElement();
    }

    /**
     * Look up citizen demographics by CPR number.
     */
    public GetPersonInformationOut getPersonInformation(String cpr, NspDgwsIdentity caller) throws JAXBException {
        final var requestBody = getPersonInformationIn(cpr);
        final Element response;
        try {
            log.info("Calling getPersonInformation at {}", serviceUri);
            response = NspClientDgws.request(
                serviceUri,
                toElement(requestBody),
                "http://rep.oio.dk/medcom.sundcom.dk/xml/wsdl/2007/06/28/getPersonInformation",
                caller
            );
        } catch (NspClientException e) {
            throw new NspClientException("CPR registry request failed", e);
        }

        final var unmarshaller = jaxbContext.createUnmarshaller();
        final var jaxbResponse = unmarshaller.unmarshal(response, GetPersonInformationOut.class);
        return jaxbResponse.getValue();
    }

    private JAXBElement<GetPersonInformationIn> getPersonInformationIn(String cpr) {
        return new JAXBElement<>(
            new QName("urn:oio:medcom:cprservice:1.0.4", "getPersonInformationIn"),
            GetPersonInformationIn.class,
            GetPersonInformationIn.builder()
                .withPersonCivilRegistrationIdentifier(cpr)
                .build()
        );
    }
}
