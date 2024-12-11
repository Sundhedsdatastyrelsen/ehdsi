package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.ncp.api.*;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.service.Utils;
import dk.nsp.epps.service.exception.CountryAException;
import dk.sds.ncp.cda.*;
import dk.sds.ncp.cda.model.DocumentLevel;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class EPrescriptionMapper {
    private final String repositoryId;

    public EPrescriptionMapper(@Value("${app.eprescription.repository.id}") String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> mapMeta(
        String patientId, PrescriptionFilter filter,
        GetPrescriptionResponseType src
    ) {
        return filter.validPrescriptionIndexes(src.getPrescription())
            .mapToObj(idx -> mapMeta(patientId, src, idx))
            .toList();
    }

    public List<EpsosDocumentDto> mapResponse(
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

    private DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType response, int prescriptionIndex) {
        try {
            final String cda;
            var dataModel = EPrescriptionL3Mapper.model(response, Optional.empty(), prescriptionIndex);
            try {
                cda = EPrescriptionL3Generator.generate(dataModel);
            } catch (TemplateException | IOException e) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
            var l3Meta = generateMeta(patientId, dataModel, EPrescriptionDocumentIdMapper.level3DocumentId(dataModel.getPrescriptionId()
                .getExtension()));
            l3Meta.setSize((long) cda.length());
            l3Meta.setHash(Utils.md5Hash(cda));

            //Generate PDF to deliver metadata on it
            var pdf = EPrescriptionL1Generator.generate(EPrescriptionL1Mapper.map(dataModel));

            var l1Meta = generateMeta(patientId, dataModel, EPrescriptionDocumentIdMapper.level1DocumentId(dataModel.getPrescriptionId()
                .getExtension()));
            l1Meta.setSize((long) pdf.length);
            l1Meta.setHash(Utils.md5Hash(pdf));

            return new DocumentAssociationForEPrescriptionDocumentMetadataDto(l3Meta, l1Meta);

        } catch (MapperException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private EPrescriptionDocumentMetadataDto generateMeta(String patientId, EPrescriptionL3 model, String documentId) {
        var meta = new EPrescriptionDocumentMetadataDto(documentId);
        meta.setPatientId(patientId);
        meta.setEffectiveTime(model.getEffectiveTimeOffsetDateTime());
        meta.setRepositoryId(repositoryId);
        meta.setAuthor(model.getAuthor().getName().getFullName());
        meta.setTitle(model.getTitle());
        meta.setDescription(model.getIndicationText());
        return meta;
    }

    private EpsosDocumentDto mapPrescription(String patientId, GetPrescriptionResponseType response, GetDrugMedicationResponseType drugMedicationResponse, int prescriptionIndex, DocumentLevel documentLevel) {
        try {
            var model = EPrescriptionL3Mapper.model(response, Optional.of(drugMedicationResponse), prescriptionIndex);
            if (DocumentLevel.LEVEL3.equals(documentLevel)) {
                var cda = EPrescriptionL3Generator.generate(model);
                return new EpsosDocumentDto(patientId, cda, ClassCodeDto._57833_6);
            } else if (DocumentLevel.LEVEL1.equals(documentLevel)) {
                var pdf = EPrescriptionL1Generator.generate(EPrescriptionL1Mapper.map(model));
                var base64Pdf = Base64.getEncoder().encodeToString(pdf);
                return new EpsosDocumentDto(patientId, base64Pdf, ClassCodeDto._57833_6);
            }
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Could not generate document of level %s", documentLevel));

        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
