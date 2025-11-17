
package org.w3._2000._09.xmldsig_;

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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ManifestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ManifestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Reference" maxOccurs="unbounded"/>
 *       </sequence>
 *       <attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestType", propOrder = {
    "reference"
})
public class ManifestType {

    @XmlElement(name = "Reference", required = true)
    protected List<ReferenceType> reference;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the reference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the reference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceType }
     * 
     * 
     * @return
     *     The value of the reference property.
     */
    public List<ReferenceType> getReference() {
        if (reference == null) {
            reference = new ArrayList<>();
        }
        return this.reference;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ManifestType.Builder<_B> _other) {
        if (this.reference == null) {
            _other.reference = null;
        } else {
            _other.reference = new ArrayList<>();
            for (ReferenceType _item: this.reference) {
                _other.reference.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
        _other.id = this.id;
    }

    public<_B >ManifestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ManifestType.Builder<_B>(_parentBuilder, this, true);
    }

    public ManifestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ManifestType.Builder<Void> builder() {
        return new ManifestType.Builder<>(null, null, false);
    }

    public static<_B >ManifestType.Builder<_B> copyOf(final ManifestType _other) {
        final ManifestType.Builder<_B> _newBuilder = new ManifestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ManifestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree referencePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reference"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(referencePropertyTree!= null):((referencePropertyTree == null)||(!referencePropertyTree.isLeaf())))) {
            if (this.reference == null) {
                _other.reference = null;
            } else {
                _other.reference = new ArrayList<>();
                for (ReferenceType _item: this.reference) {
                    _other.reference.add(((_item == null)?null:_item.newCopyBuilder(_other, referencePropertyTree, _propertyTreeUse)));
                }
            }
        }
        final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
            _other.id = this.id;
        }
    }

    public<_B >ManifestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ManifestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ManifestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ManifestType.Builder<_B> copyOf(final ManifestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ManifestType.Builder<_B> _newBuilder = new ManifestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ManifestType.Builder<Void> copyExcept(final ManifestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ManifestType.Builder<Void> copyOnly(final ManifestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ManifestType _storedValue;
        private List<ReferenceType.Builder<ManifestType.Builder<_B>>> reference;
        private String id;

        public Builder(final _B _parentBuilder, final ManifestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.reference == null) {
                        this.reference = null;
                    } else {
                        this.reference = new ArrayList<>();
                        for (ReferenceType _item: _other.reference) {
                            this.reference.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                    this.id = _other.id;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ManifestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree referencePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reference"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(referencePropertyTree!= null):((referencePropertyTree == null)||(!referencePropertyTree.isLeaf())))) {
                        if (_other.reference == null) {
                            this.reference = null;
                        } else {
                            this.reference = new ArrayList<>();
                            for (ReferenceType _item: _other.reference) {
                                this.reference.add(((_item == null)?null:_item.newCopyBuilder(this, referencePropertyTree, _propertyTreeUse)));
                            }
                        }
                    }
                    final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
                        this.id = _other.id;
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

        protected<_P extends ManifestType >_P init(final _P _product) {
            if (this.reference!= null) {
                final List<ReferenceType> reference = new ArrayList<>(this.reference.size());
                for (ReferenceType.Builder<ManifestType.Builder<_B>> _item: this.reference) {
                    reference.add(_item.build());
                }
                _product.reference = reference;
            }
            _product.id = this.id;
            return _product;
        }

        /**
         * Adds the given items to the value of "reference"
         * 
         * @param reference
         *     Items to add to the value of the "reference" property
         */
        public ManifestType.Builder<_B> addReference(final Iterable<? extends ReferenceType> reference) {
            if (reference!= null) {
                if (this.reference == null) {
                    this.reference = new ArrayList<>();
                }
                for (ReferenceType _item: reference) {
                    this.reference.add(new ReferenceType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "reference" (any previous value will be replaced)
         * 
         * @param reference
         *     New value of the "reference" property.
         */
        public ManifestType.Builder<_B> withReference(final Iterable<? extends ReferenceType> reference) {
            if (this.reference!= null) {
                this.reference.clear();
            }
            return addReference(reference);
        }

        /**
         * Adds the given items to the value of "reference"
         * 
         * @param reference
         *     Items to add to the value of the "reference" property
         */
        public ManifestType.Builder<_B> addReference(ReferenceType... reference) {
            addReference(Arrays.asList(reference));
            return this;
        }

        /**
         * Sets the new value of "reference" (any previous value will be replaced)
         * 
         * @param reference
         *     New value of the "reference" property.
         */
        public ManifestType.Builder<_B> withReference(ReferenceType... reference) {
            withReference(Arrays.asList(reference));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "Reference" property.
         * Use {@link org.w3._2000._09.xmldsig_.ReferenceType.Builder#end()} to return to
         * the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "Reference" property.
         *     Use {@link org.w3._2000._09.xmldsig_.ReferenceType.Builder#end()} to return to
         *     the current builder.
         */
        public ReferenceType.Builder<? extends ManifestType.Builder<_B>> addReference() {
            if (this.reference == null) {
                this.reference = new ArrayList<>();
            }
            final ReferenceType.Builder<ManifestType.Builder<_B>> reference_Builder = new ReferenceType.Builder<>(this, null, false);
            this.reference.add(reference_Builder);
            return reference_Builder;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        public ManifestType.Builder<_B> withId(final String id) {
            this.id = id;
            return this;
        }

        @Override
        public ManifestType build() {
            if (_storedValue == null) {
                return this.init(new ManifestType());
            } else {
                return ((ManifestType) _storedValue);
            }
        }

        public ManifestType.Builder<_B> copyOf(final ManifestType _other) {
            _other.copyTo(this);
            return this;
        }

        public ManifestType.Builder<_B> copyOf(final ManifestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ManifestType.Selector<ManifestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ManifestType.Select _root() {
            return new ManifestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private ReferenceType.Selector<TRoot, ManifestType.Selector<TRoot, TParent>> reference = null;
        private com.kscs.util.jaxb.Selector<TRoot, ManifestType.Selector<TRoot, TParent>> id = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.reference!= null) {
                products.put("reference", this.reference.init());
            }
            if (this.id!= null) {
                products.put("id", this.id.init());
            }
            return products;
        }

        public ReferenceType.Selector<TRoot, ManifestType.Selector<TRoot, TParent>> reference() {
            return ((this.reference == null)?this.reference = new ReferenceType.Selector<>(this._root, this, "reference"):this.reference);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ManifestType.Selector<TRoot, TParent>> id() {
            return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
        }

    }

}
