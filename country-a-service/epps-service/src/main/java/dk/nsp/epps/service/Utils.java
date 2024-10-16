package dk.nsp.epps.service;

import lombok.SneakyThrows;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class Utils {
    public static String md5Hash(String input) {
        return md5Hash(input.getBytes());
    }
    //SneakyThrows is accepted, since the MD5 algorithm is assumed to always be present
    @SneakyThrows
    public static String md5Hash(byte[] input) {
        var hash = MessageDigest.getInstance("MD5").digest(input);
        return String.format("%032x", new BigInteger(1, hash));
    }

    /**
     * Convert an TS.EPSOS.TZ Time Stamp to XMLGregorianCalender
     * <a href="https://wiki.art-decor.org/index.php?title=DTr1_TS.EPSOS.TZ">See ART-DECOR.</a>
     */
    public static XMLGregorianCalendar parseEpsosTime(String ts) {
        ZonedDateTime zdt;
        // The timestamp SHOULD include time and offset, but
        // our example data has only local date, so we'll handle those too.
        if (ts.length() == 8) {
            var parsed = DateTimeFormatter.ofPattern("yyyyMMdd").parse(ts);
            return xmlGregorianCalendar(LocalDate.from(parsed));
        } else {
            var parsed = DateTimeFormatter.ofPattern("yyyyMMddHHmmssX").parse(ts);
            return xmlGregorianCalendar(ZonedDateTime.from(parsed));
        }
    }

    /**
     * Convert a ZonedDateTime to XMLGregorianCalendar.
     */
    public static XMLGregorianCalendar xmlGregorianCalendar(ZonedDateTime zdt) {
        return DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(GregorianCalendar.from(zdt));
    }

    /**
     * Convert a LocalDate to XMLGregorianCalendar.
     */
    public static XMLGregorianCalendar xmlGregorianCalendar(LocalDate date) {
        return xmlGregorianCalendar(date.atStartOfDay(ZoneId.of("Z")));
    }
}
