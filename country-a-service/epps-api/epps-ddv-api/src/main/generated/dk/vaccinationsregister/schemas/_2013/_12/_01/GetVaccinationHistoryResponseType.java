
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
 * <p>Java class for GetVaccinationHistoryResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetVaccinationHistoryResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Vaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="NegativeConsentStructure" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentStructureType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetVaccinationHistoryResponseType", propOrder = {
    "vaccination",
    "negativeConsentStructure"
})
public class GetVaccinationHistoryResponseType {

    @XmlElement(name = "Vaccination")
    protected List<VaccinationType> vaccination;
    @XmlElement(name = "NegativeConsentStructure")
    protected NegativeConsentStructureType negativeConsentStructure;

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
    public<_B >void copyTo(final GetVaccinationHistoryResponseType.Builder<_B> _other) {
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

    public<_B >GetVaccinationHistoryResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetVaccinationHistoryResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetVaccinationHistoryResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetVaccinationHistoryResponseType.Builder<Void> builder() {
        return new GetVaccinationHistoryResponseType.Builder<>(null, null, false);
    }

    public static<_B >GetVaccinationHistoryResponseType.Builder<_B> copyOf(final GetVaccinationHistoryResponseType _other) {
        final GetVaccinationHistoryResponseType.Builder<_B> _newBuilder = new GetVaccinationHistoryResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetVaccinationHistoryResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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

    public<_B >GetVaccinationHistoryResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetVaccinationHistoryResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetVaccinationHistoryResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetVaccinationHistoryResponseType.Builder<_B> copyOf(final GetVaccinationHistoryResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetVaccinationHistoryResponseType.Builder<_B> _newBuilder = new GetVaccinationHistoryResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetVaccinationHistoryResponseType.Builder<Void> copyExcept(final GetVaccinationHistoryResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetVaccinationHistoryResponseType.Builder<Void> copyOnly(final GetVaccinationHistoryResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetVaccinationHistoryResponseType _storedValue;
        private List<VaccinationType.Builder<GetVaccinationHistoryResponseType.Builder<_B>>> vaccination;
        private NegativeConsentStructureType.Builder<GetVaccinationHistoryResponseType.Builder<_B>> negativeConsentStructure;

        public Builder(final _B _parentBuilder, final GetVaccinationHistoryResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
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

        public Builder(final _B _parentBuilder, final GetVaccinationHistoryResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
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

        protected<_P extends GetVaccinationHistoryResponseType >_P init(final _P _product) {
            if (this.vaccination!= null) {
                final List<VaccinationType> vaccination = new ArrayList<>(this.vaccination.size());
                for (VaccinationType.Builder<GetVaccinationHistoryResponseType.Builder<_B>> _item: this.vaccination) {
                    vaccination.add(_item.build());
                }
                _product.vaccination = vaccination;
            }
            _product.negativeConsentStructure = ((this.negativeConsentStructure == null)?null:this.negativeConsentStructure.build());
            return _product;
        }

        /**
         * Adds the given items to the value of "vaccination"
         * 
         * @param vaccination
         *     Items to add to the value of the "vaccination" property
         */
        public GetVaccinationHistoryResponseType.Builder<_B> addVaccination(final Iterable<? extends VaccinationType> vaccination) {
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
        public GetVaccinationHistoryResponseType.Builder<_B> withVaccination(final Iterable<? extends VaccinationType> vaccination) {
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
        public GetVaccinationHistoryResponseType.Builder<_B> addVaccination(VaccinationType... vaccination) {
            addVaccination(Arrays.asList(vaccination));
            return this;
        }

        /**
         * Sets the new value of "vaccination" (any previous value will be replaced)
         * 
         * @param vaccination
         *     New value of the "vaccination" property.
         */
        public GetVaccinationHistoryResponseType.Builder<_B> withVaccination(VaccinationType... vaccination) {
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
        public VaccinationType.Builder<? extends GetVaccinationHistoryResponseType.Builder<_B>> addVaccination() {
            if (this.vaccination == null) {
                this.vaccination = new ArrayList<>();
            }
            final VaccinationType.Builder<GetVaccinationHistoryResponseType.Builder<_B>> vaccination_Builder = new VaccinationType.Builder<>(this, null, false);
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
        public GetVaccinationHistoryResponseType.Builder<_B> withNegativeConsentStructure(final NegativeConsentStructureType negativeConsentStructure) {
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
        public NegativeConsentStructureType.Builder<? extends GetVaccinationHistoryResponseType.Builder<_B>> withNegativeConsentStructure() {
            if (this.negativeConsentStructure!= null) {
                return this.negativeConsentStructure;
            }
            return this.negativeConsentStructure = new NegativeConsentStructureType.Builder<>(this, null, false);
        }

        @Override
        public GetVaccinationHistoryResponseType build() {
            if (_storedValue == null) {
                return this.init(new GetVaccinationHistoryResponseType());
            } else {
                return ((GetVaccinationHistoryResponseType) _storedValue);
            }
        }

        public GetVaccinationHistoryResponseType.Builder<_B> copyOf(final GetVaccinationHistoryResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetVaccinationHistoryResponseType.Builder<_B> copyOf(final GetVaccinationHistoryResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetVaccinationHistoryResponseType.Selector<GetVaccinationHistoryResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetVaccinationHistoryResponseType.Select _root() {
            return new GetVaccinationHistoryResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private VaccinationType.Selector<TRoot, GetVaccinationHistoryResponseType.Selector<TRoot, TParent>> vaccination = null;
        private NegativeConsentStructureType.Selector<TRoot, GetVaccinationHistoryResponseType.Selector<TRoot, TParent>> negativeConsentStructure = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.vaccination!= null) {
                products.put("vaccination", this.vaccination.init());
            }
            if (this.negativeConsentStructure!= null) {
                products.put("negativeConsentStructure", this.negativeConsentStructure.init());
            }
            return products;
        }

        public VaccinationType.Selector<TRoot, GetVaccinationHistoryResponseType.Selector<TRoot, TParent>> vaccination() {
            return ((this.vaccination == null)?this.vaccination = new VaccinationType.Selector<>(this._root, this, "vaccination"):this.vaccination);
        }

        public NegativeConsentStructureType.Selector<TRoot, GetVaccinationHistoryResponseType.Selector<TRoot, TParent>> negativeConsentStructure() {
            return ((this.negativeConsentStructure == null)?this.negativeConsentStructure = new NegativeConsentStructureType.Selector<>(this._root, this, "negativeConsentStructure"):this.negativeConsentStructure);
        }

    }

}
