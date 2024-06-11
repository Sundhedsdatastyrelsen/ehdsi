package dk.sds.ncp.cda;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class EPrescriptionL1GeneratorTest {
    @Test
    void generateTest() throws IOException {
        var model = EPrescriptionL3MapperTest.getModel();
        var l1Generator = new EPrescriptionL1Generator(model);
        var base64Pdf = l1Generator.generate();
        Assertions.assertNotNull(base64Pdf);

        //Write test output files
        File directory = new File("temp/");
        if(!directory.exists()){
            directory.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream("temp/pdflevel1-test.pdf")){
            var bytes = Base64.getDecoder().decode(base64Pdf);
            fos.write(bytes);
        }
    }
}
