package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.util.*;



public class SoapHeaderParser {

    public ParsedData parse(File soapHeaderFile) throws Exception {
        // Load document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(soapHeaderFile);

        // XPath with namespace support
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        Map<String, String> ns = Map.of(
            "soapenv", "http://www.w3.org/2003/05/soap-envelope",
            "wsse",    "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
            "saml2",   "urn:oasis:names:tc:SAML:2.0:assertion",
            "ds",      "http://www.w3.org/2000/09/xmldsig#"
        );
        xpath.setNamespaceContext(new NamespaceContextMap(ns));

        ParsedData parsed = new ParsedData();
        extractAssertionNode(xpath, doc, parsed);
        return parsed;
    }

    private void extractAssertionNode(XPath xPath, Document doc, ParsedData parsedData) {
        try {
            Node assertion = (Node) xPath.evaluate("//saml2:Assertion", doc, XPathConstants.NODE);
            if (assertion == null) throw new IllegalArgumentException("No <saml2:Assertion> found");

            Element assertionEl = (Element) assertion;

            parsedData.setId(assertionEl.getAttribute("ID"));
            parsedData.setIssueInstant(assertionEl.getAttribute("IssueInstant"));
            parsedData.setVersion(assertionEl.getAttribute("Version"));

            parsedData.setIssuer(xPath.evaluate("saml2:Issuer", assertion));

            // Signature
            ParsedData.Signature signature = new ParsedData.Signature();
            signature.setSignatureMethodAlgorithm(xPath.evaluate("//ds:SignatureMethod/@Algorithm", doc));
            signature.setDigestMethodAlgorithm(xPath.evaluate("//ds:DigestMethod/@Algorithm", doc));
            signature.setDigestValue(xPath.evaluate("//ds:DigestValue", doc));
            signature.setSignatureValue(xPath.evaluate("//ds:SignatureValue", doc));
            signature.setCertificate(xPath.evaluate("//ds:X509Certificate", doc));
            parsedData.setSignature(signature);

            // Subject
            ParsedData.Subject subject = new ParsedData.Subject();
            subject.setNameIdFormat(xPath.evaluate("saml2:Subject/saml2:NameID/@Format", assertion));
            subject.setNameIdValue(xPath.evaluate("saml2:Subject/saml2:NameID", assertion));
            subject.setConfirmationMethod(xPath.evaluate("saml2:Subject/saml2:SubjectConfirmation/@Method", assertion));
            parsedData.setSubject(subject);

            // Conditions
            ParsedData.Conditions conditions = new ParsedData.Conditions();
            conditions.setNotBefore(xPath.evaluate("saml2:Conditions/@NotBefore", assertion));
            conditions.setNotOnOrAfter(xPath.evaluate("saml2:Conditions/@NotOnOrAfter", assertion));
            parsedData.setConditions(conditions);

            // Attributes
            List<ParsedData.Attribute> attributes = new ArrayList<>();
            NodeList attributeNodes = (NodeList) xPath.evaluate("saml2:AttributeStatement/saml2:Attribute", assertion, XPathConstants.NODESET);
            for (int i = 0; i < attributeNodes.getLength(); i++) {
                Element attr = (Element) attributeNodes.item(i);
                ParsedData.Attribute attribute = new ParsedData.Attribute();
                attribute.setFriendlyName(attr.getAttribute("FriendlyName"));
                attribute.setName(attr.getAttribute("Name"));

                NodeList values = attr.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeValue");
                List<String> valueList = new ArrayList<>();
                for (int j = 0; j < values.getLength(); j++) {
                    valueList.add(values.item(j).getTextContent().trim());
                }

                attribute.setValues(valueList);
                attributes.add(attribute);
            }
            parsedData.setAttributes(attributes);

            // HL7 permissions
            parsedData.setPermissions(attributes.stream()
                .filter(a -> "urn:oasis:names:tc:xspa:1.0:subject:hl7:permission".equals(a.getName()))
                .flatMap(a -> a.getValues().stream())
                .toList());

        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not evaluate XPath expression", e);
        }
    }
}
