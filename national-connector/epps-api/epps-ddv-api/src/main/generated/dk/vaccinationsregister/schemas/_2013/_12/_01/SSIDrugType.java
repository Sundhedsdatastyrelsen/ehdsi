
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
 * <p>Java class for SSIDrugType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SSIDrugType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="ATC" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ATCType" minOccurs="0"/>
 *         <element name="DrugIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugIdentifierType" minOccurs="0"/>
 *         <element name="DrugName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugNameType"/>
 *         <element name="DrugForm" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugFormType" minOccurs="0"/>
 *         <element name="DrugStrength" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SSIDrugType", propOrder = {
    "atc",
    "drugIdentifier",
    "drugName",
    "drugForm",
    "drugStrength"
})
public class SSIDrugType {

    @XmlElement(name = "ATC")
    protected ATCType atc;
    @XmlElement(name = "DrugIdentifier")
    protected Long drugIdentifier;
    @XmlElement(name = "DrugName", required = true)
    protected String drugName;
    @XmlElement(name = "DrugForm")
    protected DrugFormType drugForm;
    @XmlElement(name = "DrugStrength")
    protected DrugStrengthType drugStrength;

    /**
     * Gets the value of the atc property.
     * 
     * @return
     *     possible object is
     *     {@link ATCType }
     *     
     */
    public ATCType getATC() {
        return atc;
    }

    /**
     * Sets the value of the atc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ATCType }
     *     
     */
    public void setATC(ATCType value) {
        this.atc = value;
    }

    /**
     * Gets the value of the drugIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDrugIdentifier() {
        return drugIdentifier;
    }

    /**
     * Sets the value of the drugIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDrugIdentifier(Long value) {
        this.drugIdentifier = value;
    }

    /**
     * Gets the value of the drugName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * Sets the value of the drugName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrugName(String value) {
        this.drugName = value;
    }

    /**
     * Gets the value of the drugForm property.
     * 
     * @return
     *     possible object is
     *     {@link DrugFormType }
     *     
     */
    public DrugFormType getDrugForm() {
        return drugForm;
    }

    /**
     * Sets the value of the drugForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DrugFormType }
     *     
     */
    public void setDrugForm(DrugFormType value) {
        this.drugForm = value;
    }

    /**
     * Gets the value of the drugStrength property.
     * 
     * @return
     *     possible object is
     *     {@link DrugStrengthType }
     *     
     */
    public DrugStrengthType getDrugStrength() {
        return drugStrength;
    }

    /**
     * Sets the value of the drugStrength property.
     * 
     * @param value
     *     allowed object is
     *     {@link DrugStrengthType }
     *     
     */
    public void setDrugStrength(DrugStrengthType value) {
        this.drugStrength = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final SSIDrugType.Builder<_B> _other) {
        _other.atc = ((this.atc == null)?null:this.atc.newCopyBuilder(_other));
        _other.drugIdentifier = this.drugIdentifier;
        _other.drugName = this.drugName;
        _other.drugForm = ((this.drugForm == null)?null:this.drugForm.newCopyBuilder(_other));
        _other.drugStrength = ((this.drugStrength == null)?null:this.drugStrength.newCopyBuilder(_other));
    }

