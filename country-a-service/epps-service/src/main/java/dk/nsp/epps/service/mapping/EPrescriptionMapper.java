package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.ncp.api.*;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.service.Utils;
import dk.nsp.epps.service.exception.CountryAException;
import dk.sds.ncp.cda.*;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Component
public class EPrescriptionMapper {
    private final String repositoryId;

    public EPrescriptionMapper(@Value("${app.eprescription.repository.id}") String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> mapMeta(String patientId, PrescriptionFilter filter,
                                                          GetPrescriptionResponseType src) {
        return filter.validPrescriptionIndexes(src.getPrescription())
            .mapToObj(idx -> mapMeta(patientId, src, idx))
            .toList();
    }

    public List<DocumentAssociationForEPrescriptionDocumentsDto> mapResponse(
        String patientId,
        PrescriptionFilter filter,
        GetPrescriptionResponseType src
    ) {
        return filter.validPrescriptionIndexes(src.getPrescription())
            .mapToObj(idx -> mapPrescription(patientId, src, idx))
            .toList();
    }

    private DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType response, int prescriptionIndex) {
        try {
            String cda = null;
            var dataModel = EPrescriptionL3Mapper.model(response, prescriptionIndex);
            try {
                cda = EPrescriptionL3Generator.generate(dataModel);
            } catch (TemplateException | IOException e) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
            var l3Meta = GenerateMeta(patientId, dataModel);
            l3Meta.setSize((long) cda.length());
            l3Meta.setHash(Utils.Md5Hash(cda));

            //Generate PDF to deliver metadata on it
            var pdfBase64 = new EPrescriptionL1Generator(EPrescriptionL1Mapper.Map(dataModel)).generate();

            var l1Meta = GenerateMeta(patientId, dataModel);
            l1Meta.setSize((long) pdfBase64.length());
            l1Meta.setHash(Utils.Md5Hash(pdfBase64));

            return new DocumentAssociationForEPrescriptionDocumentMetadataDto(l3Meta, l1Meta);

        } catch (MapperException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private EPrescriptionDocumentMetadataDto GenerateMeta(String patientId, EPrescriptionL3 model) {
        var meta = new EPrescriptionDocumentMetadataDto(model.getPrescriptionId().getExtension()); //TODO  Should this be different for our L1 data?
        meta.setPatientId(patientId);
        meta.setEffectiveTime(model.getEffectiveTimeOffsetDateTime());
        meta.setRepositoryId(repositoryId);
        meta.setAuthor(model.getAuthor().getName().getFullName());
        meta.setTitle(model.getTitle());
        meta.setDescription(model.getIndicationText());
        return meta;
    }

    private DocumentAssociationForEPrescriptionDocumentsDto mapPrescription(String patientId, GetPrescriptionResponseType response, int prescriptionIndex) {
        try {
            var model = EPrescriptionL3Mapper.model(response, prescriptionIndex);
            var cda = EPrescriptionL3Generator.generate(model);
            var level3Document = new EpsosDocumentDto(patientId, cda, ClassCodeDto._57833_6);

            var pdfBase64 = new EPrescriptionL1Generator(EPrescriptionL1Mapper.Map(model)).generate();
            var level1Document = new EpsosDocumentDto(patientId, pdfBase64, ClassCodeDto._57833_6); //TODO Should this be the same ClassCode?



            return new DocumentAssociationForEPrescriptionDocumentsDto(level3Document, level1Document);
        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
