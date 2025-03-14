package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL1;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;

import java.util.Base64;

public class EPrescriptionL1Mapper {
    public static EPrescriptionL1 model(EPrescriptionL3Input input) throws MapperException {
        return model(EPrescriptionL3Mapper.model(input));
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
