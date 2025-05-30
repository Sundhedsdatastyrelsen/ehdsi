package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.oio.rep.ebxml.xml.schemas.dkcc._2003._02._13.PersonGenderCodeType;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.GenderDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PatientDemographicsDto;
import lombok.experimental.UtilityClass;
import oio.medcom.cprservice._1_0.GetPersonInformationOut;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Mapping for Cross Gateway Patient Discovery (IHE ITE-55).
 */
@UtilityClass
public class CrossGatewayPatientDiscoveryMapper {

    public static PatientDemographicsDto mapResponse(GetPersonInformationOut src) {
        var info = src != null ? src.getPersonInformationStructure() : null;
        var regular = info != null ? info.getRegularCPRPerson() : null;
        var simple = regular != null ? regular.getSimpleCPRPerson() : null;
        var name = simple != null ? simple.getPersonNameStructure() : null;
        var birthdate = regular != null ? regular.getPersonBirthDateStructure() : null;
        var structure = info != null ? info.getPersonAddressStructure() : null;
        var address = structure != null ? structure.getAddressComplete() : null;
        var postal = address != null ? address.getAddressPostal() : null;
        var country = postal != null ? postal.getCountryIdentificationCode() : null;

        var cpr = info != null && info.getRegularCPRPerson() != null && info.getRegularCPRPerson()
            .getSimpleCPRPerson() != null
            ? info.getRegularCPRPerson().getSimpleCPRPerson().getPersonCivilRegistrationIdentifier()
            : null;

        var result = new PatientDemographicsDto(cpr != null ? List.of(cpr) : Collections.emptyList());
        result.setFamilyName(name != null ? name.getPersonSurnameName() : null);
        result.setGivenName(name != null ? name.getPersonGivenName() : null);
        result.setBirthDate(birthdate != null ? toLocalDate(birthdate.getBirthDate()) : null);
        result.setAdministrativeGender(regular != null ? toGenderDto(regular.getPersonGenderCode()) : null);
        result.setCountry(country != null ? country.getValue() : null);
        result.setTelephone(null);
        result.setEmail(null);

        if (postal != null) {
            result.setPostalCode(postal.getPostCodeIdentifier());
            result.setCity(postal.getDistrictName());

            StringBuilder streetAddress = new StringBuilder();
            if (postal.getStreetNameForAddressingName() != null) {
                streetAddress.append(postal.getStreetNameForAddressingName());
            } else if (postal.getStreetName() != null) {
                streetAddress.append(postal.getStreetName());
            }
            if (postal.getStreetBuildingIdentifier() != null) {
                streetAddress.append(" ").append(postal.getStreetBuildingIdentifier());
            }
            if (postal.getFloorIdentifier() != null) {
                streetAddress.append(" ").append(postal.getFloorIdentifier());
            }
            if (postal.getSuiteIdentifier() != null) {
                streetAddress.append(" ").append(postal.getSuiteIdentifier());
            }
            result.setStreetAddress(streetAddress.isEmpty() ? null : streetAddress.toString());
        }

        return result;
    }

    private static LocalDate toLocalDate(XMLGregorianCalendar xml) {
        return xml != null ? LocalDate.of(xml.getYear(), xml.getMonth(), xml.getDay()) : null;
    }

    private static GenderDto toGenderDto(PersonGenderCodeType src) {
        if (src == null) {
            return null;
        }
        return switch (src) {
            case FEMALE -> GenderDto.FEMALE;
            case MALE -> GenderDto.MALE;
            case UNKNOWN -> GenderDto.UNDIFFERENTIATED;
        };
    }
}
