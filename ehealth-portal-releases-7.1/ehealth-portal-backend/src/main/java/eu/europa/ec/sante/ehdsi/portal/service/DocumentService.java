package eu.europa.ec.sante.ehdsi.portal.service;

import epsos.openncp.protocolterminator.ClientConnectorConsumerException;
import eu.europa.ec.sante.ehdsi.constant.ClassCode;
import eu.europa.ec.sante.ehdsi.portal.model.*;
import org.opensaml.saml.saml2.core.Assertion;

import javax.annotation.Nullable;
import java.util.List;

public interface DocumentService {

    OrCDDocumentDetail findOriginalClinicalDocument(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                    String patientIdentifier, String purposeOfUse, List<String> classCodes,
                                                    String countryCode, FilterParameters filterParameters) throws ClientConnectorConsumerException;

    DocumentDetail findPatientSummary(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                      String patientIdentifier, String purposeOfUse, String countryCode) throws ClientConnectorConsumerException;

    EPDocumentDetail findPrescription(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                    String patientIdentifier, String purposeOfUse, String countryCode) throws ClientConnectorConsumerException;

    ClinicalDocumentContent retrievePatientSummary(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                   String patientIdentifier, String purposeOfUse, String repositoryId,
                                                   String homeCommunityId, String documentIdentifier, String countryCode) throws ClientConnectorConsumerException;

    ClinicalDocumentContent retrievePrescription(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                 String patientIdentifier, String purposeOfUse, String repositoryId,
                                                 String homeCommunityId, String documentIdentifier, String countryCode) throws ClientConnectorConsumerException;

    ClinicalDocumentContent retrieveOrCD(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                                                 String patientIdentifier, String purposeOfUse, String repositoryId,
                                                 String homeCommunityId, String documentIdentifier, String countryCode, ClassCode classCode) throws ClientConnectorConsumerException;

    String submitDispense(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                          DispenseRequest dispenseRequest) throws ClientConnectorConsumerException;

    String submitDiscard(Assertion clinicianAssertion, @Nullable Assertion nextOfKinAssertion, Assertion treatmentConfirmationAssertion,
                         String patientIdentifier, String purposeOfUse, String repositoryId,
                         String homeCommunityId, String documentIdentifier, String countryCode, DiscardRequest discardRequest) throws ClientConnectorConsumerException;
}
