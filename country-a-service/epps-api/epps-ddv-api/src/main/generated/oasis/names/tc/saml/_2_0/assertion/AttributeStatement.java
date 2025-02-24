
package oasis.names.tc.saml._2_0.assertion;

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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Attribute" maxOccurs="unbounded"/>
 *       </sequence>
 *       <attribute name="id" use="required">
 *         <simpleType>
 *           <restriction base="{http://www.w3.org/2001/XMLSchema}NCName">
 *             <enumeration value="IDCardData"/>
 *             <enumeration value="UserLog"/>
 *             <enumeration value="SystemLog"/>
 *           </restriction>
 *         </simpleType>
 *       </attribute>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attribute"
})
@XmlRootElement(name = "AttributeStatement")
public class AttributeStatement {

    @XmlElement(name = "Attribute", required = true)
    protected List<Attribute> attribute;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String id;

    /**
     * Gets the value of the attribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the attribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attribute }
     * 
     * 
     * @return
     *     The value of the attribute property.
     */
    public List<Attribute> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<>();
        }
        return this.attribute;
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
    public<_B >void copyTo(final AttributeStatement.Builder<_B> _other) {
        if (this.attribute == null) {
            _other.attribute = null;
        } else {
            _other.attribute = new ArrayList<>();
            for (Attribute _item: this.attribute) {
                _other.attribute.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
        _other.id = this.id;
    }

    public<_B >AttributeStatement.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new AttributeStatement.Builder<_B>(_parentBuilder, this, true);
    }

    public AttributeStatement.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static AttributeStatement.Builder<Void> builder() {
        return new AttributeStatement.Builder<>(null, null, false);
    }

    public static<_B >AttributeStatement.Builder<_B> copyOf(final AttributeStatement _other) {
        final AttributeStatement.Builder<_B> _newBuilder = new AttributeStatement.Builder<>(null, null, false);
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
    public<_B >void copyTo(final AttributeStatement.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree attributePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("attribute"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(attributePropertyTree!= null):((attributePropertyTree == null)||(!attributePropertyTree.isLeaf())))) {
            if (this.attribute == null) {
                _other.attribute = null;
            } else {
                _other.attribute = new ArrayList<>();
                for (Attribute _item: this.attribute) {
                    _other.attribute.add(((_item == null)?null:_item.newCopyBuilder(_other, attributePropertyTree, _propertyTreeUse)));
                }
            }
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >AttributeStatement.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new AttributeStatement.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public AttributeStatement.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >AttributeStatement.Builder<_B> copyOf(final AttributeStatement _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final AttributeStatement.Builder<_B> _newBuilder = new AttributeStatement.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static AttributeStatement.Builder<Void> copyExcept(final AttributeStatement _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static AttributeStatement.Builder<Void> copyOnly(final AttributeStatement _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final AttributeStatement _storedValue;
        private List<Attribute.Builder<AttributeStatement.Builder<_B>>> attribute;
        private String id;

        public Builder(final _B _parentBuilder, final AttributeStatement _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.attribute == null) {
                        this.attribute = null;
                    } else {
                        this.attribute = new ArrayList<>();
                        for (Attribute _item: _other.attribute) {
                            this.attribute.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                    this.id = _other.id;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final AttributeStatement _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree attributePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("attribute"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(attributePropertyTree!= null):((attributePropertyTree == null)||(!attributePropertyTree.isLeaf())))) {
                        if (_other.attribute == null) {
                            this.attribute = null;
                        } else {
                            this.attribute = new ArrayList<>();
                            for (Attribute _item: _other.attribute) {
                                this.attribute.add(((_item == null)?null:_item.newCopyBuilder(this, attributePropertyTree, _propertyTreeUse)));
                            }
                        }
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

        protected<_P extends AttributeStatement >_P init(final _P _product) {
            if (this.attribute!= null) {
                final List<Attribute> attribute = new ArrayList<>(this.attribute.size());
                for (Attribute.Builder<AttributeStatement.Builder<_B>> _item: this.attribute) {
                    attribute.add(_item.build());
                }
                _product.attribute = attribute;
            }
            _product.id = this.id;
            return _product;
        }

        /**
         * Adds the given items to the value of "attribute"
         * 
         * @param attribute
         *     Items to add to the value of the "attribute" property
         */
        public AttributeStatement.Builder<_B> addAttribute(final Iterable<? extends Attribute> attribute) {
            if (attribute!= null) {
                if (this.attribute == null) {
                    this.attribute = new ArrayList<>();
                }
                for (Attribute _item: attribute) {
                    this.attribute.add(new Attribute.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "attribute" (any previous value will be replaced)
         * 
         * @param attribute
         *     New value of the "attribute" property.
         */
        public AttributeStatement.Builder<_B> withAttribute(final Iterable<? extends Attribute> attribute) {
            if (this.attribute!= null) {
                this.attribute.clear();
            }
            return addAttribute(attribute);
        }

        /**
         * Adds the given items to the value of "attribute"
         * 
         * @param attribute
         *     Items to add to the value of the "attribute" property
         */
        public AttributeStatement.Builder<_B> addAttribute(Attribute... attribute) {
            addAttribute(Arrays.asList(attribute));
            return this;
        }

        /**
         * Sets the new value of "attribute" (any previous value will be replaced)
         * 
         * @param attribute
         *     New value of the "attribute" property.
         */
        public AttributeStatement.Builder<_B> withAttribute(Attribute... attribute) {
            withAttribute(Arrays.asList(attribute));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "Attribute" property.
         * Use {@link oasis.names.tc.saml._2_0.assertion.Attribute.Builder#end()} to return
         * to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "Attribute" property.
         *     Use {@link oasis.names.tc.saml._2_0.assertion.Attribute.Builder#end()} to return
         *     to the current builder.
         */
        public Attribute.Builder<? extends AttributeStatement.Builder<_B>> addAttribute() {
            if (this.attribute == null) {
                this.attribute = new ArrayList<>();
            }
            final Attribute.Builder<AttributeStatement.Builder<_B>> attribute_Builder = new Attribute.Builder<>(this, null, false);
            this.attribute.add(attribute_Builder);
            return attribute_Builder;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public AttributeStatement.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public AttributeStatement build() {
            if (_storedValue == null) {
                return this.init(new AttributeStatement());
            } else {
                return ((AttributeStatement) _storedValue);
            }
        }

        public AttributeStatement.Builder<_B> copyOf(final AttributeStatement _other) {
            _other.copyTo(this);
            return this;
        }

        public AttributeStatement.Builder<_B> copyOf(final AttributeStatement.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends AttributeStatement.Selector<AttributeStatement.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static AttributeStatement.Select _root() {
            return new AttributeStatement.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private Attribute.Selector<TRoot, AttributeStatement.Selector<TRoot, TParent>> attribute = null;
        private com.kscs.util.jaxb.Selector<TRoot, AttributeStatement.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.attribute!= null) {
                products.put("attribute", this.attribute.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public Attribute.Selector<TRoot, AttributeStatement.Selector<TRoot, TParent>> attribute() {
            return ((this.attribute == null)?this.attribute = new Attribute.Selector<>(this._root, this, "attribute"):this.attribute);
        }

        public com.kscs.util.jaxb.Selector<TRoot, AttributeStatement.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
