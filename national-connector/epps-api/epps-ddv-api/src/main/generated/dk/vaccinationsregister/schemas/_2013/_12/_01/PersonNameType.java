
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
 * <p>Java class for PersonNameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PersonNameType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="GivenName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonGivenNameType"/>
 *         <element name="MiddleName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonMiddleNameType" minOccurs="0"/>
 *         <element name="Surname" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonSurnameType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonNameType", propOrder = {
    "givenName",
    "middleName",
    "surname"
})
public class PersonNameType {

    @XmlElement(name = "GivenName", required = true)
    protected String givenName;
    @XmlElement(name = "MiddleName")
    protected String middleName;
    @XmlElement(name = "Surname", required = true)
    protected String surname;

    /**
     * Gets the value of the givenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final PersonNameType.Builder<_B> _other) {
        _other.givenName = this.givenName;
        _other.middleName = this.middleName;
        _other.surname = this.surname;
    }

    public<_B >PersonNameType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new PersonNameType.Builder<_B>(_parentBuilder, this, true);
    }

    public PersonNameType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static PersonNameType.Builder<Void> builder() {
        return new PersonNameType.Builder<>(null, null, false);
    }

    public static<_B >PersonNameType.Builder<_B> copyOf(final PersonNameType _other) {
        final PersonNameType.Builder<_B> _newBuilder = new PersonNameType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final PersonNameType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree givenNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("givenName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(givenNamePropertyTree!= null):((givenNamePropertyTree == null)||(!givenNamePropertyTree.isLeaf())))) {
            _other.givenName = this.givenName;
        }
        final PropertyTree middleNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("middleName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(middleNamePropertyTree!= null):((middleNamePropertyTree == null)||(!middleNamePropertyTree.isLeaf())))) {
            _other.middleName = this.middleName;
        }
        final PropertyTree surnamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("surname"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(surnamePropertyTree!= null):((surnamePropertyTree == null)||(!surnamePropertyTree.isLeaf())))) {
            _other.surname = this.surname;
        }
    }

    public<_B >PersonNameType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new PersonNameType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public PersonNameType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >PersonNameType.Builder<_B> copyOf(final PersonNameType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PersonNameType.Builder<_B> _newBuilder = new PersonNameType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static PersonNameType.Builder<Void> copyExcept(final PersonNameType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static PersonNameType.Builder<Void> copyOnly(final PersonNameType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final PersonNameType _storedValue;
        private String givenName;
        private String middleName;
        private String surname;

        public Builder(final _B _parentBuilder, final PersonNameType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.givenName = _other.givenName;
                    this.middleName = _other.middleName;
                    this.surname = _other.surname;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final PersonNameType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree givenNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("givenName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(givenNamePropertyTree!= null):((givenNamePropertyTree == null)||(!givenNamePropertyTree.isLeaf())))) {
                        this.givenName = _other.givenName;
                    }
                    final PropertyTree middleNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("middleName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(middleNamePropertyTree!= null):((middleNamePropertyTree == null)||(!middleNamePropertyTree.isLeaf())))) {
                        this.middleName = _other.middleName;
                    }
                    final PropertyTree surnamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("surname"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(surnamePropertyTree!= null):((surnamePropertyTree == null)||(!surnamePropertyTree.isLeaf())))) {
                        this.surname = _other.surname;
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

        protected<_P extends PersonNameType >_P init(final _P _product) {
            _product.givenName = this.givenName;
            _product.middleName = this.middleName;
            _product.surname = this.surname;
            return _product;
        }

        /**
         * Sets the new value of "givenName" (any previous value will be replaced)
         * 
         * @param givenName
         *     New value of the "givenName" property.
         */
        public PersonNameType.Builder<_B> withGivenName(final String givenName) {
            this.givenName = givenName;
            return this;
        }

        /**
         * Sets the new value of "middleName" (any previous value will be replaced)
         * 
         * @param middleName
         *     New value of the "middleName" property.
         */
        public PersonNameType.Builder<_B> withMiddleName(final String middleName) {
            this.middleName = middleName;
            return this;
        }

        /**
         * Sets the new value of "surname" (any previous value will be replaced)
         * 
         * @param surname
         *     New value of the "surname" property.
         */
        public PersonNameType.Builder<_B> withSurname(final String surname) {
            this.surname = surname;
            return this;
        }

        @Override
        public PersonNameType build() {
            if (_storedValue == null) {
                return this.init(new PersonNameType());
            } else {
                return ((PersonNameType) _storedValue);
            }
        }

        public PersonNameType.Builder<_B> copyOf(final PersonNameType _other) {
            _other.copyTo(this);
            return this;
        }

        public PersonNameType.Builder<_B> copyOf(final PersonNameType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends PersonNameType.Selector<PersonNameType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static PersonNameType.Select _root() {
            return new PersonNameType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, PersonNameType.Selector<TRoot, TParent>> givenName = null;
        private com.kscs.util.jaxb.Selector<TRoot, PersonNameType.Selector<TRoot, TParent>> middleName = null;
        private com.kscs.util.jaxb.Selector<TRoot, PersonNameType.Selector<TRoot, TParent>> surname = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.givenName!= null) {
                products.put("givenName", this.givenName.init());
            }
            if (this.middleName!= null) {
                products.put("middleName", this.middleName.init());
            }
            if (this.surname!= null) {
                products.put("surname", this.surname.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, PersonNameType.Selector<TRoot, TParent>> givenName() {
            return ((this.givenName == null)?this.givenName = new com.kscs.util.jaxb.Selector<>(this._root, this, "givenName"):this.givenName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PersonNameType.Selector<TRoot, TParent>> middleName() {
            return ((this.middleName == null)?this.middleName = new com.kscs.util.jaxb.Selector<>(this._root, this, "middleName"):this.middleName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, PersonNameType.Selector<TRoot, TParent>> surname() {
            return ((this.surname == null)?this.surname = new com.kscs.util.jaxb.Selector<>(this._root, this, "surname"):this.surname);
        }

    }

}
