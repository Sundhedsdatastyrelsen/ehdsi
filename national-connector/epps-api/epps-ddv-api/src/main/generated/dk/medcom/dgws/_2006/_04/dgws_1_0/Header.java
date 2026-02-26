
package dk.medcom.dgws._2006._04.dgws_1_0;

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
import jakarta.xml.bind.annotation.XmlRootElement;
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
 *         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}SecurityLevel" minOccurs="0"/>
 *         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}TimeOut" minOccurs="0"/>
 *         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}Linking"/>
 *         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}FlowStatus" minOccurs="0"/>
 *         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}Priority" minOccurs="0"/>
 *         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}RequireNonRepudiationReceipt" minOccurs="0"/>
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
@XmlType(name = "", propOrder = {
    "securityLevel",
    "timeOut",
    "linking",
    "flowStatus",
    "priority",
    "requireNonRepudiationReceipt"
})
@XmlRootElement(name = "Header")
public class Header {

    @XmlElement(name = "SecurityLevel")
    protected Integer securityLevel;
    @XmlElement(name = "TimeOut")
    protected String timeOut;
    @XmlElement(name = "Linking", required = true)
    protected Linking linking;
    @XmlElement(name = "FlowStatus")
    protected String flowStatus;
    @XmlElement(name = "Priority")
    protected String priority;
    @XmlElement(name = "RequireNonRepudiationReceipt")
    protected String requireNonRepudiationReceipt;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the securityLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSecurityLevel() {
        return securityLevel;
    }

    /**
     * Sets the value of the securityLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSecurityLevel(Integer value) {
        this.securityLevel = value;
    }

    /**
     * Gets the value of the timeOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOut() {
        return timeOut;
    }

    /**
     * Sets the value of the timeOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOut(String value) {
        this.timeOut = value;
    }

    /**
     * Gets the value of the linking property.
     * 
     * @return
     *     possible object is
     *     {@link Linking }
     *     
     */
    public Linking getLinking() {
        return linking;
    }

    /**
     * Sets the value of the linking property.
     * 
     * @param value
     *     allowed object is
     *     {@link Linking }
     *     
     */
    public void setLinking(Linking value) {
        this.linking = value;
    }

