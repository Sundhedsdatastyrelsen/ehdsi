
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
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetrievalMethodType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RetrievalMethodType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Transforms" minOccurs="0"/>
 *       </sequence>
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
@XmlType(name = "RetrievalMethodType", propOrder = {
    "transforms"
})
public class RetrievalMethodType {

    @XmlElement(name = "Transforms")
    protected TransformsType transforms;
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
    public<_B >void copyTo(final RetrievalMethodType.Builder<_B> _other) {
        _other.transforms = ((this.transforms == null)?null:this.transforms.newCopyBuilder(_other));
        _other.uri = this.uri;
        _other.type = this.type;
    }

    public<_B >RetrievalMethodType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new RetrievalMethodType.Builder<_B>(_parentBuilder, this, true);
    }

    public RetrievalMethodType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static RetrievalMethodType.Builder<Void> builder() {
        return new RetrievalMethodType.Builder<>(null, null, false);
    }

    public static<_B >RetrievalMethodType.Builder<_B> copyOf(final RetrievalMethodType _other) {
        final RetrievalMethodType.Builder<_B> _newBuilder = new RetrievalMethodType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final RetrievalMethodType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree transformsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transforms"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformsPropertyTree!= null):((transformsPropertyTree == null)||(!transformsPropertyTree.isLeaf())))) {
            _other.transforms = ((this.transforms == null)?null:this.transforms.newCopyBuilder(_other, transformsPropertyTree, _propertyTreeUse));
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

    public<_B >RetrievalMethodType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new RetrievalMethodType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public RetrievalMethodType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >RetrievalMethodType.Builder<_B> copyOf(final RetrievalMethodType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final RetrievalMethodType.Builder<_B> _newBuilder = new RetrievalMethodType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static RetrievalMethodType.Builder<Void> copyExcept(final RetrievalMethodType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static RetrievalMethodType.Builder<Void> copyOnly(final RetrievalMethodType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final RetrievalMethodType _storedValue;
        private TransformsType.Builder<RetrievalMethodType.Builder<_B>> transforms;
        private String uri;
        private String type;

        public Builder(final _B _parentBuilder, final RetrievalMethodType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.transforms = ((_other.transforms == null)?null:_other.transforms.newCopyBuilder(this));
                    this.uri = _other.uri;
                    this.type = _other.type;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final RetrievalMethodType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree transformsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transforms"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformsPropertyTree!= null):((transformsPropertyTree == null)||(!transformsPropertyTree.isLeaf())))) {
                        this.transforms = ((_other.transforms == null)?null:_other.transforms.newCopyBuilder(this, transformsPropertyTree, _propertyTreeUse));
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

        protected<_P extends RetrievalMethodType >_P init(final _P _product) {
            _product.transforms = ((this.transforms == null)?null:this.transforms.build());
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
        public RetrievalMethodType.Builder<_B> withTransforms(final TransformsType transforms) {
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
        public TransformsType.Builder<? extends RetrievalMethodType.Builder<_B>> withTransforms() {
            if (this.transforms!= null) {
                return this.transforms;
            }
            return this.transforms = new TransformsType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "uri" (any previous value will be replaced)
         * 
         * @param uri
         *     New value of the "uri" property.
         */
        public RetrievalMethodType.Builder<_B> withURI(final String uri) {
            this.uri = uri;
            return this;
        }

        /**
         * Sets the new value of "type" (any previous value will be replaced)
         * 
         * @param type
         *     New value of the "type" property.
         */
        public RetrievalMethodType.Builder<_B> withType(final String type) {
            this.type = type;
            return this;
        }

        @Override
        public RetrievalMethodType build() {
            if (_storedValue == null) {
                return this.init(new RetrievalMethodType());
            } else {
                return ((RetrievalMethodType) _storedValue);
            }
        }

        public RetrievalMethodType.Builder<_B> copyOf(final RetrievalMethodType _other) {
            _other.copyTo(this);
            return this;
        }

        public RetrievalMethodType.Builder<_B> copyOf(final RetrievalMethodType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends RetrievalMethodType.Selector<RetrievalMethodType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static RetrievalMethodType.Select _root() {
            return new RetrievalMethodType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private TransformsType.Selector<TRoot, RetrievalMethodType.Selector<TRoot, TParent>> transforms = null;
        private com.kscs.util.jaxb.Selector<TRoot, RetrievalMethodType.Selector<TRoot, TParent>> uri = null;
        private com.kscs.util.jaxb.Selector<TRoot, RetrievalMethodType.Selector<TRoot, TParent>> type = null;

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
            if (this.uri!= null) {
                products.put("uri", this.uri.init());
            }
            if (this.type!= null) {
                products.put("type", this.type.init());
            }
            return products;
        }

        public TransformsType.Selector<TRoot, RetrievalMethodType.Selector<TRoot, TParent>> transforms() {
            return ((this.transforms == null)?this.transforms = new TransformsType.Selector<>(this._root, this, "transforms"):this.transforms);
        }

        public com.kscs.util.jaxb.Selector<TRoot, RetrievalMethodType.Selector<TRoot, TParent>> uri() {
            return ((this.uri == null)?this.uri = new com.kscs.util.jaxb.Selector<>(this._root, this, "uri"):this.uri);
        }

        public com.kscs.util.jaxb.Selector<TRoot, RetrievalMethodType.Selector<TRoot, TParent>> type() {
            return ((this.type == null)?this.type = new com.kscs.util.jaxb.Selector<>(this._root, this, "type"):this.type);
        }

    }

}
