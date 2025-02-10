package dk.sundhedsdatastyrelsen.ncpeh.mocks;

import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.EPrescriptionMappingService;

public class EPrescriptionMapperServiceMock extends EPrescriptionMappingService {

    @Override
    public String getPackageCodeFromPackageNumber(String packagingNumber) {
        return "FIN"; //Always return "Fyldt injentionssprøjte" for tests
    }
}
