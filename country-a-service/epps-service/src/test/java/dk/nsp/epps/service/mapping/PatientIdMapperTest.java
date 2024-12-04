package dk.nsp.epps.service.mapping;

import dk.nsp.epps.service.exception.DataRequirementException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatientIdMapperTest {
    @Test
    public void testPatientIdToCpr() {
        Assertions.assertEquals("1234567890", PatientIdMapper.toCpr("1234567890"));
        Assertions.assertEquals("1111111118", PatientIdMapper.toCpr("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO"));
    }

    @Test
    public void malformedIds() {
        Assert.assertThrows(DataRequirementException.class, () -> PatientIdMapper.toCpr("123"));
        Assert.assertThrows(DataRequirementException.class, () -> PatientIdMapper.toCpr("aaaaaaaaaa"));
        Assert.assertThrows(DataRequirementException.class, () -> PatientIdMapper.toCpr("aaaaaaaaaa^^^123"));
    }
}
