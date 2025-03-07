package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import lombok.NonNull;
import lombok.Value;

import java.util.Locale;
import java.util.UUID;

@Value
public class CdaId {
    Oid rootOid;
    UUID rootUuid;
    String extension;

    public CdaId(@NonNull Oid root, String extension) {
        this.rootOid = root;
        this.extension = extension;
        this.rootUuid = null;
    }

    public CdaId(@NonNull UUID root) {
        this.rootUuid = root;
        this.rootOid = null;
        this.extension = null;
    }

    public String getRoot() {
        if (rootOid != null) {
            return rootOid.value;
        } else if (rootUuid != null) {
            return rootUuid.toString().toUpperCase(Locale.ROOT);
        }
        throw new RuntimeException("invalid CdaId, should not happen");
    }
}
