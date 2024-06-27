package eu.europa.ec.sante.ehdsi.portal.util.cda.enums;

public enum Templates {

    EHDSI_EDISPENSATION("1.3.6.1.4.1.12559.11.10.1.3.1.1.2"),
    EHDSI_DISPENSATION_SECTION("1.3.6.1.4.1.12559.11.10.1.3.1.2.2"),
    EHDSI_SUPPLY("1.3.6.1.4.1.12559.11.10.1.3.1.3.3"),
    IHE_SUPPLY_ENTRY("1.3.6.1.4.1.19376.1.5.3.1.4.7.3"),
    CCD_SUPPLY_ACTIVITY("2.16.840.1.113883.10.20.1.34");

    private final String id;

    Templates(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
