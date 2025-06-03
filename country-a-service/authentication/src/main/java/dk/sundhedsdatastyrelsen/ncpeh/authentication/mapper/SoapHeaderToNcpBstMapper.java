package dk.sundhedsdatastyrelsen.ncpeh.authentication.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Component
public class SoapHeaderToNcpBstMapper {

    private static final String SAML_NS = "urn:oasis:names:tc:SAML:2.0:assertion";
    private static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String HL7_NS = "urn:hl7-org:v3";

    public String mapToNcpBst(String soapHeader) {
        try {
            // Parse the input SOAP header
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document inputDoc = builder.parse(new InputSource(new StringReader(soapHeader)));

            // Create new NCP-BST document
            Document outputDoc = builder.newDocument();
            Element assertion = createAssertion(outputDoc, inputDoc);

            // Add required attributes
            addSubject(outputDoc, assertion, inputDoc);
            addConditions(outputDoc, assertion);
            addAttributeStatement(outputDoc, assertion, inputDoc);

            // Convert to string
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            StringWriter sw = new StringWriter();
            trans.transform(new DOMSource(outputDoc), new StreamResult(sw));
            return sw.toString();

        } catch (Exception e) {
            log.error("Failed to map SOAP header to NCP-BST format", e);
            throw new RuntimeException("Failed to map SOAP header to NCP-BST format", e);
        }
    }

    private Element createAssertion(Document doc, Document inputDoc) {
        Element assertion = doc.createElementNS(SAML_NS, "Assertion");
        assertion.setAttribute("ID", "_" + UUID.randomUUID().toString());
        assertion.setAttribute("IssueInstant", Instant.now().toString());
        assertion.setAttribute("Version", "2.0");
        assertion.setAttribute("xmlns", SAML_NS);
        assertion.setAttribute("xmlns:xsi", XSI_NS);

        // Add Issuer
        Element issuer = doc.createElementNS(SAML_NS, "Issuer");
        issuer.setTextContent("https://t-ncp.sundhedsdatastyrelsen.dk");
        assertion.appendChild(issuer);

        doc.appendChild(assertion);
        return assertion;
    }

