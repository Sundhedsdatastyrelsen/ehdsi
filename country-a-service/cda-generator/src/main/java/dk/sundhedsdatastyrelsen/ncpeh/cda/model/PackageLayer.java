package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@Value
@Builder
@With
public class PackageLayer {
    @NonNull PackageUnit unit;
    @NonNull BigDecimal value;
    String description;
    CdaCode packageFormCode;
    CdaCode packageCode;
    /// In the CDA, the packages are weirdly listed as <innermost><outermost/></innermost>, so we list them the same way
    /// here. The CDA allows for a recursion depth of up to 3, the code currently can only handle up to 2.
    PackageLayer wrappedIn;

    public String getValue() {
        return value.stripTrailingZeros().toPlainString();
    }

    public BigDecimal getNumberValue() {
        return value;
    }
}
