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
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.service.Utils;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
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

    public static final String EHDSI_EAN = "5790001392277";   // NSI, Udenlandsk Apotek via epSOS
    public static final String EHDSI_SOR = "397941000016003"; // NSI, Udenlandsk Apotek via epSOS

    static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.HL7, XmlNamespace.PHARM);

    static ModificatorPersonType authorPerson(Document cda) throws XPathExpressionException {
        var familyNames = xpath.evalStrings(XPaths.authorFamilyName, cda);
        var givenNames = xpath.evalStrings(XPaths.authorGivenName, cda);
        var allButLastName = Stream.concat(
                givenNames.stream(),
                familyNames.subList(0, familyNames.size() - 1).stream())
            .collect(Collectors.joining(" "));
        return ModificatorPersonType.builder()
            .withName()
            .withSurname(familyNames.getLast())
            .withGivenName(allButLastName)
            .end()
            .build();
    }

    /// @hidden public for testing.
    public static String authorRole(Document cda) throws XPathExpressionException, MapperException {
        var functionCode = xpath.evalString(XPaths.authorFunctionCode, cda);
        var functionCodeSystem = xpath.evalString(XPaths.authorFunctionCodeSystem, cda);

        if (!Oid.ISCO.value.equals(functionCodeSystem)) {
            throw new MapperException("Unexpected function code system: " + functionCodeSystem);
        }

        // These values are validated by FMK, and discrepancies with the soap header are ignored.
        // Ensure they match the following:
        //    Udenlandsk apoteker
        //    Udenlandsk apoteksansat
        //    Udenlandsk sundhedsmedarbejder
        return switch (functionCode) {
            case "2262" -> "Udenlandsk apoteker";
            case "3213" -> "Udenlandsk apoteksansat";
            // case "221" -> "Udenlandsk læge"; // Not ready yet
            default -> "Udenlandsk sundhedsmedarbejder";
        };
    }

    private static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }

    private static OrganisationType authorOrganization(Document cda, OrganisationIdentifierType placeholderId, String type) throws XPathExpressionException {
        var addressLines = new ArrayList<>(xpath.evalStrings(XPaths.authorOrgAddressLine, cda));
        var postalCode = xpath.evalString(XPaths.authorOrgPostalCode, cda);
        var city = xpath.evalString(XPaths.authorOrgCity, cda);
        var state = xpath.evalString(XPaths.authorOrgState, cda);
        var country = xpath.evalString(XPaths.authorOrgCountry, cda);
        if (notBlank(postalCode)) addressLines.add(postalCode);
        if (notBlank(city)) addressLines.add(city);
        if (notBlank(state)) addressLines.add(state);
        if (notBlank(country)) addressLines.add(country);

        String email = null;
        String telephone = null;
        var telecoms = xpath.evalNodes(XPaths.authorOrgTelecom, cda)
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

        var name = xpath.evalString(XPaths.authorOrgName, cda);
        var b = OrganisationType.builder()
            .withIdentifier(placeholderId)
            .withName(StringUtils.isBlank(name) ? "(ukendt)" : name)
            .withType(type) // capitalisation matters!
            .addAddressLine(addressLines);

        if (email != null) b.withEmailAddress(email);
        if (telephone != null) b.withTelephoneNumber(telephone);

        return b.build();
    }

    static OrganisationType pharmacyEan(Document cda) throws XPathExpressionException {
        // When the type is "Apotek" we need to use EAN.  For modificator elements it needs to be "Apotek", hence EAN.
        return authorOrganization(
            cda, OrganisationIdentifierType.builder()
                .withSource(OrganisationIdentifierPredefinedSourceType.EAN_LOKATIONSNUMMER.value())
                .withValue(EHDSI_EAN)
                .build(),
            "Apotek");
    }

    static OrganisationType pharmacySor(Document cda) throws XPathExpressionException {
        // To use SOR the type has to be "apotek" (lower case). For delivery site, we must use SOR, hence "apotek".
        return authorOrganization(
            cda, OrganisationIdentifierType.builder()
                .withSource(OrganisationIdentifierPredefinedSourceType.SOR.value())
                .withValue(EHDSI_SOR)
                .build(),
            "apotek");
    }

    public static long prescriptionId(Document cda) throws XPathExpressionException, MapperException {
        var id = xpath.evalNode(XPaths.inFulfillmentOfId, cda);
        var root = xpath.evalString("@root", id);
        if (!Oid.DK_FMK_PRESCRIPTION.value.equals(root)) {
            throw new MapperException("Unknown prescription id type: " + root);
        }
        var ext = xpath.evalString("@extension", id);
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
        return CreatePharmacyEffectuationOnPrescriptionType.builder()
            .withPrescriptionIdentifier(prescriptionId(cda))
            .withOrderIdentifier(order.getIdentifier())
            .withEffectuation(effectuation(cda))
            .withTerminate(shouldTerminate(cda, startEffectuationResponse))
            .build();
    }

    static boolean shouldTerminate(Document cda, StartEffectuationResponseType startEffectuationResponse) { //NOSONAR
        // In DK, the pharmacist chooses whether to close the prescription or leave it open for more dispensations.
        // This is not supported in ePrescription and eDispensation, so we close all prescriptions.
        // If the patient needs more medicine, they will have to call their doctor to reopen or recreate the prescription.
        // But that's better than leaving all prescriptions open.

        // It should be possible, by setting up some constraints (no change in package type, no change in medicine form
        // or strength, at least some percentage of the medicine remains, etc), to decide automatically whether to leave
        // a prescription open or not. But whether we should do that is still being discussed. And even then, when
        // the constraints don't hold, we should still terminate.
        return true; //NOSONAR this is likely to change.
    }

    static Integer packageQuantity(Document cda) throws XPathExpressionException, MapperException {
        var node = xpath.evalNode(XPaths.packageQuantity, cda);
        var unit = xpath.evalString("@unit", node);
        if (!"1".equals(unit)) {
            throw new MapperException("Unsupported quantity unit: " + unit);
        }
        var value = xpath.evalString("@value", node);
        return Utils.safeParsePositiveInt(value)
            .orElseThrow(() -> new MapperException("Package quantity must be a positive integer, was: %s".formatted(value)));
    }

    static CreatePharmacyEffectuationType effectuation(Document cda) throws XPathExpressionException, MapperException {
        var effectiveTime = xpath.evalString(XPaths.effectiveTime, cda);
        var drug = drug(cda);

        return CreatePharmacyEffectuationType.builder()
            .withDateTime(Utils.parseEpsosTime(effectiveTime))
            .withPackageDispensed()
            .withPackageQuantity(packageQuantity(cda))
            .withPackageNumber(packageNumber())
            .withPackageSize(packageSize(cda))
            .withSubstitutedDrug(drug)
            .end()
            .withDeliverySite(pharmacySor(cda))
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
        var ingredientNodes = xpath.evalNodes(XPaths.activeIngredients, cda);
        if (ingredientNodes.isEmpty()) {
            return null;
        }
        var b = SubstancesType.builder();
        for (var node : ingredientNodes) {
            var substanceName = xpath.evalString("pharm:ingredientSubstance/pharm:name", node);
            if (StringUtils.isNotBlank(substanceName)) {
                b.addActiveSubstance().withFreeText(substanceName);
            } else {
                var substanceCode = xpath.evalString("pharm:ingredientSubstance/pharm:code/@code", node);
                var substanceDisplayName = xpath.evalString("pharm:ingredientSubstance/pharm:code/@displayName", node);
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
        var drugStrengthFreeText = xpath.evalString(XPaths.drugStrengthFreeText, cda);
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
        var node = xpath.evalNode(XPaths.atcCode, cda);
        if (node == null) {
            return null;
        }
        var codeSystem = xpath.evalString("@codeSystem", node);
        if (!Oid.ATC.value.equals(codeSystem)) {
            log.warn("Unexpected code system for ATC code: {}. Skipping ATC value.", codeSystem);
            return null;
        }
        var code = xpath.evalString("@code", node);
        var displayName = xpath.evalString("@displayName", node);

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
        return xpath.evalNode(XPaths.innermostContainerPackagedProduct, cda);
    }

    static Node packageId(Document cda) throws XPathExpressionException {
        var pmp = packagedMedicinalProduct(cda);
        if (pmp == null) return null;
        return xpath.evalNode("pharm:code", pmp);
    }

    static @NonNull String packagedMedicinalProductDescription(Document cda) throws XPathExpressionException {
        var pmp = packagedMedicinalProduct(cda);
        if (pmp == null) return "";
        /// "If present, the element SHALL contain a sufficiently detailed description of the prescribed/dispensed
        /// medicinal product/package. The description may contain information on the brand name, dose form, package
        /// (including its type or brand name), strength, etc."
        /// https://art-decor.ehdsi.eu/publication/epsos-html-20250221T122200/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.30-2025-01-23T141901.html
        var desc = xpath.evalString("pharm:name", pmp);
        // normalize whitespace
        return desc.replaceAll("\\s+", " ");
    }

    static String detailedDrugText(Document cda) throws XPathExpressionException {
        var drugName = xpath.evalString(XPaths.manufacturedMaterialName, cda);
        var drugIdNode = xpath.evalNode(XPaths.manufacturedMaterialCode, cda);
        var drugId = drugIdNode == null
            ? "N/A"
            : "code: %s, code system: %s".formatted(
            xpath.evalString("@code", drugIdNode),
            xpath.evalString("@codeSystem", drugIdNode));

        var packageIdNode = packageId(cda);
        var packageId = packageIdNode == null
            ? "N/A"
            : "code: %s, code system: %s".formatted(
            xpath.evalString("@code", packageIdNode),
            xpath.evalString("@codeSystem", packageIdNode));

        var drugFormDisplayName = xpath.evalString(XPaths.drugFormCodeDisplayName, cda);
        var drugForm = StringUtils.isBlank(drugFormDisplayName)
            ? "no form information"
            : drugFormDisplayName;

        var atcDisplayName = xpath.evalString(XPaths.atcCode + "/@displayName", cda);

        var ingredients = new ArrayList<String>();
        for (var node : xpath.evalNodes(XPaths.activeIngredients, cda)) {
            ingredients.add(xpath.evalString("pharm:ingredientSubstance/pharm:name", node));
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
            var node = xpath.evalNode(XPaths.contentQuantity, cda);
            // "This element describes how many content items are present in the package.
            //
            // The preferred way is to provide the quantity in a coded form using the @unit and @value attributes.
            //
            // If no coded information is available and even the use of UCUM annotations is not sufficient and more
            // information is available within the national infrastructure, the originalText element can be used to
            // add additional information[...]"
            // https://art-decor.ehdsi.eu/publication/epsos-html-20250221T122200/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.30-2025-01-23T141901.html
            var unit = xpath.evalString("@unit", node);
            var rawValue = xpath.evalString("@value", node);
            var value = Utils.safeParsePositiveBigDecimal(rawValue)
                .orElseThrow(() -> new MapperException("Content quantity must be a positive number, was %s".formatted(rawValue)));

            String unitText;
            if ("1".equals(unit)) {
                var originalText = xpath.evalString("hl7:translation/hl7:originalText", node);
                unitText = StringUtils.isEmpty(originalText) ? "units" : originalText;
            } else {
                unitText = unit;
            }
            return PackageSizeType.builder()
                .withPackageSizeText("%s %s".formatted(value, unitText))
                .build();
        } catch (XmlException e) {
            throw new MapperException("Could not find package size information", e);
        }
    }

    public static String cdaId(Document cda) throws MapperException {
        try {
            var node = xpath.evalNode(XPaths.cdaId, cda);
            var root = xpath.evalString("@root", node);
            var ext = xpath.evalString("@extension", node);
            return StringUtils.isBlank(ext)
                ? root
                : root + "^^^" + ext;
        } catch (XmlException e) {
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
                    obf.createModificatorTypeOrganisation(pharmacyEan(cda))
                ).end()
                .withOrderedAtPharmacy(pharmacyEan(cda))
                .withPrescription(startEffectuationRequestPrescription(cda))
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage());
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
                    obf.createModificatorTypeOrganisation(pharmacyEan(cda))
                ).end()
                .addPrescription(prescription(cda, startEffectuationResponse))
                .build();
        } catch (XPathExpressionException e) {
            throw new MapperException(e.getMessage(), e);
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
                    obf.createModificatorTypeOrganisation(pharmacyEan(cda))
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
