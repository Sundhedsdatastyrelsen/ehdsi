package dk.sundhedsdatastyrelsen.ncpeh.authentication.parser;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Assertion;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SoapHeaderParser {

    // Used for testing from file
    public Assertion parseFromFile(File soapHeaderFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(soapHeaderFile);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        xPath.setNamespaceContext(new SoapNamespaceContext());

        Assertion assertion = Assertion.builder().build();
        extractAssertionNode(xPath, doc, assertion);
        return assertion;
    }

    public Assertion parse(String soapHeaderXml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(soapHeaderXml.getBytes(StandardCharsets.UTF_8)));

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        xPath.setNamespaceContext(new SoapNamespaceContext());

        Assertion assertion = Assertion.builder().build();
        extractAssertionNode(xPath, doc, assertion);
        return assertion;
    }

    private void extractAssertionNode(XPath xPath, Document doc, Assertion assertion) {
        try {
            Node assertionNode = (Node) xPath.evaluate("//saml2:Assertion", doc, XPathConstants.NODE);
            if (assertionNode == null) throw new IllegalArgumentException("No <saml2:Assertion> found");

            Element assertionEl = (Element) assertionNode;

            assertion.setId(assertionEl.getAttribute("ID"));
            assertion.setIssueInstant(assertionEl.getAttribute("IssueInstant"));
            assertion.setVersion(assertionEl.getAttribute("Version"));

            assertion.setIssuer(xPath.evaluate("saml2:Issuer", assertionNode));

            // Signature
            Assertion.Signature signature = Assertion.Signature.builder()
                .signatureMethodAlgorithm(xPath.evaluate("//ds:SignatureMethod/@Algorithm", doc))
                .digestMethodAlgorithm(xPath.evaluate("//ds:DigestMethod/@Algorithm", doc))
                .digestValue(xPath.evaluate("//ds:DigestValue", doc))
                .signatureValue(xPath.evaluate("//ds:SignatureValue", doc))
                .certificate(xPath.evaluate("//ds:X509Certificate", doc))
                .build();
            assertion.setSignature(signature);

            // Subject
            Assertion.Subject subject = Assertion.Subject.builder()
                .nameIdFormat(xPath.evaluate("saml2:Subject/saml2:NameID/@Format", assertionNode))
                .nameIdValue(xPath.evaluate("saml2:Subject/saml2:NameID", assertionNode))
                .confirmationMethod(xPath.evaluate("saml2:Subject/saml2:SubjectConfirmation/@Method", assertionNode))
                .certificate(xPath.evaluate("//ds:X509Certificate", doc)) // Use same certificate for subject
                .build();
            assertion.setSubject(subject);

            // Conditions
            Assertion.Conditions conditions = Assertion.Conditions.builder()
                .notBefore(xPath.evaluate("saml2:Conditions/@NotBefore", assertionNode))
                .notOnOrAfter(xPath.evaluate("saml2:Conditions/@NotOnOrAfter", assertionNode))
                .audience(xPath.evaluate("saml2:Conditions/saml2:AudienceRestriction/saml2:Audience", assertionNode))
                .build();
            assertion.setConditions(conditions);

            // Attributes
            List<Assertion.Attribute> attributes = new ArrayList<>();
            NodeList attributeNodes = (NodeList) xPath.evaluate("saml2:AttributeStatement/saml2:Attribute", assertionNode, XPathConstants.NODESET);

            for (int i = 0; i < attributeNodes.getLength(); i++) {
                Element attrEl = (Element) attributeNodes.item(i);
                String friendlyName = attrEl.getAttribute("FriendlyName");
                String name = attrEl.getAttribute("Name");

                List<String> values = new ArrayList<>();
                NodeList valueNodes = attrEl.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeValue");

                for (int j = 0; j < valueNodes.getLength(); j++) {
                    Node valueNode = valueNodes.item(j);
                    String value = valueNode.getTextContent();
                    if (value != null && !value.trim().isEmpty()) {
                        values.add(value.trim());
                    }
                }

                Assertion.Attribute attribute = Assertion.Attribute.builder()
                    .friendlyName(friendlyName)
                    .name(name)
                    .values(values)
                    .build();
                attributes.add(attribute);
            }

            assertion.setAttributes(attributes);

        } catch (Exception e) {
            log.error("Failed to extract assertion data", e);
            throw new RuntimeException("Failed to extract assertion data", e);
        }
    }
}
