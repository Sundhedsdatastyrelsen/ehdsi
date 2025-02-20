
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
 * <p>Java class for GetPlannedVaccinationsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetPlannedVaccinationsRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="NegativeConsentRequest" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentRequestType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPlannedVaccinationsRequestType", propOrder = {
    "personCivilRegistrationIdentifier",
    "negativeConsentRequest"
})
public class GetPlannedVaccinationsRequestType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "NegativeConsentRequest")
    @XmlSchemaType(name = "string")
    protected NegativeConsentRequestType negativeConsentRequest;

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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetPlannedVaccinationsRequestType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.negativeConsentRequest = this.negativeConsentRequest;
    }

    public<_B >GetPlannedVaccinationsRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetPlannedVaccinationsRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetPlannedVaccinationsRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetPlannedVaccinationsRequestType.Builder<Void> builder() {
        return new GetPlannedVaccinationsRequestType.Builder<>(null, null, false);
    }

    public static<_B >GetPlannedVaccinationsRequestType.Builder<_B> copyOf(final GetPlannedVaccinationsRequestType _other) {
        final GetPlannedVaccinationsRequestType.Builder<_B> _newBuilder = new GetPlannedVaccinationsRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetPlannedVaccinationsRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree negativeConsentRequestPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentRequest"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentRequestPropertyTree!= null):((negativeConsentRequestPropertyTree == null)||(!negativeConsentRequestPropertyTree.isLeaf())))) {
            _other.negativeConsentRequest = this.negativeConsentRequest;
        }
    }

    public<_B >GetPlannedVaccinationsRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetPlannedVaccinationsRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetPlannedVaccinationsRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetPlannedVaccinationsRequestType.Builder<_B> copyOf(final GetPlannedVaccinationsRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetPlannedVaccinationsRequestType.Builder<_B> _newBuilder = new GetPlannedVaccinationsRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetPlannedVaccinationsRequestType.Builder<Void> copyExcept(final GetPlannedVaccinationsRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetPlannedVaccinationsRequestType.Builder<Void> copyOnly(final GetPlannedVaccinationsRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetPlannedVaccinationsRequestType _storedValue;
        private String personCivilRegistrationIdentifier;
        private NegativeConsentRequestType negativeConsentRequest;

        public Builder(final _B _parentBuilder, final GetPlannedVaccinationsRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.negativeConsentRequest = _other.negativeConsentRequest;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetPlannedVaccinationsRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
                        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    }
                    final PropertyTree negativeConsentRequestPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentRequest"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentRequestPropertyTree!= null):((negativeConsentRequestPropertyTree == null)||(!negativeConsentRequestPropertyTree.isLeaf())))) {
                        this.negativeConsentRequest = _other.negativeConsentRequest;
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

        protected<_P extends GetPlannedVaccinationsRequestType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.negativeConsentRequest = this.negativeConsentRequest;
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public GetPlannedVaccinationsRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
            this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "negativeConsentRequest" (any previous value will be
         * replaced)
         * 
         * @param negativeConsentRequest
         *     New value of the "negativeConsentRequest" property.
         */
        public GetPlannedVaccinationsRequestType.Builder<_B> withNegativeConsentRequest(final NegativeConsentRequestType negativeConsentRequest) {
            this.negativeConsentRequest = negativeConsentRequest;
            return this;
        }

        @Override
        public GetPlannedVaccinationsRequestType build() {
            if (_storedValue == null) {
                return this.init(new GetPlannedVaccinationsRequestType());
            } else {
                return ((GetPlannedVaccinationsRequestType) _storedValue);
            }
        }

        public GetPlannedVaccinationsRequestType.Builder<_B> copyOf(final GetPlannedVaccinationsRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetPlannedVaccinationsRequestType.Builder<_B> copyOf(final GetPlannedVaccinationsRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetPlannedVaccinationsRequestType.Selector<GetPlannedVaccinationsRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetPlannedVaccinationsRequestType.Select _root() {
            return new GetPlannedVaccinationsRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, GetPlannedVaccinationsRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetPlannedVaccinationsRequestType.Selector<TRoot, TParent>> negativeConsentRequest = null;

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
            if (this.negativeConsentRequest!= null) {
                products.put("negativeConsentRequest", this.negativeConsentRequest.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetPlannedVaccinationsRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetPlannedVaccinationsRequestType.Selector<TRoot, TParent>> negativeConsentRequest() {
            return ((this.negativeConsentRequest == null)?this.negativeConsentRequest = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentRequest"):this.negativeConsentRequest);
        }

    }

}
