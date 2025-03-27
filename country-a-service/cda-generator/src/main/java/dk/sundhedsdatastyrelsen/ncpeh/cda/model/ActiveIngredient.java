package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/// Active ingredients in medications.
///
/// DK does not have the ingredients encoded, so we can only provide a name and a strength.
/// Additionally, for drugs with more than one ingredient, the strength is not encoded either,
/// so we return unstructured active ingredients in those cases.
///
/// If possible, must use both numerator and denominator. These can be on the form 3 "mg" / 2 "mL". If not possible, it is
/// allowed to use something like 1.5 "mg/mL" as the numerator, and then 1 "1" as the denominator, where "1" is a special
/// unit that means "per countable unit" - like a tablet or a tube.
@Value
@Builder(toBuilder = true)
public class ActiveIngredient {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.US));
    @NonNull BigDecimal numerator;
    @NonNull BigDecimal denominator;
    /// Unit must be taken from the eHDSIUnit value set, https://art-decor.ehdsi.eu/publication/epsos-html-20240422T073854/voc-1.3.6.1.4.1.12559.11.10.1.3.1.42.16-2024-04-16T112900.html.
    @NonNull String numeratorUnit;
    @NonNull String denominatorUnit;
    @NonNull String name;
    String translation;

    public @NonNull String getNumerator() {
        return decimalFormat.format(numerator);
    }

    public @NonNull String getDenominator() {
        return decimalFormat.format(denominator);
    }
}
