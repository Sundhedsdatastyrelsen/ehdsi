package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.PublicException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;

import java.util.regex.Pattern;

/**
 * PatientId in EU contains the cpr and some identifier separated by three hats (^^^).
 * CPR should just be 10 digits.
 * Example: 1111111118^^^&1.2.208.176.1.2&ISO
 *
 * For now, we ignore the root (whatever comes after the ^^^) and assume that the value (whatever comes before ^^^)
 * represents a Danish CPR number.
 */
public final class PatientIdMapper {
    private static final String regexPattern = "^(\\d{6}-?\\d{4})(?:\\^{3}&" + Oid.DK_CPR.value + "&ISO)?";
    private static final Pattern patientIdPattern = Pattern.compile(regexPattern);

    private PatientIdMapper() {
    }

    /**
     * Extracts the CPR from a patientId. This method is quite forgiving as it accepts id with and without
     * root extension.
     *
     * @throws PublicException if the id is not a CPR number
     */
    public static String toCpr(String patientId) {
        if (patientId == null) {
            return null;
        }

        var matcher = patientIdPattern.matcher(patientId);
        if (!matcher.matches()) {
            throw new PublicException(400, "The patient identifier doesn't match any of the expected patterns");
        }
        return matcher.group(1).replace("-", "");
    }
}
