package dk.sds.ncp.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Product {
    @NonNull String name;
    @NonNull String description;
    @NonNull CdaCode formCode;
    @NonNull CdaCode packageCode;
    @NonNull CdaCode atcCode;
    @NonNull Size size;
}
