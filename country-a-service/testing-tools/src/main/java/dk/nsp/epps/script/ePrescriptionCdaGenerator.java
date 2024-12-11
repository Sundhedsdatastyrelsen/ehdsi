package dk.nsp.epps.script;

import dk.nsp.epps.testing.shared.FmkResponseStorage;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.MapperException;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ePrescriptionCdaGenerator {
    public static void main(String[] args) throws TemplateException, JAXBException, MapperException, IOException {
        var input = "target/test-files/get-prescription.xml";
        if (args.length > 0) {
            input = args[0];
        }
        var output = "target/test-files/eprescription-cda.xml";
        if (args.length > 1) {
            output = args[1];
        }

        var fmkResponse = Path.of(input);
        Files.createDirectories(fmkResponse.getParent());

        var response = FmkResponseStorage.readStoredPrescriptions(fmkResponse.toFile());
        System.out.println("Reading FMK prescription from " + fmkResponse.toAbsolutePath());
        var xmlString = EPrescriptionL3Generator.generate(response, null, 0); //Update with medication

        var ePCda = Path.of(output);
        Files.createDirectories(ePCda.getParent());

        FmkResponseStorage.serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8), ePCda.toFile());
        System.out.println("Wrote ePrescription CDA to " + ePCda.toAbsolutePath());
    }
}
