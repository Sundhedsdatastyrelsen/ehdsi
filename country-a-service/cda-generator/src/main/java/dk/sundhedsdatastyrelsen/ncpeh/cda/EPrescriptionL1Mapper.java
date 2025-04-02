package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL1;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;

import java.util.Base64;

public final class EPrescriptionL1Mapper {
    private EPrescriptionL1Mapper() {
    }

    public static EPrescriptionL1 model(EPrescriptionL3Input input) throws MapperException {
        return model(EPrescriptionL3Mapper.model(input));
    }

    public static EPrescriptionL1 model(EPrescriptionL3 l3Model) throws MapperException {
        var modelWithL1Id = l3Model.toBuilder()
            .documentId(new CdaId(
                Oid.DK_EPRESCRIPTION_REPOSITORY_ID, EPrescriptionDocumentIdMapper.level1DocumentId(l3Model.getPrescriptionId()
                .getExtension())))
            .build();
        var pdfModel = EPrescriptionPdfMapper.map(modelWithL1Id);
        var pdf = EPrescriptionPdfGenerator.generate(pdfModel);
        var base64Pdf = Base64.getEncoder().encodeToString(pdf);
        return EPrescriptionL1.builder()
            .l3(modelWithL1Id)
            .base64EncodedDocument(base64Pdf)
            .build();
    }
}
