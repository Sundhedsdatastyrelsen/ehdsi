package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Sosi;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("java:S106") // don't complain about System.out
public class FmkPrescriptionCollector {
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

        var token = Sosi.getToken(Sosi.Audience.FMK);
        var frs = new FmkResponseStorage(Fmk.idwsApiClient());
        var prescriptionResponse = frs.getPrescriptionResponse(cprInput, token);
        var prescriptionXml = frs.createXmlFromPrescription(prescriptionResponse);
        var prescriptionPath = Path.of(prescriptionOutput);
        Files.createDirectories(prescriptionPath.getParent());
        FmkResponseStorage.serializeToFile(prescriptionXml, prescriptionPath.toFile());
        System.out.printf("Wrote prescriptions to %s%n", prescriptionPath.toAbsolutePath());

        var medicationIds = prescriptionResponse.getPrescription()
            .stream()
            .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
            .toList();
        var medicationResponse = frs.getDrugMedicationResponse(cprInput, medicationIds, token);
        var medicationXml = frs.createXmlFromDrugMedication(medicationResponse);
        var medicationPath = Path.of(medicationOutput);
        Files.createDirectories(medicationPath.getParent());
        FmkResponseStorage.serializeToFile(medicationXml, medicationPath.toFile());
        System.out.printf("Wrote medications to %s%n", medicationPath.toAbsolutePath());
    }
}
