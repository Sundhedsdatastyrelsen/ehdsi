
package dk.sdsd.ddv.dgws._2010._08;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NameFormat.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="NameFormat">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="medcom:ynumber"/>
 *     <enumeration value="medcom:pnumber"/>
 *     <enumeration value="medcom:skscode"/>
 *     <enumeration value="medcom:cvrnumber"/>
 *     <enumeration value="medcom:communalnumber"/>
 *     <enumeration value="medcom:locationnumber"/>
 *     <enumeration value="medcom:sor"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "NameFormat")
@XmlEnum
public enum NameFormat {

    @XmlEnumValue("medcom:ynumber")
    MEDCOM_YNUMBER("medcom:ynumber"),
    @XmlEnumValue("medcom:pnumber")
    MEDCOM_PNUMBER("medcom:pnumber"),
    @XmlEnumValue("medcom:skscode")
    MEDCOM_SKSCODE("medcom:skscode"),
    @XmlEnumValue("medcom:cvrnumber")
    MEDCOM_CVRNUMBER("medcom:cvrnumber"),
    @XmlEnumValue("medcom:communalnumber")
    MEDCOM_COMMUNALNUMBER("medcom:communalnumber"),
    @XmlEnumValue("medcom:locationnumber")
    MEDCOM_LOCATIONNUMBER("medcom:locationnumber"),
    @XmlEnumValue("medcom:sor")
    MEDCOM_SOR("medcom:sor");
    private final String value;

    NameFormat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NameFormat fromValue(String v) {
        for (NameFormat c: NameFormat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
