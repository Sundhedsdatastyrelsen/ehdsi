package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardRequestType;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardResponseType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivateKey;

public class DdvClientIdws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DdvClientIdws.class);

    private final URI serviceUri;
    private final JAXBContext jaxbContext;
    private final PrivateKey signingKey;

    private static final dk.vaccinationsregister.schemas._2013._12._01.ObjectFactory requestFactory =
        new dk.vaccinationsregister.schemas._2013._12._01.ObjectFactory();

    public DdvClientIdws(PrivateKey signingKey, String endpoint) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(endpoint);
        this.jaxbContext = JAXBContext.newInstance(
            ":dk.vaccinationsregister.schemas._2013._12._01"
                + ":dk.sdsd.ddv.dgws._2012._06"
        );
        this.signingKey = signingKey;
    }

    /**
     * "GetVaccinationCard".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:ddv:1.4.0:getvaccinationcard">DDV documentation.</a>
     */
    public GetVaccinationCardResponseType getVaccinationCard(GetVaccinationCardRequestType request, EuropeanHcpIdwsToken caller) throws JAXBException {
        return makeDdvRequest(
            requestFactory.createGetVaccinationCardRequest(request),
            "http://vaccinationsregister.dk/schemas/2013/12/01#GetVaccinationCardIDWS",
            GetVaccinationCardResponseType.class,
            caller
        );
    }

    /**********************
     * Private
     ***********************/


    private <T, U> U makeDdvRequest(
        JAXBElement<T> request,
        String soapAction,
        Class<U> clazz,
        EuropeanHcpIdwsToken token
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        final Element body;
        try {
            body = NspClientIdws.request(
                serviceUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                token,
                this.signingKey
            );
        } catch (Exception e) {
            throw new NspClientException("DDV request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(body, clazz).getValue();
    }
}
