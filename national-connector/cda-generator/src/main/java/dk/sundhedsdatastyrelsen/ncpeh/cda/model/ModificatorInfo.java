package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ModificatorInfo {
    String authorisationId;
    String authorName;
    String organisationId;
    String organisationName;
    String countryCode;
    ModificatorKind kind;
}
