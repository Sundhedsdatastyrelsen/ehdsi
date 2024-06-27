package eu.europa.ec.sante.ehdsi.portal.util.cda.model;

public class Address {
    private final String streetAddressLine;
    private final String postalCode;
    private final String city;
    private final String country;

    public Address(String streetAddressLine, String postalCode, String city, String country) {
        this.streetAddressLine = streetAddressLine;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getStreetAddressLine() {
        return streetAddressLine;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
