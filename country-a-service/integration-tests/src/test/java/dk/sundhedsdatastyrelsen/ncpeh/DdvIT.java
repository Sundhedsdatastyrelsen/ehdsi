package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.VaccinationService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Ddv;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardRequestType;
import org.junit.jupiter.api.Test;

public class DdvIT {
    private static final VaccinationService VACCINATION_SERVICE = new VaccinationService(Fmk.apiClient());

    @Test
    void getVaccinationTest() throws Exception {
        var vaccinationRequest = GetVaccinationCardRequestType.builder()
            .withPersonCivilRegistrationIdentifier("1111111118")
            .build();

        var vaccinationCards = Ddv.apiClient()
            .getVaccinationCard(vaccinationRequest, TestIdentities.apotekerJeppeMoeller);

        var test = "test";
    }
}
