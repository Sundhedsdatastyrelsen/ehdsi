
package org.w3._2000._09.xmldsig_;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DSAKeyValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DSAKeyValueType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <sequence minOccurs="0">
 *           <element name="P" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *           <element name="Q" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *         </sequence>
 *         <element name="G" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary" minOccurs="0"/>
 *         <element name="Y" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *         <element name="J" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary" minOccurs="0"/>
 *         <sequence minOccurs="0">
 *           <element name="Seed" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *           <element name="PgenCounter" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *         </sequence>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DSAKeyValueType", propOrder = {
    "p",
    "q",
    "g",
    "y",
    "j",
    "seed",
    "pgenCounter"
})
public class DSAKeyValueType {

    @XmlElement(name = "P")
    protected byte[] p;
    @XmlElement(name = "Q")
    protected byte[] q;
    @XmlElement(name = "G")
    protected byte[] g;
    @XmlElement(name = "Y", required = true)
    protected byte[] y;
    @XmlElement(name = "J")
    protected byte[] j;
    @XmlElement(name = "Seed")
    protected byte[] seed;
    @XmlElement(name = "PgenCounter")
    protected byte[] pgenCounter;

    /**
     * Gets the value of the p property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getP() {
        return p;
    }

    /**
     * Sets the value of the p property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setP(byte[] value) {
        this.p = value;
    }

    /**
     * Gets the value of the q property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getQ() {
        return q;
    }

    /**
     * Sets the value of the q property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setQ(byte[] value) {
        this.q = value;
    }

    /**
     * Gets the value of the g property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getG() {
        return g;
    }

    /**
     * Sets the value of the g property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setG(byte[] value) {
        this.g = value;
    }

    /**
     * Gets the value of the y property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setY(byte[] value) {
        this.y = value;
    }

    /**
     * Gets the value of the j property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getJ() {
        return j;
    }

    /**
     * Sets the value of the j property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setJ(byte[] value) {
        this.j = value;
    }

    /**
     * Gets the value of the seed property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSeed() {
        return seed;
    }

    /**
     * Sets the value of the seed property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSeed(byte[] value) {
        this.seed = value;
    }

    /**
     * Gets the value of the pgenCounter property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPgenCounter() {
        return pgenCounter;
    }

    /**
     * Sets the value of the pgenCounter property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPgenCounter(byte[] value) {
        this.pgenCounter = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DSAKeyValueType.Builder<_B> _other) {
        _other.p = this.p;
        _other.q = this.q;
        _other.g = this.g;
        _other.y = this.y;
        _other.j = this.j;
        _other.seed = this.seed;
        _other.pgenCounter = this.pgenCounter;
    }

    public<_B >DSAKeyValueType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DSAKeyValueType.Builder<_B>(_parentBuilder, this, true);
    }

    public DSAKeyValueType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DSAKeyValueType.Builder<Void> builder() {
        return new DSAKeyValueType.Builder<>(null, null, false);
    }

    public static<_B >DSAKeyValueType.Builder<_B> copyOf(final DSAKeyValueType _other) {
        final DSAKeyValueType.Builder<_B> _newBuilder = new DSAKeyValueType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DSAKeyValueType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree pPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("p"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(pPropertyTree!= null):((pPropertyTree == null)||(!pPropertyTree.isLeaf())))) {
            _other.p = this.p;
        }
        final PropertyTree qPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("q"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(qPropertyTree!= null):((qPropertyTree == null)||(!qPropertyTree.isLeaf())))) {
            _other.q = this.q;
        }
        final PropertyTree gPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("g"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(gPropertyTree!= null):((gPropertyTree == null)||(!gPropertyTree.isLeaf())))) {
            _other.g = this.g;
        }
        final PropertyTree yPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("y"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(yPropertyTree!= null):((yPropertyTree == null)||(!yPropertyTree.isLeaf())))) {
            _other.y = this.y;
        }
        final PropertyTree jPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("j"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(jPropertyTree!= null):((jPropertyTree == null)||(!jPropertyTree.isLeaf())))) {
            _other.j = this.j;
        }
        final PropertyTree seedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("seed"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(seedPropertyTree!= null):((seedPropertyTree == null)||(!seedPropertyTree.isLeaf())))) {
            _other.seed = this.seed;
        }
        final PropertyTree pgenCounterPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("pgenCounter"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(pgenCounterPropertyTree!= null):((pgenCounterPropertyTree == null)||(!pgenCounterPropertyTree.isLeaf())))) {
            _other.pgenCounter = this.pgenCounter;
        }
    }

    public<_B >DSAKeyValueType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DSAKeyValueType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DSAKeyValueType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DSAKeyValueType.Builder<_B> copyOf(final DSAKeyValueType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DSAKeyValueType.Builder<_B> _newBuilder = new DSAKeyValueType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DSAKeyValueType.Builder<Void> copyExcept(final DSAKeyValueType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DSAKeyValueType.Builder<Void> copyOnly(final DSAKeyValueType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DSAKeyValueType _storedValue;
        private byte[] p;
        private byte[] q;
        private byte[] g;
        private byte[] y;
        private byte[] j;
        private byte[] seed;
        private byte[] pgenCounter;

        public Builder(final _B _parentBuilder, final DSAKeyValueType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.p = _other.p;
                    this.q = _other.q;
                    this.g = _other.g;
                    this.y = _other.y;
                    this.j = _other.j;
                    this.seed = _other.seed;
                    this.pgenCounter = _other.pgenCounter;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DSAKeyValueType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree pPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("p"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(pPropertyTree!= null):((pPropertyTree == null)||(!pPropertyTree.isLeaf())))) {
                        this.p = _other.p;
                    }
                    final PropertyTree qPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("q"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(qPropertyTree!= null):((qPropertyTree == null)||(!qPropertyTree.isLeaf())))) {
                        this.q = _other.q;
                    }
                    final PropertyTree gPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("g"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(gPropertyTree!= null):((gPropertyTree == null)||(!gPropertyTree.isLeaf())))) {
                        this.g = _other.g;
                    }
                    final PropertyTree yPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("y"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(yPropertyTree!= null):((yPropertyTree == null)||(!yPropertyTree.isLeaf())))) {
                        this.y = _other.y;
                    }
                    final PropertyTree jPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("j"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(jPropertyTree!= null):((jPropertyTree == null)||(!jPropertyTree.isLeaf())))) {
                        this.j = _other.j;
                    }
                    final PropertyTree seedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("seed"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(seedPropertyTree!= null):((seedPropertyTree == null)||(!seedPropertyTree.isLeaf())))) {
                        this.seed = _other.seed;
                    }
                    final PropertyTree pgenCounterPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("pgenCounter"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(pgenCounterPropertyTree!= null):((pgenCounterPropertyTree == null)||(!pgenCounterPropertyTree.isLeaf())))) {
                        this.pgenCounter = _other.pgenCounter;
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

        protected<_P extends DSAKeyValueType >_P init(final _P _product) {
            _product.p = this.p;
            _product.q = this.q;
            _product.g = this.g;
            _product.y = this.y;
            _product.j = this.j;
            _product.seed = this.seed;
            _product.pgenCounter = this.pgenCounter;
            return _product;
        }

        /**
         * Sets the new value of "p" (any previous value will be replaced)
         * 
         * @param p
         *     New value of the "p" property.
         */
        public DSAKeyValueType.Builder<_B> withP(final byte[] p) {
            this.p = p;
            return this;
        }

