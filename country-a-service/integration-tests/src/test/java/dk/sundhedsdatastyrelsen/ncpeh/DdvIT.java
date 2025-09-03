package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.service.VaccinationService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Ddv;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DdvIT {
    private static final VaccinationService VACCINATION_SERVICE = new VaccinationService(Ddv.apiClient());

    @Test
    void getVaccinationTest() throws Exception {
        var vaccinations = VACCINATION_SERVICE.getVaccinationsForCpr("1111111118", TestIdentities.lægeCharlesBabbage);
        assertThat(vaccinations, is(not(empty())));
    }
}
