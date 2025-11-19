package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.config.OptOutConfig;
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
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class Controller {
    private final PrescriptionService prescriptionService;
    private final PatientSummaryService patientSummaryService;
    private final CprService cprService;
    private final AuthenticationService authenticationService;
    private final NspDgwsIdentity.System systemIdentity;
    private final OptOutService optOutService;

    public Controller(
        PrescriptionService prescriptionService,
        PatientSummaryService patientSummaryService,
        CprService cprService,
        AuthenticationService authenticationService,
        NspDgwsIdentity.System systemIdentity,
        OptOutConfig optOutConfig
    ) {
        this.prescriptionService = prescriptionService;
        this.patientSummaryService = patientSummaryService;
        this.cprService = cprService;
        this.authenticationService = authenticationService;
        this.systemIdentity = systemIdentity;

        if (optOutConfig.disabled()) {
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
        checkOptOut(PatientIdMapper.toCpr(params.getPatientId()), OptOutService.Service.EPRESCRIPTION);
        var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.findEPrescriptionDocuments(
            params.getPatientId(),
            filter,
            this.getFmkToken(params.getSoapHeader()));
    }

    /// There is only one patient summary per patient, but the MyHealth@EU api functions like a document repository, so
    /// we have to let them search for that one document. We could also have returned it directly in the NCP adapter,
    /// but we want all the business logic in this application.
    @PostMapping(path = "/api/find-patient-summary-document/")
    public DocumentAssociationForPatientSummaryDocumentMetadataDto findPatientSummaryDocument(
        @Valid @RequestBody FindDocumentsRequestDto params
    ) {
        checkOptOut(PatientIdMapper.toCpr(params.getPatientId()), OptOutService.Service.PATIENT_SUMMARY);
        // TODO should maybe be a user identity instead? It's not used in patient summary yet.
        return patientSummaryService.getDocumentMetadata(params.getPatientId(), systemIdentity);
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) {
        var repoId = params.getRepositoryId();
        if (Objects.equals(repoId, Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value)) {
            checkOptOut(PatientIdMapper.toCpr(params.getPatientId()), OptOutService.Service.EPRESCRIPTION);
            var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
            return prescriptionService.getPrescriptions(params.getPatientId(), filter, this.getFmkToken(params.getSoapHeader()));
        } else if (Objects.equals(repoId, Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value)) {
            checkOptOut(PatientIdMapper.toCpr(params.getPatientId()), OptOutService.Service.PATIENT_SUMMARY);
            return patientSummaryService.getPatientSummary(
                params.getPatientId(),
                params.getDocumentId(),
                systemIdentity,
                // TODO pick out the right identity from the soap header.
                "MT^94e9cd39-f9c2-434c-9069-ee8bd81b11c1");
        }

        throw new ServerErrorException("Unknown repository id " + repoId, null);
    }

    @PostMapping(path = "/api/edispensation/submit")
    public void submitDispensation(
        @Valid @RequestBody SubmitDispensationRequestDto request
    ) {
        checkOptOut(PatientIdMapper.toCpr(request.getPatientId()), OptOutService.Service.EPRESCRIPTION);
        try {
            prescriptionService.submitDispensation(
                request.getPatientId(),
                Utils.readXmlDocument(request.getDocument()),
                this.getFmkToken(request.getSoapHeader()));
        } catch (SAXException e) {
            log.error("Could not read XML document in request", e);
        } catch (Exception e) {
            log.error("Dispensation failed", e);
            // Debug logging so we can see the full document in development.
            log.debug("patient id {}, class code {}", request.getPatientId(), request.getClassCode().toString());
            log.debug("SOAP Header: {}", request.getSoapHeader());
            log.debug("Request document: {}", request.getDocument());
            throw e;
        }
    }

    @PostMapping(path = "/api/edispensation/discard")
    public void discardDispensation(
        @Valid @RequestBody DiscardDispensationRequestDto request
    ) {
        try {
            prescriptionService.undoDispensation(
                request.getDiscardDispenseDetails().getPatientId(),
                Utils.readXmlDocument(request.getDispensationToDiscard().getDocument()),
                this.getFmkToken(request.getSoapHeader()));
        } catch (SAXException e) {
            log.error("Could not read XML document in request", e);
        } catch (Exception e) {
            log.error("Dispensation discard failed.", e);
            // Debug logging so we can see the full document in development.
            log.debug(
                "patient id {}, class code {}",
                request.getDispensationToDiscard().getPatientId(),
                request.getDispensationToDiscard().getClassCode().toString());
            log.debug("SOAP Header: {}", request.getSoapHeader());
            log.debug("Request document: {}", request.getDispensationToDiscard().getDocument());
            throw e;
        }
    }

    private EuropeanHcpIdwsToken getFmkToken(String soapHeader) {
        try {
            return this.authenticationService.xcaSoapHeaderToIdwsToken(soapHeader, "https://fmk");
        } catch (AuthenticationException e) {
            throw new CountryAException(401, "Could not authenticate.", e);
        }
    }

    private void checkOptOut(String cpr, OptOutService.Service service) {
        if (optOutService.hasOptedOut(cpr, service)) {
            throw new CountryAException(400, "Citizen has opted out of service: %s".formatted(service));
        }
    }
}
