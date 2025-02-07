
package dk.vaccinationsregister.schemas._2013._12._01;

import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetAllPermissionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetAllPermissionsType">
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
@XmlType(name = "GetAllPermissionsType")
public class GetAllPermissionsType {


    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetAllPermissionsType.Builder<_B> _other) {
    }

    public<_B >GetAllPermissionsType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetAllPermissionsType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetAllPermissionsType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetAllPermissionsType.Builder<Void> builder() {
        return new GetAllPermissionsType.Builder<>(null, null, false);
    }

    public static<_B >GetAllPermissionsType.Builder<_B> copyOf(final GetAllPermissionsType _other) {
        final GetAllPermissionsType.Builder<_B> _newBuilder = new GetAllPermissionsType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetAllPermissionsType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
    }

    public<_B >GetAllPermissionsType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetAllPermissionsType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetAllPermissionsType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetAllPermissionsType.Builder<_B> copyOf(final GetAllPermissionsType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetAllPermissionsType.Builder<_B> _newBuilder = new GetAllPermissionsType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetAllPermissionsType.Builder<Void> copyExcept(final GetAllPermissionsType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetAllPermissionsType.Builder<Void> copyOnly(final GetAllPermissionsType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetAllPermissionsType _storedValue;

        public Builder(final _B _parentBuilder, final GetAllPermissionsType _other, final boolean _copy) {
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

        public Builder(final _B _parentBuilder, final GetAllPermissionsType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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

        protected<_P extends GetAllPermissionsType >_P init(final _P _product) {
            return _product;
        }

        @Override
        public GetAllPermissionsType build() {
            if (_storedValue == null) {
                return this.init(new GetAllPermissionsType());
            } else {
                return ((GetAllPermissionsType) _storedValue);
            }
        }

        public GetAllPermissionsType.Builder<_B> copyOf(final GetAllPermissionsType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetAllPermissionsType.Builder<_B> copyOf(final GetAllPermissionsType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetAllPermissionsType.Selector<GetAllPermissionsType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetAllPermissionsType.Select _root() {
            return new GetAllPermissionsType.Select();
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
