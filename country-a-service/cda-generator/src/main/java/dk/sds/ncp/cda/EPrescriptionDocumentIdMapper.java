package dk.sds.ncp.cda;


import dk.sds.ncp.cda.model.DocumentLevel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
A utility class for translating between internal document IDs, to externally facing L1 and L3 document IDs
 */
public class EPrescriptionDocumentIdMapper {

    private static final String level1Suffix = "L1";
    private static final String level3Suffix = "L3";

    public static String level1DocumentId(String documentId) {
        return String.format("%s%s", documentId, level1Suffix); //Append "L1" for L1 documents
    }

    public static String level3DocumentId(String documentId) {
        return String.format("%s%s", documentId, level3Suffix); //Append "L3" for L3 documents
    }

    public static Set<String> possibleIds(String documentId) {
        return Set.of(level1DocumentId(documentId), level3DocumentId(documentId));
    }

    public static DocumentLevel parseDocumentLevel(String documentId) throws MapperException {
        if (documentId.endsWith(level1Suffix)) {
            return DocumentLevel.LEVEL1;
        } else if (documentId.endsWith(level3Suffix)) {
            return DocumentLevel.LEVEL3;
        }
        throw new MapperException("Document id could not parse to type of Document");
    }
}
