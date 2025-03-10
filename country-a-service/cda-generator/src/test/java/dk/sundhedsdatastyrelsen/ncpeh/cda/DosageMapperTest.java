package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageDayType;
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

    @Test
    void takeOnePillDailyTest() {
        var builder = DosageForResponseType.builder();
        builder.withStructuresFixed(DosageStructuresForResponseType.builder()
            .addEmptyStructureOrStructure(DosageStructureForResponseType.builder()
                .withIterationInterval(1)
                .addDay(DosageDayType.builder()
                    .withNumber(1)
                    .addDose(DoseType.builder().withQuantity(BigDecimal.ONE).build())
                    .build())
                .withStartDate(DatatypeFactory.newDefaultInstance().newXMLGregorianCalendarDate(2023, 11, 3, 1))
                .withDosageEndingUndetermined(DosageTimesDosageEndingUndeterminedType.builder().build())
                .build())
            .build());
        builder.withUnitText("1");
        var result = DosageMapper.model(builder.build());

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
}
