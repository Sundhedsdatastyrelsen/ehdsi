package dk.nsp.epps.service.mapping;

import dk.nsp.epps.service.FmkResponseStorage;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class EPrescriptionMapperTest {
    private static EPrescriptionMapper mapper;

    @BeforeAll
    public static void setup() throws Exception {
        mapper = new EPrescriptionMapper("repoid");
    }

    @Test
    public void testExpectedNumberOfEpsosDocuments() throws Exception {
        var response = FmkResponseStorage.storedPrescriptions("1111111118");
        var result = mapper.mapResponse("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", new PrescriptionFilter( null, null, null, null), response);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testCdaValidity() throws Exception {
        var response = FmkResponseStorage.storedPrescriptions("0201909309");
        var result = mapper.mapResponse("0201909309^^^&2.16.17.710.802.1000.990.1.500&ISO", new PrescriptionFilter( null, null, null, null), response);
        Assertions.assertNotNull(result.getFirst());

        // 1. Test if well-formed XML

        // 2. Test if valid against schema

        // 3. Test model/schematron via gazelle

        Files.writeString(Path.of("temp/cda-eprescription1.xml"), result.getFirst().getDocument(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    }
}
