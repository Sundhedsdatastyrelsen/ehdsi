package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

/// A layer of packaging. Can be used to describe "100 tablets" or "20 tablets in 5 blister packs (100 tablets total)"
/// or "3 ml in 5 bottles (15 ml total)".
@Value
@Builder
@With
public class PackageLayer {
    @NonNull PackageUnit unit;
    @NonNull BigDecimal amount;
    String description;
    CdaCode packageFormCode;
    CdaCode packageCode;
    /// In the CDA, the packages are weirdly listed as <innermost><outermost/></innermost>, so we list them the same way
    /// here. The CDA allows for a recursion depth of up to 3, our code currently can only handle up to 2.
    PackageLayer wrappedIn;

    public String getAmount() {
        return amount.stripTrailingZeros().toPlainString();
    }

    public BigDecimal getNumberValue() {
        return amount;
    }
}
