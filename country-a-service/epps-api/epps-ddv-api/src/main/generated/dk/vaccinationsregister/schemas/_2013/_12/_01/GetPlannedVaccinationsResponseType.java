
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
 * <p>Java class for GetPlannedVaccinationsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetPlannedVaccinationsResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="PlannedVaccNegativeConsentStructure" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccNegativeConsentStructureType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPlannedVaccinationsResponseType", propOrder = {
    "plannedVaccination",
    "plannedVaccNegativeConsentStructure"
})
public class GetPlannedVaccinationsResponseType {

    @XmlElement(name = "PlannedVaccination")
    protected List<PlannedVaccinationType> plannedVaccination;
    @XmlElement(name = "PlannedVaccNegativeConsentStructure")
    protected PlannedVaccNegativeConsentStructureType plannedVaccNegativeConsentStructure;

    /**
     * Gets the value of the plannedVaccination property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the plannedVaccination property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlannedVaccination().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlannedVaccinationType }
     * 
     * 
     * @return
     *     The value of the plannedVaccination property.
     */
    public List<PlannedVaccinationType> getPlannedVaccination() {
        if (plannedVaccination == null) {
            plannedVaccination = new ArrayList<>();
        }
        return this.plannedVaccination;
    }

    /**
     * Gets the value of the plannedVaccNegativeConsentStructure property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedVaccNegativeConsentStructureType }
     *     
     */
    public PlannedVaccNegativeConsentStructureType getPlannedVaccNegativeConsentStructure() {
        return plannedVaccNegativeConsentStructure;
    }

