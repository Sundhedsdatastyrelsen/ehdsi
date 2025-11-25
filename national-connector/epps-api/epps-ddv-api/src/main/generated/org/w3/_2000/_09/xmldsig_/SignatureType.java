
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
 * <p>Java class for SignatureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SignatureType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}SignedInfo"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureValue"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}KeyInfo" minOccurs="0"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Object" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "SignatureType", propOrder = {
    "signedInfo",
    "signatureValue",
    "keyInfo",
    "object"
})
public class SignatureType {

    @XmlElement(name = "SignedInfo", required = true)
    protected SignedInfoType signedInfo;
    @XmlElement(name = "SignatureValue", required = true)
    protected SignatureValueType signatureValue;
    @XmlElement(name = "KeyInfo")
    protected KeyInfoType keyInfo;
    @XmlElement(name = "Object")
    protected List<ObjectType> object;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the signedInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SignedInfoType }
     *     
     */
    public SignedInfoType getSignedInfo() {
        return signedInfo;
    }

    /**
     * Sets the value of the signedInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignedInfoType }
     *     
     */
    public void setSignedInfo(SignedInfoType value) {
        this.signedInfo = value;
    }

    /**
     * Gets the value of the signatureValue property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureValueType }
     *     
     */
    public SignatureValueType getSignatureValue() {
        return signatureValue;
    }

    /**
     * Sets the value of the signatureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureValueType }
     *     
     */
    public void setSignatureValue(SignatureValueType value) {
        this.signatureValue = value;
    }

    /**
     * Gets the value of the keyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link KeyInfoType }
     *     
     */
    public KeyInfoType getKeyInfo() {
        return keyInfo;
    }

    /**
     * Sets the value of the keyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyInfoType }
     *     
     */
    public void setKeyInfo(KeyInfoType value) {
        this.keyInfo = value;
    }

    /**
     * Gets the value of the object property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the object property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectType }
     * 
     * 
     * @return
     *     The value of the object property.
     */
    public List<ObjectType> getObject() {
        if (object == null) {
            object = new ArrayList<>();
        }
        return this.object;
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
    public<_B >void copyTo(final SignatureType.Builder<_B> _other) {
        _other.signedInfo = ((this.signedInfo == null)?null:this.signedInfo.newCopyBuilder(_other));
        _other.signatureValue = ((this.signatureValue == null)?null:this.signatureValue.newCopyBuilder(_other));
        _other.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.newCopyBuilder(_other));
        if (this.object == null) {
            _other.object = null;
        } else {
            _other.object = new ArrayList<>();
            for (ObjectType _item: this.object) {
                _other.object.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
        _other.id = this.id;
    }

    public<_B >SignatureType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SignatureType.Builder<_B>(_parentBuilder, this, true);
    }

    public SignatureType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SignatureType.Builder<Void> builder() {
        return new SignatureType.Builder<>(null, null, false);
    }

    public static<_B >SignatureType.Builder<_B> copyOf(final SignatureType _other) {
        final SignatureType.Builder<_B> _newBuilder = new SignatureType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SignatureType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree signedInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signedInfo"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signedInfoPropertyTree!= null):((signedInfoPropertyTree == null)||(!signedInfoPropertyTree.isLeaf())))) {
            _other.signedInfo = ((this.signedInfo == null)?null:this.signedInfo.newCopyBuilder(_other, signedInfoPropertyTree, _propertyTreeUse));
        }
        final PropertyTree signatureValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureValue"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signatureValuePropertyTree!= null):((signatureValuePropertyTree == null)||(!signatureValuePropertyTree.isLeaf())))) {
            _other.signatureValue = ((this.signatureValue == null)?null:this.signatureValue.newCopyBuilder(_other, signatureValuePropertyTree, _propertyTreeUse));
        }
        final PropertyTree keyInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyInfo"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyInfoPropertyTree!= null):((keyInfoPropertyTree == null)||(!keyInfoPropertyTree.isLeaf())))) {
            _other.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.newCopyBuilder(_other, keyInfoPropertyTree, _propertyTreeUse));
        }
        final PropertyTree objectPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("object"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(objectPropertyTree!= null):((objectPropertyTree == null)||(!objectPropertyTree.isLeaf())))) {
            if (this.object == null) {
                _other.object = null;
            } else {
                _other.object = new ArrayList<>();
                for (ObjectType _item: this.object) {
                    _other.object.add(((_item == null)?null:_item.newCopyBuilder(_other, objectPropertyTree, _propertyTreeUse)));
                }
            }
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >SignatureType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SignatureType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SignatureType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SignatureType.Builder<_B> copyOf(final SignatureType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SignatureType.Builder<_B> _newBuilder = new SignatureType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SignatureType.Builder<Void> copyExcept(final SignatureType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SignatureType.Builder<Void> copyOnly(final SignatureType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SignatureType _storedValue;
        private SignedInfoType.Builder<SignatureType.Builder<_B>> signedInfo;
        private SignatureValueType.Builder<SignatureType.Builder<_B>> signatureValue;
        private KeyInfoType.Builder<SignatureType.Builder<_B>> keyInfo;
        private List<ObjectType.Builder<SignatureType.Builder<_B>>> object;
        private String id;

        public Builder(final _B _parentBuilder, final SignatureType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.signedInfo = ((_other.signedInfo == null)?null:_other.signedInfo.newCopyBuilder(this));
                    this.signatureValue = ((_other.signatureValue == null)?null:_other.signatureValue.newCopyBuilder(this));
                    this.keyInfo = ((_other.keyInfo == null)?null:_other.keyInfo.newCopyBuilder(this));
                    if (_other.object == null) {
                        this.object = null;
                    } else {
                        this.object = new ArrayList<>();
                        for (ObjectType _item: _other.object) {
                            this.object.add(((_item == null)?null:_item.newCopyBuilder(this)));
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

        public Builder(final _B _parentBuilder, final SignatureType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree signedInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signedInfo"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signedInfoPropertyTree!= null):((signedInfoPropertyTree == null)||(!signedInfoPropertyTree.isLeaf())))) {
                        this.signedInfo = ((_other.signedInfo == null)?null:_other.signedInfo.newCopyBuilder(this, signedInfoPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree signatureValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureValue"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signatureValuePropertyTree!= null):((signatureValuePropertyTree == null)||(!signatureValuePropertyTree.isLeaf())))) {
                        this.signatureValue = ((_other.signatureValue == null)?null:_other.signatureValue.newCopyBuilder(this, signatureValuePropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree keyInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyInfo"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyInfoPropertyTree!= null):((keyInfoPropertyTree == null)||(!keyInfoPropertyTree.isLeaf())))) {
                        this.keyInfo = ((_other.keyInfo == null)?null:_other.keyInfo.newCopyBuilder(this, keyInfoPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree objectPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("object"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(objectPropertyTree!= null):((objectPropertyTree == null)||(!objectPropertyTree.isLeaf())))) {
                        if (_other.object == null) {
                            this.object = null;
                        } else {
                            this.object = new ArrayList<>();
                            for (ObjectType _item: _other.object) {
                                this.object.add(((_item == null)?null:_item.newCopyBuilder(this, objectPropertyTree, _propertyTreeUse)));
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

        protected<_P extends SignatureType >_P init(final _P _product) {
            _product.signedInfo = ((this.signedInfo == null)?null:this.signedInfo.build());
            _product.signatureValue = ((this.signatureValue == null)?null:this.signatureValue.build());
            _product.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.build());
            if (this.object!= null) {
                final List<ObjectType> object = new ArrayList<>(this.object.size());
                for (ObjectType.Builder<SignatureType.Builder<_B>> _item: this.object) {
                    object.add(_item.build());
                }
                _product.object = object;
            }
            _product.id = this.id;
            return _product;
        }

        /**
         * Sets the new value of "signedInfo" (any previous value will be replaced)
         * 
         * @param signedInfo
         *     New value of the "signedInfo" property.
         */
        public SignatureType.Builder<_B> withSignedInfo(final SignedInfoType signedInfo) {
            this.signedInfo = ((signedInfo == null)?null:new SignedInfoType.Builder<>(this, signedInfo, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "signedInfo" property.
         * Use {@link org.w3._2000._09.xmldsig_.SignedInfoType.Builder#end()} to return to
         * the current builder.
         * 
         * @return
         *     A new builder to build the value of the "signedInfo" property.
         *     Use {@link org.w3._2000._09.xmldsig_.SignedInfoType.Builder#end()} to return to
         *     the current builder.
         */
        public SignedInfoType.Builder<? extends SignatureType.Builder<_B>> withSignedInfo() {
            if (this.signedInfo!= null) {
                return this.signedInfo;
            }
            return this.signedInfo = new SignedInfoType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "signatureValue" (any previous value will be replaced)
         * 
         * @param signatureValue
         *     New value of the "signatureValue" property.
         */
        public SignatureType.Builder<_B> withSignatureValue(final SignatureValueType signatureValue) {
            this.signatureValue = ((signatureValue == null)?null:new SignatureValueType.Builder<>(this, signatureValue, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "signatureValue" property.
         * Use {@link org.w3._2000._09.xmldsig_.SignatureValueType.Builder#end()} to return
         * to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "signatureValue" property.
         *     Use {@link org.w3._2000._09.xmldsig_.SignatureValueType.Builder#end()} to return
         *     to the current builder.
         */
        public SignatureValueType.Builder<? extends SignatureType.Builder<_B>> withSignatureValue() {
            if (this.signatureValue!= null) {
                return this.signatureValue;
            }
            return this.signatureValue = new SignatureValueType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "keyInfo" (any previous value will be replaced)
         * 
         * @param keyInfo
         *     New value of the "keyInfo" property.
         */
        public SignatureType.Builder<_B> withKeyInfo(final KeyInfoType keyInfo) {
            this.keyInfo = ((keyInfo == null)?null:new KeyInfoType.Builder<>(this, keyInfo, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "keyInfo" property.
         * Use {@link org.w3._2000._09.xmldsig_.KeyInfoType.Builder#end()} to return to the
         * current builder.
         * 
         * @return
         *     A new builder to build the value of the "keyInfo" property.
         *     Use {@link org.w3._2000._09.xmldsig_.KeyInfoType.Builder#end()} to return to the
         *     current builder.
         */
        public KeyInfoType.Builder<? extends SignatureType.Builder<_B>> withKeyInfo() {
            if (this.keyInfo!= null) {
                return this.keyInfo;
            }
            return this.keyInfo = new KeyInfoType.Builder<>(this, null, false);
        }

        /**
         * Adds the given items to the value of "object"
         * 
         * @param object
         *     Items to add to the value of the "object" property
         */
        public SignatureType.Builder<_B> addObject(final Iterable<? extends ObjectType> object) {
            if (object!= null) {
                if (this.object == null) {
                    this.object = new ArrayList<>();
                }
                for (ObjectType _item: object) {
                    this.object.add(new ObjectType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "object" (any previous value will be replaced)
         * 
         * @param object
         *     New value of the "object" property.
         */
        public SignatureType.Builder<_B> withObject(final Iterable<? extends ObjectType> object) {
            if (this.object!= null) {
                this.object.clear();
            }
            return addObject(object);
        }

        /**
         * Adds the given items to the value of "object"
         * 
         * @param object
         *     Items to add to the value of the "object" property
         */
        public SignatureType.Builder<_B> addObject(ObjectType... object) {
            addObject(Arrays.asList(object));
            return this;
        }

        /**
         * Sets the new value of "object" (any previous value will be replaced)
         * 
         * @param object
         *     New value of the "object" property.
         */
        public SignatureType.Builder<_B> withObject(ObjectType... object) {
            withObject(Arrays.asList(object));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "Object" property.
         * Use {@link org.w3._2000._09.xmldsig_.ObjectType.Builder#end()} to return to the
         * current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "Object" property.
         *     Use {@link org.w3._2000._09.xmldsig_.ObjectType.Builder#end()} to return to the
         *     current builder.
         */
        public ObjectType.Builder<? extends SignatureType.Builder<_B>> addObject() {
            if (this.object == null) {
                this.object = new ArrayList<>();
            }
            final ObjectType.Builder<SignatureType.Builder<_B>> object_Builder = new ObjectType.Builder<>(this, null, false);
            this.object.add(object_Builder);
            return object_Builder;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public SignatureType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public SignatureType build() {
            if (_storedValue == null) {
                return this.init(new SignatureType());
            } else {
                return ((SignatureType) _storedValue);
            }
        }

        public SignatureType.Builder<_B> copyOf(final SignatureType _other) {
            _other.copyTo(this);
            return this;
        }

        public SignatureType.Builder<_B> copyOf(final SignatureType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SignatureType.Selector<SignatureType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SignatureType.Select _root() {
            return new SignatureType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private SignedInfoType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> signedInfo = null;
        private SignatureValueType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> signatureValue = null;
        private KeyInfoType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> keyInfo = null;
        private ObjectType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> object = null;
        private com.kscs.util.jaxb.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.signedInfo!= null) {
                products.put("signedInfo", this.signedInfo.init());
            }
            if (this.signatureValue!= null) {
                products.put("signatureValue", this.signatureValue.init());
            }
            if (this.keyInfo!= null) {
                products.put("keyInfo", this.keyInfo.init());
            }
            if (this.object!= null) {
                products.put("object", this.object.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public SignedInfoType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> signedInfo() {
            return ((this.signedInfo == null)?this.signedInfo = new SignedInfoType.Selector<>(this._root, this, "signedInfo"):this.signedInfo);
        }

        public SignatureValueType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> signatureValue() {
            return ((this.signatureValue == null)?this.signatureValue = new SignatureValueType.Selector<>(this._root, this, "signatureValue"):this.signatureValue);
        }

        public KeyInfoType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> keyInfo() {
            return ((this.keyInfo == null)?this.keyInfo = new KeyInfoType.Selector<>(this._root, this, "keyInfo"):this.keyInfo);
        }

        public ObjectType.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> object() {
            return ((this.object == null)?this.object = new ObjectType.Selector<>(this._root, this, "object"):this.object);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignatureType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
