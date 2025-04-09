package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataRepository;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import org.springframework.stereotype.Service;

/**
 * This service is purely to pass from spring context into the static mapping methods, to allow them to access
 * relevant spring context (such as database access), without compromising (too much) the static nature of the mappers.
 * <p>
 * This is intended to solve all of these issues, and thus should just be a shell version, autowiring other spring
 * context services into itself, so the mapping methods do not bloat with endless services in the parameters
 */
@Service
public class LmsDataLookupService {
    private final LmsDataRepository lmsDataRepository;

    public LmsDataLookupService(LmsDataRepository lmsDataRepository) {
        this.lmsDataRepository = lmsDataRepository;
    }

    public String getPackageFormCodeFromPackageNumber(String packagingNumber) {
        var lms2 = lmsDataRepository.getLms02FromPackageNumber(Integer.parseInt(packagingNumber));
        if (lms2 != null) {
            return lms2.getPackagingType();
        }
        return null;
    }

    public Lms02Data getLms02EntryFromPackageNumber(String packageNumber) {
        return lmsDataRepository.getLms02FromPackageNumber(Integer.parseInt(packageNumber));
    }
}
