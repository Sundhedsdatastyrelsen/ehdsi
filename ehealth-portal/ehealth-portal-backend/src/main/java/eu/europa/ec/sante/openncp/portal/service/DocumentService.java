package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.sante.openncp.portal.model.*;
import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorException;
import eu.europa.ec.sante.openncp.common.ClassCode;
import org.opensaml.saml.saml2.core.Assertion;

import javax.annotation.Nullable;
import java.util.List;

public interface DocumentService {

    OrCDDocumentDetail findOriginalClinicalDocument(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                    String patientIdentifier, String purposeOfUse, List<String> classCodes,
                                                    String countryCode, FilterParameters filterParameters) throws ClientConnectorException;

    DocumentDetail findPatientSummary(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                      String patientIdentifier, String purposeOfUse, String countryCode) throws ClientConnectorException;

    EPDocumentDetail findPrescription(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                    String patientIdentifier, String purposeOfUse, String countryCode) throws ClientConnectorException;

    ClinicalDocumentContent retrievePatientSummary(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                   String patientIdentifier, String purposeOfUse, String repositoryId,
                                                   String homeCommunityId, String documentIdentifier, String countryCode) throws ClientConnectorException;

    ClinicalDocumentContent retrievePrescription(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                 String patientIdentifier, String purposeOfUse, String repositoryId,
                                                 String homeCommunityId, String documentIdentifier, String countryCode) throws ClientConnectorException;

    ClinicalDocumentContent retrieveOrCD(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                 String patientIdentifier, String purposeOfUse, String repositoryId,
                                                 String homeCommunityId, String documentIdentifier, String countryCode, ClassCode classCode) throws ClientConnectorException;

    String submitDispense(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                          DispenseRequest dispenseRequest) throws ClientConnectorException;

    String submitDiscard(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                         String patientIdentifier, String purposeOfUse, String repositoryId,
                         String homeCommunityId, String documentIdentifier, String countryCode, DiscardRequest discardRequest) throws ClientConnectorException;
}
