package dk.nsp.epps.service.mapping;

import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.testing.shared.FmkResponseStorage;
import dk.sds.ncp.cda.EPrescriptionDocumentIdMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EPrescriptionMapperTest {
    private static EPrescriptionMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new EPrescriptionMapper("repoid");
    }

    @Test
    public void testExpectedNumberOfEpsosDocuments() throws Exception {
        var response = FmkResponseStorage.storedPrescriptions("1111111118");
        var documentId = EPrescriptionDocumentIdMapper.level3DocumentId(String.valueOf(response.getPrescription().getFirst().getIdentifier()));
        var prescriptionFilter = new PrescriptionFilter(documentId,null,null);
        //TODO Need to generate a drugMedicationResponse we can also load here
        //var result = mapper.mapResponse("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", prescriptionFilter, response);
        //Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(false); //Failing on purpose to highlight the TODO
    }
}
