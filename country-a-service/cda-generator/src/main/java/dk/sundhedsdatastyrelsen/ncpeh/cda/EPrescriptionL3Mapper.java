package dk.sundhedsdatastyrelsen.ncpeh.cda;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SubstancesType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DrugMedicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsi.__.stamdata._3.AuthorizationType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ActiveIngredient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Author;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Organization;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Size;
import lombok.NonNull;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class EPrescriptionL3Mapper {
 private EPrescriptionL3Mapper() {
 }
 /**
 * Map a prescription response from FMK to a CDA data model.
 */
 public static EPrescriptionL3 model(EPrescriptionL3Input input) throws MapperException {
 var response = input.fmkPrescriptionResponse();
 var prescriptionIndex = input.prescriptionIndex();
 var drugMedicationResponse = input.fmkDrugMedicationResponse();
  var prescription = response.getPrescription().get(prescriptionIndex);
 Optional<DrugMedicationType> medication = Optional.empty();
 if (drugMedicationResponse != null) {
  medication = drugMedicationResponse.getDrugMedication()
  .stream()
  .filter(dm -> prescription.getAttachedToDrugMedicationIdentifier().equals(dm.getIdentifier()))
  .findAny();
 }
  var prescriptionId = new CdaId(Oid.DK_FMK_PRESCRIPTION, Long.toString(prescription.getIdentifier()));
  var i = prescription.getIndication();
 var indicationText = i.getFreeText() != null ? i.getFreeText() : i.getText();
 var activeIngredients = getActiveIngredients(
 prescription.getDrug().getStrength(), prescription.getDrug()
 .getSubstances());
 var prescriptionBuilder = EPrescriptionL3.builder()
 .documentId(new CdaId(UUID.randomUUID()))
 .title(String.format(
 "eHDSI ePrescription %s - %s", patient(response).getName()
 .getFullName(), prescription.getIdentifier()))
 .effectiveTime(OffsetDateTime.now())
 .patient(patient(response))
 .author(author(prescription, input.authorAuthorizations()))
 .signatureTime(OffsetDateTime.now())
 .parentDocumentId(prescriptionId)
 .prescriptionId(prescriptionId)
 .product(product(prescription, input.packageFormCode()))
 .packageQuantity((long) prescription.getPackageRestriction().getPackageQuantity())
 .substitutionAllowed(prescription.isSubstitutionAllowed())
 .indicationText(indicationText)
 .patientMedicationInstructions(prescription.getDosageText())
 .activeIngredients(activeIngredients.isLeft() ? activeIngredients.getLeft() : Collections.emptyList())
 .unstructuredActiveIngredients(activeIngredients.isRight() ? activeIngredients.getRight() : null);
  if (medication.isPresent()) {
  var drugMedicationType = medication.get();
  prescriptionBuilder
  .medicationStartTime(Utils.convertToOffsetDateTime(drugMedicationType.getBeginEndDate()
  .getTreatmentStartDate()))
  .dosage(DosageMapper.model(drugMedicationType.getDosage()))
  .medicationEndTime(Utils.convertToOffsetDateTime(drugMedicationType.getBeginEndDate()
  .getTreatmentEndDate()));
    var administrationRoute = drugMedicationType.getRouteOfAdministration();
  if (administrationRoute != null) {
   var administrationRouteCdaCode = CdaCode.builder()
   .codeSystem(Oid.DK_LMS11)
   .code(administrationRoute.getCode().getValue())
   .displayName(administrationRoute.getText())
   .build();
   prescriptionBuilder.administrationRoute(administrationRouteCdaCode);
  }
 } else {
  prescriptionBuilder.dosage(new Dosage.Unstructured("No unstructured dosage text.", "No medication."));
 }
  return prescriptionBuilder.build();
 }
  private static Product product(PrescriptionType prescription, String packageFormCodeRaw) throws MapperException {
 var drugId = prescription.getDrug().getIdentifier();
 var codedId = drugId != null ? CdaCode.builder()
 .codeSystem(Oid.DK_DRUG_ID)
 .code(String.valueOf(drugId.getValue()))
 .build() : null;
 var f = prescription.getDrug().getForm();
 var formCode = CdaCode.builder()
 .codeSystem(Oid.DK_LMS22)
 .code(f.getCode().getValue())
 .displayName(f.getText())
 .build();
  var ps = prescription.getPackageRestriction().getPackageSize();
 var size = new Size(PackageUnitMapper.fromLms(ps.getUnitCode().getValue()), ps.getValue());
  var packageNumber = prescription.getPackageRestriction().getPackageNumber().getValue();
 var packageCode = CdaCode.builder()
 .codeSystem(Oid.DK_VARENUMRE)
 .code(packageNumber)
 .build();
 var packageFormCode = CdaCode.builder()
 .codeSystem(Oid.DK_EMBALLAGETYPE)
 .code(packageFormCodeRaw)
 .build();
  var atc = prescription.getDrug().getATC();
 var atcCode = CdaCode.builder()
 .codeSystem(Oid.ATC)
 .codeSystemVersion("2025-01")
 .code(atc.getCode().getValue())
 .displayName(atc.getText())
 .build();
  return Product.builder()
 .drugId(codedId)
 .name(prescription.getDrug().getName())
 .description(drugStrengthText(prescription))
 .formCode(formCode)
 .size(size)
 .packageCode(packageCode)
 .packageFormCode(packageFormCode)
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
 var address = new Address(List.of(String.format("%s %s", a.getStreetName(), a.getStreetBuildingIdentifier())), a.getDistrictName(), a.getPostCodeIdentifier(), null);
  var genderCodeBuilder = CdaCode.builder()
 .codeSystem(Oid.ADMINISTRATIVE_GENDER)
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
  private static CdaId organizationId(OrganisationIdentifierType fmkOrgId) {
 // See OrganisationIdentifierPredefinedSourceType for most likely values (can theoretically be anything)
 var root = switch (fmkOrgId.getSource()) {
  case "SKS" -> Oid.DK_SKS;
  case "Yder" -> Oid.DK_YDER;
  case "EAN-Lokationsnummer" -> Oid.EAN;
  case "CVR" -> Oid.DK_CVR;
  case "SOR" -> Oid.DK_SOR;
  // remaining: CVR-P, Kommunekode, Udenlandsk
  // have no OID that we know of.
  default -> null;
 };
 if (root == null) {
  // when we don't have an OID matching the source then we use a generic OID
  // and put the source name into the extension:
  return new CdaId(Oid.DK_REGISTRIES, String.format("%s^%s", fmkOrgId.getSource(), fmkOrgId.getValue()));
 }
 return new CdaId(root, fmkOrgId.getValue());
 }
  private static Organization authorOrganization(PrescriptionType prescription) throws MapperException {
 var org = authorOrganizationXml(prescription);
  var id = organizationId(org.getIdentifier());
 Address address = null;
 if (!org.getAddressLine().isEmpty()) {
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
  address = new Address(streetAddressLines, city, postalCode, "DK");
 }
  return new Organization(id, org.getName(), org.getTelephoneNumber(), address);
 }
  private static Author author(PrescriptionType prescription, List<AuthorizationType> authorizationTypes) throws MapperException {
 // https://www.nspop.dk/display/public/web/Autorisation has the list of education codes
 // We should use the translation layer for these codes, but they are also used in L1, which isn't mapped in
 // OpenNCP, so we have to map it in the code.
 var functionCode = FunctionCodeMapper.mapToFunctionCode(authorizationTypes.stream()
 .findFirst()
 .map(AuthorizationType::getEducationCode)
 // 0000 means 'Erstatningsautorisation' replacement authorization
 .orElse("0000"));
 var cdaFunctionCode = CdaCode.builder()
 .codeSystem(Oid.ISCO)
 .code(functionCode)
 .build();
 var creator = getAuthorizedHealthcareProfessional(prescription);
  return Author.builder()
 .functionCode(cdaFunctionCode)
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
 return prescription.getCreated()
 .getBy()
 .getContent()
 .stream()
 .filter(jaxb -> OrganisationType.class.isAssignableFrom(jaxb.getDeclaredType()))
 .map(jaxb -> (OrganisationType) jaxb.getValue())
 .findFirst()
 .orElseThrow(() -> new MapperException("Cannot find prescription creator organization"));
 }
  public static AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType getAuthorizedHealthcareProfessional(PrescriptionType prescriptionType) throws MapperException {
 return prescriptionType.getCreated()
 .getBy()
 .getContent()
 .stream()
 .filter(jaxb -> AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType.class.isAssignableFrom(jaxb.getDeclaredType()))
 .map(jaxb -> (AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType) jaxb.getValue())
 .findFirst()
 .orElseThrow(() -> new MapperException("Cannot find prescription creator information"));
 }
  private static @NonNull Either<List<ActiveIngredient>, String> getActiveIngredients(DrugStrengthType strength, SubstancesType substances) {
 if (substances == null
 || substances.getActiveSubstance() == null
 || substances.getActiveSubstance().stream().allMatch(ai -> getSubstanceText(ai) == null)) {
  return Either.ofRight(null);
 }
  if (substances.getActiveSubstance().size() == 1) {
  var text = getSubstanceText(substances.getActiveSubstance().getFirst());
  var codedStrength = SubstanceUnitMapper.fromLms(strength.getUnitCode().getValue());
  if (text != null && codedStrength != null) {
   return Either.ofLeft(List.of(ActiveIngredient.builder()
   .name(text)
   .numerator(strength.getValue())
   .numeratorUnit(codedStrength.numeratorUnit())
   .denominator(codedStrength.denominator())
   .denominatorUnit(codedStrength.denominatorUnit())
   .translation(codedStrength.translation())
   .build()));
  }
  // If the strength is not coded, or we can't find the text, fall back to unstructured.
 }
  // When there is more than 1 active substance, we don't have the strength in a structured format, so we return
 // the list unstructured.
 return Either.ofRight(Stream.concat(
 Stream.of(getSubstanceStrengthText(strength)), substances.getActiveSubstance()
 .stream()
 .map(EPrescriptionL3Mapper::getSubstanceText))
 .filter(Objects::nonNull)
 .collect(Collectors.joining("; ")));
 }
  private static String getSubstanceText(ActiveSubstanceType substance) {
 if (substance == null) return null;
 if (substance.getSubstanceText() != null) return substance.getSubstanceText().getValue();
 if (substance.getText() != null) return substance.getText();
 if (substance.getFreeText() != null) return substance.getFreeText();
 return null;
 }
  private static String getSubstanceStrengthText(DrugStrengthType strength) {
 if (strength == null) return null;
 if (strength.getUnitText() != null) return strength.getUnitText();
 if (strength.getText() != null) return strength.getText().getValue();
 return null;
 }
  private static String drugStrengthText(@NonNull PrescriptionType prescription) {
 if (prescription.getDrug() == null || prescription.getDrug().getStrength() == null ||
 prescription.getDrug().getStrength().getText() == null ||
 prescription.getDrug().getStrength().getText().getValue() == null)
 return null;
 return prescription.getDrug().getStrength().getText().getValue();
 }
}
