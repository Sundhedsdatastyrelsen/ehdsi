package dk.sundhedsdatastyrelsen.epportal.cdadisplay

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import net.sf.saxon.s9api.SaxonApiException
import java.io.File
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class CdaDisplayTransformerTest {
    private fun example(id: String): String = TestUtils.resource("ep-l3-examples/$id.xml")!!.readText()

    @Test
    fun `renders an ePrescription`() {
        val html = TestUtils.cdaDisplay.toHtml(example("513671704568702L3"))

        assertContains(html, "Knudsen")
        assertContains(html, "Babbage")
        assertContains(html, "Etindros 21")
        assertContains(html, "1 tablet morgen daglig i 3 uger, herefter en uges pause")
        // Section labels are looked up in the value sets, and would be shown as label codes if that failed
        assertContains(html, "Prescription Details")
        assertContains(html, "Packaging Details")
        // The example's ingredients are coded, and their names are looked up in the substance value set. The document
        // itself says "DROSPIRENON".
        assertContains(html, "Drospirenone")
    }

    @Test
    fun `shows the narrative in a collapsed panel`() {
        val html = TestUtils.cdaDisplay.toHtml(example("513671704568702L3"))

        assertContains(html, """<input id="collapsible-original-narrative" class="toggle" type="checkbox">""")
        assertContains(html, "Original narrative")
        // Only in the narrative block
        assertContains(html, "Data source: FMK 1.4.6")
        // Between the Prescriber panel and the coded content
        val narrative = html.indexOf("collapsible-original-narrative")
        assertTrue(html.indexOf("collapsible-prescriber-header") < narrative)
        assertTrue(narrative < html.indexOf("td_creation_date"))
    }

    /** portal.xsl overrides epCda with a copy of upstream's, plus the narrative. This catches an upstream change. */
    @Test
    fun `portal_xsl's copy of epCda matches upstream`() {
        fun read(path: String) = TestUtils.resource("cda-display-tool/$path")!!.readText()
        fun normalize(xsl: String) = xsl.replace(Regex("""\s+"""), " ").trim()

        val upstream = Regex("""<xsl:template name="epCda">(.*?)</xsl:template>""", RegexOption.DOT_MATCHES_ALL)
            .find(read("ep/ep.xsl"))!!.groupValues[1]
        val copy = Regex("""<xsl:template name="epCda">(.*?)<!-- End of the copy""", RegexOption.DOT_MATCHES_ALL)
            .find(read("portal.xsl"))!!.groupValues[1]
            .replace("""<xsl:call-template name="originalNarrative"/>""", "")

        assertEquals(normalize(upstream), normalize(copy))
    }

    @Test
    fun `renders the other example`() {
        val html = TestUtils.cdaDisplay.toHtml(example("513671704568902L3"))

        assertContains(html, "Abasaglar KwikPen")
        assertContains(html, "Insulin glargin")
    }

    @Test
    fun `uses the portal's changes to the display tool's css`() {
        val html = TestUtils.cdaDisplay.toHtml(example("513671704568702L3"))

        assertFalse(html.contains("font-family: 'Montserrat'"))
        assertContains(html, "fieldset:has(> table.ep_table > tbody > tr > td > input[name^=\"substituted_\"])")
    }

    @Test
    fun `rejects documents with a DTD`() {
        val xml = """
            <?xml version="1.0"?>
            <!DOCTYPE ClinicalDocument [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
            <ClinicalDocument xmlns="urn:hl7-org:v3"><title>&xxe;</title></ClinicalDocument>
        """.trimIndent()

        assertFailsWith<SaxonApiException> { TestUtils.cdaDisplay.toHtml(xml) }
    }

    /** scripts/update-value-sets.sh packs the referenced value sets. This catches a stylesheet update without it. */
    @Test
    fun `every value set the stylesheets reference is in the resources`() {
        val stylesheetDir = File(TestUtils.resource("cda-display-tool/cda.xsl")!!.toURI()).parentFile
        val valueSetFile = Regex("""1\.3\.6\.1\.4\.1\.12559\.11\.10\.1\.3\.1\.42\.\d+\.xml""")

        val referenced = stylesheetDir.walk()
            .filter { it.extension == "xsl" }
            .flatMap { valueSetFile.findAll(it.readText()).map { match -> match.value } }
            .toSortedSet()

        assertEquals(emptyList(), referenced.filterNot { it in TestUtils.cdaDisplay.valueSets.names })
    }

    @Test
    fun `value sets are parsed once`() {
        val valueSets = TestUtils.cdaDisplay.valueSets
        val substances = "1.3.6.1.4.1.12559.11.10.1.3.1.42.61.xml"

        assertSame(valueSets.get(substances), valueSets.get(substances))
        assertNull(valueSets.get("unknown.xml"))
    }
}
