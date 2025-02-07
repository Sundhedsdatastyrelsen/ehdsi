
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EffectuationOverviewType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EffectuationOverviewType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DrugName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugNameType"/>
 *         <element name="EffectuatedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="Vaccine" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineType" minOccurs="0"/>
 *         <element name="SSIDrug" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SSIDrugType" minOccurs="0"/>
 *         <element name="CountryIdentificationCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedInCountryCodeType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EffectuationOverviewType", propOrder = {
    "drugName",
    "effectuatedDateTime",
    "vaccine",
    "ssiDrug",
    "countryIdentificationCode"
})
public class EffectuationOverviewType {

    @XmlElement(name = "DrugName", required = true)
    protected String drugName;
    @XmlElement(name = "EffectuatedDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectuatedDateTime;
    @XmlElement(name = "Vaccine")
    protected VaccineType vaccine;
    @XmlElement(name = "SSIDrug")
    protected SSIDrugType ssiDrug;
    @XmlElement(name = "CountryIdentificationCode")
    protected String countryIdentificationCode;

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
     * Gets the value of the effectuatedDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectuatedDateTime() {
        return effectuatedDateTime;
    }

    /**
     * Sets the value of the effectuatedDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectuatedDateTime(XMLGregorianCalendar value) {
        this.effectuatedDateTime = value;
    }

    /**
     * Gets the value of the vaccine property.
     * 
     * @return
     *     possible object is
     *     {@link VaccineType }
     *     
     */
    public VaccineType getVaccine() {
        return vaccine;
    }

    /**
     * Sets the value of the vaccine property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccineType }
     *     
     */
    public void setVaccine(VaccineType value) {
        this.vaccine = value;
    }

    /**
     * Gets the value of the ssiDrug property.
     * 
     * @return
     *     possible object is
     *     {@link SSIDrugType }
     *     
     */
    public SSIDrugType getSSIDrug() {
        return ssiDrug;
    }

    /**
     * Sets the value of the ssiDrug property.
     * 
     * @param value
     *     allowed object is
     *     {@link SSIDrugType }
     *     
     */
    public void setSSIDrug(SSIDrugType value) {
        this.ssiDrug = value;
    }

    /**
     * Gets the value of the countryIdentificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryIdentificationCode() {
        return countryIdentificationCode;
    }

    /**
     * Sets the value of the countryIdentificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryIdentificationCode(String value) {
        this.countryIdentificationCode = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final EffectuationOverviewType.Builder<_B> _other) {
        _other.drugName = this.drugName;
        _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
        _other.vaccine = ((this.vaccine == null)?null:this.vaccine.newCopyBuilder(_other));
        _other.ssiDrug = ((this.ssiDrug == null)?null:this.ssiDrug.newCopyBuilder(_other));
        _other.countryIdentificationCode = this.countryIdentificationCode;
    }

    public<_B >EffectuationOverviewType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new EffectuationOverviewType.Builder<_B>(_parentBuilder, this, true);
    }

    public EffectuationOverviewType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static EffectuationOverviewType.Builder<Void> builder() {
        return new EffectuationOverviewType.Builder<>(null, null, false);
    }

    public static<_B >EffectuationOverviewType.Builder<_B> copyOf(final EffectuationOverviewType _other) {
        final EffectuationOverviewType.Builder<_B> _newBuilder = new EffectuationOverviewType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final EffectuationOverviewType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree drugNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugNamePropertyTree!= null):((drugNamePropertyTree == null)||(!drugNamePropertyTree.isLeaf())))) {
            _other.drugName = this.drugName;
        }
        final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
            _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
        }
        final PropertyTree vaccinePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccine"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinePropertyTree!= null):((vaccinePropertyTree == null)||(!vaccinePropertyTree.isLeaf())))) {
            _other.vaccine = ((this.vaccine == null)?null:this.vaccine.newCopyBuilder(_other, vaccinePropertyTree, _propertyTreeUse));
        }
        final PropertyTree ssiDrugPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("ssiDrug"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(ssiDrugPropertyTree!= null):((ssiDrugPropertyTree == null)||(!ssiDrugPropertyTree.isLeaf())))) {
            _other.ssiDrug = ((this.ssiDrug == null)?null:this.ssiDrug.newCopyBuilder(_other, ssiDrugPropertyTree, _propertyTreeUse));
        }
        final PropertyTree countryIdentificationCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("countryIdentificationCode"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(countryIdentificationCodePropertyTree!= null):((countryIdentificationCodePropertyTree == null)||(!countryIdentificationCodePropertyTree.isLeaf())))) {
            _other.countryIdentificationCode = this.countryIdentificationCode;
        }
    }

    public<_B >EffectuationOverviewType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new EffectuationOverviewType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public EffectuationOverviewType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >EffectuationOverviewType.Builder<_B> copyOf(final EffectuationOverviewType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final EffectuationOverviewType.Builder<_B> _newBuilder = new EffectuationOverviewType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static EffectuationOverviewType.Builder<Void> copyExcept(final EffectuationOverviewType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static EffectuationOverviewType.Builder<Void> copyOnly(final EffectuationOverviewType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final EffectuationOverviewType _storedValue;
        private String drugName;
        private XMLGregorianCalendar effectuatedDateTime;
        private VaccineType.Builder<EffectuationOverviewType.Builder<_B>> vaccine;
        private SSIDrugType.Builder<EffectuationOverviewType.Builder<_B>> ssiDrug;
        private String countryIdentificationCode;

        public Builder(final _B _parentBuilder, final EffectuationOverviewType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.drugName = _other.drugName;
                    this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
                    this.vaccine = ((_other.vaccine == null)?null:_other.vaccine.newCopyBuilder(this));
                    this.ssiDrug = ((_other.ssiDrug == null)?null:_other.ssiDrug.newCopyBuilder(this));
                    this.countryIdentificationCode = _other.countryIdentificationCode;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final EffectuationOverviewType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree drugNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugNamePropertyTree!= null):((drugNamePropertyTree == null)||(!drugNamePropertyTree.isLeaf())))) {
                        this.drugName = _other.drugName;
                    }
                    final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
                        this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
                    }
                    final PropertyTree vaccinePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccine"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinePropertyTree!= null):((vaccinePropertyTree == null)||(!vaccinePropertyTree.isLeaf())))) {
                        this.vaccine = ((_other.vaccine == null)?null:_other.vaccine.newCopyBuilder(this, vaccinePropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree ssiDrugPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("ssiDrug"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(ssiDrugPropertyTree!= null):((ssiDrugPropertyTree == null)||(!ssiDrugPropertyTree.isLeaf())))) {
                        this.ssiDrug = ((_other.ssiDrug == null)?null:_other.ssiDrug.newCopyBuilder(this, ssiDrugPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree countryIdentificationCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("countryIdentificationCode"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(countryIdentificationCodePropertyTree!= null):((countryIdentificationCodePropertyTree == null)||(!countryIdentificationCodePropertyTree.isLeaf())))) {
                        this.countryIdentificationCode = _other.countryIdentificationCode;
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

        protected<_P extends EffectuationOverviewType >_P init(final _P _product) {
            _product.drugName = this.drugName;
            _product.effectuatedDateTime = this.effectuatedDateTime;
            _product.vaccine = ((this.vaccine == null)?null:this.vaccine.build());
            _product.ssiDrug = ((this.ssiDrug == null)?null:this.ssiDrug.build());
            _product.countryIdentificationCode = this.countryIdentificationCode;
            return _product;
        }

        /**
         * Sets the new value of "drugName" (any previous value will be replaced)
         * 
         * @param drugName
         *     New value of the "drugName" property.
         */
        public EffectuationOverviewType.Builder<_B> withDrugName(final String drugName) {
            this.drugName = drugName;
            return this;
        }

        /**
         * Sets the new value of "effectuatedDateTime" (any previous value will be
         * replaced)
         * 
         * @param effectuatedDateTime
         *     New value of the "effectuatedDateTime" property.
         */
        public EffectuationOverviewType.Builder<_B> withEffectuatedDateTime(final XMLGregorianCalendar effectuatedDateTime) {
            this.effectuatedDateTime = effectuatedDateTime;
            return this;
        }

        /**
         * Sets the new value of "vaccine" (any previous value will be replaced)
         * 
         * @param vaccine
         *     New value of the "vaccine" property.
         */
        public EffectuationOverviewType.Builder<_B> withVaccine(final VaccineType vaccine) {
            this.vaccine = ((vaccine == null)?null:new VaccineType.Builder<>(this, vaccine, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "vaccine" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VaccineType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "vaccine" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VaccineType.Builder#end()} to
         *     return to the current builder.
         */
        public VaccineType.Builder<? extends EffectuationOverviewType.Builder<_B>> withVaccine() {
            if (this.vaccine!= null) {
                return this.vaccine;
            }
            return this.vaccine = new VaccineType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "ssiDrug" (any previous value will be replaced)
         * 
         * @param ssiDrug
         *     New value of the "ssiDrug" property.
         */
        public EffectuationOverviewType.Builder<_B> withSSIDrug(final SSIDrugType ssiDrug) {
            this.ssiDrug = ((ssiDrug == null)?null:new SSIDrugType.Builder<>(this, ssiDrug, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "ssiDrug" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "ssiDrug" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType.Builder#end()} to
         *     return to the current builder.
         */
        public SSIDrugType.Builder<? extends EffectuationOverviewType.Builder<_B>> withSSIDrug() {
            if (this.ssiDrug!= null) {
                return this.ssiDrug;
            }
            return this.ssiDrug = new SSIDrugType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "countryIdentificationCode" (any previous value will be
         * replaced)
         * 
         * @param countryIdentificationCode
         *     New value of the "countryIdentificationCode" property.
         */
        public EffectuationOverviewType.Builder<_B> withCountryIdentificationCode(final String countryIdentificationCode) {
            this.countryIdentificationCode = countryIdentificationCode;
            return this;
        }

        @Override
        public EffectuationOverviewType build() {
            if (_storedValue == null) {
                return this.init(new EffectuationOverviewType());
            } else {
                return ((EffectuationOverviewType) _storedValue);
            }
        }

        public EffectuationOverviewType.Builder<_B> copyOf(final EffectuationOverviewType _other) {
            _other.copyTo(this);
            return this;
        }

        public EffectuationOverviewType.Builder<_B> copyOf(final EffectuationOverviewType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends EffectuationOverviewType.Selector<EffectuationOverviewType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static EffectuationOverviewType.Select _root() {
            return new EffectuationOverviewType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> drugName = null;
        private com.kscs.util.jaxb.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> effectuatedDateTime = null;
        private VaccineType.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> vaccine = null;
        private SSIDrugType.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> ssiDrug = null;
        private com.kscs.util.jaxb.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> countryIdentificationCode = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.drugName!= null) {
                products.put("drugName", this.drugName.init());
            }
            if (this.effectuatedDateTime!= null) {
                products.put("effectuatedDateTime", this.effectuatedDateTime.init());
            }
            if (this.vaccine!= null) {
                products.put("vaccine", this.vaccine.init());
            }
            if (this.ssiDrug!= null) {
                products.put("ssiDrug", this.ssiDrug.init());
            }
            if (this.countryIdentificationCode!= null) {
                products.put("countryIdentificationCode", this.countryIdentificationCode.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> drugName() {
            return ((this.drugName == null)?this.drugName = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugName"):this.drugName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> effectuatedDateTime() {
            return ((this.effectuatedDateTime == null)?this.effectuatedDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedDateTime"):this.effectuatedDateTime);
        }

        public VaccineType.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> vaccine() {
            return ((this.vaccine == null)?this.vaccine = new VaccineType.Selector<>(this._root, this, "vaccine"):this.vaccine);
        }

        public SSIDrugType.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> ssiDrug() {
            return ((this.ssiDrug == null)?this.ssiDrug = new SSIDrugType.Selector<>(this._root, this, "ssiDrug"):this.ssiDrug);
        }

        public com.kscs.util.jaxb.Selector<TRoot, EffectuationOverviewType.Selector<TRoot, TParent>> countryIdentificationCode() {
            return ((this.countryIdentificationCode == null)?this.countryIdentificationCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "countryIdentificationCode"):this.countryIdentificationCode);
        }

    }

}
