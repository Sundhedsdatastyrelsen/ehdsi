
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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for SignaturePropertiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SignaturePropertiesType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureProperty" maxOccurs="unbounded"/>
 *       </sequence>
 *       <attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignaturePropertiesType", propOrder = {
    "signatureProperty"
})
public class SignaturePropertiesType {

    @XmlElement(name = "SignatureProperty", required = true)
    protected List<SignaturePropertyType> signatureProperty;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the signatureProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the signatureProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignatureProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignaturePropertyType }
     * 
     * 
     * @return
     *     The value of the signatureProperty property.
     */
    public List<SignaturePropertyType> getSignatureProperty() {
        if (signatureProperty == null) {
            signatureProperty = new ArrayList<>();
        }
        return this.signatureProperty;
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
    public<_B >void copyTo(final SignaturePropertiesType.Builder<_B> _other) {
        if (this.signatureProperty == null) {
            _other.signatureProperty = null;
        } else {
            _other.signatureProperty = new ArrayList<>();
            for (SignaturePropertyType _item: this.signatureProperty) {
                _other.signatureProperty.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
        _other.id = this.id;
    }

    public<_B >SignaturePropertiesType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SignaturePropertiesType.Builder<_B>(_parentBuilder, this, true);
    }

    public SignaturePropertiesType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SignaturePropertiesType.Builder<Void> builder() {
        return new SignaturePropertiesType.Builder<>(null, null, false);
    }

    public static<_B >SignaturePropertiesType.Builder<_B> copyOf(final SignaturePropertiesType _other) {
        final SignaturePropertiesType.Builder<_B> _newBuilder = new SignaturePropertiesType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SignaturePropertiesType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree signaturePropertyPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureProperty"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signaturePropertyPropertyTree!= null):((signaturePropertyPropertyTree == null)||(!signaturePropertyPropertyTree.isLeaf())))) {
            if (this.signatureProperty == null) {
                _other.signatureProperty = null;
            } else {
                _other.signatureProperty = new ArrayList<>();
                for (SignaturePropertyType _item: this.signatureProperty) {
                    _other.signatureProperty.add(((_item == null)?null:_item.newCopyBuilder(_other, signaturePropertyPropertyTree, _propertyTreeUse)));
                }
            }
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >SignaturePropertiesType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SignaturePropertiesType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SignaturePropertiesType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SignaturePropertiesType.Builder<_B> copyOf(final SignaturePropertiesType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SignaturePropertiesType.Builder<_B> _newBuilder = new SignaturePropertiesType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SignaturePropertiesType.Builder<Void> copyExcept(final SignaturePropertiesType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SignaturePropertiesType.Builder<Void> copyOnly(final SignaturePropertiesType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SignaturePropertiesType _storedValue;
        private List<SignaturePropertyType.Builder<SignaturePropertiesType.Builder<_B>>> signatureProperty;
        private String id;

        public Builder(final _B _parentBuilder, final SignaturePropertiesType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.signatureProperty == null) {
                        this.signatureProperty = null;
                    } else {
                        this.signatureProperty = new ArrayList<>();
                        for (SignaturePropertyType _item: _other.signatureProperty) {
                            this.signatureProperty.add(((_item == null)?null:_item.newCopyBuilder(this)));
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

        public Builder(final _B _parentBuilder, final SignaturePropertiesType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree signaturePropertyPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureProperty"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signaturePropertyPropertyTree!= null):((signaturePropertyPropertyTree == null)||(!signaturePropertyPropertyTree.isLeaf())))) {
                        if (_other.signatureProperty == null) {
                            this.signatureProperty = null;
                        } else {
                            this.signatureProperty = new ArrayList<>();
                            for (SignaturePropertyType _item: _other.signatureProperty) {
                                this.signatureProperty.add(((_item == null)?null:_item.newCopyBuilder(this, signaturePropertyPropertyTree, _propertyTreeUse)));
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

        protected<_P extends SignaturePropertiesType >_P init(final _P _product) {
            if (this.signatureProperty!= null) {
                final List<SignaturePropertyType> signatureProperty = new ArrayList<>(this.signatureProperty.size());
                for (SignaturePropertyType.Builder<SignaturePropertiesType.Builder<_B>> _item: this.signatureProperty) {
                    signatureProperty.add(_item.build());
                }
                _product.signatureProperty = signatureProperty;
            }
            _product.id = this.id;
            return _product;
        }

        /**
         * Adds the given items to the value of "signatureProperty"
         * 
         * @param signatureProperty
         *     Items to add to the value of the "signatureProperty" property
         */
        public SignaturePropertiesType.Builder<_B> addSignatureProperty(final Iterable<? extends SignaturePropertyType> signatureProperty) {
            if (signatureProperty!= null) {
                if (this.signatureProperty == null) {
                    this.signatureProperty = new ArrayList<>();
                }
                for (SignaturePropertyType _item: signatureProperty) {
                    this.signatureProperty.add(new SignaturePropertyType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "signatureProperty" (any previous value will be replaced)
         * 
         * @param signatureProperty
         *     New value of the "signatureProperty" property.
         */
        public SignaturePropertiesType.Builder<_B> withSignatureProperty(final Iterable<? extends SignaturePropertyType> signatureProperty) {
            if (this.signatureProperty!= null) {
                this.signatureProperty.clear();
            }
            return addSignatureProperty(signatureProperty);
        }

        /**
         * Adds the given items to the value of "signatureProperty"
         * 
         * @param signatureProperty
         *     Items to add to the value of the "signatureProperty" property
         */
        public SignaturePropertiesType.Builder<_B> addSignatureProperty(SignaturePropertyType... signatureProperty) {
            addSignatureProperty(Arrays.asList(signatureProperty));
            return this;
        }

        /**
         * Sets the new value of "signatureProperty" (any previous value will be replaced)
         * 
         * @param signatureProperty
         *     New value of the "signatureProperty" property.
         */
        public SignaturePropertiesType.Builder<_B> withSignatureProperty(SignaturePropertyType... signatureProperty) {
            withSignatureProperty(Arrays.asList(signatureProperty));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "SignatureProperty"
         * property.
         * Use {@link org.w3._2000._09.xmldsig_.SignaturePropertyType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "SignatureProperty" property.
         *     Use {@link org.w3._2000._09.xmldsig_.SignaturePropertyType.Builder#end()} to
         *     return to the current builder.
         */
        public SignaturePropertyType.Builder<? extends SignaturePropertiesType.Builder<_B>> addSignatureProperty() {
            if (this.signatureProperty == null) {
                this.signatureProperty = new ArrayList<>();
            }
            final SignaturePropertyType.Builder<SignaturePropertiesType.Builder<_B>> signatureProperty_Builder = new SignaturePropertyType.Builder<>(this, null, false);
            this.signatureProperty.add(signatureProperty_Builder);
            return signatureProperty_Builder;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public SignaturePropertiesType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public SignaturePropertiesType build() {
            if (_storedValue == null) {
                return this.init(new SignaturePropertiesType());
            } else {
                return ((SignaturePropertiesType) _storedValue);
            }
        }

        public SignaturePropertiesType.Builder<_B> copyOf(final SignaturePropertiesType _other) {
            _other.copyTo(this);
            return this;
        }

        public SignaturePropertiesType.Builder<_B> copyOf(final SignaturePropertiesType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SignaturePropertiesType.Selector<SignaturePropertiesType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SignaturePropertiesType.Select _root() {
            return new SignaturePropertiesType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private SignaturePropertyType.Selector<TRoot, SignaturePropertiesType.Selector<TRoot, TParent>> signatureProperty = null;
        private com.kscs.util.jaxb.Selector<TRoot, SignaturePropertiesType.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.signatureProperty!= null) {
                products.put("signatureProperty", this.signatureProperty.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public SignaturePropertyType.Selector<TRoot, SignaturePropertiesType.Selector<TRoot, TParent>> signatureProperty() {
            return ((this.signatureProperty == null)?this.signatureProperty = new SignaturePropertyType.Selector<>(this._root, this, "signatureProperty"):this.signatureProperty);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignaturePropertiesType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
