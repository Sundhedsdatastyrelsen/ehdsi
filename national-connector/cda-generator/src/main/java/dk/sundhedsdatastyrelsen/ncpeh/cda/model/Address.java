package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.NonNull;
import lombok.Value;

import java.util.Objects;

@Value
public class Address {
    @NonNull Iterable<String> streetAddressLines;
    String city;
    String postalCode;
    String countryCode;

    /// Empty country code is an error.
    public String getCountryCode() {
        return Objects.equals(countryCode, "") ? null : countryCode;
    }
}
