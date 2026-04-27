package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
@With
public class MedicationSummary {
    /**
     * One entry per medication item in the patient summary medication section.
     */
    @NonNull
    List<MedicationItem> items;

    @Value
    @Builder
    @With
    public static class MedicationItem {
        /**
         * Identifier for this medication entry in the CDA.
         * Uses FMK medication id.
         */
        @NonNull
        CdaId medicationId;

        /**
         * Start/stop period for the medication, if known.
         */
        OffsetDateTime medicationStartTime;
        OffsetDateTime medicationEndTime;

        public String getMedicationStartTime() {
            return medicationStartTime == null ? null : Utils.cdaDate(medicationStartTime);
        }

        public String getMedicationEndTime() {
            return medicationEndTime == null ? null : Utils.cdaDate(medicationEndTime);
        }

        /**
         * The element specifies the route of administration using the EDQM route of administration vocabulary. A code must
         * be specified if the route is known, and the displayName attribute should be specified. If the route is unknown,
         * this element shall not be sent.
         */
        CdaCode routeOfAdministration;

        /**
         * Dosage/posology information.
         */
        @NonNull
        Dosage dosage;

        /**
         * Product details.
         */
        Product product;

        /**
         * Structured active ingredients when available.
         */
        @NonNull
        List<ActiveIngredient> activeIngredients;

        /**
         * Unstructured ingredient text fallback.
         */
        @NonNull
        String unstructuredActiveIngredients;

        /**
         * Free text indication of intent, if available.
         */
        String indicationText;

        /**
         * Optional patient-facing medication instructions.
         */
        String patientMedicationInstructions;

    }
}
