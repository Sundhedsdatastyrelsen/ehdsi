package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class CdaCode {
    @NonNull String code;
    String displayName;
    @NonNull Oid codeSystem;
    String codeSystemName;
    String codeSystemVersion;

    public String getCodeSystem() {
        return codeSystem.value;
    }

    public String getCodeSystemName() {
        return codeSystem.objectName != null ? codeSystem.objectName : codeSystemName;
    }

    public String toSingleString() {
        return String.format("%s^%s", codeSystem, code);
    }

    /// Try to get a cda code from a string. If it cannot be parsed, null is returned.
    public static CdaCode tryParse(String input) {
        if (input == null) {
            return null;
        }
        var parts = input.split("\\^", 1);
        if (parts.length != 2) {
            return null;
        }

        var oidString = parts[0];
        var code = parts[1];
        if (code.trim().isEmpty()) {
            return null;
        }

        try {
            return CdaCode.builder().codeSystem(Oid.valueOf(oidString)).code(code.trim()).build();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
