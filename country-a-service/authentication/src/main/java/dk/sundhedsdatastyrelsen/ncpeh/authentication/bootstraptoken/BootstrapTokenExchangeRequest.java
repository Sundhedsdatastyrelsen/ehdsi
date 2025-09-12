package dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * A SOAP request body for the bootstrap-to-IDWS SOSI-STS service.
 */
public class BootstrapTokenExchangeRequest {
    private final Document soapBody;

    private BootstrapTokenExchangeRequest(Document soapBody) {
        this.soapBody = soapBody;
    }

    public Document soapBody() {
        return soapBody;
    }

    /**
     * Create a bootstrap-to-IDWS SOAP request for the STS.
     *
     * @param bst             the parameters for the bootstrap token
     * @param soapCertificate the certificate and key used to sign the SOAP request body
     */
    public static BootstrapTokenExchangeRequest of(BootstrapTokenParams bst, CertificateAndKey soapCertificate) throws AuthenticationException {
        return of(bst.audience(), BootstrapToken.of(bst), soapCertificate);
    }

    private static BootstrapTokenExchangeRequest of(String audience, BootstrapToken bootstrapToken, CertificateAndKey soapCertificate)
        throws AuthenticationException {
        var clock = Clock.systemUTC();
        var doc = XmlUtils.newDocument();

        var envelope = XmlUtils.appendChild(doc, XmlNamespace.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope,
            XmlNamespace.SOAP,
            XmlNamespace.DS,
            XmlNamespace.SAML,
            XmlNamespace.XSI,
            XmlNamespace.WSSE,
            XmlNamespace.WST,
            XmlNamespace.WST13,
            XmlNamespace.WST14,
            XmlNamespace.WSA,
            XmlNamespace.WSP,
            XmlNamespace.WSU);

        var soapHeader = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var action = XmlUtils.appendChild(soapHeader, XmlNamespace.WSA, "Action", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue");
        XmlUtils.setIdAttribute(action, XmlNamespace.WSU, "Id", "action");

        var messageID = XmlUtils.appendChild(soapHeader, XmlNamespace.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID());
        XmlUtils.setIdAttribute(messageID, XmlNamespace.WSU, "Id", "messageID");

        var security = XmlUtils.appendChild(soapHeader, XmlNamespace.WSSE, "Security");
        XmlUtils.setIdAttribute(security, XmlNamespace.WSU, "Id", "security");
        security.setAttribute("mustUnderstand", "1");

        var timestamp = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.setIdAttribute(timestamp, XmlNamespace.WSU, "Id", "ts");
        XmlUtils.appendChild(timestamp, XmlNamespace.WSU, "Created", DateTimeFormatter.ISO_INSTANT.format(Instant.now(clock)));

        var soapBody = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");
        XmlUtils.setIdAttribute(soapBody, XmlNamespace.WSU, "Id", "body");

        var requestSecurityToken = XmlUtils.appendChild(soapBody, XmlNamespace.WST13, "RequestSecurityToken");
        requestSecurityToken.setAttribute("Context", "urn:uuid:" + UUID.randomUUID());
        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST13, "TokenType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST13, "RequestType", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue");

        var actAs = XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST14, "ActAs");
        actAs.appendChild(doc.importNode(bootstrapToken.element(), true));

        XmlUtils.appendChild(
            XmlUtils.appendChild(
                XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WSP, "AppliesTo"),
                XmlNamespace.WSA, "EndpointReference"),
            XmlNamespace.WSA, "Address", audience);

        signSoapRequest(security, soapCertificate);
        return new BootstrapTokenExchangeRequest(doc);
    }

    private static void signSoapRequest(Element security, CertificateAndKey certificate) throws AuthenticationException {
        CertificateUtils.signXml(security, null, List.of("#body", "#ts", "#messageID", "#action"), certificate);
    }
}
