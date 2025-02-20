
package dk.vaccinationsregister.schemas._2013._12._01;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PredefinedOrganisationTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="PredefinedOrganisationTypeType">
 *   <restriction base="{http://vaccinationsregister.dk/schemas/2013/12/01}UndefinedOrganisationTypeType">
 *     <enumeration value="Sygehus"/>
 *     <enumeration value="Yder"/>
 *     <enumeration value="Apotek"/>
 *     <enumeration value="Kommune"/>
 *     <enumeration value="Administrator"/>
 *     <enumeration value="SOR"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "PredefinedOrganisationTypeType")
@XmlEnum
public enum PredefinedOrganisationTypeType {

    @XmlEnumValue("Sygehus")
    SYGEHUS("Sygehus"),
    @XmlEnumValue("Yder")
    YDER("Yder"),
    @XmlEnumValue("Apotek")
    APOTEK("Apotek"),
    @XmlEnumValue("Kommune")
    KOMMUNE("Kommune"),
    @XmlEnumValue("Administrator")
    ADMINISTRATOR("Administrator"),
    SOR("SOR");
    private final String value;

    PredefinedOrganisationTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PredefinedOrganisationTypeType fromValue(String v) {
        for (PredefinedOrganisationTypeType c: PredefinedOrganisationTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
