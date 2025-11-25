
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0;

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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * 
 * This complex type ties together the timestamp related elements into a composite type.
 *             
 * 
 * <p>Java class for TimestampType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TimestampType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Created" minOccurs="0"/>
 *         <element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Expires" minOccurs="0"/>
 *         <choice maxOccurs="unbounded" minOccurs="0">
 *           <any processContents='lax' namespace='##other'/>
 *         </choice>
 *       </sequence>
 *       <attGroup ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}commonAtts"/>
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimestampType", propOrder = {
    "created",
    "expires",
    "any"
})
public class TimestampType {

    @XmlElement(name = "Created")
    protected AttributedDateTime created;
    @XmlElement(name = "Expires")
    protected AttributedDateTime expires;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the created property.
     * 
     * @return
     *     possible object is
     *     {@link AttributedDateTime }
     *     
     */
    public AttributedDateTime getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributedDateTime }
     *     
     */
    public void setCreated(AttributedDateTime value) {
        this.created = value;
    }

    /**
     * Gets the value of the expires property.
     * 
     * @return
     *     possible object is
     *     {@link AttributedDateTime }
     *     
     */
    public AttributedDateTime getExpires() {
        return expires;
    }

    /**
     * Sets the value of the expires property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributedDateTime }
     *     
     */
    public void setExpires(AttributedDateTime value) {
        this.expires = value;
    }

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
    public<_B >void copyTo(final TimestampType.Builder<_B> _other) {
        _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
        _other.expires = ((this.expires == null)?null:this.expires.newCopyBuilder(_other));
        _other.id = this.id;
    }

    public<_B >TimestampType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new TimestampType.Builder<_B>(_parentBuilder, this, true);
    }

    public TimestampType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static TimestampType.Builder<Void> builder() {
        return new TimestampType.Builder<>(null, null, false);
    }

    public static<_B >TimestampType.Builder<_B> copyOf(final TimestampType _other) {
        final TimestampType.Builder<_B> _newBuilder = new TimestampType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final TimestampType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
            _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
        }
        final PropertyTree expiresPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("expires"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(expiresPropertyTree!= null):((expiresPropertyTree == null)||(!expiresPropertyTree.isLeaf())))) {
            _other.expires = ((this.expires == null)?null:this.expires.newCopyBuilder(_other, expiresPropertyTree, _propertyTreeUse));
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >TimestampType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new TimestampType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public TimestampType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >TimestampType.Builder<_B> copyOf(final TimestampType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final TimestampType.Builder<_B> _newBuilder = new TimestampType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static TimestampType.Builder<Void> copyExcept(final TimestampType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static TimestampType.Builder<Void> copyOnly(final TimestampType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final TimestampType _storedValue;
        private AttributedDateTime.Builder<TimestampType.Builder<_B>> created;
        private AttributedDateTime.Builder<TimestampType.Builder<_B>> expires;
        private List<Buildable> any;
        private String id;

        public Builder(final _B _parentBuilder, final TimestampType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
                    this.expires = ((_other.expires == null)?null:_other.expires.newCopyBuilder(this));
                    this.id = _other.id;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final TimestampType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
                        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree expiresPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("expires"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(expiresPropertyTree!= null):((expiresPropertyTree == null)||(!expiresPropertyTree.isLeaf())))) {
                        this.expires = ((_other.expires == null)?null:_other.expires.newCopyBuilder(this, expiresPropertyTree, _propertyTreeUse));
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

        protected<_P extends TimestampType >_P init(final _P _product) {
            _product.created = ((this.created == null)?null:this.created.build());
            _product.expires = ((this.expires == null)?null:this.expires.build());
            if (this.any!= null) {
                final List<Object> any = new ArrayList<>(this.any.size());
                for (Buildable _item: this.any) {
                    any.add(((Object) _item.build()));
                }
                _product.any = any;
            }
            _product.id = this.id;
            return _product;
        }

        /**
         * Sets the new value of "created" (any previous value will be replaced)
         * 
         * @param created
         *     New value of the "created" property.
         */
        public TimestampType.Builder<_B> withCreated(final AttributedDateTime created) {
            this.created = ((created == null)?null:new AttributedDateTime.Builder<>(this, created, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "created" property.
         * Use {@link
         * org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.AttributedDateTime.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "created" property.
         *     Use {@link
         *     org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.AttributedDateTime.Builder#end()}
         *     to return to the current builder.
         */
        public AttributedDateTime.Builder<? extends TimestampType.Builder<_B>> withCreated() {
            if (this.created!= null) {
                return this.created;
            }
            return this.created = new AttributedDateTime.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "expires" (any previous value will be replaced)
         * 
         * @param expires
         *     New value of the "expires" property.
         */
        public TimestampType.Builder<_B> withExpires(final AttributedDateTime expires) {
            this.expires = ((expires == null)?null:new AttributedDateTime.Builder<>(this, expires, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "expires" property.
         * Use {@link
         * org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.AttributedDateTime.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "expires" property.
         *     Use {@link
         *     org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.AttributedDateTime.Builder#end()}
         *     to return to the current builder.
         */
        public AttributedDateTime.Builder<? extends TimestampType.Builder<_B>> withExpires() {
            if (this.expires!= null) {
                return this.expires;
            }
            return this.expires = new AttributedDateTime.Builder<>(this, null, false);
        }

        /**
         * Adds the given items to the value of "any"
         * 
         * @param any
         *     Items to add to the value of the "any" property
         */
        public TimestampType.Builder<_B> addAny(final Iterable<?> any) {
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
        public TimestampType.Builder<_B> withAny(final Iterable<?> any) {
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
        public TimestampType.Builder<_B> addAny(Object... any) {
            addAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "any" (any previous value will be replaced)
         * 
         * @param any
         *     New value of the "any" property.
         */
        public TimestampType.Builder<_B> withAny(Object... any) {
            withAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public TimestampType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public TimestampType build() {
            if (_storedValue == null) {
                return this.init(new TimestampType());
            } else {
                return ((TimestampType) _storedValue);
            }
        }

        public TimestampType.Builder<_B> copyOf(final TimestampType _other) {
            _other.copyTo(this);
            return this;
        }

        public TimestampType.Builder<_B> copyOf(final TimestampType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends TimestampType.Selector<TimestampType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static TimestampType.Select _root() {
            return new TimestampType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private AttributedDateTime.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> created = null;
        private AttributedDateTime.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> expires = null;
        private com.kscs.util.jaxb.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> any = null;
        private com.kscs.util.jaxb.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.created!= null) {
                products.put("created", this.created.init());
            }
            if (this.expires!= null) {
                products.put("expires", this.expires.init());
            }
            if (this.any!= null) {
                products.put("any", this.any.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public AttributedDateTime.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> created() {
            return ((this.created == null)?this.created = new AttributedDateTime.Selector<>(this._root, this, "created"):this.created);
        }

        public AttributedDateTime.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> expires() {
            return ((this.expires == null)?this.expires = new AttributedDateTime.Selector<>(this._root, this, "expires"):this.expires);
        }

        public com.kscs.util.jaxb.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> any() {
            return ((this.any == null)?this.any = new com.kscs.util.jaxb.Selector<>(this._root, this, "any"):this.any);
        }

        public com.kscs.util.jaxb.Selector<TRoot, TimestampType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