        /**
         * Sets the new value of "q" (any previous value will be replaced)
         * 
         * @param q
         *     New value of the "q" property.
         */
        public DSAKeyValueType.Builder<_B> withQ(final byte[] q) {
            this.q = q;
            return this;
        }

        /**
         * Sets the new value of "g" (any previous value will be replaced)
         * 
         * @param g
         *     New value of the "g" property.
         */
        public DSAKeyValueType.Builder<_B> withG(final byte[] g) {
            this.g = g;
            return this;
        }

        /**
         * Sets the new value of "y" (any previous value will be replaced)
         * 
         * @param y
         *     New value of the "y" property.
         */
        public DSAKeyValueType.Builder<_B> withY(final byte[] y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the new value of "j" (any previous value will be replaced)
         * 
         * @param j
         *     New value of the "j" property.
         */
        public DSAKeyValueType.Builder<_B> withJ(final byte[] j) {
            this.j = j;
            return this;
        }

        /**
         * Sets the new value of "seed" (any previous value will be replaced)
         * 
         * @param seed
         *     New value of the "seed" property.
         */
        public DSAKeyValueType.Builder<_B> withSeed(final byte[] seed) {
            this.seed = seed;
            return this;
        }

        /**
         * Sets the new value of "pgenCounter" (any previous value will be replaced)
         * 
         * @param pgenCounter
         *     New value of the "pgenCounter" property.
         */
        public DSAKeyValueType.Builder<_B> withPgenCounter(final byte[] pgenCounter) {
            this.pgenCounter = pgenCounter;
            return this;
        }

        @Override
        public DSAKeyValueType build() {
            if (_storedValue == null) {
                return this.init(new DSAKeyValueType());
            } else {
                return ((DSAKeyValueType) _storedValue);
            }
        }

        public DSAKeyValueType.Builder<_B> copyOf(final DSAKeyValueType _other) {
            _other.copyTo(this);
            return this;
        }

        public DSAKeyValueType.Builder<_B> copyOf(final DSAKeyValueType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DSAKeyValueType.Selector<DSAKeyValueType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DSAKeyValueType.Select _root() {
            return new DSAKeyValueType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> p = null;
        private com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> q = null;
        private com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> g = null;
        private com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> y = null;
        private com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> j = null;
        private com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> seed = null;
        private com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> pgenCounter = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.p!= null) {
                products.put("p", this.p.init());
            }
            if (this.q!= null) {
                products.put("q", this.q.init());
            }
            if (this.g!= null) {
                products.put("g", this.g.init());
            }
            if (this.y!= null) {
                products.put("y", this.y.init());
            }
            if (this.j!= null) {
                products.put("j", this.j.init());
            }
            if (this.seed!= null) {
                products.put("seed", this.seed.init());
            }
            if (this.pgenCounter!= null) {
                products.put("pgenCounter", this.pgenCounter.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> p() {
            return ((this.p == null)?this.p = new com.kscs.util.jaxb.Selector<>(this._root, this, "p"):this.p);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> q() {
            return ((this.q == null)?this.q = new com.kscs.util.jaxb.Selector<>(this._root, this, "q"):this.q);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> g() {
            return ((this.g == null)?this.g = new com.kscs.util.jaxb.Selector<>(this._root, this, "g"):this.g);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> y() {
            return ((this.y == null)?this.y = new com.kscs.util.jaxb.Selector<>(this._root, this, "y"):this.y);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> j() {
            return ((this.j == null)?this.j = new com.kscs.util.jaxb.Selector<>(this._root, this, "j"):this.j);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> seed() {
            return ((this.seed == null)?this.seed = new com.kscs.util.jaxb.Selector<>(this._root, this, "seed"):this.seed);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DSAKeyValueType.Selector<TRoot, TParent>> pgenCounter() {
            return ((this.pgenCounter == null)?this.pgenCounter = new com.kscs.util.jaxb.Selector<>(this._root, this, "pgenCounter"):this.pgenCounter);
        }

    }

}
