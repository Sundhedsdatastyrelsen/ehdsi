package dk.nsp.epps.service.mapping;

import dk.nsp.epps.service.exception.CountryAException;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

/**
 * PatientId in EU contains the cpr and some identifier separated by three hats (^^^).
 * CPR should just be 10 digits.
 * Example: 1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO
 *
 * For now, we ignore the root (whatever comes after the ^^^) and assume that the value (whatever comes before ^^^)
 * represents a Danish CPR number.
 */
public final class PatientIdMapper {
    private static final Pattern patientIdPattern = Pattern.compile("(\\d{10})(?:\\^{3}.*)?");

    private PatientIdMapper() {
    }

    /**
     * Extracts the CPR from a patientId. This method is quite forgiving as it accepts id with and without
     * root extension.
     */
    public static String toCpr(String patientId) {
        if (patientId == null) {
            return null;
        }

        var matcher = patientIdPattern.matcher(patientId);
        if (!matcher.matches()) {
            throw new CountryAException(HttpStatus.BAD_REQUEST, "'" + patientId + "' doesn't match any of the expected patterns");
        }
        return matcher.group(1);
    }
}
