package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AbortEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ConsentHeaderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PrescriptionErrorType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.AbortEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivateKey;
import java.util.stream.Collectors;

public class FmkClientIdws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FmkClientIdws.class);

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory fac =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory();

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory facE2 =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory();

    private static final dk.dkma.medicinecard.xml_schema._2015._06._01.e5.ObjectFactory facE5 =
        new dk.dkma.medicinecard.xml_schema._2015._06._01.e5.ObjectFactory();

    private final URI serviceUri;
    private final JAXBContext jaxbContext;
    private final PrivateKey fmkSigningKey;

    public FmkClientIdws(
        PrivateKey fmkSigningKey,
        String endpoint
    ) throws URISyntaxException {
        this.serviceUri = new URI(endpoint);
        try {
            this.jaxbContext = JAXBContext.newInstance(
                "dk.dkma.medicinecard.xml_schema._2015._06._01"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e2"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e5"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                    + ":dk.sdsd.dgws._2012._06"
            );
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }

        this.fmkSigningKey = fmkSigningKey;
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
    public StartEffectuationResponseType startEffectuation(StartEffectuationRequestType request, EuropeanHcpIdwsToken token)
        throws JAXBException {
        return makeFmkRequest(
            facE5.createStartEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#StartEffectuation",
            StartEffectuationResponseType.class,
            token,
            false
        );
    }

    /**
     * "Afbryd ekspedition".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:afbryd_ekspedition">FMK documentation.</a>
     */
    public AbortEffectuationResponseType abortEffectuation(AbortEffectuationRequestType request, EuropeanHcpIdwsToken token)
        throws JAXBException {
        var result = makeFmkRequest(
            facE5.createAbortEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E5#AbortEffectuation",
            AbortEffectuationResponseType.class,
            token,
            false
        );

        if (result.getAbortEffectuationFailed() != null && !result.getAbortEffectuationFailed().isEmpty()) {
            throw new IllegalStateException("Abort failed. Messages: " + result.getAbortEffectuationFailed()
                .stream()
                .map(PrescriptionErrorType::getReasonText)
                .collect(Collectors.joining("; ")));
        }

        return result;
    }

    /**
     * "Opret effektuering på recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:opret_effektuering">FMK documentation.</a>
     */
    public CreatePharmacyEffectuationResponseType createPharmacyEffectuation(CreatePharmacyEffectuationRequestType request, EuropeanHcpIdwsToken token)
        throws JAXBException {
        return makeFmkRequest(
            facE2.createCreatePharmacyEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreatePharmacyEffectuation",
            CreatePharmacyEffectuationResponseType.class,
            token,
            false
        );

    }

    /**
     * "Tilbagefør effektuering på recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:tilbagefor_effektuering_pa_recept">FMK documentation.</a>
     */
    public UndoEffectuationResponseType undoEffectuation(UndoEffectuationRequestType request, EuropeanHcpIdwsToken token)
        throws JAXBException {
        return makeFmkRequest(
            facE5.createUndoEffectuationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E5#UndoEffectuation",
            UndoEffectuationResponseType.class,
            token,
            false
        );

    }

    /**
     * "Hent recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_recept">FMK documentation.</a>
     */
    public GetPrescriptionResponseType getPrescription(GetPrescriptionRequestType request, EuropeanHcpIdwsToken token) throws JAXBException {
        return makeFmkRequest(
            fac.createGetPrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription",
            GetPrescriptionResponseType.class,
            token,
            false
        );
    }

    /**
     * "Hent recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_laegemiddelordination">FMK documentation.</a>
     */
    public GetDrugMedicationResponseType getDrugMedication(GetDrugMedicationRequestType request, EuropeanHcpIdwsToken token) throws JAXBException {
        return makeFmkRequest(
            fac.createGetDrugMedicationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetDrugMedication",
            GetDrugMedicationResponseType.class,
            token,
            false
        );
    }

    /**
     * "Hent recept".
     * <a href="https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:hent_recept">FMK documentation.</a>
     */
    public GetPrescriptionResponseType getPrescriptionWithConsent(GetPrescriptionRequestType request, EuropeanHcpIdwsToken token) throws JAXBException {
        return makeFmkRequest(
            fac.createGetPrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription",
            GetPrescriptionResponseType.class,
            token,
            true
        );
    }

    public GetMedicineCardResponseType getMedicineCard(GetMedicineCardRequestType request, EuropeanHcpIdwsToken token) throws JAXBException {
        return makeFmkRequest(
            facE2.createGetMedicineCardRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetMedicineCard",
            GetMedicineCardResponseType.class,
            token,
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
        EuropeanHcpIdwsToken token,
        boolean requiresMedicineCardConsent
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        final Element body;
        Element[] extraHeaders;
        if (requiresMedicineCardConsent) {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getMedicineReviewConsent())};
        } else {
            extraHeaders = new Element[0];
        }
        try {
            body = NspClientIdws.request(
                serviceUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                token,
                this.fmkSigningKey,
                extraHeaders
            );
        } catch (Exception e) {
            throw new NspClientException("FMK request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(body, clazz).getValue();
    }
}
