
package dk.vaccinationsregister.schemas._2013._12._01;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NegativeConsentRequestType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="NegativeConsentRequestType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="nødvendig til varetagelse af en åbenbar almen interesse eller af væsentlige hensyn til patienten"/>
 *     <enumeration value="efter mundtlig eller skriftlig samtykke"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "NegativeConsentRequestType")
@XmlEnum
public enum NegativeConsentRequestType {

    @XmlEnumValue("n\u00f8dvendig til varetagelse af en \u00e5benbar almen interesse eller af v\u00e6sentlige hensyn til patienten")
    NØDVENDIG_TIL_VARETAGELSE_AF_EN_ÅBENBAR_ALMEN_INTERESSE_ELLER_AF_VÆSENTLIGE_HENSYN_TIL_PATIENTEN("n\u00f8dvendig til varetagelse af en \u00e5benbar almen interesse eller af v\u00e6sentlige hensyn til patienten"),
    @XmlEnumValue("efter mundtlig eller skriftlig samtykke")
    EFTER_MUNDTLIG_ELLER_SKRIFTLIG_SAMTYKKE("efter mundtlig eller skriftlig samtykke");
    private final String value;

    NegativeConsentRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NegativeConsentRequestType fromValue(String v) {
        for (NegativeConsentRequestType c: NegativeConsentRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
