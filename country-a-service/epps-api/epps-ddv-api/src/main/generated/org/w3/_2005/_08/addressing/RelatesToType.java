
package org.w3._2005._08.addressing;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for RelatesToType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RelatesToType">
 *   <simpleContent>
 *     <extension base="<http://www.w3.org/2001/XMLSchema>anyURI">
 *       <attribute name="RelationshipType" type="{http://www.w3.org/2005/08/addressing}RelationshipTypeOpenEnum" default="http://www.w3.org/2005/08/addressing/reply" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelatesToType", propOrder = {
    "value"
})
public class RelatesToType {

    @XmlValue
    @XmlSchemaType(name = "anyURI")
    protected String value;
    @XmlAttribute(name = "RelationshipType")
    protected String relationshipType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the relationshipType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationshipType() {
        if (relationshipType == null) {
            return "http://www.w3.org/2005/08/addressing/reply";
        } else {
            return relationshipType;
        }
    }

    /**
     * Sets the value of the relationshipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationshipType(String value) {
        this.relationshipType = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final RelatesToType.Builder<_B> _other) {
        _other.value = this.value;
        _other.relationshipType = this.relationshipType;
    }

    public<_B >RelatesToType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new RelatesToType.Builder<_B>(_parentBuilder, this, true);
    }

    public RelatesToType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static RelatesToType.Builder<Void> builder() {
        return new RelatesToType.Builder<>(null, null, false);
    }

    public static<_B >RelatesToType.Builder<_B> copyOf(final RelatesToType _other) {
        final RelatesToType.Builder<_B> _newBuilder = new RelatesToType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final RelatesToType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
            _other.value = this.value;
        }
        final PropertyTree relationshipTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("relationshipType"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(relationshipTypePropertyTree!= null):((relationshipTypePropertyTree == null)||(!relationshipTypePropertyTree.isLeaf())))) {
            _other.relationshipType = this.relationshipType;
        }
    }

    public<_B >RelatesToType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new RelatesToType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public RelatesToType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >RelatesToType.Builder<_B> copyOf(final RelatesToType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final RelatesToType.Builder<_B> _newBuilder = new RelatesToType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static RelatesToType.Builder<Void> copyExcept(final RelatesToType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static RelatesToType.Builder<Void> copyOnly(final RelatesToType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final RelatesToType _storedValue;
        private String value;
        private String relationshipType;

        public Builder(final _B _parentBuilder, final RelatesToType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.value = _other.value;
                    this.relationshipType = _other.relationshipType;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final RelatesToType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
                        this.value = _other.value;
                    }
                    final PropertyTree relationshipTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("relationshipType"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(relationshipTypePropertyTree!= null):((relationshipTypePropertyTree == null)||(!relationshipTypePropertyTree.isLeaf())))) {
                        this.relationshipType = _other.relationshipType;
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

        protected<_P extends RelatesToType >_P init(final _P _product) {
            _product.value = this.value;
            _product.relationshipType = this.relationshipType;
            return _product;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        public RelatesToType.Builder<_B> withValue(final String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the new value of "relationshipType" (any previous value will be replaced)
         * 
         * @param relationshipType
         *     New value of the "relationshipType" property.
         */
        public RelatesToType.Builder<_B> withRelationshipType(final String relationshipType) {
            this.relationshipType = relationshipType;
            return this;
        }

        @Override
        public RelatesToType build() {
            if (_storedValue == null) {
                return this.init(new RelatesToType());
            } else {
                return ((RelatesToType) _storedValue);
            }
        }

        public RelatesToType.Builder<_B> copyOf(final RelatesToType _other) {
            _other.copyTo(this);
            return this;
        }

        public RelatesToType.Builder<_B> copyOf(final RelatesToType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends RelatesToType.Selector<RelatesToType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static RelatesToType.Select _root() {
            return new RelatesToType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, RelatesToType.Selector<TRoot, TParent>> value = null;
        private com.kscs.util.jaxb.Selector<TRoot, RelatesToType.Selector<TRoot, TParent>> relationshipType = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.value!= null) {
                products.put("value", this.value.init());
            }
            if (this.relationshipType!= null) {
                products.put("relationshipType", this.relationshipType.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, RelatesToType.Selector<TRoot, TParent>> value() {
            return ((this.value == null)?this.value = new com.kscs.util.jaxb.Selector<>(this._root, this, "value"):this.value);
        }

        public com.kscs.util.jaxb.Selector<TRoot, RelatesToType.Selector<TRoot, TParent>> relationshipType() {
            return ((this.relationshipType == null)?this.relationshipType = new com.kscs.util.jaxb.Selector<>(this._root, this, "relationshipType"):this.relationshipType);
        }

    }

}
