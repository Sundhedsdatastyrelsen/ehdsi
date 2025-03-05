package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.EmptyDosageStructureType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/// Maps from the FMK ordination representation of dosage to the eHDSI substanceAdministration
/// representation of dosage.
///
/// We're trying to map from something that looks like this:
///
/// ```xml
/// <Dosage>
///     <!-- Bemærk fra og med Extension E2 vil UnitText(s) elementer ikke have en source attribut -->
///     <UnitTexts source="Local">
///         <Singular>tablet</Singular>
///         <Plural>tabletter</Plural>
///     </UnitTexts>
///     <StructuresFixed>
///         <Structure>
///             <IterationInterval>1</IterationInterval>
///             <StartDate>2014-11-18</StartDate>
///             <EndDate>2014-11-30</EndDate>
///             <Day>
///                 <Number>1</Number>
///                 <Dose>
///                     <Time>morning</Time>
///                     <Quantity>2</Quantity>
///                 </Dose>
///                 <Dose>
///                     <Time>evening</Time>
///                     <Quantity>2</Quantity>
///                 </Dose>
///             </Day>
///             <DosageTranslation>
///                 <ShortText>1 tablet morgen og aften</ShortText>
///                 <LongText>Doseringsforløbet starter mandag den 18. november 2013 og gentages hver dag:
///                     Doseringsforløb:
///                     1 tablet morgen og aften</LongText>
///                 <AverageDailyDosage>2</AverageDailyDosage>
///             </DosageTranslation>
///         </Structure>
///     </StructuresFixed>
///     <Type>fast</Type>
/// </Dosage>
///```
///
/// To something that looks like this:
///
/// ```xml
/// <substanceAdministration classCode="SBADM" moodCode="INT">
///     ...
///     <effectiveTime xsi:type="IVL_TS">
///         <low value="20141118" />
///         <high value="20141130" />
///     </effectiveTime>
///     <effectiveTime operator="A" type="PIVL_TS">
///         <period unit="h" value="12" />
///     </effectiveTime>
///     <doseQuantity value="2"/>
///     ...
/// </substanceAdministration>
///```
///
/// There are things that can be modeled in the Danish model, that cannot yet be
/// modeled in the ehdsi model.
///
/// - doses of different sizes (tapered dosing, some cases of handling 2.25 mg when only 2 and 2.5 mg tablets exist).
///   At some point, `subordinate substanceAdministration` may be added that could handle this, but they are not
///   implemented yet.
/// - doses that repeat at specific times of the day. In the danish model, it could say "take a pill in the morning and
///   one around lunch for 7 days". There is the possibility of using the `SEXP_TS` type to design a set that includes
///   only these two times, but there are no examples of this.
/// - dosage that includes breaks. Birth control pills are an example of this, but it could be as arbitrary as "take 1
///   pill on day 1, one pill on day 3, and one pill on day 7. Repeat every 2 weeks". This could also potentially be
///   solved with the `SEXP_TS` type, and there _is_ an example of birth control pills [here](https://webgate.ec.europa.eu/fpfis/wikis/pages/viewpage.action?spaceKey=EHDSI&title=General),
///   but that's the only one I've been able to find, it only covers that specific case, and it's ambiguous. It doesn't
///   specify when to start and end within the 28 days.
@Slf4j
public class DosageMapper {
    public static Dosage model(DosageForResponseType dosage) {
        final var unit = mapUnit(dosage).orElse(null);
        if (unit == null) {
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
