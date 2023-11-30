package dk.nsp.epps.mocks.cpr.data;

import dk.oio.rep.cpr_dk.xml.schemas.core._2005._11._24.PersonBirthDateStructureType;
import dk.oio.rep.ebxml.xml.schemas.dkcc._2003._02._13.PersonGenderCodeType;
import dk.oio.rep.itst_dk.xml.schemas._2006._01._17.PersonNameStructureType;
import jakarta.xml.bind.JAXBElement;
import lombok.experimental.UtilityClass;
import oio.medcom.cprservice._1_0.AddressAccessType;
import oio.medcom.cprservice._1_0.AddressCompleteType;
import oio.medcom.cprservice._1_0.AddressPostalType;
import oio.medcom.cprservice._1_0.GetPersonInformationOut;
import oio.medcom.cprservice._1_0.PersonAddressStructureType;
import oio.medcom.cprservice._1_0.PersonCivilRegistrationStatusStructureType;
import oio.medcom.cprservice._1_0.PersonInformationStructureType;
import oio.medcom.cprservice._1_0.RegularCPRPersonType;
import oio.medcom.cprservice._1_0.SimpleCPRPersonType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.util.function.Consumer;

@UtilityClass
public class CprMockDataFactory {
    public static final String NAMESPACE_URI = "urn:oio:medcom:cprservice:1.0.4";

    public JAXBElement<GetPersonInformationOut> getPersonInformationOutAsJaxb() {
        return jaxbElement(NAMESPACE_URI, "getPersonInformationOut", getPersonInformationOut());
    }

    public GetPersonInformationOut getPersonInformationOut() {
        return apply(new GetPersonInformationOut(), out -> {
            out.setPersonInformationStructure(apply(new PersonInformationStructureType(), info -> {
                info.setCurrentPersonCivilRegistrationIdentifier("0101010000");
                info.setRegularCPRPerson(apply(new RegularCPRPersonType(), regular -> {
                    regular.setSimpleCPRPerson(apply(new SimpleCPRPersonType(), simple -> {
                        simple.setPersonNameStructure(apply(new PersonNameStructureType(), name -> {
                            name.setPersonGivenName("Helle");
                            name.setPersonSurnameName("Pedersen");
                        }));
                        simple.setPersonCivilRegistrationIdentifier("0101010000");
                    }));
                    regular.setPersonNameForAddressingName("Helle Pedersen");
                    regular.setPersonGenderCode(PersonGenderCodeType.FEMALE);
                    regular.setPersonInformationProtectionIndicator(false);
                    regular.setPersonBirthDateStructure(apply(new PersonBirthDateStructureType(), birthdate -> {
                        birthdate.setBirthDate(xmldate(2001, 01, 01));
                        birthdate.setBirthDateUncertaintyIndicator(false);
                    }));
                    regular.setPersonCivilRegistrationStatusStructure(apply(new PersonCivilRegistrationStatusStructureType(), civil -> {
                        civil.setPersonCivilRegistrationStatusCode("1");
                        civil.setPersonCivilRegistrationStatusStartDate(xmldate(2014, 01, 21));
                    }));
                }));
                info.setPersonAddressStructure(apply(new PersonAddressStructureType(), structure -> {
                    structure.setAddressComplete(apply(new AddressCompleteType(), address -> {
                        address.setAddressAccess(apply(new AddressAccessType(), access -> {
                            access.setMunicipalityCode("0000");
                            access.setStreetCode("0001");
                            access.setStreetBuildingIdentifier("238");
                        }));
                        address.setAddressPostal(apply(new AddressPostalType(), postal -> {
                            postal.setStreetName("Ålborggade");
                            postal.setStreetNameForAddressingName("Ålborggade");
                            postal.setStreetBuildingIdentifier("238");
                            postal.setPostCodeIdentifier("1323");
                            postal.setDistrictName("København K");
                        }));
                    }));
                    structure.setCountyCode("0000");
                }));
            }));
        });
    }

    private XMLGregorianCalendar xmldate(int year, int month, int day) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(year, month, day, DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> JAXBElement<T> jaxbElement(String namespace, String name, T value) {
        return new JAXBElement<>(new QName(namespace, name), (Class<T>) value.getClass(), value);
    }

    /**
     * Utility function to make it possible to in-line initialize builder-less nested classes.
     * Basically a poor man's version of kotlin's apply method.
     */
    private static <T> T apply(T value, Consumer<T> initializer) {
        initializer.accept(value);
        return value;
    }
}
