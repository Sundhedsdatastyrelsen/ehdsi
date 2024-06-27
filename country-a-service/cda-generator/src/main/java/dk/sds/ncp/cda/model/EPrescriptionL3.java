package dk.sds.ncp.cda.model;

import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.Utils;
import freemarker.template.TemplateException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@SuperBuilder(toBuilder = true)
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

    public String getSignatureTime() {
        return Utils.cdaDateTime(signatureTime);
    }
    public String getPackageQuantity() {
        return Long.toString(packageQuantity);
    }
}
