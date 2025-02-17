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
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.util.List;

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
        var filter = new PrescriptionFilter(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.findEPrescriptionDocuments(params.getPatientId(), filter, TestIdentities.apotekerChrisChristoffersen);
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) {
        var filter = new PrescriptionFilter(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.getPrescriptions(params.getPatientId(), filter, TestIdentities.apotekerChrisChristoffersen);
    }

    @PostMapping(path = "/api/edispensation/submit")
    public void submitDispensation(
        @Valid @RequestBody SubmitDispensationRequestDto request
    ) {
        try {
            prescriptionService.submitDispensation(request.getPatientId(), Utils.readXmlDocument(request.getDocument()), TestIdentities.apotekerChrisChristoffersen);
        } catch (SAXException e) {
            throw new DataRequirementException("Could not read XML document in request");
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
            throw new DataRequirementException("Could not read XML document in request");
        }
    }
}
