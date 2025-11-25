
package dk.vaccinationsregister.schemas._2013._12._01;

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
 * <p>Java class for ModificatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ModificatorType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <choice>
 *           <sequence>
 *             <element name="AuthorisedHealthcareProfessional" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisedHealthcareProfessionalType"/>
 *             <element name="Organisation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationType"/>
 *             <element name="AuthorisedBy" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisedModificatorType" minOccurs="0"/>
 *           </sequence>
 *           <element name="Patient" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PatientFlagType"/>
 *           <sequence>
 *             <element name="Other" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModificatorPersonType"/>
 *             <element name="Role" type="{http://vaccinationsregister.dk/schemas/2013/12/01}RequestedRoleType"/>
 *             <element name="Organisation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationType" minOccurs="0"/>
 *             <element name="AuthorisedBy" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisedModificatorType" minOccurs="0"/>
 *           </sequence>
 *           <element name="PartlyDefinedEffectuator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PartlyDefinedEffectuatorType"/>
 *           <sequence>
 *             <element name="HealthInsuranceImport" type="{http://vaccinationsregister.dk/schemas/2013/12/01}HealthInsuranceImportFlagType"/>
 *             <element name="Organisation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationType" minOccurs="0"/>
 *           </sequence>
 *           <sequence>
 *             <element name="SystemUpdate" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SystemUpdateFlagType"/>
 *             <element name="SystemName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SystemNameType"/>
 *           </sequence>
 *         </choice>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModificatorType", propOrder = {
    "content"
})
public class ModificatorType {

    @XmlElementRefs({
        @XmlElementRef(name = "AuthorisedHealthcareProfessional", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Organisation", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "AuthorisedBy", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Patient", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Other", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Role", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PartlyDefinedEffectuator", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "HealthInsuranceImport", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SystemUpdate", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SystemName", namespace = "http://vaccinationsregister.dk/schemas/2013/12/01", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Organisation" is used by two different parts of a schema. See: 
     * line 33 of file:/Users/hansbugge/gtsrc/sds/ehdsi/national-connector/epps-api/epps-ddv-api/src/main/resources/ddv/schemas/2013/12/01/SSI_Modificator.xsd
     * line 26 of file:/Users/hansbugge/gtsrc/sds/ehdsi/national-connector/epps-api/epps-ddv-api/src/main/resources/ddv/schemas/2013/12/01/SSI_Modificator.xsd
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
     * {@link JAXBElement }{@code <}{@link AuthorisedHealthcareProfessionalType }{@code >}
     * {@link JAXBElement }{@code <}{@link AuthorisedModificatorType }{@code >}
     * {@link JAXBElement }{@code <}{@link HealthInsuranceImportFlagType }{@code >}
     * {@link JAXBElement }{@code <}{@link ModificatorPersonType }{@code >}
     * {@link JAXBElement }{@code <}{@link OrganisationType }{@code >}
     * {@link JAXBElement }{@code <}{@link PartlyDefinedEffectuatorType }{@code >}
     * {@link JAXBElement }{@code <}{@link PatientFlagType }{@code >}
     * {@link JAXBElement }{@code <}{@link SystemUpdateFlagType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     * @return
     *     The value of the content property.
     */
    public List<JAXBElement<?>> getContent() {
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
    public<_B >void copyTo(final ModificatorType.Builder<_B> _other) {
        if (this.content == null) {
            _other.content = null;
        } else {
            _other.content = new ArrayList<>();
            for (JAXBElement<?> _item: this.content) {
                _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
    }

    public<_B >ModificatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ModificatorType.Builder<_B>(_parentBuilder, this, true);
    }

    public ModificatorType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ModificatorType.Builder<Void> builder() {
        return new ModificatorType.Builder<>(null, null, false);
    }

    public static<_B >ModificatorType.Builder<_B> copyOf(final ModificatorType _other) {
        final ModificatorType.Builder<_B> _newBuilder = new ModificatorType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ModificatorType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree contentPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("content"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(contentPropertyTree!= null):((contentPropertyTree == null)||(!contentPropertyTree.isLeaf())))) {
            if (this.content == null) {
                _other.content = null;
            } else {
                _other.content = new ArrayList<>();
                for (JAXBElement<?> _item: this.content) {
                    _other.content.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
    }

    public<_B >ModificatorType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ModificatorType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ModificatorType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ModificatorType.Builder<_B> copyOf(final ModificatorType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ModificatorType.Builder<_B> _newBuilder = new ModificatorType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ModificatorType.Builder<Void> copyExcept(final ModificatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ModificatorType.Builder<Void> copyOnly(final ModificatorType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ModificatorType _storedValue;
        private List<Buildable> content;

        public Builder(final _B _parentBuilder, final ModificatorType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.content == null) {
                        this.content = null;
                    } else {
                        this.content = new ArrayList<>();
                        for (JAXBElement<?> _item: _other.content) {
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

        public Builder(final _B _parentBuilder, final ModificatorType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
                            for (JAXBElement<?> _item: _other.content) {
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

        protected<_P extends ModificatorType >_P init(final _P _product) {
            if (this.content!= null) {
                final List<JAXBElement<?>> content = new ArrayList<>(this.content.size());
                for (Buildable _item: this.content) {
                    content.add(((JAXBElement<?> ) _item.build()));
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
        public ModificatorType.Builder<_B> addContent(final Iterable<? extends JAXBElement<?>> content) {
            if (content!= null) {
                if (this.content == null) {
                    this.content = new ArrayList<>();
                }
                for (JAXBElement<?> _item: content) {
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
        public ModificatorType.Builder<_B> withContent(final Iterable<? extends JAXBElement<?>> content) {
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
        public ModificatorType.Builder<_B> addContent(JAXBElement<?> ... content) {
            addContent(Arrays.asList(content));
            return this;
        }

        /**
         * Sets the new value of "content" (any previous value will be replaced)
         * 
         * @param content
         *     New value of the "content" property.
         */
        public ModificatorType.Builder<_B> withContent(JAXBElement<?> ... content) {
            withContent(Arrays.asList(content));
            return this;
        }

        @Override
        public ModificatorType build() {
            if (_storedValue == null) {
                return this.init(new ModificatorType());
            } else {
                return ((ModificatorType) _storedValue);
            }
        }

        public ModificatorType.Builder<_B> copyOf(final ModificatorType _other) {
            _other.copyTo(this);
            return this;
        }

        public ModificatorType.Builder<_B> copyOf(final ModificatorType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ModificatorType.Selector<ModificatorType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ModificatorType.Select _root() {
            return new ModificatorType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> content = null;

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

        public com.kscs.util.jaxb.Selector<TRoot, ModificatorType.Selector<TRoot, TParent>> content() {
            return ((this.content == null)?this.content = new com.kscs.util.jaxb.Selector<>(this._root, this, "content"):this.content);
        }

    }

}
