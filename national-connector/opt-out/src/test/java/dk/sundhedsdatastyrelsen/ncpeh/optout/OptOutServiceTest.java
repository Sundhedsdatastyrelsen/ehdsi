package dk.sundhedsdatastyrelsen.ncpeh.optout;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class OptOutServiceTest {
    @Test
    void neverTest() {
        var n = OptOutService.never();
        assertThat(n.hasOptedOut("0123456789", OptOutService.Service.EPRESCRIPTION), is(false));
        assertThat(n.hasOptedOut("0123456789", OptOutService.Service.PATIENT_SUMMARY), is(false));
        assertThat(n.hasOptedOut("0123456789", null), is(false));
    }
}
