package dk.sundhedsdatastyrelsen.epportal.ism

import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.w3c.dom.Element
import java.io.InputStream

private const val LABEL_PREFIX = "label.ism."

/**
 * Parses the International Search Mask (ISM) documents that countries publish on the SMP server.
 *
 * Only the subset of the format that DK and FI actually use is supported: `identifier type="NORMAL"`
 * with `<id>` children. Everything else (CHOICE identifiers, grouped `ids`, `textField`, `birthDate`,
 * `gender`, `media`) is logged as a warning and skipped rather than causing a parse failure.
 *
 * IMPORTANT NOTE: The id fields may contain regular expressions that are used for validation. It is important that we
 * inspect these regular expressions to ensure they are safe and do not contain any unsafe patterns.
 * A regular expression may lead to denial-of-service, e.g. (a|b)*(a|c)* which can take quadratic time in the size of
 * the input, or (a+)+ which given certain input string take exponential time.
 * We do not attempt to perform automatic static analysis of the regexes to detect DoS vulnerabilities.  Instead, we
 * rely on manual inspection of the regexes. See [1] for techniques for static analysis of regular expressions.
 *
 * Therefore, we cannot introduce automatic fetching of the ISM documents, we need a process where the regular
 * expressions are manually inspected and validated.
 *
 * - [1] Wüstholz, Valentin, et al. "Static detection of DoS vulnerabilities in programs that use regular expressions."
 * International Conference on Tools and Algorithms for the Construction and Analysis of Systems. Berlin, Heidelberg:
 * Springer Berlin Heidelberg, 2017.
 * http://www.cs.cmu.edu/~mheule/publications/evil-regexes.pdf
 */
object IsmParser {
    private val log = logger()

    private val xpath = XPathWrapper(XmlNamespace.ISM)

    fun parse(input: InputStream): SearchMask {
        val document = XmlUtils.parse(input)

        val country = xpath.evalElement("//ism:searchFields/ism:country", document)
            ?: throw IllegalArgumentException("No country found in ISM document")
        val countryCode = xpath.evalString("@code", country)
        val idFields = xpath.evalElements("ism:patientSearch/*", country)
            .flatMap { parsePatientSearchChild(it, countryCode) }

        return SearchMask(countryCode, idFields)
    }

    private fun parsePatientSearchChild(child: Element, countryCode: String): List<IdField> {
        return when (child.localName) {
            "identifier" -> parseIdentifier(child, countryCode)
            "textField", "birthDate", "gender", "media" -> {
                log.warn("Skipping unsupported ISM search field '{}' for country {}", child.localName, countryCode)
                emptyList()
            }

            else -> emptyList()
        }
    }

    private fun parseIdentifier(identifier: Element, countryCode: String): List<IdField> {
        val type = identifier.getAttribute("type")
        val hasIds = xpath.evalElements("ism:ids", identifier).isNotEmpty()

        if (type == "CHOICE" || hasIds) {
            log.warn(
                "Skipping unsupported ISM identifier (type={}, hasIds={}) for country {}",
                type,
                hasIds,
                countryCode,
            )
            return emptyList()
        }

        return xpath.evalElements("ism:id", identifier)
            .map { id ->
                val format = id.getAttribute("format")
                IdField(
                    domain = id.getAttribute("domain"),
                    label = id.getAttribute("label").removePrefix(LABEL_PREFIX),
                    format = format.ifBlank { null }?.let { Regex(it) },
                    contextualDescription = id.getAttribute("contextualDescription"),
                    mandatory = id.getAttribute("mandatory") == "true",
                )
            }
    }
}
