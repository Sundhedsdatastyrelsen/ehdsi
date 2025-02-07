package dk.sundhedsdatastyrelsen.ncpeh.lms.formats;

import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Lms22Data {
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
}
