
package dk.vaccinationsregister.schemas._2013._12._01;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="VaccinationStatus">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Ikke Vaccineret"/>
 *     <enumeration value="Delvist Vaccineret"/>
 *     <enumeration value="Vaccineret"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "VaccinationStatus")
@XmlEnum
public enum VaccinationStatus {

    @XmlEnumValue("Ikke Vaccineret")
    IKKE_VACCINERET("Ikke Vaccineret"),
    @XmlEnumValue("Delvist Vaccineret")
    DELVIST_VACCINERET("Delvist Vaccineret"),
    @XmlEnumValue("Vaccineret")
    VACCINERET("Vaccineret");
    private final String value;

    VaccinationStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VaccinationStatus fromValue(String v) {
        for (VaccinationStatus c: VaccinationStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
