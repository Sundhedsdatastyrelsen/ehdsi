
package dk.vaccinationsregister.schemas._2013._12._01;

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
 * <p>Java class for PartlyDefinedEffectuatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PartlyDefinedEffectuatorType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="EffectuatedByName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedByNameType" minOccurs="0"/>
 *         <element name="EffectuatedByOrganisationName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedByOrganisationNameType" minOccurs="0"/>
 *         <element name="EffectuatedInCountryCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedInCountryCodeType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartlyDefinedEffectuatorType", propOrder = {
    "effectuatedByName",
    "effectuatedByOrganisationName",
    "effectuatedInCountryCode"
})
public class PartlyDefinedEffectuatorType {

    @XmlElement(name = "EffectuatedByName")
    protected String effectuatedByName;
    @XmlElement(name = "EffectuatedByOrganisationName")
    protected String effectuatedByOrganisationName;
    @XmlElement(name = "EffectuatedInCountryCode")
    protected String effectuatedInCountryCode;

    /**
     * Gets the value of the effectuatedByName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectuatedByName() {
        return effectuatedByName;
    }

    /**
     * Sets the value of the effectuatedByName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectuatedByName(String value) {
        this.effectuatedByName = value;
    }

    /**
     * Gets the value of the effectuatedByOrganisationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectuatedByOrganisationName() {
        return effectuatedByOrganisationName;
    }

    /**
     * Sets the value of the effectuatedByOrganisationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectuatedByOrganisationName(String value) {
        this.effectuatedByOrganisationName = value;
    }

    /**
     * Gets the value of the effectuatedInCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectuatedInCountryCode() {
        return effectuatedInCountryCode;
    }

    /**
     * Sets the value of the effectuatedInCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectuatedInCountryCode(String value) {
        this.effectuatedInCountryCode = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final PartlyDefinedEffectuatorType.Builder<_B> _other) {
        _other.effectuatedByName = this.effectuatedByName;
        _other.effectuatedByOrganisationName = this.effectuatedByOrganisationName;
        _other.effectuatedInCountryCode = this.effectuatedInCountryCode;
    }

    public<_B >PartlyDefinedEffectuatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new PartlyDefinedEffectuatorType.Builder<_B>(_parentBuilder, this, true);
    }

    public PartlyDefinedEffectuatorType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static PartlyDefinedEffectuatorType.Builder<Void> builder() {
        return new PartlyDefinedEffectuatorType.Builder<>(null, null, false);
    }

    public static<_B >PartlyDefinedEffectuatorType.Builder<_B> copyOf(final PartlyDefinedEffectuatorType _other) {
        final PartlyDefinedEffectuatorType.Builder<_B> _newBuilder = new PartlyDefinedEffectuatorType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final PartlyDefinedEffectuatorType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree effectuatedByNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByNamePropertyTree!= null):((effectuatedByNamePropertyTree == null)||(!effectuatedByNamePropertyTree.isLeaf())))) {
            _other.effectuatedByName = this.effectuatedByName;
        }
        final PropertyTree effectuatedByOrganisationNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationNamePropertyTree!= null):((effectuatedByOrganisationNamePropertyTree == null)||(!effectuatedByOrganisationNamePropertyTree.isLeaf())))) {
            _other.effectuatedByOrganisationName = this.effectuatedByOrganisationName;
        }
        final PropertyTree effectuatedInCountryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedInCountryCode"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedInCountryCodePropertyTree!= null):((effectuatedInCountryCodePropertyTree == null)||(!effectuatedInCountryCodePropertyTree.isLeaf())))) {
            _other.effectuatedInCountryCode = this.effectuatedInCountryCode;
        }
    }

    public<_B >PartlyDefinedEffectuatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new PartlyDefinedEffectuatorType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public PartlyDefinedEffectuatorType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >PartlyDefinedEffectuatorType.Builder<_B> copyOf(final PartlyDefinedEffectuatorType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PartlyDefinedEffectuatorType.Builder<_B> _newBuilder = new PartlyDefinedEffectuatorType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static PartlyDefinedEffectuatorType.Builder<Void> copyExcept(final PartlyDefinedEffectuatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static PartlyDefinedEffectuatorType.Builder<Void> copyOnly(final PartlyDefinedEffectuatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final PartlyDefinedEffectuatorType _storedValue;
        private String effectuatedByName;
        private String effectuatedByOrganisationName;
        private String effectuatedInCountryCode;

        public Builder(final _B _parentBuilder, final PartlyDefinedEffectuatorType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.effectuatedByName = _other.effectuatedByName;
                    this.effectuatedByOrganisationName = _other.effectuatedByOrganisationName;
                    this.effectuatedInCountryCode = _other.effectuatedInCountryCode;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final PartlyDefinedEffectuatorType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree effectuatedByNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByNamePropertyTree!= null):((effectuatedByNamePropertyTree == null)||(!effectuatedByNamePropertyTree.isLeaf())))) {
                        this.effectuatedByName = _other.effectuatedByName;
                    }
                    final PropertyTree effectuatedByOrganisationNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationNamePropertyTree!= null):((effectuatedByOrganisationNamePropertyTree == null)||(!effectuatedByOrganisationNamePropertyTree.isLeaf())))) {
                        this.effectuatedByOrganisationName = _other.effectuatedByOrganisationName;
                    }
                    final PropertyTree effectuatedInCountryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedInCountryCode"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedInCountryCodePropertyTree!= null):((effectuatedInCountryCodePropertyTree == null)||(!effectuatedInCountryCodePropertyTree.isLeaf())))) {
                        this.effectuatedInCountryCode = _other.effectuatedInCountryCode;
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

        protected<_P extends PartlyDefinedEffectuatorType >_P init(final _P _product) {
            _product.effectuatedByName = this.effectuatedByName;
            _product.effectuatedByOrganisationName = this.effectuatedByOrganisationName;
            _product.effectuatedInCountryCode = this.effectuatedInCountryCode;
            return _product;
        }

        /**
         * Sets the new value of "effectuatedByName" (any previous value will be replaced)
         * 
         * @param effectuatedByName
         *     New value of the "effectuatedByName" property.
         */
        public PartlyDefinedEffectuatorType.Builder<_B> withEffectuatedByName(final String effectuatedByName) {
            this.effectuatedByName = effectuatedByName;
            return this;
        }

