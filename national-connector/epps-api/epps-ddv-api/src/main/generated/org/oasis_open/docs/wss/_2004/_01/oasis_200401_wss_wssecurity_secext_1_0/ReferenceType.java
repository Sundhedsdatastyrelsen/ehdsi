
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * This type represents a reference to an external security token.
 * 
 * <p>Java class for ReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ReferenceType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="URI" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <attribute name="ValueType" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceType")
public class ReferenceType {

    @XmlAttribute(name = "URI")
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlAttribute(name = "ValueType")
    @XmlSchemaType(name = "anyURI")
    protected String valueType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

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
    public<_B >void copyTo(final ReferenceType.Builder<_B> _other) {
        _other.uri = this.uri;
        _other.valueType = this.valueType;
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
        final PropertyTree uriPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("uri"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(uriPropertyTree!= null):((uriPropertyTree == null)||(!uriPropertyTree.isLeaf())))) {
            _other.uri = this.uri;
        }
        final PropertyTree valueTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("valueType"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valueTypePropertyTree!= null):((valueTypePropertyTree == null)||(!valueTypePropertyTree.isLeaf())))) {
            _other.valueType = this.valueType;
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
        private String uri;
        private String valueType;

        public Builder(final _B _parentBuilder, final ReferenceType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.uri = _other.uri;
                    this.valueType = _other.valueType;
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
                    final PropertyTree uriPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("uri"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(uriPropertyTree!= null):((uriPropertyTree == null)||(!uriPropertyTree.isLeaf())))) {
                        this.uri = _other.uri;
                    }
                    final PropertyTree valueTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("valueType"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valueTypePropertyTree!= null):((valueTypePropertyTree == null)||(!valueTypePropertyTree.isLeaf())))) {
                        this.valueType = _other.valueType;
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
            _product.uri = this.uri;
            _product.valueType = this.valueType;
            return _product;
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
         * Sets the new value of "valueType" (any previous value will be replaced)
         * 
         * @param valueType
         *     New value of the "valueType" property.
         */
        public ReferenceType.Builder<_B> withValueType(final String valueType) {
            this.valueType = valueType;
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

        private com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> uri = null;
        private com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> valueType = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.uri!= null) {
                products.put("uri", this.uri.init());
            }
            if (this.valueType!= null) {
                products.put("valueType", this.valueType.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> uri() {
            return ((this.uri == null)?this.uri = new com.kscs.util.jaxb.Selector<>(this._root, this, "uri"):this.uri);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ReferenceType.Selector<TRoot, TParent>> valueType() {
            return ((this.valueType == null)?this.valueType = new com.kscs.util.jaxb.Selector<>(this._root, this, "valueType"):this.valueType);
        }

    }

}
