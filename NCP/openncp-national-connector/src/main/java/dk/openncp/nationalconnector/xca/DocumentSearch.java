package dk.openncp.nationalconnector.xca;

import eu.epsos.protocolterminators.ws.server.common.NationalConnectorInterface;
import eu.epsos.protocolterminators.ws.server.exception.NIException;
import eu.epsos.protocolterminators.ws.server.xca.DocumentSearchInterface;
import eu.europa.ec.sante.ehdsi.openncp.assertionvalidator.exceptions.InsufficientRightsException;
import fi.kela.se.epsos.data.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import java.util.List;

public class DocumentSearch implements NationalConnectorInterface, DocumentSearchInterface {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);

    /**
     * For use when logging clinical and personal data
     */
    private static final Logger loggerClinical = LoggerFactory.getLogger("LOGGER_CLINICAL");

    private Element soapHeader;

    @Override
    public DocumentAssociation<PSDocumentMetaData> getPSDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return null;
    }

    @Override
    public DocumentAssociation<MroDocumentMetaData> getMroDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return null;
    }

    @Override
    public List<DocumentAssociation<EPDocumentMetaData>> getEPDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        logger.info("[HERE] Normal logger hit!!!");
        loggerClinical.info("[HERE] Clinical logger hit!!!");
        return null;
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDLaboratoryResultsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return null;
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDHospitalDischargeReportsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return null;
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDMedicalImagingReportsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return null;
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDMedicalImagesDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        return null;
    }

    @Override
    public EPSOSDocument getDocument(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        logger.info("[getDocument] Normal logger hit!!!");
        loggerClinical.info("[getDocument] Clinical logger hit!!!");
        return null;
    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }
}
