
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
 * <p>Java class for GetVaccinationCardAsPDFResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetVaccinationCardAsPDFResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="VaccinationCardAsPDF" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationCardAsPDFType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetVaccinationCardAsPDFResponseType", propOrder = {
    "vaccinationCardAsPDF"
})
public class GetVaccinationCardAsPDFResponseType {

    @XmlElement(name = "VaccinationCardAsPDF")
    protected String vaccinationCardAsPDF;

    /**
     * Gets the value of the vaccinationCardAsPDF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaccinationCardAsPDF() {
        return vaccinationCardAsPDF;
    }

    /**
     * Sets the value of the vaccinationCardAsPDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaccinationCardAsPDF(String value) {
        this.vaccinationCardAsPDF = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetVaccinationCardAsPDFResponseType.Builder<_B> _other) {
        _other.vaccinationCardAsPDF = this.vaccinationCardAsPDF;
    }

    public<_B >GetVaccinationCardAsPDFResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetVaccinationCardAsPDFResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetVaccinationCardAsPDFResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetVaccinationCardAsPDFResponseType.Builder<Void> builder() {
        return new GetVaccinationCardAsPDFResponseType.Builder<>(null, null, false);
    }

    public static<_B >GetVaccinationCardAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationCardAsPDFResponseType _other) {
        final GetVaccinationCardAsPDFResponseType.Builder<_B> _newBuilder = new GetVaccinationCardAsPDFResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetVaccinationCardAsPDFResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree vaccinationCardAsPDFPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCardAsPDF"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCardAsPDFPropertyTree!= null):((vaccinationCardAsPDFPropertyTree == null)||(!vaccinationCardAsPDFPropertyTree.isLeaf())))) {
            _other.vaccinationCardAsPDF = this.vaccinationCardAsPDF;
        }
    }

    public<_B >GetVaccinationCardAsPDFResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetVaccinationCardAsPDFResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetVaccinationCardAsPDFResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetVaccinationCardAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationCardAsPDFResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetVaccinationCardAsPDFResponseType.Builder<_B> _newBuilder = new GetVaccinationCardAsPDFResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetVaccinationCardAsPDFResponseType.Builder<Void> copyExcept(final GetVaccinationCardAsPDFResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetVaccinationCardAsPDFResponseType.Builder<Void> copyOnly(final GetVaccinationCardAsPDFResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetVaccinationCardAsPDFResponseType _storedValue;
        private String vaccinationCardAsPDF;

        public Builder(final _B _parentBuilder, final GetVaccinationCardAsPDFResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.vaccinationCardAsPDF = _other.vaccinationCardAsPDF;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetVaccinationCardAsPDFResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree vaccinationCardAsPDFPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCardAsPDF"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCardAsPDFPropertyTree!= null):((vaccinationCardAsPDFPropertyTree == null)||(!vaccinationCardAsPDFPropertyTree.isLeaf())))) {
                        this.vaccinationCardAsPDF = _other.vaccinationCardAsPDF;
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

        protected<_P extends GetVaccinationCardAsPDFResponseType >_P init(final _P _product) {
            _product.vaccinationCardAsPDF = this.vaccinationCardAsPDF;
            return _product;
        }

        /**
         * Sets the new value of "vaccinationCardAsPDF" (any previous value will be
         * replaced)
         * 
         * @param vaccinationCardAsPDF
         *     New value of the "vaccinationCardAsPDF" property.
         */
        public GetVaccinationCardAsPDFResponseType.Builder<_B> withVaccinationCardAsPDF(final String vaccinationCardAsPDF) {
            this.vaccinationCardAsPDF = vaccinationCardAsPDF;
            return this;
        }

        @Override
        public GetVaccinationCardAsPDFResponseType build() {
            if (_storedValue == null) {
                return this.init(new GetVaccinationCardAsPDFResponseType());
            } else {
                return ((GetVaccinationCardAsPDFResponseType) _storedValue);
            }
        }

        public GetVaccinationCardAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationCardAsPDFResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetVaccinationCardAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationCardAsPDFResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetVaccinationCardAsPDFResponseType.Selector<GetVaccinationCardAsPDFResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetVaccinationCardAsPDFResponseType.Select _root() {
            return new GetVaccinationCardAsPDFResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationCardAsPDFResponseType.Selector<TRoot, TParent>> vaccinationCardAsPDF = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.vaccinationCardAsPDF!= null) {
                products.put("vaccinationCardAsPDF", this.vaccinationCardAsPDF.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationCardAsPDFResponseType.Selector<TRoot, TParent>> vaccinationCardAsPDF() {
            return ((this.vaccinationCardAsPDF == null)?this.vaccinationCardAsPDF = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationCardAsPDF"):this.vaccinationCardAsPDF);
        }

    }

}
