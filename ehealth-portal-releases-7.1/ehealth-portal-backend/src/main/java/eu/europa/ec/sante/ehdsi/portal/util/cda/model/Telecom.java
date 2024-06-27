package eu.europa.ec.sante.ehdsi.portal.util.cda.model;

import java.util.Map;

public class Telecom {
    private final Map<String, String> telecoms;

    public Telecom(Map<String, String> telecoms) {
        this.telecoms = telecoms;
    }

    public Map<String, String> getTelecoms() {
        return telecoms;
    }
}
