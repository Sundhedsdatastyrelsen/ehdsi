package dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespaces;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlUtils;
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
     * Create an bootstrap-to-IDWS SOAP request for the STS.
     *
     * @param bst             the parameters for the bootstrap token
     * @param soapCertificate the certificate and key used to sign the SOAP request body
     */
    public static BootstrapTokenExchangeRequest of(BootstrapTokenParams bst, CertificateAndKey soapCertificate) throws AuthenticationException {
        return of(bst.audience(), BootstrapToken.of(bst), soapCertificate);
    }

    /**
     * Create a bootstrap-to-IDWS SOAP request for the STS.
     *
     * @param audience        where do we want access (e.g. "https://fmk")
     * @param bootstrapToken  the bootstrap token
     * @param soapCertificate the certificate and key used to sign the SOAP request body
     */
    public static BootstrapTokenExchangeRequest of(String audience, BootstrapToken bootstrapToken, CertificateAndKey soapCertificate)
        throws AuthenticationException {
        var clock = Clock.systemUTC();
        var doc = XmlUtils.newDocument();

        var envelope = XmlUtils.appendChild(doc, XmlNamespaces.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope,
            XmlNamespaces.SOAP,
            XmlNamespaces.DS,
            XmlNamespaces.SAML,
            XmlNamespaces.XSI,
            XmlNamespaces.WSSE,
            XmlNamespaces.WST,
            XmlNamespaces.WST13,
            XmlNamespaces.WST14,
            XmlNamespaces.WSA,
            XmlNamespaces.WSP,
            XmlNamespaces.WSU);

        var soapHeader = XmlUtils.appendChild(envelope, XmlNamespaces.SOAP, "Header");
        var action = XmlUtils.appendChild(soapHeader, XmlNamespaces.WSA, "Action", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue");
        XmlUtils.setIdAttribute(action, XmlNamespaces.WSU, "Id", "action");

        var messageID = XmlUtils.appendChild(soapHeader, XmlNamespaces.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID());
        XmlUtils.setIdAttribute(messageID, XmlNamespaces.WSU, "Id", "messageID");

        var security = XmlUtils.appendChild(soapHeader, XmlNamespaces.WSSE, "Security");
        XmlUtils.setIdAttribute(security, XmlNamespaces.WSU, "Id", "security");
        security.setAttribute("mustUnderstand", "1");

        var timestamp = XmlUtils.appendChild(security, XmlNamespaces.WSU, "Timestamp");
        XmlUtils.setIdAttribute(timestamp, XmlNamespaces.WSU, "Id", "ts");
        XmlUtils.appendChild(timestamp, XmlNamespaces.WSU, "Created", DateTimeFormatter.ISO_INSTANT.format(Instant.now(clock)));

        var soapBody = XmlUtils.appendChild(envelope, XmlNamespaces.SOAP, "Body");
        XmlUtils.setIdAttribute(soapBody, XmlNamespaces.WSU, "Id", "body");

        var requestSecurityToken = XmlUtils.appendChild(soapBody, XmlNamespaces.WST13, "RequestSecurityToken");
        requestSecurityToken.setAttribute("Context", "urn:uuid:" + UUID.randomUUID());
        XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WST13, "TokenType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WST13, "RequestType", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue");

        var actAs = XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WST14, "ActAs");
        actAs.appendChild(doc.importNode(bootstrapToken.element(), true));

        XmlUtils.appendChild(
            XmlUtils.appendChild(
                XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WSP, "AppliesTo"),
                XmlNamespaces.WSA, "EndpointReference"),
            XmlNamespaces.WSA, "Address", audience);

        signSoapRequest(security, soapCertificate);
        return new BootstrapTokenExchangeRequest(doc);
    }

    private static void signSoapRequest(Element security, CertificateAndKey certificate) throws AuthenticationException {
        XmlUtils.sign(security, null, List.of("#body", "#ts", "#messageID", "#action"), certificate);
    }
}
