package dk.nsp.epps.testing.shared;

import java.io.File;

/***
 * Generates filenames for files that are needed to output and input in an easy way.
 */
public class TestingFileNames {
    public static File storageDir() {return storageDir("test-file-storage");};

    public static File storageDir(String resourceDir) {
        final var storageDir = new File("testing-shared/src/main/resources/" + resourceDir);
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                throw new RuntimeException("Could not create dir: " + storageDir.getAbsolutePath());
            }
        }
        return storageDir;
    }

    //Filenames are generated based on CPR to ensure we have easy traceable and reproduceable results
    public static String fmkFileName(String cpr, String mark) {
        return "get-prescription-" + mark + "-" + cpr + ".xml";
    }

    public static String cdaFileName(String cpr, String mark) {
        return "cda-"  + mark + "-" + cpr + ".xml";
    }

    public static String dispensationFileName(String cpr, String mark) {
        return "dispensation-"  + mark + "-" + cpr + ".xml";
    }

    public static String startEffectuationResponseFileName(String cpr, String mark) {
        return "dispensation-"  + mark + "-" + cpr + ".xml";
    }
}
