package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.EhdsiUnit;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EhdsiUnitMapper {
    private static final Map<String, EhdsiUnit> lms15ToEntries;

    static {
        try (var is = EhdsiUnitMapper.class.getClassLoader().getResourceAsStream("lms15ToEhdsi.yml")) {
            var yaml = new Yaml();
            HashMap<String, HashMap<String, String>> data = yaml.load(is);
            lms15ToEntries = data.entrySet().stream().map(kv -> {
                var unit = kv.getValue().get("ehdsi-unit");
                EhdsiUnit ehdsiUnit = unit != null
                    ? new EhdsiUnit.WithCode(unit)
                    : new EhdsiUnit.WithTranslation(kv.getValue().get("lms15-display-name"));
                return Map.entry(kv.getKey(), ehdsiUnit);
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert a package unit code from LMS15 ("Medicinpriser") to an eHDSI Unit code.
     */
    public static EhdsiUnit fromLms(String lms15Code) {
        final var result = lms15ToEntries.get(lms15Code);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Unknown LMS15 code: %s", lms15Code));
        }
        return result;
    }
}
