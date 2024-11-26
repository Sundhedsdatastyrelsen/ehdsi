package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.ncp.api.ClassCodeDto;
import dk.nsp.epps.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.service.Utils;
import dk.nsp.epps.service.exception.CountryAException;
import dk.sds.ncp.cda.EPrescriptionDocumentIdMapper;
import dk.sds.ncp.cda.EPrescriptionL1Generator;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.EPrescriptionL3Mapper;
import dk.sds.ncp.cda.EPrescriptionPdfGenerator;
import dk.sds.ncp.cda.EPrescriptionPdfMapper;
import dk.sds.ncp.cda.MapperException;
import dk.sds.ncp.cda.Oid;
import dk.sds.ncp.cda.model.DocumentLevel;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
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
        GetPrescriptionResponseType src
    ) {
        try {
            var documentLevel = EPrescriptionDocumentIdMapper.parseDocumentLevel(filter.documentId());
            return filter.validPrescriptionIndexes(src.getPrescription())
                .mapToObj(idx -> mapPrescription(patientId, src, idx, documentLevel))
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
            l3Meta.setHash(Utils.md5Hash(cda));

            //Generate PDF to deliver metadata on it
            var pdf = EPrescriptionPdfGenerator.generate(EPrescriptionPdfMapper.map(dataModel));

            var l1Meta = generateMeta(patientId, dataModel, EPrescriptionDocumentIdMapper.level1DocumentId(dataModel.getPrescriptionId().getExtension()));
            l1Meta.setSize((long) pdf.length);
            l1Meta.setHash(Utils.md5Hash(pdf));

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

    private static EpsosDocumentDto mapPrescription(String patientId, GetPrescriptionResponseType response, int prescriptionIndex, DocumentLevel documentLevel) {
        try {
            String cda = switch (documentLevel) {
                case LEVEL3 -> EPrescriptionL3Generator.generate(response, prescriptionIndex);
                case LEVEL1 -> EPrescriptionL1Generator.generate(response, prescriptionIndex);
            };
            return new EpsosDocumentDto(patientId, cda, ClassCodeDto._57833_6);
        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
