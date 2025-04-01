package dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
/**
* This object contains factory methods for each 
* Java content interface and Java element interface 
* generated in the dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17 package. 
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
  private static final QName _SimpleCPRPerson_QNAME = new QName("http://rep.oio.dk/cpr.dk/xml/schemas/core/2006/01/17/", "SimpleCPRPerson");
  /**
 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17
 * 
 */
 public ObjectFactory() {
 }
  /**
 * Create an instance of {@link SimpleCPRPersonType }
 * 
 * @return
 *     the new instance of {@link SimpleCPRPersonType }
 */
 public SimpleCPRPersonType createSimpleCPRPersonType() {
 return new SimpleCPRPersonType();
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link SimpleCPRPersonType }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link SimpleCPRPersonType }{@code >}
 */
 @XmlElementDecl(namespace = "http://rep.oio.dk/cpr.dk/xml/schemas/core/2006/01/17/", name = "SimpleCPRPerson")
 public JAXBElement<SimpleCPRPersonType> createSimpleCPRPerson(SimpleCPRPersonType value) {
 return new JAXBElement<>(_SimpleCPRPerson_QNAME, SimpleCPRPersonType.class, null, value);
 }
 }
