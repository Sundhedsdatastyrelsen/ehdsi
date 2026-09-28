package dk.sundhedsdatastyrelsen.epportal.ism

/**
 * Danish translations for the fixed vocabulary used by ISM documents: field labels (the
 * `labelIdentifier` values from the eHDSI ISM XSD, with the `label.ism.` prefix already stripped by
 * [IsmParser]), country names, and the OpenNCP gender codes. Anything not in the map falls back to the
 * raw key/code, so an ISM using a label we haven't translated yet still renders instead of failing.
 */
object Labels {
    private val ismLabels = mapOf(
        "nationalIdentityCardNumber" to "Nationalt ID-kortnummer",
        "nationalPersonIdentifier" to "Nationalt personnummer",
        "healthCardNumber" to "Sygesikringskortnummer",
        "passportNumber" to "Pasnummer",
        "nationalSocialSecurityNumber" to "Socialsikringsnummer",
        "regionalPatientIdentifier" to "Regionalt patient-ID",
        "regionIdentifier" to "Region-ID",
        "accessCode" to "Adgangskode",
    )

    private val countryNames = mapOf(
        "DK" to "Danmark",
        "FI" to "Finland",
    )

    private val genderNames = mapOf(
        "M" to "Mand",
        "F" to "Kvinde",
        "UN" to "Ukendt",
    )

    fun ism(key: String): String = ismLabels[key] ?: key

    fun country(code: String): String = countryNames[code] ?: code

    fun gender(code: String): String = genderNames[code] ?: code
}
