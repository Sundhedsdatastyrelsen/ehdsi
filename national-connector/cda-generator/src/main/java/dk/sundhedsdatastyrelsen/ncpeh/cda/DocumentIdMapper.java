package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/*
A utility class for translating between internal document IDs, to externally facing L1 and L3 document IDs
 */
public class DocumentIdMapper {
    private static final Logger log = LoggerFactory.getLogger(DocumentIdMapper.class);

    private static final String level1Suffix = "L1";
    private static final String level3Suffix = "L3";

    public static String level1DocumentId(String documentId) {
        return String.format("%s%s", documentId, level1Suffix);
    }

    public static String level3DocumentId(String documentId) {
        return String.format("%s%s", documentId, level3Suffix);
    }

    public static Set<String> possibleIds(String documentId) {
        return Set.of(level1DocumentId(documentId), level3DocumentId(documentId));
    }

    /// @throws MapperException if document id can't be parsed
    public static DocumentLevel parseDocumentLevel(@NonNull String documentId) {
        if (documentId.endsWith(level1Suffix)) {
            return DocumentLevel.LEVEL1;
        } else if (documentId.endsWith(level3Suffix)) {
            return DocumentLevel.LEVEL3;
        }
        log.error("Document id {} could not be mapped", documentId);
        throw new MapperException("Document id could not parse to type of Document");
    }
}
