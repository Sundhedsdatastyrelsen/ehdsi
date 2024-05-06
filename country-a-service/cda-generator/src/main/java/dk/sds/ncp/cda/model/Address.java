package dk.sds.ncp.cda.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class Address {
    @NonNull Iterable<String> streetAddressLines;
    @NonNull String city;
    @NonNull String postalCode;
    String countryCode;
}
