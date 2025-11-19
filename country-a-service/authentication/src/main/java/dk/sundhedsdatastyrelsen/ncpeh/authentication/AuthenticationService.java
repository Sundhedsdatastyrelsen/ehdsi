package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.net.URI;

/**
 * The public API for authentication actions, such as bootstrap-to-IDWS exchanges.
 */
public interface AuthenticationService {
    /**
     * Retrieve an eHDSI IDWS XUA token from the SOSI STS based on the values in the HCP and TRC assertions
     * in the SOAP header of an XCA request.
     *
     * @param soapHeader the SOAP header from OpenNCP. We trust that its signatures are verified by OpenNCP.
     * @param audience   what we want access to, e.g. "https://fmk".
     * @return a token which can be used to gain access to the requested service
     * @throws AuthenticationException if something goes wrong
     */
    EuropeanHcpIdwsToken xcaSoapHeaderToIdwsToken(String soapHeader, String audience);

    /**
     * Exchange an organization ID card for a DGWS assertion we can use to call Nsp services that only need an
     * organization identity.
     *
     * @param identity the identity to exchange to a dgws assertion.
     * @return an assertion we can use to call NSP services that require an organization identity.
     */
    DgwsAssertion nspDgwsIdentityToAssertion(NspDgwsIdentity identity);

    record IdwsConfiguration(URI uri, CertificateAndKey certificateAndKey, String issuer) {}
}
