
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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProblemActionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ProblemActionType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2005/08/addressing}Action" minOccurs="0"/>
 *         <element name="SoapAction" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *       </sequence>
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProblemActionType", propOrder = {
    "action",
    "soapAction"
})
public class ProblemActionType {

    @XmlElement(name = "Action")
    protected AttributedURIType action;
    @XmlElement(name = "SoapAction")
    @XmlSchemaType(name = "anyURI")
    protected String soapAction;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link AttributedURIType }
     *     
     */
    public AttributedURIType getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributedURIType }
     *     
     */
    public void setAction(AttributedURIType value) {
        this.action = value;
    }

    /**
     * Gets the value of the soapAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoapAction() {
        return soapAction;
    }

    /**
     * Sets the value of the soapAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoapAction(String value) {
        this.soapAction = value;
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
    public<_B >void copyTo(final ProblemActionType.Builder<_B> _other) {
        _other.action = ((this.action == null)?null:this.action.newCopyBuilder(_other));
        _other.soapAction = this.soapAction;
    }

    public<_B >ProblemActionType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new ProblemActionType.Builder<_B>(_parentBuilder, this, true);
    }

    public ProblemActionType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static ProblemActionType.Builder<Void> builder() {
        return new ProblemActionType.Builder<>(null, null, false);
    }

    public static<_B >ProblemActionType.Builder<_B> copyOf(final ProblemActionType _other) {
        final ProblemActionType.Builder<_B> _newBuilder = new ProblemActionType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final ProblemActionType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree actionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("action"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(actionPropertyTree!= null):((actionPropertyTree == null)||(!actionPropertyTree.isLeaf())))) {
            _other.action = ((this.action == null)?null:this.action.newCopyBuilder(_other, actionPropertyTree, _propertyTreeUse));
        }
        final PropertyTree soapActionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("soapAction"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(soapActionPropertyTree!= null):((soapActionPropertyTree == null)||(!soapActionPropertyTree.isLeaf())))) {
            _other.soapAction = this.soapAction;
        }
    }

    public<_B >ProblemActionType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new ProblemActionType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public ProblemActionType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >ProblemActionType.Builder<_B> copyOf(final ProblemActionType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final ProblemActionType.Builder<_B> _newBuilder = new ProblemActionType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static ProblemActionType.Builder<Void> copyExcept(final ProblemActionType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static ProblemActionType.Builder<Void> copyOnly(final ProblemActionType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final ProblemActionType _storedValue;
        private AttributedURIType.Builder<ProblemActionType.Builder<_B>> action;
        private String soapAction;

        public Builder(final _B _parentBuilder, final ProblemActionType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.action = ((_other.action == null)?null:_other.action.newCopyBuilder(this));
                    this.soapAction = _other.soapAction;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final ProblemActionType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree actionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("action"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(actionPropertyTree!= null):((actionPropertyTree == null)||(!actionPropertyTree.isLeaf())))) {
                        this.action = ((_other.action == null)?null:_other.action.newCopyBuilder(this, actionPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree soapActionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("soapAction"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(soapActionPropertyTree!= null):((soapActionPropertyTree == null)||(!soapActionPropertyTree.isLeaf())))) {
                        this.soapAction = _other.soapAction;
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

        protected<_P extends ProblemActionType >_P init(final _P _product) {
            _product.action = ((this.action == null)?null:this.action.build());
            _product.soapAction = this.soapAction;
            return _product;
        }

        /**
         * Sets the new value of "action" (any previous value will be replaced)
         * 
         * @param action
         *     New value of the "action" property.
         */
        public ProblemActionType.Builder<_B> withAction(final AttributedURIType action) {
            this.action = ((action == null)?null:new AttributedURIType.Builder<>(this, action, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the "action"
         * property.
         * Use {@link org.w3._2005._08.addressing.AttributedURIType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "action" property.
         *     Use {@link org.w3._2005._08.addressing.AttributedURIType.Builder#end()} to
         *     return to the current builder.
         */
        public AttributedURIType.Builder<? extends ProblemActionType.Builder<_B>> withAction() {
            if (this.action!= null) {
                return this.action;
            }
            return this.action = new AttributedURIType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "soapAction" (any previous value will be replaced)
         * 
         * @param soapAction
         *     New value of the "soapAction" property.
         */
        public ProblemActionType.Builder<_B> withSoapAction(final String soapAction) {
            this.soapAction = soapAction;
            return this;
        }

        @Override
        public ProblemActionType build() {
            if (_storedValue == null) {
                return this.init(new ProblemActionType());
            } else {
                return ((ProblemActionType) _storedValue);
            }
        }

        public ProblemActionType.Builder<_B> copyOf(final ProblemActionType _other) {
            _other.copyTo(this);
            return this;
        }

        public ProblemActionType.Builder<_B> copyOf(final ProblemActionType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends ProblemActionType.Selector<ProblemActionType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static ProblemActionType.Select _root() {
            return new ProblemActionType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private AttributedURIType.Selector<TRoot, ProblemActionType.Selector<TRoot, TParent>> action = null;
        private com.kscs.util.jaxb.Selector<TRoot, ProblemActionType.Selector<TRoot, TParent>> soapAction = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.action!= null) {
                products.put("action", this.action.init());
            }
            if (this.soapAction!= null) {
                products.put("soapAction", this.soapAction.init());
            }
            return products;
        }

        public AttributedURIType.Selector<TRoot, ProblemActionType.Selector<TRoot, TParent>> action() {
            return ((this.action == null)?this.action = new AttributedURIType.Selector<>(this._root, this, "action"):this.action);
        }

        public com.kscs.util.jaxb.Selector<TRoot, ProblemActionType.Selector<TRoot, TParent>> soapAction() {
            return ((this.soapAction == null)?this.soapAction = new com.kscs.util.jaxb.Selector<>(this._root, this, "soapAction"):this.soapAction);
        }

    }

}
