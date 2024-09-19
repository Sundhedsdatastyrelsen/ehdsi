package dk.nsp.epps.mock.model;

import java.util.ArrayList;
import java.util.List;

public class ClinicalDocumentRequest extends PortalRequest {

    PatientTrait patientTrait;
    FilterParameters filterParameters;

    List<String> classCodes = new ArrayList<>();

    public ClinicalDocumentRequest() {
        //  Empty Default Constructor
    }

    public PatientTrait getPatientTrait() {
        return patientTrait;
    }

    public void setPatientTrait(PatientTrait patientTrait) {
        this.patientTrait = patientTrait;
    }

    public List<String> getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(List<String> classCodes) {
        this.classCodes = classCodes;
    }

    public FilterParameters getFilterParameters() {
        return filterParameters;
    }

    public void setFilterParameters(FilterParameters filterParameters) {
        this.filterParameters = filterParameters;
    }
}
