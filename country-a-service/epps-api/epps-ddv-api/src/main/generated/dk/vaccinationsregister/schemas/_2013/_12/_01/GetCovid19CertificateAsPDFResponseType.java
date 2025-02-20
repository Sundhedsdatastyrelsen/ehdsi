
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
 * <p>Java class for GetCovid19CertificateAsPDFResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="GetCovid19CertificateAsPDFResponseType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <choice>
 *           <sequence>
 *             <element name="PDF" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *             <element name="CoronaPasses" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PassListType"/>
 *           </sequence>
 *           <element name="FutureResult" type="{http://vaccinationsregister.dk/schemas/2013/12/01}FutureResultType" minOccurs="0"/>
 *         </choice>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCovid19CertificateAsPDFResponseType", propOrder = {
    "pdf",
    "coronaPasses",
    "futureResult"
})
public class GetCovid19CertificateAsPDFResponseType {

    @XmlElement(name = "PDF")
    protected byte[] pdf;
    @XmlElement(name = "CoronaPasses")
    protected PassListType coronaPasses;
    @XmlElement(name = "FutureResult")
    protected FutureResultType futureResult;

    /**
     * Gets the value of the pdf property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPDF() {
        return pdf;
    }

    /**
     * Sets the value of the pdf property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPDF(byte[] value) {
        this.pdf = value;
    }

    /**
     * Gets the value of the coronaPasses property.
     * 
     * @return
     *     possible object is
     *     {@link PassListType }
     *     
     */
    public PassListType getCoronaPasses() {
        return coronaPasses;
    }

    /**
     * Sets the value of the coronaPasses property.
     * 
     * @param value
     *     allowed object is
     *     {@link PassListType }
     *     
     */
    public void setCoronaPasses(PassListType value) {
        this.coronaPasses = value;
    }

    /**
     * Gets the value of the futureResult property.
     * 
     * @return
     *     possible object is
     *     {@link FutureResultType }
     *     
     */
    public FutureResultType getFutureResult() {
        return futureResult;
    }

    /**
     * Sets the value of the futureResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FutureResultType }
     *     
     */
    public void setFutureResult(FutureResultType value) {
        this.futureResult = value;
    }

    /**
     * Copies all state of this object to a builder. This method is used by the {@link
     * #copyOf} method and should not be called directly by client code.
     * 
     * @param _other
     *     A builder instance to which the state of this object will be copied.
     */
    public<_B >void copyTo(final GetCovid19CertificateAsPDFResponseType.Builder<_B> _other) {
        _other.pdf = this.pdf;
        _other.coronaPasses = ((this.coronaPasses == null)?null:this.coronaPasses.newCopyBuilder(_other));
        _other.futureResult = ((this.futureResult == null)?null:this.futureResult.newCopyBuilder(_other));
    }