    public<_B >SSIDrugType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SSIDrugType.Builder<_B>(_parentBuilder, this, true);
    }

    public SSIDrugType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SSIDrugType.Builder<Void> builder() {
        return new SSIDrugType.Builder<>(null, null, false);
    }

    public static<_B >SSIDrugType.Builder<_B> copyOf(final SSIDrugType _other) {
        final SSIDrugType.Builder<_B> _newBuilder = new SSIDrugType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SSIDrugType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree atcPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("atc"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(atcPropertyTree!= null):((atcPropertyTree == null)||(!atcPropertyTree.isLeaf())))) {
            _other.atc = ((this.atc == null)?null:this.atc.newCopyBuilder(_other, atcPropertyTree, _propertyTreeUse));
        }
        final PropertyTree drugIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugIdentifierPropertyTree!= null):((drugIdentifierPropertyTree == null)||(!drugIdentifierPropertyTree.isLeaf())))) {
            _other.drugIdentifier = this.drugIdentifier;
        }
        final PropertyTree drugNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugNamePropertyTree!= null):((drugNamePropertyTree == null)||(!drugNamePropertyTree.isLeaf())))) {
            _other.drugName = this.drugName;
        }
        final PropertyTree drugFormPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugForm"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugFormPropertyTree!= null):((drugFormPropertyTree == null)||(!drugFormPropertyTree.isLeaf())))) {
            _other.drugForm = ((this.drugForm == null)?null:this.drugForm.newCopyBuilder(_other, drugFormPropertyTree, _propertyTreeUse));
        }
        final PropertyTree drugStrengthPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrength"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthPropertyTree!= null):((drugStrengthPropertyTree == null)||(!drugStrengthPropertyTree.isLeaf())))) {
            _other.drugStrength = ((this.drugStrength == null)?null:this.drugStrength.newCopyBuilder(_other, drugStrengthPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >SSIDrugType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SSIDrugType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SSIDrugType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SSIDrugType.Builder<_B> copyOf(final SSIDrugType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SSIDrugType.Builder<_B> _newBuilder = new SSIDrugType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SSIDrugType.Builder<Void> copyExcept(final SSIDrugType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SSIDrugType.Builder<Void> copyOnly(final SSIDrugType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SSIDrugType _storedValue;
        private ATCType.Builder<SSIDrugType.Builder<_B>> atc;
        private Long drugIdentifier;
        private String drugName;
        private DrugFormType.Builder<SSIDrugType.Builder<_B>> drugForm;
        private DrugStrengthType.Builder<SSIDrugType.Builder<_B>> drugStrength;

        public Builder(final _B _parentBuilder, final SSIDrugType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.atc = ((_other.atc == null)?null:_other.atc.newCopyBuilder(this));
                    this.drugIdentifier = _other.drugIdentifier;
                    this.drugName = _other.drugName;
                    this.drugForm = ((_other.drugForm == null)?null:_other.drugForm.newCopyBuilder(this));
                    this.drugStrength = ((_other.drugStrength == null)?null:_other.drugStrength.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final SSIDrugType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree atcPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("atc"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(atcPropertyTree!= null):((atcPropertyTree == null)||(!atcPropertyTree.isLeaf())))) {
                        this.atc = ((_other.atc == null)?null:_other.atc.newCopyBuilder(this, atcPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree drugIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugIdentifierPropertyTree!= null):((drugIdentifierPropertyTree == null)||(!drugIdentifierPropertyTree.isLeaf())))) {
                        this.drugIdentifier = _other.drugIdentifier;
                    }
                    final PropertyTree drugNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugNamePropertyTree!= null):((drugNamePropertyTree == null)||(!drugNamePropertyTree.isLeaf())))) {
                        this.drugName = _other.drugName;
                    }
                    final PropertyTree drugFormPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugForm"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugFormPropertyTree!= null):((drugFormPropertyTree == null)||(!drugFormPropertyTree.isLeaf())))) {
                        this.drugForm = ((_other.drugForm == null)?null:_other.drugForm.newCopyBuilder(this, drugFormPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree drugStrengthPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugStrength"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugStrengthPropertyTree!= null):((drugStrengthPropertyTree == null)||(!drugStrengthPropertyTree.isLeaf())))) {
                        this.drugStrength = ((_other.drugStrength == null)?null:_other.drugStrength.newCopyBuilder(this, drugStrengthPropertyTree, _propertyTreeUse));
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

        protected<_P extends SSIDrugType >_P init(final _P _product) {
            _product.atc = ((this.atc == null)?null:this.atc.build());
            _product.drugIdentifier = this.drugIdentifier;
            _product.drugName = this.drugName;
            _product.drugForm = ((this.drugForm == null)?null:this.drugForm.build());
            _product.drugStrength = ((this.drugStrength == null)?null:this.drugStrength.build());
            return _product;
        }

        /**
         * Sets the new value of "atc" (any previous value will be replaced)
         * 
         * @param atc
         *     New value of the "atc" property.
         */
        public SSIDrugType.Builder<_B> withATC(final ATCType atc) {
            this.atc = ((atc == null)?null:new ATCType.Builder<>(this, atc, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the "atc"
         * property.
         * Use {@link dk.vaccinationsregister.schemas._2013._12._01.ATCType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "atc" property.
         *     Use {@link dk.vaccinationsregister.schemas._2013._12._01.ATCType.Builder#end()}
         *     to return to the current builder.
         */
        public ATCType.Builder<? extends SSIDrugType.Builder<_B>> withATC() {
            if (this.atc!= null) {
                return this.atc;
            }
            return this.atc = new ATCType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "drugIdentifier" (any previous value will be replaced)
         * 
         * @param drugIdentifier
         *     New value of the "drugIdentifier" property.
         */
        public SSIDrugType.Builder<_B> withDrugIdentifier(final Long drugIdentifier) {
            this.drugIdentifier = drugIdentifier;
            return this;
        }

        /**
         * Sets the new value of "drugName" (any previous value will be replaced)
         * 
         * @param drugName
         *     New value of the "drugName" property.
         */
        public SSIDrugType.Builder<_B> withDrugName(final String drugName) {
            this.drugName = drugName;
            return this;
        }

        /**
         * Sets the new value of "drugForm" (any previous value will be replaced)
         * 
         * @param drugForm
         *     New value of the "drugForm" property.
         */
        public SSIDrugType.Builder<_B> withDrugForm(final DrugFormType drugForm) {
            this.drugForm = ((drugForm == null)?null:new DrugFormType.Builder<>(this, drugForm, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "drugForm" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.DrugFormType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "drugForm" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.DrugFormType.Builder#end()} to
         *     return to the current builder.
         */
        public DrugFormType.Builder<? extends SSIDrugType.Builder<_B>> withDrugForm() {
            if (this.drugForm!= null) {
                return this.drugForm;
            }
            return this.drugForm = new DrugFormType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "drugStrength" (any previous value will be replaced)
         * 
         * @param drugStrength
         *     New value of the "drugStrength" property.
         */
        public SSIDrugType.Builder<_B> withDrugStrength(final DrugStrengthType drugStrength) {
            this.drugStrength = ((drugStrength == null)?null:new DrugStrengthType.Builder<>(this, drugStrength, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "drugStrength" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.DrugStrengthType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "drugStrength" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.DrugStrengthType.Builder#end()} to
         *     return to the current builder.
         */
        public DrugStrengthType.Builder<? extends SSIDrugType.Builder<_B>> withDrugStrength() {
            if (this.drugStrength!= null) {
                return this.drugStrength;
            }
            return this.drugStrength = new DrugStrengthType.Builder<>(this, null, false);
        }

        @Override
        public SSIDrugType build() {
            if (_storedValue == null) {
                return this.init(new SSIDrugType());
            } else {
                return ((SSIDrugType) _storedValue);
            }
        }

        public SSIDrugType.Builder<_B> copyOf(final SSIDrugType _other) {
            _other.copyTo(this);
            return this;
        }

        public SSIDrugType.Builder<_B> copyOf(final SSIDrugType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SSIDrugType.Selector<SSIDrugType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SSIDrugType.Select _root() {
            return new SSIDrugType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private ATCType.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> atc = null;
        private com.kscs.util.jaxb.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugName = null;
        private DrugFormType.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugForm = null;
        private DrugStrengthType.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugStrength = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.atc!= null) {
                products.put("atc", this.atc.init());
            }
            if (this.drugIdentifier!= null) {
                products.put("drugIdentifier", this.drugIdentifier.init());
            }
            if (this.drugName!= null) {
                products.put("drugName", this.drugName.init());
            }
            if (this.drugForm!= null) {
                products.put("drugForm", this.drugForm.init());
            }
            if (this.drugStrength!= null) {
                products.put("drugStrength", this.drugStrength.init());
            }
            return products;
        }

        public ATCType.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> atc() {
            return ((this.atc == null)?this.atc = new ATCType.Selector<>(this._root, this, "atc"):this.atc);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugIdentifier() {
            return ((this.drugIdentifier == null)?this.drugIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugIdentifier"):this.drugIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugName() {
            return ((this.drugName == null)?this.drugName = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugName"):this.drugName);
        }

        public DrugFormType.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugForm() {
            return ((this.drugForm == null)?this.drugForm = new DrugFormType.Selector<>(this._root, this, "drugForm"):this.drugForm);
        }

        public DrugStrengthType.Selector<TRoot, SSIDrugType.Selector<TRoot, TParent>> drugStrength() {
            return ((this.drugStrength == null)?this.drugStrength = new DrugStrengthType.Selector<>(this._root, this, "drugStrength"):this.drugStrength);
        }

    }

}
