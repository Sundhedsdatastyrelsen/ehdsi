package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.PackageInfo;

import java.util.List;
import java.util.Optional;

public final class DispensationAllowed {
    private DispensationAllowed() {
    }

    public static boolean isDispensationAllowed(PrescriptionType prescription, PackageInfo packageInfo) {
        // We check that the regulation code ("Udleveringsbestemmelse") of the product is valid.
        // Specifically, we use this check to disallow narcotics ("§4-lægemidler") which are out-of-scope.
        var isValidRegulationCode = packageInfo != null
            && dispensableRegulations.stream().anyMatch(code -> code.equals(packageInfo.dispensationRegulationCode()));

        // KBP = "Kombinationspakning" (LMS14)
        // Combination packaging is out of scope, so we disallow them.
        // There is also no good transcoding of KBP.
        var isNotCombinationPackaging = packageInfo != null && !"KBP".equals(packageInfo.packageFormCode());

        // A prescription is magistral (based on a recipe) if there is a DetailedDrugText on it.
        // This is the only way to tell if a drug is magistral.
        // See also https://github.com/trifork/fmk-schemas/blob/e84edbebfeb17c1b9a98eb3acfdc62706e20f4c8/etc/schemas/2015/01/01/DetailedDrugText.xsd.
        var isNotMagistral = Optional.ofNullable(prescription)
            .map(PrescriptionType::getDrug)
            .map(DrugType::getDetailedDrugText)
            .isEmpty();

        return isValidRegulationCode && isNotCombinationPackaging && isNotMagistral;
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
        "APK", // Produktionsdyr og familiedyr, en udlevering
        "B", // Receptpligtigt, flere udleveringer
        // BEGR,Kun til sygehuse,
        // BP,"Udelukkende produktionsdyr, flere udleveringer",
        "BPK", // Produktionsdyr og familiedyr, flere udleveringer
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
        // NBS,Kun til sygehuse og speciallæger, TODO this one might be dispensable? Sounds like the one where the doctor has to have the right specialization.
        // PRMIX,Kun til fodermøller efter veterinær ordination,
        // SSI,Kan udleveres fra Statens Seruminstitut,
        // SVS,Kan udleveres fra Statens Vet. Serumlaboratorium,
    );
}
