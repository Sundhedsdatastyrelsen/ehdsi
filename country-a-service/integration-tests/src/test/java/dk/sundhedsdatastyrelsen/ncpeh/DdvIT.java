package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.service.VaccinationService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Ddv;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Sosi;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DdvIT {
    private static final VaccinationService vaccinationService = new VaccinationService(Ddv.apiClient());

    @Test
    void getVaccinationTest() throws Exception {
        var token = Sosi.getToken(Sosi.Audience.DDV);
        var vaccinations = vaccinationService.getVaccinationsForCpr("1111111118", token);
        assertThat(vaccinations, is(not(empty())));
    }
}
