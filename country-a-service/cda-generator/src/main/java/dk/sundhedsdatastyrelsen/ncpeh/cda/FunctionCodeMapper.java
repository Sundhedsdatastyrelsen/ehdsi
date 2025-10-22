package dk.sundhedsdatastyrelsen.ncpeh.cda;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

/// The function code should be mapped by the translation layer, but it is metadata also present in L1-unstructured
/// documents, and these are not mapped at all. So we have to do it in the code until that is fixed.
public final class FunctionCodeMapper {
    private FunctionCodeMapper() {
    }

    public static @NonNull Pair<String, String> mapToFunctionCode(@NonNull String role) {
        return switch (role) {
            // Optiker
            case "4498" -> Pair.of("2267", "Optometrists and ophthalmic opticians");
            // Fysioterapeut
            case "5151" -> Pair.of("2264", "Physiotherapists");
            // Social og -sundhedsassistent
            case "5152" -> Pair.of("3221", "Nursing associate professionals");
            // Ergoterapeut
            case "5153" -> Pair.of("3257", "Environmental and occupational health inspectors and associates");
            // Fodterapeut
            case "5155" -> Pair.of("3259", "Health associate professionals not elsewhere classified");
            // Radiograf
            case "5158" -> Pair.of("3211", "Medical imaging and therapeutic equipment technicians");
            // Bioanalytiker
            case "5159" -> Pair.of("3212", "Medical and pathology laboratory technicians");
            // Sygeplejerske
            case "5166" -> Pair.of("2221", "Nursing professionals");
            // Jordemoder
            case "5175" -> Pair.of("2222", "Midwifery professionals");
            // Kontaktlinseoptiker/Optometrist
            case "5176" -> Pair.of("2267", "Optometrists and ophthalmic opticians");
            // Kiropraktor
            case "5265" -> Pair.of("3259", "Health associate professionals not elsewhere classified");
            // Tandplejer
            case "5431" -> Pair.of("3251", "Dental assistants and therapists");
            // Klinisk tandtekniker
            case "5432" -> Pair.of("3214", "Medical and dental prosthetic technicians");
            // Tandlæge
            case "5433" -> Pair.of("2261", "Dentists");
            // Klinisk diætist
            case "5451" -> Pair.of("2265", "Dieticians and nutritionists");
            // Læge
            case "7170" -> Pair.of("221", "Medical doctors");
            // Bandagist
            case "9495" -> Pair.of("3259", "Health associate professionals not elsewhere classified");
            // Osteopat
            case "A511" -> Pair.of("3259", "Health associate professionals not elsewhere classified");
            // Behandlerfarmaceut
            case "B511" -> Pair.of("2262", "Pharmacists");
            // Ambulancebehandler
            case "C511" -> Pair.of("3258", "Ambulance workers");
            // For "Erstatningsautorisation" and unknown values, we have to return something, so we return the most
            // general one.
            default -> Pair.of("22", "Healthcare professionals");
        };
    }
}
