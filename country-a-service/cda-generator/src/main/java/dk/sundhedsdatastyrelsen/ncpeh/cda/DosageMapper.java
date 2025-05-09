package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageAnyDayType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageDayType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DoseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.EmptyDosageStructureType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

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
/// - according to need/per need medicine with minimum doses.
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
            return new Dosage.Unstructured(unstructuredText, "Could not parse dosage unit.");
        }
        if (dosage.getAdministrationAccordingToSchemaInLocalSystem() != null || dosage.getFreeText() != null) {
            // We can't handle it if one of these two are set - they are escape hatches in the DK model for unstructured
            // text, so the best we can do is to return everything unstructured.
            return new Dosage.Unstructured(unstructuredText, "DK model not structured.");
        }
        if (dosage.getStructuresFixed() == null && dosage.getStructuresAccordingToNeed() == null
            || dosage.getStructuresFixed() != null && dosage.getStructuresAccordingToNeed() != null) {
            // If they are both missing or both there, we can't express it in the current ehdsi model.
            return new Dosage.Unstructured(unstructuredText, "Both fixed and according to need or both missing.");
        }

        var structures = dosage.getStructuresFixed() != null ? dosage.getStructuresFixed() : dosage.getStructuresAccordingToNeed();
        if (structures.getEmptyStructureOrStructure().size() != 1) {
            // In the Danish model, there may be several structures, to support things like 'first three months
            // with one pill every day, then two months with one pill every other day'. It's a type of tapered
            // dosing, so not supported.
            return new Dosage.Unstructured(unstructuredText, "Multiple dosage structures.");
        }
        var structure = getStructureOrNull(structures.getEmptyStructureOrStructure().getFirst());
        if (structure == null) {
            return new Dosage.Unstructured(unstructuredText, "No dosage structure.");
        }
        return mapStructure(structure, unit, unstructuredText, dosage.getStructuresAccordingToNeed() != null);
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

    /// `<AnyDay>` elements can only occur when the doses are according to need, and cannot be mixed with normal days -
    /// I tried and it errors when creating them. `<AnyDay>` also is a looser prescription than just a normal `<Day>`,
    /// so currently we just map all `<AnyDay>` elements to `<Day>` elements.
    static @NonNull Dosage mapStructure(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText, boolean isAccordingToNeed) {
        if (structure.getNotIterated() == null && structure.getIterationInterval() == null) {
            // There's no information
            return new Dosage.Unstructured(unstructuredText, "Structure neither iterated nor not iterated.");
        }

        // Map AnyDay to Day.
        // The only case where this makes a difference is when the doses have a structure spanning several days. In that
        // case, it would be a little more precise to specify a `<phase>` element with a `<width>`, but it would
        // interfere with the time handling there and the benefit is minimal - "0-3 pills over 7 days, repeat every 7
        // days" as opposed to "0-3 pills every 7 days". And the extra information is in the unstructured text.
        var structureWithoutAnyDay = structure.getAnyDay() == null ? structure : structure.newCopyBuilder()
            .withAnyDay(null)
            .withDay(anyDayToNormalDay(structure.getAnyDay()))
            .build();
        if (isUnboundedAccordingToNeedCase(structureWithoutAnyDay, isAccordingToNeed)) {
            return mapUnboundedAccordingToNeed(structureWithoutAnyDay, unit, unstructuredText);
        }
        if (structureWithoutAnyDay.getNotIterated() != null) {
            return mapNotIterated(structureWithoutAnyDay, unit, unstructuredText, isAccordingToNeed);
        }
        if (structureWithoutAnyDay.getIterationInterval() == 1) {
            return mapIteratedDaily(structureWithoutAnyDay, unit, unstructuredText, isAccordingToNeed);
        }
        return mapIteratedNonDaily(structureWithoutAnyDay, unit, unstructuredText, isAccordingToNeed);
    }

    static @NonNull Dosage mapUnboundedAccordingToNeed(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText) {
        var day = structure.getAnyDay() != null
            ? anyDayToNormalDay(structure.getAnyDay())
            : structure.getDay().getFirst();
        return new Dosage.Unbounded(unstructuredText, mapDoseToQuantity(day.getDose().getFirst(), unit, true));
    }

    static @NonNull Dosage mapNotIterated(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText, boolean isAccordingToNeed) {
        if (structure.getDay() == null) {
            return new Dosage.Unstructured(unstructuredText, "No days in not iterated structure.");
        }

        if (structure.getDay().size() == 1 && structure.getDay().getFirst().getDose().size() == 1) {
            var day = structure.getDay().getFirst();
            var time = day.getDose().getFirst().getTime();
            var startDateTime = structure.getStartDate().toGregorianCalendar().toZonedDateTime();
            var transformedTime = time == null ? null : fmkTimeToLocalTime(time);
            if (time != null && transformedTime == null) {
                // Can't return the structured information without the time element.
                return new Dosage.Unstructured(unstructuredText, "Not iterated time could not be mapped.");
            }
            return new Dosage.Once(
                unstructuredText,
                transformedTime == null
                    ? Either.ofLeft(Utils.convertToLocalDate(structure.getStartDate()))
                    : Either.ofRight(LocalDateTime.of(startDateTime.toLocalDate(), transformedTime.getLeft())),
                mapDoseToQuantity(day.getDose().getFirst(), unit, isAccordingToNeed));
        }

        // We can only express one non-repeated dose simply.
        // We could try to translate a non-repeated dosage to a repeated one, so we could express it, but the
        // benefit of this is small, since in that case the DK model would probably have used a repeating pattern too.
        // Or we could try to use `SEXP_TS` to do something clever, but no one displays these complex expressions
        // yet, and there is work being done on subordinate substanceAdministrations which will also be
        // able to express this, so right now we just don't try more.
        return new Dosage.Unstructured(unstructuredText, "Multiple non-repeated doses.");
    }

    static @NonNull Dosage mapIteratedDaily(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText, boolean isAccordingToNeed) {
        var days = structure.getDay();
        if (days == null || days.size() != 1) {
            return new Dosage.Unstructured(unstructuredText, "Daily structure with days.size() != 1.");
        }

        // Either this is something you do once per day, or it is something you do several times every day.
        var day = days.getFirst();
        var doses = day.getDose();
        var firstDose = doses.getFirst();

        if (doses.size() == 1) {
            return new Dosage.PeriodicInterval(
                unstructuredText,
                true,
                new Dosage.Period.Simple("d", BigDecimal.valueOf(1)),
                mapDoseToQuantity(firstDose, unit, isAccordingToNeed),
                getStartTime(structure));
        }

        if (!dosesHaveSameQuantity(doses)) {
            // This is the case mentioned above where there are different quantities at different times of the day.
            return new Dosage.Unstructured(unstructuredText, "Daily doses with different quantities.");
        }

        if (!dosesTimeDistanceIsTheSame(doses.stream().map(DoseType::getTime).toList())) {
            return new Dosage.Unstructured(unstructuredText, "Daily doses with different times.");
        }

        if (24 % doses.size() > 0) {
            // We can't expect other countries to handle fractional hours, because we don't.
            return new Dosage.Unstructured(unstructuredText, "Daily dose doesn't fit 24h.");
        }

        return new Dosage.PeriodicInterval(
            unstructuredText,
            true,
            new Dosage.Period.Simple(
                "h",
                BigDecimal.valueOf(24).divide(BigDecimal.valueOf(doses.size()), RoundingMode.UNNECESSARY)),
            mapDoseToQuantity(firstDose, unit, isAccordingToNeed),
            getStartTime(structure));
    }

    static @NonNull Dosage mapIteratedNonDaily(@NonNull DosageStructureForResponseType structure, @NonNull Dosage.Unit unit, @NonNull String unstructuredText, boolean isAccordingToNeed) {
        // If the days within the iteration interval all have the same time distance, we can express this in the ehdsi
        // model with "every N days/weeks/months". But then they also all have to have only a single dose, and the doses
        // must all be the same quantity. If they have a time element, it also has to be the same.
        var dayDistance = getDayDistance(structure.getIterationInterval(), structure.getDay());
        var allSingleDose = structure.getDay().stream().allMatch(d -> d.getDose().size() == 1);
        var allDoses = structure.getDay().stream().flatMap(d -> d.getDose().stream()).toList();
        if (dayDistance.isEmpty() || !allSingleDose || !dosesHaveSameQuantity(allDoses) || !dosesHaveSameTime(allDoses)) {
            return new Dosage.Unstructured(unstructuredText, "Iterated non daily with unequal distance, multiple doses per day, different quantities, or different times.");
        }

        return new Dosage.PeriodicInterval(
            unstructuredText,
            false,
            new Dosage.Period.Simple("d", BigDecimal.valueOf(dayDistance.get())),
            mapDoseToQuantity(allDoses.getFirst(), unit, isAccordingToNeed),
            getStartTime(structure)
        );
    }

    /// The mapping of "morning" etc. to specific times has been verified by semantics 2025-03-10:
    ///
    /// > Vi går med 8, 12, 18 og 22. Det er nok det, som er tættest på, hvis man fx er indlagt, fortæller min
    /// > sygeplejeske-kilde. Så går man til ro ved 22-tiden for natten.
    ///
    /// @return Null if the time cannot be expressed, or a pair of LocalTime and a boolean whether the time is precise
    /// or not.
    static Pair<LocalTime, Boolean> fmkTimeToLocalTime(@NonNull String fmkTime) {
        switch (fmkTime) {
            case "morning":
                return Pair.of(LocalTime.of(8, 0), false);
            case "noon":
                return Pair.of(LocalTime.of(12, 0), false);
            case "evening":
                return Pair.of(LocalTime.of(18, 0), false);
            case "night":
                return Pair.of(LocalTime.of(22, 0), false);
            default:
                break;
        }
        // Not one of the known cases, so we try to parse to time.
        // 1-2 digits, then a single any character, then 2 digits, and no more than that.
        var pattern = Pattern.compile("^(\\d?\\d).(\\d\\d)$");
        var matcher = pattern.matcher(fmkTime);
        if (!matcher.matches()) {
            return null;
        }
        var hours = Integer.parseInt(matcher.group(1));
        var minutes = Integer.parseInt(matcher.group(2));
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            return null;
        }
        return Pair.of(LocalTime.of(hours, minutes), true);
    }

    static Dosage.Unit mapUnit(@NonNull DosageForResponseType dosage) {
        var singular = dosage.getUnitText();
        var plural = dosage.getUnitTexts();
        // Get the first text that exists, and prioritize singular over plural. This is complex to write however you do it.
        var text = singular != null
            ? singular
            : plural == null
            ? null
            : plural.getSingular() != null
            ? plural.getSingular()
            : plural.getPlural();
        return text == null ? null : new Dosage.Unit.Translated(text);
    }

    static Dosage.Quantity mapDoseToQuantity(@NonNull DoseType dose, @NonNull Dosage.Unit unit, boolean isAccordingToNeed) {
        if (dose.getQuantity() != null) {
            return new Dosage.Quantity(dose.getQuantity(), unit, isAccordingToNeed ? BigDecimal.ZERO : null);
        }
        // This is interpreting a max value and a missing min value as 0-max, which I'm pretty sure is correct.
        var min = Optional.ofNullable(dose.getMinimalQuantity()).orElse(BigDecimal.ZERO);
        var max = dose.getMaximalQuantity();
        if (max == null) {
            // I don't think we will ever hit a case where only minimum is set, but if we do, what does that mean?
            // I think we should just say we don't know the quantity in that case.
            return null;
        }

        if (isAccordingToNeed && !min.equals(BigDecimal.ZERO) && !min.equals(BigDecimal.ONE) && !min.equals(max)) {
            // No way to describe in ehdsi that you have to take a minimum of 2, but you can choose not to take any.
            return null;
        }
        return new Dosage.Quantity(max, unit, isAccordingToNeed ? BigDecimal.ZERO : min);
    }

    /// If the number of days between each dose is the same, get that number.
    ///
    /// When there is an iteration interval, the days repeat every iteration interval days, and the days' numbers are
    /// limited to the iteration interval. What we're trying to do here is to identify whether we can express the
    /// number of days between doses as a single day number - it can't vary.
    ///
    /// The iteration interval is important, because that's how many days go by from the last day until the schema
    /// repeats. So to check that the distance from the last day to the first day is the same as the other distances,
    /// we need to check how far the last day is from the end of the interval.
    static Optional<Integer> getDayDistance(int iterationInterval, @NonNull List<DosageDayType> days) {
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

        // In practice, we probably won't reach this for relevant cases. Since we require that time and quantity is the
        // same for all the days, and the distance between them is the same, the cases
        // (iterationInterval: 4, days: [1, 3]) and (iterationInterval: 2, days: [1]) are the same, and FMK tries to
        // simplify this when they save.
        return iterationInterval - days.getLast()
            .getNumber() + 1 == firstInterval ? Optional.of(firstInterval) : Optional.empty();
    }

    static boolean dosesHaveSameQuantity(@NonNull List<DoseType> doses) {
        var firstDose = doses.getFirst();
        return doses.stream().allMatch(d ->
            Objects.equals(firstDose.getQuantity(), d.getQuantity()) &&
                Objects.equals(firstDose.getMaximalQuantity(), d.getMaximalQuantity()) &&
                Objects.equals(firstDose.getMinimalQuantity(), d.getMinimalQuantity()));
    }

    static boolean dosesHaveSameTime(@NonNull List<DoseType> doses) {
        var firstDoseTime = doses.getFirst().getTime();
        return doses.stream().allMatch(d -> Objects.equals(d.getTime(), firstDoseTime));
    }

    /// If all the times are morning/noon/evening/night, there's some leverage so we can stretch most of those to
    /// every 6/8/12 hours.
    static class DoseTimeCases {
        private DoseTimeCases() {
        }

        static final String morning = "morning";
        static final String noon = "noon";
        static final String evening = "evening";
        static final String night = "night";
        static final List<List<String>> allowedCases = List.of(
            // these are about 12 hours
            List.of(morning, evening),
            List.of(morning, night),
            List.of(noon, night),
            // these are about 8 hours
            List.of(morning, noon, evening),
            List.of(morning, noon, night),
            List.of(morning, evening, night),
            // this is about 6 hours
            List.of(morning, noon, evening, night));
    }

    static boolean dosesTimeDistanceIsTheSame(@NonNull List<String> doseTimes) {
        // If none of them have times, or there is only one, they implicitly always have the same distance.
        if (doseTimes.stream().allMatch(Objects::isNull) || doseTimes.size() == 1) {
            return true;
        }

        // But now that we know that one of them has a time, none of the others should be null - I don't think this is
        // possible, but it's handled nonetheless.
        if (doseTimes.stream().anyMatch(Objects::isNull)) {
            return false;
        }

        // There are some specific cases we allow with the loose times used by FMK. These are spelled out in a helper class.
        if (DoseTimeCases.allowedCases.stream().anyMatch(option -> doseTimes.size() == option.size()
            && doseTimes.containsAll(option) && option.containsAll(doseTimes))) {
            return true;
        }

        // Finally, we try to match the times to local times, and see whether they have the same individual distance.
        var localTimes = doseTimes.stream()
            .map(DosageMapper::fmkTimeToLocalTime)
            .filter(Objects::nonNull)
            .map(Pair::getLeft)
            .sorted()
            .toList();

        if (localTimes.size() != doseTimes.size()) {
            // Some of the times couldn't be mapped.
            return false;
        }

        // Compare all the distances between the individual doses. The distance between the last and the first times
        // will always be negative, as they are ordered (23.00 to 03.00 = -20h). So we add a day.
        var lastDistance = Duration.between(localTimes.getLast(), localTimes.getFirst()).plusDays(1);
        return IntStream.range(0, localTimes.size() - 1)
            .allMatch(i -> Duration.between(localTimes.get(i), localTimes.get((i + 1)))
                .equals(lastDistance));
    }

    /// This case has a specific meaning, in that there is no limit to how many times you're allowed to take the medicine
    /// every day.
    static boolean isUnboundedAccordingToNeedCase(@NonNull DosageStructureForResponseType structure, boolean isAccordingToNeed) {
        return isAccordingToNeed && structure.getNotIterated() != null
            && (structure.getAnyDay() != null && structure.getAnyDay().getDose().size() == 1
            || structure.getDay().size() == 1 && structure.getDay().getFirst().getDose().size() == 1);
    }

    static @NonNull DosageDayType anyDayToNormalDay(@NonNull DosageAnyDayType anyDay) {
        var copiedDoses = anyDay.getDose()
            .stream()
            .map(d -> DoseType.builder()
                .withQuantity(d.getQuantity())
                .withMaximalQuantity(d.getMaximalQuantity())
                .withMinimalQuantity(d.getMinimalQuantity())
                .withTime(d.getTime())
                .build())
            .toList();
        return DosageDayType.builder().withNumber(1).withDose(copiedDoses).build();
    }

    /// Get the start time of the first dose of the first day, if there is one. Otherwise return null. Also, it has to
    /// be a local date time because that's what it is expressed like in CDA.
    static LocalDateTime getStartTime(@NonNull DosageStructureForResponseType structure) {
        var startDate = structure.getStartDate()
            .toGregorianCalendar()
            .toZonedDateTime()
            .toLocalDate();

        return Optional.ofNullable(structure.getDay())
            .map(List::getFirst)
            .map(DosageDayType::getDose)
            .map(List::getFirst)
            .map(DoseType::getTime)
            .map(DosageMapper::fmkTimeToLocalTime)
            .map(time -> LocalDateTime.of(startDate, time.getLeft()))
            .orElse(null);
    }
}
