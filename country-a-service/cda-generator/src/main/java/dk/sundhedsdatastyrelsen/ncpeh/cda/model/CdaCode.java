package dk.sundhedsdatastyrelsen.ncpeh.cda.model;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
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
  public String getCodeSystemName() {
 return codeSystem.objectName != null ? codeSystem.objectName : codeSystemName;
 }
}
