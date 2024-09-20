package dk.nsp.epps.script;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.nsp.epps.client.Identities;
import dk.nsp.epps.testing.shared.Fmk;

public class CreateFmkTestPrescription {
    static void fmkGetPrescription() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue("1111111118").end()
            .withIncludeOpenPrescriptions().end()
            .build();

        var prescriptions = Fmk.apiClient().getPrescription(getPrescriptionRequest, Identities.apotekerChrisChristoffersen);
        System.out.printf("%s %s%n",
            prescriptions.getPatient().getPerson().getName().getGivenName(),
            prescriptions.getPatient().getPerson().getName().getSurname());
        prescriptions.getPrescription().forEach(p -> {
            System.out.printf("%nIdentifier: %s%n%nDrug: %s%nDosage test: %s", p.getIdentifier(), p.getDrug().getName(), p.getDosageText());
        });
    }

    public static void main(String[] args) throws Exception {
        fmkGetPrescription();
    }
}
