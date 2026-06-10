package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DrugMedicationType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.vaccinationsregister.schemas._2013._12._01.AuthorisedHealthcareProfessionalType;
import dk.vaccinationsregister.schemas._2013._12._01.CreatedType;
import dk.vaccinationsregister.schemas._2013._12._01.DrugStrengthType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
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

    public static Product product(SSIDrugType ssiDrug) {

        if (ssiDrug == null || ssiDrug.getDrugName() == null) {
            return null;
        }
        var drugId = ssiDrug.getDrugIdentifier();
        var codedId = drugId != null ? CdaCode.builder()
            .codeSystem(Oid.DK_DDV_SSI_DRUG)
            .code(String.valueOf(drugId))
            .build() : null;

        var form = ssiDrug.getDrugForm();
        if (form == null || form.getDrugFormCode() == null || form.getDrugFormText() == null) {
            return null;
        }

        var atc = ssiDrug.getATC();
        if (atc == null || atc.getCode() == null || atc.getText() == null) {
            return null;
        }

        var formCode = CdaCode.builder()
            .codeSystem(Oid.DK_LMS22) //TODO: Check if this is the right code.
            .code(form.getDrugFormCode())
            .displayName(form.getDrugFormText())
            .build();

        var atcCode = CdaCode.builder()
            .codeSystem(Oid.ATC)
            .code(atc.getCode())
            .displayName(atc.getText())
            .build();

        return Product.builder()
            .drugId(codedId)
            .name(ssiDrug.getDrugName())
            .strength(getSubstanceStrengthText(ssiDrug.getDrugStrength()))
            .formCode(formCode)
            .atcCode(atcCode)
            .build();
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
