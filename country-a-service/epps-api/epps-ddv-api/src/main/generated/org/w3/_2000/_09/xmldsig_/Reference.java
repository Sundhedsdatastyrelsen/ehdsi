
package org.w3._2000._09.xmldsig_;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Transforms" minOccurs="0"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}DigestMethod"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}DigestValue"/>
 *       </sequence>
 *       <attribute name="URI" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "transforms",
    "digestMethod",
    "digestValue"
})
@XmlRootElement(name = "Reference")
public class Reference {

    @XmlElement(name = "Transforms")
    protected Transforms transforms;
    @XmlElement(name = "DigestMethod", required = true)
    protected DigestMethod digestMethod;
    @XmlElement(name = "DigestValue", required = true)
    protected byte[] digestValue;
    @XmlAttribute(name = "URI", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String uri;

    /**
     * Gets the value of the transforms property.
     * 
     * @return
     *     possible object is
     *     {@link Transforms }
     *     
     */
    public Transforms getTransforms() {
        return transforms;
    }

    /**
     * Sets the value of the transforms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transforms }
     *     
     */
    public void setTransforms(Transforms value) {
        this.transforms = value;
    }

    /**
     * Gets the value of the digestMethod property.
     * 
     * @return
     *     possible object is
     *     {@link DigestMethod }
     *     
     */
    public DigestMethod getDigestMethod() {
        return digestMethod;
    }

    /**
     * Sets the value of the digestMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigestMethod }
     *     
     */
    public void setDigestMethod(DigestMethod value) {
        this.digestMethod = value;
    }

    /**
     * Gets the value of the digestValue property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDigestValue() {
        return digestValue;
    }

    /**
     * Sets the value of the digestValue property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDigestValue(byte[] value) {
        this.digestValue = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURI() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURI(String value) {
        this.uri = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final Reference.Builder<_B> _other) {
        _other.transforms = ((this.transforms == null)?null:this.transforms.newCopyBuilder(_other));
        _other.digestMethod = ((this.digestMethod == null)?null:this.digestMethod.newCopyBuilder(_other));
        _other.digestValue = this.digestValue;
        _other.uri = this.uri;
    }

    public<_B >Reference.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new Reference.Builder<_B>(_parentBuilder, this, true);
    }

    public Reference.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static Reference.Builder<Void> builder() {
        return new Reference.Builder<>(null, null, false);
    }

    public static<_B >Reference.Builder<_B> copyOf(final Reference _other) {
        final Reference.Builder<_B> _newBuilder = new Reference.Builder<>(null, null, false);
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
    public<_B >void copyTo(final Reference.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree transformsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transforms"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformsPropertyTree!= null):((transformsPropertyTree == null)||(!transformsPropertyTree.isLeaf())))) {
            _other.transforms = ((this.transforms == null)?null:this.transforms.newCopyBuilder(_other, transformsPropertyTree, _propertyTreeUse));
        }
        final PropertyTree digestMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("digestMethod"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(digestMethodPropertyTree!= null):((digestMethodPropertyTree == null)||(!digestMethodPropertyTree.isLeaf())))) {
            _other.digestMethod = ((this.digestMethod == null)?null:this.digestMethod.newCopyBuilder(_other, digestMethodPropertyTree, _propertyTreeUse));
        }
        final PropertyTree digestValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("digestValue"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(digestValuePropertyTree!= null):((digestValuePropertyTree == null)||(!digestValuePropertyTree.isLeaf())))) {
            _other.digestValue = this.digestValue;
        }
        final PropertyTree uriPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("uri"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(uriPropertyTree!= null):((uriPropertyTree == null)||(!uriPropertyTree.isLeaf())))) {
            _other.uri = this.uri;
        }
    }

    public<_B >Reference.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new Reference.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public Reference.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >Reference.Builder<_B> copyOf(final Reference _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final Reference.Builder<_B> _newBuilder = new Reference.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static Reference.Builder<Void> copyExcept(final Reference _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static Reference.Builder<Void> copyOnly(final Reference _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final Reference _storedValue;
        private Transforms.Builder<Reference.Builder<_B>> transforms;
        private DigestMethod.Builder<Reference.Builder<_B>> digestMethod;
        private byte[] digestValue;
        private String uri;

        public Builder(final _B _parentBuilder, final Reference _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.transforms = ((_other.transforms == null)?null:_other.transforms.newCopyBuilder(this));
                    this.digestMethod = ((_other.digestMethod == null)?null:_other.digestMethod.newCopyBuilder(this));
                    this.digestValue = _other.digestValue;
                    this.uri = _other.uri;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final Reference _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree transformsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transforms"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformsPropertyTree!= null):((transformsPropertyTree == null)||(!transformsPropertyTree.isLeaf())))) {
                        this.transforms = ((_other.transforms == null)?null:_other.transforms.newCopyBuilder(this, transformsPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree digestMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("digestMethod"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(digestMethodPropertyTree!= null):((digestMethodPropertyTree == null)||(!digestMethodPropertyTree.isLeaf())))) {
                        this.digestMethod = ((_other.digestMethod == null)?null:_other.digestMethod.newCopyBuilder(this, digestMethodPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree digestValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("digestValue"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(digestValuePropertyTree!= null):((digestValuePropertyTree == null)||(!digestValuePropertyTree.isLeaf())))) {
                        this.digestValue = _other.digestValue;
                    }
                    final PropertyTree uriPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("uri"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(uriPropertyTree!= null):((uriPropertyTree == null)||(!uriPropertyTree.isLeaf())))) {
                        this.uri = _other.uri;
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

        protected<_P extends Reference >_P init(final _P _product) {
            _product.transforms = ((this.transforms == null)?null:this.transforms.build());
            _product.digestMethod = ((this.digestMethod == null)?null:this.digestMethod.build());
            _product.digestValue = this.digestValue;
            _product.uri = this.uri;
            return _product;
        }

        /**
         * Sets the new value of "transforms" (any previous value will be replaced)
         * 
         * @param transforms
         *     New value of the "transforms" property.
         */
        public Reference.Builder<_B> withTransforms(final Transforms transforms) {
            this.transforms = ((transforms == null)?null:new Transforms.Builder<>(this, transforms, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "transforms" property.
         * Use {@link org.w3._2000._09.xmldsig_.Transforms.Builder#end()} to return to the
         * current builder.
         * 
         * @return
         *     A new builder to build the value of the "transforms" property.
         *     Use {@link org.w3._2000._09.xmldsig_.Transforms.Builder#end()} to return to the
         *     current builder.
         */
        public Transforms.Builder<? extends Reference.Builder<_B>> withTransforms() {
            if (this.transforms!= null) {
                return this.transforms;
            }
            return this.transforms = new Transforms.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "digestMethod" (any previous value will be replaced)
         * 
         * @param digestMethod
         *     New value of the "digestMethod" property.
         */
        public Reference.Builder<_B> withDigestMethod(final DigestMethod digestMethod) {
            this.digestMethod = ((digestMethod == null)?null:new DigestMethod.Builder<>(this, digestMethod, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "digestMethod" property.
         * Use {@link org.w3._2000._09.xmldsig_.DigestMethod.Builder#end()} to return to
         * the current builder.
         * 
         * @return
         *     A new builder to build the value of the "digestMethod" property.
         *     Use {@link org.w3._2000._09.xmldsig_.DigestMethod.Builder#end()} to return to
         *     the current builder.
         */
        public DigestMethod.Builder<? extends Reference.Builder<_B>> withDigestMethod() {
            if (this.digestMethod!= null) {
                return this.digestMethod;
            }
            return this.digestMethod = new DigestMethod.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "digestValue" (any previous value will be replaced)
         * 
         * @param digestValue
         *     New value of the "digestValue" property.
         */
        public Reference.Builder<_B> withDigestValue(final byte[] digestValue) {
            this.digestValue = digestValue;
            return this;
        }

        /**
         * Sets the new value of "uri" (any previous value will be replaced)
         * 
         * @param uri
         *     New value of the "uri" property.
         */
        public Reference.Builder<_B> withURI(final String uri) {
            this.uri = uri;
            return this;
        }

        @Override
        public Reference build() {
            if (_storedValue == null) {
                return this.init(new Reference());
            } else {
                return ((Reference) _storedValue);
            }
        }

        public Reference.Builder<_B> copyOf(final Reference _other) {
            _other.copyTo(this);
            return this;
        }

        public Reference.Builder<_B> copyOf(final Reference.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends Reference.Selector<Reference.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static Reference.Select _root() {
            return new Reference.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private Transforms.Selector<TRoot, Reference.Selector<TRoot, TParent>> transforms = null;
        private DigestMethod.Selector<TRoot, Reference.Selector<TRoot, TParent>> digestMethod = null;
        private com.kscs.util.jaxb.Selector<TRoot, Reference.Selector<TRoot, TParent>> digestValue = null;
        private com.kscs.util.jaxb.Selector<TRoot, Reference.Selector<TRoot, TParent>> uri = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.transforms!= null) {
                products.put("transforms", this.transforms.init());
            }
            if (this.digestMethod!= null) {
                products.put("digestMethod", this.digestMethod.init());
            }
            if (this.digestValue!= null) {
                products.put("digestValue", this.digestValue.init());
            }
            if (this.uri!= null) {
                products.put("uri", this.uri.init());
            }
            return products;
        }

        public Transforms.Selector<TRoot, Reference.Selector<TRoot, TParent>> transforms() {
            return ((this.transforms == null)?this.transforms = new Transforms.Selector<>(this._root, this, "transforms"):this.transforms);
        }

        public DigestMethod.Selector<TRoot, Reference.Selector<TRoot, TParent>> digestMethod() {
            return ((this.digestMethod == null)?this.digestMethod = new DigestMethod.Selector<>(this._root, this, "digestMethod"):this.digestMethod);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Reference.Selector<TRoot, TParent>> digestValue() {
            return ((this.digestValue == null)?this.digestValue = new com.kscs.util.jaxb.Selector<>(this._root, this, "digestValue"):this.digestValue);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Reference.Selector<TRoot, TParent>> uri() {
            return ((this.uri == null)?this.uri = new com.kscs.util.jaxb.Selector<>(this._root, this, "uri"):this.uri);
        }

    }

}
