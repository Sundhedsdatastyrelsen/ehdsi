package dk.sundhedsdatastyrelsen.ncpeh.authentication;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;
public class AssertionBuilder {

    public String buildAssertionXml(ParsedData data, String patientId) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element assertion = doc.createElementNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
        assertion.setAttribute("ID", data.getId());
        assertion.setAttribute("IssueInstant", data.getIssueInstant());
        assertion.setAttribute("Version", data.getVersion());
        assertion.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        doc.appendChild(assertion);

        // Issuer
        Element issuer = doc.createElement("Issuer");
        issuer.setTextContent("https://t-ncp.sundhedsdatastyrelsen.dk");
        assertion.appendChild(issuer);

        // Signature (placeholder - should be signed after serialization)
        ParsedData.Signature sig = data.getSignature();
        Element signature = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        Element signedInfo = doc.createElement("SignedInfo");
        Element canonMethod = doc.createElement("CanonicalizationMethod");
        canonMethod.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        Element sigMethod = doc.createElement("SignatureMethod");
        sigMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#rsa-sha256");
        Element ref = doc.createElement("Reference");
        ref.setAttribute("URI", "#" + data.getId());
        Element transforms = doc.createElement("Transforms");
        Element t1 = doc.createElement("Transform");
        t1.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#enveloped-signature");
        Element t2 = doc.createElement("Transform");
        t2.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        transforms.appendChild(t1);
        transforms.appendChild(t2);
        ref.appendChild(transforms);
        Element digestMethod = doc.createElement("DigestMethod");
        digestMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha256");
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
        ParsedData.Subject subj = data.getSubject();
        Element subject = doc.createElement("Subject");
        Element nameId = doc.createElement("NameID");
        nameId.setAttribute("Format", "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
        nameId.setTextContent(patientId);
        subject.appendChild(nameId);

        Element subjectConfirmation = doc.createElement("SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");

        Element subjectConfirmationData = doc.createElement("SubjectConfirmationData");
        subjectConfirmationData.setAttribute("xsi:type", "KeyInfoConfirmationDataType");
        Element keyInfo2 = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
        Element x509Data2 = doc.createElement("X509Data");
        Element x509Cert2 = doc.createElement("X509Certificate");
        x509Cert2.setTextContent(sig.getCertificate());
        x509Data2.appendChild(x509Cert2);
        keyInfo2.appendChild(x509Data2);
        subjectConfirmationData.appendChild(keyInfo2);
        subjectConfirmation.appendChild(subjectConfirmationData);
        subject.appendChild(subjectConfirmation);
        assertion.appendChild(subject);

        // Conditions
        ParsedData.Conditions cond = data.getConditions();
        Element conditions = doc.createElement("Conditions");
        conditions.setAttribute("NotOnOrAfter", cond.getNotOnOrAfter());
        Element audienceRestriction = doc.createElement("AudienceRestriction");
        Element audience = doc.createElement("Audience");
        audience.setTextContent("https://sts.sosi.dk/");
        audienceRestriction.appendChild(audience);
        conditions.appendChild(audienceRestriction);
        assertion.appendChild(conditions);

        // AttributeStatement
        Element attributeStatement = doc.createElement("AttributeStatement");
        for (ParsedData.Attribute attr : data.getAttributes()) {
            Element attribute = doc.createElement("Attribute");
            attribute.setAttribute("FriendlyName", attr.getFriendlyName());
            attribute.setAttribute("Name", attr.getName());
            for (String val : attr.getValues()) {
                Element attrValue = doc.createElement("AttributeValue");
                attrValue.setTextContent(val);
                attribute.appendChild(attrValue);
            }
            attributeStatement.appendChild(attribute);
        }

        // Add required static attributes
        attributeStatement.appendChild(createSimpleAttribute(doc, "XUA Patient Id", "urn:oasis:names:tc:xacml:2.0:resource:resource-id", patientId));
        attributeStatement.appendChild(createSimpleAttribute(doc, "NSIS AssuranceLevel", "https://data.gov.dk/concept/core/nsis/loa", "3"));
        attributeStatement.appendChild(createSimpleAttribute(doc, "IDWS XUA SpecVersion", "urn:dk:healthcare:saml:SpecVersion", "eHDSI-IDWS-XUA-1.0"));
        attributeStatement.appendChild(createSimpleAttribute(doc, "IDWS XUA IssuancePolicy", "urn:dk:healthcare:saml:IssuancePolicy", "urn:dk:healthcare:ncp:eHDSI-strict"));
        attributeStatement.appendChild(createSimpleAttribute(doc, "EHDSI Country of Treatment", "urn:dk:healthcare:saml:CountryOfTreatment", "DE"));

        assertion.appendChild(attributeStatement);

        // Serialize
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    private Element createSimpleAttribute(Document doc, String friendlyName, String name, String value) {
        Element attribute = doc.createElement("Attribute");
        attribute.setAttribute("FriendlyName", friendlyName);
        attribute.setAttribute("Name", name);
        Element valueElement = doc.createElement("AttributeValue");
        valueElement.setTextContent(value);
        attribute.appendChild(valueElement);
        return attribute;
    }
}

