package dk.sundhedsdatastyrelsen.epportal.ism

import dk.sundhedsdatastyrelsen.epportal.app.FindPatient
import java.io.File

/**
 * The search masks for all configured countries, loaded once at startup.
 */
class SearchMaskRepository(private val masks: Map<String, SearchMask>) {
    fun get(cc: String): SearchMask? = masks[cc]

    val countries: List<String> get() = masks.keys.toList()

    companion object {
        /**
         * Loads the ISM file for each configured country from [FindPatient.Config.ismDirectory]. Fails
         * fast (at startup) if a file is missing or its `country/@code` doesn't match the configured code.
         */
        fun load(config: FindPatient.Config): SearchMaskRepository {
            val masks = LinkedHashMap<String, SearchMask>()
            for (cc in config.countries) {
                val file = File(config.ismDirectory, "ism-${cc.lowercase()}.xml")
                check(file.isFile) { "ISM file for country $cc not found: ${file.path}" }
                val mask = file.inputStream().use { IsmParser.parse(it) }
                check(mask.countryCode == cc) {
                    "ISM file ${file.path} has country code ${mask.countryCode}, expected $cc"
                }
                masks[cc] = mask
            }
            return SearchMaskRepository(masks)
        }
    }
}
