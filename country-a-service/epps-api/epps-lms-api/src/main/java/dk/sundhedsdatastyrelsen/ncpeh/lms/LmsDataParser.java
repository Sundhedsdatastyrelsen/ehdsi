package dk.sundhedsdatastyrelsen.ncpeh.lms;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms01Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms09Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthParser;
import org.slf4j.Logger;

import java.util.List;

public class LmsDataParser {
    private LmsDataParser() {
        throw new IllegalStateException();
    }

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LmsDataParser.class);

    public static List<Lms01Data> parseLms01Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms01Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS01 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new LmsDataException(errorMessage, e);
        }
    }

    public static List<Lms02Data> parseLms02Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms02Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS02 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new LmsDataException(errorMessage, e);
        }
    }

    public static List<Lms09Data> parseLms09Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms09Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS09 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new LmsDataException(errorMessage, e);
        }
    }

    public static List<Lms14Data> parseLms14Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms14Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS14 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new LmsDataException(errorMessage, e);
        }
    }

    public static List<Lms15Data> parseLms15Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms15Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS15 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new LmsDataException(errorMessage, e);
        }
    }

    public static List<Lms22Data> parseLms22Data(String fixedWidthData) {
        try {
            return FixedWidthParser.parseString(fixedWidthData, Lms22Data.class);
        } catch (Exception e) {
            String errorMessage = String.format("Error parsing LMS22 data, error message: %s", e.getMessage());
            log.error(errorMessage);
            throw new LmsDataException(errorMessage, e);
        }
    }
}
