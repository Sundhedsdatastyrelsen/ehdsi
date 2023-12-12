package dk.openncp.nationalconnector.xca;

import dk.nsp.epps.ApiException;
import dk.openncp.nationalconnector.CountryAService;
import eu.epsos.protocolterminators.ws.server.common.NationalConnectorInterface;
import eu.epsos.protocolterminators.ws.server.exception.NIException;
import eu.epsos.protocolterminators.ws.server.xca.DocumentSearchInterface;
import eu.europa.ec.sante.ehdsi.constant.ClassCode;
import eu.europa.ec.sante.ehdsi.constant.error.OpenNCPErrorCode;
import eu.europa.ec.sante.ehdsi.openncp.assertionvalidator.exceptions.InsufficientRightsException;
import fi.kela.se.epsos.data.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import tr.com.srdc.epsos.util.XMLUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentSearch implements NationalConnectorInterface, DocumentSearchInterface {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);
    private Element soapHeader;

    static Long parseLong(String s) {
        return s == null ? null : Long.parseLong(s);
    }

    static OffsetDateTime parseOffsetDateTime(String s) {
        return s == null ? null : OffsetDateTime.parse(s);
    }

    static Date offsetDateTimeToDate(OffsetDateTime d) {
        return d == null ? null : Date.from(d.toInstant());
    }

    static EPSOSDocument getDocumentFromCountryA(SearchCriteria searchCriteria) throws NIException {
        try {
            logger.info("Retrieving document from Country A service...");
            final var docs = CountryAService.api().getDocuments(
                    searchCriteria.getCriteriaValue(SearchCriteria.Criteria.PATIENT_ID),
                    searchCriteria.getCriteriaValue(SearchCriteria.Criteria.REPOSITORY_ID),
                    searchCriteria.getCriteriaValue(SearchCriteria.Criteria.DOCUMENT_ID),
                    parseLong(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.MAXIMUM_SIZE)),
                    parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_BEFORE)),
                    parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_AFTER))
            );
            if (docs.isEmpty()) {
                logger.info("Empty response from Country A service.");
                return null;
            }
            logger.info("Successfully retrieved document from Country A service.");
            final var doc = docs.get(0);
            return DocumentFactory.createEPSOSDocument(
                    doc.getPatientId(),
                    ClassCode.getByCode(doc.getClassCode().getValue()),
                    XMLUtil.parseContent(doc.getDocument())
            );
        } catch (ApiException e) {
            if (e.getCode() == 0) {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Could not establish connection with Country A service");
            } else if (e.getCode() == 400) {
                return null;
            } else {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, String.format("Bad response from Country A service, status: %s, body: %s",
                        e.getCode(),
                        e.getResponseBody()
                ));
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Document from Country A service is invalid XML");
        }

    }

    @Override
    public DocumentAssociation<PSDocumentMetaData> getPSDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DocumentAssociation<MroDocumentMetaData> getMroDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    static List<DocumentAssociation<EPDocumentMetaData>> getEPDocumentListFromCountryA(SearchCriteria searchCriteria) throws NIException {
        try {
            logger.info("Querying Country A service for documents...");
            final var result = CountryAService.api().findEPrescriptionDocuments(
                    searchCriteria.getCriteriaValue(SearchCriteria.Criteria.PATIENT_ID),
                    searchCriteria.getCriteriaValue(SearchCriteria.Criteria.REPOSITORY_ID),
                    searchCriteria.getCriteriaValue(SearchCriteria.Criteria.DOCUMENT_ID),
                    parseLong(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.MAXIMUM_SIZE)),
                    parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_BEFORE)),
                    parseOffsetDateTime(searchCriteria.getCriteriaValue(SearchCriteria.Criteria.CREATED_AFTER))
            );
            logger.info("Got well-formed response from Country A service.");
            return result.stream()
                    .map(md -> DocumentFactory.createDocumentAssociation(
                            DocumentFactory.createEPDocumentXML(
                                    md.getId(),
                                    md.getPatientId(),
                                    offsetDateTimeToDate(md.getEffectiveTime()),
                                    md.getRepositoryId(),
                                    md.getTitle(),
                                    md.getAuthor(),
                                    md.getDescription(),
                                    md.getSize(),
                                    md.getHash()
                            ),
                            DocumentFactory.createEPDocumentPDF(
                                    md.getId(),
                                    md.getPatientId(),
                                    offsetDateTimeToDate(md.getEffectiveTime()),
                                    md.getRepositoryId(),
                                    md.getTitle(),
                                    md.getAuthor(),
                                    md.getDescription(),
                                    md.getSize() == null ? 0 : md.getSize(),
                                    md.getHash()
                            )))
                    .collect(Collectors.toList());
        } catch (ApiException e) {
            if (e.getCode() == 0) {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Could not establish connection with Country A service");
            } else {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, String.format("Bad response from Country A service, status: %s, body: %s",
                        e.getCode(),
                        e.getResponseBody()
                ));
            }
        }
    }
    @Override
    public List<DocumentAssociation<EPDocumentMetaData>> getEPDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return getEPDocumentListFromCountryA(searchCriteria);
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
        return getDocumentFromCountryA(searchCriteria);
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
        sc.addPatientId("DKCPR^^^1234567890");
//        sc.add(SearchCriteria.Criteria.DOCUMENT_ID, "21298478");
        var ds = new DocumentSearch();
        var result = ds.getEPDocumentList(sc);
    }
}
