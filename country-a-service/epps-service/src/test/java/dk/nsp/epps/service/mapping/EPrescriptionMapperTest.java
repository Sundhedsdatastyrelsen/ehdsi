package dk.nsp.epps.service.mapping;

import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.test.FmkResponseStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EPrescriptionMapperTest {
    private static EPrescriptionMapper mapper;

    @BeforeAll
    public static void setup() throws Exception {
        mapper = new EPrescriptionMapper("repoid");
    }

    @Test
    public void testExpectedNumberOfEpsosDocuments() throws Exception {
        var response = FmkResponseStorage.storedPrescriptions("1111111118");
        var result = mapper.mapResponse("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", PrescriptionFilter.none(), response);
        Assertions.assertEquals(1, result.size());
    }
}
