package dk.sundhedsdatastyrelsen.ncpeh.cda.model;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import java.time.LocalDate;
@Value
@Builder
public class Patient {
 @NonNull CdaId id;
 @NonNull Name name;
 @NonNull Address address;
 @NonNull CdaCode genderCode;
 @NonNull LocalDate birthTime;
  public String getBirthTime() {
 return Utils.cdaDate(birthTime);
 }
}
