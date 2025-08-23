package dk.sundhedsdatastyrelsen.ncpeh.client;

import java.security.Provider;

public class STRTransformProvider extends Provider {
    /**
     *
     */
    private static final long serialVersionUID = -9148982936620100249L;

    public STRTransformProvider() {
        super("STRTransform", 1.5, "Security Token Reference Transform Provider");
        put(
            (String) "TransformService." + STRTransform.TRANSFORM_URI,
            "dk.sundhedsdatastyrelsen.ncpeh.client.STRTransform"
        );
        put((String) "TransformService." + STRTransform.TRANSFORM_URI + " MechanismType", "DOM");
    }
}
