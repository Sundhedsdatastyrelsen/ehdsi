
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
 * <p>Java class for VaccinationUpdateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VaccinationUpdateType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="OldVaccinationIdentifier" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="OldVaccinationVersion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="EffectuatedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         <element name="DrugIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugIdentifierType" minOccurs="0"/>
 *         <element name="DosageOptionIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DosageOptionIdentifierType" minOccurs="0"/>
 *         <element name="BatchNumber" type="{http://vaccinationsregister.dk/schemas/2013/12/01}BatchNumberType" minOccurs="0"/>
 *         <element name="CoverageDuration" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CoverageDurationType" minOccurs="0"/>
 *         <element name="VaccineIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineIdentifierType"/>
 *         <element name="Approved" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="NegativeConsentIndicator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentIndicatorType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationUpdateType", propOrder = {
    "oldVaccinationIdentifier",
    "oldVaccinationVersion",
    "effectuatedDateTime",
    "drugIdentifier",
    "dosageOptionIdentifier",
    "batchNumber",
    "coverageDuration",
    "vaccineIdentifier",
    "approved",
    "negativeConsentIndicator"
})
public class VaccinationUpdateType {

    @XmlElement(name = "OldVaccinationIdentifier")
    protected long oldVaccinationIdentifier;
    @XmlElement(name = "OldVaccinationVersion")
    protected long oldVaccinationVersion;
    @XmlElement(name = "EffectuatedDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectuatedDateTime;
    @XmlElement(name = "DrugIdentifier")
    protected Long drugIdentifier;
    @XmlElement(name = "DosageOptionIdentifier")
    protected Long dosageOptionIdentifier;
    @XmlElement(name = "BatchNumber")
    protected String batchNumber;
    @XmlElement(name = "CoverageDuration")
    protected String coverageDuration;
    @XmlElement(name = "VaccineIdentifier")
    protected long vaccineIdentifier;
    @XmlElement(name = "Approved")
    protected boolean approved;
    @XmlElement(name = "NegativeConsentIndicator")
    protected Boolean negativeConsentIndicator;

    /**
     * Gets the value of the oldVaccinationIdentifier property.
     * 
     */
    public long getOldVaccinationIdentifier() {
        return oldVaccinationIdentifier;
    }

    /**
     * Sets the value of the oldVaccinationIdentifier property.
     * 
     */
    public void setOldVaccinationIdentifier(long value) {
        this.oldVaccinationIdentifier = value;
    }

    /**
     * Gets the value of the oldVaccinationVersion property.
     * 
     */
    public long getOldVaccinationVersion() {
        return oldVaccinationVersion;
    }

    /**
     * Sets the value of the oldVaccinationVersion property.
     * 
     */
    public void setOldVaccinationVersion(long value) {
        this.oldVaccinationVersion = value;
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
     * Gets the value of the dosageOptionIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDosageOptionIdentifier() {
        return dosageOptionIdentifier;
    }

    /**
     * Sets the value of the dosageOptionIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDosageOptionIdentifier(Long value) {
        this.dosageOptionIdentifier = value;
    }

    /**
     * Gets the value of the batchNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchNumber() {
        return batchNumber;
    }

    /**
     * Sets the value of the batchNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchNumber(String value) {
        this.batchNumber = value;
    }

    /**
     * Gets the value of the coverageDuration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoverageDuration() {
        return coverageDuration;
    }

    /**
     * Sets the value of the coverageDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoverageDuration(String value) {
        this.coverageDuration = value;
    }

    /**
     * Gets the value of the vaccineIdentifier property.
     * 
     */
    public long getVaccineIdentifier() {
        return vaccineIdentifier;
    }

    /**
     * Sets the value of the vaccineIdentifier property.
     * 
     */
    public void setVaccineIdentifier(long value) {
        this.vaccineIdentifier = value;
    }

    /**
     * Gets the value of the approved property.
     * 
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Sets the value of the approved property.
     * 
     */
    public void setApproved(boolean value) {
        this.approved = value;
    }

    /**
     * Gets the value of the negativeConsentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNegativeConsentIndicator() {
        return negativeConsentIndicator;
    }

    /**
     * Sets the value of the negativeConsentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNegativeConsentIndicator(Boolean value) {
        this.negativeConsentIndicator = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final VaccinationUpdateType.Builder<_B> _other) {
        _other.oldVaccinationIdentifier = this.oldVaccinationIdentifier;
        _other.oldVaccinationVersion = this.oldVaccinationVersion;
        _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
        _other.drugIdentifier = this.drugIdentifier;
        _other.dosageOptionIdentifier = this.dosageOptionIdentifier;
        _other.batchNumber = this.batchNumber;
        _other.coverageDuration = this.coverageDuration;
        _other.vaccineIdentifier = this.vaccineIdentifier;
        _other.approved = this.approved;
        _other.negativeConsentIndicator = this.negativeConsentIndicator;
    }

    public<_B >VaccinationUpdateType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new VaccinationUpdateType.Builder<_B>(_parentBuilder, this, true);
    }

    public VaccinationUpdateType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static VaccinationUpdateType.Builder<Void> builder() {
        return new VaccinationUpdateType.Builder<>(null, null, false);
    }

    public static<_B >VaccinationUpdateType.Builder<_B> copyOf(final VaccinationUpdateType _other) {
        final VaccinationUpdateType.Builder<_B> _newBuilder = new VaccinationUpdateType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final VaccinationUpdateType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree oldVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("oldVaccinationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(oldVaccinationIdentifierPropertyTree!= null):((oldVaccinationIdentifierPropertyTree == null)||(!oldVaccinationIdentifierPropertyTree.isLeaf())))) {
            _other.oldVaccinationIdentifier = this.oldVaccinationIdentifier;
        }
        final PropertyTree oldVaccinationVersionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("oldVaccinationVersion"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(oldVaccinationVersionPropertyTree!= null):((oldVaccinationVersionPropertyTree == null)||(!oldVaccinationVersionPropertyTree.isLeaf())))) {
            _other.oldVaccinationVersion = this.oldVaccinationVersion;
        }
        final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
            _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
        }
        final PropertyTree drugIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugIdentifierPropertyTree!= null):((drugIdentifierPropertyTree == null)||(!drugIdentifierPropertyTree.isLeaf())))) {
            _other.drugIdentifier = this.drugIdentifier;
        }
        final PropertyTree dosageOptionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("dosageOptionIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(dosageOptionIdentifierPropertyTree!= null):((dosageOptionIdentifierPropertyTree == null)||(!dosageOptionIdentifierPropertyTree.isLeaf())))) {
            _other.dosageOptionIdentifier = this.dosageOptionIdentifier;
        }
        final PropertyTree batchNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("batchNumber"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(batchNumberPropertyTree!= null):((batchNumberPropertyTree == null)||(!batchNumberPropertyTree.isLeaf())))) {
            _other.batchNumber = this.batchNumber;
        }
        final PropertyTree coverageDurationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coverageDuration"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coverageDurationPropertyTree!= null):((coverageDurationPropertyTree == null)||(!coverageDurationPropertyTree.isLeaf())))) {
            _other.coverageDuration = this.coverageDuration;
        }
        final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
            _other.vaccineIdentifier = this.vaccineIdentifier;
        }
        final PropertyTree approvedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("approved"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(approvedPropertyTree!= null):((approvedPropertyTree == null)||(!approvedPropertyTree.isLeaf())))) {
            _other.approved = this.approved;
        }
        final PropertyTree negativeConsentIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentIndicator"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentIndicatorPropertyTree!= null):((negativeConsentIndicatorPropertyTree == null)||(!negativeConsentIndicatorPropertyTree.isLeaf())))) {
            _other.negativeConsentIndicator = this.negativeConsentIndicator;
        }
    }

    public<_B >VaccinationUpdateType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new VaccinationUpdateType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public VaccinationUpdateType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >VaccinationUpdateType.Builder<_B> copyOf(final VaccinationUpdateType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final VaccinationUpdateType.Builder<_B> _newBuilder = new VaccinationUpdateType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static VaccinationUpdateType.Builder<Void> copyExcept(final VaccinationUpdateType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static VaccinationUpdateType.Builder<Void> copyOnly(final VaccinationUpdateType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final VaccinationUpdateType _storedValue;
        private long oldVaccinationIdentifier;
        private long oldVaccinationVersion;
        private XMLGregorianCalendar effectuatedDateTime;
        private Long drugIdentifier;
        private Long dosageOptionIdentifier;
        private String batchNumber;
        private String coverageDuration;
        private long vaccineIdentifier;
        private boolean approved;
        private Boolean negativeConsentIndicator;

        public Builder(final _B _parentBuilder, final VaccinationUpdateType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.oldVaccinationIdentifier = _other.oldVaccinationIdentifier;
                    this.oldVaccinationVersion = _other.oldVaccinationVersion;
                    this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
                    this.drugIdentifier = _other.drugIdentifier;
                    this.dosageOptionIdentifier = _other.dosageOptionIdentifier;
                    this.batchNumber = _other.batchNumber;
                    this.coverageDuration = _other.coverageDuration;
                    this.vaccineIdentifier = _other.vaccineIdentifier;
                    this.approved = _other.approved;
                    this.negativeConsentIndicator = _other.negativeConsentIndicator;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final VaccinationUpdateType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree oldVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("oldVaccinationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(oldVaccinationIdentifierPropertyTree!= null):((oldVaccinationIdentifierPropertyTree == null)||(!oldVaccinationIdentifierPropertyTree.isLeaf())))) {
                        this.oldVaccinationIdentifier = _other.oldVaccinationIdentifier;
                    }
                    final PropertyTree oldVaccinationVersionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("oldVaccinationVersion"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(oldVaccinationVersionPropertyTree!= null):((oldVaccinationVersionPropertyTree == null)||(!oldVaccinationVersionPropertyTree.isLeaf())))) {
                        this.oldVaccinationVersion = _other.oldVaccinationVersion;
                    }
                    final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
                        this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
                    }
                    final PropertyTree drugIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugIdentifierPropertyTree!= null):((drugIdentifierPropertyTree == null)||(!drugIdentifierPropertyTree.isLeaf())))) {
                        this.drugIdentifier = _other.drugIdentifier;
                    }
                    final PropertyTree dosageOptionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("dosageOptionIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(dosageOptionIdentifierPropertyTree!= null):((dosageOptionIdentifierPropertyTree == null)||(!dosageOptionIdentifierPropertyTree.isLeaf())))) {
                        this.dosageOptionIdentifier = _other.dosageOptionIdentifier;
                    }
                    final PropertyTree batchNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("batchNumber"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(batchNumberPropertyTree!= null):((batchNumberPropertyTree == null)||(!batchNumberPropertyTree.isLeaf())))) {
                        this.batchNumber = _other.batchNumber;
                    }
                    final PropertyTree coverageDurationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coverageDuration"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coverageDurationPropertyTree!= null):((coverageDurationPropertyTree == null)||(!coverageDurationPropertyTree.isLeaf())))) {
                        this.coverageDuration = _other.coverageDuration;
                    }
                    final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
                        this.vaccineIdentifier = _other.vaccineIdentifier;
                    }
                    final PropertyTree approvedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("approved"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(approvedPropertyTree!= null):((approvedPropertyTree == null)||(!approvedPropertyTree.isLeaf())))) {
                        this.approved = _other.approved;
                    }
                    final PropertyTree negativeConsentIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentIndicator"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentIndicatorPropertyTree!= null):((negativeConsentIndicatorPropertyTree == null)||(!negativeConsentIndicatorPropertyTree.isLeaf())))) {
                        this.negativeConsentIndicator = _other.negativeConsentIndicator;
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

        protected<_P extends VaccinationUpdateType >_P init(final _P _product) {
            _product.oldVaccinationIdentifier = this.oldVaccinationIdentifier;
            _product.oldVaccinationVersion = this.oldVaccinationVersion;
            _product.effectuatedDateTime = this.effectuatedDateTime;
            _product.drugIdentifier = this.drugIdentifier;
            _product.dosageOptionIdentifier = this.dosageOptionIdentifier;
            _product.batchNumber = this.batchNumber;
            _product.coverageDuration = this.coverageDuration;
            _product.vaccineIdentifier = this.vaccineIdentifier;
            _product.approved = this.approved;
            _product.negativeConsentIndicator = this.negativeConsentIndicator;
            return _product;
        }

        /**
         * Sets the new value of "oldVaccinationIdentifier" (any previous value will be
         * replaced)
         * 
         * @param oldVaccinationIdentifier
         *     New value of the "oldVaccinationIdentifier" property.
         */
        public VaccinationUpdateType.Builder<_B> withOldVaccinationIdentifier(final long oldVaccinationIdentifier) {
            this.oldVaccinationIdentifier = oldVaccinationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "oldVaccinationVersion" (any previous value will be
         * replaced)
         * 
         * @param oldVaccinationVersion
         *     New value of the "oldVaccinationVersion" property.
         */
        public VaccinationUpdateType.Builder<_B> withOldVaccinationVersion(final long oldVaccinationVersion) {
            this.oldVaccinationVersion = oldVaccinationVersion;
            return this;
        }

        /**
         * Sets the new value of "effectuatedDateTime" (any previous value will be
         * replaced)
         * 
         * @param effectuatedDateTime
         *     New value of the "effectuatedDateTime" property.
         */
        public VaccinationUpdateType.Builder<_B> withEffectuatedDateTime(final XMLGregorianCalendar effectuatedDateTime) {
            this.effectuatedDateTime = effectuatedDateTime;
            return this;
        }

        /**
         * Sets the new value of "drugIdentifier" (any previous value will be replaced)
         * 
         * @param drugIdentifier
         *     New value of the "drugIdentifier" property.
         */
        public VaccinationUpdateType.Builder<_B> withDrugIdentifier(final Long drugIdentifier) {
            this.drugIdentifier = drugIdentifier;
            return this;
        }

        /**
         * Sets the new value of "dosageOptionIdentifier" (any previous value will be
         * replaced)
         * 
         * @param dosageOptionIdentifier
         *     New value of the "dosageOptionIdentifier" property.
         */
        public VaccinationUpdateType.Builder<_B> withDosageOptionIdentifier(final Long dosageOptionIdentifier) {
            this.dosageOptionIdentifier = dosageOptionIdentifier;
            return this;
        }

        /**
         * Sets the new value of "batchNumber" (any previous value will be replaced)
         * 
         * @param batchNumber
         *     New value of the "batchNumber" property.
         */
        public VaccinationUpdateType.Builder<_B> withBatchNumber(final String batchNumber) {
            this.batchNumber = batchNumber;
            return this;
        }

        /**
         * Sets the new value of "coverageDuration" (any previous value will be replaced)
         * 
         * @param coverageDuration
         *     New value of the "coverageDuration" property.
         */
        public VaccinationUpdateType.Builder<_B> withCoverageDuration(final String coverageDuration) {
            this.coverageDuration = coverageDuration;
            return this;
        }

        /**
         * Sets the new value of "vaccineIdentifier" (any previous value will be replaced)
         * 
         * @param vaccineIdentifier
         *     New value of the "vaccineIdentifier" property.
         */
        public VaccinationUpdateType.Builder<_B> withVaccineIdentifier(final long vaccineIdentifier) {
            this.vaccineIdentifier = vaccineIdentifier;
            return this;
        }

        /**
         * Sets the new value of "approved" (any previous value will be replaced)
         * 
         * @param approved
         *     New value of the "approved" property.
         */
        public VaccinationUpdateType.Builder<_B> withApproved(final boolean approved) {
            this.approved = approved;
            return this;
        }

        /**
         * Sets the new value of "negativeConsentIndicator" (any previous value will be
         * replaced)
         * 
         * @param negativeConsentIndicator
         *     New value of the "negativeConsentIndicator" property.
         */
        public VaccinationUpdateType.Builder<_B> withNegativeConsentIndicator(final Boolean negativeConsentIndicator) {
            this.negativeConsentIndicator = negativeConsentIndicator;
            return this;
        }

        @Override
        public VaccinationUpdateType build() {
            if (_storedValue == null) {
                return this.init(new VaccinationUpdateType());
            } else {
                return ((VaccinationUpdateType) _storedValue);
            }
        }

        public VaccinationUpdateType.Builder<_B> copyOf(final VaccinationUpdateType _other) {
            _other.copyTo(this);
            return this;
        }

        public VaccinationUpdateType.Builder<_B> copyOf(final VaccinationUpdateType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends VaccinationUpdateType.Selector<VaccinationUpdateType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static VaccinationUpdateType.Select _root() {
            return new VaccinationUpdateType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> effectuatedDateTime = null;
        private com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> drugIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> dosageOptionIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> batchNumber = null;
        private com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> coverageDuration = null;
        private com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> negativeConsentIndicator = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.effectuatedDateTime!= null) {
                products.put("effectuatedDateTime", this.effectuatedDateTime.init());
            }
            if (this.drugIdentifier!= null) {
                products.put("drugIdentifier", this.drugIdentifier.init());
            }
            if (this.dosageOptionIdentifier!= null) {
                products.put("dosageOptionIdentifier", this.dosageOptionIdentifier.init());
            }
            if (this.batchNumber!= null) {
                products.put("batchNumber", this.batchNumber.init());
            }
            if (this.coverageDuration!= null) {
                products.put("coverageDuration", this.coverageDuration.init());
            }
            if (this.negativeConsentIndicator!= null) {
                products.put("negativeConsentIndicator", this.negativeConsentIndicator.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> effectuatedDateTime() {
            return ((this.effectuatedDateTime == null)?this.effectuatedDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedDateTime"):this.effectuatedDateTime);
        }

        public com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> drugIdentifier() {
            return ((this.drugIdentifier == null)?this.drugIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugIdentifier"):this.drugIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> dosageOptionIdentifier() {
            return ((this.dosageOptionIdentifier == null)?this.dosageOptionIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "dosageOptionIdentifier"):this.dosageOptionIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> batchNumber() {
            return ((this.batchNumber == null)?this.batchNumber = new com.kscs.util.jaxb.Selector<>(this._root, this, "batchNumber"):this.batchNumber);
        }

        public com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> coverageDuration() {
            return ((this.coverageDuration == null)?this.coverageDuration = new com.kscs.util.jaxb.Selector<>(this._root, this, "coverageDuration"):this.coverageDuration);
        }

        public com.kscs.util.jaxb.Selector<TRoot, VaccinationUpdateType.Selector<TRoot, TParent>> negativeConsentIndicator() {
            return ((this.negativeConsentIndicator == null)?this.negativeConsentIndicator = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentIndicator"):this.negativeConsentIndicator);
        }

    }

}
