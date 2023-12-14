package dk.nsp.epps.service.mapping;

import dk.nsp.epps.service.exception.CountryAException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PatientId in EU contains the cpr and some identifier separated by three hats (^^^).
 * CPR should just be 10 digits.
 */
@UtilityClass
public class PatientIdMapper {
    private static final String PATIENTID_PREFIX = "DKCPR^^^";
    private static final Pattern CPR_PATTERN_1 = Pattern.compile("^[0-9]{10}$");
    private static final Pattern CPR_PATTERN_2 = Pattern.compile("^([0-9]{6})-([0-9]{4})$");

    /**
     * Extracts the danish cpr from a patientId. This method is quite forgiving as it accepts id with and without
     * prefix and also accepts the CPR portion of the id with and without the dash.
     */
    public String toCpr(String patientId) {
        if (patientId == null) {
            return null;
        }

        String rawCpr = patientId.toUpperCase().startsWith(PATIENTID_PREFIX) ? patientId.substring(PATIENTID_PREFIX.length()) : patientId;

        if (CPR_PATTERN_1.matcher(rawCpr).matches()) {
            return rawCpr;
        }

        Matcher matcher = CPR_PATTERN_2.matcher(rawCpr);
        if (matcher.matches()) {
            return matcher.group(1) + matcher.group(2);
        }

        throw new CountryAException(HttpStatus.BAD_REQUEST, "'" + patientId + "' doesn't match any of the expected patterns");
    }

    public String toPatientId(String cpr) {
        if (cpr == null) {
            return null;
        }

        return cpr.toUpperCase().startsWith(PATIENTID_PREFIX) ? cpr : PATIENTID_PREFIX + cpr;
    }
}
