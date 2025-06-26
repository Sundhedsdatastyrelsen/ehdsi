package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class for mapping FSK information card CDAs to the data needed in the patient summary.
 */
@Slf4j
public class FskMapper {
    private FskMapper() {
    }

    // Example of FSK information card CDA (look for the CDA V3 example):
    // https://www.nspop.dk/display/public/web/FSK+-+Guide+til+anvendere
    static class XPaths {
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

    // XPath is not thread safe so we keep a separate copy for each thread.
    private static final ThreadLocal<XPath> xpath = ThreadLocal.withInitial(() -> {
        var xp = XPathFactory.newInstance().newXPath();
        var nsCtx = new SimpleNamespaceContext();
        nsCtx.bindNamespaceUri("hl7", "urn:hl7-org:v3");
        nsCtx.bindNamespaceUri("sdtc", "urn:hl7-org:sdtc");
        xp.setNamespaceContext(nsCtx);
        return xp;
    });

    private static List<Node> evalNodeMany(Node cda, String xpathExpression) throws XPathExpressionException {
        var nodeList = (DTMNodeList) xpath.get().evaluate(xpathExpression, cda, XPathConstants.NODESET);
        var l = nodeList.getLength();
        var result = new ArrayList<Node>();
        for (var i = 0; i < l; i++) {
            result.add(nodeList.item(i));
        }
        return Collections.unmodifiableList(result);
    }

    private static List<String> evalMany(Node cda, String xpathExpression) throws XPathExpressionException {
        return evalNodeMany(cda, xpathExpression).stream().map(Node::getTextContent).toList();
    }

    static @NonNull String eval(Node node, String xpathExpression) throws XPathExpressionException {
        // TODO this trim was necessary in the test data we got. But why? Should that not be handled by xml?
        return xpath.get().evaluate(xpathExpression, node).trim();
    }

    private static Node evalNode(Node node, String xpathExpression) throws XPathExpressionException {
        return (Node) xpath.get().evaluate(xpathExpression, node, XPathConstants.NODE);
    }

    static PreferredHealthProfessional preferredHealthProfessional(Document cda) throws XPathExpressionException {
        var name = eval(cda, XPaths.preferredHpName);
        var telecoms = telecomNodesToTelecoms(evalNodeMany(cda, XPaths.preferredHpTelecoms));
        var address = addressNodeToAddress(evalNode(cda, XPaths.preferredHpAddress));

        return PreferredHealthProfessional.builder()
            .name(Name.fromFullName(name))
            .telecoms(telecoms)
            .address(address)
            .build();
    }

    static Address addressNodeToAddress(Node addressNode) throws XPathExpressionException {
        var addressLines = evalMany(addressNode, "hl7:addressLine");
        var city = eval(addressNode, "hl7:city");
        var postalCode = eval(addressNode, "hl7:postalCode");
        var countryCode = eval(addressNode, "hl7:countryCode");
        return new Address(addressLines, city, postalCode, countryCode);
    }

    static List<Telecom> telecomNodesToTelecoms(List<Node> telecomNodes) throws XPathExpressionException {
        List<Telecom> list = new ArrayList<>();
        for (Node node : telecomNodes) {
            var reportedUse = eval(node, "@use");
            var telecomUse = Arrays.stream(Telecom.Use.values())
                .filter(v -> Objects.equals(v.value, reportedUse))
                .findFirst();
            Telecom build = Telecom.builder()
                .value(eval(node, "@value"))
                .use(telecomUse.get())
                .build();
            list.add(build);
        }
        return list;
    }
}
