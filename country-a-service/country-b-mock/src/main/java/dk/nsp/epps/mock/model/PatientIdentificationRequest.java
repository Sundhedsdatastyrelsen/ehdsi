package dk.nsp.epps.mock.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PatientIdentificationRequest extends PortalRequest {

    private PatientIdentityTrait patientIdentityTrait;

    private NextOfKinTrait nextOfKinTrait;

    public PatientIdentificationRequest() {
        //  Default Constructor
    }

    public PatientIdentityTrait getPatientIdentityTrait() {
        return patientIdentityTrait;
    }

    public void setPatientIdentityTrait(PatientIdentityTrait patientIdentityTrait) {
        this.patientIdentityTrait = patientIdentityTrait;
    }

    public NextOfKinTrait getNextOfKinTrait() {
        return nextOfKinTrait;
    }

    public void setNextOfKinTrait(NextOfKinTrait nextOfKinTrait) {
        this.nextOfKinTrait = nextOfKinTrait;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("patientIdentityTrait", patientIdentityTrait)
                .append("nextOfKinTrait", nextOfKinTrait)
                .toString();
    }
}
