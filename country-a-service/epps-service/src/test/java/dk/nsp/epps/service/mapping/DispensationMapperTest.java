package dk.nsp.epps.service.mapping;

import dk.nsp.epps.Utils;
import dk.sds.ncp.cda.MapperException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
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
    void prescriptionTest() throws XPathExpressionException, MapperException {
        var sut = new DispensationMapper();
        var cda = testDispensationCda();
        var p = sut.prescription(cda);

        assertThat(p.getIdentifier(), is(equalTo(1234567890L)));
    }

    @Test
    @Disabled
    void createPharmacyEffectuationRequestTest() {
        Assertions.fail("TODO");
    }
}
