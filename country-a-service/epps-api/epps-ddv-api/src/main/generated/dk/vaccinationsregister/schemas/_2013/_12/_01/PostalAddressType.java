
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
 * <p>Java class for PostalAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PostalAddressType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Name" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NameType"/>
 *         <element name="AddressLine1" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AddressLineType"/>
 *         <element name="AddressLine2" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AddressLineType" minOccurs="0"/>
 *         <element name="City" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CityType"/>
 *         <element name="PostalCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PostalCodeType"/>
 *         <element name="CountryCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CountryCodeType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddressType", propOrder = {
    "name",
    "addressLine1",
    "addressLine2",
    "city",
    "postalCode",
    "countryCode"
})
public class PostalAddressType {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "AddressLine1", required = true)
    protected String addressLine1;
    @XmlElement(name = "AddressLine2")
    protected String addressLine2;
    @XmlElement(name = "City", required = true)
    protected String city;
    @XmlElement(name = "PostalCode", required = true)
    protected String postalCode;
    @XmlElement(name = "CountryCode", required = true)
    protected String countryCode;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the addressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final PostalAddressType.Builder<_B> _other) {
        _other.name = this.name;
        _other.addressLine1 = this.addressLine1;
        _other.addressLine2 = this.addressLine2;
        _other.city = this.city;
        _other.postalCode = this.postalCode;
        _other.countryCode = this.countryCode;
    }

    public<_B >PostalAddressType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new PostalAddressType.Builder<_B>(_parentBuilder, this, true);
    }

    public PostalAddressType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static PostalAddressType.Builder<Void> builder() {
        return new PostalAddressType.Builder<>(null, null, false);
    }

    public static<_B >PostalAddressType.Builder<_B> copyOf(final PostalAddressType _other) {
        final PostalAddressType.Builder<_B> _newBuilder = new PostalAddressType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final PostalAddressType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
            _other.name = this.name;
        }
        final PropertyTree addressLine1PropertyTree = ((_propertyTree == null)?null:_propertyTree.get("addressLine1"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(addressLine1PropertyTree!= null):((addressLine1PropertyTree == null)||(!addressLine1PropertyTree.isLeaf())))) {
            _other.addressLine1 = this.addressLine1;
        }
        final PropertyTree addressLine2PropertyTree = ((_propertyTree == null)?null:_propertyTree.get("addressLine2"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(addressLine2PropertyTree!= null):((addressLine2PropertyTree == null)||(!addressLine2PropertyTree.isLeaf())))) {
            _other.addressLine2 = this.addressLine2;
        }
        final PropertyTree cityPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("city"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(cityPropertyTree!= null):((cityPropertyTree == null)||(!cityPropertyTree.isLeaf())))) {
            _other.city = this.city;
        }
        final PropertyTree postalCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("postalCode"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(postalCodePropertyTree!= null):((postalCodePropertyTree == null)||(!postalCodePropertyTree.isLeaf())))) {
            _other.postalCode = this.postalCode;
        }
        final PropertyTree countryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("countryCode"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(countryCodePropertyTree!= null):((countryCodePropertyTree == null)||(!countryCodePropertyTree.isLeaf())))) {
            _other.countryCode = this.countryCode;
        }
    }

    public<_B >PostalAddressType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new PostalAddressType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public PostalAddressType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >PostalAddressType.Builder<_B> copyOf(final PostalAddressType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PostalAddressType.Builder<_B> _newBuilder = new PostalAddressType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static PostalAddressType.Builder<Void> copyExcept(final PostalAddressType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static PostalAddressType.Builder<Void> copyOnly(final PostalAddressType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final PostalAddressType _storedValue;
        private String name;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String postalCode;
        private String countryCode;

        public Builder(final _B _parentBuilder, final PostalAddressType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.name = _other.name;
                    this.addressLine1 = _other.addressLine1;
                    this.addressLine2 = _other.addressLine2;
                    this.city = _other.city;
                    this.postalCode = _other.postalCode;
                    this.countryCode = _other.countryCode;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final PostalAddressType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
                        this.name = _other.name;
                    }
                    final PropertyTree addressLine1PropertyTree = ((_propertyTree == null)?null:_propertyTree.get("addressLine1"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(addressLine1PropertyTree!= null):((addressLine1PropertyTree == null)||(!addressLine1PropertyTree.isLeaf())))) {
                        this.addressLine1 = _other.addressLine1;
                    }
                    final PropertyTree addressLine2PropertyTree = ((_propertyTree == null)?null:_propertyTree.get("addressLine2"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(addressLine2PropertyTree!= null):((addressLine2PropertyTree == null)||(!addressLine2PropertyTree.isLeaf())))) {
                        this.addressLine2 = _other.addressLine2;
                    }
                    final PropertyTree cityPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("city"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(cityPropertyTree!= null):((cityPropertyTree == null)||(!cityPropertyTree.isLeaf())))) {
                        this.city = _other.city;
                    }
                    final PropertyTree postalCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("postalCode"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(postalCodePropertyTree!= null):((postalCodePropertyTree == null)||(!postalCodePropertyTree.isLeaf())))) {
                        this.postalCode = _other.postalCode;
                    }
                    final PropertyTree countryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("countryCode"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(countryCodePropertyTree!= null):((countryCodePropertyTree == null)||(!countryCodePropertyTree.isLeaf())))) {
                        this.countryCode = _other.countryCode;
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

        protected<_P extends PostalAddressType >_P init(final _P _product) {
            _product.name = this.name;
            _product.addressLine1 = this.addressLine1;
            _product.addressLine2 = this.addressLine2;
            _product.city = this.city;
            _product.postalCode = this.postalCode;
            _product.countryCode = this.countryCode;
            return _product;
        }

        /**
         * Sets the new value of "name" (any previous value will be replaced)
         * 
         * @param name
         *     New value of the "name" property.
         */
        public PostalAddressType.Builder<_B> withName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the new value of "addressLine1" (any previous value will be replaced)
         * 
         * @param addressLine1
         *     New value of the "addressLine1" property.
         */
        public PostalAddressType.Builder<_B> withAddressLine1(final String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * Sets the new value of "addressLine2" (any previous value will be replaced)
         * 
         * @param addressLine2
         *     New value of the "addressLine2" property.
         */
        public PostalAddressType.Builder<_B> withAddressLine2(final String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * Sets the new value of "city" (any previous value will be replaced)
         * 
         * @param city
         *     New value of the "city" property.
         */
        public PostalAddressType.Builder<_B> withCity(final String city) {
            this.city = city;
            return this;
        }

        /**
         * Sets the new value of "postalCode" (any previous value will be replaced)
         * 
         * @param postalCode
         *     New value of the "postalCode" property.
         */
        public PostalAddressType.Builder<_B> withPostalCode(final String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * Sets the new value of "countryCode" (any previous value will be replaced)
         * 
         * @param countryCode
         *     New value of the "countryCode" property.
         */
        public PostalAddressType.Builder<_B> withCountryCode(final String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        @Override
        public PostalAddressType build() {
            if (_storedValue == null) {
                return this.init(new PostalAddressType());
            } else {
                return ((PostalAddressType) _storedValue);
            }
        }

        public PostalAddressType.Builder<_B> copyOf(final PostalAddressType _other) {
            _other.copyTo(this);
            return this;
        }

        public PostalAddressType.Builder<_B> copyOf(final PostalAddressType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends PostalAddressType.Selector<PostalAddressType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static PostalAddressType.Select _root() {
            return new PostalAddressType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> name = null;
        private com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> addressLine1 = null;
        private com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> addressLine2 = null;
        private com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> city = null;
        private com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> postalCode = null;
        private com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> countryCode = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.name!= null) {
                products.put("name", this.name.init());
            }
            if (this.addressLine1 != null) {
                products.put("addressLine1", this.addressLine1 .init());
            }
            if (this.addressLine2 != null) {
                products.put("addressLine2", this.addressLine2 .init());
            }
            if (this.city!= null) {
                products.put("city", this.city.init());
            }
            if (this.postalCode!= null) {
                products.put("postalCode", this.postalCode.init());
            }
            if (this.countryCode!= null) {
                products.put("countryCode", this.countryCode.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> name() {
            return ((this.name == null)?this.name = new com.kscs.util.jaxb.Selector<>(this._root, this, "name"):this.name);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> addressLine1() {
            return ((this.addressLine1 == null)?this.addressLine1 = new com.kscs.util.jaxb.Selector<>(this._root, this, "addressLine1"):this.addressLine1);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> addressLine2() {
            return ((this.addressLine2 == null)?this.addressLine2 = new com.kscs.util.jaxb.Selector<>(this._root, this, "addressLine2"):this.addressLine2);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> city() {
            return ((this.city == null)?this.city = new com.kscs.util.jaxb.Selector<>(this._root, this, "city"):this.city);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> postalCode() {
            return ((this.postalCode == null)?this.postalCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "postalCode"):this.postalCode);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PostalAddressType.Selector<TRoot, TParent>> countryCode() {
            return ((this.countryCode == null)?this.countryCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "countryCode"):this.countryCode);
        }

    }

}
