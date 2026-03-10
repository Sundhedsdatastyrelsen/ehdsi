package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PatientSummaryL1 {
    @NonNull String base64EncodedDocument;
    /**
     * The underlying structured document, with the L1 document ID applied.
     */
    @NonNull PatientSummaryL3 l3;
    /**
     * The document ID of the corresponding L3 document, used in the relatedDocument element.
     */
    @NonNull CdaId relatedL3DocumentId;
}
