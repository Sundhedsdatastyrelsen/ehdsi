package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PatientSummaryDocumentIdMapperTest {
    @Test
    void level1DocumentId() {
        assertThat(PatientSummaryDocumentIdMapper.level1DocumentId("abc123"), is("abc123L1"));
    }

    @Test
    void level3DocumentId() {
        assertThat(PatientSummaryDocumentIdMapper.level3DocumentId("abc123"), is("abc123L3"));
    }

    @Test
    void possibleIds() {
        var ids = PatientSummaryDocumentIdMapper.possibleIds("abc123");
        assertThat(ids, containsInAnyOrder("abc123L1", "abc123L3"));
    }

    @Test
    void parseDocumentLevelReturnsLevel1ForL1Suffix() {
        assertThat(PatientSummaryDocumentIdMapper.parseDocumentLevel("abc123L1"), is(DocumentLevel.LEVEL1));
    }

    @Test
    void parseDocumentLevelReturnsLevel3ForL3Suffix() {
        assertThat(PatientSummaryDocumentIdMapper.parseDocumentLevel("abc123L3"), is(DocumentLevel.LEVEL3));
    }

    @Test
    void parseDocumentLevelThrowsForUnknownSuffix() {
        assertThrows(MapperException.class, () -> PatientSummaryDocumentIdMapper.parseDocumentLevel("abc123"));
    }

    @Test
    void roundTripLevel1() {
        var base = "550e8400-e29b-41d4-a716-446655440000";
        var l1Id = PatientSummaryDocumentIdMapper.level1DocumentId(base);
        assertThat(PatientSummaryDocumentIdMapper.parseDocumentLevel(l1Id), is(DocumentLevel.LEVEL1));
    }

    @Test
    void roundTripLevel3() {
        var base = "550e8400-e29b-41d4-a716-446655440000";
        var l3Id = PatientSummaryDocumentIdMapper.level3DocumentId(base);
        assertThat(PatientSummaryDocumentIdMapper.parseDocumentLevel(l3Id), is(DocumentLevel.LEVEL3));
    }
}
