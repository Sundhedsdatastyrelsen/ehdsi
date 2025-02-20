package dk.sundhedsdatastyrelsen.ncpeh.cda.dosage;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;

/**
 * Convert FMK structured dosage information to international format.
 * <p/>
 * Inspired by: <a href="https://github.com/trifork/fmk-dosis-til-tekst-ts">trifork/fmk-dosis-til-tekst-ts</a>.
 */
public interface DosageConverter {
    boolean canConvert(DosageForResponseType dosage);

    Dosage convert(DosageForResponseType dosage);
}
