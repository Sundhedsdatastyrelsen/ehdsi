package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Sosi;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FmkPrescriptionCollector {
    private static final Logger logger = Logger.getLogger(FmkPrescriptionCollector.class.getName());

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.INFO);

        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.INFO);
    }

    // TODO this doesn't work with IDWS tokens. Need DGWS personal tokens.
    public static void main(String[] args) throws JAXBException, IOException {
        var cprInput = Fmk.cprKarl;
        if (args.length > 0) {
            cprInput = args[0];
        }
        var prescriptionOutput = "target/test-files/get-prescription.xml";
        if (args.length > 1) {
            prescriptionOutput = args[1];
        }
        var medicationOutput = "target/test-files/drug-medication.xml";
        if (args.length > 2) {
            medicationOutput = args[2];
        }

        var token = Sosi.getToken();
        var frs = new FmkResponseStorage(Fmk.idwsApiClient());
        var prescriptionResponse = frs.getPrescriptionResponse(cprInput, token);
        var prescriptionXml = frs.createXmlFromPrescription(prescriptionResponse);
        var prescriptionPath = Path.of(prescriptionOutput);
        Files.createDirectories(prescriptionPath.getParent());
        FmkResponseStorage.serializeToFile(prescriptionXml, prescriptionPath.toFile());
        logger.log(Level.INFO, "Wrote prescriptions to {0}", prescriptionPath.toAbsolutePath());

        var medicationIds = prescriptionResponse.getPrescription()
            .stream()
            .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
            .toList();
        var medicationResponse = frs.getDrugMedicationResponse(cprInput, medicationIds, token);
        var medicationXml = frs.createXmlFromDrugMedication(medicationResponse);
        var medicationPath = Path.of(medicationOutput);
        Files.createDirectories(medicationPath.getParent());
        FmkResponseStorage.serializeToFile(medicationXml, medicationPath.toFile());
        logger.log(Level.INFO, "Wrote medications to {0}", medicationPath.toAbsolutePath());
    }
}
