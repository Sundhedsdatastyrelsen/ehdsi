package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.PublicException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.config.OptOutConfig;
import dk.sundhedsdatastyrelsen.ncpeh.config.PatientSummaryConfig;
import dk.sundhedsdatastyrelsen.ncpeh.config.SemanticConfig;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DiscardDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForPatientSummaryDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindDocumentsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindPatientsResponseDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFetchDocumentRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFindEPrescriptionDocumentsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFindPatientsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.SubmitDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.optout.OptOutService;
import dk.sundhedsdatastyrelsen.ncpeh.optout.OptOutServiceImpl;
import dk.sundhedsdatastyrelsen.ncpeh.service.CprService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PatientSummaryService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService.PrescriptionFilter;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.Anonymizer;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import org.w3c.dom.Document;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class Controller {
    private final PrescriptionService prescriptionService;
    private final PatientSummaryService patientSummaryService;
    private final CprService cprService;
    private final AuthenticationService authenticationService;
    private final PatientSummaryConfig patientSummaryConfig;
    private final SemanticConfig semanticConfig;
    private final OptOutService optOutService;

    public Controller(
        PrescriptionService prescriptionService,
        PatientSummaryService patientSummaryService,
        CprService cprService,
        AuthenticationService authenticationService,
        OptOutConfig optOutConfig,
        PatientSummaryConfig patientSummaryConfig,
        SemanticConfig semanticConfig
    ) {
        this.prescriptionService = prescriptionService;
        this.patientSummaryService = patientSummaryService;
        this.cprService = cprService;
        this.authenticationService = authenticationService;
        this.patientSummaryConfig = patientSummaryConfig;
        this.semanticConfig = semanticConfig;

        if (!patientSummaryConfig.enabled()) {
            log.warn("Patient summary is disabled.");
        }

        if (!optOutConfig.enabled()) {
            log.warn("Opt-out integration is disabled. This should not happen in production!");
            this.optOutService = OptOutService.never();
        } else {
            this.optOutService = new OptOutServiceImpl(optOutConfig.config());
        }
    }

    @PostMapping(path = "/api/find-patients/")
    public FindPatientsResponseDto findPatients(
        @Valid @RequestBody PostFindPatientsRequestDto params
    ) {
        return cprService.findPatients(params.getPatientIds());
    }

    @PostMapping(path = "/api/find-eprescription-documents/")
    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        @Valid @RequestBody PostFindEPrescriptionDocumentsRequestDto params
    ) {
        checkOptOut(params.getPatientId(), OptOutService.Service.EPRESCRIPTION);
        var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.findEPrescriptionDocuments(
            params.getPatientId(),
            filter,
            this.getIdwsToken(params.getSoapHeader(), Audience.FMK));
    }

    /// There is only one patient summary per patient, but the MyHealth@EU api functions like a document repository, so
    /// we have to let them search for that one document. We could also have returned it directly in the NCP adapter,
    /// but we want all the business logic in national connector.
    @PostMapping(path = "/api/find-patient-summary-document/")
    public DocumentAssociationForPatientSummaryDocumentMetadataDto findPatientSummaryDocument(
        @Valid @RequestBody FindDocumentsRequestDto params
    ) {
        if (!patientSummaryConfig.enabled()) {
            throw new PublicException(500, "Patient summary is disabled");
        }

        checkOptOut(params.getPatientId(), OptOutService.Service.PATIENT_SUMMARY);
        return patientSummaryService.getDocumentMetadata(params.getPatientId());
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) {
        var repoId = params.getRepositoryId();
        if (Objects.equals(repoId, Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value)) {
            checkOptOut(params.getPatientId(), OptOutService.Service.EPRESCRIPTION);
            var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
            return prescriptionService.getPrescriptions(
                params.getPatientId(),
                filter,
                this.getIdwsToken(params.getSoapHeader(), Audience.FMK),
                semanticConfig.atcCodeSystemVersion()
            );
        } else if (Objects.equals(repoId, Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value)) {
            if (!patientSummaryConfig.enabled()) {
                throw new PublicException(500, "Patient summary is disabled");
            }

            checkOptOut(params.getPatientId(), OptOutService.Service.PATIENT_SUMMARY);
            return patientSummaryService.getPatientSummary(
                params.getPatientId(),
                params.getDocumentId(),
                this.getIdwsToken(params.getSoapHeader(), Audience.FMK),
                this.getIdwsToken(params.getSoapHeader(), Audience.DDV),
                // TODO pick out the right identity from the soap header.
                "MT^94e9cd39-f9c2-434c-9069-ee8bd81b11c1");
        }

        throw new ServerErrorException("Unknown repository id " + repoId, null);
    }

    @PostMapping(path = "/api/edispensation/submit")
    public void submitDispensation(
        @Valid @RequestBody SubmitDispensationRequestDto request
    ) {
        checkOptOut(request.getPatientId(), OptOutService.Service.EPRESCRIPTION);
        Document parsedRequestDocument;
        try {
            parsedRequestDocument = XmlUtils.parse(request.getDocument());
        } catch (XmlException e) {
            throw new PublicException(400, "Unable to read document sent from country of treatment", e);
        }

        try {
            prescriptionService.submitDispensation(
                request.getPatientId(),
                parsedRequestDocument,
                this.getIdwsToken(request.getSoapHeader(), Audience.FMK));
        } catch (Exception e) {
            log.error("Dispensation failed", e);
            try {
                log.info(Anonymizer.stripPersonalInformation(parsedRequestDocument));
            } catch (XmlException ex) {
                log.error("Could not remove personal information, so cannot print document.", ex);
            }
            throw e;
        }
    }

    @PostMapping(path = "/api/edispensation/discard")
    public void discardDispensation(
        @Valid @RequestBody DiscardDispensationRequestDto request
    ) {
        Document parsedRequestDocument;
        try {
            parsedRequestDocument = XmlUtils.parse(request.getDispensationToDiscard().getDocument());
        } catch (XmlException e) {
            throw new PublicException(400, "Could not read XML document in request", e);
        }

        try {
            prescriptionService.undoDispensation(
                request.getDiscardDispenseDetails().getPatientId(),
                parsedRequestDocument,
                this.getIdwsToken(request.getSoapHeader(), Audience.FMK));
        } catch (Exception e) {
            log.error("Dispensation discard failed.", e);
            try {
                log.info(Anonymizer.stripPersonalInformation(parsedRequestDocument));
            } catch (XmlException ex) {
                log.error("Could not remove personal information, so cannot print document.", ex);
            }
            throw e;
        }
    }

    private EuropeanHcpIdwsToken getIdwsToken(String soapHeader, Audience audience) {
        try {
            return this.authenticationService.xcaSoapHeaderToIdwsToken(soapHeader, audience.value);
        } catch (AuthenticationException e) {
            throw new PublicException(401, "Could not authenticate.", e);
        }
    }

    private void checkOptOut(String patientId, OptOutService.Service service) {
        if (optOutService.hasOptedOut(PatientIdMapper.toCpr(patientId), service)) {
            throw new PublicException(400, "Citizen has opted out of service: %s".formatted(service));
        }
    }

    enum Audience {
        FMK("https://fmk"),
        DDV("https://ddv");

        public final String value;

        Audience(String value) {
            this.value = value;
        }
    }
}
