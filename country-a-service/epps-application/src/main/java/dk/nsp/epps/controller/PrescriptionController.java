package dk.nsp.epps.controller;

import dk.nsp.epps.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.ncp.api.PostFetchDocumentRequestDto;
import dk.nsp.epps.ncp.api.PostFindEPrescriptionDocumentsRequestDto;
import dk.nsp.epps.service.PrescriptionService;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping(path = "/api/find-eprescription-documents/")
    public List<EPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        @Valid @RequestBody PostFindEPrescriptionDocumentsRequestDto params
    ) throws JAXBException, IOException, InterruptedException {
        var filter = new PrescriptionFilter(params.getDocumentId(), params.getMaximumSize(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.findEPrescriptionDocuments(params.getPatientId(), filter);
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) throws JAXBException, InterruptedException {
        var filter = new PrescriptionFilter(params.getDocumentId(), params.getMaximumSize(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.getPrescriptions(params.getPatientId(), filter);
    }
}
