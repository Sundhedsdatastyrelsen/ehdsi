package dk.nsp.epps.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatedWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthUnitCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PatientType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SimpleCPRPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageSizeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.ncp.api.ClassCodeDto;
import dk.nsp.epps.ncp.api.DocumentFormatDto;
import dk.nsp.epps.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.service.exception.CountryAException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class EPrescriptionMapper {
    private final Template template;
    private final String repositoryId;

    public EPrescriptionMapper(
        freemarker.template.Configuration cfg,
        @Value("${app.eprescription.repository.id}") String repositoryId
    ) throws IOException {
        template = cfg.getTemplate("eprescription-cda.ftlx");
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
        var model = new GetPrescriptionResponseModel(src, prescription);
        var drug = prescription.getDrug();
        var indication = prescription.getIndication();

        var meta = new EPrescriptionDocumentMetadataDto(String.valueOf(prescription.getIdentifier()));
        meta.setPatientId(patientId);
        meta.setFormat(DocumentFormatDto.XML);
        meta.setEffectiveTime(OffsetDateTime.now());
        meta.setClassCode(ClassCodeDto._57833_6);
        meta.setRepositoryId(repositoryId);
        meta.setTitle(prescription.getDrug().getName());
        meta.setAuthor(Optional.ofNullable(model.getAuthorisedHealthcareProfessionalNames()).map(Names::fullName).orElse(null));
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
            var document = new StringWriter();
            template.process(new GetPrescriptionResponseModel(src, prescription), document);

            return new EpsosDocumentDto(patientId, document.toString(), ClassCodeDto._57833_6);
        } catch (TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @lombok.Value
    public static class AdministrativeGender {
        private final String code;
        private final String displayName;
        private final String translation;
    }

    @lombok.Value
    @Builder
    public static class DrugStrength {
        private final String value;
        private final String unitCode;
        private final String unitText;
        private final String text;
    }

    @Getter
    @AllArgsConstructor
    public static class Names {
        private List<String> givenNames;
        private String surName;

        /**
         * The case where the danish spec just provides a single string with the given- and surnames, but we need to
         * fill out given and family tags. So we assume that the last name is the last thing in the string and all
         * before
         * are given names.
         */
        public Names(String names) {
            String[] nameArr = names.split(" ");
            givenNames = List.of(Arrays.copyOfRange(nameArr, 0, nameArr.length - 1));
            surName = nameArr[nameArr.length - 1];
        }

        public String fullName() {
            StringBuilder builder = new StringBuilder();
            if (givenNames != null) {
                givenNames.forEach(name -> builder.append(" ").append(name));
            }
            if (surName != null) {
                builder.append(" ").append(surName);
            }
            return builder.toString().trim();
        }
    }

    @lombok.Value
    public static class GetPrescriptionResponseModel {
        private final GetPrescriptionResponseType response;
        PrescriptionType prescription;

        public String getCdaInstanceId() {
            return UUID.randomUUID().toString();
        }

        public String getPatientFullName() {
            return Optional.of(response)
                .map(GetPrescriptionResponseType::getPatient)
                .map(PatientType::getPerson)
                .map(SimpleCPRPersonType::getName)
                .map(name -> {
                    var fullName = name.getGivenName();
                    fullName += name.getMiddleName();
                    fullName += name.getSurname();
                    return fullName;
                })
                .orElse(null);
        }

        public String getTitle() {
            var fullName = getPatientFullName();
            var id = prescription.getIdentifier();
            return String.format(
                "eHDSI ePrescription %s - %s",
                fullName, id
            );
        }

        public String getEffectiveTime() {
            return DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ").format(OffsetDateTime.now());
        }

        /**
         * Make a list of givenName + middleName for the template.
         */
        public List<String> getPatientGivenNames() {
            return Optional.of(response)
                .map(GetPrescriptionResponseType::getPatient)
                .map(PatientType::getPerson)
                .map(SimpleCPRPersonType::getName)
                .map(name -> {
                    List<String> l = new ArrayList<>(2);
                    if (name.getGivenName() != null) {
                        l.add(name.getGivenName());
                    }
                    if (name.getMiddleName() != null) {
                        l.add(name.getMiddleName());
                    }
                    return l;
                })
                .orElse(Collections.emptyList());
        }

        public AdministrativeGender getAdministrativeGender() {
            return Optional.of(response)
                .map(GetPrescriptionResponseType::getPatient)
                .map(PatientType::getPerson)
                .map(SimpleCPRPersonType::getGender)
                .map(gender -> switch (gender) {
                    case FEMALE -> new AdministrativeGender("F", "Female", "Kvinde");
                    case MALE -> new AdministrativeGender("M", "Male", "Mand");
                })
                .orElse(null);
        }

        public String getPatientBirthDate() {
            return Optional.of(response)
                .map(GetPrescriptionResponseType::getPatient)
                .map(PatientType::getPerson)
                .map(SimpleCPRPersonType::getBirthDate)
                .map(xml -> String.format("%04d%02d%02d", xml.getYear(), xml.getMonth(), xml.getDay()))
                .orElse(null);
        }

        private Optional<ModificatorWithOptionalAuthorisationIdentifierType> modificatorWithOptionalAuthorisationIdentifier() {
            return Optional.of(response)
                .map(GetPrescriptionResponseType::getPrescription)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(PrescriptionType::getCreated)
                .map(CreatedWithOptionalAuthorisationIdentifierType::getBy);
        }

        public Names getAuthorisedHealthcareProfessionalNames() {
            return modificatorWithOptionalAuthorisationIdentifier()
                .map(ModificatorWithOptionalAuthorisationIdentifierType::getContent)
                .map(list ->
                    list.stream().filter(jaxb ->
                            jaxb.getDeclaredType().isAssignableFrom(AuthorisedHealthcareProfessionalType.class)
                        )
                        .findFirst()
                        .orElse(null)
                )
                .map(xml -> (AuthorisedHealthcareProfessionalType) xml.getValue())
                .map(ahp -> new Names(ahp.getName()))
                .orElse(null);
        }

        public OrganisationType getRepresentedOrganization() {
            return modificatorWithOptionalAuthorisationIdentifier()
                .map(ModificatorWithOptionalAuthorisationIdentifierType::getContent)
                .map(list ->
                    list.stream().filter(jaxb ->
                            jaxb.getDeclaredType().isAssignableFrom(OrganisationType.class)
                        )
                        .findFirst()
                        .orElse(null)
                )
                .map(xml -> (OrganisationType) xml.getValue())
                .orElse(null);
        }

        public DrugStrength getDrugStrength() {
            return Optional.of(prescription)
                .map(PrescriptionType::getDrug)
                .map(DrugType::getStrength)
                .map(s -> {
                    var builder = DrugStrength.builder();
                    s.getContent().forEach(xml -> {
                        switch (xml.getName().getLocalPart()) {
                            case "Value":
                                builder.value(xml.getValue().toString());
                                break;
                            case "UnitCode":
                                var unitCode = (DrugStrengthUnitCodeType) xml.getValue();
                                builder.unitCode(unitCode.getValue());
                                break;
                            case "Text":
                                var text = (DrugStrengthTextType) xml.getValue();
                                builder.text(text.getValue());
                                break;
                            case "UnitText":
                                builder.unitText(xml.getValue().toString());
                                break;
                        }
                    });
                    return builder.build();
                })
                .orElse(null);
        }

        Map<String, String> lms15ToEhdsiUnit = Map.of(
            "ST", "1"
        );

        /**
         * Convert the FMK package size unit (LMS15) to eHDSIUnit
         * TODO
         *
         * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/voc-1.3.6.1.4.1.12559.11.10.1.3.1.42.16-2023-05-02T180000.html">eHDSIUnit</a>
         */
        public String getPackageSizeEhdsiUnit() {
            return Optional.of(prescription)
                .map(PrescriptionType::getPackageRestriction)
                .map(PackageRestrictionType::getPackageSize)
                .map(PackageSizeType::getUnitCode)
                .map(unitCode -> {
                    var unit = lms15ToEhdsiUnit.get(unitCode);
                    if (unit == null) {
                        throw new IllegalStateException("Unexpected value: " + unitCode);
                    }
                    return unit;
                })
                .orElse(null);
        }
    }
}
