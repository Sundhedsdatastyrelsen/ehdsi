import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlException;
import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class XmlUtilsTest {
    @Test
    void parseExample() throws Exception {
        var example = getClass().getClassLoader().getResourceAsStream("openncp_soap_header.xml");
        assertThat(example, is(notNullValue()));

        var xml = XmlUtils.parse(example);

        assertThat(xml.getFirstChild().getLocalName(), is("Header"));
    }

    @Test
    void parseString() throws Exception {
        var goodXml = "<Envelope><GoodXml/></Envelope>";
        var result = XmlUtils.parse(goodXml);
        assertThat(result.getFirstChild().getNodeName(), is("Envelope"));
    }

    @Test
    void shouldFailOnBadXml() {
        var brokenSoap = "<Envelope><BadXml></Envelope>";
        Assertions.assertThrows(XmlException.class, () -> XmlUtils.parse(brokenSoap));
    }
}
