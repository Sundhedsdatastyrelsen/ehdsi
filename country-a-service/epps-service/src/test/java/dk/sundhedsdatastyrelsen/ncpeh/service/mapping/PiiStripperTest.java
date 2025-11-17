package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.test.TestUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PiiStripperTest {
    static final String testDoc = "dispensations/PlRequest1.xml";

    @Test
    void removesNames() throws Exception {
        var xpath = PiiStripper.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(testDoc));
        var dispensation = XmlUtils.parse(doc);
        var names = xpath.evalStrings("//hl7:patientRole/hl7:patient/hl7:name/*", dispensation);
        var updatedDoc = PiiStripper.stripPersonalInformation(dispensation);
        System.out.println(updatedDoc);
        for (var name : names) {
            assertThat(name, not(emptyOrNullString()));
            assertThat(updatedDoc, not(containsStringIgnoringCase(name)));
        }
    }

    @Test
    void removesCpr() throws Exception {
        var xpath = PiiStripper.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(testDoc));
        var dispensation = XmlUtils.parse(doc);
        var cpr = xpath.evalString("//hl7:patientRole/hl7:id/@extension", dispensation);
        var updated = PiiStripper.stripPersonalInformation(dispensation);
        assertThat(cpr, not(emptyOrNullString()));
        assertThat(updated, not(containsStringIgnoringCase(cpr)));
    }

    @Test
    void removesAddress() throws Exception {
        var xpath = PiiStripper.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(testDoc));
        var dispensation = XmlUtils.parse(doc);
        var addressStrings = xpath.evalStrings("//hl7:patientRole/hl7:addr/*", dispensation);
        var updated = PiiStripper.stripPersonalInformation(dispensation);
        for (var addressString : addressStrings) {
            assertThat(addressString, not(emptyOrNullString()));
            assertThat(updated, not(containsStringIgnoringCase(addressString)));
        }
    }

    @Test
    void removesBirthday() throws Exception {
        var xpath = PiiStripper.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(testDoc));
        var dispensation = XmlUtils.parse(doc);
        var birthday = xpath.evalString("//hl7:patientRole/hl7:patient/hl7:birthTime/@value", dispensation);
        var updated = PiiStripper.stripPersonalInformation(dispensation);
        assertThat(birthday, not(emptyOrNullString()));
        assertThat(updated, not(containsStringIgnoringCase(birthday)));
    }

    @Test
    void removesPhoneNumbers() throws Exception {
        var xpath = PiiStripper.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(testDoc));
        var dispensation = XmlUtils.parse(doc);
        var phoneNumbers = xpath.evalStrings("//hl7:patientRole/hl7:telecom/@value", dispensation);
        var updated = PiiStripper.stripPersonalInformation(dispensation);
        for (var phoneNumber : phoneNumbers) {
            assertThat(phoneNumber, not(emptyOrNullString()));
            assertThat(updated, not(containsStringIgnoringCase(phoneNumber)));
        }
    }

    @Test
    void removesAuthorPerformerId() throws Exception {
        var xpath = PiiStripper.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(testDoc));
        var dispensation = XmlUtils.parse(doc);
        var authorId = xpath.evalString("//hl7:assignedAuthor/hl7:id/@extension", dispensation);
        var performerId = xpath.evalString("//hl7:performer/hl7:assignedEntity/hl7:id/@extension", dispensation);
        var updated = PiiStripper.stripPersonalInformation(dispensation);
        assertThat(authorId, not(emptyOrNullString()));
        assertThat(performerId, not(emptyOrNullString()));
        assertThat(updated, not(containsStringIgnoringCase(authorId)));
        assertThat(updated, not(containsStringIgnoringCase(performerId)));
    }

    @Test
    void removesFreeText() throws Exception {
        var xpath = PiiStripper.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(testDoc));
        var dispensation = XmlUtils.parse(doc);
        var freeTextNode = xpath.evalNode("//hl7:section/hl7:text", dispensation);
        assertThat(freeTextNode.getTextContent(), not(emptyOrNullString()));
        assertThat(freeTextNode.getTextContent(), not(equalTo("REDACTED")));

        var updatedDocument = PiiStripper.stripPersonalInformation(dispensation);
        var updatedFreeTextNode = xpath.evalNode("//hl7:section/hl7:text", XmlUtils.parse(updatedDocument));
        assertThat(updatedFreeTextNode.getTextContent(), equalTo("REDACTED"));
    }
}
