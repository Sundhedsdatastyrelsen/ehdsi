package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        return Stream.of(
            Arguments.of("dispensation1.xml"), //Our own constructed test dispensation
            Arguments.of("dispensation2.xml"), //Our own constructed test dispensation
            Arguments.of("CzRequest1.xml"), // One of the requests the CZ team sent us during the Fall 2024 test
            Arguments.of("CzRequest2.xml"), // One of the requests the CZ team sent us during the Fall 2024 test
            Arguments.of("CzRequest3.xml"), // One of the requests the CZ team sent us during the Spring 2025 test
            Arguments.of("GrRequest1.xml") // One of the requests the GR team sent us during the Fall 2024 test
        );
    }

    StartEffectuationResponseType testStartEffectuationResponse(Document cda) {
        var packageRestriction = PackageRestrictionType.builder()
            .withPackageNumber(DispensationMapper.packageNumber(cda))
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
    void startEffectuationRequestTest(String xmlFileName) throws MapperException {
        var cda = testDispensationCda(xmlFileName);
        var req = DispensationMapper.startEffectuationRequest("1111111118^^^&1.2.208.176.1.2&ISO", cda);

        assertThat(req.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(req.getPrescription().getFirst().getIdentifier() > 0L);
        assertThat(req.getPersonIdentifier().getValue(), is(equalTo("1111111118")));
    }

    @Test
    void authorPersonTest() throws XPathExpressionException {
        var xmlFileName = "CzRequest1.xml";
        var cda = testDispensationCda(xmlFileName);
        var person = DispensationMapper.authorPerson(cda);

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

        var cda = testDispensationCda(xmlFileName);
        var role = DispensationMapper.authorRole(cda);

        Assertions.assertFalse(role.isBlank());
        Assertions.assertEquals("Apoteker", role);
    }

    @Test
    void authorOrganizationTest() throws XPathExpressionException {
        var xmlFileName = "CzRequest1.xml";

        var cda = testDispensationCda(xmlFileName);
        var org = DispensationMapper.authorOrganization(cda);

        Assertions.assertFalse(org.getIdentifier().getSource().isBlank());
        Assertions.assertFalse(org.getIdentifier().getValue().isBlank());
        Assertions.assertFalse(org.getType().isBlank());
        Assertions.assertFalse(org.getAddressLine().isEmpty());

        // Assertions
        Assertions.assertEquals("EAN-Lokationsnummer", org.getIdentifier().getSource(), "Source does not match");
        Assertions.assertEquals("5790001392277", org.getIdentifier().getValue(), "Value does not match");
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

        var cda = testDispensationCda(xmlFileName);
        var p = DispensationMapper.startEffectuationRequestPrescription(cda);

        Assertions.assertEquals(469854419288102L, p.getIdentifier());
    }

    @Test
    void effectuationTest() throws Exception {
        var xmlFileName = "CzRequest1.xml";

        var cda = testDispensationCda(xmlFileName);

        var e = DispensationMapper.effectuation(cda);

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

        Assertions.assertEquals("720000", packageDispensed.getPackageNumber().getValue());
        Assertions.assertEquals("Local", packageDispensed.getPackageNumber().getSource());

    }

    @Test
    void packageQuantityTest() throws Exception {

        var xmlFileName = "CzRequest1.xml";

        var cda = testDispensationCda(xmlFileName);

        var q = DispensationMapper.packageQuantity(cda);

        Assertions.assertEquals(1, q);
    }

    @Test
    void packageSizeTest() throws Exception {
        // unit="1" and has translation text
        var s1 = DispensationMapper.packageSize(testDispensationCda("CzRequest1.xml"));
        assertThat(s1.getPackageSizeText(), is("100 stk."));
        // unit="1" and no translation text
        var s2 = DispensationMapper.packageSize(testDispensationCda("CzRequest2.xml"));
        assertThat(s2.getPackageSizeText(), is("10 units"));
        // unit!="1"
        var s3 = DispensationMapper.packageSize(testDispensationCda("dispensation1.xml"));
        assertThat(s3.getPackageSizeText(), is("100 mL"));
        // funny namespace
        var s4 = DispensationMapper.packageSize(testDispensationCda("CzRequest3.xml"));
        assertThat(s4.getPackageSizeText(), is("100 units"));
    }

    @Test
    void atcTest() throws Exception {
        {
            var atc = DispensationMapper.atc(testDispensationCda("CzRequest1.xml"));
            assertThat(atc, is(notNullValue()));
            assertThat(atc.getCode().getSource(), is("Local"));
            assertThat(atc.getCode().getValue(), is("N02BE01"));
            assertThat(atc.getText(), is("paracetamol"));
        }
        {
            var atc = DispensationMapper.atc(testDispensationCda("CzRequest3.xml"));
            assertThat(atc, is(notNullValue()));
            assertThat(atc.getCode().getSource(), is("Local"));
            assertThat(atc.getCode().getValue(), is("N04BA03"));
            assertThat(atc.getText(), is("levodopa, decarboxylase inhibitor and COMT inhibitor"));
        }
        {
            var atc = DispensationMapper.atc(testDispensationCda("dispensation1.xml"));
            assertThat(atc, is(nullValue()));
        }
    }

    @Test
    void drugStrengthTest() throws Exception {
        {
            var ds = DispensationMapper.drugStrength(testDispensationCda("CzRequest1.xml"));
            assertThat(ds, is(notNullValue()));
            assertThat(ds.getText().getSource(), is("Local"));
            assertThat(ds.getText().getValue(), is("500 mg"));
        }
        {
            var ds = DispensationMapper.drugStrength(testDispensationCda("dispensation1.xml"));
            assertThat(ds, is(nullValue()));
        }
    }

    @Test
    void substancesTest() throws Exception {
        {
            var substances = DispensationMapper.substances(testDispensationCda("CzRequest1.xml"));
            assertThat(substances, is(nullValue()));
        }
        {
            var substances = DispensationMapper.substances(testDispensationCda("CzRequest3.xml"));
            assertThat(substances, is(notNullValue()));
            assertThat(substances.getActiveSubstance(), hasSize(3));
            assertThat(substances.getActiveSubstance().stream().map(ActiveSubstanceType::getFreeText).toList(),
                contains("LEVODOPA", "CARBIDOPA", "ENTACAPONE"));
        }

    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void createPharmacyEffectuationRequestTest(String xmlFileName) throws MapperException {
        var cda = testDispensationCda(xmlFileName);
        var startEffectuationResponse = testStartEffectuationResponse(cda);
        var result = DispensationMapper.createPharmacyEffectuationRequest(
            "1111111118^^^&1.2.208.176.1.2&ISO",
            cda,
            startEffectuationResponse
        );

        assertThat(result.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(result.getPrescription().getFirst().getOrderIdentifier() > 0L);
    }
}
