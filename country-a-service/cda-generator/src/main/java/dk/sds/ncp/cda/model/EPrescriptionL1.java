package dk.sds.ncp.cda.model;


import dk.sds.ncp.cda.Utils;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Base64;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@SuperBuilder(toBuilder = true)
public class EPrescriptionL1 extends EPrescriptionBase {

    @NonNull String base64EncodedDocument;
}
