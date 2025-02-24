package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService.PrescriptionFilter;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EPrescriptionMapperTest {
    @Test
    public void testExpectedNumberOfEpsosDocuments() throws Exception {
        var response = FmkResponseStorage.storedPrescriptions(Fmk.cprKarl);
        var documentId = EPrescriptionDocumentIdMapper.level3DocumentId(String.valueOf(response.getPrescription()
            .getFirst()
            .getIdentifier()));
        var prescriptionFilter = new PrescriptionFilter(documentId, null, null);
        var result = EPrescriptionMapper.mapResponse(String.format("%s^^^&%s&ISO", Fmk.cprKarl, Oid.DK_CPR), prescriptionFilter, response, null, new LmsDataLookupServiceMock());
        Assertions.assertEquals(1, result.size());
    }

    private class LmsDataLookupServiceMock extends LmsDataLookupService {
        private LmsDataLookupServiceMock() {
            super(null);
        }

        @Override
        public String getPackageCodeFromPackageNumber(String packagingNumber) {
            return "FIN"; //Fyldt injektionssprøjte
        }
    }
}
