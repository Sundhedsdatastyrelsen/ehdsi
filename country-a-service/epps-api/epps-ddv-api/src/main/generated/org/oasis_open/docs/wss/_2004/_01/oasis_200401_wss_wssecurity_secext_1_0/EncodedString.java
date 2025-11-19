
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
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * This type is used for elements containing stringified binary data.
 * 
 * <p>Java class for EncodedString complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EncodedString">
 *   <simpleContent>
 *     <extension base="<http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd>AttributedString">
 *       <attribute name="EncodingType" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncodedString")
@XmlSeeAlso({
    BinarySecurityTokenType.class,
    KeyIdentifierType.class
})
public class EncodedString
    extends AttributedString
{

    @XmlAttribute(name = "EncodingType")
    @XmlSchemaType(name = "anyURI")
    protected String encodingType;

    /**
     * Gets the value of the encodingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncodingType() {
        return encodingType;
    }

    /**
     * Sets the value of the encodingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncodingType(String value) {
        this.encodingType = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final EncodedString.Builder<_B> _other) {
        super.copyTo(_other);
        _other.encodingType = this.encodingType;
    }

    @Override
    public<_B >EncodedString.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new EncodedString.Builder<_B>(_parentBuilder, this, true);
    }

    @Override
    public EncodedString.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static EncodedString.Builder<Void> builder() {
        return new EncodedString.Builder<>(null, null, false);
    }

    public static<_B >EncodedString.Builder<_B> copyOf(final AttributedString _other) {
        final EncodedString.Builder<_B> _newBuilder = new EncodedString.Builder<>(null, null, false);
        _other.copyTo(_newBuilder);
        return _newBuilder;
    }

    public static<_B >EncodedString.Builder<_B> copyOf(final EncodedString _other) {
        final EncodedString.Builder<_B> _newBuilder = new EncodedString.Builder<>(null, null, false);
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
    public<_B >void copyTo(final EncodedString.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        super.copyTo(_other, _propertyTree, _propertyTreeUse);
        final PropertyTree encodingTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("encodingType"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(encodingTypePropertyTree!= null):((encodingTypePropertyTree == null)||(!encodingTypePropertyTree.isLeaf())))) {
            _other.encodingType = this.encodingType;
        }
    }

    @Override
    public<_B >EncodedString.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new EncodedString.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    @Override
    public EncodedString.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >EncodedString.Builder<_B> copyOf(final AttributedString _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final EncodedString.Builder<_B> _newBuilder = new EncodedString.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static<_B >EncodedString.Builder<_B> copyOf(final EncodedString _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final EncodedString.Builder<_B> _newBuilder = new EncodedString.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static EncodedString.Builder<Void> copyExcept(final AttributedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static EncodedString.Builder<Void> copyExcept(final EncodedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static EncodedString.Builder<Void> copyOnly(final AttributedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static EncodedString.Builder<Void> copyOnly(final EncodedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >
        extends AttributedString.Builder<_B>
        implements Buildable
    {

        private String encodingType;

        public Builder(final _B _parentBuilder, final EncodedString _other, final boolean _copy) {
            super(_parentBuilder, _other, _copy);
            if (_other!= null) {
                this.encodingType = _other.encodingType;
            }
        }

        public Builder(final _B _parentBuilder, final EncodedString _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            super(_parentBuilder, _other, _copy, _propertyTree, _propertyTreeUse);
            if (_other!= null) {
                final PropertyTree encodingTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("encodingType"));
                if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(encodingTypePropertyTree!= null):((encodingTypePropertyTree == null)||(!encodingTypePropertyTree.isLeaf())))) {
                    this.encodingType = _other.encodingType;
                }
            }
        }

        protected<_P extends EncodedString >_P init(final _P _product) {
            _product.encodingType = this.encodingType;
            return super.init(_product);
        }

        /**
         * Sets the new value of "encodingType" (any previous value will be replaced)
         * 
         * @param encodingType
         *     New value of the "encodingType" property.
         */
        public EncodedString.Builder<_B> withEncodingType(final String encodingType) {
            this.encodingType = encodingType;
            return this;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        @Override
        public EncodedString.Builder<_B> withValue(final String value) {
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
        public EncodedString.Builder<_B> withId(final String id) {
            super.withId(id);
            return this;
        }

        @Override
        public EncodedString build() {
            if (_storedValue == null) {
                return this.init(new EncodedString());
            } else {
                return ((EncodedString) _storedValue);
            }
        }

        public EncodedString.Builder<_B> copyOf(final EncodedString _other) {
            _other.copyTo(this);
            return this;
        }

        public EncodedString.Builder<_B> copyOf(final EncodedString.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends EncodedString.Selector<EncodedString.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static EncodedString.Select _root() {
            return new EncodedString.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends AttributedString.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, EncodedString.Selector<TRoot, TParent>> encodingType = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.encodingType!= null) {
                products.put("encodingType", this.encodingType.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, EncodedString.Selector<TRoot, TParent>> encodingType() {
            return ((this.encodingType == null)?this.encodingType = new com.kscs.util.jaxb.Selector<>(this._root, this, "encodingType"):this.encodingType);
        }

    }

}
