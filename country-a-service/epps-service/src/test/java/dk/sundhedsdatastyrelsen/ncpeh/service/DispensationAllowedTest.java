package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.OrderStatusPredefinedType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.PackageInfo;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DispensationAllowedTest {
    @Test
    void happyPathPrescriptionIsAllowed() {
        var p = PrescriptionType.builder().build();
        var res = DispensationAllowed.getDispensationRestrictions(
            p,
            new PackageInfo("", "A", "", 1));
        assertThat(res, is(nullValue()));
    }

    @Test
    void lockedPrescriptionsAreNotAllowed() {
        var pInfo = new PackageInfo("", "A", "", 1);
        var effectuationStarted = PrescriptionType.builder()
            .addOrder()
            .withStatus("Ekspedition påbegyndt")
            .end()
            .build();
        var orderedPrescription = PrescriptionType.builder()
            .addOrder()
            .withStatus(OrderStatusPredefinedType.BESTILT.value())
            .end()
            .build();
        var res1 = DispensationAllowed.getDispensationRestrictions(effectuationStarted, pInfo);
        var res2 = DispensationAllowed.getDispensationRestrictions(orderedPrescription, pInfo);
        assertThat(res1, is("Prescription is locked to another pharmacy, and cannot be dispensed cross border."));
        assertThat(res2, is("Prescription is locked to another pharmacy, and cannot be dispensed cross border."));
    }

    @Test
    void prescriptionsWithPastDispensationsAreNotAllowed() {
        var completedPrescription = PrescriptionType.builder()
            .addOrder()
            .withStatus("Udført")
            .end()
            .build();
        var dosisDispensedPrescription = PrescriptionType.builder()
            .addOrder()
            .withStatus(OrderStatusPredefinedType.DOSISDISPENSERET.value())
            .end()
            .build();
        var pInfo = new PackageInfo("", "A", "", 1);
        var res1 = DispensationAllowed.getDispensationRestrictions(completedPrescription, pInfo);
        var res2 = DispensationAllowed.getDispensationRestrictions(dosisDispensedPrescription, pInfo);
        assertThat(res1, is("Prescription has been partially dispensed. This is not yet supported in DK."));
        assertThat(res2, is("Prescription has been partially dispensed. This is not yet supported in DK."));
    }

    @Test
    void prescriptionsWithCancelledPastDispensationsAreAllowed() {
        var prescription = PrescriptionType.builder()
            .addOrder()
            .withStatus("Annulleret")
            .end()
            .build();
        var pInfo = new PackageInfo("", "A", "", 1);
        var res1 = DispensationAllowed.getDispensationRestrictions(prescription, pInfo);
        assertThat(res1, is(nullValue()));
    }

    @Test
    void iteratedPrescriptionsAreBlocked() {
        var prescription = PrescriptionType.builder()
            .withPackageRestriction()
            .withIterationNumber(2)
            .end()
            .build();
        var pInfo = new PackageInfo("", "A", "", 1);
        var res1 = DispensationAllowed.getDispensationRestrictions(prescription, pInfo);
        assertThat(res1, is("Prescription is iterated, which is not yet supported in DK."));
    }

    @Test
    void doseDispensedPrescriptionsAreBlocked() {
        var prescription = PrescriptionType.builder()
            .withDoseDispensedRestriction().end()
            .build();
        var pInfo = new PackageInfo("", "A", "", 1);
        var res1 = DispensationAllowed.getDispensationRestrictions(prescription, pInfo);
        assertThat(res1, is("Dose dispensed medications are not supported."));
    }
}
