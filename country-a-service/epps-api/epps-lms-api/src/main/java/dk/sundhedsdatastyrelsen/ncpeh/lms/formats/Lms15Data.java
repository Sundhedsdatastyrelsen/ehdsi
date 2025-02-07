package dk.sundhedsdatastyrelsen.ncpeh.lms.formats;

import dk.sundhedsdatastyrelsen.ncpeh.lms.Parsing.FixedWidthField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Lms15Data {
    @FixedWidthField(start = 0, length = 1)
    private String TypeCode;

    @FixedWidthField(start = 1, length = 3)
    private String Code;

    @FixedWidthField(start = 4, length = 10)
    private String ShortText;

    @FixedWidthField(start = 14, length = 50)
    private String Text;
}
