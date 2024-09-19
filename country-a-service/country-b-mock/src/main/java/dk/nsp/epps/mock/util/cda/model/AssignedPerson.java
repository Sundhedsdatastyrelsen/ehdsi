package dk.nsp.epps.mock.util.cda.model;

import java.util.Set;

public class AssignedPerson {

    private final Set<String> givenNames;
    private final String familyName;
    private final String suffix;

    public AssignedPerson(Set<String> givenNames, String familyName, String suffix) {
        this.givenNames = givenNames;
        this.familyName = familyName;
        this.suffix = suffix;
    }

    public Set<String> getGivenNames() {
        return givenNames;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getSuffix() {
        return suffix;
    }
}
