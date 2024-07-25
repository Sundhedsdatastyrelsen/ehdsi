package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.Utils;
import dk.sds.ncp.cda.MapperException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.xpath.XPathExpressionException;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class DispensationMapperTest {
    Document testDispensationCda() {
        try (var is = this.getClass().getClassLoader().getResourceAsStream("cda-edispensation1.xml")) {
            return Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    StartEffectuationResponseType testStartEffectuationResponse() {
        var packageRestriction = PackageRestrictionType.builder()
            .withPackageNumber().withSource("Medicinpriser").withValue("549485").withDate("20240725").end()
            .withPackageQuantity(2)
            .build();
        return StartEffectuationResponseType.builder()
            .addPrescription(PrescriptionType.builder()
                .withPackageRestriction(packageRestriction)
                .addOrder().withIdentifier(12345L).end()
                .build())
            .build();
    }

    @Test
    void startEffectuationRequestTest() throws MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var req = sut.startEffectuationRequest("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", cda);

        assertThat(req.getPrescription().size(), is(equalTo(1)));
        assertThat(req.getPrescription().getFirst().getIdentifier(), is(equalTo(1234567890L)));
        assertThat(req.getPersonIdentifier().getValue(), is(equalTo("1111111118")));
    }

    @Test
    void authorPersonTest() throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var person = sut.authorPerson(cda);

        assertThat(person.getPersonIdentifier(), is(nullValue()));
        assertThat(person.getName().getGivenName(), is(equalTo("Anders Nyström")));
        assertThat(person.getName().getSurname(), is(equalTo("Karlsson")));
    }

    @Test
    void authorRoleTest() throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var role = sut.authorRole(cda);

        assertThat(role, is(equalTo("Apoteksansat")));
    }

    @Test
    void authorOrganizationTest() throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var org = sut.authorOrganization(cda);

        assertThat(org.getIdentifier().getSource(), is(equalTo("Udenlandsk")));
        assertThat(org.getIdentifier().getValue(), is(equalTo("1.2.752.129.2.1.2.1.3333-222")));
        assertThat(org.getName(), is(equalTo("epSOS Test")));
        assertThat(org.getType(), is(equalTo("Apotek")));
        assertThat(org.getAddressLine(), is(equalTo(List.of("Ringvägen 100", "11860", "Stockholm", "SE"))));
        assertThat(org.getTelephoneNumber(), is(equalTo("+46002000000")));
        assertThat(org.getEmailAddress(), is(equalTo("example@example.org")));
    }

    @Test
    void startEffectuationRequestPrescriptionTest() throws XPathExpressionException, MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var p = sut.startEffectuationRequestPrescription(cda);

        assertThat(p.getIdentifier(), is(equalTo(1234567890L)));
    }

    @Test
    void effectuationTest() throws Exception {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var startEffectuationResponse = testStartEffectuationResponse();

        var e = sut.effectuation(cda, startEffectuationResponse);

        var expected = DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(
            GregorianCalendar.from(ZonedDateTime.parse("2012-09-19T00:00:00Z"))
        );
        assertThat(e.getDateTime(), is(equalTo(expected)));
        var packageDispensed = e.getPackageDispensed();
        assertThat(packageDispensed.getPackageQuantity(), is(equalTo(2)));
        assertThat(packageDispensed.getPackageNumber().getSource(), is(equalTo("Medicinpriser")));
        assertThat(packageDispensed.getPackageNumber().getValue(), is(equalTo("549485")));
    }

    @Test
    void packageQuantityTest() throws Exception {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();

        var q = sut.packageQuantity(cda);

        assertThat(q, is(equalTo(2)));
    }

    @Test
    void createPharmacyEffectuationRequestTest() throws MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var startEffectuationResponse = testStartEffectuationResponse();


        var result = sut.createPharmacyEffectuationRequest(
            "1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO",
            cda,
            startEffectuationResponse
        );

        assertThat(result.getPrescription().size(), is(equalTo(1)));
        assertThat(result.getPrescription().getFirst().getOrderIdentifier(), is(equalTo(12345L)));
    }
}
