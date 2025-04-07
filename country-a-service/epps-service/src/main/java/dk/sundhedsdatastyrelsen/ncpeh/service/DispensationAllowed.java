package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;

import java.util.List;

public final class DispensationAllowed {
    private DispensationAllowed() {
    }

    public static boolean isDispensationAllowed(Lms02Data lms02Entry) {
        return dispensableRegulations.stream()
            .anyMatch(code -> code.equals(lms02Entry.getDispensationRegulationCode()));
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
