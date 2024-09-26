package dk.nsp.epps.integration;

import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.testing.shared.Fmk;
import dk.nsp.test.idp.model.Identity;

import java.io.File;
import java.io.FileInputStream;

import static dk.nsp.epps.testing.shared.TestingFileNames.getDispensationFileName;
import static dk.nsp.epps.testing.shared.TestingFileNames.storageDir;

public class SubmitDispensationToFmk {
    private static final Identity caller = TestIdentities.apotekerChrisChristoffersen;

    public static StartEffectuationResponseType startEffectuation(String cpr, String inputFileMark) throws Exception {
        var dispensationFile = new File(storageDir, getDispensationFileName(cpr, inputFileMark));
        try (var is = new FileInputStream(dispensationFile)) {
            var dispensationDocument = dk.nsp.epps.Utils.readXmlDocument(is);
            var patientId = cpr + "^^^&2.16.17.710.802.1000.990.1.500&ISO";
            var dispensationMapper = new DispensationMapper();
            //       <id extension="0201909309" root="2.16.17.710.802.1000.990.1.500" />
            var effectuationRequest = dispensationMapper.startEffectuationRequest(patientId, dispensationDocument);

            return Fmk.apiClient().startEffectuation(effectuationRequest, caller);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CreatePharmacyEffectuationResponseType createPharmacyEffectuation(String cpr, String inputFileMark, StartEffectuationResponseType startEffectuationResponse) throws Exception {
        var dispensationFile = new File(storageDir, getDispensationFileName(cpr, inputFileMark));
        try (var is = new FileInputStream(dispensationFile)) {
            var dispensationDocument = dk.nsp.epps.Utils.readXmlDocument(is);
            var patientId = cpr + "^^^&2.16.17.710.802.1000.990.1.500&ISO";
            var dispensationMapper = new DispensationMapper();

            return Fmk.apiClient().createPharmacyEffectuation(
                dispensationMapper.createPharmacyEffectuationRequest(
                    patientId,
                    dispensationDocument,
                    startEffectuationResponse),
                caller);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
