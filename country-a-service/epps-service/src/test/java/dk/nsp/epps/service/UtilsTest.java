package dk.nsp.epps.service;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class UtilsTest {
    @Test
    void parseEpsosTimeTest() {
        var xgc1 = Utils.parseEpsosTime("20120919");
        assertThat(
            xgc1.toGregorianCalendar().toZonedDateTime().toOffsetDateTime(),
            is(equalTo(OffsetDateTime.parse("2012-09-19T00:00:00Z"))));

        var xgc2 = Utils.parseEpsosTime("20111113125600+0200");
        assertThat(
            xgc2.toGregorianCalendar().toZonedDateTime().toOffsetDateTime(),
            is(equalTo(OffsetDateTime.parse("2011-11-13T12:56:00+02:00"))));
    }
}
