package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

public record OpenNcpAssertions(
    Element hcpAssertion,
    Element trcAssertion,
    String countryOfTreatment
) {
    private static final XPathWrapper xpath = new XPathWrapper(
        XmlNamespaces.WSSE,
        XmlNamespaces.DS,
        XmlNamespaces.SAML);

    public static OpenNcpAssertions fromSoapHeader(String soapHeader) throws AuthenticationException {
        try {
            var doc = XmlUtils.parse(soapHeader);
            var hcpAssertion = xpath.evalEl("//saml:Assertion[saml:Issuer[@NameQualifier='urn:ehdsi:assertions:hcp']]", doc);
            var trcAssertion = xpath.evalEl("//saml:Assertion[saml:Issuer[@NameQualifier='urn:ehdsi:assertions:trc']]", doc);

            var hcpCertB64 = xpath.evalString("//ds:X509Certificate", hcpAssertion);
            var countryOfTreatment = CertificateUtils.extractCountryCode(CertificateUtils.fromBase64(hcpCertB64));

            return new OpenNcpAssertions(hcpAssertion, trcAssertion, countryOfTreatment);
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error when parsing HCP assertion", e);
        }

    }
}
