package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import java.util.List;

@Value
@Builder
@With
public class MedicalSummary {
    /**
     * One entry per medication item in the patient summary medication section.
     */
    @NonNull
    List<MedicationItem> entries;
}
