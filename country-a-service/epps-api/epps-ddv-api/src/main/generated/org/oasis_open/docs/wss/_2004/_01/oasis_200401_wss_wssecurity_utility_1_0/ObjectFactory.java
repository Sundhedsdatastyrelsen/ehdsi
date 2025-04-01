package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
/**
* This object contains factory methods for each 
* Java content interface and Java element interface 
* generated in the org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0 package. 
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
  private static final QName _Created_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created");
  /**
 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0
 * 
 */
 public ObjectFactory() {
 }
  /**
 * Create an instance of {@link Timestamp }
 * 
 * @return
 *     the new instance of {@link Timestamp }
 */
 public Timestamp createTimestamp() {
 return new Timestamp();
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
 */
 @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", name = "Created")
 public JAXBElement<XMLGregorianCalendar> createCreated(XMLGregorianCalendar value) {
 return new JAXBElement<>(_Created_QNAME, XMLGregorianCalendar.class, null, value);
 }
 }
