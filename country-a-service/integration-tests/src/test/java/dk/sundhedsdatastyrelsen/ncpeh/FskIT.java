package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.InformationRecordService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fsk;
import org.junit.jupiter.api.Test;

public class FskIT {
    private final InformationRecordService service = new InformationRecordService(Fsk.apiClient());

    /**
     * This test simply checks that we can connect and get an answer on the data.
     *
     * @throws Exception
     */
    @Test
    void getListOfCards() throws Exception {
        var adhocRequest = service.findInformationCardDetails(Fsk.cprJensJensenReadOnly, TestIdentities.apotekerJeppeMoeller);
    }
}
