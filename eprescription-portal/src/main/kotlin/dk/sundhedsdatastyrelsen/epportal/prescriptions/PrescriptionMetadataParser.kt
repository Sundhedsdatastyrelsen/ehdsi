package dk.sundhedsdatastyrelsen.epportal.prescriptions

import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField

/**
 * Parses prescription metadata received from country A. The format is XDS ITI-38
 * CrossGatewayQuery AdhocQueryResponse.
 *
 * Each prescription is represented by two `ExtrinsicObject` document entries, these are linked
 * with an ebXML `Association`.
 *
 * The current implementation relies on a few (hopefully) reasonable assumptions, these are
 * documented below. If they turn out to be false, a warning will be logged runtime.
 */
object PrescriptionMetadataParser {
    private val log = logger()

    // Classification schemes
    private const val CLASSIFICATION_SCHEME_FORMAT_CODE = "urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d"
    private const val CLASSIFICATION_SCHEME_EVENT_CODE_LIST = "urn:uuid:2c6b8cb7-8b2a-4051-b291-b1ae6a575ef4"
    private const val CLASSIFICATION_SCHEME_AUTHOR = "urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d"

    // Coding schemes, these disambiguate the event code list classification
    private const val CODING_SCHEME_ATC = "2.16.840.1.113883.6.73"
    private const val CODING_SCHEME_DOSE_FORM = "0.4.0.127.0.16.1.1.2.1"
    private const val CODING_SCHEME_STRENGTH = "eHDSI_Strength_CodeSystem"
    private const val CODING_SCHEME_XDW_WORKFLOW_STATUS = "1.3.6.1.4.1.19376.1.2.3"

    private const val ASSOCIATION_TYPE_XFRM = "urn:ihe:iti:2007:AssociationType:XFRM"

    private const val EXTERNAL_IDENTIFIER_NAME_UNIQUE_ID = "XDSDocumentEntry.uniqueId"

    // used only to verify the target=L3/source=L1 assumption
    private const val FORMAT_CODE_CODED_EPRESCRIPTION = "urn:epsos:ep:pre:2010"
    private const val FORMAT_CODE_PDF_RENDITION = "urn:ihe:iti:xds-sd:pdf:2008"

    private fun codingSchemeXpath(codingScheme: String, xpathTail: String): String {
        val basePath =
            "rim:Classification[@classificationScheme='$CLASSIFICATION_SCHEME_EVENT_CODE_LIST' and " +
                $$"rim:Slot[@name='codingScheme']/rim:ValueList/rim:Value='%1$s']%2$s"

        return String.format(basePath, codingScheme, xpathTail)
    }

    private val xpath = XPathWrapper(XmlNamespace.RIM)

    fun parse(document: Document): List<PrescriptionListItem> {
        val entries = parseExtrinsicObjects(document)
        val associations = parseXfrmAssociations(document)
        checkAssumptions(entries, associations)
        return groupPrescriptions(entries, associations)
    }

    internal fun parseExtrinsicObjects(document: Document): List<PrescriptionMetadata> {
        return xpath.evalElements("//rim:ExtrinsicObject", document).map { parseExtrinsicObject(it) }
    }

    private fun parseExtrinsicObject(el: Element): PrescriptionMetadata {
        return PrescriptionMetadata(
            registryObjectId = el.getAttribute("id"),
            documentUniqueId = xpath.evalString(
                "rim:ExternalIdentifier[rim:Name/rim:LocalizedString/@value='${EXTERNAL_IDENTIFIER_NAME_UNIQUE_ID}']/@value",
                el,
            ),
            repositoryUniqueId = xpath.evalString("rim:Slot[@name='repositoryUniqueId']/rim:ValueList/rim:Value", el),
            homeCommunityId = el.getAttribute("home").ifBlank { null },
            formatCode = xpath.evalString(
                "rim:Classification[@classificationScheme='$CLASSIFICATION_SCHEME_FORMAT_CODE']/@nodeRepresentation",
                el,
            ),
            title = xpath.evalString("rim:Name/rim:LocalizedString/@value", el),
            description = xpath.evalString("rim:Description/rim:LocalizedString/@value", el),
            authorName = xpath.evalString(
                "rim:Classification[@classificationScheme='$CLASSIFICATION_SCHEME_AUTHOR']" +
                    "/rim:Slot[@name='authorPerson']/rim:ValueList/rim:Value",
                el,
            ),
            effectiveTime = xpath.evalString("rim:Slot[@name='creationTime']/rim:ValueList/rim:Value", el)
                .let(::parseHl7Timestamp),
            atcCode = xpath.evalString(
                codingSchemeXpath(CODING_SCHEME_ATC, "/@nodeRepresentation"),
                el,
            ),
            atcName = xpath.evalString(
                codingSchemeXpath(CODING_SCHEME_ATC, "/rim:Name/rim:LocalizedString/@value"),
                el,
            ),
            doseFormCode = xpath.evalString(
                codingSchemeXpath(CODING_SCHEME_DOSE_FORM, "/@nodeRepresentation"),
                el,
            ),
            doseFormName = xpath.evalString(
                codingSchemeXpath(CODING_SCHEME_DOSE_FORM, "/rim:Name/rim:LocalizedString/@value"),
                el,
            ),
            strength = xpath.evalString(
                codingSchemeXpath(CODING_SCHEME_STRENGTH, "/@nodeRepresentation"),
                el,
            ),
            workflowStatus = xpath.evalString(
                codingSchemeXpath(CODING_SCHEME_XDW_WORKFLOW_STATUS, "/@nodeRepresentation"),
                el,
            ),
        )
    }