    private void addSubject(Document doc, Element assertion, Document inputDoc) {
        Element subject = doc.createElementNS(SAML_NS, "Subject");
        
        // Add NameID
        Element nameId = doc.createElementNS(SAML_NS, "NameID");
        nameId.setAttribute("Format", "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
        nameId.setTextContent(UUID.randomUUID().toString());
        subject.appendChild(nameId);

        // Add SubjectConfirmation
        Element subjectConfirmation = doc.createElementNS(SAML_NS, "SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
        
        Element subjectConfirmationData = doc.createElementNS(SAML_NS, "SubjectConfirmationData");
        subjectConfirmationData.setAttribute("xsi:type", "KeyInfoConfirmationDataType");
        subjectConfirmation.appendChild(subjectConfirmationData);

        subject.appendChild(subjectConfirmation);
        assertion.appendChild(subject);
    }

    private void addConditions(Document doc, Element assertion) {
        Element conditions = doc.createElementNS(SAML_NS, "Conditions");
        conditions.setAttribute("NotOnOrAfter", Instant.now().plus(12, ChronoUnit.HOURS).toString());

        Element audienceRestriction = doc.createElementNS(SAML_NS, "AudienceRestriction");
        Element audience = doc.createElementNS(SAML_NS, "Audience");
        audience.setTextContent("https://sts.sosi.dk/");
        audienceRestriction.appendChild(audience);
        conditions.appendChild(audienceRestriction);

        assertion.appendChild(conditions);
    }

    private void addAttributeStatement(Document doc, Element assertion, Document inputDoc) {
        Element attributeStatement = doc.createElementNS(SAML_NS, "AttributeStatement");

        // Map required attributes from input to output
        addAttribute(doc, attributeStatement, "XSPA Subject", 
            "urn:oasis:names:tc:xspa:1.0:subject:subject-id", 
            getAttributeValue(inputDoc, "urn:oasis:names:tc:xspa:1.0:subject:subject-id"));

        addAttribute(doc, attributeStatement, "XSPA Role",
            "urn:oasis:names:tc:xacml:2.0:subject:role",
            getRoleAttributeValue(inputDoc));

        addAttribute(doc, attributeStatement, "Hl7 Permissions",
            "urn:oasis:names:tc:xspa:1.0:subject:hl7:permission",
            getAttributeValue(inputDoc, "urn:oasis:names:tc:xspa:1.0:subject:hl7:permission"));

        addAttribute(doc, attributeStatement, "EHDSI OnBehalfOf",
            "urn:ehdsi:names:subject:on-behalf-of",
            getRoleAttributeValue(inputDoc));

        addAttribute(doc, attributeStatement, "XSPA Organization",
            "urn:oasis:names:tc:xspa:1.0:subject:organization",
            getAttributeValue(inputDoc, "urn:oasis:names:tc:xspa:1.0:subject:organization"));

        addAttribute(doc, attributeStatement, "XSPA Organization Id",
            "urn:oasis:names:tc:xspa:1.0:subject:organization-id",
            getAttributeValue(inputDoc, "urn:oasis:names:tc:xspa:1.0:subject:organization-id"));

        addAttribute(doc, attributeStatement, "eHealth DSI Healthcare Facility Type",
            "urn:ehdsi:names:subject:healthcare-facility-type",
            getAttributeValue(inputDoc, "urn:ehdsi:names:subject:healthcare-facility-type"));

        addAttribute(doc, attributeStatement, "XSPA Purpose Of Use",
            "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse",
            getPurposeOfUseAttributeValue(inputDoc));

        addAttribute(doc, attributeStatement, "XSPA Locality",
            "urn:oasis:names:tc:xspa:1.0:environment:locality",
            getAttributeValue(inputDoc, "urn:oasis:names:tc:xspa:1.0:environment:locality"));

        addAttribute(doc, attributeStatement, "XUA Patient Id",
            "urn:oasis:names:tc:xacml:2.0:resource:resource-id",
            getAttributeValue(inputDoc, "urn:oasis:names:tc:xacml:2.0:resource:resource-id"));

        // Add required NCP-BST specific attributes
        addAttribute(doc, attributeStatement, "NSIS AssuranceLevel",
            "https://data.gov.dk/concept/core/nsis/loa", "3");

        addAttribute(doc, attributeStatement, "IDWS XUA SpecVersion",
            "urn:dk:healthcare:saml:SpecVersion", "eHDSI-IDWS-XUA-1.0");

        addAttribute(doc, attributeStatement, "IDWS XUA IssuancePolicy",
            "urn:dk:healthcare:saml:IssuancePolicy", "urn:dk:healthcare:ncp:eHDSI-strict");

        addAttribute(doc, attributeStatement, "EHDSI Country of Treatment",
            "urn:dk:healthcare:saml:CountryOfTreatment", "DK");

        assertion.appendChild(attributeStatement);
    }

    private void addAttribute(Document doc, Element attributeStatement, String friendlyName, String name, String value) {
        Element attribute = doc.createElementNS(SAML_NS, "Attribute");
        attribute.setAttribute("FriendlyName", friendlyName);
        attribute.setAttribute("Name", name);

        // Handle multiple values for attributes like Hl7 Permissions
        if (value.contains("|")) {
            String[] values = value.split("\\|");
            for (String v : values) {
                Element attributeValue = doc.createElementNS(SAML_NS, "AttributeValue");
                attributeValue.setTextContent(v.trim());
                attribute.appendChild(attributeValue);
            }
        } else {
            Element attributeValue = doc.createElementNS(SAML_NS, "AttributeValue");
            attributeValue.setTextContent(value);
            attribute.appendChild(attributeValue);
        }

        attributeStatement.appendChild(attribute);
    }

    private String getAttributeValue(Document doc, String name) {
        NodeList attributes = doc.getElementsByTagNameNS(SAML_NS, "Attribute");
        for (int i = 0; i < attributes.getLength(); i++) {
            Element attribute = (Element) attributes.item(i);
            if (name.equals(attribute.getAttribute("Name"))) {
                NodeList values = attribute.getElementsByTagNameNS(SAML_NS, "AttributeValue");
                if (values.getLength() > 0) {
                    return values.item(0).getTextContent();
                }
            }
        }
        return "";
    }

    private String getRoleAttributeValue(Document doc) {
        NodeList attributes = doc.getElementsByTagNameNS(SAML_NS, "Attribute");
        for (int i = 0; i < attributes.getLength(); i++) {
            Element attribute = (Element) attributes.item(i);
            if ("urn:oasis:names:tc:xacml:2.0:subject:role".equals(attribute.getAttribute("Name"))) {
                NodeList values = attribute.getElementsByTagNameNS(SAML_NS, "AttributeValue");
                if (values.getLength() > 0) {
                    Node value = values.item(0);
                    if (value.getFirstChild() != null) {
                        return value.getFirstChild().getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    private String getPurposeOfUseAttributeValue(Document doc) {
        NodeList attributes = doc.getElementsByTagNameNS(SAML_NS, "Attribute");
        for (int i = 0; i < attributes.getLength(); i++) {
            Element attribute = (Element) attributes.item(i);
            if ("urn:oasis:names:tc:xspa:1.0:subject:purposeofuse".equals(attribute.getAttribute("Name"))) {
                NodeList values = attribute.getElementsByTagNameNS(SAML_NS, "AttributeValue");
                if (values.getLength() > 0) {
                    Node value = values.item(0);
                    if (value.getFirstChild() != null) {
                        return value.getFirstChild().getNodeValue();
                    }
                }
            }
        }
        return "";
    }
} 