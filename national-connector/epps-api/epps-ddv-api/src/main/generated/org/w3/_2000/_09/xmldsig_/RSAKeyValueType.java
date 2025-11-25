
package org.w3._2000._09.xmldsig_;

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
 * <p>Java class for RSAKeyValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RSAKeyValueType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Modulus" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *         <element name="Exponent" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RSAKeyValueType", propOrder = {
    "modulus",
    "exponent"
})
public class RSAKeyValueType {

    @XmlElement(name = "Modulus", required = true)
    protected byte[] modulus;
    @XmlElement(name = "Exponent", required = true)
    protected byte[] exponent;

    /**
     * Gets the value of the modulus property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getModulus() {
        return modulus;
    }

    /**
     * Sets the value of the modulus property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setModulus(byte[] value) {
        this.modulus = value;
    }

    /**
     * Gets the value of the exponent property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getExponent() {
        return exponent;
    }

    /**
     * Sets the value of the exponent property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setExponent(byte[] value) {
        this.exponent = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final RSAKeyValueType.Builder<_B> _other) {
        _other.modulus = this.modulus;
        _other.exponent = this.exponent;
    }

    public<_B >RSAKeyValueType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new RSAKeyValueType.Builder<_B>(_parentBuilder, this, true);
    }

    public RSAKeyValueType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static RSAKeyValueType.Builder<Void> builder() {
        return new RSAKeyValueType.Builder<>(null, null, false);
    }

    public static<_B >RSAKeyValueType.Builder<_B> copyOf(final RSAKeyValueType _other) {
        final RSAKeyValueType.Builder<_B> _newBuilder = new RSAKeyValueType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final RSAKeyValueType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree modulusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modulus"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modulusPropertyTree!= null):((modulusPropertyTree == null)||(!modulusPropertyTree.isLeaf())))) {
            _other.modulus = this.modulus;
        }
        final PropertyTree exponentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("exponent"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(exponentPropertyTree!= null):((exponentPropertyTree == null)||(!exponentPropertyTree.isLeaf())))) {
            _other.exponent = this.exponent;
        }
    }

    public<_B >RSAKeyValueType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new RSAKeyValueType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public RSAKeyValueType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >RSAKeyValueType.Builder<_B> copyOf(final RSAKeyValueType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final RSAKeyValueType.Builder<_B> _newBuilder = new RSAKeyValueType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static RSAKeyValueType.Builder<Void> copyExcept(final RSAKeyValueType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static RSAKeyValueType.Builder<Void> copyOnly(final RSAKeyValueType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final RSAKeyValueType _storedValue;
        private byte[] modulus;
        private byte[] exponent;

        public Builder(final _B _parentBuilder, final RSAKeyValueType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.modulus = _other.modulus;
                    this.exponent = _other.exponent;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final RSAKeyValueType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree modulusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modulus"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modulusPropertyTree!= null):((modulusPropertyTree == null)||(!modulusPropertyTree.isLeaf())))) {
                        this.modulus = _other.modulus;
                    }
                    final PropertyTree exponentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("exponent"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(exponentPropertyTree!= null):((exponentPropertyTree == null)||(!exponentPropertyTree.isLeaf())))) {
                        this.exponent = _other.exponent;
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

        protected<_P extends RSAKeyValueType >_P init(final _P _product) {
            _product.modulus = this.modulus;
            _product.exponent = this.exponent;
            return _product;
        }

        /**
         * Sets the new value of "modulus" (any previous value will be replaced)
         * 
         * @param modulus
         *     New value of the "modulus" property.
         */
        public RSAKeyValueType.Builder<_B> withModulus(final byte[] modulus) {
            this.modulus = modulus;
            return this;
        }

        /**
         * Sets the new value of "exponent" (any previous value will be replaced)
         * 
         * @param exponent
         *     New value of the "exponent" property.
         */
        public RSAKeyValueType.Builder<_B> withExponent(final byte[] exponent) {
            this.exponent = exponent;
            return this;
        }

        @Override
        public RSAKeyValueType build() {
            if (_storedValue == null) {
                return this.init(new RSAKeyValueType());
            } else {
                return ((RSAKeyValueType) _storedValue);
            }
        }

        public RSAKeyValueType.Builder<_B> copyOf(final RSAKeyValueType _other) {
            _other.copyTo(this);
            return this;
        }

        public RSAKeyValueType.Builder<_B> copyOf(final RSAKeyValueType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends RSAKeyValueType.Selector<RSAKeyValueType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static RSAKeyValueType.Select _root() {
            return new RSAKeyValueType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, RSAKeyValueType.Selector<TRoot, TParent>> modulus = null;
        private com.kscs.util.jaxb.Selector<TRoot, RSAKeyValueType.Selector<TRoot, TParent>> exponent = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.modulus!= null) {
                products.put("modulus", this.modulus.init());
            }
            if (this.exponent!= null) {
                products.put("exponent", this.exponent.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, RSAKeyValueType.Selector<TRoot, TParent>> modulus() {
            return ((this.modulus == null)?this.modulus = new com.kscs.util.jaxb.Selector<>(this._root, this, "modulus"):this.modulus);
        }

        public com.kscs.util.jaxb.Selector<TRoot, RSAKeyValueType.Selector<TRoot, TParent>> exponent() {
            return ((this.exponent == null)?this.exponent = new com.kscs.util.jaxb.Selector<>(this._root, this, "exponent"):this.exponent);
        }

    }

}
