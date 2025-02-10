package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.cda.interfaces.EPrescriptionContextAwareMappingService;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service is purely to pass from spring context into the static mapping methods, to allow them to access
 * relevant spring context (such as database access), without compromising (too much) the static nature of the mappers.
 * <p>
 * This is intended to solve all of these issues, and thus should just be a shell version, autowiring other spring
 * context services into itself, so the mapping methods do not bloat with endless services in the parameters
 */
@Service
public class EPrescriptionMappingService implements EPrescriptionContextAwareMappingService {

    @Autowired
    private LmsDataRepository lmsDataRepository;

    public String getPackageCodeFromPackageNumber(String packagingNumber) {
        var lms2 = lmsDataRepository.getLms02FromPackageNumber(Integer.parseInt(packagingNumber));
        return lms2.getPackagingType();
    }
}
