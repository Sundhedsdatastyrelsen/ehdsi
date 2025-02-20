
package dk.vaccinationsregister.schemas._2013._12._01.e1;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.vaccinationsregister.schemas._2013._12._01.PersonVaccinationPlanType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateUnsubscriptionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CreateUnsubscriptionType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonVaccinationPlan" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonVaccinationPlanType"/>
 *         <element name="Status" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}PlannedVaccinationStatusType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateUnsubscriptionType", propOrder = {
    "personVaccinationPlan",
    "status"
})
public class CreateUnsubscriptionType {

    @XmlElement(name = "PersonVaccinationPlan", required = true)
    protected PersonVaccinationPlanType personVaccinationPlan;
    @XmlElement(name = "Status", required = true)
    protected String status;

    /**
     * Gets the value of the personVaccinationPlan property.
     * 
     * @return
     *     possible object is
     *     {@link PersonVaccinationPlanType }
     *     
     */
    public PersonVaccinationPlanType getPersonVaccinationPlan() {
        return personVaccinationPlan;
    }

    /**
     * Sets the value of the personVaccinationPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonVaccinationPlanType }
     *     
     */
    public void setPersonVaccinationPlan(PersonVaccinationPlanType value) {
        this.personVaccinationPlan = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final CreateUnsubscriptionType.Builder<_B> _other) {
        _other.personVaccinationPlan = ((this.personVaccinationPlan == null)?null:this.personVaccinationPlan.newCopyBuilder(_other));
        _other.status = this.status;
    }

    public<_B >CreateUnsubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new CreateUnsubscriptionType.Builder<_B>(_parentBuilder, this, true);
    }

    public CreateUnsubscriptionType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static CreateUnsubscriptionType.Builder<Void> builder() {
        return new CreateUnsubscriptionType.Builder<>(null, null, false);
    }

    public static<_B >CreateUnsubscriptionType.Builder<_B> copyOf(final CreateUnsubscriptionType _other) {
        final CreateUnsubscriptionType.Builder<_B> _newBuilder = new CreateUnsubscriptionType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final CreateUnsubscriptionType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personVaccinationPlanPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personVaccinationPlan"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personVaccinationPlanPropertyTree!= null):((personVaccinationPlanPropertyTree == null)||(!personVaccinationPlanPropertyTree.isLeaf())))) {
            _other.personVaccinationPlan = ((this.personVaccinationPlan == null)?null:this.personVaccinationPlan.newCopyBuilder(_other, personVaccinationPlanPropertyTree, _propertyTreeUse));
        }
        final PropertyTree statusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("status"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(statusPropertyTree!= null):((statusPropertyTree == null)||(!statusPropertyTree.isLeaf())))) {
            _other.status = this.status;
        }
    }

    public<_B >CreateUnsubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new CreateUnsubscriptionType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public CreateUnsubscriptionType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >CreateUnsubscriptionType.Builder<_B> copyOf(final CreateUnsubscriptionType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final CreateUnsubscriptionType.Builder<_B> _newBuilder = new CreateUnsubscriptionType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static CreateUnsubscriptionType.Builder<Void> copyExcept(final CreateUnsubscriptionType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static CreateUnsubscriptionType.Builder<Void> copyOnly(final CreateUnsubscriptionType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final CreateUnsubscriptionType _storedValue;
        private PersonVaccinationPlanType.Builder<CreateUnsubscriptionType.Builder<_B>> personVaccinationPlan;
        private String status;

        public Builder(final _B _parentBuilder, final CreateUnsubscriptionType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personVaccinationPlan = ((_other.personVaccinationPlan == null)?null:_other.personVaccinationPlan.newCopyBuilder(this));
                    this.status = _other.status;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final CreateUnsubscriptionType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personVaccinationPlanPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personVaccinationPlan"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personVaccinationPlanPropertyTree!= null):((personVaccinationPlanPropertyTree == null)||(!personVaccinationPlanPropertyTree.isLeaf())))) {
                        this.personVaccinationPlan = ((_other.personVaccinationPlan == null)?null:_other.personVaccinationPlan.newCopyBuilder(this, personVaccinationPlanPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree statusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("status"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(statusPropertyTree!= null):((statusPropertyTree == null)||(!statusPropertyTree.isLeaf())))) {
                        this.status = _other.status;
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

        protected<_P extends CreateUnsubscriptionType >_P init(final _P _product) {
            _product.personVaccinationPlan = ((this.personVaccinationPlan == null)?null:this.personVaccinationPlan.build());
            _product.status = this.status;
            return _product;
        }

        /**
         * Sets the new value of "personVaccinationPlan" (any previous value will be
         * replaced)
         * 
         * @param personVaccinationPlan
         *     New value of the "personVaccinationPlan" property.
         */
        public CreateUnsubscriptionType.Builder<_B> withPersonVaccinationPlan(final PersonVaccinationPlanType personVaccinationPlan) {
            this.personVaccinationPlan = ((personVaccinationPlan == null)?null:new PersonVaccinationPlanType.Builder<>(this, personVaccinationPlan, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "personVaccinationPlan" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.PersonVaccinationPlanType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "personVaccinationPlan" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.PersonVaccinationPlanType.Builder#end()}
         *     to return to the current builder.
         */
        public PersonVaccinationPlanType.Builder<? extends CreateUnsubscriptionType.Builder<_B>> withPersonVaccinationPlan() {
            if (this.personVaccinationPlan!= null) {
                return this.personVaccinationPlan;
            }
            return this.personVaccinationPlan = new PersonVaccinationPlanType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "status" (any previous value will be replaced)
         * 
         * @param status
         *     New value of the "status" property.
         */
        public CreateUnsubscriptionType.Builder<_B> withStatus(final String status) {
            this.status = status;
            return this;
        }

        @Override
        public CreateUnsubscriptionType build() {
            if (_storedValue == null) {
                return this.init(new CreateUnsubscriptionType());
            } else {
                return ((CreateUnsubscriptionType) _storedValue);
            }
        }

        public CreateUnsubscriptionType.Builder<_B> copyOf(final CreateUnsubscriptionType _other) {
            _other.copyTo(this);
            return this;
        }

        public CreateUnsubscriptionType.Builder<_B> copyOf(final CreateUnsubscriptionType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends CreateUnsubscriptionType.Selector<CreateUnsubscriptionType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static CreateUnsubscriptionType.Select _root() {
            return new CreateUnsubscriptionType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private PersonVaccinationPlanType.Selector<TRoot, CreateUnsubscriptionType.Selector<TRoot, TParent>> personVaccinationPlan = null;
        private com.kscs.util.jaxb.Selector<TRoot, CreateUnsubscriptionType.Selector<TRoot, TParent>> status = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.personVaccinationPlan!= null) {
                products.put("personVaccinationPlan", this.personVaccinationPlan.init());
            }
            if (this.status!= null) {
                products.put("status", this.status.init());
            }
            return products;
        }

        public PersonVaccinationPlanType.Selector<TRoot, CreateUnsubscriptionType.Selector<TRoot, TParent>> personVaccinationPlan() {
            return ((this.personVaccinationPlan == null)?this.personVaccinationPlan = new PersonVaccinationPlanType.Selector<>(this._root, this, "personVaccinationPlan"):this.personVaccinationPlan);
        }

        public com.kscs.util.jaxb.Selector<TRoot, CreateUnsubscriptionType.Selector<TRoot, TParent>> status() {
            return ((this.status == null)?this.status = new com.kscs.util.jaxb.Selector<>(this._root, this, "status"):this.status);
        }

    }

}
