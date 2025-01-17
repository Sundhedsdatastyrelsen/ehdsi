package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsp.test.idp.model.Identity;
import dk.sdsd.ddv.dgws._2010._08.NameFormat;
import dk.sdsd.ddv.dgws._2010._08.PredefinedRequestedRole;
import dk.sdsd.ddv.dgws._2012._06.WhiteListingHeader;
import dk.sosi.seal.model.Reply;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class DdvClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DdvClient.class);

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    public DdvClient(@Value("${app.fmk.endpoint.url}") String fmkEndpointUrl) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(fmkEndpointUrl);
        this.jaxbContext = JAXBContext.newInstance( //TODO UPDATE
            "dk.dkma.medicinecard.xml_schema._2015._06._01"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e2"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e5"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                + ":dk.sdsd.dgws.ddv._2012._06"
        );
    }

    private JAXBElement<WhiteListingHeader> getWhitelistingHeader(PredefinedRequestedRole requestedRole) {
        final var header = WhiteListingHeader.builder()
            .withSystemName("ePPS PoC")
            .withSystemOwnerName("Sundhedsdatastyrelsen")
            .withSystemVersion("0.1.0")
            .withOrgResponsibleName("Sundhedsdatastyrelsen")
            .withOrgUsingName("Sundhedsdatastyrelsen")
            .withOrgUsingID()
            // TODO: Don't use Region Hovedstaden's location number:
            .withNameFormat(NameFormat.MEDCOM_LOCATIONNUMBER).withValue("5790000120512").end()
            .withRequestedRole(requestedRole.value())
            .build();

        return new dk.sdsd.ddv.dgws._2012._06.ObjectFactory().createWhiteListingHeader(header);
    }

    /**********************
     * Private
     ***********************/

    private <RequestType, ResponseType> ResponseType makeFmkRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        Identity caller,
        Boolean requiresMedicineCardConsent
    ) throws JAXBException {
        return makeFmkRequest(request, soapAction, clazz, caller, PredefinedRequestedRole.FARMACEUT, requiresMedicineCardConsent);
    }

    private <RequestType, ResponseType> ResponseType makeFmkRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        Identity caller,
        PredefinedRequestedRole requestedRole,
        Boolean requiresMedicineCardConsent
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        final Reply reply;
        Element[] extraHeaders;

        extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole))};

        try {
            reply = NspClient.request(
                serviceUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                caller,
                extraHeaders
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply.getBody(), clazz).getValue();
    }
}