    public<_B >GetCovid19CertificateAsPDFResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
        return new GetCovid19CertificateAsPDFResponseType.Builder<_B>(_parentBuilder, this, true);
    }

    public GetCovid19CertificateAsPDFResponseType.Builder<Void> newCopyBuilder() {
        return newCopyBuilder(null);
    }

    public static GetCovid19CertificateAsPDFResponseType.Builder<Void> builder() {
        return new GetCovid19CertificateAsPDFResponseType.Builder<>(null, null, false);
    }

    public static<_B >GetCovid19CertificateAsPDFResponseType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFResponseType _other) {
        final GetCovid19CertificateAsPDFResponseType.Builder<_B> _newBuilder = new GetCovid19CertificateAsPDFResponseType.Builder<>(null, null, false);
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
    public<_B >void copyTo(final GetCovid19CertificateAsPDFResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final PropertyTree pdfPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("pdf"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(pdfPropertyTree!= null):((pdfPropertyTree == null)||(!pdfPropertyTree.isLeaf())))) {
            _other.pdf = this.pdf;
        }
        final PropertyTree coronaPassesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coronaPasses"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coronaPassesPropertyTree!= null):((coronaPassesPropertyTree == null)||(!coronaPassesPropertyTree.isLeaf())))) {
            _other.coronaPasses = ((this.coronaPasses == null)?null:this.coronaPasses.newCopyBuilder(_other, coronaPassesPropertyTree, _propertyTreeUse));
        }
        final PropertyTree futureResultPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("futureResult"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(futureResultPropertyTree!= null):((futureResultPropertyTree == null)||(!futureResultPropertyTree.isLeaf())))) {
            _other.futureResult = ((this.futureResult == null)?null:this.futureResult.newCopyBuilder(_other, futureResultPropertyTree, _propertyTreeUse));
        }
    }

    public<_B >GetCovid19CertificateAsPDFResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return new GetCovid19CertificateAsPDFResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
    }

    public GetCovid19CertificateAsPDFResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
    }

    public static<_B >GetCovid19CertificateAsPDFResponseType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
        final GetCovid19CertificateAsPDFResponseType.Builder<_B> _newBuilder = new GetCovid19CertificateAsPDFResponseType.Builder<>(null, null, false);
        _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
        return _newBuilder;
    }

    public static GetCovid19CertificateAsPDFResponseType.Builder<Void> copyExcept(final GetCovid19CertificateAsPDFResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
    }

    public static GetCovid19CertificateAsPDFResponseType.Builder<Void> copyOnly(final GetCovid19CertificateAsPDFResponseType _other, final PropertyTree _propertyTree) {
        return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
    }

    public static class Builder<_B >implements Buildable
    {

        protected final _B _parentBuilder;
        protected final GetCovid19CertificateAsPDFResponseType _storedValue;
        private byte[] pdf;
        private PassListType.Builder<GetCovid19CertificateAsPDFResponseType.Builder<_B>> coronaPasses;
        private FutureResultType.Builder<GetCovid19CertificateAsPDFResponseType.Builder<_B>> futureResult;

        public Builder(final _B _parentBuilder, final GetCovid19CertificateAsPDFResponseType _other, final boolean _copy) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    this.pdf = _other.pdf;
                    this.coronaPasses = ((_other.coronaPasses == null)?null:_other.coronaPasses.newCopyBuilder(this));
                    this.futureResult = ((_other.futureResult == null)?null:_other.futureResult.newCopyBuilder(this));
                } else {
                    _storedValue = _other;
                }
            } else {
                _storedValue = null;
            }
        }

        public Builder(final _B _parentBuilder, final GetCovid19CertificateAsPDFResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
            this._parentBuilder = _parentBuilder;
            if (_other!= null) {
                if (_copy) {
                    _storedValue = null;
                    final PropertyTree pdfPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("pdf"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(pdfPropertyTree!= null):((pdfPropertyTree == null)||(!pdfPropertyTree.isLeaf())))) {
                        this.pdf = _other.pdf;
                    }
                    final PropertyTree coronaPassesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coronaPasses"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coronaPassesPropertyTree!= null):((coronaPassesPropertyTree == null)||(!coronaPassesPropertyTree.isLeaf())))) {
                        this.coronaPasses = ((_other.coronaPasses == null)?null:_other.coronaPasses.newCopyBuilder(this, coronaPassesPropertyTree, _propertyTreeUse));
                    }
                    final PropertyTree futureResultPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("futureResult"));
                    if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(futureResultPropertyTree!= null):((futureResultPropertyTree == null)||(!futureResultPropertyTree.isLeaf())))) {
                        this.futureResult = ((_other.futureResult == null)?null:_other.futureResult.newCopyBuilder(this, futureResultPropertyTree, _propertyTreeUse));
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

        protected<_P extends GetCovid19CertificateAsPDFResponseType >_P init(final _P _product) {
            _product.pdf = this.pdf;
            _product.coronaPasses = ((this.coronaPasses == null)?null:this.coronaPasses.build());
            _product.futureResult = ((this.futureResult == null)?null:this.futureResult.build());
            return _product;
        }

        /**
         * Sets the new value of "pdf" (any previous value will be replaced)
         * 
         * @param pdf
         *     New value of the "pdf" property.
         */
        public GetCovid19CertificateAsPDFResponseType.Builder<_B> withPDF(final byte[] pdf) {
            this.pdf = pdf;
            return this;
        }

        /**
         * Sets the new value of "coronaPasses" (any previous value will be replaced)
         * 
         * @param coronaPasses
         *     New value of the "coronaPasses" property.
         */
        public GetCovid19CertificateAsPDFResponseType.Builder<_B> withCoronaPasses(final PassListType coronaPasses) {
            this.coronaPasses = ((coronaPasses == null)?null:new PassListType.Builder<>(this, coronaPasses, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "coronaPasses" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.PassListType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "coronaPasses" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.PassListType.Builder#end()} to
         *     return to the current builder.
         */
        public PassListType.Builder<? extends GetCovid19CertificateAsPDFResponseType.Builder<_B>> withCoronaPasses() {
            if (this.coronaPasses!= null) {
                return this.coronaPasses;
            }
            return this.coronaPasses = new PassListType.Builder<>(this, null, false);
        }

        /**
         * Sets the new value of "futureResult" (any previous value will be replaced)
         * 
         * @param futureResult
         *     New value of the "futureResult" property.
         */
        public GetCovid19CertificateAsPDFResponseType.Builder<_B> withFutureResult(final FutureResultType futureResult) {
            this.futureResult = ((futureResult == null)?null:new FutureResultType.Builder<>(this, futureResult, false));
            return this;
        }

        /**
         * Returns the existing builder or a new builder to build the value of the
         * "futureResult" property.
         * Use {@link
         * dk.vaccinationsregister.schemas._2013._12._01.FutureResultType.Builder#end()} to
         * return to the current builder.
         * 
         * @return
         *     A new builder to build the value of the "futureResult" property.
         *     Use {@link
         *     dk.vaccinationsregister.schemas._2013._12._01.FutureResultType.Builder#end()} to
         *     return to the current builder.
         */
        public FutureResultType.Builder<? extends GetCovid19CertificateAsPDFResponseType.Builder<_B>> withFutureResult() {
            if (this.futureResult!= null) {
                return this.futureResult;
            }
            return this.futureResult = new FutureResultType.Builder<>(this, null, false);
        }

        @Override
        public GetCovid19CertificateAsPDFResponseType build() {
            if (_storedValue == null) {
                return this.init(new GetCovid19CertificateAsPDFResponseType());
            } else {
                return ((GetCovid19CertificateAsPDFResponseType) _storedValue);
            }
        }

        public GetCovid19CertificateAsPDFResponseType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFResponseType _other) {
            _other.copyTo(this);
            return this;
        }

        public GetCovid19CertificateAsPDFResponseType.Builder<_B> copyOf(final GetCovid19CertificateAsPDFResponseType.Builder _other) {
            return copyOf(_other.build());
        }

    }

    public static class Select
        extends GetCovid19CertificateAsPDFResponseType.Selector<GetCovid19CertificateAsPDFResponseType.Select, Void>
    {


        Select() {
            super(null, null, null);
        }

        public static GetCovid19CertificateAsPDFResponseType.Select _root() {
            return new GetCovid19CertificateAsPDFResponseType.Select();
        }

    }

    public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
        extends com.kscs.util.jaxb.Selector<TRoot, TParent>
    {

        private com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFResponseType.Selector<TRoot, TParent>> pdf = null;
        private PassListType.Selector<TRoot, GetCovid19CertificateAsPDFResponseType.Selector<TRoot, TParent>> coronaPasses = null;
        private FutureResultType.Selector<TRoot, GetCovid19CertificateAsPDFResponseType.Selector<TRoot, TParent>> futureResult = null;

        public Selector(final TRoot root, final TParent parent, final String propertyName) {
            super(root, parent, propertyName);
        }

        @Override
        public Map<String, PropertyTree> buildChildren() {
            final Map<String, PropertyTree> products = new HashMap<>();
            products.putAll(super.buildChildren());
            if (this.pdf!= null) {
                products.put("pdf", this.pdf.init());
            }
            if (this.coronaPasses!= null) {
                products.put("coronaPasses", this.coronaPasses.init());
            }
            if (this.futureResult!= null) {
                products.put("futureResult", this.futureResult.init());
            }
            return products;
        }

        public com.kscs.util.jaxb.Selector<TRoot, GetCovid19CertificateAsPDFResponseType.Selector<TRoot, TParent>> pdf() {
            return ((this.pdf == null)?this.pdf = new com.kscs.util.jaxb.Selector<>(this._root, this, "pdf"):this.pdf);
        }

        public PassListType.Selector<TRoot, GetCovid19CertificateAsPDFResponseType.Selector<TRoot, TParent>> coronaPasses() {
            return ((this.coronaPasses == null)?this.coronaPasses = new PassListType.Selector<>(this._root, this, "coronaPasses"):this.coronaPasses);
        }

        public FutureResultType.Selector<TRoot, GetCovid19CertificateAsPDFResponseType.Selector<TRoot, TParent>> futureResult() {
            return ((this.futureResult == null)?this.futureResult = new FutureResultType.Selector<>(this._root, this, "futureResult"):this.futureResult);
        }

    }

}
