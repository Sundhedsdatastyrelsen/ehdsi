
package dk.vaccinationsregister.schemas._2013._12._01.e1;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnsubscriptionDeletedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="UnsubscriptionDeletedType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnsubscriptionDeletedType")
public class UnsubscriptionDeletedType {


    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final UnsubscriptionDeletedType.Builder<_B> _other) {
    }

    public<_B >UnsubscriptionDeletedType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new UnsubscriptionDeletedType.Builder<_B>(_parentBuilder, this, true);
    }

    public UnsubscriptionDeletedType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static UnsubscriptionDeletedType.Builder<Void> builder() {
        return new UnsubscriptionDeletedType.Builder<>(null, null, false);
    }

    public static<_B >UnsubscriptionDeletedType.Builder<_B> copyOf(final UnsubscriptionDeletedType _other) {
        final UnsubscriptionDeletedType.Builder<_B> _newBuilder = new UnsubscriptionDeletedType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final UnsubscriptionDeletedType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
    }

    public<_B >UnsubscriptionDeletedType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new UnsubscriptionDeletedType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public UnsubscriptionDeletedType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >UnsubscriptionDeletedType.Builder<_B> copyOf(final UnsubscriptionDeletedType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final UnsubscriptionDeletedType.Builder<_B> _newBuilder = new UnsubscriptionDeletedType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static UnsubscriptionDeletedType.Builder<Void> copyExcept(final UnsubscriptionDeletedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static UnsubscriptionDeletedType.Builder<Void> copyOnly(final UnsubscriptionDeletedType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final UnsubscriptionDeletedType _storedValue;

        public Builder(final _B _parentBuilder, final UnsubscriptionDeletedType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final UnsubscriptionDeletedType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
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

        protected<_P extends UnsubscriptionDeletedType >_P init(final _P _product) {
            return _product;
        }

        @Override
        public UnsubscriptionDeletedType build() {
            if (_storedValue == null) {
                return this.init(new UnsubscriptionDeletedType());
            } else {
                return ((UnsubscriptionDeletedType) _storedValue);
            }
        }

        public UnsubscriptionDeletedType.Builder<_B> copyOf(final UnsubscriptionDeletedType _other) {
            _other.copyTo(this);
            return this;
        }

        public UnsubscriptionDeletedType.Builder<_B> copyOf(final UnsubscriptionDeletedType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends UnsubscriptionDeletedType.Selector<UnsubscriptionDeletedType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static UnsubscriptionDeletedType.Select _root() {
            return new UnsubscriptionDeletedType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {


        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            return products;
        }

    }

}
