package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.vaccinationsregister.schemas._2013._12._01.AuthorisedHealthcareProfessionalType;
import dk.vaccinationsregister.schemas._2013._12._01.CreatedType;
import dk.vaccinationsregister.schemas._2013._12._01.DrugStrengthType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.vaccinationsregister.schemas._2013._12._01.ModificatorType;
import dk.vaccinationsregister.schemas._2013._12._01.OrganisationType;
import dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType;
import dk.vaccinationsregister.schemas._2013._12._01.VaccinationType;
import dk.vaccinationsregister.schemas._2013._12._01.VaccineType;

import java.util.Optional;

public class ImmunizationMapper {

    public static CdaId immunizationId(VaccinationType vaccine) {
        var id = vaccine.getVaccinationIdentifier();

        return new CdaId(
            Oid.DK_DDV_VACCINATION,
            Long.toString(id)
        );
    }

    public static CdaCode getDrugId(SSIDrugType ssiDrug) {
        return Optional.ofNullable(ssiDrug)
            .map(SSIDrugType::getDrugIdentifier)
            .map(id -> CdaCode.builder()
                .codeSystem(Oid.DK_DDV_SSI_DRUG)
                .code(String.valueOf(id))
                .build())
            .orElse(null);
    }

    public static String getDrugName(SSIDrugType ssiDrug) {
        return Optional.ofNullable(ssiDrug)
            .map(SSIDrugType::getDrugName)
            .orElse(null);
    }

    public static String getStrength(SSIDrugType ssiDrug) {
        return Optional.ofNullable(ssiDrug)
            .map(SSIDrugType::getDrugStrength)
            .map(ImmunizationMapper::getSubstanceStrengthText)
            .orElse(null);
    }

    public static CdaCode getAtcCode(SSIDrugType ssiDrug) {
        return Optional.ofNullable(ssiDrug)
            .map(SSIDrugType::getATC)
            .filter(atc -> atc.getCode() != null && atc.getText() != null)
            .map(atc -> CdaCode.builder()
                .codeSystem(Oid.ATC)
                .code(atc.getCode())
                .displayName(atc.getText())
                .build())
            .orElse(null);
    }

    public static CdaCode getFormCode(SSIDrugType ssiDrug) {
        return Optional.ofNullable(ssiDrug)
            .map(SSIDrugType::getDrugForm)
            .filter(form -> form.getDrugFormCode() != null && form.getDrugFormText() != null)
            .map(form -> CdaCode.builder()
                .codeSystem(Oid.DK_LMS22)
                .code(form.getDrugFormCode())
                .displayName(form.getDrugFormText())
                .build())
            .orElse(null);
    }

    private static String getSubstanceStrengthText(DrugStrengthType strength) {
        return strength.getDrugStrengthText();
    }

    public static String fallbackVaccineName(VaccinationType vaccination) {
        return Optional.ofNullable(vaccination.getVaccine())
            .map(VaccineType::getVaccineName)
            .orElse("Unknown vaccine");
    }

    public static String getCreatedAuthorisationId(VaccinationType vaccination) {
        return Optional.ofNullable(vaccination.getCreated())
            .map(CreatedType::getModificator)
            .map(ModificatorType::getAuthorisedHealthcareProfessional)
            .map(AuthorisedHealthcareProfessionalType::getAuthorisationIdentifier)
            .orElse(null);
    }

    public static String getCreatedAuthorName(VaccinationType vaccination) {
        return Optional.ofNullable(vaccination.getCreated())
            .map(CreatedType::getModificator)
            .map(ModificatorType::getAuthorisedHealthcareProfessional)
            .map(AuthorisedHealthcareProfessionalType::getName)
            .orElse(null);
    }

    public static String getCreatedOrganisationId(VaccinationType vaccination) {
        return Optional.ofNullable(vaccination.getCreated())
            .map(CreatedType::getModificator)
            .map(ModificatorType::getOrganisation)
            .map(org -> org.getIdentifier().getValue())
            .orElse(null);
    }

    public static String getCreatedOrganisationName(VaccinationType vaccination) {
        return Optional.ofNullable(vaccination.getCreated())
            .map(CreatedType::getModificator)
            .map(ModificatorType::getOrganisation)
            .map(OrganisationType::getName)
            .orElse(null);
    }
}
