package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenExchangeRequest;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenParams;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.OpenNcpAssertions;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final IdwsConfiguration idwsConfiguration;
    private final SosiStsClientIdws sosiStsClientIdws;
    private final DgwsIdCardRequest.Configuration dgwsConfiguration;

    public AuthenticationServiceImpl(IdwsConfiguration idwsConfiguration, DgwsIdCardRequest.Configuration dgwsConfiguration) {
        this.idwsConfiguration = idwsConfiguration;
        this.dgwsConfiguration = dgwsConfiguration;
        // Nullable for easier testing of just one half.
        sosiStsClientIdws = Optional.ofNullable(idwsConfiguration)
            .map(IdwsConfiguration::uri)
            .map(SosiStsClientIdws::new)
            .orElse(null);
    }

    @Override
    public EuropeanHcpIdwsToken xcaSoapHeaderToIdwsToken(String soapHeader, String audience) {
        var openNcpAssertions = OpenNcpAssertions.fromSoapHeader(soapHeader);
        var bstParams = BootstrapTokenParams.fromOpenNcpAssertions(
            openNcpAssertions,
            audience,
            idwsConfiguration.issuer());
        log.info("Requesting IDWS token from SOSI STS for {}", audience);
        // we use the same certificate for the bootstrap tokens and the soap envelopes
        var bstRequest = BootstrapTokenExchangeRequest.of(bstParams, idwsConfiguration.certificateAndKey(), idwsConfiguration.certificateAndKey());
        return sosiStsClientIdws.exchangeBootstrapToken(bstRequest);
    }

    @Override
    public DgwsAssertion nspDgwsIdentityToAssertion(NspDgwsIdentity identity) {
        DgwsIdCardRequest request;
        if (identity instanceof NspDgwsIdentity.ReplaceWithIdws replaceIdentity) {
            // For integration tests or for development while we wait for external dependencies to be ready with IDWS.
            request = DgwsIdCardRequest.ofEmployeeIdentity(replaceIdentity, Instant.now(), dgwsConfiguration);
        } else {
            request = DgwsIdCardRequest.of(identity.systemCertificate(), Instant.now(), dgwsConfiguration);
        }
        return SosiStsClientDgws.exchangeIdCard(request, identity.stsUri());
    }
}
