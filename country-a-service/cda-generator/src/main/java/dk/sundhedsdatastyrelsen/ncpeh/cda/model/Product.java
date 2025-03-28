package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Product {
    /// LMS drug id
    CdaCode drugId;
    @NonNull String name;
    String description;
    @NonNull CdaCode formCode;
    CdaCode packageCode;
    CdaCode packageFormCode;
    @NonNull CdaCode atcCode;
    @NonNull Size size;
}
