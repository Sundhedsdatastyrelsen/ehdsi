package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SubstancesType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DrugMedicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ActiveIngredient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicalSummary;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicationItem;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Organization;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PatientSummaryL3Mapper {
    private  PatientSummaryL3Mapper(){

    }

    /**
     * Map a patientSummary response from FMK to a CDA data model.
     *
     * @throws MapperException if something goes wrong
     */
    public static PatientSummaryL3 model(PatientSummaryInput input) {
        var response = input.fmkMedicineCardResponse();
        var patient = input.patient();
        var documentId = input.documentId();
        var medications = medications(response);

        var patientSummaryBuilder = PatientSummaryL3.builder()
            .documentId(new CdaId(
                Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID,
                DocumentIdMapper.level3DocumentId(documentId)))
            .title(makeTitle(documentId, patient))
            .effectiveTime(OffsetDateTime.now())
            .patient(patient)
            //.author(author(prescription, input.authorAuthorizations()))
            .medicalSummary(medicalSummary(medications));

        return patientSummaryBuilder.build();

    }

    private static List<DrugMedicationType> medications(GetMedicineCardResponseType response) {
        if (response == null) {
            return List.of();
        }

        return response.getMedicineCard().stream()
            .filter(Objects::nonNull)
            .flatMap(card -> card.getDrugMedication().stream())
            .filter(Objects::nonNull)
            .toList();
    }

    public static String makeTitle(String documentId, Patient patient) {
        var patientName = patient.getName();
        return String.format(
            "eHDSI Patient Summary %s - %s", patientName, documentId);
    }


    private static MedicalSummary medicalSummary(List<DrugMedicationType> medications) {
        return MedicalSummary.builder()
            .entries(medications.stream()
                .map(PatientSummaryL3Mapper::medicationItem)
                .toList())
            .build();
    }

    private static MedicationItem medicationItem(DrugMedicationType medication) {
        var dosage = DosageMapper.model(
            medication.getDosage(),
            null
        );

        if (dosage instanceof Dosage.Unstructured unstructured) {
            log.info("Dosage could not be mapped. Reason: {}", unstructured.getReason());
        }

        var administrationRoute = Optional.ofNullable(medication.getRouteOfAdministration())
            .map(route -> CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code(route.getCode().getValue())
                .displayName(route.getText())
                .build())
            .orElse(null);

        return MedicationItem.builder()
            .medicationId(medicationId(medication))
            .medicationStartTime(Optional.ofNullable(medication.getBeginEndDate())
                .map(d -> Utils.convertToOffsetDateTime(d.getTreatmentStartDate()))
                .orElse(null))
            .medicationEndTime(Optional.ofNullable(medication.getBeginEndDate())
                .map(d -> Utils.convertToOffsetDateTime(d.getTreatmentEndDate()))
                .orElse(null))
            .administrationRoute(administrationRoute)
            .dosage(dosage)
            .product(product(medication)) //Might not be needed? #Todo Check this with @JV
            .activeIngredients(activeIngredients(medication).structured())
            .unstructuredActiveIngredients(activeIngredients(medication).unstructured())
            .indicationText(indicationText(medication))
            .patientMedicationInstructions(patientMedicationInstructions(medication))
            .build();
    }

    private static CdaId medicationId(DrugMedicationType medication) {
        long id = medication.getIdentifier();

        return new CdaId(
            Oid.DK_PATIENT_SUMMARY,
            Long.toString(id)
        );
    }

    private static String indicationText(DrugMedicationType medication) {
        var indication = medication.getIndication();
        if (indication == null) {
            return null;
        }
        return indication.getFreeText() != null
            ? indication.getFreeText()
            : indication.getText();
    }

    private static String patientMedicationInstructions(DrugMedicationType medication) {
        return Optional.ofNullable(medication.getDosage())
            .map(d -> d.getFreeText())
            .map(d -> d.getText())
            .orElse(null);
    }

    private static Product product(DrugMedicationType medication) {
        var drug = medication.getDrug();
        if (drug == null || drug.getName() == null) {
            return null;
        }

        var form = drug.getForm();
        if (form == null || form.getCode() == null || form.getText() == null) {
            return null;
        }

        var atc = drug.getATC();
        if (atc == null || atc.getCode() == null || atc.getText() == null) {
            return null;
        }

        var formCode = CdaCode.builder()
            .codeSystem(Oid.DK_LMS22)
            .code(form.getCode().getValue())
            .displayName(form.getText())
            .build();

        var atcCode = CdaCode.builder()
            .codeSystem(Oid.ATC)
            .code(atc.getCode().getValue())
            .displayName(atc.getText())
            .build();

        return Product.builder()
            .name(drug.getName())
            .strength(getSubstanceStrengthText(drug.getStrength()))
            .formCode(formCode)
            .atcCode(atcCode)
            .build();
    }

    /// Public for testing
    public static BigDecimal calculateInnerPackageAmount(BigDecimal numericalPackageSize, Integer numberOfSubPackages) {
        return numericalPackageSize
            .setScale(2, RoundingMode.UNNECESSARY)
            .divide(BigDecimal.valueOf(numberOfSubPackages == null || numberOfSubPackages == 0 ? 1 : numberOfSubPackages, 0), RoundingMode.UNNECESSARY);
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

    private static Organization authorOrganization(PrescriptionType prescription) {
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

//    private static Author author(PrescriptionType prescription, List<AuthorizationType> authorizations) {
//        var firstValidAuthorization = authorizations.stream()
//            .filter(a -> "1".equals(a.getAutorisationGyldig()))
//            .findFirst();
//        // https://www.nspop.dk/display/public/web/Autorisation has the list of education codes
//        // We should use the translation layer for these codes, but they are also used in L1, which isn't mapped in
//        // OpenNCP, so we have to map it in the code.
//        var functionCodeAndDisplay = FunctionCodeMapper.mapToFunctionCode(firstValidAuthorization.map(AuthorizationType::getUddannelsesKode)
//            // 0000 means 'Erstatningsautorisation' replacement authorization
//            .orElse("0000"));
//        var cdaFunctionCode = CdaCode.builder()
//            .codeSystem(Oid.ISCO)
//            .code(functionCodeAndDisplay.first())
//            .displayName(functionCodeAndDisplay.second())
//            .build();
//        var creator = getAuthorizedHealthcareProfessional(prescription);
//
//        return Author.builder()
//            .functionCode(cdaFunctionCode)
//            .specialization(getSpecialization(firstValidAuthorization.orElse(null)))
//            .time(offsetDateTime(prescription.getCreated().getDateTime()))
//            .id(new CdaId(Oid.DK_AUTHORIZATION_REGISTRY, creator.getAuthorisationIdentifier()))
//            .name(Name.fromFullName(creator.getName()))
//            .organization(authorOrganization(prescription))
//            .build();
//    }

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

    private static OrganisationType authorOrganizationXml(PrescriptionType prescription) {
        return prescription.getCreated()
            .getBy()
            .getContent()
            .stream()
            .filter(jaxb -> OrganisationType.class.isAssignableFrom(jaxb.getDeclaredType()))
            .map(jaxb -> (OrganisationType) jaxb.getValue())
            .findFirst()
            .orElseThrow(() -> new MapperException("Cannot find prescription creator organization"));
    }

    /// @throws MapperException if something can't be mapped
    public static @NonNull AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType getAuthorizedHealthcareProfessional(PrescriptionType prescriptionType) {
        return prescriptionType.getCreated()
            .getBy()
            .getContent()
            .stream()
            .filter(jaxb -> AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType.class.isAssignableFrom(jaxb.getDeclaredType()))
            .map(jaxb -> (AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType) jaxb.getValue())
            .findFirst()
            .orElseThrow(() -> new MapperException("Cannot find prescription creator information"));
    }

    private record ActiveIngredients(
        @NonNull List<ActiveIngredient> structured,
        @NonNull String unstructured
    ) {}

    private static ActiveIngredients activeIngredients(DrugMedicationType medication) {
        var drug = medication.getDrug();
        if (drug == null) {
            return new ActiveIngredients(List.of(), "");
        }

        var substances = Optional.ofNullable(drug.getSubstances())
            .map(SubstancesType::getActiveSubstance)
            .orElse(List.of());

        var structured = substances.stream()
            .map(PatientSummaryL3Mapper::activeIngredient)
            .filter(Objects::nonNull)
            .toList();

        var unstructured = structured.stream()
            .map(ActiveIngredient::getName)
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(", "));

        return new ActiveIngredients(structured, unstructured);
    }

    private static ActiveIngredient activeIngredient(ActiveSubstanceType substance) {
        var name = getSubstanceText(substance);
        if (name == null || name.isBlank()) {
            return null;
        }

        return ActiveIngredient.builder()
            .name(name)
            .quantity(null)
            .build();
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

//    /// @throws MapperException if something can't be mapped
//    public static String drugStrengthText(@NonNull PrescriptionType prescription) {
//        return Optional.ofNullable(prescription.getDrug())
//            .map(DrugType::getStrength)
//            .map(EPrescriptionL3Mapper::getSubstanceStrengthText)
//            .orElse(null);
//    }
//
//    /// @throws MapperException if something can't be mapped
//    public static String productDescription(@NonNull PrescriptionType prescription) {
//        // "[...]the element SHALL contain a sufficiently detailed description of the prescribed
//        // medicinal product/package. The description may contain information on the brand name,
//        // dose form, package (including its type or brand name), strength, etc."
//        // For example:
//        // Abasaglar KwikPen, injektionsvæske, opløsning i fyldt pen, 100 E/ml, 5 x 3 ml (1-80E/inj.)
//
//        return Stream.of(
//                prescription.getDrug().getName(),
//                Optional.of(prescription)
//                    .map(PrescriptionType::getDrug)
//                    .map(DrugType::getForm)
//                    .map(DrugFormType::getText)
//                    .orElse(null),
//                drugStrengthText(prescription),
//                Optional.of(prescription)
//                    .map(PrescriptionType::getPackageRestriction)
//                    .map(PackageRestrictionType::getPackageSize)
//                    .map(PackageSizeType::getPackageSizeText)
//                    .orElse(null))
//            .filter(Objects::nonNull)
//            .collect(Collectors.joining(", "));
//    }
}
