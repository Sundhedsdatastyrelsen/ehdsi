
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
 * <p>Java class for ValidateVaccinationPassportRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ValidateVaccinationPassportRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="VaccinationPlanCategory" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanCategoryType"/>
 *         <element name="NegativeConsentRequest" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentRequestType" minOccurs="0"/>
 *         <element name="DocumentId" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DocumentIdType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateVaccinationPassportRequestType", propOrder = {
    "personCivilRegistrationIdentifier",
    "vaccinationPlanCategory",
    "negativeConsentRequest",
    "documentId"
})
public class ValidateVaccinationPassportRequestType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "VaccinationPlanCategory", required = true)
    protected String vaccinationPlanCategory;
    @XmlElement(name = "NegativeConsentRequest")
    @XmlSchemaType(name = "string")
    protected NegativeConsentRequestType negativeConsentRequest;
    @XmlElement(name = "DocumentId")
    protected String documentId;

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
     * Gets the value of the documentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the value of the documentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentId(String value) {
        this.documentId = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ValidateVaccinationPassportRequestType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.vaccinationPlanCategory = this.vaccinationPlanCategory;
        _other.negativeConsentRequest = this.negativeConsentRequest;
        _other.documentId = this.documentId;
    }

    public<_B >ValidateVaccinationPassportRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ValidateVaccinationPassportRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public ValidateVaccinationPassportRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ValidateVaccinationPassportRequestType.Builder<Void> builder() {
        return new ValidateVaccinationPassportRequestType.Builder<>(null, null, false);
    }

    public static<_B >ValidateVaccinationPassportRequestType.Builder<_B> copyOf(final ValidateVaccinationPassportRequestType _other) {
        final ValidateVaccinationPassportRequestType.Builder<_B> _newBuilder = new ValidateVaccinationPassportRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ValidateVaccinationPassportRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
        final PropertyTree documentIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("documentId"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(documentIdPropertyTree!= null):((documentIdPropertyTree == null)||(!documentIdPropertyTree.isLeaf())))) {
            _other.documentId = this.documentId;
        }
    }

    public<_B >ValidateVaccinationPassportRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ValidateVaccinationPassportRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ValidateVaccinationPassportRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ValidateVaccinationPassportRequestType.Builder<_B> copyOf(final ValidateVaccinationPassportRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ValidateVaccinationPassportRequestType.Builder<_B> _newBuilder = new ValidateVaccinationPassportRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ValidateVaccinationPassportRequestType.Builder<Void> copyExcept(final ValidateVaccinationPassportRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ValidateVaccinationPassportRequestType.Builder<Void> copyOnly(final ValidateVaccinationPassportRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ValidateVaccinationPassportRequestType _storedValue;
        private String personCivilRegistrationIdentifier;
        private String vaccinationPlanCategory;
        private NegativeConsentRequestType negativeConsentRequest;
        private String documentId;

        public Builder(final _B _parentBuilder, final ValidateVaccinationPassportRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.vaccinationPlanCategory = _other.vaccinationPlanCategory;
                    this.negativeConsentRequest = _other.negativeConsentRequest;
                    this.documentId = _other.documentId;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ValidateVaccinationPassportRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
                    final PropertyTree documentIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("documentId"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(documentIdPropertyTree!= null):((documentIdPropertyTree == null)||(!documentIdPropertyTree.isLeaf())))) {
                        this.documentId = _other.documentId;
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

        protected<_P extends ValidateVaccinationPassportRequestType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.vaccinationPlanCategory = this.vaccinationPlanCategory;
            _product.negativeConsentRequest = this.negativeConsentRequest;
            _product.documentId = this.documentId;
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public ValidateVaccinationPassportRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
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
        public ValidateVaccinationPassportRequestType.Builder<_B> withVaccinationPlanCategory(final String vaccinationPlanCategory) {
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
        public ValidateVaccinationPassportRequestType.Builder<_B> withNegativeConsentRequest(final NegativeConsentRequestType negativeConsentRequest) {
            this.negativeConsentRequest = negativeConsentRequest;
            return this;
        }

        /**
         * Sets the new value of "documentId" (any previous value will be replaced)
         * 
         * @param documentId
         *     New value of the "documentId" property.
         */
        public ValidateVaccinationPassportRequestType.Builder<_B> withDocumentId(final String documentId) {
            this.documentId = documentId;
            return this;
        }

        @Override
        public ValidateVaccinationPassportRequestType build() {
            if (_storedValue == null) {
                return this.init(new ValidateVaccinationPassportRequestType());
            } else {
                return ((ValidateVaccinationPassportRequestType) _storedValue);
            }
        }

        public ValidateVaccinationPassportRequestType.Builder<_B> copyOf(final ValidateVaccinationPassportRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public ValidateVaccinationPassportRequestType.Builder<_B> copyOf(final ValidateVaccinationPassportRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ValidateVaccinationPassportRequestType.Selector<ValidateVaccinationPassportRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ValidateVaccinationPassportRequestType.Select _root() {
            return new ValidateVaccinationPassportRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> vaccinationPlanCategory = null;
        private com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> negativeConsentRequest = null;
        private com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> documentId = null;

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
            if (this.documentId!= null) {
                products.put("documentId", this.documentId.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> vaccinationPlanCategory() {
            return ((this.vaccinationPlanCategory == null)?this.vaccinationPlanCategory = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanCategory"):this.vaccinationPlanCategory);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> negativeConsentRequest() {
            return ((this.negativeConsentRequest == null)?this.negativeConsentRequest = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentRequest"):this.negativeConsentRequest);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportRequestType.Selector<TRoot, TParent>> documentId() {
            return ((this.documentId == null)?this.documentId = new com.kscs.util.jaxb.Selector<>(this._root, this, "documentId"):this.documentId);
        }

    }

}
