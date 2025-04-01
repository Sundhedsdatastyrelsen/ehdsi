package dk.sundhedsdatastyrelsen.ncpeh.lms.formats;
import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthField;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.DatabaseObject;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("squid:S116")
@Data
@NoArgsConstructor
public class Lms15Data implements DatabaseObject {
 @FixedWidthField(start = 0, length = 1)
 private String TypeCode;
  @FixedWidthField(start = 1, length = 3)
 private String Code;
  @FixedWidthField(start = 4, length = 10)
 private String ShortText;
  @FixedWidthField(start = 14, length = 50)
 private String Text;
  @Override
 public String getKey() {
 return TypeCode + "-" + Code;
 }
}
