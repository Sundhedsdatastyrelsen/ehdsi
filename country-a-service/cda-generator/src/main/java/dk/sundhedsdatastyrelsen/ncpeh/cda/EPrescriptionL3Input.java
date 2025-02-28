package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsi.__.stamdata._3.AuthorizationType;

import java.util.List;

public record EPrescriptionL3Input(
    GetPrescriptionResponseType fmkPrescriptionResponse,
    int prescriptionIndex,
    GetDrugMedicationResponseType fmkDrugMedicationResponse,
    List<AuthorizationType> authorAuthorizations
) {
    public EPrescriptionL3Input(
        GetPrescriptionResponseType fmkPrescriptionResponse,
        int prescriptionIndex,
        GetDrugMedicationResponseType fmkDrugMedicationResponse
    ) {
        this(fmkPrescriptionResponse, prescriptionIndex, fmkDrugMedicationResponse, null);
    }
}
