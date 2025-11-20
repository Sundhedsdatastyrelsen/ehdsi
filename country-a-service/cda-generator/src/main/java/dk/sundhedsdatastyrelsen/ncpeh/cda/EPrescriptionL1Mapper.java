package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL1;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;

import java.util.Base64;

public final class EPrescriptionL1Mapper {
    private EPrescriptionL1Mapper() {
    }

    /// @throws MapperException if something goes wrong
    public static EPrescriptionL1 model(EPrescriptionL3Input input) {
        return model(EPrescriptionL3Mapper.model(input));
    }

    /// @throws MapperException if something goes wrong
    public static EPrescriptionL1 model(EPrescriptionL3 l3Model) {
        var modelWithL1Id = l3Model.withDocumentId(new CdaId(
            Oid.DK_EPRESCRIPTION_REPOSITORY_ID,
            EPrescriptionDocumentIdMapper.level1DocumentId(l3Model.getPrescriptionId().getExtension())));
        var pdfModel = EPrescriptionPdfMapper.map(modelWithL1Id);
        var pdf = EPrescriptionPdfGenerator.generate(pdfModel);
        var base64Pdf = Base64.getEncoder().encodeToString(pdf);
        return EPrescriptionL1.builder()
            .l3(modelWithL1Id)
            .base64EncodedDocument(base64Pdf)
            .build();
    }
}
