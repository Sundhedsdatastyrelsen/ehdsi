package eu.europa.ec.sante.openncp.portal.util.cda.model;

public class CustodianInformation {
    private final String identifier;
    private final String name;
    private final String country;

    public CustodianInformation(String identifier, String name, String country) {
        this.identifier = identifier;
        this.name = name;
        this.country = country;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
