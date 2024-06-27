package eu.europa.ec.sante.ehdsi.portal.service;

import epsos.openncp.protocolterminator.ClientConnectorConsumerException;
import eu.europa.ec.sante.ehdsi.portal.model.PatientDetail;
import eu.europa.ec.sante.ehdsi.portal.model.PatientIdentityTrait;
import org.opensaml.saml.saml2.core.Assertion;

public interface PatientService {

    PatientDetail findPatientByTraits(PatientIdentityTrait patientIdentityTrait, String countryCode, Assertion... assertions) throws ClientConnectorConsumerException;
}
