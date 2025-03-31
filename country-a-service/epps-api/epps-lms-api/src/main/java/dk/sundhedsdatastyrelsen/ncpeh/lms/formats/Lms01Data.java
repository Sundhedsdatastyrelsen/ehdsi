package dk.sundhedsdatastyrelsen.ncpeh.lms.formats;

import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthField;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.DatabaseObject;
import lombok.Data;

@Data
public class Lms01Data implements DatabaseObject {
    @FixedWidthField(start = 0, length = 11)
    private String drugId;

    @FixedWidthField(start = 126, length = 6)
    private String marketingAuthorizationHolder;

    @Override
    public String getKey() {
        return drugId;
    }
}
