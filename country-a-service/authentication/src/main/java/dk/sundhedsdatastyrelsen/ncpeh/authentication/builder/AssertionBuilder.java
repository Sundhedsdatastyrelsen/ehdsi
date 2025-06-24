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

public class AssertionBuilder {

    public String buildAssertionXml(Token token) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Create assertion with proper namespaces for NCP-BST format
        Element assertion = doc.createElement("Assertion");
        assertion.setAttribute("ID", token.getId());
        assertion.setAttribute("IssueInstant", token.getIssueInstant());
        assertion.setAttribute("Version", token.getVersion());
        assertion.setAttribute("xmlns", "urn:oasis:names:tc:SAML:2.0:assertion");
        assertion.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        doc.appendChild(assertion);

        // Issuer
        Element issuer = doc.createElement("Issuer");
        issuer.setTextContent(token.getIssuer());
        assertion.appendChild(issuer);

        // Signature
        Token.Signature sig = token.getSignature();
        Element signature = doc.createElement("Signature");
        signature.setAttribute("xmlns", "http://www.w3.org/2000/09/xmldsig#");
        
        Element signedInfo = doc.createElement("SignedInfo");
        
        Element canonMethod = doc.createElement("CanonicalizationMethod");
        canonMethod.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        
        Element sigMethod = doc.createElement("SignatureMethod");
        sigMethod.setAttribute("Algorithm", sig.getSignatureMethodAlgorithm());
        
        Element ref = doc.createElement("Reference");
        ref.setAttribute("URI", "#" + token.getId());
        
        Element transforms = doc.createElement("Transforms");
        Element t1 = doc.createElement("Transform");
        t1.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#enveloped-signature");
        Element t2 = doc.createElement("Transform");
        t2.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        transforms.appendChild(t1);
        transforms.appendChild(t2);
        ref.appendChild(transforms);
        
        Element digestMethod = doc.createElement("DigestMethod");
        digestMethod.setAttribute("Algorithm", sig.getDigestMethodAlgorithm());
        
        Element digestValue = doc.createElement("DigestValue");
        digestValue.setTextContent(sig.getDigestValue());
        
        ref.appendChild(digestMethod);
        ref.appendChild(digestValue);
        signedInfo.appendChild(canonMethod);
        signedInfo.appendChild(sigMethod);
        signedInfo.appendChild(ref);
        signature.appendChild(signedInfo);

        Element signatureValue = doc.createElement("SignatureValue");
        signatureValue.setTextContent(sig.getSignatureValue());
        signature.appendChild(signatureValue);

        Element keyInfo = doc.createElement("KeyInfo");
        Element x509Data = doc.createElement("X509Data");
        Element x509Certificate = doc.createElement("X509Certificate");
        x509Certificate.setTextContent(sig.getCertificate());
        x509Data.appendChild(x509Certificate);
        keyInfo.appendChild(x509Data);
        signature.appendChild(keyInfo);
        assertion.appendChild(signature);

        // Subject
        Token.Subject subject = token.getSubject();
        Element subjectElement = doc.createElement("Subject");
        
        Element nameId = doc.createElement("NameID");
        nameId.setAttribute("Format", subject.getNameIdFormat());
        nameId.setTextContent(subject.getNameIdValue());
        subjectElement.appendChild(nameId);

        Element subjectConfirmation = doc.createElement("SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", subject.getConfirmationMethod());

        Element subjectConfirmationData = doc.createElement("SubjectConfirmationData");
        subjectConfirmationData.setAttribute("xsi:type", "KeyInfoConfirmationDataType");
        
        Element keyInfo2 = doc.createElement("KeyInfo");
        keyInfo2.setAttribute("xmlns", "http://www.w3.org/2000/09/xmldsig#");
        Element x509Data2 = doc.createElement("X509Data");
        Element x509Cert2 = doc.createElement("X509Certificate");
        x509Cert2.setTextContent(subject.getCertificate());
        x509Data2.appendChild(x509Cert2);
        keyInfo2.appendChild(x509Data2);
        subjectConfirmationData.appendChild(keyInfo2);
        subjectConfirmation.appendChild(subjectConfirmationData);
        subjectElement.appendChild(subjectConfirmation);
        assertion.appendChild(subjectElement);

        // Conditions
        Token.Conditions cond = token.getConditions();
        Element conditions = doc.createElement("Conditions");
        conditions.setAttribute("NotOnOrAfter", cond.getNotOnOrAfter());
        
        Element audienceRestriction = doc.createElement("AudienceRestriction");
        Element audience = doc.createElement("Audience");
        audience.setTextContent(cond.getAudience());
        audienceRestriction.appendChild(audience);
        conditions.appendChild(audienceRestriction);
        assertion.appendChild(conditions);

        // AttributeStatement
        Element attributeStatement = doc.createElement("AttributeStatement");

        for (Token.Attribute attr : token.getAttributes()) {
            Element attribute = doc.createElement("Attribute");
            attribute.setAttribute("FriendlyName", attr.getFriendlyName());
            attribute.setAttribute("Name", attr.getName());
            
            for (String val : attr.getValues()) {
                Element attrValue = doc.createElement("AttributeValue");
                
                // Check if this is a complex attribute value (like Role or PurposeOfUse)
                if (attr.getFriendlyName().equals("XSPA Role")) {
                    // Create complex Role element using the actual value from token
                    Element roleElement = doc.createElement("Role");
                    roleElement.setAttribute("xmlns", "urn:hl7-org:v3");
                    roleElement.setAttribute("code", val); // Use the actual value from token
                    roleElement.setAttribute("codeSystem", "2.16.840.1.113883.2.9.6.2.7");
                    roleElement.setAttribute("codeSystemName", "ISCO");
                    roleElement.setAttribute("displayName", "Health professionals not elsewhere classified");
                    roleElement.setAttribute("xsi:type", "CE");
                    attrValue.appendChild(roleElement);
                } else if (attr.getFriendlyName().equals("XSPA Purpose Of Use")) {
                    // Create complex PurposeOfUse element using the actual value from token
                    Element purposeElement = doc.createElement("PurposeOfUse");
                    purposeElement.setAttribute("xmlns", "urn:hl7-org:v3");
                    purposeElement.setAttribute("code", val); // Use the actual value from token
                    purposeElement.setAttribute("codeSystem", "urn:oasis:names:tc:xspa:1.0");
                    purposeElement.setAttribute("xsi:type", "CE");
                    attrValue.appendChild(purposeElement);
                } else if (attr.getFriendlyName().equals("EHDSI OnBehalfOf")) {
                    // Create complex OnBehalfOf element
                    Element onBehalfOfElement = doc.createElement("Role");
                    onBehalfOfElement.setAttribute("xmlns", "urn:hl7-org:v3");
                    onBehalfOfElement.setAttribute("code", val);
                    onBehalfOfElement.setAttribute("codeSystem", "2.16.840.1.113883.2.9.6.2.7");
                    onBehalfOfElement.setAttribute("codeSystemName", "ISCO");
                    onBehalfOfElement.setAttribute("displayName", "Nursing professionals");
                    onBehalfOfElement.setAttribute("xsi:type", "CE");
                    attrValue.appendChild(onBehalfOfElement);
                } else {
                    // Simple string attribute - use the actual value from token
                    attrValue.setTextContent(val);
                }
                
                attribute.appendChild(attrValue);
            }
            attributeStatement.appendChild(attribute);
        }

        assertion.appendChild(attributeStatement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }
}


