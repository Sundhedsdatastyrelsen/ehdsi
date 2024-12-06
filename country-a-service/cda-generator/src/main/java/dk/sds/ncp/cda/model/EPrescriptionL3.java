package dk.sds.ncp.cda.model;

import dk.sds.ncp.cda.Utils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class EPrescriptionL3 {
    /**
     * Unique ID identifying the CDA document (not the prescription itself).
     */
    @NonNull CdaId documentId;
    @NonNull String title;
    @NonNull CdaId prescriptionId;

    /**
     * "[...] the date and time at which this document was created as an electronic document."
     */
    @NonNull OffsetDateTime effectiveTime;
    @NonNull Author author;

    @NonNull String indicationText;

    @NonNull Patient patient;
    /**
     * "Time of signing the document"
     * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.1.1-2022-09-12T133927.html">
     * ART-DECOR</a>
     */
    @NonNull OffsetDateTime signatureTime;
    @NonNull CdaId parentDocumentId;
    @NonNull String entryText;
    @NonNull Product product;
    @NonNull Long packageQuantity;
    @NonNull Boolean substitutionAllowed;

    public String getEffectiveTime() {
        return Utils.cdaDateTime(effectiveTime);
    }
    public OffsetDateTime getEffectiveTimeOffsetDateTime() {
        return effectiveTime;
    }
    public String getSignatureTime() {
        return Utils.cdaDateTime(signatureTime);
    }
    public String getPackageQuantity() {
        return Long.toString(packageQuantity);
    }

    public long getPackageQuantityLong() {
        return packageQuantity;
    }
}
