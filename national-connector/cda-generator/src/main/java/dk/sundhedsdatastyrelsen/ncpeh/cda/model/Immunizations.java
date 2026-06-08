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
public class Immunizations {
    @NonNull
    @Builder.Default
    List<ImmunizationItem> items = List.of();

    @Value
    @Builder
    @With
    public static class ImmunizationItem {
        /**
         * CDA id for this immunization entry.
         * Use DDV VaccinationIdentifier as extension.
         */
        @NonNull
        CdaId immunizationId;

        /**
         * hl7:effectiveTime - actual vaccination/administration date.
         * DDV EffectuatedDateTime.
         */
        OffsetDateTime vaccinationDate;

        public String getVaccinationDate() {
            return vaccinationDate == null ? null : Utils.cdaDate(vaccinationDate);
        }

        /**
         * hl7:code / target disease or agent.
         * Example: rabies, influenza, covid-19.
         */
        CdaCode targetDiseaseCode;
        String targetDiseaseText;

        /**
         * hl7:consumable / eHDSI Immunization SSIDrug.
         */
        CdaCode drugId;
        @NonNull String name;
        String strength;
        @NonNull CdaCode formCode;
        @NonNull CdaCode atcCode;

        /**
         * hl7:entryRelationship Dose Number.
         * DDV VaccinationPlanItemIndex.
         */
        Integer doseNumber;

        /**
         * DDV/eHDSI useful narrative fields.
         */
        String batchNumber;
        String coverageDuration;
        String vaccinationPlanName;
        String vaccinationPlanItemDescription;

        /**
         * Performer/administering centre.
         * Can be filled from Created modificator.
         */
        String healthProfessionalIdentifier;
        String healthProfessionalName;
        String administeringCentreIdentifier;
        String administeringCentreName;

        /**
         * Local DDV status/metadata.
         */
        Integer vaccinationCredibility;
        Boolean confirmedByPrescriptionServer;
        Boolean activeStatus;
        Boolean previous;

        /**
         * Optional comments/reason text.
         */
        @NonNull
        @Builder.Default
        List<String> comments = List.of();

        /**
         * Optional references to adverse reactions elsewhere in CDA.
         */
        @NonNull
        @Builder.Default
        List<CdaId> adverseReactionIds = List.of();
    }
}
