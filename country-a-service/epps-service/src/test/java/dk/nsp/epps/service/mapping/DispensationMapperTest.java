package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.Utils;
import dk.nsp.epps.service.exception.DataRequirementException;
import dk.sds.ncp.cda.MapperException;
import org.junit.jupiter.api.Assertions;
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
        //Todo CFB: Add more dispensation files to test
        try (var is = this.getClass().getClassLoader().getResourceAsStream(xmlFileName)) {
            return Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Stream<Arguments> testDispensationCdas() {
        //Input format:
        // - Filename
        // - Boolean signifying if the mapping of the CDA is expected to succeed
        return Stream.of(
            Arguments.of("dispensation2.xml", true), //Our own constructed test dispensation
            Arguments.of("CzRequest1.xml", true), // One of the requests the CZ team sent us during the Fall 2024 test
            Arguments.of("CzRequest2.xml", false) // One of the requests the CZ team sent us during the Fall 2024 test
        );
    }

    StartEffectuationResponseType testStartEffectuationResponse(Document cda) throws XPathExpressionException {
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
    void startEffectuationRequestTest(String xmlFileName, boolean mappingExpectedToSucceed) throws MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var req = sut.startEffectuationRequest("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", cda);

        assertThat(req.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(req.getPrescription().getFirst().getIdentifier() > 0L);
        assertThat(req.getPersonIdentifier().getValue(), is(equalTo("1111111118")));
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void authorPersonTest(String xmlFileName, boolean mappingExpectedToSucceed) throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var person = sut.authorPerson(cda);

        Assertions.assertNotNull(person.getPersonIdentifier());
        Assertions.assertFalse(person.getName().getGivenName().isBlank());
        Assertions.assertFalse(person.getName().getSurname().isBlank());
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void authorRoleTest(String xmlFileName, boolean mappingExpectedToSucceed) throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var role = sut.authorRole(cda);

        Assertions.assertFalse(role.isBlank());
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void authorOrganizationTest(String xmlFileName, boolean mappingExpectedToSucceed) throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var org = sut.authorOrganization(cda);

        //TODO Revisit these, they will probably not always be blank. Do we need to validate this? As long as the
        // mapper succeeds, the test is functional. If we want to test values mapped correctly, we should probably
        // either not do the parameterized tests, or we should include the expected values in the parameters.
        Assertions.assertFalse(org.getIdentifier().getSource().isBlank());
        Assertions.assertFalse(org.getIdentifier().getValue().isBlank());
        Assertions.assertFalse(org.getName().isBlank());
        Assertions.assertFalse(org.getType().isBlank());
        Assertions.assertFalse(org.getAddressLine().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void startEffectuationRequestPrescriptionTest(String xmlFileName, boolean mappingExpectedToSucceed) throws XPathExpressionException, MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        var p = sut.startEffectuationRequestPrescription(cda);

        Assertions.assertTrue(p.getIdentifier() > 0L);
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void effectuationTest(String xmlFileName, boolean mappingExpectedToSucceed) throws Exception {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        if (!mappingExpectedToSucceed) {
            Assertions.assertThrows(DataRequirementException.class, () -> testStartEffectuationResponse(cda));
            return;
        }

        var startEffectuationResponse = testStartEffectuationResponse(cda);

        var e = sut.effectuation(cda, startEffectuationResponse);

        Assertions.assertTrue(e.getDateTime().isValid());
        var packageDispensed = e.getPackageDispensed();
        Assertions.assertTrue(packageDispensed.getPackageQuantity() > 0);
        assertThat(packageDispensed.getPackageNumber().getSource(), is(equalTo("Medicinpriser")));
        Assertions.assertFalse(packageDispensed.getPackageNumber().getValue().isBlank());

    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void packageQuantityTest(String xmlFileName, boolean mappingExpectedToSucceed) throws Exception {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);

        var q = sut.packageQuantity(cda);

        Assertions.assertTrue(q > 0);
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void createPharmacyEffectuationRequestTest(String xmlFileName, boolean mappingExpectedToSucceed) throws MapperException, XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda(xmlFileName);
        if (!mappingExpectedToSucceed) {
            Assertions.assertThrows(DataRequirementException.class, () -> testStartEffectuationResponse(cda));
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
