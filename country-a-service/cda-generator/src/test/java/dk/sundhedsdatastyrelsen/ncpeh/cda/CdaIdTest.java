package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CdaIdTest {
    @Test
    void rootedIdTest() {
        var id = new CdaId(Oid.DK_FMK_PRESCRIPTION, "1234L3");
        assertThat(
            CdaId.fromDocumentId(CdaId.toDocumentId(id)),
            is(id));
    }
}
