package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.ObjectFactory;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            Arguments.of("dispensations/dispensation1.xml"), //Our own constructed test dispensation
            Arguments.of("dispensations/dispensation2.xml"), //Our own constructed test dispensation
            Arguments.of("dispensations/CzRequest1.xml"), // One of the requests the CZ team sent us during the Fall 2024 test
            Arguments.of("dispensations/CzRequest2.xml"), // One of the requests the CZ team sent us during the Fall 2024 test
            Arguments.of("dispensations/CzRequest3.xml"), // One of the requests the CZ team sent us during the Spring 2025 test
            Arguments.of("dispensations/GrRequest1.xml"), // One of the requests the GR team sent us during the Fall 2024 test
            Arguments.of("dispensations/PlRequest1.xml"),  // One of the requests the PL team sent us during the Spring 2025 test
            Arguments.of("dispensations/PlRequest2.xml")  // One of the requests the PL team sent us during the Spring 2025 review phase
        );
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void startEffectuationRequestTest(String xmlFileName) throws MapperException {
        var cda = testDispensationCda(xmlFileName);
        var req = DispensationMapper.startEffectuationRequest("1111111118^^^&1.2.208.176.1.2&ISO", cda);

        assertThat(req.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(req.getPrescription().getFirst().getIdentifier() > 0L);
        assertThat(req.getPersonIdentifier().getValue(), is(equalTo("1111111118")));

        ///  Validate our StartEffectuationRequest against the WSDL schema.
        var validator = fmk146E6Schema().newValidator();
        var element = new dk.dkma.medicinecard.xml_schema._2015._06._01.e5.ObjectFactory().createStartEffectuationRequest(req);
        Assertions.assertDoesNotThrow(() ->
            validator.validate(prepareElementForValidation(element)));
    }

    @Test
    void authorPersonTest() throws XPathExpressionException {
        var xmlFileName = "dispensations/CzRequest1.xml";
        var cda = testDispensationCda(xmlFileName);
        var person = DispensationMapper.authorPerson(cda);

        Assertions.assertNull(
            person.getPersonIdentifier(),
            "FMK validates the modifier heavily. It must be of type OtherPerson and ID must be null." +
                " They take the ID from the XUA IDWS header. This is not specified anywhere yet, I think, but" +
                " was discovered while both teams worked on this.");

        Assertions.assertFalse(person.getName().getGivenName().isBlank());
        Assertions.assertEquals("TOMÁŠ", person.getName().getGivenName());

        Assertions.assertFalse(person.getName().getSurname().isBlank());
        Assertions.assertEquals("HRABÁČEK", person.getName().getSurname());

        Assertions.assertNull(person.getName().getMiddleName());
    }

    @Test
    void authorRoleTest() throws XPathExpressionException {
        var xmlFileName = "dispensations/CzRequest1.xml";

        var cda = testDispensationCda(xmlFileName);
        var role = DispensationMapper.authorRole(cda);

        Assertions.assertFalse(role.isBlank());
        Assertions.assertEquals("Udenlandsk apoteker", role);
    }

    @Test
    void authorOrganizationTest() throws XPathExpressionException {
        var xmlFileName = "dispensations/CzRequest1.xml";

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
        var xmlFileName = "dispensations/CzRequest1.xml";

        var cda = testDispensationCda(xmlFileName);
        var p = DispensationMapper.startEffectuationRequestPrescription(cda);

        Assertions.assertEquals(469854419288102L, p.getIdentifier());
    }

    @Test
    void effectuationTest() throws Exception {
        var xmlFileName = "dispensations/CzRequest1.xml";

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
        // value = 1
        var q = DispensationMapper.packageQuantity(testDispensationCda("dispensations/CzRequest1.xml"));
        assertThat(q, is(1));
        // negative integer is invalid value
        var ex1 = assertThrows(
            MapperException.class, () ->
                DispensationMapper.packageQuantity(testDispensationCda("dispensations/dispensation_bad_1.xml")));
        assertThat(ex1.getMessage(), containsString("must be a positive integer"));
        // decimal value is invalid value
        var ex2 = assertThrows(
            MapperException.class, () ->
                DispensationMapper.packageQuantity((testDispensationCda("dispensations/dispensation_bad_2.xml"))));
        assertThat(ex2.getMessage(), containsString("must be a positive integer"));
    }

    @Test
    void packageSizeTest() throws Exception {
        // unit="1" and has translation text
        var s1 = DispensationMapper.packageSize(testDispensationCda("dispensations/CzRequest1.xml"));
        assertThat(s1.getPackageSizeText(), is("100 stk."));
        // unit="1" and no translation text
        var s2 = DispensationMapper.packageSize(testDispensationCda("dispensations/CzRequest2.xml"));
        assertThat(s2.getPackageSizeText(), is("10 units"));
        // unit!="1"
        var s3 = DispensationMapper.packageSize(testDispensationCda("dispensations/dispensation1.xml"));
        assertThat(s3.getPackageSizeText(), is("100 mL"));
        // funny namespace
        var s4 = DispensationMapper.packageSize(testDispensationCda("dispensations/CzRequest3.xml"));
        assertThat(s4.getPackageSizeText(), is("100 units"));
        // zero is invalid value
        var ex1 = assertThrows(
            MapperException.class, () ->
                DispensationMapper.packageSize(testDispensationCda("dispensations/dispensation_bad_1.xml")));
        assertThat(ex1.getMessage(), containsString("must be a positive number"));
        // negative number is invalid value
        var ex2 = assertThrows(
            MapperException.class, () ->
                DispensationMapper.packageSize(testDispensationCda("dispensations/dispensation_bad_2.xml")));
        assertThat(ex2.getMessage(), containsString("must be a positive number"));

    }

    @Test
    void atcTest() throws Exception {
        {
            var atc = DispensationMapper.atc(testDispensationCda("dispensations/CzRequest1.xml"));
            assertThat(atc, is(notNullValue()));
            assertThat(atc.getCode().getSource(), is("Local"));
            assertThat(atc.getCode().getValue(), is("N02BE01"));
            assertThat(atc.getText(), is("paracetamol"));
        }
        {
            var atc = DispensationMapper.atc(testDispensationCda("dispensations/CzRequest3.xml"));
            assertThat(atc, is(notNullValue()));
            assertThat(atc.getCode().getSource(), is("Local"));
            assertThat(atc.getCode().getValue(), is("N04BA03"));
            assertThat(atc.getText(), is("levodopa, decarboxylase inhibitor and COMT inhibitor"));
        }
        {
            var atc = DispensationMapper.atc(testDispensationCda("dispensations/dispensation1.xml"));
            assertThat(atc, is(nullValue()));
        }
    }

    @Test
    void drugStrengthTest() throws Exception {
        {
            var ds = DispensationMapper.drugStrength(testDispensationCda("dispensations/CzRequest1.xml"));
            assertThat(ds, is(notNullValue()));
            assertThat(ds.getText().getSource(), is("Local"));
            assertThat(ds.getText().getValue(), is("500 mg"));
        }
        {
            var ds = DispensationMapper.drugStrength(testDispensationCda("dispensations/dispensation1.xml"));
            assertThat(ds, is(nullValue()));
        }
    }

    @Test
    void substancesTest() throws Exception {
        {
            var substances = DispensationMapper.substances(testDispensationCda("dispensations/CzRequest1.xml"));
            assertThat(substances, is(nullValue()));
        }
        {
            var substances = DispensationMapper.substances(testDispensationCda("dispensations/CzRequest3.xml"));
            assertThat(substances, is(notNullValue()));
            assertThat(
                substances.getActiveSubstance().stream().map(ActiveSubstanceType::getFreeText).toList(),
                contains("LEVODOPA", "CARBIDOPA", "ENTACAPONE"));
        }
    }

    @Test
    void packageIdTest() throws Exception {
        {
            var packageId = DispensationMapper.packageId(testDispensationCda("dispensations/PlRequest1.xml"));
            assertThat(packageId, is(notNullValue()));
            assertThat(DispensationMapper.eval(packageId, "@code"), is("05909990773541"));
            assertThat(DispensationMapper.eval(packageId, "@codeSystem"), is("1.3.160"));
        }
        {
            var packageId = DispensationMapper.packageId(testDispensationCda("dispensations/CzRequest3.xml"));
            assertThat(packageId, is(nullValue()));
        }
        {
            var packageId = DispensationMapper.packageId(testDispensationCda("dispensations/dispensation2.xml"));
            assertThat(packageId, is(notNullValue()));
            assertThat(DispensationMapper.eval(packageId, "@code"), is("492285"));
            assertThat(DispensationMapper.eval(packageId, "@codeSystem"), is("2.16.17.710.802.1000.990.1.20.2"));
        }
    }

    @Test
    void packageDescriptionTest() throws Exception {
        {
            var packageDesc = DispensationMapper.packagedMedicinalProductDescription(testDispensationCda("dispensations/PlRequest1.xml"));
            assertThat(packageDesc, containsString("Asubtela, film-coated tablet, 3 mg"));
            assertThat(packageDesc, not(containsString("\n")));
        }
        {
            var packageDesc = DispensationMapper.packagedMedicinalProductDescription(testDispensationCda("dispensations/dispensation1.xml"));
            assertThat(packageDesc, is(""));
        }
    }

    @ParameterizedTest
    @MethodSource("testDispensationCdas")
    void createPharmacyEffectuationRequestTest(String xmlFileName) throws Exception {
        var cda = testDispensationCda(xmlFileName);
        var packageRestriction = PackageRestrictionType.builder()
            .withPackageNumber(DispensationMapper.packageNumber())
            .withPackageQuantity(2)
            .build();
        var startEffectuationResponse = StartEffectuationResponseType.builder()
            .addPrescription(PrescriptionType.builder()
                .withPackageRestriction(packageRestriction)
                .addOrder().withIdentifier(12345L).end()
                .build())
            .build();
        var result = DispensationMapper.createPharmacyEffectuationRequest(
            "1111111118^^^&1.2.208.176.1.2&ISO",
            cda,
            startEffectuationResponse
        );

        assertThat(result.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(result.getPrescription().getFirst().getOrderIdentifier() > 0L);

        ///  Validate our CreatePharmacyEffectuationRequest against the WSDL schema.
        var validator = fmk146E6Schema().newValidator();
        var element = new ObjectFactory().createCreatePharmacyEffectuationRequest(result);
        Assertions.assertDoesNotThrow(() ->
            validator.validate(prepareElementForValidation(element)));
    }

    private Source prepareElementForValidation(JAXBElement<?> element) throws JAXBException {
        var sw = new StringWriter();
        JAXBContext.newInstance(
                "dk.dkma.medicinecard.xml_schema._2015._06._01"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e2"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e5"
                    + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                    + ":dk.sdsd.dgws._2012._06")
            .createMarshaller()
            .marshal(element, sw);
        return new StreamSource(new StringReader(sw.toString()));
    }

    /**
     * Extract the XSD schema from the FMK WSDL file.
     */
    private Schema fmk146E6Schema() {
        try {
            // First we parse the XML document with a namespace-aware DocumentBuilder
            var wsdl = this.getClass().getClassLoader().getResource("wsdl/MedicineCard-inline_2015_06_01_E6.wsdl");
            assertThat(wsdl, is(notNullValue()));
            var doc = DocumentBuilderFactory.newDefaultNSInstance()
                .newDocumentBuilder()
                .parse(wsdl.openStream());

            // Then we extract all the "xs:schema" nodes and massage them into a javax.xml.validation.Schema object
            var schemaNodes = doc.getElementsByTagNameNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, "schema");
            var xsds = IntStream.range(0, schemaNodes.getLength())
                .mapToObj(i -> new DOMSource(schemaNodes.item(i), wsdl.toString()))
                .toArray(Source[]::new);
            return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(xsds);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new IllegalStateException(e);
        }
    }
}
