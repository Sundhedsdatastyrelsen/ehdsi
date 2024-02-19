package dk.nsp.epps.service.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.Utils;
import dk.nsp.test.idp.EmployeeIdentities;
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.OrgUsingID;
import dk.sdsd.dgws._2012._06.ObjectFactory;
import dk.sdsd.dgws._2012._06.WhitelistingHeader;
import dk.sosi.seal.model.Reply;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.dom.DOMResult;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class FmkClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FmkClient.class);

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory fac =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory facE2 =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory();

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.e5.ObjectFactory facE5 =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.e5.ObjectFactory();

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    public FmkClient(@Value("${app.fmk.endpoint.url}") String fmkEndpointUrl) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(fmkEndpointUrl);
        this.jaxbContext = JAXBContext.newInstance(
            "dk.dkma.medicinecard.xml_schema._2015._06._01"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e2"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e5"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                + ":dk.sdsd.dgws._2012._06"
        );
    }

    private <T> Element toElement(JAXBElement<T> jaxbElement) throws JAXBException {
        DOMResult res = new DOMResult();
        jaxbContext.createMarshaller().marshal(jaxbElement, res);
        return ((Document) res.getNode()).getDocumentElement();
    }

    private JAXBElement<WhitelistingHeader> getWhitelistingHeader() {
        final var whitelistingHeader = new WhitelistingHeader();
        whitelistingHeader.setSystemName("ePPS PoC");
        whitelistingHeader.setSystemOwnerName("Sundhedsdatastyrelsen");
        whitelistingHeader.setSystemVersion("0.1.0");
        whitelistingHeader.setOrgResponsibleName("Sundhedsdatastyrelsen");
        whitelistingHeader.setOrgUsingName("Sundhedsdatastyrelsen");
        whitelistingHeader.setOrgUsingID(Utils.apply(new OrgUsingID(), orgUsingID -> {
            orgUsingID.setNameFormat(NameFormat.MEDCOM_LOCATIONNUMBER);
            // TODO: Don't use Region Hovedstaden's location number
            orgUsingID.setValue("5790000120512");
        }));
        // https://wiki.fmk-teknik.dk/doku.php?id=fmk:generel:roller
        whitelistingHeader.setRequestedRole("Læge");
        final var factory = new ObjectFactory();
        return factory.createWhitelistingHeader(whitelistingHeader);
    }

    /**
     * "Påbegynd ekspedition".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:pabegynd_ekspedition">FMK documentation.</a>
     */
    public StartEffectuationResponseType startEffectuation(StartEffectuationRequestType request)
        throws JAXBException {
        return makeFmkRequest(
            facE5.createStartEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#StartEffectuation",
            StartEffectuationResponseType.class
        );
    }

    /**
     * "Opret effektuering på recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:opret_effektuering">FMK documentation.</a>
     */
    public CreatePharmacyEffectuationResponseType createPharmacyEffectuation(CreatePharmacyEffectuationRequestType request)
        throws JAXBException {
        return makeFmkRequest(
            facE2.createCreatePharmacyEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreatePharmacyEffectuation",
            CreatePharmacyEffectuationResponseType.class
        );

    }

    /**
     * "Hent recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_recept">FMK documentation.</a>
     */
    public GetPrescriptionResponseType getPrescription(GetPrescriptionRequestType request) throws JAXBException {
        return makeFmkRequest(
            fac.createGetPrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription",
            GetPrescriptionResponseType.class
        );
    }

    private <RequestType, ResponseType> ResponseType makeFmkRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        final Reply reply;
        try {
            //noinspection NonAsciiCharacters
            reply = NspClient.request(
                serviceUri,
                toElement(request),
                soapAction,
                EmployeeIdentities.lægeMargaretHamilton(),
                toElement(getWhitelistingHeader())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply.getBody(), clazz).getValue();

    }
}
