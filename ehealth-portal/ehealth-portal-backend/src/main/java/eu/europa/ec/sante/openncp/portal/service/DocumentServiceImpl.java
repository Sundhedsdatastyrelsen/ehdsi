package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.sante.openncp.portal.model.*;
import eu.europa.ec.sante.openncp.portal.util.cda.util.CDAUtil;
import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorException;
import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorService;
import eu.europa.ec.sante.openncp.common.ClassCode;
import eu.europa.ec.sante.openncp.common.configuration.ConfigurationManagerFactory;
import eu.europa.ec.sante.openncp.common.configuration.util.Constants;
import eu.europa.ec.sante.openncp.common.error.OpenNCPErrorCode;
import eu.europa.ec.sante.openncp.common.util.DateUtil;
import eu.europa.ec.sante.openncp.core.client.Author;
import eu.europa.ec.sante.openncp.core.client.*;
import eu.europa.ec.sante.openncp.core.common.ihe.assertionvalidator.constants.AssertionEnum;
import eu.europa.ec.sante.openncp.core.common.constants.ihe.IheConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.*;

@Service
public class DocumentServiceImpl extends PortalService implements DocumentService {


    private final ClientConnectorService clientConnectorService;

    private ObjectFactory objectFactory = new ObjectFactory();


    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private static String createFullId(final String root, final String extension) {
        return extension + "^^^&" + root + "&ISO";
    }

    public DocumentServiceImpl(final ClientConnectorService clientConnectorService) {
        this.clientConnectorService = clientConnectorService;
    }

