package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.nsp.test.idp.model.Identity;
import dk.nsp.test.idp.model.IdentityProvider;
import dk.nsp.test.idp.model.SecurityTokenService;
import dk.nsp.test.idp.model.ServiceConsumer;
import dk.nsp.test.idp.model.ServiceProvider;

/**
 * A representation of the foreign healthcare professional who is calling our services.
 * It is temporarily based on dk.nsp.test until the IDWS tokens are working.
 */
public interface EuropeanHealthcareProfessional extends Identity {
    /**
     * Provide an identification of the healthcare professional.
     * Will be used for MinLog entries.
     */
    String europeanHealthcareProfessionalId();

    static EuropeanHealthcareProfessional fromIdentity(Identity identity, String hcpId) {
        return new EuropeanHealthcareProfessional() {
            @Override
            public String europeanHealthcareProfessionalId() {
                return hcpId;
            }

            @Override
            public IdentityProvider getIdentityProvider() {
                return identity.getIdentityProvider();
            }

            @Override
            public ServiceConsumer getServiceConsumer() {
                return identity.getServiceConsumer();
            }

            @Override
            public ServiceProvider getServiceProvider() {
                return identity.getServiceProvider();
            }

            @Override
            public SecurityTokenService getSecurityTokenService() {
                return identity.getSecurityTokenService();
            }
        };
    }
}
