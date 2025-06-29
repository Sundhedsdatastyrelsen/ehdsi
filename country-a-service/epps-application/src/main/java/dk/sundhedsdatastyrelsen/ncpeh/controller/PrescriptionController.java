package dk.sundhedsdatastyrelsen.ncpeh.controller;

import dk.sundhedsdatastyrelsen.ncpeh.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DisardDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFetchDocumentRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFindEPrescriptionDocumentsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.SubmitDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService.PrescriptionFilter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.util.List;

@Slf4j
@RestController
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping(path = "/api/find-eprescription-documents/")
    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        @Valid @RequestBody PostFindEPrescriptionDocumentsRequestDto params
    ) {
        var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.findEPrescriptionDocuments(params.getPatientId(), filter, TestIdentities.apotekerChrisChristoffersen);
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) {
        var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.getPrescriptions(params.getPatientId(), filter, TestIdentities.apotekerChrisChristoffersen);
    }

    @PostMapping(path = "/api/edispensation/submit")
    public void submitDispensation(
        @Valid @RequestBody SubmitDispensationRequestDto request
    ) {
        try {
            prescriptionService.submitDispensation(request.getPatientId(), Utils.readXmlDocument(request.getDocument()), TestIdentities.apotekerChrisChristoffersen);
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
                    .getDocument()), TestIdentities.apotekerChrisChristoffersen);
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
}
