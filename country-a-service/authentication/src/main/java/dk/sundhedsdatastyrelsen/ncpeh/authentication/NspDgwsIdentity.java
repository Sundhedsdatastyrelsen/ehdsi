package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.net.URI;
import java.util.UUID;

///  An identity in NSP. Can be both a system identity and a user identity.
///
/// In production, we will only need a system identity here, as all user identities will go through IDWS.
public interface NspDgwsIdentity {
    URI stsUri();

    CertificateAndKey systemCertificate();

    // In prod, this is the only one that should be here.
    record System(URI stsUri, CertificateAndKey systemCertificate) implements NspDgwsIdentity {}

    /// This should only be used while we wait for our dependencies to update to IDWS, and for creating prescriptions
    /// in integration tests.
    record ReplaceWithIdws(URI stsUri, CertificateAndKey systemCertificate, UUID nameId,
                           CertificateAndKey idpCertificate) implements NspDgwsIdentity {}
}
