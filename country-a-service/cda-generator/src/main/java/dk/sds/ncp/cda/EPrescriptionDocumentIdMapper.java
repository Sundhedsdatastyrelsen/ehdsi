package dk.sds.ncp.cda;


import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sds.ncp.cda.model.DocumentLevel;

/*
A utility class for translating between internal document IDs, to externally facing L1 and L3 document IDs
 */
public class EPrescriptionDocumentIdMapper {

    private static final String Level1Suffix = "L1";
    private static final String Level3Suffix = "L3";

    public static String Level1DocumentId(String documentId) {
        return String.format("%s%s",documentId,Level1Suffix); //Append "L1" for L1 documents
    }

    public static String Level3DocumentId(String documentId) {
        return String.format("%s%s",documentId,Level3Suffix); //Append "L3" for L3 documents
    }

    public static String[] PossibleIds(String documentId) {
        return new String[] {Level1DocumentId(documentId), Level3DocumentId(documentId)};
    }

    public static DocumentLevel ParseDocumentLevel(String documentId) throws MapperException {
        if(documentId.endsWith(Level1Suffix)){
            return DocumentLevel.LEVEL1;
        } else if(documentId.endsWith(Level3Suffix)){
            return DocumentLevel.LEVEL3;
        }
        throw new MapperException("Document id could not parse to type of Document");
    }
}
