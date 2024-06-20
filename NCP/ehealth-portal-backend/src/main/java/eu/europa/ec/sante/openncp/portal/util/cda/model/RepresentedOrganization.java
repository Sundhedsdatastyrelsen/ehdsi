package eu.europa.ec.sante.openncp.portal.util.cda.model;

public class RepresentedOrganization {

    private final Identifier identifier;
    private final String name;
    private final Telecom telecom;
    private final Address addr;

    public RepresentedOrganization (Identifier identifier, String name, Telecom telecom, Address addr) {
        this.identifier = identifier;
        this.name = name;
        this.telecom = telecom;
        this.addr = addr;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Telecom getTelecom() {
        return telecom;
    }

    public Address getAddr() {
        return addr;
    }
}
