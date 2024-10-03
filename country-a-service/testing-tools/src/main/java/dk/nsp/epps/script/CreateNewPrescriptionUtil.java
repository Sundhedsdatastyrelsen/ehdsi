package dk.nsp.epps.script;

import dk.nsp.epps.integration.CreateNewPrescriptionInFmk;
import dk.nsp.epps.testing.shared.TestingInput;

public class CreateNewPrescriptionUtil {
    public static void main(String[] args) throws Exception {
        for (var cpr : TestingInput.testingCprs()) {
            CreateNewPrescriptionInFmk.createNewPrecriptionForCpr(cpr);
        }
    }
}
