
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;

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
import oasis.names.tc.saml._2_0.assertion.Assertion;
import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.Timestamp;
import org.w3._2000._09.xmldsig_.Signature;


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
 *         <element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp"/>
 *         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Assertion" minOccurs="0"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "timestamp",
    "assertion",
    "signature"
})
@XmlRootElement(name = "Security")
public class Security {

    @XmlElement(name = "Timestamp", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", required = true)
    protected Timestamp timestamp;
    @XmlElement(name = "Assertion", namespace = "urn:oasis:names:tc:SAML:2.0:assertion")
    protected Assertion assertion;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected Signature signature;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setTimestamp(Timestamp value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the assertion property.
     * 
     * @return
     *     possible object is
     *     {@link Assertion }
     *     
     */
    public Assertion getAssertion() {
        return assertion;
    }

    /**
     * Sets the value of the assertion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assertion }
     *     
     */
    public void setAssertion(Assertion value) {
        this.assertion = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setSignature(Signature value) {
        this.signature = value;
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
    public<_B >void copyTo(final Security.Builder<_B> _other) {
        _other.timestamp = ((this.timestamp == null)?null:this.timestamp.newCopyBuilder(_other));
        _other.assertion = ((this.assertion == null)?null:this.assertion.newCopyBuilder(_other));
        _other.signature = ((this.signature == null)?null:this.signature.newCopyBuilder(_other));
        _other.id = this.id;
    }

    public<_B >Security.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new Security.Builder<_B>(_parentBuilder, this, true);
    }

    public Security.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static Security.Builder<Void> builder() {
        return new Security.Builder<>(null, null, false);
    }

    public static<_B >Security.Builder<_B> copyOf(final Security _other) {
        final Security.Builder<_B> _newBuilder = new Security.Builder<>(null, null, false);
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
    public<_B >void copyTo(final Security.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree timestampPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timestamp"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timestampPropertyTree!= null):((timestampPropertyTree == null)||(!timestampPropertyTree.isLeaf())))) {
            _other.timestamp = ((this.timestamp == null)?null:this.timestamp.newCopyBuilder(_other, timestampPropertyTree, _propertyTreeUse));
        }
        final PropertyTree assertionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("assertion"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(assertionPropertyTree!= null):((assertionPropertyTree == null)||(!assertionPropertyTree.isLeaf())))) {
            _other.assertion = ((this.assertion == null)?null:this.assertion.newCopyBuilder(_other, assertionPropertyTree, _propertyTreeUse));
        }
        final PropertyTree signaturePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signature"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signaturePropertyTree!= null):((signaturePropertyTree == null)||(!signaturePropertyTree.isLeaf())))) {
            _other.signature = ((this.signature == null)?null:this.signature.newCopyBuilder(_other, signaturePropertyTree, _propertyTreeUse));
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >Security.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new Security.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public Security.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >Security.Builder<_B> copyOf(final Security _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final Security.Builder<_B> _newBuilder = new Security.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static Security.Builder<Void> copyExcept(final Security _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static Security.Builder<Void> copyOnly(final Security _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final Security _storedValue;
        private Timestamp.Builder<Security.Builder<_B>> timestamp;
        private Assertion.Builder<Security.Builder<_B>> assertion;
        private Signature.Builder<Security.Builder<_B>> signature;
        private String id;

        public Builder(final _B _parentBuilder, final Security _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.timestamp = ((_other.timestamp == null)?null:_other.timestamp.newCopyBuilder(this));
                    this.assertion = ((_other.assertion == null)?null:_other.assertion.newCopyBuilder(this));
                    this.signature = ((_other.signature == null)?null:_other.signature.newCopyBuilder(this));
                    this.id = _other.id;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final Security _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree timestampPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timestamp"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timestampPropertyTree!= null):((timestampPropertyTree == null)||(!timestampPropertyTree.isLeaf())))) {
                        this.timestamp = ((_other.timestamp == null)?null:_other.timestamp.newCopyBuilder(this, timestampPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree assertionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("assertion"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(assertionPropertyTree!= null):((assertionPropertyTree == null)||(!assertionPropertyTree.isLeaf())))) {
                        this.assertion = ((_other.assertion == null)?null:_other.assertion.newCopyBuilder(this, assertionPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree signaturePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signature"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signaturePropertyTree!= null):((signaturePropertyTree == null)||(!signaturePropertyTree.isLeaf())))) {
                        this.signature = ((_other.signature == null)?null:_other.signature.newCopyBuilder(this, signaturePropertyTree, _propertyTreeUse));
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

        protected<_P extends Security >_P init(final _P _product) {
            _product.timestamp = ((this.timestamp == null)?null:this.timestamp.build());
            _product.assertion = ((this.assertion == null)?null:this.assertion.build());
            _product.signature = ((this.signature == null)?null:this.signature.build());
            _product.id = this.id;
            return _product;
        }

        /**
         * Sets the new value of "timestamp" (any previous value will be replaced)
         * 
         * @param timestamp
         *     New value of the "timestamp" property.
         */
        public Security.Builder<_B> withTimestamp(final Timestamp timestamp) {
            this.timestamp = ((timestamp == null)?null:new Timestamp.Builder<>(this, timestamp, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "timestamp" property.
         * Use {@link
         * org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.Timestamp.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "timestamp" property.
         *     Use {@link
         *     org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.Timestamp.Builder#end()}
         *     to return to the current builder.
         */
        public Timestamp.Builder<? extends Security.Builder<_B>> withTimestamp() {
            if (this.timestamp!= null) {
                return this.timestamp;
            }
            return this.timestamp = new Timestamp.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "assertion" (any previous value will be replaced)
         * 
         * @param assertion
         *     New value of the "assertion" property.
         */
        public Security.Builder<_B> withAssertion(final Assertion assertion) {
            this.assertion = ((assertion == null)?null:new Assertion.Builder<>(this, assertion, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "assertion" property.
         * Use {@link oasis.names.tc.saml._2_0.assertion.Assertion.Builder#end()} to return
         * to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "assertion" property.
         *     Use {@link oasis.names.tc.saml._2_0.assertion.Assertion.Builder#end()} to return
         *     to the current builder.
         */
        public Assertion.Builder<? extends Security.Builder<_B>> withAssertion() {
            if (this.assertion!= null) {
                return this.assertion;
            }
            return this.assertion = new Assertion.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "signature" (any previous value will be replaced)
         * 
         * @param signature
         *     New value of the "signature" property.
         */
        public Security.Builder<_B> withSignature(final Signature signature) {
            this.signature = ((signature == null)?null:new Signature.Builder<>(this, signature, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "signature" property.
         * Use {@link org.w3._2000._09.xmldsig_.Signature.Builder#end()} to return to the
         * current builder.
         * 
         * @return
         *     A new builder to build the value of the "signature" property.
         *     Use {@link org.w3._2000._09.xmldsig_.Signature.Builder#end()} to return to the
         *     current builder.
         */
        public Signature.Builder<? extends Security.Builder<_B>> withSignature() {
            if (this.signature!= null) {
                return this.signature;
            }
            return this.signature = new Signature.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public Security.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public Security build() {
            if (_storedValue == null) {
                return this.init(new Security());
            } else {
                return ((Security) _storedValue);
            }
        }

        public Security.Builder<_B> copyOf(final Security _other) {
            _other.copyTo(this);
            return this;
        }

        public Security.Builder<_B> copyOf(final Security.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends Security.Selector<Security.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static Security.Select _root() {
            return new Security.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private Timestamp.Selector<TRoot, Security.Selector<TRoot, TParent>> timestamp = null;
        private Assertion.Selector<TRoot, Security.Selector<TRoot, TParent>> assertion = null;
        private Signature.Selector<TRoot, Security.Selector<TRoot, TParent>> signature = null;
        private com.kscs.util.jaxb.Selector<TRoot, Security.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.timestamp!= null) {
                products.put("timestamp", this.timestamp.init());
            }
            if (this.assertion!= null) {
                products.put("assertion", this.assertion.init());
            }
            if (this.signature!= null) {
                products.put("signature", this.signature.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public Timestamp.Selector<TRoot, Security.Selector<TRoot, TParent>> timestamp() {
            return ((this.timestamp == null)?this.timestamp = new Timestamp.Selector<>(this._root, this, "timestamp"):this.timestamp);
        }

        public Assertion.Selector<TRoot, Security.Selector<TRoot, TParent>> assertion() {
            return ((this.assertion == null)?this.assertion = new Assertion.Selector<>(this._root, this, "assertion"):this.assertion);
        }

        public Signature.Selector<TRoot, Security.Selector<TRoot, TParent>> signature() {
            return ((this.signature == null)?this.signature = new Signature.Selector<>(this._root, this, "signature"):this.signature);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Security.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
