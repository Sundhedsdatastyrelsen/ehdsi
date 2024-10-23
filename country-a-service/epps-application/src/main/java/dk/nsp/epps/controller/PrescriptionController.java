package dk.nsp.epps.controller;

import dk.nsp.epps.Utils;
import dk.nsp.epps.ncp.api.DisardDispensationRequestDto;
import dk.nsp.epps.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.ncp.api.PostFetchDocumentRequestDto;
import dk.nsp.epps.ncp.api.PostFindEPrescriptionDocumentsRequestDto;
import dk.nsp.epps.ncp.api.SubmitDispensationRequestDto;
import dk.nsp.epps.service.PrescriptionService;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.sds.ncp.cda.MapperException;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
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
    ) throws JAXBException, IOException, InterruptedException {
        var filter = new PrescriptionFilter(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.findEPrescriptionDocuments(params.getPatientId(), filter);
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) throws JAXBException {
        var filter = new PrescriptionFilter(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.getPrescriptions(params.getPatientId(), filter);
    }

    @PostMapping(path = "/api/edispensation/submit")
    public void submitDispensation(
        @Valid @RequestBody SubmitDispensationRequestDto request
    ) throws SAXException, MapperException {
        prescriptionService.submitDispensation(request.getPatientId(), Utils.readXmlDocument(request.getDocument()));
    }

    @PostMapping(path = "/api/edispensation/discard")
    public void discardDispensation(
        @Valid @RequestBody DisardDispensationRequestDto request
    ) throws SAXException, JAXBException, MapperException, XPathExpressionException {
        prescriptionService.undoDispensation(request.getDisardDispenseDetails()
            .getPatientId(), Utils.readXmlDocument(request.getDispensationToDiscard().getDocument()));
    }
}
