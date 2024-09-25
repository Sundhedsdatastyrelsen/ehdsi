package dk.nsp.epps.mock.util.cda.enums;

public enum CodeSystem {

    LOINC("LOINC", "2.16.840.1.113883.6.1"),
    ISCO("ISCO", "2.16.840.1.113883.2.9.6.2.7"),
    ACT_CLASS("ActClass", "2.16.840.1.113883.5.6");

    private String name;
    private String oid;

    CodeSystem(String name, String oid) {
        this.name = name;
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public String getOid() {
        return oid;
    }
}
