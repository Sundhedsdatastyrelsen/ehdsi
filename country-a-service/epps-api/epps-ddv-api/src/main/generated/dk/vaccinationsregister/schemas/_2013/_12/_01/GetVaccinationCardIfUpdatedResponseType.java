
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetVaccinationCardIfUpdatedResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetVaccinationCardIfUpdatedResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="VaccinationCardUpdateAvaible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <sequence>
 *           <element name="Vaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationType" maxOccurs="unbounded" minOccurs="0"/>
 *           <element name="NegativeConsentStructure" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentStructureType" minOccurs="0"/>
 *         </sequence>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetVaccinationCardIfUpdatedResponseType", propOrder = {
    "vaccinationCardUpdateAvaible",
    "vaccination",
    "negativeConsentStructure"
})
public class GetVaccinationCardIfUpdatedResponseType {

    @XmlElement(name = "VaccinationCardUpdateAvaible")
    protected Boolean vaccinationCardUpdateAvaible;
    @XmlElement(name = "Vaccination")
    protected List<VaccinationType> vaccination;
    @XmlElement(name = "NegativeConsentStructure")
    protected NegativeConsentStructureType negativeConsentStructure;

    /**
     * Gets the value of the vaccinationCardUpdateAvaible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVaccinationCardUpdateAvaible() {
        return vaccinationCardUpdateAvaible;
    }

    /**
     * Sets the value of the vaccinationCardUpdateAvaible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVaccinationCardUpdateAvaible(Boolean value) {
        this.vaccinationCardUpdateAvaible = value;
    }

    /**
     * Gets the value of the vaccination property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the vaccination property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccination().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VaccinationType }
     * 
     * 
     * @return
     *     The value of the vaccination property.
     */
    public List<VaccinationType> getVaccination() {
        if (vaccination == null) {
            vaccination = new ArrayList<>();
        }
        return this.vaccination;
    }

    /**
     * Gets the value of the negativeConsentStructure property.
     * 
     * @return
     *     possible object is
     *     {@link NegativeConsentStructureType }
     *     
     */
    public NegativeConsentStructureType getNegativeConsentStructure() {
        return negativeConsentStructure;
    }

