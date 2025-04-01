package dk.medcom.dgws._2006._04.dgws_1_0;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
/**
* This object contains factory methods for each 
* Java content interface and Java element interface 
* generated in the dk.medcom.dgws._2006._04.dgws_1_0 package. 
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
  private static final QName _FlowID_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "FlowID");
 private static final QName _MessageID_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "MessageID");
 private static final QName _InResponseToMessageID_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "InResponseToMessageID");
 private static final QName _Priority_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "Priority");
 private static final QName _RequireNonRepudiationReceipt_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "RequireNonRepudiationReceipt");
 private static final QName _FlowStatus_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "FlowStatus");
 private static final QName _SecurityLevel_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "SecurityLevel");
 private static final QName _TimeOut_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "TimeOut");
 private static final QName _FaultCode_QNAME = new QName("http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", "FaultCode");
  /**
 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.medcom.dgws._2006._04.dgws_1_0
 * 
 */
 public ObjectFactory() {
 }
  /**
 * Create an instance of {@link Linking }
 * 
 * @return
 *     the new instance of {@link Linking }
 */
 public Linking createLinking() {
 return new Linking();
 }
  /**
 * Create an instance of {@link Header }
 * 
 * @return
 *     the new instance of {@link Header }
 */
 public Header createHeader() {
 return new Header();
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "FlowID")
 public JAXBElement<String> createFlowID(String value) {
 return new JAXBElement<>(_FlowID_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "MessageID")
 public JAXBElement<String> createMessageID(String value) {
 return new JAXBElement<>(_MessageID_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "InResponseToMessageID")
 public JAXBElement<String> createInResponseToMessageID(String value) {
 return new JAXBElement<>(_InResponseToMessageID_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "Priority")
 public JAXBElement<String> createPriority(String value) {
 return new JAXBElement<>(_Priority_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "RequireNonRepudiationReceipt")
 public JAXBElement<String> createRequireNonRepudiationReceipt(String value) {
 return new JAXBElement<>(_RequireNonRepudiationReceipt_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "FlowStatus")
 public JAXBElement<String> createFlowStatus(String value) {
 return new JAXBElement<>(_FlowStatus_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "SecurityLevel")
 public JAXBElement<Integer> createSecurityLevel(Integer value) {
 return new JAXBElement<>(_SecurityLevel_QNAME, Integer.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "TimeOut")
 public JAXBElement<String> createTimeOut(String value) {
 return new JAXBElement<>(_TimeOut_QNAME, String.class, null, value);
 }
  /**
 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 * 
 * @param value
 *     Java instance representing xml element's value.
 * @return
 *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
 */
 @XmlElementDecl(namespace = "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd", name = "FaultCode")
 public JAXBElement<String> createFaultCode(String value) {
 return new JAXBElement<>(_FaultCode_QNAME, String.class, null, value);
 }
 }
