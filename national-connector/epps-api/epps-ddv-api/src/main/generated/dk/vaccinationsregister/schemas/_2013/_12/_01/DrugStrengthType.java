
package dk.vaccinationsregister.schemas._2013._12._01;

import java.math.BigDecimal;
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
 * <p>Java class for DrugStrengthType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DrugStrengthType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DrugStrengthValue" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthValueType" minOccurs="0"/>
 *         <element name="DrugStrengthUnitCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthUnitCodeType" minOccurs="0"/>
 *         <element name="DrugStrengthUnitText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthUnitTextType" minOccurs="0"/>
 *         <element name="DrugStrengthText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthTextType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugStrengthType", propOrder = {
    "drugStrengthValue",
    "drugStrengthUnitCode",
    "drugStrengthUnitText",
    "drugStrengthText"
})
public class DrugStrengthType {

    @XmlElement(name = "DrugStrengthValue")
    protected BigDecimal drugStrengthValue;
    @XmlElement(name = "DrugStrengthUnitCode")
    protected String drugStrengthUnitCode;
    @XmlElement(name = "DrugStrengthUnitText")
    protected String drugStrengthUnitText;
    @XmlElement(name = "DrugStrengthText")
    protected String drugStrengthText;

    /**
     * Gets the value of the drugStrengthValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDrugStrengthValue() {
        return drugStrengthValue;
    }

    /**
     * Sets the value of the drugStrengthValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDrugStrengthValue(BigDecimal value) {
        this.drugStrengthValue = value;
    }

    /**
     * Gets the value of the drugStrengthUnitCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrugStrengthUnitCode() {
        return drugStrengthUnitCode;
    }

    /**
     * Sets the value of the drugStrengthUnitCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrugStrengthUnitCode(String value) {
        this.drugStrengthUnitCode = value;
    }

    /**
     * Gets the value of the drugStrengthUnitText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrugStrengthUnitText() {
        return drugStrengthUnitText;
    }

    /**
     * Sets the value of the drugStrengthUnitText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrugStrengthUnitText(String value) {
        this.drugStrengthUnitText = value;
    }

    /**
     * Gets the value of the drugStrengthText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrugStrengthText() {
        return drugStrengthText;
    }

    /**
     * Sets the value of the drugStrengthText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrugStrengthText(String value) {
        this.drugStrengthText = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DrugStrengthType.Builder<_B> _other) {
        _other.drugStrengthValue = this.drugStrengthValue;
        _other.drugStrengthUnitCode = this.drugStrengthUnitCode;
        _other.drugStrengthUnitText = this.drugStrengthUnitText;
        _other.drugStrengthText = this.drugStrengthText;
    }

    public<_B >DrugStrengthType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DrugStrengthType.Builder<_B>(_parentBuilder, this, true);
    }

    public DrugStrengthType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DrugStrengthType.Builder<Void> builder() {
        return new DrugStrengthType.Builder<>(null, null, false);
    }

    public static<_B >DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType _other) {
        final DrugStrengthType.Builder<_B> _newBuilder = new DrugStrengthType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DrugStrengthType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree drugStrengthValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthValue"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthValuePropertyTree!= null):((drugStrengthValuePropertyTree == null)||(!drugStrengthValuePropertyTree.isLeaf())))) {
            _other.drugStrengthValue = this.drugStrengthValue;
        }
        final PropertyTree drugStrengthUnitCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthUnitCode"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthUnitCodePropertyTree!= null):((drugStrengthUnitCodePropertyTree == null)||(!drugStrengthUnitCodePropertyTree.isLeaf())))) {
            _other.drugStrengthUnitCode = this.drugStrengthUnitCode;
        }
        final PropertyTree drugStrengthUnitTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthUnitText"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthUnitTextPropertyTree!= null):((drugStrengthUnitTextPropertyTree == null)||(!drugStrengthUnitTextPropertyTree.isLeaf())))) {
            _other.drugStrengthUnitText = this.drugStrengthUnitText;
        }
        final PropertyTree drugStrengthTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthText"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthTextPropertyTree!= null):((drugStrengthTextPropertyTree == null)||(!drugStrengthTextPropertyTree.isLeaf())))) {
            _other.drugStrengthText = this.drugStrengthText;
        }
    }

    public<_B >DrugStrengthType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DrugStrengthType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DrugStrengthType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DrugStrengthType.Builder<_B> _newBuilder = new DrugStrengthType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DrugStrengthType.Builder<Void> copyExcept(final DrugStrengthType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DrugStrengthType.Builder<Void> copyOnly(final DrugStrengthType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DrugStrengthType _storedValue;
        private BigDecimal drugStrengthValue;
        private String drugStrengthUnitCode;
        private String drugStrengthUnitText;
        private String drugStrengthText;

        public Builder(final _B _parentBuilder, final DrugStrengthType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.drugStrengthValue = _other.drugStrengthValue;
                    this.drugStrengthUnitCode = _other.drugStrengthUnitCode;
                    this.drugStrengthUnitText = _other.drugStrengthUnitText;
                    this.drugStrengthText = _other.drugStrengthText;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DrugStrengthType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree drugStrengthValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthValue"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthValuePropertyTree!= null):((drugStrengthValuePropertyTree == null)||(!drugStrengthValuePropertyTree.isLeaf())))) {
                        this.drugStrengthValue = _other.drugStrengthValue;
                    }
                    final PropertyTree drugStrengthUnitCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthUnitCode"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthUnitCodePropertyTree!= null):((drugStrengthUnitCodePropertyTree == null)||(!drugStrengthUnitCodePropertyTree.isLeaf())))) {
                        this.drugStrengthUnitCode = _other.drugStrengthUnitCode;
                    }
                    final PropertyTree drugStrengthUnitTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthUnitText"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthUnitTextPropertyTree!= null):((drugStrengthUnitTextPropertyTree == null)||(!drugStrengthUnitTextPropertyTree.isLeaf())))) {
                        this.drugStrengthUnitText = _other.drugStrengthUnitText;
                    }
                    final PropertyTree drugStrengthTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrengthText"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthTextPropertyTree!= null):((drugStrengthTextPropertyTree == null)||(!drugStrengthTextPropertyTree.isLeaf())))) {
                        this.drugStrengthText = _other.drugStrengthText;
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

        protected<_P extends DrugStrengthType >_P init(final _P _product) {
            _product.drugStrengthValue = this.drugStrengthValue;
            _product.drugStrengthUnitCode = this.drugStrengthUnitCode;
            _product.drugStrengthUnitText = this.drugStrengthUnitText;
            _product.drugStrengthText = this.drugStrengthText;
            return _product;
        }

        /**
         * Sets the new value of "drugStrengthValue" (any previous value will be replaced)
         * 
         * @param drugStrengthValue
         *     New value of the "drugStrengthValue" property.
         */
        public DrugStrengthType.Builder<_B> withDrugStrengthValue(final BigDecimal drugStrengthValue) {
            this.drugStrengthValue = drugStrengthValue;
            return this;
        }

