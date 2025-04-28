package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class Author {
    @NonNull CdaCode functionCode;
    @NonNull CdaId id;
    CdaCode specialization;

    /**
     * "The author/time element represents the start time of the author’s participation in
     * the creation of the clinical document. The author/time element SHALL be present."
     */
    @NonNull OffsetDateTime time;
    @NonNull Name name;
    @NonNull Organization organization;

    public String getTime() {
        return Utils.cdaZonedDateTime(time);
    }
}
