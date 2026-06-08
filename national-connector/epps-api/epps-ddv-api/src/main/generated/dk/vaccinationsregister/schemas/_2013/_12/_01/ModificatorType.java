
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModificatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ModificatorType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="AuthorisedHealthcareProfessional" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisedHealthcareProfessionalType" minOccurs="0"/>
 *         <element name="Organisation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationType" minOccurs="0"/>
 *         <element name="AuthorisedBy" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisedModificatorType" minOccurs="0"/>
 *         <element name="Patient" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PatientFlagType" minOccurs="0"/>
 *         <element name="Other" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModificatorPersonType" minOccurs="0"/>
 *         <element name="Role" type="{http://vaccinationsregister.dk/schemas/2013/12/01}RequestedRoleType" minOccurs="0"/>
 *         <element name="PartlyDefinedEffectuator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PartlyDefinedEffectuatorType" minOccurs="0"/>
 *         <element name="HealthInsuranceImport" type="{http://vaccinationsregister.dk/schemas/2013/12/01}HealthInsuranceImportFlagType" minOccurs="0"/>
 *         <element name="SystemUpdate" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SystemUpdateFlagType" minOccurs="0"/>
 *         <element name="SystemName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SystemNameType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModificatorType", propOrder = {
    "authorisedHealthcareProfessional",
    "organisation",
    "authorisedBy",
    "patient",
    "other",
    "role",
    "partlyDefinedEffectuator",
    "healthInsuranceImport",
    "systemUpdate",
    "systemName"
})
public class ModificatorType {

    @XmlElement(name = "AuthorisedHealthcareProfessional")
    protected AuthorisedHealthcareProfessionalType authorisedHealthcareProfessional;
    @XmlElement(name = "Organisation")
    protected OrganisationType organisation;
    @XmlElement(name = "AuthorisedBy")
    protected AuthorisedModificatorType authorisedBy;
    @XmlElement(name = "Patient")
    protected PatientFlagType patient;
    @XmlElement(name = "Other")
    protected ModificatorPersonType other;
    @XmlElement(name = "Role")
    protected String role;
    @XmlElement(name = "PartlyDefinedEffectuator")
    protected PartlyDefinedEffectuatorType partlyDefinedEffectuator;
    @XmlElement(name = "HealthInsuranceImport")
    protected HealthInsuranceImportFlagType healthInsuranceImport;
    @XmlElement(name = "SystemUpdate")
    protected SystemUpdateFlagType systemUpdate;
    @XmlElement(name = "SystemName")
    protected String systemName;

    /**
     * Gets the value of the authorisedHealthcareProfessional property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorisedHealthcareProfessionalType }
     *     
     */
    public AuthorisedHealthcareProfessionalType getAuthorisedHealthcareProfessional() {
        return authorisedHealthcareProfessional;
    }

    /**
     * Sets the value of the authorisedHealthcareProfessional property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorisedHealthcareProfessionalType }
     *     
     */
    public void setAuthorisedHealthcareProfessional(AuthorisedHealthcareProfessionalType value) {
        this.authorisedHealthcareProfessional = value;
    }

    /**
     * Gets the value of the organisation property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationType }
     *     
     */
    public OrganisationType getOrganisation() {
        return organisation;
    }

    /**
     * Sets the value of the organisation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationType }
     *     
     */
    public void setOrganisation(OrganisationType value) {
        this.organisation = value;
    }

    /**
     * Gets the value of the authorisedBy property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorisedModificatorType }
     *     
     */
    public AuthorisedModificatorType getAuthorisedBy() {
        return authorisedBy;
    }

