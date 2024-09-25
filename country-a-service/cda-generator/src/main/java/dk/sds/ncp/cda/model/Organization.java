package dk.sds.ncp.cda.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class Organization {
    @NonNull CdaId id;
    @NonNull String name;
    @NonNull String telephoneNumber;
    Address address;
}
