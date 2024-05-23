package dk.sds.ncp.cda.model;

import lombok.NonNull;
import lombok.Value;

public sealed interface EhdsiUnit {
    /**
     * Short class name because Freemarker cannot match on type.
     */
    String getTag();
    @Value
    class WithCode implements EhdsiUnit {
        String tag = "WithCode";
        @NonNull String code;
    }

    @Value
    class WithTranslation implements EhdsiUnit {
        String tag = "WithTranslation";
        @NonNull String translation;
    }
}
