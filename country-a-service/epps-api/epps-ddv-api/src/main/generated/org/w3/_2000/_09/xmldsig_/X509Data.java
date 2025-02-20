
package org.w3._2000._09.xmldsig_;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
 *       <choice>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}X509Certificate"/>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}KeyName"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "x509Certificate",
    "keyName"
})
@XmlRootElement(name = "X509Data")
public class X509Data {

    @XmlElement(name = "X509Certificate")
    protected byte[] x509Certificate;
    @XmlElement(name = "KeyName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String keyName;

    /**
     * Gets the value of the x509Certificate property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509Certificate() {
        return x509Certificate;
    }

    /**
     * Sets the value of the x509Certificate property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509Certificate(byte[] value) {
        this.x509Certificate = value;
    }

    /**
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyName(String value) {
        this.keyName = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final X509Data.Builder<_B> _other) {
        _other.x509Certificate = this.x509Certificate;
        _other.keyName = this.keyName;
    }

    public<_B >X509Data.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new X509Data.Builder<_B>(_parentBuilder, this, true);
    }

    public X509Data.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static X509Data.Builder<Void> builder() {
        return new X509Data.Builder<>(null, null, false);
    }

    public static<_B >X509Data.Builder<_B> copyOf(final X509Data _other) {
        final X509Data.Builder<_B> _newBuilder = new X509Data.Builder<>(null, null, false);
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
    public<_B >void copyTo(final X509Data.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree x509CertificatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509Certificate"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509CertificatePropertyTree!= null):((x509CertificatePropertyTree == null)||(!x509CertificatePropertyTree.isLeaf())))) {
            _other.x509Certificate = this.x509Certificate;
        }
        final PropertyTree keyNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyNamePropertyTree!= null):((keyNamePropertyTree == null)||(!keyNamePropertyTree.isLeaf())))) {
            _other.keyName = this.keyName;
        }
    }

    public<_B >X509Data.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new X509Data.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public X509Data.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >X509Data.Builder<_B> copyOf(final X509Data _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final X509Data.Builder<_B> _newBuilder = new X509Data.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static X509Data.Builder<Void> copyExcept(final X509Data _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static X509Data.Builder<Void> copyOnly(final X509Data _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final X509Data _storedValue;
        private byte[] x509Certificate;
        private String keyName;

        public Builder(final _B _parentBuilder, final X509Data _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.x509Certificate = _other.x509Certificate;
                    this.keyName = _other.keyName;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final X509Data _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree x509CertificatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509Certificate"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509CertificatePropertyTree!= null):((x509CertificatePropertyTree == null)||(!x509CertificatePropertyTree.isLeaf())))) {
                        this.x509Certificate = _other.x509Certificate;
                    }
                    final PropertyTree keyNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyNamePropertyTree!= null):((keyNamePropertyTree == null)||(!keyNamePropertyTree.isLeaf())))) {
                        this.keyName = _other.keyName;
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

        protected<_P extends X509Data >_P init(final _P _product) {
            _product.x509Certificate = this.x509Certificate;
            _product.keyName = this.keyName;
            return _product;
        }

        /**
         * Sets the new value of "x509Certificate" (any previous value will be replaced)
         * 
         * @param x509Certificate
         *     New value of the "x509Certificate" property.
         */
        public X509Data.Builder<_B> withX509Certificate(final byte[] x509Certificate) {
            this.x509Certificate = x509Certificate;
            return this;
        }

        /**
         * Sets the new value of "keyName" (any previous value will be replaced)
         * 
         * @param keyName
         *     New value of the "keyName" property.
         */
        public X509Data.Builder<_B> withKeyName(final String keyName) {
            this.keyName = keyName;
            return this;
        }

        @Override
        public X509Data build() {
            if (_storedValue == null) {
                return this.init(new X509Data());
            } else {
                return ((X509Data) _storedValue);
            }
        }

        public X509Data.Builder<_B> copyOf(final X509Data _other) {
            _other.copyTo(this);
            return this;
        }

        public X509Data.Builder<_B> copyOf(final X509Data.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends X509Data.Selector<X509Data.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static X509Data.Select _root() {
            return new X509Data.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, X509Data.Selector<TRoot, TParent>> x509Certificate = null;
        private com.kscs.util.jaxb.Selector<TRoot, X509Data.Selector<TRoot, TParent>> keyName = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.x509Certificate!= null) {
                products.put("x509Certificate", this.x509Certificate.init());
            }
            if (this.keyName!= null) {
                products.put("keyName", this.keyName.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, X509Data.Selector<TRoot, TParent>> x509Certificate() {
            return ((this.x509Certificate == null)?this.x509Certificate = new com.kscs.util.jaxb.Selector<>(this._root, this, "x509Certificate"):this.x509Certificate);
        }

        public com.kscs.util.jaxb.Selector<TRoot, X509Data.Selector<TRoot, TParent>> keyName() {
            return ((this.keyName == null)?this.keyName = new com.kscs.util.jaxb.Selector<>(this._root, this, "keyName"):this.keyName);
        }

    }

}
