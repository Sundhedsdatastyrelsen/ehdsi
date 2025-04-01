package dk.sundhedsdatastyrelsen.ncpeh.cda.model;
import lombok.NonNull;
import lombok.Value;
@Value
public class Address {
 @NonNull Iterable<String> streetAddressLines;
 String city;
 String postalCode;
 String countryCode;
}
