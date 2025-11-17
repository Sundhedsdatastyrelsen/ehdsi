package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// Class to help with anonymizing CDA documents, for when we want to log a request that fails.
public class Anonymizer {
    private Anonymizer() {}

    static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.HL7, XmlNamespace.PHARM);
    static final String STRIPPED_STRING = "REDACTED";

    /// Replace all personal information in an eDispensation with "REDACTED". Used to log failing requests.
    public static String stripPersonalInformation(Document originalEDispensation) throws XPathExpressionException, TransformerException {
        var dispensation = (Document) originalEDispensation.cloneNode(true);

        var stringsToRemove = Stream.of(
                getPersonPii(xpath.evalElement("//hl7:patientRole", dispensation)),
                getPersonPii(xpath.evalElement("//hl7:author/hl7:assignedAuthor", dispensation)),
                getPersonPii(xpath.evalElement("//hl7:performer/hl7:assignedEntity", dispensation)),
                getPersonPii(xpath.evalElement("//hl7:legalAuthenticator/hl7:assignedEntity", dispensation)))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        // Free text
        xpath.evalElement("//hl7:section/hl7:text", dispensation).setTextContent(STRIPPED_STRING);

        var asString = XmlUtils.writeDocumentToString(dispensation);
        for (var stringToRemove : stringsToRemove) {
            asString = asString.replaceAll(stringToRemove, STRIPPED_STRING);
        }

        return asString;
    }

    private static Set<String> getPersonPii(Element personElement) throws XPathExpressionException {
        return Stream.of(
                // id, cpr
                xpath.evalStrings("./hl7:id/@extension", personElement),
                // address
                xpath.evalStrings("./hl7:addr/*", personElement),
                // phone numbers, emails
                xpath.evalStrings("./hl7:telecom/@value", personElement),
                // names
                xpath.evalStrings("./hl7:patient/hl7:name/*", personElement),
                xpath.evalStrings("./hl7:assignedPerson/hl7:name/*", personElement),
                // birthdate
                xpath.evalStrings("./hl7:patient/hl7:birthTime/@value", personElement))
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toSet());
    }
}
