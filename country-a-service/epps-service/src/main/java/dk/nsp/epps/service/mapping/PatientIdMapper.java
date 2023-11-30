package dk.nsp.epps.service.mapping;

import lombok.experimental.UtilityClass;

/**
 * PatientId in EU contains the cpr and some identifier separated by three hats (^^^).
 * CPR should just be 10 digits.
 */
@UtilityClass
public class PatientIdMapper {
    public String toCpr(String patientId) {
        if (patientId == null) {
            return null;
        }

        // TODO
        return patientId;
    }

    public String toPatientId(String cpr) {
        if (cpr == null) {
            return null;
        }

        // TODO
        return cpr;
    }
}
