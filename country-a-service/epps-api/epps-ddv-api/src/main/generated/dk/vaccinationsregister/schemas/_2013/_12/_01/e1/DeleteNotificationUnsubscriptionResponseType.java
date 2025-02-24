
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
 * <p>Java class for DeleteNotificationUnsubscriptionResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DeleteNotificationUnsubscriptionResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="RecipientPersonIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType" minOccurs="0"/>
 *         <element name="UnsubscriptionDeleted" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}UnsubscriptionDeletedType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteNotificationUnsubscriptionResponseType", propOrder = {
    "personCivilRegistrationIdentifier",
    "recipientPersonIdentifier",
    "unsubscriptionDeleted"
})
public class DeleteNotificationUnsubscriptionResponseType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "RecipientPersonIdentifier")
    protected String recipientPersonIdentifier;
    @XmlElement(name = "UnsubscriptionDeleted")
    protected UnsubscriptionDeletedType unsubscriptionDeleted;

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
     * Gets the value of the unsubscriptionDeleted property.
     * 
     * @return
     *     possible object is
     *     {@link UnsubscriptionDeletedType }
     *     
     */
    public UnsubscriptionDeletedType getUnsubscriptionDeleted() {
        return unsubscriptionDeleted;
    }

    /**
     * Sets the value of the unsubscriptionDeleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnsubscriptionDeletedType }
     *     
     */
    public void setUnsubscriptionDeleted(UnsubscriptionDeletedType value) {
        this.unsubscriptionDeleted = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DeleteNotificationUnsubscriptionResponseType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
        _other.unsubscriptionDeleted = ((this.unsubscriptionDeleted == null)?null:this.unsubscriptionDeleted.newCopyBuilder(_other));
    }

    public<_B >DeleteNotificationUnsubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DeleteNotificationUnsubscriptionResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public DeleteNotificationUnsubscriptionResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DeleteNotificationUnsubscriptionResponseType.Builder<Void> builder() {
        return new DeleteNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
    }

    public static<_B >DeleteNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final DeleteNotificationUnsubscriptionResponseType _other) {
        final DeleteNotificationUnsubscriptionResponseType.Builder<_B> _newBuilder = new DeleteNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DeleteNotificationUnsubscriptionResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree recipientPersonIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientPersonIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientPersonIdentifierPropertyTree!= null):((recipientPersonIdentifierPropertyTree == null)||(!recipientPersonIdentifierPropertyTree.isLeaf())))) {
            _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
        }
        final PropertyTree unsubscriptionDeletedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("unsubscriptionDeleted"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(unsubscriptionDeletedPropertyTree!= null):((unsubscriptionDeletedPropertyTree == null)||(!unsubscriptionDeletedPropertyTree.isLeaf())))) {
            _other.unsubscriptionDeleted = ((this.unsubscriptionDeleted == null)?null:this.unsubscriptionDeleted.newCopyBuilder(_other, unsubscriptionDeletedPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >DeleteNotificationUnsubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DeleteNotificationUnsubscriptionResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DeleteNotificationUnsubscriptionResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DeleteNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final DeleteNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DeleteNotificationUnsubscriptionResponseType.Builder<_B> _newBuilder = new DeleteNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DeleteNotificationUnsubscriptionResponseType.Builder<Void> copyExcept(final DeleteNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DeleteNotificationUnsubscriptionResponseType.Builder<Void> copyOnly(final DeleteNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DeleteNotificationUnsubscriptionResponseType _storedValue;
        private String personCivilRegistrationIdentifier;
        private String recipientPersonIdentifier;
        private UnsubscriptionDeletedType.Builder<DeleteNotificationUnsubscriptionResponseType.Builder<_B>> unsubscriptionDeleted;

        public Builder(final _B _parentBuilder, final DeleteNotificationUnsubscriptionResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.recipientPersonIdentifier = _other.recipientPersonIdentifier;
                    this.unsubscriptionDeleted = ((_other.unsubscriptionDeleted == null)?null:_other.unsubscriptionDeleted.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DeleteNotificationUnsubscriptionResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
                    final PropertyTree unsubscriptionDeletedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("unsubscriptionDeleted"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(unsubscriptionDeletedPropertyTree!= null):((unsubscriptionDeletedPropertyTree == null)||(!unsubscriptionDeletedPropertyTree.isLeaf())))) {
                        this.unsubscriptionDeleted = ((_other.unsubscriptionDeleted == null)?null:_other.unsubscriptionDeleted.newCopyBuilder(this, unsubscriptionDeletedPropertyTree, _propertyTreeUse));
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

        protected<_P extends DeleteNotificationUnsubscriptionResponseType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.recipientPersonIdentifier = this.recipientPersonIdentifier;
            _product.unsubscriptionDeleted = ((this.unsubscriptionDeleted == null)?null:this.unsubscriptionDeleted.build());
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public DeleteNotificationUnsubscriptionResponseType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
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
        public DeleteNotificationUnsubscriptionResponseType.Builder<_B> withRecipientPersonIdentifier(final String recipientPersonIdentifier) {
            this.recipientPersonIdentifier = recipientPersonIdentifier;
            return this;
        }

        /**
         * Sets the new value of "unsubscriptionDeleted" (any previous value will be
         * replaced)
         * 
         * @param unsubscriptionDeleted
         *     New value of the "unsubscriptionDeleted" property.
         */
        public DeleteNotificationUnsubscriptionResponseType.Builder<_B> withUnsubscriptionDeleted(final UnsubscriptionDeletedType unsubscriptionDeleted) {
            this.unsubscriptionDeleted = ((unsubscriptionDeleted == null)?null:new UnsubscriptionDeletedType.Builder<>(this, unsubscriptionDeleted, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "unsubscriptionDeleted" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.e1.UnsubscriptionDeletedType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "unsubscriptionDeleted" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.e1.UnsubscriptionDeletedType.Builder#end()}
         *     to return to the current builder.
         */
        public UnsubscriptionDeletedType.Builder<? extends DeleteNotificationUnsubscriptionResponseType.Builder<_B>> withUnsubscriptionDeleted() {
            if (this.unsubscriptionDeleted!= null) {
                return this.unsubscriptionDeleted;
            }
            return this.unsubscriptionDeleted = new UnsubscriptionDeletedType.Builder<>(this, null, false);
        }

        @Override
        public DeleteNotificationUnsubscriptionResponseType build() {
            if (_storedValue == null) {
                return this.init(new DeleteNotificationUnsubscriptionResponseType());
            } else {
                return ((DeleteNotificationUnsubscriptionResponseType) _storedValue);
            }
        }

        public DeleteNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final DeleteNotificationUnsubscriptionResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public DeleteNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final DeleteNotificationUnsubscriptionResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DeleteNotificationUnsubscriptionResponseType.Selector<DeleteNotificationUnsubscriptionResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DeleteNotificationUnsubscriptionResponseType.Select _root() {
            return new DeleteNotificationUnsubscriptionResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, DeleteNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, DeleteNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> recipientPersonIdentifier = null;
        private UnsubscriptionDeletedType.Selector<TRoot, DeleteNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> unsubscriptionDeleted = null;

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
            if (this.unsubscriptionDeleted!= null) {
                products.put("unsubscriptionDeleted", this.unsubscriptionDeleted.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, DeleteNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DeleteNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> recipientPersonIdentifier() {
            return ((this.recipientPersonIdentifier == null)?this.recipientPersonIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "recipientPersonIdentifier"):this.recipientPersonIdentifier);
        }

        public UnsubscriptionDeletedType.Selector<TRoot, DeleteNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> unsubscriptionDeleted() {
            return ((this.unsubscriptionDeleted == null)?this.unsubscriptionDeleted = new UnsubscriptionDeletedType.Selector<>(this._root, this, "unsubscriptionDeleted"):this.unsubscriptionDeleted);
        }

    }

}
