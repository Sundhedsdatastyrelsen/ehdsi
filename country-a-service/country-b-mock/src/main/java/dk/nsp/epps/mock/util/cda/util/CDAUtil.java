package dk.nsp.epps.mock.util.cda.util;

import eu.europa.ec.sante.ehdsi.openncp.configmanager.ConfigurationManagerFactory;
import dk.nsp.epps.mock.model.DispenseRequest;
import dk.nsp.epps.mock.model.PackageSize;
import dk.nsp.epps.mock.util.cda.enums.CodeSystem;
import dk.nsp.epps.mock.util.cda.enums.Namespaces;
import dk.nsp.epps.mock.util.cda.enums.Templates;
import dk.nsp.epps.mock.util.cda.model.*;
import org.apache.commons.lang.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filters;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CDAUtil {

    private static final Namespace HL7_NAMESPACE = Namespace.getNamespace(Namespaces.HL7.getPrefix(), Namespaces.HL7.getUri());
    private static final Namespace HL7_PHARMACY_NAMESPACE = Namespace.getNamespace(Namespaces.HL7_PHARMACY.getPrefix(), Namespaces.HL7_PHARMACY.getUri());
    private static final Namespace XSI_NAMESPACE = Namespace.getNamespace(Namespaces.XSI.getPrefix(), Namespaces.XSI.getUri());

    private static final Logger logger = LoggerFactory.getLogger(CDAUtil.class);

    private static final String PHARMACIST_OID;
    private static final String CUSTODIAN_OID;
    private static final String CUSTODIAN_NAME;
    private static final String NCP_COUNTRY;
    private static final String DISPENSATION_OID;

    private static final String LEGAL_AUTHENTICATOR_PERSON_OID;
    private static final String LEGAL_AUTHENTICATOR_FIRSTNAME;
    private static final String LEGAL_AUTHENTICATOR_LASTNAME;
    private static final String LEGAL_AUTHENTICATOR_ORG_OID;
    private static final String LEGAL_AUTHENTICATOR_POSTAL_CODE;
    private static final String LEGAL_AUTHENTICATOR_CITY;
    private static final String LANGUAGE_CODE;

    static {
        PHARMACIST_OID = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_PHARMACIST_OID");
        CUSTODIAN_OID = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_CUSTODIAN_OID");
        CUSTODIAN_NAME = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_CUSTODIAN_NAME");
        NCP_COUNTRY = ConfigurationManagerFactory.getConfigurationManager().getProperty("ncp.country");
        DISPENSATION_OID = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_DISPENSATION_OID");
        LEGAL_AUTHENTICATOR_PERSON_OID = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_LEGAL_AUTHENTICATOR_PERSON_OID");
        LEGAL_AUTHENTICATOR_FIRSTNAME = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_LEGAL_AUTHENTICATOR_FIRSTNAME");
        LEGAL_AUTHENTICATOR_LASTNAME = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_LEGAL_AUTHENTICATOR_LASTNAME");
        LEGAL_AUTHENTICATOR_ORG_OID = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_LEGAL_AUTHENTICATOR_ORG_OID");
        LEGAL_AUTHENTICATOR_POSTAL_CODE = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_LEGAL_AUTHENTICATOR_POSTALCODE");
        LEGAL_AUTHENTICATOR_CITY = ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_LEGAL_AUTHENTICATOR_CITY");
        LANGUAGE_CODE = ConfigurationManagerFactory.getConfigurationManager().getProperty("LANGUAGE_CODE");

    }

    private CDAUtil() {
    }

    public static byte[] generateDispensationDocument(DispenseRequest dispenseRequest,
                                                      org.w3c.dom.Document ePrescriptionDocument, String edUID) {

        var ePDocument = DOMUtil.convertDOMtoJDOM(ePrescriptionDocument);
        // create the jdom
        Document dispenseDocument = new Document();
        // create root element
        Element rootElement = new Element("ClinicalDocument", HL7_NAMESPACE);
        dispenseDocument.setRootElement(rootElement);

        addRealmCode(rootElement, dispenseRequest.getCountryCode());
        addTypeId(rootElement);
        addTemplateId(HL7_NAMESPACE, rootElement, Templates.EHDSI_EDISPENSATION);
        addId(HL7_NAMESPACE, new Identifier(DISPENSATION_OID, edUID), rootElement);
        addCode(rootElement);
        addTitle(rootElement);
        addEffectiveTime(rootElement);
        addConfidentialityCode(rootElement);
        addLanguageCode(rootElement);
        CDAHeaderUtil.addRecordTarget(rootElement, ePDocument);
        CDAHeaderUtil.addAuthor(HL7_NAMESPACE, rootElement, getAuthorInformation());
        CDAHeaderUtil.addCustodian(HL7_NAMESPACE, rootElement, getCustodianInformation());
        CDAHeaderUtil.addLegalAuthenticator(HL7_NAMESPACE, rootElement, getLegalAuthenticatorInformation());
        CDAHeaderUtil.addInFulfillmentOf(HL7_NAMESPACE, rootElement, ePDocument);
        CDABodyUtil.addComponent(HL7_NAMESPACE, rootElement, dispenseRequest, ePDocument);

        replacePackageSizes(dispenseDocument, dispenseRequest.getPackageSize(), dispenseRequest.getPartPackageSizes());

        // Output as formatted XML
        XMLOutputter xml = new XMLOutputter();
        // we want to format the xml. This is used only for demonstration.
        // pretty formatting adds extra spaces and is generally not required.
        xml.setFormat(Format.getPrettyFormat());
        var dispenseString = xml.outputString(dispenseDocument);
        logger.info("Dispense:\n'{}'", dispenseString);
        return dispenseString.getBytes(StandardCharsets.UTF_8);
    }

    private static void addRealmCode(Element rootElement, String countryCode) {
        Element realmCode = new Element("realmCode", CDAUtil.HL7_NAMESPACE);
        realmCode.setAttribute("code", countryCode);
        rootElement.addContent(realmCode);
    }

    private static void addTypeId(Element rootElement) {
        Element typeId = new Element("typeId", CDAUtil.HL7_NAMESPACE);
        typeId.setAttribute("extension", "POCD_HD000040");
        typeId.setAttribute("root", "2.16.840.1.113883.1.3");
        rootElement.addContent(typeId);
    }

    public static void addTemplateId(Namespace hl7Namespace, Element rootElement, Templates template) {
        Element templateId = new Element("templateId", hl7Namespace);
        templateId.setAttribute("root", template.getId());
        rootElement.addContent(templateId);
    }

    private static void addCode(Element rootElement) {
        Element code = new Element("code", CDAUtil.HL7_NAMESPACE);
        code.setAttribute("code", "60593-1");
        code.setAttribute("codeSystem", CodeSystem.LOINC.getOid());
        code.setAttribute("codeSystemName", CodeSystem.LOINC.getName());
        code.setAttribute("displayName", "Medication dispensed.extended Document");
        rootElement.addContent(code);
    }

    private static void addTitle(Element rootElement) {
        Element title = new Element("title", CDAUtil.HL7_NAMESPACE);
        title.addContent("Medication dispensed");
        rootElement.addContent(title);
    }

    public static void addAddress(Namespace hl7Namespace, Address addr, Element assignedAuthor) {
        var address = new Element("addr", hl7Namespace);
        if (addr == null) {
            address.setAttribute("nullFlavor", "NI");
        } else {
            var streetAddressLine = new Element("streetAddressLine", hl7Namespace);
            streetAddressLine.addContent(addr.getStreetAddressLine());
            address.addContent(streetAddressLine);
            var postalCode = new Element("postalCode", hl7Namespace);
            postalCode.addContent(addr.getPostalCode());
            address.addContent(postalCode);
            var city = new Element("city", hl7Namespace);
            city.addContent(addr.getCity());
            address.addContent(city);
            var country = new Element("country", hl7Namespace);
            country.addContent(addr.getCountry());
            address.addContent(country);
        }
        assignedAuthor.addContent(address);
    }

    public static void addTelecom(Namespace hl7Namespace, Telecom tel, Element assignedAuthor) {
        var telecom = new Element("telecom", hl7Namespace);
        if (tel == null || tel.getTelecoms() == null || tel.getTelecoms().isEmpty()) {
            telecom.setAttribute("nullFlavor", "NI");
            assignedAuthor.addContent(telecom);
        } else {
            for (String key : tel.getTelecoms().keySet()) {
                telecom.setAttribute("use", key);
                telecom.setAttribute("value", tel.getTelecoms().get(key));
                assignedAuthor.addContent(telecom);
            }
        }
    }

    private static void addEffectiveTime(Element rootElement) {
        Element effectiveTime = new Element("effectiveTime", CDAUtil.HL7_NAMESPACE);
        effectiveTime.setAttribute("value", HL7Util.formatDateHL7(new Date()));
        rootElement.addContent(effectiveTime);
    }

    private static void addConfidentialityCode(Element rootElement) {
        Element confidentialityCode = new Element("confidentialityCode", CDAUtil.HL7_NAMESPACE);
        confidentialityCode.setAttribute("code", "N");
        confidentialityCode.setAttribute("codeSystem", "2.16.840.1.113883.5.25");
        confidentialityCode.setAttribute("codeSystemName", "Confidentiality");
        confidentialityCode.setAttribute("codeSystemVersion", "913-20091020");
        confidentialityCode.setAttribute("displayName", "normal");
        rootElement.addContent(confidentialityCode);
    }

    private static void addLanguageCode(Element rootElement) {
        Element languageCode = new Element("languageCode", CDAUtil.HL7_NAMESPACE);
        languageCode.setAttribute("code", LANGUAGE_CODE);
        rootElement.addContent(languageCode);
    }

    public static void copyAllNodesAndAttributes(org.jdom2.Document ePDocument, DispenseRequest dispenseRequest, Element rootElement, String nodeName) {
        var root = ePDocument.getRootElement();
        var filter = new ElementFilter(nodeName);
        for (Element c : root.getDescendants(filter)) {
            cloneNodeAndDescendants(dispenseRequest, rootElement, c);
        }
    }

    public static void copyAllNodesAndAttributesFirstOccurrence(org.jdom2.Document ePDocument, Element rootElement, String nodeName) {
        var root = ePDocument.getRootElement();
        var filter = new ElementFilter(nodeName);
        Element c = root.getDescendants(filter).next();
        cloneNodeAndDescendants(null, rootElement, c);
    }

    private static void cloneNodeAndDescendants(DispenseRequest dispenseRequest, Element rootElement, Element c) {
        var clonedElement = c.clone();
        clonedElement.setNamespace(HL7_NAMESPACE);
        setNamespaceForChildren(clonedElement, dispenseRequest);
        rootElement.addContent(clonedElement);
    }

    private static void setNamespaceForChildren(Element clonedElement, DispenseRequest dispenseRequest) {
        for (Element child : clonedElement.getChildren()) {
            if (child.getAttribute("type", XSI_NAMESPACE) != null) {
                Attribute attribute = child.getAttribute("type", XSI_NAMESPACE);
                attribute.setValue(HL7_NAMESPACE.getPrefix() + ':' + attribute.getValue());
            }
            if (child.getName().equals("translation")) {
                child.setNamespace(HL7_NAMESPACE);
            }
            if (!child.getNamespace().equals(HL7_NAMESPACE) && !child.getNamespace().equals(HL7_PHARMACY_NAMESPACE)) {
                logger.error("unknown namespace encountered: prefix '{}' and uri '{}'", child.getNamespace().getPrefix(),
                        child.getNamespace().getURI());
            }
            if (dispenseRequest != null) {
                if (clonedElement.getName().equals("manufacturedMaterial")) {
                    if (clonedElement.getChild("code", HL7_NAMESPACE) != null) {
                        clonedElement.getChild("code", HL7_NAMESPACE).setAttribute("displayName", dispenseRequest.getProductName());
                    }
                    if (clonedElement.getChild("name", HL7_NAMESPACE) != null) {
                        clonedElement.getChild("name", HL7_NAMESPACE).setText(dispenseRequest.getProductName());
                    }
                }
            }
            setNamespaceForChildren(child, dispenseRequest);
        }
    }

    public static void addAssignedPerson(Namespace hl7Namespace, AssignedPerson assignedPerson, Element assignedAuthor) {
        var assignedPersonElement = new Element("assignedPerson", hl7Namespace);
        var name = new Element("name", hl7Namespace);
        var family = new Element("family", hl7Namespace);
        family.addContent(assignedPerson.getFamilyName());
        name.addContent(family);
        for (String givenName : assignedPerson.getGivenNames()) {
            var given = new Element("given", hl7Namespace);
            given.addContent(givenName);
            name.addContent(given);
        }
        if (assignedPerson.getSuffix() != null) {
            var suffix = new Element("suffix", hl7Namespace);
            suffix.addContent(assignedPerson.getSuffix());
            name.addContent(suffix);
        }
        assignedPersonElement.addContent(name);
        assignedAuthor.addContent(assignedPersonElement);
    }

    public static void addRepresentedOrganization(Namespace hl7Namespace, RepresentedOrganization representedOrganization, Element assignedAuthor) {
        var representedOrganizationElement = new Element("representedOrganization", hl7Namespace);
        var id = new Element("id", hl7Namespace);
        id.setAttribute("root", representedOrganization.getIdentifier().getRoot());
        if (representedOrganization.getIdentifier().getExtension() != null) {
            id.setAttribute("extension", representedOrganization.getIdentifier().getExtension());
        }
        representedOrganizationElement.addContent(id);
        var name = new Element("name", hl7Namespace);
        name.addContent(representedOrganization.getName());
        representedOrganizationElement.addContent(name);
        CDAUtil.addTelecom(hl7Namespace, representedOrganization.getTelecom(), representedOrganizationElement);
        CDAUtil.addAddress(hl7Namespace, representedOrganization.getAddr(), representedOrganizationElement);
        assignedAuthor.addContent(representedOrganizationElement);
    }

    private static AuthorInformation getAuthorInformation() {
        //TODO Provide real author information
        Map<String, String> telecoms = new HashMap<>();
        telecoms.put("WP", "tel:123456789");
        Telecom telecom = new Telecom(telecoms);
        Address address = new Address("streetAddressLine", "postalCode", "city", "country");
        AssignedPerson assignedPerson = new AssignedPerson(Collections.singleton("givenName"), "familyName", "suffix");
        RepresentedOrganization representedOrganization = new RepresentedOrganization(new Identifier("root", null), "name", telecom, address);
        return new AuthorInformation(new Identifier(PHARMACIST_OID, "extension"), address, telecom, assignedPerson, representedOrganization);
    }

    public static void addId(Namespace hl7Namespace, Identifier identifier, Element element) {
        var id = new Element("id", hl7Namespace);
        id.setAttribute("root", identifier.getRoot());
        if (!StringUtils.isEmpty(identifier.getExtension())) {
            id.setAttribute("extension", identifier.getExtension());
        }
        element.addContent(id);
    }

    private static CustodianInformation getCustodianInformation() {
        return new CustodianInformation(CUSTODIAN_OID, CUSTODIAN_NAME, NCP_COUNTRY);
    }

    private static LegalAuthenticatorInformation getLegalAuthenticatorInformation() {
        var personIdentifier = new Identifier(LEGAL_AUTHENTICATOR_PERSON_OID, null);
        var address = new Address("4, Breydel Street", "B-1000", "Brussels", "BE");
        var telecom = new Telecom(null);
        var assignedPerson = new AssignedPerson(Collections.singleton(LEGAL_AUTHENTICATOR_FIRSTNAME), LEGAL_AUTHENTICATOR_LASTNAME, null);
        var organizationIdentifier = new Identifier(LEGAL_AUTHENTICATOR_ORG_OID, null);
        var representedOrganization = new RepresentedOrganization(organizationIdentifier, "eHDSI Solution Provider", null,
                new Address("N/A", LEGAL_AUTHENTICATOR_POSTAL_CODE, LEGAL_AUTHENTICATOR_CITY, NCP_COUNTRY));
        return new LegalAuthenticatorInformation(personIdentifier, address, telecom, assignedPerson, representedOrganization);
    }

    private static void replacePackageSizes(Document document, PackageSize packageSize, List<PackageSize> partPackageSizes) {
        // create xpath factory
        XPathFactory xpathFactory = XPathFactory.instance();
        if (partPackageSizes != null && !partPackageSizes.isEmpty()) {
            XPathExpression<Element> expr = xpathFactory.compile("//hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:part", Filters.element(), null, HL7_NAMESPACE, HL7_PHARMACY_NAMESPACE);
            List<Element> parts = expr.evaluate(document);
            for (int i=0; i<parts.size(); i++) {
                PackageSize partPackageSize = partPackageSizes.get(i);
                Element part = parts.get(i);
                //quantity
                if (part.getChild("quantity", HL7_PHARMACY_NAMESPACE) != null) {
                    part.getChild("quantity", HL7_PHARMACY_NAMESPACE).getAttribute("value").setValue(partPackageSize.getQuantity());
                }
                //packageSizeL1
                expr = xpathFactory.compile("//pharm:part/pharm:partProduct/pharm:asContent/pharm:quantity", Filters.element(), null, HL7_PHARMACY_NAMESPACE);
                List<Element> results = expr.evaluate(part);
                if (!results.isEmpty() && StringUtils.isNotBlank(partPackageSize.getPackageSizeL1())) {
                    Element packageSizeL1 = results.iterator().next();
                    packageSizeL1.getAttribute("value").setValue(partPackageSize.getPackageSizeL1());
                }
                //packageSizeL2
                expr = xpathFactory.compile("//pharm:part/pharm:partProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:quantity", Filters.element(), null, HL7_PHARMACY_NAMESPACE);
                results = expr.evaluate(part);
                if (!results.isEmpty() && StringUtils.isNotBlank(partPackageSize.getPackageSizeL2())) {
                    Element packageSizeL2 = results.iterator().next();
                    packageSizeL2.getAttribute("value").setValue(partPackageSize.getPackageSizeL2());
                }
                //packageSizeL3
                expr = xpathFactory.compile("//pharm:part/pharm:partProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:quantity", Filters.element(), null, HL7_PHARMACY_NAMESPACE);
                results = expr.evaluate(part);
                if (!results.isEmpty() && StringUtils.isNotBlank(partPackageSize.getPackageSizeL3())) {
                    Element packageSizeL3 = results.iterator().next();
                    packageSizeL3.getAttribute("value").setValue(partPackageSize.getPackageSizeL3());
                }
            }
        } else {
            //packageSizeL1
            XPathExpression<Element> expr = xpathFactory.compile("//hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asContent/pharm:quantity", Filters.element(), null, HL7_NAMESPACE, HL7_PHARMACY_NAMESPACE);
            List<Element> results = expr.evaluate(document);
            if (!results.isEmpty() && StringUtils.isNotBlank(packageSize.getPackageSizeL1())) {
                Element packageSizeL1 = results.iterator().next();
                packageSizeL1.getAttribute("value").setValue(packageSize.getPackageSizeL1());
            }
            //packageSizeL2
            expr = xpathFactory.compile("//hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:quantity", Filters.element(), null, HL7_NAMESPACE, HL7_PHARMACY_NAMESPACE);
            results = expr.evaluate(document);
            if (!results.isEmpty() && StringUtils.isNotBlank(packageSize.getPackageSizeL2())) {
                Element packageSizeL2 = results.iterator().next();
                packageSizeL2.getAttribute("value").setValue(packageSize.getPackageSizeL2());
            }
            //packageSizeL3
            expr = xpathFactory.compile("//hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:quantity", Filters.element(), null, HL7_NAMESPACE, HL7_PHARMACY_NAMESPACE);
            results = expr.evaluate(document);
            if (!results.isEmpty() && StringUtils.isNotBlank(packageSize.getPackageSizeL3())) {
                Element packageSizeL3 = results.iterator().next();
                packageSizeL3.getAttribute("value").setValue(packageSize.getPackageSizeL3());
            }
        }
    }
}