    /**
     * We receive dates in the hl7 TS/DTM UTC format: https://profiles.ihe.net/ITI/TF/Volume3/ch-4.2.html#4.2.3.1.7.
     *
     * HL7 TS values have optional month, day, etc., which is why the parser is a little funky.
     *
     * @return the parsed UTC timestamp.
     * @throws IllegalArgumentException if the timestamp couldn't be parsed. Can be changed to
     *   warning and null when we are closer to go live, but right now we want to know if it fails.
     */
    internal fun parseHl7Timestamp(rawValue: String): LocalDateTime {
        return try {
            LocalDateTime.parse(rawValue.trim(), HL7_TIMESTAMP_FORMATTER)
        } catch (exc: DateTimeParseException) {
            throw IllegalArgumentException("Unable to parse timestamp from eP list response. Input: $rawValue", exc)
        }
    }

    private val HL7_TIMESTAMP_FORMATTER = DateTimeFormatterBuilder()
        // The default parsing for year is 4-19 digits, and when combined with optional fields this breaks parsing.
        // See https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatterBuilder.html#appendValue-java.time.temporal.TemporalField-int-
        .appendValue(ChronoField.YEAR, 4)
        .appendPattern("[MM[dd[HH[mm[ss]]]]]")
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .toFormatter()

    internal data class XfrmAssociation(val source: String, val target: String)

    internal fun parseXfrmAssociations(document: Document): List<XfrmAssociation> {
        return xpath.evalNodes("//rim:Association[@associationType='$ASSOCIATION_TYPE_XFRM']", document)
            .map { it as Element }
            .map { XfrmAssociation(it.getAttribute("sourceObject"), it.getAttribute("targetObject")) }
    }

    /**
     * Groups document references into prescription pairs of L1 and L3.
     * This relies on the assumptions A to C below.
     * The assumptions are verified independently.
     */
    internal fun groupPrescriptions(
        entries: List<PrescriptionMetadata>,
        associations: List<XfrmAssociation>,
    ): List<PrescriptionListItem> {
        val byId = entries.associateBy { it.registryObjectId }

        return associations.map {
            PrescriptionListItem(byId[it.target], l1 = byId[it.source])
        }
    }

    /**
     * Checks the assumptions we've made about the data structure holds in practice.
     * Warns in the log if they don't.
     * The risks are: Some prescriptions may not be shown, some links to L1 and L3
     * prescriptions may not work, some links to L3 and L1 may be swapped.
     */
    private fun checkAssumptions(
        entries: List<PrescriptionMetadata>,
        associations: List<XfrmAssociation>,
    ) {
        val byId = entries.associateBy { it.registryObjectId }
        checkDocumentsAreReferencedExactlyOnce(byId, associations)
        associations.forEach {
            checkNoDanglingReferences(it, byId)
            checkL3IsTarget(it, byId)
        }
    }

    /** Assumption A: every document is referenced by exactly one association. */
    private fun checkDocumentsAreReferencedExactlyOnce(
        byId: Map<String, PrescriptionMetadata>,
        associations: List<XfrmAssociation>,
    ) {
        val referencedIds = associations.flatMap { listOf(it.source, it.target) }
        val uniqueIds = referencedIds.toSet()
        if (uniqueIds.size < referencedIds.size) {
            log.warn("Some documents are referenced more than once.")
        }
        val unpairedIds = byId.keys - uniqueIds
        if (unpairedIds.isNotEmpty()) {
            log.warn(
                "{} document(s) not covered by any XFRM association, these will not be shown.",
                unpairedIds.size,
            )
        }
    }

    /**
     * Assumption B: an association's `target` is the coded L3 document, and its `source` is the
     * PDF L1 document.
     */
    private fun checkL3IsTarget(
        association: XfrmAssociation,
        byId: Map<String, PrescriptionMetadata>,
    ) {
        val targetFormatCode = byId[association.target]?.formatCode
        if (targetFormatCode != null && targetFormatCode != FORMAT_CODE_CODED_EPRESCRIPTION) {
            log.warn(
                "XFRM association target {} expected formatCode '{}' (coded ePrescription) but found '{}'",
                association.target,
                FORMAT_CODE_CODED_EPRESCRIPTION,
                targetFormatCode,
            )
        }
        val sourceFormatCode = byId[association.source]?.formatCode
        if (sourceFormatCode != null && sourceFormatCode != FORMAT_CODE_PDF_RENDITION) {
            log.warn(
                "XFRM association source {} expected formatCode '{}' (PDF rendition) but found '{}'",
                association.source,
                FORMAT_CODE_PDF_RENDITION,
                sourceFormatCode,
            )
        }
    }

    /** Assumption C: no dangling association references. */
    private fun checkNoDanglingReferences(association: XfrmAssociation, byId: Map<String, PrescriptionMetadata>) {
        if (byId[association.target] == null) {
            log.warn("XFRM association target {} does not resolve to any document in the response", association.target)
        }
        if (byId[association.source] == null) {
            log.warn("XFRM association source {} does not resolve to any document in the response", association.source)
        }
    }
}
