package dk.nsp.epps.testing.shared;

/**
 * This class is used for testing input - mainly to ensure that if we change in the testing input, it changes in the
 * same way, both in the integration tests and in the integration test utilities.
 */
public class TestingInput {
    public static String[] testingCprs() {
        return new String[]{"0201909309"};
    }

    public static String preparedFilesMark() {
        return "prepared";
    }
}
