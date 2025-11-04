
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
import jakarta.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * This complexType defines header block to use for security-relevant data directed at a specific SOAP actor.
 * 
 * <p>Java class for SecurityHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SecurityHeaderType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <any processContents='lax' maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityHeaderType", propOrder = {
    "any"
})
public class SecurityHeaderType {

    @XmlAnyElement(lax = true)
    protected List<Object> any;
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
    public<_B >void copyTo(final SecurityHeaderType.Builder<_B> _other) {
    }

    public<_B >SecurityHeaderType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SecurityHeaderType.Builder<_B>(_parentBuilder, this, true);
    }

    public SecurityHeaderType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SecurityHeaderType.Builder<Void> builder() {
        return new SecurityHeaderType.Builder<>(null, null, false);
    }

    public static<_B >SecurityHeaderType.Builder<_B> copyOf(final SecurityHeaderType _other) {
        final SecurityHeaderType.Builder<_B> _newBuilder = new SecurityHeaderType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SecurityHeaderType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
    }

    public<_B >SecurityHeaderType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SecurityHeaderType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SecurityHeaderType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SecurityHeaderType.Builder<_B> copyOf(final SecurityHeaderType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SecurityHeaderType.Builder<_B> _newBuilder = new SecurityHeaderType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SecurityHeaderType.Builder<Void> copyExcept(final SecurityHeaderType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SecurityHeaderType.Builder<Void> copyOnly(final SecurityHeaderType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SecurityHeaderType _storedValue;
        private List<Buildable> any;

        public Builder(final _B _parentBuilder, final SecurityHeaderType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final SecurityHeaderType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
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

        protected<_P extends SecurityHeaderType >_P init(final _P _product) {
            if (this.any!= null) {
                final List<Object> any = new ArrayList<>(this.any.size());
                for (Buildable _item: this.any) {
                    any.add(((Object) _item.build()));
                }
                _product.any = any;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "any"
         * 
         * @param any
         *     Items to add to the value of the "any" property
         */
        public SecurityHeaderType.Builder<_B> addAny(final Iterable<?> any) {
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
        public SecurityHeaderType.Builder<_B> withAny(final Iterable<?> any) {
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
        public SecurityHeaderType.Builder<_B> addAny(Object... any) {
            addAny(Arrays.asList(any));
            return this;
        }

        /**
         * Sets the new value of "any" (any previous value will be replaced)
         * 
         * @param any
         *     New value of the "any" property.
         */
        public SecurityHeaderType.Builder<_B> withAny(Object... any) {
            withAny(Arrays.asList(any));
            return this;
        }

        @Override
        public SecurityHeaderType build() {
            if (_storedValue == null) {
                return this.init(new SecurityHeaderType());
            } else {
                return ((SecurityHeaderType) _storedValue);
            }
        }

        public SecurityHeaderType.Builder<_B> copyOf(final SecurityHeaderType _other) {
            _other.copyTo(this);
            return this;
        }

        public SecurityHeaderType.Builder<_B> copyOf(final SecurityHeaderType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SecurityHeaderType.Selector<SecurityHeaderType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SecurityHeaderType.Select _root() {
            return new SecurityHeaderType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, SecurityHeaderType.Selector<TRoot, TParent>> any = null;

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
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, SecurityHeaderType.Selector<TRoot, TParent>> any() {
            return ((this.any == null)?this.any = new com.kscs.util.jaxb.Selector<>(this._root, this, "any"):this.any);
        }

    }

}
