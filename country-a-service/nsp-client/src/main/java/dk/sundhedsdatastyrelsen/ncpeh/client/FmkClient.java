package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ConsentHeaderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreateDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreateDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.test.idp.model.Identity;
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import dk.sdsd.dgws._2012._06.ObjectFactory;
import dk.sdsd.dgws._2012._06.WhitelistingHeader;
import dk.sosi.seal.model.Reply;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
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
            caller,
            false
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
            caller,
            false
        );

    }

    /**
     * "Tilbagefør effektuering på recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:tilbagefor_effektuering_pa_recept">FMK documentation.</a>
     */
    public UndoEffectuationResponseType undoEffectuation(UndoEffectuationRequestType request, Identity caller)
        throws JAXBException {
        return makeFmkRequest(
            facE5.createUndoEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E5#UndoEffectuation",
            UndoEffectuationResponseType.class,
            caller,
            false
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
            caller,
            false
        );
    }

    /**
     * "Hent recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_laegemiddelordination">FMK documentation.</a>
     */
    public GetDrugMedicationResponseType getDrugMedication(GetDrugMedicationRequestType request, Identity caller) throws JAXBException {
        return makeFmkRequest(
            fac.createGetDrugMedicationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetDrugMedication",
            GetDrugMedicationResponseType.class,
            caller,
            false
        );
    }

    /**
     * "Hent recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_recept">FMK documentation.</a>
     */
    public GetPrescriptionResponseType getPrescriptionWithConsent(GetPrescriptionRequestType request, Identity caller) throws JAXBException {
        return makeFmkRequest(
            fac.createGetPrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription",
            GetPrescriptionResponseType.class,
            caller,
            true
        );
    }

    public GetMedicineCardResponseType getMedicineCard(GetMedicineCardRequestType request, Identity caller, PredefinedRequestedRole requestedRole) throws JAXBException {
        return makeFmkRequest(
            facE2.createGetMedicineCardRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetMedicineCard",
            GetMedicineCardResponseType.class,
            caller,
            requestedRole,
            false
        );
    }

    public GetDrugMedicationResponseType getDrugMedication(GetDrugMedicationRequestType request, Identity caller, PredefinedRequestedRole requestedRole) throws JAXBException {
        return makeFmkRequest(
            fac.createGetDrugMedicationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetDrugMedication",
            GetDrugMedicationResponseType.class,
            caller,
            requestedRole,
            false
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
            requestedRole,
            false
        );
    }

    public CreatePrescriptionResponseType createPrescription(
        CreatePrescriptionRequestType request,
        Identity caller,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        return makeFmkRequest(
            facE2.createCreatePrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreatePrescription",
            CreatePrescriptionResponseType.class,
            caller,
            requestedRole,
            false
        );
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
        return makeFmkRequest(request, soapAction, clazz, caller, PredefinedRequestedRole.APOTEKER, requiresMedicineCardConsent);
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
        if (requiresMedicineCardConsent) {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole)), ClientUtils.toElement(jaxbContext, getMedicineReviewConsent())};
        } else {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole))};
        }
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
