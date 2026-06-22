package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ModificatorInfo;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ModificatorKind;
import dk.vaccinationsregister.schemas._2013._12._01.ModificatorType;
import dk.vaccinationsregister.schemas._2013._12._01.OrganisationType;

import java.util.Optional;

public class ModificatorTypeMapper {

    public static ModificatorInfo map(ModificatorType mod) {
        if (mod == null) {
            return null;
        }

        return switch (determineKind(mod)) {
            case HEALTHCARE_PROFESSIONAL -> mapHealthcareProfessional(mod);
            case PARTLY_DEFINED_EFFECTUATOR -> mapPartlyDefinedEffectuator(mod);
            case ORGANISATION -> mapOrganisation(mod);
            case PATIENT -> mapPatient(mod);
            case OTHER -> mapOther(mod);
            case SYSTEM -> mapSystem(mod);
            case UNKNOWN -> null;
        };
    }


    private static ModificatorInfo mapHealthcareProfessional(ModificatorType mod){
        var hp = mod.getAuthorisedHealthcareProfessional();
        return new ModificatorInfo(
            hp.getAuthorisationIdentifier(),
            hp.getName(),
            Optional.ofNullable(mod.getOrganisation())
                .map(o -> o.getIdentifier().getValue())
                .orElse(null),
            Optional.ofNullable(mod.getOrganisation())
                .map(OrganisationType::getName)
                .orElse(null),
            null,
            ModificatorKind.HEALTHCARE_PROFESSIONAL

        );
    }

    private static ModificatorInfo mapPartlyDefinedEffectuator(ModificatorType mod){
        return null;
    }

    private static ModificatorInfo mapOrganisation(ModificatorType mod){
        return null;
    }

    private static ModificatorInfo mapPatient(ModificatorType mod){
        return null;
    }

    private static ModificatorInfo mapOther(ModificatorType mod){
        return null;
    }

    private static ModificatorInfo mapSystem(ModificatorType mod){
        return null;
    }

    private static ModificatorKind determineKind(ModificatorType mod) {
        if (mod.getAuthorisedHealthcareProfessional() != null) {
            return ModificatorKind.HEALTHCARE_PROFESSIONAL;
        }
        if (mod.getPartlyDefinedEffectuator() != null) {
            return ModificatorKind.PARTLY_DEFINED_EFFECTUATOR;
        }
        if (mod.getOrganisation() != null) {
            return ModificatorKind.ORGANISATION;
        }

        return ModificatorKind.UNKNOWN;
    }
}
