package dk.sundhedsdatastyrelsen.ncpeh.cda;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class Utils {
    /**
     * Convert java.time value to "TS Time Stamp" datetime string as expected by CDA.
     */
    public static String cdaDateTime(TemporalAccessor time) {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ", Locale.ROOT).format(time);
    }

    /**
     * Convert java.time value to "TS Time Stamp" date string as expected by CDA.
     */
    public static String cdaDate(TemporalAccessor time) {
        return DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ROOT).format(time);
    }
}
