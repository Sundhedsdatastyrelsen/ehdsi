
package org.w3._2000._09.xmldsig_;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignatureMethodType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SignatureMethodType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="HMACOutputLength" type="{http://www.w3.org/2000/09/xmldsig#}HMACOutputLengthType" minOccurs="0"/>
 *         <any namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureMethodType", propOrder = {
    "content"
})
public class SignatureMethodType {

    @XmlElementRef(name = "HMACOutputLength", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false)
    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;
    @XmlAttribute(name = "Algorithm", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String algorithm;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * {@link Object }
     * {@link String }
     * 
     * 
     * @return
     *     The value of the content property.
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return this.content;
    }

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
    public<_B >void copyTo(final SignatureMethodType.Builder<_B> _other) {
        if (this.content == null) {
            _other.content = null;
        } else {
            _other.content = new ArrayList<>();
            for (Object _item: this.content) {
                _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
        _other.algorithm = this.algorithm;
    }

    public<_B >SignatureMethodType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SignatureMethodType.Builder<_B>(_parentBuilder, this, true);
    }

    public SignatureMethodType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SignatureMethodType.Builder<Void> builder() {
        return new SignatureMethodType.Builder<>(null, null, false);
    }

    public static<_B >SignatureMethodType.Builder<_B> copyOf(final SignatureMethodType _other) {
        final SignatureMethodType.Builder<_B> _newBuilder = new SignatureMethodType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SignatureMethodType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree contentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("content"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(contentPropertyTree!= null):((contentPropertyTree == null)||(!contentPropertyTree.isLeaf())))) {
            if (this.content == null) {
                _other.content = null;
            } else {
                _other.content = new ArrayList<>();
                for (Object _item: this.content) {
                    _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
        final PropertyTree algorithmPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("algorithm"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(algorithmPropertyTree!= null):((algorithmPropertyTree == null)||(!algorithmPropertyTree.isLeaf())))) {
            _other.algorithm = this.algorithm;
        }
    }

    public<_B >SignatureMethodType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SignatureMethodType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SignatureMethodType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SignatureMethodType.Builder<_B> copyOf(final SignatureMethodType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SignatureMethodType.Builder<_B> _newBuilder = new SignatureMethodType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SignatureMethodType.Builder<Void> copyExcept(final SignatureMethodType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SignatureMethodType.Builder<Void> copyOnly(final SignatureMethodType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SignatureMethodType _storedValue;
        private List<Buildable> content;
        private String algorithm;

        public Builder(final _B _parentBuilder, final SignatureMethodType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.content == null) {
                        this.content = null;
                    } else {
                        this.content = new ArrayList<>();
                        for (Object _item: _other.content) {
                            this.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                        }
                    }
                    this.algorithm = _other.algorithm;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final SignatureMethodType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree contentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("content"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(contentPropertyTree!= null):((contentPropertyTree == null)||(!contentPropertyTree.isLeaf())))) {
                        if (_other.content == null) {
                            this.content = null;
                        } else {
                            this.content = new ArrayList<>();
                            for (Object _item: _other.content) {
                                this.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                            }
                        }
                    }
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

        protected<_P extends SignatureMethodType >_P init(final _P _product) {
            if (this.content!= null) {
                final List<Object> content = new ArrayList<>(this.content.size());
                for (Buildable _item: this.content) {
                    content.add(((Object) _item.build()));
                }
                _product.content = content;
            }
            _product.algorithm = this.algorithm;
            return _product;
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public SignatureMethodType.Builder<_B> addContent(final Iterable<?> content) {
            if (content!= null) {
                if (this.content == null) {
                    this.content = new ArrayList<>();
                }
                for (Object _item: content) {
                    this.content.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public SignatureMethodType.Builder<_B> withContent(final Iterable<?> content) {
            if (this.content!= null) {
                this.content.clear();
            }
            return addContent(content);
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public SignatureMethodType.Builder<_B> addContent(Object... content) {
            addContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public SignatureMethodType.Builder<_B> withContent(Object... content) {
            withContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "algorithm" (any previous value will be replaced)
         * 
         * @param algorithm
         *     New value of the "algorithm" property.
         */
        public SignatureMethodType.Builder<_B> withAlgorithm(final String algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        @Override
        public SignatureMethodType build() {
            if (_storedValue == null) {
                return this.init(new SignatureMethodType());
            } else {
                return ((SignatureMethodType) _storedValue);
            }
        }

        public SignatureMethodType.Builder<_B> copyOf(final SignatureMethodType _other) {
            _other.copyTo(this);
            return this;
        }

        public SignatureMethodType.Builder<_B> copyOf(final SignatureMethodType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SignatureMethodType.Selector<SignatureMethodType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SignatureMethodType.Select _root() {
            return new SignatureMethodType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, SignatureMethodType.Selector<TRoot, TParent>> content = null;
        private com.kscs.util.jaxb.Selector<TRoot, SignatureMethodType.Selector<TRoot, TParent>> algorithm = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.content!= null) {
                products.put("content", this.content.init());
            }
            if (this.algorithm!= null) {
                products.put("algorithm", this.algorithm.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignatureMethodType.Selector<TRoot, TParent>> content() {
            return ((this.content == null)?this.content = new com.kscs.util.jaxb.Selector<>(this._root, this, "content"):this.content);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SignatureMethodType.Selector<TRoot, TParent>> algorithm() {
            return ((this.algorithm == null)?this.algorithm = new com.kscs.util.jaxb.Selector<>(this._root, this, "algorithm"):this.algorithm);
        }

    }

}
