package dk.sundhedsdatastyrelsen.ncpeh.service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class Utils {
    private Utils() {
    }

    /**
     * Convert an TS.EPSOS.TZ Time Stamp to XMLGregorianCalender
     * <a href="https://wiki.art-decor.org/index.php?title=DTr1_TS.EPSOS.TZ">See ART-DECOR.</a>
     */
    public static XMLGregorianCalendar parseEpsosTime(String ts) {
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
     * Convert an XMLGregorianCalendar to an OffsetDateTime with UTC offset.
     */
    public static OffsetDateTime utcOffsetDateTime(XMLGregorianCalendar datetime) {
        return datetime.toGregorianCalendar().toInstant().atOffset(ZoneOffset.UTC);
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
