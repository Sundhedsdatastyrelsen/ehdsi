
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
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ReferenceType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Transforms" minOccurs="0"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}DigestMethod"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}DigestValue"/>
 *       </sequence>
 *       <attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       <attribute name="URI" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <attribute name="Type" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceType", propOrder = {
    "transforms",
    "digestMethod",
    "digestValue"
})
public class ReferenceType {

    @XmlElement(name = "Transforms")
    protected TransformsType transforms;
    @XmlElement(name = "DigestMethod", required = true)
    protected DigestMethodType digestMethod;
    @XmlElement(name = "DigestValue", required = true)
    protected byte[] digestValue;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "URI")
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlAttribute(name = "Type")
    @XmlSchemaType(name = "anyURI")
    protected String type;

    /**
     * Gets the value of the transforms property.
     * 
     * @return
     *     possible object is
     *     {@link TransformsType }
     *     
     */
    public TransformsType getTransforms() {
        return transforms;
    }

    /**
     * Sets the value of the transforms property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformsType }
     *     
     */
    public void setTransforms(TransformsType value) {
        this.transforms = value;
    }

    /**
     * Gets the value of the digestMethod property.
     * 
     * @return
     *     possible object is
     *     {@link DigestMethodType }
     *     
     */
    public DigestMethodType getDigestMethod() {
        return digestMethod;
    }

    /**
     * Sets the value of the digestMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigestMethodType }
     *     
     */
    public void setDigestMethod(DigestMethodType value) {
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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ReferenceType.Builder<_B> _other) {
        _other.transforms = ((this.transforms == null)?null:this.transforms.newCopyBuilder(_other));
        _other.digestMethod = ((this.digestMethod == null)?null:this.digestMethod.newCopyBuilder(_other));
        _other.digestValue = this.digestValue;
        _other.id = this.id;
        _other.uri = this.uri;
        _other.type = this.type;
    }

    public<_B >ReferenceType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ReferenceType.Builder<_B>(_parentBuilder, this, true);
    }

    public ReferenceType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ReferenceType.Builder<Void> builder() {
        return new ReferenceType.Builder<>(null, null, false);
    }

    public static<_B >ReferenceType.Builder<_B> copyOf(final ReferenceType _other) {
        final ReferenceType.Builder<_B> _newBuilder = new ReferenceType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ReferenceType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
        final PropertyTree uriPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("uri"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(uriPropertyTree!= null):((uriPropertyTree == null)||(!uriPropertyTree.isLeaf())))) {
            _other.uri = this.uri;
        }
        final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
            _other.type = this.type;
        }
    }

    public<_B >ReferenceType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ReferenceType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ReferenceType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ReferenceType.Builder<_B> copyOf(final ReferenceType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ReferenceType.Builder<_B> _newBuilder = new ReferenceType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ReferenceType.Builder<Void> copyExcept(final ReferenceType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ReferenceType.Builder<Void> copyOnly(final ReferenceType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ReferenceType _storedValue;
        private TransformsType.Builder<ReferenceType.Builder<_B>> transforms;
        private DigestMethodType.Builder<ReferenceType.Builder<_B>> digestMethod;
        private byte[] digestValue;
        private String id;
        private String uri;
        private String type;

        public Builder(final _B _parentBuilder, final ReferenceType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.transforms = ((_other.transforms == null)?null:_other.transforms.newCopyBuilder(this));
                    this.digestMethod = ((_other.digestMethod == null)?null:_other.digestMethod.newCopyBuilder(this));
                    this.digestValue = _other.digestValue;
                    this.id = _other.id;
                    this.uri = _other.uri;
                    this.type = _other.type;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ReferenceType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
                    final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
                        this.id = _other.id;
                    }
                    final PropertyTree uriPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("uri"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(uriPropertyTree!= null):((uriPropertyTree == null)||(!uriPropertyTree.isLeaf())))) {
                        this.uri = _other.uri;
                    }
                    final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
                        this.type = _other.type;
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

        protected<_P extends ReferenceType >_P init(final _P _product) {
            _product.transforms = ((this.transforms == null)?null:this.transforms.build());
            _product.digestMethod = ((this.digestMethod == null)?null:this.digestMethod.build());
            _product.digestValue = this.digestValue;
            _product.id = this.id;
            _product.uri = this.uri;
            _product.type = this.type;
            return _product;
        }

        /**
         * Sets the new value of "transforms" (any previous value will be replaced)
         * 
         * @param transforms
         *     New value of the "transforms" property.
         */
        public ReferenceType.Builder<_B> withTransforms(final TransformsType transforms) {
            this.transforms = ((transforms == null)?null:new TransformsType.Builder<>(this, transforms, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "transforms" property.
         * Use {@link org.w3._2000._09.xmldsig_.TransformsType.Builder#end()} to return to
         * the current builder.
         * 
         * @return
         *     A new builder to build the value of the "transforms" property.
         *     Use {@link org.w3._2000._09.xmldsig_.TransformsType.Builder#end()} to return to
         *     the current builder.
         */
        public TransformsType.Builder<? extends ReferenceType.Builder<_B>> withTransforms() {
            if (this.transforms!= null) {
                return this.transforms;
            }
            return this.transforms = new TransformsType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "digestMethod" (any previous value will be replaced)
         * 
         * @param digestMethod
         *     New value of the "digestMethod" property.
         */
        public ReferenceType.Builder<_B> withDigestMethod(final DigestMethodType digestMethod) {
            this.digestMethod = ((digestMethod == null)?null:new DigestMethodType.Builder<>(this, digestMethod, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "digestMethod" property.
         * Use {@link org.w3._2000._09.xmldsig_.DigestMethodType.Builder#end()} to return
         * to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "digestMethod" property.
         *     Use {@link org.w3._2000._09.xmldsig_.DigestMethodType.Builder#end()} to return
         *     to the current builder.
         */
        public DigestMethodType.Builder<? extends ReferenceType.Builder<_B>> withDigestMethod() {
            if (this.digestMethod!= null) {
                return this.digestMethod;
            }
            return this.digestMethod = new DigestMethodType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "digestValue" (any previous value will be replaced)
         * 
         * @param digestValue
         *     New value of the "digestValue" property.
         */
        public ReferenceType.Builder<_B> withDigestValue(final byte[] digestValue) {
            this.digestValue = digestValue;
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public ReferenceType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the new value of "uri" (any previous value will be replaced)
         * 
         * @param uri
         *     New value of the "uri" property.
         */
        public ReferenceType.Builder<_B> withURI(final String uri) {
            this.uri = uri;
            return this;
        }

        /**
         * Sets the new value of "type" (any previous value will be replaced)
         * 
         * @param type
         *     New value of the "type" property.
         */
        public ReferenceType.Builder<_B> withType(final String type) {
            this.type = type;
            return this;
        }

        @Override
        public ReferenceType build() {
            if (_storedValue == null) {
                return this.init(new ReferenceType());
            } else {
                return ((ReferenceType) _storedValue);
            }
        }

        public ReferenceType.Builder<_B> copyOf(final ReferenceType _other) {
            _other.copyTo(this);
            return this;
        }

        public ReferenceType.Builder<_B> copyOf(final ReferenceType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ReferenceType.Selector<ReferenceType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ReferenceType.Select _root() {
            return new ReferenceType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private TransformsType.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> transforms = null;
        private DigestMethodType.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> digestMethod = null;
        private com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> digestValue = null;
        private com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> id = null;
        private com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> uri = null;
        private com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> type = null;

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
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            if (this.uri!= null) {
                products.put("uri", this.uri.init());
            }
            if (this.type!= null) {
                products.put("type", this.type.init());
            }
            return products;
        }

        public TransformsType.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> transforms() {
            return ((this.transforms == null)?this.transforms = new TransformsType.Selector<>(this._root, this, "transforms"):this.transforms);
        }

        public DigestMethodType.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> digestMethod() {
            return ((this.digestMethod == null)?this.digestMethod = new DigestMethodType.Selector<>(this._root, this, "digestMethod"):this.digestMethod);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> digestValue() {
            return ((this.digestValue == null)?this.digestValue = new com.kscs.util.jaxb.Selector<>(this._root, this, "digestValue"):this.digestValue);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> uri() {
            return ((this.uri == null)?this.uri = new com.kscs.util.jaxb.Selector<>(this._root, this, "uri"):this.uri);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> type() {
            return ((this.type == null)?this.type = new com.kscs.util.jaxb.Selector<>(this._root, this, "type"):this.type);
        }

    }

}
