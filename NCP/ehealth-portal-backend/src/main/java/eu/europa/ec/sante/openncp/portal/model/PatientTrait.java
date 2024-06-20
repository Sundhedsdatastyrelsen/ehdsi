package eu.europa.ec.sante.openncp.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PatientTrait extends PortalRequest {

    private String patientIdentifier;

    private String purposeOfUse;

    private String prescriptionId;

    private String dispensationPinCode;

    private boolean nextOfKin;

    public PatientTrait() {
        // Empty default constructor.
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public String getPurposeOfUse() {
        return purposeOfUse;
    }

    public void setPurposeOfUse(String purposeOfUse) {
        this.purposeOfUse = purposeOfUse;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDispensationPinCode() {
        return dispensationPinCode;
    }

    public void setDispensationPinCode(String dispensationPinCode) {
        this.dispensationPinCode = dispensationPinCode;
    }

    public boolean isNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(boolean nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("patientIdentifier", patientIdentifier)
                .append("purposeOfUse", purposeOfUse)
                .append("prescriptionId", prescriptionId)
                .append("dispensationPinCode", dispensationPinCode)
                .append("nextOfKin", nextOfKin)
                .toString();
    }
}
