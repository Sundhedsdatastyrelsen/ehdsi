package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
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

    /// Try to get an id from a string also containing the correct root "1.2.208.176.7.2.3^123456L3" -> "123456L3". If
    /// it cannot be parsed, null is returned. See also the comment on [CdaId#toDocumentId(CdaId)].
    public static CdaId fromDocumentId(String input) {
        if (input == null) {
            return null;
        }

        try {
            return new CdaId(UUID.fromString(input));
        } catch (IllegalArgumentException e) {
            // Not a UUID then.
        }

        var parts = input.split("\\^", 2);
        if (parts.length != 2) {
            return null;
        }

        var oidString = parts[0];
        var oid = Arrays.stream(Oid.values()).filter(v -> Objects.equals(v.value, oidString)).findFirst();
        var id = parts[1];
        if (id.trim().isEmpty() || oid.isEmpty()) {
            return null;
        }

        return new CdaId(oid.get(), id);
    }

    /// We found during the Spring test of 2025 that the countries that were already in production expected us to pass
    /// rooted ePrescription ids - so we do that now. The format is not documented anywhere I can find.
    public static String toDocumentId(CdaId id) {
        return id.getRootUuid() == null
            ? String.format("%s^%s", id.getRootOid().value, id.getExtension())
            : id.getRootUuid().toString();
    }
}
