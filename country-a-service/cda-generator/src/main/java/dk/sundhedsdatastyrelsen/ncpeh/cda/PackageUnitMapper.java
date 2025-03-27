package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PackageUnit;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PackageUnitMapper {
    private static final Map<String, PackageUnit> lmsPackageUnitsToEntries;

    static {
        try (var is = PackageUnitMapper.class.getClassLoader().getResourceAsStream("lmsPackageUnitToEhdsi.yml")) {
            var yaml = new Yaml();
            HashMap<String, HashMap<String, String>> data = yaml.load(is);
            lmsPackageUnitsToEntries = data.entrySet().stream().map(kv -> {
                var unit = kv.getValue().get("ehdsi-unit");
                PackageUnit ehdsiUnit = unit != null
                    ? new PackageUnit.WithCode(unit)
                    : new PackageUnit.WithTranslation(kv.getValue().get("lms15-display-name"));
                return Map.entry(kv.getKey(), ehdsiUnit);
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert a package unit code from LMS15 ("Medicinpriser") to an eHDSI Unit code.
     * <p>
     * Examples: "AM", "HD", "MT", "KG"
     */
    public static PackageUnit fromLms(String lms15Code) {
        final var result = lmsPackageUnitsToEntries.get(lms15Code);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Unknown LMS15 package unit code: %s", lms15Code));
        }
        return result;
    }
}
