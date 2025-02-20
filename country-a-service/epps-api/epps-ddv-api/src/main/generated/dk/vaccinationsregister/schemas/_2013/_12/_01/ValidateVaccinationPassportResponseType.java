
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17.SimpleCPRPersonType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateVaccinationPassportResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ValidateVaccinationPassportResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonInfo" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2006/01/17/}SimpleCPRPersonType"/>
 *         <element name="VaccinationAgainst" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationAgainstType"/>
 *         <element name="VaccinationPassportMetadata" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPassportMetadataType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateVaccinationPassportResponseType", propOrder = {
    "personInfo",
    "vaccinationAgainst",
    "vaccinationPassportMetadata"
})
public class ValidateVaccinationPassportResponseType {

    @XmlElement(name = "PersonInfo", required = true)
    protected SimpleCPRPersonType personInfo;
    @XmlElement(name = "VaccinationAgainst", required = true)
    protected String vaccinationAgainst;
    @XmlElement(name = "VaccinationPassportMetadata")
    protected List<VaccinationPassportMetadataType> vaccinationPassportMetadata;

    /**
     * Gets the value of the personInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleCPRPersonType }
     *     
     */
    public SimpleCPRPersonType getPersonInfo() {
        return personInfo;
    }

    /**
     * Sets the value of the personInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleCPRPersonType }
     *     
     */
    public void setPersonInfo(SimpleCPRPersonType value) {
        this.personInfo = value;
    }

    /**
     * Gets the value of the vaccinationAgainst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaccinationAgainst() {
        return vaccinationAgainst;
    }

    /**
     * Sets the value of the vaccinationAgainst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaccinationAgainst(String value) {
        this.vaccinationAgainst = value;
    }

    /**
     * Gets the value of the vaccinationPassportMetadata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the vaccinationPassportMetadata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccinationPassportMetadata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VaccinationPassportMetadataType }
     * 
     * 
     * @return
     *     The value of the vaccinationPassportMetadata property.
     */
    public List<VaccinationPassportMetadataType> getVaccinationPassportMetadata() {
        if (vaccinationPassportMetadata == null) {
            vaccinationPassportMetadata = new ArrayList<>();
        }
        return this.vaccinationPassportMetadata;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ValidateVaccinationPassportResponseType.Builder<_B> _other) {
        _other.personInfo = ((this.personInfo == null)?null:this.personInfo.newCopyBuilder(_other));
        _other.vaccinationAgainst = this.vaccinationAgainst;
        if (this.vaccinationPassportMetadata == null) {
            _other.vaccinationPassportMetadata = null;
        } else {
            _other.vaccinationPassportMetadata = new ArrayList<>();
            for (VaccinationPassportMetadataType _item: this.vaccinationPassportMetadata) {
                _other.vaccinationPassportMetadata.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >ValidateVaccinationPassportResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ValidateVaccinationPassportResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public ValidateVaccinationPassportResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ValidateVaccinationPassportResponseType.Builder<Void> builder() {
        return new ValidateVaccinationPassportResponseType.Builder<>(null, null, false);
    }

    public static<_B >ValidateVaccinationPassportResponseType.Builder<_B> copyOf(final ValidateVaccinationPassportResponseType _other) {
        final ValidateVaccinationPassportResponseType.Builder<_B> _newBuilder = new ValidateVaccinationPassportResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ValidateVaccinationPassportResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personInfo"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personInfoPropertyTree!= null):((personInfoPropertyTree == null)||(!personInfoPropertyTree.isLeaf())))) {
            _other.personInfo = ((this.personInfo == null)?null:this.personInfo.newCopyBuilder(_other, personInfoPropertyTree, _propertyTreeUse));
        }
        final PropertyTree vaccinationAgainstPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationAgainst"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationAgainstPropertyTree!= null):((vaccinationAgainstPropertyTree == null)||(!vaccinationAgainstPropertyTree.isLeaf())))) {
            _other.vaccinationAgainst = this.vaccinationAgainst;
        }
        final PropertyTree vaccinationPassportMetadataPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPassportMetadata"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPassportMetadataPropertyTree!= null):((vaccinationPassportMetadataPropertyTree == null)||(!vaccinationPassportMetadataPropertyTree.isLeaf())))) {
            if (this.vaccinationPassportMetadata == null) {
                _other.vaccinationPassportMetadata = null;
            } else {
                _other.vaccinationPassportMetadata = new ArrayList<>();
                for (VaccinationPassportMetadataType _item: this.vaccinationPassportMetadata) {
                    _other.vaccinationPassportMetadata.add(((_item == null)?null:_item.newCopyBuilder(_other, vaccinationPassportMetadataPropertyTree, _propertyTreeUse)));
                }
            }
        }
    }

    public<_B >ValidateVaccinationPassportResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ValidateVaccinationPassportResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ValidateVaccinationPassportResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ValidateVaccinationPassportResponseType.Builder<_B> copyOf(final ValidateVaccinationPassportResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ValidateVaccinationPassportResponseType.Builder<_B> _newBuilder = new ValidateVaccinationPassportResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ValidateVaccinationPassportResponseType.Builder<Void> copyExcept(final ValidateVaccinationPassportResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ValidateVaccinationPassportResponseType.Builder<Void> copyOnly(final ValidateVaccinationPassportResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ValidateVaccinationPassportResponseType _storedValue;
        private SimpleCPRPersonType.Builder<ValidateVaccinationPassportResponseType.Builder<_B>> personInfo;
        private String vaccinationAgainst;
        private List<VaccinationPassportMetadataType.Builder<ValidateVaccinationPassportResponseType.Builder<_B>>> vaccinationPassportMetadata;

        public Builder(final _B _parentBuilder, final ValidateVaccinationPassportResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personInfo = ((_other.personInfo == null)?null:_other.personInfo.newCopyBuilder(this));
                    this.vaccinationAgainst = _other.vaccinationAgainst;
                    if (_other.vaccinationPassportMetadata == null) {
                        this.vaccinationPassportMetadata = null;
                    } else {
                        this.vaccinationPassportMetadata = new ArrayList<>();
                        for (VaccinationPassportMetadataType _item: _other.vaccinationPassportMetadata) {
                            this.vaccinationPassportMetadata.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ValidateVaccinationPassportResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personInfo"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personInfoPropertyTree!= null):((personInfoPropertyTree == null)||(!personInfoPropertyTree.isLeaf())))) {
                        this.personInfo = ((_other.personInfo == null)?null:_other.personInfo.newCopyBuilder(this, personInfoPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree vaccinationAgainstPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationAgainst"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationAgainstPropertyTree!= null):((vaccinationAgainstPropertyTree == null)||(!vaccinationAgainstPropertyTree.isLeaf())))) {
                        this.vaccinationAgainst = _other.vaccinationAgainst;
                    }
                    final PropertyTree vaccinationPassportMetadataPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPassportMetadata"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPassportMetadataPropertyTree!= null):((vaccinationPassportMetadataPropertyTree == null)||(!vaccinationPassportMetadataPropertyTree.isLeaf())))) {
                        if (_other.vaccinationPassportMetadata == null) {
                            this.vaccinationPassportMetadata = null;
                        } else {
                            this.vaccinationPassportMetadata = new ArrayList<>();
                            for (VaccinationPassportMetadataType _item: _other.vaccinationPassportMetadata) {
                                this.vaccinationPassportMetadata.add(((_item == null)?null:_item.newCopyBuilder(this, vaccinationPassportMetadataPropertyTree, _propertyTreeUse)));
                            }
                        }
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

        protected<_P extends ValidateVaccinationPassportResponseType >_P init(final _P _product) {
            _product.personInfo = ((this.personInfo == null)?null:this.personInfo.build());
            _product.vaccinationAgainst = this.vaccinationAgainst;
            if (this.vaccinationPassportMetadata!= null) {
                final List<VaccinationPassportMetadataType> vaccinationPassportMetadata = new ArrayList<>(this.vaccinationPassportMetadata.size());
                for (VaccinationPassportMetadataType.Builder<ValidateVaccinationPassportResponseType.Builder<_B>> _item: this.vaccinationPassportMetadata) {
                    vaccinationPassportMetadata.add(_item.build());
                }
                _product.vaccinationPassportMetadata = vaccinationPassportMetadata;
            }
            return _product;
        }

        /**
         * Sets the new value of "personInfo" (any previous value will be replaced)
         * 
         * @param personInfo
         *     New value of the "personInfo" property.
         */
        public ValidateVaccinationPassportResponseType.Builder<_B> withPersonInfo(final SimpleCPRPersonType personInfo) {
            this.personInfo = ((personInfo == null)?null:new SimpleCPRPersonType.Builder<>(this, personInfo, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "personInfo" property.
         * Use {@link
         * dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17.SimpleCPRPersonType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "personInfo" property.
         *     Use {@link
         *     dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17.SimpleCPRPersonType.Builder#end()}
         *     to return to the current builder.
         */
        public SimpleCPRPersonType.Builder<? extends ValidateVaccinationPassportResponseType.Builder<_B>> withPersonInfo() {
            if (this.personInfo!= null) {
                return this.personInfo;
            }
            return this.personInfo = new SimpleCPRPersonType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "vaccinationAgainst" (any previous value will be replaced)
         * 
         * @param vaccinationAgainst
         *     New value of the "vaccinationAgainst" property.
         */
        public ValidateVaccinationPassportResponseType.Builder<_B> withVaccinationAgainst(final String vaccinationAgainst) {
            this.vaccinationAgainst = vaccinationAgainst;
            return this;
        }

        /**
         * Adds the given items to the value of "vaccinationPassportMetadata"
         * 
         * @param vaccinationPassportMetadata
         *     Items to add to the value of the "vaccinationPassportMetadata" property
         */
        public ValidateVaccinationPassportResponseType.Builder<_B> addVaccinationPassportMetadata(final Iterable<? extends VaccinationPassportMetadataType> vaccinationPassportMetadata) {
            if (vaccinationPassportMetadata!= null) {
                if (this.vaccinationPassportMetadata == null) {
                    this.vaccinationPassportMetadata = new ArrayList<>();
                }
                for (VaccinationPassportMetadataType _item: vaccinationPassportMetadata) {
                    this.vaccinationPassportMetadata.add(new VaccinationPassportMetadataType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "vaccinationPassportMetadata" (any previous value will be
         * replaced)
         * 
         * @param vaccinationPassportMetadata
         *     New value of the "vaccinationPassportMetadata" property.
         */
        public ValidateVaccinationPassportResponseType.Builder<_B> withVaccinationPassportMetadata(final Iterable<? extends VaccinationPassportMetadataType> vaccinationPassportMetadata) {
            if (this.vaccinationPassportMetadata!= null) {
                this.vaccinationPassportMetadata.clear();
            }
            return addVaccinationPassportMetadata(vaccinationPassportMetadata);
        }

        /**
         * Adds the given items to the value of "vaccinationPassportMetadata"
         * 
         * @param vaccinationPassportMetadata
         *     Items to add to the value of the "vaccinationPassportMetadata" property
         */
        public ValidateVaccinationPassportResponseType.Builder<_B> addVaccinationPassportMetadata(VaccinationPassportMetadataType... vaccinationPassportMetadata) {
            addVaccinationPassportMetadata(Arrays.asList(vaccinationPassportMetadata));
            return this;
        }

        /**
         * Sets the new value of "vaccinationPassportMetadata" (any previous value will be
         * replaced)
         * 
         * @param vaccinationPassportMetadata
         *     New value of the "vaccinationPassportMetadata" property.
         */
        public ValidateVaccinationPassportResponseType.Builder<_B> withVaccinationPassportMetadata(VaccinationPassportMetadataType... vaccinationPassportMetadata) {
            withVaccinationPassportMetadata(Arrays.asList(vaccinationPassportMetadata));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the
         * "VaccinationPassportMetadata" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VaccinationPassportMetadataType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "VaccinationPassportMetadata"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VaccinationPassportMetadataType.Builder#end()}
         *     to return to the current builder.
         */
        public VaccinationPassportMetadataType.Builder<? extends ValidateVaccinationPassportResponseType.Builder<_B>> addVaccinationPassportMetadata() {
            if (this.vaccinationPassportMetadata == null) {
                this.vaccinationPassportMetadata = new ArrayList<>();
            }
            final VaccinationPassportMetadataType.Builder<ValidateVaccinationPassportResponseType.Builder<_B>> vaccinationPassportMetadata_Builder = new VaccinationPassportMetadataType.Builder<>(this, null, false);
            this.vaccinationPassportMetadata.add(vaccinationPassportMetadata_Builder);
            return vaccinationPassportMetadata_Builder;
        }

        @Override
        public ValidateVaccinationPassportResponseType build() {
            if (_storedValue == null) {
                return this.init(new ValidateVaccinationPassportResponseType());
            } else {
                return ((ValidateVaccinationPassportResponseType) _storedValue);
            }
        }

        public ValidateVaccinationPassportResponseType.Builder<_B> copyOf(final ValidateVaccinationPassportResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public ValidateVaccinationPassportResponseType.Builder<_B> copyOf(final ValidateVaccinationPassportResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ValidateVaccinationPassportResponseType.Selector<ValidateVaccinationPassportResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ValidateVaccinationPassportResponseType.Select _root() {
            return new ValidateVaccinationPassportResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private SimpleCPRPersonType.Selector<TRoot, ValidateVaccinationPassportResponseType.Selector<TRoot, TParent>> personInfo = null;
        private com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportResponseType.Selector<TRoot, TParent>> vaccinationAgainst = null;
        private VaccinationPassportMetadataType.Selector<TRoot, ValidateVaccinationPassportResponseType.Selector<TRoot, TParent>> vaccinationPassportMetadata = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.personInfo!= null) {
                products.put("personInfo", this.personInfo.init());
            }
            if (this.vaccinationAgainst!= null) {
                products.put("vaccinationAgainst", this.vaccinationAgainst.init());
            }
            if (this.vaccinationPassportMetadata!= null) {
                products.put("vaccinationPassportMetadata", this.vaccinationPassportMetadata.init());
            }
            return products;
        }

        public SimpleCPRPersonType.Selector<TRoot, ValidateVaccinationPassportResponseType.Selector<TRoot, TParent>> personInfo() {
            return ((this.personInfo == null)?this.personInfo = new SimpleCPRPersonType.Selector<>(this._root, this, "personInfo"):this.personInfo);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ValidateVaccinationPassportResponseType.Selector<TRoot, TParent>> vaccinationAgainst() {
            return ((this.vaccinationAgainst == null)?this.vaccinationAgainst = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationAgainst"):this.vaccinationAgainst);
        }

        public VaccinationPassportMetadataType.Selector<TRoot, ValidateVaccinationPassportResponseType.Selector<TRoot, TParent>> vaccinationPassportMetadata() {
            return ((this.vaccinationPassportMetadata == null)?this.vaccinationPassportMetadata = new VaccinationPassportMetadataType.Selector<>(this._root, this, "vaccinationPassportMetadata"):this.vaccinationPassportMetadata);
        }

    }

}
