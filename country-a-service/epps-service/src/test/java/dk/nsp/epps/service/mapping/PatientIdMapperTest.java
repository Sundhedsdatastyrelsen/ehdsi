package dk.nsp.epps.service.mapping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatientIdMapperTest {
    @Test
    public void testPatientIdToCpr() {
        Assertions.assertEquals("1234567890", PatientIdMapper.toCpr("1234567890"));
        Assertions.assertEquals("1234567890", PatientIdMapper.toCpr("123456-7890"));
        Assertions.assertEquals("1234567890", PatientIdMapper.toCpr("DKCPR^^^1234567890"));
        Assertions.assertEquals("1234567890", PatientIdMapper.toCpr("DKCPR^^^123456-7890"));
    }
}
