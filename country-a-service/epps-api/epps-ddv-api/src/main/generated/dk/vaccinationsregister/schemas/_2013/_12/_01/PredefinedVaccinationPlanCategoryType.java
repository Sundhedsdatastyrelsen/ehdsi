package dk.vaccinationsregister.schemas._2013._12._01;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for PredefinedVaccinationPlanCategoryType.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* <pre>{@code
* <simpleType name="PredefinedVaccinationPlanCategoryType">
*   <restriction base="{http://vaccinationsregister.dk/schemas/2013/12/01}UndefinedVaccinationPlanCategoryType">
*     <enumeration value="COVID-19"/>
*   </restriction>
* </simpleType>
* }</pre>
* 
*/
@XmlType(name = "PredefinedVaccinationPlanCategoryType")
@XmlEnum
public enum PredefinedVaccinationPlanCategoryType {
  @XmlEnumValue("COVID-19")
 COVID_19("COVID-19");
 private final String value;
  PredefinedVaccinationPlanCategoryType(String v) {
 value = v;
 }
  public String value() {
 return value;
 }
  public static PredefinedVaccinationPlanCategoryType fromValue(String v) {
 for (PredefinedVaccinationPlanCategoryType c: PredefinedVaccinationPlanCategoryType.values()) {
  if (c.value.equals(v)) {
   return c;
  }
 }
 throw new IllegalArgumentException(v);
 }
 }
