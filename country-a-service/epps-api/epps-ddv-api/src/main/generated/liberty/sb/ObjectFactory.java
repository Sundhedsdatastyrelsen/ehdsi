
package liberty.sb;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the liberty.sb package. 
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

    private static final QName _Framework_QNAME = new QName("urn:liberty:sb", "Framework");
    private static final QName _IDWSFault_QNAME = new QName("urn:liberty:sb", "IDWSFault");
    private static final QName _Faultcode_QNAME = new QName("urn:liberty:sb", "faultcode");
    private static final QName _Faultstring_QNAME = new QName("urn:liberty:sb", "faultstring");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: liberty.sb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FrameworkType }
     * 
     * @return
     *     the new instance of {@link FrameworkType }
     */
    public FrameworkType createFrameworkType() {
        return new FrameworkType();
    }

    /**
     * Create an instance of {@link IDWSFaultType }
     * 
     * @return
     *     the new instance of {@link IDWSFaultType }
     */
    public IDWSFaultType createIDWSFaultType() {
        return new IDWSFaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FrameworkType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FrameworkType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:liberty:sb", name = "Framework")
    public JAXBElement<FrameworkType> createFramework(FrameworkType value) {
        return new JAXBElement<>(_Framework_QNAME, FrameworkType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IDWSFaultType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IDWSFaultType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:liberty:sb", name = "IDWSFault")
    public JAXBElement<IDWSFaultType> createIDWSFault(IDWSFaultType value) {
        return new JAXBElement<>(_IDWSFault_QNAME, IDWSFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "urn:liberty:sb", name = "faultcode")
    public JAXBElement<String> createFaultcode(String value) {
        return new JAXBElement<>(_Faultcode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "urn:liberty:sb", name = "faultstring")
    public JAXBElement<String> createFaultstring(String value) {
        return new JAXBElement<>(_Faultstring_QNAME, String.class, null, value);
    }

}
