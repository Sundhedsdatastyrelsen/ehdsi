package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.service.InformationCardService;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fsk;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FskIT {
    private final InformationCardService service = new InformationCardService(Fsk.apiClient());

    /**
     * This test simply checks that we can connect and get an answer on the data.
     *
     * @throws Exception
     */
    @Test
    void getListOfCards() throws Exception {
        var documentIdList = service.findInformationCardDetails(Fsk.cprJensJensenReadOnly, OrganizationIdentities.sundhedsdatastyrelsen());
        assertThat(documentIdList.size(), is(1));
    }

    @Test
    void getDocument() throws Exception {
        var informationCard = service.getInformationCard(Fsk.documentJensJensenFskResponse, OrganizationIdentities.sundhedsdatastyrelsen());
        assertThat(informationCard, is(notNullValue()));
        assertThat(informationCard, containsString("<ClinicalDocument"));
    }

    @ParameterizedTest
    @ValueSource(strings = {Fsk.cprJensJensenReadOnly})
    void getInformationCardFromPerson(String cpr) {
        var documentIdList = service.findInformationCardDetails(cpr, OrganizationIdentities.sundhedsdatastyrelsen());
        var documentId = documentIdList.getFirst();
        var informationCard = service.getInformationCard(documentId, OrganizationIdentities.sundhedsdatastyrelsen());
        assertThat(informationCard, is(notNullValue()));
        assertThat(informationCard, containsString("<ClinicalDocument"));
    }
}
