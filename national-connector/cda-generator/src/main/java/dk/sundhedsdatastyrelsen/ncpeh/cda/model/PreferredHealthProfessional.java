package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@Builder
@With
public class PreferredHealthProfessional {
    @NonNull Name name;
    @NonNull List<Telecom> telecoms;
    @NonNull Address address;
}
