
package dk.vaccinationsregister.schemas._2013._12._01;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PredefinedSourceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="PredefinedSourceType">
 *   <restriction base="{http://vaccinationsregister.dk/schemas/2013/12/01}UndefinedSourceType">
 *     <enumeration value="Chemical Abstract (CAS)"/>
 *     <enumeration value="DKMA Medicine Prices"/>
 *     <enumeration value="DA Medicine Prices"/>
 *     <enumeration value="Local drug assortment"/>
 *     <enumeration value="FMK dosage quantity unit"/>
 *     <enumeration value="Local dosage quantity unit"/>
 *     <enumeration value="SKS"/>
 *     <enumeration value="Ydernummer"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "PredefinedSourceType")
@XmlEnum
public enum PredefinedSourceType {

    @XmlEnumValue("Chemical Abstract (CAS)")
    CHEMICAL_ABSTRACT_CAS("Chemical Abstract (CAS)"),
    @XmlEnumValue("DKMA Medicine Prices")
    DKMA_MEDICINE_PRICES("DKMA Medicine Prices"),
    @XmlEnumValue("DA Medicine Prices")
    DA_MEDICINE_PRICES("DA Medicine Prices"),
    @XmlEnumValue("Local drug assortment")
    LOCAL_DRUG_ASSORTMENT("Local drug assortment"),
    @XmlEnumValue("FMK dosage quantity unit")
    FMK_DOSAGE_QUANTITY_UNIT("FMK dosage quantity unit"),
    @XmlEnumValue("Local dosage quantity unit")
    LOCAL_DOSAGE_QUANTITY_UNIT("Local dosage quantity unit"),
    SKS("SKS"),
    @XmlEnumValue("Ydernummer")
    YDERNUMMER("Ydernummer");
    private final String value;

    PredefinedSourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PredefinedSourceType fromValue(String v) {
        for (PredefinedSourceType c: PredefinedSourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
