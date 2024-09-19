package dk.nsp.epps.mock.util.cda.model;

public class LegalAuthenticatorInformation {

    private final Identifier identifier;
    private final Address address;
    private final Telecom telecom;
    private final AssignedPerson assignedPerson;
    private final RepresentedOrganization representedOrganization;

    public LegalAuthenticatorInformation(Identifier identifier, Address address, Telecom telecom, AssignedPerson assignedPerson, RepresentedOrganization representedOrganization) {
        this.identifier = identifier;
        this.address = address;
        this.telecom = telecom;
        this.assignedPerson = assignedPerson;
        this.representedOrganization = representedOrganization;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Address getAddress() {
        return address;
    }

    public Telecom getTelecom() {
        return telecom;
    }

    public AssignedPerson getAssignedPerson() {
        return assignedPerson;
    }

    public RepresentedOrganization getRepresentedOrganization() {
        return representedOrganization;
    }
}
