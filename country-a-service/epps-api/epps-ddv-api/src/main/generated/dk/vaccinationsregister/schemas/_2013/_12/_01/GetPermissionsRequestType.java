
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
 * <p>Java class for GetPermissionsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetPermissionsRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element ref="{http://vaccinationsregister.dk/schemas/2013/12/01}GetAllPermissions"/>
 *         <element ref="{http://vaccinationsregister.dk/schemas/2013/12/01}GetCallersPermissions"/>
 *         <element ref="{http://vaccinationsregister.dk/schemas/2013/12/01}GetCallersPermissionsToPerson"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPermissionsRequestType", propOrder = {
    "getAllPermissions",
    "getCallersPermissions",
    "getCallersPermissionsToPerson"
})
public class GetPermissionsRequestType {

    @XmlElement(name = "GetAllPermissions")
    protected GetAllPermissionsType getAllPermissions;
    @XmlElement(name = "GetCallersPermissions")
    protected GetCallersPermissionsType getCallersPermissions;
    @XmlElement(name = "GetCallersPermissionsToPerson")
    protected GetCallersPermissionsToPersonType getCallersPermissionsToPerson;

    /**
     * Gets the value of the getAllPermissions property.
     * 
     * @return
     *     possible object is
     *     {@link GetAllPermissionsType }
     *     
     */
    public GetAllPermissionsType getGetAllPermissions() {
        return getAllPermissions;
    }

    /**
     * Sets the value of the getAllPermissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAllPermissionsType }
     *     
     */
    public void setGetAllPermissions(GetAllPermissionsType value) {
        this.getAllPermissions = value;
    }

    /**
     * Gets the value of the getCallersPermissions property.
     * 
     * @return
     *     possible object is
     *     {@link GetCallersPermissionsType }
     *     
     */
    public GetCallersPermissionsType getGetCallersPermissions() {
        return getCallersPermissions;
    }

    /**
     * Sets the value of the getCallersPermissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCallersPermissionsType }
     *     
     */
    public void setGetCallersPermissions(GetCallersPermissionsType value) {
        this.getCallersPermissions = value;
    }

    /**
     * Gets the value of the getCallersPermissionsToPerson property.
     * 
     * @return
     *     possible object is
     *     {@link GetCallersPermissionsToPersonType }
     *     
     */
    public GetCallersPermissionsToPersonType getGetCallersPermissionsToPerson() {
        return getCallersPermissionsToPerson;
    }

    /**
     * Sets the value of the getCallersPermissionsToPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetCallersPermissionsToPersonType }
     *     
     */
    public void setGetCallersPermissionsToPerson(GetCallersPermissionsToPersonType value) {
        this.getCallersPermissionsToPerson = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetPermissionsRequestType.Builder<_B> _other) {
        _other.getAllPermissions = ((this.getAllPermissions == null)?null:this.getAllPermissions.newCopyBuilder(_other));
        _other.getCallersPermissions = ((this.getCallersPermissions == null)?null:this.getCallersPermissions.newCopyBuilder(_other));
        _other.getCallersPermissionsToPerson = ((this.getCallersPermissionsToPerson == null)?null:this.getCallersPermissionsToPerson.newCopyBuilder(_other));
    }

    public<_B >GetPermissionsRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetPermissionsRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetPermissionsRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetPermissionsRequestType.Builder<Void> builder() {
        return new GetPermissionsRequestType.Builder<>(null, null, false);
    }

