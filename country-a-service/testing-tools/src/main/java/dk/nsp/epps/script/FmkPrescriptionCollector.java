package dk.nsp.epps.script;

import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.epps.testing.shared.FmkResponseStorage;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FmkPrescriptionCollector {
    public static void main(String[] args) throws JAXBException, IOException {
        var input = Fmk.cprKarl;
        if (args.length > 0) {
            input = args[0];
        }
        var output = "target/test-files/get-prescription.xml";
        if (args.length > 1) {
            output = args[1];
        }

        var frs = new FmkResponseStorage(Fmk.apiClient());
        var response = frs.getPrescriptionResponse(input, TestIdentities.apotekerChrisChristoffersen);
        var xml = frs.createXmlFromPrescription(response);
        var f = Path.of(output);
        Files.createDirectories(f.getParent());
        FmkResponseStorage.serializeToFile(xml, f.toFile());
        System.out.println("Wrote prescriptions to " + f.toAbsolutePath());
    }
}
