package dk.vaccinationsregister.schemas._2013._12._01;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for PredefinedPermission.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* <pre>{@code
* <simpleType name="PredefinedPermission">
*   <restriction base="{http://vaccinationsregister.dk/schemas/2013/12/01}UndefinedPermission">
*     <enumeration value="BorgerOpslag"/>
*     <enumeration value="SundhedsfagligOpslag"/>
*     <enumeration value="Recept"/>
*     <enumeration value="Lægemiddelordination"/>
*     <enumeration value="Effektuering"/>
*     <enumeration value="Privatmarkering"/>
*     <enumeration value="VisPrivatmarkeretVærdispring"/>
*     <enumeration value="VisPrivatmarkeretSamtykke"/>
*     <enumeration value="Suspendering"/>
*     <enumeration value="Afstemning"/>
*     <enumeration value="LøsRecept"/>
*     <enumeration value="Administration"/>
*     <enumeration value="Tilknytning"/>
*     <enumeration value="BestilEffektuering"/>
*     <enumeration value="Default permissions - Ingen specielle permissions påkrævet"/>
*     <enumeration value="Se Vaccinationer"/>
*     <enumeration value="Vedligehold vaccinationer"/>
*     <enumeration value="Vedligehold anbefalede vaccinationer"/>
*     <enumeration value="Godkend vaccinationer"/>
*   </restriction>
* </simpleType>
* }</pre>
* 
*/
@XmlType(name = "PredefinedPermission")
@XmlEnum
public enum PredefinedPermission {
  @XmlEnumValue("BorgerOpslag")
 BORGER_OPSLAG("BorgerOpslag"),
 @XmlEnumValue("SundhedsfagligOpslag")
 SUNDHEDSFAGLIG_OPSLAG("SundhedsfagligOpslag"),
 @XmlEnumValue("Recept")
 RECEPT("Recept"),
 @XmlEnumValue("L\u00e6gemiddelordination")
 LÆGEMIDDELORDINATION("L\u00e6gemiddelordination"),
 @XmlEnumValue("Effektuering")
 EFFEKTUERING("Effektuering"),
 @XmlEnumValue("Privatmarkering")
 PRIVATMARKERING("Privatmarkering"),
 @XmlEnumValue("VisPrivatmarkeretV\u00e6rdispring")
 VIS_PRIVATMARKERET_VÆRDISPRING("VisPrivatmarkeretV\u00e6rdispring"),
 @XmlEnumValue("VisPrivatmarkeretSamtykke")
 VIS_PRIVATMARKERET_SAMTYKKE("VisPrivatmarkeretSamtykke"),
 @XmlEnumValue("Suspendering")
 SUSPENDERING("Suspendering"),
 @XmlEnumValue("Afstemning")
 AFSTEMNING("Afstemning"),
 @XmlEnumValue("L\u00f8sRecept")
 LØS_RECEPT("L\u00f8sRecept"),
 @XmlEnumValue("Administration")
 ADMINISTRATION("Administration"),
 @XmlEnumValue("Tilknytning")
 TILKNYTNING("Tilknytning"),
 @XmlEnumValue("BestilEffektuering")
 BESTIL_EFFEKTUERING("BestilEffektuering"),
 @XmlEnumValue("Default permissions - Ingen specielle permissions p\u00e5kr\u00e6vet")
 DEFAULT_PERMISSIONS_INGEN_SPECIELLE_PERMISSIONS_PÅKRÆVET("Default permissions - Ingen specielle permissions p\u00e5kr\u00e6vet"),
 @XmlEnumValue("Se Vaccinationer")
 SE_VACCINATIONER("Se Vaccinationer"),
 @XmlEnumValue("Vedligehold vaccinationer")
 VEDLIGEHOLD_VACCINATIONER("Vedligehold vaccinationer"),
 @XmlEnumValue("Vedligehold anbefalede vaccinationer")
 VEDLIGEHOLD_ANBEFALEDE_VACCINATIONER("Vedligehold anbefalede vaccinationer"),
 @XmlEnumValue("Godkend vaccinationer")
 GODKEND_VACCINATIONER("Godkend vaccinationer");
 private final String value;
  PredefinedPermission(String v) {
 value = v;
 }
  public String value() {
 return value;
 }
  public static PredefinedPermission fromValue(String v) {
 for (PredefinedPermission c: PredefinedPermission.values()) {
  if (c.value.equals(v)) {
   return c;
  }
 }
 throw new IllegalArgumentException(v);
 }
 }