    @Override
    public OrCDDocumentDetail findOriginalClinicalDocument(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion,
                                                           Assertion treatmentConfirmationAssertion,
                                                           String patientIdentifier, String purposeOfUse, List<String> classCodes,
                                                           String countryCode, FilterParameters filterParameters) throws ClientConnectorException {

        logger.info("[Document Service] List ORcD type(s): '{}' from Patient ID: '{}' with FilterParameters '{}'",
                Arrays.toString(classCodes.toArray()), patientIdentifier, filterParameters.toString());
        List<GenericDocumentCode> genericDocumentCodes = new ArrayList<>();
        for (String classCode : classCodes) {
            var genericDocumentCode = objectFactory.createGenericDocumentCode();
            genericDocumentCode.setNodeRepresentation(classCode);
            genericDocumentCode.setSchema(IheConstants.CLASSCODE_SCHEME);
            genericDocumentCode.setValue(classCode);
            genericDocumentCodes.add(genericDocumentCode);
        }

        var filterParams = objectFactory.createFilterParams();
        if (filterParameters.getCreatedAfter() != null) {
            var createdAfter = Calendar.getInstance();
            createdAfter.setTime(filterParameters.getCreatedAfter());
            filterParams.setCreatedAfter(DateUtil.toCalendar(filterParameters.getCreatedAfter()));
        }
        if (filterParameters.getCreatedBefore() != null) {
            var createdBefore = Calendar.getInstance();
            createdBefore.setTime(filterParameters.getCreatedBefore());
            filterParams.setCreatedBefore(DateUtil.toCalendar(filterParameters.getCreatedBefore()));
        }
        if (filterParameters.getMaximumSize() != null) {
            filterParams.setMaximumSize(BigInteger.valueOf(filterParameters.getMaximumSize()));
        }
        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);
        return listOrCDDocument(assertionMap, patientIdentifier, purposeOfUse, genericDocumentCodes, countryCode, filterParams);
    }

    @Override
    public DocumentDetail findPatientSummary(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion,
                                             Assertion treatmentConfirmationAssertion, String patientIdentifier,
                                             String purposeOfUse, String countryCode) throws ClientConnectorException {

        GenericDocumentCode classCode = objectFactory.createGenericDocumentCode();
        classCode.setNodeRepresentation(ClassCode.PS_CLASSCODE.getCode());
        classCode.setSchema(IheConstants.CLASSCODE_SCHEME);
        classCode.setValue(Constants.PS_TITLE);

        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);

        return listClinicalDocument(assertionMap, patientIdentifier, purposeOfUse, List.of(classCode), countryCode, null);
    }

    @Override
    public EPDocumentDetail findPrescription(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion,
                                             Assertion treatmentConfirmationAssertion, String patientIdentifier,
                                             String purposeOfUse, String countryCode) throws ClientConnectorException {

        GenericDocumentCode classCode = objectFactory.createGenericDocumentCode();
        classCode.setNodeRepresentation(ClassCode.EP_CLASSCODE.getCode());
        classCode.setSchema(IheConstants.CLASSCODE_SCHEME);
        classCode.setValue(Constants.EP_TITLE);
        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);
        return listEPClinicalDocument(assertionMap, patientIdentifier, purposeOfUse, List.of(classCode), countryCode, null);
    }

    @Override
    public ClinicalDocumentContent retrievePatientSummary(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion,
                                                          Assertion treatmentConfirmationAssertion,
                                                          String patientIdentifier, String purposeOfUse, String repositoryId,
                                                          String homeCommunityId, String documentIdentifier, String countryCode) throws ClientConnectorException {

        GenericDocumentCode classCode = objectFactory.createGenericDocumentCode();
        classCode.setNodeRepresentation(ClassCode.PS_CLASSCODE.getCode());
        classCode.setSchema(IheConstants.CLASSCODE_SCHEME);
        classCode.setValue(Constants.PS_TITLE);
        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);
        return retrieveClinicalDocument(assertionMap, patientIdentifier, purposeOfUse, repositoryId, homeCommunityId,
                documentIdentifier, classCode, countryCode);
    }

    @Override
    public ClinicalDocumentContent retrievePrescription(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion,
                                                        Assertion treatmentConfirmationAssertion, String patientIdentifier,
                                                        String purposeOfUse, String repositoryId, String homeCommunityId,
                                                        String documentIdentifier, String countryCode) throws ClientConnectorException {

        GenericDocumentCode classCode = objectFactory.createGenericDocumentCode();
        classCode.setNodeRepresentation(ClassCode.EP_CLASSCODE.getCode());
        classCode.setSchema(IheConstants.CLASSCODE_SCHEME);
        classCode.setValue(Constants.EP_TITLE);

        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);

        return retrieveClinicalDocument(assertionMap, patientIdentifier, purposeOfUse, repositoryId, homeCommunityId,
                documentIdentifier, classCode, countryCode);
    }

    @Override
    public ClinicalDocumentContent retrieveOrCD(Assertion clinicianAssertion,
                                                @Nullable Assertion nextOfKinAssertion,
                                                Assertion treatmentConfirmationAssertion,
                                                String patientIdentifier,
                                                String purposeOfUse,
                                                String repositoryId,
                                                String homeCommunityId,
                                                String documentIdentifier,
                                                String countryCode,
                                                ClassCode classCode) throws ClientConnectorException {

        var genericDocumentCode = objectFactory.createGenericDocumentCode();
        genericDocumentCode.setNodeRepresentation(classCode.getCode());
        genericDocumentCode.setSchema(IheConstants.CLASSCODE_SCHEME);
        var title = "";
        switch (classCode) {
            case ORCD_HOSPITAL_DISCHARGE_REPORTS_CLASSCODE:
                title = Constants.ORCD_HOSPITAL_DISCHARGE_REPORTS_TITLE;
                break;
            case ORCD_LABORATORY_RESULTS_CLASSCODE:
                title = Constants.ORCD_LABORATORY_RESULTS_TITLE;
                break;
            case ORCD_MEDICAL_IMAGING_REPORTS_CLASSCODE:
                title = Constants.ORCD_MEDICAL_IMAGING_REPORTS_TITLE;
                break;
            case ORCD_MEDICAL_IMAGES_CLASSCODE:
                title = Constants.ORCD_MEDICAL_IMAGES_TITLE;
                break;
            default:
                logger.warn("Document ClassCode not supported: '{}'", classCode);
        }
        genericDocumentCode.setValue(title);

        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);

        return retrieveClinicalDocument(assertionMap, patientIdentifier, purposeOfUse,
                repositoryId, homeCommunityId, documentIdentifier, genericDocumentCode, countryCode);
    }

    private DocumentDetail listClinicalDocument(Map<AssertionEnum, Assertion> assertionMap, String patientIdentifier,
                                                String purposeOfUse, List<GenericDocumentCode> documentCodes,
                                                String countryCode, FilterParams filterParams) throws ClientConnectorException {

        //"'" + id + "^^^&" + homeCommunityId + "&" + "ISO'")
        String[] ids = StringUtils.split(patientIdentifier, "^^^&");

        PatientId id = objectFactory.createPatientId();
        id.setRoot(StringUtils.remove(ids[1], "&ISO"));
        id.setExtension(ids[0]);

        List<EpsosDocument> document1List = clientConnectorService.queryDocuments(
                assertionMap, countryCode, id, documentCodes, filterParams);
        var documentDetail = new DocumentDetail();
        if (assertionMap.containsKey(AssertionEnum.NEXT_OF_KIN)) {
            documentDetail.setNextOfKin(true);
        }
        documentDetail.setPatientIdentifier(patientIdentifier);
        for (EpsosDocument document1 : document1List) {
            var clinicalDocument = new ClinicalDocument();
            clinicalDocument.setRepositoryId(document1.getRepositoryId());
            clinicalDocument.setHomeCommunityId(document1.getHcid());
            clinicalDocument.setIdentifier(document1.getUuid());
            clinicalDocument.setName(document1.getTitle());
            clinicalDocument.setFormatCode(document1.getFormatCode().getValue());
            clinicalDocument.setMimeType(document1.getMimeType());

            documentDetail.getClinicalDocuments().add(clinicalDocument);
        }
        return documentDetail;
    }

    private EPDocumentDetail listEPClinicalDocument(Map<AssertionEnum, Assertion> assertionMap, String patientIdentifier,
                                                    String purposeOfUse, List<GenericDocumentCode> documentCodes, String countryCode,
                                                    FilterParams filterParams) throws ClientConnectorException {

        //"'" + id + "^^^&" + homeCommunityId + "&" + "ISO'")
        String[] ids = StringUtils.split(patientIdentifier, "^^^&");

        PatientId id = objectFactory.createPatientId();
        id.setRoot(StringUtils.remove(ids[1], "&ISO"));
        id.setExtension(ids[0]);

        List<EpsosDocument> documentList = clientConnectorService.queryDocuments(
                assertionMap, countryCode, id, documentCodes, filterParams);
        var documentDetail = new EPDocumentDetail();
        if (assertionMap.containsKey(AssertionEnum.NEXT_OF_KIN)) {
            documentDetail.setNextOfKin(true);
        }
        documentDetail.setPatientIdentifier(patientIdentifier);
        for (EpsosDocument document : documentList) {
            documentDetail.setAtcCode(document.getAtcCode());
            documentDetail.setAtcText(document.getAtcText());
            documentDetail.setDoseFormCode(document.getDoseFormCode());
            documentDetail.setDoseFormText(document.getDoseFormText());
            documentDetail.setStrength(document.getStrength());
            documentDetail.setSubstitution(document.getSubstitution());
            documentDetail.setDescription(document.getDescription());
            documentDetail.setDispensable(document.isDispensable());

            var clinicalDocument = new ClinicalDocument();
            clinicalDocument.setRepositoryId(document.getRepositoryId());
            clinicalDocument.setHomeCommunityId(document.getHcid());
            clinicalDocument.setIdentifier(document.getUuid());
            clinicalDocument.setName(document.getTitle());
            clinicalDocument.setFormatCode(document.getFormatCode().getValue());
            clinicalDocument.setMimeType(document.getMimeType());

            documentDetail.getClinicalDocuments().add(clinicalDocument);
        }
        return documentDetail;

    }

    private OrCDDocumentDetail listOrCDDocument(Map<AssertionEnum, Assertion> assertionMap, String patientIdentifier,
                                                String purposeOfUse, List<GenericDocumentCode> documentCodes,
                                                String countryCode, FilterParams filterParams) throws ClientConnectorException {

        //"'" + id + "^^^&" + homeCommunityId + "&" + "ISO'")
        String[] ids = StringUtils.split(patientIdentifier, "^^^&");

        PatientId id = objectFactory.createPatientId();
        id.setRoot(StringUtils.remove(ids[1], "&ISO"));
        id.setExtension(ids[0]);

        List<EpsosDocument> document1List = clientConnectorService.queryDocuments(
                assertionMap, countryCode, id, documentCodes, filterParams);
        var documentDetail = new OrCDDocumentDetail();
        documentDetail.setPatientIdentifier(patientIdentifier);
        for (EpsosDocument document : document1List) {

            var orCDDocument = new OriginalClinicalDocument();
            orCDDocument.setRepositoryId(document.getRepositoryId());
            if (document.getClassCode() != null) {
                orCDDocument.setClassCode(document.getClassCode().getNodeRepresentation());
            }
            orCDDocument.setHomeCommunityId(document.getHcid());
            orCDDocument.setIdentifier(document.getUuid());
            orCDDocument.setName(document.getTitle());
            orCDDocument.setFormatCode(document.getFormatCode().getValue());
            orCDDocument.setMimeType(document.getMimeType());
            orCDDocument.setSize(document.getSize());
            orCDDocument.setCreationDate(document.getCreationDate() != null ? document.getCreationDate().getTime() : null);
            orCDDocument.setEventDate(document.getEventDate() != null ? document.getEventDate().getTime() : null);
            for (Author author : document.getAuthors()) {
                String person = author.getPerson();
                var orCDAuthor = new eu.europa.ec.sante.openncp.portal.model.Author(person, author.getSpecialty());
                orCDDocument.getAuthors().add(orCDAuthor);
            }
            var reasonOfHospitalisation = document.getReasonOfHospitalisation();
            if (reasonOfHospitalisation != null) {
                var orCDReasonOfHospitalisation =
                        new eu.europa.ec.sante.openncp.portal.model.ReasonOfHospitalisation(reasonOfHospitalisation.getCode(),
                                reasonOfHospitalisation.getText());
                orCDDocument.setReasonOfHospitalisation(orCDReasonOfHospitalisation);
            }
            documentDetail.getClinicalDocuments().add(orCDDocument);
        }
        return documentDetail;

    }

    private ClinicalDocumentContent retrieveClinicalDocument(Map<AssertionEnum, Assertion> assertionMap,
                                                             String patientIdentifier, String purposeOfUse,
                                                             String repositoryId, String homeCommunityId,
                                                             String documentIdentifier, GenericDocumentCode documentCode,
                                                             String countryCode) throws ClientConnectorException {

        var documentId = objectFactory.createDocumentId();
        documentId.setDocumentUniqueId(documentIdentifier);
        documentId.setRepositoryUniqueId(repositoryId);

        EpsosDocument document1 = clientConnectorService.retrieveDocument(
                assertionMap, countryCode, documentId,
                homeCommunityId, documentCode, "fr-FR");

        var clinicalDocument = new ClinicalDocumentContent();
        clinicalDocument.setIdentifier(document1.getUuid());
        clinicalDocument.setName(document1.getTitle());
        clinicalDocument.setContent(document1.getBase64Binary());
        clinicalDocument.setMimeType(document1.getMimeType());
        clinicalDocument.setRepositoryId(repositoryId);
        clinicalDocument.setHomeCommunityId(homeCommunityId);
        if (document1.getFormatCode() != null) {
            clinicalDocument.setFormatCode(document1.getFormatCode().getValue());
        }
        if (assertionMap.containsKey(AssertionEnum.NEXT_OF_KIN)) {
            clinicalDocument.setNextOfKin(true);
        }
        return clinicalDocument;
    }

    @Override
    public String submitDispense(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion,
                                 Assertion treatmentConfirmationAssertion, DispenseRequest dispenseRequest) throws ClientConnectorException {

        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);

        var patientDemographics = dispenseRequest.getPatientDemographics();

        logger.info("Patient in session: '{}'", patientDemographics);

        var prescriptionId = dispenseRequest.getPrescriptionId();
        var clinicalDocumentContent = dispenseRequest.getClinicalDocumentContent();
        if (clinicalDocumentContent == null) {
            logger.error("Cannot retrieve ePrescription document '{}' from Session", prescriptionId);
            throw new ClientConnectorException(OpenNCPErrorCode.ERROR_ED_EPRESCRIPTION_NOT_IDENTIFIABLE, "Cannot retrieve ePrescription document from Session", null);
        }
        logger.info("Prescription ID in session: '{}'", StringUtils.isNotEmpty(clinicalDocumentContent.getIdentifier()) ?
                clinicalDocumentContent.getIdentifier() : "ePrescription N/A in Session");

        var documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document epDoc = null;
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            epDoc = documentBuilder.parse(new ByteArrayInputStream(clinicalDocumentContent.getContent()));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            logger.error("Error when parsing eP byteArray into DOM document");
        }

        var edUid = generateIdentifierExtension();
        var edOid = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_DISPENSATION_OID");
        byte[] fileContent = CDAUtil.generateDispensationDocument(dispenseRequest, epDoc, edUid);

        var document = buildDispenseDocument("My Testing Portal", edOid, edUid, fileContent);
        SubmitDocumentResponse documentResponse;

        documentResponse = clientConnectorService.submitDocument(
                assertionMap, dispenseRequest.getCountryCode(), document, patientDemographics);
        logger.info("Submit dispense status: '{}'", documentResponse.getResponseStatus());
        return documentResponse.getResponseStatus();
    }

    @Override
    public String submitDiscard(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion,
                                Assertion treatmentConfirmationAssertion, String patientIdentifier,
                                String purposeOfUse, String repositoryId, String homeCommunityId,
                                String documentIdentifier, String countryCode, DiscardRequest discardRequest) throws ClientConnectorException {

        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);
        if (nextOfKinAssertion != null) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, nextOfKinAssertion);
        }
        assertionMap.put(AssertionEnum.TREATMENT, treatmentConfirmationAssertion);

        PatientDemographics patientDemographics = objectFactory.createPatientDemographics();
        if (patientDemographics == null) {
            logger.error("Cannot retrieve Patient Demographics '{}' from Session", patientIdentifier);
            throw new ClientConnectorException(OpenNCPErrorCode.ERROR_ED_DISCARD_FAILED, "Empty Patient Demographics", null);
        }
        logger.info("Patient in session: '{}'", getSession().getAttribute(patientIdentifier));
        var file = loadMedication(documentIdentifier);
        var discardResponse = new MedicationDispensed.DiscardResponse();
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String eDUid = generateIdentifierExtension();
            String edOid = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_DISPENSATION_OID");
            var document = buildDiscardDispenseDocument("My Testing Portal", edOid, eDUid, fileContent);

            SubmitDocumentResponse documentResponse = clientConnectorService.submitDocument(assertionMap, countryCode,
                    document, patientDemographics);
            logger.info("Submit dispense status: '{}'", documentResponse.getResponseStatus());
            discardResponse.setStatus(documentResponse.getResponseStatus());

        } catch (IOException e) {
            logger.error("IOException: '{}'", e.getMessage());
            discardResponse.setStatus("Failed");
        }

        return discardResponse.getStatus();
    }

    private EpsosDocument buildDiscardDispenseDocument(String authorPerson, String dispenseRoot, String dispenseExtension,
                                                        byte[] dispense) {

        GenericDocumentCode classCode = objectFactory.createGenericDocumentCode();
        classCode.setNodeRepresentation(ClassCode.EDD_CLASSCODE.getCode());
        classCode.setSchema(IheConstants.CLASSCODE_SCHEME);
        classCode.setValue(Constants.ED_TITLE);

        // <formatCode>
        // <nodeRepresentation>urn:epsos:ep:dis:2010</nodeRepresentation>
        // <schema>epSOS formatCodes</schema>
        // <value>epSOS coded eDispensation</value>
        // </formatCode>

        // public static final String DISPLAY_NAME = "eHDSI coded eDispensation
        // Discard";
        // public static final String NODE_REPRESENTATION = "urn:eHDSI:ed:discard:2020";
        // public static final String CODING_SCHEME = "eHDSI formatCodes";
        GenericDocumentCode formatCode = objectFactory.createGenericDocumentCode();
        formatCode.setSchema("eHDSI formatCodes");
        formatCode.setNodeRepresentation("urn:eHDSI:ed:discard:2020");
        formatCode.setValue("eHDSI coded eDispensation Discard");

        EpsosDocument document = objectFactory.createEpsosDocument();

        var author = objectFactory.createAuthor();
        author.setPerson(authorPerson);
        document.getAuthors().add(author);
        var timeZone = TimeZone.getTimeZone("UTC");
        document.setCreationDate(Calendar.getInstance(timeZone));
        document.setDescription(Constants.ED_TITLE);
        document.setTitle(Constants.ED_TITLE);
        document.setUuid(dispenseRoot + "^" + dispenseExtension);
        document.setSubmissionSetId("2.1.2.3.4.5.6.7.8.9");
        document.setClassCode(classCode);
        document.setFormatCode(formatCode);
        document.setBase64Binary(dispense);

        return document;
    }

    private EpsosDocument buildDispenseDocument(String authorPerson, String dispenseRoot, String dispenseExtension, byte[] dispense) {

        GenericDocumentCode classCode = objectFactory.createGenericDocumentCode();
        classCode.setNodeRepresentation(ClassCode.ED_CLASSCODE.getCode());
        classCode.setSchema(IheConstants.CLASSCODE_SCHEME);
        classCode.setValue(Constants.ED_TITLE);

        GenericDocumentCode formatCode = objectFactory.createGenericDocumentCode();
        formatCode.setSchema(IheConstants.DISPENSATION_FORMATCODE_CODINGSCHEMA);
        formatCode.setNodeRepresentation(IheConstants.DISPENSATION_FORMATCODE_NODEREPRESENTATION);
        formatCode.setValue(IheConstants.DISPENSATION_FORMATCODE_DISPLAYNAME);

        EpsosDocument document = objectFactory.createEpsosDocument();
        var author = objectFactory.createAuthor();
        author.setPerson(authorPerson);
        document.getAuthors().add(author);
        var timeZone = TimeZone.getTimeZone("UTC");
        document.setCreationDate(Calendar.getInstance(timeZone));
        document.setDescription(Constants.ED_TITLE);
        document.setTitle(Constants.ED_TITLE);
        document.setUuid(dispenseRoot + "^" + dispenseExtension);
        document.setSubmissionSetId("2.1.2.3.4.5.6.7.8.9");
        document.setClassCode(classCode);
        document.setFormatCode(formatCode);
        document.setBase64Binary(dispense);

        return document;
    }

    private String generateIdentifierExtension() {

        Random secureRandom = new SecureRandom();
        var bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        var extension = Base64.encodeBase64String(bytes);
        return extension.substring(0, 16);
    }

    private File loadMedication(String documentId) {

        String directoryName = Constants.EPSOS_PROPS_PATH + "integration/" + Constants.HOME_COMM_ID + "/medication/"
                + documentId;
        return new File(directoryName);
    }

    private void writeFile(String dispenseId, byte[] document) {

        var directoryName = Constants.EPSOS_PROPS_PATH + "integration/" + Constants.HOME_COMM_ID + "/medication";
        String fileName = UUID.randomUUID() + ".xml";

        var directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        var file = new File(directoryName + "/" + fileName);
        var dispense = new String(document, StandardCharsets.UTF_8);
        try (var fileWriter = new FileWriter(file.getAbsoluteFile())) {
            var bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dispense);
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error("IOException: '{}'", e.getMessage(), e);
        }
    }
}
