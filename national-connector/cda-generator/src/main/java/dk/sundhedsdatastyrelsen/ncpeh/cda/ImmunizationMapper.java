package dk.sundhedsdatastyrelsen.ncpeh.cda;


import dk.vaccinationsregister.schemas._2013._12._01.DrugStrengthType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
import dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType;
import jakarta.xml.bind.JAXBElement;

import java.util.Objects;
import java.util.Optional;

public class ImmunizationMapper {



    private static Product product(SSIDrugType ssiDrug) {

        if (ssiDrug == null || ssiDrug.getDrugName() == null) {
            return null;
        }

        var form = ssiDrug.getDrugForm();
        if (form == null || form.getDrugFormCode() == null || form.getDrugFormText() == null) {
            return null;
        }

        var atc = ssiDrug.getATC();
        if (atc == null || atc.getCode() == null || atc.getText() == null) {
            return null;
        }

        var formCode = CdaCode.builder()
            .codeSystem(Oid.DK_LMS22)
            .code(form.getDrugFormCode())
            .displayName(form.getDrugFormText())
            .build();

        var atcCode = CdaCode.builder()
            .codeSystem(Oid.ATC)
            .code(atc.getCode())
            .displayName(atc.getText())
            .build();

        return Product.builder()
            .name(ssiDrug.getDrugName())
            .strength(getSubstanceStrengthText(ssiDrug.getDrugStrength()))
            .formCode(formCode)
            .atcCode(atcCode)
            .build();
    }

    // DDV's JAXB-generated DrugStrengthType stores strength fields in a generic JAXBElement
    // content list due to the underlying XSD choice definition, so we must find the
    // DrugStrengthText element manually or we should change the XSD definition
    // TODO: Follow-up on this
    private static String getSubstanceStrengthText(DrugStrengthType strength) {
        return strength.getDrugStrengthText();
    }
}
