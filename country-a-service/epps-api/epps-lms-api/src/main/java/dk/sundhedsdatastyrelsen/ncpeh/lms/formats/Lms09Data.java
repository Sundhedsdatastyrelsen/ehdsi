package dk.sundhedsdatastyrelsen.ncpeh.lms.formats;

import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthField;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.DatabaseObject;
import lombok.Data;

@Data
public class Lms09Data implements DatabaseObject {
    @FixedWidthField(start = 0, length = 6)
    private String companyId;

    @FixedWidthField(start = 26, length = 32)
    private String companyName;

    @Override
    public String getKey() {
        return companyId;
    }

}
