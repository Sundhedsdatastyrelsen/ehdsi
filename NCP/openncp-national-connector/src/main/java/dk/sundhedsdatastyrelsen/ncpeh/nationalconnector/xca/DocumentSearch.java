package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xca;

import dk.sundhedsdatastyrelsen.ncpeh.ApiException;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.PostFindEPrescriptionDocumentsRequest;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.PostFindPatientSummaryDocumentRequest;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.PostFetchDocumentRequest;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.CountryAService;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.Utils;
import eu.europa.ec.sante.openncp.common.ClassCode;
import eu.europa.ec.sante.openncp.common.error.OpenNCPErrorCode;
import eu.europa.ec.sante.openncp.core.common.ihe.NationalConnectorInterface;
import eu.europa.ec.sante.openncp.core.common.assertion.exceptions.InsufficientRightsException;
import eu.europa.ec.sante.openncp.core.common.ihe.datamodel.SimpleConfidentialityEnum;
import eu.europa.ec.sante.openncp.core.common.ihe.datamodel.xds.*;
import eu.europa.ec.sante.openncp.core.common.ihe.exception.NIException;
import eu.europa.ec.sante.openncp.core.common.ihe.transformation.util.XmlUtil;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xca.DocumentSearchInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentSearch implements NationalConnectorInterface, DocumentSearchInterface {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);
    private Element soapHeader;

    static EPSOSDocument getDocumentFromCountryA(SearchCriteria searchCriteria, Element soapHeader) throws NIException {
        try {
            logger.info("Retrieving document from Country A service...");
            final var request = new PostFetchDocumentRequest()
                .patientId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.PATIENT_ID))
                .repositoryId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.REPOSITORY_ID))
                .documentId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.DOCUMENT_ID))
                .maximumSize(Utils.parseLong(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.MAXIMUM_SIZE)))
                .createdBefore(Utils.parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_BEFORE)))
                .createdAfter(Utils.parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_AFTER)))
                .soapHeader(Utils.elementToString(soapHeader));

            final var docs = CountryAService.api().postFetchDocument(request);
            if (docs.isEmpty()) {
                logger.info("Empty response from Country A service.");
                return null;
            }
            logger.info("Successfully retrieved document from Country A service.");
            final var doc = docs.get(0);
            return DocumentFactory.createEPSOSDocument(
                doc.getPatientId(),
                ClassCode.getByCode(doc.getClassCode().getValue()),
                XmlUtil.parseContent(doc.getDocument())
            );
        } catch (ApiException e) {
            if (e.getCode() == 0) {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Could not establish connection with service");
            } else if (e.getCode() == 400) {
                throw new NIException(
                    OpenNCPErrorCode.ERROR_GENERIC,
                    String.format("Bad Request: %s", e.getResponseBody()));
            } else {
                throw new NIException(
                    OpenNCPErrorCode.ERROR_GENERIC, String.format(
                    "Error:, status: %s, body: %s",
                    e.getCode(),
                    e.getResponseBody()
                ));
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Document received was invalid XML");
        }
    }

    @Override
    public DocumentAssociation<PSDocumentMetaData> getPSDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        try {
            final var soapHeader = this.soapHeader;
            logger.info("Querying Country A service for patient summary documents...");
            // Patient summary has the same arguments as EP, so we use the same class.
            final var request = new PostFindPatientSummaryDocumentRequest()
                .patientId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.PATIENT_ID))
                .repositoryId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.REPOSITORY_ID))
                .documentId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.DOCUMENT_ID))
                .maximumSize(Utils.parseLong(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.MAXIMUM_SIZE)))
                .createdBefore(Utils.parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_BEFORE)))
                .createdAfter(Utils.parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_AFTER)))
                .soapHeader(Utils.elementToString(soapHeader));
            final var md = CountryAService.api().postFindPatientSummaryDocument(request);
            logger.info("Got well-formed response from Country A service.");
            final var l3 = md.getLevel3();
            // TODO L1 should not be the same as L3, fix once we've implemented L1
            final var l1 = md.getLevel3();
            return DocumentFactory.createDocumentAssociation(
                DocumentFactory.createPSDocumentXML(
                    l3.getId(),
                    l3.getPatientId(),
                    Utils.offsetDateTimeToDate(l3.getEffectiveTime()),
                    l3.getRepositoryId(),
                    l3.getTitle(),
                    l3.getAuthor(),
                    SimpleConfidentialityEnum.findByCode(l3.getConfidentiality().getConfidentialityCode()),
                    l3.getLanguage(),
                    l3.getSize(),
                    l3.getHash()
                ),
                DocumentFactory.createPSDocumentPDF(
                    l1.getId(),
                    l1.getPatientId(),
                    Utils.offsetDateTimeToDate(l1.getEffectiveTime()),
                    l1.getRepositoryId(),
                    l1.getTitle(),
                    l1.getAuthor(),
                    SimpleConfidentialityEnum.findByCode(l1.getConfidentiality().getConfidentialityCode()),
                    l1.getLanguage(),
                    l1.getSize(),
                    l1.getHash()
                ));
        } catch (ApiException e) {
            if (e.getCode() == 0) {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Could not establish connection with service");
            } else {
                throw new NIException(
                    OpenNCPErrorCode.ERROR_GENERIC, String.format(
                    "Error, status: %s, body: %s",
                    e.getCode(),
                    e.getResponseBody()
                ));
            }
        }
    }

    static List<DocumentAssociation<EPDocumentMetaData>> getEPDocumentListFromCountryA(SearchCriteria searchCriteria,
            Element soapHeader) throws NIException {
        try {
            logger.info("Querying Country A service for documents...");
            final var request = new PostFindEPrescriptionDocumentsRequest()
                .patientId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.PATIENT_ID))
                .repositoryId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.REPOSITORY_ID))
                .documentId(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.DOCUMENT_ID))
                .maximumSize(Utils.parseLong(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.MAXIMUM_SIZE)))
                .createdBefore(Utils.parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_BEFORE)))
                .createdAfter(Utils.parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_AFTER)))
                .soapHeader(Utils.elementToString(soapHeader));
            final var result = CountryAService.api().postFindEPrescriptionDocuments(request);
            logger.info("Got well-formed response from Country A service.");
            return result.stream()
                .map(md -> DocumentFactory.createDocumentAssociation(
                    DocumentFactory.createEPDocumentXML(
                        md.getLevel3().getId(),
                        md.getLevel3().getPatientId(),
                        Utils.offsetDateTimeToDate(md.getLevel3().getEffectiveTime()),
                        md.getLevel3().getRepositoryId(),
                        md.getLevel3().getTitle(),
                        md.getLevel3().getAuthor(),
                        md.getLevel3().getDescription(),
                        md.getLevel3().getProductCode(),
                        md.getLevel3().getProductName(),
                        new EpListParam(
                            md.getLevel3().getDispensable(),
                            md.getLevel3().getAtcCode(),
                            md.getLevel3().getAtcName(),
                            md.getLevel3().getDoseFormCode(),
                            md.getLevel3().getDoseFormName(),
                            md.getLevel3().getStrength(),
                            new SubstitutionMetadata(
                                md.getLevel3().getSubstitutionCode(),
                                md.getLevel3().getSubstitutionDisplayName())),
                        SimpleConfidentialityEnum.findByCode(md.getLevel3().getConfidentiality().getConfidentialityCode()),
                        md.getLevel3().getLanguage(),
                        md.getLevel3().getSize(),
                        md.getLevel3().getHash()
                    ),
                    DocumentFactory.createEPDocumentPDF(
                        md.getLevel1().getId(),
                        md.getLevel1().getPatientId(),
                        Utils.offsetDateTimeToDate(md.getLevel1().getEffectiveTime()),
                        md.getLevel1().getRepositoryId(),
                        md.getLevel1().getTitle(),
                        md.getLevel1().getAuthor(),
                        md.getLevel1().getDescription(),
                        md.getLevel1().getProductCode(),
                        md.getLevel1().getProductName(),
                        new EpListParam(
                            md.getLevel1().getDispensable(),
                            md.getLevel1().getAtcCode(),
                            md.getLevel1().getAtcName(),
                            md.getLevel1().getDoseFormCode(),
                            md.getLevel1().getDoseFormName(),
                            md.getLevel1().getStrength(),
                            new SubstitutionMetadata(
                                md.getLevel1().getSubstitutionCode(),
                                md.getLevel1().getSubstitutionDisplayName())),
                        SimpleConfidentialityEnum.findByCode(md.getLevel1().getConfidentiality().getConfidentialityCode()),
                        md.getLevel1().getLanguage(),
                        md.getLevel1().getSize(),
                        md.getLevel1().getHash()
                    )))
                .collect(Collectors.toList());
        } catch (ApiException e) {
            if (e.getCode() == 0) {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Could not establish connection with service");
            } else {
                throw new NIException(
                    OpenNCPErrorCode.ERROR_GENERIC, String.format(
                    "Error, status: %s, body: %s",
                    e.getCode(),
                    e.getResponseBody()
                ));
            }
        }
    }

    @Override
    public List<DocumentAssociation<EPDocumentMetaData>> getEPDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return getEPDocumentListFromCountryA(searchCriteria, this.soapHeader);
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDLaboratoryResultsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDHospitalDischargeReportsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDMedicalImagingReportsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDMedicalImagesDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public EPSOSDocument getDocument(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return getDocumentFromCountryA(searchCriteria, this.soapHeader);
    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }

    /**
     * For testing/debugging in IDE
     */
    public static void main(String[] args) throws NIException, InsufficientRightsException {
        var sc = new SearchCriteriaImpl();
        sc.addPatientId("1234567890");
//        sc.add(SearchCriteria.Criteria.DOCUMENT_ID, "21298478");
        var ds = new DocumentSearch();
        var result = ds.getEPDocumentList(sc);
    }
}