    public static<_B >GetPermissionsRequestType.Builder<_B> copyOf(final GetPermissionsRequestType _other) {
        final GetPermissionsRequestType.Builder<_B> _newBuilder = new GetPermissionsRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetPermissionsRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree getAllPermissionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("getAllPermissions"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(getAllPermissionsPropertyTree!= null):((getAllPermissionsPropertyTree == null)||(!getAllPermissionsPropertyTree.isLeaf())))) {
            _other.getAllPermissions = ((this.getAllPermissions == null)?null:this.getAllPermissions.newCopyBuilder(_other, getAllPermissionsPropertyTree, _propertyTreeUse));
        }
        final PropertyTree getCallersPermissionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("getCallersPermissions"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(getCallersPermissionsPropertyTree!= null):((getCallersPermissionsPropertyTree == null)||(!getCallersPermissionsPropertyTree.isLeaf())))) {
            _other.getCallersPermissions = ((this.getCallersPermissions == null)?null:this.getCallersPermissions.newCopyBuilder(_other, getCallersPermissionsPropertyTree, _propertyTreeUse));
        }
        final PropertyTree getCallersPermissionsToPersonPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("getCallersPermissionsToPerson"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(getCallersPermissionsToPersonPropertyTree!= null):((getCallersPermissionsToPersonPropertyTree == null)||(!getCallersPermissionsToPersonPropertyTree.isLeaf())))) {
            _other.getCallersPermissionsToPerson = ((this.getCallersPermissionsToPerson == null)?null:this.getCallersPermissionsToPerson.newCopyBuilder(_other, getCallersPermissionsToPersonPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >GetPermissionsRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetPermissionsRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetPermissionsRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetPermissionsRequestType.Builder<_B> copyOf(final GetPermissionsRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetPermissionsRequestType.Builder<_B> _newBuilder = new GetPermissionsRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetPermissionsRequestType.Builder<Void> copyExcept(final GetPermissionsRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetPermissionsRequestType.Builder<Void> copyOnly(final GetPermissionsRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetPermissionsRequestType _storedValue;
        private GetAllPermissionsType.Builder<GetPermissionsRequestType.Builder<_B>> getAllPermissions;
        private GetCallersPermissionsType.Builder<GetPermissionsRequestType.Builder<_B>> getCallersPermissions;
        private GetCallersPermissionsToPersonType.Builder<GetPermissionsRequestType.Builder<_B>> getCallersPermissionsToPerson;

        public Builder(final _B _parentBuilder, final GetPermissionsRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.getAllPermissions = ((_other.getAllPermissions == null)?null:_other.getAllPermissions.newCopyBuilder(this));
                    this.getCallersPermissions = ((_other.getCallersPermissions == null)?null:_other.getCallersPermissions.newCopyBuilder(this));
                    this.getCallersPermissionsToPerson = ((_other.getCallersPermissionsToPerson == null)?null:_other.getCallersPermissionsToPerson.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetPermissionsRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree getAllPermissionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("getAllPermissions"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(getAllPermissionsPropertyTree!= null):((getAllPermissionsPropertyTree == null)||(!getAllPermissionsPropertyTree.isLeaf())))) {
                        this.getAllPermissions = ((_other.getAllPermissions == null)?null:_other.getAllPermissions.newCopyBuilder(this, getAllPermissionsPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree getCallersPermissionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("getCallersPermissions"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(getCallersPermissionsPropertyTree!= null):((getCallersPermissionsPropertyTree == null)||(!getCallersPermissionsPropertyTree.isLeaf())))) {
                        this.getCallersPermissions = ((_other.getCallersPermissions == null)?null:_other.getCallersPermissions.newCopyBuilder(this, getCallersPermissionsPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree getCallersPermissionsToPersonPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("getCallersPermissionsToPerson"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(getCallersPermissionsToPersonPropertyTree!= null):((getCallersPermissionsToPersonPropertyTree == null)||(!getCallersPermissionsToPersonPropertyTree.isLeaf())))) {
                        this.getCallersPermissionsToPerson = ((_other.getCallersPermissionsToPerson == null)?null:_other.getCallersPermissionsToPerson.newCopyBuilder(this, getCallersPermissionsToPersonPropertyTree, _propertyTreeUse));
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

        protected<_P extends GetPermissionsRequestType >_P init(final _P _product) {
            _product.getAllPermissions = ((this.getAllPermissions == null)?null:this.getAllPermissions.build());
            _product.getCallersPermissions = ((this.getCallersPermissions == null)?null:this.getCallersPermissions.build());
            _product.getCallersPermissionsToPerson = ((this.getCallersPermissionsToPerson == null)?null:this.getCallersPermissionsToPerson.build());
            return _product;
        }

        /**
         * Sets the new value of "getAllPermissions" (any previous value will be replaced)
         * 
         * @param getAllPermissions
         *     New value of the "getAllPermissions" property.
         */
        public GetPermissionsRequestType.Builder<_B> withGetAllPermissions(final GetAllPermissionsType getAllPermissions) {
            this.getAllPermissions = ((getAllPermissions == null)?null:new GetAllPermissionsType.Builder<>(this, getAllPermissions, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "getAllPermissions" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.GetAllPermissionsType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "getAllPermissions" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.GetAllPermissionsType.Builder#end()}
         *     to return to the current builder.
         */
        public GetAllPermissionsType.Builder<? extends GetPermissionsRequestType.Builder<_B>> withGetAllPermissions() {
            if (this.getAllPermissions!= null) {
                return this.getAllPermissions;
            }
            return this.getAllPermissions = new GetAllPermissionsType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "getCallersPermissions" (any previous value will be
         * replaced)
         * 
         * @param getCallersPermissions
         *     New value of the "getCallersPermissions" property.
         */
        public GetPermissionsRequestType.Builder<_B> withGetCallersPermissions(final GetCallersPermissionsType getCallersPermissions) {
            this.getCallersPermissions = ((getCallersPermissions == null)?null:new GetCallersPermissionsType.Builder<>(this, getCallersPermissions, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "getCallersPermissions" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.GetCallersPermissionsType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "getCallersPermissions" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.GetCallersPermissionsType.Builder#end()}
         *     to return to the current builder.
         */
        public GetCallersPermissionsType.Builder<? extends GetPermissionsRequestType.Builder<_B>> withGetCallersPermissions() {
            if (this.getCallersPermissions!= null) {
                return this.getCallersPermissions;
            }
            return this.getCallersPermissions = new GetCallersPermissionsType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "getCallersPermissionsToPerson" (any previous value will
         * be replaced)
         * 
         * @param getCallersPermissionsToPerson
         *     New value of the "getCallersPermissionsToPerson" property.
         */
        public GetPermissionsRequestType.Builder<_B> withGetCallersPermissionsToPerson(final GetCallersPermissionsToPersonType getCallersPermissionsToPerson) {
            this.getCallersPermissionsToPerson = ((getCallersPermissionsToPerson == null)?null:new GetCallersPermissionsToPersonType.Builder<>(this, getCallersPermissionsToPerson, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "getCallersPermissionsToPerson" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.GetCallersPermissionsToPersonType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "getCallersPermissionsToPerson"
         *     property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.GetCallersPermissionsToPersonType.Builder#end()}
         *     to return to the current builder.
         */
        public GetCallersPermissionsToPersonType.Builder<? extends GetPermissionsRequestType.Builder<_B>> withGetCallersPermissionsToPerson() {
            if (this.getCallersPermissionsToPerson!= null) {
                return this.getCallersPermissionsToPerson;
            }
            return this.getCallersPermissionsToPerson = new GetCallersPermissionsToPersonType.Builder<>(this, null, false);
        }

        @Override
        public GetPermissionsRequestType build() {
            if (_storedValue == null) {
                return this.init(new GetPermissionsRequestType());
            } else {
                return ((GetPermissionsRequestType) _storedValue);
            }
        }

        public GetPermissionsRequestType.Builder<_B> copyOf(final GetPermissionsRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetPermissionsRequestType.Builder<_B> copyOf(final GetPermissionsRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetPermissionsRequestType.Selector<GetPermissionsRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetPermissionsRequestType.Select _root() {
            return new GetPermissionsRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private GetAllPermissionsType.Selector<TRoot, GetPermissionsRequestType.Selector<TRoot, TParent>> getAllPermissions = null;
        private GetCallersPermissionsType.Selector<TRoot, GetPermissionsRequestType.Selector<TRoot, TParent>> getCallersPermissions = null;
        private GetCallersPermissionsToPersonType.Selector<TRoot, GetPermissionsRequestType.Selector<TRoot, TParent>> getCallersPermissionsToPerson = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.getAllPermissions!= null) {
                products.put("getAllPermissions", this.getAllPermissions.init());
            }
            if (this.getCallersPermissions!= null) {
                products.put("getCallersPermissions", this.getCallersPermissions.init());
            }
            if (this.getCallersPermissionsToPerson!= null) {
                products.put("getCallersPermissionsToPerson", this.getCallersPermissionsToPerson.init());
            }
            return products;
        }

        public GetAllPermissionsType.Selector<TRoot, GetPermissionsRequestType.Selector<TRoot, TParent>> getAllPermissions() {
            return ((this.getAllPermissions == null)?this.getAllPermissions = new GetAllPermissionsType.Selector<>(this._root, this, "getAllPermissions"):this.getAllPermissions);
        }

        public GetCallersPermissionsType.Selector<TRoot, GetPermissionsRequestType.Selector<TRoot, TParent>> getCallersPermissions() {
            return ((this.getCallersPermissions == null)?this.getCallersPermissions = new GetCallersPermissionsType.Selector<>(this._root, this, "getCallersPermissions"):this.getCallersPermissions);
        }

        public GetCallersPermissionsToPersonType.Selector<TRoot, GetPermissionsRequestType.Selector<TRoot, TParent>> getCallersPermissionsToPerson() {
            return ((this.getCallersPermissionsToPerson == null)?this.getCallersPermissionsToPerson = new GetCallersPermissionsToPersonType.Selector<>(this._root, this, "getCallersPermissionsToPerson"):this.getCallersPermissionsToPerson);
        }

    }

}
