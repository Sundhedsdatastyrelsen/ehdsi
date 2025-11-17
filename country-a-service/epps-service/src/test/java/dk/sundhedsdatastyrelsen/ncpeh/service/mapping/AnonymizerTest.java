package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.test.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AnonymizerTest {
    static final String TEST_DOC = "dispensations/PlRequest1.xml";

    @Test
    void removesNames() throws Exception {
        var xpath = Anonymizer.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(TEST_DOC));
        var dispensation = XmlUtils.parse(doc);
        var names = xpath.evalStrings("//hl7:patientRole/hl7:patient/hl7:name/*", dispensation);
        var updatedDoc = Anonymizer.stripPersonalInformation(dispensation);
        System.out.println(updatedDoc);
        for (var name : names) {
            assertThat(name, not(emptyOrNullString()));
            assertThat(updatedDoc, not(containsStringIgnoringCase(name)));
        }
    }

    @Test
    void removesCpr() throws Exception {
        var xpath = Anonymizer.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(TEST_DOC));
        var dispensation = XmlUtils.parse(doc);
        var cpr = xpath.evalString("//hl7:patientRole/hl7:id/@extension", dispensation);
        var updated = Anonymizer.stripPersonalInformation(dispensation);
        assertThat(cpr, not(emptyOrNullString()));
        assertThat(updated, not(containsStringIgnoringCase(cpr)));
    }

    @Test
    void removesAddress() throws Exception {
        var xpath = Anonymizer.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(TEST_DOC));
        var dispensation = XmlUtils.parse(doc);
        var addressStrings = xpath.evalStrings("//hl7:patientRole/hl7:addr/*", dispensation);
        var updated = Anonymizer.stripPersonalInformation(dispensation);
        for (var addressString : addressStrings) {
            assertThat(addressString, not(emptyOrNullString()));
            assertThat(updated, not(containsStringIgnoringCase(addressString)));
        }
    }

    @Test
    void removesBirthday() throws Exception {
        var xpath = Anonymizer.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(TEST_DOC));
        var dispensation = XmlUtils.parse(doc);
        var birthday = xpath.evalString("//hl7:patientRole/hl7:patient/hl7:birthTime/@value", dispensation);
        var updated = Anonymizer.stripPersonalInformation(dispensation);
        assertThat(birthday, not(emptyOrNullString()));
        assertThat(updated, not(containsStringIgnoringCase(birthday)));
    }

    @Test
    void removesPhoneNumbers() throws Exception {
        var xpath = Anonymizer.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(TEST_DOC));
        var dispensation = XmlUtils.parse(doc);
        var phoneNumbers = xpath.evalStrings("//hl7:patientRole/hl7:telecom/@value", dispensation);
        var updated = Anonymizer.stripPersonalInformation(dispensation);
        for (var phoneNumber : phoneNumbers) {
            assertThat(phoneNumber, not(emptyOrNullString()));
            assertThat(updated, not(containsStringIgnoringCase(phoneNumber)));
        }
    }

    @Test
    void removesAuthor_Performer_LegalAuthenticatorId() throws Exception {
        var xpath = Anonymizer.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(TEST_DOC));
        var dispensation = XmlUtils.parse(doc);
        var ids = Stream.of(
                xpath.evalString("//hl7:assignedAuthor/hl7:id/@extension", dispensation),
                xpath.evalString("//hl7:performer/hl7:assignedEntity/hl7:id/@extension", dispensation),
                xpath.evalString("//hl7:legalAuthenticator/hl7:assignedEntity/hl7:id/@extension", dispensation))
            .filter(s -> !s.isEmpty())
            .toList();
        var updated = Anonymizer.stripPersonalInformation(dispensation);
        for (var id : ids) {
            assertThat(id, not(emptyOrNullString()));
            assertThat(updated, not(containsStringIgnoringCase(id)));
        }
    }

    @Test
    void removesFreeText() throws Exception {
        var xpath = Anonymizer.xpath;
        var doc = TestUtils.slurp(TestUtils.resource(TEST_DOC));
        var dispensation = XmlUtils.parse(doc);
        var freeTextNode = xpath.evalNode("//hl7:section/hl7:text", dispensation);
        assertThat(freeTextNode.getTextContent(), not(emptyOrNullString()));
        assertThat(freeTextNode.getTextContent(), not(equalTo("REDACTED")));

        var updatedDocument = Anonymizer.stripPersonalInformation(dispensation);
        assertThat(updatedDocument, containsString(">REDACTED</text>"));
    }
}
