package dk.sundhedsdatastyrelsen.ncpeh.cda;

import lombok.NonNull;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubstanceUnitMapper {
    public record Substance(@NonNull String numeratorUnit, @NonNull String denominatorUnit,
                            @NonNull BigDecimal denominator,
                            String translation) {
    }

    private static final Map<String, Substance> lmsSubstanceUnitToIngredient;

    static {
        try (var is = SubstanceUnitMapper.class.getClassLoader().getResourceAsStream("lmsSubstanceUnitToEhdsi.yml")) {
            var yaml = new Yaml();
            HashMap<String, HashMap<String, String>> data = yaml.load(is);
            lmsSubstanceUnitToIngredient = data.entrySet().stream().map(kv -> {
                var numerator = Optional.ofNullable(kv.getValue().get("numerator")).orElse("1");
                var denominator = Optional.ofNullable(kv.getValue().get("denominator")).orElse("1");
                var denominatorValue = Optional.ofNullable(kv.getValue().get("denominatorValue")).orElse("1");
                var displayName = kv.getValue().get("lms15-display-name");
                return Map.entry(kv.getKey(), new Substance(numerator, denominator, new BigDecimal(denominatorValue), displayName));
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Convert a substance unit code from LMS15 ("Medicinpriser") to an active ingredient.
     * <p>
     * Examples: "G", "GML", "KCA"
     */
    public static Substance fromLms(String lms15Code) {
        return lmsSubstanceUnitToIngredient.get(lms15Code);
    }
}
