
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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * This type represents a username token per Section 4.1
 * 
 * <p>Java class for UsernameTokenType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="UsernameTokenType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Username" type="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}AttributedString"/>
 *         <any processContents='lax' maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Id"/>
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsernameTokenType", propOrder = {
    "username",
    "any"
})
public class UsernameTokenType {

    @XmlElement(name = "Username", required = true)
    protected AttributedString username;
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
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link AttributedString }
     *     
     */
    public AttributedString getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributedString }
     *     
     */
    public void setUsername(AttributedString value) {
        this.username = value;
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
    public<_B >void copyTo(final UsernameTokenType.Builder<_B> _other) {
        _other.username = ((this.username == null)?null:this.username.newCopyBuilder(_other));
        _other.id = this.id;
    }

    public<_B >UsernameTokenType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new UsernameTokenType.Builder<_B>(_parentBuilder, this, true);
    }

    public UsernameTokenType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static UsernameTokenType.Builder<Void> builder() {
        return new UsernameTokenType.Builder<>(null, null, false);
    }

    public static<_B >UsernameTokenType.Builder<_B> copyOf(final UsernameTokenType _other) {
        final UsernameTokenType.Builder<_B> _newBuilder = new UsernameTokenType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final UsernameTokenType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree usernamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("username"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usernamePropertyTree!= null):((usernamePropertyTree == null)||(!usernamePropertyTree.isLeaf())))) {
            _other.username = ((this.username == null)?null:this.username.newCopyBuilder(_other, usernamePropertyTree, _propertyTreeUse));
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >UsernameTokenType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new UsernameTokenType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public UsernameTokenType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >UsernameTokenType.Builder<_B> copyOf(final UsernameTokenType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final UsernameTokenType.Builder<_B> _newBuilder = new UsernameTokenType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static UsernameTokenType.Builder<Void> copyExcept(final UsernameTokenType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static UsernameTokenType.Builder<Void> copyOnly(final UsernameTokenType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final UsernameTokenType _storedValue;
        private AttributedString.Builder<UsernameTokenType.Builder<_B>> username;
        private List<Buildable> any;
        private String id;

        public Builder(final _B _parentBuilder, final UsernameTokenType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.username = ((_other.username == null)?null:_other.username.newCopyBuilder(this));
                    this.id = _other.id;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final UsernameTokenType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree usernamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("username"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usernamePropertyTree!= null):((usernamePropertyTree == null)||(!usernamePropertyTree.isLeaf())))) {
                        this.username = ((_other.username == null)?null:_other.username.newCopyBuilder(this, usernamePropertyTree, _propertyTreeUse));
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

        protected<_P extends UsernameTokenType >_P init(final _P _product) {
            _product.username = ((this.username == null)?null:this.username.build());
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
         * Sets the new value of "username" (any previous value will be replaced)
         * 
         * @param username
         *     New value of the "username" property.
         */
        public UsernameTokenType.Builder<_B> withUsername(final AttributedString username) {
            this.username = ((username == null)?null:new AttributedString.Builder<>(this, username, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "username" property.
         * Use {@link
         * org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.AttributedString.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "username" property.
         *     Use {@link
         *     org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.AttributedString.Builder#end()}
         *     to return to the current builder.
         */
        public AttributedString.Builder<? extends UsernameTokenType.Builder<_B>> withUsername() {
            if (this.username!= null) {
                return this.username;
            }
            return this.username = new AttributedString.Builder<>(this, null, false);
        }

        /**
         * Adds the given items to the value of "any"
         * 
         * @param any
         *     Items to add to the value of the "any" property
         */
        public UsernameTokenType.Builder<_B> addAny(final Iterable<?> any) {
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
        public UsernameTokenType.Builder<_B> withAny(final Iterable<?> any) {
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
        public UsernameTokenType.Builder<_B> addAny(Object... any) {
            addAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "any" (any previous value will be replaced)
         * 
         * @param any
         *     New value of the "any" property.
         */
        public UsernameTokenType.Builder<_B> withAny(Object... any) {
            withAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public UsernameTokenType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public UsernameTokenType build() {
            if (_storedValue == null) {
                return this.init(new UsernameTokenType());
            } else {
                return ((UsernameTokenType) _storedValue);
            }
        }

        public UsernameTokenType.Builder<_B> copyOf(final UsernameTokenType _other) {
            _other.copyTo(this);
            return this;
        }

        public UsernameTokenType.Builder<_B> copyOf(final UsernameTokenType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends UsernameTokenType.Selector<UsernameTokenType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static UsernameTokenType.Select _root() {
            return new UsernameTokenType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private AttributedString.Selector<TRoot, UsernameTokenType.Selector<TRoot, TParent>> username = null;
        private com.kscs.util.jaxb.Selector<TRoot, UsernameTokenType.Selector<TRoot, TParent>> any = null;
        private com.kscs.util.jaxb.Selector<TRoot, UsernameTokenType.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.username!= null) {
                products.put("username", this.username.init());
            }
            if (this.any!= null) {
                products.put("any", this.any.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public AttributedString.Selector<TRoot, UsernameTokenType.Selector<TRoot, TParent>> username() {
            return ((this.username == null)?this.username = new AttributedString.Selector<>(this._root, this, "username"):this.username);
        }

        public com.kscs.util.jaxb.Selector<TRoot, UsernameTokenType.Selector<TRoot, TParent>> any() {
            return ((this.any == null)?this.any = new com.kscs.util.jaxb.Selector<>(this._root, this, "any"):this.any);
        }

        public com.kscs.util.jaxb.Selector<TRoot, UsernameTokenType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
