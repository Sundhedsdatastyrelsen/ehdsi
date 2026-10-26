package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.w3c.dom.Element
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import javax.xml.XMLConstants

/**
 * Creates signed eHDSI HCP identity assertions, which the OpenNCP client connector requires on every call.
 *
 * The rules the connector enforces are in OpenNCP's HcpAssertionValidation and DefaultPolicyManagerImpl. See also
 * NCP/test-tool/templates/xcpd/hcp.xml.
 */
class HcpAssertionFactory(
    private val hcp: OpenNcp.HcpConfig,
    private val issuer: String,
    private val signingKey: OpenNcp.SigningKey,
    private val clock: Clock = Clock.systemUTC(),
) {
    fun create(): Element {
        val now = Instant.now(clock).truncatedTo(ChronoUnit.SECONDS)
        val doc = XmlUtils.newDocument()
        val assertion = XmlUtils.appendChild(doc, SAML, "Assertion").apply {
            // Declare namespaces as attributes: the serializer would otherwise add them only on output, and then
            // canonicalization when signing (in-memory DOM) differs from when verifying (parsed output).
            XmlUtils.declareNamespaces(this, SAML)
            setAttribute("ID", "_" + UUID.randomUUID())
            setIdAttribute("ID", true)
            setAttribute("IssueInstant", now.toString())
            setAttribute("Version", "2.0")
        }
        val issuer = XmlUtils.appendChild(assertion, SAML, "Issuer", issuer)
        issuer.setAttribute("NameQualifier", "urn:ehdsi:assertions:hcp")
        val subject = XmlUtils.appendChild(assertion, SAML, "Subject")
        XmlUtils.appendChild(subject, SAML, "NameID", hcp.nameId)
            .setAttribute("Format", "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
        XmlUtils.appendChild(subject, SAML, "SubjectConfirmation")
            .setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches")
        XmlUtils.appendChild(assertion, SAML, "Conditions").apply {
            setAttribute("NotBefore", now.toString())
            setAttribute("NotOnOrAfter", now.plus(hcp.validity).toString())
        }
        val authnStatement = XmlUtils.appendChild(assertion, SAML, "AuthnStatement")
        authnStatement.setAttribute("AuthnInstant", now.toString())
        val authnContext = XmlUtils.appendChild(authnStatement, SAML, "AuthnContext")
        XmlUtils.appendChild(authnContext, SAML, "AuthnContextClassRef", hcp.authnContextClassRef)

        val attributes = XmlUtils.appendChild(assertion, SAML, "AttributeStatement")
        fun attribute(name: String, friendlyName: String): Element =
            XmlUtils.appendChild(attributes, SAML, "Attribute").apply {
                setAttribute("FriendlyName", friendlyName)
                setAttribute("Name", name)
                setAttribute("NameFormat", "urn:oasis:names:tc:SAML:2.0:attrname-format:uri")
            }

        fun textAttribute(name: String, friendlyName: String, vararg values: String) {
            val attr = attribute(name, friendlyName)
            values.forEach { XmlUtils.appendChild(attr, SAML, "AttributeValue", it) }
        }

        /** An HL7 element as the only value of an attribute, with HL7 as its (explicitly declared) default namespace. */
        fun hl7Attribute(name: String, friendlyName: String, hl7Name: String): Element {
            val value = XmlUtils.appendChild(attribute(name, friendlyName), SAML, "AttributeValue")
            return XmlUtils.appendChild(value, HL7, hl7Name).apply {
                setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns", HL7.uri)
            }
        }

        textAttribute("urn:ihe:iti:xca:2010:homeCommunityId", "HCI Identifier", hcp.homeCommunityId)
        textAttribute("urn:oasis:names:tc:xspa:1.0:subject:subject-id", "XSPA Subject", hcp.subjectId)
        hl7Attribute("urn:oasis:names:tc:xacml:2.0:subject:role", "XSPA Role", "Role").apply {
            setAttribute("code", hcp.roleCode)
            setAttribute("codeSystem", "2.16.840.1.113883.2.9.6.2.7")
            setAttribute("codeSystemName", "ISCO")
            setAttribute("displayName", hcp.roleDisplayName)
        }
        textAttribute("urn:oasis:names:tc:xspa:1.0:subject:organization", "XSPA Organization", hcp.organization)
        textAttribute(
            "urn:oasis:names:tc:xspa:1.0:subject:organization-id", "XSPA Organization ID", hcp.organizationId,
        )
        textAttribute("urn:ehdsi:names:subject:healthcare-facility-type", "XSPA Healthcare Facility", hcp.facilityType)
        hl7Attribute("urn:oasis:names:tc:xspa:1.0:subject:purposeofuse", "XSPA Purpose of Use", "PurposeOfUse").apply {
            setAttribute("code", hcp.purposeOfUse)
            setAttribute("codeSystem", "3bc18518-d305-46c2-a8d6-94bd59856e9e")
            setAttribute("codeSystemName", "eHDSI PurposeOfUse")
            setAttribute("displayName", hcp.purposeOfUse.lowercase().replaceFirstChar { it.uppercase() })
        }
        textAttribute("urn:oasis:names:tc:xspa:1.0:environment:locality", "XSPA Locality", hcp.locality)
        textAttribute(
            "urn:oasis:names:tc:xspa:1.0:subject:hl7:permission", "Hl7 Permissions",
            *hcp.permissions.map { "urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:$it" }.toTypedArray(),
        )

        // The SAML schema requires the signature directly after Issuer.
        XmlUtils.sign(assertion, signingKey.key, signingKey.cert, insertBefore = subject)
        return assertion
    }

    companion object {
        private val SAML = XmlNamespace.SAML

        /** HL7 v3 as the default namespace, as in the eHDSI examples. */
        private val HL7 = XmlNamespace(null, XmlNamespace.HL7.uri)
    }
}
