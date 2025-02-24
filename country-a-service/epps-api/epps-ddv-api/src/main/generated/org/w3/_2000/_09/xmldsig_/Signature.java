
package org.w3._2000._09.xmldsig_;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
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
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}SignedInfo"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureValue"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}KeyInfo"/>
 *       </sequence>
 *       <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "signedInfo",
    "signatureValue",
    "keyInfo"
})
@XmlRootElement(name = "Signature")
public class Signature {

    @XmlElement(name = "SignedInfo", required = true)
    protected SignedInfo signedInfo;
    @XmlElement(name = "SignatureValue", required = true)
    protected byte[] signatureValue;
    @XmlElement(name = "KeyInfo", required = true)
    protected KeyInfo keyInfo;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String id;

    /**
     * Gets the value of the signedInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SignedInfo }
     *     
     */
    public SignedInfo getSignedInfo() {
        return signedInfo;
    }

    /**
     * Sets the value of the signedInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignedInfo }
     *     
     */
    public void setSignedInfo(SignedInfo value) {
        this.signedInfo = value;
    }

    /**
     * Gets the value of the signatureValue property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignatureValue() {
        return signatureValue;
    }

    /**
     * Sets the value of the signatureValue property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignatureValue(byte[] value) {
        this.signatureValue = value;
    }

    /**
     * Gets the value of the keyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link KeyInfo }
     *     
     */
    public KeyInfo getKeyInfo() {
        return keyInfo;
    }

    /**
     * Sets the value of the keyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyInfo }
     *     
     */
    public void setKeyInfo(KeyInfo value) {
        this.keyInfo = value;
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
    public<_B >void copyTo(final Signature.Builder<_B> _other) {
        _other.signedInfo = ((this.signedInfo == null)?null:this.signedInfo.newCopyBuilder(_other));
        _other.signatureValue = this.signatureValue;
        _other.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.newCopyBuilder(_other));
        _other.id = this.id;
    }