    /**
     * Sets the value of the negativeConsentStructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link NegativeConsentStructureType }
     *     
     */
    public void setNegativeConsentStructure(NegativeConsentStructureType value) {
        this.negativeConsentStructure = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetVaccinationCardIfUpdatedResponseType.Builder<_B> _other) {
        _other.vaccinationCardUpdateAvaible = this.vaccinationCardUpdateAvaible;
        if (this.vaccination == null) {
            _other.vaccination = null;
        } else {
            _other.vaccination = new ArrayList<>();
            for (VaccinationType _item: this.vaccination) {
                _other.vaccination.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
        _other.negativeConsentStructure = ((this.negativeConsentStructure == null)?null:this.negativeConsentStructure.newCopyBuilder(_other));
    }

    public<_B >GetVaccinationCardIfUpdatedResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetVaccinationCardIfUpdatedResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetVaccinationCardIfUpdatedResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetVaccinationCardIfUpdatedResponseType.Builder<Void> builder() {
        return new GetVaccinationCardIfUpdatedResponseType.Builder<>(null, null, false);
    }

    public static<_B >GetVaccinationCardIfUpdatedResponseType.Builder<_B> copyOf(final GetVaccinationCardIfUpdatedResponseType _other) {
        final GetVaccinationCardIfUpdatedResponseType.Builder<_B> _newBuilder = new GetVaccinationCardIfUpdatedResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetVaccinationCardIfUpdatedResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree vaccinationCardUpdateAvaiblePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCardUpdateAvaible"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCardUpdateAvaiblePropertyTree!= null):((vaccinationCardUpdateAvaiblePropertyTree == null)||(!vaccinationCardUpdateAvaiblePropertyTree.isLeaf())))) {
            _other.vaccinationCardUpdateAvaible = this.vaccinationCardUpdateAvaible;
        }
        final PropertyTree vaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPropertyTree!= null):((vaccinationPropertyTree == null)||(!vaccinationPropertyTree.isLeaf())))) {
            if (this.vaccination == null) {
                _other.vaccination = null;
            } else {
                _other.vaccination = new ArrayList<>();
                for (VaccinationType _item: this.vaccination) {
                    _other.vaccination.add(((_item == null)?null:_item.newCopyBuilder(_other, vaccinationPropertyTree, _propertyTreeUse)));
                }
            }
        }
        final PropertyTree negativeConsentStructurePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentStructure"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentStructurePropertyTree!= null):((negativeConsentStructurePropertyTree == null)||(!negativeConsentStructurePropertyTree.isLeaf())))) {
            _other.negativeConsentStructure = ((this.negativeConsentStructure == null)?null:this.negativeConsentStructure.newCopyBuilder(_other, negativeConsentStructurePropertyTree, _propertyTreeUse));
        }
    }

    public<_B >GetVaccinationCardIfUpdatedResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetVaccinationCardIfUpdatedResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetVaccinationCardIfUpdatedResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetVaccinationCardIfUpdatedResponseType.Builder<_B> copyOf(final GetVaccinationCardIfUpdatedResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetVaccinationCardIfUpdatedResponseType.Builder<_B> _newBuilder = new GetVaccinationCardIfUpdatedResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetVaccinationCardIfUpdatedResponseType.Builder<Void> copyExcept(final GetVaccinationCardIfUpdatedResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetVaccinationCardIfUpdatedResponseType.Builder<Void> copyOnly(final GetVaccinationCardIfUpdatedResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetVaccinationCardIfUpdatedResponseType _storedValue;
        private Boolean vaccinationCardUpdateAvaible;
        private List<VaccinationType.Builder<GetVaccinationCardIfUpdatedResponseType.Builder<_B>>> vaccination;
        private NegativeConsentStructureType.Builder<GetVaccinationCardIfUpdatedResponseType.Builder<_B>> negativeConsentStructure;

        public Builder(final _B _parentBuilder, final GetVaccinationCardIfUpdatedResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.vaccinationCardUpdateAvaible = _other.vaccinationCardUpdateAvaible;
                    if (_other.vaccination == null) {
                        this.vaccination = null;
                    } else {
                        this.vaccination = new ArrayList<>();
                        for (VaccinationType _item: _other.vaccination) {
                            this.vaccination.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                    this.negativeConsentStructure = ((_other.negativeConsentStructure == null)?null:_other.negativeConsentStructure.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetVaccinationCardIfUpdatedResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree vaccinationCardUpdateAvaiblePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCardUpdateAvaible"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCardUpdateAvaiblePropertyTree!= null):((vaccinationCardUpdateAvaiblePropertyTree == null)||(!vaccinationCardUpdateAvaiblePropertyTree.isLeaf())))) {
                        this.vaccinationCardUpdateAvaible = _other.vaccinationCardUpdateAvaible;
                    }
                    final PropertyTree vaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPropertyTree!= null):((vaccinationPropertyTree == null)||(!vaccinationPropertyTree.isLeaf())))) {
                        if (_other.vaccination == null) {
                            this.vaccination = null;
                        } else {
                            this.vaccination = new ArrayList<>();
                            for (VaccinationType _item: _other.vaccination) {
                                this.vaccination.add(((_item == null)?null:_item.newCopyBuilder(this, vaccinationPropertyTree, _propertyTreeUse)));
                            }
                        }
                    }
                    final PropertyTree negativeConsentStructurePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentStructure"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentStructurePropertyTree!= null):((negativeConsentStructurePropertyTree == null)||(!negativeConsentStructurePropertyTree.isLeaf())))) {
                        this.negativeConsentStructure = ((_other.negativeConsentStructure == null)?null:_other.negativeConsentStructure.newCopyBuilder(this, negativeConsentStructurePropertyTree, _propertyTreeUse));
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

        protected<_P extends GetVaccinationCardIfUpdatedResponseType >_P init(final _P _product) {
            _product.vaccinationCardUpdateAvaible = this.vaccinationCardUpdateAvaible;
            if (this.vaccination!= null) {
                final List<VaccinationType> vaccination = new ArrayList<>(this.vaccination.size());
                for (VaccinationType.Builder<GetVaccinationCardIfUpdatedResponseType.Builder<_B>> _item: this.vaccination) {
                    vaccination.add(_item.build());
                }
                _product.vaccination = vaccination;
            }
            _product.negativeConsentStructure = ((this.negativeConsentStructure == null)?null:this.negativeConsentStructure.build());
            return _product;
        }

        /**
         * Sets the new value of "vaccinationCardUpdateAvaible" (any previous value will be
         * replaced)
         * 
         * @param vaccinationCardUpdateAvaible
         *     New value of the "vaccinationCardUpdateAvaible" property.
         */
        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> withVaccinationCardUpdateAvaible(final Boolean vaccinationCardUpdateAvaible) {
            this.vaccinationCardUpdateAvaible = vaccinationCardUpdateAvaible;
            return this;
        }

        /**
         * Adds the given items to the value of "vaccination"
         * 
         * @param vaccination
         *     Items to add to the value of the "vaccination" property
         */
        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> addVaccination(final Iterable<? extends VaccinationType> vaccination) {
            if (vaccination!= null) {
                if (this.vaccination == null) {
                    this.vaccination = new ArrayList<>();
                }
                for (VaccinationType _item: vaccination) {
                    this.vaccination.add(new VaccinationType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "vaccination" (any previous value will be replaced)
         * 
         * @param vaccination
         *     New value of the "vaccination" property.
         */
        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> withVaccination(final Iterable<? extends VaccinationType> vaccination) {
            if (this.vaccination!= null) {
                this.vaccination.clear();
            }
            return addVaccination(vaccination);
        }

        /**
         * Adds the given items to the value of "vaccination"
         * 
         * @param vaccination
         *     Items to add to the value of the "vaccination" property
         */
        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> addVaccination(VaccinationType... vaccination) {
            addVaccination(Arrays.asList(vaccination));
            return this;
        }

        /**
         * Sets the new value of "vaccination" (any previous value will be replaced)
         * 
         * @param vaccination
         *     New value of the "vaccination" property.
         */
        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> withVaccination(VaccinationType... vaccination) {
            withVaccination(Arrays.asList(vaccination));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "Vaccination"
         * property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VaccinationType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "Vaccination" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VaccinationType.Builder#end()} to
         *     return to the current builder.
         */
        public VaccinationType.Builder<? extends GetVaccinationCardIfUpdatedResponseType.Builder<_B>> addVaccination() {
            if (this.vaccination == null) {
                this.vaccination = new ArrayList<>();
            }
            final VaccinationType.Builder<GetVaccinationCardIfUpdatedResponseType.Builder<_B>> vaccination_Builder = new VaccinationType.Builder<>(this, null, false);
            this.vaccination.add(vaccination_Builder);
            return vaccination_Builder;
        }

        /**
         * Sets the new value of "negativeConsentStructure" (any previous value will be
         * replaced)
         * 
         * @param negativeConsentStructure
         *     New value of the "negativeConsentStructure" property.
         */
        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> withNegativeConsentStructure(final NegativeConsentStructureType negativeConsentStructure) {
            this.negativeConsentStructure = ((negativeConsentStructure == null)?null:new NegativeConsentStructureType.Builder<>(this, negativeConsentStructure, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "negativeConsentStructure" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.NegativeConsentStructureType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "negativeConsentStructure" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.NegativeConsentStructureType.Builder#end()}
         *     to return to the current builder.
         */
        public NegativeConsentStructureType.Builder<? extends GetVaccinationCardIfUpdatedResponseType.Builder<_B>> withNegativeConsentStructure() {
            if (this.negativeConsentStructure!= null) {
                return this.negativeConsentStructure;
            }
            return this.negativeConsentStructure = new NegativeConsentStructureType.Builder<>(this, null, false);
        }

        @Override
        public GetVaccinationCardIfUpdatedResponseType build() {
            if (_storedValue == null) {
                return this.init(new GetVaccinationCardIfUpdatedResponseType());
            } else {
                return ((GetVaccinationCardIfUpdatedResponseType) _storedValue);
            }
        }

        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> copyOf(final GetVaccinationCardIfUpdatedResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetVaccinationCardIfUpdatedResponseType.Builder<_B> copyOf(final GetVaccinationCardIfUpdatedResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetVaccinationCardIfUpdatedResponseType.Selector<GetVaccinationCardIfUpdatedResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetVaccinationCardIfUpdatedResponseType.Select _root() {
            return new GetVaccinationCardIfUpdatedResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationCardIfUpdatedResponseType.Selector<TRoot, TParent>> vaccinationCardUpdateAvaible = null;
        private VaccinationType.Selector<TRoot, GetVaccinationCardIfUpdatedResponseType.Selector<TRoot, TParent>> vaccination = null;
        private NegativeConsentStructureType.Selector<TRoot, GetVaccinationCardIfUpdatedResponseType.Selector<TRoot, TParent>> negativeConsentStructure = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.vaccinationCardUpdateAvaible!= null) {
                products.put("vaccinationCardUpdateAvaible", this.vaccinationCardUpdateAvaible.init());
            }
            if (this.vaccination!= null) {
                products.put("vaccination", this.vaccination.init());
            }
            if (this.negativeConsentStructure!= null) {
                products.put("negativeConsentStructure", this.negativeConsentStructure.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationCardIfUpdatedResponseType.Selector<TRoot, TParent>> vaccinationCardUpdateAvaible() {
            return ((this.vaccinationCardUpdateAvaible == null)?this.vaccinationCardUpdateAvaible = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationCardUpdateAvaible"):this.vaccinationCardUpdateAvaible);
        }

        public VaccinationType.Selector<TRoot, GetVaccinationCardIfUpdatedResponseType.Selector<TRoot, TParent>> vaccination() {
            return ((this.vaccination == null)?this.vaccination = new VaccinationType.Selector<>(this._root, this, "vaccination"):this.vaccination);
        }

        public NegativeConsentStructureType.Selector<TRoot, GetVaccinationCardIfUpdatedResponseType.Selector<TRoot, TParent>> negativeConsentStructure() {
            return ((this.negativeConsentStructure == null)?this.negativeConsentStructure = new NegativeConsentStructureType.Selector<>(this._root, this, "negativeConsentStructure"):this.negativeConsentStructure);
        }

    }

}
