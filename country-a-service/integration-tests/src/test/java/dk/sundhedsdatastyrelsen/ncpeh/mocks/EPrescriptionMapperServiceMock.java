package dk.sundhedsdatastyrelsen.ncpeh.mocks;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.LmsDataLookupService;

public class EPrescriptionMapperServiceMock extends LmsDataLookupService {

    public EPrescriptionMapperServiceMock() {
        super(null);
    }

    @Override
    public String getPackageFormCodeFromPackageNumber(String packagingNumber) {
        return "FIN"; //Always return "Fyldt injektionssprøjte" for tests
    }

    @Override
    public Lms02Data getLms02EntryFromPackageNumber(String packageNumber) {
        var d = new Lms02Data();
        d.setPackagingType("A");
        return d;
    }
}
