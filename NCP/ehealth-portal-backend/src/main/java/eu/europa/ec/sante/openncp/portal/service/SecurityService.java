package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.sante.openncp.portal.model.Concept;
import eu.europa.ec.sante.openncp.portal.model.NextOfKinTrait;
import org.opensaml.saml.saml2.core.Assertion;

import java.util.List;

public interface SecurityService {

    Assertion generateClinicianToken(String username, String fullName, String email, Concept role,
                                     String organization, String organizationId, String facilityType, String purposeOfUse,
                                     String locality, List<String> permissions, String onBehalf);

    Assertion generateNextOfKinToken(Assertion clinicianAssertion, NextOfKinTrait nextOfKinTrait);

    Assertion generateTreatmentToken(Assertion clinicianAssertion, String patientIdentifier, String purposeOfUse);

    Assertion generateTreatmentToken(Assertion clinicianAssertion, String patientIdentifier, String purposeOfUse,
                                     String prescriptionId, String dispensePinCode);
}
