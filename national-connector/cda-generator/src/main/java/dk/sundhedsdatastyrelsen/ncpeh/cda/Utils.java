package dk.sundhedsdatastyrelsen.ncpeh.cda;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public final class Utils {
    private Utils() {
    }

    static final DateTimeFormatter cdaZonedDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ", Locale.ROOT);
    static final DateTimeFormatter cdaDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ROOT);
    // The times expected in the dosage are full instants without seconds.
    static final DateTimeFormatter cdaDosageTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * Convert java.time value to "TS Time Stamp" datetime string as expected by CDA.
     */
    public static String cdaZonedDateTime(TemporalAccessor time) {
        return cdaZonedDateTimeFormatter.format(time);
    }

    /**
     * Convert java.time value to "TS Time Stamp" date string as expected by CDA.
     */
    public static String cdaDate(TemporalAccessor time) {
        return cdaDateFormatter.format(time);
    }

    /// Convert java.time value to the string value expected in the dosages. Local time up to the minute. See for
    /// example [the art decor definition](https://art-decor.ehdsi.eu/publication/epsos-html-20240422T073854/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.2-2023-07-03T135239.html),
    /// the 2nd and 3rd effective times regarding dosage.
    public static String cdaDosageTime(TemporalAccessor time) {
        return cdaDosageTimeFormatter.format(time);
    }

    /**
     * Convert XMLGregorianCalendar to OffsetDateTime
     */
    public static OffsetDateTime convertToOffsetDateTime(XMLGregorianCalendar gregorianCalendar) {
        if (gregorianCalendar == null) {
            return null; // Handle null input gracefully
        }
        return gregorianCalendar.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
    }

    public static LocalDate convertToLocalDate(XMLGregorianCalendar gregorianCalendar) {
        return gregorianCalendar == null ? null : gregorianCalendar.toGregorianCalendar()
            .toZonedDateTime()
            .toLocalDate();
    }
}
