package dk.nsp.epps.integration;

import dk.nsp.epps.testing.shared.FmkResponseStorage;
import dk.nsp.epps.testing.shared.TestingFileNames;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.MapperException;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GenerateCdaDocument {
    public static void generateCdaDocumentForCpr(String cpr, String inputFileMark, String outputFileMark)
        throws JAXBException, TemplateException, MapperException, IOException {
        var fmkResponseFile = new File(TestingFileNames.storageDir(), TestingFileNames.fmkFileName(cpr, inputFileMark));
        var response = FmkResponseStorage.readStoredPrescriptions(fmkResponseFile);
        var xmlString = EPrescriptionL3Generator.generate(response, 0);
        var cdaFile = new File(TestingFileNames.storageDir(), TestingFileNames.cdaFileName(cpr, outputFileMark));
        FmkResponseStorage.serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8), cdaFile);
        System.out.println("Wrote CDAs to " + cdaFile.getAbsolutePath());
    }
}
