package dk.sundhedsdatastyrelsen.epportal.ism

import dk.sundhedsdatastyrelsen.epportal.logger
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

private const val ISM_NS = "http://ec.europa.eu/sante/ehncp/ism"
private const val LABEL_PREFIX = "label.ism."

/**
 * Parses the International Search Mask (ISM) documents that countries publish on the SMP server.
 *
 * Only the subset of the format that DK and FI actually use is supported: `identifier type="NORMAL"`
 * with `<id>` children. Everything else (CHOICE identifiers, grouped `ids`, `textField`, `birthDate`,
 * `gender`, `media`) is logged as a warning and skipped rather than causing a parse failure, since a
 * country's ISM may legitimately contain search fields we don't support yet.
 */
object IsmParser {
    private val log = logger()

    fun parse(input: InputStream): SearchMask {
        val factory = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
            // Harden against XXE / entity-expansion attacks.
            setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
            setFeature("http://xml.org/sax/features/external-general-entities", false)
            setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            isXIncludeAware = false
            isExpandEntityReferences = false
        }
        val document = factory.newDocumentBuilder().parse(input)

        val searchFields = document.getElementsByTagNameNS(ISM_NS, "searchFields").item(0) as? Element
            ?: throw IllegalArgumentException("ISM document has no searchFields element")

        val country = searchFields.getElementsByTagNameNS(ISM_NS, "country").item(0) as? Element
            ?: throw IllegalArgumentException("ISM document has no country element")

        val countryCode = country.getAttribute("code")

        val idFields = country.getElementsByTagNameNS(ISM_NS, "patientSearch")
            .toElementList()
            .flatMap { it.childElements() }
            .flatMap { child -> parsePatientSearchChild(child, countryCode) }

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
        val children = identifier.childElements()
        val hasIds = children.any { it.localName == "ids" }

        if (type == "CHOICE" || hasIds) {
            log.warn(
                "Skipping unsupported ISM identifier (type={}, hasIds={}) for country {}",
                type,
                hasIds,
                countryCode,
            )
            return emptyList()
        }

        return children
            .filter { it.localName == "id" }
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

    private fun org.w3c.dom.NodeList.toElementList(): List<Element> =
        (0 until length).mapNotNull { item(it) as? Element }

    private fun Element.childElements(): List<Element> =
        (0 until childNodes.length).mapNotNull { childNodes.item(it) as? Element }
}
