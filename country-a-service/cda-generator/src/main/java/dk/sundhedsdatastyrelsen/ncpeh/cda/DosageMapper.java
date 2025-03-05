package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.EmptyDosageStructureType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
        final var unstructuredText = getUnstructuredText(dosage);
        final var unit = mapUnit(dosage).orElse(null);
        if (unit == null) {
            // If there is no unit, it is not safe to display any dosage.
            // We still provide the free-text parts of the prescription in the narrative block.
            return new Dosage.Unstructured(unstructuredText);
        }
        switch (dosage.getType()) {
            case FAST: {
                // In "FAST", the dosage contains only "structuresFixed" and no "structuresAccordingToNeed".
                var structures = dosage.getStructuresFixed().getEmptyStructureOrStructure();
                if (structures.size() != 1) {
                    // In the Danish model, there may be several structures, to support things like 'first three months
                    // with one pill every day, then two months with one pill every other day'. It's a type of tapered
                    // dosing, so not supported.
                    return new Dosage.Unstructured(unstructuredText);
                }
                var maybePeriodAndQuantity = mapEmptyStructureOrStructure(structures.getFirst(), unit);
                return maybePeriodAndQuantity.map(periodAndQuantity -> (Dosage) new Dosage.PeriodicInterval(
                        unstructuredText,
                        false,
                        periodAndQuantity.getLeft(),
                        periodAndQuantity.getRight()))
                    .orElseGet(() -> new Dosage.Unstructured(unstructuredText));
            }
            case EFTER_BEHOV: {
                // In "EFTER_BEHOV", the dosage contains only "structuresAccordingToNeed" and no "structuresFixed".
                // TODO implement this
                return new Dosage.Unstructured(unstructuredText);
            }
            case IKKE_ANGIVET, KOMBINERET: {
                // In these cases, the dosage may contain both a need element and a fixed element. To cover 'you have to
                // take these pills at these times, and then you can add one or two more if you need them' cases.
                // We could also just ignore the type, and always handle both?
                // TODO implement this
                return new Dosage.Unstructured(unstructuredText);
            }
            default:
                log.warn("Unknown dosage type {}", dosage.getType());
                return new Dosage.Unstructured(unstructuredText);
        }
    }

    static @NonNull String getUnstructuredText(@NonNull DosageForResponseType dosage) {
        final var unstructuredTexts = new ArrayList<String>();
        if (dosage.getStructuresFixed() != null && dosage.getStructuresFixed()
            .getDosageTranslationCombined() != null && dosage.getStructuresFixed()
            .getDosageTranslationCombined()
            .getLongText() != null) {
            unstructuredTexts.add(dosage.getStructuresFixed().getDosageTranslationCombined().getLongText());
        }
        if (dosage.getStructuresAccordingToNeed() != null && dosage.getStructuresAccordingToNeed()
            .getDosageTranslationCombined() != null && dosage.getStructuresAccordingToNeed()
            .getDosageTranslationCombined()
            .getLongText() != null) {
            unstructuredTexts.add(dosage.getStructuresAccordingToNeed().getDosageTranslationCombined().getLongText());
        }
        if (dosage.getFreeText() != null && dosage.getFreeText().getText() != null) {
            unstructuredTexts.add(dosage.getFreeText().getText());
        }

        final var builder = new StringBuilder();
        for (String s : unstructuredTexts) {
            if (!builder.isEmpty()) {
                builder.append("\n\n");
            }
            builder.append(s);
        }

        return builder.isEmpty() ? "No unstructured dosage text" : builder.toString();
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
