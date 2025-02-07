
package dk.vaccinationsregister.schemas._2013._12._01.e1;

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
 * <p>Java class for CreateNotificationUnsubscriptionResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CreateNotificationUnsubscriptionResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="RecipientPersonIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateNotificationUnsubscriptionResponseType", propOrder = {
    "personCivilRegistrationIdentifier",
    "recipientPersonIdentifier"
})
public class CreateNotificationUnsubscriptionResponseType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "RecipientPersonIdentifier")
    protected String recipientPersonIdentifier;

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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final CreateNotificationUnsubscriptionResponseType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
    }

    public<_B >CreateNotificationUnsubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new CreateNotificationUnsubscriptionResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public CreateNotificationUnsubscriptionResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static CreateNotificationUnsubscriptionResponseType.Builder<Void> builder() {
        return new CreateNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
    }

    public static<_B >CreateNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionResponseType _other) {
        final CreateNotificationUnsubscriptionResponseType.Builder<_B> _newBuilder = new CreateNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final CreateNotificationUnsubscriptionResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree recipientPersonIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientPersonIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientPersonIdentifierPropertyTree!= null):((recipientPersonIdentifierPropertyTree == null)||(!recipientPersonIdentifierPropertyTree.isLeaf())))) {
            _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
        }
    }

    public<_B >CreateNotificationUnsubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new CreateNotificationUnsubscriptionResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public CreateNotificationUnsubscriptionResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >CreateNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final CreateNotificationUnsubscriptionResponseType.Builder<_B> _newBuilder = new CreateNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static CreateNotificationUnsubscriptionResponseType.Builder<Void> copyExcept(final CreateNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static CreateNotificationUnsubscriptionResponseType.Builder<Void> copyOnly(final CreateNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final CreateNotificationUnsubscriptionResponseType _storedValue;
        private String personCivilRegistrationIdentifier;
        private String recipientPersonIdentifier;

        public Builder(final _B _parentBuilder, final CreateNotificationUnsubscriptionResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.recipientPersonIdentifier = _other.recipientPersonIdentifier;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final CreateNotificationUnsubscriptionResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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

        protected<_P extends CreateNotificationUnsubscriptionResponseType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.recipientPersonIdentifier = this.recipientPersonIdentifier;
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public CreateNotificationUnsubscriptionResponseType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
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
        public CreateNotificationUnsubscriptionResponseType.Builder<_B> withRecipientPersonIdentifier(final String recipientPersonIdentifier) {
            this.recipientPersonIdentifier = recipientPersonIdentifier;
            return this;
        }

        @Override
        public CreateNotificationUnsubscriptionResponseType build() {
            if (_storedValue == null) {
                return this.init(new CreateNotificationUnsubscriptionResponseType());
            } else {
                return ((CreateNotificationUnsubscriptionResponseType) _storedValue);
            }
        }

        public CreateNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public CreateNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final CreateNotificationUnsubscriptionResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends CreateNotificationUnsubscriptionResponseType.Selector<CreateNotificationUnsubscriptionResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static CreateNotificationUnsubscriptionResponseType.Select _root() {
            return new CreateNotificationUnsubscriptionResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> recipientPersonIdentifier = null;

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
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, CreateNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> recipientPersonIdentifier() {
            return ((this.recipientPersonIdentifier == null)?this.recipientPersonIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "recipientPersonIdentifier"):this.recipientPersonIdentifier);
        }

    }

}
