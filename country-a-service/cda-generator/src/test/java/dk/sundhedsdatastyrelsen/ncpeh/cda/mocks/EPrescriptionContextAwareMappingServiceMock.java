package dk.sundhedsdatastyrelsen.ncpeh.cda.mocks;

import dk.sundhedsdatastyrelsen.ncpeh.cda.interfaces.EPrescriptionContextAwareMappingService;

public class EPrescriptionContextAwareMappingServiceMock implements EPrescriptionContextAwareMappingService {
    @Override
    public String getPackageCodeFromPackageNumber(String packagingNumber) {
        return "FIN"; //Fyldt injektionssprøjte
    }
}
