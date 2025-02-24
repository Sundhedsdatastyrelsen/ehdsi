
package dk.vaccinationsregister.schemas._2013._12._01.e1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PredefinedPlannedVaccinationStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="PredefinedPlannedVaccinationStatusType">
 *   <restriction base="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}UndefinedPlannedVaccinationStatusType">
 *     <enumeration value="Planlagt"/>
 *     <enumeration value="Effektueret"/>
 *     <enumeration value="Fejlregistrering"/>
 *     <enumeration value="Slettet på borgers anmodning"/>
 *     <enumeration value="Slettet af lægefaglige årsager"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "PredefinedPlannedVaccinationStatusType")
@XmlEnum
public enum PredefinedPlannedVaccinationStatusType {

    @XmlEnumValue("Planlagt")
    PLANLAGT("Planlagt"),
    @XmlEnumValue("Effektueret")
    EFFEKTUERET("Effektueret"),
    @XmlEnumValue("Fejlregistrering")
    FEJLREGISTRERING("Fejlregistrering"),
    @XmlEnumValue("Slettet p\u00e5 borgers anmodning")
    SLETTET_PÅ_BORGERS_ANMODNING("Slettet p\u00e5 borgers anmodning"),
    @XmlEnumValue("Slettet af l\u00e6gefaglige \u00e5rsager")
    SLETTET_AF_LÆGEFAGLIGE_ÅRSAGER("Slettet af l\u00e6gefaglige \u00e5rsager");
    private final String value;

    PredefinedPlannedVaccinationStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PredefinedPlannedVaccinationStatusType fromValue(String v) {
        for (PredefinedPlannedVaccinationStatusType c: PredefinedPlannedVaccinationStatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
