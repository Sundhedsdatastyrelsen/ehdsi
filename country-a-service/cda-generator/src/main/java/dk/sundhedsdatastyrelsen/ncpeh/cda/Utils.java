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

    static final DateTimeFormatter cdaDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ", Locale.ROOT);
    static final DateTimeFormatter cdaDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ROOT);
    static final DateTimeFormatter cdaTSFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * Convert java.time value to "TS Time Stamp" datetime string as expected by CDA.
     */
    public static String cdaDateTime(TemporalAccessor time) {
        return cdaDateTimeFormatter.format(time);
    }

    /**
     * Convert java.time value to "TS Time Stamp" date string as expected by CDA.
     */
    public static String cdaDate(TemporalAccessor time) {
        return cdaDateFormatter.format(time);
    }

    public static String cdaTs(TemporalAccessor time) {
        return cdaTSFormatter.format(time);
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
