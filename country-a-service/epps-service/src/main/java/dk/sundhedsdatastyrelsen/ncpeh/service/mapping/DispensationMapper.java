package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ATCType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ObjectFactory;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrderStatusPredefinedType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierPredefinedSourceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PackageNumberType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SubstancesType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationOnPrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.OrderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageSizeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.AbortEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.service.Utils;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        static final String innermostContainerPackagedProduct =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial"
                + "//pharm:containerPackagedProduct[not(pharm:asContent)]";
        static final String manufacturedMaterialName =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/hl7:name";
        static final String manufacturedMaterialCode =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/hl7:code";
        static final String contentQuantity =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asContent/pharm:quantity";
        static final String substitution =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:entryRelationship[@typeCode = 'COMP']/hl7:act/hl7:code[@code = 'SUBST']";
        static final String atcCode =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:asSpecializedKind[@classCode='GRIC']/pharm:generalizedMaterialKind/pharm:code";
        static final String drugStrengthFreeText =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:desc";
        static final String activeIngredients =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:ingredient[@classCode = 'ACTI']";
        static final String drugFormCodeDisplayName =
            "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section/hl7:entry/hl7:supply/hl7:product/hl7:manufacturedProduct/hl7:manufacturedMaterial/pharm:formCode/@displayName";
        static final String cdaId =
            "/hl7:ClinicalDocument/hl7:id";
    }

    // XPath is not thread safe so we keep a separate copy for each thread.
    private static final ThreadLocal<XPath> xpath = ThreadLocal.withInitial(() -> {
        var xp = XPathFactory.newInstance().newXPath();
        var nsCtx = new SimpleNamespaceContext();
        nsCtx.bindNamespaceUri("hl7", "urn:hl7-org:v3");
        nsCtx.bindNamespaceUri("pharm", "urn:hl7-org:pharm");
        xp.setNamespaceContext(nsCtx);
        return xp;
    });

    private static List<Node> evalNodeMany(Document cda, String xpathExpression) throws XPathExpressionException {
        var nodeList = (NodeList) xpath.get().evaluate(xpathExpression, cda, XPathConstants.NODESET);
        var l = nodeList.getLength();
        var result = new ArrayList<Node>();
        for (var i = 0; i < l; i++) {
            result.add(nodeList.item(i));
        }
        return Collections.unmodifiableList(result);
    }

    private static List<String> evalMany(Document cda, String xpathExpression) throws XPathExpressionException {
        return evalNodeMany(cda, xpathExpression).stream().map(Node::getTextContent).toList();
    }

    static @NonNull String eval(Node node, String xpathExpression) throws XPathExpressionException {
        return xpath.get().evaluate(xpathExpression, node);
    }

    private static Node evalNode(Node node, String xpathExpression) throws XPathExpressionException {
        return (Node) xpath.get().evaluate(xpathExpression, node, XPathConstants.NODE);
    }

    static ModificatorPersonType authorPerson(Document cda) throws XPathExpressionException {
        var familyNames = evalMany(cda, XPaths.authorFamilyName);
        var givenNames = evalMany(cda, XPaths.authorGivenName);
        var allButLastName = Stream.concat(
                givenNames.stream(),
                familyNames.subList(0, familyNames.size() - 1).stream())
            .collect(Collectors.joining(" "));
        return ModificatorPersonType.builder()
            .withName()
            .withSurname(familyNames.getLast())
            .withGivenName(allButLastName)
            .end()
            // FMK's undo requires personidentifier to not be null, but it can't have a value, so this is what has been
            // agreed with them to work. See FmkIT for where we test it.
            .withPersonIdentifier()
            .withSource("Udenlandsk")
            .end()
            .build();
    }

    static String authorRole(Document cda) throws XPathExpressionException {
        var functionCode = eval(cda, XPaths.authorFunctionCode);
        var functionCodeSystem = eval(cda, XPaths.authorFunctionCodeSystem);
        if ("2262".equals(functionCode) && "2.16.840.1.113883.2.9.6.2.7".equals(functionCodeSystem)) {
            // The "official" translation of "Pharmacists" from ISCO is "Apoteker", but FMK validates this
            // and compares it with the soap header role. And they translate that as "Udenlandsk apoteker",
            // so we need to return the same here.
            return "Udenlandsk apoteker";
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

    private static OrganisationIdentifierType placeholderPharmacyId() {
        return OrganisationIdentifierType.builder()
            .withSource(OrganisationIdentifierPredefinedSourceType.EAN_LOKATIONSNUMMER.value())
            .withValue("5790001392277") // NSI, Udenlandsk Apotek via epSOS ( SOR-nummer: 397941000016003 )
            .build();
    }

    static OrganisationType authorOrganization(Document cda) throws XPathExpressionException {
        var addressLines = new ArrayList<>(evalMany(cda, XPaths.authorOrgAddressLine));
        var postalCode = eval(cda, XPaths.authorOrgPostalCode);
        var city = eval(cda, XPaths.authorOrgCity);
        var state = eval(cda, XPaths.authorOrgState);
        var country = eval(cda, XPaths.authorOrgCountry);
        if (notBlank(postalCode)) addressLines.add(postalCode);
        if (notBlank(city)) addressLines.add(city);
        if (notBlank(state)) addressLines.add(state);
        if (notBlank(country)) addressLines.add(country);

        String email = null;
        String telephone = null;
        var telecoms = evalNodeMany(cda, XPaths.authorOrgTelecom)
            .stream()
            .map(node -> node.getAttributes().getNamedItem("value"))
            .filter(Objects::nonNull)
            .map(Node::getTextContent)
            .toList();
        for (var t : telecoms) {
            if (t == null) continue;
            if (t.startsWith("tel:")) telephone = t.substring(4);
            if (t.startsWith("mailto:")) email = t.substring(7);
        }

        var name = eval(cda, XPaths.authorOrgName);
        var b = OrganisationType.builder()
            .withIdentifier(placeholderPharmacyId())
            .withName(StringUtils.isBlank(name) ? "(ukendt)" : name)
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
        var id = evalNode(cda, XPaths.inFulfillmentOfId);
        var root = eval(id, "@root");
        if (!Oid.DK_FMK_PRESCRIPTION.value.equals(root)) {
            throw new MapperException("Unknown prescription id type: " + root);
        }
        var ext = eval(id, "@extension");
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
        var order = getOpenedOrder(startEffectuationResponse.getPrescription().getFirst());
        var packageRestriction = startEffectuationResponse.getPrescription().getFirst().getPackageRestriction();
        var terminate = packageQuantity(cda) == packageRestriction.getPackageQuantity();
        return CreatePharmacyEffectuationOnPrescriptionType.builder()
            .withPrescriptionIdentifier(prescriptionId(cda))
            .withOrderIdentifier(order.getIdentifier())
            .withEffectuation(effectuation(cda))
            .withTerminate(terminate)
            .build();
    }

    static Integer packageQuantity(Document cda) throws XPathExpressionException, MapperException {
        var node = evalNode(cda, XPaths.packageQuantity);
        var unit = eval(node, "@unit");
        if (!"1".equals(unit)) {
            throw new MapperException("Unsupported quantity unit: " + unit);
        }
        var value = eval(node, "@value");
        return Utils.safeParsePositiveInt(value)
            .orElseThrow(() -> new MapperException("Package quantity must be a positive integer, was: %s".formatted(value)));
    }

    static CreatePharmacyEffectuationType effectuation(Document cda) throws XPathExpressionException, MapperException {
        var effectiveTime = eval(cda, XPaths.effectiveTime);
        var drug = drug(cda);

        return CreatePharmacyEffectuationType.builder()
            .withDateTime(Utils.parseEpsosTime(effectiveTime))
            .withPackageDispensed()
            .withPackageQuantity(packageQuantity(cda))
            .withPackageNumber(packageNumber())
            .withPackageSize(packageSize(cda))
            .withSubstitutedDrug(drug)
            .end()
            .withDeliverySite(OrganisationType.builder()
                .withName("Ry Apoteksudsalg")
                .withType("Apotek")
                .withIdentifier().withSource("CVR-P").withValue("1008648049").end()
                .build()) // TODO #190
            .build();
    }

    static DrugType drug(Document cda) throws XPathExpressionException {
        return DrugType.builder()
            .withDetailedDrugText(detailedDrugText(cda))
            .withATC(atc(cda))
            .withStrength(drugStrength(cda))
            .withSubstances(substances(cda))
            .build();
    }

    static SubstancesType substances(Document cda) throws XPathExpressionException {
        var ingredientNodes = evalNodeMany(cda, XPaths.activeIngredients);
        if (ingredientNodes.isEmpty()) {
            return null;
        }
        var b = SubstancesType.builder();
        for (var node : ingredientNodes) {
            var substanceName = eval(node, "pharm:ingredientSubstance/pharm:name");
            if (StringUtils.isNotBlank(substanceName)) {
                b.addActiveSubstance().withFreeText(substanceName);
            } else {
                var substanceCode = eval(node, "pharm:ingredientSubstance/pharm:code/@code");
                var substanceDisplayName = eval(node, "pharm:ingredientSubstance/pharm:code/@displayName");
                if (StringUtils.isNotBlank(substanceCode)) {
                    b.addActiveSubstance().withFreeText("%s %s".formatted(substanceCode, substanceDisplayName).trim());
                }
            }
        }
        var result = b.build();
        // if the data was weird (e.g. only substances with no name and no code) then we act as if there was no information.
        if (result.getActiveSubstance().isEmpty()) {
            return null;
        }
        return result;
    }

    static DrugStrengthType drugStrength(Document cda) throws XPathExpressionException {
        // [...]
        // Tilsvarende kan lægemidlet styrke angives for i Medicinpriser og “Stærke vitaminer og mineraler”,
        // og skal angives hvis der substitueres til andre typer af lægemidler. Styrken angives enten som
        // numerisk værdi og enhedskode samt evt. enheds-tekst og evt. komplet tekst, eller alternativt
        // som komplet tekst.
        // https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:opret_effektuering
        var drugStrengthFreeText = eval(cda, XPaths.drugStrengthFreeText);
        if (StringUtils.isBlank(drugStrengthFreeText)) {
            return null;
        }
        // FMK's DrugStrengthTextValueType has a max length of 20
        var value = StringUtils.truncate(drugStrengthFreeText, 20);
        return DrugStrengthType.builder()
            .withText()
            .withSource("Local")
            .withValue(value)
            .end()
            .build();
    }

    static ATCType atc(Document cda) throws XPathExpressionException {
        var node = evalNode(cda, XPaths.atcCode);
        if (node == null) {
            return null;
        }
        var codeSystem = eval(node, "@codeSystem");
        if (!Oid.ATC.value.equals(codeSystem)) {
            log.warn("Unexpected code system for ATC code: {}. Skipping ATC value.", codeSystem);
            return null;
        }
        var code = eval(node, "@code");
        var displayName = eval(node, "@displayName");

        return ATCType.builder()
            .withCode()
            .withSource("Local")
            .withValue(code)
            .end()
            .withText(displayName)
            .build();
    }

    static Node packagedMedicinalProduct(Document cda) throws XPathExpressionException {
        /// The Packaged Medicinal Product is found on the "containerPackagedProduct" element.  But there can be up to 3
        /// containerPackagedProduct elements nested inside each other.
        /// Our current understanding (2025-05-14) of the convoluted documentation in ART-DECOR
        /// https://art-decor.ehdsi.eu/publication/epsos-html-20250221T122200/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.30-2025-01-23T141901.html
        /// is that it is the innermost (most deeply nested) containerPackagedProduct which represents the
        /// packaged medicinal product.  I.e., the meaning of the element ".../manufacturedMaterial/asContent"
        /// is dependent on whether it has a "/containerPackagedProduct/asContent" subelement (and also on whether
        /// that subelement has a "/containerPackagedProduct/asContent" subelement).
        return evalNode(cda, XPaths.innermostContainerPackagedProduct);
    }

    static Node packageId(Document cda) throws XPathExpressionException {
        var pmp = packagedMedicinalProduct(cda);
        if (pmp == null) return null;
        return evalNode(pmp, "pharm:code");
    }

    static @NonNull String packagedMedicinalProductDescription(Document cda) throws XPathExpressionException {
        var pmp = packagedMedicinalProduct(cda);
        if (pmp == null) return "";
        /// "If present, the element SHALL contain a sufficiently detailed description of the prescribed/dispensed
        /// medicinal product/package. The description may contain information on the brand name, dose form, package
        /// (including its type or brand name), strength, etc."
        /// https://art-decor.ehdsi.eu/publication/epsos-html-20250221T122200/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.30-2025-01-23T141901.html
        var desc = eval(pmp, "pharm:name");
        // normalize whitespace
        return desc.replaceAll("\\s+", " ");
    }

    static String detailedDrugText(Document cda) throws XPathExpressionException {
        var drugName = eval(cda, XPaths.manufacturedMaterialName);
        var drugIdNode = evalNode(cda, XPaths.manufacturedMaterialCode);
        var drugId = drugIdNode == null
            ? "N/A"
            : "code: %s, code system: %s".formatted(
            eval(drugIdNode, "@code"),
            eval(drugIdNode, "@codeSystem"));

        var packageIdNode = packageId(cda);
        var packageId = packageIdNode == null
            ? "N/A"
            : "code: %s, code system: %s".formatted(
            eval(packageIdNode, "@code"),
            eval(packageIdNode, "@codeSystem"));

        var drugFormDisplayName = eval(cda, XPaths.drugFormCodeDisplayName);
        var drugForm = StringUtils.isBlank(drugFormDisplayName)
            ? "no form information"
            : drugFormDisplayName;

        var atcDisplayName = eval(cda, XPaths.atcCode + "/@displayName");

        var ingredients = new ArrayList<String>();
        for (var node : evalNodeMany(cda, XPaths.activeIngredients)) {
            ingredients.add(eval(node, "pharm:ingredientSubstance/pharm:name"));
        }

        // The "DetailedDrugText" element in FMK has a max size of 400 chars.
        return StringUtils.abbreviate(
            """
                %s, %s;
                %s;
                Lægemiddel-id: %s;
                Pakke-id: %s;
                %s;
                %s
                """.formatted(
                drugName,
                drugForm,
                packagedMedicinalProductDescription(cda),
                drugId,
                packageId,
                atcDisplayName,
                String.join(", ", ingredients)),
            400);
    }

    static PackageNumberType packageNumber() {
        return PackageNumberType.builder()
            .withSource("Local")
            .withValue("720000") // "Ukendt" https://wiki.fmk-teknik.dk/doku.php?id=fmk:generel:varenumre
            .build();
    }

    static PackageSizeType packageSize(Document cda) throws MapperException {
        try {
            var node = evalNode(cda, XPaths.contentQuantity);
            // "This element describes how many content items are present in the package.
            //
            // The preferred way is to provide the quantity in a coded form using the @unit and @value attributes.
            //
            // If no coded information is available and even the use of UCUM annotations is not sufficient and more
            // information is available within the national infrastructure, the originalText element can be used to
            // add additional information[...]"
            // https://art-decor.ehdsi.eu/publication/epsos-html-20250221T122200/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.30-2025-01-23T141901.html
            var unit = eval(node, "@unit");
            var rawValue = eval(node, "@value");
            var value = Utils.safeParsePositiveBigDecimal(rawValue)
                .orElseThrow(() -> new MapperException("Content quantity must be a positive number, was %s".formatted(rawValue)));

            String unitText;
            if ("1".equals(unit)) {
                var originalText = eval(node, "hl7:translation/hl7:originalText");
                unitText = StringUtils.isEmpty(originalText) ? "units" : originalText;
            } else {
                unitText = unit;
            }
            return PackageSizeType.builder()
                .withPackageSizeText("%s %s".formatted(value, unitText))
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException("Could not find package size information", e);
        }
    }

    public static String cdaId(Document cda) throws MapperException {
        try {
            var node = evalNode(cda, XPaths.cdaId);
            var root = eval(node, "@root");
            var ext = eval(node, "@extension");
            return StringUtils.isBlank(ext)
                ? root
                : root + "^^^" + ext;
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage());
        }
    }

    @WithSpan
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
        } finally {
            // Make Sonar happy
            xpath.remove();
        }
    }

    public static @NonNull OrderType getOpenedOrder(PrescriptionType prescription) throws MapperException {
        return prescription.getOrder()
            .stream()
            .filter(o -> Objects.equals(o.getStatus(), OrderStatusPredefinedType.EKSPEDITION_PÅBEGYNDT.value()))
            .findFirst()
            .orElseThrow(() -> new MapperException("No started effectuation found."));
    }

    public static AbortEffectuationRequestType abortEffectuationRequest(
        StartEffectuationRequestType startEffectuationRequest,
        StartEffectuationResponseType startEffectuationResponse
    ) throws MapperException {
        return AbortEffectuationRequestType.builder()
            .withPersonIdentifier(startEffectuationRequest.getPersonIdentifier())
            .withModifiedBy(startEffectuationRequest.getModifiedBy())
            .withOrganisationIdentifier(startEffectuationRequest.getOrganisationIdentifier())
            .withReportedBy(startEffectuationRequest.getReportedBy())
            .addPrescription()
            .withIdentifier(startEffectuationRequest.getPrescription().getFirst().getIdentifier())
            .withOrder(AbortEffectuationRequestType.Prescription.Order.builder()
                .withIdentifier(
                    DispensationMapper.getOpenedOrder(startEffectuationResponse.getPrescription().getFirst())
                        .getIdentifier())
                .build())
            .end()
            .build();
    }

    @WithSpan
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
        } finally {
            // Make Sonar happy
            xpath.remove();
        }
    }

    @WithSpan
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
