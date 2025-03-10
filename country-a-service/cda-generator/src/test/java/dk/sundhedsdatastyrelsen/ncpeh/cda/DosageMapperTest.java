package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DosageDayType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
