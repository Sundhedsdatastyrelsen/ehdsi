
package dk.vaccinationsregister.schemas._2013._12._01;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationCredibilityEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="VaccinationCredibilityEnumType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="Slettet"/>
 *     <enumeration value="Oprettet af læge / medhjælp"/>
 *     <enumeration value="Oprettet på baggrund af data fra Sygesikringsregisteret"/>
 *     <enumeration value="Udleveret på apotek og godkendt af læge"/>
 *     <enumeration value="Oprettet af læge eller oprettet af borger og godkendt af læge"/>
 *     <enumeration value="Udleveret på apotek"/>
 *     <enumeration value="Oprettet af borger"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "VaccinationCredibilityEnumType")
@XmlEnum
public enum VaccinationCredibilityEnumType {

    @XmlEnumValue("Slettet")
    SLETTET("Slettet"),
    @XmlEnumValue("Oprettet af l\u00e6ge / medhj\u00e6lp")
    OPRETTET_AF_LÆGE_MEDHJÆLP("Oprettet af l\u00e6ge / medhj\u00e6lp"),
    @XmlEnumValue("Oprettet p\u00e5 baggrund af data fra Sygesikringsregisteret")
    OPRETTET_PÅ_BAGGRUND_AF_DATA_FRA_SYGESIKRINGSREGISTERET("Oprettet p\u00e5 baggrund af data fra Sygesikringsregisteret"),
    @XmlEnumValue("Udleveret p\u00e5 apotek og godkendt af l\u00e6ge")
    UDLEVERET_PÅ_APOTEK_OG_GODKENDT_AF_LÆGE("Udleveret p\u00e5 apotek og godkendt af l\u00e6ge"),
    @XmlEnumValue("Oprettet af l\u00e6ge eller oprettet af borger og godkendt af l\u00e6ge")
    OPRETTET_AF_LÆGE_ELLER_OPRETTET_AF_BORGER_OG_GODKENDT_AF_LÆGE("Oprettet af l\u00e6ge eller oprettet af borger og godkendt af l\u00e6ge"),
    @XmlEnumValue("Udleveret p\u00e5 apotek")
    UDLEVERET_PÅ_APOTEK("Udleveret p\u00e5 apotek"),
    @XmlEnumValue("Oprettet af borger")
    OPRETTET_AF_BORGER("Oprettet af borger");
    private final String value;

    VaccinationCredibilityEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VaccinationCredibilityEnumType fromValue(String v) {
        for (VaccinationCredibilityEnumType c: VaccinationCredibilityEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
