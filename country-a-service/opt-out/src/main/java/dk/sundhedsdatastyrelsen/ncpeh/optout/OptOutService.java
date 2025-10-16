package dk.sundhedsdatastyrelsen.ncpeh.optout;

/// The public API of the opt-out lookup service client.
public interface OptOutService {
    record Config() {}

    enum Service {EPRESCRIPTION, PATIENT_SUMMARY}

    static OptOutService never() {
        return (cpr, service) -> false;
    }

    boolean hasOptedOut(String cpr, Service service);
}
