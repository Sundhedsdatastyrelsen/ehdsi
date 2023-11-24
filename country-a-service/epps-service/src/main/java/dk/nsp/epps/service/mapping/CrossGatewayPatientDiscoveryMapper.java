package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PersonIdentifierType;
import lombok.experimental.UtilityClass;

/**
 * Mapping for Cross Gateway Patient Discovery (IHE ITE-55).
 */
@UtilityClass
public class CrossGatewayPatientDiscoveryMapper {
    public static GetPrescriptionRequestType mapRequest(String patientId) {
        var request = new GetPrescriptionRequestType();
        request.setPersonIdentifier(toPersonIdentifierType(patientId));
        request.setIncludeEffectuations(false);
        return request;
    }

    private static PersonIdentifierType toPersonIdentifierType(String patientId) {
        // TODO
        var personIdentifier = new PersonIdentifierType();
        // personIdentifier.setSource("CPR");
        personIdentifier.setValue(patientId);
        return personIdentifier;
    }

    private static String givenName(String givenName, String middleName) {
        if (givenName != null && middleName != null) {
            return givenName + " " + middleName;
        }
        return givenName;
    }

    /*
    public static PatientDemographicsDto mapResponse(GetPrescriptionResponseType src) {
        var patient = src.getPatient();
        var person = patient != null ? patient.getPerson() : null;
        var name = person != null ? person.getName() : null;
        var personIdentifier = person != null ? person.getPersonIdentifier() : null;
        var patientIdList = personIdentifier != null ? List.of(toEuPatientId(personIdentifier)) : null;

        var result = new PatientDemographicsDto(patientIdList);
        result.setGivenName(name != null ? givenName(name.getGivenName(), name.getMiddleName()) : null);
        result.setFamilyName(name != null ? name.getSurname(): null);
        result.setBirthDate(person != null ? toLocalDate(person.getBirthDate()) : null);
        result.setAdministrativeGender(person != null ? mapGender(person.getGender()) : null);
        return result;
    }

    private static String toEuPatientId(PersonIdentifierType personIdentifier) {
        // TODO
        return personIdentifier.getValue();
    }

    private static LocalDate toLocalDate(XMLGregorianCalendar src) {
        // TODO
        return null;
    }

    private static GenderDto mapGender(GenderType src) {
        return switch (src) {
            case FEMALE -> GenderDto.FEMALE;
            case MALE -> GenderDto.MALE;
            // default -> GenderDto.UNDIFFERENTIATED;
        };
    }
     */
}
