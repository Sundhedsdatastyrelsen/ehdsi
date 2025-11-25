
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
 * <p>Java class for DisplayMinimumVaccinationPlanType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DisplayMinimumVaccinationPlanType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType" minOccurs="0"/>
 *         <element name="VaccinationPlanName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanNameType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DisplayMinimumVaccinationPlanType", propOrder = {
    "vaccinationPlanIdentifier",
    "vaccinationPlanName"
})
public class DisplayMinimumVaccinationPlanType {

    @XmlElement(name = "VaccinationPlanIdentifier")
    protected Long vaccinationPlanIdentifier;
    @XmlElement(name = "VaccinationPlanName")
    protected String vaccinationPlanName;

    /**
     * Gets the value of the vaccinationPlanIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVaccinationPlanIdentifier() {
        return vaccinationPlanIdentifier;
    }

    /**
     * Sets the value of the vaccinationPlanIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVaccinationPlanIdentifier(Long value) {
        this.vaccinationPlanIdentifier = value;
    }

    /**
     * Gets the value of the vaccinationPlanName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaccinationPlanName() {
        return vaccinationPlanName;
    }

    /**
     * Sets the value of the vaccinationPlanName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaccinationPlanName(String value) {
        this.vaccinationPlanName = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DisplayMinimumVaccinationPlanType.Builder<_B> _other) {
        _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
        _other.vaccinationPlanName = this.vaccinationPlanName;
    }

    public<_B >DisplayMinimumVaccinationPlanType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DisplayMinimumVaccinationPlanType.Builder<_B>(_parentBuilder, this, true);
    }

    public DisplayMinimumVaccinationPlanType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DisplayMinimumVaccinationPlanType.Builder<Void> builder() {
        return new DisplayMinimumVaccinationPlanType.Builder<>(null, null, false);
    }

    public static<_B >DisplayMinimumVaccinationPlanType.Builder<_B> copyOf(final DisplayMinimumVaccinationPlanType _other) {
        final DisplayMinimumVaccinationPlanType.Builder<_B> _newBuilder = new DisplayMinimumVaccinationPlanType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DisplayMinimumVaccinationPlanType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
            _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
        }
        final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
            _other.vaccinationPlanName = this.vaccinationPlanName;
        }
    }

    public<_B >DisplayMinimumVaccinationPlanType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DisplayMinimumVaccinationPlanType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DisplayMinimumVaccinationPlanType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DisplayMinimumVaccinationPlanType.Builder<_B> copyOf(final DisplayMinimumVaccinationPlanType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DisplayMinimumVaccinationPlanType.Builder<_B> _newBuilder = new DisplayMinimumVaccinationPlanType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DisplayMinimumVaccinationPlanType.Builder<Void> copyExcept(final DisplayMinimumVaccinationPlanType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DisplayMinimumVaccinationPlanType.Builder<Void> copyOnly(final DisplayMinimumVaccinationPlanType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DisplayMinimumVaccinationPlanType _storedValue;
        private Long vaccinationPlanIdentifier;
        private String vaccinationPlanName;

        public Builder(final _B _parentBuilder, final DisplayMinimumVaccinationPlanType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
                    this.vaccinationPlanName = _other.vaccinationPlanName;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DisplayMinimumVaccinationPlanType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
                        this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
                    }
                    final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
                        this.vaccinationPlanName = _other.vaccinationPlanName;
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

        protected<_P extends DisplayMinimumVaccinationPlanType >_P init(final _P _product) {
            _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
            _product.vaccinationPlanName = this.vaccinationPlanName;
            return _product;
        }

        /**
         * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
         * replaced)
         * 
         * @param vaccinationPlanIdentifier
         *     New value of the "vaccinationPlanIdentifier" property.
         */
        public DisplayMinimumVaccinationPlanType.Builder<_B> withVaccinationPlanIdentifier(final Long vaccinationPlanIdentifier) {
            this.vaccinationPlanIdentifier = vaccinationPlanIdentifier;
            return this;
        }

        /**
         * Sets the new value of "vaccinationPlanName" (any previous value will be
         * replaced)
         * 
         * @param vaccinationPlanName
         *     New value of the "vaccinationPlanName" property.
         */
        public DisplayMinimumVaccinationPlanType.Builder<_B> withVaccinationPlanName(final String vaccinationPlanName) {
            this.vaccinationPlanName = vaccinationPlanName;
            return this;
        }

        @Override
        public DisplayMinimumVaccinationPlanType build() {
            if (_storedValue == null) {
                return this.init(new DisplayMinimumVaccinationPlanType());
            } else {
                return ((DisplayMinimumVaccinationPlanType) _storedValue);
            }
        }

        public DisplayMinimumVaccinationPlanType.Builder<_B> copyOf(final DisplayMinimumVaccinationPlanType _other) {
            _other.copyTo(this);
            return this;
        }

        public DisplayMinimumVaccinationPlanType.Builder<_B> copyOf(final DisplayMinimumVaccinationPlanType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DisplayMinimumVaccinationPlanType.Selector<DisplayMinimumVaccinationPlanType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DisplayMinimumVaccinationPlanType.Select _root() {
            return new DisplayMinimumVaccinationPlanType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, DisplayMinimumVaccinationPlanType.Selector<TRoot, TParent>> vaccinationPlanIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, DisplayMinimumVaccinationPlanType.Selector<TRoot, TParent>> vaccinationPlanName = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.vaccinationPlanIdentifier!= null) {
                products.put("vaccinationPlanIdentifier", this.vaccinationPlanIdentifier.init());
            }
            if (this.vaccinationPlanName!= null) {
                products.put("vaccinationPlanName", this.vaccinationPlanName.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, DisplayMinimumVaccinationPlanType.Selector<TRoot, TParent>> vaccinationPlanIdentifier() {
            return ((this.vaccinationPlanIdentifier == null)?this.vaccinationPlanIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanIdentifier"):this.vaccinationPlanIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DisplayMinimumVaccinationPlanType.Selector<TRoot, TParent>> vaccinationPlanName() {
            return ((this.vaccinationPlanName == null)?this.vaccinationPlanName = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanName"):this.vaccinationPlanName);
        }

    }

}
