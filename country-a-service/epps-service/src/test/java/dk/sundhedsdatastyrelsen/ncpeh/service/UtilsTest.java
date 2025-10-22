package dk.sundhedsdatastyrelsen.ncpeh.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

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

    @Test
    void safeParsePositiveBigDecimalTest() {
        assertThat(Utils.safeParsePositiveBigDecimal(null), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveBigDecimal(""), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveBigDecimal("   "), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveBigDecimal("null"), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveBigDecimal("0"), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveBigDecimal("1.0"), is(Optional.of(BigDecimal.valueOf(1.0))));
        assertThat(Utils.safeParsePositiveBigDecimal("1"), is(Optional.of(BigDecimal.valueOf(1))));
    }

    @Test
    void safeParsePositiveBigIntTest() {
        assertThat(Utils.safeParsePositiveInt(null), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveInt(""), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveInt("   "), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveInt("null"), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveBigDecimal("0"), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveInt("1.0"), is(Optional.empty()));
        assertThat(Utils.safeParsePositiveInt("1"), is(Optional.of(1)));
    }

    @Test
    void splitUniqueIdToRepositoryIdAndDocumentIdTest() {
        var testOid = "1.2.208.176.43210.8.10.12";
        var testUuid = "aa575bf2-fde6-434c-bd0c-ccf5a512680d";
        var testDocumentId = testOid + "^" + testUuid;
        var tuple = InformationCardService.splitUniqueIdToRepositoryIdAndDocumentId(testDocumentId);
        assertThat(tuple.first(), Matchers.is(testOid));
        assertThat(tuple.second(), Matchers.is(testUuid));
    }
}
