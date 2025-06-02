package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

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

}
