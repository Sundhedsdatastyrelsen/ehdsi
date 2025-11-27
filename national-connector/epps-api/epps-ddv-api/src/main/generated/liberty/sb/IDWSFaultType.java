
package liberty.sb;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IDWSFaultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="IDWSFaultType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{urn:liberty:sb}faultcode" minOccurs="0"/>
 *         <element ref="{urn:liberty:sb}faultstring" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IDWSFaultType", propOrder = {
    "faultcode",
    "faultstring"
})
public class IDWSFaultType {

    protected String faultcode;
    protected String faultstring;

    /**
     * Gets the value of the faultcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultcode() {
        return faultcode;
    }

    /**
     * Sets the value of the faultcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultcode(String value) {
        this.faultcode = value;
    }

    /**
     * Gets the value of the faultstring property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * Sets the value of the faultstring property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultstring(String value) {
        this.faultstring = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final IDWSFaultType.Builder<_B> _other) {
        _other.faultcode = this.faultcode;
        _other.faultstring = this.faultstring;
    }

    public<_B >IDWSFaultType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new IDWSFaultType.Builder<_B>(_parentBuilder, this, true);
    }

    public IDWSFaultType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static IDWSFaultType.Builder<Void> builder() {
        return new IDWSFaultType.Builder<>(null, null, false);
    }

    public static<_B >IDWSFaultType.Builder<_B> copyOf(final IDWSFaultType _other) {
        final IDWSFaultType.Builder<_B> _newBuilder = new IDWSFaultType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final IDWSFaultType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree faultcodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("faultcode"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(faultcodePropertyTree!= null):((faultcodePropertyTree == null)||(!faultcodePropertyTree.isLeaf())))) {
            _other.faultcode = this.faultcode;
        }
        final PropertyTree faultstringPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("faultstring"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(faultstringPropertyTree!= null):((faultstringPropertyTree == null)||(!faultstringPropertyTree.isLeaf())))) {
            _other.faultstring = this.faultstring;
        }
    }

    public<_B >IDWSFaultType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new IDWSFaultType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public IDWSFaultType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >IDWSFaultType.Builder<_B> copyOf(final IDWSFaultType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final IDWSFaultType.Builder<_B> _newBuilder = new IDWSFaultType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static IDWSFaultType.Builder<Void> copyExcept(final IDWSFaultType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static IDWSFaultType.Builder<Void> copyOnly(final IDWSFaultType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final IDWSFaultType _storedValue;
        private String faultcode;
        private String faultstring;

        public Builder(final _B _parentBuilder, final IDWSFaultType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.faultcode = _other.faultcode;
                    this.faultstring = _other.faultstring;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final IDWSFaultType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree faultcodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("faultcode"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(faultcodePropertyTree!= null):((faultcodePropertyTree == null)||(!faultcodePropertyTree.isLeaf())))) {
                        this.faultcode = _other.faultcode;
                    }
                    final PropertyTree faultstringPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("faultstring"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(faultstringPropertyTree!= null):((faultstringPropertyTree == null)||(!faultstringPropertyTree.isLeaf())))) {
                        this.faultstring = _other.faultstring;
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

        protected<_P extends IDWSFaultType >_P init(final _P _product) {
            _product.faultcode = this.faultcode;
            _product.faultstring = this.faultstring;
            return _product;
        }

        /**
         * Sets the new value of "faultcode" (any previous value will be replaced)
         * 
         * @param faultcode
         *     New value of the "faultcode" property.
         */
        public IDWSFaultType.Builder<_B> withFaultcode(final String faultcode) {
            this.faultcode = faultcode;
            return this;
        }

        /**
         * Sets the new value of "faultstring" (any previous value will be replaced)
         * 
         * @param faultstring
         *     New value of the "faultstring" property.
         */
        public IDWSFaultType.Builder<_B> withFaultstring(final String faultstring) {
            this.faultstring = faultstring;
            return this;
        }

        @Override
        public IDWSFaultType build() {
            if (_storedValue == null) {
                return this.init(new IDWSFaultType());
            } else {
                return ((IDWSFaultType) _storedValue);
            }
        }

        public IDWSFaultType.Builder<_B> copyOf(final IDWSFaultType _other) {
            _other.copyTo(this);
            return this;
        }

        public IDWSFaultType.Builder<_B> copyOf(final IDWSFaultType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends IDWSFaultType.Selector<IDWSFaultType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static IDWSFaultType.Select _root() {
            return new IDWSFaultType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, IDWSFaultType.Selector<TRoot, TParent>> faultcode = null;
        private com.kscs.util.jaxb.Selector<TRoot, IDWSFaultType.Selector<TRoot, TParent>> faultstring = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.faultcode!= null) {
                products.put("faultcode", this.faultcode.init());
            }
            if (this.faultstring!= null) {
                products.put("faultstring", this.faultstring.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, IDWSFaultType.Selector<TRoot, TParent>> faultcode() {
            return ((this.faultcode == null)?this.faultcode = new com.kscs.util.jaxb.Selector<>(this._root, this, "faultcode"):this.faultcode);
        }

        public com.kscs.util.jaxb.Selector<TRoot, IDWSFaultType.Selector<TRoot, TParent>> faultstring() {
            return ((this.faultstring == null)?this.faultstring = new com.kscs.util.jaxb.Selector<>(this._root, this, "faultstring"):this.faultstring);
        }

    }

}
