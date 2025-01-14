package dk.sundhedsdatastyrelsen.ncpeh.cda;

public enum Oid {
    /**
     * EPrescription document repository id. For now, we use the DK home OID.
     */
    DK_EPRESCRIPTION_REPOSITORY_ID("2.16.17.710.802.1000.990.1"),
    /**
     * Danish CPR numbers.
     * <a href="https://oid-base.com/get/1.2.208.176.1.2">Registered.</a>
     */
    DK_CPR("1.2.208.176.1.2"),
    /**
     * FMK prescription ids
     */
    DK_FMK_PRESCRIPTION("2.16.17.710.802.1000.990.1.10"),
    /**
     * "Det danske autorisationsregister"
     * <a href="https://oid-base.com/get/1.2.208.176.1.3">Registered.</a>
     */
    DK_AUTHORIZATION_REGISTRY("1.2.208.176.1.3"),
    /**
     * Danske ydernumre.
     * <a href="https://oid-base.com/get/1.2.208.176.1.4">Registered.</a>
     */
    DK_YDER("1.2.208.176.1.4"),
    /**
     * Sundhedsvæsenets Organisationsregister (SOR).
     * <a href="https://oid-base.com/get/1.2.208.176.1.1">Registered.</a>
     */
    DK_SOR("1.2.208.176.1.1"),
    /**
     * Varenumre på lægemiddelpakninger
     * <a href="https://laegemiddelstyrelsen.dk/da/tilskud/varenumre/">https://laegemiddelstyrelsen.dk/da/tilskud/varenumre/</a>
     */
    DK_VARENUMRE("2.16.17.710.802.1000.990.1.20.2", "Varenumre på lægemiddelpakninger"),
    /**
     * LMS11 - "Administrationsvej"
     */
    DK_LMS11("2.16.17.710.802.1000.990.1.20.11", "LMS11 Administrationsvej"),
    /**
     * LMS22 - "Lægemiddelform-betegnelser"
     */
    DK_LMS22("2.16.17.710.802.1000.990.1.20.22", "LMS22 Lægemiddelform-betegnelser"),


    /**
     * Anatomical Therapeutic Chemical
     */
    ATC("2.16.840.1.113883.6.73", "Anatomical Therapeutic Chemical"),

    /**
     * Administrative Gender.
     * <a href="https://oid-base.com/get/2.16.840.1.113883.5.1">Registered.</a>
     */
    ADMINISTRATIVE_GENDER("2.16.840.1.113883.5.1", "AdministrativeGender"),

    HEALTHCARE_PROFESSIONAL_ROLES("2.16.840.1.113883.2.9.6.2.7", "ISCO"),

    DK_ORG_SKS("2.16.17.710.802.1000.990.1.60.1"),
    DK_ORG_EAN("2.16.17.710.802.1000.990.1.60.2"),
    DK_ORG_CVR("2.16.17.710.802.1000.990.1.60.3"),
    DK_ORG_CVR_P("2.16.17.710.802.1000.990.1.60.4"),
    DK_ORG_KOMMUNEKODE("2.16.17.710.802.1000.990.1.60.5"),
    DK_ORG_UDENLANDSK("2.16.17.710.802.1000.990.1.60.90");

    public final String value;
    public final String name;

    Oid(String value) {
        this(value, null);
    }

    Oid(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
