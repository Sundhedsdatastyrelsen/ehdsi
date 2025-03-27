package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Size {
    @NonNull PackageUnit unit;
    @NonNull BigDecimal value;

    public String getValue() {
        return value.stripTrailingZeros().toPlainString();
    }
}
