
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimingListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TimingListType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Timing" type="{http://vaccinationsregister.dk/schemas/2013/12/01}TimingType" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimingListType", propOrder = {
    "timing"
})
public class TimingListType {

    @XmlElement(name = "Timing", required = true)
    protected List<TimingType> timing;

    /**
     * Gets the value of the timing property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the timing property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTiming().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimingType }
     * 
     * 
     * @return
     *     The value of the timing property.
     */
    public List<TimingType> getTiming() {
        if (timing == null) {
            timing = new ArrayList<>();
        }
        return this.timing;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final TimingListType.Builder<_B> _other) {
        if (this.timing == null) {
            _other.timing = null;
        } else {
            _other.timing = new ArrayList<>();
            for (TimingType _item: this.timing) {
                _other.timing.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >TimingListType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new TimingListType.Builder<_B>(_parentBuilder, this, true);
    }

    public TimingListType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static TimingListType.Builder<Void> builder() {
        return new TimingListType.Builder<>(null, null, false);
    }

    public static<_B >TimingListType.Builder<_B> copyOf(final TimingListType _other) {
        final TimingListType.Builder<_B> _newBuilder = new TimingListType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final TimingListType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree timingPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timing"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timingPropertyTree!= null):((timingPropertyTree == null)||(!timingPropertyTree.isLeaf())))) {
            if (this.timing == null) {
                _other.timing = null;
            } else {
                _other.timing = new ArrayList<>();
                for (TimingType _item: this.timing) {
                    _other.timing.add(((_item == null)?null:_item.newCopyBuilder(_other, timingPropertyTree, _propertyTreeUse)));
                }
            }
        }
    }

    public<_B >TimingListType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new TimingListType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public TimingListType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >TimingListType.Builder<_B> copyOf(final TimingListType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final TimingListType.Builder<_B> _newBuilder = new TimingListType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static TimingListType.Builder<Void> copyExcept(final TimingListType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static TimingListType.Builder<Void> copyOnly(final TimingListType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final TimingListType _storedValue;
        private List<TimingType.Builder<TimingListType.Builder<_B>>> timing;

        public Builder(final _B _parentBuilder, final TimingListType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.timing == null) {
                        this.timing = null;
                    } else {
                        this.timing = new ArrayList<>();
                        for (TimingType _item: _other.timing) {
                            this.timing.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final TimingListType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree timingPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("timing"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(timingPropertyTree!= null):((timingPropertyTree == null)||(!timingPropertyTree.isLeaf())))) {
                        if (_other.timing == null) {
                            this.timing = null;
                        } else {
                            this.timing = new ArrayList<>();
                            for (TimingType _item: _other.timing) {
                                this.timing.add(((_item == null)?null:_item.newCopyBuilder(this, timingPropertyTree, _propertyTreeUse)));
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

        protected<_P extends TimingListType >_P init(final _P _product) {
            if (this.timing!= null) {
                final List<TimingType> timing = new ArrayList<>(this.timing.size());
                for (TimingType.Builder<TimingListType.Builder<_B>> _item: this.timing) {
                    timing.add(_item.build());
                }
                _product.timing = timing;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "timing"
         * 
         * @param timing
         *     Items to add to the value of the "timing" property
         */
        public TimingListType.Builder<_B> addTiming(final Iterable<? extends TimingType> timing) {
            if (timing!= null) {
                if (this.timing == null) {
                    this.timing = new ArrayList<>();
                }
                for (TimingType _item: timing) {
                    this.timing.add(new TimingType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "timing" (any previous value will be replaced)
         * 
         * @param timing
         *     New value of the "timing" property.
         */
        public TimingListType.Builder<_B> withTiming(final Iterable<? extends TimingType> timing) {
            if (this.timing!= null) {
                this.timing.clear();
            }
            return addTiming(timing);
        }

        /**
         * Adds the given items to the value of "timing"
         * 
         * @param timing
         *     Items to add to the value of the "timing" property
         */
        public TimingListType.Builder<_B> addTiming(TimingType... timing) {
            addTiming(Arrays.asList(timing));
            return this;
        }

        /**
         * Sets the new value of "timing" (any previous value will be replaced)
         * 
         * @param timing
         *     New value of the "timing" property.
         */
        public TimingListType.Builder<_B> withTiming(TimingType... timing) {
            withTiming(Arrays.asList(timing));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "Timing" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.TimingType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "Timing" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.TimingType.Builder#end()} to
         *     return to the current builder.
         */
        public TimingType.Builder<? extends TimingListType.Builder<_B>> addTiming() {
            if (this.timing == null) {
                this.timing = new ArrayList<>();
            }
            final TimingType.Builder<TimingListType.Builder<_B>> timing_Builder = new TimingType.Builder<>(this, null, false);
            this.timing.add(timing_Builder);
            return timing_Builder;
        }

        @Override
        public TimingListType build() {
            if (_storedValue == null) {
                return this.init(new TimingListType());
            } else {
                return ((TimingListType) _storedValue);
            }
        }

        public TimingListType.Builder<_B> copyOf(final TimingListType _other) {
            _other.copyTo(this);
            return this;
        }

        public TimingListType.Builder<_B> copyOf(final TimingListType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends TimingListType.Selector<TimingListType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static TimingListType.Select _root() {
            return new TimingListType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private TimingType.Selector<TRoot, TimingListType.Selector<TRoot, TParent>> timing = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.timing!= null) {
                products.put("timing", this.timing.init());
            }
            return products;
        }

        public TimingType.Selector<TRoot, TimingListType.Selector<TRoot, TParent>> timing() {
            return ((this.timing == null)?this.timing = new TimingType.Selector<>(this._root, this, "timing"):this.timing);
        }

    }

}
