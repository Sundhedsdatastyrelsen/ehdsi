
package org.w3._2000._09.xmldsig_;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="Algorithm" use="required">
 *         <simpleType>
 *           <restriction base="{http://www.w3.org/2001/XMLSchema}anyURI">
 *             <enumeration value="http://www.w3.org/2000/09/xmldsig#sha1"/>
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
@XmlType(name = "")
@XmlRootElement(name = "DigestMethod")
public class DigestMethod {

    @XmlAttribute(name = "Algorithm", required = true)
    protected String algorithm;

    /**
     * Gets the value of the algorithm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Sets the value of the algorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgorithm(String value) {
        this.algorithm = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DigestMethod.Builder<_B> _other) {
        _other.algorithm = this.algorithm;
    }

    public<_B >DigestMethod.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DigestMethod.Builder<_B>(_parentBuilder, this, true);
    }

    public DigestMethod.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DigestMethod.Builder<Void> builder() {
        return new DigestMethod.Builder<>(null, null, false);
    }

    public static<_B >DigestMethod.Builder<_B> copyOf(final DigestMethod _other) {
        final DigestMethod.Builder<_B> _newBuilder = new DigestMethod.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DigestMethod.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree algorithmPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("algorithm"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(algorithmPropertyTree!= null):((algorithmPropertyTree == null)||(!algorithmPropertyTree.isLeaf())))) {
            _other.algorithm = this.algorithm;
        }
    }

    public<_B >DigestMethod.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DigestMethod.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DigestMethod.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DigestMethod.Builder<_B> copyOf(final DigestMethod _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DigestMethod.Builder<_B> _newBuilder = new DigestMethod.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DigestMethod.Builder<Void> copyExcept(final DigestMethod _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DigestMethod.Builder<Void> copyOnly(final DigestMethod _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DigestMethod _storedValue;
        private String algorithm;

        public Builder(final _B _parentBuilder, final DigestMethod _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.algorithm = _other.algorithm;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DigestMethod _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree algorithmPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("algorithm"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(algorithmPropertyTree!= null):((algorithmPropertyTree == null)||(!algorithmPropertyTree.isLeaf())))) {
                        this.algorithm = _other.algorithm;
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

        protected<_P extends DigestMethod >_P init(final _P _product) {
            _product.algorithm = this.algorithm;
            return _product;
        }

        /**
         * Sets the new value of "algorithm" (any previous value will be replaced)
         * 
         * @param algorithm
         *     New value of the "algorithm" property.
         */
        public DigestMethod.Builder<_B> withAlgorithm(final String algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        @Override
        public DigestMethod build() {
            if (_storedValue == null) {
                return this.init(new DigestMethod());
            } else {
                return ((DigestMethod) _storedValue);
            }
        }

        public DigestMethod.Builder<_B> copyOf(final DigestMethod _other) {
            _other.copyTo(this);
            return this;
        }

        public DigestMethod.Builder<_B> copyOf(final DigestMethod.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DigestMethod.Selector<DigestMethod.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DigestMethod.Select _root() {
            return new DigestMethod.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, DigestMethod.Selector<TRoot, TParent>> algorithm = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.algorithm!= null) {
                products.put("algorithm", this.algorithm.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, DigestMethod.Selector<TRoot, TParent>> algorithm() {
            return ((this.algorithm == null)?this.algorithm = new com.kscs.util.jaxb.Selector<>(this._root, this, "algorithm"):this.algorithm);
        }

    }

}
