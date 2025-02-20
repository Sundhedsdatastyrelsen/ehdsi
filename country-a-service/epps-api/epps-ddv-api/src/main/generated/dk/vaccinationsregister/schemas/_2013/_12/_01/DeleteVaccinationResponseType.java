
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
 * <p>Java class for DeleteVaccinationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DeleteVaccinationResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="VersionMismatchWarningIndicator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VersionMismatchWarningIndicatorType" minOccurs="0"/>
 *         <element name="Vaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteVaccinationResponseType", propOrder = {
    "versionMismatchWarningIndicator",
    "vaccination"
})
public class DeleteVaccinationResponseType {

    @XmlElement(name = "VersionMismatchWarningIndicator")
    protected VersionMismatchWarningIndicatorType versionMismatchWarningIndicator;
    @XmlElement(name = "Vaccination", required = true)
    protected VaccinationType vaccination;

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
    public<_B >void copyTo(final DeleteVaccinationResponseType.Builder<_B> _other) {
        _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other));
        _other.vaccination = ((this.vaccination == null)?null:this.vaccination.newCopyBuilder(_other));
    }

    public<_B >DeleteVaccinationResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DeleteVaccinationResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public DeleteVaccinationResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DeleteVaccinationResponseType.Builder<Void> builder() {
        return new DeleteVaccinationResponseType.Builder<>(null, null, false);
    }

    public static<_B >DeleteVaccinationResponseType.Builder<_B> copyOf(final DeleteVaccinationResponseType _other) {
        final DeleteVaccinationResponseType.Builder<_B> _newBuilder = new DeleteVaccinationResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DeleteVaccinationResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree versionMismatchWarningIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("versionMismatchWarningIndicator"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionMismatchWarningIndicatorPropertyTree!= null):((versionMismatchWarningIndicatorPropertyTree == null)||(!versionMismatchWarningIndicatorPropertyTree.isLeaf())))) {
            _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other, versionMismatchWarningIndicatorPropertyTree, _propertyTreeUse));
        }
        final PropertyTree vaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPropertyTree!= null):((vaccinationPropertyTree == null)||(!vaccinationPropertyTree.isLeaf())))) {
            _other.vaccination = ((this.vaccination == null)?null:this.vaccination.newCopyBuilder(_other, vaccinationPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >DeleteVaccinationResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DeleteVaccinationResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DeleteVaccinationResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DeleteVaccinationResponseType.Builder<_B> copyOf(final DeleteVaccinationResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DeleteVaccinationResponseType.Builder<_B> _newBuilder = new DeleteVaccinationResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DeleteVaccinationResponseType.Builder<Void> copyExcept(final DeleteVaccinationResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DeleteVaccinationResponseType.Builder<Void> copyOnly(final DeleteVaccinationResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DeleteVaccinationResponseType _storedValue;
        private VersionMismatchWarningIndicatorType.Builder<DeleteVaccinationResponseType.Builder<_B>> versionMismatchWarningIndicator;
        private VaccinationType.Builder<DeleteVaccinationResponseType.Builder<_B>> vaccination;

        public Builder(final _B _parentBuilder, final DeleteVaccinationResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.versionMismatchWarningIndicator = ((_other.versionMismatchWarningIndicator == null)?null:_other.versionMismatchWarningIndicator.newCopyBuilder(this));
                    this.vaccination = ((_other.vaccination == null)?null:_other.vaccination.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DeleteVaccinationResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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

        protected<_P extends DeleteVaccinationResponseType >_P init(final _P _product) {
            _product.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.build());
            _product.vaccination = ((this.vaccination == null)?null:this.vaccination.build());
            return _product;
        }

        /**
         * Sets the new value of "versionMismatchWarningIndicator" (any previous value will
         * be replaced)
         * 
         * @param versionMismatchWarningIndicator
         *     New value of the "versionMismatchWarningIndicator" property.
         */
        public DeleteVaccinationResponseType.Builder<_B> withVersionMismatchWarningIndicator(final VersionMismatchWarningIndicatorType versionMismatchWarningIndicator) {
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
        public VersionMismatchWarningIndicatorType.Builder<? extends DeleteVaccinationResponseType.Builder<_B>> withVersionMismatchWarningIndicator() {
            if (this.versionMismatchWarningIndicator!= null) {
                return this.versionMismatchWarningIndicator;
            }
            return this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "vaccination" (any previous value will be replaced)
         * 
         * @param vaccination
         *     New value of the "vaccination" property.
         */
        public DeleteVaccinationResponseType.Builder<_B> withVaccination(final VaccinationType vaccination) {
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
        public VaccinationType.Builder<? extends DeleteVaccinationResponseType.Builder<_B>> withVaccination() {
            if (this.vaccination!= null) {
                return this.vaccination;
            }
            return this.vaccination = new VaccinationType.Builder<>(this, null, false);
        }

        @Override
        public DeleteVaccinationResponseType build() {
            if (_storedValue == null) {
                return this.init(new DeleteVaccinationResponseType());
            } else {
                return ((DeleteVaccinationResponseType) _storedValue);
            }
        }

        public DeleteVaccinationResponseType.Builder<_B> copyOf(final DeleteVaccinationResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public DeleteVaccinationResponseType.Builder<_B> copyOf(final DeleteVaccinationResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DeleteVaccinationResponseType.Selector<DeleteVaccinationResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DeleteVaccinationResponseType.Select _root() {
            return new DeleteVaccinationResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private VersionMismatchWarningIndicatorType.Selector<TRoot, DeleteVaccinationResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator = null;
        private VaccinationType.Selector<TRoot, DeleteVaccinationResponseType.Selector<TRoot, TParent>> vaccination = null;

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

        public VersionMismatchWarningIndicatorType.Selector<TRoot, DeleteVaccinationResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator() {
            return ((this.versionMismatchWarningIndicator == null)?this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Selector<>(this._root, this, "versionMismatchWarningIndicator"):this.versionMismatchWarningIndicator);
        }

        public VaccinationType.Selector<TRoot, DeleteVaccinationResponseType.Selector<TRoot, TParent>> vaccination() {
            return ((this.vaccination == null)?this.vaccination = new VaccinationType.Selector<>(this._root, this, "vaccination"):this.vaccination);
        }

    }

}
