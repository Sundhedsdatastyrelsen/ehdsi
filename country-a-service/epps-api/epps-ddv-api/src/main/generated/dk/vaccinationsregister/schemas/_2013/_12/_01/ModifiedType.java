
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModifiedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ModifiedType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Modificator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModificatorType"/>
 *         <element name="ModifiedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModifiedType", propOrder = {
    "modificator",
    "modifiedDateTime"
})
public class ModifiedType {

    @XmlElement(name = "Modificator", required = true)
    protected ModificatorType modificator;
    @XmlElement(name = "ModifiedDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDateTime;

    /**
     * Gets the value of the modificator property.
     * 
     * @return
     *     possible object is
     *     {@link ModificatorType }
     *     
     */
    public ModificatorType getModificator() {
        return modificator;
    }

    /**
     * Sets the value of the modificator property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModificatorType }
     *     
     */
    public void setModificator(ModificatorType value) {
        this.modificator = value;
    }

    /**
     * Gets the value of the modifiedDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDateTime() {
        return modifiedDateTime;
    }

    /**
     * Sets the value of the modifiedDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDateTime(XMLGregorianCalendar value) {
        this.modifiedDateTime = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ModifiedType.Builder<_B> _other) {
        _other.modificator = ((this.modificator == null)?null:this.modificator.newCopyBuilder(_other));
        _other.modifiedDateTime = ((this.modifiedDateTime == null)?null:((XMLGregorianCalendar) this.modifiedDateTime.clone()));
    }

    public<_B >ModifiedType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ModifiedType.Builder<_B>(_parentBuilder, this, true);
    }

    public ModifiedType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ModifiedType.Builder<Void> builder() {
        return new ModifiedType.Builder<>(null, null, false);
    }

    public static<_B >ModifiedType.Builder<_B> copyOf(final ModifiedType _other) {
        final ModifiedType.Builder<_B> _newBuilder = new ModifiedType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ModifiedType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree modificatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modificator"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modificatorPropertyTree!= null):((modificatorPropertyTree == null)||(!modificatorPropertyTree.isLeaf())))) {
            _other.modificator = ((this.modificator == null)?null:this.modificator.newCopyBuilder(_other, modificatorPropertyTree, _propertyTreeUse));
        }
        final PropertyTree modifiedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modifiedDateTime"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedDateTimePropertyTree!= null):((modifiedDateTimePropertyTree == null)||(!modifiedDateTimePropertyTree.isLeaf())))) {
            _other.modifiedDateTime = ((this.modifiedDateTime == null)?null:((XMLGregorianCalendar) this.modifiedDateTime.clone()));
        }
    }

    public<_B >ModifiedType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ModifiedType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ModifiedType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ModifiedType.Builder<_B> copyOf(final ModifiedType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ModifiedType.Builder<_B> _newBuilder = new ModifiedType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ModifiedType.Builder<Void> copyExcept(final ModifiedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ModifiedType.Builder<Void> copyOnly(final ModifiedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ModifiedType _storedValue;
        private ModificatorType.Builder<ModifiedType.Builder<_B>> modificator;
        private XMLGregorianCalendar modifiedDateTime;

        public Builder(final _B _parentBuilder, final ModifiedType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.modificator = ((_other.modificator == null)?null:_other.modificator.newCopyBuilder(this));
                    this.modifiedDateTime = ((_other.modifiedDateTime == null)?null:((XMLGregorianCalendar) _other.modifiedDateTime.clone()));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ModifiedType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree modificatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modificator"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modificatorPropertyTree!= null):((modificatorPropertyTree == null)||(!modificatorPropertyTree.isLeaf())))) {
                        this.modificator = ((_other.modificator == null)?null:_other.modificator.newCopyBuilder(this, modificatorPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree modifiedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modifiedDateTime"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedDateTimePropertyTree!= null):((modifiedDateTimePropertyTree == null)||(!modifiedDateTimePropertyTree.isLeaf())))) {
                        this.modifiedDateTime = ((_other.modifiedDateTime == null)?null:((XMLGregorianCalendar) _other.modifiedDateTime.clone()));
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

        protected<_P extends ModifiedType >_P init(final _P _product) {
            _product.modificator = ((this.modificator == null)?null:this.modificator.build());
            _product.modifiedDateTime = this.modifiedDateTime;
            return _product;
        }

        /**
         * Sets the new value of "modificator" (any previous value will be replaced)
         * 
         * @param modificator
         *     New value of the "modificator" property.
         */
        public ModifiedType.Builder<_B> withModificator(final ModificatorType modificator) {
            this.modificator = ((modificator == null)?null:new ModificatorType.Builder<>(this, modificator, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "modificator" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.ModificatorType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "modificator" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.ModificatorType.Builder#end()} to
         *     return to the current builder.
         */
        public ModificatorType.Builder<? extends ModifiedType.Builder<_B>> withModificator() {
            if (this.modificator!= null) {
                return this.modificator;
            }
            return this.modificator = new ModificatorType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "modifiedDateTime" (any previous value will be replaced)
         * 
         * @param modifiedDateTime
         *     New value of the "modifiedDateTime" property.
         */
        public ModifiedType.Builder<_B> withModifiedDateTime(final XMLGregorianCalendar modifiedDateTime) {
            this.modifiedDateTime = modifiedDateTime;
            return this;
        }

        @Override
        public ModifiedType build() {
            if (_storedValue == null) {
                return this.init(new ModifiedType());
            } else {
                return ((ModifiedType) _storedValue);
            }
        }

        public ModifiedType.Builder<_B> copyOf(final ModifiedType _other) {
            _other.copyTo(this);
            return this;
        }

        public ModifiedType.Builder<_B> copyOf(final ModifiedType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ModifiedType.Selector<ModifiedType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ModifiedType.Select _root() {
            return new ModifiedType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private ModificatorType.Selector<TRoot, ModifiedType.Selector<TRoot, TParent>> modificator = null;
        private com.kscs.util.jaxb.Selector<TRoot, ModifiedType.Selector<TRoot, TParent>> modifiedDateTime = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.modificator!= null) {
                products.put("modificator", this.modificator.init());
            }
            if (this.modifiedDateTime!= null) {
                products.put("modifiedDateTime", this.modifiedDateTime.init());
            }
            return products;
        }

        public ModificatorType.Selector<TRoot, ModifiedType.Selector<TRoot, TParent>> modificator() {
            return ((this.modificator == null)?this.modificator = new ModificatorType.Selector<>(this._root, this, "modificator"):this.modificator);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ModifiedType.Selector<TRoot, TParent>> modifiedDateTime() {
            return ((this.modifiedDateTime == null)?this.modifiedDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "modifiedDateTime"):this.modifiedDateTime);
        }

    }

}
