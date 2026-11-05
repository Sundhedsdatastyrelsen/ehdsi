package dk.sundhedsdatastyrelsen.epportal.openncp

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.junit.jupiter.api.Test
import org.w3c.dom.Element
import java.security.cert.X509Certificate
import java.time.Duration
import javax.xml.crypto.dsig.Reference
import javax.xml.crypto.dsig.XMLSignatureFactory
import javax.xml.crypto.dsig.dom.DOMValidateContext

/**
 * Verifies the HCP and TRC assertion signatures after a serialize/parse round trip.
 */
class AssertionSignatureTest {
    private val xpath = XPathWrapper(XmlNamespace.SOAP12, XmlNamespace.WSSE, XmlNamespace.SAML, XmlNamespace.DS)
    private val config = testOpenNcpConfig()
    private val signingKey = OpenNcp.loadSigningKey(config.signing)

    @Test
    fun `HCP and TRC assertions in one envelope verify after serialization`() {
        val hcp = HcpAssertionFactory(config.hcp, config.issuer, signingKey).create()
        val trc = TrcAssertionFactory(config.issuer, signingKey, Duration.ofHours(2)).create(
                hcp,
                "0410009234^^^&1.2.208.176.1.2&ISO"
            )
        val envelope = OpenNcpClient.envelope(OpenNcpClient.QUERY_DOCUMENTS_ACTION, listOf(hcp, trc)) {}
        val received = XmlUtils.parse(XmlUtils.writeDocumentToString(envelope))

        val assertions = xpath.evalElements("/soap12:Envelope/soap12:Header/wsse:Security/saml:Assertion", received)
        assertThat(assertions).hasSize(2)
        assertions.forEach { it.setIdAttribute("ID", true) }
        assertThat(assertions.flatMap { signatureFailures(it, signingKey.cert) }).isEmpty()
    }

    /** List errors with the assertion signatures, empty list means no errors. */
    private fun signatureFailures(assertion: Element, cert: X509Certificate): List<String> {
        val id = assertion.getAttribute("ID")
        val signatureElement = xpath.evalElement("ds:Signature", assertion) ?: return listOf("$id: no ds:Signature")
        val ctx = DOMValidateContext(cert.publicKey, signatureElement)
        val signature = XMLSignatureFactory.getInstance("DOM").unmarshalXMLSignature(ctx)
        if (signature.validate(ctx)) return emptyList()

        val failures = mutableListOf<String>()
        if (!signature.signatureValue.validate(ctx)) failures += "$id: SignedInfo signature value"
        signature.signedInfo.references.filterIsInstance<Reference>().filterNot { it.validate(ctx) }
            .forEach { failures += "$id: digest of reference URI='${it.uri}'" }
        return failures.ifEmpty { listOf("$id: invalid, but no failing component found") }
    }
}
