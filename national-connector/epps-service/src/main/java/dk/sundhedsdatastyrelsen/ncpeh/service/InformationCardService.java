package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.PublicException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.tuple.Pair;
import dk.sundhedsdatastyrelsen.ncpeh.client.FskClient;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class InformationCardService {
    private final FskClient fskClient;
    private final MinLogService minLogService;
    /**
     * The system (FOCES/VOCES) which makes the service request, i.e., certificates representing the NCPeH DK.
     * This is not the same as the parent request caller, i.e., the foreign healthcare professional.
     */
    private final NspDgwsIdentity systemCaller;

    public InformationCardService(FskClient fskClient, MinLogService minLogService, NspDgwsIdentity systemCaller) {
        this.fskClient = fskClient;
        this.minLogService = minLogService;
        this.systemCaller = systemCaller;
    }

    /// @return A list of ids of information cards.
    public List<String> findInformationCardDetails(
        String patientId
    ) {
        try {
            String cpr = PatientIdMapper.toCpr(patientId);

            final var request = AdhocQueryRequest.builder()
                // Magic important setup, see https://www.nspop.dk/display/public/web/FSK+Registry+Adapter+-+Guide+til+Anvendere
                // Response type and AdHoc Query ID are set from here
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

            var fskResponse = fskClient.list(request, systemCaller);


            return fskResponse.getRegistryObjectList()
                .getIdentifiable()
                .stream()
                .map(identifiable -> identifiable.getValue().getId())
                .toList();
        } catch (JAXBException e) {
            // TODO exception text
            throw new PublicException(500, e);
        }
    }

    public Document getInformationCard(String uniqueDocumentId, String patientId, String europeanHealthProfessionalId) {
        try {
            var documentId = splitUniqueIdToRepositoryIdAndDocumentId(uniqueDocumentId);
            final var request = RetrieveDocumentSetRequestType.builder()
                .addDocumentRequest()
                .withRepositoryUniqueId(documentId.first())
                .withDocumentUniqueId(uniqueDocumentId) //Yes, we need to use the whole unique document ID, as well as part of it earlier.
                .end().build();

            //Log that we looked up the data on the fælles stamkort, due to using the organisational endpoint
            var cpr = PatientIdMapper.toCpr(patientId);
            minLogService.logEventOnPatient(cpr, "Fælles Stamkort opslag", europeanHealthProfessionalId);

            //The endpoints does not (in the near future) support IDWS, so we have to use the organisatorial endpoint.
            var fskResponse = fskClient.getDocument(request, systemCaller);
            byte[] documentBytes = fskResponse.getDocumentResponse()
                .stream()
                .map(RetrieveDocumentSetResponseType.DocumentResponse::getDocument)
                .findFirst()
                .orElse(null);
            return documentBytes == null ? null : XmlUtils.parse(new ByteArrayInputStream(documentBytes));
        } catch (IllegalArgumentException e) {
            // TODO exception text
            throw new PublicException(400, e);
        } catch (JAXBException | XmlException e) {
            // TODO exception text
            throw new PublicException(500, e);
        }
    }

    static Pair<String, String> splitUniqueIdToRepositoryIdAndDocumentId(String uniqueDocumentId) {
        //We assume the documentId follows this format: 1.2.208.176.43210.8.10.12^aa575bf2-fde6-434c-bd0c-ccf5a512680d
        //We extract the document ID using regex capture groups to ensure that the format is correct
        String documentIdFormatRegex = "^([\\d.]+)\\^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})$";

        Pattern documentIdFormatPattern = Pattern.compile(documentIdFormatRegex);
        Matcher documentIdFormatMatcher = documentIdFormatPattern.matcher(uniqueDocumentId);

        if (documentIdFormatMatcher.find() && documentIdFormatMatcher.groupCount() == 2) {
            String oid = documentIdFormatMatcher.group(1);
            String uuid = documentIdFormatMatcher.group(2);
            return new Pair<>(oid, uuid); //Repository ID, Local document ID
        } else {
            throw new IllegalArgumentException(String.format("Cannot parse uniqueDocumentId: %s", uniqueDocumentId));
        }
    }


}
