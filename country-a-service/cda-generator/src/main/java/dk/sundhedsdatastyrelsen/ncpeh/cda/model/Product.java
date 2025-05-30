package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class Product {
    /// LMS drug id
    CdaCode drugId;
    @NonNull String name;
    String strength;
    @NonNull CdaCode formCode;
    @NonNull CdaCode atcCode;
    @NonNull PackageLayer packageInfo;
    String manufacturerOrganizationName;
}
