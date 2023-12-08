package dk.openncp.nationalconnector.xcpd;

import dk.openncp.nationalconnector.xca.DocumentSearch;
import eu.epsos.protocolterminators.ws.server.common.NationalConnectorInterface;
import eu.epsos.protocolterminators.ws.server.exception.NIException;
import eu.epsos.protocolterminators.ws.server.xcpd.PatientSearchInterfaceWithDemographics;
import eu.europa.ec.sante.ehdsi.openncp.assertionvalidator.exceptions.InsufficientRightsException;
import org.opensaml.core.xml.io.MarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import tr.com.srdc.epsos.data.model.PatientDemographics;
import tr.com.srdc.epsos.data.model.PatientId;

import java.util.List;

public class PatientSearch implements NationalConnectorInterface, PatientSearchInterfaceWithDemographics {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);

    /**
     * For use when logging clinical and personal data
     */
    private static final Logger loggerClinical = LoggerFactory.getLogger("LOGGER_CLINICAL");

    private Element soapHeader;

    @Override
    public void setPatientDemographics(PatientDemographics patientDemographics) {

    }

    @Override
    public String getPatientId(String s) throws NIException, InsufficientRightsException {
        return null;
    }

    @Override
    public List<PatientDemographics> getPatientDemographics(List<PatientId> list) throws NIException, InsufficientRightsException, MarshallingException {
        logger.info("[HERE] Normal logger hit!!!");
        loggerClinical.info("[HERE] Clinical logger hit!!!");
        return null;
    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }
}
