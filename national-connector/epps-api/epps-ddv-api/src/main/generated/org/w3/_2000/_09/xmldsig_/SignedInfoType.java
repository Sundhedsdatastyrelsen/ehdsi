
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
 * <p>Java class for SignedInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SignedInfoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}CanonicalizationMethod"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureMethod"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Reference" maxOccurs="unbounded"/>
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
@XmlType(name = "SignedInfoType", propOrder = {
    "canonicalizationMethod",
    "signatureMethod",
    "reference"
})
public class SignedInfoType {

    @XmlElement(name = "CanonicalizationMethod", required = true)
    protected CanonicalizationMethodType canonicalizationMethod;
    @XmlElement(name = "SignatureMethod", required = true)
    protected SignatureMethodType signatureMethod;
    @XmlElement(name = "Reference", required = true)
    protected List<ReferenceType> reference;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the canonicalizationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link CanonicalizationMethodType }
     *     
     */
    public CanonicalizationMethodType getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    /**
     * Sets the value of the canonicalizationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanonicalizationMethodType }
     *     
     */
    public void setCanonicalizationMethod(CanonicalizationMethodType value) {
        this.canonicalizationMethod = value;
    }

    /**
     * Gets the value of the signatureMethod property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureMethodType }
     *     
     */
    public SignatureMethodType getSignatureMethod() {
        return signatureMethod;
    }

    /**
     * Sets the value of the signatureMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureMethodType }
     *     
     */
    public void setSignatureMethod(SignatureMethodType value) {
        this.signatureMethod = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the reference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceType }
     * 
     * 
     * @return
     *     The value of the reference property.
     */
    public List<ReferenceType> getReference() {
        if (reference == null) {
            reference = new ArrayList<>();
        }
        return this.reference;
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
    public<_B >void copyTo(final SignedInfoType.Builder<_B> _other) {
        _other.canonicalizationMethod = ((this.canonicalizationMethod == null)?null:this.canonicalizationMethod.newCopyBuilder(_other));
        _other.signatureMethod = ((this.signatureMethod == null)?null:this.signatureMethod.newCopyBuilder(_other));
        if (this.reference == null) {
            _other.reference = null;
        } else {
            _other.reference = new ArrayList<>();
            for (ReferenceType _item: this.reference) {
                _other.reference.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
        _other.id = this.id;
    }

    public<_B >SignedInfoType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SignedInfoType.Builder<_B>(_parentBuilder, this, true);
    }

    public SignedInfoType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SignedInfoType.Builder<Void> builder() {
        return new SignedInfoType.Builder<>(null, null, false);
    }

    public static<_B >SignedInfoType.Builder<_B> copyOf(final SignedInfoType _other) {
        final SignedInfoType.Builder<_B> _newBuilder = new SignedInfoType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SignedInfoType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree canonicalizationMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("canonicalizationMethod"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(canonicalizationMethodPropertyTree!= null):((canonicalizationMethodPropertyTree == null)||(!canonicalizationMethodPropertyTree.isLeaf())))) {
            _other.canonicalizationMethod = ((this.canonicalizationMethod == null)?null:this.canonicalizationMethod.newCopyBuilder(_other, canonicalizationMethodPropertyTree, _propertyTreeUse));
        }
        final PropertyTree signatureMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureMethod"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signatureMethodPropertyTree!= null):((signatureMethodPropertyTree == null)||(!signatureMethodPropertyTree.isLeaf())))) {
            _other.signatureMethod = ((this.signatureMethod == null)?null:this.signatureMethod.newCopyBuilder(_other, signatureMethodPropertyTree, _propertyTreeUse));
        }
        final PropertyTree referencePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reference"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(referencePropertyTree!= null):((referencePropertyTree == null)||(!referencePropertyTree.isLeaf())))) {
            if (this.reference == null) {
                _other.reference = null;
            } else {
                _other.reference = new ArrayList<>();
                for (ReferenceType _item: this.reference) {
                    _other.reference.add(((_item == null)?null:_item.newCopyBuilder(_other, referencePropertyTree, _propertyTreeUse)));
                }
            }
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >SignedInfoType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SignedInfoType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SignedInfoType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SignedInfoType.Builder<_B> copyOf(final SignedInfoType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SignedInfoType.Builder<_B> _newBuilder = new SignedInfoType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SignedInfoType.Builder<Void> copyExcept(final SignedInfoType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SignedInfoType.Builder<Void> copyOnly(final SignedInfoType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SignedInfoType _storedValue;
        private CanonicalizationMethodType.Builder<SignedInfoType.Builder<_B>> canonicalizationMethod;
        private SignatureMethodType.Builder<SignedInfoType.Builder<_B>> signatureMethod;
        private List<ReferenceType.Builder<SignedInfoType.Builder<_B>>> reference;
        private String id;

        public Builder(final _B _parentBuilder, final SignedInfoType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.canonicalizationMethod = ((_other.canonicalizationMethod == null)?null:_other.canonicalizationMethod.newCopyBuilder(this));
                    this.signatureMethod = ((_other.signatureMethod == null)?null:_other.signatureMethod.newCopyBuilder(this));
                    if (_other.reference == null) {
                        this.reference = null;
                    } else {
                        this.reference = new ArrayList<>();
                        for (ReferenceType _item: _other.reference) {
                            this.reference.add(((_item == null)?null:_item.newCopyBuilder(this)));
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

        public Builder(final _B _parentBuilder, final SignedInfoType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree canonicalizationMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("canonicalizationMethod"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(canonicalizationMethodPropertyTree!= null):((canonicalizationMethodPropertyTree == null)||(!canonicalizationMethodPropertyTree.isLeaf())))) {
                        this.canonicalizationMethod = ((_other.canonicalizationMethod == null)?null:_other.canonicalizationMethod.newCopyBuilder(this, canonicalizationMethodPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree signatureMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureMethod"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signatureMethodPropertyTree!= null):((signatureMethodPropertyTree == null)||(!signatureMethodPropertyTree.isLeaf())))) {
                        this.signatureMethod = ((_other.signatureMethod == null)?null:_other.signatureMethod.newCopyBuilder(this, signatureMethodPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree referencePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reference"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(referencePropertyTree!= null):((referencePropertyTree == null)||(!referencePropertyTree.isLeaf())))) {
                        if (_other.reference == null) {
                            this.reference = null;
                        } else {
                            this.reference = new ArrayList<>();
                            for (ReferenceType _item: _other.reference) {
                                this.reference.add(((_item == null)?null:_item.newCopyBuilder(this, referencePropertyTree, _propertyTreeUse)));
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

        protected<_P extends SignedInfoType >_P init(final _P _product) {
            _product.canonicalizationMethod = ((this.canonicalizationMethod == null)?null:this.canonicalizationMethod.build());
            _product.signatureMethod = ((this.signatureMethod == null)?null:this.signatureMethod.build());
            if (this.reference!= null) {
                final List<ReferenceType> reference = new ArrayList<>(this.reference.size());
                for (ReferenceType.Builder<SignedInfoType.Builder<_B>> _item: this.reference) {
                    reference.add(_item.build());
                }
                _product.reference = reference;
            }
            _product.id = this.id;
            return _product;
        }

        /**
         * Sets the new value of "canonicalizationMethod" (any previous value will be
         * replaced)
         * 
         * @param canonicalizationMethod
         *     New value of the "canonicalizationMethod" property.
         */
        public SignedInfoType.Builder<_B> withCanonicalizationMethod(final CanonicalizationMethodType canonicalizationMethod) {
            this.canonicalizationMethod = ((canonicalizationMethod == null)?null:new CanonicalizationMethodType.Builder<>(this, canonicalizationMethod, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "canonicalizationMethod" property.
         * Use {@link org.w3._2000._09.xmldsig_.CanonicalizationMethodType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "canonicalizationMethod" property.
         *     Use {@link org.w3._2000._09.xmldsig_.CanonicalizationMethodType.Builder#end()}
         *     to return to the current builder.
         */
        public CanonicalizationMethodType.Builder<? extends SignedInfoType.Builder<_B>> withCanonicalizationMethod() {
            if (this.canonicalizationMethod!= null) {
                return this.canonicalizationMethod;
            }
            return this.canonicalizationMethod = new CanonicalizationMethodType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "signatureMethod" (any previous value will be replaced)
         * 
         * @param signatureMethod
         *     New value of the "signatureMethod" property.
         */
        public SignedInfoType.Builder<_B> withSignatureMethod(final SignatureMethodType signatureMethod) {
            this.signatureMethod = ((signatureMethod == null)?null:new SignatureMethodType.Builder<>(this, signatureMethod, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "signatureMethod" property.
         * Use {@link org.w3._2000._09.xmldsig_.SignatureMethodType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "signatureMethod" property.
         *     Use {@link org.w3._2000._09.xmldsig_.SignatureMethodType.Builder#end()} to
         *     return to the current builder.
         */
        public SignatureMethodType.Builder<? extends SignedInfoType.Builder<_B>> withSignatureMethod() {
            if (this.signatureMethod!= null) {
                return this.signatureMethod;
            }
            return this.signatureMethod = new SignatureMethodType.Builder<>(this, null, false);
        }

        /**
         * Adds the given items to the value of "reference"
         * 
         * @param reference
         *     Items to add to the value of the "reference" property
         */
        public SignedInfoType.Builder<_B> addReference(final Iterable<? extends ReferenceType> reference) {
            if (reference!= null) {
                if (this.reference == null) {
                    this.reference = new ArrayList<>();
                }
                for (ReferenceType _item: reference) {
                    this.reference.add(new ReferenceType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "reference" (any previous value will be replaced)
         * 
         * @param reference
         *     New value of the "reference" property.
         */
        public SignedInfoType.Builder<_B> withReference(final Iterable<? extends ReferenceType> reference) {
            if (this.reference!= null) {
                this.reference.clear();
            }
            return addReference(reference);
        }

        /**
         * Adds the given items to the value of "reference"
         * 
         * @param reference
         *     Items to add to the value of the "reference" property
         */
        public SignedInfoType.Builder<_B> addReference(ReferenceType... reference) {
            addReference(Arrays.asList(reference));
            return this;
        }

        /**
         * Sets the new value of "reference" (any previous value will be replaced)
         * 
         * @param reference
         *     New value of the "reference" property.
         */
        public SignedInfoType.Builder<_B> withReference(ReferenceType... reference) {
            withReference(Arrays.asList(reference));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "Reference" property.
         * Use {@link org.w3._2000._09.xmldsig_.ReferenceType.Builder#end()} to return to
         * the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "Reference" property.
         *     Use {@link org.w3._2000._09.xmldsig_.ReferenceType.Builder#end()} to return to
         *     the current builder.
         */
        public ReferenceType.Builder<? extends SignedInfoType.Builder<_B>> addReference() {
            if (this.reference == null) {
                this.reference = new ArrayList<>();
            }
            final ReferenceType.Builder<SignedInfoType.Builder<_B>> reference_Builder = new ReferenceType.Builder<>(this, null, false);
            this.reference.add(reference_Builder);
            return reference_Builder;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public SignedInfoType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public SignedInfoType build() {
            if (_storedValue == null) {
                return this.init(new SignedInfoType());
            } else {
                return ((SignedInfoType) _storedValue);
            }
        }

        public SignedInfoType.Builder<_B> copyOf(final SignedInfoType _other) {
            _other.copyTo(this);
            return this;
        }

        public SignedInfoType.Builder<_B> copyOf(final SignedInfoType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SignedInfoType.Selector<SignedInfoType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SignedInfoType.Select _root() {
            return new SignedInfoType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private CanonicalizationMethodType.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> canonicalizationMethod = null;
        private SignatureMethodType.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> signatureMethod = null;
        private ReferenceType.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> reference = null;
        private com.kscs.util.jaxb.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.canonicalizationMethod!= null) {
                products.put("canonicalizationMethod", this.canonicalizationMethod.init());
            }
            if (this.signatureMethod!= null) {
                products.put("signatureMethod", this.signatureMethod.init());
            }
            if (this.reference!= null) {
                products.put("reference", this.reference.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public CanonicalizationMethodType.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> canonicalizationMethod() {
            return ((this.canonicalizationMethod == null)?this.canonicalizationMethod = new CanonicalizationMethodType.Selector<>(this._root, this, "canonicalizationMethod"):this.canonicalizationMethod);
        }

        public SignatureMethodType.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> signatureMethod() {
            return ((this.signatureMethod == null)?this.signatureMethod = new SignatureMethodType.Selector<>(this._root, this, "signatureMethod"):this.signatureMethod);
        }

        public ReferenceType.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> reference() {
            return ((this.reference == null)?this.reference = new ReferenceType.Selector<>(this._root, this, "reference"):this.reference);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignedInfoType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
