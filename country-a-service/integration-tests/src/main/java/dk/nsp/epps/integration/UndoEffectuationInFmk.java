package dk.nsp.epps.integration;

import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.nsp.epps.service.PrescriptionService;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.epps.testing.shared.TestingFileNames;

import java.io.File;
import java.io.FileInputStream;

public class UndoEffectuationInFmk {
    public static UndoEffectuationResponseType undoEffectuation(String cpr, String inputFileMark) {
        var dispensationFile = new File(
            TestingFileNames.storageDir(),
            TestingFileNames.dispensationFileName(cpr, inputFileMark));
        try (var is = new FileInputStream(dispensationFile)) {
            var dispensationDocument = dk.nsp.epps.Utils.readXmlDocument(is);
            var patientId = cpr + "^^^&2.16.17.710.802.1000.990.1.500&ISO";

            var service = new PrescriptionService(Fmk.apiClient(), new EPrescriptionMapper(""), new DispensationMapper());

            return service.undoDispensation(patientId, dispensationDocument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
