package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sdsd.ddv.dgws._2010._08.NameFormat;
import dk.sdsd.ddv.dgws._2010._08.PredefinedRequestedRole;
import dk.sdsd.ddv.dgws._2012._06.WhiteListingHeader;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
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

public class DdvClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DdvClient.class);

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    private static final dk.vaccinationsregister.schemas._2013._12._01.ObjectFactory requestFactory =
        new dk.vaccinationsregister.schemas._2013._12._01.ObjectFactory();

    public DdvClient(String fmkEndpointUrl) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(fmkEndpointUrl);
        this.jaxbContext = JAXBContext.newInstance(
            ":dk.vaccinationsregister.schemas._2013._12._01"
                + ":dk.sdsd.ddv.dgws._2012._06"
        );
    }

    /**
     * "GetVaccinationCard".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:ddv:1.4.0:getvaccinationcard">DDV documentation.</a>
     */
    public GetVaccinationCardResponseType getVaccinationCard(GetVaccinationCardRequestType request, NspDgwsIdentity caller) throws JAXBException {
        return makeDdvRequest(
            requestFactory.createGetVaccinationCardRequest(request),
            "http://vaccinationsregister.dk/schemas/2013/12/01#GetVaccinationCard",
            GetVaccinationCardResponseType.class,
            caller
        );
    }

    /**********************
     * Private
     ***********************/

    private JAXBElement<WhiteListingHeader> getWhitelistingHeader(PredefinedRequestedRole requestedRole) {
        final var header = WhiteListingHeader.builder()
            .withSystemName("Patient Summary")
            .withSystemOwnerName("Sundhedsdatastyrelsen")
            .withSystemVersion("1.0")
            .withOrgResponsibleName("Sundhedsdatastyrelsen")
            .withOrgUsingName("Sundhedsdatastyrelsen")
            .withOrgUsingID()
            // TODO: Don't use Region Hovedstaden's location number:
            .withNameFormat(NameFormat.MEDCOM_LOCATIONNUMBER).withValue("5790000120512").end()
            .withRequestedRole(requestedRole.value())
            .build();

        return new dk.sdsd.ddv.dgws._2012._06.ObjectFactory().createWhiteListingHeader(header);
    }

    private <RequestType, ResponseType> ResponseType makeDdvRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        NspDgwsIdentity caller
    ) throws JAXBException {
        return makeDdvRequest(request, soapAction, clazz, caller, PredefinedRequestedRole.LÆGE);
    }

    private <RequestType, ResponseType> ResponseType makeDdvRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        NspDgwsIdentity caller,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        final Element reply;
        Element[] extraHeaders;

        extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole))};

        try {
            reply = NspClientDgws.request(
                serviceUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                caller,
                extraHeaders
            );
        } catch (NspClientException e) {
            throw new NspClientException("DDV request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply, clazz).getValue();
    }
}
