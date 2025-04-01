package dk.sundhedsdatastyrelsen.ncpeh.cda;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PackageUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class EhdsiUnitMapperTest {
  @Test
 void fromLmsTest() {
 Assertions.assertEquals(new PackageUnit.WithCode("{Ampoule}"), PackageUnitMapper.fromLms("AM"));
 Assertions.assertEquals(new PackageUnit.WithTranslation("Inf.flaske"), PackageUnitMapper.fromLms("IF"));
 Assertions.assertThrows(
 IllegalArgumentException.class, () ->
 PackageUnitMapper.fromLms("unknown code"));
 }
}
