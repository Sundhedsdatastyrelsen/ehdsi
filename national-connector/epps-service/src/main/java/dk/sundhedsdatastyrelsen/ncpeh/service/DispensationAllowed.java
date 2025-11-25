package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.OrderStatusPredefinedType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.PackageInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DispensationAllowed {
    private DispensationAllowed() {
    }

    /// Check whether dispensation is allowed. Returns an error message if it is not, otherwise null.
    ///
    /// @param prescription the prescription to dispense. Must include effectuations and orders.
    /// @return an error message if dispensation is not allowed. Null if dispensation is allowed.
    public static String getDispensationRestrictions(PrescriptionType prescription, @Nullable PackageInfo packageInfo) {
        var errors = new ArrayList<String>();

        // If another pharmacy has started handling this prescription, calls to FMK to dispense it will fail, as they
        // have a lock on the prescription.
        var effectuationStartedOrder = prescription.getOrder()
            .stream()
            .filter(o ->
                // The documentation for ekspedition påbegyndt is clear, the prescription is locked to that pharmacy.
                // All foreign pharmacies share the same ID here, so we can't use that.
                Objects.equals(o.getStatus(), OrderStatusPredefinedType.EKSPEDITION_PÅBEGYNDT.value()))
            .findFirst();
        if (effectuationStartedOrder.isPresent()) {
            errors.add("Prescription is locked to another pharmacy, and cannot be dispensed cross border.");
        }

        // If there are past dispensations on an open prescription, this is either an iterated prescription or a
        // prescription that is already partially dispensed. FMK doesn't return effectuations that didn't dispense
        // anything.
        // For now, we don't support those. See also #328 and #205
        var pastEffectuation = prescription.getOrder()
            .stream()
            .filter(o ->
                // Negative list so if they add a new status, we block it by default.
                // Cancelled orders are OK.
                !Objects.equals(o.getStatus(), OrderStatusPredefinedType.ANNULLERET.value())
                    // We already check for ekspedition påbegyndt above. Don't want to report that error twice.
                    && !Objects.equals(o.getStatus(), OrderStatusPredefinedType.EKSPEDITION_PÅBEGYNDT.value())
                    // Bestilt means "The prescriber thinks you should pick it up here, but you can still pick it up
                    // anywhere". When we cancel an effectuation, the prescriptions remains "bestilt" to us. So we allow
                    // "bestilt" prescriptions. See also https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:bestilling and
                    // https://wiki.fmk-teknik.dk/doku.php?id=apo:generel:scenarier:borger_med_cpr-nummer_henvender_sig_pa_apoteket_for_at_hente_bestilt_laegemiddel
                    && !Objects.equals(o.getStatus(), OrderStatusPredefinedType.BESTILT.value()))
            .findFirst();
        if (pastEffectuation.isPresent()) {
            errors.add("Prescription has been partially dispensed. This is not yet supported in DK.");
        }

        // Iterated prescriptions require clarification, so we block them for now.
        var iterationNumber = Optional.ofNullable(prescription.getPackageRestriction())
            .map(PackageRestrictionType::getIterationNumber)
            .orElse(null);
        if (iterationNumber != null && iterationNumber > 1) {
            errors.add("Prescription is iterated, which is not yet supported in DK.");
        }

        // Dose dispensed prescriptions are out of scope https://webgate.ec.europa.eu/fpfis/wikis/spaces/EHDSI/pages/912798895/ePrescription+Test+Framework+Extension.
        // It's not as clear-cut as the out of scope items defined in the MyHealth@EU scope and business goals.
        var isDoseDispensed = prescription.getDoseDispensedRestriction() != null;
        if (isDoseDispensed) {
            errors.add("Dose dispensed medications are not supported.");
        }

        // The rest are the formal out-of-scope cases defined in https://webgate.ec.europa.eu/fpfis/wikis/spaces/EHDSI/pages/888398063/MyHealth+EU+Scope+and+Business+Goals
        // We check that the regulation code ("Udleveringsbestemmelse") of the product is valid.
        // Specifically, we use this check to disallow narcotics ("§4-lægemidler") which are out-of-scope.
        var isValidRegulationCode = packageInfo != null
            && dispensableRegulations.stream().anyMatch(code -> code.equals(packageInfo.dispensationRegulationCode()));
        if (!isValidRegulationCode) {
            errors.add("Medicine dispensation regulation prohibits cross border dispensation.");
        }

        // KBP = "Kombinationspakning" (LMS14)
        // Combination packaging is out of scope, so we disallow them.
        // There is also no good transcoding of KBP.
        var isCombinationPackaging = packageInfo == null || "KBP".equals(packageInfo.packageFormCode());
        if (isCombinationPackaging) {
            errors.add("Combination packages are not supported.");
        }

        // A prescription is magistral (based on a recipe) if there is a DetailedDrugText on it.
        // This is the only way to tell if a drug is magistral.
        // See also https://github.com/trifork/fmk-schemas/blob/e84edbebfeb17c1b9a98eb3acfdc62706e20f4c8/etc/schemas/2015/01/01/DetailedDrugText.xsd.
        var isMagistral = EPrescriptionL3Mapper.isMagistral(prescription);
        if (isMagistral) {
            errors.add("Magistral prescriptions are not supported.");
        }

        return errors.isEmpty() ? null : String.join("\n", errors);
    }

    /// The list of regulations we can dispense in other EU countries, with the
    /// ones we cannot commented out. The list was taken from LMS at 2025-04-07.
    private static final List<String> dispensableRegulations = List.of(
        "A", // Receptpligtigt, en udlevering
        // AP,"Udelukkende produktionsdyr, en udlevering"
        // The AP4 drugs are narcotics, and narcotics are out of scope for MyHealth@EU
        // AP4,Kopieringspligtigt
        // AP4BG,"Kopieringspligtigt, kun sygehuse"
        // AP4NB,"Kopieringspligtigt, kun sygehuse og speciallæger"
        // "APK", // Produktionsdyr og familiedyr, en udlevering
        "B", // Receptpligtigt, flere udleveringer
        // BEGR,Kun til sygehuse,
        // BP,"Udelukkende produktionsdyr, flere udleveringer",
        // "BPK", // Produktionsdyr og familiedyr, flere udleveringer
        // These two with medicinal gas are never used in the dataset.
        // GA,"Medicinsk gas, en udlevering",
        // GB,"Medicinsk gas, flere udleveringer",
        "GH", // Medicinsk gas, håndkøb
        "HA", // Håndkøb, apoteksforbeholdt
        "HA18", // Håndkøb, apoteksforbeholdt, 18 år eller derover
        "HF", // Håndkøb, ikke apoteksforbeholdt
        // HP,"Udelukkende produktionsdyr, håndkøb",
        "HPK", // Produktionsdyr og familiedyr, håndkøb
        "HV", // Håndkøb, veterinær, ikke apoteksforbeholdt
        "HX", // Håndkøb, ikke apoteksforbeholdt, begrænset
        "HX18" // Håndkøb, ikke apoteksforbeholdt, 18 år el. derover
        // NBS,Kun til sygehuse og speciallæger,
        // PRMIX,Kun til fodermøller efter veterinær ordination,
        // SSI,Kan udleveres fra Statens Seruminstitut,
        // SVS,Kan udleveres fra Statens Vet. Serumlaboratorium,
    );
}
