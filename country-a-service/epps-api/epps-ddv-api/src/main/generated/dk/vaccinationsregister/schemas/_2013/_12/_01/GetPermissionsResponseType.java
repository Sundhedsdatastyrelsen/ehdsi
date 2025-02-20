
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
 * <p>Java class for GetPermissionsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetPermissionsResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="RolesPermissions" type="{http://vaccinationsregister.dk/schemas/2013/12/01}RolesPermissionsType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPermissionsResponseType", propOrder = {
    "rolesPermissions"
})
public class GetPermissionsResponseType {

    @XmlElement(name = "RolesPermissions")
    protected List<RolesPermissionsType> rolesPermissions;

    /**
     * Gets the value of the rolesPermissions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the rolesPermissions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRolesPermissions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RolesPermissionsType }
     * 
     * 
     * @return
     *     The value of the rolesPermissions property.
     */
    public List<RolesPermissionsType> getRolesPermissions() {
        if (rolesPermissions == null) {
            rolesPermissions = new ArrayList<>();
        }
        return this.rolesPermissions;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetPermissionsResponseType.Builder<_B> _other) {
        if (this.rolesPermissions == null) {
            _other.rolesPermissions = null;
        } else {
            _other.rolesPermissions = new ArrayList<>();
            for (RolesPermissionsType _item: this.rolesPermissions) {
                _other.rolesPermissions.add(((_item == null)?null:_item.newCopyBuilder(_other)));
            }
        }
    }

    public<_B >GetPermissionsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetPermissionsResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetPermissionsResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetPermissionsResponseType.Builder<Void> builder() {
        return new GetPermissionsResponseType.Builder<>(null, null, false);
    }

