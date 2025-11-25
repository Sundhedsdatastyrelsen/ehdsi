
package org.w3._2000._09.xmldsig_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * <p>Java class for ObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ObjectType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence maxOccurs="unbounded" minOccurs="0">
 *         <any processContents='lax'/>
 *       </sequence>
 *       <attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       <attribute name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="Encoding" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectType", propOrder = {
    "content"
})
public class ObjectType {

    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "MimeType")
    protected String mimeType;
    @XmlAttribute(name = "Encoding")
    @XmlSchemaType(name = "anyURI")
    protected String encoding;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link String }
     * {@link Element }
     * 
     * 
     * @return
     *     The value of the content property.
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return this.content;
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
     * Gets the value of the mimeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the encoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncoding(String value) {
        this.encoding = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ObjectType.Builder<_B> _other) {
        if (this.content == null) {
            _other.content = null;
        } else {
            _other.content = new ArrayList<>();
            for (Object _item: this.content) {
                _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
        _other.id = this.id;
        _other.mimeType = this.mimeType;
        _other.encoding = this.encoding;
    }

    public<_B >ObjectType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ObjectType.Builder<_B>(_parentBuilder, this, true);
    }

    public ObjectType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ObjectType.Builder<Void> builder() {
        return new ObjectType.Builder<>(null, null, false);
    }

    public static<_B >ObjectType.Builder<_B> copyOf(final ObjectType _other) {
        final ObjectType.Builder<_B> _newBuilder = new ObjectType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ObjectType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree contentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("content"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(contentPropertyTree!= null):((contentPropertyTree == null)||(!contentPropertyTree.isLeaf())))) {
            if (this.content == null) {
                _other.content = null;
            } else {
                _other.content = new ArrayList<>();
                for (Object _item: this.content) {
                    _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
        final PropertyTree mimeTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("mimeType"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(mimeTypePropertyTree!= null):((mimeTypePropertyTree == null)||(!mimeTypePropertyTree.isLeaf())))) {
            _other.mimeType = this.mimeType;
        }
        final PropertyTree encodingPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("encoding"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(encodingPropertyTree!= null):((encodingPropertyTree == null)||(!encodingPropertyTree.isLeaf())))) {
            _other.encoding = this.encoding;
        }
    }

    public<_B >ObjectType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ObjectType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ObjectType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ObjectType.Builder<_B> copyOf(final ObjectType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ObjectType.Builder<_B> _newBuilder = new ObjectType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ObjectType.Builder<Void> copyExcept(final ObjectType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ObjectType.Builder<Void> copyOnly(final ObjectType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ObjectType _storedValue;
        private List<Buildable> content;
        private String id;
        private String mimeType;
        private String encoding;

        public Builder(final _B _parentBuilder, final ObjectType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.content == null) {
                        this.content = null;
                    } else {
                        this.content = new ArrayList<>();
                        for (Object _item: _other.content) {
                            this.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                        }
                    }
                    this.id = _other.id;
                    this.mimeType = _other.mimeType;
                    this.encoding = _other.encoding;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ObjectType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree contentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("content"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(contentPropertyTree!= null):((contentPropertyTree == null)||(!contentPropertyTree.isLeaf())))) {
                        if (_other.content == null) {
                            this.content = null;
                        } else {
                            this.content = new ArrayList<>();
                            for (Object _item: _other.content) {
                                this.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                            }
                        }
                    }
                    final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
                        this.id = _other.id;
                    }
                    final PropertyTree mimeTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("mimeType"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(mimeTypePropertyTree!= null):((mimeTypePropertyTree == null)||(!mimeTypePropertyTree.isLeaf())))) {
                        this.mimeType = _other.mimeType;
                    }
                    final PropertyTree encodingPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("encoding"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(encodingPropertyTree!= null):((encodingPropertyTree == null)||(!encodingPropertyTree.isLeaf())))) {
                        this.encoding = _other.encoding;
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

        protected<_P extends ObjectType >_P init(final _P _product) {
            if (this.content!= null) {
                final List<Object> content = new ArrayList<>(this.content.size());
                for (Buildable _item: this.content) {
                    content.add(((Object) _item.build()));
                }
                _product.content = content;
            }
            _product.id = this.id;
            _product.mimeType = this.mimeType;
            _product.encoding = this.encoding;
            return _product;
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public ObjectType.Builder<_B> addContent(final Iterable<?> content) {
            if (content!= null) {
                if (this.content == null) {
                    this.content = new ArrayList<>();
                }
                for (Object _item: content) {
                    this.content.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public ObjectType.Builder<_B> withContent(final Iterable<?> content) {
            if (this.content!= null) {
                this.content.clear();
            }
            return addContent(content);
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public ObjectType.Builder<_B> addContent(Object... content) {
            addContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public ObjectType.Builder<_B> withContent(Object... content) {
            withContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public ObjectType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the new value of "mimeType" (any previous value will be replaced)
         * 
         * @param mimeType
         *     New value of the "mimeType" property.
         */
        public ObjectType.Builder<_B> withMimeType(final String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        /**
         * Sets the new value of "encoding" (any previous value will be replaced)
         * 
         * @param encoding
         *     New value of the "encoding" property.
         */
        public ObjectType.Builder<_B> withEncoding(final String encoding) {
            this.encoding = encoding;
            return this;
        }

        @Override
        public ObjectType build() {
            if (_storedValue == null) {
                return this.init(new ObjectType());
            } else {
                return ((ObjectType) _storedValue);
            }
        }

        public ObjectType.Builder<_B> copyOf(final ObjectType _other) {
            _other.copyTo(this);
            return this;
        }

        public ObjectType.Builder<_B> copyOf(final ObjectType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ObjectType.Selector<ObjectType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ObjectType.Select _root() {
            return new ObjectType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> content = null;
        private com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> id = null;
        private com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> mimeType = null;
        private com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> encoding = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.content!= null) {
                products.put("content", this.content.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            if (this.mimeType!= null) {
                products.put("mimeType", this.mimeType.init());
            }
            if (this.encoding!= null) {
                products.put("encoding", this.encoding.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> content() {
            return ((this.content == null)?this.content = new com.kscs.util.jaxb.Selector<>(this._root, this, "content"):this.content);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> mimeType() {
            return ((this.mimeType == null)?this.mimeType = new com.kscs.util.jaxb.Selector<>(this._root, this, "mimeType"):this.mimeType);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ObjectType.Selector<TRoot, TParent>> encoding() {
            return ((this.encoding == null)?this.encoding = new com.kscs.util.jaxb.Selector<>(this._root, this, "encoding"):this.encoding);
        }

    }

}
