package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

public sealed interface Dosage {
    String getTag();

    /**
     * An interval based dosage, e.g. "2 pills 3 times per day".
     */
    @Value
    class Interval implements Dosage {
        String tag = "interval";
        boolean institutionSpecified;
        Period period;
        Quantity quantity;
    }

    /**
     * Period of time used for dosage.
     * <p>
     * Can be used to describe things like "twice per day" (unit: "d", value: .5) or every 4 hours (unit: "h", value: 4).
     * <p>
     * In art-decor, called "Physical quantity" https://wiki.art-decor.org/index.php?title=DTr1_PQ, but part of Periodic
     * Interval of Timezones (https://wiki.art-decor.org/index.php?title=DTr1_PIVL_TS).
     */
    sealed interface Period {
        @NonNull
        String getTag();

        @Value
        class Simple implements Period {
            String tag = "Simple";

            /**
             * Unit of time. It is not specified what values are allowed, but examples cite "h" and "d", so I think it's
             * intended to be from the UCUM codes seen here: https://ucum.org/ucum, as they are used elsewhere in cda. The
             * possibly interesting values from that set are "min" (minute), "h" (hour), "d" (day), "wk" (week), and "mo"
             * (month).
             */
            @NonNull String unit;

            /**
             * Can be fractional (eg 0.5, 0.25) or whole (eg 2, 3).
             */
            @NonNull String value; // Or float/double?
        }
    }

    /**
     * Quantity of dosage.
     * <p>
     * This can take one of several forms. A null flavor, simple with just a number (value), a number and a unit, min
     * and max with translations etc. We have the translations of the units elsewhere, so we do not need to code them
     * here. We start with the simplest and extend as needed.
     * <p>
     * Dose quantity is described <a href="https://art-decor.ehdsi.eu/publication/epsos-html-20240422T073854/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.2-2023-07-03T135239.html">here</a>.
     */
    @Value
    class Quantity {
        @NonNull BigDecimal value;
    }
}
