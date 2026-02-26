
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
