package dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlUtils;
import org.w3c.dom.Element;

import java.security.cert.CertificateEncodingException;
import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * An OIO SAML bootstrap token XML element, meant for exchanging with an STS to get an IDWS token.
 */
public class BootstrapToken {
    private final Element bst;

    private BootstrapToken(Element bst) {
        this.bst = bst;
    }

    static {
        // Avoid <CR><LF> in signature values.
        // https://bugs.openjdk.org/browse/JDK-8264194
        // We hope that no other packages sets this to another value.  If line breaks start to appear, that might
        // be the cause.
        System.setProperty("com.sun.org.apache.xml.internal.security.ignoreLineBreaks", "true");
    }

    public Element element() {
        return bst;
    }

    /**
     * Create and sign an OIO SAML bootstrap token XML element, meant for exchanging with an STS to get an IDWS token.
     *
     * @param bst the parameters for the bootstrap token
     * @return the Bootstrap token object
     * @throws AuthenticationException if token generation fails
     */
    public static BootstrapToken of(BootstrapTokenParams bst) throws AuthenticationException {
        var clock = Clock.systemUTC();
        // we don't want higher resolution than seconds
        var now = Instant.now(clock).truncatedTo(ChronoUnit.SECONDS);

        var doc = XmlUtils.newDocument();

        var assertion = XmlUtils.appendChild(doc, XmlNamespace.SAML, "Assertion");
        var assertionId = "_" + UUID.randomUUID();
        XmlUtils.declareNamespaces(assertion, XmlNamespace.XSD, XmlNamespace.XSI);
        assertion.setAttribute("IssueInstant", DateTimeFormatter.ISO_INSTANT.format(now));
        assertion.setAttribute("Version", "2.0");
        assertion.setAttribute("ID", assertionId);
        assertion.setIdAttribute("ID", true);

        XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Issuer", bst.issuer());
        var subject = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Subject");

        var nameID = XmlUtils.appendChild(subject, XmlNamespace.SAML, "NameID", bst.nameId());
        nameID.setAttribute("Format", bst.nameIdFormat());

        var subjectConfirmation = XmlUtils.appendChild(subject, XmlNamespace.SAML, "SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
        var subjectConfirmationData = XmlUtils.appendChild(subjectConfirmation, XmlNamespace.SAML, "SubjectConfirmationData");
        var keyInfo = XmlUtils.appendChild(subjectConfirmationData, XmlNamespace.DS, "KeyInfo");
        try {
            var certB64 = Base64.getEncoder().encodeToString(bst.idpCert().certificate().getEncoded());
            XmlUtils.appendChild(
                XmlUtils.appendChild(keyInfo, XmlNamespace.DS, "X509Data"),
                XmlNamespace.DS,
                "X509Certificate",
                certB64);
        } catch (CertificateEncodingException e) {
            throw new IllegalArgumentException("Could not encode certificate", e);
        }

        var conditions = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Conditions");
        conditions.setAttribute(
            "NotBefore", DateTimeFormatter.ISO_INSTANT.format(now));
        conditions.setAttribute(
            "NotOnOrAfter", DateTimeFormatter.ISO_INSTANT.format(now.plus(2, ChronoUnit.HOURS)));

        XmlUtils.appendChild(
            XmlUtils.appendChild(conditions, XmlNamespace.SAML, "AudienceRestriction"),
            XmlNamespace.SAML,
            "Audience",
            bst.audience());

        var authnStatement = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "AuthnStatement");
        authnStatement.setAttribute("AuthnInstant", DateTimeFormatter.ISO_INSTANT.format(now));
        authnStatement.setAttribute("SessionIndex", assertionId);
        XmlUtils.appendChild(
            XmlUtils.appendChild(authnStatement, XmlNamespace.SAML, "AuthnContext"),
            XmlNamespace.SAML,
            "AuthnContextClassRef",
            "urn:oasis:names:tc:SAML:2.0:ac:classes:X509");

        var attributeStatement = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "AttributeStatement");
        for (var attr : bst.attributes()) {
            switch (attr) {
                case BootstrapTokenParams.SamlAttribute.Raw(var node): {
                    attributeStatement.appendChild(doc.importNode(node, true));
                    break;
                }
                case BootstrapTokenParams.SamlAttribute.New(var name, var friendlyName, var vals): {
                    var attrEl = XmlUtils.appendChild(attributeStatement, XmlNamespace.SAML, "Attribute");
                    attrEl.setAttribute("FriendlyName", friendlyName);
                    attrEl.setAttribute("Name", name);
                    for (var val : vals) {
                        var valEl = XmlUtils.appendChild(attrEl, XmlNamespace.SAML, "AttributeValue", val);
                        XmlUtils.setAttribute(valEl, XmlNamespace.XSI, "type", "xsd:string");
                    }
                    break;
                }
            }
        }

        signAssertion(assertion, bst.idpCert());
        return new BootstrapToken(assertion);
    }

    private static void signAssertion(Element assertion, CertificateAndKey idpCertificate) throws AuthenticationException {
        var id = "#" + assertion.getAttribute("ID");
        // "Subject" is the second child element, put the signature before that.
        var subject = assertion.getChildNodes().item(1);
        CertificateUtils.signXml(assertion, subject, List.of(id), idpCertificate);
    }
}
