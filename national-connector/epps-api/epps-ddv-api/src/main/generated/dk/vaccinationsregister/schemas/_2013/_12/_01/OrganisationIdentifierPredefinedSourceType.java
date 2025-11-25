
package dk.vaccinationsregister.schemas._2013._12._01;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrganisationIdentifierPredefinedSourceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="OrganisationIdentifierPredefinedSourceType">
 *   <restriction base="{http://vaccinationsregister.dk/schemas/2013/12/01}UndefinedSourceType">
 *     <enumeration value="SKS"/>
 *     <enumeration value="Yder"/>
 *     <enumeration value="EAN-Lokationsnummer"/>
 *     <enumeration value="CVR"/>
 *     <enumeration value="CVR-P"/>
 *     <enumeration value="Kommunekode"/>
 *     <enumeration value="SOR"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "OrganisationIdentifierPredefinedSourceType")
@XmlEnum
public enum OrganisationIdentifierPredefinedSourceType {

    SKS("SKS"),
    @XmlEnumValue("Yder")
    YDER("Yder"),
    @XmlEnumValue("EAN-Lokationsnummer")
    EAN_LOKATIONSNUMMER("EAN-Lokationsnummer"),
    CVR("CVR"),
    @XmlEnumValue("CVR-P")
    CVR_P("CVR-P"),
    @XmlEnumValue("Kommunekode")
    KOMMUNEKODE("Kommunekode"),
    SOR("SOR");
    private final String value;

    OrganisationIdentifierPredefinedSourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrganisationIdentifierPredefinedSourceType fromValue(String v) {
        for (OrganisationIdentifierPredefinedSourceType c: OrganisationIdentifierPredefinedSourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
