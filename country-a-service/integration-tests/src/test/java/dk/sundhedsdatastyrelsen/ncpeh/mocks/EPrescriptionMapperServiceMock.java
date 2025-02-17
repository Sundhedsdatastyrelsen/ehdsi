package dk.sundhedsdatastyrelsen.ncpeh.mocks;

import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.LmsDataLookupService;

public class EPrescriptionMapperServiceMock extends LmsDataLookupService {

    public EPrescriptionMapperServiceMock() {
        super();
    }

    @Override
    public String getPackageCodeFromPackageNumber(String packagingNumber) {
        return "FIN"; //Always return "Fyldt injentionssprøjte" for tests
    }
}