    /**
     * Gets the value of the flowStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowStatus() {
        return flowStatus;
    }

    /**
     * Sets the value of the flowStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowStatus(String value) {
        this.flowStatus = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriority(String value) {
        this.priority = value;
    }

    /**
     * Gets the value of the requireNonRepudiationReceipt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequireNonRepudiationReceipt() {
        return requireNonRepudiationReceipt;
    }

    /**
     * Sets the value of the requireNonRepudiationReceipt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequireNonRepudiationReceipt(String value) {
        this.requireNonRepudiationReceipt = value;
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
    public<_B >void copyTo(final Header.Builder<_B> _other) {
        _other.securityLevel = this.securityLevel;
        _other.timeOut = this.timeOut;
        _other.linking = ((this.linking == null)?null:this.linking.newCopyBuilder(_other));
        _other.flowStatus = this.flowStatus;
        _other.priority = this.priority;
        _other.requireNonRepudiationReceipt = this.requireNonRepudiationReceipt;
    }

    public<_B >Header.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new Header.Builder<_B>(_parentBuilder, this, true);
    }

    public Header.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static Header.Builder<Void> builder() {
        return new Header.Builder<>(null, null, false);
    }

    public static<_B >Header.Builder<_B> copyOf(final Header _other) {
        final Header.Builder<_B> _newBuilder = new Header.Builder<>(null, null, false);
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
    public<_B >void copyTo(final Header.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree securityLevelPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("securityLevel"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(securityLevelPropertyTree!= null):((securityLevelPropertyTree == null)||(!securityLevelPropertyTree.isLeaf())))) {
            _other.securityLevel = this.securityLevel;
        }
        final PropertyTree timeOutPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timeOut"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timeOutPropertyTree!= null):((timeOutPropertyTree == null)||(!timeOutPropertyTree.isLeaf())))) {
            _other.timeOut = this.timeOut;
        }
        final PropertyTree linkingPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("linking"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(linkingPropertyTree!= null):((linkingPropertyTree == null)||(!linkingPropertyTree.isLeaf())))) {
            _other.linking = ((this.linking == null)?null:this.linking.newCopyBuilder(_other, linkingPropertyTree, _propertyTreeUse));
        }
        final PropertyTree flowStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("flowStatus"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(flowStatusPropertyTree!= null):((flowStatusPropertyTree == null)||(!flowStatusPropertyTree.isLeaf())))) {
            _other.flowStatus = this.flowStatus;
        }
        final PropertyTree priorityPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("priority"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(priorityPropertyTree!= null):((priorityPropertyTree == null)||(!priorityPropertyTree.isLeaf())))) {
            _other.priority = this.priority;
        }
        final PropertyTree requireNonRepudiationReceiptPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("requireNonRepudiationReceipt"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(requireNonRepudiationReceiptPropertyTree!= null):((requireNonRepudiationReceiptPropertyTree == null)||(!requireNonRepudiationReceiptPropertyTree.isLeaf())))) {
            _other.requireNonRepudiationReceipt = this.requireNonRepudiationReceipt;
        }
    }

    public<_B >Header.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new Header.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public Header.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >Header.Builder<_B> copyOf(final Header _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final Header.Builder<_B> _newBuilder = new Header.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static Header.Builder<Void> copyExcept(final Header _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static Header.Builder<Void> copyOnly(final Header _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final Header _storedValue;
        private Integer securityLevel;
        private String timeOut;
        private Linking.Builder<Header.Builder<_B>> linking;
        private String flowStatus;
        private String priority;
        private String requireNonRepudiationReceipt;

        public Builder(final _B _parentBuilder, final Header _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.securityLevel = _other.securityLevel;
                    this.timeOut = _other.timeOut;
                    this.linking = ((_other.linking == null)?null:_other.linking.newCopyBuilder(this));
                    this.flowStatus = _other.flowStatus;
                    this.priority = _other.priority;
                    this.requireNonRepudiationReceipt = _other.requireNonRepudiationReceipt;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final Header _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree securityLevelPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("securityLevel"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(securityLevelPropertyTree!= null):((securityLevelPropertyTree == null)||(!securityLevelPropertyTree.isLeaf())))) {
                        this.securityLevel = _other.securityLevel;
                    }
                    final PropertyTree timeOutPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timeOut"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timeOutPropertyTree!= null):((timeOutPropertyTree == null)||(!timeOutPropertyTree.isLeaf())))) {
                        this.timeOut = _other.timeOut;
                    }
                    final PropertyTree linkingPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("linking"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(linkingPropertyTree!= null):((linkingPropertyTree == null)||(!linkingPropertyTree.isLeaf())))) {
                        this.linking = ((_other.linking == null)?null:_other.linking.newCopyBuilder(this, linkingPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree flowStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("flowStatus"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(flowStatusPropertyTree!= null):((flowStatusPropertyTree == null)||(!flowStatusPropertyTree.isLeaf())))) {
                        this.flowStatus = _other.flowStatus;
                    }
                    final PropertyTree priorityPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("priority"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(priorityPropertyTree!= null):((priorityPropertyTree == null)||(!priorityPropertyTree.isLeaf())))) {
                        this.priority = _other.priority;
                    }
                    final PropertyTree requireNonRepudiationReceiptPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("requireNonRepudiationReceipt"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(requireNonRepudiationReceiptPropertyTree!= null):((requireNonRepudiationReceiptPropertyTree == null)||(!requireNonRepudiationReceiptPropertyTree.isLeaf())))) {
                        this.requireNonRepudiationReceipt = _other.requireNonRepudiationReceipt;
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

        protected<_P extends Header >_P init(final _P _product) {
            _product.securityLevel = this.securityLevel;
            _product.timeOut = this.timeOut;
            _product.linking = ((this.linking == null)?null:this.linking.build());
            _product.flowStatus = this.flowStatus;
            _product.priority = this.priority;
            _product.requireNonRepudiationReceipt = this.requireNonRepudiationReceipt;
            return _product;
        }

        /**
         * Sets the new value of "securityLevel" (any previous value will be replaced)
         * 
         * @param securityLevel
         *     New value of the "securityLevel" property.
         */
        public Header.Builder<_B> withSecurityLevel(final Integer securityLevel) {
            this.securityLevel = securityLevel;
            return this;
        }

