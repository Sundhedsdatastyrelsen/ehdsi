package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class DispensationMapperTest {
    Document testDispensationCda(String xmlFileName) {
        try (var is = this.getClass().getClassLoader().getResourceAsStream(xmlFileName)) {
            return Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    static Stream<Arguments> testDispensationCdas() {
        //Input format:
        // - Filename
        // - String of expected error message from mapping the CDA (null means no error is expected)
        String missingPharmCodeErrorMessage = String.format("Could not find find data at path: %s", DispensationMapper.XPaths.manufacturedMaterialCode);
        return Stream.of(
            Arguments.of("dispensation2.xml", null), //Our own constructed test dispensation
            Arguments.of("CzRequest1.xml", null), // One of the requests the CZ team sent us during the Fall 2024 test
            Arguments.of("CzRequest2.xml", missingPharmCodeErrorMessage), // One of the requests the CZ team sent us during the Fall 2024 test
            Arguments.of("GrRequest1.xml", missingPharmCodeErrorMessage) // One of the requests the GR team sent us during the Fall 2024 test

        );
    }

    StartEffectuationResponseType testStartEffectuationResponse(Document cda) {
        String packageNumber = new DispensationMapper().packageNumber(cda);
        var packageRestriction = PackageRestrictionType.builder()
            .withPackageNumber().withSource("Medicinpriser").withValue(packageNumber).withDate("20240725").end()
            .withPackageQuantity(2)
            .build();
        return StartEffectuationResponseType.builder()
            .addPrescription(PrescriptionType.builder()
                .withPackageRestriction(packageRestriction)
                .addOrder().withIdentifier(12345L).end()
                .build())
            .build();
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void startEffectuationRequestTest(String xmlFileName, String mappingErrorMessageExpected) throws MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var req = sut.startEffectuationRequest("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", cda);

        assertThat(req.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(req.getPrescription().getFirst().getIdentifier() > 0L);
        assertThat(req.getPersonIdentifier().getValue(), is(equalTo("1111111118")));
    }

    @Test
    void authorPersonTest() throws XPathExpressionException {
        var xmlFileName = "CzRequest1.xml";
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var person = sut.authorPerson(cda);

        Assertions.assertNotNull(person.getPersonIdentifier());
        Assertions.assertEquals("CPR", person.getPersonIdentifier().getSource());
        Assertions.assertEquals("3001010033", person.getPersonIdentifier().getValue());

        Assertions.assertFalse(person.getName().getGivenName().isBlank());
        Assertions.assertEquals("TOMÁŠ", person.getName().getGivenName());

        Assertions.assertFalse(person.getName().getSurname().isBlank());
        Assertions.assertEquals("HRABÁČEK", person.getName().getSurname());

        Assertions.assertNull(person.getName().getMiddleName());
    }

    @Test
    void authorRoleTest() throws XPathExpressionException {
        var xmlFileName = "CzRequest1.xml";

        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var role = sut.authorRole(cda);

        Assertions.assertFalse(role.isBlank());
        Assertions.assertEquals("Apoteker", role);
    }

    @Test
    void authorOrganizationTest() throws XPathExpressionException {
        var xmlFileName = "CzRequest1.xml";

        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var org = sut.authorOrganization(cda);
        
        Assertions.assertFalse(org.getIdentifier().getSource().isBlank());
        Assertions.assertFalse(org.getIdentifier().getValue().isBlank());
        Assertions.assertFalse(org.getType().isBlank());
        Assertions.assertFalse(org.getAddressLine().isEmpty());

        // Assertions
        Assertions.assertEquals("EAN-Lokationsnummer", org.getIdentifier().getSource(), "Source does not match");
        Assertions.assertEquals("5790000170609", org.getIdentifier().getValue(), "Value does not match");
        Assertions.assertEquals("Apotek", org.getType(), "Type does not match");
        Assertions.assertEquals("NCP-B-CZ Portal", org.getName(), "Name does not match");
        Assertions.assertEquals("invalid@email.test", org.getEmailAddress(), "Email does not match");
        Assertions.assertNull(org.getTelephoneNumber(), "TelephoneNumber should be null");
        Assertions.assertEquals(2, org.getAddressLine().size(), "AddressLine size does not match");
        Assertions.assertEquals("", org.getAddressLine().get(0), "AddressLine[0] does not match");
        Assertions.assertEquals("CZ", org.getAddressLine().get(1), "AddressLine[1] does not match");
    }

    @Test
    void startEffectuationRequestPrescriptionTest() throws XPathExpressionException, MapperException {
        var xmlFileName = "CzRequest1.xml";

        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var p = sut.startEffectuationRequestPrescription(cda);

        Assertions.assertEquals(469854419288102L, p.getIdentifier());
    }

    @Test
    void effectuationTest() throws Exception {
        var xmlFileName = "CzRequest1.xml";

        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);

        var startEffectuationResponse = testStartEffectuationResponse(cda);

        var e = sut.effectuation(cda, startEffectuationResponse);

        Assertions.assertTrue(e.getDateTime().isValid());

        Assertions.assertEquals(2024, e.getDateTime().getYear());
        Assertions.assertEquals(11, e.getDateTime().getMonth());
        Assertions.assertEquals(25, e.getDateTime().getDay());
        Assertions.assertEquals(14, e.getDateTime().getHour());
        Assertions.assertEquals(48, e.getDateTime().getMinute());
        Assertions.assertEquals(4, e.getDateTime().getSecond());

        var packageDispensed = e.getPackageDispensed();
        Assertions.assertTrue(packageDispensed.getPackageQuantity() > 0);
        Assertions.assertEquals(1, packageDispensed.getPackageQuantity());

        Assertions.assertEquals("56232", packageDispensed.getPackageNumber().getValue());
        Assertions.assertEquals("20240725", packageDispensed.getPackageNumber().getDate());
        Assertions.assertEquals("Medicinpriser", packageDispensed.getPackageNumber().getSource());

    }

    @Test
    void packageQuantityTest() throws Exception {

        var xmlFileName = "CzRequest1.xml";

        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);

        var q = sut.packageQuantity(cda);

        Assertions.assertEquals(1, q);
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void createPharmacyEffectuationRequestTest(String xmlFileName, String mappingErrorMessageExpected) throws MapperException, XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        if (mappingErrorMessageExpected != null) {
            var exception = Assertions.assertThrows(DataRequirementException.class, () -> testStartEffectuationResponse(cda));
            Assertions.assertEquals(exception.getMessage(), mappingErrorMessageExpected);
            return;
        }
        var startEffectuationResponse = testStartEffectuationResponse(cda);


        var result = sut.createPharmacyEffectuationRequest(
            "1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO",
            cda,
            startEffectuationResponse
        );

        assertThat(result.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(result.getPrescription().getFirst().getOrderIdentifier() > 0L);
    }
}