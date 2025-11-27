
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
 * <p>Java class for SignaturePropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SignaturePropertyType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <any processContents='lax' namespace='##other'/>
 *       </choice>
 *       <attribute name="Target" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignaturePropertyType", propOrder = {
    "content"
})
public class SignaturePropertyType {

    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;
    @XmlAttribute(name = "Target", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String target;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

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
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final SignaturePropertyType.Builder<_B> _other) {
        if (this.content == null) {
            _other.content = null;
        } else {
            _other.content = new ArrayList<>();
            for (Object _item: this.content) {
                _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
        _other.target = this.target;
        _other.id = this.id;
    }

    public<_B >SignaturePropertyType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SignaturePropertyType.Builder<_B>(_parentBuilder, this, true);
    }

    public SignaturePropertyType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SignaturePropertyType.Builder<Void> builder() {
        return new SignaturePropertyType.Builder<>(null, null, false);
    }

    public static<_B >SignaturePropertyType.Builder<_B> copyOf(final SignaturePropertyType _other) {
        final SignaturePropertyType.Builder<_B> _newBuilder = new SignaturePropertyType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SignaturePropertyType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
        final PropertyTree targetPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("target"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(targetPropertyTree!= null):((targetPropertyTree == null)||(!targetPropertyTree.isLeaf())))) {
            _other.target = this.target;
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >SignaturePropertyType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SignaturePropertyType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SignaturePropertyType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SignaturePropertyType.Builder<_B> copyOf(final SignaturePropertyType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SignaturePropertyType.Builder<_B> _newBuilder = new SignaturePropertyType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SignaturePropertyType.Builder<Void> copyExcept(final SignaturePropertyType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SignaturePropertyType.Builder<Void> copyOnly(final SignaturePropertyType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SignaturePropertyType _storedValue;
        private List<Buildable> content;
        private String target;
        private String id;

        public Builder(final _B _parentBuilder, final SignaturePropertyType _other, final boolean _copy) {
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
                    this.target = _other.target;
                    this.id = _other.id;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final SignaturePropertyType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
                    final PropertyTree targetPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("target"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(targetPropertyTree!= null):((targetPropertyTree == null)||(!targetPropertyTree.isLeaf())))) {
                        this.target = _other.target;
                    }
                    final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
                        this.id = _other.id;
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

        protected<_P extends SignaturePropertyType >_P init(final _P _product) {
            if (this.content!= null) {
                final List<Object> content = new ArrayList<>(this.content.size());
                for (Buildable _item: this.content) {
                    content.add(((Object) _item.build()));
                }
                _product.content = content;
            }
            _product.target = this.target;
            _product.id = this.id;
            return _product;
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public SignaturePropertyType.Builder<_B> addContent(final Iterable<?> content) {
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
        public SignaturePropertyType.Builder<_B> withContent(final Iterable<?> content) {
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
        public SignaturePropertyType.Builder<_B> addContent(Object... content) {
            addContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public SignaturePropertyType.Builder<_B> withContent(Object... content) {
            withContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "target" (any previous value will be replaced)
         * 
         * @param target
         *     New value of the "target" property.
         */
        public SignaturePropertyType.Builder<_B> withTarget(final String target) {
            this.target = target;
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public SignaturePropertyType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public SignaturePropertyType build() {
            if (_storedValue == null) {
                return this.init(new SignaturePropertyType());
            } else {
                return ((SignaturePropertyType) _storedValue);
            }
        }

        public SignaturePropertyType.Builder<_B> copyOf(final SignaturePropertyType _other) {
            _other.copyTo(this);
            return this;
        }

        public SignaturePropertyType.Builder<_B> copyOf(final SignaturePropertyType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SignaturePropertyType.Selector<SignaturePropertyType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SignaturePropertyType.Select _root() {
            return new SignaturePropertyType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, SignaturePropertyType.Selector<TRoot, TParent>> content = null;
        private com.kscs.util.jaxb.Selector<TRoot, SignaturePropertyType.Selector<TRoot, TParent>> target = null;
        private com.kscs.util.jaxb.Selector<TRoot, SignaturePropertyType.Selector<TRoot, TParent>> id = null;

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
            if (this.target!= null) {
                products.put("target", this.target.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignaturePropertyType.Selector<TRoot, TParent>> content() {
            return ((this.content == null)?this.content = new com.kscs.util.jaxb.Selector<>(this._root, this, "content"):this.content);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignaturePropertyType.Selector<TRoot, TParent>> target() {
            return ((this.target == null)?this.target = new com.kscs.util.jaxb.Selector<>(this._root, this, "target"):this.target);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignaturePropertyType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
