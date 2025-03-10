package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageDayType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageIsNotIteratedType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructureForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageStructuresForResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageTimesDosageEndingUndeterminedType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DoseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DosageForResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class DosageMapperTest {
    @Test
    void getDayDistanceIsSaneTest() {
        Assertions.assertEquals(Optional.of(1), DosageMapper.getDayDistance(1, days(1)));
        Assertions.assertEquals(Optional.of(3), DosageMapper.getDayDistance(3, days(1)));
        Assertions.assertEquals(Optional.of(1), DosageMapper.getDayDistance(3, days(1, 2, 3)));
        Assertions.assertEquals(Optional.of(2), DosageMapper.getDayDistance(4, days(1, 3)));
        Assertions.assertEquals(Optional.empty(), DosageMapper.getDayDistance(3, days(1, 3)));
        Assertions.assertEquals(Optional.empty(), DosageMapper.getDayDistance(3, days(1, 2)));
    }

    List<DosageDayType> days(int... numbers) {
        return Arrays.stream(numbers).mapToObj(num -> DosageDayType.builder().withNumber(num).build()).toList();
    }

    private final DoseType onePillDose = DoseType.builder().withQuantity(BigDecimal.ONE).build();
    private final DosageDayType onePillDay = DosageDayType.builder()
        .withNumber(1)
        .addDose(onePillDose)
        .build();
    private final DosageStructureForResponseType onePillDailyStructure = DosageStructureForResponseType.builder()
        .withIterationInterval(1)
        .addDay(onePillDay)
        .withStartDate(DatatypeFactory.newDefaultInstance().newXMLGregorianCalendarDate(2023, 11, 3, 1))
        .withDosageEndingUndetermined(DosageTimesDosageEndingUndeterminedType.builder().build())
        .build();
    private final DosageForResponseType onePillDailyResponse = DosageForResponseType.builder()
        .withStructuresFixed(DosageStructuresForResponseType.builder()
            .addEmptyStructureOrStructure(onePillDailyStructure)
            .build()).withUnitText("1").build();

    @Test
    void takeOnePillDailyTest() {
        var result = DosageMapper.model(onePillDailyResponse);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertEquals("PeriodicInterval", result.getTag());
        Assertions.assertInstanceOf(Dosage.PeriodicInterval.class, result);
        var dosage = (Dosage.PeriodicInterval) result;
        Assertions.assertTrue(dosage.isInstitutionSpecified());

        Assertions.assertEquals("Simple", dosage.getPeriod().getTag());
        Assertions.assertInstanceOf(Dosage.Period.Simple.class, dosage.getPeriod());
        var period = (Dosage.Period.Simple) dosage.getPeriod();
        Assertions.assertEquals("d", period.getUnit());
        Assertions.assertEquals("1", period.getValue());

        var quantity = dosage.getQuantity();
        Assertions.assertEquals("1", quantity.getMinValue());
        Assertions.assertEquals("1", quantity.getValue());

        Assertions.assertEquals("Translated", quantity.getUnit().getTag());
        Assertions.assertInstanceOf(Dosage.Unit.Translated.class, quantity.getUnit());
        var unit = (Dosage.Unit.Translated) quantity.getUnit();
        Assertions.assertEquals("1", unit.getTranslation());
    }

    @Test
    void takeTwoPillsDailyTest() {
        var response = onePillDailyResponse.newCopyBuilder()
            .withStructuresFixed(onePillDailyResponse.getStructuresFixed()
                .newCopyBuilder()
                .withEmptyStructureOrStructure(
                    onePillDailyStructure.newCopyBuilder().withDay(onePillDay.newCopyBuilder().withDose(
                        onePillDose.newCopyBuilder().withQuantity(BigDecimal.valueOf(2)).build()
                    ).build()).build())
                .build())
            .build();

        var result = DosageMapper.model(response);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertInstanceOf(Dosage.PeriodicInterval.class, result);
        var dosage = (Dosage.PeriodicInterval) result;

        var quantity = dosage.getQuantity();
        Assertions.assertEquals("2", quantity.getMinValue());
        Assertions.assertEquals("2", quantity.getValue());
    }

    @Test
    void takeOnePillTwiceADayTest() {
        var response = onePillDailyResponse.newCopyBuilder()
            .withStructuresFixed(onePillDailyResponse.getStructuresFixed()
                .newCopyBuilder()
                .withEmptyStructureOrStructure(
                    onePillDailyStructure.newCopyBuilder()
                        .withDay(onePillDay.newCopyBuilder().addDose(onePillDose).build())
                        .build())
                .build())
            .build();

        var result = DosageMapper.model(response);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertInstanceOf(Dosage.PeriodicInterval.class, result);
        var dosage = (Dosage.PeriodicInterval) result;

        Assertions.assertInstanceOf(Dosage.Period.Simple.class, dosage.getPeriod());
        var period = (Dosage.Period.Simple) dosage.getPeriod();
        Assertions.assertEquals("h", period.getUnit());
        Assertions.assertEquals("12", period.getValue());

        var quantity = dosage.getQuantity();
        Assertions.assertEquals("1", quantity.getMinValue());
        Assertions.assertEquals("1", quantity.getValue());
    }

    @Test
    void takeOnePillEveryOtherDayTest() {
        var response = onePillDailyResponse.newCopyBuilder()
            .withStructuresFixed(onePillDailyResponse.getStructuresFixed()
                .newCopyBuilder()
                .withEmptyStructureOrStructure(
                    onePillDailyStructure.newCopyBuilder().withIterationInterval(2).build())
                .build())
            .build();

        var result = DosageMapper.model(response);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertInstanceOf(Dosage.PeriodicInterval.class, result);
        var dosage = (Dosage.PeriodicInterval) result;

        Assertions.assertInstanceOf(Dosage.Period.Simple.class, dosage.getPeriod());
        var period = (Dosage.Period.Simple) dosage.getPeriod();
        Assertions.assertEquals("d", period.getUnit());
        Assertions.assertEquals("2", period.getValue());
    }

    @Test
    void takeOnePillOnceTest() {
        var response = onePillDailyResponse.newCopyBuilder()
            .withStructuresFixed(onePillDailyResponse.getStructuresFixed()
                .newCopyBuilder()
                .withEmptyStructureOrStructure(
                    onePillDailyStructure.newCopyBuilder()
                        .withIterationInterval(null)
                        .withNotIterated(new DosageIsNotIteratedType())
                        .build())
                .build())
            .build();

        var result = DosageMapper.model(response);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertEquals("Once", result.getTag());
        Assertions.assertInstanceOf(Dosage.Once.class, result);
        var dosage = (Dosage.Once) result;
        Assertions.assertEquals("20231103", dosage.getTimeValue());
    }

    @Test
    void takeOnePillOnceWithTimeTest() {
        var response = onePillDailyResponse.newCopyBuilder()
            .withStructuresFixed(onePillDailyResponse.getStructuresFixed()
                .newCopyBuilder()
                .withEmptyStructureOrStructure(
                    onePillDailyStructure.newCopyBuilder()
                        .withIterationInterval(null)
                        .withNotIterated(new DosageIsNotIteratedType())
                        .withDay(onePillDay.newCopyBuilder()
                            .withDose(onePillDose.newCopyBuilder().withTime("08.00").build())
                            .build())
                        .build())
                .build())
            .build();

        var result = DosageMapper.model(response);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertEquals("Once", result.getTag());
        Assertions.assertInstanceOf(Dosage.Once.class, result);
        var dosage = (Dosage.Once) result;
        Assertions.assertEquals("20231103080000", dosage.getTimeValue());
    }

    private final DosageForResponseType onePillAccordingToNeedResponse = onePillDailyResponse.newCopyBuilder()
        .withStructuresFixed(null)
        .withStructuresAccordingToNeed(onePillDailyResponse.getStructuresFixed().newCopyBuilder().build())
        .build();

    @Test
    void takeOnePillAccordingToNeedTest() {
        var result = DosageMapper.model(onePillAccordingToNeedResponse);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertEquals("PeriodicInterval", result.getTag());
        Assertions.assertInstanceOf(Dosage.PeriodicInterval.class, result);
        var dosage = (Dosage.PeriodicInterval) result;
        Assertions.assertTrue(dosage.isInstitutionSpecified());

        Assertions.assertEquals("Simple", dosage.getPeriod().getTag());
        Assertions.assertInstanceOf(Dosage.Period.Simple.class, dosage.getPeriod());
        var period = (Dosage.Period.Simple) dosage.getPeriod();
        Assertions.assertEquals("d", period.getUnit());
        Assertions.assertEquals("1", period.getValue());

        var quantity = dosage.getQuantity();
        Assertions.assertEquals("0", quantity.getMinValue());
        Assertions.assertEquals("1", quantity.getValue());

        Assertions.assertEquals("Translated", quantity.getUnit().getTag());
        Assertions.assertInstanceOf(Dosage.Unit.Translated.class, quantity.getUnit());
        var unit = (Dosage.Unit.Translated) quantity.getUnit();
        Assertions.assertEquals("1", unit.getTranslation());
    }

    @Test
    void takeOnePillAccordingToNeedUnboundedTest() {
        var response = onePillAccordingToNeedResponse.newCopyBuilder().withStructuresAccordingToNeed(
                onePillAccordingToNeedResponse.getStructuresAccordingToNeed()
                    .newCopyBuilder()
                    .withEmptyStructureOrStructure(
                        onePillDailyStructure.newCopyBuilder()
                            .withIterationInterval(null)
                            .withNotIterated(new DosageIsNotIteratedType())
                            .build())
                    .build())
            .build();

        var result = DosageMapper.model(response);

        if (result instanceof Dosage.Unstructured) {
            Assertions.fail(((Dosage.Unstructured) result).getReason());
        }

        Assertions.assertEquals("Unbounded", result.getTag());
        Assertions.assertInstanceOf(Dosage.Unbounded.class, result);
        var dosage = (Dosage.Unbounded) result;

        var quantity = dosage.getQuantity();
        Assertions.assertEquals("0", quantity.getMinValue());
        Assertions.assertEquals("1", quantity.getValue());

        Assertions.assertEquals("Translated", quantity.getUnit().getTag());
        Assertions.assertInstanceOf(Dosage.Unit.Translated.class, quantity.getUnit());
        var unit = (Dosage.Unit.Translated) quantity.getUnit();
        Assertions.assertEquals("1", unit.getTranslation());
    }
}
