package dk.sds.ncp.cda;

import dk.nsp.epps.testing.shared.FmkResponseStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class EPrescriptionL1GeneratorTest {
    @Test
    void generateTest() throws Exception {
        var cpr = "0201909309";
        var fmkResponse = FmkResponseStorage.storedPrescriptions(cpr);
        var modelL3 = EPrescriptionL3Mapper.model(fmkResponse, Optional.empty(), 0); //TODO should probably also test with a drug medication response
        var modelL1 = EPrescriptionL1Mapper.map(modelL3);
        var pdf = EPrescriptionL1Generator.generate(modelL1);
        Assertions.assertNotNull(pdf);

        // Write PDF to disk for debugging purposes
//        java.nio.file.Files.write(
//            java.nio.file.Path.of("temp/cda-eprescription-l1-" + cpr + ".pdf"),
//            pdf,
//            java.nio.file.StandardOpenOption.CREATE,
//            java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
//        );
    }

    @Test
    void sameFileDifferentGenerations() {
        var model = EPrescriptionL3MapperTest.getModel();
        var pdfFields = EPrescriptionL1Mapper.map(model);
        var pdf = EPrescriptionL1Generator.generate(pdfFields);

        var secondPdf = EPrescriptionL1Generator.generate(pdfFields);

        Assertions.assertNotNull(pdf);
        Assertions.assertNotNull(secondPdf);
        Assertions.assertArrayEquals(pdf, secondPdf);
    }
}
