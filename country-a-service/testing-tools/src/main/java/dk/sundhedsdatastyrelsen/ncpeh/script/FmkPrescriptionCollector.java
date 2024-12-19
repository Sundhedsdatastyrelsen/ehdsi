package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FmkPrescriptionCollector {
    private static final Logger logger = LoggerFactory.getLogger(FmkPrescriptionCollector.class);

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

        var frs = new FmkResponseStorage(Fmk.apiClient());
        var prescriptionResponse = frs.getPrescriptionResponse(cprInput, TestIdentities.apotekerChrisChristoffersen);
        var prescriptionXml = frs.createXmlFromPrescription(prescriptionResponse);
        var prescriptionPath = Path.of(prescriptionOutput);
        Files.createDirectories(prescriptionPath.getParent());
        FmkResponseStorage.serializeToFile(prescriptionXml, prescriptionPath.toFile());
        logger.info("Wrote prescriptions to {}", prescriptionPath.toAbsolutePath());

        var medicationIds = prescriptionResponse.getPrescription()
            .stream()
            .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
            .toList();
        var medicationResponse = frs.getDrugMedicationResponse(cprInput, medicationIds, TestIdentities.apotekerChrisChristoffersen);
        var medicationXml = frs.createXmlFromDrugMedication(medicationResponse);
        var medicationPath = Path.of(medicationOutput);
        Files.createDirectories(medicationPath.getParent());
        FmkResponseStorage.serializeToFile(medicationXml, medicationPath.toFile());
        logger.info("Wrote medications to {}", medicationPath.toAbsolutePath());
    }
}
