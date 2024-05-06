package dk.sds.ncp.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class Patient {
    @NonNull CdaId id;
    @NonNull Name name;
    @NonNull Address address;
    @NonNull CdaCode genderCode;
    @NonNull LocalDate birthTime;

    public String getBirthTime() {
        return Utils.cdaDate(birthTime);
    }
}
