
package dk.vaccinationsregister.schemas._2013._12._01;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DrugStrengthType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DrugStrengthType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <sequence>
 *           <element name="DrugStrengthValue" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthValueType"/>
 *           <element name="DrugStrengthUnitCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthUnitCodeType"/>
 *           <element name="DrugStrengthUnitText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthUnitTextType" minOccurs="0"/>
 *         </sequence>
 *         <sequence>
 *           <element name="DrugStrengthText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthTextType" minOccurs="0"/>
 *           <element name="DrugStrengthUnitText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugStrengthUnitTextType" minOccurs="0"/>
 *         </sequence>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugStrengthType", propOrder = {
    "content"
})
public class DrugStrengthType {

    @XmlElementRefs({
        @XmlElementRef(name = "DrugStrengthValue", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "DrugStrengthUnitCode", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "DrugStrengthUnitText", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "DrugStrengthText", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<? extends Serializable>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "DrugStrengthUnitText" is used by two different parts of a schema. See: 
     * line 25 of file:/mnt/d/Code/Globeteam/ehdsi/country-a-service/epps-api/epps-ddv-api/src/main/resources/schemas/2013/12/01/SSI_DrugStrength.xsd
     * line 21 of file:/mnt/d/Code/Globeteam/ehdsi/country-a-service/epps-api/epps-ddv-api/src/main/resources/schemas/2013/12/01/SSI_DrugStrength.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names:Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * 
     * @return
     *     The value of the content property.
     */
    public List<JAXBElement<? extends Serializable>> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return this.content;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DrugStrengthType.Builder<_B> _other) {
        if (this.content == null) {
            _other.content = null;
        } else {
            _other.content = new ArrayList<>();
            for (JAXBElement<? extends Serializable> _item: this.content) {
                _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
    }

    public<_B >DrugStrengthType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DrugStrengthType.Builder<_B>(_parentBuilder, this, true);
    }

    public DrugStrengthType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DrugStrengthType.Builder<Void> builder() {
        return new DrugStrengthType.Builder<>(null, null, false);
    }

    public static<_B >DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType _other) {
        final DrugStrengthType.Builder<_B> _newBuilder = new DrugStrengthType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DrugStrengthType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree contentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("content"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(contentPropertyTree!= null):((contentPropertyTree == null)||(!contentPropertyTree.isLeaf())))) {
            if (this.content == null) {
                _other.content = null;
            } else {
                _other.content = new ArrayList<>();
                for (JAXBElement<? extends Serializable> _item: this.content) {
                    _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
    }

    public<_B >DrugStrengthType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DrugStrengthType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DrugStrengthType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DrugStrengthType.Builder<_B> _newBuilder = new DrugStrengthType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DrugStrengthType.Builder<Void> copyExcept(final DrugStrengthType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DrugStrengthType.Builder<Void> copyOnly(final DrugStrengthType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DrugStrengthType _storedValue;
        private List<Buildable> content;

        public Builder(final _B _parentBuilder, final DrugStrengthType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.content == null) {
                        this.content = null;
                    } else {
                        this.content = new ArrayList<>();
                        for (JAXBElement<? extends Serializable> _item: _other.content) {
                            this.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DrugStrengthType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree contentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("content"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(contentPropertyTree!= null):((contentPropertyTree == null)||(!contentPropertyTree.isLeaf())))) {
                        if (_other.content == null) {
                            this.content = null;
                        } else {
                            this.content = new ArrayList<>();
                            for (JAXBElement<? extends Serializable> _item: _other.content) {
                                this.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
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

        protected<_P extends DrugStrengthType >_P init(final _P _product) {
            if (this.content!= null) {
                final List<JAXBElement<? extends Serializable>> content = new ArrayList<>(this.content.size());
                for (Buildable _item: this.content) {
                    content.add(((JAXBElement<? extends Serializable> ) _item.build()));
                }
                _product.content = content;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public DrugStrengthType.Builder<_B> addContent(final Iterable<? extends JAXBElement<? extends Serializable>> content) {
            if (content!= null) {
                if (this.content == null) {
                    this.content = new ArrayList<>();
                }
                for (JAXBElement<? extends Serializable> _item: content) {
                    this.content.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public DrugStrengthType.Builder<_B> withContent(final Iterable<? extends JAXBElement<? extends Serializable>> content) {
            if (this.content!= null) {
                this.content.clear();
            }
            return addContent(content);
        }

        /**
         * Adds the given items to the value of "content"
         * 
         * @param content
         *     Items to add to the value of the "content" property
         */
        public DrugStrengthType.Builder<_B> addContent(JAXBElement<? extends Serializable> ... content) {
            addContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public DrugStrengthType.Builder<_B> withContent(JAXBElement<? extends Serializable> ... content) {
            withContent(Arrays.asList(content));
            return this;
        }

        @Override
        public DrugStrengthType build() {
            if (_storedValue == null) {
                return this.init(new DrugStrengthType());
            } else {
                return ((DrugStrengthType) _storedValue);
            }
        }

        public DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType _other) {
            _other.copyTo(this);
            return this;
        }

        public DrugStrengthType.Builder<_B> copyOf(final DrugStrengthType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DrugStrengthType.Selector<DrugStrengthType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DrugStrengthType.Select _root() {
            return new DrugStrengthType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> content = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.content!= null) {
                products.put("content", this.content.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, DrugStrengthType.Selector<TRoot, TParent>> content() {
            return ((this.content == null)?this.content = new com.kscs.util.jaxb.Selector<>(this._root, this, "content"):this.content);
        }

    }

}
