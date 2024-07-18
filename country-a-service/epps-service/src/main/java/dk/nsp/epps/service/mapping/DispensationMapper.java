package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierPredefinedSourceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.sds.ncp.cda.MapperException;
import dk.sds.ncp.cda.Oid;
import lombok.NonNull;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for mapping eDispensation CDAs to FMK requests.
 * Not threadsafe due to xpath, so construct a new instance for each thread.
 */
public class DispensationMapper {
    // ART DECOR template for eDispensation CDA:
    // https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/tmp-2.16.840.1.113883.3.1937.777.11.10.111-2020-10-07T094007.html
    // Example FMK request:
    /*
    <StartEffectuationRequest>
    <PersonIdentifier source="CPR">1111111118</PersonIdentifier>
    <ModifiedBy>
        <Other>
            <Name>
                <GivenName>Anne</GivenName>
                <Surname>Andersen</Surname>
            </Name>
            <PersonIdentifier source="CPR">1212121234</PersonIdentifier>
        </Other>
        <Role>Apoteksansat</Role>
        <Organisation>
            <Name>Skanderborg Apotek</Name>
            <AddressLine>Adelgade 27</AddressLine>
            <AddressLine>8660 Skanderborg</AddressLine>
            <Type>Apotek</Type>
            <Identifier source="EAN-Lokationsnummer">5790000170609</Identifier>
        </Organisation>
    </ModifiedBy>
    <OrderedAtPharmacy>
        <Name>Skanderborg Apotek</Name>
        <Type>Apotek</Type>
        <Identifier source="EAN-Lokationsnummer">5790000170609</Identifier>
    </OrderedAtPharmacy>
    <Prescription>
        <Identifier>1341404071001001001</Identifier>
    </Prescription>
    <Prescription>
        <Identifier>1341404071001002001</Identifier>
    </Prescription>
</StartEffectuationRequest>
    */
    private static class XPaths {
        static final String authorFamilyName =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:assignedPerson/hl7:name/hl7:family";
        static final String authorGivenName =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:assignedPerson/hl7:name/hl7:given";
        static final String authorFunctionCode =
            "/hl7:ClinicalDocument/hl7:author/hl7:functionCode/@code";
        static final String authorFunctionCodeSystem =
            "/hl7:ClinicalDocument/hl7:author/hl7:functionCode/@codeSystem";
        static final String authorOrgName =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:name";
        static final String authorOrgId =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:id";
        static final String authorOrgTelecom =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:telecom";
        static final String authorOrgAddressLine =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:addr/hl7" +
                ":streetAddressLine";
        static final String authorOrgCity =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:addr/hl7:city";
        static final String authorOrgPostalCode =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:addr/hl7:postalCode";
        static final String authorOrgState =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:addr/hl7:state";
        static final String authorOrgCountry =
            "/hl7:ClinicalDocument/hl7:author/hl7:assignedAuthor/hl7:representedOrganization/hl7:addr/hl7:country";
        static final String inFulfillmentOfId =
            "/hl7:ClinicalDocument/hl7:inFulfillmentOf/hl7:order/hl7:id";
    }

    private final XPath xpath;

    {
        xpath = XPathFactory.newInstance().newXPath();
        var nsCtx = new SimpleNamespaceContext();
        nsCtx.bindNamespaceUri("hl7", "urn:hl7-org:v3");
        xpath.setNamespaceContext(nsCtx);
    }

    private List<String> evalMany(Document cda, String xpathExpression) throws XPathExpressionException {
        var nodeList = (DTMNodeList) xpath.evaluate(xpathExpression, cda, XPathConstants.NODESET);
        var l = nodeList.getLength();
        var result = new ArrayList<String>();
        for (var i = 0; i < l; i++) {
            result.add(nodeList.item(i).getTextContent());
        }
        return Collections.unmodifiableList(result);
    }

    private List<String> evalMany(
        Document cda,
        String xpathExpression,
        String attrName
    ) throws XPathExpressionException {
        var nodeList = (DTMNodeList) xpath.evaluate(xpathExpression, cda, XPathConstants.NODESET);
        var l = nodeList.getLength();
        var result = new ArrayList<String>();
        for (var i = 0; i < l; i++) {
            result.add(nodeList.item(i).getAttributes().getNamedItem(attrName).getTextContent());
        }
        return Collections.unmodifiableList(result);
    }

    private String eval(Document cda, String xpathExpression) throws XPathExpressionException {
        return (String) xpath.evaluate(xpathExpression, cda, XPathConstants.STRING);
    }

    private Node evalNode(Document cda, String xpathExpression) throws XPathExpressionException {
        return (Node) xpath.evaluate(xpathExpression, cda, XPathConstants.NODE);
    }