        /**
         * Sets the new value of "timeOut" (any previous value will be replaced)
         * 
         * @param timeOut
         *     New value of the "timeOut" property.
         */
        public Header.Builder<_B> withTimeOut(final String timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        /**
         * Sets the new value of "linking" (any previous value will be replaced)
         * 
         * @param linking
         *     New value of the "linking" property.
         */
        public Header.Builder<_B> withLinking(final Linking linking) {
            this.linking = ((linking == null)?null:new Linking.Builder<>(this, linking, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "linking" property.
         * Use {@link dk.medcom.dgws._2006._04.dgws_1_0.Linking.Builder#end()} to return to
         * the current builder.
         * 
         * @return
         *     A new builder to build the value of the "linking" property.
         *     Use {@link dk.medcom.dgws._2006._04.dgws_1_0.Linking.Builder#end()} to return to
         *     the current builder.
         */
        public Linking.Builder<? extends Header.Builder<_B>> withLinking() {
            if (this.linking!= null) {
                return this.linking;
            }
            return this.linking = new Linking.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "flowStatus" (any previous value will be replaced)
         * 
         * @param flowStatus
         *     New value of the "flowStatus" property.
         */
        public Header.Builder<_B> withFlowStatus(final String flowStatus) {
            this.flowStatus = flowStatus;
            return this;
        }

        /**
         * Sets the new value of "priority" (any previous value will be replaced)
         * 
         * @param priority
         *     New value of the "priority" property.
         */
        public Header.Builder<_B> withPriority(final String priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Sets the new value of "requireNonRepudiationReceipt" (any previous value will be
         * replaced)
         * 
         * @param requireNonRepudiationReceipt
         *     New value of the "requireNonRepudiationReceipt" property.
         */
        public Header.Builder<_B> withRequireNonRepudiationReceipt(final String requireNonRepudiationReceipt) {
            this.requireNonRepudiationReceipt = requireNonRepudiationReceipt;
            return this;
        }

        @Override
        public Header build() {
            if (_storedValue == null) {
                return this.init(new Header());
            } else {
                return ((Header) _storedValue);
            }
        }

        public Header.Builder<_B> copyOf(final Header _other) {
            _other.copyTo(this);
            return this;
        }

        public Header.Builder<_B> copyOf(final Header.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends Header.Selector<Header.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static Header.Select _root() {
            return new Header.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> securityLevel = null;
        private com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> timeOut = null;
        private Linking.Selector<TRoot, Header.Selector<TRoot, TParent>> linking = null;
        private com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> flowStatus = null;
        private com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> priority = null;
        private com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> requireNonRepudiationReceipt = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.securityLevel!= null) {
                products.put("securityLevel", this.securityLevel.init());
            }
            if (this.timeOut!= null) {
                products.put("timeOut", this.timeOut.init());
            }
            if (this.linking!= null) {
                products.put("linking", this.linking.init());
            }
            if (this.flowStatus!= null) {
                products.put("flowStatus", this.flowStatus.init());
            }
            if (this.priority!= null) {
                products.put("priority", this.priority.init());
            }
            if (this.requireNonRepudiationReceipt!= null) {
                products.put("requireNonRepudiationReceipt", this.requireNonRepudiationReceipt.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> securityLevel() {
            return ((this.securityLevel == null)?this.securityLevel = new com.kscs.util.jaxb.Selector<>(this._root, this, "securityLevel"):this.securityLevel);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> timeOut() {
            return ((this.timeOut == null)?this.timeOut = new com.kscs.util.jaxb.Selector<>(this._root, this, "timeOut"):this.timeOut);
        }

        public Linking.Selector<TRoot, Header.Selector<TRoot, TParent>> linking() {
            return ((this.linking == null)?this.linking = new Linking.Selector<>(this._root, this, "linking"):this.linking);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> flowStatus() {
            return ((this.flowStatus == null)?this.flowStatus = new com.kscs.util.jaxb.Selector<>(this._root, this, "flowStatus"):this.flowStatus);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> priority() {
            return ((this.priority == null)?this.priority = new com.kscs.util.jaxb.Selector<>(this._root, this, "priority"):this.priority);
        }

        public com.kscs.util.jaxb.Selector<TRoot, Header.Selector<TRoot, TParent>> requireNonRepudiationReceipt() {
            return ((this.requireNonRepudiationReceipt == null)?this.requireNonRepudiationReceipt = new com.kscs.util.jaxb.Selector<>(this._root, this, "requireNonRepudiationReceipt"):this.requireNonRepudiationReceipt);
        }

    }

}
