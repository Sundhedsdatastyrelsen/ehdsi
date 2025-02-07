
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
 * <p>Java class for GetVaccinationHistoryRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetVaccinationHistoryRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="VaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationIdentifierType"/>
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
@XmlType(name = "GetVaccinationHistoryRequestType", propOrder = {
    "vaccinationIdentifier",
    "negativeConsentRequest"
})
public class GetVaccinationHistoryRequestType {

    @XmlElement(name = "VaccinationIdentifier")
    protected long vaccinationIdentifier;
    @XmlElement(name = "NegativeConsentRequest")
    @XmlSchemaType(name = "string")
    protected NegativeConsentRequestType negativeConsentRequest;

    /**
     * Gets the value of the vaccinationIdentifier property.
     * 
     */
    public long getVaccinationIdentifier() {
        return vaccinationIdentifier;
    }

    /**
     * Sets the value of the vaccinationIdentifier property.
     * 
     */
    public void setVaccinationIdentifier(long value) {
        this.vaccinationIdentifier = value;
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
    public<_B >void copyTo(final GetVaccinationHistoryRequestType.Builder<_B> _other) {
        _other.vaccinationIdentifier = this.vaccinationIdentifier;
        _other.negativeConsentRequest = this.negativeConsentRequest;
    }

    public<_B >GetVaccinationHistoryRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetVaccinationHistoryRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetVaccinationHistoryRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetVaccinationHistoryRequestType.Builder<Void> builder() {
        return new GetVaccinationHistoryRequestType.Builder<>(null, null, false);
    }

    public static<_B >GetVaccinationHistoryRequestType.Builder<_B> copyOf(final GetVaccinationHistoryRequestType _other) {
        final GetVaccinationHistoryRequestType.Builder<_B> _newBuilder = new GetVaccinationHistoryRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetVaccinationHistoryRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
            _other.vaccinationIdentifier = this.vaccinationIdentifier;
        }
        final PropertyTree negativeConsentRequestPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentRequest"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentRequestPropertyTree!= null):((negativeConsentRequestPropertyTree == null)||(!negativeConsentRequestPropertyTree.isLeaf())))) {
            _other.negativeConsentRequest = this.negativeConsentRequest;
        }
    }

    public<_B >GetVaccinationHistoryRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetVaccinationHistoryRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetVaccinationHistoryRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetVaccinationHistoryRequestType.Builder<_B> copyOf(final GetVaccinationHistoryRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetVaccinationHistoryRequestType.Builder<_B> _newBuilder = new GetVaccinationHistoryRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetVaccinationHistoryRequestType.Builder<Void> copyExcept(final GetVaccinationHistoryRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetVaccinationHistoryRequestType.Builder<Void> copyOnly(final GetVaccinationHistoryRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetVaccinationHistoryRequestType _storedValue;
        private long vaccinationIdentifier;
        private NegativeConsentRequestType negativeConsentRequest;

        public Builder(final _B _parentBuilder, final GetVaccinationHistoryRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.vaccinationIdentifier = _other.vaccinationIdentifier;
                    this.negativeConsentRequest = _other.negativeConsentRequest;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetVaccinationHistoryRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
                        this.vaccinationIdentifier = _other.vaccinationIdentifier;
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

        protected<_P extends GetVaccinationHistoryRequestType >_P init(final _P _product) {
            _product.vaccinationIdentifier = this.vaccinationIdentifier;
            _product.negativeConsentRequest = this.negativeConsentRequest;
            return _product;
        }

        /**
         * Sets the new value of "vaccinationIdentifier" (any previous value will be
         * replaced)
         * 
         * @param vaccinationIdentifier
         *     New value of the "vaccinationIdentifier" property.
         */
        public GetVaccinationHistoryRequestType.Builder<_B> withVaccinationIdentifier(final long vaccinationIdentifier) {
            this.vaccinationIdentifier = vaccinationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "negativeConsentRequest" (any previous value will be
         * replaced)
         * 
         * @param negativeConsentRequest
         *     New value of the "negativeConsentRequest" property.
         */
        public GetVaccinationHistoryRequestType.Builder<_B> withNegativeConsentRequest(final NegativeConsentRequestType negativeConsentRequest) {
            this.negativeConsentRequest = negativeConsentRequest;
            return this;
        }

        @Override
        public GetVaccinationHistoryRequestType build() {
            if (_storedValue == null) {
                return this.init(new GetVaccinationHistoryRequestType());
            } else {
                return ((GetVaccinationHistoryRequestType) _storedValue);
            }
        }

        public GetVaccinationHistoryRequestType.Builder<_B> copyOf(final GetVaccinationHistoryRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetVaccinationHistoryRequestType.Builder<_B> copyOf(final GetVaccinationHistoryRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetVaccinationHistoryRequestType.Selector<GetVaccinationHistoryRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetVaccinationHistoryRequestType.Select _root() {
            return new GetVaccinationHistoryRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationHistoryRequestType.Selector<TRoot, TParent>> negativeConsentRequest = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.negativeConsentRequest!= null) {
                products.put("negativeConsentRequest", this.negativeConsentRequest.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationHistoryRequestType.Selector<TRoot, TParent>> negativeConsentRequest() {
            return ((this.negativeConsentRequest == null)?this.negativeConsentRequest = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentRequest"):this.negativeConsentRequest);
        }

    }

}
