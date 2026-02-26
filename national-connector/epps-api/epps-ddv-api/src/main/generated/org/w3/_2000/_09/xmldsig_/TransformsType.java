
package org.w3._2000._09.xmldsig_;

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
 * <p>Java class for TransformsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TransformsType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://www.w3.org/2000/09/xmldsig#}Transform" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransformsType", propOrder = {
    "transform"
})
public class TransformsType {

    @XmlElement(name = "Transform", required = true)
    protected List<TransformType> transform;

    /**
     * Gets the value of the transform property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the transform property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransform().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransformType }
     * 
     * 
     * @return
     *     The value of the transform property.
     */
    public List<TransformType> getTransform() {
        if (transform == null) {
            transform = new ArrayList<>();
        }
        return this.transform;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final TransformsType.Builder<_B> _other) {
        if (this.transform == null) {
            _other.transform = null;
        } else {
            _other.transform = new ArrayList<>();
            for (TransformType _item: this.transform) {
                _other.transform.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >TransformsType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new TransformsType.Builder<_B>(_parentBuilder, this, true);
    }

    public TransformsType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static TransformsType.Builder<Void> builder() {
        return new TransformsType.Builder<>(null, null, false);
    }

    public static<_B >TransformsType.Builder<_B> copyOf(final TransformsType _other) {
        final TransformsType.Builder<_B> _newBuilder = new TransformsType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final TransformsType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree transformPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transform"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformPropertyTree!= null):((transformPropertyTree == null)||(!transformPropertyTree.isLeaf())))) {
            if (this.transform == null) {
                _other.transform = null;
            } else {
                _other.transform = new ArrayList<>();
                for (TransformType _item: this.transform) {
                    _other.transform.add(((_item == null)?null:_item.newCopyBuilder(_other, transformPropertyTree, _propertyTreeUse)));
                }
            }
        }
    }

    public<_B >TransformsType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new TransformsType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public TransformsType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >TransformsType.Builder<_B> copyOf(final TransformsType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final TransformsType.Builder<_B> _newBuilder = new TransformsType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static TransformsType.Builder<Void> copyExcept(final TransformsType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static TransformsType.Builder<Void> copyOnly(final TransformsType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final TransformsType _storedValue;
        private List<TransformType.Builder<TransformsType.Builder<_B>>> transform;

        public Builder(final _B _parentBuilder, final TransformsType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.transform == null) {
                        this.transform = null;
                    } else {
                        this.transform = new ArrayList<>();
                        for (TransformType _item: _other.transform) {
                            this.transform.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final TransformsType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree transformPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transform"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformPropertyTree!= null):((transformPropertyTree == null)||(!transformPropertyTree.isLeaf())))) {
                        if (_other.transform == null) {
                            this.transform = null;
                        } else {
                            this.transform = new ArrayList<>();
                            for (TransformType _item: _other.transform) {
                                this.transform.add(((_item == null)?null:_item.newCopyBuilder(this, transformPropertyTree, _propertyTreeUse)));
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

        protected<_P extends TransformsType >_P init(final _P _product) {
            if (this.transform!= null) {
                final List<TransformType> transform = new ArrayList<>(this.transform.size());
                for (TransformType.Builder<TransformsType.Builder<_B>> _item: this.transform) {
                    transform.add(_item.build());
                }
                _product.transform = transform;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "transform"
         * 
         * @param transform
         *     Items to add to the value of the "transform" property
         */
        public TransformsType.Builder<_B> addTransform(final Iterable<? extends TransformType> transform) {
            if (transform!= null) {
                if (this.transform == null) {
                    this.transform = new ArrayList<>();
                }
                for (TransformType _item: transform) {
                    this.transform.add(new TransformType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "transform" (any previous value will be replaced)
         * 
         * @param transform
         *     New value of the "transform" property.
         */
        public TransformsType.Builder<_B> withTransform(final Iterable<? extends TransformType> transform) {
            if (this.transform!= null) {
                this.transform.clear();
            }
            return addTransform(transform);
        }

        /**
         * Adds the given items to the value of "transform"
         * 
         * @param transform
         *     Items to add to the value of the "transform" property
         */
        public TransformsType.Builder<_B> addTransform(TransformType... transform) {
            addTransform(Arrays.asList(transform));
            return this;
        }

        /**
         * Sets the new value of "transform" (any previous value will be replaced)
         * 
         * @param transform
         *     New value of the "transform" property.
         */
        public TransformsType.Builder<_B> withTransform(TransformType... transform) {
            withTransform(Arrays.asList(transform));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "Transform" property.
         * Use {@link org.w3._2000._09.xmldsig_.TransformType.Builder#end()} to return to
         * the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "Transform" property.
         *     Use {@link org.w3._2000._09.xmldsig_.TransformType.Builder#end()} to return to
         *     the current builder.
         */
        public TransformType.Builder<? extends TransformsType.Builder<_B>> addTransform() {
            if (this.transform == null) {
                this.transform = new ArrayList<>();
            }
            final TransformType.Builder<TransformsType.Builder<_B>> transform_Builder = new TransformType.Builder<>(this, null, false);
            this.transform.add(transform_Builder);
            return transform_Builder;
        }

        @Override
        public TransformsType build() {
            if (_storedValue == null) {
                return this.init(new TransformsType());
            } else {
                return ((TransformsType) _storedValue);
            }
        }

        public TransformsType.Builder<_B> copyOf(final TransformsType _other) {
            _other.copyTo(this);
            return this;
        }

        public TransformsType.Builder<_B> copyOf(final TransformsType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends TransformsType.Selector<TransformsType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static TransformsType.Select _root() {
            return new TransformsType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private TransformType.Selector<TRoot, TransformsType.Selector<TRoot, TParent>> transform = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.transform!= null) {
                products.put("transform", this.transform.init());
            }
            return products;
        }

        public TransformType.Selector<TRoot, TransformsType.Selector<TRoot, TParent>> transform() {
            return ((this.transform == null)?this.transform = new TransformType.Selector<>(this._root, this, "transform"):this.transform);
        }

    }

}