    /**
     * Sets the value of the authorisedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorisedModificatorType }
     *     
     */
    public void setAuthorisedBy(AuthorisedModificatorType value) {
        this.authorisedBy = value;
    }

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link PatientFlagType }
     *     
     */
    public PatientFlagType getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientFlagType }
     *     
     */
    public void setPatient(PatientFlagType value) {
        this.patient = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link ModificatorPersonType }
     *     
     */
    public ModificatorPersonType getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModificatorPersonType }
     *     
     */
    public void setOther(ModificatorPersonType value) {
        this.other = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the partlyDefinedEffectuator property.
     * 
     * @return
     *     possible object is
     *     {@link PartlyDefinedEffectuatorType }
     *     
     */
    public PartlyDefinedEffectuatorType getPartlyDefinedEffectuator() {
        return partlyDefinedEffectuator;
    }

    /**
     * Sets the value of the partlyDefinedEffectuator property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartlyDefinedEffectuatorType }
     *     
     */
    public void setPartlyDefinedEffectuator(PartlyDefinedEffectuatorType value) {
        this.partlyDefinedEffectuator = value;
    }

    /**
     * Gets the value of the healthInsuranceImport property.
     * 
     * @return
     *     possible object is
     *     {@link HealthInsuranceImportFlagType }
     *     
     */
    public HealthInsuranceImportFlagType getHealthInsuranceImport() {
        return healthInsuranceImport;
    }

    /**
     * Sets the value of the healthInsuranceImport property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthInsuranceImportFlagType }
     *     
     */
    public void setHealthInsuranceImport(HealthInsuranceImportFlagType value) {
        this.healthInsuranceImport = value;
    }

    /**
     * Gets the value of the systemUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link SystemUpdateFlagType }
     *     
     */
    public SystemUpdateFlagType getSystemUpdate() {
        return systemUpdate;
    }

    /**
     * Sets the value of the systemUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemUpdateFlagType }
     *     
     */
    public void setSystemUpdate(SystemUpdateFlagType value) {
        this.systemUpdate = value;
    }

    /**
     * Gets the value of the systemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * Sets the value of the systemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemName(String value) {
        this.systemName = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ModificatorType.Builder<_B> _other) {
        _other.authorisedHealthcareProfessional = ((this.authorisedHealthcareProfessional == null)?null:this.authorisedHealthcareProfessional.newCopyBuilder(_other));
        _other.organisation = ((this.organisation == null)?null:this.organisation.newCopyBuilder(_other));
        _other.authorisedBy = ((this.authorisedBy == null)?null:this.authorisedBy.newCopyBuilder(_other));
        _other.patient = ((this.patient == null)?null:this.patient.newCopyBuilder(_other));
        _other.other = ((this.other == null)?null:this.other.newCopyBuilder(_other));
        _other.role = this.role;
        _other.partlyDefinedEffectuator = ((this.partlyDefinedEffectuator == null)?null:this.partlyDefinedEffectuator.newCopyBuilder(_other));
        _other.healthInsuranceImport = ((this.healthInsuranceImport == null)?null:this.healthInsuranceImport.newCopyBuilder(_other));
        _other.systemUpdate = ((this.systemUpdate == null)?null:this.systemUpdate.newCopyBuilder(_other));
        _other.systemName = this.systemName;
    }

    public<_B >ModificatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ModificatorType.Builder<_B>(_parentBuilder, this, true);
    }

    public ModificatorType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ModificatorType.Builder<Void> builder() {
        return new ModificatorType.Builder<>(null, null, false);
    }

    public static<_B >ModificatorType.Builder<_B> copyOf(final ModificatorType _other) {
        final ModificatorType.Builder<_B> _newBuilder = new ModificatorType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder);
        return _newBuilder;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ModificatorType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree authorisedHealthcareProfessionalPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisedHealthcareProfessional"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisedHealthcareProfessionalPropertyTree!= null):((authorisedHealthcareProfessionalPropertyTree == null)||(!authorisedHealthcareProfessionalPropertyTree.isLeaf())))) {
            _other.authorisedHealthcareProfessional = ((this.authorisedHealthcareProfessional == null)?null:this.authorisedHealthcareProfessional.newCopyBuilder(_other, authorisedHealthcareProfessionalPropertyTree, _propertyTreeUse));
        }
        final PropertyTree organisationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("organisation"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(organisationPropertyTree!= null):((organisationPropertyTree == null)||(!organisationPropertyTree.isLeaf())))) {
            _other.organisation = ((this.organisation == null)?null:this.organisation.newCopyBuilder(_other, organisationPropertyTree, _propertyTreeUse));
        }
        final PropertyTree authorisedByPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisedBy"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisedByPropertyTree!= null):((authorisedByPropertyTree == null)||(!authorisedByPropertyTree.isLeaf())))) {
            _other.authorisedBy = ((this.authorisedBy == null)?null:this.authorisedBy.newCopyBuilder(_other, authorisedByPropertyTree, _propertyTreeUse));
        }
        final PropertyTree patientPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("patient"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(patientPropertyTree!= null):((patientPropertyTree == null)||(!patientPropertyTree.isLeaf())))) {
            _other.patient = ((this.patient == null)?null:this.patient.newCopyBuilder(_other, patientPropertyTree, _propertyTreeUse));
        }
        final PropertyTree otherPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("other"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(otherPropertyTree!= null):((otherPropertyTree == null)||(!otherPropertyTree.isLeaf())))) {
            _other.other = ((this.other == null)?null:this.other.newCopyBuilder(_other, otherPropertyTree, _propertyTreeUse));
        }
        final PropertyTree rolePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("role"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(rolePropertyTree!= null):((rolePropertyTree == null)||(!rolePropertyTree.isLeaf())))) {
            _other.role = this.role;
        }
        final PropertyTree partlyDefinedEffectuatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("partlyDefinedEffectuator"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(partlyDefinedEffectuatorPropertyTree!= null):((partlyDefinedEffectuatorPropertyTree == null)||(!partlyDefinedEffectuatorPropertyTree.isLeaf())))) {
            _other.partlyDefinedEffectuator = ((this.partlyDefinedEffectuator == null)?null:this.partlyDefinedEffectuator.newCopyBuilder(_other, partlyDefinedEffectuatorPropertyTree, _propertyTreeUse));
        }
        final PropertyTree healthInsuranceImportPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("healthInsuranceImport"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(healthInsuranceImportPropertyTree!= null):((healthInsuranceImportPropertyTree == null)||(!healthInsuranceImportPropertyTree.isLeaf())))) {
            _other.healthInsuranceImport = ((this.healthInsuranceImport == null)?null:this.healthInsuranceImport.newCopyBuilder(_other, healthInsuranceImportPropertyTree, _propertyTreeUse));
        }
        final PropertyTree systemUpdatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemUpdate"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemUpdatePropertyTree!= null):((systemUpdatePropertyTree == null)||(!systemUpdatePropertyTree.isLeaf())))) {
            _other.systemUpdate = ((this.systemUpdate == null)?null:this.systemUpdate.newCopyBuilder(_other, systemUpdatePropertyTree, _propertyTreeUse));
        }
        final PropertyTree systemNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemNamePropertyTree!= null):((systemNamePropertyTree == null)||(!systemNamePropertyTree.isLeaf())))) {
            _other.systemName = this.systemName;
        }
    }

    public<_B >ModificatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ModificatorType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ModificatorType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ModificatorType.Builder<_B> copyOf(final ModificatorType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ModificatorType.Builder<_B> _newBuilder = new ModificatorType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ModificatorType.Builder<Void> copyExcept(final ModificatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ModificatorType.Builder<Void> copyOnly(final ModificatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ModificatorType _storedValue;
        private AuthorisedHealthcareProfessionalType.Builder<ModificatorType.Builder<_B>> authorisedHealthcareProfessional;
        private OrganisationType.Builder<ModificatorType.Builder<_B>> organisation;
        private AuthorisedModificatorType.Builder<ModificatorType.Builder<_B>> authorisedBy;
        private PatientFlagType.Builder<ModificatorType.Builder<_B>> patient;
        private ModificatorPersonType.Builder<ModificatorType.Builder<_B>> other;
        private String role;
        private PartlyDefinedEffectuatorType.Builder<ModificatorType.Builder<_B>> partlyDefinedEffectuator;
        private HealthInsuranceImportFlagType.Builder<ModificatorType.Builder<_B>> healthInsuranceImport;
        private SystemUpdateFlagType.Builder<ModificatorType.Builder<_B>> systemUpdate;
        private String systemName;

        public Builder(final _B _parentBuilder, final ModificatorType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.authorisedHealthcareProfessional = ((_other.authorisedHealthcareProfessional == null)?null:_other.authorisedHealthcareProfessional.newCopyBuilder(this));
                    this.organisation = ((_other.organisation == null)?null:_other.organisation.newCopyBuilder(this));
                    this.authorisedBy = ((_other.authorisedBy == null)?null:_other.authorisedBy.newCopyBuilder(this));
                    this.patient = ((_other.patient == null)?null:_other.patient.newCopyBuilder(this));
                    this.other = ((_other.other == null)?null:_other.other.newCopyBuilder(this));
                    this.role = _other.role;
                    this.partlyDefinedEffectuator = ((_other.partlyDefinedEffectuator == null)?null:_other.partlyDefinedEffectuator.newCopyBuilder(this));
                    this.healthInsuranceImport = ((_other.healthInsuranceImport == null)?null:_other.healthInsuranceImport.newCopyBuilder(this));
                    this.systemUpdate = ((_other.systemUpdate == null)?null:_other.systemUpdate.newCopyBuilder(this));
                    this.systemName = _other.systemName;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ModificatorType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree authorisedHealthcareProfessionalPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisedHealthcareProfessional"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisedHealthcareProfessionalPropertyTree!= null):((authorisedHealthcareProfessionalPropertyTree == null)||(!authorisedHealthcareProfessionalPropertyTree.isLeaf())))) {
                        this.authorisedHealthcareProfessional = ((_other.authorisedHealthcareProfessional == null)?null:_other.authorisedHealthcareProfessional.newCopyBuilder(this, authorisedHealthcareProfessionalPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree organisationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("organisation"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(organisationPropertyTree!= null):((organisationPropertyTree == null)||(!organisationPropertyTree.isLeaf())))) {
                        this.organisation = ((_other.organisation == null)?null:_other.organisation.newCopyBuilder(this, organisationPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree authorisedByPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisedBy"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisedByPropertyTree!= null):((authorisedByPropertyTree == null)||(!authorisedByPropertyTree.isLeaf())))) {
                        this.authorisedBy = ((_other.authorisedBy == null)?null:_other.authorisedBy.newCopyBuilder(this, authorisedByPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree patientPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("patient"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(patientPropertyTree!= null):((patientPropertyTree == null)||(!patientPropertyTree.isLeaf())))) {
                        this.patient = ((_other.patient == null)?null:_other.patient.newCopyBuilder(this, patientPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree otherPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("other"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(otherPropertyTree!= null):((otherPropertyTree == null)||(!otherPropertyTree.isLeaf())))) {
                        this.other = ((_other.other == null)?null:_other.other.newCopyBuilder(this, otherPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree rolePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("role"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(rolePropertyTree!= null):((rolePropertyTree == null)||(!rolePropertyTree.isLeaf())))) {
                        this.role = _other.role;
                    }
                    final PropertyTree partlyDefinedEffectuatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("partlyDefinedEffectuator"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(partlyDefinedEffectuatorPropertyTree!= null):((partlyDefinedEffectuatorPropertyTree == null)||(!partlyDefinedEffectuatorPropertyTree.isLeaf())))) {
                        this.partlyDefinedEffectuator = ((_other.partlyDefinedEffectuator == null)?null:_other.partlyDefinedEffectuator.newCopyBuilder(this, partlyDefinedEffectuatorPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree healthInsuranceImportPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("healthInsuranceImport"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(healthInsuranceImportPropertyTree!= null):((healthInsuranceImportPropertyTree == null)||(!healthInsuranceImportPropertyTree.isLeaf())))) {
                        this.healthInsuranceImport = ((_other.healthInsuranceImport == null)?null:_other.healthInsuranceImport.newCopyBuilder(this, healthInsuranceImportPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree systemUpdatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemUpdate"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemUpdatePropertyTree!= null):((systemUpdatePropertyTree == null)||(!systemUpdatePropertyTree.isLeaf())))) {
                        this.systemUpdate = ((_other.systemUpdate == null)?null:_other.systemUpdate.newCopyBuilder(this, systemUpdatePropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree systemNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemNamePropertyTree!= null):((systemNamePropertyTree == null)||(!systemNamePropertyTree.isLeaf())))) {
                        this.systemName = _other.systemName;
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public _B end() {
            return this._parentBuilder;
        }

        protected<_P extends ModificatorType >_P init(final _P _product) {
            _product.authorisedHealthcareProfessional = ((this.authorisedHealthcareProfessional == null)?null:this.authorisedHealthcareProfessional.build());
            _product.organisation = ((this.organisation == null)?null:this.organisation.build());
            _product.authorisedBy = ((this.authorisedBy == null)?null:this.authorisedBy.build());
            _product.patient = ((this.patient == null)?null:this.patient.build());
            _product.other = ((this.other == null)?null:this.other.build());
            _product.role = this.role;
            _product.partlyDefinedEffectuator = ((this.partlyDefinedEffectuator == null)?null:this.partlyDefinedEffectuator.build());
            _product.healthInsuranceImport = ((this.healthInsuranceImport == null)?null:this.healthInsuranceImport.build());
            _product.systemUpdate = ((this.systemUpdate == null)?null:this.systemUpdate.build());
            _product.systemName = this.systemName;
            return _product;
        }

        /**
         * Sets the new value of "authorisedHealthcareProfessional" (any previous value
         * will be replaced)
         * 
         * @param authorisedHealthcareProfessional
         *     New value of the "authorisedHealthcareProfessional" property.
         */
        public ModificatorType.Builder<_B> withAuthorisedHealthcareProfessional(final AuthorisedHealthcareProfessionalType authorisedHealthcareProfessional) {
            this.authorisedHealthcareProfessional = ((authorisedHealthcareProfessional == null)?null:new AuthorisedHealthcareProfessionalType.Builder<>(this, authorisedHealthcareProfessional, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "authorisedHealthcareProfessional" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.AuthorisedHealthcareProfessionalType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "authorisedHealthcareProfessional"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.AuthorisedHealthcareProfessionalType.Builder#end()}
         *     to return to the current builder.
         */
        public AuthorisedHealthcareProfessionalType.Builder<? extends ModificatorType.Builder<_B>> withAuthorisedHealthcareProfessional() {
            if (this.authorisedHealthcareProfessional!= null) {
                return this.authorisedHealthcareProfessional;
            }
            return this.authorisedHealthcareProfessional = new AuthorisedHealthcareProfessionalType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "organisation" (any previous value will be replaced)
         * 
         * @param organisation
         *     New value of the "organisation" property.
         */
        public ModificatorType.Builder<_B> withOrganisation(final OrganisationType organisation) {
            this.organisation = ((organisation == null)?null:new OrganisationType.Builder<>(this, organisation, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "organisation" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.OrganisationType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "organisation" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.OrganisationType.Builder#end()} to
         *     return to the current builder.
         */
        public OrganisationType.Builder<? extends ModificatorType.Builder<_B>> withOrganisation() {
            if (this.organisation!= null) {
                return this.organisation;
            }
            return this.organisation = new OrganisationType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "authorisedBy" (any previous value will be replaced)
         * 
         * @param authorisedBy
         *     New value of the "authorisedBy" property.
         */
        public ModificatorType.Builder<_B> withAuthorisedBy(final AuthorisedModificatorType authorisedBy) {
            this.authorisedBy = ((authorisedBy == null)?null:new AuthorisedModificatorType.Builder<>(this, authorisedBy, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "authorisedBy" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.AuthorisedModificatorType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "authorisedBy" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.AuthorisedModificatorType.Builder#end()}
         *     to return to the current builder.
         */
        public AuthorisedModificatorType.Builder<? extends ModificatorType.Builder<_B>> withAuthorisedBy() {
            if (this.authorisedBy!= null) {
                return this.authorisedBy;
            }
            return this.authorisedBy = new AuthorisedModificatorType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "patient" (any previous value will be replaced)
         * 
         * @param patient
         *     New value of the "patient" property.
         */
        public ModificatorType.Builder<_B> withPatient(final PatientFlagType patient) {
            this.patient = ((patient == null)?null:new PatientFlagType.Builder<>(this, patient, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "patient" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.PatientFlagType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "patient" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.PatientFlagType.Builder#end()} to
         *     return to the current builder.
         */
        public PatientFlagType.Builder<? extends ModificatorType.Builder<_B>> withPatient() {
            if (this.patient!= null) {
                return this.patient;
            }
            return this.patient = new PatientFlagType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "other" (any previous value will be replaced)
         * 
         * @param other
         *     New value of the "other" property.
         */
        public ModificatorType.Builder<_B> withOther(final ModificatorPersonType other) {
            this.other = ((other == null)?null:new ModificatorPersonType.Builder<>(this, other, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the "other"
         * property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.ModificatorPersonType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "other" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.ModificatorPersonType.Builder#end()}
         *     to return to the current builder.
         */
        public ModificatorPersonType.Builder<? extends ModificatorType.Builder<_B>> withOther() {
            if (this.other!= null) {
                return this.other;
            }
            return this.other = new ModificatorPersonType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "role" (any previous value will be replaced)
         * 
         * @param role
         *     New value of the "role" property.
         */
        public ModificatorType.Builder<_B> withRole(final String role) {
            this.role = role;
            return this;
        }

        /**
         * Sets the new value of "partlyDefinedEffectuator" (any previous value will be
         * replaced)
         * 
         * @param partlyDefinedEffectuator
         *     New value of the "partlyDefinedEffectuator" property.
         */
        public ModificatorType.Builder<_B> withPartlyDefinedEffectuator(final PartlyDefinedEffectuatorType partlyDefinedEffectuator) {
            this.partlyDefinedEffectuator = ((partlyDefinedEffectuator == null)?null:new PartlyDefinedEffectuatorType.Builder<>(this, partlyDefinedEffectuator, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "partlyDefinedEffectuator" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.PartlyDefinedEffectuatorType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "partlyDefinedEffectuator" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.PartlyDefinedEffectuatorType.Builder#end()}
         *     to return to the current builder.
         */
        public PartlyDefinedEffectuatorType.Builder<? extends ModificatorType.Builder<_B>> withPartlyDefinedEffectuator() {
            if (this.partlyDefinedEffectuator!= null) {
                return this.partlyDefinedEffectuator;
            }
            return this.partlyDefinedEffectuator = new PartlyDefinedEffectuatorType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "healthInsuranceImport" (any previous value will be
         * replaced)
         * 
         * @param healthInsuranceImport
         *     New value of the "healthInsuranceImport" property.
         */
        public ModificatorType.Builder<_B> withHealthInsuranceImport(final HealthInsuranceImportFlagType healthInsuranceImport) {
            this.healthInsuranceImport = ((healthInsuranceImport == null)?null:new HealthInsuranceImportFlagType.Builder<>(this, healthInsuranceImport, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "healthInsuranceImport" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.HealthInsuranceImportFlagType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "healthInsuranceImport" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.HealthInsuranceImportFlagType.Builder#end()}
         *     to return to the current builder.
         */
        public HealthInsuranceImportFlagType.Builder<? extends ModificatorType.Builder<_B>> withHealthInsuranceImport() {
            if (this.healthInsuranceImport!= null) {
                return this.healthInsuranceImport;
            }
            return this.healthInsuranceImport = new HealthInsuranceImportFlagType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "systemUpdate" (any previous value will be replaced)
         * 
         * @param systemUpdate
         *     New value of the "systemUpdate" property.
         */
        public ModificatorType.Builder<_B> withSystemUpdate(final SystemUpdateFlagType systemUpdate) {
            this.systemUpdate = ((systemUpdate == null)?null:new SystemUpdateFlagType.Builder<>(this, systemUpdate, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "systemUpdate" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.SystemUpdateFlagType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "systemUpdate" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.SystemUpdateFlagType.Builder#end()}
         *     to return to the current builder.
         */
        public SystemUpdateFlagType.Builder<? extends ModificatorType.Builder<_B>> withSystemUpdate() {
            if (this.systemUpdate!= null) {
                return this.systemUpdate;
            }
            return this.systemUpdate = new SystemUpdateFlagType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "systemName" (any previous value will be replaced)
         * 
         * @param systemName
         *     New value of the "systemName" property.
         */
        public ModificatorType.Builder<_B> withSystemName(final String systemName) {
            this.systemName = systemName;
            return this;
        }

        @Override
        public ModificatorType build() {
            if (_storedValue == null) {
                return this.init(new ModificatorType());
            } else {
                return ((ModificatorType) _storedValue);
            }
        }

        public ModificatorType.Builder<_B> copyOf(final ModificatorType _other) {
            _other.copyTo(this);
            return this;
        }

        public ModificatorType.Builder<_B> copyOf(final ModificatorType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ModificatorType.Selector<ModificatorType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ModificatorType.Select _root() {
            return new ModificatorType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private AuthorisedHealthcareProfessionalType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> authorisedHealthcareProfessional = null;
        private OrganisationType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> organisation = null;
        private AuthorisedModificatorType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> authorisedBy = null;
        private PatientFlagType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> patient = null;
        private ModificatorPersonType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> other = null;
        private com.kscs.util.jaxb.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> role = null;
        private PartlyDefinedEffectuatorType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> partlyDefinedEffectuator = null;
        private HealthInsuranceImportFlagType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> healthInsuranceImport = null;
        private SystemUpdateFlagType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> systemUpdate = null;
        private com.kscs.util.jaxb.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> systemName = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.authorisedHealthcareProfessional!= null) {
                products.put("authorisedHealthcareProfessional", this.authorisedHealthcareProfessional.init());
            }
            if (this.organisation!= null) {
                products.put("organisation", this.organisation.init());
            }
            if (this.authorisedBy!= null) {
                products.put("authorisedBy", this.authorisedBy.init());
            }
            if (this.patient!= null) {
                products.put("patient", this.patient.init());
            }
            if (this.other!= null) {
                products.put("other", this.other.init());
            }
            if (this.role!= null) {
                products.put("role", this.role.init());
            }
            if (this.partlyDefinedEffectuator!= null) {
                products.put("partlyDefinedEffectuator", this.partlyDefinedEffectuator.init());
            }
            if (this.healthInsuranceImport!= null) {
                products.put("healthInsuranceImport", this.healthInsuranceImport.init());
            }
            if (this.systemUpdate!= null) {
                products.put("systemUpdate", this.systemUpdate.init());
            }
            if (this.systemName!= null) {
                products.put("systemName", this.systemName.init());
            }
            return products;
        }

        public AuthorisedHealthcareProfessionalType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> authorisedHealthcareProfessional() {
            return ((this.authorisedHealthcareProfessional == null)?this.authorisedHealthcareProfessional = new AuthorisedHealthcareProfessionalType.Selector<>(this._root, this, "authorisedHealthcareProfessional"):this.authorisedHealthcareProfessional);
        }

        public OrganisationType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> organisation() {
            return ((this.organisation == null)?this.organisation = new OrganisationType.Selector<>(this._root, this, "organisation"):this.organisation);
        }

        public AuthorisedModificatorType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> authorisedBy() {
            return ((this.authorisedBy == null)?this.authorisedBy = new AuthorisedModificatorType.Selector<>(this._root, this, "authorisedBy"):this.authorisedBy);
        }

        public PatientFlagType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> patient() {
            return ((this.patient == null)?this.patient = new PatientFlagType.Selector<>(this._root, this, "patient"):this.patient);
        }

        public ModificatorPersonType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> other() {
            return ((this.other == null)?this.other = new ModificatorPersonType.Selector<>(this._root, this, "other"):this.other);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> role() {
            return ((this.role == null)?this.role = new com.kscs.util.jaxb.Selector<>(this._root, this, "role"):this.role);
        }

        public PartlyDefinedEffectuatorType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> partlyDefinedEffectuator() {
            return ((this.partlyDefinedEffectuator == null)?this.partlyDefinedEffectuator = new PartlyDefinedEffectuatorType.Selector<>(this._root, this, "partlyDefinedEffectuator"):this.partlyDefinedEffectuator);
        }

        public HealthInsuranceImportFlagType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> healthInsuranceImport() {
            return ((this.healthInsuranceImport == null)?this.healthInsuranceImport = new HealthInsuranceImportFlagType.Selector<>(this._root, this, "healthInsuranceImport"):this.healthInsuranceImport);
        }

        public SystemUpdateFlagType.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> systemUpdate() {
            return ((this.systemUpdate == null)?this.systemUpdate = new SystemUpdateFlagType.Selector<>(this._root, this, "systemUpdate"):this.systemUpdate);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> systemName() {
            return ((this.systemName == null)?this.systemName = new com.kscs.util.jaxb.Selector<>(this._root, this, "systemName"):this.systemName);
        }

    }

}
