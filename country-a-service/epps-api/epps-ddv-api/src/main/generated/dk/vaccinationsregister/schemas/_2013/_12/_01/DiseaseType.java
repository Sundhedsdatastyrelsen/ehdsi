
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
 * <p>Java class for DiseaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DiseaseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DiseaseIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DiseaseIdentifierType" minOccurs="0"/>
 *         <element name="DiseaseName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DiseaseNameType" minOccurs="0"/>
 *         <element name="DiseaseNameDK" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DiseaseNameDKType" minOccurs="0"/>
 *         <element name="ATC" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ATCType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiseaseType", propOrder = {
    "diseaseIdentifier",
    "diseaseName",
    "diseaseNameDK",
    "atc"
})
public class DiseaseType {

    @XmlElement(name = "DiseaseIdentifier")
    protected Long diseaseIdentifier;
    @XmlElement(name = "DiseaseName")
    protected String diseaseName;
    @XmlElement(name = "DiseaseNameDK")
    protected String diseaseNameDK;
    @XmlElement(name = "ATC", required = true)
    protected ATCType atc;

    /**
     * Gets the value of the diseaseIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDiseaseIdentifier() {
        return diseaseIdentifier;
    }

    /**
     * Sets the value of the diseaseIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDiseaseIdentifier(Long value) {
        this.diseaseIdentifier = value;
    }

    /**
     * Gets the value of the diseaseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiseaseName() {
        return diseaseName;
    }

    /**
     * Sets the value of the diseaseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiseaseName(String value) {
        this.diseaseName = value;
    }

    /**
     * Gets the value of the diseaseNameDK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiseaseNameDK() {
        return diseaseNameDK;
    }

    /**
     * Sets the value of the diseaseNameDK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiseaseNameDK(String value) {
        this.diseaseNameDK = value;
    }

    /**
     * Gets the value of the atc property.
     * 
     * @return
     *     possible object is
     *     {@link ATCType }
     *     
     */
    public ATCType getATC() {
        return atc;
    }

    /**
     * Sets the value of the atc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ATCType }
     *     
     */
    public void setATC(ATCType value) {
        this.atc = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final DiseaseType.Builder<_B> _other) {
        _other.diseaseIdentifier = this.diseaseIdentifier;
        _other.diseaseName = this.diseaseName;
        _other.diseaseNameDK = this.diseaseNameDK;
        _other.atc = ((this.atc == null)?null:this.atc.newCopyBuilder(_other));
    }

