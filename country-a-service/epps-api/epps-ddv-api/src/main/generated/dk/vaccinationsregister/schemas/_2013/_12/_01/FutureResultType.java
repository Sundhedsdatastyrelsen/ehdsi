
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
 * <p>Java class for FutureResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="FutureResultType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="JobId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FutureResultType", propOrder = {
    "jobId"
})
public class FutureResultType {

    @XmlElement(name = "JobId", required = true)
    protected String jobId;

    /**
     * Gets the value of the jobId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Sets the value of the jobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobId(String value) {
        this.jobId = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final FutureResultType.Builder<_B> _other) {
        _other.jobId = this.jobId;
    }

    public<_B >FutureResultType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new FutureResultType.Builder<_B>(_parentBuilder, this, true);
    }

    public FutureResultType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static FutureResultType.Builder<Void> builder() {
        return new FutureResultType.Builder<>(null, null, false);
    }

    public static<_B >FutureResultType.Builder<_B> copyOf(final FutureResultType _other) {
        final FutureResultType.Builder<_B> _newBuilder = new FutureResultType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final FutureResultType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree jobIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("jobId"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(jobIdPropertyTree!= null):((jobIdPropertyTree == null)||(!jobIdPropertyTree.isLeaf())))) {
            _other.jobId = this.jobId;
        }
    }

    public<_B >FutureResultType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new FutureResultType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public FutureResultType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >FutureResultType.Builder<_B> copyOf(final FutureResultType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final FutureResultType.Builder<_B> _newBuilder = new FutureResultType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static FutureResultType.Builder<Void> copyExcept(final FutureResultType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static FutureResultType.Builder<Void> copyOnly(final FutureResultType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final FutureResultType _storedValue;
        private String jobId;

        public Builder(final _B _parentBuilder, final FutureResultType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.jobId = _other.jobId;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final FutureResultType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree jobIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("jobId"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(jobIdPropertyTree!= null):((jobIdPropertyTree == null)||(!jobIdPropertyTree.isLeaf())))) {
                        this.jobId = _other.jobId;
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

        protected<_P extends FutureResultType >_P init(final _P _product) {
            _product.jobId = this.jobId;
            return _product;
        }

        /**
         * Sets the new value of "jobId" (any previous value will be replaced)
         * 
         * @param jobId
         *     New value of the "jobId" property.
         */
        public FutureResultType.Builder<_B> withJobId(final String jobId) {
            this.jobId = jobId;
            return this;
        }

        @Override
        public FutureResultType build() {
            if (_storedValue == null) {
                return this.init(new FutureResultType());
            } else {
                return ((FutureResultType) _storedValue);
            }
        }

        public FutureResultType.Builder<_B> copyOf(final FutureResultType _other) {
            _other.copyTo(this);
            return this;
        }

        public FutureResultType.Builder<_B> copyOf(final FutureResultType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends FutureResultType.Selector<FutureResultType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static FutureResultType.Select _root() {
            return new FutureResultType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, FutureResultType.Selector<TRoot, TParent>> jobId = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.jobId!= null) {
                products.put("jobId", this.jobId.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, FutureResultType.Selector<TRoot, TParent>> jobId() {
            return ((this.jobId == null)?this.jobId = new com.kscs.util.jaxb.Selector<>(this._root, this, "jobId"):this.jobId);
        }

    }

}
