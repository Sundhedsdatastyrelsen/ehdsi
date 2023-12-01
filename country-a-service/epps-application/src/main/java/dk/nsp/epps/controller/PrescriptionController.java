package dk.nsp.epps.controller;

import dk.nsp.epps.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.PrescriptionService;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @GetMapping(path = "/api/find-eprescription-documents/")
    public List<EPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        @Valid @RequestParam(value = "patientId", required = true) String patientId,
        @Valid @RequestParam(value = "repositoryId", required = false) String repositoryId,
        @Valid @RequestParam(value = "documentId", required = false) String documentId,
        @Valid @RequestParam(value = "maximumSize", required = false) Long maximumSize,
        @Valid @RequestParam(value = "createdBefore", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdBefore,
        @Valid @RequestParam(value = "createdAfter", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdAfter
    ) {
        var filter = new PrescriptionFilter(documentId, maximumSize, createdBefore, createdAfter);
        return prescriptionService.findEPrescriptionDocuments(patientId, filter);
    }

    @GetMapping(path = "/api/documents/")
    public List<EpsosDocumentDto> getDocuments(
        @Valid @RequestParam(value = "patientId", required = true) String patientId,
        @Valid @RequestParam(value = "repositoryId", required = false) String repositoryId,
        @Valid @RequestParam(value = "documentId", required = false) String documentId,
        @Valid @RequestParam(value = "maximumSize", required = false) Long maximumSize,
        @Valid @RequestParam(value = "createdBefore", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdBefore,
        @Valid @RequestParam(value = "createdAfter", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdAfter
    ) {
        var filter = new PrescriptionFilter(documentId, maximumSize, createdBefore, createdAfter);
        return prescriptionService.getPrescriptions(patientId, filter);
    }
}
