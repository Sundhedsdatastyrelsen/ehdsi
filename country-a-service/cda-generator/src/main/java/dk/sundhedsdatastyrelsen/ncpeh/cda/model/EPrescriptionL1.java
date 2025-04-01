package dk.sundhedsdatastyrelsen.ncpeh.cda.model;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
@Value
@Builder
public class EPrescriptionL1 {
 @NonNull String base64EncodedDocument;
 /**
 * The underlying structured document
 */
 @NonNull EPrescriptionL3 l3;
}
