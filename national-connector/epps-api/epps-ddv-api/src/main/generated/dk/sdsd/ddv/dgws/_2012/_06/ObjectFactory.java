
package dk.sdsd.ddv.dgws._2012._06;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dk.sdsd.dgws._2012._06 package. 
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

    private static final QName _WhiteListingHeader_QNAME = new QName("http://www.sdsd.dk/dgws/2012/06", "WhiteListingHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.sdsd.dgws._2012._06
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WhiteListingHeader }
     * 
     * @return
     *     the new instance of {@link WhiteListingHeader }
     */
    public WhiteListingHeader createWhiteListingHeader() {
        return new WhiteListingHeader();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WhiteListingHeader }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link WhiteListingHeader }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2012/06", name = "WhiteListingHeader")
    public JAXBElement<WhiteListingHeader> createWhiteListingHeader(WhiteListingHeader value) {
        return new JAXBElement<>(_WhiteListingHeader_QNAME, WhiteListingHeader.class, null, value);
    }

}
