package dk.sundhedsdatastyrelsen.epportal.ism

/**
 * The subset of a country's International Search Mask (ISM) that this application understands:
 * one or more identifier fields of type NORMAL. See [IsmParser] for what is deliberately skipped.
 */
data class SearchMask(
    val countryCode: String,
    val idFields: List<IdField>,
)

/**
 * One `identifier/id` field from a NORMAL identifier in the ISM.
 *
 * @param format `null` when the ISM's `format` attribute is blank (as FI's is). Otherwise, a regex the
 *   entered value must fully match.
 */
data class IdField(
    val domain: String,
    val label: String,
    val format: Regex?,
    val contextualDescription: String,
    val mandatory: Boolean,
)
