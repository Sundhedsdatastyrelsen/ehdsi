package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Input;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionPdfGenerator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionPdfMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import freemarker.template.TemplateException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class EPrescriptionMapper {

    private EPrescriptionMapper() {
    }

    public static DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, EPrescriptionL3Input generatorInput) {
        try {
            final String cda;
            var dataModel = EPrescriptionL3Mapper.model(generatorInput);
            try {
                cda = EPrescriptionL3Generator.generate(dataModel);
            } catch (TemplateException | IOException e) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
            var l3Meta = generateMeta(
                patientId, dataModel, EPrescriptionDocumentIdMapper.level3DocumentId(dataModel.getPrescriptionId()
                    .getExtension()));
            l3Meta.setSize((long) cda.length());
            // "Document metadata shall include a SHA1 hash of the document content."
            // https://www.ihe.net/uploadedFiles/Documents/ITI/IHE_ITI_TF_Rev17-0_Vol1_FT_2020-07-20.pdf
            l3Meta.setHash(DigestUtils.sha1Hex(cda));

            //Generate PDF to deliver metadata on it
            var pdf = EPrescriptionPdfGenerator.generate(EPrescriptionPdfMapper.map(dataModel));

            var l1Meta = generateMeta(
                patientId, dataModel, EPrescriptionDocumentIdMapper.level1DocumentId(dataModel.getPrescriptionId()
                    .getExtension()));
            l1Meta.setSize((long) pdf.length);
            // "Document metadata shall include a SHA1 hash of the document content."
            // https://www.ihe.net/uploadedFiles/Documents/ITI/IHE_ITI_TF_Rev17-0_Vol1_FT_2020-07-20.pdf
            l1Meta.setHash(DigestUtils.sha1Hex(cda));

            return new DocumentAssociationForEPrescriptionDocumentMetadataDto(l3Meta, l1Meta);

        } catch (MapperException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private static EPrescriptionDocumentMetadataDto generateMeta(String patientId, EPrescriptionL3 model, String documentId) {
        var meta = new EPrescriptionDocumentMetadataDto(documentId);
        meta.setPatientId(patientId);
        meta.setEffectiveTime(model.getEffectiveTimeOffsetDateTime());
        meta.setRepositoryId(Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value);
        meta.setAuthor(model.getAuthor().getName().getFullName());
        meta.setTitle(model.getTitle());
        meta.setDescription(model.getIndicationText());
        return meta;
    }
}
