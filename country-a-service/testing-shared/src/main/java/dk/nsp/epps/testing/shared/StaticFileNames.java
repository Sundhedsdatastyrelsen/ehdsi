package dk.nsp.epps.testing.shared;

import java.io.File;

/***
 * Generates filenames for files that are needed to output and input in an easy way.
 */
public class StaticFileNames {
    public static final File storageDir = new File("src/test/resources/test-file-storage");

    //Filenames are generated based on CPR to ensure we have easy traceable and reproduceable results
    public static String getFmkFileName(String cpr, String mark) {
        return "get-prescription-" + mark + "-" + cpr + ".xml";
    }

    public static String getCdaFileName(String cpr, String mark) {
        return "cda-"  + mark + "-" + cpr + ".xml";
    }

    public static String getDispensationFileName(String cpr, String mark) {
        return "dispensation-"  + mark + "-" + cpr + ".xml";
    }

    public static String getStartEffectuationResponseFileName(String cpr, String mark) {
        return "dispensation-"  + mark + "-" + cpr + ".xml";
    }

    //Filenames are generated based on CPR to ensure we have easy traceable and reproduceable results
    public static String getFmkFileName(String cpr) {
        return getFmkFileName(cpr,"integration");
    }

    public static String getCdaFileName(String cpr) {
        return getCdaFileName(cpr,"integration");
    }

    public static String getDispensationFileName(String cpr) {
        return getDispensationFileName(cpr,"integration");
    }

    public static String getStartEffectuationResponseFileName(String cpr) {
        return getStartEffectuationResponseFileName(cpr,"integration");
    }
}
