package dk.sds.ncp.cda;

import dk.nsp.epps.testing.shared.FmkResponseStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EPrescriptionPdfGeneratorTest {
    @Test
    void generateTest() throws Exception {
        var cpr = "0201909309";
        var fmkResponse = FmkResponseStorage.storedPrescriptions(cpr);
        var modelL3 = EPrescriptionL3Mapper.model(fmkResponse, 0);
        var modelL1 = EPrescriptionPdfMapper.map(modelL3);
        var pdf = EPrescriptionPdfGenerator.generate(modelL1);
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
        var pdfFields = EPrescriptionPdfMapper.map(model);
        var pdf = EPrescriptionPdfGenerator.generate(pdfFields);

        var secondPdf = EPrescriptionPdfGenerator.generate(pdfFields);

        Assertions.assertNotNull(pdf);
        Assertions.assertNotNull(secondPdf);
        Assertions.assertArrayEquals(pdf, secondPdf);
    }
}