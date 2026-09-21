package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ActiveIngredient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicationSummary;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Immunizations;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryPdf;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PdfField;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class PatientSummaryPdfMapper {
    private PatientSummaryPdfMapper() {
    }

    /**
     * Map a L3 model to a L1 Model, consisting of PDF Fields to be written on a PDF page
     *
     * @param dataModel - A L3 Datamodel
     * @return A list of PdfField objects, consisting of Coordinates and an array of strings intended as seperate lines for each string
     */
    public static PatientSummaryPdf map(PatientSummaryL3 dataModel) {
        return new PatientSummaryPdf(List.of(
            field(titleLines(dataModel)),
            field(patientLines(dataModel.getPatient())),
            field(medicationLines(dataModel.getMedicationSummary())),
            field(allergyLines()),
            field(immunizationLines(dataModel.getImmunizations())),
            field(surgeryLines()),
            field(pastIllnessLines()),
            field(activeProblemLines()),
            field(medicalDeviceLines()),
            field(healthMaintenanceCarePlanLines()),
            field(functionalStatusLines()),
            field(socialHistoryLines()),
            field(pregnancyHistoryLines()),
            field(vitalSignsLines()),
            field(codedResultsLines()),
            field(advanceDirectivesLines())
            //field(authorLines((dataModel.getAuthor())),40),
            //field(List.of(String.format("ID: %s", dataModel.getPrescriptionId().getExtension())),40)
        ));
    }

    private static final int DEFAULT_WRAP_LENGTH = 75;
    private static final String lineSpacer = "----------------------------------------";

    // Overload
    private static PdfField field(List<String> lines) {
        return field(lines, DEFAULT_WRAP_LENGTH);
    }

    private static PdfField field(List<String> lines, int wrapLength) {
        return new PdfField(0, 0, lines, wrapLength);
    }

    private static List<String> titleLines(PatientSummaryL3 dataModel) {
        return List.of("Patient Summary " + dataModel.getPatient().getName().getFullName());
    }

    private static List<String> patientLines(Patient patient) {
        var lines = new ArrayList<String>();

        lines.add("PATIENT");
        lines.add(lineSpacer);

        var cpr = patient.getId().getExtension();
        if (cpr != null && cpr.length() >= 10) {
            cpr = cpr.substring(0, 6) + "-" + cpr.substring(6, 10);
        }

        lines.add("Name: " + patient.getName().getFullName());
        lines.add("CPR: " + cpr);

        if (patient.getBirthTime() != null) {
            lines.add("Date of birth: " + formatCdaDate(patient.getBirthTime()));
        }

        if (patient.getAddress() != null) {
            for (var addressLine : patient.getAddress().getStreetAddressLines()) {
                addToListIfNotNullOrEmpty(lines, addressLine);
            }

            addToListIfNotNullOrEmpty(
                lines,
                constructPostalCityLine(
                    patient.getAddress().getPostalCode(),
                    patient.getAddress().getCity()
                )
            );

            addToListIfNotNullOrEmpty(
                lines,
                patient.getAddress().getCountryCode()
            );
        }

        return lines;
    }

    private static String constructPostalCityLine(String postalCode, String cityName) {
        var finalLine = "";
        if (postalCode != null && !postalCode.isEmpty()) {
            finalLine += postalCode + " ";
        }
        if (cityName != null && !cityName.isEmpty()) {
            finalLine += cityName;
        }
        return finalLine.trim();
    }

    private static void addToListIfNotNullOrEmpty(List<String> list, String value) {
        if (value != null && !value.isEmpty()) {
            list.add(value);
        }
    }

    private static List<String> medicationLines(MedicationSummary medicationSummary) {
        var medicationLines = new ArrayList<String>();

        medicationLines.add("MEDICATION SUMMARY");
        medicationLines.add(lineSpacer);

        if (medicationSummary == null || medicationSummary.getItems().isEmpty()) {
            medicationLines.add("The patient has no current or recent medications according to our data");
            return medicationLines;
        }

        var items = medicationSummary.getItems();

        for (var i = 0; i < items.size(); i++) {
            var medication = items.get(i);

            medicationLines.add(medicationHeading(i + 1, medication));

            if (medication.getMedicationStartTime() != null) {
                medicationLines.add("Start: " + formatCdaDate(medication.getMedicationStartTime()));
            }

            if (medication.getMedicationEndTime() != null) {
                medicationLines.add("End: " + formatCdaDate(medication.getMedicationEndTime()));
            }

            addToListIfNotNullOrEmpty(
                medicationLines, prefixed(
                    "Form: ", medication.getProduct() != null ? medication.getProduct()
                        .getFormCode()
                        .getDisplayName() : null)
            );

            addToListIfNotNullOrEmpty(
                medicationLines, prefixed(
                    "Route: ", medication.getRouteOfAdministration() != null ? medication.getRouteOfAdministration()
                        .getDisplayName() : null)
            );

            addToListIfNotNullOrEmpty(medicationLines, prefixed("Active ingredient(s): ", activeIngredients(medication))
            );

            addToListIfNotNullOrEmpty(medicationLines, prefixed("Indication: ", medication.getIndicationText())
            );

            addToListIfNotNullOrEmpty(
                medicationLines, prefixed(
                    "Dosage: ", medication.getDosage()
                        .getUnstructuredText())
            );

            addToListIfNotNullOrEmpty(medicationLines, prefixed("Instructions: ", medication.getPatientMedicationInstructions())
            );

            medicationLines.add("");
        }

        medicationLines.add("Data source: FMK 1.4.6");

        return medicationLines;
    }

    private static String medicationHeading(int number, MedicationSummary.MedicationItem medication
    ) {
        if (medication.getProduct() == null) {
            return number + ". Unknown medication";
        }

        var product = medication.getProduct();
        var result = number + ". " + product.getName();

        if (product.getStrength() != null && !product.getStrength().isBlank()) {
            result += " " + product.getStrength();
        }

        return result;
    }

    private static List<String> immunizationLines(Immunizations immunizations) {
        var lines = new ArrayList<String>();

        lines.add("IMMUNIZATIONS");
        lines.add(lineSpacer);

        if (immunizations == null || immunizations.getItems().isEmpty()) {
            lines.add("No information available.");
            return lines;
        }

        var items = immunizations.getItems();

        for (var i = 0; i < items.size(); i++) {
            var immunization = items.get(i);

            lines.add(immunizationHeading(i + 1, immunization));

            addToListIfNotNullOrEmpty(lines, prefixed("Date: ", immunization.getFriendlyVaccinationDate()));

            addToListIfNotNullOrEmpty(lines, prefixed("Target disease: ", targetDisease(immunization)));

            addToListIfNotNullOrEmpty(
                lines, prefixed(
                    "Form: ", immunization.getFormCode() != null ? immunization.getFormCode()
                        .getDisplayName() : null));

            addToListIfNotNullOrEmpty(
                lines, prefixed(
                    "ATC: ", immunization.getAtcCode() != null ? immunization.getAtcCode()
                        .getDisplayName() : null));

            if (immunization.getDoseNumber() != null) {
                lines.add("Dose number: " + immunization.getDoseNumber());
            }

            addToListIfNotNullOrEmpty(lines, prefixed("Batch number: ", immunization.getBatchNumber()));

            addToListIfNotNullOrEmpty(lines, prefixed("Vaccination plan: ", immunization.getVaccinationPlanName()));

            addToListIfNotNullOrEmpty(lines, prefixed("Plan item: ", immunization.getVaccinationPlanItemDescription()));

            addToListIfNotNullOrEmpty(lines, prefixed("Coverage duration: ", immunization.getCoverageDuration()));

            addToListIfNotNullOrEmpty(lines, prefixed("Performed by: ", immunization.getPerformerName()));

            addToListIfNotNullOrEmpty(lines, prefixed("Organization: ", immunization.getPerformerOrganizationName()));

            for (var comment : immunization.getComments()) {
                addToListIfNotNullOrEmpty(lines, prefixed("Comment: ", comment));
            }

            lines.add("");
        }

        lines.add("Data source: DDV 1.4.6");

        return lines;
    }

    private static List<String> allergyLines() {
        return List.of(
            "ALLERGIES",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> surgeryLines() {
        return List.of(
            "LIST OF SURGERIES",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> pastIllnessLines() {
        return List.of(
            "HISTORY OF PAST ILLNESSES",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> activeProblemLines() {
        return List.of(
            "ACTIVE PROBLEMS",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> medicalDeviceLines() {
        return List.of(
            "MEDICAL DEVICES",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> healthMaintenanceCarePlanLines() {
        return List.of(
            "HEALTH MAINTENANCE CARE PLAN",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> functionalStatusLines() {
        return List.of(
            "FUNCTIONAL STATUS",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> socialHistoryLines() {
        return List.of(
            "SOCIAL HISTORY",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> pregnancyHistoryLines() {
        return List.of(
            "PREGNANCY HISTORY",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> vitalSignsLines() {
        return List.of(
            "VITAL SIGNS",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> codedResultsLines() {
        return List.of(
            "CODED RESULTS",
            lineSpacer,
            "No information available."
        );
    }

    private static List<String> advanceDirectivesLines() {
        return List.of(
            "ADVANCE DIRECTIVES",
            lineSpacer,
            "No information available."
        );
    }

    private static String immunizationHeading(int number, Immunizations.ImmunizationItem immunization) {
        var name = immunization.getName();

        if (name == null || name.isBlank()) {
            name = "Unknown immunization";
        }

        var result = number + ". " + name;

        if (immunization.getStrength() != null && !immunization.getStrength().isBlank()) {
            result += " " + immunization.getStrength();
        }

        return result;
    }

    private static String targetDisease(Immunizations.ImmunizationItem immunization) {
        if (immunization.getTargetDiseaseText() != null && !immunization.getTargetDiseaseText().isBlank()) {
            return immunization.getTargetDiseaseText();
        }

        if (immunization.getTargetDiseaseCode() != null) {
            return immunization.getTargetDiseaseCode().getDisplayName();
        }

        return null;
    }

    private static String activeIngredients(MedicationSummary.MedicationItem medication) {
        if (!medication.getActiveIngredients().isEmpty()) {

            return medication.getActiveIngredients().stream()
                .map(ActiveIngredient::getName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(", "));
        }

        return medication.getUnstructuredActiveIngredients();
    }

    private static String prefixed(String prefix, String value) {
        return value == null || value.isBlank() ? null : prefix + value;
    }

    private static String formatCdaDate(String date) {
        if (date == null || date.length() < 8) {
            return date;
        }

        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }
}
