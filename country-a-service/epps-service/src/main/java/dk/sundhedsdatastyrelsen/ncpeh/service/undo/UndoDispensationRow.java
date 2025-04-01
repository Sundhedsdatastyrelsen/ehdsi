package dk.sundhedsdatastyrelsen.ncpeh.service.undo;
import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;
import java.time.Instant;
/**
* Represents a row in the undo_dispensation table that tracks undo information for dispensations.
*
* @param cdaIdHash      An SHA-256 hash of the eDispensation CDA id (to mask potential sensitive data)
* @param effectuationId FMK id of the effectuation that needs to be undone
* @param orderId        FMK id of the prescription order containing the effectuation
* @param timestamp      When this row was created in the database, populated automatically on insert
*/
public record UndoDispensationRow(
@NonNull String cdaIdHash,
@NonNull Long effectuationId,
@NonNull Long orderId,
Instant timestamp
) {
 /**
 * Creates a new UndoDispensationRow from a CDA ID and associated effectuation and order IDs.
 * The timestamp is set to null as it will be populated by the database on insert.
 *
 * @param cdaId          The CDA ID of the dispensation to be hashed
 * @param effectuationId The ID of the effectuation to undo
 * @param orderId        The ID of the prescription order containing the effectuation
 * @return A new UndoDispensationRow with the provided information
 */
 public static UndoDispensationRow fromCdaId(String cdaId, Long effectuationId, Long orderId) {
 return new UndoDispensationRow(
 cdaIdHash(cdaId),
 effectuationId,
 orderId,
 null
 );
 }
  /**
 * Generates a SHA-256 hash of the provided CDA ID.
 * This method is used to mask potentially sensitive data in the original identifier.
 *
 * @param cdaId The CDA ID to hash
 * @return The SHA-256 hex string of the input CDA ID
 */
 public static String cdaIdHash(String cdaId) {
 return DigestUtils.sha256Hex(cdaId);
 }
}
