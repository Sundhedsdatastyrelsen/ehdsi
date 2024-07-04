package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import lombok.NonNull;
import org.w3c.dom.Document;

public class DispensationMapper {
    public StartEffectuationRequestType startEffectuationRequest(@NonNull String patientId, @NonNull Document cda) {
        throw new UnsupportedOperationException("TODO");
    }

    public CreatePharmacyEffectuationRequestType createPharmacyEffectuationRequest(@NonNull String patientId, @NonNull Document dispensationCda) {
        throw new UnsupportedOperationException("TODO");
    }
}
