package dk.sundhedsdatastyrelsen.ncpeh.cda;

public enum Oid {
    /**
     * EPrescription document repository id. For now, we use the DK home OID.
     */
    DK_EPRESCRIPTION_REPOSITORY_ID("2.16.17.710.802.1000.990.1"),
    /**
     * Danish CPR numbers
     */
    DK_CPR("2.16.17.710.802.1000.990.1.500"),
    /**
     * FMK prescription ids
     */
    DK_FMK_PRESCRIPTION("2.16.17.710.802.1000.990.1.10"),
    /**
     * "Det danske autorisationsregister"
     */
    DK_AUTHORIZATION_REGISTRY("2.16.17.710.802.1000.990.1.30"),
    /**
     * Danske ydernumre
     */
    DK_YDER("2.16.17.710.802.1000.990.1.40"),
    /**
     * Sundhedsvæsenets Organisationsregister (SOR)
     */
    DK_SOR("2.16.17.710.802.1000.990.1.50"),
    /**
     * LMS02 - "Grunddata pakning"
     */
    DK_LMS02("2.16.17.710.802.1000.990.1.20.2"),
    /**
     * LMS22 - "Lægemiddelform-betegnelser"
     */
    DK_LMS22("2.16.17.710.802.1000.990.1.20.22"),

    /**
     * Anatomical Therapeutic Chemical
     */
    ATC("2.16.840.1.113883.6.73"),
    ADMINISTRATIVE_GENDER("2.16.840.1.113883.5.1"),
    HEALTHCARE_PROFESSIONAL_ROLES("2.16.840.1.113883.2.9.6.2.7"),

    DK_ORG_SKS("2.16.17.710.802.1000.990.1.60.1"),
    DK_ORG_EAN("2.16.17.710.802.1000.990.1.60.2"),
    DK_ORG_CVR("2.16.17.710.802.1000.990.1.60.3"),
    DK_ORG_CVR_P("2.16.17.710.802.1000.990.1.60.4"),
    DK_ORG_KOMMUNEKODE("2.16.17.710.802.1000.990.1.60.5"),
    DK_ORG_UDENLANDSK("2.16.17.710.802.1000.990.1.60.90");

    public final String value;

    Oid(String value)  {
        this.value = value;
    }
}
