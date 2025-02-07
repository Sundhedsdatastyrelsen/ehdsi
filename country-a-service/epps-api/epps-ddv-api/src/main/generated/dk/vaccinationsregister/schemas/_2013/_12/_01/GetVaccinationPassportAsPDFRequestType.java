
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetVaccinationPassportAsPDFRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetVaccinationPassportAsPDFRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="VaccinationPlanCategory" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanCategoryType"/>
 *         <element name="NegativeConsentRequest" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentRequestType" minOccurs="0"/>
 *         <element name="IncludePdf" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="IncludeVaccinationStatus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="IncludeVaccinesAndDrugs" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="IncludeEffectuationCountryCode" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="IncludeAdditionalEffectuations" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetVaccinationPassportAsPDFRequestType", propOrder = {
    "personCivilRegistrationIdentifier",
    "vaccinationPlanCategory",
    "negativeConsentRequest",
    "includePdf",
    "includeVaccinationStatus",
    "includeVaccinesAndDrugs",
    "includeEffectuationCountryCode",
    "includeAdditionalEffectuations"
})
public class GetVaccinationPassportAsPDFRequestType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "VaccinationPlanCategory", required = true)
    protected String vaccinationPlanCategory;
    @XmlElement(name = "NegativeConsentRequest")
    @XmlSchemaType(name = "string")
    protected NegativeConsentRequestType negativeConsentRequest;
    @XmlElement(name = "IncludePdf")
    protected Boolean includePdf;
    @XmlElement(name = "IncludeVaccinationStatus")
    protected Boolean includeVaccinationStatus;
    @XmlElement(name = "IncludeVaccinesAndDrugs")
    protected Boolean includeVaccinesAndDrugs;
    @XmlElement(name = "IncludeEffectuationCountryCode")
    protected Boolean includeEffectuationCountryCode;
    @XmlElement(name = "IncludeAdditionalEffectuations")
    protected Boolean includeAdditionalEffectuations;

    /**
     * Gets the value of the personCivilRegistrationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonCivilRegistrationIdentifier() {
        return personCivilRegistrationIdentifier;
    }

    /**
     * Sets the value of the personCivilRegistrationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonCivilRegistrationIdentifier(String value) {
        this.personCivilRegistrationIdentifier = value;
    }

    /**
     * Gets the value of the vaccinationPlanCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaccinationPlanCategory() {
        return vaccinationPlanCategory;
    }

    /**
     * Sets the value of the vaccinationPlanCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaccinationPlanCategory(String value) {
        this.vaccinationPlanCategory = value;
    }

    /**
     * Gets the value of the negativeConsentRequest property.
     * 
     * @return
     *     possible object is
     *     {@link NegativeConsentRequestType }
     *     
     */
    public NegativeConsentRequestType getNegativeConsentRequest() {
        return negativeConsentRequest;
    }

    /**
     * Sets the value of the negativeConsentRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link NegativeConsentRequestType }
     *     
     */
    public void setNegativeConsentRequest(NegativeConsentRequestType value) {
        this.negativeConsentRequest = value;
    }

    /**
     * Gets the value of the includePdf property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludePdf() {
        return includePdf;
    }

    /**
     * Sets the value of the includePdf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludePdf(Boolean value) {
        this.includePdf = value;
    }

    /**
     * Gets the value of the includeVaccinationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeVaccinationStatus() {
        return includeVaccinationStatus;
    }

    /**
     * Sets the value of the includeVaccinationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeVaccinationStatus(Boolean value) {
        this.includeVaccinationStatus = value;
    }

    /**
     * Gets the value of the includeVaccinesAndDrugs property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeVaccinesAndDrugs() {
        return includeVaccinesAndDrugs;
    }

    /**
     * Sets the value of the includeVaccinesAndDrugs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeVaccinesAndDrugs(Boolean value) {
        this.includeVaccinesAndDrugs = value;
    }

    /**
     * Gets the value of the includeEffectuationCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeEffectuationCountryCode() {
        return includeEffectuationCountryCode;
    }

    /**
     * Sets the value of the includeEffectuationCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeEffectuationCountryCode(Boolean value) {
        this.includeEffectuationCountryCode = value;
    }

    /**
     * Gets the value of the includeAdditionalEffectuations property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeAdditionalEffectuations() {
        return includeAdditionalEffectuations;
    }

    /**
     * Sets the value of the includeAdditionalEffectuations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeAdditionalEffectuations(Boolean value) {
        this.includeAdditionalEffectuations = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetVaccinationPassportAsPDFRequestType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.vaccinationPlanCategory = this.vaccinationPlanCategory;
        _other.negativeConsentRequest = this.negativeConsentRequest;
        _other.includePdf = this.includePdf;
        _other.includeVaccinationStatus = this.includeVaccinationStatus;
        _other.includeVaccinesAndDrugs = this.includeVaccinesAndDrugs;
        _other.includeEffectuationCountryCode = this.includeEffectuationCountryCode;
        _other.includeAdditionalEffectuations = this.includeAdditionalEffectuations;
    }

    public<_B >GetVaccinationPassportAsPDFRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetVaccinationPassportAsPDFRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetVaccinationPassportAsPDFRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetVaccinationPassportAsPDFRequestType.Builder<Void> builder() {
        return new GetVaccinationPassportAsPDFRequestType.Builder<>(null, null, false);
    }

    public static<_B >GetVaccinationPassportAsPDFRequestType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFRequestType _other) {
        final GetVaccinationPassportAsPDFRequestType.Builder<_B> _newBuilder = new GetVaccinationPassportAsPDFRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetVaccinationPassportAsPDFRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree vaccinationPlanCategoryPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanCategory"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanCategoryPropertyTree!= null):((vaccinationPlanCategoryPropertyTree == null)||(!vaccinationPlanCategoryPropertyTree.isLeaf())))) {
            _other.vaccinationPlanCategory = this.vaccinationPlanCategory;
        }
        final PropertyTree negativeConsentRequestPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentRequest"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentRequestPropertyTree!= null):((negativeConsentRequestPropertyTree == null)||(!negativeConsentRequestPropertyTree.isLeaf())))) {
            _other.negativeConsentRequest = this.negativeConsentRequest;
        }
        final PropertyTree includePdfPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includePdf"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includePdfPropertyTree!= null):((includePdfPropertyTree == null)||(!includePdfPropertyTree.isLeaf())))) {
            _other.includePdf = this.includePdf;
        }
        final PropertyTree includeVaccinationStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinationStatus"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinationStatusPropertyTree!= null):((includeVaccinationStatusPropertyTree == null)||(!includeVaccinationStatusPropertyTree.isLeaf())))) {
            _other.includeVaccinationStatus = this.includeVaccinationStatus;
        }
        final PropertyTree includeVaccinesAndDrugsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinesAndDrugs"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinesAndDrugsPropertyTree!= null):((includeVaccinesAndDrugsPropertyTree == null)||(!includeVaccinesAndDrugsPropertyTree.isLeaf())))) {
            _other.includeVaccinesAndDrugs = this.includeVaccinesAndDrugs;
        }
        final PropertyTree includeEffectuationCountryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeEffectuationCountryCode"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeEffectuationCountryCodePropertyTree!= null):((includeEffectuationCountryCodePropertyTree == null)||(!includeEffectuationCountryCodePropertyTree.isLeaf())))) {
            _other.includeEffectuationCountryCode = this.includeEffectuationCountryCode;
        }
        final PropertyTree includeAdditionalEffectuationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeAdditionalEffectuations"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeAdditionalEffectuationsPropertyTree!= null):((includeAdditionalEffectuationsPropertyTree == null)||(!includeAdditionalEffectuationsPropertyTree.isLeaf())))) {
            _other.includeAdditionalEffectuations = this.includeAdditionalEffectuations;
        }
    }

    public<_B >GetVaccinationPassportAsPDFRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetVaccinationPassportAsPDFRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetVaccinationPassportAsPDFRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetVaccinationPassportAsPDFRequestType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetVaccinationPassportAsPDFRequestType.Builder<_B> _newBuilder = new GetVaccinationPassportAsPDFRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetVaccinationPassportAsPDFRequestType.Builder<Void> copyExcept(final GetVaccinationPassportAsPDFRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetVaccinationPassportAsPDFRequestType.Builder<Void> copyOnly(final GetVaccinationPassportAsPDFRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetVaccinationPassportAsPDFRequestType _storedValue;
        private String personCivilRegistrationIdentifier;
        private String vaccinationPlanCategory;
        private NegativeConsentRequestType negativeConsentRequest;
        private Boolean includePdf;
        private Boolean includeVaccinationStatus;
        private Boolean includeVaccinesAndDrugs;
        private Boolean includeEffectuationCountryCode;
        private Boolean includeAdditionalEffectuations;

        public Builder(final _B _parentBuilder, final GetVaccinationPassportAsPDFRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.vaccinationPlanCategory = _other.vaccinationPlanCategory;
                    this.negativeConsentRequest = _other.negativeConsentRequest;
                    this.includePdf = _other.includePdf;
                    this.includeVaccinationStatus = _other.includeVaccinationStatus;
                    this.includeVaccinesAndDrugs = _other.includeVaccinesAndDrugs;
                    this.includeEffectuationCountryCode = _other.includeEffectuationCountryCode;
                    this.includeAdditionalEffectuations = _other.includeAdditionalEffectuations;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetVaccinationPassportAsPDFRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
                        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    }
                    final PropertyTree vaccinationPlanCategoryPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanCategory"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanCategoryPropertyTree!= null):((vaccinationPlanCategoryPropertyTree == null)||(!vaccinationPlanCategoryPropertyTree.isLeaf())))) {
                        this.vaccinationPlanCategory = _other.vaccinationPlanCategory;
                    }
                    final PropertyTree negativeConsentRequestPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentRequest"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentRequestPropertyTree!= null):((negativeConsentRequestPropertyTree == null)||(!negativeConsentRequestPropertyTree.isLeaf())))) {
                        this.negativeConsentRequest = _other.negativeConsentRequest;
                    }
                    final PropertyTree includePdfPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includePdf"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includePdfPropertyTree!= null):((includePdfPropertyTree == null)||(!includePdfPropertyTree.isLeaf())))) {
                        this.includePdf = _other.includePdf;
                    }
                    final PropertyTree includeVaccinationStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinationStatus"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinationStatusPropertyTree!= null):((includeVaccinationStatusPropertyTree == null)||(!includeVaccinationStatusPropertyTree.isLeaf())))) {
                        this.includeVaccinationStatus = _other.includeVaccinationStatus;
                    }
                    final PropertyTree includeVaccinesAndDrugsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinesAndDrugs"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinesAndDrugsPropertyTree!= null):((includeVaccinesAndDrugsPropertyTree == null)||(!includeVaccinesAndDrugsPropertyTree.isLeaf())))) {
                        this.includeVaccinesAndDrugs = _other.includeVaccinesAndDrugs;
                    }
                    final PropertyTree includeEffectuationCountryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeEffectuationCountryCode"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeEffectuationCountryCodePropertyTree!= null):((includeEffectuationCountryCodePropertyTree == null)||(!includeEffectuationCountryCodePropertyTree.isLeaf())))) {
                        this.includeEffectuationCountryCode = _other.includeEffectuationCountryCode;
                    }
                    final PropertyTree includeAdditionalEffectuationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeAdditionalEffectuations"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeAdditionalEffectuationsPropertyTree!= null):((includeAdditionalEffectuationsPropertyTree == null)||(!includeAdditionalEffectuationsPropertyTree.isLeaf())))) {
                        this.includeAdditionalEffectuations = _other.includeAdditionalEffectuations;
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

        protected<_P extends GetVaccinationPassportAsPDFRequestType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.vaccinationPlanCategory = this.vaccinationPlanCategory;
            _product.negativeConsentRequest = this.negativeConsentRequest;
            _product.includePdf = this.includePdf;
            _product.includeVaccinationStatus = this.includeVaccinationStatus;
            _product.includeVaccinesAndDrugs = this.includeVaccinesAndDrugs;
            _product.includeEffectuationCountryCode = this.includeEffectuationCountryCode;
            _product.includeAdditionalEffectuations = this.includeAdditionalEffectuations;
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
            this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "vaccinationPlanCategory" (any previous value will be
         * replaced)
         * 
         * @param vaccinationPlanCategory
         *     New value of the "vaccinationPlanCategory" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withVaccinationPlanCategory(final String vaccinationPlanCategory) {
            this.vaccinationPlanCategory = vaccinationPlanCategory;
            return this;
        }

        /**
         * Sets the new value of "negativeConsentRequest" (any previous value will be
         * replaced)
         * 
         * @param negativeConsentRequest
         *     New value of the "negativeConsentRequest" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withNegativeConsentRequest(final NegativeConsentRequestType negativeConsentRequest) {
            this.negativeConsentRequest = negativeConsentRequest;
            return this;
        }

        /**
         * Sets the new value of "includePdf" (any previous value will be replaced)
         * 
         * @param includePdf
         *     New value of the "includePdf" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withIncludePdf(final Boolean includePdf) {
            this.includePdf = includePdf;
            return this;
        }

        /**
         * Sets the new value of "includeVaccinationStatus" (any previous value will be
         * replaced)
         * 
         * @param includeVaccinationStatus
         *     New value of the "includeVaccinationStatus" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withIncludeVaccinationStatus(final Boolean includeVaccinationStatus) {
            this.includeVaccinationStatus = includeVaccinationStatus;
            return this;
        }

        /**
         * Sets the new value of "includeVaccinesAndDrugs" (any previous value will be
         * replaced)
         * 
         * @param includeVaccinesAndDrugs
         *     New value of the "includeVaccinesAndDrugs" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withIncludeVaccinesAndDrugs(final Boolean includeVaccinesAndDrugs) {
            this.includeVaccinesAndDrugs = includeVaccinesAndDrugs;
            return this;
        }

        /**
         * Sets the new value of "includeEffectuationCountryCode" (any previous value will
         * be replaced)
         * 
         * @param includeEffectuationCountryCode
         *     New value of the "includeEffectuationCountryCode" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withIncludeEffectuationCountryCode(final Boolean includeEffectuationCountryCode) {
            this.includeEffectuationCountryCode = includeEffectuationCountryCode;
            return this;
        }

        /**
         * Sets the new value of "includeAdditionalEffectuations" (any previous value will
         * be replaced)
         * 
         * @param includeAdditionalEffectuations
         *     New value of the "includeAdditionalEffectuations" property.
         */
        public GetVaccinationPassportAsPDFRequestType.Builder<_B> withIncludeAdditionalEffectuations(final Boolean includeAdditionalEffectuations) {
            this.includeAdditionalEffectuations = includeAdditionalEffectuations;
            return this;
        }

        @Override
        public GetVaccinationPassportAsPDFRequestType build() {
            if (_storedValue == null) {
                return this.init(new GetVaccinationPassportAsPDFRequestType());
            } else {
                return ((GetVaccinationPassportAsPDFRequestType) _storedValue);
            }
        }

        public GetVaccinationPassportAsPDFRequestType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetVaccinationPassportAsPDFRequestType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetVaccinationPassportAsPDFRequestType.Selector<GetVaccinationPassportAsPDFRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetVaccinationPassportAsPDFRequestType.Select _root() {
            return new GetVaccinationPassportAsPDFRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> vaccinationPlanCategory = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> negativeConsentRequest = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includePdf = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeVaccinationStatus = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeVaccinesAndDrugs = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeEffectuationCountryCode = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeAdditionalEffectuations = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.personCivilRegistrationIdentifier!= null) {
                products.put("personCivilRegistrationIdentifier", this.personCivilRegistrationIdentifier.init());
            }
            if (this.vaccinationPlanCategory!= null) {
                products.put("vaccinationPlanCategory", this.vaccinationPlanCategory.init());
            }
            if (this.negativeConsentRequest!= null) {
                products.put("negativeConsentRequest", this.negativeConsentRequest.init());
            }
            if (this.includePdf!= null) {
                products.put("includePdf", this.includePdf.init());
            }
            if (this.includeVaccinationStatus!= null) {
                products.put("includeVaccinationStatus", this.includeVaccinationStatus.init());
            }
            if (this.includeVaccinesAndDrugs!= null) {
                products.put("includeVaccinesAndDrugs", this.includeVaccinesAndDrugs.init());
            }
            if (this.includeEffectuationCountryCode!= null) {
                products.put("includeEffectuationCountryCode", this.includeEffectuationCountryCode.init());
            }
            if (this.includeAdditionalEffectuations!= null) {
                products.put("includeAdditionalEffectuations", this.includeAdditionalEffectuations.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> vaccinationPlanCategory() {
            return ((this.vaccinationPlanCategory == null)?this.vaccinationPlanCategory = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanCategory"):this.vaccinationPlanCategory);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> negativeConsentRequest() {
            return ((this.negativeConsentRequest == null)?this.negativeConsentRequest = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentRequest"):this.negativeConsentRequest);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includePdf() {
            return ((this.includePdf == null)?this.includePdf = new com.kscs.util.jaxb.Selector<>(this._root, this, "includePdf"):this.includePdf);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeVaccinationStatus() {
            return ((this.includeVaccinationStatus == null)?this.includeVaccinationStatus = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeVaccinationStatus"):this.includeVaccinationStatus);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeVaccinesAndDrugs() {
            return ((this.includeVaccinesAndDrugs == null)?this.includeVaccinesAndDrugs = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeVaccinesAndDrugs"):this.includeVaccinesAndDrugs);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeEffectuationCountryCode() {
            return ((this.includeEffectuationCountryCode == null)?this.includeEffectuationCountryCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeEffectuationCountryCode"):this.includeEffectuationCountryCode);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFRequestType.Selector<TRoot, TParent>> includeAdditionalEffectuations() {
            return ((this.includeAdditionalEffectuations == null)?this.includeAdditionalEffectuations = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeAdditionalEffectuations"):this.includeAdditionalEffectuations);
        }

    }

}
