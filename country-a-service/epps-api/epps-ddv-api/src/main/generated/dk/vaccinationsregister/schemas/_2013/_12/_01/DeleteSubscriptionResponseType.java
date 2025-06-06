
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
 * <p>Java class for DeleteSubscriptionResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DeleteSubscriptionResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteSubscriptionResponseType", propOrder = {
    "plannedVaccination"
})
public class DeleteSubscriptionResponseType {

    @XmlElement(name = "PlannedVaccination")
    protected List<PlannedVaccinationType> plannedVaccination;

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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DeleteSubscriptionResponseType.Builder<_B> _other) {
        if (this.plannedVaccination == null) {
            _other.plannedVaccination = null;
        } else {
            _other.plannedVaccination = new ArrayList<>();
            for (PlannedVaccinationType _item: this.plannedVaccination) {
                _other.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >DeleteSubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DeleteSubscriptionResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public DeleteSubscriptionResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DeleteSubscriptionResponseType.Builder<Void> builder() {
        return new DeleteSubscriptionResponseType.Builder<>(null, null, false);
    }

    public static<_B >DeleteSubscriptionResponseType.Builder<_B> copyOf(final DeleteSubscriptionResponseType _other) {
        final DeleteSubscriptionResponseType.Builder<_B> _newBuilder = new DeleteSubscriptionResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DeleteSubscriptionResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
    }

    public<_B >DeleteSubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DeleteSubscriptionResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DeleteSubscriptionResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DeleteSubscriptionResponseType.Builder<_B> copyOf(final DeleteSubscriptionResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DeleteSubscriptionResponseType.Builder<_B> _newBuilder = new DeleteSubscriptionResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DeleteSubscriptionResponseType.Builder<Void> copyExcept(final DeleteSubscriptionResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DeleteSubscriptionResponseType.Builder<Void> copyOnly(final DeleteSubscriptionResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DeleteSubscriptionResponseType _storedValue;
        private List<PlannedVaccinationType.Builder<DeleteSubscriptionResponseType.Builder<_B>>> plannedVaccination;

        public Builder(final _B _parentBuilder, final DeleteSubscriptionResponseType _other, final boolean _copy) {
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
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DeleteSubscriptionResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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

        protected<_P extends DeleteSubscriptionResponseType >_P init(final _P _product) {
            if (this.plannedVaccination!= null) {
                final List<PlannedVaccinationType> plannedVaccination = new ArrayList<>(this.plannedVaccination.size());
                for (PlannedVaccinationType.Builder<DeleteSubscriptionResponseType.Builder<_B>> _item: this.plannedVaccination) {
                    plannedVaccination.add(_item.build());
                }
                _product.plannedVaccination = plannedVaccination;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "plannedVaccination"
         * 
         * @param plannedVaccination
         *     Items to add to the value of the "plannedVaccination" property
         */
        public DeleteSubscriptionResponseType.Builder<_B> addPlannedVaccination(final Iterable<? extends PlannedVaccinationType> plannedVaccination) {
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
        public DeleteSubscriptionResponseType.Builder<_B> withPlannedVaccination(final Iterable<? extends PlannedVaccinationType> plannedVaccination) {
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
        public DeleteSubscriptionResponseType.Builder<_B> addPlannedVaccination(PlannedVaccinationType... plannedVaccination) {
            addPlannedVaccination(Arrays.asList(plannedVaccination));
            return this;
        }

        /**
         * Sets the new value of "plannedVaccination" (any previous value will be replaced)
         * 
         * @param plannedVaccination
         *     New value of the "plannedVaccination" property.
         */
        public DeleteSubscriptionResponseType.Builder<_B> withPlannedVaccination(PlannedVaccinationType... plannedVaccination) {
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
        public PlannedVaccinationType.Builder<? extends DeleteSubscriptionResponseType.Builder<_B>> addPlannedVaccination() {
            if (this.plannedVaccination == null) {
                this.plannedVaccination = new ArrayList<>();
            }
            final PlannedVaccinationType.Builder<DeleteSubscriptionResponseType.Builder<_B>> plannedVaccination_Builder = new PlannedVaccinationType.Builder<>(this, null, false);
            this.plannedVaccination.add(plannedVaccination_Builder);
            return plannedVaccination_Builder;
        }

        @Override
        public DeleteSubscriptionResponseType build() {
            if (_storedValue == null) {
                return this.init(new DeleteSubscriptionResponseType());
            } else {
                return ((DeleteSubscriptionResponseType) _storedValue);
            }
        }

        public DeleteSubscriptionResponseType.Builder<_B> copyOf(final DeleteSubscriptionResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public DeleteSubscriptionResponseType.Builder<_B> copyOf(final DeleteSubscriptionResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DeleteSubscriptionResponseType.Selector<DeleteSubscriptionResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DeleteSubscriptionResponseType.Select _root() {
            return new DeleteSubscriptionResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private PlannedVaccinationType.Selector<TRoot, DeleteSubscriptionResponseType.Selector<TRoot, TParent>> plannedVaccination = null;

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
            return products;
        }

        public PlannedVaccinationType.Selector<TRoot, DeleteSubscriptionResponseType.Selector<TRoot, TParent>> plannedVaccination() {
            return ((this.plannedVaccination == null)?this.plannedVaccination = new PlannedVaccinationType.Selector<>(this._root, this, "plannedVaccination"):this.plannedVaccination);
        }

    }

}
