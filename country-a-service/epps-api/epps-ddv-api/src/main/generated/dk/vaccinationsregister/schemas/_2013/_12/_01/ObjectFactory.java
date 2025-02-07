
package dk.vaccinationsregister.schemas._2013._12._01;

import java.math.BigDecimal;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dk.vaccinationsregister.schemas._2013._12._01 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _AuthorisationIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "AuthorisationIdentifier");
    private static final QName _OnBehalfOf_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "OnBehalfOf");
    private static final QName _SystemName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SystemName");
    private static final QName _ServiceName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ServiceName");
    private static final QName _Timing_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Timing");
    private static final QName _TimingList_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "TimingList");
    private static final QName _NegativeConsentRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "NegativeConsentRequest");
    private static final QName _GetVaccinationCardRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationCardRequest");
    private static final QName _VaccinationIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationIdentifier");
    private static final QName _VaccinationVersionIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationVersionIdentifier");
    private static final QName _VaccineIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccineIdentifier");
    private static final QName _VaccineName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccineName");
    private static final QName _DiseaseIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DiseaseIdentifier");
    private static final QName _DiseaseName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DiseaseName");
    private static final QName _DiseaseNameDK_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DiseaseNameDK");
    private static final QName _ATCCode_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ATCCode");
    private static final QName _ATCText_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ATCText");
    private static final QName _ATC_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ATC");
    private static final QName _Disease_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Disease");
    private static final QName _DrugIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugIdentifier");
    private static final QName _DrugName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugName");
    private static final QName _DrugFormCode_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugFormCode");
    private static final QName _DrugFormText_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugFormText");
    private static final QName _DrugForm_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugForm");
    private static final QName _DrugStrengthValue_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugStrengthValue");
    private static final QName _DrugStrengthUnitCode_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugStrengthUnitCode");
    private static final QName _DrugStrengthUnitText_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugStrengthUnitText");
    private static final QName _DrugStrengthText_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugStrengthText");
    private static final QName _DrugStrength_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DrugStrength");
    private static final QName _SSIDrug_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SSIDrug");
    private static final QName _VaccineKeywordsText_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccineKeywordsText");
    private static final QName _VaccinationPlanIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanIdentifier");
    private static final QName _VaccinationPlanName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanName");
    private static final QName _DisplayMinimumVaccinationPlan_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DisplayMinimumVaccinationPlan");
    private static final QName _Vaccine_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Vaccine");
    private static final QName _AuthorisedHealthcareProfessionalName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "AuthorisedHealthcareProfessionalName");
    private static final QName _SpecialityCode_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SpecialityCode");
    private static final QName _AuthorisedHealthcareProfessional_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "AuthorisedHealthcareProfessional");
    private static final QName _AddressLine_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "AddressLine");
    private static final QName _EmailAddress_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EmailAddress");
    private static final QName _OrganisationIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "OrganisationIdentifier");
    private static final QName _OrganisationName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "OrganisationName");
    private static final QName _OrganisationType_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "OrganisationType");
    private static final QName _TelephoneNumber_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "TelephoneNumber");
    private static final QName _Organisation_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Organisation");
    private static final QName _PatientFlag_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PatientFlag");
    private static final QName _RequestedRole_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "RequestedRole");
    private static final QName _PersonIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PersonIdentifier");
    private static final QName _PersonGivenName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PersonGivenName");
    private static final QName _PersonMiddleName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PersonMiddleName");
    private static final QName _PersonSurname_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PersonSurname");
    private static final QName _PersonName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PersonName");
    private static final QName _ModificatorPerson_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ModificatorPerson");
    private static final QName _EffectuatedByName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatedByName");
    private static final QName _EffectuatedByOrganisationName_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatedByOrganisationName");
    private static final QName _EffectuatedInCountryCode_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatedInCountryCode");
    private static final QName _PartlyDefinedEffectuator_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PartlyDefinedEffectuator");
    private static final QName _HealthInsuranceImportFlag_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "HealthInsuranceImportFlag");
    private static final QName _SystemUpdateFlag_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SystemUpdateFlag");
    private static final QName _Modified_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Modified");
    private static final QName _Created_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Created");
    private static final QName _Reported_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Reported");
    private static final QName _Reviewed_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Reviewed");
    private static final QName _BatchNumber_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "BatchNumber");
    private static final QName _CoverageDuration_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CoverageDuration");
    private static final QName _DosageOptionIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DosageOptionIdentifier");
    private static final QName _DosageText_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DosageText");
    private static final QName _VaccinationCredibilityEnum_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationCredibilityEnum");
    private static final QName _ServiceCode_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ServiceCode");
    private static final QName _ServiceCodeRemarkText_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ServiceCodeRemarkText");
    private static final QName _EffectuatedByOrganisationName2_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatedByOrganisationName2");
    private static final QName _EffectuatedByOrganisationType_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatedByOrganisationType");
    private static final QName _EffectuatedByOrganisationNumber_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatedByOrganisationNumber");
    private static final QName _Effectuated_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Effectuated");
    private static final QName _VaccinationPlanVersionIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanVersionIdentifier");
    private static final QName _VaccinationPlanItemIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanItemIdentifier");
    private static final QName _VaccinationPlanItemIndex_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanItemIndex");
    private static final QName _VaccinationPlanItemDescription_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanItemDescription");
    private static final QName _VaccinationPlanItemSeries_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanItemSeries");
    private static final QName _EffectuatedPlannedItem_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatedPlannedItem");
    private static final QName _NegativeConsentIndicator_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "NegativeConsentIndicator");
    private static final QName _Vaccination_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Vaccination");
    private static final QName _NegativeConsentStructure_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "NegativeConsentStructure");
    private static final QName _GetVaccinationCardResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationCardResponse");
    private static final QName _GetVaccinationCardIfUpdatedRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationCardIfUpdatedRequest");
    private static final QName _GetVaccinationCardIfUpdatedResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationCardIfUpdatedResponse");
    private static final QName _GetVaccinationHistoryRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationHistoryRequest");
    private static final QName _GetVaccinationHistoryResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationHistoryResponse");
    private static final QName _DeleteVaccination_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeleteVaccination");
    private static final QName _DeleteVaccinationRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeleteVaccinationRequest");
    private static final QName _VersionMismatchWarningIndicator_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VersionMismatchWarningIndicator");
    private static final QName _DeleteVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeleteVaccinationResponse");
    private static final QName _VaccinationCreate_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationCreate");
    private static final QName _CreateVaccinationRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreateVaccinationRequest");
    private static final QName _CreateVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreateVaccinationResponse");
    private static final QName _PreviousVaccinationCreate_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PreviousVaccinationCreate");
    private static final QName _CreatePreviousVaccinationRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreatePreviousVaccinationRequest");
    private static final QName _CreatePreviousVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreatePreviousVaccinationResponse");
    private static final QName _VaccinationUpdate_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationUpdate");
    private static final QName _UpdateVaccinationRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "UpdateVaccinationRequest");
    private static final QName _UpdateVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "UpdateVaccinationResponse");
    private static final QName _PlannedVaccinationIdentifier_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PlannedVaccinationIdentifier");
    private static final QName _VaccinationPlanItemMinimumInterval_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanItemMinimumInterval");
    private static final QName _PlannedVaccination_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PlannedVaccination");
    private static final QName _DeleteSubscriptionResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeleteSubscriptionResponse");
    private static final QName _CreateUnsubscriptionResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreateUnsubscriptionResponse");
    private static final QName _PersonVaccinationPlan_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PersonVaccinationPlan");
    private static final QName _DeleteUnsubscription_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeleteUnsubscription");
    private static final QName _DeleteUnsubscriptionRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeleteUnsubscriptionRequest");
    private static final QName _DeleteUnsubscriptionResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeleteUnsubscriptionResponse");
    private static final QName _DeletePlannedVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "DeletePlannedVaccinationResponse");
    private static final QName _GetUnsubscriptionsRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetUnsubscriptionsRequest");
    private static final QName _GetPlannedVaccinationsRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetPlannedVaccinationsRequest");
    private static final QName _PlannedVaccNegativeConsentStructure_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PlannedVaccNegativeConsentStructure");
    private static final QName _GetPlannedVaccinationsResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetPlannedVaccinationsResponse");
    private static final QName _UpdatePlannedVaccination_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "UpdatePlannedVaccination");
    private static final QName _UpdatePlannedVaccinationRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "UpdatePlannedVaccinationRequest");
    private static final QName _UpdatePlannedVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "UpdatePlannedVaccinationResponse");
    private static final QName _EffectuatePlannedVaccination_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatePlannedVaccination");
    private static final QName _EffectuatePlannedVaccinationRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatePlannedVaccinationRequest");
    private static final QName _EffectuatePlannedVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "EffectuatePlannedVaccinationResponse");
    private static final QName _CreateSinglePlannedVaccination_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreateSinglePlannedVaccination");
    private static final QName _CreateSinglePlannedVaccinationRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreateSinglePlannedVaccinationRequest");
    private static final QName _CreateSinglePlannedVaccinationResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreateSinglePlannedVaccinationResponse");
    private static final QName _CreatePlannedVaccination_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreatePlannedVaccination");
    private static final QName _SubscribeAndCreatePlannedVaccinations_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SubscribeAndCreatePlannedVaccinations");
    private static final QName _SubscribeAndCreatePlannedVaccinationsRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SubscribeAndCreatePlannedVaccinationsRequest");
    private static final QName _SubscribeAndCreatePlannedVaccinationsResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SubscribeAndCreatePlannedVaccinationsResponse");
    private static final QName _GetVaccinationCardAsPDFRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationCardAsPDFRequest");
    private static final QName _VaccinationCardAsPDF_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationCardAsPDF");
    private static final QName _GetVaccinationCardAsPDFResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationCardAsPDFResponse");
    private static final QName _VaccinationPlanCategory_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPlanCategory");
    private static final QName _GetVaccinationPassportAsPDFRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationPassportAsPDFRequest");
    private static final QName _VaccinationPassportAsPDF_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "VaccinationPassportAsPDF");
    private static final QName _GetVaccinationPassportAsPDFResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetVaccinationPassportAsPDFResponse");
    private static final QName _ValidateVaccinationPassportRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ValidateVaccinationPassportRequest");
    private static final QName _ValidateVaccinationPassportResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "ValidateVaccinationPassportResponse");
    private static final QName _PostalAddress_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PostalAddress");
    private static final QName _RequestVaccinationPassportHardCopyRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "RequestVaccinationPassportHardCopyRequest");
    private static final QName _RequestVaccinationPassportHardCopyResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "RequestVaccinationPassportHardCopyResponse");
    private static final QName _MultiUpdateOut_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "MultiUpdateOut");
    private static final QName _MultiUpdateResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "MultiUpdateResponse");
    private static final QName _GetAllPermissions_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetAllPermissions");
    private static final QName _GetCallersPermissions_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetCallersPermissions");
    private static final QName _GetCallersPermissionsToPerson_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetCallersPermissionsToPerson");
    private static final QName _GetPermissionsRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetPermissionsRequest");
    private static final QName _UndefinedPermission_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "UndefinedPermission");
    private static final QName _PredefinedPermission_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "PredefinedPermission");
    private static final QName _Permission_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Permission");
    private static final QName _RolesPermissions_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "RolesPermissions");
    private static final QName _GetPermissionsResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetPermissionsResponse");
    private static final QName _GetCovid19CertificateAsPDFRequest_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetCovid19CertificateAsPDFRequest");
    private static final QName _GetCovid19CertificateAsPDFResponse_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "GetCovid19CertificateAsPDFResponse");
    private static final QName _CreateVaccinationPlanSubscription_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "CreateVaccinationPlanSubscription");
    private static final QName _AuthorisedModificatorTypeOther_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Other");
    private static final QName _ModificatorTypeAuthorisedBy_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "AuthorisedBy");
    private static final QName _ModificatorTypePatient_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Patient");
    private static final QName _ModificatorTypeRole_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "Role");
    private static final QName _ModificatorTypeHealthInsuranceImport_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "HealthInsuranceImport");
    private static final QName _ModificatorTypeSystemUpdate_QNAME = new QName("http://vaccinationsregister.dk/schemas/2013/12/01", "SystemUpdate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.vaccinationsregister.schemas._2013._12._01
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OnBehalfOfType }
     * 
     * @return
     *     the new instance of {@link OnBehalfOfType }
     */
    public OnBehalfOfType createOnBehalfOfType() {
        return new OnBehalfOfType();
    }

    /**
     * Create an instance of {@link TimingType }
     * 
     * @return
     *     the new instance of {@link TimingType }
     */
    public TimingType createTimingType() {
        return new TimingType();
    }

    /**
     * Create an instance of {@link TimingListType }
     * 
     * @return
     *     the new instance of {@link TimingListType }
     */
    public TimingListType createTimingListType() {
        return new TimingListType();
    }

    /**
     * Create an instance of {@link GetVaccinationCardRequestType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationCardRequestType }
     */
    public GetVaccinationCardRequestType createGetVaccinationCardRequestType() {
        return new GetVaccinationCardRequestType();
    }

    /**
     * Create an instance of {@link ATCType }
     * 
     * @return
     *     the new instance of {@link ATCType }
     */
    public ATCType createATCType() {
        return new ATCType();
    }

    /**
     * Create an instance of {@link DiseaseType }
     * 
     * @return
     *     the new instance of {@link DiseaseType }
     */
    public DiseaseType createDiseaseType() {
        return new DiseaseType();
    }

    /**
     * Create an instance of {@link DrugFormType }
     * 
     * @return
     *     the new instance of {@link DrugFormType }
     */
    public DrugFormType createDrugFormType() {
        return new DrugFormType();
    }

    /**
     * Create an instance of {@link DrugStrengthType }
     * 
     * @return
     *     the new instance of {@link DrugStrengthType }
     */
    public DrugStrengthType createDrugStrengthType() {
        return new DrugStrengthType();
    }

    /**
     * Create an instance of {@link SSIDrugType }
     * 
     * @return
     *     the new instance of {@link SSIDrugType }
     */
    public SSIDrugType createSSIDrugType() {
        return new SSIDrugType();
    }

    /**
     * Create an instance of {@link DisplayMinimumVaccinationPlanType }
     * 
     * @return
     *     the new instance of {@link DisplayMinimumVaccinationPlanType }
     */
    public DisplayMinimumVaccinationPlanType createDisplayMinimumVaccinationPlanType() {
        return new DisplayMinimumVaccinationPlanType();
    }

    /**
     * Create an instance of {@link VaccineType }
     * 
     * @return
     *     the new instance of {@link VaccineType }
     */
    public VaccineType createVaccineType() {
        return new VaccineType();
    }

    /**
     * Create an instance of {@link AuthorisedHealthcareProfessionalType }
     * 
     * @return
     *     the new instance of {@link AuthorisedHealthcareProfessionalType }
     */
    public AuthorisedHealthcareProfessionalType createAuthorisedHealthcareProfessionalType() {
        return new AuthorisedHealthcareProfessionalType();
    }

    /**
     * Create an instance of {@link OrganisationIdentifierType }
     * 
     * @return
     *     the new instance of {@link OrganisationIdentifierType }
     */
    public OrganisationIdentifierType createOrganisationIdentifierType() {
        return new OrganisationIdentifierType();
    }

    /**
     * Create an instance of {@link OrganisationType }
     * 
     * @return
     *     the new instance of {@link OrganisationType }
     */
    public OrganisationType createOrganisationType() {
        return new OrganisationType();
    }

    /**
     * Create an instance of {@link PatientFlagType }
     * 
     * @return
     *     the new instance of {@link PatientFlagType }
     */
    public PatientFlagType createPatientFlagType() {
        return new PatientFlagType();
    }

    /**
     * Create an instance of {@link PersonNameType }
     * 
     * @return
     *     the new instance of {@link PersonNameType }
     */
    public PersonNameType createPersonNameType() {
        return new PersonNameType();
    }

    /**
     * Create an instance of {@link ModificatorPersonType }
     * 
     * @return
     *     the new instance of {@link ModificatorPersonType }
     */
    public ModificatorPersonType createModificatorPersonType() {
        return new ModificatorPersonType();
    }

    /**
     * Create an instance of {@link PartlyDefinedEffectuatorType }
     * 
     * @return
     *     the new instance of {@link PartlyDefinedEffectuatorType }
     */
    public PartlyDefinedEffectuatorType createPartlyDefinedEffectuatorType() {
        return new PartlyDefinedEffectuatorType();
    }

    /**
     * Create an instance of {@link HealthInsuranceImportFlagType }
     * 
     * @return
     *     the new instance of {@link HealthInsuranceImportFlagType }
     */
    public HealthInsuranceImportFlagType createHealthInsuranceImportFlagType() {
        return new HealthInsuranceImportFlagType();
    }

    /**
     * Create an instance of {@link SystemUpdateFlagType }
     * 
     * @return
     *     the new instance of {@link SystemUpdateFlagType }
     */
    public SystemUpdateFlagType createSystemUpdateFlagType() {
        return new SystemUpdateFlagType();
    }

    /**
     * Create an instance of {@link ModifiedType }
     * 
     * @return
     *     the new instance of {@link ModifiedType }
     */
    public ModifiedType createModifiedType() {
        return new ModifiedType();
    }

    /**
     * Create an instance of {@link CreatedType }
     * 
     * @return
     *     the new instance of {@link CreatedType }
     */
    public CreatedType createCreatedType() {
        return new CreatedType();
    }

    /**
     * Create an instance of {@link ReportedType }
     * 
     * @return
     *     the new instance of {@link ReportedType }
     */
    public ReportedType createReportedType() {
        return new ReportedType();
    }

    /**
     * Create an instance of {@link ReviewedType }
     * 
     * @return
     *     the new instance of {@link ReviewedType }
     */
    public ReviewedType createReviewedType() {
        return new ReviewedType();
    }

    /**
     * Create an instance of {@link EffectuatedType }
     * 
     * @return
     *     the new instance of {@link EffectuatedType }
     */
    public EffectuatedType createEffectuatedType() {
        return new EffectuatedType();
    }

    /**
     * Create an instance of {@link EffectuatedPlannedItemType }
     * 
     * @return
     *     the new instance of {@link EffectuatedPlannedItemType }
     */
    public EffectuatedPlannedItemType createEffectuatedPlannedItemType() {
        return new EffectuatedPlannedItemType();
    }

    /**
     * Create an instance of {@link VaccinationType }
     * 
     * @return
     *     the new instance of {@link VaccinationType }
     */
    public VaccinationType createVaccinationType() {
        return new VaccinationType();
    }

    /**
     * Create an instance of {@link NegativeConsentStructureType }
     * 
     * @return
     *     the new instance of {@link NegativeConsentStructureType }
     */
    public NegativeConsentStructureType createNegativeConsentStructureType() {
        return new NegativeConsentStructureType();
    }

    /**
     * Create an instance of {@link GetVaccinationCardResponseType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationCardResponseType }
     */
    public GetVaccinationCardResponseType createGetVaccinationCardResponseType() {
        return new GetVaccinationCardResponseType();
    }

    /**
     * Create an instance of {@link GetVaccinationCardIfUpdatedRequestType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationCardIfUpdatedRequestType }
     */
    public GetVaccinationCardIfUpdatedRequestType createGetVaccinationCardIfUpdatedRequestType() {
        return new GetVaccinationCardIfUpdatedRequestType();
    }

    /**
     * Create an instance of {@link GetVaccinationCardIfUpdatedResponseType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationCardIfUpdatedResponseType }
     */
    public GetVaccinationCardIfUpdatedResponseType createGetVaccinationCardIfUpdatedResponseType() {
        return new GetVaccinationCardIfUpdatedResponseType();
    }

    /**
     * Create an instance of {@link GetVaccinationHistoryRequestType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationHistoryRequestType }
     */
    public GetVaccinationHistoryRequestType createGetVaccinationHistoryRequestType() {
        return new GetVaccinationHistoryRequestType();
    }

    /**
     * Create an instance of {@link GetVaccinationHistoryResponseType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationHistoryResponseType }
     */
    public GetVaccinationHistoryResponseType createGetVaccinationHistoryResponseType() {
        return new GetVaccinationHistoryResponseType();
    }

    /**
     * Create an instance of {@link DeleteVaccinationType }
     * 
     * @return
     *     the new instance of {@link DeleteVaccinationType }
     */
    public DeleteVaccinationType createDeleteVaccinationType() {
        return new DeleteVaccinationType();
    }

    /**
     * Create an instance of {@link DeleteVaccinationRequestType }
     * 
     * @return
     *     the new instance of {@link DeleteVaccinationRequestType }
     */
    public DeleteVaccinationRequestType createDeleteVaccinationRequestType() {
        return new DeleteVaccinationRequestType();
    }

    /**
     * Create an instance of {@link VersionMismatchWarningIndicatorType }
     * 
     * @return
     *     the new instance of {@link VersionMismatchWarningIndicatorType }
     */
    public VersionMismatchWarningIndicatorType createVersionMismatchWarningIndicatorType() {
        return new VersionMismatchWarningIndicatorType();
    }

    /**
     * Create an instance of {@link DeleteVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link DeleteVaccinationResponseType }
     */
    public DeleteVaccinationResponseType createDeleteVaccinationResponseType() {
        return new DeleteVaccinationResponseType();
    }

    /**
     * Create an instance of {@link VaccinationCreateType }
     * 
     * @return
     *     the new instance of {@link VaccinationCreateType }
     */
    public VaccinationCreateType createVaccinationCreateType() {
        return new VaccinationCreateType();
    }

    /**
     * Create an instance of {@link CreateVaccinationRequestType }
     * 
     * @return
     *     the new instance of {@link CreateVaccinationRequestType }
     */
    public CreateVaccinationRequestType createCreateVaccinationRequestType() {
        return new CreateVaccinationRequestType();
    }

    /**
     * Create an instance of {@link CreateVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link CreateVaccinationResponseType }
     */
    public CreateVaccinationResponseType createCreateVaccinationResponseType() {
        return new CreateVaccinationResponseType();
    }

    /**
     * Create an instance of {@link PreviousVaccinationCreateType }
     * 
     * @return
     *     the new instance of {@link PreviousVaccinationCreateType }
     */
    public PreviousVaccinationCreateType createPreviousVaccinationCreateType() {
        return new PreviousVaccinationCreateType();
    }

    /**
     * Create an instance of {@link CreatePreviousVaccinationRequestType }
     * 
     * @return
     *     the new instance of {@link CreatePreviousVaccinationRequestType }
     */
    public CreatePreviousVaccinationRequestType createCreatePreviousVaccinationRequestType() {
        return new CreatePreviousVaccinationRequestType();
    }

    /**
     * Create an instance of {@link CreatePreviousVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link CreatePreviousVaccinationResponseType }
     */
    public CreatePreviousVaccinationResponseType createCreatePreviousVaccinationResponseType() {
        return new CreatePreviousVaccinationResponseType();
    }

    /**
     * Create an instance of {@link VaccinationUpdateType }
     * 
     * @return
     *     the new instance of {@link VaccinationUpdateType }
     */
    public VaccinationUpdateType createVaccinationUpdateType() {
        return new VaccinationUpdateType();
    }

    /**
     * Create an instance of {@link UpdateVaccinationRequestType }
     * 
     * @return
     *     the new instance of {@link UpdateVaccinationRequestType }
     */
    public UpdateVaccinationRequestType createUpdateVaccinationRequestType() {
        return new UpdateVaccinationRequestType();
    }

    /**
     * Create an instance of {@link UpdateVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link UpdateVaccinationResponseType }
     */
    public UpdateVaccinationResponseType createUpdateVaccinationResponseType() {
        return new UpdateVaccinationResponseType();
    }

    /**
     * Create an instance of {@link PlannedVaccinationType }
     * 
     * @return
     *     the new instance of {@link PlannedVaccinationType }
     */
    public PlannedVaccinationType createPlannedVaccinationType() {
        return new PlannedVaccinationType();
    }

    /**
     * Create an instance of {@link DeleteSubscriptionResponseType }
     * 
     * @return
     *     the new instance of {@link DeleteSubscriptionResponseType }
     */
    public DeleteSubscriptionResponseType createDeleteSubscriptionResponseType() {
        return new DeleteSubscriptionResponseType();
    }

    /**
     * Create an instance of {@link CreateUnsubscriptionResponseType }
     * 
     * @return
     *     the new instance of {@link CreateUnsubscriptionResponseType }
     */
    public CreateUnsubscriptionResponseType createCreateUnsubscriptionResponseType() {
        return new CreateUnsubscriptionResponseType();
    }

    /**
     * Create an instance of {@link PersonVaccinationPlanType }
     * 
     * @return
     *     the new instance of {@link PersonVaccinationPlanType }
     */
    public PersonVaccinationPlanType createPersonVaccinationPlanType() {
        return new PersonVaccinationPlanType();
    }

    /**
     * Create an instance of {@link DeleteUnsubscriptionType }
     * 
     * @return
     *     the new instance of {@link DeleteUnsubscriptionType }
     */
    public DeleteUnsubscriptionType createDeleteUnsubscriptionType() {
        return new DeleteUnsubscriptionType();
    }

    /**
     * Create an instance of {@link DeleteUnsubscriptionRequestType }
     * 
     * @return
     *     the new instance of {@link DeleteUnsubscriptionRequestType }
     */
    public DeleteUnsubscriptionRequestType createDeleteUnsubscriptionRequestType() {
        return new DeleteUnsubscriptionRequestType();
    }

    /**
     * Create an instance of {@link DeleteUnsubscriptionResponseType }
     * 
     * @return
     *     the new instance of {@link DeleteUnsubscriptionResponseType }
     */
    public DeleteUnsubscriptionResponseType createDeleteUnsubscriptionResponseType() {
        return new DeleteUnsubscriptionResponseType();
    }

    /**
     * Create an instance of {@link DeletePlannedVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link DeletePlannedVaccinationResponseType }
     */
    public DeletePlannedVaccinationResponseType createDeletePlannedVaccinationResponseType() {
        return new DeletePlannedVaccinationResponseType();
    }

    /**
     * Create an instance of {@link GetUnsubscriptionsRequestType }
     * 
     * @return
     *     the new instance of {@link GetUnsubscriptionsRequestType }
     */
    public GetUnsubscriptionsRequestType createGetUnsubscriptionsRequestType() {
        return new GetUnsubscriptionsRequestType();
    }

    /**
     * Create an instance of {@link GetPlannedVaccinationsRequestType }
     * 
     * @return
     *     the new instance of {@link GetPlannedVaccinationsRequestType }
     */
    public GetPlannedVaccinationsRequestType createGetPlannedVaccinationsRequestType() {
        return new GetPlannedVaccinationsRequestType();
    }

    /**
     * Create an instance of {@link PlannedVaccNegativeConsentStructureType }
     * 
     * @return
     *     the new instance of {@link PlannedVaccNegativeConsentStructureType }
     */
    public PlannedVaccNegativeConsentStructureType createPlannedVaccNegativeConsentStructureType() {
        return new PlannedVaccNegativeConsentStructureType();
    }

    /**
     * Create an instance of {@link GetPlannedVaccinationsResponseType }
     * 
     * @return
     *     the new instance of {@link GetPlannedVaccinationsResponseType }
     */
    public GetPlannedVaccinationsResponseType createGetPlannedVaccinationsResponseType() {
        return new GetPlannedVaccinationsResponseType();
    }

    /**
     * Create an instance of {@link UpdatePlannedVaccinationType }
     * 
     * @return
     *     the new instance of {@link UpdatePlannedVaccinationType }
     */
    public UpdatePlannedVaccinationType createUpdatePlannedVaccinationType() {
        return new UpdatePlannedVaccinationType();
    }

    /**
     * Create an instance of {@link UpdatePlannedVaccinationRequestType }
     * 
     * @return
     *     the new instance of {@link UpdatePlannedVaccinationRequestType }
     */
    public UpdatePlannedVaccinationRequestType createUpdatePlannedVaccinationRequestType() {
        return new UpdatePlannedVaccinationRequestType();
    }

    /**
     * Create an instance of {@link UpdatePlannedVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link UpdatePlannedVaccinationResponseType }
     */
    public UpdatePlannedVaccinationResponseType createUpdatePlannedVaccinationResponseType() {
        return new UpdatePlannedVaccinationResponseType();
    }

    /**
     * Create an instance of {@link EffectuatePlannedVaccinationType }
     * 
     * @return
     *     the new instance of {@link EffectuatePlannedVaccinationType }
     */
    public EffectuatePlannedVaccinationType createEffectuatePlannedVaccinationType() {
        return new EffectuatePlannedVaccinationType();
    }

    /**
     * Create an instance of {@link EffectuatePlannedVaccinationRequestType }
     * 
     * @return
     *     the new instance of {@link EffectuatePlannedVaccinationRequestType }
     */
    public EffectuatePlannedVaccinationRequestType createEffectuatePlannedVaccinationRequestType() {
        return new EffectuatePlannedVaccinationRequestType();
    }

    /**
     * Create an instance of {@link EffectuatePlannedVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link EffectuatePlannedVaccinationResponseType }
     */
    public EffectuatePlannedVaccinationResponseType createEffectuatePlannedVaccinationResponseType() {
        return new EffectuatePlannedVaccinationResponseType();
    }

    /**
     * Create an instance of {@link CreateSinglePlannedVaccinationType }
     * 
     * @return
     *     the new instance of {@link CreateSinglePlannedVaccinationType }
     */
    public CreateSinglePlannedVaccinationType createCreateSinglePlannedVaccinationType() {
        return new CreateSinglePlannedVaccinationType();
    }

    /**
     * Create an instance of {@link CreateSinglePlannedVaccinationRequestType }
     * 
     * @return
     *     the new instance of {@link CreateSinglePlannedVaccinationRequestType }
     */
    public CreateSinglePlannedVaccinationRequestType createCreateSinglePlannedVaccinationRequestType() {
        return new CreateSinglePlannedVaccinationRequestType();
    }

    /**
     * Create an instance of {@link CreateSinglePlannedVaccinationResponseType }
     * 
     * @return
     *     the new instance of {@link CreateSinglePlannedVaccinationResponseType }
     */
    public CreateSinglePlannedVaccinationResponseType createCreateSinglePlannedVaccinationResponseType() {
        return new CreateSinglePlannedVaccinationResponseType();
    }

    /**
     * Create an instance of {@link CreatePlannedVaccinationType }
     * 
     * @return
     *     the new instance of {@link CreatePlannedVaccinationType }
     */
    public CreatePlannedVaccinationType createCreatePlannedVaccinationType() {
        return new CreatePlannedVaccinationType();
    }

    /**
     * Create an instance of {@link SubscribeAndCreatePlannedVaccinationsType }
     * 
     * @return
     *     the new instance of {@link SubscribeAndCreatePlannedVaccinationsType }
     */
    public SubscribeAndCreatePlannedVaccinationsType createSubscribeAndCreatePlannedVaccinationsType() {
        return new SubscribeAndCreatePlannedVaccinationsType();
    }

    /**
     * Create an instance of {@link SubscribeAndCreatePlannedVaccinationsRequestType }
     * 
     * @return
     *     the new instance of {@link SubscribeAndCreatePlannedVaccinationsRequestType }
     */
    public SubscribeAndCreatePlannedVaccinationsRequestType createSubscribeAndCreatePlannedVaccinationsRequestType() {
        return new SubscribeAndCreatePlannedVaccinationsRequestType();
    }

    /**
     * Create an instance of {@link SubscribeAndCreatePlannedVaccinationsResponseType }
     * 
     * @return
     *     the new instance of {@link SubscribeAndCreatePlannedVaccinationsResponseType }
     */
    public SubscribeAndCreatePlannedVaccinationsResponseType createSubscribeAndCreatePlannedVaccinationsResponseType() {
        return new SubscribeAndCreatePlannedVaccinationsResponseType();
    }

    /**
     * Create an instance of {@link GetVaccinationCardAsPDFRequestType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationCardAsPDFRequestType }
     */
    public GetVaccinationCardAsPDFRequestType createGetVaccinationCardAsPDFRequestType() {
        return new GetVaccinationCardAsPDFRequestType();
    }

    /**
     * Create an instance of {@link GetVaccinationCardAsPDFResponseType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationCardAsPDFResponseType }
     */
    public GetVaccinationCardAsPDFResponseType createGetVaccinationCardAsPDFResponseType() {
        return new GetVaccinationCardAsPDFResponseType();
    }

    /**
     * Create an instance of {@link GetVaccinationPassportAsPDFRequestType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationPassportAsPDFRequestType }
     */
    public GetVaccinationPassportAsPDFRequestType createGetVaccinationPassportAsPDFRequestType() {
        return new GetVaccinationPassportAsPDFRequestType();
    }

    /**
     * Create an instance of {@link GetVaccinationPassportAsPDFResponseType }
     * 
     * @return
     *     the new instance of {@link GetVaccinationPassportAsPDFResponseType }
     */
    public GetVaccinationPassportAsPDFResponseType createGetVaccinationPassportAsPDFResponseType() {
        return new GetVaccinationPassportAsPDFResponseType();
    }

    /**
     * Create an instance of {@link ValidateVaccinationPassportRequestType }
     * 
     * @return
     *     the new instance of {@link ValidateVaccinationPassportRequestType }
     */
    public ValidateVaccinationPassportRequestType createValidateVaccinationPassportRequestType() {
        return new ValidateVaccinationPassportRequestType();
    }

    /**
     * Create an instance of {@link ValidateVaccinationPassportResponseType }
     * 
     * @return
     *     the new instance of {@link ValidateVaccinationPassportResponseType }
     */
    public ValidateVaccinationPassportResponseType createValidateVaccinationPassportResponseType() {
        return new ValidateVaccinationPassportResponseType();
    }

    /**
     * Create an instance of {@link PostalAddressType }
     * 
     * @return
     *     the new instance of {@link PostalAddressType }
     */
    public PostalAddressType createPostalAddressType() {
        return new PostalAddressType();
    }

    /**
     * Create an instance of {@link RequestVaccinationPassportHardCopyRequestType }
     * 
     * @return
     *     the new instance of {@link RequestVaccinationPassportHardCopyRequestType }
     */
    public RequestVaccinationPassportHardCopyRequestType createRequestVaccinationPassportHardCopyRequestType() {
        return new RequestVaccinationPassportHardCopyRequestType();
    }

    /**
     * Create an instance of {@link RequestVaccinationPassportHardCopyResponseType }
     * 
     * @return
     *     the new instance of {@link RequestVaccinationPassportHardCopyResponseType }
     */
    public RequestVaccinationPassportHardCopyResponseType createRequestVaccinationPassportHardCopyResponseType() {
        return new RequestVaccinationPassportHardCopyResponseType();
    }

    /**
     * Create an instance of {@link MultiUpdateOutType }
     * 
     * @return
     *     the new instance of {@link MultiUpdateOutType }
     */
    public MultiUpdateOutType createMultiUpdateOutType() {
        return new MultiUpdateOutType();
    }

    /**
     * Create an instance of {@link MultiUpdateResponseType }
     * 
     * @return
     *     the new instance of {@link MultiUpdateResponseType }
     */
    public MultiUpdateResponseType createMultiUpdateResponseType() {
        return new MultiUpdateResponseType();
    }

    /**
     * Create an instance of {@link GetAllPermissionsType }
     * 
     * @return
     *     the new instance of {@link GetAllPermissionsType }
     */
    public GetAllPermissionsType createGetAllPermissionsType() {
        return new GetAllPermissionsType();
    }

    /**
     * Create an instance of {@link GetCallersPermissionsType }
     * 
     * @return
     *     the new instance of {@link GetCallersPermissionsType }
     */
    public GetCallersPermissionsType createGetCallersPermissionsType() {
        return new GetCallersPermissionsType();
    }

    /**
     * Create an instance of {@link GetCallersPermissionsToPersonType }
     * 
     * @return
     *     the new instance of {@link GetCallersPermissionsToPersonType }
     */
    public GetCallersPermissionsToPersonType createGetCallersPermissionsToPersonType() {
        return new GetCallersPermissionsToPersonType();
    }

    /**
     * Create an instance of {@link GetPermissionsRequestType }
     * 
     * @return
     *     the new instance of {@link GetPermissionsRequestType }
     */
    public GetPermissionsRequestType createGetPermissionsRequestType() {
        return new GetPermissionsRequestType();
    }

    /**
     * Create an instance of {@link RolesPermissionsType }
     * 
     * @return
     *     the new instance of {@link RolesPermissionsType }
     */
    public RolesPermissionsType createRolesPermissionsType() {
        return new RolesPermissionsType();
    }

    /**
     * Create an instance of {@link GetPermissionsResponseType }
     * 
     * @return
     *     the new instance of {@link GetPermissionsResponseType }
     */
    public GetPermissionsResponseType createGetPermissionsResponseType() {
        return new GetPermissionsResponseType();
    }

    /**
     * Create an instance of {@link GetCovid19CertificateAsPDFRequestType }
     * 
     * @return
     *     the new instance of {@link GetCovid19CertificateAsPDFRequestType }
     */
    public GetCovid19CertificateAsPDFRequestType createGetCovid19CertificateAsPDFRequestType() {
        return new GetCovid19CertificateAsPDFRequestType();
    }

    /**
     * Create an instance of {@link GetCovid19CertificateAsPDFResponseType }
     * 
     * @return
     *     the new instance of {@link GetCovid19CertificateAsPDFResponseType }
     */
    public GetCovid19CertificateAsPDFResponseType createGetCovid19CertificateAsPDFResponseType() {
        return new GetCovid19CertificateAsPDFResponseType();
    }

    /**
     * Create an instance of {@link CreateVaccinationPlanSubscriptionType }
     * 
     * @return
     *     the new instance of {@link CreateVaccinationPlanSubscriptionType }
     */
    public CreateVaccinationPlanSubscriptionType createCreateVaccinationPlanSubscriptionType() {
        return new CreateVaccinationPlanSubscriptionType();
    }

    /**
     * Create an instance of {@link ModificatorType }
     * 
     * @return
     *     the new instance of {@link ModificatorType }
     */
    public ModificatorType createModificatorType() {
        return new ModificatorType();
    }

    /**
     * Create an instance of {@link AuthorisedModificatorType }
     * 
     * @return
     *     the new instance of {@link AuthorisedModificatorType }
     */
    public AuthorisedModificatorType createAuthorisedModificatorType() {
        return new AuthorisedModificatorType();
    }

    /**
     * Create an instance of {@link EffectuationOverviewType }
     * 
     * @return
     *     the new instance of {@link EffectuationOverviewType }
     */
    public EffectuationOverviewType createEffectuationOverviewType() {
        return new EffectuationOverviewType();
    }

    /**
     * Create an instance of {@link VaccinationPassportMetadataType }
     * 
     * @return
     *     the new instance of {@link VaccinationPassportMetadataType }
     */
    public VaccinationPassportMetadataType createVaccinationPassportMetadataType() {
        return new VaccinationPassportMetadataType();
    }

    /**
     * Create an instance of {@link PassListType }
     * 
     * @return
     *     the new instance of {@link PassListType }
     */
    public PassListType createPassListType() {
        return new PassListType();
    }

    /**
     * Create an instance of {@link PassJWTType }
     * 
     * @return
     *     the new instance of {@link PassJWTType }
     */
    public PassJWTType createPassJWTType() {
        return new PassJWTType();
    }

    /**
     * Create an instance of {@link KeyValuePairType }
     * 
     * @return
     *     the new instance of {@link KeyValuePairType }
     */
    public KeyValuePairType createKeyValuePairType() {
        return new KeyValuePairType();
    }

    /**
     * Create an instance of {@link FutureResultType }
     * 
     * @return
     *     the new instance of {@link FutureResultType }
     */
    public FutureResultType createFutureResultType() {
        return new FutureResultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "AuthorisationIdentifier")
    public JAXBElement<String> createAuthorisationIdentifier(String value) {
        return new JAXBElement<>(_AuthorisationIdentifier_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OnBehalfOfType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OnBehalfOfType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "OnBehalfOf")
    public JAXBElement<OnBehalfOfType> createOnBehalfOf(OnBehalfOfType value) {
        return new JAXBElement<>(_OnBehalfOf_QNAME, OnBehalfOfType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SystemName")
    public JAXBElement<String> createSystemName(String value) {
        return new JAXBElement<>(_SystemName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ServiceName")
    public JAXBElement<String> createServiceName(String value) {
        return new JAXBElement<>(_ServiceName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimingType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TimingType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Timing")
    public JAXBElement<TimingType> createTiming(TimingType value) {
        return new JAXBElement<>(_Timing_QNAME, TimingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimingListType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TimingListType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "TimingList")
    public JAXBElement<TimingListType> createTimingList(TimingListType value) {
        return new JAXBElement<>(_TimingList_QNAME, TimingListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NegativeConsentRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NegativeConsentRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "NegativeConsentRequest")
    public JAXBElement<NegativeConsentRequestType> createNegativeConsentRequest(NegativeConsentRequestType value) {
        return new JAXBElement<>(_NegativeConsentRequest_QNAME, NegativeConsentRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationCardRequest")
    public JAXBElement<GetVaccinationCardRequestType> createGetVaccinationCardRequest(GetVaccinationCardRequestType value) {
        return new JAXBElement<>(_GetVaccinationCardRequest_QNAME, GetVaccinationCardRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationIdentifier")
    public JAXBElement<Long> createVaccinationIdentifier(Long value) {
        return new JAXBElement<>(_VaccinationIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationVersionIdentifier")
    public JAXBElement<Long> createVaccinationVersionIdentifier(Long value) {
        return new JAXBElement<>(_VaccinationVersionIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccineIdentifier")
    public JAXBElement<Long> createVaccineIdentifier(Long value) {
        return new JAXBElement<>(_VaccineIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccineName")
    public JAXBElement<String> createVaccineName(String value) {
        return new JAXBElement<>(_VaccineName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DiseaseIdentifier")
    public JAXBElement<Long> createDiseaseIdentifier(Long value) {
        return new JAXBElement<>(_DiseaseIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DiseaseName")
    public JAXBElement<String> createDiseaseName(String value) {
        return new JAXBElement<>(_DiseaseName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DiseaseNameDK")
    public JAXBElement<String> createDiseaseNameDK(String value) {
        return new JAXBElement<>(_DiseaseNameDK_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ATCCode")
    public JAXBElement<String> createATCCode(String value) {
        return new JAXBElement<>(_ATCCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ATCText")
    public JAXBElement<String> createATCText(String value) {
        return new JAXBElement<>(_ATCText_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ATCType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ATCType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ATC")
    public JAXBElement<ATCType> createATC(ATCType value) {
        return new JAXBElement<>(_ATC_QNAME, ATCType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiseaseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DiseaseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Disease")
    public JAXBElement<DiseaseType> createDisease(DiseaseType value) {
        return new JAXBElement<>(_Disease_QNAME, DiseaseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugIdentifier")
    public JAXBElement<Long> createDrugIdentifier(Long value) {
        return new JAXBElement<>(_DrugIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugName")
    public JAXBElement<String> createDrugName(String value) {
        return new JAXBElement<>(_DrugName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugFormCode")
    public JAXBElement<String> createDrugFormCode(String value) {
        return new JAXBElement<>(_DrugFormCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugFormText")
    public JAXBElement<String> createDrugFormText(String value) {
        return new JAXBElement<>(_DrugFormText_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DrugFormType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DrugFormType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugForm")
    public JAXBElement<DrugFormType> createDrugForm(DrugFormType value) {
        return new JAXBElement<>(_DrugForm_QNAME, DrugFormType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthValue")
    public JAXBElement<BigDecimal> createDrugStrengthValue(BigDecimal value) {
        return new JAXBElement<>(_DrugStrengthValue_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthUnitCode")
    public JAXBElement<String> createDrugStrengthUnitCode(String value) {
        return new JAXBElement<>(_DrugStrengthUnitCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthUnitText")
    public JAXBElement<String> createDrugStrengthUnitText(String value) {
        return new JAXBElement<>(_DrugStrengthUnitText_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthText")
    public JAXBElement<String> createDrugStrengthText(String value) {
        return new JAXBElement<>(_DrugStrengthText_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DrugStrengthType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DrugStrengthType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrength")
    public JAXBElement<DrugStrengthType> createDrugStrength(DrugStrengthType value) {
        return new JAXBElement<>(_DrugStrength_QNAME, DrugStrengthType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SSIDrugType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SSIDrugType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SSIDrug")
    public JAXBElement<SSIDrugType> createSSIDrug(SSIDrugType value) {
        return new JAXBElement<>(_SSIDrug_QNAME, SSIDrugType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccineKeywordsText")
    public JAXBElement<String> createVaccineKeywordsText(String value) {
        return new JAXBElement<>(_VaccineKeywordsText_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanIdentifier")
    public JAXBElement<Long> createVaccinationPlanIdentifier(Long value) {
        return new JAXBElement<>(_VaccinationPlanIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanName")
    public JAXBElement<String> createVaccinationPlanName(String value) {
        return new JAXBElement<>(_VaccinationPlanName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisplayMinimumVaccinationPlanType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DisplayMinimumVaccinationPlanType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DisplayMinimumVaccinationPlan")
    public JAXBElement<DisplayMinimumVaccinationPlanType> createDisplayMinimumVaccinationPlan(DisplayMinimumVaccinationPlanType value) {
        return new JAXBElement<>(_DisplayMinimumVaccinationPlan_QNAME, DisplayMinimumVaccinationPlanType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VaccineType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VaccineType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Vaccine")
    public JAXBElement<VaccineType> createVaccine(VaccineType value) {
        return new JAXBElement<>(_Vaccine_QNAME, VaccineType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "AuthorisedHealthcareProfessionalName")
    public JAXBElement<String> createAuthorisedHealthcareProfessionalName(String value) {
        return new JAXBElement<>(_AuthorisedHealthcareProfessionalName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SpecialityCode")
    public JAXBElement<String> createSpecialityCode(String value) {
        return new JAXBElement<>(_SpecialityCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorisedHealthcareProfessionalType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthorisedHealthcareProfessionalType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "AuthorisedHealthcareProfessional")
    public JAXBElement<AuthorisedHealthcareProfessionalType> createAuthorisedHealthcareProfessional(AuthorisedHealthcareProfessionalType value) {
        return new JAXBElement<>(_AuthorisedHealthcareProfessional_QNAME, AuthorisedHealthcareProfessionalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "AddressLine")
    public JAXBElement<String> createAddressLine(String value) {
        return new JAXBElement<>(_AddressLine_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EmailAddress")
    public JAXBElement<String> createEmailAddress(String value) {
        return new JAXBElement<>(_EmailAddress_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrganisationIdentifierType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrganisationIdentifierType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "OrganisationIdentifier")
    public JAXBElement<OrganisationIdentifierType> createOrganisationIdentifier(OrganisationIdentifierType value) {
        return new JAXBElement<>(_OrganisationIdentifier_QNAME, OrganisationIdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "OrganisationName")
    public JAXBElement<String> createOrganisationName(String value) {
        return new JAXBElement<>(_OrganisationName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "OrganisationType")
    public JAXBElement<String> createOrganisationType(String value) {
        return new JAXBElement<>(_OrganisationType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "TelephoneNumber")
    public JAXBElement<String> createTelephoneNumber(String value) {
        return new JAXBElement<>(_TelephoneNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrganisationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrganisationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Organisation")
    public JAXBElement<OrganisationType> createOrganisation(OrganisationType value) {
        return new JAXBElement<>(_Organisation_QNAME, OrganisationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientFlagType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PatientFlagType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PatientFlag")
    public JAXBElement<PatientFlagType> createPatientFlag(PatientFlagType value) {
        return new JAXBElement<>(_PatientFlag_QNAME, PatientFlagType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "RequestedRole")
    public JAXBElement<String> createRequestedRole(String value) {
        return new JAXBElement<>(_RequestedRole_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PersonIdentifier")
    public JAXBElement<String> createPersonIdentifier(String value) {
        return new JAXBElement<>(_PersonIdentifier_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PersonGivenName")
    public JAXBElement<String> createPersonGivenName(String value) {
        return new JAXBElement<>(_PersonGivenName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PersonMiddleName")
    public JAXBElement<String> createPersonMiddleName(String value) {
        return new JAXBElement<>(_PersonMiddleName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PersonSurname")
    public JAXBElement<String> createPersonSurname(String value) {
        return new JAXBElement<>(_PersonSurname_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonNameType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PersonNameType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PersonName")
    public JAXBElement<PersonNameType> createPersonName(PersonNameType value) {
        return new JAXBElement<>(_PersonName_QNAME, PersonNameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModificatorPersonType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ModificatorPersonType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ModificatorPerson")
    public JAXBElement<ModificatorPersonType> createModificatorPerson(ModificatorPersonType value) {
        return new JAXBElement<>(_ModificatorPerson_QNAME, ModificatorPersonType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatedByName")
    public JAXBElement<String> createEffectuatedByName(String value) {
        return new JAXBElement<>(_EffectuatedByName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatedByOrganisationName")
    public JAXBElement<String> createEffectuatedByOrganisationName(String value) {
        return new JAXBElement<>(_EffectuatedByOrganisationName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatedInCountryCode")
    public JAXBElement<String> createEffectuatedInCountryCode(String value) {
        return new JAXBElement<>(_EffectuatedInCountryCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartlyDefinedEffectuatorType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartlyDefinedEffectuatorType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PartlyDefinedEffectuator")
    public JAXBElement<PartlyDefinedEffectuatorType> createPartlyDefinedEffectuator(PartlyDefinedEffectuatorType value) {
        return new JAXBElement<>(_PartlyDefinedEffectuator_QNAME, PartlyDefinedEffectuatorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HealthInsuranceImportFlagType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HealthInsuranceImportFlagType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "HealthInsuranceImportFlag")
    public JAXBElement<HealthInsuranceImportFlagType> createHealthInsuranceImportFlag(HealthInsuranceImportFlagType value) {
        return new JAXBElement<>(_HealthInsuranceImportFlag_QNAME, HealthInsuranceImportFlagType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemUpdateFlagType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SystemUpdateFlagType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SystemUpdateFlag")
    public JAXBElement<SystemUpdateFlagType> createSystemUpdateFlag(SystemUpdateFlagType value) {
        return new JAXBElement<>(_SystemUpdateFlag_QNAME, SystemUpdateFlagType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifiedType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ModifiedType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Modified")
    public JAXBElement<ModifiedType> createModified(ModifiedType value) {
        return new JAXBElement<>(_Modified_QNAME, ModifiedType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatedType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatedType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Created")
    public JAXBElement<CreatedType> createCreated(CreatedType value) {
        return new JAXBElement<>(_Created_QNAME, CreatedType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReportedType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReportedType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Reported")
    public JAXBElement<ReportedType> createReported(ReportedType value) {
        return new JAXBElement<>(_Reported_QNAME, ReportedType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReviewedType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReviewedType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Reviewed")
    public JAXBElement<ReviewedType> createReviewed(ReviewedType value) {
        return new JAXBElement<>(_Reviewed_QNAME, ReviewedType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "BatchNumber")
    public JAXBElement<String> createBatchNumber(String value) {
        return new JAXBElement<>(_BatchNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CoverageDuration")
    public JAXBElement<String> createCoverageDuration(String value) {
        return new JAXBElement<>(_CoverageDuration_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DosageOptionIdentifier")
    public JAXBElement<Long> createDosageOptionIdentifier(Long value) {
        return new JAXBElement<>(_DosageOptionIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DosageText")
    public JAXBElement<String> createDosageText(String value) {
        return new JAXBElement<>(_DosageText_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VaccinationCredibilityEnumType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VaccinationCredibilityEnumType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationCredibilityEnum")
    public JAXBElement<VaccinationCredibilityEnumType> createVaccinationCredibilityEnum(VaccinationCredibilityEnumType value) {
        return new JAXBElement<>(_VaccinationCredibilityEnum_QNAME, VaccinationCredibilityEnumType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ServiceCode")
    public JAXBElement<String> createServiceCode(String value) {
        return new JAXBElement<>(_ServiceCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ServiceCodeRemarkText")
    public JAXBElement<String> createServiceCodeRemarkText(String value) {
        return new JAXBElement<>(_ServiceCodeRemarkText_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatedByOrganisationName2")
    public JAXBElement<String> createEffectuatedByOrganisationName2(String value) {
        return new JAXBElement<>(_EffectuatedByOrganisationName2_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatedByOrganisationType")
    public JAXBElement<String> createEffectuatedByOrganisationType(String value) {
        return new JAXBElement<>(_EffectuatedByOrganisationType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatedByOrganisationNumber")
    public JAXBElement<String> createEffectuatedByOrganisationNumber(String value) {
        return new JAXBElement<>(_EffectuatedByOrganisationNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EffectuatedType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EffectuatedType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Effectuated")
    public JAXBElement<EffectuatedType> createEffectuated(EffectuatedType value) {
        return new JAXBElement<>(_Effectuated_QNAME, EffectuatedType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanVersionIdentifier")
    public JAXBElement<Long> createVaccinationPlanVersionIdentifier(Long value) {
        return new JAXBElement<>(_VaccinationPlanVersionIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanItemIdentifier")
    public JAXBElement<Long> createVaccinationPlanItemIdentifier(Long value) {
        return new JAXBElement<>(_VaccinationPlanItemIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanItemIndex")
    public JAXBElement<Long> createVaccinationPlanItemIndex(Long value) {
        return new JAXBElement<>(_VaccinationPlanItemIndex_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanItemDescription")
    public JAXBElement<String> createVaccinationPlanItemDescription(String value) {
        return new JAXBElement<>(_VaccinationPlanItemDescription_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanItemSeries")
    public JAXBElement<String> createVaccinationPlanItemSeries(String value) {
        return new JAXBElement<>(_VaccinationPlanItemSeries_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EffectuatedPlannedItemType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EffectuatedPlannedItemType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatedPlannedItem")
    public JAXBElement<EffectuatedPlannedItemType> createEffectuatedPlannedItem(EffectuatedPlannedItemType value) {
        return new JAXBElement<>(_EffectuatedPlannedItem_QNAME, EffectuatedPlannedItemType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "NegativeConsentIndicator")
    public JAXBElement<Boolean> createNegativeConsentIndicator(Boolean value) {
        return new JAXBElement<>(_NegativeConsentIndicator_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VaccinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VaccinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Vaccination")
    public JAXBElement<VaccinationType> createVaccination(VaccinationType value) {
        return new JAXBElement<>(_Vaccination_QNAME, VaccinationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NegativeConsentStructureType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NegativeConsentStructureType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "NegativeConsentStructure")
    public JAXBElement<NegativeConsentStructureType> createNegativeConsentStructure(NegativeConsentStructureType value) {
        return new JAXBElement<>(_NegativeConsentStructure_QNAME, NegativeConsentStructureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationCardResponse")
    public JAXBElement<GetVaccinationCardResponseType> createGetVaccinationCardResponse(GetVaccinationCardResponseType value) {
        return new JAXBElement<>(_GetVaccinationCardResponse_QNAME, GetVaccinationCardResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardIfUpdatedRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardIfUpdatedRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationCardIfUpdatedRequest")
    public JAXBElement<GetVaccinationCardIfUpdatedRequestType> createGetVaccinationCardIfUpdatedRequest(GetVaccinationCardIfUpdatedRequestType value) {
        return new JAXBElement<>(_GetVaccinationCardIfUpdatedRequest_QNAME, GetVaccinationCardIfUpdatedRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardIfUpdatedResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardIfUpdatedResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationCardIfUpdatedResponse")
    public JAXBElement<GetVaccinationCardIfUpdatedResponseType> createGetVaccinationCardIfUpdatedResponse(GetVaccinationCardIfUpdatedResponseType value) {
        return new JAXBElement<>(_GetVaccinationCardIfUpdatedResponse_QNAME, GetVaccinationCardIfUpdatedResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationHistoryRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationHistoryRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationHistoryRequest")
    public JAXBElement<GetVaccinationHistoryRequestType> createGetVaccinationHistoryRequest(GetVaccinationHistoryRequestType value) {
        return new JAXBElement<>(_GetVaccinationHistoryRequest_QNAME, GetVaccinationHistoryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationHistoryResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationHistoryResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationHistoryResponse")
    public JAXBElement<GetVaccinationHistoryResponseType> createGetVaccinationHistoryResponse(GetVaccinationHistoryResponseType value) {
        return new JAXBElement<>(_GetVaccinationHistoryResponse_QNAME, GetVaccinationHistoryResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVaccinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteVaccinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeleteVaccination")
    public JAXBElement<DeleteVaccinationType> createDeleteVaccination(DeleteVaccinationType value) {
        return new JAXBElement<>(_DeleteVaccination_QNAME, DeleteVaccinationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVaccinationRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteVaccinationRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeleteVaccinationRequest")
    public JAXBElement<DeleteVaccinationRequestType> createDeleteVaccinationRequest(DeleteVaccinationRequestType value) {
        return new JAXBElement<>(_DeleteVaccinationRequest_QNAME, DeleteVaccinationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionMismatchWarningIndicatorType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VersionMismatchWarningIndicatorType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VersionMismatchWarningIndicator")
    public JAXBElement<VersionMismatchWarningIndicatorType> createVersionMismatchWarningIndicator(VersionMismatchWarningIndicatorType value) {
        return new JAXBElement<>(_VersionMismatchWarningIndicator_QNAME, VersionMismatchWarningIndicatorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeleteVaccinationResponse")
    public JAXBElement<DeleteVaccinationResponseType> createDeleteVaccinationResponse(DeleteVaccinationResponseType value) {
        return new JAXBElement<>(_DeleteVaccinationResponse_QNAME, DeleteVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VaccinationCreateType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VaccinationCreateType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationCreate")
    public JAXBElement<VaccinationCreateType> createVaccinationCreate(VaccinationCreateType value) {
        return new JAXBElement<>(_VaccinationCreate_QNAME, VaccinationCreateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateVaccinationRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateVaccinationRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreateVaccinationRequest")
    public JAXBElement<CreateVaccinationRequestType> createCreateVaccinationRequest(CreateVaccinationRequestType value) {
        return new JAXBElement<>(_CreateVaccinationRequest_QNAME, CreateVaccinationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreateVaccinationResponse")
    public JAXBElement<CreateVaccinationResponseType> createCreateVaccinationResponse(CreateVaccinationResponseType value) {
        return new JAXBElement<>(_CreateVaccinationResponse_QNAME, CreateVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreviousVaccinationCreateType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PreviousVaccinationCreateType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PreviousVaccinationCreate")
    public JAXBElement<PreviousVaccinationCreateType> createPreviousVaccinationCreate(PreviousVaccinationCreateType value) {
        return new JAXBElement<>(_PreviousVaccinationCreate_QNAME, PreviousVaccinationCreateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePreviousVaccinationRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatePreviousVaccinationRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreatePreviousVaccinationRequest")
    public JAXBElement<CreatePreviousVaccinationRequestType> createCreatePreviousVaccinationRequest(CreatePreviousVaccinationRequestType value) {
        return new JAXBElement<>(_CreatePreviousVaccinationRequest_QNAME, CreatePreviousVaccinationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePreviousVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatePreviousVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreatePreviousVaccinationResponse")
    public JAXBElement<CreatePreviousVaccinationResponseType> createCreatePreviousVaccinationResponse(CreatePreviousVaccinationResponseType value) {
        return new JAXBElement<>(_CreatePreviousVaccinationResponse_QNAME, CreatePreviousVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VaccinationUpdateType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VaccinationUpdateType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationUpdate")
    public JAXBElement<VaccinationUpdateType> createVaccinationUpdate(VaccinationUpdateType value) {
        return new JAXBElement<>(_VaccinationUpdate_QNAME, VaccinationUpdateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateVaccinationRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateVaccinationRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "UpdateVaccinationRequest")
    public JAXBElement<UpdateVaccinationRequestType> createUpdateVaccinationRequest(UpdateVaccinationRequestType value) {
        return new JAXBElement<>(_UpdateVaccinationRequest_QNAME, UpdateVaccinationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "UpdateVaccinationResponse")
    public JAXBElement<UpdateVaccinationResponseType> createUpdateVaccinationResponse(UpdateVaccinationResponseType value) {
        return new JAXBElement<>(_UpdateVaccinationResponse_QNAME, UpdateVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PlannedVaccinationIdentifier")
    public JAXBElement<Long> createPlannedVaccinationIdentifier(Long value) {
        return new JAXBElement<>(_PlannedVaccinationIdentifier_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanItemMinimumInterval")
    public JAXBElement<Long> createVaccinationPlanItemMinimumInterval(Long value) {
        return new JAXBElement<>(_VaccinationPlanItemMinimumInterval_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlannedVaccinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PlannedVaccinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PlannedVaccination")
    public JAXBElement<PlannedVaccinationType> createPlannedVaccination(PlannedVaccinationType value) {
        return new JAXBElement<>(_PlannedVaccination_QNAME, PlannedVaccinationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSubscriptionResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteSubscriptionResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeleteSubscriptionResponse")
    public JAXBElement<DeleteSubscriptionResponseType> createDeleteSubscriptionResponse(DeleteSubscriptionResponseType value) {
        return new JAXBElement<>(_DeleteSubscriptionResponse_QNAME, DeleteSubscriptionResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUnsubscriptionResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateUnsubscriptionResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreateUnsubscriptionResponse")
    public JAXBElement<CreateUnsubscriptionResponseType> createCreateUnsubscriptionResponse(CreateUnsubscriptionResponseType value) {
        return new JAXBElement<>(_CreateUnsubscriptionResponse_QNAME, CreateUnsubscriptionResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonVaccinationPlanType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PersonVaccinationPlanType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PersonVaccinationPlan")
    public JAXBElement<PersonVaccinationPlanType> createPersonVaccinationPlan(PersonVaccinationPlanType value) {
        return new JAXBElement<>(_PersonVaccinationPlan_QNAME, PersonVaccinationPlanType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUnsubscriptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteUnsubscriptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeleteUnsubscription")
    public JAXBElement<DeleteUnsubscriptionType> createDeleteUnsubscription(DeleteUnsubscriptionType value) {
        return new JAXBElement<>(_DeleteUnsubscription_QNAME, DeleteUnsubscriptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUnsubscriptionRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteUnsubscriptionRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeleteUnsubscriptionRequest")
    public JAXBElement<DeleteUnsubscriptionRequestType> createDeleteUnsubscriptionRequest(DeleteUnsubscriptionRequestType value) {
        return new JAXBElement<>(_DeleteUnsubscriptionRequest_QNAME, DeleteUnsubscriptionRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUnsubscriptionResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteUnsubscriptionResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeleteUnsubscriptionResponse")
    public JAXBElement<DeleteUnsubscriptionResponseType> createDeleteUnsubscriptionResponse(DeleteUnsubscriptionResponseType value) {
        return new JAXBElement<>(_DeleteUnsubscriptionResponse_QNAME, DeleteUnsubscriptionResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePlannedVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeletePlannedVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DeletePlannedVaccinationResponse")
    public JAXBElement<DeletePlannedVaccinationResponseType> createDeletePlannedVaccinationResponse(DeletePlannedVaccinationResponseType value) {
        return new JAXBElement<>(_DeletePlannedVaccinationResponse_QNAME, DeletePlannedVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUnsubscriptionsRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetUnsubscriptionsRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetUnsubscriptionsRequest")
    public JAXBElement<GetUnsubscriptionsRequestType> createGetUnsubscriptionsRequest(GetUnsubscriptionsRequestType value) {
        return new JAXBElement<>(_GetUnsubscriptionsRequest_QNAME, GetUnsubscriptionsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedVaccinationsRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetPlannedVaccinationsRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetPlannedVaccinationsRequest")
    public JAXBElement<GetPlannedVaccinationsRequestType> createGetPlannedVaccinationsRequest(GetPlannedVaccinationsRequestType value) {
        return new JAXBElement<>(_GetPlannedVaccinationsRequest_QNAME, GetPlannedVaccinationsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlannedVaccNegativeConsentStructureType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PlannedVaccNegativeConsentStructureType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PlannedVaccNegativeConsentStructure")
    public JAXBElement<PlannedVaccNegativeConsentStructureType> createPlannedVaccNegativeConsentStructure(PlannedVaccNegativeConsentStructureType value) {
        return new JAXBElement<>(_PlannedVaccNegativeConsentStructure_QNAME, PlannedVaccNegativeConsentStructureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedVaccinationsResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetPlannedVaccinationsResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetPlannedVaccinationsResponse")
    public JAXBElement<GetPlannedVaccinationsResponseType> createGetPlannedVaccinationsResponse(GetPlannedVaccinationsResponseType value) {
        return new JAXBElement<>(_GetPlannedVaccinationsResponse_QNAME, GetPlannedVaccinationsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePlannedVaccinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdatePlannedVaccinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "UpdatePlannedVaccination")
    public JAXBElement<UpdatePlannedVaccinationType> createUpdatePlannedVaccination(UpdatePlannedVaccinationType value) {
        return new JAXBElement<>(_UpdatePlannedVaccination_QNAME, UpdatePlannedVaccinationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePlannedVaccinationRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdatePlannedVaccinationRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "UpdatePlannedVaccinationRequest")
    public JAXBElement<UpdatePlannedVaccinationRequestType> createUpdatePlannedVaccinationRequest(UpdatePlannedVaccinationRequestType value) {
        return new JAXBElement<>(_UpdatePlannedVaccinationRequest_QNAME, UpdatePlannedVaccinationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePlannedVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdatePlannedVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "UpdatePlannedVaccinationResponse")
    public JAXBElement<UpdatePlannedVaccinationResponseType> createUpdatePlannedVaccinationResponse(UpdatePlannedVaccinationResponseType value) {
        return new JAXBElement<>(_UpdatePlannedVaccinationResponse_QNAME, UpdatePlannedVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EffectuatePlannedVaccinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EffectuatePlannedVaccinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatePlannedVaccination")
    public JAXBElement<EffectuatePlannedVaccinationType> createEffectuatePlannedVaccination(EffectuatePlannedVaccinationType value) {
        return new JAXBElement<>(_EffectuatePlannedVaccination_QNAME, EffectuatePlannedVaccinationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EffectuatePlannedVaccinationRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EffectuatePlannedVaccinationRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatePlannedVaccinationRequest")
    public JAXBElement<EffectuatePlannedVaccinationRequestType> createEffectuatePlannedVaccinationRequest(EffectuatePlannedVaccinationRequestType value) {
        return new JAXBElement<>(_EffectuatePlannedVaccinationRequest_QNAME, EffectuatePlannedVaccinationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EffectuatePlannedVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EffectuatePlannedVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "EffectuatePlannedVaccinationResponse")
    public JAXBElement<EffectuatePlannedVaccinationResponseType> createEffectuatePlannedVaccinationResponse(EffectuatePlannedVaccinationResponseType value) {
        return new JAXBElement<>(_EffectuatePlannedVaccinationResponse_QNAME, EffectuatePlannedVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSinglePlannedVaccinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateSinglePlannedVaccinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreateSinglePlannedVaccination")
    public JAXBElement<CreateSinglePlannedVaccinationType> createCreateSinglePlannedVaccination(CreateSinglePlannedVaccinationType value) {
        return new JAXBElement<>(_CreateSinglePlannedVaccination_QNAME, CreateSinglePlannedVaccinationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSinglePlannedVaccinationRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateSinglePlannedVaccinationRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreateSinglePlannedVaccinationRequest")
    public JAXBElement<CreateSinglePlannedVaccinationRequestType> createCreateSinglePlannedVaccinationRequest(CreateSinglePlannedVaccinationRequestType value) {
        return new JAXBElement<>(_CreateSinglePlannedVaccinationRequest_QNAME, CreateSinglePlannedVaccinationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSinglePlannedVaccinationResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateSinglePlannedVaccinationResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreateSinglePlannedVaccinationResponse")
    public JAXBElement<CreateSinglePlannedVaccinationResponseType> createCreateSinglePlannedVaccinationResponse(CreateSinglePlannedVaccinationResponseType value) {
        return new JAXBElement<>(_CreateSinglePlannedVaccinationResponse_QNAME, CreateSinglePlannedVaccinationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePlannedVaccinationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatePlannedVaccinationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreatePlannedVaccination")
    public JAXBElement<CreatePlannedVaccinationType> createCreatePlannedVaccination(CreatePlannedVaccinationType value) {
        return new JAXBElement<>(_CreatePlannedVaccination_QNAME, CreatePlannedVaccinationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeAndCreatePlannedVaccinationsType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubscribeAndCreatePlannedVaccinationsType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SubscribeAndCreatePlannedVaccinations")
    public JAXBElement<SubscribeAndCreatePlannedVaccinationsType> createSubscribeAndCreatePlannedVaccinations(SubscribeAndCreatePlannedVaccinationsType value) {
        return new JAXBElement<>(_SubscribeAndCreatePlannedVaccinations_QNAME, SubscribeAndCreatePlannedVaccinationsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeAndCreatePlannedVaccinationsRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubscribeAndCreatePlannedVaccinationsRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SubscribeAndCreatePlannedVaccinationsRequest")
    public JAXBElement<SubscribeAndCreatePlannedVaccinationsRequestType> createSubscribeAndCreatePlannedVaccinationsRequest(SubscribeAndCreatePlannedVaccinationsRequestType value) {
        return new JAXBElement<>(_SubscribeAndCreatePlannedVaccinationsRequest_QNAME, SubscribeAndCreatePlannedVaccinationsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeAndCreatePlannedVaccinationsResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubscribeAndCreatePlannedVaccinationsResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SubscribeAndCreatePlannedVaccinationsResponse")
    public JAXBElement<SubscribeAndCreatePlannedVaccinationsResponseType> createSubscribeAndCreatePlannedVaccinationsResponse(SubscribeAndCreatePlannedVaccinationsResponseType value) {
        return new JAXBElement<>(_SubscribeAndCreatePlannedVaccinationsResponse_QNAME, SubscribeAndCreatePlannedVaccinationsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardAsPDFRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardAsPDFRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationCardAsPDFRequest")
    public JAXBElement<GetVaccinationCardAsPDFRequestType> createGetVaccinationCardAsPDFRequest(GetVaccinationCardAsPDFRequestType value) {
        return new JAXBElement<>(_GetVaccinationCardAsPDFRequest_QNAME, GetVaccinationCardAsPDFRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationCardAsPDF")
    public JAXBElement<String> createVaccinationCardAsPDF(String value) {
        return new JAXBElement<>(_VaccinationCardAsPDF_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardAsPDFResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationCardAsPDFResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationCardAsPDFResponse")
    public JAXBElement<GetVaccinationCardAsPDFResponseType> createGetVaccinationCardAsPDFResponse(GetVaccinationCardAsPDFResponseType value) {
        return new JAXBElement<>(_GetVaccinationCardAsPDFResponse_QNAME, GetVaccinationCardAsPDFResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPlanCategory")
    public JAXBElement<String> createVaccinationPlanCategory(String value) {
        return new JAXBElement<>(_VaccinationPlanCategory_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationPassportAsPDFRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationPassportAsPDFRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationPassportAsPDFRequest")
    public JAXBElement<GetVaccinationPassportAsPDFRequestType> createGetVaccinationPassportAsPDFRequest(GetVaccinationPassportAsPDFRequestType value) {
        return new JAXBElement<>(_GetVaccinationPassportAsPDFRequest_QNAME, GetVaccinationPassportAsPDFRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "VaccinationPassportAsPDF")
    public JAXBElement<byte[]> createVaccinationPassportAsPDF(byte[] value) {
        return new JAXBElement<>(_VaccinationPassportAsPDF_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVaccinationPassportAsPDFResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetVaccinationPassportAsPDFResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetVaccinationPassportAsPDFResponse")
    public JAXBElement<GetVaccinationPassportAsPDFResponseType> createGetVaccinationPassportAsPDFResponse(GetVaccinationPassportAsPDFResponseType value) {
        return new JAXBElement<>(_GetVaccinationPassportAsPDFResponse_QNAME, GetVaccinationPassportAsPDFResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateVaccinationPassportRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateVaccinationPassportRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ValidateVaccinationPassportRequest")
    public JAXBElement<ValidateVaccinationPassportRequestType> createValidateVaccinationPassportRequest(ValidateVaccinationPassportRequestType value) {
        return new JAXBElement<>(_ValidateVaccinationPassportRequest_QNAME, ValidateVaccinationPassportRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateVaccinationPassportResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateVaccinationPassportResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "ValidateVaccinationPassportResponse")
    public JAXBElement<ValidateVaccinationPassportResponseType> createValidateVaccinationPassportResponse(ValidateVaccinationPassportResponseType value) {
        return new JAXBElement<>(_ValidateVaccinationPassportResponse_QNAME, ValidateVaccinationPassportResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostalAddressType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PostalAddressType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PostalAddress")
    public JAXBElement<PostalAddressType> createPostalAddress(PostalAddressType value) {
        return new JAXBElement<>(_PostalAddress_QNAME, PostalAddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestVaccinationPassportHardCopyRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RequestVaccinationPassportHardCopyRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "RequestVaccinationPassportHardCopyRequest")
    public JAXBElement<RequestVaccinationPassportHardCopyRequestType> createRequestVaccinationPassportHardCopyRequest(RequestVaccinationPassportHardCopyRequestType value) {
        return new JAXBElement<>(_RequestVaccinationPassportHardCopyRequest_QNAME, RequestVaccinationPassportHardCopyRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestVaccinationPassportHardCopyResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RequestVaccinationPassportHardCopyResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "RequestVaccinationPassportHardCopyResponse")
    public JAXBElement<RequestVaccinationPassportHardCopyResponseType> createRequestVaccinationPassportHardCopyResponse(RequestVaccinationPassportHardCopyResponseType value) {
        return new JAXBElement<>(_RequestVaccinationPassportHardCopyResponse_QNAME, RequestVaccinationPassportHardCopyResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiUpdateOutType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MultiUpdateOutType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "MultiUpdateOut")
    public JAXBElement<MultiUpdateOutType> createMultiUpdateOut(MultiUpdateOutType value) {
        return new JAXBElement<>(_MultiUpdateOut_QNAME, MultiUpdateOutType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiUpdateResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MultiUpdateResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "MultiUpdateResponse")
    public JAXBElement<MultiUpdateResponseType> createMultiUpdateResponse(MultiUpdateResponseType value) {
        return new JAXBElement<>(_MultiUpdateResponse_QNAME, MultiUpdateResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPermissionsType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllPermissionsType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetAllPermissions")
    public JAXBElement<GetAllPermissionsType> createGetAllPermissions(GetAllPermissionsType value) {
        return new JAXBElement<>(_GetAllPermissions_QNAME, GetAllPermissionsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallersPermissionsType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCallersPermissionsType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetCallersPermissions")
    public JAXBElement<GetCallersPermissionsType> createGetCallersPermissions(GetCallersPermissionsType value) {
        return new JAXBElement<>(_GetCallersPermissions_QNAME, GetCallersPermissionsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallersPermissionsToPersonType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCallersPermissionsToPersonType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetCallersPermissionsToPerson")
    public JAXBElement<GetCallersPermissionsToPersonType> createGetCallersPermissionsToPerson(GetCallersPermissionsToPersonType value) {
        return new JAXBElement<>(_GetCallersPermissionsToPerson_QNAME, GetCallersPermissionsToPersonType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPermissionsRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetPermissionsRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetPermissionsRequest")
    public JAXBElement<GetPermissionsRequestType> createGetPermissionsRequest(GetPermissionsRequestType value) {
        return new JAXBElement<>(_GetPermissionsRequest_QNAME, GetPermissionsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "UndefinedPermission")
    public JAXBElement<String> createUndefinedPermission(String value) {
        return new JAXBElement<>(_UndefinedPermission_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PredefinedPermission }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PredefinedPermission }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PredefinedPermission")
    public JAXBElement<PredefinedPermission> createPredefinedPermission(PredefinedPermission value) {
        return new JAXBElement<>(_PredefinedPermission_QNAME, PredefinedPermission.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Permission")
    public JAXBElement<String> createPermission(String value) {
        return new JAXBElement<>(_Permission_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RolesPermissionsType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RolesPermissionsType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "RolesPermissions")
    public JAXBElement<RolesPermissionsType> createRolesPermissions(RolesPermissionsType value) {
        return new JAXBElement<>(_RolesPermissions_QNAME, RolesPermissionsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPermissionsResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetPermissionsResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetPermissionsResponse")
    public JAXBElement<GetPermissionsResponseType> createGetPermissionsResponse(GetPermissionsResponseType value) {
        return new JAXBElement<>(_GetPermissionsResponse_QNAME, GetPermissionsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCovid19CertificateAsPDFRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCovid19CertificateAsPDFRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetCovid19CertificateAsPDFRequest")
    public JAXBElement<GetCovid19CertificateAsPDFRequestType> createGetCovid19CertificateAsPDFRequest(GetCovid19CertificateAsPDFRequestType value) {
        return new JAXBElement<>(_GetCovid19CertificateAsPDFRequest_QNAME, GetCovid19CertificateAsPDFRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCovid19CertificateAsPDFResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCovid19CertificateAsPDFResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "GetCovid19CertificateAsPDFResponse")
    public JAXBElement<GetCovid19CertificateAsPDFResponseType> createGetCovid19CertificateAsPDFResponse(GetCovid19CertificateAsPDFResponseType value) {
        return new JAXBElement<>(_GetCovid19CertificateAsPDFResponse_QNAME, GetCovid19CertificateAsPDFResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateVaccinationPlanSubscriptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateVaccinationPlanSubscriptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "CreateVaccinationPlanSubscription")
    public JAXBElement<CreateVaccinationPlanSubscriptionType> createCreateVaccinationPlanSubscription(CreateVaccinationPlanSubscriptionType value) {
        return new JAXBElement<>(_CreateVaccinationPlanSubscription_QNAME, CreateVaccinationPlanSubscriptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorisedHealthcareProfessionalType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthorisedHealthcareProfessionalType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "AuthorisedHealthcareProfessional", scope = AuthorisedModificatorType.class)
    public JAXBElement<AuthorisedHealthcareProfessionalType> createAuthorisedModificatorTypeAuthorisedHealthcareProfessional(AuthorisedHealthcareProfessionalType value) {
        return new JAXBElement<>(_AuthorisedHealthcareProfessional_QNAME, AuthorisedHealthcareProfessionalType.class, AuthorisedModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrganisationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrganisationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Organisation", scope = AuthorisedModificatorType.class)
    public JAXBElement<OrganisationType> createAuthorisedModificatorTypeOrganisation(OrganisationType value) {
        return new JAXBElement<>(_Organisation_QNAME, OrganisationType.class, AuthorisedModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModificatorPersonType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ModificatorPersonType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Other", scope = AuthorisedModificatorType.class)
    public JAXBElement<ModificatorPersonType> createAuthorisedModificatorTypeOther(ModificatorPersonType value) {
        return new JAXBElement<>(_AuthorisedModificatorTypeOther_QNAME, ModificatorPersonType.class, AuthorisedModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorisedHealthcareProfessionalType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthorisedHealthcareProfessionalType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "AuthorisedHealthcareProfessional", scope = ModificatorType.class)
    public JAXBElement<AuthorisedHealthcareProfessionalType> createModificatorTypeAuthorisedHealthcareProfessional(AuthorisedHealthcareProfessionalType value) {
        return new JAXBElement<>(_AuthorisedHealthcareProfessional_QNAME, AuthorisedHealthcareProfessionalType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrganisationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrganisationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Organisation", scope = ModificatorType.class)
    public JAXBElement<OrganisationType> createModificatorTypeOrganisation(OrganisationType value) {
        return new JAXBElement<>(_Organisation_QNAME, OrganisationType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorisedModificatorType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthorisedModificatorType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "AuthorisedBy", scope = ModificatorType.class)
    public JAXBElement<AuthorisedModificatorType> createModificatorTypeAuthorisedBy(AuthorisedModificatorType value) {
        return new JAXBElement<>(_ModificatorTypeAuthorisedBy_QNAME, AuthorisedModificatorType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientFlagType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PatientFlagType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Patient", scope = ModificatorType.class)
    public JAXBElement<PatientFlagType> createModificatorTypePatient(PatientFlagType value) {
        return new JAXBElement<>(_ModificatorTypePatient_QNAME, PatientFlagType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModificatorPersonType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ModificatorPersonType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Other", scope = ModificatorType.class)
    public JAXBElement<ModificatorPersonType> createModificatorTypeOther(ModificatorPersonType value) {
        return new JAXBElement<>(_AuthorisedModificatorTypeOther_QNAME, ModificatorPersonType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "Role", scope = ModificatorType.class)
    public JAXBElement<String> createModificatorTypeRole(String value) {
        return new JAXBElement<>(_ModificatorTypeRole_QNAME, String.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartlyDefinedEffectuatorType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartlyDefinedEffectuatorType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "PartlyDefinedEffectuator", scope = ModificatorType.class)
    public JAXBElement<PartlyDefinedEffectuatorType> createModificatorTypePartlyDefinedEffectuator(PartlyDefinedEffectuatorType value) {
        return new JAXBElement<>(_PartlyDefinedEffectuator_QNAME, PartlyDefinedEffectuatorType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HealthInsuranceImportFlagType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HealthInsuranceImportFlagType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "HealthInsuranceImport", scope = ModificatorType.class)
    public JAXBElement<HealthInsuranceImportFlagType> createModificatorTypeHealthInsuranceImport(HealthInsuranceImportFlagType value) {
        return new JAXBElement<>(_ModificatorTypeHealthInsuranceImport_QNAME, HealthInsuranceImportFlagType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemUpdateFlagType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SystemUpdateFlagType }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SystemUpdate", scope = ModificatorType.class)
    public JAXBElement<SystemUpdateFlagType> createModificatorTypeSystemUpdate(SystemUpdateFlagType value) {
        return new JAXBElement<>(_ModificatorTypeSystemUpdate_QNAME, SystemUpdateFlagType.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "SystemName", scope = ModificatorType.class)
    public JAXBElement<String> createModificatorTypeSystemName(String value) {
        return new JAXBElement<>(_SystemName_QNAME, String.class, ModificatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthValue", scope = DrugStrengthType.class)
    public JAXBElement<BigDecimal> createDrugStrengthTypeDrugStrengthValue(BigDecimal value) {
        return new JAXBElement<>(_DrugStrengthValue_QNAME, BigDecimal.class, DrugStrengthType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthUnitCode", scope = DrugStrengthType.class)
    public JAXBElement<String> createDrugStrengthTypeDrugStrengthUnitCode(String value) {
        return new JAXBElement<>(_DrugStrengthUnitCode_QNAME, String.class, DrugStrengthType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthUnitText", scope = DrugStrengthType.class)
    public JAXBElement<String> createDrugStrengthTypeDrugStrengthUnitText(String value) {
        return new JAXBElement<>(_DrugStrengthUnitText_QNAME, String.class, DrugStrengthType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", name = "DrugStrengthText", scope = DrugStrengthType.class)
    public JAXBElement<String> createDrugStrengthTypeDrugStrengthText(String value) {
        return new JAXBElement<>(_DrugStrengthText_QNAME, String.class, DrugStrengthType.class, value);
    }

}
