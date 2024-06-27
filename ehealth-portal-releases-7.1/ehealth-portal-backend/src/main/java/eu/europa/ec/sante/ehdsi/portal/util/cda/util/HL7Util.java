package eu.europa.ec.sante.ehdsi.portal.util.cda.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HL7Util {
    public static String formatDateHL7(Date date) {

        var formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
        return formatter.format(date);
    }
}