    /**
     * Sets the value of the plannedVaccNegativeConsentStructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedVaccNegativeConsentStructureType }
     *     
     */
    public void setPlannedVaccNegativeConsentStructure(PlannedVaccNegativeConsentStructureType value) {
        this.plannedVaccNegativeConsentStructure = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetPlannedVaccinationsResponseType.Builder<_B> _other) {
        if (this.plannedVaccination == null) {
            _other.plannedVaccination = null;
        } else {
            _other.plannedVaccination = new ArrayList<>();
            for (PlannedVaccinationType _item: this.plannedVaccination) {
                _other.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
        _other.plannedVaccNegativeConsentStructure = ((this.plannedVaccNegativeConsentStructure == null)?null:this.plannedVaccNegativeConsentStructure.newCopyBuilder(_other));
    }

    public<_B >GetPlannedVaccinationsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetPlannedVaccinationsResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetPlannedVaccinationsResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetPlannedVaccinationsResponseType.Builder<Void> builder() {
        return new GetPlannedVaccinationsResponseType.Builder<>(null, null, false);
    }

    public static<_B >GetPlannedVaccinationsResponseType.Builder<_B> copyOf(final GetPlannedVaccinationsResponseType _other) {
        final GetPlannedVaccinationsResponseType.Builder<_B> _newBuilder = new GetPlannedVaccinationsResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetPlannedVaccinationsResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
            if (this.plannedVaccination == null) {
                _other.plannedVaccination = null;
            } else {
                _other.plannedVaccination = new ArrayList<>();
                for (PlannedVaccinationType _item: this.plannedVaccination) {
                    _other.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(_other, plannedVaccinationPropertyTree, _propertyTreeUse)));
                }
            }
        }
        final PropertyTree plannedVaccNegativeConsentStructurePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccNegativeConsentStructure"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccNegativeConsentStructurePropertyTree!= null):((plannedVaccNegativeConsentStructurePropertyTree == null)||(!plannedVaccNegativeConsentStructurePropertyTree.isLeaf())))) {
            _other.plannedVaccNegativeConsentStructure = ((this.plannedVaccNegativeConsentStructure == null)?null:this.plannedVaccNegativeConsentStructure.newCopyBuilder(_other, plannedVaccNegativeConsentStructurePropertyTree, _propertyTreeUse));
        }
    }

    public<_B >GetPlannedVaccinationsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetPlannedVaccinationsResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetPlannedVaccinationsResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetPlannedVaccinationsResponseType.Builder<_B> copyOf(final GetPlannedVaccinationsResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetPlannedVaccinationsResponseType.Builder<_B> _newBuilder = new GetPlannedVaccinationsResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetPlannedVaccinationsResponseType.Builder<Void> copyExcept(final GetPlannedVaccinationsResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetPlannedVaccinationsResponseType.Builder<Void> copyOnly(final GetPlannedVaccinationsResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetPlannedVaccinationsResponseType _storedValue;
        private List<PlannedVaccinationType.Builder<GetPlannedVaccinationsResponseType.Builder<_B>>> plannedVaccination;
        private PlannedVaccNegativeConsentStructureType.Builder<GetPlannedVaccinationsResponseType.Builder<_B>> plannedVaccNegativeConsentStructure;

        public Builder(final _B _parentBuilder, final GetPlannedVaccinationsResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.plannedVaccination == null) {
                        this.plannedVaccination = null;
                    } else {
                        this.plannedVaccination = new ArrayList<>();
                        for (PlannedVaccinationType _item: _other.plannedVaccination) {
                            this.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                    this.plannedVaccNegativeConsentStructure = ((_other.plannedVaccNegativeConsentStructure == null)?null:_other.plannedVaccNegativeConsentStructure.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetPlannedVaccinationsResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
                        if (_other.plannedVaccination == null) {
                            this.plannedVaccination = null;
                        } else {
                            this.plannedVaccination = new ArrayList<>();
                            for (PlannedVaccinationType _item: _other.plannedVaccination) {
                                this.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(this, plannedVaccinationPropertyTree, _propertyTreeUse)));
                            }
                        }
                    }
                    final PropertyTree plannedVaccNegativeConsentStructurePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccNegativeConsentStructure"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccNegativeConsentStructurePropertyTree!= null):((plannedVaccNegativeConsentStructurePropertyTree == null)||(!plannedVaccNegativeConsentStructurePropertyTree.isLeaf())))) {
                        this.plannedVaccNegativeConsentStructure = ((_other.plannedVaccNegativeConsentStructure == null)?null:_other.plannedVaccNegativeConsentStructure.newCopyBuilder(this, plannedVaccNegativeConsentStructurePropertyTree, _propertyTreeUse));
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

        protected<_P extends GetPlannedVaccinationsResponseType >_P init(final _P _product) {
            if (this.plannedVaccination!= null) {
                final List<PlannedVaccinationType> plannedVaccination = new ArrayList<>(this.plannedVaccination.size());
                for (PlannedVaccinationType.Builder<GetPlannedVaccinationsResponseType.Builder<_B>> _item: this.plannedVaccination) {
                    plannedVaccination.add(_item.build());
                }
                _product.plannedVaccination = plannedVaccination;
            }
            _product.plannedVaccNegativeConsentStructure = ((this.plannedVaccNegativeConsentStructure == null)?null:this.plannedVaccNegativeConsentStructure.build());
            return _product;
        }

        /**
         * Adds the given items to the value of "plannedVaccination"
         * 
         * @param plannedVaccination
         *     Items to add to the value of the "plannedVaccination" property
         */
        public GetPlannedVaccinationsResponseType.Builder<_B> addPlannedVaccination(final Iterable<? extends PlannedVaccinationType> plannedVaccination) {
            if (plannedVaccination!= null) {
                if (this.plannedVaccination == null) {
                    this.plannedVaccination = new ArrayList<>();
                }
                for (PlannedVaccinationType _item: plannedVaccination) {
                    this.plannedVaccination.add(new PlannedVaccinationType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "plannedVaccination" (any previous value will be replaced)
         * 
         * @param plannedVaccination
         *     New value of the "plannedVaccination" property.
         */
        public GetPlannedVaccinationsResponseType.Builder<_B> withPlannedVaccination(final Iterable<? extends PlannedVaccinationType> plannedVaccination) {
            if (this.plannedVaccination!= null) {
                this.plannedVaccination.clear();
            }
            return addPlannedVaccination(plannedVaccination);
        }

        /**
         * Adds the given items to the value of "plannedVaccination"
         * 
         * @param plannedVaccination
         *     Items to add to the value of the "plannedVaccination" property
         */
        public GetPlannedVaccinationsResponseType.Builder<_B> addPlannedVaccination(PlannedVaccinationType... plannedVaccination) {
            addPlannedVaccination(Arrays.asList(plannedVaccination));
            return this;
        }

        /**
         * Sets the new value of "plannedVaccination" (any previous value will be replaced)
         * 
         * @param plannedVaccination
         *     New value of the "plannedVaccination" property.
         */
        public GetPlannedVaccinationsResponseType.Builder<_B> withPlannedVaccination(PlannedVaccinationType... plannedVaccination) {
            withPlannedVaccination(Arrays.asList(plannedVaccination));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "PlannedVaccination"
         * property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccinationType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "PlannedVaccination" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccinationType.Builder#end()}
         *     to return to the current builder.
         */
        public PlannedVaccinationType.Builder<? extends GetPlannedVaccinationsResponseType.Builder<_B>> addPlannedVaccination() {
            if (this.plannedVaccination == null) {
                this.plannedVaccination = new ArrayList<>();
            }
            final PlannedVaccinationType.Builder<GetPlannedVaccinationsResponseType.Builder<_B>> plannedVaccination_Builder = new PlannedVaccinationType.Builder<>(this, null, false);
            this.plannedVaccination.add(plannedVaccination_Builder);
            return plannedVaccination_Builder;
        }

        /**
         * Sets the new value of "plannedVaccNegativeConsentStructure" (any previous value
         * will be replaced)
         * 
         * @param plannedVaccNegativeConsentStructure
         *     New value of the "plannedVaccNegativeConsentStructure" property.
         */
        public GetPlannedVaccinationsResponseType.Builder<_B> withPlannedVaccNegativeConsentStructure(final PlannedVaccNegativeConsentStructureType plannedVaccNegativeConsentStructure) {
            this.plannedVaccNegativeConsentStructure = ((plannedVaccNegativeConsentStructure == null)?null:new PlannedVaccNegativeConsentStructureType.Builder<>(this, plannedVaccNegativeConsentStructure, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "plannedVaccNegativeConsentStructure" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccNegativeConsentStructureType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "plannedVaccNegativeConsentStructure"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccNegativeConsentStructureType.Builder#end()}
         *     to return to the current builder.
         */
        public PlannedVaccNegativeConsentStructureType.Builder<? extends GetPlannedVaccinationsResponseType.Builder<_B>> withPlannedVaccNegativeConsentStructure() {
            if (this.plannedVaccNegativeConsentStructure!= null) {
                return this.plannedVaccNegativeConsentStructure;
            }
            return this.plannedVaccNegativeConsentStructure = new PlannedVaccNegativeConsentStructureType.Builder<>(this, null, false);
        }

        @Override
        public GetPlannedVaccinationsResponseType build() {
            if (_storedValue == null) {
                return this.init(new GetPlannedVaccinationsResponseType());
            } else {
                return ((GetPlannedVaccinationsResponseType) _storedValue);
            }
        }

        public GetPlannedVaccinationsResponseType.Builder<_B> copyOf(final GetPlannedVaccinationsResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetPlannedVaccinationsResponseType.Builder<_B> copyOf(final GetPlannedVaccinationsResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetPlannedVaccinationsResponseType.Selector<GetPlannedVaccinationsResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetPlannedVaccinationsResponseType.Select _root() {
            return new GetPlannedVaccinationsResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private PlannedVaccinationType.Selector<TRoot, GetPlannedVaccinationsResponseType.Selector<TRoot, TParent>> plannedVaccination = null;
        private PlannedVaccNegativeConsentStructureType.Selector<TRoot, GetPlannedVaccinationsResponseType.Selector<TRoot, TParent>> plannedVaccNegativeConsentStructure = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.plannedVaccination!= null) {
                products.put("plannedVaccination", this.plannedVaccination.init());
            }
            if (this.plannedVaccNegativeConsentStructure!= null) {
                products.put("plannedVaccNegativeConsentStructure", this.plannedVaccNegativeConsentStructure.init());
            }
            return products;
        }

        public PlannedVaccinationType.Selector<TRoot, GetPlannedVaccinationsResponseType.Selector<TRoot, TParent>> plannedVaccination() {
            return ((this.plannedVaccination == null)?this.plannedVaccination = new PlannedVaccinationType.Selector<>(this._root, this, "plannedVaccination"):this.plannedVaccination);
        }

        public PlannedVaccNegativeConsentStructureType.Selector<TRoot, GetPlannedVaccinationsResponseType.Selector<TRoot, TParent>> plannedVaccNegativeConsentStructure() {
            return ((this.plannedVaccNegativeConsentStructure == null)?this.plannedVaccNegativeConsentStructure = new PlannedVaccNegativeConsentStructureType.Selector<>(this._root, this, "plannedVaccNegativeConsentStructure"):this.plannedVaccNegativeConsentStructure);
        }

    }

}
