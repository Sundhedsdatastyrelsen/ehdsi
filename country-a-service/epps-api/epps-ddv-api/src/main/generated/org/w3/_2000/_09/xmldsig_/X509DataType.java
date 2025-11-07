
package org.w3._2000._09.xmldsig_;

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
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for X509DataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="X509DataType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence maxOccurs="unbounded">
 *         <choice>
 *           <element name="X509IssuerSerial" type="{http://www.w3.org/2000/09/xmldsig#}X509IssuerSerialType"/>
 *           <element name="X509SKI" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *           <element name="X509SubjectName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           <element name="X509Certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *           <element name="X509CRL" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *           <any processContents='lax' namespace='##other'/>
 *         </choice>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509DataType", propOrder = {
    "x509IssuerSerialOrX509SKIOrX509SubjectName"
})
public class X509DataType {

    @XmlElementRefs({
        @XmlElementRef(name = "X509IssuerSerial", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "X509SKI", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "X509SubjectName", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "X509Certificate", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "X509CRL", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> x509IssuerSerialOrX509SKIOrX509SubjectName;

    /**
     * Gets the value of the x509IssuerSerialOrX509SKIOrX509SubjectName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the x509IssuerSerialOrX509SKIOrX509SubjectName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getX509IssuerSerialOrX509SKIOrX509SubjectName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link X509IssuerSerialType }{@code >}
     * {@link Object }
     * {@link Element }
     * 
     * 
     * @return
     *     The value of the x509IssuerSerialOrX509SKIOrX509SubjectName property.
     */
    public List<Object> getX509IssuerSerialOrX509SKIOrX509SubjectName() {
        if (x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
            x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<>();
        }
        return this.x509IssuerSerialOrX509SKIOrX509SubjectName;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final X509DataType.Builder<_B> _other) {
        if (this.x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
            _other.x509IssuerSerialOrX509SKIOrX509SubjectName = null;
        } else {
            _other.x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<>();
            for (Object _item: this.x509IssuerSerialOrX509SKIOrX509SubjectName) {
                _other.x509IssuerSerialOrX509SKIOrX509SubjectName.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
    }

    public<_B >X509DataType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new X509DataType.Builder<_B>(_parentBuilder, this, true);
    }

    public X509DataType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static X509DataType.Builder<Void> builder() {
        return new X509DataType.Builder<>(null, null, false);
    }

    public static<_B >X509DataType.Builder<_B> copyOf(final X509DataType _other) {
        final X509DataType.Builder<_B> _newBuilder = new X509DataType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final X509DataType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509IssuerSerialOrX509SKIOrX509SubjectName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree!= null):((x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree == null)||(!x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree.isLeaf())))) {
            if (this.x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
                _other.x509IssuerSerialOrX509SKIOrX509SubjectName = null;
            } else {
                _other.x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<>();
                for (Object _item: this.x509IssuerSerialOrX509SKIOrX509SubjectName) {
                    _other.x509IssuerSerialOrX509SKIOrX509SubjectName.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
    }

    public<_B >X509DataType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new X509DataType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public X509DataType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >X509DataType.Builder<_B> copyOf(final X509DataType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final X509DataType.Builder<_B> _newBuilder = new X509DataType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static X509DataType.Builder<Void> copyExcept(final X509DataType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static X509DataType.Builder<Void> copyOnly(final X509DataType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final X509DataType _storedValue;
        private List<Buildable> x509IssuerSerialOrX509SKIOrX509SubjectName;

        public Builder(final _B _parentBuilder, final X509DataType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
                        this.x509IssuerSerialOrX509SKIOrX509SubjectName = null;
                    } else {
                        this.x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<>();
                        for (Object _item: _other.x509IssuerSerialOrX509SKIOrX509SubjectName) {
                            this.x509IssuerSerialOrX509SKIOrX509SubjectName.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final X509DataType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509IssuerSerialOrX509SKIOrX509SubjectName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree!= null):((x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree == null)||(!x509IssuerSerialOrX509SKIOrX509SubjectNamePropertyTree.isLeaf())))) {
                        if (_other.x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
                            this.x509IssuerSerialOrX509SKIOrX509SubjectName = null;
                        } else {
                            this.x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<>();
                            for (Object _item: _other.x509IssuerSerialOrX509SKIOrX509SubjectName) {
                                this.x509IssuerSerialOrX509SKIOrX509SubjectName.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                            }
                        }
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

        protected<_P extends X509DataType >_P init(final _P _product) {
            if (this.x509IssuerSerialOrX509SKIOrX509SubjectName!= null) {
                final List<Object> x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<>(this.x509IssuerSerialOrX509SKIOrX509SubjectName.size());
                for (Buildable _item: this.x509IssuerSerialOrX509SKIOrX509SubjectName) {
                    x509IssuerSerialOrX509SKIOrX509SubjectName.add(((Object) _item.build()));
                }
                _product.x509IssuerSerialOrX509SKIOrX509SubjectName = x509IssuerSerialOrX509SKIOrX509SubjectName;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of
         * "x509IssuerSerialOrX509SKIOrX509SubjectName"
         * 
         * @param x509IssuerSerialOrX509SKIOrX509SubjectName
         *     Items to add to the value of the "x509IssuerSerialOrX509SKIOrX509SubjectName"
         *     property
         */
        public X509DataType.Builder<_B> addX509IssuerSerialOrX509SKIOrX509SubjectName(final Iterable<?> x509IssuerSerialOrX509SKIOrX509SubjectName) {
            if (x509IssuerSerialOrX509SKIOrX509SubjectName!= null) {
                if (this.x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
                    this.x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<>();
                }
                for (Object _item: x509IssuerSerialOrX509SKIOrX509SubjectName) {
                    this.x509IssuerSerialOrX509SKIOrX509SubjectName.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "x509IssuerSerialOrX509SKIOrX509SubjectName" (any previous
         * value will be replaced)
         * 
         * @param x509IssuerSerialOrX509SKIOrX509SubjectName
         *     New value of the "x509IssuerSerialOrX509SKIOrX509SubjectName" property.
         */
        public X509DataType.Builder<_B> withX509IssuerSerialOrX509SKIOrX509SubjectName(final Iterable<?> x509IssuerSerialOrX509SKIOrX509SubjectName) {
            if (this.x509IssuerSerialOrX509SKIOrX509SubjectName!= null) {
                this.x509IssuerSerialOrX509SKIOrX509SubjectName.clear();
            }
            return addX509IssuerSerialOrX509SKIOrX509SubjectName(x509IssuerSerialOrX509SKIOrX509SubjectName);
        }

        /**
         * Adds the given items to the value of
         * "x509IssuerSerialOrX509SKIOrX509SubjectName"
         * 
         * @param x509IssuerSerialOrX509SKIOrX509SubjectName
         *     Items to add to the value of the "x509IssuerSerialOrX509SKIOrX509SubjectName"
         *     property
         */
        public X509DataType.Builder<_B> addX509IssuerSerialOrX509SKIOrX509SubjectName(Object... x509IssuerSerialOrX509SKIOrX509SubjectName) {
            addX509IssuerSerialOrX509SKIOrX509SubjectName(Arrays.asList(x509IssuerSerialOrX509SKIOrX509SubjectName));
            return this;
        }

        /**
         * Sets the new value of "x509IssuerSerialOrX509SKIOrX509SubjectName" (any previous
         * value will be replaced)
         * 
         * @param x509IssuerSerialOrX509SKIOrX509SubjectName
         *     New value of the "x509IssuerSerialOrX509SKIOrX509SubjectName" property.
         */
        public X509DataType.Builder<_B> withX509IssuerSerialOrX509SKIOrX509SubjectName(Object... x509IssuerSerialOrX509SKIOrX509SubjectName) {
            withX509IssuerSerialOrX509SKIOrX509SubjectName(Arrays.asList(x509IssuerSerialOrX509SKIOrX509SubjectName));
            return this;
        }

        @Override
        public X509DataType build() {
            if (_storedValue == null) {
                return this.init(new X509DataType());
            } else {
                return ((X509DataType) _storedValue);
            }
        }

        public X509DataType.Builder<_B> copyOf(final X509DataType _other) {
            _other.copyTo(this);
            return this;
        }

        public X509DataType.Builder<_B> copyOf(final X509DataType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends X509DataType.Selector<X509DataType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static X509DataType.Select _root() {
            return new X509DataType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, X509DataType.Selector<TRoot, TParent>> x509IssuerSerialOrX509SKIOrX509SubjectName = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.x509IssuerSerialOrX509SKIOrX509SubjectName!= null) {
                products.put("x509IssuerSerialOrX509SKIOrX509SubjectName", this.x509IssuerSerialOrX509SKIOrX509SubjectName.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, X509DataType.Selector<TRoot, TParent>> x509IssuerSerialOrX509SKIOrX509SubjectName() {
            return ((this.x509IssuerSerialOrX509SKIOrX509SubjectName == null)?this.x509IssuerSerialOrX509SKIOrX509SubjectName = new com.kscs.util.jaxb.Selector<>(this._root, this, "x509IssuerSerialOrX509SKIOrX509SubjectName"):this.x509IssuerSerialOrX509SKIOrX509SubjectName);
        }

    }

}
