
package org.w3._2000._09.xmldsig_;

import java.math.BigInteger;
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
 * <p>Java class for X509IssuerSerialType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="X509IssuerSerialType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="X509IssuerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="X509SerialNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509IssuerSerialType", propOrder = {
    "x509IssuerName",
    "x509SerialNumber"
})
public class X509IssuerSerialType {

    @XmlElement(name = "X509IssuerName", required = true)
    protected String x509IssuerName;
    @XmlElement(name = "X509SerialNumber", required = true)
    protected BigInteger x509SerialNumber;

    /**
     * Gets the value of the x509IssuerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getX509IssuerName() {
        return x509IssuerName;
    }

    /**
     * Sets the value of the x509IssuerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setX509IssuerName(String value) {
        this.x509IssuerName = value;
    }

    /**
     * Gets the value of the x509SerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getX509SerialNumber() {
        return x509SerialNumber;
    }

    /**
     * Sets the value of the x509SerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setX509SerialNumber(BigInteger value) {
        this.x509SerialNumber = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final X509IssuerSerialType.Builder<_B> _other) {
        _other.x509IssuerName = this.x509IssuerName;
        _other.x509SerialNumber = this.x509SerialNumber;
    }

    public<_B >X509IssuerSerialType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new X509IssuerSerialType.Builder<_B>(_parentBuilder, this, true);
    }

    public X509IssuerSerialType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static X509IssuerSerialType.Builder<Void> builder() {
        return new X509IssuerSerialType.Builder<>(null, null, false);
    }

    public static<_B >X509IssuerSerialType.Builder<_B> copyOf(final X509IssuerSerialType _other) {
        final X509IssuerSerialType.Builder<_B> _newBuilder = new X509IssuerSerialType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final X509IssuerSerialType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree x509IssuerNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509IssuerName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509IssuerNamePropertyTree!= null):((x509IssuerNamePropertyTree == null)||(!x509IssuerNamePropertyTree.isLeaf())))) {
            _other.x509IssuerName = this.x509IssuerName;
        }
        final PropertyTree x509SerialNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509SerialNumber"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509SerialNumberPropertyTree!= null):((x509SerialNumberPropertyTree == null)||(!x509SerialNumberPropertyTree.isLeaf())))) {
            _other.x509SerialNumber = this.x509SerialNumber;
        }
    }

    public<_B >X509IssuerSerialType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new X509IssuerSerialType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public X509IssuerSerialType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >X509IssuerSerialType.Builder<_B> copyOf(final X509IssuerSerialType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final X509IssuerSerialType.Builder<_B> _newBuilder = new X509IssuerSerialType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static X509IssuerSerialType.Builder<Void> copyExcept(final X509IssuerSerialType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static X509IssuerSerialType.Builder<Void> copyOnly(final X509IssuerSerialType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final X509IssuerSerialType _storedValue;
        private String x509IssuerName;
        private BigInteger x509SerialNumber;

        public Builder(final _B _parentBuilder, final X509IssuerSerialType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.x509IssuerName = _other.x509IssuerName;
                    this.x509SerialNumber = _other.x509SerialNumber;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final X509IssuerSerialType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree x509IssuerNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509IssuerName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509IssuerNamePropertyTree!= null):((x509IssuerNamePropertyTree == null)||(!x509IssuerNamePropertyTree.isLeaf())))) {
                        this.x509IssuerName = _other.x509IssuerName;
                    }
                    final PropertyTree x509SerialNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509SerialNumber"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509SerialNumberPropertyTree!= null):((x509SerialNumberPropertyTree == null)||(!x509SerialNumberPropertyTree.isLeaf())))) {
                        this.x509SerialNumber = _other.x509SerialNumber;
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

        protected<_P extends X509IssuerSerialType >_P init(final _P _product) {
            _product.x509IssuerName = this.x509IssuerName;
            _product.x509SerialNumber = this.x509SerialNumber;
            return _product;
        }

        /**
         * Sets the new value of "x509IssuerName" (any previous value will be replaced)
         * 
         * @param x509IssuerName
         *     New value of the "x509IssuerName" property.
         */
        public X509IssuerSerialType.Builder<_B> withX509IssuerName(final String x509IssuerName) {
            this.x509IssuerName = x509IssuerName;
            return this;
        }

        /**
         * Sets the new value of "x509SerialNumber" (any previous value will be replaced)
         * 
         * @param x509SerialNumber
         *     New value of the "x509SerialNumber" property.
         */
        public X509IssuerSerialType.Builder<_B> withX509SerialNumber(final BigInteger x509SerialNumber) {
            this.x509SerialNumber = x509SerialNumber;
            return this;
        }

        @Override
        public X509IssuerSerialType build() {
            if (_storedValue == null) {
                return this.init(new X509IssuerSerialType());
            } else {
                return ((X509IssuerSerialType) _storedValue);
            }
        }

        public X509IssuerSerialType.Builder<_B> copyOf(final X509IssuerSerialType _other) {
            _other.copyTo(this);
            return this;
        }

        public X509IssuerSerialType.Builder<_B> copyOf(final X509IssuerSerialType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends X509IssuerSerialType.Selector<X509IssuerSerialType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static X509IssuerSerialType.Select _root() {
            return new X509IssuerSerialType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, X509IssuerSerialType.Selector<TRoot, TParent>> x509IssuerName = null;
        private com.kscs.util.jaxb.Selector<TRoot, X509IssuerSerialType.Selector<TRoot, TParent>> x509SerialNumber = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.x509IssuerName!= null) {
                products.put("x509IssuerName", this.x509IssuerName.init());
            }
            if (this.x509SerialNumber!= null) {
                products.put("x509SerialNumber", this.x509SerialNumber.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, X509IssuerSerialType.Selector<TRoot, TParent>> x509IssuerName() {
            return ((this.x509IssuerName == null)?this.x509IssuerName = new com.kscs.util.jaxb.Selector<>(this._root, this, "x509IssuerName"):this.x509IssuerName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, X509IssuerSerialType.Selector<TRoot, TParent>> x509SerialNumber() {
            return ((this.x509SerialNumber == null)?this.x509SerialNumber = new com.kscs.util.jaxb.Selector<>(this._root, this, "x509SerialNumber"):this.x509SerialNumber);
        }

    }

}
