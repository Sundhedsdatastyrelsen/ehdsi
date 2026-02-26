
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
 * <p>Java class for ReviewedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ReviewedType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Modificator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModificatorType"/>
 *         <element name="CreatedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReviewedType", propOrder = {
    "modificator",
    "createdDateTime"
})
public class ReviewedType {

    @XmlElement(name = "Modificator", required = true)
    protected ModificatorType modificator;
    @XmlElement(name = "CreatedDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDateTime;

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
     * Gets the value of the createdDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Sets the value of the createdDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDateTime(XMLGregorianCalendar value) {
        this.createdDateTime = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final ReviewedType.Builder<_B> _other) {
        _other.modificator = ((this.modificator == null)?null:this.modificator.newCopyBuilder(_other));
        _other.createdDateTime = ((this.createdDateTime == null)?null:((XMLGregorianCalendar) this.createdDateTime.clone()));
    }

    public<_B >ReviewedType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ReviewedType.Builder<_B>(_parentBuilder, this, true);
    }

    public ReviewedType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ReviewedType.Builder<Void> builder() {
        return new ReviewedType.Builder<>(null, null, false);
    }

    public static<_B >ReviewedType.Builder<_B> copyOf(final ReviewedType _other) {
        final ReviewedType.Builder<_B> _newBuilder = new ReviewedType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ReviewedType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree modificatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modificator"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modificatorPropertyTree!= null):((modificatorPropertyTree == null)||(!modificatorPropertyTree.isLeaf())))) {
            _other.modificator = ((this.modificator == null)?null:this.modificator.newCopyBuilder(_other, modificatorPropertyTree, _propertyTreeUse));
        }
        final PropertyTree createdDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createdDateTime"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdDateTimePropertyTree!= null):((createdDateTimePropertyTree == null)||(!createdDateTimePropertyTree.isLeaf())))) {
            _other.createdDateTime = ((this.createdDateTime == null)?null:((XMLGregorianCalendar) this.createdDateTime.clone()));
        }
    }

    public<_B >ReviewedType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ReviewedType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ReviewedType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ReviewedType.Builder<_B> copyOf(final ReviewedType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ReviewedType.Builder<_B> _newBuilder = new ReviewedType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ReviewedType.Builder<Void> copyExcept(final ReviewedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ReviewedType.Builder<Void> copyOnly(final ReviewedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ReviewedType _storedValue;
        private ModificatorType.Builder<ReviewedType.Builder<_B>> modificator;
        private XMLGregorianCalendar createdDateTime;

        public Builder(final _B _parentBuilder, final ReviewedType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.modificator = ((_other.modificator == null)?null:_other.modificator.newCopyBuilder(this));
                    this.createdDateTime = ((_other.createdDateTime == null)?null:((XMLGregorianCalendar) _other.createdDateTime.clone()));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ReviewedType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree modificatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modificator"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modificatorPropertyTree!= null):((modificatorPropertyTree == null)||(!modificatorPropertyTree.isLeaf())))) {
                        this.modificator = ((_other.modificator == null)?null:_other.modificator.newCopyBuilder(this, modificatorPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree createdDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createdDateTime"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdDateTimePropertyTree!= null):((createdDateTimePropertyTree == null)||(!createdDateTimePropertyTree.isLeaf())))) {
                        this.createdDateTime = ((_other.createdDateTime == null)?null:((XMLGregorianCalendar) _other.createdDateTime.clone()));
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

        protected<_P extends ReviewedType >_P init(final _P _product) {
            _product.modificator = ((this.modificator == null)?null:this.modificator.build());
            _product.createdDateTime = this.createdDateTime;
            return _product;
        }

        /**
         * Sets the new value of "modificator" (any previous value will be replaced)
         * 
         * @param modificator
         *     New value of the "modificator" property.
         */
        public ReviewedType.Builder<_B> withModificator(final ModificatorType modificator) {
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
        public ModificatorType.Builder<? extends ReviewedType.Builder<_B>> withModificator() {
            if (this.modificator!= null) {
                return this.modificator;
            }
            return this.modificator = new ModificatorType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "createdDateTime" (any previous value will be replaced)
         * 
         * @param createdDateTime
         *     New value of the "createdDateTime" property.
         */
        public ReviewedType.Builder<_B> withCreatedDateTime(final XMLGregorianCalendar createdDateTime) {
            this.createdDateTime = createdDateTime;
            return this;
        }

        @Override
        public ReviewedType build() {
            if (_storedValue == null) {
                return this.init(new ReviewedType());
            } else {
                return ((ReviewedType) _storedValue);
            }
        }

        public ReviewedType.Builder<_B> copyOf(final ReviewedType _other) {
            _other.copyTo(this);
            return this;
        }

        public ReviewedType.Builder<_B> copyOf(final ReviewedType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ReviewedType.Selector<ReviewedType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ReviewedType.Select _root() {
            return new ReviewedType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private ModificatorType.Selector<TRoot, ReviewedType.Selector<TRoot, TParent>> modificator = null;
        private com.kscs.util.jaxb.Selector<TRoot, ReviewedType.Selector<TRoot, TParent>> createdDateTime = null;

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
            if (this.createdDateTime!= null) {
                products.put("createdDateTime", this.createdDateTime.init());
            }
            return products;
        }

        public ModificatorType.Selector<TRoot, ReviewedType.Selector<TRoot, TParent>> modificator() {
            return ((this.modificator == null)?this.modificator = new ModificatorType.Selector<>(this._root, this, "modificator"):this.modificator);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ReviewedType.Selector<TRoot, TParent>> createdDateTime() {
            return ((this.createdDateTime == null)?this.createdDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "createdDateTime"):this.createdDateTime);
        }

    }

}