    public<_B >Signature.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new Signature.Builder<_B>(_parentBuilder, this, true);
    }

    public Signature.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static Signature.Builder<Void> builder() {
        return new Signature.Builder<>(null, null, false);
    }

    public static<_B >Signature.Builder<_B> copyOf(final Signature _other) {
        final Signature.Builder<_B> _newBuilder = new Signature.Builder<>(null, null, false);
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
    public<_B >void copyTo(final Signature.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree signedInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signedInfo"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signedInfoPropertyTree!= null):((signedInfoPropertyTree == null)||(!signedInfoPropertyTree.isLeaf())))) {
            _other.signedInfo = ((this.signedInfo == null)?null:this.signedInfo.newCopyBuilder(_other, signedInfoPropertyTree, _propertyTreeUse));
        }
        final PropertyTree signatureValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureValue"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signatureValuePropertyTree!= null):((signatureValuePropertyTree == null)||(!signatureValuePropertyTree.isLeaf())))) {
            _other.signatureValue = this.signatureValue;
        }
        final PropertyTree keyInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyInfo"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyInfoPropertyTree!= null):((keyInfoPropertyTree == null)||(!keyInfoPropertyTree.isLeaf())))) {
            _other.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.newCopyBuilder(_other, keyInfoPropertyTree, _propertyTreeUse));
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >Signature.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new Signature.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public Signature.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >Signature.Builder<_B> copyOf(final Signature _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final Signature.Builder<_B> _newBuilder = new Signature.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static Signature.Builder<Void> copyExcept(final Signature _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static Signature.Builder<Void> copyOnly(final Signature _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final Signature _storedValue;
        private SignedInfo.Builder<Signature.Builder<_B>> signedInfo;
        private byte[] signatureValue;
        private KeyInfo.Builder<Signature.Builder<_B>> keyInfo;
        private String id;

        public Builder(final _B _parentBuilder, final Signature _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.signedInfo = ((_other.signedInfo == null)?null:_other.signedInfo.newCopyBuilder(this));
                    this.signatureValue = _other.signatureValue;
                    this.keyInfo = ((_other.keyInfo == null)?null:_other.keyInfo.newCopyBuilder(this));
                    this.id = _other.id;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final Signature _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
                        this.signatureValue = _other.signatureValue;
                    }
                    final PropertyTree keyInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyInfo"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyInfoPropertyTree!= null):((keyInfoPropertyTree == null)||(!keyInfoPropertyTree.isLeaf())))) {
                        this.keyInfo = ((_other.keyInfo == null)?null:_other.keyInfo.newCopyBuilder(this, keyInfoPropertyTree, _propertyTreeUse));
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

        protected<_P extends Signature >_P init(final _P _product) {
            _product.signedInfo = ((this.signedInfo == null)?null:this.signedInfo.build());
            _product.signatureValue = this.signatureValue;
            _product.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.build());
            _product.id = this.id;
            return _product;
        }

        /**
         * Sets the new value of "signedInfo" (any previous value will be replaced)
         * 
         * @param signedInfo
         *     New value of the "signedInfo" property.
         */
        public Signature.Builder<_B> withSignedInfo(final SignedInfo signedInfo) {
            this.signedInfo = ((signedInfo == null)?null:new SignedInfo.Builder<>(this, signedInfo, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "signedInfo" property.
         * Use {@link org.w3._2000._09.xmldsig_.SignedInfo.Builder#end()} to return to the
         * current builder.
         * 
         * @return
         *     A new builder to build the value of the "signedInfo" property.
         *     Use {@link org.w3._2000._09.xmldsig_.SignedInfo.Builder#end()} to return to the
         *     current builder.
         */
        public SignedInfo.Builder<? extends Signature.Builder<_B>> withSignedInfo() {
            if (this.signedInfo!= null) {
                return this.signedInfo;
            }
            return this.signedInfo = new SignedInfo.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "signatureValue" (any previous value will be replaced)
         * 
         * @param signatureValue
         *     New value of the "signatureValue" property.
         */
        public Signature.Builder<_B> withSignatureValue(final byte[] signatureValue) {
            this.signatureValue = signatureValue;
            return this;
        }

        /**
         * Sets the new value of "keyInfo" (any previous value will be replaced)
         * 
         * @param keyInfo
         *     New value of the "keyInfo" property.
         */
        public Signature.Builder<_B> withKeyInfo(final KeyInfo keyInfo) {
            this.keyInfo = ((keyInfo == null)?null:new KeyInfo.Builder<>(this, keyInfo, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "keyInfo" property.
         * Use {@link org.w3._2000._09.xmldsig_.KeyInfo.Builder#end()} to return to the
         * current builder.
         * 
         * @return
         *     A new builder to build the value of the "keyInfo" property.
         *     Use {@link org.w3._2000._09.xmldsig_.KeyInfo.Builder#end()} to return to the
         *     current builder.
         */
        public KeyInfo.Builder<? extends Signature.Builder<_B>> withKeyInfo() {
            if (this.keyInfo!= null) {
                return this.keyInfo;
            }
            return this.keyInfo = new KeyInfo.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public Signature.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public Signature build() {
            if (_storedValue == null) {
                return this.init(new Signature());
            } else {
                return ((Signature) _storedValue);
            }
        }

        public Signature.Builder<_B> copyOf(final Signature _other) {
            _other.copyTo(this);
            return this;
        }

        public Signature.Builder<_B> copyOf(final Signature.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends Signature.Selector<Signature.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static Signature.Select _root() {
            return new Signature.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private SignedInfo.Selector<TRoot, Signature.Selector<TRoot, TParent>> signedInfo = null;
        private com.kscs.util.jaxb.Selector<TRoot, Signature.Selector<TRoot, TParent>> signatureValue = null;
        private KeyInfo.Selector<TRoot, Signature.Selector<TRoot, TParent>> keyInfo = null;
        private com.kscs.util.jaxb.Selector<TRoot, Signature.Selector<TRoot, TParent>> id = null;

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
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public SignedInfo.Selector<TRoot, Signature.Selector<TRoot, TParent>> signedInfo() {
            return ((this.signedInfo == null)?this.signedInfo = new SignedInfo.Selector<>(this._root, this, "signedInfo"):this.signedInfo);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Signature.Selector<TRoot, TParent>> signatureValue() {
            return ((this.signatureValue == null)?this.signatureValue = new com.kscs.util.jaxb.Selector<>(this._root, this, "signatureValue"):this.signatureValue);
        }

        public KeyInfo.Selector<TRoot, Signature.Selector<TRoot, TParent>> keyInfo() {
            return ((this.keyInfo == null)?this.keyInfo = new KeyInfo.Selector<>(this._root, this, "keyInfo"):this.keyInfo);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Signature.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
