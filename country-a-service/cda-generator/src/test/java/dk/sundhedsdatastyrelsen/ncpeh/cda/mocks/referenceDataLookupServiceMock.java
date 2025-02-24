package dk.sundhedsdatastyrelsen.ncpeh.cda.mocks;

import dk.sundhedsdatastyrelsen.ncpeh.cda.interfaces.ReferenceDataLookupService;

public class referenceDataLookupServiceMock implements ReferenceDataLookupService {
    @Override
    public String getPackageCodeFromPackageNumber(String packagingNumber) {
        return "FIN"; //Fyldt injektionssprøjte
    }
}
