package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import lombok.NonNull;
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
    @Override
    @NonNull
    public String subjectId() {
        return xpath.evalString(
            "saml:AttributeStatement/saml:Attribute[@Name='urn:oasis:names:tc:xspa:1.0:subject:subject-id']/saml:AttributeValue",
            assertion);
    }

    ///  Get "country of treatment" from the HCP token
    @Override
    @NonNull
    public String countryOfTreatment() {
        return xpath.evalString(
            "saml:AttributeStatement/saml:Attribute[@Name='urn:dk:healthcare:saml:CountryOfTreatment']/saml:AttributeValue",
            assertion);
    }

    ///  Get "XSPA Organization ID" from the HCP token
    @Override
    @NonNull
    public String organizationId() {
        return xpath.evalString(
            "saml:AttributeStatement/saml:Attribute[@Name='urn:oasis:names:tc:xspa:1.0:subject:organization-id']/saml:AttributeValue",
            assertion);
    }

    ///  Get "XSPA Locality" from the HCP token, i.e., the name of the place where treatment is taking place.
    @Override
    @NonNull
    public String pointOfCare() {
        return xpath.evalString(
            "saml:AttributeStatement/saml:Attribute[@Name='urn:oasis:names:tc:xspa:1.0:environment:locality']/saml:AttributeValue",
            assertion);
    }

    ///  Get "XSPA Organization" from the HCP token.  This is an optional value, we might not receive it.
    /// It should only be filled when it differs from "point of care"/"locality".
    @Override
    public String organizationName() {
        var node = xpath.evalNode(
            "saml:AttributeStatement/saml:Attribute[@Name='urn:oasis:names:tc:xspa:1.0:subject:organization']/saml:AttributeValue",
            assertion);
        return node == null ? null : node.getTextContent();
    }
}
