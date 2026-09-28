package dk.sundhedsdatastyrelsen.epportal.utils

import org.w3c.dom.Document
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

object XmlUtils {
    /**
     * Parses XML InputStream into a Document.
     *
     * @param xml the XML InputStream to parse
     * @return the parsed Document
     */
    fun parse(xml: InputStream): Document {
        xml.use {
            val builder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder()
            return builder.parse(xml)
        }
    }
}
