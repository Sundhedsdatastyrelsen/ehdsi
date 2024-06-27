package eu.europa.ec.sante.ehdsi.portal.util.cda.util;

import eu.europa.ec.sante.ehdsi.portal.model.DispenseRequest;
import eu.europa.ec.sante.ehdsi.portal.util.cda.enums.CodeSystem;
import eu.europa.ec.sante.ehdsi.portal.util.cda.enums.Namespaces;
import eu.europa.ec.sante.ehdsi.portal.util.cda.enums.Templates;
import eu.europa.ec.sante.ehdsi.portal.util.cda.model.*;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CDABodyUtil {

    private static final Namespace HL7_PHARMACY_NAMESPACE = Namespace.getNamespace(Namespaces.HL7_PHARMACY.getPrefix(), Namespaces.HL7_PHARMACY.getUri());

    private CDABodyUtil() {
    }

    public static void addComponent(Namespace hl7Namespace, Element rootElement, DispenseRequest dispenseRequest, org.jdom2.Document ePDocument) {

        var component = new Element("component", hl7Namespace);
        var structuredBody = new Element("structuredBody", hl7Namespace);
        structuredBody.setAttribute("classCode", "DOCBODY");
        var subComponent = new Element("component", hl7Namespace);
        subComponent.setAttribute("typeCode", "COMP");
        var section = new Element("section", hl7Namespace);
        section.setAttribute("classCode", "DOCSECT");
        section.setAttribute("moodCode", "EVN");
        CDAUtil.addTemplateId(hl7Namespace, section, Templates.EHDSI_DISPENSATION_SECTION);

        var root = ePDocument.getRootElement();
        var id = root.getChild("id", hl7Namespace);
        Identifier identifier = new Identifier(id.getAttribute("root").getValue(),
                StringUtils.isNotBlank(id.getAttribute("extension").getValue()) ? id.getAttribute("extension").getValue() : null);

        CDAUtil.addId(hl7Namespace, identifier, section);
        addCode(hl7Namespace, section);
        addTitle(hl7Namespace, section, identifier);
        addText(section, ePDocument);
        addEntry(hl7Namespace, section, dispenseRequest, ePDocument);
        subComponent.addContent(section);
        structuredBody.addContent(subComponent);
        component.addContent(structuredBody);
        rootElement.addContent(component);
    }

    private static void addCode(Namespace hl7Namespace, Element section) {
        Element code = new Element("code", hl7Namespace);
        code.setAttribute("code", "60590-7");
        code.setAttribute("codeSystem", CodeSystem.LOINC.getOid());
        code.setAttribute("codeSystemName", CodeSystem.LOINC.getName());
        code.setAttribute("displayName", "Medication dispensed");
        section.addContent(code);
    }

    private static void addTitle(Namespace hl7Namespace, Element section, Identifier identifier) {
        Element title = new Element("title", hl7Namespace);
        title.addContent("Dispensation: " + identifier.getExtension());
        section.addContent(title);
    }

    private static void addText(Element section, org.jdom2.Document ePDocument) {
        CDAUtil.copyAllNodesAndAttributesFirstOccurrence(ePDocument, section, "text");
    }

    private static void addEntry(Namespace hl7Namespace, Element section, DispenseRequest dispenseRequest, org.jdom2.Document ePDocument) {
        Element entry = new Element("entry", hl7Namespace);
        entry.setAttribute("typeCode", "COMP");
        addSupply(hl7Namespace, entry, dispenseRequest, ePDocument);
        section.addContent(entry);
    }

    private static void addSupply(Namespace hl7Namespace, Element entry, DispenseRequest dispenseRequest, org.jdom2.Document ePDocument) {
        Element supply = new Element("supply", hl7Namespace);
        supply.setAttribute("classCode", "SPLY");
        supply.setAttribute("moodCode", "EVN");
        CDAUtil.addTemplateId(hl7Namespace, supply, Templates.EHDSI_SUPPLY);
        CDAUtil.addTemplateId(hl7Namespace, supply, Templates.IHE_SUPPLY_ENTRY);
        CDAUtil.addTemplateId(hl7Namespace, supply, Templates.CCD_SUPPLY_ACTIVITY);
        //TODO create real identifier
        Identifier identifier = new Identifier("root", "extension");
        CDAUtil.addId(hl7Namespace, identifier, supply);
        addQuantity(hl7Namespace, supply, dispenseRequest.getNumberOfPackage());
        addProduct(hl7Namespace, supply, dispenseRequest, ePDocument);
        addPerformer(hl7Namespace, supply, getPharmacistInformation());
        addParticipant(hl7Namespace, supply);
        addRelatedPrescription(hl7Namespace, supply, ePDocument);
        if (dispenseRequest.isSubstitution()) {
            addSubstitution(hl7Namespace, supply);
        }
        entry.addContent(supply);
    }

    private static void addQuantity(Namespace hl7Namespace, Element supply, long numberOfPackages) {
        var quantity = new Element("quantity", hl7Namespace);
        quantity.setAttribute("value", String.valueOf(numberOfPackages));
        quantity.setAttribute("unit", "1");
        supply.addContent(quantity);
    }

    private static void addProduct(Namespace hl7Namespace, Element supply, DispenseRequest dispenseRequest, org.jdom2.Document ePDocument) {
        var product = new Element("product", hl7Namespace);
        supply.addContent(product);
        CDAUtil.copyAllNodesAndAttributes(ePDocument, dispenseRequest, product, "manufacturedProduct");
    }

    private static void addPerformer(Namespace hl7Namespace, Element supply, PharmacistInformation pharmacistInformation) {
        var performer = new Element("performer", hl7Namespace);
        performer.setAttribute("typeCode", "PRF");
        var time = new Element("time", hl7Namespace);
        //TODO Set the same time
        time.setAttribute("value", HL7Util.formatDateHL7(new Date()));
        performer.addContent(time);
        addAssignedEntity(hl7Namespace, performer, pharmacistInformation);
        supply.addContent(performer);
    }

    private static void addAssignedEntity(Namespace hl7Namespace, Element performer, PharmacistInformation pharmacistInformation) {
        var assignedEntity = new Element("assignedEntity", hl7Namespace);
        CDAUtil.addId(hl7Namespace, pharmacistInformation.getIdentifier(), assignedEntity);
        CDAUtil.addAddress(hl7Namespace, pharmacistInformation.getAddress(), assignedEntity);
        CDAUtil.addTelecom(hl7Namespace, pharmacistInformation.getTelecom(), assignedEntity);
        CDAUtil.addAssignedPerson(hl7Namespace, pharmacistInformation.getAssignedPerson(), assignedEntity);
        CDAUtil.addRepresentedOrganization(hl7Namespace, pharmacistInformation.getRepresentedOrganization(), assignedEntity);
        performer.addContent(assignedEntity);
    }

    private static void addParticipant(Namespace hl7Namespace, Element supply) {
        var participant = new Element("participant", hl7Namespace);
        participant.setAttribute("typeCode", "PRF");
        participant.setAttribute("contextControlCode", "OP");
        var participantRole = new Element("participantRole", hl7Namespace);
        participantRole.setAttribute("classCode", "LIC");
        CDAUtil.addId(hl7Namespace, new Identifier("entryOid", null), participantRole);
        addScopingEntity(hl7Namespace, participantRole);
        participant.addContent(participantRole);
        supply.addContent(participant);
    }

    private static void addScopingEntity(Namespace hl7Namespace, Element participantRole) {
        var scopingEntity = new Element("scopingEntity", hl7Namespace);
        scopingEntity.setAttribute("classCode", "ORG");
        CDAUtil.addId(hl7Namespace, new Identifier("entryOid", null), scopingEntity);
        participantRole.addContent(scopingEntity);
    }

    private static void addRelatedPrescription(Namespace hl7Namespace, Element supply, org.jdom2.Document ePDocument) {
        var entryRelationship = new Element("entryRelationship", hl7Namespace);
        entryRelationship.setAttribute("typeCode", "REFR");
        var substanceAdministration = new Element("substanceAdministration", hl7Namespace);
        substanceAdministration.setAttribute("classCode", "SBADM");
        substanceAdministration.setAttribute("moodCode", "INT");
        var id = new Element("id", hl7Namespace);
        var root = ePDocument.getRootElement();
        var filter = new ElementFilter("substanceAdministration");
        for (Element c : root.getDescendants(filter)) {
            var substanceAdministrationId = c.getChild("id", hl7Namespace);
            if(substanceAdministrationId.getAttribute("extension") != null) {
                id.setAttribute("extension", substanceAdministrationId.getAttribute("extension").getValue());
            }
            id.setAttribute("root", substanceAdministrationId.getAttribute("root").getValue());
        }
        substanceAdministration.addContent(id);
        var consumable = new Element("consumable", hl7Namespace);
        CDAUtil.copyAllNodesAndAttributes(ePDocument, null, consumable, "manufacturedProduct");
        substanceAdministration.addContent(consumable);
        entryRelationship.addContent(substanceAdministration);
        supply.addContent(entryRelationship);
    }

    private static void addSubstitution(Namespace hl7Namespace, Element supply) {
        var entryRelationship = new Element("entryRelationship", hl7Namespace);
        entryRelationship.setAttribute("typeCode", "COMP");
        var act = new Element("act", hl7Namespace);
        act.setAttribute("classCode", "ACT");
        act.setAttribute("moodCode", "EVN");
        addSubstitutionCode(hl7Namespace, act);
        entryRelationship.addContent(act);
        supply.addContent(entryRelationship);
    }

    private static void addSubstitutionCode(Namespace hl7Namespace, Element act) {
        var code = new Element("code", hl7Namespace);
        code.setAttribute("code", "SUBST");
        code.setAttribute("codeSystem", CodeSystem.ACT_CLASS.getOid());
        code.setAttribute("codeSystemName", CodeSystem.ACT_CLASS.getName());
        code.setAttribute("displayName", "Substitution");
        act.addContent(code);
    }

    private static PharmacistInformation getPharmacistInformation() {
        //TODO provide real pharmacistInformation
        Map<String, String> telecoms = new HashMap<>();
        telecoms.put("WP", "tel:123456789");
        Telecom telecom = new Telecom(telecoms);
        Address address = new Address("streetAddressLine", "postalCode", "city", "country");
        AssignedPerson assignedPerson = new AssignedPerson(Set.of("givenName"), "familyName", "suffix");
        RepresentedOrganization representedOrganization = new RepresentedOrganization(new Identifier("root", null), "name", telecom, address);
        return new PharmacistInformation(new Identifier("root", "extension"), address, telecom, assignedPerson, representedOrganization);
    }
}
