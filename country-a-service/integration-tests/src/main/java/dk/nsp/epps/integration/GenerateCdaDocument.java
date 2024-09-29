package dk.nsp.epps.integration;

import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.MapperException;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static dk.nsp.epps.testing.shared.FmkResponseStorage.readStoredPrescriptions;
import static dk.nsp.epps.testing.shared.FmkResponseStorage.serializeToFile;
import static dk.nsp.epps.testing.shared.TestingFileNames.cdaFileName;
import static dk.nsp.epps.testing.shared.TestingFileNames.fmkFileName;
import static dk.nsp.epps.testing.shared.TestingFileNames.storageDir;

public class GenerateCdaDocument {
    public static void generateCdaDocumentForCpr(String cpr, String inputFileMark, String outputFileMark) throws JAXBException, TemplateException, MapperException, IOException {
        var fmkResponseFile = new File(storageDir(), fmkFileName(cpr, inputFileMark));
        var response = readStoredPrescriptions(fmkResponseFile);
        var xmlString = EPrescriptionL3Generator.generate(response, 0);
        var cdaFile = new File(storageDir(), cdaFileName(cpr, outputFileMark));
        serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8), cdaFile);
        System.out.println("Wrote CDAs to " + cdaFile.getAbsolutePath());
    }
}
