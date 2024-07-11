package dk.nsp.epps.service.mapping;

import dk.nsp.epps.Utils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class DispensationMapperTest {
    Document testDispensationCda() {
        try (var is = this.getClass().getClassLoader().getResourceAsStream("cda-edispensation1.xml")) {
            return Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void startEffectuationRequest() {
//        var sut = new DispensationMapper();
//
//        var cdaDispensation = testDispensationCda();
//
//        sut.startEffectuationRequest(
//            "1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO",
//            cdaDispensation);
    }

    @Test
    void createPharmacyEffectuationRequest() {
    }
}
