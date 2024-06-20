package eu.europa.ec.sante.openncp.portal.controller;

import eu.europa.ec.sante.openncp.portal.mock.MockService;
import eu.europa.ec.sante.openncp.portal.model.*;
import eu.europa.ec.sante.openncp.portal.service.DocumentService;
import eu.europa.ec.sante.openncp.portal.service.PatientService;
import eu.europa.ec.sante.openncp.portal.service.SecurityService;
import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorException;
import eu.europa.ec.sante.openncp.common.ClassCode;
import eu.europa.ec.sante.openncp.common.configuration.util.Constants;
import eu.europa.ec.sante.openncp.core.client.cdadisplaytool.CdaXSLTransformer;
import eu.europa.ec.sante.openncp.core.client.cdadisplaytool.exceptions.UITransformationException;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PortalController {

    private static final String SESSION_ATTR_HCP_ASSERTION = "HCP_ASSERT";
    private static final String SESSION_ATTR_NOK_ASSERTION = "NOK_ASSERT";
    private static final String SESSION_ATTR_TRC_ASSERTION = "TRC_ASSERT";
    private final Logger logger = LoggerFactory.getLogger(PortalController.class);
    private final PatientService patientService;
    private final DocumentService documentService;
    private final SecurityService securityService;
    private final HttpSession httpSession;
    private final MockService mockService;

    @Autowired
    public PortalController(PatientService patientService, DocumentService documentService,
                            SecurityService securityService, HttpSession httpSession, MockService mockService) {
        this.patientService = patientService;
        this.documentService = documentService;
        this.securityService = securityService;
        this.httpSession = httpSession;
        this.mockService = mockService;
    }

    @PostMapping(
            value = "/patient",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PatientDetail> findPatientByTraits(@RequestBody PatientIdentificationRequest patientIdentificationRequest) throws ClientConnectorException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] Find Patient Demographics: '{}'", patientIdentificationRequest.toString());
        }
        httpSession.removeAttribute(SESSION_ATTR_NOK_ASSERTION);
        httpSession.removeAttribute(SESSION_ATTR_TRC_ASSERTION);
        var clinicianAssertion = processClinicianAssertion();

        PatientDetail patientDetail;

        if (patientIdentificationRequest.getNextOfKinTrait() != null) {
            var nextOfKinAssertion = processNextOfKinAssertion(clinicianAssertion,
                    patientIdentificationRequest.getNextOfKinTrait());
            if (nextOfKinAssertion == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            patientDetail = patientService.findPatientByTraits(
                    patientIdentificationRequest.getPatientIdentityTrait(),
                    patientIdentificationRequest.getCountryCode(),
                    clinicianAssertion, nextOfKinAssertion);
            httpSession.setAttribute(SESSION_ATTR_NOK_ASSERTION, nextOfKinAssertion);
        } else {
            patientDetail = patientService.findPatientByTraits(
                    patientIdentificationRequest.getPatientIdentityTrait(),
                    patientIdentificationRequest.getCountryCode(),
                    clinicianAssertion);
            if (patientDetail != null) patientDetail.setNextOfKin(false);
        }
        if (patientDetail == null) {
            return ResponseEntity
                    .ok()
                    .body(null);
        }
        if (logger.isInfoEnabled()) {
            logger.info(patientDetail.toString());
        }

        return ResponseEntity
                .ok()
                .body(patientDetail);
    }

    @PostMapping(
            value = "/orcd",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrCDDocumentDetail> findOriginalClinicalDocument(@RequestBody ClinicalDocumentRequest clinicalDocumentRequest) throws ClientConnectorException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] List Original Clinical Documents: '{}' for Patient: '{}'",
                    clinicalDocumentRequest.getClassCodes(), clinicalDocumentRequest.getPatientTrait().toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion,
                clinicalDocumentRequest.getPatientTrait());
        logger.info("Find Original Clinical Documents for patient '{}' - Purpose of Use: '{}'", clinicalDocumentRequest.getPatientTrait().getPatientIdentifier(),
                clinicalDocumentRequest.getPatientTrait().getPurposeOfUse());
        httpSession.setAttribute(SESSION_ATTR_TRC_ASSERTION, treatmentConfirmationAssertion);

        OrCDDocumentDetail detail = documentService.findOriginalClinicalDocument(clinicianAssertion,
                null,
                treatmentConfirmationAssertion,
                clinicalDocumentRequest.getPatientTrait().getPatientIdentifier(),
                clinicalDocumentRequest.getPatientTrait().getPurposeOfUse(),
                clinicalDocumentRequest.getClassCodes(),
                clinicalDocumentRequest.getCountryCode(),
                clinicalDocumentRequest.getFilterParameters());

        return ResponseEntity
                .ok()
                .body(detail);
    }

    @GetMapping(value = "/ed/list")
    public ResponseEntity<List<MedicationDispensed>> findMedicationDispensed() {

        logger.info("[Portal] List Medications Dispensed by the System");
        String directoryName = Constants.EPSOS_PROPS_PATH + "integration/" + Constants.HOME_COMM_ID + "/medication";
        var folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles();

        List<MedicationDispensed> medicationDispensedList = Optional.ofNullable(listOfFiles).map(Arrays::asList).orElse(Collections.emptyList())
                .stream().filter(f -> f.isFile() && f.getName().endsWith(".xml")).map(this::loadMedicationDispensed)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(medicationDispensedList);
    }

    @PostMapping(
            value = "/ps",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DocumentDetail> findPatientSummary(@RequestBody PatientTrait patientTrait) throws ClientConnectorException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] List Patient Summary for Patient: '{}'", patientTrait.toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, patientTrait);

        Assertion nextOfKinAssertion = null;
        if (patientTrait.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }

        DocumentDetail detail = documentService.findPatientSummary(clinicianAssertion, nextOfKinAssertion,
                treatmentConfirmationAssertion, patientTrait.getPatientIdentifier(), patientTrait.getPurposeOfUse(),
                patientTrait.getCountryCode());

        return ResponseEntity
                .ok()
                .body(detail);
    }

    @PostMapping(
            value = "/ep",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EPDocumentDetail> findPrescription(@RequestBody PatientTrait patientTrait) throws ClientConnectorException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] List Prescription for Patient: '{}'", patientTrait.toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, patientTrait);
        httpSession.setAttribute(SESSION_ATTR_TRC_ASSERTION, treatmentConfirmationAssertion);
        Assertion nextOfKinAssertion = null;
        if (patientTrait.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }
        logger.info("Find ePrescriptions for patient '{}' - Purpose of Use: '{}'", patientTrait.getPatientIdentifier(),
                patientTrait.getPurposeOfUse());

        EPDocumentDetail detail = documentService.findPrescription(clinicianAssertion, nextOfKinAssertion, treatmentConfirmationAssertion,
                patientTrait.getPatientIdentifier(), patientTrait.getPurposeOfUse(), patientTrait.getCountryCode());

        return ResponseEntity
                .ok()
                .body(detail);
    }

    @GetMapping(value = "/document/type/list")
    public ResponseEntity<List<ClinicalDocumentType>> listClinicalDocumentTypes() {

        logger.info("[Portal] List Original Clinical Document Types");
        List<ClinicalDocumentType> clinicalDocumentTypes = new ArrayList<>();
        ClinicalDocumentEnum[] clinicalDocumentEnums = ClinicalDocumentEnum.values();
        for (ClinicalDocumentEnum clinicalDocumentEnum : clinicalDocumentEnums) {
            var clinicalDocumentType = new ClinicalDocumentType();
            clinicalDocumentType.setClassCode(clinicalDocumentEnum.classCode);
            clinicalDocumentType.setValue(clinicalDocumentEnum.value);
            clinicalDocumentType.setCodeSystemId(clinicalDocumentEnum.codeSystemId);
            clinicalDocumentType.setScope(clinicalDocumentEnum.scope);
            clinicalDocumentTypes.add(clinicalDocumentType);
        }

        return ResponseEntity
                .ok()
                .body(clinicalDocumentTypes);
    }

    @PostMapping(
            value = "/ps/retrieve",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClinicalDocumentContent> retrievePatientSummary(@RequestBody DocumentTrait documentTrait) throws ClientConnectorException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] Retrieve Patient Summary for Patient: '{}'", documentTrait.toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, documentTrait);

        Assertion nextOfKinAssertion = null;
        if (documentTrait.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }
        logger.info("Find Patient Summary '{}' for patient '{}'", documentTrait.getDocumentIdentifier(), documentTrait.getPatientIdentifier());
        ClinicalDocumentContent clinicalDocument = documentService.retrievePatientSummary(clinicianAssertion, nextOfKinAssertion, treatmentConfirmationAssertion,
                documentTrait.getPatientIdentifier(), documentTrait.getPurposeOfUse(), documentTrait.getRepositoryId(),
                documentTrait.getHomeCommunityId(), documentTrait.getDocumentIdentifier(), documentTrait.getCountryCode());

        return ResponseEntity
                .ok()
                .body(clinicalDocument);
    }

    @PostMapping(
            value = "/orcd/retrieve",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClinicalDocumentContent> retrieveOrCD(@RequestBody DocumentTrait documentTrait) throws ClientConnectorException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] Retrieve Original Clinical Document for Patient: '{}'", documentTrait.toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, documentTrait);

        Assertion nextOfKinAssertion = null;
        if (documentTrait.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }

        ClinicalDocumentContent clinicalDocument = documentService.retrieveOrCD(clinicianAssertion, nextOfKinAssertion, treatmentConfirmationAssertion,
                documentTrait.getPatientIdentifier(), documentTrait.getPurposeOfUse(), documentTrait.getRepositoryId(),
                documentTrait.getHomeCommunityId(), documentTrait.getDocumentIdentifier(), documentTrait.getCountryCode(), ClassCode.getByCode(documentTrait.getClassCode()));

        return ResponseEntity
                .ok()
                .body(clinicalDocument);
    }

    @PostMapping(
            value = "/ep/retrieve",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClinicalDocumentContent> retrievePrescription(@RequestBody DocumentTrait documentTrait) throws ClientConnectorException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] Retrieve Prescription for Patient: '{}'", documentTrait.toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, documentTrait);
        Assertion nextOfKinAssertion = null;
        if (documentTrait.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }
        logger.info("Find Patient Summary '{}' for patient '{}'", documentTrait.getDocumentIdentifier(), documentTrait.getPatientIdentifier());
        ClinicalDocumentContent clinicalDocument = documentService.retrievePrescription(clinicianAssertion, nextOfKinAssertion, treatmentConfirmationAssertion,
                documentTrait.getPatientIdentifier(), documentTrait.getPurposeOfUse(), documentTrait.getRepositoryId(),
                documentTrait.getHomeCommunityId(), documentTrait.getDocumentIdentifier(), documentTrait.getCountryCode());

        return ResponseEntity
                .ok()
                .body(clinicalDocument);
    }

    @PostMapping(
            value = "/ed/discard",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MedicationDispensed.DiscardResponse> discardDispensedMedication(@RequestBody DiscardRequest discardRequest) throws ClientConnectorException {

        logger.info("[Portal] Submit Discard Dispensed Medication: '{}'", discardRequest.getDispenseId());

        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, discardRequest);
        Assertion nextOfKinAssertion = null;

        if (discardRequest.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }

        var response = documentService.submitDiscard(clinicianAssertion, nextOfKinAssertion,
                treatmentConfirmationAssertion, discardRequest.getPatientIdentifier(), "EMERGENCY",
                discardRequest.getRepositoryId(), discardRequest.getHomeCommunityId(), discardRequest.getDispenseName(),
                discardRequest.getCountryCode(), discardRequest);
        var discardResponse = new MedicationDispensed.DiscardResponse();
        discardResponse.setStatus(response);

        return ResponseEntity
                .ok()
                .body(discardResponse);
    }

    @PostMapping(
            value = "/ep/dispense",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DispenseResponse> dispensePrescription(@RequestBody DispenseRequest dispenseRequest) throws ClientConnectorException {

        logger.info("[Portal] Submit Dispense Medication of Prescription: '{}'", dispenseRequest.getPrescriptionId());
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, dispenseRequest);
        Assertion nextOfKinAssertion = null;
        if (dispenseRequest.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }

        String response = documentService.submitDispense(clinicianAssertion,
                nextOfKinAssertion, treatmentConfirmationAssertion, dispenseRequest);

        var dispenseResponse = new DispenseResponse();
        dispenseResponse.setStatus(response);

        return ResponseEntity
                .ok()
                .body(dispenseResponse);
    }

    @PostMapping(
            value = "/ep/display",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.TEXT_HTML_VALUE})
    public ResponseEntity<String> displayEpDocument(@RequestBody DocumentTrait documentTrait) throws ClientConnectorException, UITransformationException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] Display EP Document: '{}'", documentTrait.toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, documentTrait);
        Assertion nextOfKinAssertion = null;
        if (documentTrait.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }
        ClinicalDocumentContent clinicalDocument = documentService.retrievePrescription(clinicianAssertion,
                nextOfKinAssertion, treatmentConfirmationAssertion, documentTrait.getPatientIdentifier(),
                documentTrait.getPurposeOfUse(), documentTrait.getRepositoryId(), documentTrait.getHomeCommunityId(),
                documentTrait.getDocumentIdentifier(), documentTrait.getCountryCode());

        var clinicalDocumentStream = new String(clinicalDocument.getContent(), StandardCharsets.UTF_8);

        return ResponseEntity
                .ok()
                .body(CdaXSLTransformer.getInstance().transform(clinicalDocumentStream, "en-GB", "/toto"));
    }

    @PostMapping(
            value = "/ps/display",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.TEXT_HTML_VALUE})
    public ResponseEntity<String> displayPsDocument(@RequestBody DocumentTrait documentTrait) throws ClientConnectorException, UITransformationException {

        if (logger.isInfoEnabled()) {
            logger.info("[Portal] Display PS Document: '{}'", documentTrait.toString());
        }
        var clinicianAssertion = processClinicianAssertion();
        var treatmentConfirmationAssertion = processTreatmentConfirmationAssertion(clinicianAssertion, documentTrait);
        Assertion nextOfKinAssertion = null;
        if (documentTrait.isNextOfKin()) {
            nextOfKinAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_NOK_ASSERTION);
        }
        ClinicalDocumentContent clinicalDocument = documentService.retrievePatientSummary(clinicianAssertion,
                nextOfKinAssertion, treatmentConfirmationAssertion, documentTrait.getPatientIdentifier(),
                documentTrait.getPurposeOfUse(), documentTrait.getRepositoryId(), documentTrait.getHomeCommunityId(),
                documentTrait.getDocumentIdentifier(), documentTrait.getCountryCode());

        var clinicalDocumentStream = new String(clinicalDocument.getContent(), StandardCharsets.UTF_8);

        return ResponseEntity
                .ok()
                .body(CdaXSLTransformer.getInstance().transform(clinicalDocumentStream, "en-GB", "/toto"));
    }

    private Assertion processClinicianAssertion() {

        if (httpSession.getAttribute(SESSION_ATTR_HCP_ASSERTION) != null) {
            return (Assertion) httpSession.getAttribute(SESSION_ATTR_HCP_ASSERTION);
        } else {
            var clinicianAssertion = mockService.generateClinicianToken();
            httpSession.setAttribute(SESSION_ATTR_HCP_ASSERTION, clinicianAssertion);
            return clinicianAssertion;
        }
    }

    private Assertion processNextOfKinAssertion(Assertion clinicianAssertion, NextOfKinTrait nextOfKinTrait) {
        return securityService.generateNextOfKinToken(clinicianAssertion, nextOfKinTrait);
    }

    private Assertion processTreatmentConfirmationAssertion(Assertion clinicianAssertion, PatientTrait trait) {

        if (logger.isInfoEnabled()) {
            logger.info("[TRC Assertion] Patient traits: '{}'", trait.toString());
        }
        String dispensePinCode = trait.getDispensationPinCode();
        String prescriptionId = trait.getPrescriptionId();
        var treatmentConfirmationAssertion = (Assertion) httpSession.getAttribute(SESSION_ATTR_TRC_ASSERTION);
        if (treatmentConfirmationAssertion == null) {
            if (StringUtils.isNotBlank(dispensePinCode) || StringUtils.isNotBlank(prescriptionId)) {
                treatmentConfirmationAssertion = securityService.generateTreatmentToken(clinicianAssertion,
                        trait.getPatientIdentifier(), trait.getPurposeOfUse(), prescriptionId, dispensePinCode);
            } else {
                treatmentConfirmationAssertion = securityService.generateTreatmentToken(clinicianAssertion,
                        trait.getPatientIdentifier(), trait.getPurposeOfUse());
            }
            httpSession.setAttribute(SESSION_ATTR_TRC_ASSERTION, treatmentConfirmationAssertion);
        }
        return treatmentConfirmationAssertion;
    }

    private MedicationDispensed loadMedicationDispensed(File file) {

        var medicationDispensed = new MedicationDispensed();

        try {
            medicationDispensed.setDocument(file.getName());

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagNameNS("*", "id");

            if (nodeList != null && nodeList.getLength() > 0 && nodeList.item(0) != null) {
                Element link = (Element) nodeList.item(0);
                String root = link.getAttribute("root");
                String extension = link.getAttribute("extension");
                String andRoot = root == null ? "" : "^";
                var dispenseId = (root == null ? "" : root) + (extension == null ? "" : andRoot + extension);
                medicationDispensed.setDispensedId(dispenseId);
            }

            nodeList = document.getElementsByTagNameNS("*", "effectiveTime");
            if (nodeList != null //
                    && nodeList.getLength() > 0 //
                    && nodeList.item(0) != null //
                    && ((Element) nodeList.item(0)).getAttribute("value") != null) {
                Element link = (Element) nodeList.item(0);
                String time = link.getAttribute("value");
                if (time.length() >= 14) {
                    medicationDispensed.setEffectiveTime(new SimpleDateFormat("yyyyMMddHHmmss")//
                            .parse(time.substring(0, 14)));
                } else if (time.length() >= 8) {
                    medicationDispensed.setEffectiveTime(new SimpleDateFormat("yyyyMMdd")//
                            .parse(time.substring(0, 8)));
                }
            }
        } catch (Exception e) {
            logger.error("Error getting country ids '{}'", e.getMessage(), e);
        }

        return medicationDispensed;
    }
}
