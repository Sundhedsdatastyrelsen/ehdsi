package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import org.w3c.dom.Element;

import java.time.Instant;

/**
 * An IDWS XUA token from SOSI STS which can be used to gain access to a service within a short period of time.
 */
public record EuropeanHcpIdwsToken(
    Element assertion,
    String audience,
    Instant created,
    Instant expires
) implements EuropeanHcpId {
    private static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.SAML);

    /// Get the "XSPA Subject" attribute value from the HCP token – i.e., the full name of the HCP
    public String subjectId() {
        return xpath.evalString(
            "saml:AttributeStatement/saml:Attribute[@Name='urn:oasis:names:tc:xspa:1.0:subject:subject-id']/saml:AttributeValue",
            assertion);
    }

    ///  Get "country of treatment" from the HCP token
    public String countryOfTreatment() {
        return xpath.evalString(
            "saml:AttributeStatement/saml:Attribute[@Name='urn:dk:healthcare:saml:CountryOfTreatment']/saml:AttributeValue",
            assertion);
    }
}
