package dk.sds.ncp.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class Author {
    @NonNull CdaCode functionCode;
    @NonNull CdaId id;

    /**
     * "The author/time element represents the start time of the author’s participation in
     *  the creation of the clinical document. The author/time element SHALL be present."
     */
    @NonNull OffsetDateTime time;
    @NonNull Name name;
    @NonNull Organization organization;

    public String getTime() {
        return Utils.cdaDateTime(time);
    }
}
