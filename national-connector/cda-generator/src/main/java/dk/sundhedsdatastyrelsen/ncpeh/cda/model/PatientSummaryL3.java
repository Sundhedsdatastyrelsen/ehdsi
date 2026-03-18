package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;

@Value
@Builder
@With
public class PatientSummaryL3 {
    /**
     * Unique ID identifying the CDA document (not the summary itself).
     */
    @NonNull CdaId documentId;
    @NonNull String title;

    /**
     * "[...] the date and time at which this document was created as an electronic document."
     */
    @NonNull OffsetDateTime effectiveTime;

    public String getEffectiveTime() {
        return Utils.cdaZonedDateTime(effectiveTime);
    }

    @NonNull Patient patient;

    /// Egen læge
    /// Might be null if we don't have the information.
    PreferredHealthProfessional preferredHp;
}
