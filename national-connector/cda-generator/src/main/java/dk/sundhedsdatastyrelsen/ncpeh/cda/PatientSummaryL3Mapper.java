package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SubstancesType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DrugMedicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ActiveIngredient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicationSummary;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicationSummary.MedicationItem;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class PatientSummaryL3Mapper {
    private PatientSummaryL3Mapper() {

    }

    /**
     * Map a patientSummary response from FMK to a CDA data model.
     *
     * @throws MapperException if something goes wrong
     */
    public static PatientSummaryL3 model(PatientSummaryInput input) {
        if (input == null) {
            throw new MapperException("Input is null");
        }
        if (input.patient() == null) {
            throw new MapperException("Patient is missing");
        }
        if (input.documentId() == null || input.documentId().isBlank()) {
            throw new MapperException("Document ID is missing");
        }

        var response = input.fmkMedicineCardResponse();
        var patient = input.patient();
        var preferredHP = input.preferredHealthProfessional();
        var documentId = input.documentId();
        var medications = medications(response);

        var patientSummaryBuilder = PatientSummaryL3.builder()
            .documentId(new CdaId(
                Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID,
                DocumentIdMapper.level3DocumentId(documentId)))
            .title(makeTitle(documentId, patient))
            .effectiveTime(OffsetDateTime.now())
            .patient(patient)
            .preferredHp(preferredHP)
            .medicationSummary(medicationSummary(medications));

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
        // #TODO: Figure out correct title format
        return String.format(
            "epSOS Patient Summary %s - %s", patientName, documentId);
    }

    private static MedicationSummary medicationSummary(List<DrugMedicationType> medications) {
        return MedicationSummary.builder()
            .items(medications.stream()
                .map(PatientSummaryL3Mapper::medicationItem)
                .toList())
            .build();
    }

    private static MedicationItem medicationItem(DrugMedicationType medication) {
        if (medication == null) {
            throw new MapperException("Medication is null");
        }

        /// We currently only look at drug medications, therefore null in prescriptionDosageText.
        ///#TODO: Check if it is okay not to include overridden dosage from prescription
        var dosage = DosageMapper.model(
            medication.getDosage(),
            null
        );

        if (dosage instanceof Dosage.Unstructured unstructured) {
            log.info("Dosage could not be mapped. Reason: {}", unstructured.getReason());
        }

        var routeOfAdministration = Optional.ofNullable(medication.getRouteOfAdministration())
            .filter(route -> route.getCode() != null)
            .map(route -> CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code(route.getCode().getValue())
                .displayName(route.getText())
                .build())
            .orElse(null);

        var activeIngredients = activeIngredients(medication);

        return MedicationItem.builder()
            .medicationId(medicationId(medication))
            .medicationStartTime(Optional.ofNullable(medication.getBeginEndDate())
                .map(d -> Utils.convertToOffsetDateTime(d.getTreatmentStartDate()))
                .orElse(null))
            .medicationEndTime(Optional.ofNullable(medication.getBeginEndDate())
                .map(d -> Utils.convertToOffsetDateTime(d.getTreatmentEndDate()))
                .orElse(null))
            .routeOfAdministration(routeOfAdministration)
            .dosage(dosage)
            .product(product(medication))
            .activeIngredients(activeIngredients.structured())
            .unstructuredActiveIngredients(activeIngredients.unstructured())
            .indicationText(indicationText(medication))
            .patientMedicationInstructions(patientMedicationInstructions(medication))
            .build();
    }

    private static CdaId medicationId(DrugMedicationType medication) {
        var id = medication.getIdentifier();

        return new CdaId(
            Oid.DK_FMK_MEDICATION,
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
            .collect(Collectors.joining("; "));

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

}
