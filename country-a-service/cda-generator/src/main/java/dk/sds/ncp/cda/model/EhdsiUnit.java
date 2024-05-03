package dk.sds.ncp.cda.model;

import lombok.NonNull;
import lombok.Value;

public sealed interface EhdsiUnit {
    @Value
    class WithCode implements EhdsiUnit {
        @NonNull String code;
    }

    @Value
    class WithTranslation implements EhdsiUnit {
        @NonNull String translation;
    }
}
