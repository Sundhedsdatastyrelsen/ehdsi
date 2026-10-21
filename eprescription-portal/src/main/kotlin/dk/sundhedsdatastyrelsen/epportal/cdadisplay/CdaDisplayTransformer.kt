package dk.sundhedsdatastyrelsen.epportal.cdadisplay

import net.sf.saxon.lib.ResourceResolver
import net.sf.saxon.s9api.Processor
import net.sf.saxon.s9api.QName
import net.sf.saxon.s9api.XdmAtomicValue
import net.sf.saxon.s9api.XsltExecutable
import net.sf.saxon.trans.XPathException
import org.xml.sax.InputSource
import java.io.StringReader
import java.io.StringWriter
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.sax.SAXSource
import javax.xml.transform.stream.StreamSource

/**
 * Renders a CDA document as HTML with the eHDSI CDA display tool, the XSLT stylesheets copied from OpenNCP into
 * `resources/cda-display-tool`, with the portal's additions in `portal.xsl` (see the README there). The stylesheets
 * look up labels and code translations in [ValueSets].
 *
 * The compiled stylesheet is thread-safe, so one instance can be shared.
 */
class CdaDisplayTransformer(private val config: Config) {
    /** @property language the language of labels and code translations, e.g. `en-GB` */
    data class Config(val language: String)

    private val processor = Processor(false)

    private val executable: XsltExecutable = run {
        // The portal's additions, on top of the upstream cda.xsl
        val url = resource("$STYLESHEET_DIR/portal.xsl")
        processor.newXsltCompiler().compile(StreamSource(url.openStream(), url.toExternalForm()))
    }

    internal val valueSets = ValueSets(processor)

    fun toHtml(cdaXml: String): String {
        val output = StringWriter()
        executable.load().apply {
            setSource(SAXSource(newSecureXmlReader(), InputSource(StringReader(cdaXml))))
            // The stylesheets load value sets with document() from this directory, which is resolved to valueSets
            setParameter(QName("epsosLangDir"), XdmAtomicValue(VALUE_SET_DIR))
            resourceResolver = ResourceResolver { request ->
                if (request.uri.startsWith("$VALUE_SET_DIR/")) {
                    valueSets.get(request.uri.removePrefix("$VALUE_SET_DIR/"))?.underlyingNode
                        ?: throw XPathException("Unknown value set ${request.uri}")
                } else {
                    null // The stylesheets' own files, such as the CSS, resolved relative to the stylesheets
                }
            }
            setParameter(QName("userLang"), XdmAtomicValue(config.language))
            // Hides the dispense form's submit button, css/style.css hides the form.
            setParameter(QName("allowDispense"), XdmAtomicValue("false"))
            destination = processor.newSerializer(output)
            transform()
        }
        return output.toString()
    }

    /** The documents come from foreign countries, so reject DTDs, which also rules out external entities. */
    private fun newSecureXmlReader() = SAXParserFactory.newDefaultNSInstance().apply {
        setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
        setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    }.newSAXParser().xmlReader

    private fun resource(path: String) =
        requireNotNull(javaClass.getResource(path)) { "Missing classpath resource $path" }

    private companion object {
        const val STYLESHEET_DIR = "/cda-display-tool"
        // Not a real location, only a prefix for the resolver to recognise. It must not end with a slash, since the
        // stylesheets add one.
        const val VALUE_SET_DIR = "urn:x-epportal:value-sets"
    }
}
