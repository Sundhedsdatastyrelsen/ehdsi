package dk.nsp.epps.controller;

import dk.nsp.epps.Utils;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.ncp.api.DisardDispensationRequestDto;
import dk.nsp.epps.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.ncp.api.PostFetchDocumentRequestDto;
import dk.nsp.epps.ncp.api.PostFindEPrescriptionDocumentsRequestDto;
import dk.nsp.epps.ncp.api.SubmitDispensationRequestDto;
import dk.nsp.epps.service.PrescriptionService;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.service.exception.DataRequirementException;
import dk.sds.ncp.cda.MapperException;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBException;
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
    ) throws SAXException, JAXBException, MapperException {
        prescriptionService.undoDispensation(request.getDisardDispenseDetails()
            .getPatientId(), Utils.readXmlDocument(request.getDispensationToDiscard()
            .getDocument()), TestIdentities.apotekerChrisChristoffersen);
    }
}
