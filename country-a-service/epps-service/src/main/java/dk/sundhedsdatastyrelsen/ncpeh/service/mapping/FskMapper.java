package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

        static final String effectiveTime =
            "/hl7:ClinicalDocument/hl7:effectiveTime/@value";
        static final String cdaId =
            "/hl7:ClinicalDocument/hl7:id";

    }

    /// @throws dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException if something goes wrong
    public static PreferredHealthProfessional preferredHealthProfessional(Document cda) {
        var name = xpath.evalString(XPaths.preferredHpName, cda);
        var telecoms = telecomNodesToTelecoms(xpath.evalNodes(XPaths.preferredHpTelecoms, cda));
        var address = addressNodeToAddress(xpath.evalNode(XPaths.preferredHpAddress, cda));

        return PreferredHealthProfessional.builder()
            .name(Name.fromFullName(name))
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
        for (Node node : telecomNodes) {
            var reportedUse = xpath.evalString("@use", node);
            var telecomUse = Arrays.stream(Telecom.Use.values())
                .filter(v -> Objects.equals(v.value, reportedUse))
                .findFirst();
            Telecom build = Telecom.builder()
                .value(xpath.evalString("@value", node))
                .use(telecomUse.get())
                .build();
            list.add(build);
        }
        return list;
    }

}
