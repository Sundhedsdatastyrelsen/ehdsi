package dk.nsp.epps.mock.model;

import java.util.ArrayList;
import java.util.List;

public class DocumentDetail {

    private String patientIdentifier;

    private List<ClinicalDocument> clinicalDocuments = new ArrayList<>();

    private boolean nextOfKin;

    public DocumentDetail() {
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public List<ClinicalDocument> getClinicalDocuments() { return clinicalDocuments;  }

    public void setClinicalDocuments(List<ClinicalDocument> clinicalDocuments) {
        this.clinicalDocuments = clinicalDocuments;
    }

    public boolean isNextOfKin() { return nextOfKin; }

    public void setNextOfKin(boolean nextOfKin) {
        this.nextOfKin = nextOfKin;
    }
}
