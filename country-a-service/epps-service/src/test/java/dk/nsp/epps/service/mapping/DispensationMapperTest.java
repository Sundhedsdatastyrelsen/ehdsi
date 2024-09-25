package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.Utils;
import dk.sds.ncp.cda.MapperException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.xpath.XPathExpressionException;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

class DispensationMapperTest {
    Document testDispensationCda() {
        try (var is = this.getClass().getClassLoader().getResourceAsStream("dispensation2.xml")) {
            return Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    StartEffectuationResponseType testStartEffectuationResponse(Document cda) throws XPathExpressionException {
        var packageNumber = new DispensationMapper().packageNumber(cda);
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

    @Test
    void startEffectuationRequestTest() throws MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var req = sut.startEffectuationRequest("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", cda);

        assertThat(req.getPrescription().size(), is(equalTo(1)));
        Assertions.assertTrue(req.getPrescription().getFirst().getIdentifier() > 0L);
        assertThat(req.getPersonIdentifier().getValue(), is(equalTo("1111111118")));
    }

    @Test
    void authorPersonTest() throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var person = sut.authorPerson(cda);

        Assertions.assertNotNull(person.getPersonIdentifier());
        Assertions.assertFalse(person.getName().getGivenName().isBlank());
        Assertions.assertFalse(person.getName().getSurname().isBlank());
    }

    @Test
    void authorRoleTest() throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var role = sut.authorRole(cda);

        Assertions.assertFalse(role.isBlank());
    }

    @Test
    void authorOrganizationTest() throws XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var org = sut.authorOrganization(cda);

        Assertions.assertFalse(org.getIdentifier().getSource().isBlank());
        Assertions.assertFalse(org.getIdentifier().getValue().isBlank());
        Assertions.assertFalse(org.getName().isBlank());
        Assertions.assertFalse(org.getType().isBlank());
        Assertions.assertFalse(org.getAddressLine().isEmpty());
        Assertions.assertFalse(org.getTelephoneNumber().isBlank());
    }

    @Test
    void startEffectuationRequestPrescriptionTest() throws XPathExpressionException, MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var p = sut.startEffectuationRequestPrescription(cda);

        Assertions.assertTrue(p.getIdentifier() > 0L);
    }

    @Test
    void effectuationTest() throws Exception {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var startEffectuationResponse = testStartEffectuationResponse(cda);

        var e = sut.effectuation(cda, startEffectuationResponse);

        Assertions.assertTrue(e.getDateTime().isValid());
        var packageDispensed = e.getPackageDispensed();
        Assertions.assertTrue(packageDispensed.getPackageQuantity()>0);
        assertThat(packageDispensed.getPackageNumber().getSource(), is(equalTo("Medicinpriser")));
        Assertions.assertFalse(packageDispensed.getPackageNumber().getValue().isBlank());
    }

    @Test
    void packageQuantityTest() throws Exception {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();

        var q = sut.packageQuantity(cda);

        Assertions.assertTrue(q > 0);
    }

    @Test
    void createPharmacyEffectuationRequestTest() throws MapperException, XPathExpressionException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
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
