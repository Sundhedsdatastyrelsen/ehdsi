
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * This type represents a reference to an embedded security token.
 * 
 * <p>Java class for EmbeddedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EmbeddedType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded" minOccurs="0">
 *         <any processContents='lax'/>
 *       </choice>
 *       <attribute name="ValueType" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmbeddedType", propOrder = {
    "any"
})
public class EmbeddedType {

    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "ValueType")
    @XmlSchemaType(name = "anyURI")
    protected String valueType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     * 
     * 
     * @return
     *     The value of the any property.
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<>();
        }
        return this.any;
    }

    /**
     * Gets the value of the valueType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * Sets the value of the valueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueType(String value) {
        this.valueType = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final EmbeddedType.Builder<_B> _other) {
        _other.valueType = this.valueType;
    }

    public<_B >EmbeddedType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new EmbeddedType.Builder<_B>(_parentBuilder, this, true);
    }

    public EmbeddedType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static EmbeddedType.Builder<Void> builder() {
        return new EmbeddedType.Builder<>(null, null, false);
    }

    public static<_B >EmbeddedType.Builder<_B> copyOf(final EmbeddedType _other) {
        final EmbeddedType.Builder<_B> _newBuilder = new EmbeddedType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final EmbeddedType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree valueTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("valueType"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valueTypePropertyTree!= null):((valueTypePropertyTree == null)||(!valueTypePropertyTree.isLeaf())))) {
            _other.valueType = this.valueType;
        }
    }

    public<_B >EmbeddedType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new EmbeddedType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public EmbeddedType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >EmbeddedType.Builder<_B> copyOf(final EmbeddedType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final EmbeddedType.Builder<_B> _newBuilder = new EmbeddedType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static EmbeddedType.Builder<Void> copyExcept(final EmbeddedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static EmbeddedType.Builder<Void> copyOnly(final EmbeddedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final EmbeddedType _storedValue;
        private List<Buildable> any;
        private String valueType;

        public Builder(final _B _parentBuilder, final EmbeddedType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.valueType = _other.valueType;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final EmbeddedType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree valueTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("valueType"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valueTypePropertyTree!= null):((valueTypePropertyTree == null)||(!valueTypePropertyTree.isLeaf())))) {
                        this.valueType = _other.valueType;
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

        protected<_P extends EmbeddedType >_P init(final _P _product) {
            if (this.any!= null) {
                final List<Object> any = new ArrayList<>(this.any.size());
                for (Buildable _item: this.any) {
                    any.add(((Object) _item.build()));
                }
                _product.any = any;
            }
            _product.valueType = this.valueType;
            return _product;
        }

        /**
         * Adds the given items to the value of "any"
         * 
         * @param any
         *     Items to add to the value of the "any" property
         */
        public EmbeddedType.Builder<_B> addAny(final Iterable<?> any) {
            if (any!= null) {
                if (this.any == null) {
                    this.any = new ArrayList<>();
                }
                for (Object _item: any) {
                    this.any.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "any" (any previous value will be replaced)
         * 
         * @param any
         *     New value of the "any" property.
         */
        public EmbeddedType.Builder<_B> withAny(final Iterable<?> any) {
            if (this.any!= null) {
                this.any.clear();
            }
            return addAny(any);
        }

        /**
         * Adds the given items to the value of "any"
         * 
         * @param any
         *     Items to add to the value of the "any" property
         */
        public EmbeddedType.Builder<_B> addAny(Object... any) {
            addAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "any" (any previous value will be replaced)
         * 
         * @param any
         *     New value of the "any" property.
         */
        public EmbeddedType.Builder<_B> withAny(Object... any) {
            withAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "valueType" (any previous value will be replaced)
         * 
         * @param valueType
         *     New value of the "valueType" property.
         */
        public EmbeddedType.Builder<_B> withValueType(final String valueType) {
            this.valueType = valueType;
            return this;
        }

        @Override
        public EmbeddedType build() {
            if (_storedValue == null) {
                return this.init(new EmbeddedType());
            } else {
                return ((EmbeddedType) _storedValue);
            }
        }

        public EmbeddedType.Builder<_B> copyOf(final EmbeddedType _other) {
            _other.copyTo(this);
            return this;
        }

        public EmbeddedType.Builder<_B> copyOf(final EmbeddedType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends EmbeddedType.Selector<EmbeddedType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static EmbeddedType.Select _root() {
            return new EmbeddedType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, EmbeddedType.Selector<TRoot, TParent>> any = null;
        private com.kscs.util.jaxb.Selector<TRoot, EmbeddedType.Selector<TRoot, TParent>> valueType = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.any!= null) {
                products.put("any", this.any.init());
            }
            if (this.valueType!= null) {
                products.put("valueType", this.valueType.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, EmbeddedType.Selector<TRoot, TParent>> any() {
            return ((this.any == null)?this.any = new com.kscs.util.jaxb.Selector<>(this._root, this, "any"):this.any);
        }

        public com.kscs.util.jaxb.Selector<TRoot, EmbeddedType.Selector<TRoot, TParent>> valueType() {
            return ((this.valueType == null)?this.valueType = new com.kscs.util.jaxb.Selector<>(this._root, this, "valueType"):this.valueType);
        }

    }

}
