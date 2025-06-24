package dk.sundhedsdatastyrelsen.ncpeh.authentication.builder;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Token;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class SoapEnvelopeBuilder {

    private final AssertionBuilder assertionBuilder;

    public SoapEnvelopeBuilder() {
        this.assertionBuilder = new AssertionBuilder();
    }

    public String buildSoapEnvelope(Token token) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Create SOAP Envelope
        Element envelope = doc.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Envelope");
        envelope.setAttribute("xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
        envelope.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
        envelope.setAttribute("xmlns:saml", "urn:oasis:names:tc:SAML:2.0:assertion");
        envelope.setAttribute("xmlns:wsa", "http://www.w3.org/2005/08/addressing");
        envelope.setAttribute("xmlns:wsp", "http://schemas.xmlsoap.org/ws/2004/09/policy");
        envelope.setAttribute("xmlns:wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        envelope.setAttribute("xmlns:wst", "http://docs.oasis-open.org/ws-sx/ws-trust/200512");
        envelope.setAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        doc.appendChild(envelope);

        // Create SOAP Header
        Element header = doc.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Header");
        envelope.appendChild(header);

        // Add Security element to header
        Element security = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Security");
        security.setAttribute("mustUnderstand", "1");
        security.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", "security");
        header.appendChild(security);

        // Add Timestamp to Security
        Element timestamp = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Timestamp");
        timestamp.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", "ts");
        security.appendChild(timestamp);

        Element created = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Created");
        created.setTextContent(token.getIssueInstant());
        timestamp.appendChild(created);

        // Add Signature to Security (this would be the SOAP-level signature)
        Element soapSignature = createSoapSignature(doc, token);
        security.appendChild(soapSignature);

        // Add WS-Addressing elements to header
        Element action = doc.createElementNS("http://www.w3.org/2005/08/addressing", "wsa:Action");
        action.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", "action");
        action.setTextContent("http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue");
        header.appendChild(action);

        Element messageId = doc.createElementNS("http://www.w3.org/2005/08/addressing", "wsa:MessageID");
        messageId.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", "messageID");
        messageId.setTextContent("urn:uuid:" + java.util.UUID.randomUUID().toString());
        header.appendChild(messageId);

        Element relatesTo = doc.createElementNS("http://www.w3.org/2005/08/addressing", "wsa:RelatesTo");
        relatesTo.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", "relatesTo");
        relatesTo.setTextContent("urn:uuid:" + java.util.UUID.randomUUID().toString());
        header.appendChild(relatesTo);

        // Create SOAP Body
        Element body = doc.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Body");
        body.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", "body");
        envelope.appendChild(body);

        // Add RequestSecurityTokenResponseCollection to body
        Element responseCollection = doc.createElementNS("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "wst:RequestSecurityTokenResponseCollection");
        body.appendChild(responseCollection);

        Element response = doc.createElementNS("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "wst:RequestSecurityTokenResponse");
        response.setAttribute("Context", "urn:uuid:" + java.util.UUID.randomUUID().toString());
        responseCollection.appendChild(response);

        // Add TokenType
        Element tokenType = doc.createElementNS("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "wst:TokenType");
        tokenType.setTextContent("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        response.appendChild(tokenType);

        // Add RequestedSecurityToken with the SAML assertion
        Element requestedToken = doc.createElementNS("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "wst:RequestedSecurityToken");
        response.appendChild(requestedToken);

        // Get the SAML assertion from AssertionBuilder
        String assertionXml = assertionBuilder.buildAssertionXml(token);
        
        // Parse the assertion XML and import it into our document
        DocumentBuilder assertionBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document assertionDoc = assertionBuilder.parse(new java.io.ByteArrayInputStream(assertionXml.getBytes()));
        Element assertionElement = assertionDoc.getDocumentElement();
        
        // Import the assertion element into our SOAP document
        Element importedAssertion = (Element) doc.importNode(assertionElement, true);
        requestedToken.appendChild(importedAssertion);

        // Add AppliesTo
        Element appliesTo = doc.createElementNS("http://schemas.xmlsoap.org/ws/2004/09/policy", "wsp:AppliesTo");
        response.appendChild(appliesTo);

        Element endpointRef = doc.createElementNS("http://www.w3.org/2005/08/addressing", "wsa:EndpointReference");
        appliesTo.appendChild(endpointRef);

        Element address = doc.createElementNS("http://www.w3.org/2005/08/addressing", "wsa:Address");
        address.setTextContent("https://fmk");
        endpointRef.appendChild(address);

        // Add Lifetime
        Element lifetime = doc.createElementNS("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "wst:Lifetime");
        response.appendChild(lifetime);

        Element createdLifetime = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Created");
        createdLifetime.setTextContent(token.getIssueInstant());
        lifetime.appendChild(createdLifetime);

        Element expires = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Expires");
        expires.setTextContent(token.getConditions().getNotOnOrAfter());
        lifetime.appendChild(expires);

        // Transform to string
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    private Element createSoapSignature(Document doc, Token token) {
        Element signature = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:Signature");
        
        Element signedInfo = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:SignedInfo");
        
        Element canonMethod = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:CanonicalizationMethod");
        canonMethod.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        
        Element sigMethod = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:SignatureMethod");
        sigMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#rsa-sha1");
        
        // Add references for SOAP elements
        addReference(doc, signedInfo, "body");
        addReference(doc, signedInfo, "ts");
        addReference(doc, signedInfo, "messageID");
        addReference(doc, signedInfo, "relatesTo");
        addReference(doc, signedInfo, "action");
        
        signedInfo.appendChild(canonMethod);
        signedInfo.appendChild(sigMethod);
        signature.appendChild(signedInfo);

        Element signatureValue = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:SignatureValue");
        signatureValue.setTextContent("SOAP_SIGNATURE_VALUE_PLACEHOLDER");
        signature.appendChild(signatureValue);

        Element keyInfo = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:KeyInfo");
        Element x509Data = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:X509Data");
        Element x509Certificate = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:X509Certificate");
        x509Certificate.setTextContent(token.getSignature().getCertificate());
        x509Data.appendChild(x509Certificate);
        keyInfo.appendChild(x509Data);
        signature.appendChild(keyInfo);
        
        return signature;
    }

    private void addReference(Document doc, Element signedInfo, String uri) {
        Element ref = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:Reference");
        ref.setAttribute("URI", "#" + uri);
        
        Element transforms = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:Transforms");
        Element transform = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:Transform");
        transform.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        transforms.appendChild(transform);
        ref.appendChild(transforms);
        
        Element digestMethod = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:DigestMethod");
        digestMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
        
        Element digestValue = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:DigestValue");
        digestValue.setTextContent("DIGEST_VALUE_PLACEHOLDER");
        
        ref.appendChild(digestMethod);
        ref.appendChild(digestValue);
        signedInfo.appendChild(ref);
    }
} 