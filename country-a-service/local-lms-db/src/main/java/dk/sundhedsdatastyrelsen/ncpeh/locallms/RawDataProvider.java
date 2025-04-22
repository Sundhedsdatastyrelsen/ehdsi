package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import java.io.IOException;
import java.io.InputStream;

/**
 * Provides an input stream for an LMS table, e.g., by downloading it from FTP.
 */
@FunctionalInterface
public interface RawDataProvider {
    InputStream get(Specs.Table table) throws IOException;
}
