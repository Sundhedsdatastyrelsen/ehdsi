package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;

public record PatientSummaryInput(
    String documentId,
    PreferredHealthProfessional preferredHealthProfessional,
    Patient patient,
    GetMedicineCardResponseType fmkMedicineCardResponse,
    GetVaccinationCardResponseType ddv
) {
}
