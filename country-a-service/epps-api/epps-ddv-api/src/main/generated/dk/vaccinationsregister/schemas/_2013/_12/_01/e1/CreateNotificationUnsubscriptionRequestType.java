
package dk.vaccinationsregister.schemas._2013._12._01.e1;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.vaccinationsregister.schemas._2013._12._01.ModifiedType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateNotificationUnsubscriptionRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CreateNotificationUnsubscriptionRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="RecipientPersonIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType" minOccurs="0"/>
 *         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType"/>
 *         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateNotificationUnsubscriptionRequestType", propOrder = {
    "personCivilRegistrationIdentifier",
    "recipientPersonIdentifier",
    "created",
    "reported"
})
public class CreateNotificationUnsubscriptionRequestType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "RecipientPersonIdentifier")
    protected String recipientPersonIdentifier;
    @XmlElement(name = "Created", required = true)
    protected ModifiedType created;
    @XmlElement(name = "Reported")
    protected ModifiedType reported;

    /**
     * Gets the value of the personCivilRegistrationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonCivilRegistrationIdentifier() {
        return personCivilRegistrationIdentifier;
    }

    /**
     * Sets the value of the personCivilRegistrationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonCivilRegistrationIdentifier(String value) {
        this.personCivilRegistrationIdentifier = value;
    }

    /**
     * Gets the value of the recipientPersonIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientPersonIdentifier() {
        return recipientPersonIdentifier;
    }

    /**
     * Sets the value of the recipientPersonIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientPersonIdentifier(String value) {
        this.recipientPersonIdentifier = value;
    }

    /**
     * Gets the value of the created property.
     * 
     * @return
     *     possible object is
     *     {@link ModifiedType }
     *     
     */
    public ModifiedType getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifiedType }
     *     
     */
    public void setCreated(ModifiedType value) {
        this.created = value;
    }

    /**
     * Gets the value of the reported property.
     * 
     * @return
     *     possible object is
     *     {@link ModifiedType }
     *     
     */
    public ModifiedType getReported() {
        return reported;
    }

    /**
     * Sets the value of the reported property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifiedType }
     *     
     */
    public void setReported(ModifiedType value) {
        this.reported = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final CreateNotificationUnsubscriptionRequestType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
        _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
        _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
    }

    public<_B >CreateNotificationUnsubscriptionRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new CreateNotificationUnsubscriptionRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public CreateNotificationUnsubscriptionRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static CreateNotificationUnsubscriptionRequestType.Builder<Void> builder() {
        return new CreateNotificationUnsubscriptionRequestType.Builder<>(null, null, false);
    }

    public static<_B >CreateNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionRequestType _other) {
        final CreateNotificationUnsubscriptionRequestType.Builder<_B> _newBuilder = new CreateNotificationUnsubscriptionRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final CreateNotificationUnsubscriptionRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree recipientPersonIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientPersonIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientPersonIdentifierPropertyTree!= null):((recipientPersonIdentifierPropertyTree == null)||(!recipientPersonIdentifierPropertyTree.isLeaf())))) {
            _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
        }
        final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
            _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
        }
        final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
            _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other, reportedPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >CreateNotificationUnsubscriptionRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new CreateNotificationUnsubscriptionRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public CreateNotificationUnsubscriptionRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >CreateNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final CreateNotificationUnsubscriptionRequestType.Builder<_B> _newBuilder = new CreateNotificationUnsubscriptionRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static CreateNotificationUnsubscriptionRequestType.Builder<Void> copyExcept(final CreateNotificationUnsubscriptionRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static CreateNotificationUnsubscriptionRequestType.Builder<Void> copyOnly(final CreateNotificationUnsubscriptionRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final CreateNotificationUnsubscriptionRequestType _storedValue;
        private String personCivilRegistrationIdentifier;
        private String recipientPersonIdentifier;
        private ModifiedType.Builder<CreateNotificationUnsubscriptionRequestType.Builder<_B>> created;
        private ModifiedType.Builder<CreateNotificationUnsubscriptionRequestType.Builder<_B>> reported;

        public Builder(final _B _parentBuilder, final CreateNotificationUnsubscriptionRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.recipientPersonIdentifier = _other.recipientPersonIdentifier;
                    this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
                    this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final CreateNotificationUnsubscriptionRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
                        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    }
                    final PropertyTree recipientPersonIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientPersonIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientPersonIdentifierPropertyTree!= null):((recipientPersonIdentifierPropertyTree == null)||(!recipientPersonIdentifierPropertyTree.isLeaf())))) {
                        this.recipientPersonIdentifier = _other.recipientPersonIdentifier;
                    }
                    final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
                        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
                        this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this, reportedPropertyTree, _propertyTreeUse));
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

        protected<_P extends CreateNotificationUnsubscriptionRequestType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.recipientPersonIdentifier = this.recipientPersonIdentifier;
            _product.created = ((this.created == null)?null:this.created.build());
            _product.reported = ((this.reported == null)?null:this.reported.build());
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public CreateNotificationUnsubscriptionRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
            this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "recipientPersonIdentifier" (any previous value will be
         * replaced)
         * 
         * @param recipientPersonIdentifier
         *     New value of the "recipientPersonIdentifier" property.
         */
        public CreateNotificationUnsubscriptionRequestType.Builder<_B> withRecipientPersonIdentifier(final String recipientPersonIdentifier) {
            this.recipientPersonIdentifier = recipientPersonIdentifier;
            return this;
        }

        /**
         * Sets the new value of "created" (any previous value will be replaced)
         * 
         * @param created
         *     New value of the "created" property.
         */
        public CreateNotificationUnsubscriptionRequestType.Builder<_B> withCreated(final ModifiedType created) {
            this.created = ((created == null)?null:new ModifiedType.Builder<>(this, created, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "created" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "created" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
         *     return to the current builder.
         */
        public ModifiedType.Builder<? extends CreateNotificationUnsubscriptionRequestType.Builder<_B>> withCreated() {
            if (this.created!= null) {
                return this.created;
            }
            return this.created = new ModifiedType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "reported" (any previous value will be replaced)
         * 
         * @param reported
         *     New value of the "reported" property.
         */
        public CreateNotificationUnsubscriptionRequestType.Builder<_B> withReported(final ModifiedType reported) {
            this.reported = ((reported == null)?null:new ModifiedType.Builder<>(this, reported, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "reported" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "reported" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
         *     return to the current builder.
         */
        public ModifiedType.Builder<? extends CreateNotificationUnsubscriptionRequestType.Builder<_B>> withReported() {
            if (this.reported!= null) {
                return this.reported;
            }
            return this.reported = new ModifiedType.Builder<>(this, null, false);
        }

        @Override
        public CreateNotificationUnsubscriptionRequestType build() {
            if (_storedValue == null) {
                return this.init(new CreateNotificationUnsubscriptionRequestType());
            } else {
                return ((CreateNotificationUnsubscriptionRequestType) _storedValue);
            }
        }

        public CreateNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public CreateNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends CreateNotificationUnsubscriptionRequestType.Selector<CreateNotificationUnsubscriptionRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static CreateNotificationUnsubscriptionRequestType.Select _root() {
            return new CreateNotificationUnsubscriptionRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> recipientPersonIdentifier = null;
        private ModifiedType.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> created = null;
        private ModifiedType.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> reported = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.personCivilRegistrationIdentifier!= null) {
                products.put("personCivilRegistrationIdentifier", this.personCivilRegistrationIdentifier.init());
            }
            if (this.recipientPersonIdentifier!= null) {
                products.put("recipientPersonIdentifier", this.recipientPersonIdentifier.init());
            }
            if (this.created!= null) {
                products.put("created", this.created.init());
            }
            if (this.reported!= null) {
                products.put("reported", this.reported.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> recipientPersonIdentifier() {
            return ((this.recipientPersonIdentifier == null)?this.recipientPersonIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "recipientPersonIdentifier"):this.recipientPersonIdentifier);
        }

        public ModifiedType.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> created() {
            return ((this.created == null)?this.created = new ModifiedType.Selector<>(this._root, this, "created"):this.created);
        }

        public ModifiedType.Selector<TRoot, CreateNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> reported() {
            return ((this.reported == null)?this.reported = new ModifiedType.Selector<>(this._root, this, "reported"):this.reported);
        }

    }

}
