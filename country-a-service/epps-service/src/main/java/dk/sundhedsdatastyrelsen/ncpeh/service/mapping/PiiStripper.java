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

public class PiiStripper {
    private PiiStripper() {}

    static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.HL7, XmlNamespace.PHARM);
    static final String strippedString = "REDACTED";

    public static String stripPersonalInformation(Document originalEDispensation) throws XPathExpressionException, TransformerException {
        var dispensation = (Document) originalEDispensation.cloneNode(true);

        var stringsToRemove = Stream.of(
                getPersonPii(xpath.evalElement("//hl7:patientRole", dispensation)),
                getPersonPii(xpath.evalElement("//hl7:author", dispensation)),
                getPersonPii(xpath.evalElement("//hl7:performer", dispensation)))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        // Names
        for (var nameNode : xpath.evalNodes("//hl7:name/*", dispensation)) {
            nameNode.setTextContent(strippedString);
        }

        // CPR
        xpath.evalElement("//hl7:patientRole/hl7:id", dispensation).setAttribute("extension", strippedString);

        // Addresses
        for (var addressNode : xpath.evalNodes("//hl7:addr/*", dispensation)) {
            addressNode.setTextContent(strippedString);
        }

        // Birthdays
        for (var birthdayElement : xpath.evalNodes("//hl7:birthTime", dispensation)) {
            if (birthdayElement instanceof Element el) {
                el.setAttribute("value", strippedString);
            }
        }

        // Phone numbers
        for (var phoneNumber : xpath.evalNodes("//hl7:telecom", dispensation)) {
            if (phoneNumber instanceof Element el) {
                el.setAttribute("value", strippedString);
            }
        }

        // Author id
        xpath.evalElement("//hl7:assignedAuthor/hl7:id", dispensation).setAttribute("extension", strippedString);

        // Performer id
        xpath.evalElement("//hl7:performer/hl7:assignedEntity/hl7:id", dispensation)
            .setAttribute("extension", strippedString);

        // Legal authenticator id
        xpath.evalElement("//hl7:legalAuthenticator/hl7:assignedEntity/hl7:id", dispensation)
            .setAttribute("extension", strippedString);

        // Free text
        xpath.evalElement("//hl7:section/hl7:text", dispensation).setTextContent(strippedString);

        var asString = XmlUtils.writeDocumentToString(dispensation);
        for (var stringToRemove : stringsToRemove) {
            asString = asString.replaceAll(stringToRemove, strippedString);
        }

        return asString;
    }

    private static Set<String> getPersonPii(Element personElement) throws XPathExpressionException {
        return Stream.of(
                xpath.evalStrings("./hl7:id/@extension", personElement),
                xpath.evalStrings("./hl7:addr/hl7:streetAddressLine", personElement),
                xpath.evalStrings("./hl7:telecom/@value", personElement),
                xpath.evalStrings("./hl7:patient|hl7:assignedPerson/hl7:name/*", personElement),
                xpath.evalStrings("./hl7:patient/hl7:birthTime/@value", personElement))
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }
}
