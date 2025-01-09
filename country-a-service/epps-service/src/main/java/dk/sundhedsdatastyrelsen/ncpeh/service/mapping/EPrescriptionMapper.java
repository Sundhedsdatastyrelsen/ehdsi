package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService.PrescriptionFilter;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL1Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionPdfGenerator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionPdfMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

public class EPrescriptionMapper {

    private EPrescriptionMapper() {
    }

    public static List<DocumentAssociationForEPrescriptionDocumentMetadataDto> mapMeta(String patientId, PrescriptionFilter filter,
                                                          GetPrescriptionResponseType src) {
        return filter.validPrescriptionIndexes(src.getPrescription())
            .mapToObj(idx -> mapMeta(patientId, src, idx))
            .toList();
    }

    public static List<EpsosDocumentDto> mapResponse(
        String patientId,
        PrescriptionFilter filter,
        GetPrescriptionResponseType src,
        GetDrugMedicationResponseType drugMedicationResponse
    ) {
        try {
            var documentLevel = EPrescriptionDocumentIdMapper.parseDocumentLevel(filter.documentId());
            return filter.validPrescriptionIndexes(src.getPrescription())
                .mapToObj(idx -> mapPrescription(patientId, src, drugMedicationResponse, idx, documentLevel))
                .toList();
        } catch (MapperException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private static DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType response, int prescriptionIndex) {
        try {
            final String cda;
            var dataModel = EPrescriptionL3Mapper.model(response, prescriptionIndex);
            try {
                cda = EPrescriptionL3Generator.generate(dataModel);
            } catch (TemplateException | IOException e) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
            var l3Meta = generateMeta(patientId, dataModel, EPrescriptionDocumentIdMapper.level3DocumentId(dataModel.getPrescriptionId().getExtension()));
            l3Meta.setSize((long) cda.length());
            // "Document metadata shall include a SHA1 hash of the document content."
            // https://www.ihe.net/uploadedFiles/Documents/ITI/IHE_ITI_TF_Rev17-0_Vol1_FT_2020-07-20.pdf
            l3Meta.setHash(DigestUtils.sha1Hex(cda));

            //Generate PDF to deliver metadata on it
            var pdf = EPrescriptionPdfGenerator.generate(EPrescriptionPdfMapper.map(dataModel));

            var l1Meta = generateMeta(patientId, dataModel, EPrescriptionDocumentIdMapper.level1DocumentId(dataModel.getPrescriptionId().getExtension()));
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

    private static EpsosDocumentDto mapPrescription(String patientId, GetPrescriptionResponseType response,GetDrugMedicationResponseType medicationResponseType, int prescriptionIndex, DocumentLevel documentLevel) {
        try {
            String cda = switch (documentLevel) {
                case LEVEL3 -> EPrescriptionL3Generator.generate(response, medicationResponseType, prescriptionIndex);
                case LEVEL1 -> EPrescriptionL1Generator.generate(response, prescriptionIndex);
            };
            return new EpsosDocumentDto(patientId, cda, ClassCodeDto._57833_6);
        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
