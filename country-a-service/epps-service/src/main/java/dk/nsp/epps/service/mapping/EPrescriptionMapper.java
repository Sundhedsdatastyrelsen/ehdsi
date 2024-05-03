package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.ncp.api.ClassCodeDto;
import dk.nsp.epps.ncp.api.DocumentFormatDto;
import dk.nsp.epps.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.service.exception.CountryAException;
import dk.sds.ncp.cda.EPrescriptionL3Generator;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EPrescriptionMapper {
    private final String repositoryId;

    public EPrescriptionMapper(@Value("${app.eprescription.repository.id}") String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public List<EPrescriptionDocumentMetadataDto> mapMeta(String patientId, PrescriptionFilter filter,
                                                          GetPrescriptionResponseType src) {
        return filter.applyTo(src.getPrescription())
            .map(prescription -> mapMeta(patientId, src, prescription))
            .toList();
    }

    public List<EpsosDocumentDto> mapResponse(String patientId, PrescriptionFilter filter,
                                              GetPrescriptionResponseType src) throws TemplateException, IOException {
        return filter.applyTo(src.getPrescription())
            .map(prescription -> mapPrescription(patientId, src, prescription))
            .toList();
    }

    private EPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType src,
                                                     PrescriptionType prescription) {
        var model = new EPrescriptionL3(src, prescription);
        var drug = prescription.getDrug();
        var indication = prescription.getIndication();

        var meta = new EPrescriptionDocumentMetadataDto(String.valueOf(prescription.getIdentifier()));
        meta.setPatientId(patientId);
        meta.setFormat(DocumentFormatDto.XML);
        meta.setEffectiveTime(OffsetDateTime.now());
        meta.setClassCode(ClassCodeDto._57833_6);
        meta.setRepositoryId(repositoryId);
        meta.setTitle(prescription.getDrug().getName());
        meta.setAuthor(Optional.ofNullable(model.getAuthorisedHealthcareProfessionalNames()).map(EPrescriptionL3.Names::fullName).orElse(null));
        meta.setLanguage(null);
        meta.setSize(null);
        meta.setHash(null);
        meta.setConfidentiality(null);
        if (indication != null) {
            meta.setDescription(indication.getFreeText() != null ? indication.getFreeText() : indication.getText());
        }
        if (drug != null) {
            meta.setProductCode(drug.getIdentifier() != null ? String.valueOf(drug.getIdentifier().getValue()) : null);
            meta.setProductName(drug.getName());
            var atc = drug.getATC();
            if (atc != null) {
                meta.setAtcCode(atc.getCode() != null ? atc.getCode().getValue() : null);
                meta.setAtcName(atc.getText());
            }
        }
        meta.setDispensable(null);
        meta.setDoseFormCode(null);
        meta.setDoseFormName(null);
        meta.setStrength(model.getDrugStrength() != null ? model.getDrugStrength().getText() : null);
        meta.setSubstitutionCode(null);
        meta.setSubstitutionDisplayName(null);
        return meta;
    }

    private EpsosDocumentDto mapPrescription(String patientId, GetPrescriptionResponseType src,
                                             PrescriptionType prescription) {
        try {
            var document = EPrescriptionL3Generator.generate(new EPrescriptionL3(src, prescription));
            return new EpsosDocumentDto(patientId, document, ClassCodeDto._57833_6);
        } catch (TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
