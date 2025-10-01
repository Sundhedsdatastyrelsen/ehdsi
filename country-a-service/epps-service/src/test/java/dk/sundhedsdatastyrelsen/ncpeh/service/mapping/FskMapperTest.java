package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static dk.sundhedsdatastyrelsen.ncpeh.service.mapping.FskMapper.splitUniqueIdToRepositoryIdAndDocumentId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FskMapperTest {
    Document testInformationCardCda(String xmlFileName) {
        try (var is = this.getClass().getClassLoader().getResourceAsStream(xmlFileName)) {
            return Utils.readXmlDocument(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void preferredHealthProfessionalTest() throws Exception {
        var cda = testInformationCardCda("informationCards/test-card.xml");
        var preferredHealthProfessional = FskMapper.preferredHealthProfessional(cda);

        assertThat(preferredHealthProfessional, is(notNullValue()));
        assertThat(preferredHealthProfessional.getName().getFullName(), is("laege praksis"));
        assertThat(preferredHealthProfessional.getTelecoms(), is(not(empty())));
        assertThat(preferredHealthProfessional.getTelecoms().getFirst().getValue(), is("tel:12345678"));
        assertThat(preferredHealthProfessional.getTelecoms().getFirst().getUse(), is(Telecom.Use.WORK_PLACE));
        assertThat(preferredHealthProfessional.getTelecoms().get(1).getValue(), is("mailto:laege@praksis.dk"));
        assertThat(preferredHealthProfessional.getAddress().getCity(), is("City"));
    }

    @Test
    void splitUniqueIdToRepositoryIdAndDocumentIdTest() {
        var testOid = "1.2.208.176.43210.8.10.12";
        var testUuid = "aa575bf2-fde6-434c-bd0c-ccf5a512680d";
        var testDocumentId = testOid+"^"+testUuid;
        var tuple = splitUniqueIdToRepositoryIdAndDocumentId(testDocumentId);
        assertThat(tuple._1(),is(testOid));
        assertThat(tuple._2(),is(testUuid));
    }
}
