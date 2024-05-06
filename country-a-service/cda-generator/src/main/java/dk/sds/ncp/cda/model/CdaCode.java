package dk.sds.ncp.cda.model;

import dk.sds.ncp.cda.Oid;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CdaCode {
    @NonNull String code;
    String displayName;
    @NonNull Oid codeSystem;
    String codeSystemName;
    String codeSystemVersion;

    public String getCodeSystem() {
        return codeSystem.value;
    }
}
