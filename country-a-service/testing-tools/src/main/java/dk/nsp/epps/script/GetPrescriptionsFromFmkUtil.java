package dk.nsp.epps.script;

import dk.nsp.epps.testing.shared.TestingInput;
import jakarta.xml.bind.JAXBException;

import java.net.URISyntaxException;

import static dk.nsp.epps.integration.GetPrescriptionFromFmk.getPrescriptionsFromFmkForCpr;

public class GetPrescriptionsFromFmkUtil {
    public static void main(String[] args) throws JAXBException, URISyntaxException {
        for (String cpr : TestingInput.testingCprs()) {
            getPrescriptionsFromFmkForCpr(cpr, TestingInput.preparedFilesMark());
        }
    }
}
