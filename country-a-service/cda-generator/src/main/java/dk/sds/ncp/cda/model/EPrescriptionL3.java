package dk.sds.ncp.cda.model;

import dk.sds.ncp.cda.EPrescriptionL3Generator;
import freemarker.template.TemplateException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Base64;

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
    @NonNull String indicationText;
    @NonFinal String cda;

    public String GetCda(){
        if(cda == null){
            try {
                cda = EPrescriptionL3Generator.generate(this);
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(String.format("Could not generate CDA for L3 EPrescription %s",this.documentId.getExtension()),e);
            }
        }
        return cda;
    }

    @Override
    public String GetHash() {
        return Utils.Md5Hash(cda);
    }

    @Override
    public Long GetSize() {
        return (long) cda.length();
    }

    public String getSignatureTime() {
        return Utils.cdaDateTime(signatureTime);
    }
    public String getPackageQuantity() {
        return Long.toString(packageQuantity);
    }
}
