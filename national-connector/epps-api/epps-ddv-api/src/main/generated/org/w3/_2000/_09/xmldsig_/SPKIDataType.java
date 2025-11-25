
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
import jakarta.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for SPKIDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SPKIDataType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence maxOccurs="unbounded">
 *         <element name="SPKISexp" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         <any processContents='lax' namespace='##other' minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SPKIDataType", propOrder = {
    "spkiSexpAndAny"
})
public class SPKIDataType {

    @XmlElementRef(name = "SPKISexp", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class)
    @XmlAnyElement(lax = true)
    protected List<Object> spkiSexpAndAny;

    /**
     * Gets the value of the spkiSexpAndAny property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the spkiSexpAndAny property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSPKISexpAndAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * {@link Object }
     * {@link Element }
     * 
     * 
     * @return
     *     The value of the spkiSexpAndAny property.
     */
    public List<Object> getSPKISexpAndAny() {
        if (spkiSexpAndAny == null) {
            spkiSexpAndAny = new ArrayList<>();
        }
        return this.spkiSexpAndAny;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final SPKIDataType.Builder<_B> _other) {
        if (this.spkiSexpAndAny == null) {
            _other.spkiSexpAndAny = null;
        } else {
            _other.spkiSexpAndAny = new ArrayList<>();
            for (Object _item: this.spkiSexpAndAny) {
                _other.spkiSexpAndAny.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
    }

    public<_B >SPKIDataType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SPKIDataType.Builder<_B>(_parentBuilder, this, true);
    }

    public SPKIDataType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SPKIDataType.Builder<Void> builder() {
        return new SPKIDataType.Builder<>(null, null, false);
    }

    public static<_B >SPKIDataType.Builder<_B> copyOf(final SPKIDataType _other) {
        final SPKIDataType.Builder<_B> _newBuilder = new SPKIDataType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SPKIDataType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree spkiSexpAndAnyPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("spkiSexpAndAny"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(spkiSexpAndAnyPropertyTree!= null):((spkiSexpAndAnyPropertyTree == null)||(!spkiSexpAndAnyPropertyTree.isLeaf())))) {
            if (this.spkiSexpAndAny == null) {
                _other.spkiSexpAndAny = null;
            } else {
                _other.spkiSexpAndAny = new ArrayList<>();
                for (Object _item: this.spkiSexpAndAny) {
                    _other.spkiSexpAndAny.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
    }

    public<_B >SPKIDataType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SPKIDataType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SPKIDataType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SPKIDataType.Builder<_B> copyOf(final SPKIDataType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SPKIDataType.Builder<_B> _newBuilder = new SPKIDataType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SPKIDataType.Builder<Void> copyExcept(final SPKIDataType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SPKIDataType.Builder<Void> copyOnly(final SPKIDataType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SPKIDataType _storedValue;
        private List<Buildable> spkiSexpAndAny;

        public Builder(final _B _parentBuilder, final SPKIDataType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.spkiSexpAndAny == null) {
                        this.spkiSexpAndAny = null;
                    } else {
                        this.spkiSexpAndAny = new ArrayList<>();
                        for (Object _item: _other.spkiSexpAndAny) {
                            this.spkiSexpAndAny.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final SPKIDataType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree spkiSexpAndAnyPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("spkiSexpAndAny"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(spkiSexpAndAnyPropertyTree!= null):((spkiSexpAndAnyPropertyTree == null)||(!spkiSexpAndAnyPropertyTree.isLeaf())))) {
                        if (_other.spkiSexpAndAny == null) {
                            this.spkiSexpAndAny = null;
                        } else {
                            this.spkiSexpAndAny = new ArrayList<>();
                            for (Object _item: _other.spkiSexpAndAny) {
                                this.spkiSexpAndAny.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
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

        protected<_P extends SPKIDataType >_P init(final _P _product) {
            if (this.spkiSexpAndAny!= null) {
                final List<Object> spkiSexpAndAny = new ArrayList<>(this.spkiSexpAndAny.size());
                for (Buildable _item: this.spkiSexpAndAny) {
                    spkiSexpAndAny.add(((Object) _item.build()));
                }
                _product.spkiSexpAndAny = spkiSexpAndAny;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "spkiSexpAndAny"
         * 
         * @param spkiSexpAndAny
         *     Items to add to the value of the "spkiSexpAndAny" property
         */
        public SPKIDataType.Builder<_B> addSPKISexpAndAny(final Iterable<?> spkiSexpAndAny) {
            if (spkiSexpAndAny!= null) {
                if (this.spkiSexpAndAny == null) {
                    this.spkiSexpAndAny = new ArrayList<>();
                }
                for (Object _item: spkiSexpAndAny) {
                    this.spkiSexpAndAny.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "spkiSexpAndAny" (any previous value will be replaced)
         * 
         * @param spkiSexpAndAny
         *     New value of the "spkiSexpAndAny" property.
         */
        public SPKIDataType.Builder<_B> withSPKISexpAndAny(final Iterable<?> spkiSexpAndAny) {
            if (this.spkiSexpAndAny!= null) {
                this.spkiSexpAndAny.clear();
            }
            return addSPKISexpAndAny(spkiSexpAndAny);
        }

        /**
         * Adds the given items to the value of "spkiSexpAndAny"
         * 
         * @param spkiSexpAndAny
         *     Items to add to the value of the "spkiSexpAndAny" property
         */
        public SPKIDataType.Builder<_B> addSPKISexpAndAny(Object... spkiSexpAndAny) {
            addSPKISexpAndAny(Arrays.asList(spkiSexpAndAny));
            return this;
        }

        /**
         * Sets the new value of "spkiSexpAndAny" (any previous value will be replaced)
         * 
         * @param spkiSexpAndAny
         *     New value of the "spkiSexpAndAny" property.
         */
        public SPKIDataType.Builder<_B> withSPKISexpAndAny(Object... spkiSexpAndAny) {
            withSPKISexpAndAny(Arrays.asList(spkiSexpAndAny));
            return this;
        }

        @Override
        public SPKIDataType build() {
            if (_storedValue == null) {
                return this.init(new SPKIDataType());
            } else {
                return ((SPKIDataType) _storedValue);
            }
        }

        public SPKIDataType.Builder<_B> copyOf(final SPKIDataType _other) {
            _other.copyTo(this);
            return this;
        }

        public SPKIDataType.Builder<_B> copyOf(final SPKIDataType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SPKIDataType.Selector<SPKIDataType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SPKIDataType.Select _root() {
            return new SPKIDataType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, SPKIDataType.Selector<TRoot, TParent>> spkiSexpAndAny = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.spkiSexpAndAny!= null) {
                products.put("spkiSexpAndAny", this.spkiSexpAndAny.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, SPKIDataType.Selector<TRoot, TParent>> spkiSexpAndAny() {
            return ((this.spkiSexpAndAny == null)?this.spkiSexpAndAny = new com.kscs.util.jaxb.Selector<>(this._root, this, "spkiSexpAndAny"):this.spkiSexpAndAny);
        }

    }

}
