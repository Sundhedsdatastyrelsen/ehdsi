package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.vaccinationsregister.schemas._2013._12._01.ModificatorType;
import dk.vaccinationsregister.schemas._2013._12._01.OrganisationType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ModificatorInfo;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ModificatorKind;

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
            case OTHER -> mapOther(mod); //Patient reported
            case UNKNOWN -> null;
        };
    }

    private static ModificatorInfo mapHealthcareProfessional(ModificatorType mod) {
        var hp = mod.getAuthorisedHealthcareProfessional();

        return ModificatorInfo.builder()
            .authorisationId(hp.getAuthorisationIdentifier())
            .authorName(hp.getName())
            .organisationId(Optional.ofNullable(mod.getOrganisation())
                .map(o -> o.getIdentifier().getValue())
                .orElse(null))
            .organisationName(Optional.ofNullable(mod.getOrganisation())
                .map(OrganisationType::getName)
                .orElse(null))
            .countryCode("DK") //This is always country of origin which in our case is "DK"
            .kind(ModificatorKind.HEALTHCARE_PROFESSIONAL)
            .build();

    }

    private static ModificatorInfo mapPartlyDefinedEffectuator(ModificatorType mod) {
        var effectuator = mod.getPartlyDefinedEffectuator();

        return ModificatorInfo.builder()
            .authorName(effectuator.getEffectuatedByName())
            .organisationName(effectuator.getEffectuatedByOrganisationName())
            .countryCode(effectuator.getEffectuatedInCountryCode() != null ? effectuator.getEffectuatedInCountryCode() : "DK") //Required in the input, but we stay on the safe side therefore this null check
            .kind(ModificatorKind.PARTLY_DEFINED_EFFECTUATOR)
            .build();
    }

    private static ModificatorInfo mapOrganisation(ModificatorType mod) {
        var org = mod.getOrganisation();

        //TODO: Expand this with correct information.
        return ModificatorInfo.builder()
            .organisationId(org.getIdentifier().getValue())
            .organisationName(org.getName())
            .countryCode("DK") //This is always country of origin which in our case is "DK"
            .kind(ModificatorKind.ORGANISATION)
            .build();
    }

    private static ModificatorInfo mapOther(ModificatorType mod) {
        var other = mod.getOther(); //This is if a person adds/reports the vaccination themselves
        //TODO: get a example on how this is mapped.
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
        if (mod.getOther() != null) {
            return ModificatorKind.OTHER;
        }

        return ModificatorKind.UNKNOWN;
    }
}