        /**
         * Sets the new value of "drugStrengthUnitCode" (any previous value will be
         * replaced)
         * 
         * @param drugStrengthUnitCode
         *     New value of the "drugStrengthUnitCode" property.
         */
        public DrugStrengthType.Builder<_B> withDrugStrengthUnitCode(final String drugStrengthUnitCode) {
            this.drugStrengthUnitCode = drugStrengthUnitCode;
            return this;
        }

        /**
         * Sets the new value of "drugStrengthUnitText" (any previous value will be
         * replaced)
         * 
         * @param drugStrengthUnitText
         *     New value of the "drugStrengthUnitText" property.
         */
        public DrugStrengthType.Builder<_B> withDrugStrengthUnitText(final String drugStrengthUnitText) {
            this.drugStrengthUnitText = drugStrengthUnitText;
            return this;
        }

        /**
         * Sets the new value of "drugStrengthText" (any previous value will be replaced)
         * 
         * @param drugStrengthText
         *     New value of the "drugStrengthText" property.
         */
        public DrugStrengthType.Builder<_B> withDrugStrengthText(final String drugStrengthText) {
            this.drugStrengthText = drugStrengthText;
            return this;
        }

        @Override
        public DrugStrengthType build() {
            if (_storedValue == null) {
                return this.init(new DrugStrengthType());
            } else {
                return ((DrugStrengthType) _storedValue);
            }
        }

        public DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType _other) {
            _other.copyTo(this);
            return this;
        }

        public DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DrugStrengthType.Selector<DrugStrengthType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DrugStrengthType.Select _root() {
            return new DrugStrengthType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthValue = null;
        private com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthUnitCode = null;
        private com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthUnitText = null;
        private com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthText = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.drugStrengthValue!= null) {
                products.put("drugStrengthValue", this.drugStrengthValue.init());
            }
            if (this.drugStrengthUnitCode!= null) {
                products.put("drugStrengthUnitCode", this.drugStrengthUnitCode.init());
            }
            if (this.drugStrengthUnitText!= null) {
                products.put("drugStrengthUnitText", this.drugStrengthUnitText.init());
            }
            if (this.drugStrengthText!= null) {
                products.put("drugStrengthText", this.drugStrengthText.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthValue() {
            return ((this.drugStrengthValue == null)?this.drugStrengthValue = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugStrengthValue"):this.drugStrengthValue);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthUnitCode() {
            return ((this.drugStrengthUnitCode == null)?this.drugStrengthUnitCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugStrengthUnitCode"):this.drugStrengthUnitCode);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthUnitText() {
            return ((this.drugStrengthUnitText == null)?this.drugStrengthUnitText = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugStrengthUnitText"):this.drugStrengthUnitText);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> drugStrengthText() {
            return ((this.drugStrengthText == null)?this.drugStrengthText = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugStrengthText"):this.drugStrengthText);
        }

    }

}
