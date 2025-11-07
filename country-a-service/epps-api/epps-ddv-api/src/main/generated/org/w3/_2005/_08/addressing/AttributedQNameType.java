
package org.w3._2005._08.addressing;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for AttributedQNameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="AttributedQNameType">
 *   <simpleContent>
 *     <extension base="<http://www.w3.org/2001/XMLSchema>QName">
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributedQNameType", propOrder = {
    "value"
})
public class AttributedQNameType {

    @XmlValue
    protected QName value;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setValue(QName value) {
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
    public<_B >void copyTo(final AttributedQNameType.Builder<_B> _other) {
        _other.value = this.value;
    }

    public<_B >AttributedQNameType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new AttributedQNameType.Builder<_B>(_parentBuilder, this, true);
    }

    public AttributedQNameType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static AttributedQNameType.Builder<Void> builder() {
        return new AttributedQNameType.Builder<>(null, null, false);
    }

    public static<_B >AttributedQNameType.Builder<_B> copyOf(final AttributedQNameType _other) {
        final AttributedQNameType.Builder<_B> _newBuilder = new AttributedQNameType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final AttributedQNameType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
            _other.value = this.value;
        }
    }

    public<_B >AttributedQNameType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new AttributedQNameType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public AttributedQNameType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >AttributedQNameType.Builder<_B> copyOf(final AttributedQNameType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final AttributedQNameType.Builder<_B> _newBuilder = new AttributedQNameType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static AttributedQNameType.Builder<Void> copyExcept(final AttributedQNameType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static AttributedQNameType.Builder<Void> copyOnly(final AttributedQNameType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final AttributedQNameType _storedValue;
        private QName value;

        public Builder(final _B _parentBuilder, final AttributedQNameType _other, final boolean _copy) {
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

        public Builder(final _B _parentBuilder, final AttributedQNameType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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

        protected<_P extends AttributedQNameType >_P init(final _P _product) {
            _product.value = this.value;
            return _product;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        public AttributedQNameType.Builder<_B> withValue(final QName value) {
            this.value = value;
            return this;
        }

        @Override
        public AttributedQNameType build() {
            if (_storedValue == null) {
                return this.init(new AttributedQNameType());
            } else {
                return ((AttributedQNameType) _storedValue);
            }
        }

        public AttributedQNameType.Builder<_B> copyOf(final AttributedQNameType _other) {
            _other.copyTo(this);
            return this;
        }

        public AttributedQNameType.Builder<_B> copyOf(final AttributedQNameType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends AttributedQNameType.Selector<AttributedQNameType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static AttributedQNameType.Select _root() {
            return new AttributedQNameType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, AttributedQNameType.Selector<TRoot, TParent>> value = null;

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

        public com.kscs.util.jaxb.Selector<TRoot, AttributedQNameType.Selector<TRoot, TParent>> value() {
            return ((this.value == null)?this.value = new com.kscs.util.jaxb.Selector<>(this._root, this, "value"):this.value);
        }

    }

}
