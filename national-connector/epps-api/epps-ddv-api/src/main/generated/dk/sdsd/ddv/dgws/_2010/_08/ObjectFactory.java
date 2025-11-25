
package dk.sdsd.ddv.dgws._2010._08;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dk.sdsd.dgws._2010._08 package. 
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

    private static final QName _SystemOwnerName_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "SystemOwnerName");
    private static final QName _SystemName_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "SystemName");
    private static final QName _SystemVersion_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "SystemVersion");
    private static final QName _OrgResponsibleName_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "OrgResponsibleName");
    private static final QName _OrgUsingName_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "OrgUsingName");
    private static final QName _NameFormat_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "NameFormat");
    private static final QName _OrgUsingID_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "OrgUsingID");
    private static final QName _RequestedRole_QNAME = new QName("http://www.sdsd.dk/dgws/2010/08", "RequestedRole");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.sdsd.dgws._2010._08
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OrgUsingID }
     * 
     * @return
     *     the new instance of {@link OrgUsingID }
     */
    public OrgUsingID createOrgUsingID() {
        return new OrgUsingID();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "SystemOwnerName")
    public JAXBElement<String> createSystemOwnerName(String value) {
        return new JAXBElement<>(_SystemOwnerName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "SystemName")
    public JAXBElement<String> createSystemName(String value) {
        return new JAXBElement<>(_SystemName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "SystemVersion")
    public JAXBElement<String> createSystemVersion(String value) {
        return new JAXBElement<>(_SystemVersion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "OrgResponsibleName")
    public JAXBElement<String> createOrgResponsibleName(String value) {
        return new JAXBElement<>(_OrgResponsibleName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "OrgUsingName")
    public JAXBElement<String> createOrgUsingName(String value) {
        return new JAXBElement<>(_OrgUsingName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameFormat }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NameFormat }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "NameFormat")
    public JAXBElement<NameFormat> createNameFormat(NameFormat value) {
        return new JAXBElement<>(_NameFormat_QNAME, NameFormat.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrgUsingID }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrgUsingID }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "OrgUsingID")
    public JAXBElement<OrgUsingID> createOrgUsingID(OrgUsingID value) {
        return new JAXBElement<>(_OrgUsingID_QNAME, OrgUsingID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sdsd.dk/dgws/2010/08", name = "RequestedRole")
    public JAXBElement<String> createRequestedRole(String value) {
        return new JAXBElement<>(_RequestedRole_QNAME, String.class, null, value);
    }

}
