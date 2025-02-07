
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
 * <p>Java class for UpdateVaccinationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="UpdateVaccinationResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="VersionMismatchWarningIndicator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VersionMismatchWarningIndicatorType" minOccurs="0"/>
 *         <element name="Vaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateVaccinationResponseType", propOrder = {
    "versionMismatchWarningIndicator",
    "vaccination"
})
public class UpdateVaccinationResponseType {

    @XmlElement(name = "VersionMismatchWarningIndicator")
    protected VersionMismatchWarningIndicatorType versionMismatchWarningIndicator;
    @XmlElement(name = "Vaccination")
    protected List<VaccinationType> vaccination;

    /**
     * Gets the value of the versionMismatchWarningIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link VersionMismatchWarningIndicatorType }
     *     
     */
    public VersionMismatchWarningIndicatorType getVersionMismatchWarningIndicator() {
        return versionMismatchWarningIndicator;
    }

    /**
     * Sets the value of the versionMismatchWarningIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link VersionMismatchWarningIndicatorType }
     *     
     */
    public void setVersionMismatchWarningIndicator(VersionMismatchWarningIndicatorType value) {
        this.versionMismatchWarningIndicator = value;
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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final UpdateVaccinationResponseType.Builder<_B> _other) {
        _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other));
        if (this.vaccination == null) {
            _other.vaccination = null;
        } else {
            _other.vaccination = new ArrayList<>();
            for (VaccinationType _item: this.vaccination) {
                _other.vaccination.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >UpdateVaccinationResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new UpdateVaccinationResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public UpdateVaccinationResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static UpdateVaccinationResponseType.Builder<Void> builder() {
        return new UpdateVaccinationResponseType.Builder<>(null, null, false);
    }

    public static<_B >UpdateVaccinationResponseType.Builder<_B> copyOf(final UpdateVaccinationResponseType _other) {
        final UpdateVaccinationResponseType.Builder<_B> _newBuilder = new UpdateVaccinationResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final UpdateVaccinationResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree versionMismatchWarningIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("versionMismatchWarningIndicator"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionMismatchWarningIndicatorPropertyTree!= null):((versionMismatchWarningIndicatorPropertyTree == null)||(!versionMismatchWarningIndicatorPropertyTree.isLeaf())))) {
            _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other, versionMismatchWarningIndicatorPropertyTree, _propertyTreeUse));
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
    }

    public<_B >UpdateVaccinationResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new UpdateVaccinationResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public UpdateVaccinationResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >UpdateVaccinationResponseType.Builder<_B> copyOf(final UpdateVaccinationResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final UpdateVaccinationResponseType.Builder<_B> _newBuilder = new UpdateVaccinationResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static UpdateVaccinationResponseType.Builder<Void> copyExcept(final UpdateVaccinationResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static UpdateVaccinationResponseType.Builder<Void> copyOnly(final UpdateVaccinationResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final UpdateVaccinationResponseType _storedValue;
        private VersionMismatchWarningIndicatorType.Builder<UpdateVaccinationResponseType.Builder<_B>> versionMismatchWarningIndicator;
        private List<VaccinationType.Builder<UpdateVaccinationResponseType.Builder<_B>>> vaccination;

        public Builder(final _B _parentBuilder, final UpdateVaccinationResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.versionMismatchWarningIndicator = ((_other.versionMismatchWarningIndicator == null)?null:_other.versionMismatchWarningIndicator.newCopyBuilder(this));
                    if (_other.vaccination == null) {
                        this.vaccination = null;
                    } else {
                        this.vaccination = new ArrayList<>();
                        for (VaccinationType _item: _other.vaccination) {
                            this.vaccination.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final UpdateVaccinationResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree versionMismatchWarningIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("versionMismatchWarningIndicator"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionMismatchWarningIndicatorPropertyTree!= null):((versionMismatchWarningIndicatorPropertyTree == null)||(!versionMismatchWarningIndicatorPropertyTree.isLeaf())))) {
                        this.versionMismatchWarningIndicator = ((_other.versionMismatchWarningIndicator == null)?null:_other.versionMismatchWarningIndicator.newCopyBuilder(this, versionMismatchWarningIndicatorPropertyTree, _propertyTreeUse));
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

        protected<_P extends UpdateVaccinationResponseType >_P init(final _P _product) {
            _product.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.build());
            if (this.vaccination!= null) {
                final List<VaccinationType> vaccination = new ArrayList<>(this.vaccination.size());
                for (VaccinationType.Builder<UpdateVaccinationResponseType.Builder<_B>> _item: this.vaccination) {
                    vaccination.add(_item.build());
                }
                _product.vaccination = vaccination;
            }
            return _product;
        }

        /**
         * Sets the new value of "versionMismatchWarningIndicator" (any previous value will
         * be replaced)
         * 
         * @param versionMismatchWarningIndicator
         *     New value of the "versionMismatchWarningIndicator" property.
         */
        public UpdateVaccinationResponseType.Builder<_B> withVersionMismatchWarningIndicator(final VersionMismatchWarningIndicatorType versionMismatchWarningIndicator) {
            this.versionMismatchWarningIndicator = ((versionMismatchWarningIndicator == null)?null:new VersionMismatchWarningIndicatorType.Builder<>(this, versionMismatchWarningIndicator, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "versionMismatchWarningIndicator" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VersionMismatchWarningIndicatorType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "versionMismatchWarningIndicator"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VersionMismatchWarningIndicatorType.Builder#end()}
         *     to return to the current builder.
         */
        public VersionMismatchWarningIndicatorType.Builder<? extends UpdateVaccinationResponseType.Builder<_B>> withVersionMismatchWarningIndicator() {
            if (this.versionMismatchWarningIndicator!= null) {
                return this.versionMismatchWarningIndicator;
            }
            return this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Builder<>(this, null, false);
        }

        /**
         * Adds the given items to the value of "vaccination"
         * 
         * @param vaccination
         *     Items to add to the value of the "vaccination" property
         */
        public UpdateVaccinationResponseType.Builder<_B> addVaccination(final Iterable<? extends VaccinationType> vaccination) {
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
        public UpdateVaccinationResponseType.Builder<_B> withVaccination(final Iterable<? extends VaccinationType> vaccination) {
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
        public UpdateVaccinationResponseType.Builder<_B> addVaccination(VaccinationType... vaccination) {
            addVaccination(Arrays.asList(vaccination));
            return this;
        }

        /**
         * Sets the new value of "vaccination" (any previous value will be replaced)
         * 
         * @param vaccination
         *     New value of the "vaccination" property.
         */
        public UpdateVaccinationResponseType.Builder<_B> withVaccination(VaccinationType... vaccination) {
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
        public VaccinationType.Builder<? extends UpdateVaccinationResponseType.Builder<_B>> addVaccination() {
            if (this.vaccination == null) {
                this.vaccination = new ArrayList<>();
            }
            final VaccinationType.Builder<UpdateVaccinationResponseType.Builder<_B>> vaccination_Builder = new VaccinationType.Builder<>(this, null, false);
            this.vaccination.add(vaccination_Builder);
            return vaccination_Builder;
        }

        @Override
        public UpdateVaccinationResponseType build() {
            if (_storedValue == null) {
                return this.init(new UpdateVaccinationResponseType());
            } else {
                return ((UpdateVaccinationResponseType) _storedValue);
            }
        }

        public UpdateVaccinationResponseType.Builder<_B> copyOf(final UpdateVaccinationResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public UpdateVaccinationResponseType.Builder<_B> copyOf(final UpdateVaccinationResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends UpdateVaccinationResponseType.Selector<UpdateVaccinationResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static UpdateVaccinationResponseType.Select _root() {
            return new UpdateVaccinationResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private VersionMismatchWarningIndicatorType.Selector<TRoot, UpdateVaccinationResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator = null;
        private VaccinationType.Selector<TRoot, UpdateVaccinationResponseType.Selector<TRoot, TParent>> vaccination = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.versionMismatchWarningIndicator!= null) {
                products.put("versionMismatchWarningIndicator", this.versionMismatchWarningIndicator.init());
            }
            if (this.vaccination!= null) {
                products.put("vaccination", this.vaccination.init());
            }
            return products;
        }

        public VersionMismatchWarningIndicatorType.Selector<TRoot, UpdateVaccinationResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator() {
            return ((this.versionMismatchWarningIndicator == null)?this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Selector<>(this._root, this, "versionMismatchWarningIndicator"):this.versionMismatchWarningIndicator);
        }

        public VaccinationType.Selector<TRoot, UpdateVaccinationResponseType.Selector<TRoot, TParent>> vaccination() {
            return ((this.vaccination == null)?this.vaccination = new VaccinationType.Selector<>(this._root, this, "vaccination"):this.vaccination);
        }

    }

}
