package dk.sundhedsdatastyrelsen.ncpeh.mocks;

import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.LmsDataLookupService;

public class EPrescriptionMapperServiceMock extends LmsDataLookupService {

    public EPrescriptionMapperServiceMock() {
        super(null);
    }

    @Override
    public String getPackageFormCodeFromPackageNumber(String packagingNumber) {
        return "FIN"; //Always return "Fyldt injentionssprøjte" for tests
    }
}
