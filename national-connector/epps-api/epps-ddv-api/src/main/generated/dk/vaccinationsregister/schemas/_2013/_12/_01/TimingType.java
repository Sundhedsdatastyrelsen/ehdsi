
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TimingType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="SystemName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SystemNameType"/>
 *         <element name="ServiceName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ServiceNameType"/>
 *         <element name="TimeInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimingType", propOrder = {
    "systemName",
    "serviceName",
    "timeInMilliseconds"
})
public class TimingType {

    @XmlElement(name = "SystemName", required = true)
    protected String systemName;
    @XmlElement(name = "ServiceName", required = true)
    protected String serviceName;
    @XmlElement(name = "TimeInMilliseconds")
    protected int timeInMilliseconds;

    /**
     * Gets the value of the systemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * Sets the value of the systemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemName(String value) {
        this.systemName = value;
    }

    /**
     * Gets the value of the serviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the timeInMilliseconds property.
     * 
     */
    public int getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    /**
     * Sets the value of the timeInMilliseconds property.
     * 
     */
    public void setTimeInMilliseconds(int value) {
        this.timeInMilliseconds = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final TimingType.Builder<_B> _other) {
        _other.systemName = this.systemName;
        _other.serviceName = this.serviceName;
        _other.timeInMilliseconds = this.timeInMilliseconds;
    }

    public<_B >TimingType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new TimingType.Builder<_B>(_parentBuilder, this, true);
    }

    public TimingType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static TimingType.Builder<Void> builder() {
        return new TimingType.Builder<>(null, null, false);
    }

    public static<_B >TimingType.Builder<_B> copyOf(final TimingType _other) {
        final TimingType.Builder<_B> _newBuilder = new TimingType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final TimingType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree systemNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemNamePropertyTree!= null):((systemNamePropertyTree == null)||(!systemNamePropertyTree.isLeaf())))) {
            _other.systemName = this.systemName;
        }
        final PropertyTree serviceNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("serviceName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(serviceNamePropertyTree!= null):((serviceNamePropertyTree == null)||(!serviceNamePropertyTree.isLeaf())))) {
            _other.serviceName = this.serviceName;
        }
        final PropertyTree timeInMillisecondsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timeInMilliseconds"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timeInMillisecondsPropertyTree!= null):((timeInMillisecondsPropertyTree == null)||(!timeInMillisecondsPropertyTree.isLeaf())))) {
            _other.timeInMilliseconds = this.timeInMilliseconds;
        }
    }

    public<_B >TimingType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new TimingType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public TimingType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >TimingType.Builder<_B> copyOf(final TimingType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final TimingType.Builder<_B> _newBuilder = new TimingType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static TimingType.Builder<Void> copyExcept(final TimingType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static TimingType.Builder<Void> copyOnly(final TimingType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final TimingType _storedValue;
        private String systemName;
        private String serviceName;
        private int timeInMilliseconds;

        public Builder(final _B _parentBuilder, final TimingType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.systemName = _other.systemName;
                    this.serviceName = _other.serviceName;
                    this.timeInMilliseconds = _other.timeInMilliseconds;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final TimingType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree systemNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemNamePropertyTree!= null):((systemNamePropertyTree == null)||(!systemNamePropertyTree.isLeaf())))) {
                        this.systemName = _other.systemName;
                    }
                    final PropertyTree serviceNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("serviceName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(serviceNamePropertyTree!= null):((serviceNamePropertyTree == null)||(!serviceNamePropertyTree.isLeaf())))) {
                        this.serviceName = _other.serviceName;
                    }
                    final PropertyTree timeInMillisecondsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timeInMilliseconds"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timeInMillisecondsPropertyTree!= null):((timeInMillisecondsPropertyTree == null)||(!timeInMillisecondsPropertyTree.isLeaf())))) {
                        this.timeInMilliseconds = _other.timeInMilliseconds;
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

        protected<_P extends TimingType >_P init(final _P _product) {
            _product.systemName = this.systemName;
            _product.serviceName = this.serviceName;
            _product.timeInMilliseconds = this.timeInMilliseconds;
            return _product;
        }

        /**
         * Sets the new value of "systemName" (any previous value will be replaced)
         * 
         * @param systemName
         *     New value of the "systemName" property.
         */
        public TimingType.Builder<_B> withSystemName(final String systemName) {
            this.systemName = systemName;
            return this;
        }

        /**
         * Sets the new value of "serviceName" (any previous value will be replaced)
         * 
         * @param serviceName
         *     New value of the "serviceName" property.
         */
        public TimingType.Builder<_B> withServiceName(final String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        /**
         * Sets the new value of "timeInMilliseconds" (any previous value will be replaced)
         * 
         * @param timeInMilliseconds
         *     New value of the "timeInMilliseconds" property.
         */
        public TimingType.Builder<_B> withTimeInMilliseconds(final int timeInMilliseconds) {
            this.timeInMilliseconds = timeInMilliseconds;
            return this;
        }

        @Override
        public TimingType build() {
            if (_storedValue == null) {
                return this.init(new TimingType());
            } else {
                return ((TimingType) _storedValue);
            }
        }

        public TimingType.Builder<_B> copyOf(final TimingType _other) {
            _other.copyTo(this);
            return this;
        }

        public TimingType.Builder<_B> copyOf(final TimingType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends TimingType.Selector<TimingType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static TimingType.Select _root() {
            return new TimingType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, TimingType.Selector<TRoot, TParent>> systemName = null;
        private com.kscs.util.jaxb.Selector<TRoot, TimingType.Selector<TRoot, TParent>> serviceName = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.systemName!= null) {
                products.put("systemName", this.systemName.init());
            }
            if (this.serviceName!= null) {
                products.put("serviceName", this.serviceName.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, TimingType.Selector<TRoot, TParent>> systemName() {
            return ((this.systemName == null)?this.systemName = new com.kscs.util.jaxb.Selector<>(this._root, this, "systemName"):this.systemName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, TimingType.Selector<TRoot, TParent>> serviceName() {
            return ((this.serviceName == null)?this.serviceName = new com.kscs.util.jaxb.Selector<>(this._root, this, "serviceName"):this.serviceName);
        }

    }

}
