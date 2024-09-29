package dk.nsp.epps.integration;

import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.epps.testing.shared.FmkResponseStorage;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.net.URISyntaxException;

import static dk.nsp.epps.testing.shared.FmkResponseStorage.serializeToFile;
import static dk.nsp.epps.testing.shared.TestingFileNames.fmkFileName;
import static dk.nsp.epps.testing.shared.TestingFileNames.storageDir;

public class GetPrescriptionFromFmk {
    public static void getPrescriptionsFromFmkForCpr(String cpr, String outputFileMark) throws JAXBException, URISyntaxException {
        var frs = new FmkResponseStorage(Fmk.apiClient());
        var f = new File(storageDir(), fmkFileName(cpr, outputFileMark));
        var response = frs.openPrescriptionsForCpr(cpr);
        serializeToFile(response, f);
        System.out.println("Wrote prescriptions to " + f.getAbsolutePath());
    }
}
