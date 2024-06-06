package dk.sds.ncp.cda.model;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class Utils {
    /**
     * Convert java.time value to "TS Time Stamp" datetime string as expected by CDA.
     */
    static String cdaDateTime(TemporalAccessor time) {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ", Locale.ROOT).format(time);
    }

    /**
     * Convert java.time value to "TS Time Stamp" date string as expected by CDA.
     */
    static String cdaDate(TemporalAccessor time) {
        return DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ROOT).format(time);
    }

    //SneakyThrows is accepted, since the MD5 algorithm is assumed to always be present
    @SneakyThrows
    static String Md5Hash(String input) {
        byte[] bytes = input.getBytes();
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(bytes);
        return new String(hash);
    }
}
