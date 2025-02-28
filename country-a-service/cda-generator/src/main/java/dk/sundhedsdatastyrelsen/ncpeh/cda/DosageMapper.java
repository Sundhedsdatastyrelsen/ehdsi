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
        final var unit = mapUnit(dosage).orElse(null);
        if(unit == null) {
            // If there is no unit, it is not safe to display any dosage.
            // We still provide the free-text parts of the prescription in the narrative block.
            return new Dosage.Empty();
        }
        switch (dosage.getType()) {
            case FAST:
                var structures = dosage.getStructuresFixed().getEmptyStructureOrStructure();
                if (structures.size() != 1) {
                    log.warn("Only 1 dosage structure supported, but was {}.", structures.size());
                    return new Dosage.Empty();
                }
                var maybePair = mapEmptyStructureOrStructure(structures.getFirst(), unit);
                return maybePair.map(pair -> (Dosage) new Dosage.Interval(
                        false,
                        pair.getLeft(),
                        pair.getRight()))
                    .orElseGet(Dosage.Empty::new);
            default:
                log.warn("Unknown dosage type {}", dosage.getType());
                return new Dosage.Empty();
        }
    }

    static Optional<Pair<Dosage.Period, Dosage.Quantity>> mapEmptyStructureOrStructure(Object emptyStructureOrStructure, Dosage.Unit unit) {
        switch (emptyStructureOrStructure) {
            case DosageStructureForResponseType ds:
                return mapToPeriod(ds, unit);
            case EmptyDosageStructureType ignored:
                return Optional.empty();
            default:
                log.warn("Unknown dosage structure type {}", emptyStructureOrStructure.getClass().getName());
                return Optional.empty();
        }
    }

    static Optional<Pair<Dosage.Period, Dosage.Quantity>> mapToPeriod(DosageStructureForResponseType structure, Dosage.Unit unit) {
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
                new Dosage.Quantity(firstDose.getQuantity(), unit)));
    }

    static Optional<Dosage.Unit> mapUnit(DosageForResponseType dosage) {
        var singular = dosage.getUnitText();
        var plural = dosage.getUnitTexts();
        var text = singular != null
            ? singular
            : plural == null
            ? null
            : plural.getSingular() != null
            ? plural.getSingular()
            : plural.getPlural();
        return text == null ? Optional.empty() : Optional.of(new Dosage.Unit.Translated(text));
    }
}
