package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import lombok.NonNull;

import java.util.List;

public record EPrescriptionL3Input(
    GetPrescriptionResponseType fmkPrescriptionResponse,
    int prescriptionIndex,
    GetDrugMedicationResponseType fmkDrugMedicationResponse,
    @NonNull List<AuthorizationType> authorAuthorizations,
    String packageFormCode,
    String manufacturerOrganizationName
) {
    public EPrescriptionL3Input(
        GetPrescriptionResponseType fmkPrescriptionResponse,
        int prescriptionIndex,
        GetDrugMedicationResponseType fmkDrugMedicationResponse,
        String packageFormCode,
        String manufacturerOrganizationName
    ) {
        this(fmkPrescriptionResponse, prescriptionIndex, fmkDrugMedicationResponse, List.of(), packageFormCode, manufacturerOrganizationName);
    }
}
