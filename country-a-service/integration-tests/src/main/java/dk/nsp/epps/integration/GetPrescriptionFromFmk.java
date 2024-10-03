package dk.nsp.epps.integration;

import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.epps.testing.shared.FmkResponseStorage;
import dk.nsp.epps.testing.shared.TestingFileNames;
import jakarta.xml.bind.JAXBException;

import java.io.File;

public class GetPrescriptionFromFmk {
    public static void getPrescriptionsFromFmkForCpr(String cpr, String outputFileMark) throws JAXBException {
        var frs = new FmkResponseStorage(Fmk.apiClient());
        var f = new File(TestingFileNames.storageDir(), TestingFileNames.fmkFileName(cpr, outputFileMark));
        var response = frs.openPrescriptionsForCpr(cpr);
        FmkResponseStorage.serializeToFile(response, f);
        System.out.println("Wrote prescriptions to " + f.getAbsolutePath());
    }
}
