package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.EmployeeIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.client.TestIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.VaccinationService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Ddv;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardRequestType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class DdvIT {
    private static final VaccinationService VACCINATION_SERVICE = new VaccinationService(Ddv.apiClient());

    @Test
    void getVaccinationTest() throws Exception {
        var vaccinations = VACCINATION_SERVICE.GetVaccinationsForCpr("1111111118");
        assertThat(vaccinations, is(not(empty())));
    }
}
