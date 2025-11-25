
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * A security token that is encoded in binary
 * 
 * <p>Java class for BinarySecurityTokenType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="BinarySecurityTokenType">
 *   <simpleContent>
 *     <extension base="<http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd>EncodedString">
 *       <attribute name="ValueType" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinarySecurityTokenType")
public class BinarySecurityTokenType
    extends EncodedString
{

    @XmlAttribute(name = "ValueType")
    @XmlSchemaType(name = "anyURI")
    protected String valueType;

    /**
     * Gets the value of the valueType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * Sets the value of the valueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueType(String value) {
        this.valueType = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final BinarySecurityTokenType.Builder<_B> _other) {
        super.copyTo(_other);
        _other.valueType = this.valueType;
    }

    @Override
    public<_B >BinarySecurityTokenType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new BinarySecurityTokenType.Builder<_B>(_parentBuilder, this, true);
    }

    @Override
    public BinarySecurityTokenType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static BinarySecurityTokenType.Builder<Void> builder() {
        return new BinarySecurityTokenType.Builder<>(null, null, false);
    }

    public static<_B >BinarySecurityTokenType.Builder<_B> copyOf(final AttributedString _other) {
        final BinarySecurityTokenType.Builder<_B> _newBuilder = new BinarySecurityTokenType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder);
        return _newBuilder;
    }

    public static<_B >BinarySecurityTokenType.Builder<_B> copyOf(final EncodedString _other) {
        final BinarySecurityTokenType.Builder<_B> _newBuilder = new BinarySecurityTokenType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder);
        return _newBuilder;
    }

    public static<_B >BinarySecurityTokenType.Builder<_B> copyOf(final BinarySecurityTokenType _other) {
        final BinarySecurityTokenType.Builder<_B> _newBuilder = new BinarySecurityTokenType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final BinarySecurityTokenType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        super.copyTo(_other, _propertyTree, _propertyTreeUse);
        final PropertyTree valueTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("valueType"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valueTypePropertyTree!= null):((valueTypePropertyTree == null)||(!valueTypePropertyTree.isLeaf())))) {
            _other.valueType = this.valueType;
        }
    }

    @Override
    public<_B >BinarySecurityTokenType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new BinarySecurityTokenType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    @Override
    public BinarySecurityTokenType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >BinarySecurityTokenType.Builder<_B> copyOf(final AttributedString _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final BinarySecurityTokenType.Builder<_B> _newBuilder = new BinarySecurityTokenType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static<_B >BinarySecurityTokenType.Builder<_B> copyOf(final EncodedString _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final BinarySecurityTokenType.Builder<_B> _newBuilder = new BinarySecurityTokenType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static<_B >BinarySecurityTokenType.Builder<_B> copyOf(final BinarySecurityTokenType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final BinarySecurityTokenType.Builder<_B> _newBuilder = new BinarySecurityTokenType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static BinarySecurityTokenType.Builder<Void> copyExcept(final AttributedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static BinarySecurityTokenType.Builder<Void> copyExcept(final EncodedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static BinarySecurityTokenType.Builder<Void> copyExcept(final BinarySecurityTokenType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static BinarySecurityTokenType.Builder<Void> copyOnly(final AttributedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static BinarySecurityTokenType.Builder<Void> copyOnly(final EncodedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static BinarySecurityTokenType.Builder<Void> copyOnly(final BinarySecurityTokenType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >
        extends EncodedString.Builder<_B>
        implements Buildable
    {

        private String valueType;

        public Builder(final _B _parentBuilder, final BinarySecurityTokenType _other, final boolean _copy) {
            super(_parentBuilder, _other, _copy);
            if (_other!= null) {
                this.valueType = _other.valueType;
            }
        }

        public Builder(final _B _parentBuilder, final BinarySecurityTokenType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            super(_parentBuilder, _other, _copy, _propertyTree, _propertyTreeUse);
            if (_other!= null) {
                final PropertyTree valueTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("valueType"));
                if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valueTypePropertyTree!= null):((valueTypePropertyTree == null)||(!valueTypePropertyTree.isLeaf())))) {
                    this.valueType = _other.valueType;
                }
            }
        }

        protected<_P extends BinarySecurityTokenType >_P init(final _P _product) {
            _product.valueType = this.valueType;
            return super.init(_product);
        }

        /**
         * Sets the new value of "valueType" (any previous value will be replaced)
         * 
         * @param valueType
         *     New value of the "valueType" property.
         */
        public BinarySecurityTokenType.Builder<_B> withValueType(final String valueType) {
            this.valueType = valueType;
            return this;
        }

        /**
         * Sets the new value of "encodingType" (any previous value will be replaced)
         * 
         * @param encodingType
         *     New value of the "encodingType" property.
         */
        @Override
        public BinarySecurityTokenType.Builder<_B> withEncodingType(final String encodingType) {
            super.withEncodingType(encodingType);
            return this;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        @Override
        public BinarySecurityTokenType.Builder<_B> withValue(final String value) {
            super.withValue(value);
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        @Override
        public BinarySecurityTokenType.Builder<_B> withId(final String id) {
            super.withId(id);
            return this;
        }

        @Override
        public BinarySecurityTokenType build() {
            if (_storedValue == null) {
                return this.init(new BinarySecurityTokenType());
            } else {
                return ((BinarySecurityTokenType) _storedValue);
            }
        }

        public BinarySecurityTokenType.Builder<_B> copyOf(final BinarySecurityTokenType _other) {
            _other.copyTo(this);
            return this;
        }

        public BinarySecurityTokenType.Builder<_B> copyOf(final BinarySecurityTokenType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends BinarySecurityTokenType.Selector<BinarySecurityTokenType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static BinarySecurityTokenType.Select _root() {
            return new BinarySecurityTokenType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends EncodedString.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, BinarySecurityTokenType.Selector<TRoot, TParent>> valueType = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.valueType!= null) {
                products.put("valueType", this.valueType.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, BinarySecurityTokenType.Selector<TRoot, TParent>> valueType() {
            return ((this.valueType == null)?this.valueType = new com.kscs.util.jaxb.Selector<>(this._root, this, "valueType"):this.valueType);
        }

    }

}
