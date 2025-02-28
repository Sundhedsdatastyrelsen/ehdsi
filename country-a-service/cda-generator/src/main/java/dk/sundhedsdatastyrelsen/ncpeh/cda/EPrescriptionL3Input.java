package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;

public record EPrescriptionL3Input(
    GetPrescriptionResponseType response,
    int prescriptionIndex,
    GetDrugMedicationResponseType drugMedicationResponse) {
}
