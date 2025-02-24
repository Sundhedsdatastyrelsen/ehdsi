
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0;

import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
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
 *       <sequence>
 *         <element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Created"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "created"
})
@XmlRootElement(name = "Timestamp")
public class Timestamp {

    @XmlElement(name = "Created", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar created;

    /**
     * Gets the value of the created property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreated(XMLGregorianCalendar value) {
        this.created = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final Timestamp.Builder<_B> _other) {
        _other.created = ((this.created == null)?null:((XMLGregorianCalendar) this.created.clone()));
    }

    public<_B >Timestamp.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new Timestamp.Builder<_B>(_parentBuilder, this, true);
    }

    public Timestamp.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static Timestamp.Builder<Void> builder() {
        return new Timestamp.Builder<>(null, null, false);
    }

    public static<_B >Timestamp.Builder<_B> copyOf(final Timestamp _other) {
        final Timestamp.Builder<_B> _newBuilder = new Timestamp.Builder<>(null, null, false);
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
    public<_B >void copyTo(final Timestamp.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
            _other.created = ((this.created == null)?null:((XMLGregorianCalendar) this.created.clone()));
        }
    }

    public<_B >Timestamp.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new Timestamp.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public Timestamp.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >Timestamp.Builder<_B> copyOf(final Timestamp _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final Timestamp.Builder<_B> _newBuilder = new Timestamp.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static Timestamp.Builder<Void> copyExcept(final Timestamp _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static Timestamp.Builder<Void> copyOnly(final Timestamp _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final Timestamp _storedValue;
        private XMLGregorianCalendar created;

        public Builder(final _B _parentBuilder, final Timestamp _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.created = ((_other.created == null)?null:((XMLGregorianCalendar) _other.created.clone()));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final Timestamp _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
                        this.created = ((_other.created == null)?null:((XMLGregorianCalendar) _other.created.clone()));
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

        protected<_P extends Timestamp >_P init(final _P _product) {
            _product.created = this.created;
            return _product;
        }

        /**
         * Sets the new value of "created" (any previous value will be replaced)
         * 
         * @param created
         *     New value of the "created" property.
         */
        public Timestamp.Builder<_B> withCreated(final XMLGregorianCalendar created) {
            this.created = created;
            return this;
        }

        @Override
        public Timestamp build() {
            if (_storedValue == null) {
                return this.init(new Timestamp());
            } else {
                return ((Timestamp) _storedValue);
            }
        }

        public Timestamp.Builder<_B> copyOf(final Timestamp _other) {
            _other.copyTo(this);
            return this;
        }

        public Timestamp.Builder<_B> copyOf(final Timestamp.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends Timestamp.Selector<Timestamp.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static Timestamp.Select _root() {
            return new Timestamp.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, Timestamp.Selector<TRoot, TParent>> created = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.created!= null) {
                products.put("created", this.created.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, Timestamp.Selector<TRoot, TParent>> created() {
            return ((this.created == null)?this.created = new com.kscs.util.jaxb.Selector<>(this._root, this, "created"):this.created);
        }

    }

}
