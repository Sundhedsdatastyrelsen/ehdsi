package dk.sds.ncp.cda.model;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatedWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthUnitCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PackageSizeUnitCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PatientType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SimpleCPRPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageSizeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sds.ncp.cda.EhdsiUnitMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// Based on dk.nsp.epps.service.mapping.EPrescriptionMapper.
/// TODO: Make dumber "record" type dataclass and a function
/// (GetPrescriptionResponseType, PrescriptionType) -> EPrescriptionL3

/**
 * Model for ePrescription CDA L3, for use by Freemarker template.
 */
@Value
public class EPrescriptionL3 {
    GetPrescriptionResponseType response;
    PrescriptionType prescription;
    String cdaCreatedAtTime = cdaOffsetDateTime(OffsetDateTime.now());

    /**
     * Unique ID identifying the CDA document (not the prescription itself).
     */
    String CdaInstanceId = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);

    /**
     * See
     * <a href="https://tracker.dksund.dk/confluence/display/ePPS/Nationale+OID%27er+anvendt+i+projektet">Confluence page on OIDs</a>.
     */
    Map<String, String> sourceOid = Map.of(
        "Yder", "2.16.17.710.802.1000.990.1.40"
    );

    public String getPatientFullName() {
        return Optional.of(response)
            .map(GetPrescriptionResponseType::getPatient)
            .map(PatientType::getPerson)
            .map(SimpleCPRPersonType::getName)
            .map(name -> Stream.of(name.getGivenName(), name.getMiddleName(), name.getSurname())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(" ")))
            .orElse(null);
    }


    public HealthcareProfessional getAuthor() {
        // TODO: extract professional code from prescription?
        var role = new HealthcareProfessionalRole("221", "Medical doctors");
        var org = getOrganisation();
        var orgRoot = sourceOid.get(org.getIdentifier().getSource());
        if (orgRoot == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Unknown organisation id source: %s.  Valid sources are: %s",
                    org.getIdentifier().getSource(),
                    String.join(", ", sourceOid.keySet()))
            );
        }

        var organisationId = new Identifier(org.getIdentifier().getValue(), orgRoot);

        return new HealthcareProfessional(
            getAuthorisedHealthcareProfessional().getAuthorisationIdentifier(),
            role,
            getAuthorisedHealthcareProfessionalNames(),
            new Organisation(
                organisationId,
                org.getName(),
                org.getTelephoneNumber(),
                org.getAddressLine()
            )
        );
    }

    public String getTitle() {
        var fullName = getPatientFullName();
        var id = prescription.getIdentifier();
        return String.format(
            "eHDSI ePrescription %s - %s",
            fullName, id
        );
    }

    public static String cdaOffsetDateTime(TemporalAccessor time) {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ").format(time);
    }

    public static String cdaOffsetDateTime(XMLGregorianCalendar time) {
        var zonedDateTime = time.toGregorianCalendar().toZonedDateTime();
        return cdaOffsetDateTime(zonedDateTime);
    }

    /**
     * "[...] the date and time at which this document was created as an electronic document."
     */
    public String getEffectiveTime() {
        return cdaCreatedAtTime;
    }

    /**
     * "Time of signing the document"
     * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.1.1-2022-09-12T133927.html">
     * ART-DECOR</a>
     */
    public String getSignatureTime() {
        return cdaCreatedAtTime;
    }

    public String getPrescriptionCreatedTime() {
        return cdaOffsetDateTime(prescription.getCreated().getDateTime());
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

    public AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType getAuthorisedHealthcareProfessional() {
        return modificatorWithOptionalAuthorisationIdentifier()
            .map(ModificatorWithOptionalAuthorisationIdentifierType::getContent)
            .flatMap(content -> content.stream()
                .filter(jaxb -> AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType.class.isAssignableFrom(jaxb.getDeclaredType()))
                .map(jaxb -> (AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType) jaxb.getValue())
                .findFirst())
            .orElse(null);
    }

    public OrganisationType getOrganisation() {
        return modificatorWithOptionalAuthorisationIdentifier()
            .map(ModificatorWithOptionalAuthorisationIdentifierType::getContent)
            .flatMap(content -> content.stream()
                .filter(jaxb -> OrganisationType.class.isAssignableFrom(jaxb.getDeclaredType()))
                .map(jaxb -> (OrganisationType) jaxb.getValue())
                .findFirst())
            .orElse(null);
    }

    public Names getAuthorisedHealthcareProfessionalNames() {
        return modificatorWithOptionalAuthorisationIdentifier()
            .map(ModificatorWithOptionalAuthorisationIdentifierType::getContent)
            .map(list ->
                list.stream().filter(jaxb ->
                        jaxb.getDeclaredType().isAssignableFrom(AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType.class)
                    )
                    .findFirst()
                    .orElse(null)
            )
            .map(xml -> (AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType) xml.getValue())
            .map(ahp -> new Names(ahp.getName()))
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

    @Getter
    @AllArgsConstructor
    public static class Names {
        private List<String> givenNames;
        private String surname;

        /**
         * The case where the danish spec just provides a single string with the given- and surnames, but we need to
         * fill out given and family tags. So we assume that the last name is the last thing in the string and all
         * before
         * are given names.
         */
        public Names(String names) {
            String[] nameArr = names.split(" ");
            givenNames = List.of(Arrays.copyOfRange(nameArr, 0, nameArr.length - 1));
            surname = nameArr[nameArr.length - 1];
        }

        public String fullName() {
            StringBuilder builder = new StringBuilder();
            if (givenNames != null) {
                givenNames.forEach(name -> builder.append(" ").append(name));
            }
            if (surname != null) {
                builder.append(" ").append(surname);
            }
            return builder.toString().trim();
        }
    }

    @lombok.Value
    public static class AdministrativeGender {
        String code;
        String displayName;
        String translation;
    }

    @lombok.Value
    @Builder
    public static class DrugStrength {
        String value;
        String unitCode;
        String unitText;
        String text;
    }


    /**
     * Convert the FMK package size unit (LMS15) to eHDSIUnit
     * TODO
     *
     * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/voc-1.3.6.1.4.1.12559.11.10.1.3.1.42.16-2023-05-02T180000.html">eHDSIUnit</a>
     */
    public EhdsiUnit getPackageSizeEhdsiUnit() {
        return Optional.of(prescription)
            .map(PrescriptionType::getPackageRestriction)
            .map(PackageRestrictionType::getPackageSize)
            .map(PackageSizeType::getUnitCode)
            .map(PackageSizeUnitCodeType::getValue)
            .map(EhdsiUnitMapper::fromLms)
            .orElse(null);
    }

    /**
     * Values pertaining to the
     * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/tmp-2.16.840.1.113883.3.1937.777.11.10.103-2020-10-07T082031.html">eHDSI Author Prescriber template</a>
     */
    @lombok.Value
    public static class HealthcareProfessional {
        String id;
        /**
         * Corresponds to "functionCode", should be from the
         * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/voc-1.3.6.1.4.1.12559.11.10.1.3.1.42.1-DYNAMIC.html">eHDSIHealthcareProfessionalRole value set</a>.
         */
        HealthcareProfessionalRole role;
        Names name;
        Organisation organisation;
    }

    /**
     * Corresponds to "functionCode", should be from the
     * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/voc-1.3.6.1.4.1.12559.11.10.1.3.1.42.1-DYNAMIC.html">eHDSIHealthcareProfessionalRole value set</a>.
     */
    @lombok.Value
    public static class HealthcareProfessionalRole {
        String code;
        String displayName;
    }

    @lombok.Value
    public static class Organisation {
        Identifier id;
        String name;
        String telephoneNumber;
        List<String> addressLines;
    }

    /**
     * A CDA identifier, see <a href="https://wiki.art-decor.org/index.php?title=DTr1_II.EPSOS">II.EPSOS</a>.
     */
    @lombok.Value
    public static class Identifier {
        String extension;
        String root;
    }
}
