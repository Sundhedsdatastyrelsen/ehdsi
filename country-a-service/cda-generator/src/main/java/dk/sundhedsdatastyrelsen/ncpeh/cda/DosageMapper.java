package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.EmptyDosageStructureType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Optional;

@Slf4j
public class DosageMapper {
    public static Dosage model(DosageForResponseType dosage) {
        return switch (dosage.getType()) {
            case FAST -> {
                var structure = dosage.getStructuresFixed().getEmptyStructureOrStructure().getFirst();
                Optional<Pair<Dosage.Period, Dosage.Quantity>> pair = switch (structure) {
                    case DosageStructureForResponseType ds -> mapToPeriod(ds);
                    case EmptyDosageStructureType ignored -> Optional.empty();
                    default -> {
                        log.warn("Unknown dosage structure type {}", structure.getClass().getName());
                        yield Optional.empty();
                    }
                };
                yield new Dosage.Interval(
                    false,
                    pair.map(Pair::getLeft).orElse(null),
                    pair.map(Pair::getRight).orElse(null),
                    Collections.emptyList());
            }
            default -> {
                log.warn("Unknown dosage type {}", dosage.getType());
                yield new Dosage.Empty();
            }
        };
    }

    public static Optional<Pair<Dosage.Period, Dosage.Quantity>> mapToPeriod(DosageStructureForResponseType structure) {
        var days = structure.getDay();
        if (days.size() != 1) {
            log.warn("Can only handle exactly one day for now.");
            return Optional.empty();
        }
        var day = days.getFirst();
        var doses = day.getDose();
        var firstDose = doses.getFirst();
        if (doses.stream().anyMatch(d -> !d.getQuantity().equals(firstDose.getQuantity()))) {
            log.warn("Can't handle split dosing yet.");
            return Optional.empty();
        }

        return Optional.of(
            Pair.of(
                new Dosage.Period.Simple(
                    "d",
                    BigDecimal.valueOf(1).divide(BigDecimal.valueOf(doses.size()), 3, RoundingMode.HALF_EVEN)),
                new Dosage.Quantity(firstDose.getQuantity())));
    }
}
