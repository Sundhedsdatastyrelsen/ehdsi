package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.sante.openncp.portal.model.PatientDetail;
import eu.europa.ec.sante.openncp.portal.model.PatientIdentityTrait;
import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorException;
import org.opensaml.saml.saml2.core.Assertion;

public interface PatientService {

    PatientDetail findPatientByTraits(PatientIdentityTrait patientIdentityTrait, String countryCode, Assertion... assertions) throws ClientConnectorException;
}
