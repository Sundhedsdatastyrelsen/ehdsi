package dk.sds.ncp.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sds.ncp.cda.model.Address;
import dk.sds.ncp.cda.model.Author;
import dk.sds.ncp.cda.model.CdaCode;
import dk.sds.ncp.cda.model.CdaId;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import dk.sds.ncp.cda.model.Name;
import dk.sds.ncp.cda.model.Organization;
import dk.sds.ncp.cda.model.Patient;
import dk.sds.ncp.cda.model.Product;
import dk.sds.ncp.cda.model.Size;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class EPrescriptionL3Mapper {
    /**
     * Map a prescription response from FMK to a CDA data model.
     */
    public static EPrescriptionL3 model(GetPrescriptionResponseType response, int prescriptionIndex) throws MapperException {
        var prescription = response.getPrescription().get(prescriptionIndex);

        var prescriptionId = new CdaId(Oid.DK_FMK_PRESCRIPTION, Long.toString(prescription.getIdentifier()));

        var i = prescription.getIndication();
        var indicationText = i.getFreeText() != null ? i.getFreeText() : i.getText();
        return EPrescriptionL3.builder()
            .documentId(new CdaId(UUID.randomUUID()))
            .title(String.format(
                "eHDSI ePrescription %s - %s",
                patient(response).getName().getFullName(), prescription.getIdentifier()))
            .effectiveTime(OffsetDateTime.now())
            .patient(patient(response))
            .author(author(prescription))
            .signatureTime(OffsetDateTime.now())
            .parentDocumentId(prescriptionId)
            .prescriptionId(prescriptionId)
            .entryText(prescription.getDosageText())
            .product(product(prescription))
            .packageQuantity((long) prescription.getPackageRestriction().getPackageQuantity())
            .substitutionAllowed(prescription.isSubstitutionAllowed())
            .indicationText(indicationText)
            .build();
    }

    private static Product product(PrescriptionType prescription) throws MapperException {
        var f = prescription.getDrug().getForm();
        var formCode = CdaCode.builder()
            .codeSystem(Oid.DK_LMS22)
            .codeSystemName("LMS22")
            .code(f.getCode().getValue())
            .displayName(f.getText())
            .build();

        var ps = prescription.getPackageRestriction().getPackageSize();
        var size = new Size(
            EhdsiUnitMapper.fromLms(ps.getUnitCode().getValue()),
            ps.getValue());

        var packageCode = CdaCode.builder()
            .codeSystem(Oid.DK_LMS02)
            .codeSystemName("LMS02")
            .code(prescription.getPackageRestriction().getPackageNumber().getValue())
            .build();

        var atc = prescription.getDrug().getATC();
        var atcCode = CdaCode.builder()
            .codeSystem(Oid.ATC)
            .codeSystemName("Anatomical Therapeutic Chemical")
            .codeSystemVersion("2024-01")
            .code(atc.getCode().getValue())
            .displayName(atc.getText())
            .build();

        return Product.builder()
            .name(prescription.getDrug().getName())
            .description(drugStrengthText(prescription))
            .formCode(formCode)
            .size(size)
            .packageCode(packageCode)
            .atcCode(atcCode)
            .build();
    }

    private static Patient patient(GetPrescriptionResponseType response) throws MapperException {
        var person = response.getPatient().getPerson();
        var id = person.getPersonIdentifier();
        if (!Objects.equals(id.getSource(), "CPR")) {
            throw new MapperException("Only CPR person ids supported, got: " + id.getSource());
        }

        var a = response.getPatient().getAddress();
        var address = new Address(
            List.of(String.format("%s %s", a.getStreetName(), a.getStreetBuildingIdentifier())),
            a.getDistrictName(),
            a.getPostCodeIdentifier(),
            null);

        var genderCodeBuilder = CdaCode.builder()
            .codeSystem(Oid.ADMINISTRATIVE_GENDER)
            .codeSystemName("AdministrativeGender")
            .codeSystemVersion("913-20091020");

        switch (person.getGender()) {
            case FEMALE -> genderCodeBuilder.code("F").displayName("Female");
            case MALE -> genderCodeBuilder.code("M").displayName("Male");
        }

        var n = person.getName();

        return Patient.builder()
            .id(new CdaId(Oid.DK_CPR, id.getValue()))
            .address(address)
            .name(Name.fromFirstMiddleLast(n.getGivenName(), n.getMiddleName(), n.getSurname()))
            .genderCode(genderCodeBuilder.build())
            .birthTime(localDate(person.getBirthDate()))
            .build();
    }

    private static Organization authorOrganization(PrescriptionType prescription) throws MapperException {
        var org = authorOrganizationXml(prescription);

        if (!Objects.equals(org.getIdentifier().getSource(), "Yder")) {
            throw new MapperException(
                "Invalid organization identifier source, expected Yder got: "
                + org.getIdentifier().getSource());
        }

        var id = new CdaId(Oid.DK_YDER, org.getIdentifier().getValue());

        var streetAddressLines = new LinkedList<String>();
        String city = null, postalCode = null;
        var postalCityPattern = Pattern.compile("(\\d{4}) (.+)");
        for (var line : org.getAddressLine()) {
            var m = postalCityPattern.matcher(line);
            if (m.matches()) {
                postalCode = m.group(1);
                city = m.group(2);
            } else {
                streetAddressLines.add(line);
            }
        }
        if (city == null || postalCode == null) {
            throw new MapperException("Unable to extract postal code and city from author organization address");
        }

        return new Organization(
            id,
            org.getName(),
            org.getTelephoneNumber(),
            new Address(streetAddressLines, city, postalCode, "DK"));
    }

    private static Author author(PrescriptionType prescription) throws MapperException {
        // TODO: extract professional code from prescription?
        var functionCode = CdaCode.builder()
            .code("221")
            .displayName("Medical doctors")
            .codeSystem(Oid.HEALTHCARE_PROFESSIONAL_ROLES)
            .codeSystemName("ISCO")
            .build();

        var creator = createdByXml(prescription);

        return Author.builder()
            .functionCode(functionCode)
            .time(offsetDateTime(prescription.getCreated().getDateTime()))
            .id(new CdaId(Oid.DK_AUTHORIZATION_REGISTRY, creator.getAuthorisationIdentifier()))
            .name(Name.fromFullName(creator.getName()))
            .organization(authorOrganization(prescription))
            .build();
    }

    /**
     * Converts an XML date (xs:date) to a LocalDate.
     */
    private static LocalDate localDate(XMLGregorianCalendar date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
    }

    /**
     * Converts an XML datetime (xs:dateTime) to an OffsetDateTime.
     */
    private static OffsetDateTime offsetDateTime(XMLGregorianCalendar dateTime) {
        return dateTime.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
    }

    private static OrganisationType authorOrganizationXml(PrescriptionType prescription) throws MapperException {
        return prescription
            .getCreated()
            .getBy()
            .getContent()
            .stream()
            .filter(jaxb -> OrganisationType.class.isAssignableFrom(jaxb.getDeclaredType()))
            .map(jaxb -> (OrganisationType)jaxb.getValue())
            .findFirst()
            .orElseThrow(() -> new MapperException("Cannot find prescription creator organization"));
    }

    private static AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType createdByXml(PrescriptionType prescriptionType) throws MapperException {
        return prescriptionType
            .getCreated()
            .getBy()
            .getContent()
            .stream()
            .filter(jaxb -> AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType.class.isAssignableFrom(jaxb.getDeclaredType()))
            .map(jaxb -> (AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType) jaxb.getValue())
            .findFirst()
            .orElseThrow(() -> new MapperException("Cannot find prescription creator information"));
    }

    private static String drugStrengthText(PrescriptionType prescription) throws MapperException {
        for (var xml : prescription.getDrug().getStrength().getContent()) {
            if (xml.getName().getLocalPart().equals("Text")) {
                return ((DrugStrengthTextType) xml.getValue()).getValue();
            }
        }
        throw new MapperException("Missing Text element on DrugStrength");
    }
}