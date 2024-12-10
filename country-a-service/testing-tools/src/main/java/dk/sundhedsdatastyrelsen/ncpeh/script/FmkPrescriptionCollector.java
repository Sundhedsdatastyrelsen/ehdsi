package dk.sundhedsdatastyrelsen.ncpeh.script;

import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
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
        var response = frs.openPrescriptionsForCpr(input);
        var f = Path.of(output);
        Files.createDirectories(f.getParent());
        FmkResponseStorage.serializeToFile(response, f.toFile());
        System.out.println("Wrote prescriptions to " + f.toAbsolutePath());
    }
}
