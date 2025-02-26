package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.EmptyDosageStructureType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class DosageMapper {
    public static Dosage model(DosageForResponseType dosage) {
        switch (dosage.getType()) {
            case FAST -> {
                var structure = dosage.getStructuresFixed().getEmptyStructureOrStructure().getFirst();
                Optional<Pair<Dosage.Period, Dosage.Quantity>> pair = switch (structure) {
                    case DosageStructureForResponseType ds -> Optional.of(mapToPeriod(ds));
                    case EmptyDosageStructureType _ds -> Optional.empty();
                    default -> throw new NotImplementedException("Unsupported dosage structure type");
                };
                return new Dosage.Interval(
                    false,
                    pair.map(Pair::getLeft).orElse(null),
                    pair.map(Pair::getRight).orElse(null));
            }
            default -> throw new NotImplementedException("Work in progress");
        }

    }

    public static Pair<Dosage.Period, Dosage.Quantity> mapToPeriod(DosageStructureForResponseType structure) {
        var days = structure.getDay();
        if (days.size() != 1) {
            throw new NotImplementedException("Can only handle exactly one day for now");
        }
        var day = days.getFirst();
        var doses = day.getDose();
        var firstDose = doses.getFirst();
        if (doses.stream().anyMatch(d -> !d.getQuantity().equals(firstDose.getQuantity()))) {
            throw new NotImplementedException("We don't support split dosing yet");
        }

        return Pair.of(new Dosage.Period.Simple("d", Double.toString(1d / doses.size())), new Dosage.Quantity(firstDose.getQuantity()));
    }
}
