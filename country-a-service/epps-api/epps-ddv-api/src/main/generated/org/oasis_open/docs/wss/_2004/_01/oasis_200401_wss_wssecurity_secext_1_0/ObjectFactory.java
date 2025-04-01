package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
* This object contains factory methods for each 
* Java content interface and Java element interface 
* generated in the org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0 package. 
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
  private static final QName _Username_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Username");
 private static final QName _Password_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password");
  /**
 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0
 * 
 */
 public ObjectFactory() {
 }
  /**
 * Create an instance of {@link Security }
 * 
 * @return
 *     the new instance of {@link Security }
 */
 public Security createSecurity() {
 return new Security();
 }
  /**
 * Create an instance of {@link UsernameToken }
 * 
 * @return
 *     the new instance of {@link UsernameToken }
 */
 public UsernameToken createUsernameToken() {
 return new UsernameToken();
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name = "Username")
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 public JAXBElement<String> createUsername(String value) {
 return new JAXBElement<>(_Username_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name = "Password")
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 public JAXBElement<String> createPassword(String value) {
 return new JAXBElement<>(_Password_QNAME, String.class, null, value);
 }
 }
