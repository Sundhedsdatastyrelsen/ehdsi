package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionListItem
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionMetadata
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.time.ZoneOffset

private val xpath = XPathWrapper(XmlNamespace.SOAP12, XmlNamespace.OPENNCP_CLIENT)
private val log = logger()

private const val FORMAT_CODE_CODED_EPRESCRIPTION = "urn:epsos:ep:pre:2010"
private const val FORMAT_CODE_PDF_RENDITION = "urn:ihe:iti:xds-sd:pdf:2008"

/**
 * Parses a list of documents to a list of prescription metadata.
 *
 * In the document list, each prescription is rendered twice. Once as L3 with L1 as associated document and once as L1
 * with L3 as associated document. We ignore the ones with L1 first and just use the L3 ones, since the full metadata
 * is there in the associated document as well. For an actual response, see [query-documents-response-2.xml].
 *
 * We also verify the assumptions and log if they do not hold.
 */
fun parseQueryDocumentsResponse(document: Document): List<PrescriptionListItem> {
    val response = xpath.evalElement("/soap12:Envelope/soap12:Body/ncpc:queryDocumentsResponse", document)
        ?: throw OpenNcpException("queryDocuments: no queryDocumentsResponse in the response")

    val allPrescriptions = xpath.evalElements("return", response)
    checkAssumptions(allPrescriptions)
    return allPrescriptions.filter {
        xpath.evalString("formatCode/nodeRepresentation", it) == FORMAT_CODE_CODED_EPRESCRIPTION
    }.map { e ->
        // TODO A prescription without an associated L1 is against the spec, but it fails the whole list.
        PrescriptionListItem(
            toPrescriptionMetadata(e), toPrescriptionMetadata(xpath.evalElements("associatedDocuments", e).first())
        )
    }
}

/**
 * Logs a warning for each way the documents break what the parser assumes.
 *
 * - every document is an L3 or an L1
 * - every L3 document has exactly one associated document, which is its L1 rendition
 * - every L1 document is the associated document of some L3 document, so no prescription is left out of the list
 */
private fun checkAssumptions(roots: List<Element>) {
    fun uuid(e: Element) = xpath.evalString("uuid", e)
    fun formatCode(e: Element) = xpath.evalString("formatCode/nodeRepresentation", e)
    fun associated(e: Element) = xpath.evalElements("associatedDocuments", e)

    val rootsByFormatCode = roots.groupBy(::formatCode)
    (rootsByFormatCode - setOf(FORMAT_CODE_CODED_EPRESCRIPTION, FORMAT_CODE_PDF_RENDITION)).forEach { (code, docs) ->
        docs.forEach { log.warn("queryDocuments: root document {} has unexpected formatCode '{}'", uuid(it), code) }
    }

    val l3Roots = rootsByFormatCode[FORMAT_CODE_CODED_EPRESCRIPTION].orEmpty()
    val pairedL1Ids = l3Roots.mapNotNullTo(mutableSetOf()) { l3 ->
        val associatedDocs = associated(l3)
        val l1 = associatedDocs.singleOrNull()
        when {
            l1 == null -> null.also {
                log.warn("queryDocuments: L3 {} has {} associated documents, expected 1", uuid(l3), associatedDocs.size)
            }
            formatCode(l1) != FORMAT_CODE_PDF_RENDITION -> null.also {
                log.warn(
                    "queryDocuments: L3 {} has associated document {} with formatCode '{}', expected L1",
                    uuid(l3), uuid(l1), formatCode(l1),
                )
            }
            else -> uuid(l1)
        }
    }
    val l1RootIds = rootsByFormatCode[FORMAT_CODE_PDF_RENDITION].orEmpty().map(::uuid)
    (l1RootIds - pairedL1Ids)
        .forEach { log.warn("queryDocuments: L1 root {} is not associated with any L3 root document", it) }
}

private fun toPrescriptionMetadata(e: Element): PrescriptionMetadata {
    fun text(path: String): String? = xpath.evalString(path, e).ifEmpty { null }
    val uuid = text("uuid") ?: throw OpenNcpException("queryDocuments: document without uuid")
    return PrescriptionMetadata(
        registryObjectId = uuid,
        documentUniqueId = uuid,
        repositoryUniqueId = text("repositoryId"),
        homeCommunityId = text("hcid"),
        formatCode = text("formatCode/nodeRepresentation"),
        title = text("title"),
        description = text("description"),
        authorName = text("authors[1]/person"),
        effectiveTime = text("creationDate")?.let {
            parseDateTime(it).withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime()
        },
        atcCode = text("atcCode"),
        atcName = text("atcText"),
        doseFormCode = text("doseFormCode"),
        doseFormName = text("doseFormText"),
        strength = text("strength"),
        substitution = text("substitution"),
        dispensable = text("dispensable")?.let { it == "true" || it == "1" },
    )
}