    ModificatorPersonType authorPerson(Document cda) throws XPathExpressionException {
        var familyNames = evalMany(cda, XPaths.authorFamilyName);
        var givenNames = evalMany(cda, XPaths.authorGivenName);
        var allButLastName = Stream.concat(
                givenNames.stream(),
                familyNames.subList(0, familyNames.size() - 1).stream())
            .collect(Collectors.joining(" "));
        return ModificatorPersonType.builder()
            .withName()
            .withSurname(familyNames.getLast())
            .withGivenName(allButLastName).end()
            .build();
    }

    String authorRole(Document cda) throws XPathExpressionException {
        var functionCode = eval(cda, XPaths.authorFunctionCode);
        var functionCodeSystem = eval(cda, XPaths.authorFunctionCodeSystem);
        if ("2262".equals(functionCode) && "2.16.840.1.113883.2.9.6.2.7".equals(functionCodeSystem)) {
            return "Apoteksansat";
        }
        throw new IllegalArgumentException("Unexpected function code: " + functionCode);
    }

    private boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }

    private OrganisationIdentifierType identifier(Node id) {
        var attrs = id.getAttributes();
        var root = attrs.getNamedItem("root");
        var ext = attrs.getNamedItem("extension");
        if (root == null) {
            throw new IllegalArgumentException("Id nodes without root attributes are unsupported");
        }
        return OrganisationIdentifierType.builder()
            .withSource(OrganisationIdentifierPredefinedSourceType.UDENLANDSK.value())
            .withValue(ext == null
                ? root.getTextContent()
                : String.format("%s.%s", root.getTextContent(), ext.getTextContent()))
            .build();
    }

    OrganisationType authorOrganization(Document cda) throws XPathExpressionException {
        var addressLines = new ArrayList<>(evalMany(cda, XPaths.authorOrgAddressLine));
        var postalCode = eval(cda, XPaths.authorOrgPostalCode);
        var city = eval(cda, XPaths.authorOrgCity);
        var state = eval(cda, XPaths.authorOrgState);
        var country = eval(cda, XPaths.authorOrgCountry);
        if (notBlank(postalCode)) addressLines.add(postalCode);
        if (notBlank(city)) addressLines.add(city);
        if (notBlank(state)) addressLines.add(state);
        if (notBlank(country)) addressLines.add(country);

        String email = null, telephone = null;
        var telecoms = evalMany(cda, XPaths.authorOrgTelecom, "value");
        for (var t : telecoms) {
            if (t == null) continue;
            if (t.startsWith("tel:")) telephone = t.substring(4);
            if (t.startsWith("mailto:")) email = t.substring(7);
        }

        var b = OrganisationType.builder()
            .withIdentifier(identifier(evalNode(cda, XPaths.authorOrgId)))
            .withName(eval(cda, XPaths.authorOrgName))
            .withType("Apotek") // TODO: How can we determine this?
            .addAddressLine(addressLines);

        if (email != null) b.withEmailAddress(email);
        if (telephone != null) b.withTelephoneNumber(telephone);

        return b.build();
    }

    OrganisationType orderedAtPharmacy(Document cda) throws XPathExpressionException {
        return authorOrganization(cda);
    }

    private long prescriptionId(Document cda) throws XPathExpressionException, MapperException {
        var id = evalNode(cda, XPaths.inFulfillmentOfId);
        var root = xpath.evaluate("@root", id);
        if (!Oid.DK_FMK_PRESCRIPTION.value.equals(root)) {
            throw new MapperException("Unknown prescription id type: " + root);
        }
        var ext = xpath.evaluate("@extension", id);
        try {
            return Long.parseLong(ext);
        } catch (NumberFormatException e) {
            throw new MapperException("Unexpected prescription id format: " + ext);
        }
    }

    StartEffectuationRequestType.Prescription prescription(Document cda) throws XPathExpressionException,
        MapperException {
        return StartEffectuationRequestType.Prescription.builder()
            .withIdentifier(prescriptionId(cda))
            .build();
    }

    public StartEffectuationRequestType startEffectuationRequest(
        @NonNull String patientId,
        @NonNull Document cda
    ) throws MapperException {
        try {
            var obf = new ObjectFactory();
            return StartEffectuationRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue(PatientIdMapper.toCpr(patientId)).end()
                .withModifiedBy().withContent(
                    obf.createModificatorTypeOther(authorPerson(cda)),
                    obf.createModificatorTypeRole(authorRole(cda)),
                    obf.createModificatorTypeOrganisation(authorOrganization(cda))
                ).end()
                .withOrderedAtPharmacy(orderedAtPharmacy(cda))
                .withPrescription(prescription(cda))
                .build();
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public CreatePharmacyEffectuationRequestType createPharmacyEffectuationRequest(
        @NonNull String patientId,
        @NonNull Document dispensationCda
    ) {
        throw new UnsupportedOperationException("TODO");
    }
}
