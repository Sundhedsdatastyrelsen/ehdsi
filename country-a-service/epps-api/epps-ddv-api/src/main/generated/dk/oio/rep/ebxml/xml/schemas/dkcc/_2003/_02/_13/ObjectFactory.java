package dk.oio.rep.ebxml.xml.schemas.dkcc._2003._02._13;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
/**
* This object contains factory methods for each 
* Java content interface and Java element interface 
* generated in the dk.oio.rep.ebxml.xml.schemas.dkcc._2003._02._13 package. 
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
  private static final QName _PersonGivenName_QNAME = new QName("http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", "PersonGivenName");
 private static final QName _PersonMiddleName_QNAME = new QName("http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", "PersonMiddleName");
 private static final QName _PersonSurnameName_QNAME = new QName("http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", "PersonSurnameName");
  /**
 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.oio.rep.ebxml.xml.schemas.dkcc._2003._02._13
 * 
 */
 public ObjectFactory() {
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", name = "PersonGivenName")
 public JAXBElement<String> createPersonGivenName(String value) {
 return new JAXBElement<>(_PersonGivenName_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", name = "PersonMiddleName")
 public JAXBElement<String> createPersonMiddleName(String value) {
 return new JAXBElement<>(_PersonMiddleName_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", name = "PersonSurnameName")
 public JAXBElement<String> createPersonSurnameName(String value) {
 return new JAXBElement<>(_PersonSurnameName_QNAME, String.class, null, value);
 }
 }
