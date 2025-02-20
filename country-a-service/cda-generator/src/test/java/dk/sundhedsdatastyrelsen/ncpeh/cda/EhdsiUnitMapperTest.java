package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EhdsiUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EhdsiUnitMapperTest {

    @Test
    void fromLmsTest() {
        Assertions.assertEquals(new EhdsiUnit.WithCode("{Ampoule}"), EhdsiUnitMapper.fromLms("AM"));
        Assertions.assertEquals(new EhdsiUnit.WithTranslation("Inf.flaske"), EhdsiUnitMapper.fromLms("IF"));
        Assertions.assertThrows(
            IllegalArgumentException.class, () ->
                EhdsiUnitMapper.fromLms("unknown code"));
    }
}
