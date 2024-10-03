package dk.nsp.epps.script;

import dk.nsp.epps.integration.GetPrescriptionFromFmk;
import dk.nsp.epps.testing.shared.TestingInput;
import jakarta.xml.bind.JAXBException;

public class GetPrescriptionsFromFmkUtil {
    public static void main(String[] args) throws JAXBException {
        for (String cpr : TestingInput.testingCprs()) {
            GetPrescriptionFromFmk.getPrescriptionsFromFmkForCpr(cpr, TestingInput.preparedFilesMark());
        }
    }
}
