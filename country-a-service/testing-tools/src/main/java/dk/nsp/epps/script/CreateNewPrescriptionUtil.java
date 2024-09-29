package dk.nsp.epps.script;

import dk.nsp.epps.testing.shared.TestingInput;

import static dk.nsp.epps.integration.CreateNewPrescriptionInFmk.createNewPrecriptionForCpr;

public class CreateNewPrescriptionUtil {
    public static void main(String[] args) throws Exception {
        for (var cpr : TestingInput.testingCprs()) {
            createNewPrecriptionForCpr(cpr);
        }
    }
}
