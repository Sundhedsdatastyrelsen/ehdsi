package dk.sundhedsdatastyrelsen.ncpeh.lms;

import dk.sundhedsdatastyrelsen.ncpeh.lms.Parsing.FixedWidthParser;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import org.slf4j.Logger;

import java.util.List;

public class LmsDataParser {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LmsDataParser.class);

    public static List<Lms15Data> ParseLms15Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms15Data.class);
        } catch (Exception e) {
            log.error(String.format("Error parsing LMS15 data, error message: %s", e.getMessage()));
            throw new RuntimeException(e);
        }
    }
}
