
package dk.vaccinationsregister.schemas._2013._12._01.e1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
 * <p>Java class for MultiUpdateRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="MultiUpdateRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType"/>
 *         <element name="Modified" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType"/>
 *         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType" minOccurs="0"/>
 *         <element name="MultiUpdateIn" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}MultiUpdateInType" maxOccurs="20" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiUpdateRequestType", propOrder = {
    "personCivilRegistrationIdentifier",
    "created",
    "modified",
    "reported",
    "multiUpdateIn"
})
public class MultiUpdateRequestType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "Created", required = true)
    protected ModifiedType created;
    @XmlElement(name = "Modified", required = true)
    protected ModifiedType modified;
    @XmlElement(name = "Reported")
    protected ModifiedType reported;
    @XmlElement(name = "MultiUpdateIn")
    protected List<MultiUpdateInType> multiUpdateIn;

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
     * Gets the value of the modified property.
     * 
     * @return
     *     possible object is
     *     {@link ModifiedType }
     *     
     */
    public ModifiedType getModified() {
        return modified;
    }

    /**
     * Sets the value of the modified property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifiedType }
     *     
     */
    public void setModified(ModifiedType value) {
        this.modified = value;
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
     * Gets the value of the multiUpdateIn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the multiUpdateIn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiUpdateIn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultiUpdateInType }
     * 
     * 
     * @return
     *     The value of the multiUpdateIn property.
     */
    public List<MultiUpdateInType> getMultiUpdateIn() {
        if (multiUpdateIn == null) {
            multiUpdateIn = new ArrayList<>();
        }
        return this.multiUpdateIn;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final MultiUpdateRequestType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
        _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other));
        _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
        if (this.multiUpdateIn == null) {
            _other.multiUpdateIn = null;
        } else {
            _other.multiUpdateIn = new ArrayList<>();
            for (MultiUpdateInType _item: this.multiUpdateIn) {
                _other.multiUpdateIn.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >MultiUpdateRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new MultiUpdateRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public MultiUpdateRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static MultiUpdateRequestType.Builder<Void> builder() {
        return new MultiUpdateRequestType.Builder<>(null, null, false);
    }

    public static<_B >MultiUpdateRequestType.Builder<_B> copyOf(final MultiUpdateRequestType _other) {
        final MultiUpdateRequestType.Builder<_B> _newBuilder = new MultiUpdateRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final MultiUpdateRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
            _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
        }
        final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
            _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other, modifiedPropertyTree, _propertyTreeUse));
        }
        final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
            _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other, reportedPropertyTree, _propertyTreeUse));
        }
        final PropertyTree multiUpdateInPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("multiUpdateIn"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(multiUpdateInPropertyTree!= null):((multiUpdateInPropertyTree == null)||(!multiUpdateInPropertyTree.isLeaf())))) {
            if (this.multiUpdateIn == null) {
                _other.multiUpdateIn = null;
            } else {
                _other.multiUpdateIn = new ArrayList<>();
                for (MultiUpdateInType _item: this.multiUpdateIn) {
                    _other.multiUpdateIn.add(((_item == null)?null:_item.newCopyBuilder(_other, multiUpdateInPropertyTree, _propertyTreeUse)));
                }
            }
        }
    }

    public<_B >MultiUpdateRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new MultiUpdateRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public MultiUpdateRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >MultiUpdateRequestType.Builder<_B> copyOf(final MultiUpdateRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final MultiUpdateRequestType.Builder<_B> _newBuilder = new MultiUpdateRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static MultiUpdateRequestType.Builder<Void> copyExcept(final MultiUpdateRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static MultiUpdateRequestType.Builder<Void> copyOnly(final MultiUpdateRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final MultiUpdateRequestType _storedValue;
        private String personCivilRegistrationIdentifier;
        private ModifiedType.Builder<MultiUpdateRequestType.Builder<_B>> created;
        private ModifiedType.Builder<MultiUpdateRequestType.Builder<_B>> modified;
        private ModifiedType.Builder<MultiUpdateRequestType.Builder<_B>> reported;
        private List<MultiUpdateInType.Builder<MultiUpdateRequestType.Builder<_B>>> multiUpdateIn;

        public Builder(final _B _parentBuilder, final MultiUpdateRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
                    this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this));
                    this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
                    if (_other.multiUpdateIn == null) {
                        this.multiUpdateIn = null;
                    } else {
                        this.multiUpdateIn = new ArrayList<>();
                        for (MultiUpdateInType _item: _other.multiUpdateIn) {
                            this.multiUpdateIn.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final MultiUpdateRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
                        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    }
                    final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
                        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
                        this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this, modifiedPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
                        this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this, reportedPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree multiUpdateInPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("multiUpdateIn"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(multiUpdateInPropertyTree!= null):((multiUpdateInPropertyTree == null)||(!multiUpdateInPropertyTree.isLeaf())))) {
                        if (_other.multiUpdateIn == null) {
                            this.multiUpdateIn = null;
                        } else {
                            this.multiUpdateIn = new ArrayList<>();
                            for (MultiUpdateInType _item: _other.multiUpdateIn) {
                                this.multiUpdateIn.add(((_item == null)?null:_item.newCopyBuilder(this, multiUpdateInPropertyTree, _propertyTreeUse)));
                            }
                        }
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

        protected<_P extends MultiUpdateRequestType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.created = ((this.created == null)?null:this.created.build());
            _product.modified = ((this.modified == null)?null:this.modified.build());
            _product.reported = ((this.reported == null)?null:this.reported.build());
            if (this.multiUpdateIn!= null) {
                final List<MultiUpdateInType> multiUpdateIn = new ArrayList<>(this.multiUpdateIn.size());
                for (MultiUpdateInType.Builder<MultiUpdateRequestType.Builder<_B>> _item: this.multiUpdateIn) {
                    multiUpdateIn.add(_item.build());
                }
                _product.multiUpdateIn = multiUpdateIn;
            }
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public MultiUpdateRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
            this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "created" (any previous value will be replaced)
         * 
         * @param created
         *     New value of the "created" property.
         */
        public MultiUpdateRequestType.Builder<_B> withCreated(final ModifiedType created) {
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
        public ModifiedType.Builder<? extends MultiUpdateRequestType.Builder<_B>> withCreated() {
            if (this.created!= null) {
                return this.created;
            }
            return this.created = new ModifiedType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "modified" (any previous value will be replaced)
         * 
         * @param modified
         *     New value of the "modified" property.
         */
        public MultiUpdateRequestType.Builder<_B> withModified(final ModifiedType modified) {
            this.modified = ((modified == null)?null:new ModifiedType.Builder<>(this, modified, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "modified" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "modified" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
         *     return to the current builder.
         */
        public ModifiedType.Builder<? extends MultiUpdateRequestType.Builder<_B>> withModified() {
            if (this.modified!= null) {
                return this.modified;
            }
            return this.modified = new ModifiedType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "reported" (any previous value will be replaced)
         * 
         * @param reported
         *     New value of the "reported" property.
         */
        public MultiUpdateRequestType.Builder<_B> withReported(final ModifiedType reported) {
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
        public ModifiedType.Builder<? extends MultiUpdateRequestType.Builder<_B>> withReported() {
            if (this.reported!= null) {
                return this.reported;
            }
            return this.reported = new ModifiedType.Builder<>(this, null, false);
        }

        /**
         * Adds the given items to the value of "multiUpdateIn"
         * 
         * @param multiUpdateIn
         *     Items to add to the value of the "multiUpdateIn" property
         */
        public MultiUpdateRequestType.Builder<_B> addMultiUpdateIn(final Iterable<? extends MultiUpdateInType> multiUpdateIn) {
            if (multiUpdateIn!= null) {
                if (this.multiUpdateIn == null) {
                    this.multiUpdateIn = new ArrayList<>();
                }
                for (MultiUpdateInType _item: multiUpdateIn) {
                    this.multiUpdateIn.add(new MultiUpdateInType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "multiUpdateIn" (any previous value will be replaced)
         * 
         * @param multiUpdateIn
         *     New value of the "multiUpdateIn" property.
         */
        public MultiUpdateRequestType.Builder<_B> withMultiUpdateIn(final Iterable<? extends MultiUpdateInType> multiUpdateIn) {
            if (this.multiUpdateIn!= null) {
                this.multiUpdateIn.clear();
            }
            return addMultiUpdateIn(multiUpdateIn);
        }

        /**
         * Adds the given items to the value of "multiUpdateIn"
         * 
         * @param multiUpdateIn
         *     Items to add to the value of the "multiUpdateIn" property
         */
        public MultiUpdateRequestType.Builder<_B> addMultiUpdateIn(MultiUpdateInType... multiUpdateIn) {
            addMultiUpdateIn(Arrays.asList(multiUpdateIn));
            return this;
        }

        /**
         * Sets the new value of "multiUpdateIn" (any previous value will be replaced)
         * 
         * @param multiUpdateIn
         *     New value of the "multiUpdateIn" property.
         */
        public MultiUpdateRequestType.Builder<_B> withMultiUpdateIn(MultiUpdateInType... multiUpdateIn) {
            withMultiUpdateIn(Arrays.asList(multiUpdateIn));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "MultiUpdateIn"
         * property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.e1.MultiUpdateInType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "MultiUpdateIn" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.e1.MultiUpdateInType.Builder#end()}
         *     to return to the current builder.
         */
        public MultiUpdateInType.Builder<? extends MultiUpdateRequestType.Builder<_B>> addMultiUpdateIn() {
            if (this.multiUpdateIn == null) {
                this.multiUpdateIn = new ArrayList<>();
            }
            final MultiUpdateInType.Builder<MultiUpdateRequestType.Builder<_B>> multiUpdateIn_Builder = new MultiUpdateInType.Builder<>(this, null, false);
            this.multiUpdateIn.add(multiUpdateIn_Builder);
            return multiUpdateIn_Builder;
        }

        @Override
        public MultiUpdateRequestType build() {
            if (_storedValue == null) {
                return this.init(new MultiUpdateRequestType());
            } else {
                return ((MultiUpdateRequestType) _storedValue);
            }
        }

        public MultiUpdateRequestType.Builder<_B> copyOf(final MultiUpdateRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public MultiUpdateRequestType.Builder<_B> copyOf(final MultiUpdateRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends MultiUpdateRequestType.Selector<MultiUpdateRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static MultiUpdateRequestType.Select _root() {
            return new MultiUpdateRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private ModifiedType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> created = null;
        private ModifiedType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> modified = null;
        private ModifiedType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> reported = null;
        private MultiUpdateInType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> multiUpdateIn = null;

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
            if (this.created!= null) {
                products.put("created", this.created.init());
            }
            if (this.modified!= null) {
                products.put("modified", this.modified.init());
            }
            if (this.reported!= null) {
                products.put("reported", this.reported.init());
            }
            if (this.multiUpdateIn!= null) {
                products.put("multiUpdateIn", this.multiUpdateIn.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public ModifiedType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> created() {
            return ((this.created == null)?this.created = new ModifiedType.Selector<>(this._root, this, "created"):this.created);
        }

        public ModifiedType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> modified() {
            return ((this.modified == null)?this.modified = new ModifiedType.Selector<>(this._root, this, "modified"):this.modified);
        }

        public ModifiedType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> reported() {
            return ((this.reported == null)?this.reported = new ModifiedType.Selector<>(this._root, this, "reported"):this.reported);
        }

        public MultiUpdateInType.Selector<TRoot, MultiUpdateRequestType.Selector<TRoot, TParent>> multiUpdateIn() {
            return ((this.multiUpdateIn == null)?this.multiUpdateIn = new MultiUpdateInType.Selector<>(this._root, this, "multiUpdateIn"):this.multiUpdateIn);
        }

    }

}
