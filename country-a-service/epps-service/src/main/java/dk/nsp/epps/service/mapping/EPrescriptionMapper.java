package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.ncp.api.ClassCodeDto;
import dk.nsp.epps.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.service.exception.CountryAException;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.EPrescriptionL3Mapper;
import dk.sds.ncp.cda.MapperException;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EPrescriptionMapper {
    private final String repositoryId;

    public EPrescriptionMapper(@Value("${app.eprescription.repository.id}") String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public List<EPrescriptionDocumentMetadataDto> mapMeta(String patientId, PrescriptionFilter filter,
                                                          GetPrescriptionResponseType src) {
        return filter.validPrescriptionIndexes(src.getPrescription())
            .mapToObj(idx -> mapMeta(patientId, src, idx))
            .toList();
    }

    public List<EpsosDocumentDto> mapResponse(
        String patientId,
        PrescriptionFilter filter,
        GetPrescriptionResponseType src
    ) {
        return filter.validPrescriptionIndexes(src.getPrescription())
            .mapToObj(idx -> mapPrescription(patientId, src, idx))
            .toList();
    }

    private EPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType response, int prescriptionIndex) {
        try {
            String cda = null;
            try {
                cda = EPrescriptionL3Generator.generate(response, prescriptionIndex);
            } catch (TemplateException | IOException e) {
                throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
            var model = EPrescriptionL3Mapper.model(response, prescriptionIndex,cda);
            var meta = new EPrescriptionDocumentMetadataDto(model.getPrescriptionId().getExtension());
            meta.setPatientId(patientId);
            meta.setEffectiveTime(model.getEffectiveTimeOffsetDateTime());
            meta.setRepositoryId(repositoryId);
            meta.setAuthor(model.getAuthor().getName().getFullName());
            meta.setTitle(model.getTitle());
            meta.setDescription(model.getIndicationText());
            meta.setSize(model.GetSize());
            meta.setHash(model.GetHash());
            return meta;

        } catch (MapperException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private EpsosDocumentDto mapPrescription(String patientId, GetPrescriptionResponseType response, int prescriptionIndex) {
        try {
            var cda = EPrescriptionL3Generator.generate(response, prescriptionIndex);
            return new EpsosDocumentDto(patientId, cda, ClassCodeDto._57833_6);
        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
