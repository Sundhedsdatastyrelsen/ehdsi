package dk.sundhedsdatastyrelsen.ncpeh.optout;

/// The public API of the opt-out lookup service client.
public interface OptOutService {
    /// Returns true if the citizen has opted out of using the given service.
    boolean hasOptedOut(String cpr, Service service);

    record Config(
        String host,
        String clientKeystorePath,
        String clientKeystorePassword,
        String clientTruststorePath,
        String clientTruststorePassword
    ) {}

    /// The services which can be opted out of.
    ///
    /// Note: This enum is defined by the API of the FSEU service.  Change it here, change it there.
    enum Service {EPRESCRIPTION, PATIENT_SUMMARY}

    /// Returns an OptOutService which always returns false, i.e., no citizens are ever opted out.
    static OptOutService never() {
        return (cpr, service) -> false;
    }

    // Returns an OptOutService which calls out to the lookup service of an external opt-out lookup service.
    static OptOutService create(Config config) {
        return new OptOutServiceImpl(config);
    }
}
