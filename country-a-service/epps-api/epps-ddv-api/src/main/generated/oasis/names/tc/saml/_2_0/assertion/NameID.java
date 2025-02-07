
package oasis.names.tc.saml._2_0.assertion;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <simpleContent>
 *     <extension base="<http://www.w3.org/2001/XMLSchema>string">
 *       <attribute name="Format" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "NameID")
public class NameID {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "Format", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String format;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final NameID.Builder<_B> _other) {
        _other.value = this.value;
        _other.format = this.format;
    }

    public<_B >NameID.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new NameID.Builder<_B>(_parentBuilder, this, true);
    }

    public NameID.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static NameID.Builder<Void> builder() {
        return new NameID.Builder<>(null, null, false);
    }

    public static<_B >NameID.Builder<_B> copyOf(final NameID _other) {
        final NameID.Builder<_B> _newBuilder = new NameID.Builder<>(null, null, false);
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
    public<_B >void copyTo(final NameID.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
            _other.value = this.value;
        }
        final PropertyTree formatPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("format"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(formatPropertyTree!= null):((formatPropertyTree == null)||(!formatPropertyTree.isLeaf())))) {
            _other.format = this.format;
        }
    }

    public<_B >NameID.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new NameID.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public NameID.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >NameID.Builder<_B> copyOf(final NameID _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final NameID.Builder<_B> _newBuilder = new NameID.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static NameID.Builder<Void> copyExcept(final NameID _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static NameID.Builder<Void> copyOnly(final NameID _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final NameID _storedValue;
        private String value;
        private String format;

        public Builder(final _B _parentBuilder, final NameID _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.value = _other.value;
                    this.format = _other.format;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final NameID _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
                        this.value = _other.value;
                    }
                    final PropertyTree formatPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("format"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(formatPropertyTree!= null):((formatPropertyTree == null)||(!formatPropertyTree.isLeaf())))) {
                        this.format = _other.format;
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

        protected<_P extends NameID >_P init(final _P _product) {
            _product.value = this.value;
            _product.format = this.format;
            return _product;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        public NameID.Builder<_B> withValue(final String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the new value of "format" (any previous value will be replaced)
         * 
         * @param format
         *     New value of the "format" property.
         */
        public NameID.Builder<_B> withFormat(final String format) {
            this.format = format;
            return this;
        }

        @Override
        public NameID build() {
            if (_storedValue == null) {
                return this.init(new NameID());
            } else {
                return ((NameID) _storedValue);
            }
        }

        public NameID.Builder<_B> copyOf(final NameID _other) {
            _other.copyTo(this);
            return this;
        }

        public NameID.Builder<_B> copyOf(final NameID.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends NameID.Selector<NameID.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static NameID.Select _root() {
            return new NameID.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, NameID.Selector<TRoot, TParent>> value = null;
        private com.kscs.util.jaxb.Selector<TRoot, NameID.Selector<TRoot, TParent>> format = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.value!= null) {
                products.put("value", this.value.init());
            }
            if (this.format!= null) {
                products.put("format", this.format.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, NameID.Selector<TRoot, TParent>> value() {
            return ((this.value == null)?this.value = new com.kscs.util.jaxb.Selector<>(this._root, this, "value"):this.value);
        }

        public com.kscs.util.jaxb.Selector<TRoot, NameID.Selector<TRoot, TParent>> format() {
            return ((this.format == null)?this.format = new com.kscs.util.jaxb.Selector<>(this._root, this, "format"):this.format);
        }

    }

}
