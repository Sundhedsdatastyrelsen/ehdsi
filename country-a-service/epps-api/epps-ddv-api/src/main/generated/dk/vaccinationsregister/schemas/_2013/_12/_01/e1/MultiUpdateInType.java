
package dk.vaccinationsregister.schemas._2013._12._01.e1;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.vaccinationsregister.schemas._2013._12._01.CreateSinglePlannedVaccinationType;
import dk.vaccinationsregister.schemas._2013._12._01.DeleteUnsubscriptionType;
import dk.vaccinationsregister.schemas._2013._12._01.DeleteVaccinationType;
import dk.vaccinationsregister.schemas._2013._12._01.EffectuatePlannedVaccinationType;
import dk.vaccinationsregister.schemas._2013._12._01.PreviousVaccinationCreateType;
import dk.vaccinationsregister.schemas._2013._12._01.SubscribeAndCreatePlannedVaccinationsType;
import dk.vaccinationsregister.schemas._2013._12._01.UpdatePlannedVaccinationType;
import dk.vaccinationsregister.schemas._2013._12._01.VaccinationCreateType;
import dk.vaccinationsregister.schemas._2013._12._01.VaccinationUpdateType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MultiUpdateInType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="MultiUpdateInType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="VaccinationCreate" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationCreateType"/>
 *         <element name="PreviousVaccinationCreate" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PreviousVaccinationCreateType"/>
 *         <element name="VaccinationUpdate" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationUpdateType"/>
 *         <element name="DeleteVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DeleteVaccinationType"/>
 *         <element name="CreateSinglePlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CreateSinglePlannedVaccinationType"/>
 *         <element name="EffectuatePlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatePlannedVaccinationType"/>
 *         <element name="UpdatePlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}UpdatePlannedVaccinationType"/>
 *         <element name="DeletePlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}DeletePlannedVaccinationType"/>
 *         <element name="SubscribeAndCreatePlannedVaccinations" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SubscribeAndCreatePlannedVaccinationsType"/>
 *         <element name="DeleteSubscription" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}DeleteSubscriptionType"/>
 *         <element name="CreateUnsubscription" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}CreateUnsubscriptionType"/>
 *         <element name="DeleteUnsubscription" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DeleteUnsubscriptionType"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiUpdateInType", propOrder = {
    "vaccinationCreate",
    "previousVaccinationCreate",
    "vaccinationUpdate",
    "deleteVaccination",
    "createSinglePlannedVaccination",
    "effectuatePlannedVaccination",
    "updatePlannedVaccination",
    "deletePlannedVaccination",
    "subscribeAndCreatePlannedVaccinations",
    "deleteSubscription",
    "createUnsubscription",
    "deleteUnsubscription"
})
public class MultiUpdateInType {

    @XmlElement(name = "VaccinationCreate")
    protected VaccinationCreateType vaccinationCreate;
    @XmlElement(name = "PreviousVaccinationCreate")
    protected PreviousVaccinationCreateType previousVaccinationCreate;
    @XmlElement(name = "VaccinationUpdate")
    protected VaccinationUpdateType vaccinationUpdate;
    @XmlElement(name = "DeleteVaccination")
    protected DeleteVaccinationType deleteVaccination;
    @XmlElement(name = "CreateSinglePlannedVaccination")
    protected CreateSinglePlannedVaccinationType createSinglePlannedVaccination;
    @XmlElement(name = "EffectuatePlannedVaccination")
    protected EffectuatePlannedVaccinationType effectuatePlannedVaccination;
    @XmlElement(name = "UpdatePlannedVaccination")
    protected UpdatePlannedVaccinationType updatePlannedVaccination;
    @XmlElement(name = "DeletePlannedVaccination")
    protected DeletePlannedVaccinationType deletePlannedVaccination;
    @XmlElement(name = "SubscribeAndCreatePlannedVaccinations")
    protected SubscribeAndCreatePlannedVaccinationsType subscribeAndCreatePlannedVaccinations;
    @XmlElement(name = "DeleteSubscription")
    protected DeleteSubscriptionType deleteSubscription;
    @XmlElement(name = "CreateUnsubscription")
    protected CreateUnsubscriptionType createUnsubscription;
    @XmlElement(name = "DeleteUnsubscription")
    protected DeleteUnsubscriptionType deleteUnsubscription;

    /**
     * Gets the value of the vaccinationCreate property.
     * 
     * @return
     *     possible object is
     *     {@link VaccinationCreateType }
     *     
     */
    public VaccinationCreateType getVaccinationCreate() {
        return vaccinationCreate;
    }

    /**
     * Sets the value of the vaccinationCreate property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccinationCreateType }
     *     
     */
    public void setVaccinationCreate(VaccinationCreateType value) {
        this.vaccinationCreate = value;
    }

    /**
     * Gets the value of the previousVaccinationCreate property.
     * 
     * @return
     *     possible object is
     *     {@link PreviousVaccinationCreateType }
     *     
     */
    public PreviousVaccinationCreateType getPreviousVaccinationCreate() {
        return previousVaccinationCreate;
    }

    /**
     * Sets the value of the previousVaccinationCreate property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreviousVaccinationCreateType }
     *     
     */
    public void setPreviousVaccinationCreate(PreviousVaccinationCreateType value) {
        this.previousVaccinationCreate = value;
    }

    /**
     * Gets the value of the vaccinationUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link VaccinationUpdateType }
     *     
     */
    public VaccinationUpdateType getVaccinationUpdate() {
        return vaccinationUpdate;
    }

    /**
     * Sets the value of the vaccinationUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccinationUpdateType }
     *     
     */
    public void setVaccinationUpdate(VaccinationUpdateType value) {
        this.vaccinationUpdate = value;
    }

    /**
     * Gets the value of the deleteVaccination property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteVaccinationType }
     *     
     */
    public DeleteVaccinationType getDeleteVaccination() {
        return deleteVaccination;
    }

