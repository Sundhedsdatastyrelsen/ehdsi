package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.w3c.dom.Element
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import javax.xml.XMLConstants

class TrcAssertionFactory(
    private val issuer: String,
    private val signingKey: OpenNcp.SigningKey,
    private val validity: Duration,
    private val clock: Clock = Clock.systemUTC(),
) {
    private val xpath = XPathWrapper(XmlNamespace.Companion.SAML)

    fun create(
        hcpAssertion: Element,
        /** Patient id is the full patient id including OID, eg. 0410009234^^^&1.2.208.176.1.2&ISO */
        patientId: String,
        purposeOfUseValue: String = "TREATMENT",
        purposeOfUseDisplayName: String = "Treatment",
    ): Element {
        // From eHDSI_SAML_Profile_v10.0.0, https://webgate.ec.europa.eu/fpfis/wikis/x/AeTzN on NameID:
        //
        // > Identifier of the health professional encoded as an X.509 subject
        // > name, an e-Mail address or as a string value (unspecified format).
        // > The same identifier and encoding MUST be used as for the
        // > referenced HP Identity Assertion.
        val hcpAssertionId = xpath.evalString("@ID", hcpAssertion)
        val hcpAssertionSubjectNameId = xpath.evalString("saml:Subject/saml:NameID", hcpAssertion)
        val hcpAssertionSubjectNameIdFormat =
            xpath.evalString("saml:Subject/saml:NameID/@Format", hcpAssertion)

        val now = Instant.now(clock).truncatedTo(ChronoUnit.SECONDS)
        val doc = XmlUtils.newDocument()
        val assertion = XmlUtils.appendChild(doc, XmlNamespace.Companion.SAML, "Assertion").apply {
            // Declare namespaces as attributes: the serializer would otherwise add them only on output, and then
            // canonicalization when signing (in-memory DOM) differs from when verifying (parsed output).
            XmlUtils.declareNamespaces(this, XmlNamespace.Companion.SAML)
            setAttribute("ID", "_" + UUID.randomUUID())
            setIdAttribute("ID", true)
            setAttribute("IssueInstant", now.toString())
            setAttribute("Version", "2.0")
        }
        val issuer = XmlUtils.appendChild(assertion, XmlNamespace.Companion.SAML, "Issuer", issuer)
            issuer.setAttribute("NameQualifier", "urn:ehdsi:assertions:trc")
        val subject = XmlUtils.appendChild(assertion, XmlNamespace.Companion.SAML, "Subject")
        XmlUtils.appendChild(subject, XmlNamespace.Companion.SAML, "NameID", hcpAssertionSubjectNameId)
            .setAttribute("Format", hcpAssertionSubjectNameIdFormat)
        XmlUtils.appendChild(subject, XmlNamespace.Companion.SAML, "SubjectConfirmation")
            .setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches")
        XmlUtils.appendChild(assertion, XmlNamespace.Companion.SAML, "Conditions").apply {
            setAttribute("NotBefore", now.toString())
            setAttribute("NotOnOrAfter", now.plus(validity).toString())
        }
        val advice = XmlUtils.appendChild(assertion, XmlNamespace.Companion.SAML, "Advice")
        XmlUtils.appendChild(advice, XmlNamespace.Companion.SAML, "AssertionIDRef", hcpAssertionId)
        val authnStatement = XmlUtils.appendChild(assertion, XmlNamespace.Companion.SAML, "AuthnStatement")
        authnStatement.setAttribute("AuthnInstant", now.toString())
        val authnContext = XmlUtils.appendChild(authnStatement, XmlNamespace.Companion.SAML, "AuthnContext")
        XmlUtils.appendChild(authnContext, XmlNamespace.Companion.SAML, "AuthnContextClassRef", "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession")

        val attributes = XmlUtils.appendChild(assertion, XmlNamespace.Companion.SAML, "AttributeStatement")

        val xspaSubject = XmlUtils.appendChild(attributes, XmlNamespace.Companion.SAML, "Attribute")
        xspaSubject.setAttribute("FriendlyName", "XSPA subject")
        xspaSubject.setAttribute("Name", "urn:oasis:names:tc:xspa:1.0:subject:subject-id")
        xspaSubject.setAttribute("NameFormat", "urn:oasis:names:tc:SAML:2.0:attrname-format:uri")
        XmlUtils.appendChild(xspaSubject, XmlNamespace.Companion.SAML, "AttributeValue", patientId)

        val purposeOfUseAttribute = XmlUtils.appendChild(attributes, XmlNamespace.Companion.SAML, "Attribute")
        purposeOfUseAttribute.setAttribute("FriendlyName", "XSPA Purpose of Use")
        purposeOfUseAttribute.setAttribute("Name", "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse")
        purposeOfUseAttribute.setAttribute("NameFormat", "urn:oasis:names:tc:SAML:2.0:attrname-format:uri")
        val purposeOfUseAttrValue = XmlUtils.appendChild(purposeOfUseAttribute, XmlNamespace.Companion.SAML, "AttributeValue")
        // Purpose of use has an explicitly declared default namespace in all the examples. It must be unprefixed: a
        // prefix would only be declared by the serializer on output, so the signed and the sent XML would differ.
        val purposeOfUse = XmlUtils.appendChild(purposeOfUseAttrValue, XmlNamespace(null, XmlNamespace.Companion.HL7.uri), "PurposeOfUse").apply {
            setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns", XmlNamespace.Companion.HL7.uri)
        }
        purposeOfUse.setAttribute("code", purposeOfUseValue)
        purposeOfUse.setAttribute("codeSystem", OpenNcp.PURPOSE_OF_USE_CODE_SYSTEM)
        purposeOfUse.setAttribute("codeSystemName", OpenNcp.PURPOSE_OF_USE_CODE_SYSTEM_NAME)
        purposeOfUse.setAttribute("displayName", purposeOfUseDisplayName)

        // The SAML schema requires the signature directly after Issuer.
        XmlUtils.sign(assertion, signingKey.key, signingKey.cert, insertBefore = subject)
        return assertion
    }
}
