package dk.sundhedsdatastyrelsen.ncpeh.cda;

public enum Oid {
    /**
     * EPrescription document repository id. For now, we use the DK home OID.
     * <a href="https://oid-base.com/get/1.2.208">Registered.</a>
     */
    DK_EPRESCRIPTION_REPOSITORY_ID("1.2.208"),
    /**
     * Registries used in the Danish health by data exchange.
     * <a href="https://oid-base.com/get/1.2.208.176.1">Registered.</a>
     */
    DK_REGISTRIES("1.2.208.176.1"),
    /**
     * Danish CPR numbers.
     * <a href="https://oid-base.com/get/1.2.208.176.1.2">Registered.</a>
     */
    DK_CPR("1.2.208.176.1.2"),
    /**
     * FMK prescription ids
     * <a href="https://oid-base.com/get/1.2.208.176.7.2.2">Registered.</a>
     */
    DK_FMK_PRESCRIPTION("1.2.208.176.7.2.2"),
    /**
     * "Det danske autorisationsregister"
     * <a href="https://oid-base.com/get/1.2.208.176.1.3">Registered.</a>
     */
    DK_AUTHORIZATION_REGISTRY("1.2.208.176.1.3"),
    /**
     * faggrupper
     * "Uddannelseskoder i det danske autorisationsregister"
     * <a href="https://oid-base.com/get/1.2.208.176.1.3.1">Registered.</a>
     */
    DK_AUTHORIZATION_REGISTRY_EDUCATION_CODE("1.2.208.176.1.3.1"),
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
     * Sundhedsvæsenets Klassifikations System (SKS).
     * <a href="https://oid-base.com/get/1.2.208.176.2.4">Registered.</a>
     */
    DK_SKS("1.2.208.176.2.4", "Sundhedsvæsenets Klassifikations System"),
    /**
     * Varenumre på lægemiddelpakninger
     * <a href="https://laegemiddelstyrelsen.dk/da/tilskud/varenumre/">https://laegemiddelstyrelsen.dk/da/tilskud/varenumre/</a>
     * <a href="https://oid-base.com/get/1.2.208.176.3.2">Registered.</a>
     */
    DK_VARENUMRE("1.2.208.176.3.2", "Varenumre på lægemiddelpakninger"),

    /**
     * Emballagetype fra LMS14
     * <a href="https://oid-base.com/get/1.2.208.176.3.14">Registered.</a>
     */
    DK_EMBALLAGETYPE("1.2.208.176.3.14", "LMS14"),
    /**
     * Central Virksomhedsregister (CVR).
     * <a href="https://oid-base.com/get/2.16.840.1.113883.2.24.1.1">Registered.</a>
     */
    DK_CVR("2.16.840.1.113883.2.24.1.1", "Central Virksomhedsregister"),
    /**
     * Global Location Number (GLN) (previously, European Article Number (EAN) location code)
     * <a href="https://oid-base.com/get/1.3.88">Registered.</a>
     */
    EAN("1.3.88", "Global Location Number"),
    /**
     * LMS11 - "Administrationsvej"
     * <a href="https://oid-base.com/get/1.2.208.176.3.11">Registered.</a>
     */
    DK_LMS11("1.2.208.176.3.11", "LMS11"),
    /**
     * LMS22 - "Lægemiddelform-betegnelser"
     * <a href="https://oid-base.com/get/1.2.208.176.3.22">Registered.</a>
     */
    DK_LMS22("1.2.208.176.3.22", "LMS22"),


    /**
     * Anatomical Therapeutic Chemical.
     * <a href="https://oid-base.com/get/2.16.840.1.113883.6.73">Registered.</a>
     */
    ATC("2.16.840.1.113883.6.73", "Anatomical Therapeutic Chemical"),

    /// eHDSIHealthcareProfessionalRole used for function code
    FUNCTION_CODE("1.3.6.1.4.1.12559.11.10.1.3.1.42.1", "eHDSIHealthcareProfessionalRole"),

    /**
     * Administrative Gender.
     * <a href="https://oid-base.com/get/2.16.840.1.113883.5.1">Registered.</a>
     */
    ADMINISTRATIVE_GENDER("2.16.840.1.113883.5.1", "AdministrativeGender"),

    /**
     * International Standard Classification of Occupations (ISCO).
     * <a href="https://art-decor.ehdsi.eu/publication/epsos-html-20240422T073854/identifiers.html">Not registered in
     * the OID repository, but listed on ART-DECOR.</a>
     */
    ISCO("2.16.840.1.113883.2.9.6.2.7", "International Standard Classification of Occupations");

    public final String value;
    public final String objectName;

    Oid(String value) {
        this(value, null);
    }

    Oid(String value, String objectName) {
        this.value = value;
        this.objectName = objectName;
    }
}
