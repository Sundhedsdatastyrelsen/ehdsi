package eu.europa.ec.sante.openncp.portal.model;

import java.util.ArrayList;
import java.util.List;

public class OrCDDocumentDetail {

    private String patientIdentifier;

    private List<OriginalClinicalDocument> clinicalDocuments = new ArrayList<>();

    private boolean nextOfKin;

    public OrCDDocumentDetail() {
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public List<OriginalClinicalDocument> getClinicalDocuments() {
        return clinicalDocuments;
    }

    public void setClinicalDocuments(List<OriginalClinicalDocument> clinicalDocuments) {
        this.clinicalDocuments = clinicalDocuments;
    }

    public boolean isNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(boolean nextOfKin) {
        this.nextOfKin = nextOfKin;
    }


}
