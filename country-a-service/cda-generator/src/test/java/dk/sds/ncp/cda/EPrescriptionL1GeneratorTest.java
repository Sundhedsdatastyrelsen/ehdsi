package dk.sds.ncp.cda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EPrescriptionL1GeneratorTest {
    @Test
    void generateTest() throws IOException {
        var model = EPrescriptionL3MapperTest.getModel();
        var pdfFields = EPrescriptionL1Mapper.map(model);
        var pdf = EPrescriptionL1Generator.generate(pdfFields);
        Assertions.assertNotNull(pdf);

        //Write test output files
        File directory = new File("temp/");
        if(!directory.exists()){
            directory.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream("temp/pdflevel1-test.pdf")){
            fos.write(pdf);
        }
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
