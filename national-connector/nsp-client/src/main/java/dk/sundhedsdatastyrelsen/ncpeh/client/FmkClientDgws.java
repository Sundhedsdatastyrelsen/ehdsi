package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ConsentHeaderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import dk.sdsd.dgws._2012._06.ObjectFactory;
import dk.sdsd.dgws._2012._06.WhitelistingHeader;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import java.net.URI;
import java.net.URISyntaxException;


public class FmkClientDgws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FmkClientDgws.class);

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory facE2 =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory();

    private final URI serviceUri;
    private final AuthenticationService authenticationService;
    private final JAXBContext jaxbContext;


    public FmkClientDgws(
        String endpoint,
        AuthenticationService authenticationService
    ) throws URISyntaxException {
        this.serviceUri = new URI(endpoint);
        this.authenticationService = authenticationService;
        try {
            this.jaxbContext = JAXBContext.newInstance(
                ":dk.dkma.medicinecard.xml_schema._2015._06._01.e2"
                    + ":dk.sdsd.dgws._2012._06"
            );
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * "Hent medicinkort".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_medicinkort">FMK documentation.</a>
     */
    public GetMedicineCardResponseType getMedicineCard(GetMedicineCardRequestType request, NspDgwsIdentity caller, PredefinedRequestedRole requestedRole) throws JAXBException {
        return fmkRequestDgws(
            facE2.createGetMedicineCardRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetMedicineCard",
            GetMedicineCardResponseType.class,
            caller,
            requestedRole,
            false
        );
    }

    /**********************
     * Private
     ***********************/

    /// We only need DGWS to create prescriptions and drug medications for tests. Foreign health professionals should
    /// not be able to create Danish prescriptions. So we keep it in here.
    /// Additionally due to limitations of IDWS, we also allow retrieval of MedicineCards (For now).
    private <RequestType, ResponseType> ResponseType fmkRequestDgws(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        NspDgwsIdentity caller,
        PredefinedRequestedRole requestedRole,
        boolean requiresMedicineCardConsent
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        Element[] extraHeaders;
        var assertion = authenticationService.nspDgwsIdentityToAssertion(caller);
        if (requiresMedicineCardConsent) {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole)), ClientUtils.toElement(jaxbContext, getMedicineReviewConsent())};
        } else {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole))};
        }
        try {
            final var body = NspClientDgws.request(
                serviceUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                assertion,
                extraHeaders
            );

            return jaxbContext.createUnmarshaller().unmarshal(body, clazz).getValue();
        } catch (Exception e) {
            throw new NspClientException("FMK request failed", e);
        }
    }

    private  JAXBElement<WhitelistingHeader> getWhitelistingHeader(PredefinedRequestedRole requestedRole) {
        final var organisation = "Sundhedsdatastyrelsen";

        final var header = WhitelistingHeader.builder()
            .withSystemName("ePPS PoC")
            .withSystemOwnerName(organisation)
            .withSystemVersion("0.1.0")
            .withOrgResponsibleName(organisation)
            .withOrgUsingName(organisation)
            .withOrgUsingID()
            .withNameFormat(NameFormat.MEDCOM_LOCATIONNUMBER).withValue("5790000120512").end()
            .withRequestedRole(requestedRole.value())
            .build();

        return new ObjectFactory().createWhitelistingHeader(header);
    }

    private JAXBElement<ConsentHeaderType> getMedicineReviewConsent() {
        final var consentHeader = ConsentHeaderType.builder()
            .addConsent()
            .withSource("User")
            .withConsentType("MedicineReviewConsent")
            .withContent("MedicineCard")
            .end()
            .build();

        var objectFactory = new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();

        return objectFactory.createConsentHeader(consentHeader);
    }

}
