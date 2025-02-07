
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MultiUpdateResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="MultiUpdateResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="VersionMismatchWarningIndicator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VersionMismatchWarningIndicatorType" minOccurs="0"/>
 *         <element name="MultiUpdateOut" type="{http://vaccinationsregister.dk/schemas/2013/12/01}MultiUpdateOutType" maxOccurs="20" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiUpdateResponseType", propOrder = {
    "personCivilRegistrationIdentifier",
    "versionMismatchWarningIndicator",
    "multiUpdateOut"
})
public class MultiUpdateResponseType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "VersionMismatchWarningIndicator")
    protected VersionMismatchWarningIndicatorType versionMismatchWarningIndicator;
    @XmlElement(name = "MultiUpdateOut")
    protected List<MultiUpdateOutType> multiUpdateOut;

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
     * Gets the value of the versionMismatchWarningIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link VersionMismatchWarningIndicatorType }
     *     
     */
    public VersionMismatchWarningIndicatorType getVersionMismatchWarningIndicator() {
        return versionMismatchWarningIndicator;
    }

    /**
     * Sets the value of the versionMismatchWarningIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link VersionMismatchWarningIndicatorType }
     *     
     */
    public void setVersionMismatchWarningIndicator(VersionMismatchWarningIndicatorType value) {
        this.versionMismatchWarningIndicator = value;
    }

    /**
     * Gets the value of the multiUpdateOut property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the multiUpdateOut property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiUpdateOut().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultiUpdateOutType }
     * 
     * 
     * @return
     *     The value of the multiUpdateOut property.
     */
    public List<MultiUpdateOutType> getMultiUpdateOut() {
        if (multiUpdateOut == null) {
            multiUpdateOut = new ArrayList<>();
        }
        return this.multiUpdateOut;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final MultiUpdateResponseType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other));
        if (this.multiUpdateOut == null) {
            _other.multiUpdateOut = null;
        } else {
            _other.multiUpdateOut = new ArrayList<>();
            for (MultiUpdateOutType _item: this.multiUpdateOut) {
                _other.multiUpdateOut.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >MultiUpdateResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new MultiUpdateResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public MultiUpdateResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static MultiUpdateResponseType.Builder<Void> builder() {
        return new MultiUpdateResponseType.Builder<>(null, null, false);
    }

    public static<_B >MultiUpdateResponseType.Builder<_B> copyOf(final MultiUpdateResponseType _other) {
        final MultiUpdateResponseType.Builder<_B> _newBuilder = new MultiUpdateResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final MultiUpdateResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree versionMismatchWarningIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("versionMismatchWarningIndicator"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionMismatchWarningIndicatorPropertyTree!= null):((versionMismatchWarningIndicatorPropertyTree == null)||(!versionMismatchWarningIndicatorPropertyTree.isLeaf())))) {
            _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other, versionMismatchWarningIndicatorPropertyTree, _propertyTreeUse));
        }
        final PropertyTree multiUpdateOutPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("multiUpdateOut"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(multiUpdateOutPropertyTree!= null):((multiUpdateOutPropertyTree == null)||(!multiUpdateOutPropertyTree.isLeaf())))) {
            if (this.multiUpdateOut == null) {
                _other.multiUpdateOut = null;
            } else {
                _other.multiUpdateOut = new ArrayList<>();
                for (MultiUpdateOutType _item: this.multiUpdateOut) {
                    _other.multiUpdateOut.add(((_item == null)?null:_item.newCopyBuilder(_other, multiUpdateOutPropertyTree, _propertyTreeUse)));
                }
            }
        }
    }

    public<_B >MultiUpdateResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new MultiUpdateResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public MultiUpdateResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >MultiUpdateResponseType.Builder<_B> copyOf(final MultiUpdateResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final MultiUpdateResponseType.Builder<_B> _newBuilder = new MultiUpdateResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static MultiUpdateResponseType.Builder<Void> copyExcept(final MultiUpdateResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static MultiUpdateResponseType.Builder<Void> copyOnly(final MultiUpdateResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final MultiUpdateResponseType _storedValue;
        private String personCivilRegistrationIdentifier;
        private VersionMismatchWarningIndicatorType.Builder<MultiUpdateResponseType.Builder<_B>> versionMismatchWarningIndicator;
        private List<MultiUpdateOutType.Builder<MultiUpdateResponseType.Builder<_B>>> multiUpdateOut;

        public Builder(final _B _parentBuilder, final MultiUpdateResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.versionMismatchWarningIndicator = ((_other.versionMismatchWarningIndicator == null)?null:_other.versionMismatchWarningIndicator.newCopyBuilder(this));
                    if (_other.multiUpdateOut == null) {
                        this.multiUpdateOut = null;
                    } else {
                        this.multiUpdateOut = new ArrayList<>();
                        for (MultiUpdateOutType _item: _other.multiUpdateOut) {
                            this.multiUpdateOut.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final MultiUpdateResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
                        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    }
                    final PropertyTree versionMismatchWarningIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("versionMismatchWarningIndicator"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionMismatchWarningIndicatorPropertyTree!= null):((versionMismatchWarningIndicatorPropertyTree == null)||(!versionMismatchWarningIndicatorPropertyTree.isLeaf())))) {
                        this.versionMismatchWarningIndicator = ((_other.versionMismatchWarningIndicator == null)?null:_other.versionMismatchWarningIndicator.newCopyBuilder(this, versionMismatchWarningIndicatorPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree multiUpdateOutPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("multiUpdateOut"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(multiUpdateOutPropertyTree!= null):((multiUpdateOutPropertyTree == null)||(!multiUpdateOutPropertyTree.isLeaf())))) {
                        if (_other.multiUpdateOut == null) {
                            this.multiUpdateOut = null;
                        } else {
                            this.multiUpdateOut = new ArrayList<>();
                            for (MultiUpdateOutType _item: _other.multiUpdateOut) {
                                this.multiUpdateOut.add(((_item == null)?null:_item.newCopyBuilder(this, multiUpdateOutPropertyTree, _propertyTreeUse)));
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

        protected<_P extends MultiUpdateResponseType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.build());
            if (this.multiUpdateOut!= null) {
                final List<MultiUpdateOutType> multiUpdateOut = new ArrayList<>(this.multiUpdateOut.size());
                for (MultiUpdateOutType.Builder<MultiUpdateResponseType.Builder<_B>> _item: this.multiUpdateOut) {
                    multiUpdateOut.add(_item.build());
                }
                _product.multiUpdateOut = multiUpdateOut;
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
        public MultiUpdateResponseType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
            this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "versionMismatchWarningIndicator" (any previous value will
         * be replaced)
         * 
         * @param versionMismatchWarningIndicator
         *     New value of the "versionMismatchWarningIndicator" property.
         */
        public MultiUpdateResponseType.Builder<_B> withVersionMismatchWarningIndicator(final VersionMismatchWarningIndicatorType versionMismatchWarningIndicator) {
            this.versionMismatchWarningIndicator = ((versionMismatchWarningIndicator == null)?null:new VersionMismatchWarningIndicatorType.Builder<>(this, versionMismatchWarningIndicator, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "versionMismatchWarningIndicator" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.VersionMismatchWarningIndicatorType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "versionMismatchWarningIndicator"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.VersionMismatchWarningIndicatorType.Builder#end()}
         *     to return to the current builder.
         */
        public VersionMismatchWarningIndicatorType.Builder<? extends MultiUpdateResponseType.Builder<_B>> withVersionMismatchWarningIndicator() {
            if (this.versionMismatchWarningIndicator!= null) {
                return this.versionMismatchWarningIndicator;
            }
            return this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Builder<>(this, null, false);
        }

        /**
         * Adds the given items to the value of "multiUpdateOut"
         * 
         * @param multiUpdateOut
         *     Items to add to the value of the "multiUpdateOut" property
         */
        public MultiUpdateResponseType.Builder<_B> addMultiUpdateOut(final Iterable<? extends MultiUpdateOutType> multiUpdateOut) {
            if (multiUpdateOut!= null) {
                if (this.multiUpdateOut == null) {
                    this.multiUpdateOut = new ArrayList<>();
                }
                for (MultiUpdateOutType _item: multiUpdateOut) {
                    this.multiUpdateOut.add(new MultiUpdateOutType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "multiUpdateOut" (any previous value will be replaced)
         * 
         * @param multiUpdateOut
         *     New value of the "multiUpdateOut" property.
         */
        public MultiUpdateResponseType.Builder<_B> withMultiUpdateOut(final Iterable<? extends MultiUpdateOutType> multiUpdateOut) {
            if (this.multiUpdateOut!= null) {
                this.multiUpdateOut.clear();
            }
            return addMultiUpdateOut(multiUpdateOut);
        }

        /**
         * Adds the given items to the value of "multiUpdateOut"
         * 
         * @param multiUpdateOut
         *     Items to add to the value of the "multiUpdateOut" property
         */
        public MultiUpdateResponseType.Builder<_B> addMultiUpdateOut(MultiUpdateOutType... multiUpdateOut) {
            addMultiUpdateOut(Arrays.asList(multiUpdateOut));
            return this;
        }

        /**
         * Sets the new value of "multiUpdateOut" (any previous value will be replaced)
         * 
         * @param multiUpdateOut
         *     New value of the "multiUpdateOut" property.
         */
        public MultiUpdateResponseType.Builder<_B> withMultiUpdateOut(MultiUpdateOutType... multiUpdateOut) {
            withMultiUpdateOut(Arrays.asList(multiUpdateOut));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "MultiUpdateOut"
         * property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.MultiUpdateOutType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "MultiUpdateOut" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.MultiUpdateOutType.Builder#end()}
         *     to return to the current builder.
         */
        public MultiUpdateOutType.Builder<? extends MultiUpdateResponseType.Builder<_B>> addMultiUpdateOut() {
            if (this.multiUpdateOut == null) {
                this.multiUpdateOut = new ArrayList<>();
            }
            final MultiUpdateOutType.Builder<MultiUpdateResponseType.Builder<_B>> multiUpdateOut_Builder = new MultiUpdateOutType.Builder<>(this, null, false);
            this.multiUpdateOut.add(multiUpdateOut_Builder);
            return multiUpdateOut_Builder;
        }

        @Override
        public MultiUpdateResponseType build() {
            if (_storedValue == null) {
                return this.init(new MultiUpdateResponseType());
            } else {
                return ((MultiUpdateResponseType) _storedValue);
            }
        }

        public MultiUpdateResponseType.Builder<_B> copyOf(final MultiUpdateResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public MultiUpdateResponseType.Builder<_B> copyOf(final MultiUpdateResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends MultiUpdateResponseType.Selector<MultiUpdateResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static MultiUpdateResponseType.Select _root() {
            return new MultiUpdateResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, MultiUpdateResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private VersionMismatchWarningIndicatorType.Selector<TRoot, MultiUpdateResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator = null;
        private MultiUpdateOutType.Selector<TRoot, MultiUpdateResponseType.Selector<TRoot, TParent>> multiUpdateOut = null;

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
            if (this.versionMismatchWarningIndicator!= null) {
                products.put("versionMismatchWarningIndicator", this.versionMismatchWarningIndicator.init());
            }
            if (this.multiUpdateOut!= null) {
                products.put("multiUpdateOut", this.multiUpdateOut.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, MultiUpdateResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public VersionMismatchWarningIndicatorType.Selector<TRoot, MultiUpdateResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator() {
            return ((this.versionMismatchWarningIndicator == null)?this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Selector<>(this._root, this, "versionMismatchWarningIndicator"):this.versionMismatchWarningIndicator);
        }

        public MultiUpdateOutType.Selector<TRoot, MultiUpdateResponseType.Selector<TRoot, TParent>> multiUpdateOut() {
            return ((this.multiUpdateOut == null)?this.multiUpdateOut = new MultiUpdateOutType.Selector<>(this._root, this, "multiUpdateOut"):this.multiUpdateOut);
        }

    }

}
