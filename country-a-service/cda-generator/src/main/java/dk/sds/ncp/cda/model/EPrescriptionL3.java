package dk.sds.ncp.cda.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.Base64;

@EqualsAndHashCode(callSuper = true)
@Value
@SuperBuilder
@With
@SuppressWarnings("unused")
public class EPrescriptionL3 extends EPrescriptionBase{

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
    @NonNull String indicationText;

    String cdaDocument; //TODO CFB Consider removal, I need to figure out if we want to have this as part of the EPrescription.

    @Override
    public String GetHash() {
        return Utils.Md5Hash(cdaDocument);
    }

    @Override
    public Long GetSize() {
        return (long) cdaDocument.length();
    }

    public String getSignatureTime() {
        return Utils.cdaDateTime(signatureTime);
    }
    public String getPackageQuantity() {
        return Long.toString(packageQuantity);
    }
}
