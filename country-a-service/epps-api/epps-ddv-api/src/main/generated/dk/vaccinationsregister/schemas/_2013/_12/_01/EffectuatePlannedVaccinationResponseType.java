
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
 * <p>Java class for EffectuatePlannedVaccinationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EffectuatePlannedVaccinationResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Vaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EffectuatePlannedVaccinationResponseType", propOrder = {
    "vaccination"
})
public class EffectuatePlannedVaccinationResponseType {

    @XmlElement(name = "Vaccination")
    protected VaccinationType vaccination;

    /**
     * Gets the value of the vaccination property.
     * 
     * @return
     *     possible object is
     *     {@link VaccinationType }
     *     
     */
    public VaccinationType getVaccination() {
        return vaccination;
    }

    /**
     * Sets the value of the vaccination property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccinationType }
     *     
     */
    public void setVaccination(VaccinationType value) {
        this.vaccination = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final EffectuatePlannedVaccinationResponseType.Builder<_B> _other) {
        _other.vaccination = ((this.vaccination == null)?null:this.vaccination.newCopyBuilder(_other));
    }

    public<_B >EffectuatePlannedVaccinationResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new EffectuatePlannedVaccinationResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public EffectuatePlannedVaccinationResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static EffectuatePlannedVaccinationResponseType.Builder<Void> builder() {
        return new EffectuatePlannedVaccinationResponseType.Builder<>(null, null, false);
    }

    public static<_B >EffectuatePlannedVaccinationResponseType.Builder<_B> copyOf(final EffectuatePlannedVaccinationResponseType _other) {
        final EffectuatePlannedVaccinationResponseType.Builder<_B> _newBuilder = new EffectuatePlannedVaccinationResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final EffectuatePlannedVaccinationResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree vaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPropertyTree!= null):((vaccinationPropertyTree == null)||(!vaccinationPropertyTree.isLeaf())))) {
            _other.vaccination = ((this.vaccination == null)?null:this.vaccination.newCopyBuilder(_other, vaccinationPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >EffectuatePlannedVaccinationResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new EffectuatePlannedVaccinationResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public EffectuatePlannedVaccinationResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >EffectuatePlannedVaccinationResponseType.Builder<_B> copyOf(final EffectuatePlannedVaccinationResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final EffectuatePlannedVaccinationResponseType.Builder<_B> _newBuilder = new EffectuatePlannedVaccinationResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static EffectuatePlannedVaccinationResponseType.Builder<Void> copyExcept(final EffectuatePlannedVaccinationResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static EffectuatePlannedVaccinationResponseType.Builder<Void> copyOnly(final EffectuatePlannedVaccinationResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final EffectuatePlannedVaccinationResponseType _storedValue;
        private VaccinationType.Builder<EffectuatePlannedVaccinationResponseType.Builder<_B>> vaccination;

        public Builder(final _B _parentBuilder, final EffectuatePlannedVaccinationResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.vaccination = ((_other.vaccination == null)?null:_other.vaccination.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final EffectuatePlannedVaccinationResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree vaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPropertyTree!= null):((vaccinationPropertyTree == null)||(!vaccinationPropertyTree.isLeaf())))) {
                        this.vaccination = ((_other.vaccination == null)?null:_other.vaccination.newCopyBuilder(this, vaccinationPropertyTree, _propertyTreeUse));
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

        protected<_P extends EffectuatePlannedVaccinationResponseType >_P init(final _P _product) {
            _product.vaccination = ((this.vaccination == null)?null:this.vaccination.build());
            return _product;
        }

        /**
         * Sets the new value of "vaccination" (any previous value will be replaced)
         * 
         * @param vaccination
         *     New value of the "vaccination" property.
         */
        public EffectuatePlannedVaccinationResponseType.Builder<_B> withVaccination(final VaccinationType vaccination) {
            this.vaccination = ((vaccination == null)?null:new VaccinationType.Builder<>(this, vaccination, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "vaccination" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VaccinationType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "vaccination" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VaccinationType.Builder#end()} to
         *     return to the current builder.
         */
        public VaccinationType.Builder<? extends EffectuatePlannedVaccinationResponseType.Builder<_B>> withVaccination() {
            if (this.vaccination!= null) {
                return this.vaccination;
            }
            return this.vaccination = new VaccinationType.Builder<>(this, null, false);
        }

        @Override
        public EffectuatePlannedVaccinationResponseType build() {
            if (_storedValue == null) {
                return this.init(new EffectuatePlannedVaccinationResponseType());
            } else {
                return ((EffectuatePlannedVaccinationResponseType) _storedValue);
            }
        }

        public EffectuatePlannedVaccinationResponseType.Builder<_B> copyOf(final EffectuatePlannedVaccinationResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public EffectuatePlannedVaccinationResponseType.Builder<_B> copyOf(final EffectuatePlannedVaccinationResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends EffectuatePlannedVaccinationResponseType.Selector<EffectuatePlannedVaccinationResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static EffectuatePlannedVaccinationResponseType.Select _root() {
            return new EffectuatePlannedVaccinationResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private VaccinationType.Selector<TRoot, EffectuatePlannedVaccinationResponseType.Selector<TRoot, TParent>> vaccination = null;

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
            return products;
        }

        public VaccinationType.Selector<TRoot, EffectuatePlannedVaccinationResponseType.Selector<TRoot, TParent>> vaccination() {
            return ((this.vaccination == null)?this.vaccination = new VaccinationType.Selector<>(this._root, this, "vaccination"):this.vaccination);
        }

    }

}
