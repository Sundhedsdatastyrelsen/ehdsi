
package org.w3._2000._09.xmldsig_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for KeyValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="KeyValueType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}DSAKeyValue"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}RSAKeyValue"/>
 *         <any processContents='lax' namespace='##other'/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyValueType", propOrder = {
    "content"
})
public class KeyValueType {

    @XmlElementRefs({
        @XmlElementRef(name = "DSAKeyValue", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "RSAKeyValue", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;

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
     * {@link JAXBElement }{@code <}{@link DSAKeyValueType }{@code >}
     * {@link JAXBElement }{@code <}{@link RSAKeyValueType }{@code >}
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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final KeyValueType.Builder<_B> _other) {
        if (this.content == null) {
            _other.content = null;
        } else {
            _other.content = new ArrayList<>();
            for (Object _item: this.content) {
                _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
    }

    public<_B >KeyValueType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new KeyValueType.Builder<_B>(_parentBuilder, this, true);
    }

    public KeyValueType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static KeyValueType.Builder<Void> builder() {
        return new KeyValueType.Builder<>(null, null, false);
    }

    public static<_B >KeyValueType.Builder<_B> copyOf(final KeyValueType _other) {
        final KeyValueType.Builder<_B> _newBuilder = new KeyValueType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final KeyValueType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
    }

    public<_B >KeyValueType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new KeyValueType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public KeyValueType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >KeyValueType.Builder<_B> copyOf(final KeyValueType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final KeyValueType.Builder<_B> _newBuilder = new KeyValueType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static KeyValueType.Builder<Void> copyExcept(final KeyValueType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static KeyValueType.Builder<Void> copyOnly(final KeyValueType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final KeyValueType _storedValue;
        private List<Buildable> content;

        public Builder(final _B _parentBuilder, final KeyValueType _other, final boolean _copy) {
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
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final KeyValueType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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

        protected<_P extends KeyValueType >_P init(final _P _product) {
            if (this.content!= null) {
                final List<Object> content = new ArrayList<>(this.content.size());
                for (Buildable _item: this.content) {
                    content.add(((Object) _item.build()));
                }
                _product.content = content;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public KeyValueType.Builder<_B> addContent(final Iterable<?> content) {
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
        public KeyValueType.Builder<_B> withContent(final Iterable<?> content) {
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
        public KeyValueType.Builder<_B> addContent(Object... content) {
            addContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public KeyValueType.Builder<_B> withContent(Object... content) {
            withContent(Arrays.asList(content));
            return this;
        }

        @Override
        public KeyValueType build() {
            if (_storedValue == null) {
                return this.init(new KeyValueType());
            } else {
                return ((KeyValueType) _storedValue);
            }
        }

        public KeyValueType.Builder<_B> copyOf(final KeyValueType _other) {
            _other.copyTo(this);
            return this;
        }

        public KeyValueType.Builder<_B> copyOf(final KeyValueType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends KeyValueType.Selector<KeyValueType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static KeyValueType.Select _root() {
            return new KeyValueType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, KeyValueType.Selector<TRoot, TParent>> content = null;

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
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, KeyValueType.Selector<TRoot, TParent>> content() {
            return ((this.content == null)?this.content = new com.kscs.util.jaxb.Selector<>(this._root, this, "content"):this.content);
        }

    }

}
