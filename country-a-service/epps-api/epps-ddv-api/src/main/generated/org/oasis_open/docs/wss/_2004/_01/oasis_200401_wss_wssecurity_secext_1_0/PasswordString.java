
package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * This type is used for password elements per Section 4.1.
 * 
 * <p>Java class for PasswordString complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PasswordString">
 *   <simpleContent>
 *     <extension base="<http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd>AttributedString">
 *       <attribute name="Type" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PasswordString")
public class PasswordString
    extends AttributedString
{

    @XmlAttribute(name = "Type")
    @XmlSchemaType(name = "anyURI")
    protected String type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final PasswordString.Builder<_B> _other) {
        super.copyTo(_other);
        _other.type = this.type;
    }

    @Override
    public<_B >PasswordString.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new PasswordString.Builder<_B>(_parentBuilder, this, true);
    }

    @Override
    public PasswordString.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static PasswordString.Builder<Void> builder() {
        return new PasswordString.Builder<>(null, null, false);
    }

    public static<_B >PasswordString.Builder<_B> copyOf(final AttributedString _other) {
        final PasswordString.Builder<_B> _newBuilder = new PasswordString.Builder<>(null, null, false);
        _other.copyTo(_newBuilder);
        return _newBuilder;
    }

    public static<_B >PasswordString.Builder<_B> copyOf(final PasswordString _other) {
        final PasswordString.Builder<_B> _newBuilder = new PasswordString.Builder<>(null, null, false);
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
    public<_B >void copyTo(final PasswordString.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        super.copyTo(_other, _propertyTree, _propertyTreeUse);
        final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
            _other.type = this.type;
        }
    }

    @Override
    public<_B >PasswordString.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new PasswordString.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    @Override
    public PasswordString.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >PasswordString.Builder<_B> copyOf(final AttributedString _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PasswordString.Builder<_B> _newBuilder = new PasswordString.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static<_B >PasswordString.Builder<_B> copyOf(final PasswordString _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PasswordString.Builder<_B> _newBuilder = new PasswordString.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static PasswordString.Builder<Void> copyExcept(final AttributedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static PasswordString.Builder<Void> copyExcept(final PasswordString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static PasswordString.Builder<Void> copyOnly(final AttributedString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static PasswordString.Builder<Void> copyOnly(final PasswordString _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >
        extends AttributedString.Builder<_B>
        implements Buildable
    {

        private String type;

        public Builder(final _B _parentBuilder, final PasswordString _other, final boolean _copy) {
            super(_parentBuilder, _other, _copy);
            if (_other!= null) {
                this.type = _other.type;
            }
        }

        public Builder(final _B _parentBuilder, final PasswordString _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            super(_parentBuilder, _other, _copy, _propertyTree, _propertyTreeUse);
            if (_other!= null) {
                final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
                if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
                    this.type = _other.type;
                }
            }
        }

        protected<_P extends PasswordString >_P init(final _P _product) {
            _product.type = this.type;
            return super.init(_product);
        }

        /**
         * Sets the new value of "type" (any previous value will be replaced)
         * 
         * @param type
         *     New value of the "type" property.
         */
        public PasswordString.Builder<_B> withType(final String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the new value of "value" (any previous value will be replaced)
         * 
         * @param value
         *     New value of the "value" property.
         */
        @Override
        public PasswordString.Builder<_B> withValue(final String value) {
            super.withValue(value);
            return this;
        }

        /**
         * Sets the new value of "id" (any previous value will be replaced)
         * 
         * @param id
         *     New value of the "id" property.
         */
        @Override
        public PasswordString.Builder<_B> withId(final String id) {
            super.withId(id);
            return this;
        }

        @Override
        public PasswordString build() {
            if (_storedValue == null) {
                return this.init(new PasswordString());
            } else {
                return ((PasswordString) _storedValue);
            }
        }

        public PasswordString.Builder<_B> copyOf(final PasswordString _other) {
            _other.copyTo(this);
            return this;
        }

        public PasswordString.Builder<_B> copyOf(final PasswordString.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends PasswordString.Selector<PasswordString.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static PasswordString.Select _root() {
            return new PasswordString.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends AttributedString.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, PasswordString.Selector<TRoot, TParent>> type = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.type!= null) {
                products.put("type", this.type.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, PasswordString.Selector<TRoot, TParent>> type() {
            return ((this.type == null)?this.type = new com.kscs.util.jaxb.Selector<>(this._root, this, "type"):this.type);
        }

    }

}
