package dk.sundhedsdatastyrelsen.ncpeh.lms.formats;
import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthField;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.DatabaseObject;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("squid:S116")
@Data
@NoArgsConstructor
public class Lms22Data implements DatabaseObject {
 @FixedWidthField(start = 0, length = 7)
 private String Code;
  @FixedWidthField(start = 7, length = 100)
 private String Text;
  @FixedWidthField(start = 107, length = 1)
 private String ActiveInactive;
  public Boolean getActive() {
 if (ActiveInactive == null) {
  return false;
 }
 return ActiveInactive.equalsIgnoreCase("a");
 }
  @Override
 public String getKey() {
 return Code;
 }
}
