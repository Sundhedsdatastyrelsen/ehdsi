
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * This type is used reference a security token.
 * 
 * <p>Java class for SecurityTokenReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SecurityTokenReferenceType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded" minOccurs="0">
 *         <any processContents='lax'/>
 *       </choice>
 *       <attribute ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Id"/>
 *       <attribute ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}Usage"/>
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityTokenReferenceType", propOrder = {
    "any"
})
public class SecurityTokenReferenceType {

    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "Usage", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
    protected List<String> usage;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     * 
     * 
     * @return
     *     The value of the any property.
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<>();
        }
        return this.any;
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
     * Gets the value of the usage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the usage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     * @return
     *     The value of the usage property.
     */
    public List<String> getUsage() {
        if (usage == null) {
            usage = new ArrayList<>();
        }
        return this.usage;
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
    public<_B >void copyTo(final SecurityTokenReferenceType.Builder<_B> _other) {
        _other.id = this.id;
        if (this.usage == null) {
            _other.usage = null;
        } else {
            _other.usage = new ArrayList<>();
            for (String _item: this.usage) {
                _other.usage.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
    }

    public<_B >SecurityTokenReferenceType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SecurityTokenReferenceType.Builder<_B>(_parentBuilder, this, true);
    }

    public SecurityTokenReferenceType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SecurityTokenReferenceType.Builder<Void> builder() {
        return new SecurityTokenReferenceType.Builder<>(null, null, false);
    }

    public static<_B >SecurityTokenReferenceType.Builder<_B> copyOf(final SecurityTokenReferenceType _other) {
        final SecurityTokenReferenceType.Builder<_B> _newBuilder = new SecurityTokenReferenceType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SecurityTokenReferenceType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
        final PropertyTree usagePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("usage"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usagePropertyTree!= null):((usagePropertyTree == null)||(!usagePropertyTree.isLeaf())))) {
            if (this.usage == null) {
                _other.usage = null;
            } else {
                _other.usage = new ArrayList<>();
                for (String _item: this.usage) {
                    _other.usage.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
    }

    public<_B >SecurityTokenReferenceType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SecurityTokenReferenceType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SecurityTokenReferenceType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SecurityTokenReferenceType.Builder<_B> copyOf(final SecurityTokenReferenceType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SecurityTokenReferenceType.Builder<_B> _newBuilder = new SecurityTokenReferenceType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SecurityTokenReferenceType.Builder<Void> copyExcept(final SecurityTokenReferenceType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SecurityTokenReferenceType.Builder<Void> copyOnly(final SecurityTokenReferenceType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SecurityTokenReferenceType _storedValue;
        private List<Buildable> any;
        private String id;
        private List<Buildable> usage;

        public Builder(final _B _parentBuilder, final SecurityTokenReferenceType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.id = _other.id;
                    if (_other.usage == null) {
                        this.usage = null;
                    } else {
                        this.usage = new ArrayList<>();
                        for (String _item: _other.usage) {
                            this.usage.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final SecurityTokenReferenceType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
                        this.id = _other.id;
                    }
                    final PropertyTree usagePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("usage"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usagePropertyTree!= null):((usagePropertyTree == null)||(!usagePropertyTree.isLeaf())))) {
                        if (_other.usage == null) {
                            this.usage = null;
                        } else {
                            this.usage = new ArrayList<>();
                            for (String _item: _other.usage) {
                                this.usage.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
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

        protected<_P extends SecurityTokenReferenceType >_P init(final _P _product) {
            if (this.any!= null) {
                final List<Object> any = new ArrayList<>(this.any.size());
                for (Buildable _item: this.any) {
                    any.add(((Object) _item.build()));
                }
                _product.any = any;
            }
            _product.id = this.id;
            if (this.usage!= null) {
                final List<String> usage = new ArrayList<>(this.usage.size());
                for (Buildable _item: this.usage) {
                    usage.add(((String) _item.build()));
                }
                _product.usage = usage;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "any"
         * 
         * @param any
         *     Items to add to the value of the "any" property
         */
        public SecurityTokenReferenceType.Builder<_B> addAny(final Iterable<?> any) {
            if (any!= null) {
                if (this.any == null) {
                    this.any = new ArrayList<>();
                }
                for (Object _item: any) {
                    this.any.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "any" (any previous value will be replaced)
         * 
         * @param any
         *     New value of the "any" property.
         */
        public SecurityTokenReferenceType.Builder<_B> withAny(final Iterable<?> any) {
            if (this.any!= null) {
                this.any.clear();
            }
            return addAny(any);
        }

        /**
         * Adds the given items to the value of "any"
         * 
         * @param any
         *     Items to add to the value of the "any" property
         */
        public SecurityTokenReferenceType.Builder<_B> addAny(Object... any) {
            addAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "any" (any previous value will be replaced)
         * 
         * @param any
         *     New value of the "any" property.
         */
        public SecurityTokenReferenceType.Builder<_B> withAny(Object... any) {
            withAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public SecurityTokenReferenceType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        /**
         * Adds the given items to the value of "usage"
         * 
         * @param usage
         *     Items to add to the value of the "usage" property
         */
        public SecurityTokenReferenceType.Builder<_B> addUsage(final Iterable<? extends String> usage) {
            if (usage!= null) {
                if (this.usage == null) {
                    this.usage = new ArrayList<>();
                }
                for (String _item: usage) {
                    this.usage.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "usage" (any previous value will be replaced)
         * 
         * @param usage
         *     New value of the "usage" property.
         */
        public SecurityTokenReferenceType.Builder<_B> withUsage(final Iterable<? extends String> usage) {
            if (this.usage!= null) {
                this.usage.clear();
            }
            return addUsage(usage);
        }

        /**
         * Adds the given items to the value of "usage"
         * 
         * @param usage
         *     Items to add to the value of the "usage" property
         */
        public SecurityTokenReferenceType.Builder<_B> addUsage(String... usage) {
            addUsage(Arrays.asList(usage));
            return this;
        }

        /**
         * Sets the new value of "usage" (any previous value will be replaced)
         * 
         * @param usage
         *     New value of the "usage" property.
         */
        public SecurityTokenReferenceType.Builder<_B> withUsage(String... usage) {
            withUsage(Arrays.asList(usage));
            return this;
        }

        @Override
        public SecurityTokenReferenceType build() {
            if (_storedValue == null) {
                return this.init(new SecurityTokenReferenceType());
            } else {
                return ((SecurityTokenReferenceType) _storedValue);
            }
        }

        public SecurityTokenReferenceType.Builder<_B> copyOf(final SecurityTokenReferenceType _other) {
            _other.copyTo(this);
            return this;
        }

        public SecurityTokenReferenceType.Builder<_B> copyOf(final SecurityTokenReferenceType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SecurityTokenReferenceType.Selector<SecurityTokenReferenceType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SecurityTokenReferenceType.Select _root() {
            return new SecurityTokenReferenceType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, SecurityTokenReferenceType.Selector<TRoot, TParent>> any = null;
        private com.kscs.util.jaxb.Selector<TRoot, SecurityTokenReferenceType.Selector<TRoot, TParent>> id = null;
        private com.kscs.util.jaxb.Selector<TRoot, SecurityTokenReferenceType.Selector<TRoot, TParent>> usage = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.any!= null) {
                products.put("any", this.any.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            if (this.usage!= null) {
                products.put("usage", this.usage.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, SecurityTokenReferenceType.Selector<TRoot, TParent>> any() {
            return ((this.any == null)?this.any = new com.kscs.util.jaxb.Selector<>(this._root, this, "any"):this.any);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SecurityTokenReferenceType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SecurityTokenReferenceType.Selector<TRoot, TParent>> usage() {
            return ((this.usage == null)?this.usage = new com.kscs.util.jaxb.Selector<>(this._root, this, "usage"):this.usage);
        }

    }

}
