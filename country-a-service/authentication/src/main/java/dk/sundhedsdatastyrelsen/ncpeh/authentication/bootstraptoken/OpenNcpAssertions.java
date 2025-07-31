package dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlUtils;
import lombok.NonNull;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Record representing the pertinent parts of an OpenNCP XCA SOAP header.
 *
 * @param hcpAssertion       the SAML assertion representing the healthcare professional
 * @param trcAssertion       the SAML assertion representing the treatment relationship confirmation (incl. patient id)
 * @param countryOfTreatment the country of treatment extracted from the signing certificate (similar to how OpenNCP extracts country)
 */
public record OpenNcpAssertions(
    @NonNull Element hcpAssertion,
    @NonNull Element trcAssertion,
    @NonNull String countryOfTreatment
) {
    private static final XPathWrapper xpath = new XPathWrapper(
        XmlNamespace.WSSE,
        XmlNamespace.DS,
        XmlNamespace.SAML);

    public static OpenNcpAssertions fromSoapHeader(String soapHeader) throws AuthenticationException {
        try {
            var doc = XmlUtils.parse(soapHeader);
            var hcpAssertion = xpath.evalEl("//saml:Assertion[saml:Issuer[@NameQualifier='urn:ehdsi:assertions:hcp']]", doc);
            var trcAssertion = xpath.evalEl("//saml:Assertion[saml:Issuer[@NameQualifier='urn:ehdsi:assertions:trc']]", doc);

            if (trcAssertion == null) {
                throw new AuthenticationException("Missing TRC assertion. Cannot generate bootstrap token.");
            }
            // We determine the country of treatment by extracting the country code from the signing certificate.
            // This is similar to how OpenNCP determines country of treatment.
            var hcpCertB64 = xpath.evalString("//ds:X509Certificate", hcpAssertion);
            var countryOfTreatment = CertificateUtils.extractCountryCode(CertificateUtils.fromBase64(hcpCertB64));

            return new OpenNcpAssertions(hcpAssertion, trcAssertion, countryOfTreatment);
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error when parsing HCP assertion", e);
        }

    }
}
