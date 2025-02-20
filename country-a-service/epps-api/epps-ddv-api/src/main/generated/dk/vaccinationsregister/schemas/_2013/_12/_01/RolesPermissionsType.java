
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
 * <p>Java class for RolesPermissionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="RolesPermissionsType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="RequestedRole" type="{http://vaccinationsregister.dk/schemas/2013/12/01}RequestedRoleType"/>
 *         <element name="Permission" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PermissionType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RolesPermissionsType", propOrder = {
    "requestedRole",
    "permission"
})
public class RolesPermissionsType {

    @XmlElement(name = "RequestedRole", required = true)
    protected String requestedRole;
    @XmlElement(name = "Permission")
    protected List<String> permission;

    /**
     * Gets the value of the requestedRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedRole() {
        return requestedRole;
    }

    /**
     * Sets the value of the requestedRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedRole(String value) {
        this.requestedRole = value;
    }

    /**
     * Gets the value of the permission property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the permission property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermission().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     * @return
     *     The value of the permission property.
     */
    public List<String> getPermission() {
        if (permission == null) {
            permission = new ArrayList<>();
        }
        return this.permission;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final RolesPermissionsType.Builder<_B> _other) {
        _other.requestedRole = this.requestedRole;
        if (this.permission == null) {
            _other.permission = null;
        } else {
            _other.permission = new ArrayList<>();
            for (String _item: this.permission) {
                _other.permission.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
            }
        }
    }

    public<_B >RolesPermissionsType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new RolesPermissionsType.Builder<_B>(_parentBuilder, this, true);
    }

    public RolesPermissionsType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static RolesPermissionsType.Builder<Void> builder() {
        return new RolesPermissionsType.Builder<>(null, null, false);
    }

    public static<_B >RolesPermissionsType.Builder<_B> copyOf(final RolesPermissionsType _other) {
        final RolesPermissionsType.Builder<_B> _newBuilder = new RolesPermissionsType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final RolesPermissionsType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree requestedRolePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("requestedRole"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(requestedRolePropertyTree!= null):((requestedRolePropertyTree == null)||(!requestedRolePropertyTree.isLeaf())))) {
            _other.requestedRole = this.requestedRole;
        }
        final PropertyTree permissionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("permission"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(permissionPropertyTree!= null):((permissionPropertyTree == null)||(!permissionPropertyTree.isLeaf())))) {
            if (this.permission == null) {
                _other.permission = null;
            } else {
                _other.permission = new ArrayList<>();
                for (String _item: this.permission) {
                    _other.permission.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                }
            }
        }
    }

    public<_B >RolesPermissionsType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new RolesPermissionsType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public RolesPermissionsType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >RolesPermissionsType.Builder<_B> copyOf(final RolesPermissionsType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final RolesPermissionsType.Builder<_B> _newBuilder = new RolesPermissionsType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static RolesPermissionsType.Builder<Void> copyExcept(final RolesPermissionsType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static RolesPermissionsType.Builder<Void> copyOnly(final RolesPermissionsType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final RolesPermissionsType _storedValue;
        private String requestedRole;
        private List<Buildable> permission;

        public Builder(final _B _parentBuilder, final RolesPermissionsType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.requestedRole = _other.requestedRole;
                    if (_other.permission == null) {
                        this.permission = null;
                    } else {
                        this.permission = new ArrayList<>();
                        for (String _item: _other.permission) {
                            this.permission.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
                        }
                    }
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final RolesPermissionsType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree requestedRolePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("requestedRole"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(requestedRolePropertyTree!= null):((requestedRolePropertyTree == null)||(!requestedRolePropertyTree.isLeaf())))) {
                        this.requestedRole = _other.requestedRole;
                    }
                    final PropertyTree permissionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("permission"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(permissionPropertyTree!= null):((permissionPropertyTree == null)||(!permissionPropertyTree.isLeaf())))) {
                        if (_other.permission == null) {
                            this.permission = null;
                        } else {
                            this.permission = new ArrayList<>();
                            for (String _item: _other.permission) {
                                this.permission.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
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

        protected<_P extends RolesPermissionsType >_P init(final _P _product) {
            _product.requestedRole = this.requestedRole;
            if (this.permission!= null) {
                final List<String> permission = new ArrayList<>(this.permission.size());
                for (Buildable _item: this.permission) {
                    permission.add(((String) _item.build()));
                }
                _product.permission = permission;
            }
            return _product;
        }

        /**
         * Sets the new value of "requestedRole" (any previous value will be replaced)
         * 
         * @param requestedRole
         *     New value of the "requestedRole" property.
         */
        public RolesPermissionsType.Builder<_B> withRequestedRole(final String requestedRole) {
            this.requestedRole = requestedRole;
            return this;
        }

        /**
         * Adds the given items to the value of "permission"
         * 
         * @param permission
         *     Items to add to the value of the "permission" property
         */
        public RolesPermissionsType.Builder<_B> addPermission(final Iterable<? extends String> permission) {
            if (permission!= null) {
                if (this.permission == null) {
                    this.permission = new ArrayList<>();
                }
                for (String _item: permission) {
                    this.permission.add(new Buildable.PrimitiveBuildable(_item));
                }
            }
            return this;
        }

        /**
         * Sets the new value of "permission" (any previous value will be replaced)
         * 
         * @param permission
         *     New value of the "permission" property.
         */
        public RolesPermissionsType.Builder<_B> withPermission(final Iterable<? extends String> permission) {
            if (this.permission!= null) {
                this.permission.clear();
            }
            return addPermission(permission);
        }

        /**
         * Adds the given items to the value of "permission"
         * 
         * @param permission
         *     Items to add to the value of the "permission" property
         */
        public RolesPermissionsType.Builder<_B> addPermission(String... permission) {
            addPermission(Arrays.asList(permission));
            return this;
        }

        /**
         * Sets the new value of "permission" (any previous value will be replaced)
         * 
         * @param permission
         *     New value of the "permission" property.
         */
        public RolesPermissionsType.Builder<_B> withPermission(String... permission) {
            withPermission(Arrays.asList(permission));
            return this;
        }

        @Override
        public RolesPermissionsType build() {
            if (_storedValue == null) {
                return this.init(new RolesPermissionsType());
            } else {
                return ((RolesPermissionsType) _storedValue);
            }
        }

        public RolesPermissionsType.Builder<_B> copyOf(final RolesPermissionsType _other) {
            _other.copyTo(this);
            return this;
        }

        public RolesPermissionsType.Builder<_B> copyOf(final RolesPermissionsType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends RolesPermissionsType.Selector<RolesPermissionsType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static RolesPermissionsType.Select _root() {
            return new RolesPermissionsType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, RolesPermissionsType.Selector<TRoot, TParent>> requestedRole = null;
        private com.kscs.util.jaxb.Selector<TRoot, RolesPermissionsType.Selector<TRoot, TParent>> permission = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.requestedRole!= null) {
                products.put("requestedRole", this.requestedRole.init());
            }
            if (this.permission!= null) {
                products.put("permission", this.permission.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, RolesPermissionsType.Selector<TRoot, TParent>> requestedRole() {
            return ((this.requestedRole == null)?this.requestedRole = new com.kscs.util.jaxb.Selector<>(this._root, this, "requestedRole"):this.requestedRole);
        }

        public com.kscs.util.jaxb.Selector<TRoot, RolesPermissionsType.Selector<TRoot, TParent>> permission() {
            return ((this.permission == null)?this.permission = new com.kscs.util.jaxb.Selector<>(this._root, this, "permission"):this.permission);
        }

    }

}
