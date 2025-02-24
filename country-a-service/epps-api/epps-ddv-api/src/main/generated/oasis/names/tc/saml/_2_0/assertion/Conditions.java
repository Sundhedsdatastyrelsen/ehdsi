
package oasis.names.tc.saml._2_0.assertion;

import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="NotBefore" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       <attribute name="NotOnOrAfter" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Conditions")
public class Conditions {

    @XmlAttribute(name = "NotBefore", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notBefore;
    @XmlAttribute(name = "NotOnOrAfter", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notOnOrAfter;

    /**
     * Gets the value of the notBefore property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotBefore() {
        return notBefore;
    }

    /**
     * Sets the value of the notBefore property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotBefore(XMLGregorianCalendar value) {
        this.notBefore = value;
    }

    /**
     * Gets the value of the notOnOrAfter property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotOnOrAfter() {
        return notOnOrAfter;
    }

    /**
     * Sets the value of the notOnOrAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotOnOrAfter(XMLGregorianCalendar value) {
        this.notOnOrAfter = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final Conditions.Builder<_B> _other) {
        _other.notBefore = ((this.notBefore == null)?null:((XMLGregorianCalendar) this.notBefore.clone()));
        _other.notOnOrAfter = ((this.notOnOrAfter == null)?null:((XMLGregorianCalendar) this.notOnOrAfter.clone()));
    }

    public<_B >Conditions.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new Conditions.Builder<_B>(_parentBuilder, this, true);
    }

    public Conditions.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static Conditions.Builder<Void> builder() {
        return new Conditions.Builder<>(null, null, false);
    }

    public static<_B >Conditions.Builder<_B> copyOf(final Conditions _other) {
        final Conditions.Builder<_B> _newBuilder = new Conditions.Builder<>(null, null, false);
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
    public<_B >void copyTo(final Conditions.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree notBeforePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("notBefore"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(notBeforePropertyTree!= null):((notBeforePropertyTree == null)||(!notBeforePropertyTree.isLeaf())))) {
            _other.notBefore = ((this.notBefore == null)?null:((XMLGregorianCalendar) this.notBefore.clone()));
        }
        final PropertyTree notOnOrAfterPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("notOnOrAfter"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(notOnOrAfterPropertyTree!= null):((notOnOrAfterPropertyTree == null)||(!notOnOrAfterPropertyTree.isLeaf())))) {
            _other.notOnOrAfter = ((this.notOnOrAfter == null)?null:((XMLGregorianCalendar) this.notOnOrAfter.clone()));
        }
    }

    public<_B >Conditions.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new Conditions.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public Conditions.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >Conditions.Builder<_B> copyOf(final Conditions _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final Conditions.Builder<_B> _newBuilder = new Conditions.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static Conditions.Builder<Void> copyExcept(final Conditions _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static Conditions.Builder<Void> copyOnly(final Conditions _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final Conditions _storedValue;
        private XMLGregorianCalendar notBefore;
        private XMLGregorianCalendar notOnOrAfter;

        public Builder(final _B _parentBuilder, final Conditions _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.notBefore = ((_other.notBefore == null)?null:((XMLGregorianCalendar) _other.notBefore.clone()));
                    this.notOnOrAfter = ((_other.notOnOrAfter == null)?null:((XMLGregorianCalendar) _other.notOnOrAfter.clone()));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final Conditions _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree notBeforePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("notBefore"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(notBeforePropertyTree!= null):((notBeforePropertyTree == null)||(!notBeforePropertyTree.isLeaf())))) {
                        this.notBefore = ((_other.notBefore == null)?null:((XMLGregorianCalendar) _other.notBefore.clone()));
                    }
                    final PropertyTree notOnOrAfterPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("notOnOrAfter"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(notOnOrAfterPropertyTree!= null):((notOnOrAfterPropertyTree == null)||(!notOnOrAfterPropertyTree.isLeaf())))) {
                        this.notOnOrAfter = ((_other.notOnOrAfter == null)?null:((XMLGregorianCalendar) _other.notOnOrAfter.clone()));
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

        protected<_P extends Conditions >_P init(final _P _product) {
            _product.notBefore = this.notBefore;
            _product.notOnOrAfter = this.notOnOrAfter;
            return _product;
        }

        /**
         * Sets the new value of "notBefore" (any previous value will be replaced)
         * 
         * @param notBefore
         *     New value of the "notBefore" property.
         */
        public Conditions.Builder<_B> withNotBefore(final XMLGregorianCalendar notBefore) {
            this.notBefore = notBefore;
            return this;
        }

        /**
         * Sets the new value of "notOnOrAfter" (any previous value will be replaced)
         * 
         * @param notOnOrAfter
         *     New value of the "notOnOrAfter" property.
         */
        public Conditions.Builder<_B> withNotOnOrAfter(final XMLGregorianCalendar notOnOrAfter) {
            this.notOnOrAfter = notOnOrAfter;
            return this;
        }

        @Override
        public Conditions build() {
            if (_storedValue == null) {
                return this.init(new Conditions());
            } else {
                return ((Conditions) _storedValue);
            }
        }

        public Conditions.Builder<_B> copyOf(final Conditions _other) {
            _other.copyTo(this);
            return this;
        }

        public Conditions.Builder<_B> copyOf(final Conditions.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends Conditions.Selector<Conditions.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static Conditions.Select _root() {
            return new Conditions.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, Conditions.Selector<TRoot, TParent>> notBefore = null;
        private com.kscs.util.jaxb.Selector<TRoot, Conditions.Selector<TRoot, TParent>> notOnOrAfter = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.notBefore!= null) {
                products.put("notBefore", this.notBefore.init());
            }
            if (this.notOnOrAfter!= null) {
                products.put("notOnOrAfter", this.notOnOrAfter.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, Conditions.Selector<TRoot, TParent>> notBefore() {
            return ((this.notBefore == null)?this.notBefore = new com.kscs.util.jaxb.Selector<>(this._root, this, "notBefore"):this.notBefore);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Conditions.Selector<TRoot, TParent>> notOnOrAfter() {
            return ((this.notOnOrAfter == null)?this.notOnOrAfter = new com.kscs.util.jaxb.Selector<>(this._root, this, "notOnOrAfter"):this.notOnOrAfter);
        }

    }

}
