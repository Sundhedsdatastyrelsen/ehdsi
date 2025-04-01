package oasis.names.tc.saml._2_0.assertion;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
* This object contains factory methods for each 
* Java content interface and Java element interface 
* generated in the oasis.names.tc.saml._2_0.assertion package. 
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
  private static final QName _Issuer_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer");
 private static final QName _ConfirmationMethod_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ConfirmationMethod");
 private static final QName _AttributeValue_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeValue");
  /**
 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: oasis.names.tc.saml._2_0.assertion
 * 
 */
 public ObjectFactory() {
 }
  /**
 * Create an instance of {@link Assertion }
 * 
 * @return
 *     the new instance of {@link Assertion }
 */
 public Assertion createAssertion() {
 return new Assertion();
 }
  /**
 * Create an instance of {@link Subject }
 * 
 * @return
 *     the new instance of {@link Subject }
 */
 public Subject createSubject() {
 return new Subject();
 }
  /**
 * Create an instance of {@link NameID }
 * 
 * @return
 *     the new instance of {@link NameID }
 */
 public NameID createNameID() {
 return new NameID();
 }
  /**
 * Create an instance of {@link SubjectConfirmation }
 * 
 * @return
 *     the new instance of {@link SubjectConfirmation }
 */
 public SubjectConfirmation createSubjectConfirmation() {
 return new SubjectConfirmation();
 }
  /**
 * Create an instance of {@link SubjectConfirmationData }
 * 
 * @return
 *     the new instance of {@link SubjectConfirmationData }
 */
 public SubjectConfirmationData createSubjectConfirmationData() {
 return new SubjectConfirmationData();
 }
  /**
 * Create an instance of {@link Conditions }
 * 
 * @return
 *     the new instance of {@link Conditions }
 */
 public Conditions createConditions() {
 return new Conditions();
 }
  /**
 * Create an instance of {@link AttributeStatement }
 * 
 * @return
 *     the new instance of {@link AttributeStatement }
 */
 public AttributeStatement createAttributeStatement() {
 return new AttributeStatement();
 }
  /**
 * Create an instance of {@link Attribute }
 * 
 * @return
 *     the new instance of {@link Attribute }
 */
 public Attribute createAttribute() {
 return new Attribute();
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Issuer")
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 public JAXBElement<String> createIssuer(String value) {
 return new JAXBElement<>(_Issuer_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "ConfirmationMethod")
 public JAXBElement<String> createConfirmationMethod(String value) {
 return new JAXBElement<>(_ConfirmationMethod_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AttributeValue")
 public JAXBElement<String> createAttributeValue(String value) {
 return new JAXBElement<>(_AttributeValue_QNAME, String.class, null, value);
 }
 }