    public<_B >DiseaseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new DiseaseType.Builder<_B>(_parentBuilder, this, true);
    }

    public DiseaseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static DiseaseType.Builder<Void> builder() {
        return new DiseaseType.Builder<>(null, null, false);
    }

    public static<_B >DiseaseType.Builder<_B> copyOf(final DiseaseType _other) {
        final DiseaseType.Builder<_B> _newBuilder = new DiseaseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final DiseaseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree diseaseIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("diseaseIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseaseIdentifierPropertyTree!= null):((diseaseIdentifierPropertyTree == null)||(!diseaseIdentifierPropertyTree.isLeaf())))) {
            _other.diseaseIdentifier = this.diseaseIdentifier;
        }
        final PropertyTree diseaseNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("diseaseName"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseaseNamePropertyTree!= null):((diseaseNamePropertyTree == null)||(!diseaseNamePropertyTree.isLeaf())))) {
            _other.diseaseName = this.diseaseName;
        }
        final PropertyTree diseaseNameDKPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("diseaseNameDK"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseaseNameDKPropertyTree!= null):((diseaseNameDKPropertyTree == null)||(!diseaseNameDKPropertyTree.isLeaf())))) {
            _other.diseaseNameDK = this.diseaseNameDK;
        }
        final PropertyTree atcPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("atc"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(atcPropertyTree!= null):((atcPropertyTree == null)||(!atcPropertyTree.isLeaf())))) {
            _other.atc = ((this.atc == null)?null:this.atc.newCopyBuilder(_other, atcPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >DiseaseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new DiseaseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public DiseaseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >DiseaseType.Builder<_B> copyOf(final DiseaseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final DiseaseType.Builder<_B> _newBuilder = new DiseaseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static DiseaseType.Builder<Void> copyExcept(final DiseaseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static DiseaseType.Builder<Void> copyOnly(final DiseaseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final DiseaseType _storedValue;
        private Long diseaseIdentifier;
        private String diseaseName;
        private String diseaseNameDK;
        private ATCType.Builder<DiseaseType.Builder<_B>> atc;

        public Builder(final _B _parentBuilder, final DiseaseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.diseaseIdentifier = _other.diseaseIdentifier;
                    this.diseaseName = _other.diseaseName;
                    this.diseaseNameDK = _other.diseaseNameDK;
                    this.atc = ((_other.atc == null)?null:_other.atc.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final DiseaseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree diseaseIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("diseaseIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseaseIdentifierPropertyTree!= null):((diseaseIdentifierPropertyTree == null)||(!diseaseIdentifierPropertyTree.isLeaf())))) {
                        this.diseaseIdentifier = _other.diseaseIdentifier;
                    }
                    final PropertyTree diseaseNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("diseaseName"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseaseNamePropertyTree!= null):((diseaseNamePropertyTree == null)||(!diseaseNamePropertyTree.isLeaf())))) {
                        this.diseaseName = _other.diseaseName;
                    }
                    final PropertyTree diseaseNameDKPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("diseaseNameDK"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseaseNameDKPropertyTree!= null):((diseaseNameDKPropertyTree == null)||(!diseaseNameDKPropertyTree.isLeaf())))) {
                        this.diseaseNameDK = _other.diseaseNameDK;
                    }
                    final PropertyTree atcPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("atc"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(atcPropertyTree!= null):((atcPropertyTree == null)||(!atcPropertyTree.isLeaf())))) {
                        this.atc = ((_other.atc == null)?null:_other.atc.newCopyBuilder(this, atcPropertyTree, _propertyTreeUse));
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

        protected<_P extends DiseaseType >_P init(final _P _product) {
            _product.diseaseIdentifier = this.diseaseIdentifier;
            _product.diseaseName = this.diseaseName;
            _product.diseaseNameDK = this.diseaseNameDK;
            _product.atc = ((this.atc == null)?null:this.atc.build());
            return _product;
        }

        /**
         * Sets the new value of "diseaseIdentifier" (any previous value will be replaced)
         * 
         * @param diseaseIdentifier
         *     New value of the "diseaseIdentifier" property.
         */
        public DiseaseType.Builder<_B> withDiseaseIdentifier(final Long diseaseIdentifier) {
            this.diseaseIdentifier = diseaseIdentifier;
            return this;
        }

        /**
         * Sets the new value of "diseaseName" (any previous value will be replaced)
         * 
         * @param diseaseName
         *     New value of the "diseaseName" property.
         */
        public DiseaseType.Builder<_B> withDiseaseName(final String diseaseName) {
            this.diseaseName = diseaseName;
            return this;
        }

        /**
         * Sets the new value of "diseaseNameDK" (any previous value will be replaced)
         * 
         * @param diseaseNameDK
         *     New value of the "diseaseNameDK" property.
         */
        public DiseaseType.Builder<_B> withDiseaseNameDK(final String diseaseNameDK) {
            this.diseaseNameDK = diseaseNameDK;
            return this;
        }

        /**
         * Sets the new value of "atc" (any previous value will be replaced)
         * 
         * @param atc
         *     New value of the "atc" property.
         */
        public DiseaseType.Builder<_B> withATC(final ATCType atc) {
            this.atc = ((atc == null)?null:new ATCType.Builder<>(this, atc, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the "atc"
         * property.
         * Use {@link dk.vaccinationsregister.schemas._2013._12._01.ATCType.Builder#end()}
         * to return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "atc" property.
         *     Use {@link dk.vaccinationsregister.schemas._2013._12._01.ATCType.Builder#end()}
         *     to return to the current builder.
         */
        public ATCType.Builder<? extends DiseaseType.Builder<_B>> withATC() {
            if (this.atc!= null) {
                return this.atc;
            }
            return this.atc = new ATCType.Builder<>(this, null, false);
        }

        @Override
        public DiseaseType build() {
            if (_storedValue == null) {
                return this.init(new DiseaseType());
            } else {
                return ((DiseaseType) _storedValue);
            }
        }

        public DiseaseType.Builder<_B> copyOf(final DiseaseType _other) {
            _other.copyTo(this);
            return this;
        }

        public DiseaseType.Builder<_B> copyOf(final DiseaseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends DiseaseType.Selector<DiseaseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static DiseaseType.Select _root() {
            return new DiseaseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> diseaseIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> diseaseName = null;
        private com.kscs.util.jaxb.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> diseaseNameDK = null;
        private ATCType.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> atc = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.diseaseIdentifier!= null) {
                products.put("diseaseIdentifier", this.diseaseIdentifier.init());
            }
            if (this.diseaseName!= null) {
                products.put("diseaseName", this.diseaseName.init());
            }
            if (this.diseaseNameDK!= null) {
                products.put("diseaseNameDK", this.diseaseNameDK.init());
            }
            if (this.atc!= null) {
                products.put("atc", this.atc.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> diseaseIdentifier() {
            return ((this.diseaseIdentifier == null)?this.diseaseIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "diseaseIdentifier"):this.diseaseIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> diseaseName() {
            return ((this.diseaseName == null)?this.diseaseName = new com.kscs.util.jaxb.Selector<>(this._root, this, "diseaseName"):this.diseaseName);
        }

        public com.kscs.util.jaxb.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> diseaseNameDK() {
            return ((this.diseaseNameDK == null)?this.diseaseNameDK = new com.kscs.util.jaxb.Selector<>(this._root, this, "diseaseNameDK"):this.diseaseNameDK);
        }

        public ATCType.Selector<TRoot, DiseaseType.Selector<TRoot, TParent>> atc() {
            return ((this.atc == null)?this.atc = new ATCType.Selector<>(this._root, this, "atc"):this.atc);
        }

    }

}
