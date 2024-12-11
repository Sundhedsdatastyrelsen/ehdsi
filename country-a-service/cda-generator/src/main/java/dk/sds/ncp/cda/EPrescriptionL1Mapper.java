package dk.sds.ncp.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.sds.ncp.cda.model.EPrescriptionL1;
import dk.sds.ncp.cda.model.EPrescriptionL3;

import java.util.Base64;
import java.util.Optional;

public class EPrescriptionL1Mapper {
    public static EPrescriptionL1 model(GetPrescriptionResponseType response, int prescriptionIndex) throws MapperException {
        return model(EPrescriptionL3Mapper.model(response, Optional.empty(), prescriptionIndex)); //Should this also have the medication? Probably
    }

    public static EPrescriptionL1 model(EPrescriptionL3 l3Model) throws MapperException {
        var pdfModel = EPrescriptionPdfMapper.map(l3Model);
        var pdf = EPrescriptionPdfGenerator.generate(pdfModel);
        var base64Pdf = Base64.getEncoder().encodeToString(pdf);
        return EPrescriptionL1.builder()
            .l3(l3Model)
            .base64EncodedDocument(base64Pdf)
            .build();
    }
}
