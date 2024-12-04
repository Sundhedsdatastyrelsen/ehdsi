package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationOnPrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.service.Utils;
import dk.nsp.epps.service.exception.CountryAException;
import dk.nsp.epps.service.exception.DataRequirementException;
import dk.sds.ncp.cda.MapperException;
import dk.sds.ncp.cda.Oid;
import lombok.NonNull;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
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
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DispensationMapper.class);

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
        static final String manufacturedMaterialCode =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:code";
        static final String substitution =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:entryRelationship[@typeCode = 'COMP']/hl7:act/hl7:code[@code = 'SUBST']";
    }

    private final XPath xpath;

    {
        xpath = XPathFactory.newInstance().newXPath();
        var nsCtx = new SimpleNamespaceContext();
        nsCtx.bindNamespaceUri("hl7", "urn:hl7-org:v3");
        nsCtx.bindNamespaceUri("pharm", "urn:hl7-org:pharm");
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
            var item = nodeList.item(i).getAttributes().getNamedItem(attrName);
            if (item != null) {
                result.add(item.getTextContent());
            }
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
            .withPersonIdentifier().withSource("CPR").withValue("3001010033").end()
            .build();
    }

    String authorRole(Document cda) throws XPathExpressionException {
        var functionCode = eval(cda, XPaths.authorFunctionCode);
        var functionCodeSystem = eval(cda, XPaths.authorFunctionCodeSystem);
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

    private boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }

    /**
     * TODO FMK Improvement
     * FMK is supposed to support "Udenlandsk" OrganisationIdenfier, according to the documentation
     * When called, it responds with an error that it is not supported yet
     * <p>
     * Two options going forward:
     * - FMK start supporting Udenlandsk, and we reimplement forwarding the identifier from Country-B
     * - We agree with FMK to "proxy" all requests through another Organisation, like we do in the code right now.
     * <p>
     * (2024/09/10)
     */
    private OrganisationIdentifierType identifier(Node id) {

//        var attrs = id.getAttributes();
//        var root = attrs.getNamedItem("root");
//        var ext = attrs.getNamedItem("extension");
//        if (root == null) {
//            throw new IllegalArgumentException("Id nodes without root attributes are unsupported");
//        }
//        return OrganisationIdentifierType.builder()
//            .withSource(OrganisationIdentifierPredefinedSourceType.UDENLANDSK.value())
//            .withValue(ext == null
//                ? root.getTextContent()
//                : String.format("%s.%s", root.getTextContent(), ext.getTextContent()))
//            .build();
        return TestIdentities.skanderborgApotek;
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

    public long prescriptionId(Document cda) throws XPathExpressionException, MapperException {
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

    StartEffectuationRequestType.Prescription startEffectuationRequestPrescription(Document cda) throws XPathExpressionException,
        MapperException {
        return StartEffectuationRequestType.Prescription.builder()
            .withIdentifier(prescriptionId(cda))
            .build();
    }

    CreatePharmacyEffectuationOnPrescriptionType prescription(
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

    Integer packageQuantity(Document cda) throws XPathExpressionException, MapperException {
        var node = evalNode(cda, XPaths.packageQuantity);
        var unit = xpath.evaluate("@unit", node);
        if (!"1".equals(unit)) {
            throw new MapperException("Unsupported quantity unit: " + unit);
        }
        return Integer.parseInt(xpath.evaluate("@value", node));
    }

    CreatePharmacyEffectuationType effectuation(
        Document cda,
        StartEffectuationResponseType startEffectuationResponse
    ) throws XPathExpressionException, MapperException {
        var effectiveTime = eval(cda, XPaths.effectiveTime);
        var packageRestriction = startEffectuationResponse.getPrescription().getFirst().getPackageRestriction();

        // how do we handle substitutions? for now, disallow
        if (evalNode(cda, XPaths.substitution) != null) {
            log.warn("Substitutions are not supported. No conversion was made.");
        }
        // verify that package number matches prescription
        if (!packageRestriction.getPackageNumber().getValue().equals(packageNumber(cda))) {
            throw new MapperException(String.format(
                "Package number in dispensation (%s) does not match prescription (%s).",
                packageNumber(cda),
                packageRestriction.getPackageNumber().getValue()));
        }

        return CreatePharmacyEffectuationType.builder()
            .withDateTime(Utils.parseEpsosTime(effectiveTime))
            .withPackageDispensed()
            .withPackageQuantity(packageQuantity(cda))
            .withPackageNumber(packageRestriction.getPackageNumber())
            .withPackageSize(packageRestriction.getPackageSize())
            .end()
            .withDeliverySite(TestIdentities.deliverySiteRyApotek)
            .build();
    }

    String packageNumber(Document cda) {
        try {
            var node = evalNode(cda, XPaths.manufacturedMaterialCode);
            var codeSystem = xpath.evaluate("@codeSystem", node);
            if (!Oid.DK_LMS02.value.equals(codeSystem)) {
                // throw?
                log.warn(
                    "Expected LMS02 ({}) code system, for {}. Got: {}",
                    Oid.DK_LMS02.value,
                    XPaths.manufacturedMaterialCode,
                    codeSystem
                );
            }
            return xpath.evaluate("@code", node);
        } catch (XPathExpressionException e) {
            throw new DataRequirementException(String.format("Could not find find data at path: %s", XPaths.manufacturedMaterialCode));
        }
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
                .withPrescription(startEffectuationRequestPrescription(cda))
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage());
        }
    }

    public CreatePharmacyEffectuationRequestType createPharmacyEffectuationRequest(
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


    /***
     * @param patientId PatientID (containing a CPR)
     * @param cda CDA Document
     * @param prescriptionResponse Requires a new GetPrescriptionResponse specifically created to get prescriptions to cancel effectuations of
     * @throws MapperException
     */
    public UndoEffectuationRequestType createUndoEffectuationRequest(
        @NonNull String patientId,
        @NonNull Document cda,
        @NonNull GetPrescriptionResponseType prescriptionResponse
    ) throws MapperException {
        var obf = new ObjectFactory();
        try {
            var prescriptionId = prescriptionId(cda);
            var fmkPrescription = prescriptionResponse.getPrescription()
                .stream()
                .filter(p -> p.getIdentifier() == prescriptionId)
                .findFirst();
            if (fmkPrescription.isEmpty()) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find prescription to discard in system");
            }
            var lastOrder = fmkPrescription.get()
                .getOrder()
                .stream()
                .max((o1, o2) -> o1.getCreated().getDateTime().compare(o2.getCreated().getDateTime()));
            if (lastOrder.isEmpty()) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find order to discard on prescription in system");
            }
            var lastEffectuation = lastOrder.get().getEffectuation();
            if (lastEffectuation == null) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find effectuation to discard on prescription in system");
            }


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
                .withIdentifier(lastOrder.get().getIdentifier())
                .withEffectuation().withIdentifier(lastEffectuation.getIdentifier()).end()
                .end()
                .end()
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage());
        }
    }


}
