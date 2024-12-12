package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService.PrescriptionFilter;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EPrescriptionMapperTest {
    @Test
    public void testExpectedNumberOfEpsosDocuments() throws Exception {
        var response = FmkResponseStorage.storedPrescriptions("1111111118");
        var documentId = EPrescriptionDocumentIdMapper.level3DocumentId(String.valueOf(response.getPrescription().getFirst().getIdentifier()));
        var prescriptionFilter = new PrescriptionFilter(documentId,null,null);
        var result = EPrescriptionMapper.mapResponse("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", prescriptionFilter, response);
        Assertions.assertEquals(1, result.size());
    }
}
