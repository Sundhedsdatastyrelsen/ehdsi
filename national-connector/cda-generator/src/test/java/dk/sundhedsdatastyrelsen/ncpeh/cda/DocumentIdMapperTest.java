package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentIdMapperTest {
    @Test
    void level1DocumentId() {
        assertThat(DocumentIdMapper.level1DocumentId("abc123"), is("abc123L1"));
    }

    @Test
    void level3DocumentId() {
        assertThat(DocumentIdMapper.level3DocumentId("abc123"), is("abc123L3"));
    }

    @Test
    void possibleIds() {
        var ids = DocumentIdMapper.possibleIds("abc123");
        assertThat(ids, containsInAnyOrder("abc123L1", "abc123L3"));
    }

    @Test
    void parseDocumentLevelReturnsLevel1ForL1Suffix() {
        assertThat(DocumentIdMapper.parseDocumentLevel("abc123L1"), is(DocumentLevel.LEVEL1));
    }

    @Test
    void parseDocumentLevelReturnsLevel3ForL3Suffix() {
        assertThat(DocumentIdMapper.parseDocumentLevel("abc123L3"), is(DocumentLevel.LEVEL3));
    }

    @Test
    void parseDocumentLevelThrowsForUnknownSuffix() {
        assertThrows(MapperException.class, () -> DocumentIdMapper.parseDocumentLevel("abc123"));
    }

    @Test
    void roundTripLevel1() {
        var base = "550e8400-e29b-41d4-a716-446655440000";
        var l1Id = DocumentIdMapper.level1DocumentId(base);
        assertThat(DocumentIdMapper.parseDocumentLevel(l1Id), is(DocumentLevel.LEVEL1));
    }

    @Test
    void roundTripLevel3() {
        var base = "550e8400-e29b-41d4-a716-446655440000";
        var l3Id = DocumentIdMapper.level3DocumentId(base);
        assertThat(DocumentIdMapper.parseDocumentLevel(l3Id), is(DocumentLevel.LEVEL3));
    }
}
