package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Input;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("java:S106") // don't complain about System.out
public class EPrescriptionCdaGenerator {
    public static void main(String[] args) throws JAXBException, MapperException, IOException {
        String prescriptionIdPath = "target/test-files/prescription-id.txt";
        if (args.length > 0) {
            prescriptionIdPath = args[0];
        }

        var prescriptionInput = "target/test-files/get-prescription.xml";
        if (args.length > 1) {
            prescriptionInput = args[1];
        }

        var medicationInput = "target/test-files/drug-medication.xml";
        if (args.length > 2) {
            medicationInput = args[2];
        }

        var cdaOutput = "target/test-files/eprescription-cda.xml";
        if (args.length > 3) {
            cdaOutput = args[3];
        }


        var prescriptionResponseFile = Path.of(prescriptionInput);
        Files.createDirectories(prescriptionResponseFile.getParent());

        var medicationResponseFile = Path.of(medicationInput);
        Files.createDirectories(medicationResponseFile.getParent());

        var prescriptionResponse = FmkResponseStorage.readStoredPrescriptions(prescriptionResponseFile.toFile());
        System.out.printf("Reading FMK prescription from %s%n", prescriptionResponseFile.toAbsolutePath());
        var prescriptionIndex = 0;
        if (prescriptionIdPath != null) {
            var prescriptionId = Long.parseLong(Files.readString(Path.of(prescriptionIdPath), StandardCharsets.UTF_8));
            var prescription = prescriptionResponse.getPrescription()
                .stream()
                .filter(p -> p.getIdentifier() == prescriptionId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                    String.format("Prescription with id %s not found in gotten prescriptions", prescriptionId)));
            prescriptionIndex = prescriptionResponse.getPrescription().indexOf(prescription);
        }

        var medicationResponse = FmkResponseStorage.readStoredMedication(medicationResponseFile.toFile());
        System.out.printf("Reading FMK medication from %s%n", medicationResponseFile.toAbsolutePath());
        var xmlString = EPrescriptionL3Generator.generate(
            new EPrescriptionL3Input(
                prescriptionResponse,
                prescriptionIndex,
                medicationResponse,
                "FIN", // Fyldt injektionssprøjte
                1,
                "Manufacturer"
            ));

        var ePCda = Path.of(cdaOutput);
        Files.createDirectories(ePCda.getParent());

        FmkResponseStorage.serializeToFile(xmlString.getBytes(StandardCharsets.UTF_8), ePCda.toFile());
        System.out.printf("Wrote ePrescription CDA to %s%n", ePCda.toAbsolutePath());
    }
}
