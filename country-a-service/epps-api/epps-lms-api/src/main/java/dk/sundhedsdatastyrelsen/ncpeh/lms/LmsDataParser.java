package dk.sundhedsdatastyrelsen.ncpeh.lms;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthParser;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import org.slf4j.Logger;

import java.util.List;

public class LmsDataParser {
    private LmsDataParser() {
        throw new IllegalStateException();
    }

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LmsDataParser.class);

    public static List<Lms02Data> parseLms02Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms02Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS02 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

    public static List<Lms14Data> parseLms14Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms14Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS14 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

    public static List<Lms15Data> parseLms15Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms15Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS15 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

    public static List<Lms22Data> parseLms22Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms22Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS22 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }
}
