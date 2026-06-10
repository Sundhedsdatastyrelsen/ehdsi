
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
 * <p>Java class for AuthorisedModificatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="AuthorisedModificatorType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <sequence>
 *           <element name="AuthorisedHealthcareProfessional" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisedHealthcareProfessionalType" minOccurs="0"/>
 *           <element name="Other" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModificatorPersonType" minOccurs="0"/>
 *           <element name="Organisation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationType" minOccurs="0"/>
 *         </sequence>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorisedModificatorType", propOrder = {
    "authorisedHealthcareProfessional",
    "other",
    "organisation"
})
public class AuthorisedModificatorType {

    @XmlElement(name = "AuthorisedHealthcareProfessional")
    protected AuthorisedHealthcareProfessionalType authorisedHealthcareProfessional;
    @XmlElement(name = "Other")
    protected ModificatorPersonType other;
    @XmlElement(name = "Organisation")
    protected OrganisationType organisation;

    /**
     * Gets the value of the authorisedHealthcareProfessional property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorisedHealthcareProfessionalType }
     *     
     */
    public AuthorisedHealthcareProfessionalType getAuthorisedHealthcareProfessional() {
        return authorisedHealthcareProfessional;
    }

    /**
     * Sets the value of the authorisedHealthcareProfessional property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorisedHealthcareProfessionalType }
     *     
     */
    public void setAuthorisedHealthcareProfessional(AuthorisedHealthcareProfessionalType value) {
        this.authorisedHealthcareProfessional = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link ModificatorPersonType }
     *     
     */
    public ModificatorPersonType getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModificatorPersonType }
     *     
     */
    public void setOther(ModificatorPersonType value) {
        this.other = value;
    }

    /**
     * Gets the value of the organisation property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationType }
     *     
     */
    public OrganisationType getOrganisation() {
        return organisation;
    }

    /**
     * Sets the value of the organisation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationType }
     *     
     */
    public void setOrganisation(OrganisationType value) {
        this.organisation = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final AuthorisedModificatorType.Builder<_B> _other) {
        _other.authorisedHealthcareProfessional = ((this.authorisedHealthcareProfessional == null)?null:this.authorisedHealthcareProfessional.newCopyBuilder(_other));
        _other.other = ((this.other == null)?null:this.other.newCopyBuilder(_other));
        _other.organisation = ((this.organisation == null)?null:this.organisation.newCopyBuilder(_other));
    }

    public<_B >AuthorisedModificatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new AuthorisedModificatorType.Builder<_B>(_parentBuilder, this, true);
    }

    public AuthorisedModificatorType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static AuthorisedModificatorType.Builder<Void> builder() {
        return new AuthorisedModificatorType.Builder<>(null, null, false);
    }

    public static<_B >AuthorisedModificatorType.Builder<_B> copyOf(final AuthorisedModificatorType _other) {
        final AuthorisedModificatorType.Builder<_B> _newBuilder = new AuthorisedModificatorType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final AuthorisedModificatorType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree authorisedHealthcareProfessionalPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisedHealthcareProfessional"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisedHealthcareProfessionalPropertyTree!= null):((authorisedHealthcareProfessionalPropertyTree == null)||(!authorisedHealthcareProfessionalPropertyTree.isLeaf())))) {
            _other.authorisedHealthcareProfessional = ((this.authorisedHealthcareProfessional == null)?null:this.authorisedHealthcareProfessional.newCopyBuilder(_other, authorisedHealthcareProfessionalPropertyTree, _propertyTreeUse));
        }
        final PropertyTree otherPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("other"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(otherPropertyTree!= null):((otherPropertyTree == null)||(!otherPropertyTree.isLeaf())))) {
            _other.other = ((this.other == null)?null:this.other.newCopyBuilder(_other, otherPropertyTree, _propertyTreeUse));
        }
        final PropertyTree organisationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("organisation"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(organisationPropertyTree!= null):((organisationPropertyTree == null)||(!organisationPropertyTree.isLeaf())))) {
            _other.organisation = ((this.organisation == null)?null:this.organisation.newCopyBuilder(_other, organisationPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >AuthorisedModificatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new AuthorisedModificatorType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public AuthorisedModificatorType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >AuthorisedModificatorType.Builder<_B> copyOf(final AuthorisedModificatorType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final AuthorisedModificatorType.Builder<_B> _newBuilder = new AuthorisedModificatorType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static AuthorisedModificatorType.Builder<Void> copyExcept(final AuthorisedModificatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static AuthorisedModificatorType.Builder<Void> copyOnly(final AuthorisedModificatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final AuthorisedModificatorType _storedValue;
        private AuthorisedHealthcareProfessionalType.Builder<AuthorisedModificatorType.Builder<_B>> authorisedHealthcareProfessional;
        private ModificatorPersonType.Builder<AuthorisedModificatorType.Builder<_B>> other;
        private OrganisationType.Builder<AuthorisedModificatorType.Builder<_B>> organisation;

        public Builder(final _B _parentBuilder, final AuthorisedModificatorType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.authorisedHealthcareProfessional = ((_other.authorisedHealthcareProfessional == null)?null:_other.authorisedHealthcareProfessional.newCopyBuilder(this));
                    this.other = ((_other.other == null)?null:_other.other.newCopyBuilder(this));
                    this.organisation = ((_other.organisation == null)?null:_other.organisation.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final AuthorisedModificatorType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree authorisedHealthcareProfessionalPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisedHealthcareProfessional"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisedHealthcareProfessionalPropertyTree!= null):((authorisedHealthcareProfessionalPropertyTree == null)||(!authorisedHealthcareProfessionalPropertyTree.isLeaf())))) {
                        this.authorisedHealthcareProfessional = ((_other.authorisedHealthcareProfessional == null)?null:_other.authorisedHealthcareProfessional.newCopyBuilder(this, authorisedHealthcareProfessionalPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree otherPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("other"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(otherPropertyTree!= null):((otherPropertyTree == null)||(!otherPropertyTree.isLeaf())))) {
                        this.other = ((_other.other == null)?null:_other.other.newCopyBuilder(this, otherPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree organisationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("organisation"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(organisationPropertyTree!= null):((organisationPropertyTree == null)||(!organisationPropertyTree.isLeaf())))) {
                        this.organisation = ((_other.organisation == null)?null:_other.organisation.newCopyBuilder(this, organisationPropertyTree, _propertyTreeUse));
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

        protected<_P extends AuthorisedModificatorType >_P init(final _P _product) {
            _product.authorisedHealthcareProfessional = ((this.authorisedHealthcareProfessional == null)?null:this.authorisedHealthcareProfessional.build());
            _product.other = ((this.other == null)?null:this.other.build());
            _product.organisation = ((this.organisation == null)?null:this.organisation.build());
            return _product;
        }

        /**
         * Sets the new value of "authorisedHealthcareProfessional" (any previous value
         * will be replaced)
         * 
         * @param authorisedHealthcareProfessional
         *     New value of the "authorisedHealthcareProfessional" property.
         */
        public AuthorisedModificatorType.Builder<_B> withAuthorisedHealthcareProfessional(final AuthorisedHealthcareProfessionalType authorisedHealthcareProfessional) {
            this.authorisedHealthcareProfessional = ((authorisedHealthcareProfessional == null)?null:new AuthorisedHealthcareProfessionalType.Builder<>(this, authorisedHealthcareProfessional, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "authorisedHealthcareProfessional" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.AuthorisedHealthcareProfessionalType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "authorisedHealthcareProfessional"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.AuthorisedHealthcareProfessionalType.Builder#end()}
         *     to return to the current builder.
         */
        public AuthorisedHealthcareProfessionalType.Builder<? extends AuthorisedModificatorType.Builder<_B>> withAuthorisedHealthcareProfessional() {
            if (this.authorisedHealthcareProfessional!= null) {
                return this.authorisedHealthcareProfessional;
            }
            return this.authorisedHealthcareProfessional = new AuthorisedHealthcareProfessionalType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "other" (any previous value will be replaced)
         * 
         * @param other
         *     New value of the "other" property.
         */
        public AuthorisedModificatorType.Builder<_B> withOther(final ModificatorPersonType other) {
            this.other = ((other == null)?null:new ModificatorPersonType.Builder<>(this, other, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the "other"
         * property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.ModificatorPersonType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "other" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.ModificatorPersonType.Builder#end()}
         *     to return to the current builder.
         */
        public ModificatorPersonType.Builder<? extends AuthorisedModificatorType.Builder<_B>> withOther() {
            if (this.other!= null) {
                return this.other;
            }
            return this.other = new ModificatorPersonType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "organisation" (any previous value will be replaced)
         * 
         * @param organisation
         *     New value of the "organisation" property.
         */
        public AuthorisedModificatorType.Builder<_B> withOrganisation(final OrganisationType organisation) {
            this.organisation = ((organisation == null)?null:new OrganisationType.Builder<>(this, organisation, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "organisation" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.OrganisationType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "organisation" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.OrganisationType.Builder#end()} to
         *     return to the current builder.
         */
        public OrganisationType.Builder<? extends AuthorisedModificatorType.Builder<_B>> withOrganisation() {
            if (this.organisation!= null) {
                return this.organisation;
            }
            return this.organisation = new OrganisationType.Builder<>(this, null, false);
        }

        @Override
        public AuthorisedModificatorType build() {
            if (_storedValue == null) {
                return this.init(new AuthorisedModificatorType());
            } else {
                return ((AuthorisedModificatorType) _storedValue);
            }
        }

        public AuthorisedModificatorType.Builder<_B> copyOf(final AuthorisedModificatorType _other) {
            _other.copyTo(this);
            return this;
        }

        public AuthorisedModificatorType.Builder<_B> copyOf(final AuthorisedModificatorType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends AuthorisedModificatorType.Selector<AuthorisedModificatorType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static AuthorisedModificatorType.Select _root() {
            return new AuthorisedModificatorType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private AuthorisedHealthcareProfessionalType.Selector<TRoot, AuthorisedModificatorType.Selector<TRoot, TParent>> authorisedHealthcareProfessional = null;
        private ModificatorPersonType.Selector<TRoot, AuthorisedModificatorType.Selector<TRoot, TParent>> other = null;
        private OrganisationType.Selector<TRoot, AuthorisedModificatorType.Selector<TRoot, TParent>> organisation = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.authorisedHealthcareProfessional!= null) {
                products.put("authorisedHealthcareProfessional", this.authorisedHealthcareProfessional.init());
            }
            if (this.other!= null) {
                products.put("other", this.other.init());
            }
            if (this.organisation!= null) {
                products.put("organisation", this.organisation.init());
            }
            return products;
        }

        public AuthorisedHealthcareProfessionalType.Selector<TRoot, AuthorisedModificatorType.Selector<TRoot, TParent>> authorisedHealthcareProfessional() {
            return ((this.authorisedHealthcareProfessional == null)?this.authorisedHealthcareProfessional = new AuthorisedHealthcareProfessionalType.Selector<>(this._root, this, "authorisedHealthcareProfessional"):this.authorisedHealthcareProfessional);
        }

        public ModificatorPersonType.Selector<TRoot, AuthorisedModificatorType.Selector<TRoot, TParent>> other() {
            return ((this.other == null)?this.other = new ModificatorPersonType.Selector<>(this._root, this, "other"):this.other);
        }

        public OrganisationType.Selector<TRoot, AuthorisedModificatorType.Selector<TRoot, TParent>> organisation() {
            return ((this.organisation == null)?this.organisation = new OrganisationType.Selector<>(this._root, this, "organisation"):this.organisation);
        }

    }

}
