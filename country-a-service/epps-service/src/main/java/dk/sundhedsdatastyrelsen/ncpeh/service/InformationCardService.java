package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.nsp.test.idp.model.Identity;
import dk.sundhedsdatastyrelsen.ncpeh.client.FskClient;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class InformationCardService {
    private final FskClient fskClient;
    private final MinLogService minLogService;

    public InformationCardService(FskClient fskClient, MinLogService minLogService) {
        this.fskClient = fskClient;
        this.minLogService = minLogService;
    }

    public List<String> findInformationCardDetails(
        String patientId,
        String healthcareOfficialId,
        Identity caller
    ) {
        try {
            String cpr = PatientIdMapper.toCpr(patientId);

            final var request = AdhocQueryRequest.builder()
                /** Magic important setup, see https://www.nspop.dk/display/public/web/FSK+Registry+Adapter+-+Guide+til+Anvendere
                 * Response type and AdHoc Query ID are set from here
                 * **/
                .withResponseOption(ResponseOptionType.builder()
                    .withReturnType("LeafClass")
                    .withReturnComposedObjects(true)
                    .build())
                .withAdhocQuery(AdhocQueryType.builder()
                    .withId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d") //See above
                    .withSlot(
                        SlotType1.builder()
                            .withName("$XDSDocumentEntryPatientId")
                            .withValueList(ValueListType.builder()
                                .withValue("'" + cpr + "^^^&1.2.208.176.1.2&ISO'")
                                .build())
                            .build(),
                        SlotType1.builder()
                            .withName("$XDSDocumentEntryType")
                            .withValueList(ValueListType.builder()
                                .withValue(
                                    "('urn:uuid:34268e47-fdf5-41a6-ba33-82133c465248')",
                                    "('urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1')")
                                .build())
                            .build(),
                        SlotType1.builder()
                            .withName("$XDSDocumentEntryStatus")
                            .withValueList(ValueListType.builder()
                                .withValue("('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')")
                                .build())
                            .build(),
                        SlotType1.builder()
                            .withName("$XDSDocumentEntryFormatCode")
                            .withValueList(ValueListType.builder()
                                .withValue("('urn:ad:dk:medcom:pdc-v3.0:full^^1.2.208.184.100.10')")
                                .build())
                            .build())
                    .build())
                .build();

            var fskResponse = fskClient.list(request, caller);

            minLogService.logEventOnPatient(cpr, "FSK Opslag - Stamkort", healthcareOfficialId, caller);

            return fskResponse.getRegistryObjectList()
                .getIdentifiable()
                .stream()
                .map(identifiable -> identifiable.getValue().getId())
                .toList();
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public String getInformationCard(String uniqueDocumentId, Identity caller) {
        try {
            var documentId = splitUniqueIdToRepositoryIdAndDocumentId(uniqueDocumentId);
            final var request = RetrieveDocumentSetRequestType.builder()
                .addDocumentRequest()
                .withRepositoryUniqueId(documentId._1())
                .withDocumentUniqueId(uniqueDocumentId) //Yes, we need to use the whole unique document ID, as well as part of it earlier.
                .end().build();

            //TODO: We should log that we did this to MinLog, since we do it as an organisation
            //The endpoints does not (in the near future) support IDWS, so we have to use the organisatorial endpoint.
            var fskResponse = fskClient.getDocument(request, caller);
            var document = fskResponse.getDocumentResponse()
                .stream()
                .map(dr -> new String(dr.getDocument(), StandardCharsets.UTF_8))
                .findFirst();
            return document.orElse(null);

        } catch (IllegalArgumentException e) {
            throw new CountryAException(HttpStatus.BAD_REQUEST, e);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

    }

    private static Tuple<String, String> splitUniqueIdToRepositoryIdAndDocumentId(String uniqueDocumentId) {
        //We assume the documentId follows this format: 1.2.208.176.43210.8.10.12^aa575bf2-fde6-434c-bd0c-ccf5a512680d
        String[] parts = uniqueDocumentId.split("\\^");
        if (parts.length != 2) {
            throw new IllegalArgumentException(String.format("Cannot parse uniqueDocumentId: %s", uniqueDocumentId));
        }
        return new Tuple<>(parts[0], parts[1]); //Repository ID, Local document ID
    }

}


