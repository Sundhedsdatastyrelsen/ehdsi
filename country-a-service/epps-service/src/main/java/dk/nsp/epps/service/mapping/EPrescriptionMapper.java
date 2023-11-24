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
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EPrescriptionMapper {
    private final freemarker.template.Configuration cfg;
    private Template template;

    @PostConstruct
    public void initTemplate() throws IOException {
        template = cfg.getTemplate("eprescription-cda.ftl");
    }

    public List<EpsosDocumentDto> mapResponse(GetPrescriptionResponseType src) throws TemplateException, IOException {
        return src.getPrescription().stream()
            .map(prescription -> mapPrescription(src, prescription))
            .collect(Collectors.toList());
    }

    private EpsosDocumentDto mapPrescription(GetPrescriptionResponseType src, PrescriptionType prescription) {
        try {
            var result = new EpsosDocumentDto();

            var out = new StringWriter();
            template.process(new GetPrescriptionResponseModel(src, prescription), out);
            result.setDocument(out.toString());

            return result;
        } catch (TemplateException|IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Value
    public static class AdministrativeGender {
        private final String code;
        private final String displayName;
        private final String translation;
    }

    @Value
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
         * fill out given and family tags. So we assume that the last name is the last thing in the string and all before
         * are given names.
         */
        public Names(String names) {
            String[] nameArr = names.split(" ");
            givenNames = List.of(Arrays.copyOfRange(nameArr, 0, nameArr.length - 1));
            surName = nameArr[nameArr.length - 1];
        }
    }

    @Value
    public static class GetPrescriptionResponseModel {
        private final GetPrescriptionResponseType response;
        private final PrescriptionType prescription;

        public String getEffectiveTime() {
            return DateTimeFormatter.ofPattern("YYYYMMddHHmmssZ").format(OffsetDateTime.now());
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

    }

}
