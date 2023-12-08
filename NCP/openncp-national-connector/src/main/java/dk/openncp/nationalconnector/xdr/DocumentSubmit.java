package dk.openncp.nationalconnector.xdr;

import dk.openncp.nationalconnector.xca.DocumentSearch;
import eu.epsos.protocolterminators.ws.server.common.NationalConnectorInterface;
import eu.epsos.protocolterminators.ws.server.exception.NIException;
import eu.epsos.protocolterminators.ws.server.xdr.DocumentProcessingException;
import eu.epsos.protocolterminators.ws.server.xdr.DocumentSubmitInterface;
import eu.europa.ec.sante.ehdsi.openncp.assertionvalidator.exceptions.InsufficientRightsException;
import eu.europa.ec.sante.ehdsi.openncp.model.DiscardDispenseDetails;
import fi.kela.se.epsos.data.model.ConsentDocumentMetaData;
import fi.kela.se.epsos.data.model.DocumentAssociation;
import fi.kela.se.epsos.data.model.EPSOSDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;


public class DocumentSubmit implements NationalConnectorInterface, DocumentSubmitInterface {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);

    /**
     * For use when logging clinical and personal data
     */
    private static final Logger loggerClinical = LoggerFactory.getLogger("LOGGER_CLINICAL");

    private Element soapHeader;

    @Override
    public void submitDispensation(EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {

    }

    @Override
    public void cancelDispensation(DiscardDispenseDetails discardDispenseDetails, EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {

    }

    @Override
    public void submitPatientConsent(EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {

    }

    @Override
    public void cancelConsent(DocumentAssociation<ConsentDocumentMetaData> documentAssociation) throws NIException, InsufficientRightsException {

    }

    @Override
    public void submitHCER(EPSOSDocument epsosDocument) throws DocumentProcessingException {

    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }
}
