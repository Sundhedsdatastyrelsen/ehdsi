package dk.sundhedsdatastyrelsen.ncpeh.cda.interfaces;

/**
 * This interface is purely to pass from spring context into the static mapping methods, to allow them to access
 * relevant spring context (such as database access), without compromising (too much) the static nature of the mappers.
 * <p>
 * This is intended to solve all of these issues, and thus should just be a shell version, autowiring other spring
 * context services into itself, so the mapping methods do not bloat with endless services in the parameters
 */
public interface ReferenceDataLookupService {
    String getPackageCodeFromPackageNumber(String packagingNumber);
}
