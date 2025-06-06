package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugFormType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SubstancesType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DrugMedicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageSizeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ActiveIngredient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Author;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Organization;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PackageLayer;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PackageUnit;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class EPrescriptionL3Mapper {
    private EPrescriptionL3Mapper() {
    }

    /**
     * Map a prescription response from FMK to a CDA data model.
     */
    public static EPrescriptionL3 model(EPrescriptionL3Input input) throws MapperException {
        var response = input.fmkPrescriptionResponse();
        var prescriptionIndex = input.prescriptionIndex();
        var drugMedicationResponse = input.fmkDrugMedicationResponse();

        var prescription = response.getPrescription().get(prescriptionIndex);
        Optional<DrugMedicationType> medication = Optional.empty();
        if (drugMedicationResponse != null) {
            medication = drugMedicationResponse.getDrugMedication()
                .stream()
                .filter(dm -> prescription.getAttachedToDrugMedicationIdentifier().equals(dm.getIdentifier()))
                .findAny();
        }

        var prescriptionId = new CdaId(Oid.DK_FMK_PRESCRIPTION, Long.toString(prescription.getIdentifier()));

        var i = prescription.getIndication();
        var indicationText = i.getFreeText() != null ? i.getFreeText() : i.getText();
        var activeIngredients = getActiveIngredients(
            prescription.getDrug().getStrength(),
            prescription.getDrug().getSubstances());
        var prescriptionBuilder = EPrescriptionL3.builder()
            .documentId(new CdaId(Oid.DK_EPRESCRIPTION_REPOSITORY_ID, EPrescriptionDocumentIdMapper.level3DocumentId(prescriptionId.getExtension())))
            .title(makeTitle(response, prescription))
            .effectiveTime(OffsetDateTime.now())
            .patient(patient(response))
            .author(author(prescription, input.authorAuthorizations()))
            .signatureTime(OffsetDateTime.now())
            .parentDocumentId(prescriptionId)
            .prescriptionId(prescriptionId)
            .product(product(prescription, input.packageFormCode(), input.numberOfSubPackages(), input.manufacturerOrganizationName()))
            .packageQuantity((long) prescription.getPackageRestriction().getPackageQuantity())
            .substitutionAllowed(prescription.isSubstitutionAllowed())
            .indicationText(indicationText)
            .patientMedicationInstructions(prescription.getDosageText())
            .activeIngredients(activeIngredients.structured())
            .unstructuredActiveIngredients(activeIngredients.unstructured());

        if (medication.isPresent()) {
            var drugMedicationType = medication.get();
            var dosage = DosageMapper.model(drugMedicationType.getDosage());
            if (dosage instanceof Dosage.Unstructured unstructured) {
                log.info("Dosage could not be mapped. Reason: {}", unstructured.getReason());
            }
            prescriptionBuilder
                .medicationStartTime(Utils.convertToOffsetDateTime(drugMedicationType.getBeginEndDate()
                    .getTreatmentStartDate()))
                .dosage(DosageMapper.model(drugMedicationType.getDosage()))
                .medicationEndTime(Utils.convertToOffsetDateTime(drugMedicationType.getBeginEndDate()
                    .getTreatmentEndDate()));

            var administrationRoute = drugMedicationType.getRouteOfAdministration();
            if (administrationRoute != null) {
                var administrationRouteCdaCode = CdaCode.builder()
                    .codeSystem(Oid.DK_LMS11)
                    .code(administrationRoute.getCode().getValue())
                    .displayName(administrationRoute.getText())
                    .build();
                prescriptionBuilder.administrationRoute(administrationRouteCdaCode);
            }
        } else {
            prescriptionBuilder.dosage(new Dosage.Unstructured("No unstructured dosage text.", "No medication."));
        }

        return prescriptionBuilder.build();
    }

    public static String makeTitle(GetPrescriptionResponseType response, PrescriptionType prescription) {
        var patientName = response.getPatient().getPerson().getName();
        return String.format(
            "eHDSI ePrescription %s - %s", Name.fromFirstMiddleLast(patientName.getGivenName(), patientName.getMiddleName(), patientName.getSurname())
                .getFullName(), prescription.getIdentifier());
    }

    private static Product product(PrescriptionType prescription, String packageFormCodeRaw, Integer numberOfSubPackages, String manufacturer) {
        var drugId = prescription.getDrug().getIdentifier();
        var codedId = drugId != null ? CdaCode.builder()
            .codeSystem(Oid.DK_DRUG_ID)
            .code(String.valueOf(drugId.getValue()))
            .build() : null;
        var f = prescription.getDrug().getForm();
        var formCode = CdaCode.builder()
            .codeSystem(Oid.DK_LMS22)
            .code(f.getCode().getValue())
            .displayName(f.getText())
            .build();

        var packageNumber = prescription.getPackageRestriction().getPackageNumber().getValue();
        var packageCode = CdaCode.builder()
            .codeSystem(Oid.DK_VARENUMRE)
            .code(packageNumber)
            .build();
        var packageFormCode = packageFormCodeRaw != null ? CdaCode.builder()
            .codeSystem(Oid.DK_EMBALLAGETYPE)
            .code(packageFormCodeRaw)
            .build() : null;
        var ps = prescription.getPackageRestriction().getPackageSize();
        var subpackages = numberOfSubPackages == null || numberOfSubPackages == 0 ? 1 : numberOfSubPackages;

        var outerLayer = PackageLayer.builder()
            .unit(
                subpackages > 1 ?
                    new PackageUnit.WithCode("1") :
                    PackageUnitMapper.fromLms(ps.getUnitCode().getValue()))
            .amount(subpackages > 1 ? BigDecimal.valueOf(subpackages) : ps.getValue())
            .description(productDescription(prescription))
            .packageFormCode(packageFormCode)
            .packageCode(packageCode)
            .build();

        var innerLayer = subpackages > 1 ?
            PackageLayer.builder()
                .unit(PackageUnitMapper.fromLms(ps.getUnitCode().getValue()))
                .amount(calculateInnerPackageAmount(ps.getValue(), numberOfSubPackages))
                .wrappedIn(outerLayer)
                .build()
            : null;


        var atc = prescription.getDrug().getATC();
        var atcCode = CdaCode.builder()
            .codeSystem(Oid.ATC)
            .codeSystemVersion("2025-01")
            .code(atc.getCode().getValue())
            .displayName(atc.getText())
            .build();

        return Product.builder()
            .drugId(codedId)
            .name(prescription.getDrug().getName())
            .strength(drugStrengthText(prescription))
            .formCode(formCode)
            .innermostPackageLayer(innerLayer != null ? innerLayer : outerLayer)
            .atcCode(atcCode)
            .manufacturerOrganizationName(manufacturer)
            .build();
    }

    /// Public for testing
    public static BigDecimal calculateInnerPackageAmount(BigDecimal numericalPackageSize, Integer numberOfSubPackages) {
        return numericalPackageSize
            .setScale(2, RoundingMode.UNNECESSARY)
            .divide(BigDecimal.valueOf(numberOfSubPackages == null || numberOfSubPackages == 0 ? 1 : numberOfSubPackages, 0), RoundingMode.UNNECESSARY);
    }

    private static Patient patient(GetPrescriptionResponseType response) throws MapperException {
        var person = response.getPatient().getPerson();
        var id = person.getPersonIdentifier();
        if (!Objects.equals(id.getSource(), "CPR")) {
            throw new MapperException("Only CPR person ids supported, got: " + id.getSource());
        }

        var a = response.getPatient().getAddress();
        // There will be no address when the patient has address protection ("adressebeskyttelse")
        var address = a != null
            ? new Address(List.of(String.format("%s %s", a.getStreetName(), a.getStreetBuildingIdentifier())), a.getDistrictName(), a.getPostCodeIdentifier(), null)
            : null;

        var genderCodeBuilder = CdaCode.builder()
            .codeSystem(Oid.ADMINISTRATIVE_GENDER)
            .codeSystemVersion("913-20091020");

        switch (person.getGender()) {
            case FEMALE -> genderCodeBuilder.code("F").displayName("Female");
            case MALE -> genderCodeBuilder.code("M").displayName("Male");
        }

        var n = person.getName();

        return Patient.builder()
            .id(new CdaId(Oid.DK_CPR, id.getValue()))
            .address(address)
            .name(Name.fromFirstMiddleLast(n.getGivenName(), n.getMiddleName(), n.getSurname()))
            .genderCode(genderCodeBuilder.build())
            .birthTime(localDate(person.getBirthDate()))
            .build();
    }

    private static CdaId organizationId(OrganisationIdentifierType fmkOrgId) {
        // See OrganisationIdentifierPredefinedSourceType for most likely values (can theoretically be anything)
        var root = switch (fmkOrgId.getSource()) {
            case "SKS" -> Oid.DK_SKS;
            case "Yder" -> Oid.DK_YDER;
            case "EAN-Lokationsnummer" -> Oid.EAN;
            case "CVR" -> Oid.DK_CVR;
            case "SOR" -> Oid.DK_SOR;
            // remaining: CVR-P, Kommunekode, Udenlandsk
            // have no OID that we know of.
            default -> null;
        };
        if (root == null) {
            // when we don't have an OID matching the source then we use a generic OID
            // and put the source name into the extension:
            return new CdaId(Oid.DK_REGISTRIES, String.format("%s^%s", fmkOrgId.getSource(), fmkOrgId.getValue()));
        }
        return new CdaId(root, fmkOrgId.getValue());
    }

    private static Organization authorOrganization(PrescriptionType prescription) throws MapperException {
        var org = authorOrganizationXml(prescription);

        var id = organizationId(org.getIdentifier());
        Address address = null;
        if (!org.getAddressLine().isEmpty()) {
            var streetAddressLines = new LinkedList<String>();
            String city = null, postalCode = null;
            var postalCityPattern = Pattern.compile("(\\d{4}) (.+)");
            for (var line : org.getAddressLine()) {
                var m = postalCityPattern.matcher(line);
                if (m.matches()) {
                    postalCode = m.group(1);
                    city = m.group(2);
                } else {
                    streetAddressLines.add(line);
                }
            }
            address = new Address(streetAddressLines, city, postalCode, "DK");
        }

        return new Organization(id, org.getName(), org.getTelephoneNumber(), address);
    }

    private static Author author(PrescriptionType prescription, List<AuthorizationType> authorizations) throws MapperException {
        var firstValidAuthorization = authorizations.stream()
            .filter(a -> "1".equals(a.getAutorisationGyldig()))
            .findFirst();
        // https://www.nspop.dk/display/public/web/Autorisation has the list of education codes
        // We should use the translation layer for these codes, but they are also used in L1, which isn't mapped in
        // OpenNCP, so we have to map it in the code.
        var functionCode = FunctionCodeMapper.mapToFunctionCode(firstValidAuthorization.map(AuthorizationType::getUddannelsesKode)
            // 0000 means 'Erstatningsautorisation' replacement authorization
            .orElse("0000"));
        var cdaFunctionCode = CdaCode.builder()
            .codeSystem(Oid.ISCO)
            .code(functionCode)
            .build();
        var creator = getAuthorizedHealthcareProfessional(prescription);

        return Author.builder()
            .functionCode(cdaFunctionCode)
            .specialization(getSpecialization(firstValidAuthorization.orElse(null)))
            .time(offsetDateTime(prescription.getCreated().getDateTime()))
            .id(new CdaId(Oid.DK_AUTHORIZATION_REGISTRY, creator.getAuthorisationIdentifier()))
            .name(Name.fromFullName(creator.getName()))
            .organization(authorOrganization(prescription))
            .build();
    }

    private static CdaCode getSpecialization(AuthorizationType authorization) {
        if (authorization == null) {
            return null;
        }

        var specializations = Stream.of(
                Optional.ofNullable(authorization.getSpeciale1()),
                Optional.ofNullable(authorization.getSpeciale2()),
                Optional.ofNullable(authorization.getSpeciale3()))
            .filter(spe -> !spe.orElse("").trim().isEmpty())
            .map(Optional::get)
            .toList();

        // There is no specified coded format for specializations in eHDSI CDA, and no way to send more than one
        // specialization. So we send it over in an uncoded format. However, in order to display it on the receiver
        // side, we do need to specify a code system. So we make one up. This is not the ideal way of doing this, we
        // should ask for a code system from the EU and change the CDA format to support more than one specialization,
        // but that could take years, so we do this for now.
        return specializations.isEmpty() ? null : CdaCode.builder()
            .codeSystem(Oid.DK_AUTHORIZATION_REGISTRY_SPECIALIZATION)
            .code(Base64.getEncoder()
                .encodeToString(String.join(", ", specializations).getBytes(StandardCharsets.UTF_8)))
            .displayName(String.join(", ", specializations))
            .build();
    }

    /**
     * Converts an XML date (xs:date) to a LocalDate.
     */
    private static LocalDate localDate(XMLGregorianCalendar date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
    }

    /**
     * Converts an XML datetime (xs:dateTime) to an OffsetDateTime.
     */
    private static OffsetDateTime offsetDateTime(XMLGregorianCalendar dateTime) {
        return dateTime.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
    }

    private static OrganisationType authorOrganizationXml(PrescriptionType prescription) throws MapperException {
        return prescription.getCreated()
            .getBy()
            .getContent()
            .stream()
            .filter(jaxb -> OrganisationType.class.isAssignableFrom(jaxb.getDeclaredType()))
            .map(jaxb -> (OrganisationType) jaxb.getValue())
            .findFirst()
            .orElseThrow(() -> new MapperException("Cannot find prescription creator organization"));
    }

    public static @NonNull AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType getAuthorizedHealthcareProfessional(PrescriptionType prescriptionType) throws MapperException {
        return prescriptionType.getCreated()
            .getBy()
            .getContent()
            .stream()
            .filter(jaxb -> AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType.class.isAssignableFrom(jaxb.getDeclaredType()))
            .map(jaxb -> (AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType) jaxb.getValue())
            .findFirst()
            .orElseThrow(() -> new MapperException("Cannot find prescription creator information"));
    }

    private record ActiveIngredients(@NonNull List<ActiveIngredient> structured, @NonNull String unstructured) {
    }

    private static @NonNull ActiveIngredients getActiveIngredients(DrugStrengthType strength, SubstancesType substances) {
        if (substances == null
            || substances.getActiveSubstance() == null
            || substances.getActiveSubstance().stream().allMatch(ai -> getSubstanceText(ai) == null)) {
            return new ActiveIngredients(List.of(), "");
        }

        var structured = new ArrayList<ActiveIngredient>();
        // We can only assign a strength value to an active ingredient when there is exactly 1 active ingredient,
        // because the "strength" value from FMK is a combined value for all ingredients, and for combination drugs
        // it is given in free text, so there is no safe way to separate it out to the individual ingredients.
        if (substances.getActiveSubstance().size() == 1 && strength != null && strength.getUnitCode() != null) {
            var text = getSubstanceText(substances.getActiveSubstance().getFirst());
            var codedStrength = SubstanceUnitMapper.fromLms(strength.getUnitCode().getValue());
            if (text != null && codedStrength != null) {
                structured.add(ActiveIngredient.builder()
                    .name(text)
                    .quantity(ActiveIngredient.Quantity.builder()
                        .numerator(strength.getValue())
                        .numeratorUnit(codedStrength.numeratorUnit())
                        .denominator(codedStrength.denominator())
                        .denominatorUnit(codedStrength.denominatorUnit())
                        .translation(codedStrength.translation())
                        .build())
                    .build());
            }
        }

        // If the single element could not be mapped, or there were more than one, add them all as simple names.
        if (structured.isEmpty()) {
            structured.addAll(substances.getActiveSubstance().stream()
                .map(EPrescriptionL3Mapper::getSubstanceText)
                .filter(Objects::nonNull)
                .map(st -> ActiveIngredient.builder()
                    .name(st)
                    .build())
                .toList());
        }

        var unstructured = Stream.concat(
                Stream.of(getSubstanceStrengthText(strength)),
                substances.getActiveSubstance()
                    .stream()
                    .map(EPrescriptionL3Mapper::getSubstanceText))
            .filter(Objects::nonNull)
            .collect(Collectors.joining("; "));

        return new ActiveIngredients(structured, unstructured);
    }

    private static String getSubstanceText(ActiveSubstanceType substance) {
        if (substance == null) return null;
        if (substance.getSubstanceText() != null) return substance.getSubstanceText().getValue();
        if (substance.getText() != null) return substance.getText();
        if (substance.getFreeText() != null) return substance.getFreeText();
        return null;
    }

    private static String getSubstanceStrengthText(DrugStrengthType strength) {
        return Optional.ofNullable(strength)
            .map(DrugStrengthType::getText)
            .map(DrugStrengthTextType::getValue)
            .orElse(null);
    }

    public static String drugStrengthText(@NonNull PrescriptionType prescription) {
        return Optional.ofNullable(prescription.getDrug())
            .map(DrugType::getStrength)
            .map(EPrescriptionL3Mapper::getSubstanceStrengthText)
            .orElse(null);
    }

    public static String productDescription(@NonNull PrescriptionType prescription) {
        // "[...]the element SHALL contain a sufficiently detailed description of the prescribed
        // medicinal product/package. The description may contain information on the brand name,
        // dose form, package (including its type or brand name), strength, etc."
        // For example:
        // Abasaglar KwikPen, injektionsvæske, opløsning i fyldt pen, 100 E/ml, 5 x 3 ml (1-80E/inj.)

        return Stream.of(
                prescription.getDrug().getName(),
                Optional.of(prescription)
                    .map(PrescriptionType::getDrug)
                    .map(DrugType::getForm)
                    .map(DrugFormType::getText)
                    .orElse(null),
                drugStrengthText(prescription),
                Optional.of(prescription)
                    .map(PrescriptionType::getPackageRestriction)
                    .map(PackageRestrictionType::getPackageSize)
                    .map(PackageSizeType::getPackageSizeText)
                    .orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.joining(", "));
    }
}
