package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageDayType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DoseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.EmptyDosageStructureType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
/// - dosage that occurs at more than one event. The `EIVL_TS` element that represents event-based intervals only supports
///   one event code. It is possible to express multiple events by wrapping them in a `SEXP_TS`, but nobody reads them,
///   and they are hard to translate to text.
///
/// Other gotchas:
///
/// - In the Danish documentation of dosage, they mention an `<IsAccordingToNeed/>` element. But this is never actually
///   returned from calls to FMK, so the documentation seems to be outdated.
@Slf4j
public final class DosageMapper {
    private DosageMapper() {
    }

    public static @NonNull Dosage model(@NonNull DosageForResponseType dosage) {
        final var unstructuredText = getUnstructuredText(dosage);
        final var unit = mapUnit(dosage);
        if (unit == null) {
            // If there is no unit, it is not safe to display any dosage.
            // We still provide the free-text parts of the prescription in the narrative block.
            return new Dosage.Unstructured(unstructuredText);
        }
        if (dosage.getStructuresFixed() != null && dosage.getStructuresAccordingToNeed() == null && dosage.getAdministrationAccordingToSchemaInLocalSystem() == null && dosage.getFreeText() == null) {
            // Map structures that are not according to need.
            var structures = dosage.getStructuresFixed().getEmptyStructureOrStructure();
            if (structures.size() != 1) {
                // In the Danish model, there may be several structures, to support things like 'first three months
                // with one pill every day, then two months with one pill every other day'. It's a type of tapered
                // dosing, so not supported.
                return new Dosage.Unstructured(unstructuredText);
            }
            var structure = getStructureOrNull(structures.getFirst());
            if (structure == null) {
                return new Dosage.Unstructured(unstructuredText);
            }
            return mapFixedStructure(structure, unit, unstructuredText);
        }
        if (dosage.getStructuresFixed() == null && dosage.getStructuresAccordingToNeed() != null && dosage.getAdministrationAccordingToSchemaInLocalSystem() == null && dosage.getFreeText() == null) {
            // Map structures that are according to need.
            var structures = dosage.getStructuresAccordingToNeed().getEmptyStructureOrStructure();
            if (structures.size() != 1) {
                // In the Danish model, there may be several structures, to support things like 'first three months
                // with one pill every day, then two months with one pill every other day'. It's a type of tapered
                // dosing, so not supported.
                return new Dosage.Unstructured(unstructuredText);
            }
            var structure = getStructureOrNull(structures.getFirst());
            if (structure == null) {
                return new Dosage.Unstructured(unstructuredText);
            }
            // TODO handle the according-to-need cases we _can_ handle
            return new Dosage.Unstructured(unstructuredText);
        }
        // The remaining cases we can't handle. Mixed, where there are both fixed and according to need/other elements,
        // and the more unstructured ones. In those cases, we just return the unstructured one.
        return new Dosage.Unstructured(unstructuredText);
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

    static DosageStructureForResponseType getStructureOrNull(@NonNull Object emptyStructureOrStructure) {
        switch (emptyStructureOrStructure) {
            case DosageStructureForResponseType ds:
                return ds;
            case EmptyDosageStructureType ignored:
                return null;
            default:
                log.warn("Unknown dosage structure type {}", emptyStructureOrStructure.getClass().getName());
                return null;
        }
    }

    /// TODO Don't mind this docstring right now. It's more for the unstructured variant.
    ///
    /// This is where it gets complex. A structure can have any combination of iterated/not iterated, day/anyDay,
    /// endDate/dosageEndingUndetermined, iterationInterval can be set to 1 or 28.
    ///
    /// `<AnyDay>` elements can only occur in StructuresAccordingToNeed. Also, you cannot mix Day and AnyDay elements.
    /// I tried and it errors.
    ///
    /// ## Examples
    ///
    /// ### PN-1
    /// Take two when needed, as many times per day as you need.
    /// This example is a little special. It is only interpreted this way if there is exactly 1 day and that day has
    /// exactly 1 dose, and the dose is according to need.
    /// ```
    /// <StructuresAccordingToNeed>
    ///     ...
    ///     <NotIterated/>
    ///     <AnyDay><!-- Could also just be Day 1, these are equivalent if there is only 1 day -->
    ///         <Dose>
    ///             <Quantity>2</Quantity>
    ///         </Dose>
    ///     </AnyDay>
    ///     ...
    /// </StructuresAccordingToNeed>
    ///```
    ///
    /// ### PN-2
    /// Take 1 when needed, maximum 1 per day.
    /// ```
    /// <StructuresAccordingToNeed>
    ///     ...
    ///     <IterationInterval>1</IterationInterval>
    ///     <AnyDay><!-- Could also just be Day 1, these are equivalent when IterationInterval is 1 -->
    ///         <Dose>
    ///             <Quantity>1</Quantity>
    ///         </Dose>
    ///     </AnyDay>
    ///     ...
    /// </StructuresAccordingToNeed>
    ///```
    static @NonNull Dosage mapFixedStructure(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText) {
        if (structure.getNotIterated() == null && structure.getIterationInterval() == null) {
            // There's no information
            return new Dosage.Unstructured(unstructuredText);
        }
        if (structure.getNotIterated() != null) {
            return mapFixedNotIterated(structure, unit, unstructuredText);
        }
        if (structure.getIterationInterval() == 1) {
            return mapFixedIteratedDaily(structure, unit, unstructuredText);
        }
        return mapFixedIteratedNonDaily(structure, unit, unstructuredText);
    }

    static @NonNull Dosage mapFixedNotIterated(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText) {
        if (structure.getDay() == null) {
            return new Dosage.Unstructured(unstructuredText);
        }

        if (structure.getDay().size() == 1 && structure.getDay().getFirst().getDose().size() == 1) {
            var day = structure.getDay().getFirst();
            var time = day.getDose().getFirst().getTime();
            var startDateTime = structure.getStartDate().toGregorianCalendar().toZonedDateTime();
            return new Dosage.Once(
                unstructuredText,
                time == null
                    ? Either.ofLeft(Utils.convertToLocalDate(structure.getStartDate()))
                    // TODO look a little more at this time/timezone use. The FMK start date is zoned to UTC, but the
                    //  dose time is probably meant to be 'local to wherever you are'. Unless of course it's very
                    //  precise medicine, in which case it's 'local to DK time'. If you take the low-dose birth control
                    //  pills at 08:00 DK time and you travel to another time zone, you still have to take them 08:00 DK
                    //  time every day, right? How do we express that? Also, some of the dose times are things like
                    //  "noon" and "morning", that should probably be translated to event-based instead? But can't in
                    //  this case, because it's not iterated.
                    : Either.ofRight(ZonedDateTime.of(startDateTime.toLocalDate(), fmkTimeToLocalTime(time), startDateTime.getZone())),
                mapDoseToQuantity(day.getDose().getFirst(), unit));
        }

        // We can only express one non-repeated dose simply.
        // We could try to translate a non-repeated dosage to a repeated one, so we could express it, but the
        // benefit of this is small, since in that case the DK model would probably have used a repeating pattern too.
        // Or we could try to use `SEXP_TS` to do something clever, but no one displays these complex expressions
        // yet, and there is work being done on subordinate substanceAdministrations which will also be
        // able to express this, so right now we just don't try more.
        return new Dosage.Unstructured(unstructuredText);
    }

    static @NonNull Dosage mapFixedIteratedDaily(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText) {
        var days = structure.getDay();
        if (days.size() != 1) {
            return new Dosage.Unstructured(unstructuredText);
        }

        // Either this is something you do once per day, or it is something you do several times every day.
        var day = days.getFirst();
        var doses = day.getDose();
        var firstDose = doses.getFirst();

        if (doses.size() == 1) {
            // TODO there's a time aspect of the single dose too. Can probably be expressed by the <phase><low> element.

            return new Dosage.PeriodicInterval(
                unstructuredText,
                true,
                new Dosage.Period.Simple("d", BigDecimal.valueOf(1)),
                mapDoseToQuantity(firstDose, unit));
        }

        if (!dosesHaveSameQuantity(doses)) {
            // This is the case mentioned above where there are different quantities at different times of the day.
            return new Dosage.Unstructured(unstructuredText);
        }

        if (doses.stream().anyMatch(d -> d.getTime() != null)) {
            // TODO identify the cases where the time distance between the doses is the same and
            //  handle those. It's complex, but can in some cases be expressed with <phase><low>.
            return new Dosage.Unstructured(unstructuredText);
        }

        if (24 % doses.size() > 0) {
            // We can't expect other countries to handle fractional hours, because we don't.
            return new Dosage.Unstructured(unstructuredText);
        }

        return new Dosage.PeriodicInterval(
            unstructuredText,
            true,
            new Dosage.Period.Simple(
                "h",
                BigDecimal.valueOf(24).divide(BigDecimal.valueOf(doses.size()), RoundingMode.UNNECESSARY)),
            mapDoseToQuantity(firstDose, unit));
    }

    static @NonNull Dosage mapFixedIteratedNonDaily(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText) {
        // If the days within the iteration interval all have the same time distance, we can express this in the ehdsi
        // model with "every N days/weeks/months". But then they also all have to have only a single dose, and the doses
        // must all be the same quantity. If they have a time element, it also has to be the same.
        var dayDistance = getDayDistance(structure.getIterationInterval(), structure.getDay());
        var allSingleDose = structure.getDay().stream().allMatch(d -> d.getDose().size() == 1);
        var allDoses = structure.getDay().stream().flatMap(d -> d.getDose().stream()).toList();
        if (dayDistance.isEmpty() || !allSingleDose || !dosesHaveSameQuantity(allDoses) || !dosesHaveSameTime(allDoses)) {
            return new Dosage.Unstructured(unstructuredText);
        }

        // TODO Add support for when they all have the same time element. Can be supported with the <phase><low> element.
        if (allDoses.stream().anyMatch(d -> d.getTime() != null)) {
            return new Dosage.Unstructured(unstructuredText);
        }

        return new Dosage.PeriodicInterval(
            unstructuredText,
            false,
            new Dosage.Period.Simple("d", BigDecimal.valueOf(dayDistance.get())),
            mapDoseToQuantity(allDoses.getFirst(), unit)
        );
    }

    static LocalTime fmkTimeToLocalTime(@NonNull String fmkTime) {
        // TODO fmkTime can be 'morning', 'noon', etc, or an actual time.
        // I'm unsure whether this should be allowed to be null.
        return LocalTime.of(12, 0);
    }

    static Dosage.Unit mapUnit(@NonNull DosageForResponseType dosage) {
        var singular = dosage.getUnitText();
        var plural = dosage.getUnitTexts();
        var text = singular != null
            ? singular
            : plural == null
            ? null
            : plural.getSingular() != null
            ? plural.getSingular()
            : plural.getPlural();
        return text == null ? null : new Dosage.Unit.Translated(text);
    }

    static @NonNull Dosage.Quantity mapDoseToQuantity(@NonNull DoseType dose, @NonNull Dosage.Unit unit) {
        // TODO add support for max/min dosage. The Danish `DoseType` also has `maxDosage` and `minDosage`.
        return new Dosage.Quantity(dose.getQuantity(), unit, null);
    }

    public static Optional<Integer> getDayDistance(int iterationInterval, List<DosageDayType> days) {
        var firstDay = days.getFirst();
        if (firstDay.getNumber() != 1) {
            // Sanity check. I don't think this will ever happen.
            return Optional.empty();
        }
        if (days.size() == 1) {
            return Optional.of(iterationInterval);
        }

        var firstInterval = days.get(1).getNumber() - firstDay.getNumber();
        for (int i = 1; i < days.size(); i++) {
            if (days.get(i).getNumber() - days.get(i - 1).getNumber() != firstInterval) {
                return Optional.empty();
            }
        }

        return iterationInterval - days.getLast()
            .getNumber() + 1 == firstInterval ? Optional.of(firstInterval) : Optional.empty();
    }

    static boolean dosesHaveSameQuantity(@NonNull List<DoseType> doses) {
        var firstDose = doses.getFirst();
        // TODO this doesn't cover max/min dosage types.
        return doses.stream().anyMatch(d -> !d.getQuantity().equals(firstDose.getQuantity()));
    }

    static boolean dosesHaveSameTime(@NonNull List<DoseType> doses) {
        var firstDoseTime = doses.getFirst().getTime();
        return doses.stream().allMatch(d -> d.getTime().equals(firstDoseTime));
    }
}
