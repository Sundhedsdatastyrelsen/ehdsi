
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
 * <p>Java class for GetCovid19CertificateAsPDFRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetCovid19CertificateAsPDFRequestType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
 *         <element name="IncludePdf" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="IncludePasses" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="IncludeVaccinations" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="IncludeRestitutions" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCovid19CertificateAsPDFRequestType", propOrder = {
    "personCivilRegistrationIdentifier",
    "includePdf",
    "includePasses",
    "includeVaccinations",
    "includeRestitutions"
})
public class GetCovid19CertificateAsPDFRequestType {

    @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
    protected String personCivilRegistrationIdentifier;
    @XmlElement(name = "IncludePdf")
    protected Boolean includePdf;
    @XmlElement(name = "IncludePasses")
    protected Boolean includePasses;
    @XmlElement(name = "IncludeVaccinations")
    protected Boolean includeVaccinations;
    @XmlElement(name = "IncludeRestitutions")
    protected Boolean includeRestitutions;

    /**
     * Gets the value of the personCivilRegistrationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonCivilRegistrationIdentifier() {
        return personCivilRegistrationIdentifier;
    }

    /**
     * Sets the value of the personCivilRegistrationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonCivilRegistrationIdentifier(String value) {
        this.personCivilRegistrationIdentifier = value;
    }

    /**
     * Gets the value of the includePdf property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludePdf() {
        return includePdf;
    }

    /**
     * Sets the value of the includePdf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludePdf(Boolean value) {
        this.includePdf = value;
    }

    /**
     * Gets the value of the includePasses property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludePasses() {
        return includePasses;
    }

    /**
     * Sets the value of the includePasses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludePasses(Boolean value) {
        this.includePasses = value;
    }

    /**
     * Gets the value of the includeVaccinations property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeVaccinations() {
        return includeVaccinations;
    }

    /**
     * Sets the value of the includeVaccinations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeVaccinations(Boolean value) {
        this.includeVaccinations = value;
    }

    /**
     * Gets the value of the includeRestitutions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeRestitutions() {
        return includeRestitutions;
    }

    /**
     * Sets the value of the includeRestitutions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeRestitutions(Boolean value) {
        this.includeRestitutions = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetCovid19CertificateAsPDFRequestType.Builder<_B> _other) {
        _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        _other.includePdf = this.includePdf;
        _other.includePasses = this.includePasses;
        _other.includeVaccinations = this.includeVaccinations;
        _other.includeRestitutions = this.includeRestitutions;
    }

    public<_B >GetCovid19CertificateAsPDFRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetCovid19CertificateAsPDFRequestType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetCovid19CertificateAsPDFRequestType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetCovid19CertificateAsPDFRequestType.Builder<Void> builder() {
        return new GetCovid19CertificateAsPDFRequestType.Builder<>(null, null, false);
    }

    public static<_B >GetCovid19CertificateAsPDFRequestType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFRequestType _other) {
        final GetCovid19CertificateAsPDFRequestType.Builder<_B> _newBuilder = new GetCovid19CertificateAsPDFRequestType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetCovid19CertificateAsPDFRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
            _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
        }
        final PropertyTree includePdfPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includePdf"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includePdfPropertyTree!= null):((includePdfPropertyTree == null)||(!includePdfPropertyTree.isLeaf())))) {
            _other.includePdf = this.includePdf;
        }
        final PropertyTree includePassesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includePasses"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includePassesPropertyTree!= null):((includePassesPropertyTree == null)||(!includePassesPropertyTree.isLeaf())))) {
            _other.includePasses = this.includePasses;
        }
        final PropertyTree includeVaccinationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinations"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinationsPropertyTree!= null):((includeVaccinationsPropertyTree == null)||(!includeVaccinationsPropertyTree.isLeaf())))) {
            _other.includeVaccinations = this.includeVaccinations;
        }
        final PropertyTree includeRestitutionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeRestitutions"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeRestitutionsPropertyTree!= null):((includeRestitutionsPropertyTree == null)||(!includeRestitutionsPropertyTree.isLeaf())))) {
            _other.includeRestitutions = this.includeRestitutions;
        }
    }

    public<_B >GetCovid19CertificateAsPDFRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetCovid19CertificateAsPDFRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetCovid19CertificateAsPDFRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetCovid19CertificateAsPDFRequestType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetCovid19CertificateAsPDFRequestType.Builder<_B> _newBuilder = new GetCovid19CertificateAsPDFRequestType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetCovid19CertificateAsPDFRequestType.Builder<Void> copyExcept(final GetCovid19CertificateAsPDFRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetCovid19CertificateAsPDFRequestType.Builder<Void> copyOnly(final GetCovid19CertificateAsPDFRequestType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetCovid19CertificateAsPDFRequestType _storedValue;
        private String personCivilRegistrationIdentifier;
        private Boolean includePdf;
        private Boolean includePasses;
        private Boolean includeVaccinations;
        private Boolean includeRestitutions;

        public Builder(final _B _parentBuilder, final GetCovid19CertificateAsPDFRequestType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    this.includePdf = _other.includePdf;
                    this.includePasses = _other.includePasses;
                    this.includeVaccinations = _other.includeVaccinations;
                    this.includeRestitutions = _other.includeRestitutions;
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetCovid19CertificateAsPDFRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
                        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
                    }
                    final PropertyTree includePdfPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includePdf"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includePdfPropertyTree!= null):((includePdfPropertyTree == null)||(!includePdfPropertyTree.isLeaf())))) {
                        this.includePdf = _other.includePdf;
                    }
                    final PropertyTree includePassesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includePasses"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includePassesPropertyTree!= null):((includePassesPropertyTree == null)||(!includePassesPropertyTree.isLeaf())))) {
                        this.includePasses = _other.includePasses;
                    }
                    final PropertyTree includeVaccinationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinations"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinationsPropertyTree!= null):((includeVaccinationsPropertyTree == null)||(!includeVaccinationsPropertyTree.isLeaf())))) {
                        this.includeVaccinations = _other.includeVaccinations;
                    }
                    final PropertyTree includeRestitutionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeRestitutions"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeRestitutionsPropertyTree!= null):((includeRestitutionsPropertyTree == null)||(!includeRestitutionsPropertyTree.isLeaf())))) {
                        this.includeRestitutions = _other.includeRestitutions;
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

        protected<_P extends GetCovid19CertificateAsPDFRequestType >_P init(final _P _product) {
            _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
            _product.includePdf = this.includePdf;
            _product.includePasses = this.includePasses;
            _product.includeVaccinations = this.includeVaccinations;
            _product.includeRestitutions = this.includeRestitutions;
            return _product;
        }

        /**
         * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
         * will be replaced)
         * 
         * @param personCivilRegistrationIdentifier
         *     New value of the "personCivilRegistrationIdentifier" property.
         */
        public GetCovid19CertificateAsPDFRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
            this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
            return this;
        }

        /**
         * Sets the new value of "includePdf" (any previous value will be replaced)
         * 
         * @param includePdf
         *     New value of the "includePdf" property.
         */
        public GetCovid19CertificateAsPDFRequestType.Builder<_B> withIncludePdf(final Boolean includePdf) {
            this.includePdf = includePdf;
            return this;
        }

        /**
         * Sets the new value of "includePasses" (any previous value will be replaced)
         * 
         * @param includePasses
         *     New value of the "includePasses" property.
         */
        public GetCovid19CertificateAsPDFRequestType.Builder<_B> withIncludePasses(final Boolean includePasses) {
            this.includePasses = includePasses;
            return this;
        }

        /**
         * Sets the new value of "includeVaccinations" (any previous value will be
         * replaced)
         * 
         * @param includeVaccinations
         *     New value of the "includeVaccinations" property.
         */
        public GetCovid19CertificateAsPDFRequestType.Builder<_B> withIncludeVaccinations(final Boolean includeVaccinations) {
            this.includeVaccinations = includeVaccinations;
            return this;
        }

        /**
         * Sets the new value of "includeRestitutions" (any previous value will be
         * replaced)
         * 
         * @param includeRestitutions
         *     New value of the "includeRestitutions" property.
         */
        public GetCovid19CertificateAsPDFRequestType.Builder<_B> withIncludeRestitutions(final Boolean includeRestitutions) {
            this.includeRestitutions = includeRestitutions;
            return this;
        }

        @Override
        public GetCovid19CertificateAsPDFRequestType build() {
            if (_storedValue == null) {
                return this.init(new GetCovid19CertificateAsPDFRequestType());
            } else {
                return ((GetCovid19CertificateAsPDFRequestType) _storedValue);
            }
        }

        public GetCovid19CertificateAsPDFRequestType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFRequestType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetCovid19CertificateAsPDFRequestType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFRequestType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetCovid19CertificateAsPDFRequestType.Selector<GetCovid19CertificateAsPDFRequestType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetCovid19CertificateAsPDFRequestType.Select _root() {
            return new GetCovid19CertificateAsPDFRequestType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includePdf = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includePasses = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includeVaccinations = null;
        private com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includeRestitutions = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.personCivilRegistrationIdentifier!= null) {
                products.put("personCivilRegistrationIdentifier", this.personCivilRegistrationIdentifier.init());
            }
            if (this.includePdf!= null) {
                products.put("includePdf", this.includePdf.init());
            }
            if (this.includePasses!= null) {
                products.put("includePasses", this.includePasses.init());
            }
            if (this.includeVaccinations!= null) {
                products.put("includeVaccinations", this.includeVaccinations.init());
            }
            if (this.includeRestitutions!= null) {
                products.put("includeRestitutions", this.includeRestitutions.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
            return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includePdf() {
            return ((this.includePdf == null)?this.includePdf = new com.kscs.util.jaxb.Selector<>(this._root, this, "includePdf"):this.includePdf);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includePasses() {
            return ((this.includePasses == null)?this.includePasses = new com.kscs.util.jaxb.Selector<>(this._root, this, "includePasses"):this.includePasses);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includeVaccinations() {
            return ((this.includeVaccinations == null)?this.includeVaccinations = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeVaccinations"):this.includeVaccinations);
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFRequestType.Selector<TRoot, TParent>> includeRestitutions() {
            return ((this.includeRestitutions == null)?this.includeRestitutions = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeRestitutions"):this.includeRestitutions);
        }

    }

}
