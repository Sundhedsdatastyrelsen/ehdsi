
package dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.oio.rep.itst_dk.xml.schemas._2006._01._17.PersonNameStructureType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SimpleCPRPersonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SimpleCPRPersonType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://rep.oio.dk/itst.dk/xml/schemas/2006/01/17/}PersonNameStructure"/>
 *         <element ref="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifier"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleCPRPersonType", propOrder = {
    "personNameStructure",
    "personCivilRegistrationIdentifier"
})
public class SimpleCPRPersonType {

    @XmlElement(name = "PersonNameStructure", namespace = "http://rep.oio.dk/itst.dk/xml/schemas/2006/01/17/", required = true)
    protected PersonNameStructureType personNameStructure;
    @XmlElement(name = "PersonCivilRegistrationIdentifier", namespace = "http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/", required = true)
    protected String personCivilRegistrationIdentifier;

    /**
     * Gets the value of the personNameStructure property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameStructureType }
     *     
     */
    public PersonNameStructureType getPersonNameStructure() {
        return personNameStructure;
    }

    /**
     * Sets the value of the personNameStructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameStructureType }
     *     
     */
    public void setPersonNameStructure(PersonNameStructureType value) {
        this.personNameStructure = value;
    }

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
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final SimpleCPRPersonType.Builder<_B> _other) {
        _other.personNameStructure = ((this.personNameStructure == null)?null:this.personNameStructure.newCopyBuilder(_other));
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
    }

    public<_B >SimpleCPRPersonType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new SimpleCPRPersonType.Builder<_B>(_parentBuilder, this, true);
    }

    public SimpleCPRPersonType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static SimpleCPRPersonType.Builder<Void> builder() {
        return new SimpleCPRPersonType.Builder<>(null, null, false);
    }

    public static<_B >SimpleCPRPersonType.Builder<_B> copyOf(final SimpleCPRPersonType _other) {
        final SimpleCPRPersonType.Builder<_B> _newBuilder = new SimpleCPRPersonType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final SimpleCPRPersonType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personNameStructurePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personNameStructure"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personNameStructurePropertyTree!= null):((personNameStructurePropertyTree == null)||(!personNameStructurePropertyTree.isLeaf())))) {
            _other.personNameStructure = ((this.personNameStructure == null)?null:this.personNameStructure.newCopyBuilder(_other, personNameStructurePropertyTree, _propertyTreeUse));
        }
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
    }

    public<_B >SimpleCPRPersonType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new SimpleCPRPersonType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public SimpleCPRPersonType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >SimpleCPRPersonType.Builder<_B> copyOf(final SimpleCPRPersonType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final SimpleCPRPersonType.Builder<_B> _newBuilder = new SimpleCPRPersonType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static SimpleCPRPersonType.Builder<Void> copyExcept(final SimpleCPRPersonType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static SimpleCPRPersonType.Builder<Void> copyOnly(final SimpleCPRPersonType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final SimpleCPRPersonType _storedValue;
        private PersonNameStructureType.Builder<SimpleCPRPersonType.Builder<_B>> personNameStructure;
        private String personCivilRegistrationIdentifier;

        public Builder(final _B _parentBuilder, final SimpleCPRPersonType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personNameStructure = ((_other.personNameStructure == null)?null:_other.personNameStructure.newCopyBuilder(this));
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final SimpleCPRPersonType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personNameStructurePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personNameStructure"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personNameStructurePropertyTree!= null):((personNameStructurePropertyTree == null)||(!personNameStructurePropertyTree.isLeaf())))) {
                        this.personNameStructure = ((_other.personNameStructure == null)?null:_other.personNameStructure.newCopyBuilder(this, personNameStructurePropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
                        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
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

        protected<_P extends SimpleCPRPersonType >_P init(final _P _product) {
            _product.personNameStructure = ((this.personNameStructure == null)?null:this.personNameStructure.build());
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            return _product;
        }

        /**
         * Sets the new value of "personNameStructure" (any previous value will be
         * replaced)
         * 
         * @param personNameStructure
         *     New value of the "personNameStructure" property.
         */
        public SimpleCPRPersonType.Builder<_B> withPersonNameStructure(final PersonNameStructureType personNameStructure) {
            this.personNameStructure = ((personNameStructure == null)?null:new PersonNameStructureType.Builder<>(this, personNameStructure, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "personNameStructure" property.
         * Use {@link
         * dk.oio.rep.itst_dk.xml.schemas._2006._01._17.PersonNameStructureType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "personNameStructure" property.
         *     Use {@link
         *     dk.oio.rep.itst_dk.xml.schemas._2006._01._17.PersonNameStructureType.Builder#end()}
         *     to return to the current builder.
         */
        public PersonNameStructureType.Builder<? extends SimpleCPRPersonType.Builder<_B>> withPersonNameStructure() {
            if (this.personNameStructure!= null) {
                return this.personNameStructure;
            }
            return this.personNameStructure = new PersonNameStructureType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public SimpleCPRPersonType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
            this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
            return this;
        }

        @Override
        public SimpleCPRPersonType build() {
            if (_storedValue == null) {
                return this.init(new SimpleCPRPersonType());
            } else {
                return ((SimpleCPRPersonType) _storedValue);
            }
        }

        public SimpleCPRPersonType.Builder<_B> copyOf(final SimpleCPRPersonType _other) {
            _other.copyTo(this);
            return this;
        }

        public SimpleCPRPersonType.Builder<_B> copyOf(final SimpleCPRPersonType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends SimpleCPRPersonType.Selector<SimpleCPRPersonType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static SimpleCPRPersonType.Select _root() {
            return new SimpleCPRPersonType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private PersonNameStructureType.Selector<TRoot, SimpleCPRPersonType.Selector<TRoot, TParent>> personNameStructure = null;
        private com.kscs.util.jaxb.Selector<TRoot, SimpleCPRPersonType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.personNameStructure!= null) {
                products.put("personNameStructure", this.personNameStructure.init());
            }
            if (this.personCivilRegistrationIdentifier!= null) {
                products.put("personCivilRegistrationIdentifier", this.personCivilRegistrationIdentifier.init());
            }
            return products;
        }

        public PersonNameStructureType.Selector<TRoot, SimpleCPRPersonType.Selector<TRoot, TParent>> personNameStructure() {
            return ((this.personNameStructure == null)?this.personNameStructure = new PersonNameStructureType.Selector<>(this._root, this, "personNameStructure"):this.personNameStructure);
        }

        public com.kscs.util.jaxb.Selector<TRoot, SimpleCPRPersonType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

    }

}