    /**
     * Sets the value of the deleteVaccination property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteVaccinationType }
     *     
     */
    public void setDeleteVaccination(DeleteVaccinationType value) {
        this.deleteVaccination = value;
    }

    /**
     * Gets the value of the createSinglePlannedVaccination property.
     * 
     * @return
     *     possible object is
     *     {@link CreateSinglePlannedVaccinationType }
     *     
     */
    public CreateSinglePlannedVaccinationType getCreateSinglePlannedVaccination() {
        return createSinglePlannedVaccination;
    }

    /**
     * Sets the value of the createSinglePlannedVaccination property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateSinglePlannedVaccinationType }
     *     
     */
    public void setCreateSinglePlannedVaccination(CreateSinglePlannedVaccinationType value) {
        this.createSinglePlannedVaccination = value;
    }

    /**
     * Gets the value of the effectuatePlannedVaccination property.
     * 
     * @return
     *     possible object is
     *     {@link EffectuatePlannedVaccinationType }
     *     
     */
    public EffectuatePlannedVaccinationType getEffectuatePlannedVaccination() {
        return effectuatePlannedVaccination;
    }

    /**
     * Sets the value of the effectuatePlannedVaccination property.
     * 
     * @param value
     *     allowed object is
     *     {@link EffectuatePlannedVaccinationType }
     *     
     */
    public void setEffectuatePlannedVaccination(EffectuatePlannedVaccinationType value) {
        this.effectuatePlannedVaccination = value;
    }

    /**
     * Gets the value of the updatePlannedVaccination property.
     * 
     * @return
     *     possible object is
     *     {@link UpdatePlannedVaccinationType }
     *     
     */
    public UpdatePlannedVaccinationType getUpdatePlannedVaccination() {
        return updatePlannedVaccination;
    }

    /**
     * Sets the value of the updatePlannedVaccination property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdatePlannedVaccinationType }
     *     
     */
    public void setUpdatePlannedVaccination(UpdatePlannedVaccinationType value) {
        this.updatePlannedVaccination = value;
    }

    /**
     * Gets the value of the deletePlannedVaccination property.
     * 
     * @return
     *     possible object is
     *     {@link DeletePlannedVaccinationType }
     *     
     */
    public DeletePlannedVaccinationType getDeletePlannedVaccination() {
        return deletePlannedVaccination;
    }

    /**
     * Sets the value of the deletePlannedVaccination property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeletePlannedVaccinationType }
     *     
     */
    public void setDeletePlannedVaccination(DeletePlannedVaccinationType value) {
        this.deletePlannedVaccination = value;
    }

    /**
     * Gets the value of the subscribeAndCreatePlannedVaccinations property.
     * 
     * @return
     *     possible object is
     *     {@link SubscribeAndCreatePlannedVaccinationsType }
     *     
     */
    public SubscribeAndCreatePlannedVaccinationsType getSubscribeAndCreatePlannedVaccinations() {
        return subscribeAndCreatePlannedVaccinations;
    }

    /**
     * Sets the value of the subscribeAndCreatePlannedVaccinations property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscribeAndCreatePlannedVaccinationsType }
     *     
     */
    public void setSubscribeAndCreatePlannedVaccinations(SubscribeAndCreatePlannedVaccinationsType value) {
        this.subscribeAndCreatePlannedVaccinations = value;
    }

    /**
     * Gets the value of the deleteSubscription property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteSubscriptionType }
     *     
     */
    public DeleteSubscriptionType getDeleteSubscription() {
        return deleteSubscription;
    }

    /**
     * Sets the value of the deleteSubscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteSubscriptionType }
     *     
     */
    public void setDeleteSubscription(DeleteSubscriptionType value) {
        this.deleteSubscription = value;
    }

    /**
     * Gets the value of the createUnsubscription property.
     * 
     * @return
     *     possible object is
     *     {@link CreateUnsubscriptionType }
     *     
     */
    public CreateUnsubscriptionType getCreateUnsubscription() {
        return createUnsubscription;
    }

    /**
     * Sets the value of the createUnsubscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateUnsubscriptionType }
     *     
     */
    public void setCreateUnsubscription(CreateUnsubscriptionType value) {
        this.createUnsubscription = value;
    }

    /**
     * Gets the value of the deleteUnsubscription property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteUnsubscriptionType }
     *     
     */
    public DeleteUnsubscriptionType getDeleteUnsubscription() {
        return deleteUnsubscription;
    }

