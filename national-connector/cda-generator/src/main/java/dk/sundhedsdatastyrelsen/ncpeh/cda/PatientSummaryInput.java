package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;

public record PatientSummaryInput(
    String documentId,
    PreferredHealthProfessional preferredHealthProfessional,
    Patient patient
) {
}
