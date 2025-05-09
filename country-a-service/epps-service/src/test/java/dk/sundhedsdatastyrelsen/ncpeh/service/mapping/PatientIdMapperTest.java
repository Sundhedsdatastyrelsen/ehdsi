package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatientIdMapperTest {
    @Test
    public void testPatientIdToCpr() {
        Assertions.assertEquals("1234567890", PatientIdMapper.toCpr("1234567890"));
        Assertions.assertEquals("1111111118", PatientIdMapper.toCpr("1111111118^^^&1.2.208.176.1.2&ISO"));
    }

    @Test
    public void malformedIds() {
        Assert.assertThrows(DataRequirementException.class, () -> PatientIdMapper.toCpr("123"));
        Assert.assertThrows(DataRequirementException.class, () -> PatientIdMapper.toCpr("aaaaaaaaaa"));
        Assert.assertThrows(DataRequirementException.class, () -> PatientIdMapper.toCpr("aaaaaaaaaa^^^123"));
    }
}
