
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
 * <p>Java class for DeleteUnsubscriptionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DeleteUnsubscriptionType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonVaccinationPlan" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonVaccinationPlanType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteUnsubscriptionType", propOrder = {
    "personVaccinationPlan"
})
public class DeleteUnsubscriptionType {

    @XmlElement(name = "PersonVaccinationPlan", required = true)
    protected PersonVaccinationPlanType personVaccinationPlan;

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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DeleteUnsubscriptionType.Builder<_B> _other) {
        _other.personVaccinationPlan = ((this.personVaccinationPlan == null)?null:this.personVaccinationPlan.newCopyBuilder(_other));
    }

    public<_B >DeleteUnsubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DeleteUnsubscriptionType.Builder<_B>(_parentBuilder, this, true);
    }

    public DeleteUnsubscriptionType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DeleteUnsubscriptionType.Builder<Void> builder() {
        return new DeleteUnsubscriptionType.Builder<>(null, null, false);
    }

    public static<_B >DeleteUnsubscriptionType.Builder<_B> copyOf(final DeleteUnsubscriptionType _other) {
        final DeleteUnsubscriptionType.Builder<_B> _newBuilder = new DeleteUnsubscriptionType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DeleteUnsubscriptionType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personVaccinationPlanPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personVaccinationPlan"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personVaccinationPlanPropertyTree!= null):((personVaccinationPlanPropertyTree == null)||(!personVaccinationPlanPropertyTree.isLeaf())))) {
            _other.personVaccinationPlan = ((this.personVaccinationPlan == null)?null:this.personVaccinationPlan.newCopyBuilder(_other, personVaccinationPlanPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >DeleteUnsubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DeleteUnsubscriptionType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DeleteUnsubscriptionType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DeleteUnsubscriptionType.Builder<_B> copyOf(final DeleteUnsubscriptionType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DeleteUnsubscriptionType.Builder<_B> _newBuilder = new DeleteUnsubscriptionType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DeleteUnsubscriptionType.Builder<Void> copyExcept(final DeleteUnsubscriptionType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DeleteUnsubscriptionType.Builder<Void> copyOnly(final DeleteUnsubscriptionType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DeleteUnsubscriptionType _storedValue;
        private PersonVaccinationPlanType.Builder<DeleteUnsubscriptionType.Builder<_B>> personVaccinationPlan;

        public Builder(final _B _parentBuilder, final DeleteUnsubscriptionType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personVaccinationPlan = ((_other.personVaccinationPlan == null)?null:_other.personVaccinationPlan.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DeleteUnsubscriptionType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personVaccinationPlanPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personVaccinationPlan"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personVaccinationPlanPropertyTree!= null):((personVaccinationPlanPropertyTree == null)||(!personVaccinationPlanPropertyTree.isLeaf())))) {
                        this.personVaccinationPlan = ((_other.personVaccinationPlan == null)?null:_other.personVaccinationPlan.newCopyBuilder(this, personVaccinationPlanPropertyTree, _propertyTreeUse));
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

        protected<_P extends DeleteUnsubscriptionType >_P init(final _P _product) {
            _product.personVaccinationPlan = ((this.personVaccinationPlan == null)?null:this.personVaccinationPlan.build());
            return _product;
        }

        /**
         * Sets the new value of "personVaccinationPlan" (any previous value will be
         * replaced)
         * 
         * @param personVaccinationPlan
         *     New value of the "personVaccinationPlan" property.
         */
        public DeleteUnsubscriptionType.Builder<_B> withPersonVaccinationPlan(final PersonVaccinationPlanType personVaccinationPlan) {
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
        public PersonVaccinationPlanType.Builder<? extends DeleteUnsubscriptionType.Builder<_B>> withPersonVaccinationPlan() {
            if (this.personVaccinationPlan!= null) {
                return this.personVaccinationPlan;
            }
            return this.personVaccinationPlan = new PersonVaccinationPlanType.Builder<>(this, null, false);
        }

        @Override
        public DeleteUnsubscriptionType build() {
            if (_storedValue == null) {
                return this.init(new DeleteUnsubscriptionType());
            } else {
                return ((DeleteUnsubscriptionType) _storedValue);
            }
        }

        public DeleteUnsubscriptionType.Builder<_B> copyOf(final DeleteUnsubscriptionType _other) {
            _other.copyTo(this);
            return this;
        }

        public DeleteUnsubscriptionType.Builder<_B> copyOf(final DeleteUnsubscriptionType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DeleteUnsubscriptionType.Selector<DeleteUnsubscriptionType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DeleteUnsubscriptionType.Select _root() {
            return new DeleteUnsubscriptionType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private PersonVaccinationPlanType.Selector<TRoot, DeleteUnsubscriptionType.Selector<TRoot, TParent>> personVaccinationPlan = null;

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
            return products;
        }

        public PersonVaccinationPlanType.Selector<TRoot, DeleteUnsubscriptionType.Selector<TRoot, TParent>> personVaccinationPlan() {
            return ((this.personVaccinationPlan == null)?this.personVaccinationPlan = new PersonVaccinationPlanType.Selector<>(this._root, this, "personVaccinationPlan"):this.personVaccinationPlan);
        }

    }

}
