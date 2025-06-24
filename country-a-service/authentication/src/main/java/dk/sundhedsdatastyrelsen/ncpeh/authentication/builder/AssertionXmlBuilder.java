package dk.sundhedsdatastyrelsen.ncpeh.authentication.builder;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Assertion;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class AssertionXmlBuilder {

    public String build(Assertion assertion) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Create assertion with proper namespaces for NCP-BST format
        Element assertionElement = doc.createElement("Assertion");
        assertionElement.setAttribute("ID", assertion.getId());
        assertionElement.setAttribute("IssueInstant", assertion.getIssueInstant());
        assertionElement.setAttribute("Version", assertion.getVersion());
        assertionElement.setAttribute("xmlns", "urn:oasis:names:tc:SAML:2.0:assertion");
        assertionElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        doc.appendChild(assertionElement);

        // Issuer
        Element issuer = doc.createElement("Issuer");
        issuer.setTextContent(assertion.getIssuer());
        assertionElement.appendChild(issuer);

        // Signature
        Assertion.Signature sig = assertion.getSignature();
        Element signature = doc.createElement("Signature");
        signature.setAttribute("xmlns", "http://www.w3.org/2000/09/xmldsig#");
        
        Element signedInfo = doc.createElement("SignedInfo");
        
        Element canonMethod = doc.createElement("CanonicalizationMethod");
        canonMethod.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        
        Element sigMethod = doc.createElement("SignatureMethod");
        sigMethod.setAttribute("Algorithm", sig.getSignatureMethodAlgorithm());
        
        Element ref = doc.createElement("Reference");
        ref.setAttribute("URI", "#" + assertion.getId());
        
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
        assertionElement.appendChild(signature);

        // Subject
        Assertion.Subject subject = assertion.getSubject();
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
        assertionElement.appendChild(subjectElement);

        // Conditions
        Assertion.Conditions cond = assertion.getConditions();
        Element conditions = doc.createElement("Conditions");
        if (cond.getNotBefore() != null) {
            conditions.setAttribute("NotBefore", cond.getNotBefore());
        }
        conditions.setAttribute("NotOnOrAfter", cond.getNotOnOrAfter());
        
        if (cond.getAudience() != null) {
            Element audienceRestriction = doc.createElement("AudienceRestriction");
            Element audience = doc.createElement("Audience");
            audience.setTextContent(cond.getAudience());
            audienceRestriction.appendChild(audience);
            conditions.appendChild(audienceRestriction);
        }
        assertionElement.appendChild(conditions);

        // AttributeStatement
        Element attributeStatement = doc.createElement("AttributeStatement");

        for (Assertion.Attribute attr : assertion.getAttributes()) {
            Element attribute = doc.createElement("Attribute");
            attribute.setAttribute("FriendlyName", attr.getFriendlyName());
            attribute.setAttribute("Name", attr.getName());
            
            for (String val : attr.getValues()) {
                Element attrValue = doc.createElement("AttributeValue");
                
                // Check if this is a complex attribute value (like Role or PurposeOfUse)
                if (attr.getFriendlyName().equals("XSPA Role")) {
                    // Create complex Role element using the actual value from assertion
                    Element roleElement = doc.createElement("Role");
                    roleElement.setAttribute("xmlns", "urn:hl7-org:v3");
                    roleElement.setAttribute("code", val); // Use the actual value from assertion
                    roleElement.setAttribute("codeSystem", "2.16.840.1.113883.2.9.6.2.7");
                    roleElement.setAttribute("codeSystemName", "ISCO");
                    roleElement.setAttribute("displayName", "Health professionals not elsewhere classified");
                    roleElement.setAttribute("xsi:type", "CE");
                    attrValue.appendChild(roleElement);
                } else if (attr.getFriendlyName().equals("XSPA Purpose Of Use")) {
                    // Create complex PurposeOfUse element using the actual value from assertion
                    Element purposeElement = doc.createElement("PurposeOfUse");
                    purposeElement.setAttribute("xmlns", "urn:hl7-org:v3");
                    purposeElement.setAttribute("code", val); // Use the actual value from assertion
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
                    // Simple string attribute - use the actual value from assertion
                    attrValue.setTextContent(val);
                }
                
                attribute.appendChild(attrValue);
            }
            attributeStatement.appendChild(attribute);
        }

        assertionElement.appendChild(attributeStatement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }
} 