        /**
         * Sets the new value of "effectuatedByOrganisationName" (any previous value will
         * be replaced)
         * 
         * @param effectuatedByOrganisationName
         *     New value of the "effectuatedByOrganisationName" property.
         */
        public PartlyDefinedEffectuatorType.Builder<_B> withEffectuatedByOrganisationName(final String effectuatedByOrganisationName) {
            this.effectuatedByOrganisationName = effectuatedByOrganisationName;
            return this;
        }

        /**
         * Sets the new value of "effectuatedInCountryCode" (any previous value will be
         * replaced)
         * 
         * @param effectuatedInCountryCode
         *     New value of the "effectuatedInCountryCode" property.
         */
        public PartlyDefinedEffectuatorType.Builder<_B> withEffectuatedInCountryCode(final String effectuatedInCountryCode) {
            this.effectuatedInCountryCode = effectuatedInCountryCode;
            return this;
        }

        @Override
        public PartlyDefinedEffectuatorType build() {
            if (_storedValue == null) {
                return this.init(new PartlyDefinedEffectuatorType());
            } else {
                return ((PartlyDefinedEffectuatorType) _storedValue);
            }
        }

        public PartlyDefinedEffectuatorType.Builder<_B> copyOf(final PartlyDefinedEffectuatorType _other) {
            _other.copyTo(this);
            return this;
        }

        public PartlyDefinedEffectuatorType.Builder<_B> copyOf(final PartlyDefinedEffectuatorType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends PartlyDefinedEffectuatorType.Selector<PartlyDefinedEffectuatorType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static PartlyDefinedEffectuatorType.Select _root() {
            return new PartlyDefinedEffectuatorType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, PartlyDefinedEffectuatorType.Selector<TRoot, TParent>> effectuatedByName = null;
        private com.kscs.util.jaxb.Selector<TRoot, PartlyDefinedEffectuatorType.Selector<TRoot, TParent>> effectuatedByOrganisationName = null;
        private com.kscs.util.jaxb.Selector<TRoot, PartlyDefinedEffectuatorType.Selector<TRoot, TParent>> effectuatedInCountryCode = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.effectuatedByName!= null) {
                products.put("effectuatedByName", this.effectuatedByName.init());
            }
            if (this.effectuatedByOrganisationName!= null) {
                products.put("effectuatedByOrganisationName", this.effectuatedByOrganisationName.init());
            }
            if (this.effectuatedInCountryCode!= null) {
                products.put("effectuatedInCountryCode", this.effectuatedInCountryCode.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, PartlyDefinedEffectuatorType.Selector<TRoot, TParent>> effectuatedByName() {
            return ((this.effectuatedByName == null)?this.effectuatedByName = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedByName"):this.effectuatedByName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PartlyDefinedEffectuatorType.Selector<TRoot, TParent>> effectuatedByOrganisationName() {
            return ((this.effectuatedByOrganisationName == null)?this.effectuatedByOrganisationName = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedByOrganisationName"):this.effectuatedByOrganisationName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PartlyDefinedEffectuatorType.Selector<TRoot, TParent>> effectuatedInCountryCode() {
            return ((this.effectuatedInCountryCode == null)?this.effectuatedInCountryCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedInCountryCode"):this.effectuatedInCountryCode);
        }

    }

}
