package dk.nsp.epps.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.CreateDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreateDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.test.idp.model.Identity;
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
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

    private JAXBElement<WhitelistingHeader> getWhitelistingHeader(PredefinedRequestedRole requestedRole) {
        final var header = WhitelistingHeader.builder()
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

        return new ObjectFactory().createWhitelistingHeader(header);
    }

    /**
     * "Påbegynd ekspedition".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:pabegynd_ekspedition">FMK documentation.</a>
     */
    public StartEffectuationResponseType startEffectuation(StartEffectuationRequestType request, Identity caller)
        throws JAXBException {
        return makeFmkRequest(
            facE5.createStartEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#StartEffectuation",
            StartEffectuationResponseType.class,
            caller
        );
    }

    /**
     * "Opret effektuering på recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:opret_effektuering">FMK documentation.</a>
     */
    public CreatePharmacyEffectuationResponseType createPharmacyEffectuation(CreatePharmacyEffectuationRequestType request, Identity caller)
        throws JAXBException {
        return makeFmkRequest(
            facE2.createCreatePharmacyEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreatePharmacyEffectuation",
            CreatePharmacyEffectuationResponseType.class,
            caller
        );

    }

    /**
     * "Hent recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_recept">FMK documentation.</a>
     */
    public GetPrescriptionResponseType getPrescription(GetPrescriptionRequestType request, Identity caller) throws JAXBException {
        return makeFmkRequest(
            fac.createGetPrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription",
            GetPrescriptionResponseType.class,
            caller
        );
    }

    public GetMedicineCardResponseType getMedicineCard(GetMedicineCardRequestType request, Identity caller, PredefinedRequestedRole requestedRole) throws JAXBException {
        return makeFmkRequest(
            facE2.createGetMedicineCardRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetMedicineCard",
            GetMedicineCardResponseType.class,
            caller,
            requestedRole
        );
    }

    public CreateDrugMedicationResponseType createDrugMedication(
        CreateDrugMedicationRequestType request,
        Identity caller,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        return makeFmkRequest(
            facE2.createCreateDrugMedicationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreateDrugMedication",
            CreateDrugMedicationResponseType.class,
            caller,
            requestedRole
        );
    }

    private <RequestType, ResponseType> ResponseType makeFmkRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        Identity caller
    ) throws JAXBException {
        return makeFmkRequest(request, soapAction, clazz, caller, PredefinedRequestedRole.APOTEKER);
    }

    private <RequestType, ResponseType> ResponseType makeFmkRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        Identity caller,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        final Reply reply;
        try {
            reply = NspClient.request(
                serviceUri,
                toElement(request),
                soapAction,
                caller,
                toElement(getWhitelistingHeader(requestedRole))
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply.getBody(), clazz).getValue();
    }
}
