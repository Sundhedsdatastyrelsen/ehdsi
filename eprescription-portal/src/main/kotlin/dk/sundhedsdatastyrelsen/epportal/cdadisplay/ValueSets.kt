package dk.sundhedsdatastyrelsen.epportal.cdadisplay

import net.sf.saxon.s9api.Processor
import net.sf.saxon.s9api.XdmNode
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.ZipInputStream
import javax.xml.transform.stream.StreamSource

/**
 * The value sets that the CDA display tool looks up labels and code translations in, from
 * `resources/value-sets/value-sets.zip` (made by `scripts/update-value-sets.sh`).
 *
 * Each value set is parsed the first time it is used and then kept. Saxon only keeps documents loaded with
 * `document()` for the duration of one transformation, so without this the display tool would parse them again for
 * every prescription, and the substance list alone is 13 MB.
 */
internal class ValueSets(private val processor: Processor) {
    // The compressed zip is a few MB, while the value sets are tens of MB, so only the parsed ones are kept unpacked
    private val zip: ByteArray =
        requireNotNull(javaClass.getResource(ZIP)) { "Missing classpath resource $ZIP" }.readBytes()

    /** The file names of the value sets, e.g. `1.3.6.1.4.1.12559.11.10.1.3.1.42.46.xml`. */
    val names: Set<String> = entries { generateSequence { nextEntry }.map { it.name }.toSet() }

    private val parsed = ConcurrentHashMap<String, XdmNode>()

    /** @return the value set with the given file name, or null if there is none */
    fun get(name: String): XdmNode? =
        if (name in names) parsed.computeIfAbsent(name, ::parse) else null

    private fun parse(name: String): XdmNode = entries {
        generateSequence { nextEntry }.first { it.name == name }
        processor.newDocumentBuilder().build(StreamSource(this, "$ZIP/$name"))
    }

    private fun <T> entries(block: ZipInputStream.() -> T): T = ZipInputStream(zip.inputStream()).use(block)

    private companion object {
        const val ZIP = "/value-sets/value-sets.zip"
    }
}