    public static<_B >GetPermissionsResponseType.Builder<_B> copyOf(final GetPermissionsResponseType _other) {
        final GetPermissionsResponseType.Builder<_B> _newBuilder = new GetPermissionsResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetPermissionsResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree rolesPermissionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("rolesPermissions"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(rolesPermissionsPropertyTree!= null):((rolesPermissionsPropertyTree == null)||(!rolesPermissionsPropertyTree.isLeaf())))) {
            if (this.rolesPermissions == null) {
                _other.rolesPermissions = null;
            } else {
                _other.rolesPermissions = new ArrayList<>();
                for (RolesPermissionsType _item: this.rolesPermissions) {
                    _other.rolesPermissions.add(((_item == null)?null:_item.newCopyBuilder(_other, rolesPermissionsPropertyTree, _propertyTreeUse)));
                }
            }
        }
    }

    public<_B >GetPermissionsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetPermissionsResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetPermissionsResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetPermissionsResponseType.Builder<_B> copyOf(final GetPermissionsResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetPermissionsResponseType.Builder<_B> _newBuilder = new GetPermissionsResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetPermissionsResponseType.Builder<Void> copyExcept(final GetPermissionsResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetPermissionsResponseType.Builder<Void> copyOnly(final GetPermissionsResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetPermissionsResponseType _storedValue;
        private List<RolesPermissionsType.Builder<GetPermissionsResponseType.Builder<_B>>> rolesPermissions;

        public Builder(final _B _parentBuilder, final GetPermissionsResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    if (_other.rolesPermissions == null) {
                        this.rolesPermissions = null;
                    } else {
                        this.rolesPermissions = new ArrayList<>();
                        for (RolesPermissionsType _item: _other.rolesPermissions) {
                            this.rolesPermissions.add(((_item == null)?null:_item.newCopyBuilder(this)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetPermissionsResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree rolesPermissionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("rolesPermissions"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(rolesPermissionsPropertyTree!= null):((rolesPermissionsPropertyTree == null)||(!rolesPermissionsPropertyTree.isLeaf())))) {
                        if (_other.rolesPermissions == null) {
                            this.rolesPermissions = null;
                        } else {
                            this.rolesPermissions = new ArrayList<>();
                            for (RolesPermissionsType _item: _other.rolesPermissions) {
                                this.rolesPermissions.add(((_item == null)?null:_item.newCopyBuilder(this, rolesPermissionsPropertyTree, _propertyTreeUse)));
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

        protected<_P extends GetPermissionsResponseType >_P init(final _P _product) {
            if (this.rolesPermissions!= null) {
                final List<RolesPermissionsType> rolesPermissions = new ArrayList<>(this.rolesPermissions.size());
                for (RolesPermissionsType.Builder<GetPermissionsResponseType.Builder<_B>> _item: this.rolesPermissions) {
                    rolesPermissions.add(_item.build());
                }
                _product.rolesPermissions = rolesPermissions;
            }
            return _product;
        }

        /**
         * Adds the given items to the value of "rolesPermissions"
         * 
         * @param rolesPermissions
         *     Items to add to the value of the "rolesPermissions" property
         */
        public GetPermissionsResponseType.Builder<_B> addRolesPermissions(final Iterable<? extends RolesPermissionsType> rolesPermissions) {
            if (rolesPermissions!= null) {
                if (this.rolesPermissions == null) {
                    this.rolesPermissions = new ArrayList<>();
                }
                for (RolesPermissionsType _item: rolesPermissions) {
                    this.rolesPermissions.add(new RolesPermissionsType.Builder<>(this, _item, false));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "rolesPermissions" (any previous value will be replaced)
         * 
         * @param rolesPermissions
         *     New value of the "rolesPermissions" property.
         */
        public GetPermissionsResponseType.Builder<_B> withRolesPermissions(final Iterable<? extends RolesPermissionsType> rolesPermissions) {
            if (this.rolesPermissions!= null) {
                this.rolesPermissions.clear();
            }
            return addRolesPermissions(rolesPermissions);
        }

        /**
         * Adds the given items to the value of "rolesPermissions"
         * 
         * @param rolesPermissions
         *     Items to add to the value of the "rolesPermissions" property
         */
        public GetPermissionsResponseType.Builder<_B> addRolesPermissions(RolesPermissionsType... rolesPermissions) {
            addRolesPermissions(Arrays.asList(rolesPermissions));
            return this;
        }

        /**
         * Sets the new value of "rolesPermissions" (any previous value will be replaced)
         * 
         * @param rolesPermissions
         *     New value of the "rolesPermissions" property.
         */
        public GetPermissionsResponseType.Builder<_B> withRolesPermissions(RolesPermissionsType... rolesPermissions) {
            withRolesPermissions(Arrays.asList(rolesPermissions));
            return this;
        }

        /**
         * Returns a new builder to build an additional value of the "RolesPermissions"
         * property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.RolesPermissionsType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     a new builder to build an additional value of the "RolesPermissions" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.RolesPermissionsType.Builder#end()}
         *     to return to the current builder.
         */
        public RolesPermissionsType.Builder<? extends GetPermissionsResponseType.Builder<_B>> addRolesPermissions() {
            if (this.rolesPermissions == null) {
                this.rolesPermissions = new ArrayList<>();
            }
            final RolesPermissionsType.Builder<GetPermissionsResponseType.Builder<_B>> rolesPermissions_Builder = new RolesPermissionsType.Builder<>(this, null, false);
            this.rolesPermissions.add(rolesPermissions_Builder);
            return rolesPermissions_Builder;
        }

        @Override
        public GetPermissionsResponseType build() {
            if (_storedValue == null) {
                return this.init(new GetPermissionsResponseType());
            } else {
                return ((GetPermissionsResponseType) _storedValue);
            }
        }

        public GetPermissionsResponseType.Builder<_B> copyOf(final GetPermissionsResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetPermissionsResponseType.Builder<_B> copyOf(final GetPermissionsResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetPermissionsResponseType.Selector<GetPermissionsResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetPermissionsResponseType.Select _root() {
            return new GetPermissionsResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private RolesPermissionsType.Selector<TRoot, GetPermissionsResponseType.Selector<TRoot, TParent>> rolesPermissions = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.rolesPermissions!= null) {
                products.put("rolesPermissions", this.rolesPermissions.init());
            }
            return products;
        }

        public RolesPermissionsType.Selector<TRoot, GetPermissionsResponseType.Selector<TRoot, TParent>> rolesPermissions() {
            return ((this.rolesPermissions == null)?this.rolesPermissions = new RolesPermissionsType.Selector<>(this._root, this, "rolesPermissions"):this.rolesPermissions);
        }

    }

}
