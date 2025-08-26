package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.config.AuthenticationServiceConfig;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DisardDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForPatientSummaryDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindDocumentsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindPatientsResponseDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFetchDocumentRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFindEPrescriptionDocumentsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFindPatientsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.SubmitDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.CprService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PatientSummaryService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService.PrescriptionFilter;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.net.URI;
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

    public Controller(
        PrescriptionService prescriptionService,
        PatientSummaryService patientSummaryService,
        CprService cprService,
        AuthenticationServiceConfig authServiceConfig,
        NspDgwsIdentity.System systemIdentity
    ) {
        this.prescriptionService = prescriptionService;
        this.patientSummaryService = patientSummaryService;
        this.cprService = cprService;
        this.authenticationService = new AuthenticationService(
            URI.create(authServiceConfig.sosiStsUri()),
            authServiceConfig.signingCertificate().getCertificateAndKey(),
            authServiceConfig.issuer()
        );
        this.systemIdentity = systemIdentity;
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
        // TODO should maybe be a user identity instead?
        return patientSummaryService.getDocumentMetadata(params.getPatientId(), systemIdentity);
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) {
        var repoId = params.getRepositoryId();
        if (Objects.equals(repoId, Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value)) {
            var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
            return prescriptionService.getPrescriptions(params.getPatientId(), filter, this.getFmkToken(params.getSoapHeader()));
        } else if (Objects.equals(repoId, Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value)) {
            return patientSummaryService.getPatientSummary(
                params.getPatientId(),
                params.getDocumentId(),
                systemIdentity,
                // TODO pick out the right identity from the soap header.
                "MT^94e9cd39-f9c2-434c-9069-ee8bd81b11c1");
        }

        // TODO better exception
        throw new RuntimeException("Unknown repository id " + repoId);
    }

    @PostMapping(path = "/api/edispensation/submit")
    public void submitDispensation(
        @Valid @RequestBody SubmitDispensationRequestDto request
    ) {
        try {
            prescriptionService.submitDispensation(
                request.getPatientId(),
                Utils.readXmlDocument(request.getDocument()),
                this.getFmkToken(request.getSoapHeader()));
        } catch (SAXException e) {
            log.error("Could not read XML document in request", e);
        } catch (Exception e) {
            // TODO Logs here might contain patient information, should be stored somewhere with better security
            // TODO Logs are probably not the best tool for communicating this
            log.error(
                "Failed in handling dispensation request for patient id %s, with classcode {}", request.getPatientId(), request.getClassCode()
                    .toString());
            log.error("SOAP Header: {}", request.getSoapHeader());
            log.error("Request document: {}", request.getDocument());
            throw e;
        }
    }

    @PostMapping(path = "/api/edispensation/discard")
    public void discardDispensation(
        @Valid @RequestBody DisardDispensationRequestDto request
    ) {
        try {
            prescriptionService.undoDispensation(
                request.getDisardDispenseDetails()
                    .getPatientId(), Utils.readXmlDocument(request.getDispensationToDiscard()
                    .getDocument()), this.getFmkToken(request.getSoapHeader()));
        } catch (SAXException e) {
            log.error("Could not read XML document in request");
        } catch (Exception e) {
            // TODO Logs here might contain patient information, should be stored somewhere with better security
            // TODO Logs are probably not the best tool for communicating this
            log.error(
                "Failed in handling discard request for patient id %s, with classcode {}", request.getDispensationToDiscard()
                    .getPatientId(), request.getDispensationToDiscard().getClassCode()
                    .toString());
            log.error("SOAP Header: {}", request.getSoapHeader());
            log.error("Request document: {}", request.getDispensationToDiscard().getDocument());
            throw e;
        }
    }

    private EuropeanHcpIdwsToken getFmkToken(String soapHeader) {
        try {
            return this.authenticationService.xcaSoapHeaderToIdwsToken(soapHeader, "https://fmk");
        } catch (AuthenticationException e) {
            throw new CountryAException(HttpStatus.UNAUTHORIZED, "Could not authenticate.", e);
        }
    }
}
