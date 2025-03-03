package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Input;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EPrescriptionCdaGenerator {
    private static final Logger logger = Logger.getLogger(EPrescriptionCdaGenerator.class.getName());

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.INFO);

        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.INFO);
    }

    public static void main(String[] args) throws TemplateException, JAXBException, MapperException, IOException {
        var prescriptionInput = "target/test-files/get-prescription.xml";
        if (args.length > 0) {
            prescriptionInput = args[0];
        }

        var medicationInput = "target/test-files/drug-medication.xml";
        if (args.length > 1) {
            medicationInput = args[1];
        }

        var cdaOutput = "target/test-files/eprescription-cda.xml";
        if (args.length > 2) {
            cdaOutput = args[2];
        }


        var prescriptionResponseFile = Path.of(prescriptionInput);
        Files.createDirectories(prescriptionResponseFile.getParent());

        var medicationResponseFile = Path.of(medicationInput);
        Files.createDirectories(medicationResponseFile.getParent());

        var prescriptionResponse = FmkResponseStorage.readStoredPrescriptions(prescriptionResponseFile.toFile());
        logger.log(Level.INFO, "Reading FMK prescription from {0}", prescriptionResponseFile.toAbsolutePath());

        var medicationResponse = FmkResponseStorage.readStoredMedication(medicationResponseFile.toFile());
        logger.log(Level.INFO, "Reading FMK medication from {0}", medicationResponseFile.toAbsolutePath());
        var xmlString = EPrescriptionL3Generator.generate(
            new EPrescriptionL3Input(
                prescriptionResponse,
                0,
                medicationResponse,
                "FIN" // Fyldt injektionssprøjte
            ));

        var ePCda = Path.of(cdaOutput);
        Files.createDirectories(ePCda.getParent());

        FmkResponseStorage.serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8), ePCda.toFile());
        logger.log(Level.INFO, "Wrote ePrescription CDA to {0}", ePCda.toAbsolutePath());
    }
}
