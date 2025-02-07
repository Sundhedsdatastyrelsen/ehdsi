
package dk.sdsd.ddv.dgws._2010._08;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for OrgUsingID complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="OrgUsingID">
 *   <simpleContent>
 *     <extension base="<http://www.sdsd.dk/dgws/2010/08>OrgUsingIDString">
 *       <attribute name="NameFormat" use="required" type="{http://www.sdsd.dk/dgws/2010/08}NameFormat" />
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgUsingID", propOrder = {
    "value"
})
public class OrgUsingID {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "NameFormat", required = true)
    protected NameFormat nameFormat;

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
     * Gets the value of the nameFormat property.
     * 
     * @return
     *     possible object is
     *     {@link NameFormat }
     *     
     */
    public NameFormat getNameFormat() {
        return nameFormat;
    }

    /**
     * Sets the value of the nameFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameFormat }
     *     
     */
    public void setNameFormat(NameFormat value) {
        this.nameFormat = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final OrgUsingID.Builder<_B> _other) {
        _other.value = this.value;
        _other.nameFormat = this.nameFormat;
    }

    public<_B >OrgUsingID.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new OrgUsingID.Builder<_B>(_parentBuilder, this, true);
    }

    public OrgUsingID.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static OrgUsingID.Builder<Void> builder() {
        return new OrgUsingID.Builder<>(null, null, false);
    }

    public static<_B >OrgUsingID.Builder<_B> copyOf(final OrgUsingID _other) {
        final OrgUsingID.Builder<_B> _newBuilder = new OrgUsingID.Builder<>(null, null, false);
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
    public<_B >void copyTo(final OrgUsingID.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
            _other.value = this.value;
        }
        final PropertyTree nameFormatPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("nameFormat"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(nameFormatPropertyTree!= null):((nameFormatPropertyTree == null)||(!nameFormatPropertyTree.isLeaf())))) {
            _other.nameFormat = this.nameFormat;
        }
    }

    public<_B >OrgUsingID.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new OrgUsingID.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public OrgUsingID.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >OrgUsingID.Builder<_B> copyOf(final OrgUsingID _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final OrgUsingID.Builder<_B> _newBuilder = new OrgUsingID.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static OrgUsingID.Builder<Void> copyExcept(final OrgUsingID _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static OrgUsingID.Builder<Void> copyOnly(final OrgUsingID _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final OrgUsingID _storedValue;
        private String value;
        private NameFormat nameFormat;

        public Builder(final _B _parentBuilder, final OrgUsingID _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.value = _other.value;
                    this.nameFormat = _other.nameFormat;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final OrgUsingID _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
                        this.value = _other.value;
                    }
                    final PropertyTree nameFormatPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("nameFormat"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(nameFormatPropertyTree!= null):((nameFormatPropertyTree == null)||(!nameFormatPropertyTree.isLeaf())))) {
                        this.nameFormat = _other.nameFormat;
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

        protected<_P extends OrgUsingID >_P init(final _P _product) {
            _product.value = this.value;
            _product.nameFormat = this.nameFormat;
            return _product;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        public OrgUsingID.Builder<_B> withValue(final String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the new value of "nameFormat" (any previous value will be replaced)
         * 
         * @param nameFormat
         *     New value of the "nameFormat" property.
         */
        public OrgUsingID.Builder<_B> withNameFormat(final NameFormat nameFormat) {
            this.nameFormat = nameFormat;
            return this;
        }

        @Override
        public OrgUsingID build() {
            if (_storedValue == null) {
                return this.init(new OrgUsingID());
            } else {
                return ((OrgUsingID) _storedValue);
            }
        }

        public OrgUsingID.Builder<_B> copyOf(final OrgUsingID _other) {
            _other.copyTo(this);
            return this;
        }

        public OrgUsingID.Builder<_B> copyOf(final OrgUsingID.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends OrgUsingID.Selector<OrgUsingID.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static OrgUsingID.Select _root() {
            return new OrgUsingID.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, OrgUsingID.Selector<TRoot, TParent>> value = null;
        private com.kscs.util.jaxb.Selector<TRoot, OrgUsingID.Selector<TRoot, TParent>> nameFormat = null;

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
            if (this.nameFormat!= null) {
                products.put("nameFormat", this.nameFormat.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, OrgUsingID.Selector<TRoot, TParent>> value() {
            return ((this.value == null)?this.value = new com.kscs.util.jaxb.Selector<>(this._root, this, "value"):this.value);
        }

        public com.kscs.util.jaxb.Selector<TRoot, OrgUsingID.Selector<TRoot, TParent>> nameFormat() {
            return ((this.nameFormat == null)?this.nameFormat = new com.kscs.util.jaxb.Selector<>(this._root, this, "nameFormat"):this.nameFormat);
        }

    }

}
