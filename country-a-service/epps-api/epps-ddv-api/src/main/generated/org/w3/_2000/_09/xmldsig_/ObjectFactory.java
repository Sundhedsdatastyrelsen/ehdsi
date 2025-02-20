
package org.w3._2000._09.xmldsig_;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.w3._2000._09.xmldsig_ package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _KeyName_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyName");
    private static final QName _X509Certificate_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
    private static final QName _DigestValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
    private static final QName _SignatureValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureValue");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.w3._2000._09.xmldsig_
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Transforms }
     * 
     * @return
     *     the new instance of {@link Transforms }
     */
    public Transforms createTransforms() {
        return new Transforms();
    }

    /**
     * Create an instance of {@link KeyInfo }
     * 
     * @return
     *     the new instance of {@link KeyInfo }
     */
    public KeyInfo createKeyInfo() {
        return new KeyInfo();
    }

    /**
     * Create an instance of {@link X509Data }
     * 
     * @return
     *     the new instance of {@link X509Data }
     */
    public X509Data createX509Data() {
        return new X509Data();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     * @return
     *     the new instance of {@link Signature }
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link SignedInfo }
     * 
     * @return
     *     the new instance of {@link SignedInfo }
     */
    public SignedInfo createSignedInfo() {
        return new SignedInfo();
    }

    /**
     * Create an instance of {@link CanonicalizationMethod }
     * 
     * @return
     *     the new instance of {@link CanonicalizationMethod }
     */
    public CanonicalizationMethod createCanonicalizationMethod() {
        return new CanonicalizationMethod();
    }

    /**
     * Create an instance of {@link SignatureMethod }
     * 
     * @return
     *     the new instance of {@link SignatureMethod }
     */
    public SignatureMethod createSignatureMethod() {
        return new SignatureMethod();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     * @return
     *     the new instance of {@link Reference }
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link Transforms.Transform }
     * 
     * @return
     *     the new instance of {@link Transforms.Transform }
     */
    public Transforms.Transform createTransformsTransform() {
        return new Transforms.Transform();
    }

    /**
     * Create an instance of {@link DigestMethod }
     * 
     * @return
     *     the new instance of {@link DigestMethod }
     */
    public DigestMethod createDigestMethod() {
        return new DigestMethod();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createKeyName(String value) {
        return new JAXBElement<>(_KeyName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Certificate")
    public JAXBElement<byte[]> createX509Certificate(byte[] value) {
        return new JAXBElement<>(_X509Certificate_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestValue")
    public JAXBElement<byte[]> createDigestValue(byte[] value) {
        return new JAXBElement<>(_DigestValue_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureValue")
    public JAXBElement<byte[]> createSignatureValue(byte[] value) {
        return new JAXBElement<>(_SignatureValue_QNAME, byte[].class, null, ((byte[]) value));
    }

}
