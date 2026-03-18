package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.PublicException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class for mapping FSK information card CDAs to the data needed in the patient summary.
 */
@Slf4j
public class FskMapper {
    static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.HL7, XmlNamespace.SDTC);

    private FskMapper() {
    }

    // Example of FSK information card CDA (look for the CDA V3 example):
    // https://www.nspop.dk/display/public/web/FSK+-+Guide+til+anvendere
    private static class XPaths {
        private XPaths() {
            throw new IllegalStateException("Static utility class should not be instantiated");
        }

        static final String preferredHpName = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:providerOrganization/hl7:name";
        static final String preferredHpTelecoms = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:providerOrganization/hl7:telecom";
        static final String preferredHpAddress = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:providerOrganization/hl7:addr";

        static final String patientIdValue = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:id/@extension";
        static final String patientIdRoot = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:id/@root";
        static final String patientGivenNames = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:given";
        static final String patientFamilyName = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:family";
        static final String patientGenderCodeSystem = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:administrativeGenderCode/@codeSystem";
        static final String patientGenderCode = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:administrativeGenderCode/@code";
        static final String patientBirthTime = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:birthTime/@value";
        static final String patientAddressNode = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr";
    }

    /// @throws dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException if something goes wrong
    public static PreferredHealthProfessional preferredHealthProfessional(Document cda) {
        var name = xpath.evalString(XPaths.preferredHpName, cda);
        var telecoms = telecomNodesToTelecoms(xpath.evalNodes(XPaths.preferredHpTelecoms, cda));
        var address = Optional.ofNullable(xpath.evalNode(XPaths.preferredHpAddress, cda))
            .map(FskMapper::addressNodeToAddress)
            .orElse(null);

        if ((name == null || name.isEmpty()) && telecoms.isEmpty() && address == null) {
            return null;
        }

        return PreferredHealthProfessional.builder()
            .name(name == null ? null : Name.fromFullName(name))
            .telecoms(telecoms)
            .address(address)
            .build();
    }

    /// @throws dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException if something goes wrong
    private static Address addressNodeToAddress(Node addressNode) {
        var addressLines = xpath.evalNodes("hl7:addressLine", addressNode)
            .stream()
            .map(Node::getTextContent)
            .toList();
        var city = xpath.evalString("hl7:city", addressNode);
        var postalCode = xpath.evalString("hl7:postalCode", addressNode);
        var countryCode = xpath.evalString("hl7:countryCode", addressNode);
        return new Address(addressLines, city, postalCode, countryCode);
    }

    /// @throws dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException if something goes wrong
    private static List<Telecom> telecomNodesToTelecoms(List<Node> telecomNodes) {
        List<Telecom> list = new ArrayList<>();
        for (var node : telecomNodes) {
            var reportedUse = xpath.evalString("@use", node);
            var telecomUse = Arrays.stream(Telecom.Use.values())
                .filter(v -> Objects.equals(v.value, reportedUse))
                .findFirst();
            var build = Telecom.builder()
                .value(xpath.evalString("@value", node))
                .use(telecomUse.get())
                .build();
            list.add(build);
        }
        return list;
    }

    /// @throws dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException if something goes wrong
    public static Patient patient(Document cda) {
        return Patient.builder()
            .id(new CdaId(
                Oid.fromOid(xpath.evalString(XPaths.patientIdRoot, cda)).orElse(Oid.DK_CPR),
                xpath.evalString(XPaths.patientIdValue, cda)))
            .name(new Name(xpath.evalString(XPaths.patientFamilyName, cda), xpath.evalStrings(XPaths.patientGivenNames, cda)))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.fromOid(xpath.evalString(XPaths.patientGenderCodeSystem, cda))
                    .orElse(Oid.ADMINISTRATIVE_GENDER))
                .code(xpath.evalString(XPaths.patientGenderCode, cda))
                .build())
            .birthTime(parseBirthTime(xpath.evalString(XPaths.patientBirthTime, cda)))
            .address(Optional.ofNullable(xpath.evalNode(XPaths.patientAddressNode, cda))
                .map(FskMapper::addressNodeToAddress)
                .orElse(null))
            .build();
    }

    private static LocalDate parseBirthTime(String birthTime) {
        if (birthTime.matches("\\d{8}")) {
            return LocalDate.parse(birthTime, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } else if (birthTime.matches("\\d{14}\\+\\d{4}")) {
            return LocalDate.parse(birthTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ"));
        }
        log.error("Unable to parse birth time from FSK. Birth time: {}", birthTime);
        throw new PublicException("Could not read patient birth date, unable to generate patient summary.");
    }
}