    /**
     * Sets the value of the deleteUnsubscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteUnsubscriptionType }
     *     
     */
    public void setDeleteUnsubscription(DeleteUnsubscriptionType value) {
        this.deleteUnsubscription = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final MultiUpdateInType.Builder<_B> _other) {
        _other.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.newCopyBuilder(_other));
        _other.previousVaccinationCreate = ((this.previousVaccinationCreate == null)?null:this.previousVaccinationCreate.newCopyBuilder(_other));
        _other.vaccinationUpdate = ((this.vaccinationUpdate == null)?null:this.vaccinationUpdate.newCopyBuilder(_other));
        _other.deleteVaccination = ((this.deleteVaccination == null)?null:this.deleteVaccination.newCopyBuilder(_other));
        _other.createSinglePlannedVaccination = ((this.createSinglePlannedVaccination == null)?null:this.createSinglePlannedVaccination.newCopyBuilder(_other));
        _other.effectuatePlannedVaccination = ((this.effectuatePlannedVaccination == null)?null:this.effectuatePlannedVaccination.newCopyBuilder(_other));
        _other.updatePlannedVaccination = ((this.updatePlannedVaccination == null)?null:this.updatePlannedVaccination.newCopyBuilder(_other));
        _other.deletePlannedVaccination = ((this.deletePlannedVaccination == null)?null:this.deletePlannedVaccination.newCopyBuilder(_other));
        _other.subscribeAndCreatePlannedVaccinations = ((this.subscribeAndCreatePlannedVaccinations == null)?null:this.subscribeAndCreatePlannedVaccinations.newCopyBuilder(_other));
        _other.deleteSubscription = ((this.deleteSubscription == null)?null:this.deleteSubscription.newCopyBuilder(_other));
        _other.createUnsubscription = ((this.createUnsubscription == null)?null:this.createUnsubscription.newCopyBuilder(_other));
        _other.deleteUnsubscription = ((this.deleteUnsubscription == null)?null:this.deleteUnsubscription.newCopyBuilder(_other));
    }

    public<_B >MultiUpdateInType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new MultiUpdateInType.Builder<_B>(_parentBuilder, this, true);
    }

    public MultiUpdateInType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static MultiUpdateInType.Builder<Void> builder() {
        return new MultiUpdateInType.Builder<>(null, null, false);
    }

    public static<_B >MultiUpdateInType.Builder<_B> copyOf(final MultiUpdateInType _other) {
        final MultiUpdateInType.Builder<_B> _newBuilder = new MultiUpdateInType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final MultiUpdateInType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree vaccinationCreatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCreate"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCreatePropertyTree!= null):((vaccinationCreatePropertyTree == null)||(!vaccinationCreatePropertyTree.isLeaf())))) {
            _other.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.newCopyBuilder(_other, vaccinationCreatePropertyTree, _propertyTreeUse));
        }
        final PropertyTree previousVaccinationCreatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("previousVaccinationCreate"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(previousVaccinationCreatePropertyTree!= null):((previousVaccinationCreatePropertyTree == null)||(!previousVaccinationCreatePropertyTree.isLeaf())))) {
            _other.previousVaccinationCreate = ((this.previousVaccinationCreate == null)?null:this.previousVaccinationCreate.newCopyBuilder(_other, previousVaccinationCreatePropertyTree, _propertyTreeUse));
        }
        final PropertyTree vaccinationUpdatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationUpdate"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationUpdatePropertyTree!= null):((vaccinationUpdatePropertyTree == null)||(!vaccinationUpdatePropertyTree.isLeaf())))) {
            _other.vaccinationUpdate = ((this.vaccinationUpdate == null)?null:this.vaccinationUpdate.newCopyBuilder(_other, vaccinationUpdatePropertyTree, _propertyTreeUse));
        }
        final PropertyTree deleteVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteVaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteVaccinationPropertyTree!= null):((deleteVaccinationPropertyTree == null)||(!deleteVaccinationPropertyTree.isLeaf())))) {
            _other.deleteVaccination = ((this.deleteVaccination == null)?null:this.deleteVaccination.newCopyBuilder(_other, deleteVaccinationPropertyTree, _propertyTreeUse));
        }
        final PropertyTree createSinglePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createSinglePlannedVaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createSinglePlannedVaccinationPropertyTree!= null):((createSinglePlannedVaccinationPropertyTree == null)||(!createSinglePlannedVaccinationPropertyTree.isLeaf())))) {
            _other.createSinglePlannedVaccination = ((this.createSinglePlannedVaccination == null)?null:this.createSinglePlannedVaccination.newCopyBuilder(_other, createSinglePlannedVaccinationPropertyTree, _propertyTreeUse));
        }
        final PropertyTree effectuatePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatePlannedVaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatePlannedVaccinationPropertyTree!= null):((effectuatePlannedVaccinationPropertyTree == null)||(!effectuatePlannedVaccinationPropertyTree.isLeaf())))) {
            _other.effectuatePlannedVaccination = ((this.effectuatePlannedVaccination == null)?null:this.effectuatePlannedVaccination.newCopyBuilder(_other, effectuatePlannedVaccinationPropertyTree, _propertyTreeUse));
        }
        final PropertyTree updatePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("updatePlannedVaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(updatePlannedVaccinationPropertyTree!= null):((updatePlannedVaccinationPropertyTree == null)||(!updatePlannedVaccinationPropertyTree.isLeaf())))) {
            _other.updatePlannedVaccination = ((this.updatePlannedVaccination == null)?null:this.updatePlannedVaccination.newCopyBuilder(_other, updatePlannedVaccinationPropertyTree, _propertyTreeUse));
        }
        final PropertyTree deletePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deletePlannedVaccination"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deletePlannedVaccinationPropertyTree!= null):((deletePlannedVaccinationPropertyTree == null)||(!deletePlannedVaccinationPropertyTree.isLeaf())))) {
            _other.deletePlannedVaccination = ((this.deletePlannedVaccination == null)?null:this.deletePlannedVaccination.newCopyBuilder(_other, deletePlannedVaccinationPropertyTree, _propertyTreeUse));
        }
        final PropertyTree subscribeAndCreatePlannedVaccinationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subscribeAndCreatePlannedVaccinations"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subscribeAndCreatePlannedVaccinationsPropertyTree!= null):((subscribeAndCreatePlannedVaccinationsPropertyTree == null)||(!subscribeAndCreatePlannedVaccinationsPropertyTree.isLeaf())))) {
            _other.subscribeAndCreatePlannedVaccinations = ((this.subscribeAndCreatePlannedVaccinations == null)?null:this.subscribeAndCreatePlannedVaccinations.newCopyBuilder(_other, subscribeAndCreatePlannedVaccinationsPropertyTree, _propertyTreeUse));
        }
        final PropertyTree deleteSubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteSubscription"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteSubscriptionPropertyTree!= null):((deleteSubscriptionPropertyTree == null)||(!deleteSubscriptionPropertyTree.isLeaf())))) {
            _other.deleteSubscription = ((this.deleteSubscription == null)?null:this.deleteSubscription.newCopyBuilder(_other, deleteSubscriptionPropertyTree, _propertyTreeUse));
        }
        final PropertyTree createUnsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createUnsubscription"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createUnsubscriptionPropertyTree!= null):((createUnsubscriptionPropertyTree == null)||(!createUnsubscriptionPropertyTree.isLeaf())))) {
            _other.createUnsubscription = ((this.createUnsubscription == null)?null:this.createUnsubscription.newCopyBuilder(_other, createUnsubscriptionPropertyTree, _propertyTreeUse));
        }
        final PropertyTree deleteUnsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteUnsubscription"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteUnsubscriptionPropertyTree!= null):((deleteUnsubscriptionPropertyTree == null)||(!deleteUnsubscriptionPropertyTree.isLeaf())))) {
            _other.deleteUnsubscription = ((this.deleteUnsubscription == null)?null:this.deleteUnsubscription.newCopyBuilder(_other, deleteUnsubscriptionPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >MultiUpdateInType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new MultiUpdateInType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public MultiUpdateInType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >MultiUpdateInType.Builder<_B> copyOf(final MultiUpdateInType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final MultiUpdateInType.Builder<_B> _newBuilder = new MultiUpdateInType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static MultiUpdateInType.Builder<Void> copyExcept(final MultiUpdateInType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static MultiUpdateInType.Builder<Void> copyOnly(final MultiUpdateInType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final MultiUpdateInType _storedValue;
        private VaccinationCreateType.Builder<MultiUpdateInType.Builder<_B>> vaccinationCreate;
        private PreviousVaccinationCreateType.Builder<MultiUpdateInType.Builder<_B>> previousVaccinationCreate;
        private VaccinationUpdateType.Builder<MultiUpdateInType.Builder<_B>> vaccinationUpdate;
        private DeleteVaccinationType.Builder<MultiUpdateInType.Builder<_B>> deleteVaccination;
        private CreateSinglePlannedVaccinationType.Builder<MultiUpdateInType.Builder<_B>> createSinglePlannedVaccination;
        private EffectuatePlannedVaccinationType.Builder<MultiUpdateInType.Builder<_B>> effectuatePlannedVaccination;
        private UpdatePlannedVaccinationType.Builder<MultiUpdateInType.Builder<_B>> updatePlannedVaccination;
        private DeletePlannedVaccinationType.Builder<MultiUpdateInType.Builder<_B>> deletePlannedVaccination;
        private SubscribeAndCreatePlannedVaccinationsType.Builder<MultiUpdateInType.Builder<_B>> subscribeAndCreatePlannedVaccinations;
        private DeleteSubscriptionType.Builder<MultiUpdateInType.Builder<_B>> deleteSubscription;
        private CreateUnsubscriptionType.Builder<MultiUpdateInType.Builder<_B>> createUnsubscription;
        private DeleteUnsubscriptionType.Builder<MultiUpdateInType.Builder<_B>> deleteUnsubscription;

        public Builder(final _B _parentBuilder, final MultiUpdateInType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.vaccinationCreate = ((_other.vaccinationCreate == null)?null:_other.vaccinationCreate.newCopyBuilder(this));
                    this.previousVaccinationCreate = ((_other.previousVaccinationCreate == null)?null:_other.previousVaccinationCreate.newCopyBuilder(this));
                    this.vaccinationUpdate = ((_other.vaccinationUpdate == null)?null:_other.vaccinationUpdate.newCopyBuilder(this));
                    this.deleteVaccination = ((_other.deleteVaccination == null)?null:_other.deleteVaccination.newCopyBuilder(this));
                    this.createSinglePlannedVaccination = ((_other.createSinglePlannedVaccination == null)?null:_other.createSinglePlannedVaccination.newCopyBuilder(this));
                    this.effectuatePlannedVaccination = ((_other.effectuatePlannedVaccination == null)?null:_other.effectuatePlannedVaccination.newCopyBuilder(this));
                    this.updatePlannedVaccination = ((_other.updatePlannedVaccination == null)?null:_other.updatePlannedVaccination.newCopyBuilder(this));
                    this.deletePlannedVaccination = ((_other.deletePlannedVaccination == null)?null:_other.deletePlannedVaccination.newCopyBuilder(this));
                    this.subscribeAndCreatePlannedVaccinations = ((_other.subscribeAndCreatePlannedVaccinations == null)?null:_other.subscribeAndCreatePlannedVaccinations.newCopyBuilder(this));
                    this.deleteSubscription = ((_other.deleteSubscription == null)?null:_other.deleteSubscription.newCopyBuilder(this));
                    this.createUnsubscription = ((_other.createUnsubscription == null)?null:_other.createUnsubscription.newCopyBuilder(this));
                    this.deleteUnsubscription = ((_other.deleteUnsubscription == null)?null:_other.deleteUnsubscription.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final MultiUpdateInType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree vaccinationCreatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCreate"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCreatePropertyTree!= null):((vaccinationCreatePropertyTree == null)||(!vaccinationCreatePropertyTree.isLeaf())))) {
                        this.vaccinationCreate = ((_other.vaccinationCreate == null)?null:_other.vaccinationCreate.newCopyBuilder(this, vaccinationCreatePropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree previousVaccinationCreatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("previousVaccinationCreate"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(previousVaccinationCreatePropertyTree!= null):((previousVaccinationCreatePropertyTree == null)||(!previousVaccinationCreatePropertyTree.isLeaf())))) {
                        this.previousVaccinationCreate = ((_other.previousVaccinationCreate == null)?null:_other.previousVaccinationCreate.newCopyBuilder(this, previousVaccinationCreatePropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree vaccinationUpdatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationUpdate"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationUpdatePropertyTree!= null):((vaccinationUpdatePropertyTree == null)||(!vaccinationUpdatePropertyTree.isLeaf())))) {
                        this.vaccinationUpdate = ((_other.vaccinationUpdate == null)?null:_other.vaccinationUpdate.newCopyBuilder(this, vaccinationUpdatePropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree deleteVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteVaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteVaccinationPropertyTree!= null):((deleteVaccinationPropertyTree == null)||(!deleteVaccinationPropertyTree.isLeaf())))) {
                        this.deleteVaccination = ((_other.deleteVaccination == null)?null:_other.deleteVaccination.newCopyBuilder(this, deleteVaccinationPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree createSinglePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createSinglePlannedVaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createSinglePlannedVaccinationPropertyTree!= null):((createSinglePlannedVaccinationPropertyTree == null)||(!createSinglePlannedVaccinationPropertyTree.isLeaf())))) {
                        this.createSinglePlannedVaccination = ((_other.createSinglePlannedVaccination == null)?null:_other.createSinglePlannedVaccination.newCopyBuilder(this, createSinglePlannedVaccinationPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree effectuatePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatePlannedVaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatePlannedVaccinationPropertyTree!= null):((effectuatePlannedVaccinationPropertyTree == null)||(!effectuatePlannedVaccinationPropertyTree.isLeaf())))) {
                        this.effectuatePlannedVaccination = ((_other.effectuatePlannedVaccination == null)?null:_other.effectuatePlannedVaccination.newCopyBuilder(this, effectuatePlannedVaccinationPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree updatePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("updatePlannedVaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(updatePlannedVaccinationPropertyTree!= null):((updatePlannedVaccinationPropertyTree == null)||(!updatePlannedVaccinationPropertyTree.isLeaf())))) {
                        this.updatePlannedVaccination = ((_other.updatePlannedVaccination == null)?null:_other.updatePlannedVaccination.newCopyBuilder(this, updatePlannedVaccinationPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree deletePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deletePlannedVaccination"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deletePlannedVaccinationPropertyTree!= null):((deletePlannedVaccinationPropertyTree == null)||(!deletePlannedVaccinationPropertyTree.isLeaf())))) {
                        this.deletePlannedVaccination = ((_other.deletePlannedVaccination == null)?null:_other.deletePlannedVaccination.newCopyBuilder(this, deletePlannedVaccinationPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree subscribeAndCreatePlannedVaccinationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subscribeAndCreatePlannedVaccinations"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subscribeAndCreatePlannedVaccinationsPropertyTree!= null):((subscribeAndCreatePlannedVaccinationsPropertyTree == null)||(!subscribeAndCreatePlannedVaccinationsPropertyTree.isLeaf())))) {
                        this.subscribeAndCreatePlannedVaccinations = ((_other.subscribeAndCreatePlannedVaccinations == null)?null:_other.subscribeAndCreatePlannedVaccinations.newCopyBuilder(this, subscribeAndCreatePlannedVaccinationsPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree deleteSubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteSubscription"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteSubscriptionPropertyTree!= null):((deleteSubscriptionPropertyTree == null)||(!deleteSubscriptionPropertyTree.isLeaf())))) {
                        this.deleteSubscription = ((_other.deleteSubscription == null)?null:_other.deleteSubscription.newCopyBuilder(this, deleteSubscriptionPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree createUnsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createUnsubscription"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createUnsubscriptionPropertyTree!= null):((createUnsubscriptionPropertyTree == null)||(!createUnsubscriptionPropertyTree.isLeaf())))) {
                        this.createUnsubscription = ((_other.createUnsubscription == null)?null:_other.createUnsubscription.newCopyBuilder(this, createUnsubscriptionPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree deleteUnsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteUnsubscription"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteUnsubscriptionPropertyTree!= null):((deleteUnsubscriptionPropertyTree == null)||(!deleteUnsubscriptionPropertyTree.isLeaf())))) {
                        this.deleteUnsubscription = ((_other.deleteUnsubscription == null)?null:_other.deleteUnsubscription.newCopyBuilder(this, deleteUnsubscriptionPropertyTree, _propertyTreeUse));
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

        protected<_P extends MultiUpdateInType >_P init(final _P _product) {
            _product.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.build());
            _product.previousVaccinationCreate = ((this.previousVaccinationCreate == null)?null:this.previousVaccinationCreate.build());
            _product.vaccinationUpdate = ((this.vaccinationUpdate == null)?null:this.vaccinationUpdate.build());
            _product.deleteVaccination = ((this.deleteVaccination == null)?null:this.deleteVaccination.build());
            _product.createSinglePlannedVaccination = ((this.createSinglePlannedVaccination == null)?null:this.createSinglePlannedVaccination.build());
            _product.effectuatePlannedVaccination = ((this.effectuatePlannedVaccination == null)?null:this.effectuatePlannedVaccination.build());
            _product.updatePlannedVaccination = ((this.updatePlannedVaccination == null)?null:this.updatePlannedVaccination.build());
            _product.deletePlannedVaccination = ((this.deletePlannedVaccination == null)?null:this.deletePlannedVaccination.build());
            _product.subscribeAndCreatePlannedVaccinations = ((this.subscribeAndCreatePlannedVaccinations == null)?null:this.subscribeAndCreatePlannedVaccinations.build());
            _product.deleteSubscription = ((this.deleteSubscription == null)?null:this.deleteSubscription.build());
            _product.createUnsubscription = ((this.createUnsubscription == null)?null:this.createUnsubscription.build());
            _product.deleteUnsubscription = ((this.deleteUnsubscription == null)?null:this.deleteUnsubscription.build());
            return _product;
        }

        /**
         * Sets the new value of "vaccinationCreate" (any previous value will be replaced)
         * 
         * @param vaccinationCreate
         *     New value of the "vaccinationCreate" property.
         */
        public MultiUpdateInType.Builder<_B> withVaccinationCreate(final VaccinationCreateType vaccinationCreate) {
            this.vaccinationCreate = ((vaccinationCreate == null)?null:new VaccinationCreateType.Builder<>(this, vaccinationCreate, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "vaccinationCreate" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VaccinationCreateType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "vaccinationCreate" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VaccinationCreateType.Builder#end()}
         *     to return to the current builder.
         */
        public VaccinationCreateType.Builder<? extends MultiUpdateInType.Builder<_B>> withVaccinationCreate() {
            if (this.vaccinationCreate!= null) {
                return this.vaccinationCreate;
            }
            return this.vaccinationCreate = new VaccinationCreateType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "previousVaccinationCreate" (any previous value will be
         * replaced)
         * 
         * @param previousVaccinationCreate
         *     New value of the "previousVaccinationCreate" property.
         */
        public MultiUpdateInType.Builder<_B> withPreviousVaccinationCreate(final PreviousVaccinationCreateType previousVaccinationCreate) {
            this.previousVaccinationCreate = ((previousVaccinationCreate == null)?null:new PreviousVaccinationCreateType.Builder<>(this, previousVaccinationCreate, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "previousVaccinationCreate" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.PreviousVaccinationCreateType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "previousVaccinationCreate" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.PreviousVaccinationCreateType.Builder#end()}
         *     to return to the current builder.
         */
        public PreviousVaccinationCreateType.Builder<? extends MultiUpdateInType.Builder<_B>> withPreviousVaccinationCreate() {
            if (this.previousVaccinationCreate!= null) {
                return this.previousVaccinationCreate;
            }
            return this.previousVaccinationCreate = new PreviousVaccinationCreateType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "vaccinationUpdate" (any previous value will be replaced)
         * 
         * @param vaccinationUpdate
         *     New value of the "vaccinationUpdate" property.
         */
        public MultiUpdateInType.Builder<_B> withVaccinationUpdate(final VaccinationUpdateType vaccinationUpdate) {
            this.vaccinationUpdate = ((vaccinationUpdate == null)?null:new VaccinationUpdateType.Builder<>(this, vaccinationUpdate, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "vaccinationUpdate" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VaccinationUpdateType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "vaccinationUpdate" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VaccinationUpdateType.Builder#end()}
         *     to return to the current builder.
         */
        public VaccinationUpdateType.Builder<? extends MultiUpdateInType.Builder<_B>> withVaccinationUpdate() {
            if (this.vaccinationUpdate!= null) {
                return this.vaccinationUpdate;
            }
            return this.vaccinationUpdate = new VaccinationUpdateType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "deleteVaccination" (any previous value will be replaced)
         * 
         * @param deleteVaccination
         *     New value of the "deleteVaccination" property.
         */
        public MultiUpdateInType.Builder<_B> withDeleteVaccination(final DeleteVaccinationType deleteVaccination) {
            this.deleteVaccination = ((deleteVaccination == null)?null:new DeleteVaccinationType.Builder<>(this, deleteVaccination, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "deleteVaccination" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.DeleteVaccinationType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "deleteVaccination" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.DeleteVaccinationType.Builder#end()}
         *     to return to the current builder.
         */
        public DeleteVaccinationType.Builder<? extends MultiUpdateInType.Builder<_B>> withDeleteVaccination() {
            if (this.deleteVaccination!= null) {
                return this.deleteVaccination;
            }
            return this.deleteVaccination = new DeleteVaccinationType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "createSinglePlannedVaccination" (any previous value will
         * be replaced)
         * 
         * @param createSinglePlannedVaccination
         *     New value of the "createSinglePlannedVaccination" property.
         */
        public MultiUpdateInType.Builder<_B> withCreateSinglePlannedVaccination(final CreateSinglePlannedVaccinationType createSinglePlannedVaccination) {
            this.createSinglePlannedVaccination = ((createSinglePlannedVaccination == null)?null:new CreateSinglePlannedVaccinationType.Builder<>(this, createSinglePlannedVaccination, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "createSinglePlannedVaccination" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.CreateSinglePlannedVaccinationType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "createSinglePlannedVaccination"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.CreateSinglePlannedVaccinationType.Builder#end()}
         *     to return to the current builder.
         */
        public CreateSinglePlannedVaccinationType.Builder<? extends MultiUpdateInType.Builder<_B>> withCreateSinglePlannedVaccination() {
            if (this.createSinglePlannedVaccination!= null) {
                return this.createSinglePlannedVaccination;
            }
            return this.createSinglePlannedVaccination = new CreateSinglePlannedVaccinationType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "effectuatePlannedVaccination" (any previous value will be
         * replaced)
         * 
         * @param effectuatePlannedVaccination
         *     New value of the "effectuatePlannedVaccination" property.
         */
        public MultiUpdateInType.Builder<_B> withEffectuatePlannedVaccination(final EffectuatePlannedVaccinationType effectuatePlannedVaccination) {
            this.effectuatePlannedVaccination = ((effectuatePlannedVaccination == null)?null:new EffectuatePlannedVaccinationType.Builder<>(this, effectuatePlannedVaccination, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "effectuatePlannedVaccination" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.EffectuatePlannedVaccinationType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "effectuatePlannedVaccination" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.EffectuatePlannedVaccinationType.Builder#end()}
         *     to return to the current builder.
         */
        public EffectuatePlannedVaccinationType.Builder<? extends MultiUpdateInType.Builder<_B>> withEffectuatePlannedVaccination() {
            if (this.effectuatePlannedVaccination!= null) {
                return this.effectuatePlannedVaccination;
            }
            return this.effectuatePlannedVaccination = new EffectuatePlannedVaccinationType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "updatePlannedVaccination" (any previous value will be
         * replaced)
         * 
         * @param updatePlannedVaccination
         *     New value of the "updatePlannedVaccination" property.
         */
        public MultiUpdateInType.Builder<_B> withUpdatePlannedVaccination(final UpdatePlannedVaccinationType updatePlannedVaccination) {
            this.updatePlannedVaccination = ((updatePlannedVaccination == null)?null:new UpdatePlannedVaccinationType.Builder<>(this, updatePlannedVaccination, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "updatePlannedVaccination" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.UpdatePlannedVaccinationType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "updatePlannedVaccination" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.UpdatePlannedVaccinationType.Builder#end()}
         *     to return to the current builder.
         */
        public UpdatePlannedVaccinationType.Builder<? extends MultiUpdateInType.Builder<_B>> withUpdatePlannedVaccination() {
            if (this.updatePlannedVaccination!= null) {
                return this.updatePlannedVaccination;
            }
            return this.updatePlannedVaccination = new UpdatePlannedVaccinationType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "deletePlannedVaccination" (any previous value will be
         * replaced)
         * 
         * @param deletePlannedVaccination
         *     New value of the "deletePlannedVaccination" property.
         */
        public MultiUpdateInType.Builder<_B> withDeletePlannedVaccination(final DeletePlannedVaccinationType deletePlannedVaccination) {
            this.deletePlannedVaccination = ((deletePlannedVaccination == null)?null:new DeletePlannedVaccinationType.Builder<>(this, deletePlannedVaccination, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "deletePlannedVaccination" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.e1.DeletePlannedVaccinationType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "deletePlannedVaccination" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.e1.DeletePlannedVaccinationType.Builder#end()}
         *     to return to the current builder.
         */
        public DeletePlannedVaccinationType.Builder<? extends MultiUpdateInType.Builder<_B>> withDeletePlannedVaccination() {
            if (this.deletePlannedVaccination!= null) {
                return this.deletePlannedVaccination;
            }
            return this.deletePlannedVaccination = new DeletePlannedVaccinationType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "subscribeAndCreatePlannedVaccinations" (any previous
         * value will be replaced)
         * 
         * @param subscribeAndCreatePlannedVaccinations
         *     New value of the "subscribeAndCreatePlannedVaccinations" property.
         */
        public MultiUpdateInType.Builder<_B> withSubscribeAndCreatePlannedVaccinations(final SubscribeAndCreatePlannedVaccinationsType subscribeAndCreatePlannedVaccinations) {
            this.subscribeAndCreatePlannedVaccinations = ((subscribeAndCreatePlannedVaccinations == null)?null:new SubscribeAndCreatePlannedVaccinationsType.Builder<>(this, subscribeAndCreatePlannedVaccinations, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "subscribeAndCreatePlannedVaccinations" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.SubscribeAndCreatePlannedVaccinationsType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "subscribeAndCreatePlannedVaccinations"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.SubscribeAndCreatePlannedVaccinationsType.Builder#end()}
         *     to return to the current builder.
         */
        public SubscribeAndCreatePlannedVaccinationsType.Builder<? extends MultiUpdateInType.Builder<_B>> withSubscribeAndCreatePlannedVaccinations() {
            if (this.subscribeAndCreatePlannedVaccinations!= null) {
                return this.subscribeAndCreatePlannedVaccinations;
            }
            return this.subscribeAndCreatePlannedVaccinations = new SubscribeAndCreatePlannedVaccinationsType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "deleteSubscription" (any previous value will be replaced)
         * 
         * @param deleteSubscription
         *     New value of the "deleteSubscription" property.
         */
        public MultiUpdateInType.Builder<_B> withDeleteSubscription(final DeleteSubscriptionType deleteSubscription) {
            this.deleteSubscription = ((deleteSubscription == null)?null:new DeleteSubscriptionType.Builder<>(this, deleteSubscription, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "deleteSubscription" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.e1.DeleteSubscriptionType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "deleteSubscription" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.e1.DeleteSubscriptionType.Builder#end()}
         *     to return to the current builder.
         */
        public DeleteSubscriptionType.Builder<? extends MultiUpdateInType.Builder<_B>> withDeleteSubscription() {
            if (this.deleteSubscription!= null) {
                return this.deleteSubscription;
            }
            return this.deleteSubscription = new DeleteSubscriptionType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "createUnsubscription" (any previous value will be
         * replaced)
         * 
         * @param createUnsubscription
         *     New value of the "createUnsubscription" property.
         */
        public MultiUpdateInType.Builder<_B> withCreateUnsubscription(final CreateUnsubscriptionType createUnsubscription) {
            this.createUnsubscription = ((createUnsubscription == null)?null:new CreateUnsubscriptionType.Builder<>(this, createUnsubscription, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "createUnsubscription" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.e1.CreateUnsubscriptionType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "createUnsubscription" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.e1.CreateUnsubscriptionType.Builder#end()}
         *     to return to the current builder.
         */
        public CreateUnsubscriptionType.Builder<? extends MultiUpdateInType.Builder<_B>> withCreateUnsubscription() {
            if (this.createUnsubscription!= null) {
                return this.createUnsubscription;
            }
            return this.createUnsubscription = new CreateUnsubscriptionType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "deleteUnsubscription" (any previous value will be
         * replaced)
         * 
         * @param deleteUnsubscription
         *     New value of the "deleteUnsubscription" property.
         */
        public MultiUpdateInType.Builder<_B> withDeleteUnsubscription(final DeleteUnsubscriptionType deleteUnsubscription) {
            this.deleteUnsubscription = ((deleteUnsubscription == null)?null:new DeleteUnsubscriptionType.Builder<>(this, deleteUnsubscription, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "deleteUnsubscription" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.DeleteUnsubscriptionType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "deleteUnsubscription" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.DeleteUnsubscriptionType.Builder#end()}
         *     to return to the current builder.
         */
        public DeleteUnsubscriptionType.Builder<? extends MultiUpdateInType.Builder<_B>> withDeleteUnsubscription() {
            if (this.deleteUnsubscription!= null) {
                return this.deleteUnsubscription;
            }
            return this.deleteUnsubscription = new DeleteUnsubscriptionType.Builder<>(this, null, false);
        }

        @Override
        public MultiUpdateInType build() {
            if (_storedValue == null) {
                return this.init(new MultiUpdateInType());
            } else {
                return ((MultiUpdateInType) _storedValue);
            }
        }

        public MultiUpdateInType.Builder<_B> copyOf(final MultiUpdateInType _other) {
            _other.copyTo(this);
            return this;
        }

        public MultiUpdateInType.Builder<_B> copyOf(final MultiUpdateInType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends MultiUpdateInType.Selector<MultiUpdateInType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static MultiUpdateInType.Select _root() {
            return new MultiUpdateInType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private VaccinationCreateType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> vaccinationCreate = null;
        private PreviousVaccinationCreateType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> previousVaccinationCreate = null;
        private VaccinationUpdateType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> vaccinationUpdate = null;
        private DeleteVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deleteVaccination = null;
        private CreateSinglePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> createSinglePlannedVaccination = null;
        private EffectuatePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> effectuatePlannedVaccination = null;
        private UpdatePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> updatePlannedVaccination = null;
        private DeletePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deletePlannedVaccination = null;
        private SubscribeAndCreatePlannedVaccinationsType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> subscribeAndCreatePlannedVaccinations = null;
        private DeleteSubscriptionType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deleteSubscription = null;
        private CreateUnsubscriptionType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> createUnsubscription = null;
        private DeleteUnsubscriptionType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deleteUnsubscription = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.vaccinationCreate!= null) {
                products.put("vaccinationCreate", this.vaccinationCreate.init());
            }
            if (this.previousVaccinationCreate!= null) {
                products.put("previousVaccinationCreate", this.previousVaccinationCreate.init());
            }
            if (this.vaccinationUpdate!= null) {
                products.put("vaccinationUpdate", this.vaccinationUpdate.init());
            }
            if (this.deleteVaccination!= null) {
                products.put("deleteVaccination", this.deleteVaccination.init());
            }
            if (this.createSinglePlannedVaccination!= null) {
                products.put("createSinglePlannedVaccination", this.createSinglePlannedVaccination.init());
            }
            if (this.effectuatePlannedVaccination!= null) {
                products.put("effectuatePlannedVaccination", this.effectuatePlannedVaccination.init());
            }
            if (this.updatePlannedVaccination!= null) {
                products.put("updatePlannedVaccination", this.updatePlannedVaccination.init());
            }
            if (this.deletePlannedVaccination!= null) {
                products.put("deletePlannedVaccination", this.deletePlannedVaccination.init());
            }
            if (this.subscribeAndCreatePlannedVaccinations!= null) {
                products.put("subscribeAndCreatePlannedVaccinations", this.subscribeAndCreatePlannedVaccinations.init());
            }
            if (this.deleteSubscription!= null) {
                products.put("deleteSubscription", this.deleteSubscription.init());
            }
            if (this.createUnsubscription!= null) {
                products.put("createUnsubscription", this.createUnsubscription.init());
            }
            if (this.deleteUnsubscription!= null) {
                products.put("deleteUnsubscription", this.deleteUnsubscription.init());
            }
            return products;
        }

        public VaccinationCreateType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> vaccinationCreate() {
            return ((this.vaccinationCreate == null)?this.vaccinationCreate = new VaccinationCreateType.Selector<>(this._root, this, "vaccinationCreate"):this.vaccinationCreate);
        }

        public PreviousVaccinationCreateType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> previousVaccinationCreate() {
            return ((this.previousVaccinationCreate == null)?this.previousVaccinationCreate = new PreviousVaccinationCreateType.Selector<>(this._root, this, "previousVaccinationCreate"):this.previousVaccinationCreate);
        }

        public VaccinationUpdateType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> vaccinationUpdate() {
            return ((this.vaccinationUpdate == null)?this.vaccinationUpdate = new VaccinationUpdateType.Selector<>(this._root, this, "vaccinationUpdate"):this.vaccinationUpdate);
        }

        public DeleteVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deleteVaccination() {
            return ((this.deleteVaccination == null)?this.deleteVaccination = new DeleteVaccinationType.Selector<>(this._root, this, "deleteVaccination"):this.deleteVaccination);
        }

        public CreateSinglePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> createSinglePlannedVaccination() {
            return ((this.createSinglePlannedVaccination == null)?this.createSinglePlannedVaccination = new CreateSinglePlannedVaccinationType.Selector<>(this._root, this, "createSinglePlannedVaccination"):this.createSinglePlannedVaccination);
        }

        public EffectuatePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> effectuatePlannedVaccination() {
            return ((this.effectuatePlannedVaccination == null)?this.effectuatePlannedVaccination = new EffectuatePlannedVaccinationType.Selector<>(this._root, this, "effectuatePlannedVaccination"):this.effectuatePlannedVaccination);
        }

        public UpdatePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> updatePlannedVaccination() {
            return ((this.updatePlannedVaccination == null)?this.updatePlannedVaccination = new UpdatePlannedVaccinationType.Selector<>(this._root, this, "updatePlannedVaccination"):this.updatePlannedVaccination);
        }

        public DeletePlannedVaccinationType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deletePlannedVaccination() {
            return ((this.deletePlannedVaccination == null)?this.deletePlannedVaccination = new DeletePlannedVaccinationType.Selector<>(this._root, this, "deletePlannedVaccination"):this.deletePlannedVaccination);
        }

        public SubscribeAndCreatePlannedVaccinationsType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> subscribeAndCreatePlannedVaccinations() {
            return ((this.subscribeAndCreatePlannedVaccinations == null)?this.subscribeAndCreatePlannedVaccinations = new SubscribeAndCreatePlannedVaccinationsType.Selector<>(this._root, this, "subscribeAndCreatePlannedVaccinations"):this.subscribeAndCreatePlannedVaccinations);
        }

        public DeleteSubscriptionType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deleteSubscription() {
            return ((this.deleteSubscription == null)?this.deleteSubscription = new DeleteSubscriptionType.Selector<>(this._root, this, "deleteSubscription"):this.deleteSubscription);
        }

        public CreateUnsubscriptionType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> createUnsubscription() {
            return ((this.createUnsubscription == null)?this.createUnsubscription = new CreateUnsubscriptionType.Selector<>(this._root, this, "createUnsubscription"):this.createUnsubscription);
        }

        public DeleteUnsubscriptionType.Selector<TRoot, MultiUpdateInType.Selector<TRoot, TParent>> deleteUnsubscription() {
            return ((this.deleteUnsubscription == null)?this.deleteUnsubscription = new DeleteUnsubscriptionType.Selector<>(this._root, this, "deleteUnsubscription"):this.deleteUnsubscription);
        }

    }

}
