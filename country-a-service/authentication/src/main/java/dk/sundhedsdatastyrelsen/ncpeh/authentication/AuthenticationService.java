package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.net.URI;

public interface AuthenticationService {
    EuropeanHcpIdwsToken xcaSoapHeaderToIdwsToken(String soapHeader, String audience) throws AuthenticationException;

    DgwsAssertion nspDgwsIdentityToAssertion(NspDgwsIdentity identity) throws AuthenticationException;

    record IdwsConfiguration(URI uri, CertificateAndKey certificateAndKey, String issuer) {}
}
