package eu.europa.ec.sante.ehdsi.portal.controller;

public enum ClinicalDocumentEnum {

    DISPENSE_MEDICATION_CLASS_CODE("60593-1", "Medication Dispensed", "2.16.840.1.113883.6.1", "ED"),
    DISPENSE_DISCARD_MEDICATION_CLASS_CODE("DISCARD-60593-1", "Discarded Medication Dispensed", "2.16.840.1.113883.6.1", "ED"),
    PRESCRIPTION_CLASS_CODE("57833-6", "Prescription for Medication", "2.16.840.1.113883.6.1", "EP"),
    PATIENT_SUMMARY_CLASS_CODE("60591-5", "Patient Summary", "2.16.840.1.113883.6.1", "PS"),
    ORCD_HOSPITAL_DISCHARGE_REPORTS_CLASS_CODE("34105-7", "Hospital Discharge Summary", "2.16.840.1.113883.6.1", "ORCD"),
    ORCD_MEDICAL_IMAGING_REPORTS_CLASS_CODE("18748-4", "Diagnostic Imaging Study", "2.16.840.1.113883.6.1", "ORCD"),
    ORCD_MEDICAL_IMAGES_CLASS_CODE("x-clinical-image", "Medical Images", "2.16.840.1.113883.6.1", "ORCD"),
    ORCD_LABORATORY_RESULTS_CLASS_CODE("11502-2", "Laboratory Report", "2.16.840.1.113883.6.1", "ORCD");

    public final String classCode;
    public final String value;
    public final String codeSystemId;
    public final String scope;

    ClinicalDocumentEnum(String classCode, String value, String codeSystemId, String scope) {
        this.classCode = classCode;
        this.value = value;
        this.codeSystemId = codeSystemId;
        this.scope = scope;
    }
}
