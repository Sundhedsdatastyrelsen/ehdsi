package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class EPrescriptionMetadataMapperTest {
    @Test
    void rootedIdTest() {
        var id = "1234L3";
        assertThat(EPrescriptionMetadataMapper.fromRootedId(EPrescriptionMetadataMapper.toRootedId(id)), is(id));
    }
}
