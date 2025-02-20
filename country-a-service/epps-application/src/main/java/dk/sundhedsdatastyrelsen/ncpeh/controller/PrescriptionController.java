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
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.util.List;

@RestController
public class PrescriptionController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PrescriptionController.class);
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
            logger.error("Could not read XML document in request", e);
        } catch (Exception e) {
            // The received dispensation is just a receipt, error handling needs to make sure the information is
            // communicated to relevant error handling parties - and not throw an error, since the NCP service does not
            // regard failure as anything other than the service being down.
            // TODO Logs here might contain patient information, should be stored somewhere with better security
            // TODO Logs are probably not the best tool for communicating this
            logger.error(String.format("Failed in handling dispensation request for patient id %s, with classcode %s", request.getPatientId(), request.getClassCode()
                .toString()));
            logger.error(String.format("SOAP Header: %s", request.getSoapHeader()));
            logger.error(String.format("Request document: %s", request.getDocument()));
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
            logger.error("Could not read XML document in request");
        } catch (Exception e) {
            // The received discard request is just a receipt, error handling needs to make sure the information is
            // communicated to relevant error handling parties - and not throw an error, since the NCP service does not
            // regard failure as anything other than the service being down.
            // TODO Logs here might contain patient information, should be stored somewhere with better security
            // TODO Logs are probably not the best tool for communicating this
            logger.error(String.format("Failed in handling discard request for patient id %s, with classcode %s", request.getDispensationToDiscard()
                .getPatientId(), request.getDispensationToDiscard().getClassCode()
                .toString()));
            logger.error(String.format("SOAP Header: %s", request.getSoapHeader()));
            logger.error(String.format("Request document: %s", request.getDispensationToDiscard().getDocument()));
        }
    }
}
