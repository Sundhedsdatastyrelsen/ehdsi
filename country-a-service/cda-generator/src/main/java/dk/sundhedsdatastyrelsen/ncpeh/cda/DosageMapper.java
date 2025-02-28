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
        switch (dosage.getType()) {
            case FAST:
                var structures = dosage.getStructuresFixed().getEmptyStructureOrStructure();
                if (structures.size() != 1) {
                    log.warn("Only 1 dosage structure supported, but was {}.", structures.size());
                    return new Dosage.Empty();
                }
                var maybePair = mapEmptyStructureOrStructure(structures.getFirst());
                return maybePair.map(pair -> (Dosage) new Dosage.Interval(
                        false,
                        pair.getLeft(),
                        pair.getRight(),
                        Collections.emptyList()))
                    .orElseGet(Dosage.Empty::new);
            default:
                log.warn("Unknown dosage type {}", dosage.getType());
                return new Dosage.Empty();
        }
    }

    public static Optional<Pair<Dosage.Period, Dosage.Quantity>> mapEmptyStructureOrStructure(Object emptyStructureOrStructure) {
        switch (emptyStructureOrStructure) {
            case DosageStructureForResponseType ds:
                return mapToPeriod(ds);
            case EmptyDosageStructureType ignored:
                return Optional.empty();
            default:
                log.warn("Unknown dosage structure type {}", emptyStructureOrStructure.getClass().getName());
                return Optional.empty();
        }
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
