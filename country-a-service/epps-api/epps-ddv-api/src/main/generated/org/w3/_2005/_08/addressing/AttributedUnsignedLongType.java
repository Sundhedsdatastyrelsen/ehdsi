
package org.w3._2005._08.addressing;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for AttributedUnsignedLongType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="AttributedUnsignedLongType">
 *   <simpleContent>
 *     <extension base="<http://www.w3.org/2001/XMLSchema>unsignedLong">
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributedUnsignedLongType", propOrder = {
    "value"
})
public class AttributedUnsignedLongType {

    @XmlValue
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger value;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setValue(BigInteger value) {
        this.value = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final AttributedUnsignedLongType.Builder<_B> _other) {
        _other.value = this.value;
    }

    public<_B >AttributedUnsignedLongType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new AttributedUnsignedLongType.Builder<_B>(_parentBuilder, this, true);
    }

    public AttributedUnsignedLongType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static AttributedUnsignedLongType.Builder<Void> builder() {
        return new AttributedUnsignedLongType.Builder<>(null, null, false);
    }

    public static<_B >AttributedUnsignedLongType.Builder<_B> copyOf(final AttributedUnsignedLongType _other) {
        final AttributedUnsignedLongType.Builder<_B> _newBuilder = new AttributedUnsignedLongType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final AttributedUnsignedLongType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
            _other.value = this.value;
        }
    }

    public<_B >AttributedUnsignedLongType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new AttributedUnsignedLongType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public AttributedUnsignedLongType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >AttributedUnsignedLongType.Builder<_B> copyOf(final AttributedUnsignedLongType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final AttributedUnsignedLongType.Builder<_B> _newBuilder = new AttributedUnsignedLongType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static AttributedUnsignedLongType.Builder<Void> copyExcept(final AttributedUnsignedLongType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static AttributedUnsignedLongType.Builder<Void> copyOnly(final AttributedUnsignedLongType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final AttributedUnsignedLongType _storedValue;
        private BigInteger value;

        public Builder(final _B _parentBuilder, final AttributedUnsignedLongType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.value = _other.value;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final AttributedUnsignedLongType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
                        this.value = _other.value;
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

        protected<_P extends AttributedUnsignedLongType >_P init(final _P _product) {
            _product.value = this.value;
            return _product;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        public AttributedUnsignedLongType.Builder<_B> withValue(final BigInteger value) {
            this.value = value;
            return this;
        }

        @Override
        public AttributedUnsignedLongType build() {
            if (_storedValue == null) {
                return this.init(new AttributedUnsignedLongType());
            } else {
                return ((AttributedUnsignedLongType) _storedValue);
            }
        }

        public AttributedUnsignedLongType.Builder<_B> copyOf(final AttributedUnsignedLongType _other) {
            _other.copyTo(this);
            return this;
        }

        public AttributedUnsignedLongType.Builder<_B> copyOf(final AttributedUnsignedLongType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends AttributedUnsignedLongType.Selector<AttributedUnsignedLongType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static AttributedUnsignedLongType.Select _root() {
            return new AttributedUnsignedLongType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, AttributedUnsignedLongType.Selector<TRoot, TParent>> value = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.value!= null) {
                products.put("value", this.value.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, AttributedUnsignedLongType.Selector<TRoot, TParent>> value() {
            return ((this.value == null)?this.value = new com.kscs.util.jaxb.Selector<>(this._root, this, "value"):this.value);
        }

    }

}
