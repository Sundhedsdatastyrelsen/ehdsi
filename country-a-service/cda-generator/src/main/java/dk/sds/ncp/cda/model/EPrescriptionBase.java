package dk.sds.ncp.cda.model;

import dk.sds.ncp.cda.Utils;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@SuperBuilder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class EPrescriptionBase {
    /**
     * Unique ID identifying the CDA document (not the prescription itself).
     */
    @NonNull CdaId documentId;
    @NonNull String title;
    @NonNull CdaId prescriptionId;

    /**
     * "[...] the date and time at which this document was created as an electronic document."
     */
    @NonNull OffsetDateTime effectiveTime;
    @NonNull Author author;

    @NonNull String indicationText;

    public String getEffectiveTime() {
        return Utils.cdaDateTime(effectiveTime);
    }
    public OffsetDateTime getEffectiveTimeOffsetDateTime() {
        return effectiveTime;
    }

}
