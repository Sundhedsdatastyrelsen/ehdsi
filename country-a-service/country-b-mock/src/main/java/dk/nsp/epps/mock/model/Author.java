package dk.nsp.epps.mock.model;

import java.util.List;

public class Author {

    String person;

    List<String> specialities;

    public Author(String person, List<String> specialties) {
        this.person = person;
        this.specialities = specialties;
    }

    public String getPerson() {
        return person;
    }

    public List<String> getSpecialities() {
        return specialities;
    }
}
