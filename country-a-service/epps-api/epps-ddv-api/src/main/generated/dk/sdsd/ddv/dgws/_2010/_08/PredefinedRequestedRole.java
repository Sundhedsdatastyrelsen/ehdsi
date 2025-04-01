package dk.sdsd.ddv.dgws._2010._08;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for PredefinedRequestedRole.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* <pre>{@code
* <simpleType name="PredefinedRequestedRole">
*   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
*     <enumeration value="Læge"/>
*     <enumeration value="Tandlæge"/>
*     <enumeration value="Jordemoder"/>
*     <enumeration value="Sygeplejerske"/>
*     <enumeration value="Social- og sundhedsassistent"/>
*     <enumeration value="Social- og sundhedshjælper"/>
*     <enumeration value="Sundhedsplejerske"/>
*     <enumeration value="Farmaceut"/>
*     <enumeration value="Behandlerfarmaceut"/>
*     <enumeration value="Farmakonom"/>
*     <enumeration value="Assistent for Læge"/>
*     <enumeration value="Assistent for Tandlæge"/>
*     <enumeration value="Assistent for Sygeplejer"/>
*     <enumeration value="Assistent for Jordemoder"/>
*     <enumeration value="Assistent for Social- og sundhedsassistent"/>
*     <enumeration value="Borger"/>
*     <enumeration value="Forældermyndighed"/>
*     <enumeration value="Værge"/>
*     <enumeration value="Web administrator"/>
*   </restriction>
* </simpleType>
* }</pre>
* 
*/
@XmlType(name = "PredefinedRequestedRole")
@XmlEnum
public enum PredefinedRequestedRole {
  @XmlEnumValue("L\u00e6ge")
 LÆGE("L\u00e6ge"),
 @XmlEnumValue("Tandl\u00e6ge")
 TANDLÆGE("Tandl\u00e6ge"),
 @XmlEnumValue("Jordemoder")
 JORDEMODER("Jordemoder"),
 @XmlEnumValue("Sygeplejerske")
 SYGEPLEJERSKE("Sygeplejerske"),
 @XmlEnumValue("Social- og sundhedsassistent")
 SOCIAL_OG_SUNDHEDSASSISTENT("Social- og sundhedsassistent"),
 @XmlEnumValue("Social- og sundhedshj\u00e6lper")
 SOCIAL_OG_SUNDHEDSHJÆLPER("Social- og sundhedshj\u00e6lper"),
 @XmlEnumValue("Sundhedsplejerske")
 SUNDHEDSPLEJERSKE("Sundhedsplejerske"),
 @XmlEnumValue("Farmaceut")
 FARMACEUT("Farmaceut"),
 @XmlEnumValue("Behandlerfarmaceut")
 BEHANDLERFARMACEUT("Behandlerfarmaceut"),
 @XmlEnumValue("Farmakonom")
 FARMAKONOM("Farmakonom"),
 @XmlEnumValue("Assistent for L\u00e6ge")
 ASSISTENT_FOR_LÆGE("Assistent for L\u00e6ge"),
 @XmlEnumValue("Assistent for Tandl\u00e6ge")
 ASSISTENT_FOR_TANDLÆGE("Assistent for Tandl\u00e6ge"),
 @XmlEnumValue("Assistent for Sygeplejer")
 ASSISTENT_FOR_SYGEPLEJER("Assistent for Sygeplejer"),
 @XmlEnumValue("Assistent for Jordemoder")
 ASSISTENT_FOR_JORDEMODER("Assistent for Jordemoder"),
 @XmlEnumValue("Assistent for Social- og sundhedsassistent")
 ASSISTENT_FOR_SOCIAL_OG_SUNDHEDSASSISTENT("Assistent for Social- og sundhedsassistent"),
 @XmlEnumValue("Borger")
 BORGER("Borger"),
 @XmlEnumValue("For\u00e6ldermyndighed")
 FORÆLDERMYNDIGHED("For\u00e6ldermyndighed"),
 @XmlEnumValue("V\u00e6rge")
 VÆRGE("V\u00e6rge"),
 @XmlEnumValue("Web administrator")
 WEB_ADMINISTRATOR("Web administrator");
 private final String value;
  PredefinedRequestedRole(String v) {
 value = v;
 }
  public String value() {
 return value;
 }
  public static PredefinedRequestedRole fromValue(String v) {
 for (PredefinedRequestedRole c: PredefinedRequestedRole.values()) {
  if (c.value.equals(v)) {
   return c;
  }
 }
 throw new IllegalArgumentException(v);
 }
 }
