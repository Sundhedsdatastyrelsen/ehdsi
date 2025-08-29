package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.service.InformationCardService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fsk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FskIT {
    private final InformationCardService service = new InformationCardService(
        Fsk.apiClient(),
        MinLogIT.minLogService(),
        TestIdentities.systemIdentity);

    /**
     * This test simply checks that we can connect and get an answer on the data.
     */
    @Test
    void getListOfCards() throws Exception {
        var documentIdList = service.findInformationCardDetails(Fsk.cprJensJensenReadOnly, "DE^ad93e02e-6732-4a06-ad73-6c491b20f4f9");
        assertThat(documentIdList.size(), is(1));
    }

    @Test
    void getDocument() throws Exception {
        var informationCard = service.getInformationCard(Fsk.documentJensJensenFskResponse);
        assertThat(informationCard, is(notNullValue()));
        assertThat(
            "the clinical document tag is there", informationCard.getElementsByTagName("ClinicalDocument")
                .getLength(), is(greaterThan(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {Fsk.cprJensJensenReadOnly})
    void getInformationCardFromPerson(String cpr) {
        var documentIdList = service.findInformationCardDetails(cpr, "DE^ad93e02e-6732-4a06-ad73-6c491b20f4f9");
        var documentId = documentIdList.getFirst();
        var informationCard = service.getInformationCard(documentId);
        assertThat(informationCard, is(notNullValue()));
        assertThat(
            "the clinical document tag is there",
            informationCard.getElementsByTagName("ClinicalDocument").getLength(),
            is(greaterThan(0)));
    }
}
