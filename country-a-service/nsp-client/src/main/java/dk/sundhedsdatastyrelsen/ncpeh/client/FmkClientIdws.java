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
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import dk.sdsd.dgws._2012._06.ObjectFactory;
import dk.sdsd.dgws._2012._06.WhitelistingHeader;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;

@Component
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

    @Component
    public record Config(
        InputStream keystore,
        String keystoreAlias,
        String keystorePassword,
        String endpoint
    ) {
        @Autowired
        public Config(
            @Value("${app.fmk.keystore.path}") String keystorePath,
            @Value("${app.fmk.keystore.alias}") String keystoreAlias,
            @Value("${app.fmk.keystore.password}") String keystorePassword,
            @Value("${app.fmk.endpoint.url}") String endpoint
        ) throws IOException {
            this(new BufferedInputStream(Files.newInputStream(Path.of(keystorePath))), keystoreAlias, keystorePassword, endpoint);
        }
    }

    public FmkClientIdws(Config config) throws URISyntaxException, JAXBException, AuthenticationException, IOException {
        this.serviceUri = new URI(config.endpoint());
        this.jaxbContext = JAXBContext.newInstance(
            "dk.dkma.medicinecard.xml_schema._2015._06._01"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e2"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e5"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                + ":dk.sdsd.dgws._2012._06"
        );

        // Currently, we use the same certificate for STS and FMK, but that's not necessarily true always, so we allow
        // other values to be specified.
        var fmkAlias = config.keystoreAlias();
        if (fmkAlias == null)
            throw new IllegalArgumentException("Keystore alias must be set to send IDWS calls to FMK.");
        var fmkPassword = config.keystorePassword();
        if (fmkPassword == null)
            throw new IllegalArgumentException("Keystore password must be set to send IDWS calls to FMK.");
        var cert = CertificateUtils.loadCertificateFromKeystore(
            config.keystore(),
            fmkAlias,
            fmkPassword);
        this.fmkSigningKey = cert.privateKey();
        config.keystore.close();
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

    public GetMedicineCardResponseType getMedicineCard(GetMedicineCardRequestType request, EuropeanHcpIdwsToken token, PredefinedRequestedRole requestedRole) throws JAXBException {
        return makeFmkRequest(
            facE2.createGetMedicineCardRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetMedicineCard",
            GetMedicineCardResponseType.class,
            token,
            requestedRole,
            false
        );
    }

    public GetDrugMedicationResponseType getDrugMedication(GetDrugMedicationRequestType request, EuropeanHcpIdwsToken token, PredefinedRequestedRole requestedRole) throws JAXBException {
        return makeFmkRequest(
            fac.createGetDrugMedicationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#GetDrugMedication",
            GetDrugMedicationResponseType.class,
            token,
            requestedRole,
            false
        );
    }

    public CreateDrugMedicationResponseType createDrugMedication(
        CreateDrugMedicationRequestType request,
        EuropeanHcpIdwsToken token,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        return makeFmkRequest(
            facE2.createCreateDrugMedicationRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreateDrugMedication",
            CreateDrugMedicationResponseType.class,
            token,
            requestedRole,
            false
        );
    }

    public CreatePrescriptionResponseType createPrescription(
        CreatePrescriptionRequestType request,
        EuropeanHcpIdwsToken token,
        PredefinedRequestedRole requestedRole
    ) throws JAXBException {
        return makeFmkRequest(
            facE2.createCreatePrescriptionRequest(request),
            "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2#CreatePrescription",
            CreatePrescriptionResponseType.class,
            token,
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
        EuropeanHcpIdwsToken token,
        boolean requiresMedicineCardConsent
    ) throws JAXBException {
        return makeFmkRequest(request, soapAction, clazz, token, PredefinedRequestedRole.APOTEKER, requiresMedicineCardConsent);
    }

    private <RequestType, ResponseType> ResponseType makeFmkRequest(
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        EuropeanHcpIdwsToken token,
        PredefinedRequestedRole requestedRole,
        boolean requiresMedicineCardConsent
    ) throws JAXBException {
        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
        final Element body;
        Element[] extraHeaders;
        if (requiresMedicineCardConsent) {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole)), ClientUtils.toElement(jaxbContext, getMedicineReviewConsent())};
        } else {
            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole))};
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
}
