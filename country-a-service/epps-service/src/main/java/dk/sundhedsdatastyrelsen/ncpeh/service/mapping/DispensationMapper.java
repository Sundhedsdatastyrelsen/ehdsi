package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierPredefinedSourceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PackageNumberType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationOnPrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import lombok.NonNull;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.slf4j.Logger;
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
 */
public class DispensationMapper {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DispensationMapper.class);

    private DispensationMapper() {
    }

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
    /*
<CreatePharmacyEffectuationRequest>
    <PersonIdentifier source="CPR">1111111118</PersonIdentifier>
    <CreatedBy>
        <Other>
            <Name>
                <GivenName>Anne</GivenName>
                <Surname>Andersen</Surname>
            </Name>
        </Other>
        <Role>Apoteksansat</Role>
        <Organisation>
            <Name>Skanderborg Apotek</Name>
            <AddressLine>Adelgade 27</AddressLine>
            <AddressLine>8660 Skanderborg</AddressLine>
            <Type>Apotek</Type>
	    <Identifier source="EAN-Lokationsnummer">5790000170609</Identifier>
        </Organisation>
    </CreatedBy>
    <Prescription>
        <PrescriptionIdentifier>1341404071655002003</PrescriptionIdentifier>
        <OrderIdentifier>1341404070747001001</OrderIdentifier>
        <Effectuation>
            <DateTime>2014-03-10T11:12:39Z</DateTime>
            <PackageDispensed>
                <PackageQuantity>1</PackageQuantity>
                <PackageNumber source="Medicinpriser" date="2014-03-10">789</PackageNumber>
                <PackageSize>
                    <Value>40</Value>
                    <UnitCode source="Medicinpriser" date="2014-03-10">stk</UnitCode>
                    <UnitText>stk</UnitText>
                </PackageSize>
                <SubstitutedDrug>
                    <Identifier source="Medicinpriser" date="2014-03-10">28101234504</Identifier>
                    <Name>Eksemplificin</Name>
                    <Form>
                        <Code source="Medicinpriser" date="2014-03-10">TAB</Code>
                        <Text>tabletter</Text>
                    </Form>
                    <Strength>
                        <Value>50</Value>
                        <UnitCode source="Medicinpriser" date="2014-03-10">MG</UnitCode>
                        <UnitText>mg</UnitText>
                    </Strength>
                </SubstitutedDrug>
            </PackageDispensed>
            <ExpectedDeliveryDateTime>2014-03-10T14:00:00Z</ExpectedDeliveryDateTime>
            <PharmacyComment>Oprettet i Betalingsservice</PharmacyComment>
            <LabelText>1 tablet morgen, middag og aften mod smerter</LabelText>
            <DeliverySite>
                <Name>Ry Apoteksudsalg</Name>
                <AddressLine>Siimtoften 2</AddressLine>
                <AddressLine>8660 Ry</AddressLine>
                <Type>Apotek</Type>
	        <Identifier source="CVR-P">1008648049</Identifier>
            </DeliverySite>
        </Effectuation>
        <Terminate>true</Terminate>
    </Prescription>
</CreatePharmacyEffectuationRequest>
     */
    static class XPaths {
        private XPaths() {
            throw new IllegalStateException("Static utility class should not be instantiated");
        }

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
        static final String effectiveTime =
            "/hl7:ClinicalDocument/hl7:effectiveTime/@value";
        static final String packageQuantity =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:quantity";
        static final String containerPackagedProductCode =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:code";
        static final String manufacturedMaterialName =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/hl7:name";
        static final String manufacturedMaterialCode =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/hl7:code";
        static final String substitution =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:entryRelationship[@typeCode = 'COMP']/hl7:act/hl7:code[@code = 'SUBST']";
        static final String atcCode =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asSpecializedKind[@classCode='GRIC']/pharm:generalizedMaterialKind/pharm:code";
        static final String cdaId =
            "/hl7:ClinicalDocument/hl7:id";
    }

    /**
     * Construct a new XPath object.
     * XPath is not thread-safe, so we construct a new instance each time instead of reusing.
     */
    private static XPath xpath() {
        var xpath = XPathFactory.newInstance().newXPath();
        var nsCtx = new SimpleNamespaceContext();
        nsCtx.bindNamespaceUri("hl7", "urn:hl7-org:v3");
        nsCtx.bindNamespaceUri("pharm", "urn:hl7-org:pharm");
        xpath.setNamespaceContext(nsCtx);
        return xpath;
    }

    private static List<String> evalMany(XPath xpath, Document cda, String xpathExpression) throws XPathExpressionException {
        var nodeList = (DTMNodeList) xpath.evaluate(xpathExpression, cda, XPathConstants.NODESET);
        var l = nodeList.getLength();
        var result = new ArrayList<String>();
        for (var i = 0; i < l; i++) {
            result.add(nodeList.item(i).getTextContent());
        }
        return Collections.unmodifiableList(result);
    }

    private static List<String> evalMany(
        XPath xpath,
        Document cda,
        String xpathExpression,
        String attrName
    ) throws XPathExpressionException {
        var nodeList = (DTMNodeList) xpath.evaluate(xpathExpression, cda, XPathConstants.NODESET);
        var l = nodeList.getLength();
        var result = new ArrayList<String>();
        for (var i = 0; i < l; i++) {
            var item = nodeList.item(i).getAttributes().getNamedItem(attrName);
            if (item != null) {
                result.add(item.getTextContent());
            }
        }
        return Collections.unmodifiableList(result);
    }

    static ModificatorPersonType authorPerson(Document cda) throws XPathExpressionException {
        var xpath = xpath();
        var familyNames = evalMany(xpath, cda, XPaths.authorFamilyName);
        var givenNames = evalMany(xpath, cda, XPaths.authorGivenName);
        var allButLastName = Stream.concat(
                givenNames.stream(),
                familyNames.subList(0, familyNames.size() - 1).stream())
            .collect(Collectors.joining(" "));
        return ModificatorPersonType.builder()
            .withName()
            .withSurname(familyNames.getLast())
            .withGivenName(allButLastName).end()
            .withPersonIdentifier().withSource("CPR").withValue("3001010033").end()
            .build();
    }

    static String authorRole(Document cda) throws XPathExpressionException {
        var xpath = xpath();
        var functionCode = (String) xpath.evaluate(XPaths.authorFunctionCode, cda, XPathConstants.STRING);
        var functionCodeSystem = (String) xpath.evaluate(XPaths.authorFunctionCodeSystem, cda, XPathConstants.STRING);
        if ("2262".equals(functionCode) && "2.16.840.1.113883.2.9.6.2.7".equals(functionCodeSystem)) {
            //This is the "official" translation of "Pharmacists" from ISCO.
            // It has implications in FMK, who validates these
            return "Apoteker";
        }
        if ("221".equals(functionCode) && "2.16.840.1.113883.2.9.6.2.7".equals(functionCodeSystem)) {
            //This is the translation of "Medical Doctor"
            return "Læge";
        }

        throw new IllegalArgumentException("Unexpected function code: " + functionCode);
    }

    private static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }

    private static OrganisationIdentifierType placeholdePharmacyId() {
        return OrganisationIdentifierType.builder()
            .withSource(OrganisationIdentifierPredefinedSourceType.EAN_LOKATIONSNUMMER.value())
            .withValue("5790001392277") // NSI, Udenlandsk Apotek via epSOS ( SOR-nummer: 397941000016003 )
            .build();
    }

    static OrganisationType authorOrganization(Document cda) throws XPathExpressionException {
        var xpath = xpath();
        var addressLines = new ArrayList<>(evalMany(xpath, cda, XPaths.authorOrgAddressLine));
        var postalCode = (String) xpath.evaluate(XPaths.authorOrgPostalCode, cda, XPathConstants.STRING);
        var city = (String) xpath.evaluate(XPaths.authorOrgCity, cda, XPathConstants.STRING);
        var state = (String) xpath.evaluate(XPaths.authorOrgState, cda, XPathConstants.STRING);
        var country = (String) xpath.evaluate(XPaths.authorOrgCountry, cda, XPathConstants.STRING);
        if (notBlank(postalCode)) addressLines.add(postalCode);
        if (notBlank(city)) addressLines.add(city);
        if (notBlank(state)) addressLines.add(state);
        if (notBlank(country)) addressLines.add(country);

        String email = null, telephone = null;
        var telecoms = evalMany(xpath, cda, XPaths.authorOrgTelecom, "value");
        for (var t : telecoms) {
            if (t == null) continue;
            if (t.startsWith("tel:")) telephone = t.substring(4);
            if (t.startsWith("mailto:")) email = t.substring(7);
        }

        var b = OrganisationType.builder()
            .withIdentifier(placeholdePharmacyId())
            .withName((String) xpath.evaluate(XPaths.authorOrgName, cda, XPathConstants.STRING))
            .withType("Apotek")
            .addAddressLine(addressLines);

        if (email != null) b.withEmailAddress(email);
        if (telephone != null) b.withTelephoneNumber(telephone);

        return b.build();
    }

    static OrganisationType orderedAtPharmacy(Document cda) throws XPathExpressionException {
        return authorOrganization(cda);
    }

    public static long prescriptionId(Document cda) throws XPathExpressionException, MapperException {
        var xpath = xpath();
        var id = (Node) xpath.evaluate(XPaths.inFulfillmentOfId, cda, XPathConstants.NODE);
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

    static StartEffectuationRequestType.Prescription startEffectuationRequestPrescription(Document cda) throws XPathExpressionException,
        MapperException {
        return StartEffectuationRequestType.Prescription.builder()
            .withIdentifier(prescriptionId(cda))
            .build();
    }

    static CreatePharmacyEffectuationOnPrescriptionType prescription(
        Document cda,
        @NonNull StartEffectuationResponseType startEffectuationResponse
    ) throws XPathExpressionException, MapperException {
        var order = startEffectuationResponse.getPrescription().getFirst().getOrder().getFirst();
        var packageRestriction = startEffectuationResponse.getPrescription().getFirst().getPackageRestriction();
        var terminate = packageQuantity(cda) == packageRestriction.getPackageQuantity();
        return CreatePharmacyEffectuationOnPrescriptionType.builder()
            .withPrescriptionIdentifier(prescriptionId(cda))
            .withOrderIdentifier(order.getIdentifier())
            .withEffectuation(effectuation(cda, startEffectuationResponse))
            .withTerminate(terminate)
            .build();
    }

    static Integer packageQuantity(Document cda) throws XPathExpressionException, MapperException {
        var xpath = xpath();
        var node = (Node) xpath.evaluate(XPaths.packageQuantity, cda, XPathConstants.NODE);
        var unit = xpath.evaluate("@unit", node);
        if (!"1".equals(unit)) {
            throw new MapperException("Unsupported quantity unit: " + unit);
        }
        return Integer.parseInt(xpath.evaluate("@value", node));
    }

    static CreatePharmacyEffectuationType effectuation(
        Document cda,
        StartEffectuationResponseType startEffectuationResponse
    ) throws XPathExpressionException, MapperException {
        var xpath = xpath();
        var effectiveTime = (String) xpath.evaluate(XPaths.effectiveTime, cda, XPathConstants.STRING);
        var packageRestriction = startEffectuationResponse.getPrescription().getFirst().getPackageRestriction();

        var drug = drug(cda);

        return CreatePharmacyEffectuationType.builder()
            .withDateTime(Utils.parseEpsosTime(effectiveTime))
            .withPackageDispensed()
            .withPackageQuantity(packageQuantity(cda))
            .withPackageNumber(packageNumber(cda))
            .withPackageSize(packageRestriction.getPackageSize()) // TODO get package size from CDA
            .withSubstitutedDrug(drug)
            .end()
            .withDeliverySite(TestIdentities.deliverySiteRyApotek) // TODO #190
            .build();
    }

    static DrugType drug(Document cda) {
        return DrugType.builder()
            .withDetailedDrugText(detailedDrugText(cda))
            // TODO: Strength
            // TODO: ATC code
            // TODO: substances
            .build();
    }

    static String detailedDrugText(Document cda) {
        var xpath = xpath();
        String drugName;
        try {
            drugName = (String) xpath.evaluate(XPaths.manufacturedMaterialName, cda, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            throw new DataRequirementException(String.format("Could not find data at path: %s", XPaths.manufacturedMaterialName));
        }
        String drugId;
        try {
            var node = (Node) xpath.evaluate(XPaths.manufacturedMaterialCode, cda, XPathConstants.NODE);
            var system = xpath.evaluate("@codeSystem", node);
            var id = xpath.evaluate("@code", node);
            drugId = String.format("%s^^^%s", system, id);
        } catch (XPathExpressionException e) {
            drugId = "unknown";
        }
        return String.format("%s - id: %s", drugName, drugId);
    }

    static PackageNumberType packageNumber(Document cda) {
        //        try {
//            var xpath = xpath();
//            var node = (Node) xpath.evaluate(XPaths.containerPackagedProductCode, cda, XPathConstants.NODE);
//            if (node == null) {
//                return null; //The field is 0..1, and we cannot require it to be there. If it is missing, we return null.
//            }
//            var codeSystem = xpath.evaluate("@codeSystem", node);
//            var code = xpath.evaluate("@code", node);
//
//            var packageNumber = codeSystem + "^^^" + code;
//            return PackageNumberType.builder()
//                .withSource("Local")
//                .withValue(packageNumber)
//                .build();
//        } catch (XPathExpressionException e) {
//            log.warn("Could not find find data at path: {}", XPaths.manufacturedMaterialCode);
//            return null;
//        }
        return PackageNumberType.builder()
            .withSource("Local")
            .withValue("720000") // "Ukendt" https://wiki.fmk-teknik.dk/doku.php?id=fmk:generel:varenumre
            .build();
    }

    public static String cdaId(Document cda) throws MapperException {
        try {
            var xpath = xpath();
            var node = (Node) xpath.evaluate(XPaths.cdaId, cda, XPathConstants.NODE);
            var root = xpath.evaluate("@root", node);
            var ext = xpath.evaluate("@extension", node);
            return ext == null
                ? root
                : root + "^^^" + ext;
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage());
        }
    }

    public static StartEffectuationRequestType startEffectuationRequest(
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
                .withPrescription(startEffectuationRequestPrescription(cda))
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage());
        }
    }

    public static CreatePharmacyEffectuationRequestType createPharmacyEffectuationRequest(
        @NonNull String patientId,
        @NonNull Document cda,
        @NonNull StartEffectuationResponseType startEffectuationResponse
    ) throws MapperException {
        var obf = new ObjectFactory();
        try {
            return CreatePharmacyEffectuationRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue(PatientIdMapper.toCpr(patientId)).end()
                .withCreatedBy().withContent(
                    obf.createModificatorTypeOther(authorPerson(cda)),
                    obf.createModificatorTypeRole(authorRole(cda)),
                    obf.createModificatorTypeOrganisation(authorOrganization(cda))
                ).end()
                .addPrescription(prescription(cda, startEffectuationResponse))
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage(), e);
        }
    }

    public static UndoEffectuationRequestType createUndoEffectuationRequest(
        @NonNull String patientId,
        @NonNull Document cda,
        long orderId,
        long effectuationId
    ) throws MapperException {
        var obf = new ObjectFactory();
        try {
            var prescriptionId = prescriptionId(cda);

            return dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue(PatientIdMapper.toCpr(patientId)).end()
                .withModifiedBy().withContent(
                    obf.createModificatorTypeOther(authorPerson(cda)),
                    obf.createModificatorTypeRole(authorRole(cda)),
                    obf.createModificatorTypeOrganisation(authorOrganization(cda))
                ).end()
                .addPrescription()
                .withIdentifier(prescriptionId)
                .withOrder()
                .withIdentifier(orderId)
                .withEffectuation().withIdentifier(effectuationId).end()
                .end()
                .end()
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage());
        }
    }
}
